package need.clarity.audio;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.TargetDataLine;

public final class BeatAnalyzer {
    public static final int BANDS = 32;
    public static final String DESKTOP = "Desktop Audio";
    private static final int N = 1024;
    private static final int HOP = 512;
    private static final float RATE = 44100.0F;
    private static final String[] LOOPBACK_HINTS;
    private static final float[] bands;
    private static final float[] peaks;
    private static volatile float bass;
    private static final long[] beatTimes;
    private static int beatIdx;
    private static volatile boolean running;
    private static volatile boolean hasAudio;
    private static volatile String deviceName;
    private static volatile float sensitivity;
    private static Thread thread;
    private static String wantedDevice;

    private BeatAnalyzer() {
    }

    public static synchronized void start(String arg0) {
        if (arg0 == null) {
        } else {
        }
        String var1 = arg0.trim();
        if (running) {
            if (var1.equals(wantedDevice)) {
                return;
            }
            stop();
            wantedDevice = var1;
            running = true;
            thread = new Thread(() -> BeatAnalyzer.lambda$start$0(var1), "clarity-music-beats");
            thread.setDaemon(true);
            thread.start();
            return;
        }
    }

    public static synchronized void stop() {
        running = false;
        if (thread != null) {
            thread.interrupt();
            thread = null;
        }
        hasAudio = false;
    }

    public static void setSensitivity(float arg0) {
        sensitivity = Math.max(0.1F, arg0);
    }

    public static boolean hasAudio() {
        return hasAudio;
    }

    public static String device() {
        return deviceName;
    }

    public static float bass() {
        return bass;
    }

    public static void copyBands(float[] arg0, float[] arg1) {
        float[] var2 = bands;
        System.arraycopy(bands, 0, arg0, 0, 32);
        System.arraycopy(peaks, 0, arg1, 0, 32);
        float[] var3 = var2;
        throw var3;
    }

    public static void beatAges(float[] arg0, long arg1) {
        long[] var3 = beatTimes;
        int var4 = 0;
        while (true) {
            if (var4 >= 4) break;
            long var5 = beatTimes[((((beatIdx - 1) - var4) + 8) % 4)];
            if (var5 == 0L) {
            } else {
            }
            var4[-1.0F] = ((float) (((double) (arg1 - var5)) / 1.0E9));
            var4++;
        }
        long[] var7 = var3;
        throw var7;
    }

    private static void loop(String arg0) {
        TargetDataLine var1 = null;
        PulseCapture var2 = null;
        int var3 = 0;
        int var4 = 0;
        int var10 = 0;
        float var11 = 0.0F;
        float var12 = 0.0F;
        long var13 = 0L;
        int var15 = 0;
        int var16 = 0;
        int var18 = 0;
        float var19 = 0.0F;
        int var20 = 0;
        int var21 = 0;
        int var22 = 0;
        double var23 = 0.0;
        int var25 = 0;
        float var27 = 0.0F;
        int var28 = 0;
        var1 = null;
        var2 = null;
        try {
            if (!arg0.isEmpty()) {
                if (!arg0.equalsIgnoreCase("auto")) {
                    if (arg0.equalsIgnoreCase("Desktop Audio")) {
                    }
                }
                var3 = 0;
                if (var3 != 0) {
                    var2 = PulseCapture.open("@DEFAULT_MONITOR@", 44100, 2, 2048);
                    if (var2 != null) {
                        deviceName = "Desktop Audio (what your speakers play)";
                    } else {
                        if (arg0.equalsIgnoreCase("Desktop Audio")) {
                        }
                        if (var2 == null) {
                            if (arg0.equalsIgnoreCase("Desktop Audio")) {
                            } else {
                            }
                            var1 = open(arg0);
                        }
                        if (var2 == null) {
                            if (var1 == null) {
                            }
                        }
                    }
                }
            }
        } catch (Throwable ignored) {
        }
        hasAudio = false;
        if (var1 != null) {
            try {
                var1.stop();
                var1.close();
            } catch (Throwable ignored) {
            }
            var4 = "";
        }
        if (var2 != null) {
            var2.close();
        }
        return;
        try {
            if (var2 != null) {
            } else {
            }
            var4 = var1.getFormat().getChannels();
            byte[] var5 = new byte[(1024 * var4)];
            float[] var6 = new float[1024];
            float[] var7 = new float[1024];
            float[] var8 = new float[1024];
            float[] var9 = new float[1024];
            var10 = 0;
            while (true) {
                if (var10 >= 1024) break;
                var9[var10] = ((float) (0.5 - (0.5 * Math.cos(((6.283185307179586 * ((double) var10)) / 1023.0)))));
                var10++;
            }
            var10 = new float[32];
            Arrays.fill(var10, 0.001F);
            var11 = 0.0F;
            var12 = 0.001F;
            var13 = 0L;
            var15 = 0;
            hasAudio = true;
            while (true) {
                if (!running) break;
                if (Thread.currentThread().isInterrupted()) break;
                if (var2 != null) {
                    if (var2.read(var5)) {
                    } else {
                    }
                    var16 = -1;
                } else {
                    var16 = var1.read(var5, 0, var5.length);
                }
                if (var16 < 0) {
                } else {
                    if (var16 <= 0) {
                    }
                    int var17 = (var16 / (2 * var4));
                    System.arraycopy(var6, var17, var6, 0, (1024 - var17));
                    var18 = 0;
                    while (true) {
                        if (var18 >= var17) break;
                        var19 = 0.0F;
                        var20 = 0;
                        while (true) {
                            if (var20 >= var4) break;
                            var21 = (((var18 * var4) + var20) * 2);
                            var19 = (var19 + (((float) ((short) ((var5[(var21 + 1)] << 8) | (var5[var21] & 255)))) / 32768.0F));
                            var20++;
                        }
                        var6[((1024 - var17) + var18)] = (var19 / ((float) var4));
                        var18++;
                    }
                    var15 = Math.min(1024, (var15 + var17));
                    if (var15 < 1024) {
                    }
                    var18 = 0;
                    while (true) {
                        if (var18 >= 1024) break;
                        var7[var18] = (var6[var18] * var9[var18]);
                        var8[var18] = 0.0F;
                        var18++;
                    }
                    fft(var7, var8);
                    var18 = new float[32];
                    var19 = 0.0F;
                    var20 = 0;
                    while (true) {
                        if (var20 >= 32) break;
                        var21 = (40.0 * Math.pow(400.0, (((double) var20) / 32.0)));
                        var23 = (40.0 * Math.pow(400.0, (((double) (var20 + 1)) / 32.0)));
                        var25 = Math.max(1, ((int) ((var21 * 1024.0) / 44100.0)));
                        int var26 = Math.max((var25 + 1), ((int) ((var23 * 1024.0) / 44100.0)));
                        var27 = 0.0F;
                        var28 = var25;
                        while (true) {
                            if (var28 >= var26) break;
                            if (var28 >= 512) break;
                            var27 = Math.max(var27, ((var7[var28] * var7[var28]) + (var8[var28] * var8[var28])));
                            var28++;
                        }
                        var28 = ((float) Math.sqrt(((double) var27)));
                        var10[var20] = Math.max(Math.max(var28, 0.05F), (var10[var20] * 0.9985F));
                        float var29 = (var28 / Math.max(var10[var20], 1.0E-4F));
                        var18[var20] = (((float) Math.pow(((double) Math.min(1.0F, var29)), 0.8)) * Math.min(1.0F, (var10[var20] * 400.0F)));
                        if (var23 < 160.0) {
                            var19 = (var19 + var28);
                        }
                        var20++;
                    }
                    var20 = sensitivity;
                    var21 = bands;
                    var22 = 0;
                    while (true) {
                        if (var22 >= 32) break;
                        var23 = Math.min(1.0F, (var18[var22] * var20));
                        if (var23 > bands[var22]) {
                        } else {
                        }
                        var22[(bands[var22] + ((var23 - bands[var22]) * 0.65F))] = (bands[var22] * 0.82F);
                        peaks[var22] = Math.max(bands[var22], (peaks[var22] - 0.012F));
                        var22++;
                    }
                    Object var30 = var21;
                    throw var30;
                    var12 = Math.max(var19, (var12 * 0.999F));
                    var11 = ((var11 * 0.94F) + (var19 * 0.06F));
                    var21 = (var19 / Math.max(var12, 1.0E-4F));
                    bass = ((bass * 0.7F) + (Math.min(1.0F, (var21 * var20)) * 0.3F));
                    var22 = System.nanoTime();
                    if ((var19 > 0.8F)) {
                        if (var19 > (var11 * (1.35F / Math.max(0.3F, var20)))) {
                            if (var21 > 0.3F) {
                                if ((var22 - var13) > 180000000L) {
                                    var13 = var22;
                                    var25 = beatTimes;
                                    beatTimes[beatIdx] = var22;
                                    beatIdx = ((beatIdx + 1) % 4);
                                    Object var31 = var25;
                                    throw var31;
                                }
                            }
                        }
                    }
                }
                hasAudio = false;
                if (var1 == null) break;
                try {
                    var1.stop();
                    var1.close();
                } catch (Throwable ignored) {
                }
                var3 = var25;
                if (var2 == null) break;
                var2.close();
                var3 = beatTimes;
                hasAudio = false;
                if (var1 == null) break;
                try {
                    var1.stop();
                    var1.close();
                } catch (Throwable ignored) {
                }
                var3 = var21;
                if (var2 == null) break;
                var2.close();
                float[] var32 = bands;
                hasAudio = false;
                if (var1 == null) break;
                try {
                    var1.stop();
                    var1.close();
                } catch (Throwable ignored) {
                }
                float[] var33 = bands;
                if (var2 == null) break;
                var2.close();
                throw var32;
                return;
            }
        } catch (Throwable ignored) {
        }
    }

    private static TargetDataLine open(String arg0) {
        Mixer.Info[] var3 = null;
        int var4 = 0;
        int var5 = 0;
        Mixer.Info var6 = null;
        Mixer var7 = null;
        int var8 = 0;
        int var9 = 0;
        int var10 = 0;
        new AudioFormat[2][0] = new AudioFormat(44100.0F, 16, 2, true, false);
        new AudioFormat[2][1] = new AudioFormat(44100.0F, 16, 1, true, false);
        AudioFormat[] var1 = new AudioFormat[2];
        ArrayList var2 = new ArrayList();
        var3 = AudioSystem.getMixerInfo();
        var4 = var3.length;
        var5 = 0;
        while (true) {
            if (var5 >= var4) break;
            var6 = var3[var5];
            var7 = AudioSystem.getMixer(var6);
            var8 = var1;
            var9 = var8.length;
            var10 = 0;
            while (true) {
                if (var10 >= var9) break;
                AudioFormat var11 = var8[var10];
                if (var7.isLineSupported(new DataLine.Info(TargetDataLine.class, var11))) {
                    var2.add(var6);
                } else {
                    var10++;
                }
                var5++;
            }
        }
        var3 = null;
        var4 = arg0.toLowerCase(Locale.ROOT);
        if (!var4.isEmpty()) {
            if (!var4.equals("auto")) {
                var5 = var2.iterator();
                while (true) {
                    if (!var5.hasNext()) break;
                    var6 = ((Mixer.Info) var5.next());
                    if (var6.getName().equalsIgnoreCase(arg0)) {
                        var3 = var6;
                    } else {
                    }
                    if (var3 != null) break;
                    var5 = var2.iterator();
                    while (true) {
                        if (!var5.hasNext()) break;
                        var6 = ((Mixer.Info) var5.next());
                        if (label(var6).toLowerCase(Locale.ROOT).contains(var4)) {
                            var3 = var6;
                        } else {
                        }
                        if (var3 != null) break;
                    }
                }
            }
            if (var3 == null) {
                var5 = LOOPBACK_HINTS;
                var6 = var5.length;
                var7 = 0;
                while (true) {
                    if (var7 >= var6) break;
                    var8 = var5[var7];
                    var9 = var2.iterator();
                    while (true) {
                        if (!var9.hasNext()) break;
                        var10 = ((Mixer.Info) var9.next());
                        if (label(var10).toLowerCase(Locale.ROOT).contains(var8)) {
                            var3 = var10;
                        } else {
                        }
                    }
                    var7++;
                }
                var5 = var1;
                var6 = var5.length;
                var7 = 0;
                while (true) {
                    if (var7 >= var6) break;
                    var8 = var5[var7];
                    try {
                        var9 = new DataLine.Info(TargetDataLine.class, var8);
                        if (var3 != null) {
                        } else {
                        }
                        var10 = ((TargetDataLine) AudioSystem.getLine(var9));
                        var10.open(var8, (4096 * var8.getChannels()));
                        var10.start();
                        if (var3 != null) {
                        } else {
                        }
                        deviceName = "system default input";
                    } catch (Throwable ignored) {
                    }
                    return var10;
                }
                return null;
            }
        }
    }

    private static String label(Mixer.Info arg0) {
        return (arg0.getName()) + " (" + (arg0.getDescription()) + ")";
    }

    public static List deviceNames() {
        Mixer var7 = null;
        ArrayList var0 = new ArrayList();
        if (PulseCapture.available()) {
            var0.add("Desktop Audio");
        }
        AudioFormat var1 = new AudioFormat(44100.0F, 16, 2, true, false);
        AudioFormat var2 = new AudioFormat(44100.0F, 16, 1, true, false);
        Mixer.Info[] var3 = AudioSystem.getMixerInfo();
        int var4 = var3.length;
        int var5 = 0;
        while (true) {
            if (var5 >= var4) break;
            Mixer.Info var6 = var3[var5];
            try {
                var7 = AudioSystem.getMixer(var6);
                if (!(var7.isLineSupported(new DataLine.Info(TargetDataLine.class, var1)))) {
                    if (var7.isLineSupported(new DataLine.Info(TargetDataLine.class, var2))) {
                    }
                }
                if (!var0.contains(var6.getName())) {
                    var0.add(var6.getName());
                }
                var5++;
            } catch (Throwable ignored) {
            }
        }
        return var0;
    }

    public static String listDevices() {
        ArrayList var0 = new ArrayList();
        AudioFormat var1 = new AudioFormat(44100.0F, 16, 2, true, false);
        Mixer.Info[] var2 = AudioSystem.getMixerInfo();
        int var3 = var2.length;
        int var4 = 0;
        while (true) {
            if (var4 >= var3) break;
            Mixer.Info var5 = var2[var4];
            Mixer var6 = AudioSystem.getMixer(var5);
            if (!(var6.isLineSupported(new DataLine.Info(TargetDataLine.class, var1)))) {
                if (var6.isLineSupported(new DataLine.Info(TargetDataLine.class, new AudioFormat(44100.0F, 16, 1, true, false)))) {
                }
            }
            var0.add(var5.getName());
            var4++;
        }
        return var0.toString();
    }

    private static void fft(float[] arg0, float[] arg1) {
        int var3 = 0;
        int var4 = 0;
        int var5 = 0;
        float var6 = 0.0F;
        int var8 = 0;
        float var9 = 0.0F;
        float var10 = 0.0F;
        int var2 = arg0.length;
        var3 = 1;
        var4 = 0;
        while (true) {
            if (var3 >= var2) break;
            var5 = (var2 >> 1);
            while (true) {
                if ((var4 & var5) == 0) break;
                var4 = (var4 ^ var5);
                var5 = (var5 >> 1);
            }
            var4 = (var4 ^ var5);
            if (var3 < var4) {
                var6 = arg0[var3];
                arg0[var3] = arg0[var4];
                arg0[var4] = var6;
                var6 = arg1[var3];
                arg1[var3] = arg1[var4];
                arg1[var4] = var6;
            }
            var3++;
        }
        var3 = 2;
        while (true) {
            if (var3 > var2) break;
            var4 = (-6.283185307179586 / ((double) var3));
            var6 = ((float) Math.cos(var4));
            float var7 = ((float) Math.sin(var4));
            var8 = 0;
            while (true) {
                if (var8 >= var2) break;
                var9 = 1.0F;
                var10 = 0.0F;
                int var11 = 0;
                while (true) {
                    if (var11 >= (var3 / 2)) break;
                    int var12 = (var8 + var11);
                    int var13 = (var12 + (var3 / 2));
                    float var14 = ((arg0[var13] * var9) - (arg1[var13] * var10));
                    float var15 = ((arg0[var13] * var10) + (arg1[var13] * var9));
                    arg0[var13] = (arg0[var12] - var14);
                    arg1[var13] = (arg1[var12] - var15);
                    arr[0] = (arg0[var12] + var14);
                    arr[0] = (arg1[var12] + var15);
                    float var16 = ((var9 * var6) - (var10 * var7));
                    var10 = ((var9 * var7) + (var10 * var6));
                    var9 = var16;
                    var11++;
                }
                var8 = (var8 + var3);
            }
            var3 = (var3 << 1);
        }
    }

    static {
        new String[9][0] = "monitor";
        new String[9][1] = "stereo mix";
        new String[9][2] = "loopback";
        new String[9][3] = "what u hear";
        new String[9][4] = "wave out";
        new String[9][5] = "cable output";
        new String[9][6] = "blackhole";
        new String[9][7] = "soundflower";
        new String[9][8] = "mix";
        LOOPBACK_HINTS = new String[9];
        bands = new float[32];
        peaks = new float[32];
        bass = 0.0F;
        beatTimes = new long[4];
        beatIdx = 0;
        running = false;
        hasAudio = false;
        deviceName = "";
        sensitivity = 1.0F;
        wantedDevice = null;
    }

}
