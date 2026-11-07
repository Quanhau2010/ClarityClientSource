package need.clarity.modules.visual.cosmetics.aura;

import need.clarity.modules.visual.cosmetics.ColorMathUtils;
import need.clarity.modules.visual.cosmetics.CosmeticDrawBuilder;
import need.clarity.modules.visual.cosmetics.GeometryPrimitives;
import need.clarity.modules.visual.cosmetics.ICosmeticEffectRenderer;
import need.clarity.modules.visual.cosmetics.IGradientColorSampler;

public final class SwordsOrbitRenderer implements ICosmeticEffectRenderer {
    static final int CONST_INT = 12;
    private static final double CONST_DBL = 0.58;
    private static final double CONST_DBL1 = 0.042;
    private static final double CONST_DBL2 = 0.011;
    private static final double CONST_DBL3 = 0.21;
    private final OrbitAuraSpec orbitAuraSpec;

    public SwordsOrbitRenderer() {
        OrbitAuraSpec var2 = OrbitAuraSpec.evaluate();
        this(var2);
    }

    public SwordsOrbitRenderer(OrbitAuraSpec arg0) {
        this.orbitAuraSpec = arg0;
    }

    public void renderEffect(CosmeticDrawBuilder arg0) {
        int var30 = 0;
        double var47 = 0.0;
        int var40 = this.orbitAuraSpec.computeColorOrIndex(12);
        int var43 = this.orbitAuraSpec.computeColorOrIndex1(var40);
        if (arg0.enabledState) {
            var47 = arg0.doubleValue;
        } else {
            var47 = 0.0;
        }
        double var22 = var47;
        double var51 = this.orbitAuraSpec.radius();
        double var55 = this.orbitAuraSpec.computeDoubleMetric();
        double var165 = (6.283185307179586 * var51);
        double var194 = ((double) var40);
        double var169 = ((var165 / var194) / 0.75);
        double var127 = Math.min(1.0, var169);
        double var59 = Math.max(0.7, var127);
        var30 = 0;
        while (true) {
            if (var30 >= var40) break;
            OrbitAuraSpec var64 = this.orbitAuraSpec;
            int var101 = var30;
            double var65 = var64.computeDoubleMetric(var101, var40);
            double var132 = (var22 * 1.6);
            double var176 = (((double) var30) * 1.1);
            double var136 = Math.sin((var132 + var176));
            double var69 = (0.05 * var136);
            double var140 = (var22 * 1.7);
            double var183 = (((double) var30) * 2.3);
            double var144 = Math.sin((var140 + var183));
            double var73 = (0.12 * var144);
            int var19 = var30;
            this.drawGeometryPart(arg0, var65, var69, var51, var55, var43, var19);
            CosmeticDrawBuilder var77 = arg0.drawGeometryPart();
            double var150 = Math.sin(var65);
            double var105 = (var51 * var150);
            double var209 = Math.cos(var65);
            double var189 = (var51 * var209);
            CosmeticDrawBuilder var79 = arg0.drawGeometryPart(var105, var69, var189);
            CosmeticDrawBuilder var81 = arg0.drawGeometryPart2(var65);
            double var117 = (((-(var55) * 3.141592653589793) / 2.0) + var73);
            CosmeticDrawBuilder var83 = arg0.drawGeometryPart4(var117);
            CosmeticDrawBuilder var85 = arg0.drawGeometryPart(var59);
            CosmeticDrawBuilder var87 = arg0.drawGeometryPart(0.0, -0.21, 0.0);
            CosmeticDrawBuilder var89 = arg0.drawGeometryPart2(0.8);
            double var91 = AuraRenderMath.drawGeometryPart(arg0);
            if (Double.compare(var91, 0.02) > 0) {
                double var207 = (((double) var30) * 1.4);
                double var163 = AuraRenderMath.drawGeometryPart(arg0, 2.6, var207);
                drawGeometryPart(arg0, var91, var163);
            }
            CosmeticDrawBuilder var98 = arg0.drawGeometryPart1();
            var30 = (var30 + 1);
        }
    }

    private void drawGeometryPart(CosmeticDrawBuilder arg0, double arg1, double arg2, double arg3, double arg4, int arg5, int arg6) {
        int var33 = 0;
        int var66 = 0;
        if (arg5 == 2) {
            var66 = 4;
        } else {
            if (arg5 == 1) {
                var66 = 3;
            } else {
                var66 = 3;
            }
            int var25 = var66;
            double var38 = Math.max(arg3, 0.3);
            double var79 = ((arg4 * 0.24) / var38);
            double var42 = (arg1 - var79);
            double[][] var48 = new double[(var25 + 1)][];
            var33 = 0;
            while (true) {
                if (var33 > var25) break;
                double var87 = (arg4 * 0.6);
                double var107 = ((double) var33);
                double var89 = (var87 * var107);
                double var110 = ((double) var25);
                double var91 = (var89 / var110);
                double var62 = (var42 - var91);
                int var73 = var33;
                double var114 = Math.sin(var62);
                double var95 = (arg3 * var114);
                double var133 = Math.cos(var62);
                double var126 = (arg3 * var133);
                double[] var97 = GeometryPrimitives.computeVector(var95, arg2, var126);
                var48[var73] = var97;
                var33 = (var33 + 1);
            }
            int var52 = arg0.counterValue;
            IGradientColorSampler var56 = (p0, p1) -> SwordsOrbitRenderer.lambda$a(arg0.counterValue1, var52, p0, p1);
            double[] var118 = GeometryPrimitives.computeVector(0.0, 1.0, 0.0);
            GeometryPrimitives.drawGeometryPart(arg0, var48, 0.08, 0.0, var118, var56);
            if (arg5 >= 1) {
                double var121 = Math.sin(arg1);
                double var137 = Math.cos(arg1);
                double[] var123 = GeometryPrimitives.computeVector(var121, 0.0, var137);
                GeometryPrimitives.drawGeometryPart(arg0, var48, 0.06, 0.0, var123, var56);
            }
            return;
        }
    }

    private static void drawGeometryPart(CosmeticDrawBuilder arg0, double arg1, double arg2) {
        int var37 = 0;
        int var38 = 0;
        int var41 = 0;
        int var468 = 0;
        int var473 = 0;
        int var44 = arg0.counterValue;
        int var46 = arg0.counterValue1;
        int var48 = ColorMathUtils.computeColorOrIndex8(var44, 0.35);
        int var131 = ColorMathUtils.computeColorOrIndex7(var46, 0.55);
        int var49 = ColorMathUtils.computeColorOrIndex(var48, var131, 0.45);
        int var51 = ColorMathUtils.computeColorOrIndex(var46, -1, 0.15);
        double[] var53 = new double[3];
        var53[0] = 0.0;
        var53[1] = 0.22039999999999998;
        var53[2] = 0.45239999999999997;
        double[] var55 = new double[3];
        var55[0] = 0.042;
        var55[1] = 0.04074;
        var55[2] = 0.033600000000000005;
        double[] var57 = new double[3];
        var57[0] = 0.011;
        var57[1] = 0.011;
        var57[2] = 0.0088;
        double[][][] var59 = new double[3][4][];
        int[][] var61 = new int[3][4];
        var37 = 0;
        while (true) {
            int var63 = var37;
            if (var63 >= 3) break;
            int var172 = var37;
            double[][] var104 = var59[var172];
            int var308 = var37;
            double var237 = var55[var308];
            int var387 = var37;
            double var346 = var53[var387];
            double[] var239 = GeometryPrimitives.computeVector(var237, var346, 0.0);
            var104[0] = var239;
            int var174 = var37;
            double[][] var106 = var59[var174];
            int var388 = var37;
            double var349 = var53[var388];
            int var436 = var37;
            double var413 = var57[var436];
            double[] var242 = GeometryPrimitives.computeVector(0.0, var349, var413);
            var106[1] = var242;
            int var176 = var37;
            double[][] var108 = var59[var176];
            int var309 = var37;
            double var244 = var55[var309];
            double var246 = -(var244);
            int var389 = var37;
            double var352 = var53[var389];
            double[] var248 = GeometryPrimitives.computeVector(var246, var352, 0.0);
            var108[2] = var248;
            int var178 = var37;
            double[][] var110 = var59[var178];
            int var390 = var37;
            double var355 = var53[var390];
            int var437 = var37;
            double var418 = var57[var437];
            double var420 = -(var418);
            double[] var251 = GeometryPrimitives.computeVector(0.0, var355, var420);
            var110[3] = var251;
            int var180 = var37;
            double var112 = var53[var180];
            double var114 = (var112 / 0.58);
            double var471 = var114;
            int var117 = ColorMathUtils.computeColorOrIndex8(var49, 0.3);
            double var359 = (0.6 * var471);
            double var256 = (0.4 + var359);
            int var118 = ColorMathUtils.computeColorOrIndex(var117, var49, var256);
            int var476 = var118;
            double var260 = (0.5 * var471);
            int var120 = ColorMathUtils.computeColorOrIndex(var49, var46, var260);
            int var121 = ColorMathUtils.computeColorOrIndex7(var120, 0.55);
            int var478 = var121;
            int var187 = var37;
            int[] var123 = var61[var187];
            int var263 = AuraRenderMath.computeColorOrIndex(var478, arg1);
            var123[0] = var263;
            int var189 = var37;
            int[] var125 = var61[var189];
            int var265 = AuraRenderMath.computeColorOrIndex(var476, arg1);
            var125[1] = var265;
            int var191 = var37;
            int[] var127 = var61[var191];
            int var267 = AuraRenderMath.computeColorOrIndex(var478, arg1);
            var127[2] = var267;
            int var193 = var37;
            int[] var129 = var61[var193];
            int var269 = ColorMathUtils.computeColorOrIndex6(var476, 0.86);
            int var270 = AuraRenderMath.computeColorOrIndex(var269, arg1);
            var129[3] = var270;
            var37 = (var37 + 1);
        }
        var468 = 0;
        while (true) {
            int var65 = var468;
            if (var65 >= 2) break;
            var38 = 0;
            while (true) {
                int var67 = var38;
                if (var67 >= 4) break;
                int var68 = var38;
                int var69 = (var68 + 1);
                int var70 = (var69 & 3);
                int var208 = var468;
                double[][] var141 = var59[var208];
                int var209 = var38;
                double[] var142 = var141[var209];
                int var287 = var468;
                double[][] var211 = var59[var287];
                double[] var212 = var211[var70];
                int var12 = var468;
                int var320 = (var12 + 1);
                double[][] var290 = var59[var320];
                double[] var291 = var290[var70];
                int var363 = var468;
                int var364 = (var363 + 1);
                double[][] var323 = var59[var364];
                int var365 = var38;
                double[] var324 = var323[var365];
                int var391 = var468;
                int[] var367 = var61[var391];
                int var392 = var38;
                int var368 = var367[var392];
                int var15 = var468;
                int[] var394 = var61[var15];
                int var395 = var394[var70];
                int var16 = var468;
                int var438 = (var16 + 1);
                int[] var426 = var61[var438];
                int var439 = var70;
                int var427 = var426[var439];
                int[][] var440 = var61;
                int var449 = var468;
                int var450 = (var449 + 1);
                int[] var441 = var440[var450];
                int var451 = var38;
                int var442 = var441[var451];
                arg0.applyStep(var142, var212, var291, var324, var368, var395, var427, var442);
                var38 = (var38 + 1);
            }
            var468 = (var468 + 1);
        }
        double[] var74 = GeometryPrimitives.computeVector(0.0, 0.58, 0.0);
        double[] var469 = var74;
        int var76 = ColorMathUtils.computeColorOrIndex7(var46, 0.85);
        int var77 = AuraRenderMath.computeColorOrIndex(var76, arg1);
        int var470 = var77;
        var473 = 0;
        while (true) {
            int var79 = var473;
            if (var79 >= 4) break;
            int var99 = var473;
            int var100 = (var99 + 1);
            int var101 = (var100 & 3);
            int var475 = var101;
            double[][] var170 = var59[2];
            int var232 = var473;
            double[] var171 = var170[var232];
            double[][] var234 = var59[2];
            double[] var235 = var234[var475];
            int[] var343 = var61[2];
            int var383 = var473;
            int var344 = var343[var383];
            int[] var385 = var61[2];
            int var386 = var385[var475];
            arg0.applyStep(var171, var235, var469, var344, var386, var470);
            var473 = (var473 + 1);
        }
        double var329 = (0.5 * arg2);
        double var217 = (0.25 + var329);
        int var81 = ColorMathUtils.computeColorOrIndex(var46, -1, var217);
        int var82 = AuraRenderMath.computeColorOrIndex(var81, arg1);
        int var474 = var82;
        double var333 = (0.4 * arg2);
        double var221 = (0.5 + var333);
        int var84 = ColorMathUtils.computeColorOrIndex(var44, var46, var221);
        int var85 = AuraRenderMath.computeColorOrIndex(var84, arg1);
        var41 = -1;
        while (true) {
            int var87 = var41;
            if (var87 > 1) break;
            int var93 = var41;
            double var94 = ((double) var93);
            double var96 = (var94 * 0.0135);
            double[] var166 = GeometryPrimitives.computeVector(-0.008, 0.028999999999999998, var96);
            double[] var230 = GeometryPrimitives.computeVector(0.008, 0.028999999999999998, var96);
            double[] var304 = GeometryPrimitives.computeVector(0.005, 0.41759999999999997, var96);
            double var446 = var96;
            double[] var341 = GeometryPrimitives.computeVector(-0.005, 0.41759999999999997, var446);
            int var448 = var474;
            arg0.applyStep(var166, var230, var304, var341, var85, var85, var474, var448);
            var41 = (var41 + 2);
        }
        double var452 = 0.005;
        int var462 = ColorMathUtils.computeColorOrIndex8(var51, 0.25);
        double var464 = arg1;
        int var463 = AuraRenderMath.computeColorOrIndex(var462, var464);
        int var466 = var51;
        int var467 = AuraRenderMath.computeColorOrIndex(var466, arg1);
        GeometryPrimitives.drawGeometryPart(arg0, -0.095, -0.018, -0.014, 0.095, var452, 0.014, var463, var467);
        int var90 = ColorMathUtils.computeColorOrIndex8(var44, 0.55);
        int var477 = var90;
        double[] var161 = GeometryPrimitives.computeVector(0.0, -0.018, 0.0);
        double[] var225 = GeometryPrimitives.computeVector(0.0, -0.11, 0.0);
        int var443 = var477;
        IGradientColorSampler var444 = (p0, p1) -> SwordsOrbitRenderer.lambda$b(var443, p0, p1);
        double var454 = arg1;
        IGradientColorSampler var445 = AuraRenderMath.evaluate(var444, var454);
        int var456 = 0;
        GeometryPrimitives.drawGeometryPart(arg0, var161, var225, 0.013, 0.013, 4, var445, var456);
        int var457 = 5;
        int var458 = 3;
        int var459 = var51;
        IGradientColorSampler var460 = (p0, p1) -> SwordsOrbitRenderer.lambda$a(var459, p0, p1);
        IGradientColorSampler var461 = AuraRenderMath.evaluate(var460, arg1);
        GeometryPrimitives.drawGeometryPart(arg0, 0.0, -0.128, 0.0, 0.022, var457, var458, var461);
    }

}
