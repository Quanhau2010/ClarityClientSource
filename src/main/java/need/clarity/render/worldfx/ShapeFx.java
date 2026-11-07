package need.clarity.render.worldfx;

import need.clarity.util.ClarityRenderUtils;
import net.minecraft.world.phys.Vec3;

public final class ShapeFx {
    public static final String[] TARGET;
    public static final String[] AURA;
    private static final float[] R;
    private static final float[] U;
    private static final float[] F;
    private static float alpha;

    private ShapeFx() {
    }

    public static int comboStyle(String arg0) {
        int var1 = targetStyle(arg0);
        if (var1 >= 5) {
        } else {
        }
        return -1;
    }

    public static int targetStyle(String arg0) {
        return indexOf(TARGET, arg0);
    }

    public static int auraStyle(String arg0) {
        return indexOf(AURA, arg0);
    }

    public static void target(int arg0, double arg1, double arg2, double arg3, float arg4, float arg5, float[] arg6, float[] arg7, float arg8, float arg9, float arg10, boolean arg11) {
        float var20 = 0.0F;
        float var21 = 0.0F;
        int var22 = 0;
        int var23 = 0;
        double var24 = 0.0;
        double var26 = 0.0;
        float var28 = 0.0F;
        float var29 = 0.0F;
        Vec3 var15 = ClarityRenderUtils.getCameraPos();
        WorldFx.axes(R, U, F);
        float var16 = ((float) (arg1 - var15.x));
        float var17 = ((float) (arg2 - var15.y));
        float var18 = ((float) (arg3 - var15.z));
        float var19 = Math.max(arg5, arg4);
        WorldFx.begin();
        switch (arg0) {
            case 0 -> {}
            case 1 -> {}
            case 2 -> {}
            case 3 -> {}
            case 4 -> {}
            case 5 -> {}
            case 6 -> {}
            case 7 -> {}
            default -> {}
        }
        var20 = (((arg4 * 0.7F) + 0.35F) * 1.25F);
        quad(var16, (var17 + 0.02F), var18, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, var20, var20, -1.25F, -1.25F, 1.25F, 1.25F, arg6);
        var20 = (((var19 * 0.62F) * 1.3F) * (1.0F + ((1.0F - arg10) * 0.8F)));
        quad(var16, (var17 + (arg5 * 0.5F)), var18, R[0], R[1], R[2], U[0], U[1], U[2], var20, var20, -1.3F, -1.3F, 1.3F, 1.3F, arg6);
        var20 = ((arg4 * 0.85F) + 0.1F);
        var21 = (arg5 * 1.2F);
        var22 = 24;
        var23 = 0;
        while (true) {
            if (var23 >= var22) break;
            var24 = ((6.283185307179586 * ((double) var23)) / ((double) var22));
            var26 = ((6.283185307179586 * ((double) (var23 + 1))) / ((double) var22));
            var28 = (var16 + (((float) Math.cos(var24)) * var20));
            var29 = (var18 + (((float) Math.sin(var24)) * var20));
            float var30 = (var16 + (((float) Math.cos(var26)) * var20));
            float var31 = (var18 + (((float) Math.sin(var26)) * var20));
            float var32 = (((float) var23) / ((float) var22));
            float var33 = (((float) (var23 + 1)) / ((float) var22));
            v(var28, var17, var29, var32, 0.0F, arg6);
            v(var30, var17, var31, var33, 0.0F, arg6);
            v(var30, (var17 + var21), var31, var33, 1.0F, arg6);
            v(var28, var17, var29, var32, 0.0F, arg6);
            v(var30, (var17 + var21), var31, var33, 1.0F, arg6);
            v(var28, (var17 + var21), var29, var32, 1.0F, arg6);
            var23++;
        }
        var20 = (var19 * 0.62F);
        quad(var16, (var17 + (arg5 * 0.5F)), var18, R[0], R[1], R[2], U[0], U[1], U[2], var20, var20, -1.0F, -1.0F, 1.0F, 1.0F, arg6);
        var20 = R[0];
        var21 = R[2];
        var22 = ((float) Math.sqrt(((double) ((var20 * var20) + (var21 * var21)))));
        if (var22 < 1.0E-4F) {
            var20 = 1.0F;
            var21 = 0.0F;
        } else {
            var20 = (var20 / var22);
            var21 = (var21 / var22);
        }
        var23 = F[0];
        var24 = F[2];
        float var25 = ((float) Math.sqrt(((double) ((var23 * var23) + (var24 * var24)))));
        if (var25 < 1.0E-4F) {
            var23 = 0.0F;
            var24 = 1.0F;
        } else {
            var23 = (var23 / var25);
            var24 = (var24 / var25);
        }
        var26 = 0.6F;
        float var27 = ((float) Math.cos(((double) var26)));
        var28 = ((float) Math.sin(((double) var26)));
        var29 = (((arg4 * 0.9F) + 0.5F) * 1.3F);
        quad(var16, (var17 + (arg5 * 0.55F)), var18, var20, 0.0F, var21, (var23 * var27), var28, (var24 * var27), var29, var29, -1.3F, -1.3F, 1.3F, 1.3F, arg6);
        var20 = ((var19 * 0.62F) * 1.3F);
        quad(var16, (var17 + (arg5 * 0.5F)), var18, R[0], R[1], R[2], U[0], U[1], U[2], var20, var20, -1.3F, -1.3F, 1.3F, 1.3F, arg6);
        var20 = 0.364F;
        quad(var16, ((var17 + arg5) + 0.55F), var18, R[0], R[1], R[2], U[0], U[1], U[2], var20, var20, -1.3F, -1.3F, 1.3F, 1.3F, arg6);
        WorldFx.flush("target", arg11, (p0) -> ShapeFx.lambda$target$0(arg8, arg0, arg7, arg9, arg10, p0));
    }

    public static void aura(int arg0, double arg1, double arg2, double arg3, float arg4, float arg5, float arg6, int arg7, float[] arg8, float[] arg9, float arg10, float arg11, boolean arg12) {
        float var21 = 0.0F;
        float var22 = 0.0F;
        float var23 = 0.0F;
        if (arg0 != 3) {
            if (arg0 == 4) {
            }
        }
        if (arg0 == 3) {
        } else {
        }
        GpuFx.auraParticles(arg1, arg2, arg3, arg4, (arg6 * arg5), Math.max(1, arg7), Math.round((140.0F * arg5)), arg5, arg8, arg9, arg10, arg11, arg12);
        return;
        Vec3 var16 = ClarityRenderUtils.getCameraPos();
        WorldFx.axes(R, U, F);
        float var17 = ((float) (arg1 - var16.x));
        float var18 = ((float) (arg2 - var16.y));
        float var19 = ((float) (arg3 - var16.z));
        new float[3][0] = arg8[0];
        new float[3][1] = arg8[1];
        new float[3][2] = arg8[2];
        float[] var20 = new float[3];
        alpha = arg10;
        WorldFx.begin();
        switch (arg0) {
            case 0 -> {}
            case 1 -> {}
            case 2 -> {}
            default -> {}
        }
        var21 = (1.05F * arg5);
        quad(var17, (var18 + (arg4 * 0.5F)), var19, R[0], R[1], R[2], U[0], U[1], U[2], var21, var21, -1.0F, -1.0F, 1.0F, 1.0F, var20);
        var21 = R[0];
        var22 = R[2];
        var23 = ((float) Math.sqrt(((double) ((var21 * var21) + (var22 * var22)))));
        if (var23 < 1.0E-4F) {
            var21 = 1.0F;
            var22 = 0.0F;
            var23 = 1.0F;
        }
        var21 = (var21 / var23);
        var22 = (var22 / var23);
        float var24 = (1.0F * arg5);
        float var25 = (1.4F * arg5);
        quad(var17, ((var18 + var25) - 0.05F), var19, var21, 0.0F, var22, 0.0F, 1.0F, 0.0F, var24, var25, -1.0F, 0.0F, 1.0F, 1.0F, var20);
        var21 = ((1.5F * arg5) * 1.2F);
        quad(var17, (var18 + (arg4 * 0.5F)), var19, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, var21, var21, -1.2F, -1.2F, 1.2F, 1.2F, var20);
        alpha = 1.0F;
        WorldFx.flush("aura", arg12, (p0) -> ShapeFx.lambda$aura$0(arg11, arg0, arg9, p0));
    }

    private static void quad(float arg0, float arg1, float arg2, float arg3, float arg4, float arg5, float arg6, float arg7, float arg8, float arg9, float arg10, float arg11, float arg12, float arg13, float arg14, float[] arg15) {
        float var16 = ((arg11 + arg13) * 0.5F);
        float var17 = ((arg12 + arg14) * 0.5F);
        float[][] var21 = new float[4][3];
        float[][] var22 = new float[][]{
            new float[]{arg11, arg12},
            new float[]{arg13, arg12},
            new float[]{arg13, arg14},
            new float[]{arg11, arg14}
        };
        for (int var23 = 0; var23 < 4; var23++) {
            float var24 = ((var22[var23][0] - var16) / ((arg13 - arg11) * 0.5F));
            float var25 = ((var22[var23][1] - var17) / ((arg14 - arg12) * 0.5F));
            var21[var23] = new float[]{
                ((arg0 + ((arg3 * arg9) * var24)) + ((arg6 * arg10) * var25)),
                ((arg1 + ((arg4 * arg9) * var24)) + ((arg7 * arg10) * var25)),
                ((arg2 + ((arg5 * arg9) * var24)) + ((arg8 * arg10) * var25))
            };
        }
        v(var21[0][0], var21[0][1], var21[0][2], arg11, arg12, arg15);
        v(var21[1][0], var21[1][1], var21[1][2], arg13, arg12, arg15);
        v(var21[2][0], var21[2][1], var21[2][2], arg13, arg14, arg15);
        v(var21[0][0], var21[0][1], var21[0][2], arg11, arg12, arg15);
        v(var21[2][0], var21[2][1], var21[2][2], arg13, arg14, arg15);
        v(var21[3][0], var21[3][1], var21[3][2], arg11, arg14, arg15);
    }

    private static void v(float arg0, float arg1, float arg2, float arg3, float arg4, float[] arg5) {
        WorldFx.vertex(arg0, arg1, arg2, arg3, arg4, arg5[0], arg5[1], arg5[2], alpha, 0.0F, 0.0F);
    }

    private static int indexOf(String[] arg0, String arg1) {
        if (arg1 != null) {
            int var2 = 0;
            while (true) {
                if (var2 >= arg0.length) break;
                if (arg0[var2].equalsIgnoreCase(arg1)) {
                    return var2;
                }
                var2++;
            }
            return -1;
        }
    }

    public static float[] rgb(int arg0) {
        new float[3][0] = (((float) ((arg0 >> 16) & 255)) / 255.0F);
        new float[3][1] = (((float) ((arg0 >> 8) & 255)) / 255.0F);
        new float[3][2] = (((float) (arg0 & 255)) / 255.0F);
        return new float[3];
    }

    static {
        new String[8][0] = "Plasma Ring";
        new String[8][1] = "Reticle";
        new String[8][2] = "Vortex";
        new String[8][3] = "Energy Shield";
        new String[8][4] = "Marker";
        new String[8][5] = "Triangle Slice";
        new String[8][6] = "Reaper";
        new String[8][7] = "Shatter Lock";
        TARGET = new String[8];
        new String[5][0] = "Plasma Shield";
        new String[5][1] = "Flame Aura";
        new String[5][2] = "Galaxy Ring";
        new String[5][3] = "Spirit Orbs";
        new String[5][4] = "Rising Sparks";
        AURA = new String[5];
        R = new float[3];
        U = new float[3];
        F = new float[3];
        alpha = 1.0F;
    }

}
