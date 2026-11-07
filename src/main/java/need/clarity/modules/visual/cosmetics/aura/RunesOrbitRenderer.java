package need.clarity.modules.visual.cosmetics.aura;

import need.clarity.modules.visual.cosmetics.ColorMathUtils;
import need.clarity.modules.visual.cosmetics.CosmeticDrawBuilder;
import need.clarity.modules.visual.cosmetics.GeometryPrimitives;
import need.clarity.modules.visual.cosmetics.ICosmeticEffectRenderer;
import need.clarity.modules.visual.cosmetics.IGradientColorSampler;

public final class RunesOrbitRenderer implements ICosmeticEffectRenderer {
    static final int CONST_INT = 12;
    private static final double CONST_DBL = 0.15;
    private static final double CONST_DBL1 = 0.02;
    private static final double CONST_DBL2 = 0.25;
    private static final double[][][] meshTensor;
    private final OrbitAuraSpec orbitAuraSpec;

    public RunesOrbitRenderer() {
        OrbitAuraSpec var2 = OrbitAuraSpec.evaluate();
        this(var2);
    }

    public RunesOrbitRenderer(OrbitAuraSpec arg0) {
        this.orbitAuraSpec = arg0;
    }

    public void renderEffect(CosmeticDrawBuilder arg0) {
        double var72 = 0.0;
        int var25 = 0;
        double var39 = 0.0;
        int var33 = this.orbitAuraSpec.computeColorOrIndex(12);
        int var36 = this.orbitAuraSpec.computeColorOrIndex1(var33);
        if (arg0.enabledState) {
            var39 = arg0.doubleValue;
        } else {
            var39 = 0.0;
        }
        double var21 = var39;
        double var43 = this.orbitAuraSpec.radius();
        var25 = 0;
        while (true) {
            if (var25 >= var33) break;
            OrbitAuraSpec var48 = this.orbitAuraSpec;
            int var77 = var25;
            double var49 = var48.computeDoubleMetric(var77, var33);
            if ((var25 % 2) == 0) {
                var72 = 0.08;
            } else {
                var72 = -0.08;
            }
            double var101 = (var21 * 1.4);
            double var115 = (((double) var25) * 1.3);
            double var105 = Math.sin((var101 + var115));
            double var90 = (0.03 * var105);
            double var53 = (var72 + var90);
            CosmeticDrawBuilder var56 = arg0.drawGeometryPart();
            double var95 = Math.sin(var49);
            double var81 = (var43 * var95);
            double var119 = Math.cos(var49);
            double var107 = (var43 * var119);
            CosmeticDrawBuilder var58 = arg0.drawGeometryPart(var81, var53, var107);
            CosmeticDrawBuilder var60 = arg0.drawGeometryPart2(var49);
            CosmeticDrawBuilder var62 = arg0.drawGeometryPart3(-0.1);
            double var64 = AuraRenderMath.drawGeometryPart(arg0);
            if (Double.compare(var64, 0.02) > 0) {
                int var87 = var25;
                double var124 = (((double) var25) * 1.3);
                double var110 = AuraRenderMath.drawGeometryPart(arg0, 2.2, var124);
                drawGeometryPart(arg0, var87, var36, var64, var110);
            }
            CosmeticDrawBuilder var71 = arg0.drawGeometryPart1();
            var25 = (var25 + 1);
        }
    }

    private static void drawGeometryPart(CosmeticDrawBuilder arg0, int arg1, int arg2, double arg3, double arg4) {
        int var36 = 0;
        int var38 = arg0.counterValue;
        int var40 = arg0.counterValue1;
        int var43 = ColorMathUtils.computeColorOrIndex8(ColorMathUtils.computeColorOrIndex(var38, var40, 0.2), 0.45);
        double var160 = (0.25 * arg4);
        double var134 = (0.2 + var160);
        int var45 = ColorMathUtils.computeColorOrIndex(var40, -1, var134);
        int var47 = ColorMathUtils.computeColorOrIndex7(var40, 0.3);
        double var164 = (0.45 * arg4);
        double var138 = (0.15 + var164);
        int var48 = ColorMathUtils.computeColorOrIndex(var47, -1, var138);
        CosmeticDrawBuilder var50 = arg0.drawGeometryPart();
        CosmeticDrawBuilder var52 = arg0.drawGeometryPart3(1.5707963267948966);
        IGradientColorSampler var187 = AuraRenderMath.evaluate((p0, p1) -> RunesOrbitRenderer.lambda$b(var43, p0, p1), arg3);
        GeometryPrimitives.drawGeometryPart(arg0, -0.02, 0.02, 0.15, 6, 0.0, var187, true);
        IGradientColorSampler var56 = AuraRenderMath.evaluate((p0, p1) -> RunesOrbitRenderer.lambda$a(var45, p0, p1), arg3);
        GeometryPrimitives.drawGeometryPart(arg0, 0.022, 0.12, 0.1425, 6, var56);
        if (arg2 >= 1) {
            GeometryPrimitives.drawGeometryPart(arg0, -0.022, 0.12, 0.1425, 6, var56);
        }
        CosmeticDrawBuilder var61 = arg0.drawGeometryPart1();
        double[][][] var62 = meshTensor;
        int var141 = meshTensor.length;
        int var106 = (arg1 % var141);
        double[][] var63 = var62[var106];
        int var66 = AuraRenderMath.computeColorOrIndex(ColorMathUtils.computeColorOrIndex(var40, var38, 0.35), arg3);
        int var68 = AuraRenderMath.computeColorOrIndex(var48, arg3);
        int var71 = var63.length;
        var36 = 0;
        while (true) {
            if (var36 >= var71) break;
            int var128 = var36;
            double[] var84 = var63[var128];
            drawGeometryPart(arg0, var84, 0.024, var66, var68);
            if (arg2 >= 1) {
                drawGeometryPart(arg0, var84, -0.024, var66, var68);
            }
            var36 = (var36 + 1);
        }
        int var75 = ColorMathUtils.computeColorOrIndex7(var40, 0.3);
        double var154 = (0.18 * arg4);
        double var117 = (0.26 + var154);
        int var77 = AuraRenderMath.computeColorOrIndex(ColorMathUtils.computeColorOrIndex5(var75, var117), arg3);
        int var79 = ColorMathUtils.computeColorOrIndex5(var40, 0.0);
        drawGeometryPart(arg0, 0.25, 0.045, var77, var79);
        if (arg2 >= 1) {
            drawGeometryPart(arg0, 0.25, -0.045, var77, var79);
        }
    }

    private static void drawGeometryPart(CosmeticDrawBuilder arg0, double[] arg1, double arg2, int arg3, int arg4) {
        double var42 = (arg1[0] * 0.15);
        double var47 = (arg1[1] * 0.15);
        double var52 = (arg1[2] * 0.15);
        double var57 = (arg1[3] * 0.15);
        double var61 = (var52 - var42);
        double var65 = (var57 - var47);
        double var69 = (var61 * var61);
        double var115 = (var65 * var65);
        double var73 = Math.sqrt((var69 + var115));
        if (Double.compare(var73, 1.0E-9) < 0) {
            return;
        }
        double var84 = ((-(var65) / var73) * 0.017);
        double var90 = ((var61 / var73) * 0.017);
        double var98 = (var42 + var84);
        double var134 = (var47 + var90);
        double[] var100 = GeometryPrimitives.computeVector(var98, var134, arg2);
        double var129 = (var42 - var84);
        double var145 = (var47 - var90);
        double[] var131 = GeometryPrimitives.computeVector(var129, var145, arg2);
        double var138 = (var52 - var84);
        double var158 = (var57 - var90);
        double[] var140 = GeometryPrimitives.computeVector(var138, var158, arg2);
        double var149 = (var52 + var84);
        double var167 = (var57 + var90);
        double[] var151 = GeometryPrimitives.computeVector(var149, var167, arg2);
        arg0.applyStep(var100, var131, var140, var151, arg3, arg3, arg4, arg4);
    }

    private static void drawGeometryPart(CosmeticDrawBuilder arg0, double arg1, double arg2, int arg3, int arg4) {
        int var23 = 0;
        double[] var28 = GeometryPrimitives.computeVector(0.0, 0.0, arg2);
        var23 = 0;
        while (true) {
            if (var23 >= 6) break;
            double var36 = ((((double) var23) * 3.141592653589793) / 3.0);
            double var44 = ((((double) (var23 + 1)) * 3.141592653589793) / 3.0);
            double var67 = Math.cos(var36);
            double var59 = (arg1 * var67);
            double var81 = Math.sin(var36);
            double var71 = (arg1 * var81);
            double[] var61 = GeometryPrimitives.computeVector(var59, var71, arg2);
            double var74 = Math.cos(var44);
            double var62 = (arg1 * var74);
            double var86 = Math.sin(var44);
            double var78 = (arg1 * var86);
            double[] var64 = GeometryPrimitives.computeVector(var62, var78, arg2);
            arg0.applyStep(var28, var61, var64, arg3, arg4, arg4);
            var23 = (var23 + 1);
        }
    }

    static {
        double[][][] var8 = new double[6][][];
        double[][] var14 = new double[3][];
        double[] var42 = new double[4];
        var42[0] = 0.0;
        var42[1] = -0.55;
        var42[2] = 0.0;
        var42[3] = 0.55;
        var14[0] = var42;
        double[] var44 = new double[4];
        var44[0] = 0.0;
        var44[1] = 0.55;
        var44[2] = 0.42;
        var44[3] = 0.22;
        var14[1] = var44;
        double[] var46 = new double[4];
        var46[0] = 0.0;
        var46[1] = 0.2;
        var46[2] = 0.42;
        var46[3] = -0.13;
        var14[2] = var46;
        var8[0] = var14;
        double[][] var16 = new double[3][];
        double[] var48 = new double[4];
        var48[0] = 0.0;
        var48[1] = 0.55;
        var48[2] = -0.42;
        var48[3] = -0.35;
        var16[0] = var48;
        double[] var50 = new double[4];
        var50[0] = 0.0;
        var50[1] = 0.55;
        var50[2] = 0.42;
        var50[3] = -0.35;
        var16[1] = var50;
        double[] var52 = new double[4];
        var52[0] = -0.42;
        var52[1] = -0.35;
        var52[2] = 0.42;
        var52[3] = -0.35;
        var16[2] = var52;
        var8[1] = var16;
        double[][] var18 = new double[2][];
        double[] var54 = new double[4];
        var54[0] = -0.42;
        var54[1] = -0.5;
        var54[2] = 0.42;
        var54[3] = 0.5;
        var18[0] = var54;
        double[] var56 = new double[4];
        var56[0] = -0.42;
        var56[1] = 0.5;
        var56[2] = 0.42;
        var56[3] = -0.5;
        var18[1] = var56;
        var8[2] = var18;
        double[][] var20 = new double[4][];
        double[] var58 = new double[4];
        var58[0] = 0.0;
        var58[1] = 0.55;
        var58[2] = 0.4;
        var58[3] = 0.0;
        var20[0] = var58;
        double[] var60 = new double[4];
        var60[0] = 0.4;
        var60[1] = 0.0;
        var60[2] = 0.0;
        var60[3] = -0.55;
        var20[1] = var60;
        double[] var62 = new double[4];
        var62[0] = 0.0;
        var62[1] = -0.55;
        var62[2] = -0.4;
        var62[3] = 0.0;
        var20[2] = var62;
        double[] var64 = new double[4];
        var64[0] = -0.4;
        var64[1] = 0.0;
        var64[2] = 0.0;
        var64[3] = 0.55;
        var20[3] = var64;
        var8[3] = var20;
        double[][] var22 = new double[3][];
        double[] var66 = new double[4];
        var66[0] = -0.36;
        var66[1] = 0.52;
        var66[2] = 0.22;
        var66[3] = 0.16;
        var22[0] = var66;
        double[] var68 = new double[4];
        var68[0] = 0.22;
        var68[1] = 0.16;
        var68[2] = -0.22;
        var68[3] = -0.16;
        var22[1] = var68;
        double[] var70 = new double[4];
        var70[0] = -0.22;
        var70[1] = -0.16;
        var70[2] = 0.36;
        var70[3] = -0.52;
        var22[2] = var70;
        var8[4] = var22;
        double[][] var24 = new double[3][];
        double[] var72 = new double[4];
        var72[0] = 0.0;
        var72[1] = -0.55;
        var72[2] = 0.0;
        var72[3] = 0.55;
        var24[0] = var72;
        double[] var74 = new double[4];
        var74[0] = -0.38;
        var74[1] = 0.18;
        var74[2] = 0.38;
        var74[3] = 0.18;
        var24[1] = var74;
        double[] var76 = new double[4];
        var76[0] = -0.26;
        var76[1] = -0.28;
        var76[2] = 0.26;
        var76[3] = -0.28;
        var24[2] = var76;
        var8[5] = var24;
        meshTensor = var8;
    }

}
