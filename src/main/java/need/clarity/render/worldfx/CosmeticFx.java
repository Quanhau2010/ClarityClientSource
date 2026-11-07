package need.clarity.render.worldfx;

import need.clarity.modules.visual.cosmetics.AnchorTransform;
import need.clarity.modules.visual.wings.WingRenderContext;
import need.clarity.util.ClarityRenderUtils;
import net.minecraft.world.phys.Vec3;

public final class CosmeticFx {
    public static final String[] WING_STYLES;
    public static final String[] HAT_STYLES;
    private static final double WING_U0 = -0.1;
    private static final double WING_U1 = 1.25;
    private static final double WING_V0 = -0.45;
    private static final double WING_V1 = 1.28;
    private static final int NU = 12;
    private static final int NV = 12;
    private static final double HAT_PAD = 1.35;
    private static final float[] gx;
    private static final float[] gy;
    private static final float[] gz;
    private static final WingRenderContext MAPPER;

    private CosmeticFx() {
    }

    public static int wingStyle(String arg0) {
        return indexOf(WING_STYLES, arg0);
    }

    public static int hatStyle(String arg0) {
        return indexOf(HAT_STYLES, arg0);
    }

    public static void wings(int arg0, double[] arg1, double arg2, double arg3, double arg4, double arg5, double arg6, double arg7, double arg8, float[] arg9, float[] arg10, float arg11, boolean arg12, float arg13) {
        double var33 = 0.0;
        double var35 = 0.0;
        int var27 = 0;
        int var28 = 0;
        double var29 = 0.0;
        double var31 = 0.0;
        Vec3 var21 = ClarityRenderUtils.getCameraPos();
        WorldFx.begin();
        int var22 = 0;
        while (true) {
            if (var22 >= 2) break;
            if (var22 == 0) {
            } else {
            }
            double var23 = -(arg2);
            if (var22 == 0) {
            } else {
            }
            double var25 = -(arg3);
            MAPPER.applyStep(arg1, var23, var25, arg4, arg5, 1.0, arg7, arg8, var22);
            var27 = 0;
            while (true) {
                if (var27 > 12) break;
                var28 = 0;
                while (true) {
                    if (var28 > 12) break;
                    var29 = (-0.1 + ((1.35 * ((double) var28)) / 12.0));
                    var31 = (-0.45 + ((1.73 * ((double) var27)) / 12.0));
                    var33 = (var29 * arg6);
                    var35 = ((var31 - 0.62) * arg6);
                    double var37 = (arg6 * (((0.22 * var29) * var29) + ((0.04 * var29) * (var31 - 0.62))));
                    new double[3][0] = var33;
                    new double[3][1] = var35;
                    new double[3][2] = var37;
                    double[] var39 = MAPPER.computeVector(new double[3]);
                    int var40 = ((var27 * 13) + var28);
                    gx[var40] = ((float) (var39[0] - var21.x));
                    gy[var40] = ((float) (var39[1] - var21.y));
                    gz[var40] = ((float) (var39[2] - var21.z));
                    var28++;
                }
                var27++;
            }
            var27 = 0;
            while (true) {
                if (var27 >= 12) break;
                var28 = 0;
                while (true) {
                    if (var28 >= 12) break;
                    var29 = ((var27 * 13) + var28);
                    int var30 = (var29 + 1);
                    var31 = ((var29 + 12) + 1);
                    int var32 = (var31 + 1);
                    var33 = ((float) (-0.1 + ((1.35 * ((double) var28)) / 12.0)));
                    float var34 = ((float) (-0.1 + ((1.35 * ((double) (var28 + 1))) / 12.0)));
                    var35 = ((float) (-0.45 + ((1.73 * ((double) var27)) / 12.0)));
                    float var36 = ((float) (-0.45 + ((1.73 * ((double) (var27 + 1))) / 12.0)));
                    v(var29, var33, var35, arg9, arg11, var22);
                    v(var30, var34, var35, arg9, arg11, var22);
                    v(var32, var34, var36, arg9, arg11, var22);
                    v(var29, var33, var35, arg9, arg11, var22);
                    v(var32, var34, var36, arg9, arg11, var22);
                    v(var31, var33, var36, arg9, arg11, var22);
                    var28++;
                }
                var27++;
            }
            var22++;
        }
        flush("wings", arg12, arg0, arg10, arg13, 0.0F);
    }

    private static void v(int arg0, float arg1, float arg2, float[] arg3, float arg4, int arg5) {
        WorldFx.vertex(gx[arg0], gy[arg0], gz[arg0], arg1, arg2, arg3[0], arg3[1], arg3[2], arg4, ((float) arg5), 0.37F);
    }

    public static void hat(int arg0, AnchorTransform arg1, double arg2, float[] arg3, float[] arg4, float arg5, boolean arg6, float arg7, float arg8, boolean arg9) {
        double[] var17 = null;
        double var18 = 0.0;
        int var20 = 0;
        double var21 = 0.0;
        double var22 = 0.0;
        double var23 = 0.0;
        double[] var26 = null;
        double[] var27 = null;
        Vec3 var11 = ClarityRenderUtils.getCameraPos();
        new double[3][0] = arg1.doubleValue3;
        new double[3][1] = arg1.doubleValue4;
        new double[3][2] = arg1.doubleValue5;
        double[] var12 = new double[3];
        new double[3][0] = arg1.doubleValue6;
        new double[3][1] = arg1.doubleValue7;
        new double[3][2] = arg1.doubleValue8;
        double[] var13 = new double[3];
        new double[3][0] = arg1.doubleValue9;
        new double[3][1] = arg1.doubleValue10;
        new double[3][2] = arg1.doubleValue11;
        double[] var14 = new double[3];
        if (arg9) {
        } else {
        }
        double var15 = 0.0;
        WorldFx.begin();
        switch (arg0) {
            case 0 -> {}
            case 1 -> {}
            case 2 -> {}
            case 3 -> {}
            default -> {}
        }
        var17 = arg1.computeVector(0.0, ((0.3 * arg2) + var15), 0.0);
        var18 = (0.36 * arg2);
        quad(var11, var17, var12, var14, var18, var18, 0.0F, 0.0F, arg3, arg5);
        var17 = arg1.computeVector(0.0, -0.234, 0.0);
        var18 = (0.52 * arg2);
        var20 = 0;
        while (true) {
            if (var20 >= 2) break;
            var21 = ((((double) arg7) * 0.35) + ((((double) var20) * 3.141592653589793) * 0.5));
            if (var20 == 0) {
            } else {
            }
            var23 = -0.55;
            double[] var25 = add(mul(var12, Math.cos(var21)), mul(var14, Math.sin(var21)));
            var26 = add(mul(var12, -(Math.sin(var21))), mul(var14, Math.cos(var21)));
            var27 = add(mul(var25, Math.cos(var23)), mul(var13, Math.sin(var23)));
            if (var20 == 0) {
            } else {
            }
            quad(var17, var27, var26, var18, var18, ((float) var20), 1.0F, -1.0F, arg3, arg5);
            var20++;
        }
        var17 = arg1.computeVector(0.0, (-0.05 * arg2), 0.0);
        var18 = (0.25 * arg2);
        var20 = 24;
        var21 = 0;
        while (true) {
            if (var21 >= var20) break;
            var22 = ((6.283185307179586 * ((double) var21)) / ((double) var20));
            double var24 = ((6.283185307179586 * ((double) (var21 + 1))) / ((double) var20));
            var26 = add(mul(var12, Math.cos(var22)), mul(var14, Math.sin(var22)));
            var27 = add(mul(var12, Math.cos(var24)), mul(var14, Math.sin(var24)));
            double[] var28 = add(var17, mul(var26, var18));
            double[] var29 = add(var17, mul(var27, var18));
            double[] var32 = add(add(var17, mul(var26, (var18 * 1.138))), mul(var13, ((0.24 * arg2) * 1.15)));
            double[] var33 = add(add(var17, mul(var27, (var18 * 1.138))), mul(var13, ((0.24 * arg2) * 1.15)));
            float var34 = (((float) var21) / ((float) var20));
            float var35 = (((float) (var21 + 1)) / ((float) var20));
            tri(var11, var28, var34, 0.0F, var29, var35, 0.0F, var33, var35, 1.15F, arg3, arg5, 0.0F);
            tri(var11, var28, var34, 0.0F, var33, var35, 1.15F, var32, var34, 1.15F, arg3, arg5, 0.0F);
            var21++;
        }
        var17 = -1;
        while (true) {
            if (var17 > 1) break;
            var18 = (0.28 * ((double) var17));
            var20 = add(mul(var13, Math.cos(var18)), mul(var12, Math.sin(var18)));
            var21 = add(mul(var12, Math.cos(var18)), mul(var13, -(Math.sin(var18))));
            var22 = add(arg1.computeVector(((((double) var17) * 0.15) * arg2), 0.0, (-0.02 * arg2)), mul(var20, (0.12 * arg2)));
            var23 = mul(var21, ((double) var17));
            quad(var11, var22, var23, var20, (0.12 * arg2), (0.13 * arg2), 0.0F, 0.0F, arg3, arg5);
            var17 += 2;
        }
        flush("hats", arg6, arg0, arg4, arg7, arg8);
    }

    private static void flush(String arg0, boolean arg1, int arg2, float[] arg3, float arg4, float arg5) {
        WorldFx.flush(arg0, arg1, (p0) -> CosmeticFx.lambda$flush$0(arg4, arg2, arg3, arg5, p0));
    }

    private static void quad(Vec3 arg0, double[] arg1, double[] arg2, double[] arg3, double arg4, double arg5, float arg6, float arg7, float[] arg8, float arg9) {
        arg4 = (arg4 * 1.35);
        arg5 = (arg5 * 1.35);
        float var14 = ((float) 1.35);
        double[] var15 = add(add(arg1, mul(arg2, -(arg4))), mul(arg3, -(arg5)));
        double[] var16 = add(add(arg1, mul(arg2, arg4)), mul(arg3, -(arg5)));
        double[] var17 = add(add(arg1, mul(arg2, arg4)), mul(arg3, arg5));
        double[] var18 = add(add(arg1, mul(arg2, -(arg4))), mul(arg3, arg5));
        tri(arg0, var15, -(var14), -(var14), var16, var14, -(var14), var17, var14, var14, arg8, arg9, arg6, arg7);
        tri(arg0, var15, -(var14), -(var14), var17, var14, var14, var18, -(var14), var14, arg8, arg9, arg6, arg7);
    }

    private static void tri(Vec3 arg0, double[] arg1, float arg2, float arg3, double[] arg4, float arg5, float arg6, double[] arg7, float arg8, float arg9, float[] arg10, float arg11, float arg12) {
        tri(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10, arg11, arg12, 0.0F);
    }

    private static void tri(Vec3 arg0, double[] arg1, float arg2, float arg3, double[] arg4, float arg5, float arg6, double[] arg7, float arg8, float arg9, float[] arg10, float arg11, float arg12, float arg13) {
        vert(arg0, arg1, arg2, arg3, arg10, arg11, arg12, arg13);
        vert(arg0, arg4, arg5, arg6, arg10, arg11, arg12, arg13);
        vert(arg0, arg7, arg8, arg9, arg10, arg11, arg12, arg13);
    }

    private static void vert(Vec3 arg0, double[] arg1, float arg2, float arg3, float[] arg4, float arg5, float arg6, float arg7) {
        WorldFx.vertex(((float) (arg1[0] - arg0.x)), ((float) (arg1[1] - arg0.y)), ((float) (arg1[2] - arg0.z)), arg2, arg3, arg4[0], arg4[1], arg4[2], arg5, arg6, arg7);
    }

    private static double[] add(double[] arg0, double[] arg1) {
        new double[3][0] = (arg0[0] + arg1[0]);
        new double[3][1] = (arg0[1] + arg1[1]);
        new double[3][2] = (arg0[2] + arg1[2]);
        return new double[3];
    }

    private static double[] mul(double[] arg0, double arg1) {
        new double[3][0] = (arg0[0] * arg1);
        new double[3][1] = (arg0[1] * arg1);
        new double[3][2] = (arg0[2] * arg1);
        return new double[3];
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
        new String[5][0] = "Seraph";
        new String[5][1] = "Void";
        new String[5][2] = "Circuit";
        new String[5][3] = "Inferno";
        new String[5][4] = "Prism";
        WING_STYLES = new String[5];
        new String[4][0] = "Divine Halo";
        new String[4][1] = "Orbit Rings";
        new String[4][2] = "Nebula Crown";
        new String[4][3] = "Neon Ears";
        HAT_STYLES = new String[4];
        gx = new float[169];
        gy = new float[gx.length];
        gz = new float[gx.length];
        MAPPER = new WingRenderContext((p0, p1, p2, p3, p4, p5, p6, p7) -> CosmeticFx.lambda$static$0(p0, p1, p2, p3, p4, p5, p6, p7), 0, 0, 1.0, 0.0, false);
    }

}
