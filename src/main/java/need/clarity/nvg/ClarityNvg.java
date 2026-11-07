package need.clarity.nvg;

import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NanoVG;
import org.lwjgl.system.MemoryStack;

public final class ClarityNvg {
    private ClarityNvg() {
    }

    public static NVGColor argb(int arg0, NVGColor arg1) {
        int var12 = ((arg0 >>> 24) & 255);
        int var15 = ((arg0 >> 16) & 255);
        int var18 = ((arg0 >> 8) & 255);
        int var20 = (arg0 & 255);
        int var22 = ((byte) var15);
        int var31 = ((byte) var18);
        int var32 = ((byte) var20);
        int var33 = ((byte) var12);
        return NanoVG.nvgRGBA(var22, var31, var32, var33, arg1);
    }

    public static NVGColor rgb(int arg0, float arg1, NVGColor arg2) {
        int var12 = ((arg0 >> 16) & 255);
        int var15 = ((arg0 >> 8) & 255);
        int var17 = (arg0 & 255);
        int var19 = ((byte) var12);
        int var26 = ((byte) var15);
        int var27 = ((byte) var17);
        int var30 = ((byte) clamp255((arg1 * 255.0F)));
        return NanoVG.nvgRGBA(var19, var26, var27, var30, arg2);
    }

    public static NVGColor white(float arg0, NVGColor arg1) {
        int var10 = ((byte) clamp255((arg0 * 255.0F)));
        return NanoVG.nvgRGBA(-1, -1, -1, var10, arg1);
    }

    public static NVGColor black(float arg0, NVGColor arg1) {
        int var10 = ((byte) clamp255((arg0 * 255.0F)));
        return NanoVG.nvgRGBA(0, 0, 0, var10, arg1);
    }

    public static int withAlpha(int arg0, float arg1) {
        int var7 = (clamp255((arg1 * 255.0F)) << 24);
        int var11 = (arg0 & 16777215);
        return (var7 | var11);
    }

    public static NVGColor color(MemoryStack arg0) {
        return NVGColor.malloc(arg0);
    }

    private static int clamp255(float arg0) {
        int var7 = 0;
        int var4 = Math.round(arg0);
        if (var4 < 0) {
            var7 = 0;
        } else {
            if (var4 > 255) {
                var7 = 255;
            } else {
                var7 = var4;
            }
            return var7;
        }
    }

}
