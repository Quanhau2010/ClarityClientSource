package need.clarity.nvg;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.lwjgl.nanovg.NanoVG;
import org.lwjgl.system.MemoryUtil;

public final class ClarityFonts {
    public static final String INTER = "inter";
    public static final String INTER_MEDIUM = "inter-med";
    public static final String INTER_SEMIBOLD = "inter-sb";
    private static final List RETAINED;
    private static boolean loaded;

    private ClarityFonts() {
    }

    public static void loadAll(long arg0) {
        if (loaded) {
            return;
        }
        tryLoad(arg0, "inter", "Inter-Regular");
        tryLoad(arg0, "inter-med", "Inter-Medium");
        tryLoad(arg0, "inter-sb", "Inter-SemiBold");
        loaded = true;
    }

    public static void loadInto(long arg0) {
        tryLoad(arg0, "inter", "Inter-Regular");
        tryLoad(arg0, "inter-med", "Inter-Medium");
        tryLoad(arg0, "inter-sb", "Inter-SemiBold");
    }

    public static boolean isLoaded() {
        return loaded;
    }

    public static void free() {
        Iterator var3 = RETAINED.iterator();
        while (true) {
            if (!var3.hasNext()) break;
            MemoryUtil.memFree(((ByteBuffer) var3.next()));
        }
        RETAINED.clear();
        loaded = false;
    }

    private static void tryLoad(long arg0, String arg1, String arg2) {
        ByteBuffer var9 = null;
        var9 = readQuietly("/fonts/" + (arg2) + ".ttf");
        if (var9 == null) {
            var9 = readQuietly("/assets/clarity/fonts/" + (arg2) + ".ttf");
        }
        if (var9 == null) {
            return;
        }
        List var17 = RETAINED;
        ByteBuffer var5 = var9;
        boolean var18 = var17.add(var5);
        ByteBuffer var7 = var9;
        int var21 = NanoVG.nvgCreateFontMem(arg0, arg1, var7, false);
    }

    private static ByteBuffer readQuietly(String arg0) {
        try {
            InputStream var7 = ClarityFonts.class.getResourceAsStream(arg0);
            try {
                if (var7 == null) {
                }
            } catch (Throwable ignored) {
            }
            if (var7 != null) {
                var7.close();
            }
            return null;
            try {
                try {
                    byte[] var22 = var7.readAllBytes();
                    ByteBuffer var25 = MemoryUtil.memAlloc(var22.length);
                    ByteBuffer var28 = var25.put(var22).flip();
                } catch (IOException ignored) {
                }
            } catch (Throwable ignored) {
            }
            if (var7 != null) {
                var7.close();
            }
            return var25;
            if (var7 != null) {
                try {
                    var7.close();
                } catch (Throwable ignored) {
                }
                var15.addSuppressed(var18);
            }
            throw var15;
        } catch (IOException ignored) {
        }
    }

    static {
        ArrayList var0 = new ArrayList();
        RETAINED = var0;
        loaded = false;
    }

}
