package need.clarity.modules.visual.cosmetics.hats;

import java.util.function.DoubleUnaryOperator;
import need.clarity.modules.visual.cosmetics.ColorMathUtils;
import need.clarity.modules.visual.cosmetics.CosmeticDrawBuilder;
import need.clarity.modules.visual.cosmetics.GeometryPrimitives;
import need.clarity.modules.visual.cosmetics.ICosmeticEffectRenderer;
import need.clarity.modules.visual.cosmetics.IGradientColorSampler;

public final class RoyalCrownRenderer implements ICosmeticEffectRenderer {
    static final double CONST_DBL = 0.255;
    static final double CONST_DBL1 = 0.298;
    static final double CONST_DBL2 = 0.125;
    static final double CONST_DBL3 = 0.285;
    static final double CONST_DBL4 = 0.06;
    static final int CONST_INT = 24;
    static final int CONST_INT1 = 6;
    static final int CONST_INT2 = 5;
    private static final double CONST_DBL5 = 6.283185307179586;
    private final HatRenderSpec hatRenderSpec;

    public RoyalCrownRenderer() {
        HatRenderSpec var2 = HatRenderSpec.evaluate();
        this(var2);
    }

    public RoyalCrownRenderer(HatRenderSpec arg0) {
        this.hatRenderSpec = arg0;
    }

    private static double computeDoubleMetric(double arg0) {
        double var12 = ((0.04299999999999998 * arg0) / 0.125);
        return (0.255 + var12);
    }

    public void renderEffect(CosmeticDrawBuilder arg0) {
        double var261 = 0.0;
        double var264 = 0.0;
        int var532 = 0;
        double var279 = 0.0;
        double var281 = 0.0;
        int var30 = 0;
        double var378 = 0.0;
        double var380 = 0.0;
        double var138 = 0.0;
        int var140 = 0;
        double var189 = 0.0;
        double var222 = 0.0;
        boolean var48 = arg0.enabledState;
        if (var48) {
            var138 = arg0.doubleValue;
        } else {
            var138 = 0.0;
        }
        double var23 = var138;
        int var51 = arg0.counterValue;
        int var53 = arg0.counterValue1;
        int var55 = ColorMathUtils.computeColorOrIndex8(var53, 0.5);
        int var57 = ColorMathUtils.computeColorOrIndex7(var51, 0.55);
        CosmeticDrawBuilder var59 = arg0.drawGeometryPart();
        HatRenderSpec var224 = this.hatRenderSpec;
        boolean var225 = var224.helmet();
        if (var225) {
            var279 = 0.06;
        } else {
            var279 = 0.0;
        }
        CosmeticDrawBuilder var61 = arg0.drawGeometryPart(0.0, var279, 0.0);
        IGradientColorSampler var488 = (p0, p1) -> RoyalCrownRenderer.lambda$d(var51, var23, p0, p1);
        int var505 = 0;
        GeometryPrimitives.drawGeometryPart(arg0, 0.0, 0.125, 0.255, 0.298, 24, var488, var505, false);
        IGradientColorSampler var64 = (p0, p1) -> RoyalCrownRenderer.lambda$c(var51, var23, p0, p1);
        double var410 = computeDoubleMetric(0.026);
        double var463 = 0.004;
        double var412 = (var410 + var463);
        int var465 = 24;
        IGradientColorSampler var489 = var64;
        int var506 = 0;
        GeometryPrimitives.drawGeometryPart(arg0, 0.0, 0.026, 0.259, var412, var465, var489, var506, false);
        double var324 = computeDoubleMetric(0.099);
        double var326 = (var324 + 0.004);
        int var466 = 24;
        IGradientColorSampler var490 = var64;
        int var507 = 0;
        GeometryPrimitives.drawGeometryPart(arg0, 0.099, 0.125, var326, 0.302, var466, var490, var507, false);
        var30 = 0;
        while (true) {
            int var68 = var30;
            if (var68 >= 4) break;
            int var202 = var30;
            double var203 = ((double) var202);
            double var205 = (var203 * 3.141592653589793);
            double var207 = (var205 / 2.0);
            double var105 = (1.5707963267948966 + var207);
            int var107 = var30;
            if (var107 == 0) {
                var140 = 1;
            } else {
                var140 = 0;
            }
            int var537 = var140;
            double var112 = computeDoubleMetric(0.064);
            double var114 = (var112 + 0.002);
            CrownJewelGeometry.drawGeometryPart(arg0, var114, var105, 0.064);
            if (var537 != 0) {
                var281 = 0.044;
            } else {
                var281 = 0.034;
            }
            double var258 = (var281 * 0.98);
            if (var537 != 0) {
                var380 = 0.044;
            } else {
                var380 = 0.034;
            }
            double var427 = 1.32;
            double var358 = (var380 * var427);
            int var429 = 8;
            int var455 = var51;
            double var482 = var23;
            IGradientColorSampler var456 = (p0, p1) -> RoyalCrownRenderer.lambda$b(var455, var482, p0, p1);
            GeometryPrimitives.drawGeometryPart(arg0, 0.004, var258, var358, var429, var456);
            if (var537 != 0) {
                var189 = 0.044;
            } else {
                var189 = 0.034;
            }
            if (var537 != 0) {
                var261 = 0.03;
            } else {
                var261 = 0.022;
            }
            if (var537 != 0) {
                var378 = 0.014;
            } else {
                var378 = 0.01;
            }
            int var430 = 6;
            int var457 = var53;
            int var484 = var57;
            int var498 = var30;
            double var499 = ((double) var498);
            double var501 = (var499 * 1.7);
            CrownJewelGeometry.drawGeometryPart(arg0, var189, var261, var378, var430, var457, var484, var501);
            if (var537 != 0) {
                var264 = 0.03;
            } else {
                var264 = 0.022;
            }
            double[] var180 = GeometryPrimitives.computeVector(0.0, var264, 0.0);
            if (var537 != 0) {
                var222 = 0.075;
            } else {
                var222 = 0.055;
            }
            int var312 = ColorMathUtils.computeColorOrIndex7(var53, 0.6);
            int var458 = var30;
            double var459 = ((double) var458);
            double var503 = 0.27;
            double var461 = (var459 * var503);
            double var366 = CrownJewelGeometry.drawGeometryPart(arg0, 2.9, var461);
            CrownJewelGeometry.drawGeometryPart(arg0, var180, var222, var312, var366);
            CosmeticDrawBuilder var121 = arg0.drawGeometryPart1();
            double var214 = Math.cos(var105);
            double var126 = (0.29 * var214);
            double var403 = Math.sin(var105);
            double var315 = (0.29 * var403);
            double[] var128 = GeometryPrimitives.computeVector(var126, 0.121, var315);
            double var268 = Math.cos(var105);
            double var183 = (0.02 * var268);
            double var431 = var105;
            double var433 = Math.sin(var431);
            double var370 = (0.02 * var433);
            double[] var130 = GeometryPrimitives.computeVector(var128, var183, 0.078, var370);
            int var131 = var30;
            double var132 = ((double) var131);
            double var134 = (var132 / 4.0);
            double var435 = var134;
            double var485 = var23;
            IGradientColorSampler var406 = (p0, p1) -> RoyalCrownRenderer.lambda$a(var51, var435, var485, p0, p1);
            GeometryPrimitives.drawGeometryPart(arg0, var128, var130, 0.03, 4, var406);
            double var187 = var130[0];
            double var275 = var130[1];
            double var277 = (var275 + 0.008);
            double var376 = var130[2];
            double var437 = 0.015;
            int var487 = var53;
            CrownJewelGeometry.drawGeometryPart(arg0, var187, var277, var376, var437, var487);
            var30 = (var30 + 1);
        }
        int var467 = var55;
        IGradientColorSampler var468 = (p0, p1) -> RoyalCrownRenderer.lambda$a(var467, p0, p1);
        drawGeometryPart(arg0, 0.12, 0.278, 0.135, 16, 4, var468);
        var532 = 0;
        while (true) {
            int var71 = var532;
            if (var71 >= 4) break;
            int var5 = var532;
            double var191 = ((double) var5);
            double var193 = (var191 * 3.141592653589793);
            double var195 = (var193 / 2.0);
            double var74 = (0.7853981633974483 + var195);
            double var78 = Math.cos(var74);
            double var82 = Math.sin(var74);
            double[][] var158 = new double[4][];
            double var287 = (0.286 * var78);
            double var439 = 0.286;
            double var491 = var82;
            double var441 = (var439 * var491);
            double[] var289 = GeometryPrimitives.computeVector(var287, 0.119, var441);
            var158[0] = var289;
            double var292 = (0.292 * var78);
            double var443 = 0.292;
            double var493 = var82;
            double var445 = (var443 * var493);
            double[] var294 = GeometryPrimitives.computeVector(var292, 0.215, var445);
            var158[1] = var294;
            double var297 = (0.15 * var78);
            double var447 = 0.15;
            double var495 = var82;
            double var449 = (var447 * var495);
            double[] var299 = GeometryPrimitives.computeVector(var297, 0.285, var449);
            var158[2] = var299;
            double var451 = 0.0;
            double[] var302 = GeometryPrimitives.computeVector(0.0, 0.285, var451);
            var158[3] = var302;
            double[][] var85 = GeometryPrimitives.computeMatrix(6, var158);
            int var86 = var532;
            DoubleUnaryOperator var197 = (p0) -> RoyalCrownRenderer.lambda$b(p0);
            IGradientColorSampler var304 = (p0, p1) -> RoyalCrownRenderer.lambda$a(var51, var86, var23, p0, p1);
            GeometryPrimitives.drawGeometryPart(arg0, var85, var197, 5, var304, false, false);
            var532 = (var532 + 1);
        }
        int var469 = 8;
        int var497 = 4;
        int var508 = var51;
        IGradientColorSampler var509 = (p0, p1) -> RoyalCrownRenderer.lambda$a(var508, var23, p0, p1);
        GeometryPrimitives.drawGeometryPart(arg0, 0.0, 0.31499999999999995, 0.0, 0.036, var469, var497, var509);
        double var243 = (0.31499999999999995 + 0.022);
        double[] var164 = GeometryPrimitives.computeVector(0.012, var243, 0.028);
        int var306 = ColorMathUtils.computeColorOrIndex7(var51, 0.7);
        double var453 = 0.5;
        double var341 = CrownJewelGeometry.drawGeometryPart(arg0, 3.3, var453);
        CrownJewelGeometry.drawGeometryPart(arg0, var164, 0.07, var306, var341);
        double var94 = (0.31499999999999995 + 0.032);
        int var97 = ColorMathUtils.computeColorOrIndex7(var51, 0.15);
        double var421 = 0.008;
        double var470 = var94;
        double var510 = 0.085;
        double var472 = (var470 + var510);
        GeometryPrimitives.drawGeometryPart(arg0, -0.008, var94, -0.008, var421, var472, 0.008, var51, var97);
        double var249 = (var94 + 0.048);
        double var423 = 0.034;
        double var474 = var94;
        double var476 = (var474 + 0.064);
        GeometryPrimitives.drawGeometryPart(arg0, -0.034, var249, -0.008, var423, var476, 0.008, var51, var97);
        double var253 = (var94 + 0.048);
        double var425 = 0.008;
        double var478 = var94;
        double var480 = (var478 + 0.064);
        GeometryPrimitives.drawGeometryPart(arg0, -0.008, var253, -0.034, var425, var480, 0.034, var51, var97);
        CosmeticDrawBuilder var102 = arg0.drawGeometryPart1();
    }

    private static void drawGeometryPart(CosmeticDrawBuilder arg0, double arg1, double arg2, double arg3, int arg4, int arg5, IGradientColorSampler arg6) {
        int var31 = 0;
        int var48 = 0;
        var31 = 0;
        while (true) {
            if (var31 < arg5) {
                double var63 = ((double) var31);
                double var147 = ((double) arg5);
                double var65 = (var63 / var147);
                double var69 = ((double) (var31 + 1));
                double var150 = ((double) arg5);
                double var71 = (var69 / var150);
                double var77 = ((var65 * 3.141592653589793) / 2.0);
                double var83 = ((var71 * 3.141592653589793) / 2.0);
                double var162 = Math.cos(var77);
                double var87 = (arg2 * var162);
                double var166 = Math.cos(var83);
                double var91 = (arg2 * var166);
                double var202 = Math.sin(var77);
                double var170 = (arg3 * var202);
                double var95 = (arg1 + var170);
                double var206 = Math.sin(var83);
                double var174 = (arg3 * var206);
                double var99 = (arg1 + var174);
                var48 = 0;
                while (true) {
                    if (var48 >= arg4) break;
                    double var104 = ((double) var48);
                    double var177 = ((double) arg4);
                    double var106 = (var104 / var177);
                    double var110 = ((double) (var48 + 1));
                    double var180 = ((double) arg4);
                    double var112 = (var110 / var180);
                    double var118 = Math.cos((var106 * 6.283185307179586));
                    double var124 = Math.sin((var106 * 6.283185307179586));
                    double var130 = Math.cos((var112 * 6.283185307179586));
                    double var136 = Math.sin((var112 * 6.283185307179586));
                    double var144 = (var87 * var118);
                    double var217 = (var87 * var124);
                    double[] var146 = GeometryPrimitives.computeVector(var144, var95, var217);
                    double var192 = (var87 * var130);
                    double var225 = (var87 * var136);
                    double[] var194 = GeometryPrimitives.computeVector(var192, var95, var225);
                    double var199 = (var91 * var130);
                    double var237 = (var91 * var136);
                    double[] var201 = GeometryPrimitives.computeVector(var199, var99, var237);
                    double var214 = (var91 * var118);
                    double var245 = (var91 * var124);
                    double[] var216 = GeometryPrimitives.computeVector(var214, var99, var245);
                    int var224 = arg6.computeColorOrIndex(var106, var65);
                    int var234 = arg6.computeColorOrIndex(var112, var65);
                    int var242 = arg6.computeColorOrIndex(var112, var71);
                    int var252 = arg6.computeColorOrIndex(var106, var71);
                    arg0.applyStep(var146, var194, var201, var216, var224, var234, var242, var252);
                    var48 = (var48 + 1);
                }
                return;
                var31 = (var31 + 1);
            }
        }
    }

}
