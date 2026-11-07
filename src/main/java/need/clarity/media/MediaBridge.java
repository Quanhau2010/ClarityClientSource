package need.clarity.media;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public final class MediaBridge {
    private static final MediaBridge.Snapshot EMPTY;
    private static volatile MediaBridge.Snapshot current;
    private static volatile boolean wanted;
    private static Thread worker;
    private static Process proc;
    private static Path cmdFile;
    private static int artCounter;
    private static long nextRestartAtMs;
    private static final String WIN_SCRIPT;
    private static final HttpClient HTTP;

    private MediaBridge() {
    }

    public static MediaBridge.Snapshot get() {
        return current;
    }

    public static synchronized void ensureStarted() {
        int var6 = 0;
        wanted = true;
        if (worker != null) {
            if (worker.isAlive()) {
                return;
            }
        }
        long var10 = System.currentTimeMillis();
        long var2 = nextRestartAtMs;
        if (Long.compare(var10, var2) < 0) {
            return;
        }
        nextRestartAtMs = (System.currentTimeMillis() + 5000L);
        String var18 = System.getProperty("os.name", "");
        Locale var30 = Locale.ROOT;
        String var19 = var18.toLowerCase(var30);
        if (var19.contains("win")) {
            var6 = () -> MediaBridge.runWindows();
        } else {
            if (var19.contains("mac")) {
                var6 = () -> MediaBridge.runMac();
            } else {
                var6 = () -> MediaBridge.runLinux();
            }
            Runnable var36 = var6;
            Thread var25 = new Thread(var36, "Clarity-MediaBridge");
            worker = var25;
            worker.setDaemon(true);
            worker.start();
            return;
        }
    }

    public static synchronized void stop() {
        wanted = false;
        Process var3 = proc;
        if (var3 != null) {
            Process var6 = var3.destroyForcibly();
        }
        proc = null;
        Thread var8 = worker;
        if (var8 != null) {
            var8.interrupt();
        }
        worker = null;
        current = EMPTY;
    }

    public static void next() {
        command("next");
    }

    public static void previous() {
        command("prev");
    }

    public static void playPause() {
        command("toggle");
    }

    private static void command(String arg0) {
        String var7 = System.getProperty("os.name", "");
        Locale var12 = Locale.ROOT;
        String var8 = var7.toLowerCase(var12);
        Runnable var14 = () -> MediaBridge.lambda$command$0(var8, arg0);
        Thread var9 = new Thread(var14, "Clarity-MediaCmd");
        var9.setDaemon(true);
        var9.start();
    }

    private static void apply(boolean arg0, boolean arg1, String arg2, String arg3, String arg4, String arg5, double arg6, double arg7, byte[] arg8, boolean arg9) {
        double var32 = 0.0;
        long var34 = 0L;
        byte[] var101 = null;
        int var102 = 0;
        int var103 = 0;
        byte[] var30 = null;
        int var31 = 0;
        MediaBridge.Snapshot var12 = current;
        if (!arg0) {
            if (var12.art != null) {
                int var44 = (artCounter + 1);
                artCounter = var44;
                var103 = (var44 + 1000);
            }
        } else {
            if (var12.active) {
                if (!arg9) {
                    if (eq(var12.title, arg2)) {
                        if (eq(var12.artist, arg3)) {
                            var102 = 1;
                        }
                    }
                }
            } else {
                var102 = 0;
            }
            int var136 = var102;
            if (var136 != 0) {
                var101 = var12.art;
            } else {
                var101 = null;
            }
            var30 = var101;
            var31 = var12.artVersion;
            if (arg8 != null) {
                var30 = arg8;
                int var98 = (artCounter + 1);
                artCounter = var98;
                var31 = (var98 + 1000);
            } else {
                if (var136 == 0) {
                    if (var12.art != null) {
                        int var62 = (artCounter + 1);
                        artCounter = var62;
                        var31 = (var62 + 1000);
                    }
                    var32 = arg6;
                    var34 = System.nanoTime();
                    if (var136 != 0) {
                        if (var12.playing == arg1) {
                            if (Double.compare(Math.abs((var12.durSec - arg7)), 0.5) < 0) {
                                if (Double.compare(Math.abs((var12.position() - arg6)), 1.25) < 0) {
                                    var32 = var12.posSec;
                                    var34 = var12.stampNanos;
                                }
                                double var127 = var32;
                                long var131 = var34;
                                byte[] var133 = var30;
                                int var134 = var31;
                                MediaBridge.Snapshot var95 = new MediaBridge.Snapshot(true, arg1, arg2, arg3, arg4, arg5, var127, arg7, var131, var133, var134, null);
                                current = var95;
                                return;
                                var103 = var12.artVersion;
                            }
                        }
                    }
                }
            }
        }
        int var29 = var103;
        MediaBridge.Snapshot var42 = new MediaBridge.Snapshot(false, false, "", "", "", "", 0.0, 0.0, 0L, null, var29, null);
        current = var42;
    }

    private static boolean eq(String arg0, String arg1) {
        int var6 = 0;
        if (arg0 == null) {
            if (arg1 == null) {
                var6 = 1;
            } else {
                var6 = 0;
            }
        } else {
            var6 = arg0.equals(arg1);
        }
        return var6;
    }

    private static void fail(String arg0) {
        int var17 = current.artVersion;
        MediaBridge.Snapshot var1 = new MediaBridge.Snapshot(false, false, "", "", "", "", 0.0, 0.0, 0L, null, var17, arg0);
        current = var1;
    }

    private static void runWindows() {
        String var22 = null;
        int var23 = 0;
        byte[] var26 = null;
        int var212 = 0;
        try {
            String var27 = System.getProperty("java.io.tmpdir");
            String[] var107 = new String[1];
            var107[0] = "clarity_media";
            Path var28 = Path.of(var27, var107);
            FileAttribute[] var109 = new FileAttribute[0];
            Path var30 = Files.createDirectories(var28, var109);
            Path var32 = var28.resolve("media.ps1");
            String var33 = WIN_SCRIPT;
            Charset var111 = StandardCharsets.UTF_8;
            byte[] var34 = var33.getBytes(var111);
            byte[] var36 = new byte[3];
            var36[0] = -17;
            var36[1] = -69;
            var36[2] = -65;
            int var38 = var36.length;
            int var113 = var34.length;
            byte[] var40 = new byte[(var38 + var113)];
            int var165 = var36.length;
            System.arraycopy(var36, 0, var40, 0, var165);
            int var159 = var36.length;
            int var167 = var34.length;
            System.arraycopy(var34, 0, var40, var159, var167);
            OpenOption[] var145 = new OpenOption[0];
            Path var44 = Files.write(var32, var40, var145);
            cmdFile = var28.resolve("cmd.txt");
            boolean var48 = Files.deleteIfExists(cmdFile);
            String[] var147 = new String[10];
            var147[0] = "powershell.exe";
            var147[1] = "-NoProfile";
            var147[2] = "-NonInteractive";
            var147[3] = "-WindowStyle";
            var147[4] = "Hidden";
            var147[5] = "-ExecutionPolicy";
            var147[6] = "Bypass";
            var147[7] = "-File";
            String var192 = var32.toString();
            var147[8] = var192;
            String var194 = cmdFile.toString();
            var147[9] = var194;
            ProcessBuilder var49 = new ProcessBuilder(var147);
            ProcessBuilder var51 = var49.redirectErrorStream(false);
            ProcessBuilder.Redirect var119 = ProcessBuilder.Redirect.DISCARD;
            ProcessBuilder var53 = var49.redirectError(var119);
            Process var55 = var49.start();
            proc = var55;
            InputStream var179 = var55.getInputStream();
            Charset var195 = StandardCharsets.UTF_8;
            InputStreamReader var148 = new InputStreamReader(var179, var195);
            BufferedReader var57 = new BufferedReader(var148);
            try {
                var23 = -1;
                while (true) {
                    if (!wanted) break;
                    String var61 = var57.readLine();
                    var22 = var61;
                    if (var61 == null) break;
                    if (!var22.isEmpty()) {
                        if (var22.charAt(0) == 65279) {
                            var22 = var22.substring(1);
                        }
                        if (var22.isEmpty()) continue;
                        if (var22.charAt(0) != 123) {
                        }
                        try {
                            JsonObject var81 = JsonParser.parseString(var22).getAsJsonObject();
                            if (var81.has("error")) {
                                fail(var81.get("error").getAsString());
                            }
                        } catch (Throwable ignored) {
                        }
                        try {
                            if (var81.has("active")) {
                                if (!var81.get("active").getAsBoolean()) {
                                }
                            }
                            apply(false, false, "", "", "", "", 0.0, 0.0, null, false);
                        } catch (Throwable ignored) {
                        }
                        try {
                            int var99 = var81.get("id").getAsInt();
                            var26 = null;
                            if (var81.has("thumb")) {
                                Base64.Decoder var103 = Base64.getDecoder();
                                String var135 = var81.get("thumb").getAsString();
                                var26 = var103.decode(var135);
                            }
                            boolean var138 = var81.get("playing").getAsBoolean();
                            String var153 = str(var81, "title");
                            String var163 = str(var81, "artist");
                            String var183 = str(var81, "album");
                            String var200 = friendlyApp(str(var81, "app"));
                            double var204 = var81.get("pos").getAsDouble();
                            double var208 = var81.get("dur").getAsDouble();
                            byte[] var210 = var26;
                            int var13 = var23;
                            if (var99 != var13) {
                                var212 = 1;
                            } else {
                                var212 = 0;
                            }
                            apply(true, var138, var153, var163, var183, var200, var204, var208, var210, var212);
                            var23 = var99;
                        } catch (Throwable ignored) {
                        }
                    }
                }
                var57.close();
                try {
                    var57.close();
                } catch (Throwable ignored) {
                }
                var72.addSuppressed(var77);
                throw var72;
            } catch (Throwable ignored) {
            }
        } catch (Throwable ignored) {
        }
        try {
            Process var89 = var55.destroyForcibly();
        } catch (Throwable ignored) {
        }
        fail("PowerShell unavailable");
    }

    private static String str(JsonObject arg0, String arg1) {
        String var10 = null;
        if (arg0.has(arg1)) {
            if (!arg0.get(arg1).isJsonNull()) {
                var10 = arg0.get(arg1).getAsString();
            } else {
                var10 = "";
            }
            return var10;
        }
    }

    private static String friendlyApp(String arg0) {
        String var34 = null;
        if (arg0 == null) {
            var34 = "";
        } else {
            Locale var2 = Locale.ROOT;
            var34 = arg0.toLowerCase(var2);
        }
        String var3 = var34;
        if (var3.contains("spotify")) {
            return "Spotify";
        }
        if (var3.contains("chrome")) {
            return "Chrome";
        }
        if (!var3.contains("msedge")) {
            if (var3.contains("edge")) {
            }
        }
        return "Edge";
        if (var3.contains("firefox")) {
            return "Firefox";
        }
        if (var3.contains("brave")) {
            return "Brave";
        }
        if (var3.contains("opera")) {
            return "Opera";
        }
        if (var3.contains("vlc")) {
            return "VLC";
        }
        if (!var3.contains("music")) {
            if (var3.contains("itunes")) {
            }
        }
        return "Music";
        return "";
    }

    private static void runLinux() {
        String var10 = null;
        String var11 = null;
        int var12 = 0;
        int var13 = 0;
        byte[] var24 = null;
        int var95 = 0;
        var10 = null;
        var11 = "";
        var12 = 0;
        var13 = 0;
        while (true) {
            if (wanted) {
                if (!Thread.currentThread().isInterrupted()) {
                    try {
                        try {
                            try {
                                String[] var116 = new String[4];
                                var116[0] = "playerctl";
                                var116[1] = "metadata";
                                var116[2] = "--format";
                                String var144 = "{{status}}" + ("\u001f") + "{{title}}" + ("\u001f") + "{{artist}}" + ("\u001f") + "{{album}}" + ("\u001f") + "{{mpris:length}}" + ("\u001f") + "{{position}}" + ("\u001f") + "{{mpris:artUrl}}" + ("\u001f") + "{{playerName}}";
                                var116[3] = var144;
                                ProcessBuilder var33 = new ProcessBuilder(var116);
                                ProcessBuilder var34 = var33.redirectErrorStream(false);
                                ProcessBuilder.Redirect var98 = ProcessBuilder.Redirect.DISCARD;
                                Process var36 = var34.redirectError(var98).start();
                                InputStream var136 = var36.getInputStream();
                                Charset var145 = StandardCharsets.UTF_8;
                                InputStreamReader var117 = new InputStreamReader(var136, var145);
                                BufferedReader var37 = new BufferedReader(var117);
                                try {
                                    String var39 = var37.readLine();
                                } catch (Throwable ignored) {
                                }
                                var37.close();
                                try {
                                    var37.close();
                                } catch (Throwable ignored) {
                                }
                                var40.addSuppressed(var52);
                                throw var40;
                            } catch (Throwable ignored) {
                            }
                            try {
                                try {
                                    try {
                                        TimeUnit var126 = TimeUnit.SECONDS;
                                        boolean var56 = var36.waitFor(3L, var126);
                                        if (var39 != null) {
                                            if (var36.exitValue() != 0) {
                                            }
                                        }
                                        apply(false, false, "", "", "", "", 0.0, 0.0, null, false);
                                        String[] var61 = var39.split("\u001f", -1);
                                        if (var61.length >= 8) {
                                            String var105 = var61[0];
                                            boolean var65 = "Playing".equalsIgnoreCase(var105);
                                            double var68 = parseMicros(var61[4]);
                                            double var72 = parseMicros(var61[5]);
                                            String var75 = var61[1];
                                            String var110 = var61[2];
                                            String var122 = var61[3];
                                            String var76 = (var75) + "|" + (var110) + "|" + (var122);
                                            String var111 = var11;
                                            if (!var76.equals(var111)) {
                                                var95 = 1;
                                            } else {
                                                var95 = 0;
                                            }
                                            int var23 = var95;
                                            if (var23 != 0) {
                                                var11 = var76;
                                                var12 = (var12 + 1);
                                                var10 = null;
                                            }
                                            var24 = null;
                                            String var84 = var61[6];
                                            if (var84 != null) {
                                                if (!var84.isBlank()) {
                                                    String var113 = var10;
                                                    if (!var84.equals(var113)) {
                                                        var24 = fetchArt(var84);
                                                        if (var24 != null) {
                                                            var10 = var84;
                                                        }
                                                        String var124 = var61[1];
                                                        String var130 = var61[2];
                                                        String var139 = var61[3];
                                                        String var149 = cap(var61[7]);
                                                        byte[] var160 = var24;
                                                        apply(true, var65, var124, var130, var139, var149, var72, var68, var160, var23);
                                                    }
                                                }
                                            }
                                        }
                                    } catch (Throwable ignored) {
                                    }
                                } catch (InterruptedException ignored) {
                                }
                            } catch (IOException ignored) {
                            }
                        } catch (InterruptedException ignored) {
                        }
                    } catch (IOException ignored) {
                    }
                }
            } else {
                return;
            }
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException ignored) {
            }
        }
    }

    private static double parseMicros(String arg0) {
        try {
            double var8 = (Double.parseDouble(arg0.trim()) / 1000000.0);
        } catch (Throwable ignored) {
        }
        return var8;
    }

    private static String cap(String arg0) {
        if (arg0 != null) {
            if (arg0.isEmpty()) {
            }
        }
        return "";
        Locale var2 = Locale.ROOT;
        String var8 = arg0.toLowerCase(var2);
        if (var8.startsWith("spotify")) {
            return "Spotify";
        }
        if (var8.startsWith("chrom")) {
            return "Chrome";
        }
        if (var8.startsWith("firefox")) {
            return "Firefox";
        }
        int var19 = Character.toUpperCase(var8.charAt(0));
        String var28 = var8.substring(1);
        return (var19) + (var28);
    }

    private static byte[] fetchArt(String arg0) {
        try {
            if (arg0.startsWith("file://")) {
                byte[] var10 = Files.readAllBytes(Path.of(URI.create(arg0)));
            }
        } catch (Throwable ignored) {
        }
        return var10;
        try {
            if (!arg0.startsWith("http://")) {
                if (arg0.startsWith("https://")) {
                }
            }
            HttpClient var17 = HTTP;
            HttpRequest.Builder var32 = HttpRequest.newBuilder(URI.create(arg0));
            Duration var38 = Duration.ofSeconds(6L);
            HttpRequest var35 = var32.timeout(var38).GET().build();
            HttpResponse.BodyHandler var39 = HttpResponse.BodyHandlers.ofByteArray();
            HttpResponse var18 = var17.send(var35, var39);
            if (var18.statusCode() == 200) {
                if (((byte[]) var18.body()).length > 64) {
                    byte[] var27 = ((byte[]) var18.body());
                }
            }
        } catch (Throwable ignored) {
        }
        return var27;
        return null;
    }

    private static void runMac() {
        String var10 = null;
        int var11 = 0;
        String var12 = null;
        int var13 = 0;
        int var155 = 0;
        byte[] var27 = null;
        int var123 = 0;
        var10 = "";
        var11 = 0;
        var12 = "";
        var13 = 0;
        while (true) {
            if (!wanted) break;
            if (Thread.currentThread().isInterrupted()) break;
            try {
                try {
                    try {
                        String[] var156 = new String[9];
                        var156[0] = "nowplaying-cli";
                        var156[1] = "get";
                        var156[2] = "title";
                        var156[3] = "artist";
                        var156[4] = "album";
                        var156[5] = "duration";
                        var156[6] = "elapsedTime";
                        var156[7] = "playbackRate";
                        var156[8] = "artworkData";
                        ProcessBuilder var36 = new ProcessBuilder(var156);
                        ProcessBuilder var37 = var36.redirectErrorStream(false);
                        ProcessBuilder.Redirect var125 = ProcessBuilder.Redirect.DISCARD;
                        Process var39 = var37.redirectError(var125).start();
                        ArrayList var40 = new ArrayList();
                        InputStream var178 = var39.getInputStream();
                        Charset var189 = StandardCharsets.UTF_8;
                        InputStreamReader var157 = new InputStreamReader(var178, var189);
                        BufferedReader var41 = new BufferedReader(var157);
                        while (true) {
                            try {
                                String var43 = var41.readLine();
                                if (var43 == null) break;
                                boolean var45 = var40.add(var43);
                            } catch (Throwable ignored) {
                            }
                            var41.close();
                            try {
                                var41.close();
                            } catch (Throwable ignored) {
                            }
                            var46.addSuppressed(var58);
                            throw var46;
                        }
                    } catch (Throwable ignored) {
                    }
                    try {
                        try {
                            try {
                                TimeUnit var164 = TimeUnit.SECONDS;
                                boolean var62 = var39.waitFor(3L, var164);
                                if (var40.size() >= 6) {
                                    Object var132 = var40.get(0);
                                    if ("(null)".equals(var132)) {
                                    }
                                }
                                apply(false, false, "", "", "", "", 0.0, 0.0, null, false);
                                String var70 = nn(((String) var40.get(0)));
                                String var74 = nn(((String) var40.get(1)));
                                String var78 = nn(((String) var40.get(2)));
                                double var82 = num(((String) var40.get(3)));
                                double var87 = num(((String) var40.get(4)));
                                double var92 = num(((String) var40.get(5)));
                                String var95 = (var70) + "|" + (var74) + "|" + (var78);
                                String var140 = var10;
                                if (!var95.equals(var140)) {
                                    var123 = 1;
                                } else {
                                    var123 = 0;
                                }
                                int var26 = var123;
                                if (var26 != 0) {
                                    var10 = var95;
                                    var11 = (var11 + 1);
                                    var12 = "";
                                }
                                var27 = null;
                                if (var40.size() >= 7) {
                                    Object var143 = var40.get(6);
                                    if (!"(null)".equals(var143)) {
                                        if (!((String) var40.get(6)).isBlank()) {
                                            String var114 = Integer.toString(((String) var40.get(6)).hashCode());
                                            String var146 = var12;
                                            if (!var114.equals(var146)) {
                                                try {
                                                    Base64.Decoder var117 = Base64.getDecoder();
                                                    String var150 = ((String) var40.get(6)).trim();
                                                    var27 = var117.decode(var150);
                                                    var12 = var114;
                                                } catch (Throwable ignored) {
                                                }
                                            }
                                            if (Double.compare(var92, 0.0) > 0) {
                                                var155 = 1;
                                            } else {
                                                var155 = 0;
                                            }
                                            byte[] var8 = var27;
                                            apply(true, var155, var70, var74, var78, "", var87, var82, var8, var26);
                                        }
                                    }
                                }
                            } catch (Throwable ignored) {
                            }
                            try {
                                Thread.sleep(1000L);
                            } catch (InterruptedException ignored) {
                            }
                            return;
                        } catch (InterruptedException ignored) {
                        }
                    } catch (IOException ignored) {
                    }
                } catch (InterruptedException ignored) {
                }
            } catch (IOException ignored) {
            }
        }
    }

    private static String nn(String arg0) {
        String var5 = null;
        if (arg0 != null) {
            if ("(null)".equals(arg0)) {
            }
        }
        var5 = "";
        var5 = arg0;
        return var5;
    }

    private static double num(String arg0) {
        try {
            double var4 = Double.parseDouble(arg0.trim());
        } catch (Throwable ignored) {
        }
        return var4;
    }

    static {
        MediaBridge.Snapshot var0 = new MediaBridge.Snapshot(false, false, "", "", "", "", 0.0, 0.0, 0L, null, 0, null);
        EMPTY = var0;
        current = EMPTY;
        CharSequence[] var24 = new CharSequence[69];
        var24[0] = "param([string]$CmdFile)";
        var24[1] = "$ErrorActionPreference = 'SilentlyContinue'";
        var24[2] = "[Console]::OutputEncoding = New-Object System.Text.UTF8Encoding($false)";
        var24[3] = "Add-Type -AssemblyName System.Runtime.WindowsRuntime";
        var24[4] = "$asTaskGeneric = ([System.WindowsRuntimeSystemExtensions].GetMethods() | Where-Object { $_.Name -eq 'AsTask' -and $_.GetParameters().Count -eq 1 -and $_.GetParameters()[0].ParameterType.Name -eq 'IAsyncOperation`1' })[0]";
        var24[5] = "function Await($op, $type) {";
        var24[6] = "  $t = $asTaskGeneric.MakeGenericMethod($type).Invoke($null, @($op))";
        var24[7] = "  $t.Wait(-1) | Out-Null";
        var24[8] = "  $t.Result";
        var24[9] = "}";
        var24[10] = "$mgrType   = [Windows.Media.Control.GlobalSystemMediaTransportControlsSessionManager, Windows.Media.Control, ContentType=WindowsRuntime]";
        var24[11] = "$propsType = [Windows.Media.Control.GlobalSystemMediaTransportControlsSessionMediaProperties, Windows.Media.Control, ContentType=WindowsRuntime]";
        var24[12] = "$streamType = [Windows.Storage.Streams.IRandomAccessStreamWithContentType, Windows.Storage.Streams, ContentType=WindowsRuntime]";
        var24[13] = "$mgr = Await ($mgrType::RequestAsync()) $mgrType";
        var24[14] = "if ($mgr -eq $null) { [Console]::Out.WriteLine('{\"error\":\"no media session manager\"}'); exit 1 }";
        var24[15] = "function Get-Thumb($props) {";
        var24[16] = "  try {";
        var24[17] = "    $ref = $props.Thumbnail";
        var24[18] = "    if ($ref -eq $null) { return $null }";
        var24[19] = "    $stream = Await ($ref.OpenReadAsync()) $streamType";
        var24[20] = "    if ($stream -eq $null) { return $null }";
        var24[21] = "    $net = [System.IO.WindowsRuntimeStreamExtensions]::AsStreamForRead($stream)";
        var24[22] = "    $ms = New-Object System.IO.MemoryStream";
        var24[23] = "    $net.CopyTo($ms)";
        var24[24] = "    $net.Dispose()";
        var24[25] = "    if ($ms.Length -lt 64) { return $null }";
        var24[26] = "    return [Convert]::ToBase64String($ms.ToArray())";
        var24[27] = "  } catch { return $null }";
        var24[28] = "}";
        var24[29] = "$lastKey = ''; $id = 0; $thumbSent = $false; $thumbTries = 0; $thumb = $null; $tick = 0";
        var24[30] = "while ($true) {";
        var24[31] = "  try {";
        var24[32] = "    if (Test-Path $CmdFile) {";
        var24[33] = "      $c = ([System.IO.File]::ReadAllText($CmdFile)).Trim()";
        var24[34] = "      Remove-Item $CmdFile -Force";
        var24[35] = "      $cs = $mgr.GetCurrentSession()";
        var24[36] = "      if ($cs -ne $null) {";
        var24[37] = "        if ($c -eq 'next')   { $null = $cs.TrySkipNextAsync() }";
        var24[38] = "        if ($c -eq 'prev')   { $null = $cs.TrySkipPreviousAsync() }";
        var24[39] = "        if ($c -eq 'toggle') { $null = $cs.TryTogglePlayPauseAsync() }";
        var24[40] = "      }";
        var24[41] = "    }";
        var24[42] = "    if ($tick % 2 -eq 0) {";
        var24[43] = "      $s = $mgr.GetCurrentSession()";
        var24[44] = "      if ($s -eq $null) {";
        var24[45] = "        [Console]::Out.WriteLine('{\"active\":false}')";
        var24[46] = "      } else {";
        var24[47] = "        $props = Await ($s.TryGetMediaPropertiesAsync()) $propsType";
        var24[48] = "        $tl = $s.GetTimelineProperties()";
        var24[49] = "        $status = $s.GetPlaybackInfo().PlaybackStatus.ToString()";
        var24[50] = "        $playing = ($status -eq 'Playing')";
        var24[51] = "        $start = $tl.StartTime.TotalSeconds; $end = $tl.EndTime.TotalSeconds";
        var24[52] = "        $dur = $end - $start; if ($dur -lt 0) { $dur = 0 }";
        var24[53] = "        $pos = $tl.Position.TotalSeconds - $start";
        var24[54] = "        if ($playing -and $tl.LastUpdatedTime.Year -gt 2000) { $pos += ([DateTimeOffset]::UtcNow - $tl.LastUpdatedTime).TotalSeconds }";
        var24[55] = "        if ($pos -lt 0) { $pos = 0 }; if ($dur -gt 0 -and $pos -gt $dur) { $pos = $dur }";
        var24[56] = "        $key = '' + $props.Title + '|' + $props.Artist + '|' + $props.AlbumTitle";
        var24[57] = "        if ($key -ne $lastKey) { $lastKey = $key; $id++; $thumb = $null; $thumbSent = $false; $thumbTries = 0 }";
        var24[58] = "        if (-not $thumbSent -and $thumb -eq $null -and $thumbTries -lt 8) { $thumb = Get-Thumb $props; $thumbTries++ }";
        var24[59] = "        $o = [ordered]@{ active = $true; id = $id; playing = $playing; title = [string]$props.Title; artist = [string]$props.Artist; album = [string]$props.AlbumTitle; app = [string]$s.SourceAppUserModelId; pos = [math]::Round($pos, 2); dur = [math]::Round($dur, 2) }";
        var24[60] = "        if (-not $thumbSent -and $thumb -ne $null) { $o['thumb'] = $thumb; $thumbSent = $true }";
        var24[61] = "        [Console]::Out.WriteLine(($o | ConvertTo-Json -Compress))";
        var24[62] = "      }";
        var24[63] = "      [Console]::Out.Flush()";
        var24[64] = "    }";
        var24[65] = "  } catch { }";
        var24[66] = "  $tick++";
        var24[67] = "  Start-Sleep -Milliseconds 250";
        var24[68] = "}";
        WIN_SCRIPT = String.join("\n", var24);
        HttpClient.Builder var19 = HttpClient.newBuilder();
        Duration var27 = Duration.ofSeconds(4L);
        HTTP = var19.connectTimeout(var27).build();
        Runtime var22 = Runtime.getRuntime();
        Runnable var98 = () -> MediaBridge.lambda$static$0();
        Thread var28 = new Thread(var98, "Clarity-MediaBridge-Shutdown");
        var22.addShutdownHook(var28);
    }

    public final class Snapshot {
        public final boolean active;
        public final boolean playing;
        public final String title;
        public final String artist;
        public final String album;
        public final String app;
        public final double posSec;
        public final double durSec;
        public final long stampNanos;
        public final byte[] art;
        public final int artVersion;
        public final String error;

        Snapshot(boolean arg0, boolean arg1, String arg2, String arg3, String arg4, String arg5, double arg6, double arg7, long arg8, byte[] arg9, int arg10, String arg11) {
            this.active = arg0;
            this.playing = arg1;
            this.title = arg2;
            this.artist = arg3;
            this.album = arg4;
            this.app = arg5;
            this.posSec = arg6;
            this.durSec = arg7;
            this.stampNanos = arg8;
            this.art = arg9;
            this.artVersion = arg10;
            this.error = arg11;
        }

        public double position() {
            double var5 = 0.0;
            var5 = this.posSec;
            if (this.playing) {
                double var11 = var5;
                long var2 = System.nanoTime();
                long var40 = this.stampNanos;
                double var31 = (((double) (var2 - var40)) / 1.0E9);
                var5 = (var11 + var31);
            }
            if (Double.compare(this.durSec, 0.0) > 0) {
                double var19 = var5;
                double var36 = this.durSec;
                var5 = Math.min(var19, var36);
            }
            double var38 = var5;
            return Math.max(0.0, var38);
        }

        public double remaining() {
            double var10 = 0.0;
            if (Double.compare(this.durSec, 0.0) > 0) {
                double var13 = this.durSec;
                double var17 = this.position();
                double var15 = (var13 - var17);
                var10 = Math.max(0.0, var15);
            } else {
                var10 = -1.0;
            }
            return var10;
        }

    }

}
