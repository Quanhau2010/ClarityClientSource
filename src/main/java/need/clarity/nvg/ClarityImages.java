package need.clarity.nvg;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import org.lwjgl.BufferUtils;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NVGPaint;
import org.lwjgl.nanovg.NanoVG;
import org.lwjgl.system.MemoryStack;

public final class ClarityImages {
    private static final Map CACHE;

    private ClarityImages() {
    }

    public static int get(long arg0, String arg1) {
        int var8 = 0;
        Integer var13 = ((Integer) CACHE.get(arg1));
        if (var13 != null) {
            return var13;
        }
        var8 = 0;
        try {
            String var45 = "/assets/clarity/textures/fx/" + (arg1) + ".png";
            InputStream var17 = ClarityImages.class.getResourceAsStream(var45);
            try {
                if (var17 != null) {
                    byte[] var20 = var17.readAllBytes();
                    ByteBuffer var23 = BufferUtils.createByteBuffer(var20.length);
                    ByteBuffer var26 = var23.put(var20).flip();
                    var8 = NanoVG.nvgCreateImageMem(arg0, 1, var23);
                }
                if (var17 != null) {
                    var17.close();
                    if (var17 != null) {
                        try {
                            var17.close();
                        } catch (Throwable ignored) {
                        }
                        var36.addSuppressed(var39);
                    }
                    throw var36;
                }
            } catch (Throwable ignored) {
            }
        } catch (Throwable ignored) {
        }
        Map var32 = CACHE;
        Integer var50 = var8;
        Object var33 = var32.put(arg1, var50);
        return var8;
    }

    public static void clear() {
        CACHE.clear();
    }

    public static void draw(long arg0, int arg1, float arg2, float arg3, float arg4, float arg5, int arg6) {
        if (arg1 <= 0) {
            return;
        }
        float var30 = (((float) ((arg6 >>> 24) & 255)) / 255.0F);
        float var35 = (((float) ((arg6 >>> 16) & 255)) / 255.0F);
        float var40 = (((float) ((arg6 >>> 8) & 255)) / 255.0F);
        float var44 = (((float) (arg6 & 255)) / 255.0F);
        float var46 = (arg4 * 0.5F);
        MemoryStack var47 = MemoryStack.stackPush();
        try {
            NVGPaint var49 = NVGPaint.malloc(var47);
            NanoVG.nvgSave(arg0);
            NanoVG.nvgTranslate(arg0, arg2, arg3);
            NanoVG.nvgRotate(arg0, arg5);
            NVGPaint var58 = NanoVG.nvgImagePattern(arg0, var46, var46, arg4, arg4, 0.0F, arg1, 1.0F, var49);
            NVGColor var64 = var49.innerColor().r(var35).g(var40).b(var44).a(var30);
            NVGColor var70 = var49.outerColor().r(var35).g(var40).b(var44).a(var30);
            NanoVG.nvgBeginPath(arg0);
            NanoVG.nvgRect(arg0, var46, var46, arg4, arg4);
            NanoVG.nvgFillPaint(arg0, var49);
            NanoVG.nvgFill(arg0);
            NanoVG.nvgRestore(arg0);
        } catch (Throwable ignored) {
        }
        if (var47 != null) {
            var47.close();
            if (var47 != null) {
                try {
                    var47.close();
                } catch (Throwable ignored) {
                }
                var81.addSuppressed(var84);
            }
            throw var81;
        }
    }

    static {
        HashMap var0 = new HashMap();
        CACHE = var0;
    }

}
