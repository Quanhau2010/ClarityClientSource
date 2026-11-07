package need.clarity.modules.visual.cosmetics.hats;

import need.clarity.modules.visual.cosmetics.ColorMathUtils;
import need.clarity.modules.visual.cosmetics.CosmeticDrawBuilder;
import need.clarity.modules.visual.cosmetics.GeometryPrimitives;
import need.clarity.modules.visual.cosmetics.ICosmeticEffectRenderer;
import need.clarity.modules.visual.cosmetics.IGradientColorSampler;

public final class SpikedCrownRenderer implements ICosmeticEffectRenderer {
    static final double CONST_DBL = 0.262;
    static final double CONST_DBL1 = 0.288;
    static final double CONST_DBL2 = 0.095;
    static final double CONST_DBL3 = 0.06;
    static final int CONST_INT = 24;
    static final int CONST_INT1 = 9;
    static final double[] coordsVector;
    private static final double CONST_DBL4 = 6.283185307179586;
    private final HatRenderSpec hatRenderSpec;

    public SpikedCrownRenderer() {
        HatRenderSpec var2 = HatRenderSpec.evaluate();
        this(var2);
    }

    public SpikedCrownRenderer(HatRenderSpec arg0) {
        this.hatRenderSpec = arg0;
    }

    private static double computeDoubleMetric(double arg0) {
        double var12 = ((0.025999999999999968 * arg0) / 0.095);
        return (0.262 + var12);
    }

    public void renderEffect(CosmeticDrawBuilder arg0) {
        double var267 = 0.0;
        int var29 = 0;
        int var30 = 0;
        double var318 = 0.0;
        double var320 = 0.0;
        double var118 = 0.0;
        int var126 = 0;
        double var405 = 0.0;
        double var160 = 0.0;
        double var248 = 0.0;
        int var507 = 0;
        int var508 = 0;
        boolean var47 = arg0.enabledState;
        if (var47) {
            var160 = arg0.doubleValue;
        } else {
            var160 = 0.0;
        }
        double var22 = var160;
        int var49 = arg0.counterValue;
        int var51 = arg0.counterValue1;
        int var53 = ColorMathUtils.computeColorOrIndex8(var49, 0.22);
        int var55 = ColorMathUtils.computeColorOrIndex7(var49, 0.5);
        CosmeticDrawBuilder var57 = arg0.drawGeometryPart();
        HatRenderSpec var265 = this.hatRenderSpec;
        boolean var266 = var265.helmet();
        if (var266) {
            var267 = 0.06;
        } else {
            var267 = 0.0;
        }
        CosmeticDrawBuilder var59 = arg0.drawGeometryPart(0.0, var267, 0.0);
        IGradientColorSampler var486 = (p0, p1) -> SpikedCrownRenderer.lambda$c(var53, var22, p0, p1);
        int var499 = 0;
        GeometryPrimitives.drawGeometryPart(arg0, 0.0, 0.095, 0.262, 0.288, 24, var486, var499, false);
        IGradientColorSampler var62 = (p0, p1) -> SpikedCrownRenderer.lambda$b(var49, var22, p0, p1);
        double var434 = computeDoubleMetric(0.018);
        double var436 = (var434 + 0.004);
        int var500 = 0;
        int var502 = 0;
        GeometryPrimitives.drawGeometryPart(arg0, 0.0, 0.018, 0.266, var436, 24, var62, var500, var502);
        double var363 = computeDoubleMetric(0.077);
        double var365 = (var363 + 0.004);
        int var501 = 0;
        int var503 = 0;
        GeometryPrimitives.drawGeometryPart(arg0, 0.077, 0.095, var365, 0.292, 24, var62, var501, var503);
        var29 = 0;
        while (true) {
            int var66 = var29;
            if (var66 >= 18) break;
            int var127 = var29;
            double var128 = ((double) var127);
            double var130 = (var128 * 6.283185307179586);
            double var132 = (var130 / 18.0);
            double var509 = var132;
            boolean var136 = isEligible(var509);
            if (var136) {
            } else {
                double var141 = computeDoubleMetric(0.048);
                double var143 = (var141 + 0.002);
                double[] var147 = CrownJewelGeometry.computeVector(var143, var509, 0.048);
                double var150 = (var143 + 0.012);
                double[] var152 = CrownJewelGeometry.computeVector(var150, var509, 0.048);
                int var153 = var29;
                double var154 = ((double) var153);
                double var156 = (var154 / 18.0);
                IGradientColorSampler var431 = (p0, p1) -> SpikedCrownRenderer.lambda$a(var49, var156, var22, p0, p1);
                GeometryPrimitives.drawGeometryPart(arg0, var147, var152, 0.0105, 4, var431);
            }
            var29 = (var29 + 1);
        }
        double[] var68 = new double[3];
        var68[0] = 1.5707963267948966;
        double var369 = Math.toRadians(40.0);
        double var279 = (1.5707963267948966 + var369);
        var68[1] = var279;
        double var373 = Math.toRadians(40.0);
        double var283 = (1.5707963267948966 - var373);
        var68[2] = var283;
        double[] var506 = var68;
        var30 = 0;
        while (true) {
            int var70 = var30;
            if (var70 >= 3) break;
            int var116 = var30;
            if (var116 == 0) {
                var126 = 1;
            } else {
                var126 = 0;
            }
            int var513 = var126;
            if (var513 != 0) {
                var118 = 0.038;
            } else {
                var118 = 0.024;
            }
            double var33 = var118;
            double var197 = computeDoubleMetric(0.048);
            double var199 = (var197 + 0.002);
            int var350 = var30;
            double var310 = var506[var350];
            CrownJewelGeometry.drawGeometryPart(arg0, var199, var310, 0.048);
            double var314 = (var33 * 0.98);
            double var402 = (var33 * 1.34);
            IGradientColorSampler var470 = (p0, p1) -> SpikedCrownRenderer.lambda$a(var49, var22, p0, p1);
            GeometryPrimitives.drawGeometryPart(arg0, 0.004, var314, var402, 8, var470);
            if (var513 != 0) {
                var320 = 0.026;
            } else {
                var320 = 0.017;
            }
            if (var513 != 0) {
                var405 = 0.012;
            } else {
                var405 = 0.008;
            }
            int var492 = var30;
            double var493 = ((double) var492);
            double var504 = 2.3;
            double var495 = (var493 * var504);
            CrownJewelGeometry.drawGeometryPart(arg0, var33, var320, var405, 6, var51, var55, var495);
            if (var513 != 0) {
                var318 = 0.026;
            } else {
                var318 = 0.017;
            }
            double[] var207 = GeometryPrimitives.computeVector(0.0, var318, 0.0);
            if (var513 != 0) {
                var248 = 0.07;
            } else {
                var248 = 0.05;
            }
            int var352 = ColorMathUtils.computeColorOrIndex7(var51, 0.6);
            int var472 = var30;
            double var473 = ((double) var472);
            double var497 = 0.31;
            double var475 = (var473 * var497);
            double var412 = CrownJewelGeometry.drawGeometryPart(arg0, 3.1, var475);
            CrownJewelGeometry.drawGeometryPart(arg0, var207, var248, var352, var412);
            CosmeticDrawBuilder var125 = arg0.drawGeometryPart1();
            var30 = (var30 + 1);
        }
        var507 = 0;
        while (true) {
            int var72 = var507;
            if (var72 >= 9) break;
            int var211 = var507;
            double var212 = ((double) var211);
            double var214 = (var212 * 6.283185307179586);
            double var216 = (var214 / 9.0);
            double var75 = (1.5707963267948966 + var216);
            double[] var77 = coordsVector;
            int var177 = var507;
            double var78 = var77[var177];
            double var328 = (var22 * 2.4);
            int var415 = var507;
            double var416 = ((double) var415);
            double var418 = (var416 * 0.8);
            double var330 = (var328 + var418);
            double var332 = Math.sin(var330);
            double var220 = (0.5 * var332);
            double var82 = (0.5 + var220);
            double var287 = (0.3 * var82);
            double var180 = (0.15 + var287);
            int var85 = ColorMathUtils.computeColorOrIndex7(var51, var180);
            double[] var88 = CrownJewelGeometry.computeVector(0.27799999999999997, var75, 0.089);
            double var226 = (0.04 * var78);
            double var91 = (0.03 + var226);
            double var291 = Math.cos(var75);
            double var184 = (var91 * var291);
            double var444 = Math.sin(var75);
            double var379 = (var91 * var444);
            double[] var94 = GeometryPrimitives.computeVector(var88, var184, var78, var379);
            int var95 = var507;
            double var383 = (0.05 * var78);
            double var297 = (0.026 + var383);
            int var489 = var85;
            IGradientColorSampler var421 = (p0, p1) -> SpikedCrownRenderer.lambda$a(var49, var95, var22, var489, p0, p1);
            GeometryPrimitives.drawGeometryPart(arg0, var88, var94, var297, 4, var421);
            int var99 = Double.compare(var78, 0.2);
            if (var99 > 0) {
                double[] var188 = GeometryPrimitives.computeVector(var94, 0.0, 0.008, 0.0);
                int var341 = ColorMathUtils.computeColorOrIndex7(var51, 0.7);
                int var462 = var507;
                double var463 = ((double) var462);
                double var490 = 0.19;
                double var465 = (var463 * var490);
                double var389 = CrownJewelGeometry.drawGeometryPart(arg0, 3.7, var465);
                CrownJewelGeometry.drawGeometryPart(arg0, var188, 0.07, var341, var389);
            }
            var507 = (var507 + 1);
        }
        var508 = 0;
        while (true) {
            int var102 = var508;
            if (var102 >= 9) break;
            int var235 = var508;
            double var236 = ((double) var235);
            double var238 = (var236 + 0.5);
            double var240 = (var238 * 6.283185307179586);
            double var242 = (var240 / 9.0);
            double var105 = (1.5707963267948966 + var242);
            double var511 = var105;
            double[] var109 = CrownJewelGeometry.computeVector(0.27999999999999997, var511, 0.089);
            double var301 = Math.cos(var511);
            double var192 = (0.012 * var301);
            double var451 = Math.sin(var511);
            double var393 = (0.012 * var451);
            double[] var111 = GeometryPrimitives.computeVector(var109, var192, 0.062, var393);
            int var112 = var508;
            IGradientColorSampler var427 = (p0, p1) -> SpikedCrownRenderer.lambda$a(var49, var112, var22, p0, p1);
            GeometryPrimitives.drawGeometryPart(arg0, var109, var111, 0.022, 4, var427);
            var508 = (var508 + 1);
        }
        CosmeticDrawBuilder var115 = arg0.drawGeometryPart1();
    }

    private static boolean isEligible(double arg0) {
        int var13 = 0;
        double var18 = Math.toRadians(12.0);
        double[] var21 = new double[3];
        var21[0] = 1.5707963267948966;
        double var76 = Math.toRadians(40.0);
        double var70 = (1.5707963267948966 + var76);
        var21[1] = var70;
        double var80 = Math.toRadians(40.0);
        double var74 = (1.5707963267948966 - var80);
        var21[2] = var74;
        int var23 = var21.length;
        var13 = 0;
        while (true) {
            if (var13 >= var23) break;
            int var51 = var13;
            double var27 = var21[var51];
            if (Double.compare(Math.abs(((((((arg0 - var27) % 6.283185307179586) + 6.283185307179586) + 3.141592653589793) % 6.283185307179586) - 3.141592653589793)), var18) < 0) {
                return true;
            }
            var13 = (var13 + 1);
        }
        return false;
    }

    static {
        double[] var4 = new double[9];
        var4[0] = 0.27;
        var4[1] = 0.17;
        var4[2] = 0.21;
        var4[3] = 0.15;
        var4[4] = 0.19;
        var4[5] = 0.19;
        var4[6] = 0.15;
        var4[7] = 0.21;
        var4[8] = 0.17;
        coordsVector = var4;
    }

}
