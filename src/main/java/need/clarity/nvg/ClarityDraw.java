package need.clarity.nvg;

import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NVGPaint;
import org.lwjgl.nanovg.NanoVG;
import org.lwjgl.system.MemoryStack;

public final class ClarityDraw {
    public static final float[] BOUNDS;

    private ClarityDraw() {
    }

    public static void fillRRect(long arg0, float arg1, float arg2, float arg3, float arg4, float arg5, int arg6) {
        MemoryStack var8 = MemoryStack.stackPush();
        try {
            NVGColor var19 = NVGColor.malloc(var8);
            NanoVG.nvgBeginPath(arg0);
            NanoVG.nvgRoundedRect(arg0, arg1, arg2, arg3, arg4, arg5);
            NVGColor var37 = ClarityNvg.argb(arg6, var19);
            NanoVG.nvgFillColor(arg0, var37);
            NanoVG.nvgFill(arg0);
        } catch (Throwable ignored) {
        }
        if (var8 != null) {
            var8.close();
        }
        return;
        if (var8 != null) {
            try {
                var8.close();
            } catch (Throwable ignored) {
            }
            var28.addSuppressed(var32);
        }
        throw var28;
    }

    public static void fillRRect(long arg0, float arg1, float arg2, float arg3, float arg4, float arg5, NVGColor arg6) {
        NanoVG.nvgBeginPath(arg0);
        NanoVG.nvgRoundedRect(arg0, arg1, arg2, arg3, arg4, arg5);
        NanoVG.nvgFillColor(arg0, arg6);
        NanoVG.nvgFill(arg0);
    }

    public static void fillRRectVarying(long arg0, float arg1, float arg2, float arg3, float arg4, float arg5, float arg6, float arg7, float arg8, int arg9) {
        MemoryStack var11 = MemoryStack.stackPush();
        try {
            NVGColor var25 = NVGColor.malloc(var11);
            NanoVG.nvgBeginPath(arg0);
            NanoVG.nvgRoundedRectVarying(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8);
            NVGColor var43 = ClarityNvg.argb(arg9, var25);
            NanoVG.nvgFillColor(arg0, var43);
            NanoVG.nvgFill(arg0);
        } catch (Throwable ignored) {
        }
        if (var11 != null) {
            var11.close();
            if (var11 != null) {
                try {
                    var11.close();
                } catch (Throwable ignored) {
                }
                var36.addSuppressed(var40);
            }
            throw var36;
        }
    }

    public static void fillRRectVaryingLinear(long arg0, float arg1, float arg2, float arg3, float arg4, float arg5, float arg6, float arg7, float arg8, float arg9, float arg10, float arg11, float arg12, int arg13, int arg14) {
        MemoryStack var16 = MemoryStack.stackPush();
        try {
            NVGColor var31 = NVGColor.malloc(var16);
            NVGColor var33 = NVGColor.malloc(var16);
            NVGPaint var35 = NVGPaint.malloc(var16);
            NanoVG.nvgBeginPath(arg0);
            NanoVG.nvgRoundedRectVarying(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8);
            NVGColor var61 = ClarityNvg.argb(arg13, var31);
            NVGColor var64 = ClarityNvg.argb(arg14, var33);
            NVGPaint var42 = NanoVG.nvgLinearGradient(arg0, arg9, arg10, arg11, arg12, var61, var64, var35);
            NanoVG.nvgFillPaint(arg0, var35);
            NanoVG.nvgFill(arg0);
        } catch (Throwable ignored) {
        }
        if (var16 != null) {
            var16.close();
            if (var16 != null) {
                try {
                    var16.close();
                } catch (Throwable ignored) {
                }
                var47.addSuppressed(var50);
            }
            throw var47;
        }
    }

    public static void fillRRectLinear(long arg0, float arg1, float arg2, float arg3, float arg4, float arg5, float arg6, float arg7, float arg8, float arg9, int arg10, int arg11) {
        MemoryStack var13 = MemoryStack.stackPush();
        try {
            NVGColor var27 = NVGColor.malloc(var13);
            NVGColor var29 = NVGColor.malloc(var13);
            NVGPaint var31 = NVGPaint.malloc(var13);
            NanoVG.nvgBeginPath(arg0);
            NanoVG.nvgRoundedRect(arg0, arg1, arg2, arg3, arg4, arg5);
            NVGColor var57 = ClarityNvg.argb(arg10, var27);
            NVGColor var59 = ClarityNvg.argb(arg11, var29);
            NVGPaint var38 = NanoVG.nvgLinearGradient(arg0, arg6, arg7, arg8, arg9, var57, var59, var31);
            NanoVG.nvgFillPaint(arg0, var31);
            NanoVG.nvgFill(arg0);
        } catch (Throwable ignored) {
        }
        if (var13 != null) {
            var13.close();
            if (var13 != null) {
                try {
                    var13.close();
                } catch (Throwable ignored) {
                }
                var45.addSuppressed(var49);
            }
            throw var45;
        }
    }

    public static void strokeRRect(long arg0, float arg1, float arg2, float arg3, float arg4, float arg5, float arg6, int arg7) {
        MemoryStack var9 = MemoryStack.stackPush();
        try {
            NVGColor var22 = NVGColor.malloc(var9);
            float var24 = (arg6 * 0.5F);
            NanoVG.nvgBeginPath(arg0);
            float var44 = (arg1 + var24);
            float var49 = (arg2 + var24);
            float var52 = (arg3 - arg6);
            float var54 = (arg4 - arg6);
            float var57 = (arg5 - var24);
            float var56 = Math.max(0.0F, var57);
            NanoVG.nvgRoundedRect(arg0, var44, var49, var52, var54, var56);
            NVGColor var46 = ClarityNvg.argb(arg7, var22);
            NanoVG.nvgStrokeColor(arg0, var46);
            NanoVG.nvgStrokeWidth(arg0, arg6);
            NanoVG.nvgStroke(arg0);
        } catch (Throwable ignored) {
        }
        if (var9 != null) {
            var9.close();
        }
        return;
        if (var9 != null) {
            try {
                var9.close();
            } catch (Throwable ignored) {
            }
            var35.addSuppressed(var39);
        }
        throw var35;
    }

    public static void edgeHighlight(long arg0, float arg1, float arg2, float arg3, float arg4, boolean arg5, float arg6) {
        MemoryStack var8 = MemoryStack.stackPush();
        try {
            NVGColor var17 = NVGColor.malloc(var8);
            NanoVG.nvgBeginPath(arg0);
            if (arg5) {
                float var43 = (arg1 + 0.75F);
                float var56 = (arg2 + 1.0F);
                NanoVG.nvgMoveTo(arg0, var43, var56);
                float var45 = (arg1 + 0.75F);
                float var60 = ((arg2 + arg4) - 1.0F);
                NanoVG.nvgLineTo(arg0, var45, var60);
            } else {
                float var51 = ((arg1 + arg3) - 0.75F);
                float var65 = (arg2 + 1.0F);
                NanoVG.nvgMoveTo(arg0, var51, var65);
                float var54 = ((arg1 + arg3) - 0.75F);
                float var70 = ((arg2 + arg4) - 1.0F);
                NanoVG.nvgLineTo(arg0, var54, var70);
            }
            NVGColor var47 = ClarityNvg.white(arg6, var17);
            NanoVG.nvgStrokeColor(arg0, var47);
            NanoVG.nvgStrokeWidth(arg0, 1.0F);
            NanoVG.nvgStroke(arg0);
        } catch (Throwable ignored) {
        }
        if (var8 != null) {
            var8.close();
        }
        return;
        if (var8 != null) {
            try {
                var8.close();
            } catch (Throwable ignored) {
            }
            var33.addSuppressed(var37);
        }
        throw var33;
    }

    public static void textOutlined(long arg0, String arg1, float arg2, float arg3, int arg4, float arg5, float arg6, String arg7, int arg8, int arg9, float arg10) {
        int var24 = 0;
        int var25 = 0;
        if (Float.compare(arg10, 0.0F) <= 0) {
            float var29 = text(arg0, arg1, arg2, arg3, arg8, arg4, arg5, arg6, arg7);
            return;
        }
        MemoryStack var30 = MemoryStack.stackPush();
        try {
            NVGColor var32 = NVGColor.malloc(var30);
            NanoVG.nvgFontFace(arg0, arg1);
            NanoVG.nvgFontSize(arg0, arg2);
            NanoVG.nvgTextLetterSpacing(arg0, arg3);
            NanoVG.nvgTextAlign(arg0, arg4);
            NVGColor var71 = ClarityNvg.argb(arg9, var32);
            NanoVG.nvgFillColor(arg0, var71);
            var24 = -1;
            while (true) {
                if (var24 > 1) break;
                var25 = -1;
                while (true) {
                    if (var25 > 1) break;
                    if (var24 == 0) {
                        if (var25 == 0) {
                        } else {
                            float var78 = (((float) var24) * arg10);
                            float var73 = (arg5 + var78);
                            float var85 = (((float) var25) * arg10);
                            float var80 = (arg6 + var85);
                            float var51 = NanoVG.nvgText(arg0, var73, var80, arg7);
                        }
                        var25 = (var25 + 1);
                    }
                }
                var24 = (var24 + 1);
            }
            if (var30 != null) {
                try {
                    var30.close();
                } catch (Throwable ignored) {
                }
                var52.addSuppressed(var56);
            }
            throw var52;
            if (var30 != null) {
                var30.close();
            }
            float var62 = text(arg0, arg1, arg2, arg3, arg8, arg4, arg5, arg6, arg7);
            return;
        } catch (Throwable ignored) {
        }
    }

    public static void panel(long arg0, float arg1, float arg2, float arg3, float arg4, float arg5, int arg6, int arg7, int arg8, float arg9) {
        float var36 = (arg2 + arg4);
        fillRRectLinear(arg0, arg1, arg2, arg3, arg4, arg5, arg1, arg2, arg1, var36, arg6, arg7);
        if (Float.compare(arg9, 0.0F) > 0) {
            strokeRRect(arg0, arg1, arg2, arg3, arg4, arg5, arg9, arg8);
        }
    }

    public static void topHighlight(long arg0, float arg1, float arg2, float arg3, float arg4, float arg5, int arg6) {
        float var20 = (arg5 * 0.7F);
        float var17 = Math.max(2.0F, var20);
        float var22 = (arg1 + var17);
        float var24 = (arg2 + 1.0F);
        float var29 = (var17 * 2.0F);
        float var27 = (arg3 - var29);
        float var26 = Math.max(1.0F, var27);
        fillRRect(arg0, var22, var24, var26, 1.0F, 0.5F, arg6);
    }

    public static void bottomShadow(long arg0, float arg1, float arg2, float arg3, float arg4, float arg5, int arg6) {
        float var20 = (arg5 * 0.7F);
        float var17 = Math.max(2.0F, var20);
        float var22 = (arg1 + var17);
        float var25 = ((arg2 + arg4) - 2.0F);
        float var31 = (var17 * 2.0F);
        float var29 = (arg3 - var31);
        float var28 = Math.max(1.0F, var29);
        fillRRect(arg0, var22, var25, var28, 1.0F, 0.5F, arg6);
    }

    public static float text(long arg0, String arg1, float arg2, float arg3, NVGColor arg4, int arg5, float arg6, float arg7, String arg8) {
        NanoVG.nvgFontFace(arg0, arg1);
        NanoVG.nvgFontSize(arg0, arg2);
        NanoVG.nvgTextLetterSpacing(arg0, arg3);
        NanoVG.nvgTextAlign(arg0, arg5);
        NanoVG.nvgFillColor(arg0, arg4);
        return NanoVG.nvgText(arg0, arg6, arg7, arg8);
    }

    public static float text(long arg0, String arg1, float arg2, float arg3, int arg4, int arg5, float arg6, float arg7, String arg8) {
        MemoryStack var10 = MemoryStack.stackPush();
        try {
            NVGColor var19 = NVGColor.malloc(var10);
            NanoVG.nvgFontFace(arg0, arg1);
            NanoVG.nvgFontSize(arg0, arg2);
            NanoVG.nvgTextLetterSpacing(arg0, arg3);
            NanoVG.nvgTextAlign(arg0, arg5);
            NVGColor var46 = ClarityNvg.argb(arg4, var19);
            NanoVG.nvgFillColor(arg0, var46);
            float var32 = NanoVG.nvgText(arg0, arg6, arg7, arg8);
        } catch (Throwable ignored) {
        }
        if (var10 != null) {
            var10.close();
        }
        return var32;
        if (var10 != null) {
            try {
                var10.close();
            } catch (Throwable ignored) {
            }
            var33.addSuppressed(var37);
        }
        throw var33;
    }

    public static float textWidth(long arg0, String arg1, float arg2, float arg3, String arg4) {
        NanoVG.nvgFontFace(arg0, arg1);
        NanoVG.nvgFontSize(arg0, arg2);
        NanoVG.nvgTextLetterSpacing(arg0, arg3);
        NanoVG.nvgTextAlign(arg0, 9);
        float[] var11 = BOUNDS;
        return NanoVG.nvgTextBounds(arg0, 0.0F, 0.0F, arg4, var11);
    }

    public static void hudText(long arg0, float arg1, int arg2, int arg3, float arg4, float arg5, String arg6) {
        float var18 = text(arg0, "inter-sb", arg1, 0.0F, arg2, arg3, arg4, arg5, arg6);
    }

    static {
        BOUNDS = new float[4];
    }

}
