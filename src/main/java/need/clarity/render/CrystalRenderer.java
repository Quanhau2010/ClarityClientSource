package need.clarity.render;

import java.awt.Color;
import java.util.Iterator;
import need.clarity.modules.visual.CrystalPopEffectEntry;
import need.clarity.modules.visual.CrystalTrackState;
import need.clarity.modules.visual.CrystalView;
import need.clarity.render.worldfx.WorldFx;
import need.clarity.util.ClarityRenderUtils;
import need.clarity.util.ThemeColors;
import net.minecraft.world.phys.Vec3;

public final class CrystalRenderer {
    public static final String CRYSTAL = "crystal";
    public static final String POP = "crystalpop";
    private static final float[] R;
    private static final float[] U;
    private static final float[] F;
    private static final long START;

    private CrystalRenderer() {
    }

    public static void draw(CrystalView arg0, long arg1) {
        String var8 = null;
        int var9 = 0;
        Vec3 var3 = ClarityRenderUtils.getCameraPos();
        WorldFx.axes(R, U, F);
        float var4 = ((float) ((((double) (arg1 - START)) / 1.0E9) % 3600.0));
        float[] var5 = new float[3];
        float[] var6 = new float[3];
        colours(arg0, var4, var5, var6);
        var8 = arg0.getStyle();
        var9 = -1;
        switch (var8.hashCode()) {
            case -1023954175 -> {}
            case -702797370 -> {}
            case 949337771 -> {}
            default -> {}
        }
        if (var8.equals("Plasma Orb")) {
            var9 = 0;
            if (var8.equals("Neon Cage")) {
                var9 = 1;
                if (var8.equals("Void Core")) {
                    var9 = 2;
                }
                switch (var9) {
                    case 0 -> {}
                    case 1 -> {}
                    case 2 -> {}
                    default -> {}
                }
                int var7 = 0;
                var8 = arg0.getSize();
                var9 = Math.max(0.1F, arg0.getPopDuration());
                if (arg0.isEligible()) {
                    drawOverlay(arg0, arg1, var3, var4, var5, var6, var9);
                } else {
                    drawShapes(arg0, arg1, var3, var4, var5, var6, var8, var9, var7);
                }
                drawPops(arg0, arg1, var3, var5, var6, var8, var9);
                return;
            }
        }
    }

    private static void drawOverlay(CrystalView arg0, long arg1, Vec3 arg2, float arg3, float[] arg4, float[] arg5, float arg6) {
        String var10 = null;
        int var11 = 0;
        CrystalTrackState var12 = null;
        float var13 = 0.0F;
        float var14 = 0.0F;
        int var8 = CrystalModelFx.textureId();
        if (var8 == 0) {
            return;
        }
        var10 = arg0.getStyle();
        var11 = -1;
        switch (var10.hashCode()) {
            case -440373695 -> {}
            case 68891850 -> {}
            case 2125565744 -> {}
            default -> {}
        }
        if (var10.equals("Hologram")) {
            var11 = 0;
            if (var10.equals("Galaxy")) {
                var11 = 1;
                if (var10.equals("Glint")) {
                    var11 = 2;
                }
                switch (var11) {
                    case 0 -> {}
                    case 1 -> {}
                    case 2 -> {}
                    default -> {}
                }
                int var9 = 0;
                var10 = Math.max(0.2F, arg0.getGlow());
                WorldFx.begin();
                var11 = arg0.entriesMap.values().iterator();
                while (true) {
                    if (!var11.hasNext()) break;
                    var12 = ((CrystalTrackState) var11.next());
                    if (var12.enabledState) {
                    }
                    var13 = (((float) (arg1 - var12.timestampMs)) / 1.0E9F);
                    var14 = (clamp01((var13 / 0.25F)) * var10);
                    CrystalModelFx.emit(((float) (var12.doubleValue3 - arg2.x)), ((float) (var12.doubleValue4 - arg2.y)), ((float) (var12.doubleValue5 - arg2.z)), var12.floatValue1, var14, 1.012F, var12.floatValue2);
                }
                var11 = arg0.entriesList.iterator();
                while (true) {
                    if (!var11.hasNext()) break;
                    var12 = ((CrystalPopEffectEntry) var11.next());
                    var13 = clamp01((((((float) (arg1 - var12.timestampMs)) / 1.0E9F) / arg6) / 0.45F));
                    if (var13 >= 1.0F) {
                    }
                    var14 = (1.0F - ((1.0F - var13) * (1.0F - var13)));
                    CrystalModelFx.emit(((float) (var12.doubleValue3 - arg2.x)), ((float) (var12.doubleValue4 - arg2.y)), ((float) (var12.doubleValue5 - arg2.z)), var12.floatValue2, (((1.0F - var13) * (1.0F - var13)) * var10), (1.012F + (0.45F * var14)), var12.floatValue1);
                }
                var11 = var9;
                WorldFx.flushTextured("crystalskin", arg0.isThroughWalls(), var8, (p0) -> CrystalRenderer.lambda$drawOverlay$0(arg3, var11, arg4, arg5, arg2, p0));
                return;
            }
        }
    }

    private static void drawShapes(CrystalView arg0, long arg1, Vec3 arg2, float arg3, float[] arg4, float[] arg5, float arg6, float arg7, int arg8) {
        Iterator var10 = null;
        CrystalTrackState var11 = null;
        float var12 = 0.0F;
        float var13 = 0.0F;
        WorldFx.begin();
        var10 = arg0.entriesMap.values().iterator();
        while (true) {
            if (!var10.hasNext()) break;
            var11 = ((CrystalTrackState) var10.next());
            if (var11.enabledState) {
            }
            var12 = (((float) (arg1 - var11.timestampMs)) / 1.0E9F);
            var13 = outBack((var12 / 0.35F));
            float var14 = clamp01((var12 / 0.18F));
            billboard(((float) (var11.doubleValue - arg2.x)), ((float) (var11.doubleValue1 - arg2.y)), ((float) (var11.doubleValue2 - arg2.z)), ((0.95F * arg6) * Math.max(var13, 1.0F)), var11.floatValue, 0.0F, (arg6 * var13), var14, var11.floatValue2);
        }
        var10 = arg0.entriesList.iterator();
        while (true) {
            if (!var10.hasNext()) break;
            var11 = ((CrystalPopEffectEntry) var10.next());
            var12 = clamp01((((((float) (arg1 - var11.timestampMs)) / 1.0E9F) / arg7) / 0.4F));
            if (var12 >= 1.0F) {
            }
            var13 = (arg6 * (1.0F + (0.22F * var12)));
            billboard(((float) (var11.doubleValue - arg2.x)), ((float) (var11.doubleValue1 - arg2.y)), ((float) (var11.doubleValue2 - arg2.z)), (0.95F * var13), (var11.floatValue + (var12 * 2.0F)), var12, var13, (1.0F - (var12 * var12)), var11.floatValue1);
        }
        var10 = arg8;
        var11 = arg0.getGlow();
        WorldFx.flush("crystal", arg0.isThroughWalls(), (p0) -> CrystalRenderer.lambda$drawShapes$0(arg3, var10, arg4, arg5, var11, p0));
    }

    private static void drawPops(CrystalView arg0, long arg1, Vec3 arg2, float[] arg3, float[] arg4, float arg5, float arg6) {
        if (arg0.entriesList.isEmpty()) {
            return;
        }
        WorldFx.begin();
        float var8 = arg0.getPopSize();
        boolean var9 = arg0.isGroundRing();
        Iterator var10 = arg0.entriesList.iterator();
        while (true) {
            if (!var10.hasNext()) break;
            CrystalPopEffectEntry var11 = ((CrystalPopEffectEntry) var10.next());
            float var12 = clamp01(((((float) (arg1 - var11.timestampMs)) / 1.0E9F) / arg6));
            float var13 = ((float) (var11.doubleValue - arg2.x));
            float var14 = ((float) (var11.doubleValue1 - arg2.y));
            float var15 = ((float) (var11.doubleValue2 - arg2.z));
            quad(var13, var14, var15, R, U, ((3.2F * var8) * arg5), var12, var11.floatValue1);
            if (var9) {
                float var16 = (((float) (var11.doubleValue4 - arg2.y)) + 0.03F);
                new float[3][0] = 1.0F;
                new float[3][1] = 0.0F;
                new float[3][2] = 0.0F;
                new float[3][0] = 0.0F;
                new float[3][1] = 0.0F;
                new float[3][2] = 1.0F;
                quad(((float) (var11.doubleValue3 - arg2.x)), var16, ((float) (var11.doubleValue5 - arg2.z)), new float[3], new float[3], ((3.5F * var8) * arg5), var12, (1.0F + var11.floatValue1));
            }
        }
        WorldFx.flush("crystalpop", arg0.isThroughWalls(), (p0) -> CrystalRenderer.lambda$drawPops$0(arg3, arg4, p0));
    }

    private static void billboard(float arg0, float arg1, float arg2, float arg3, float arg4, float arg5, float arg6, float arg7, float arg8) {
        quadRaw(arg0, arg1, arg2, R, U, arg3, arg4, arg5, arg6, arg7, arg3, arg8);
    }

    private static void quad(float arg0, float arg1, float arg2, float[] arg3, float[] arg4, float arg5, float arg6, float arg7) {
        quadRaw(arg0, arg1, arg2, arg3, arg4, arg5, 1.0F, 1.0F, 1.0F, 1.0F, arg6, arg7);
    }

    private static void quadRaw(float arg0, float arg1, float arg2, float[] arg3, float[] arg4, float arg5, float arg6, float arg7, float arg8, float arg9, float arg10, float arg11) {
        corner(arg0, arg1, arg2, arg3, arg4, arg5, -1.0F, -1.0F, arg6, arg7, arg8, arg9, arg10, arg11);
        corner(arg0, arg1, arg2, arg3, arg4, arg5, 1.0F, -1.0F, arg6, arg7, arg8, arg9, arg10, arg11);
        corner(arg0, arg1, arg2, arg3, arg4, arg5, 1.0F, 1.0F, arg6, arg7, arg8, arg9, arg10, arg11);
        corner(arg0, arg1, arg2, arg3, arg4, arg5, -1.0F, -1.0F, arg6, arg7, arg8, arg9, arg10, arg11);
        corner(arg0, arg1, arg2, arg3, arg4, arg5, 1.0F, 1.0F, arg6, arg7, arg8, arg9, arg10, arg11);
        corner(arg0, arg1, arg2, arg3, arg4, arg5, -1.0F, 1.0F, arg6, arg7, arg8, arg9, arg10, arg11);
    }

    private static void corner(float arg0, float arg1, float arg2, float[] arg3, float[] arg4, float arg5, float arg6, float arg7, float arg8, float arg9, float arg10, float arg11, float arg12, float arg13) {
        WorldFx.vertex((arg0 + (((arg3[0] * arg6) + (arg4[0] * arg7)) * arg5)), (arg1 + (((arg3[1] * arg6) + (arg4[1] * arg7)) * arg5)), (arg2 + (((arg3[2] * arg6) + (arg4[2] * arg7)) * arg5)), arg6, arg7, arg8, arg9, arg10, arg11, arg12, arg13);
    }

    private static void colours(CrystalView arg0, float arg1, float[] arg2, float[] arg3) {
        int var4 = 0;
        int var5 = 0;
        int var7 = 0;
        String var6 = arg0.getColorMode();
        var7 = -1;
        switch (var6.hashCode()) {
            case 2017705622 -> {}
            case 2029746065 -> {}
            default -> {}
        }
        if (var6.equals("Custom")) {
            var7 = 0;
            if (var6.equals("Chroma")) {
                var7 = 1;
            }
            switch (var7) {
                case 0 -> {}
                case 1 -> {}
                default -> {}
            }
            var4 = ThemeColors.parseHexRgb(arg0.getColor(), 8037111);
            var5 = ThemeColors.parseHexRgb(arg0.getSecondColour(), 4020641);
            var4 = Color.HSBtoRGB(((arg1 * 0.08F) % 1.0F), 0.6F, 1.0F);
            var5 = Color.HSBtoRGB((((arg1 * 0.08F) + 0.33F) % 1.0F), 0.7F, 0.75F);
            var4 = ThemeColors.getAccentRgb();
            var5 = ThemeColors.getAccent2Rgb();
            arg2[0] = (((float) ((var4 >> 16) & 255)) / 255.0F);
            arg2[1] = (((float) ((var4 >> 8) & 255)) / 255.0F);
            arg2[2] = (((float) (var4 & 255)) / 255.0F);
            arg3[0] = (((float) ((var5 >> 16) & 255)) / 255.0F);
            arg3[1] = (((float) ((var5 >> 8) & 255)) / 255.0F);
            arg3[2] = (((float) (var5 & 255)) / 255.0F);
            return;
        }
    }

    private static float clamp01(float arg0) {
        if (arg0 < 0.0F) {
        } else {
            if (arg0 > 1.0F) {
            } else {
            }
            return arg0;
        }
    }

    private static float outBack(float arg0) {
        arg0 = (clamp01(arg0) - 1.0F);
        return ((1.0F + ((((1.7F + 1.0F) * arg0) * arg0) * arg0)) + ((1.7F * arg0) * arg0));
    }

    static {
        R = new float[3];
        U = new float[3];
        F = new float[3];
        START = System.nanoTime();
    }

}
