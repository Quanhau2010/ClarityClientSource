package need.clarity.audio;

import com.sun.jna.Native;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;

public final class PulseCapture {
    public static final String MONITOR = "@DEFAULT_MONITOR@";
    private static volatile Boolean available;
    private static PulseSimpleLib lib;
    private final Pointer handle;
    private final IntByReference err;

    private PulseCapture(Pointer arg0) {
        this.err = new IntByReference();
        this.handle = arg0;
    }

    public static synchronized boolean available() {
        if (available != null) {
            return available;
        }
        String var0 = System.getProperty("os.name", "").toLowerCase();
        if (!var0.contains("linux")) {
            if (!var0.contains("bsd")) {
                available = false;
                return false;
            }
            new String[2][0] = "pulse-simple";
            new String[2][1] = "libpulse-simple.so.0";
            String[] var1 = new String[2];
            int var2 = var1.length;
            int var3 = 0;
            while (true) {
                if (var3 >= var2) break;
                String var4 = var1[var3];
                try {
                    lib = ((PulseSimpleLib) Native.load(var4, PulseSimpleLib.class));
                    available = true;
                } catch (Throwable ignored) {
                }
                return true;
            }
            available = false;
            return false;
        }
    }

    public static PulseCapture open(String arg0, int arg1, int arg2, int arg3) {
        if (!available()) {
            return null;
        }
        PaSampleSpec var4 = new PaSampleSpec();
        var4.format = 3;
        var4.rate = arg1;
        var4.channels = ((byte) arg2);
        PaBufferAttr var5 = new PaBufferAttr();
        var5.fragsize = arg3;
        IntByReference var6 = new IntByReference();
        Pointer var7 = lib.pa_simple_new(null, "Minecraft (Clarity Music Beats)", 2, arg0, "Music Beats", var4, null, var5, var6);
        if (var7 == null) {
        } else {
        }
        return new PulseCapture(var7);
    }

    public boolean read(byte[] arg0) {
        return (lib.pa_simple_read(this.handle, arg0, new NativeLong(((long) arg0.length)), this.err) >= 0);
    }

    public void close() {
        try {
            lib.pa_simple_free(this.handle);
        } catch (Throwable ignored) {
        }
    }

    static {
        available = null;
    }

}
