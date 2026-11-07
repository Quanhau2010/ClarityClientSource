package need.clarity.modules.visual.cosmetics.aura;

import need.clarity.modules.visual.cosmetics.ColorMathUtils;
import need.clarity.modules.visual.cosmetics.CosmeticDrawBuilder;
import need.clarity.modules.visual.cosmetics.GeometryPrimitives;
import need.clarity.modules.visual.cosmetics.ICosmeticEffectRenderer;
import need.clarity.modules.visual.cosmetics.IGradientColorSampler;

public final class FrostAuraRenderer implements ICosmeticEffectRenderer {
    public static final int CONST_INT = 12907007;
    public static final int CONST_INT1 = 4033791;
    public static final int CONST_INT2 = 432;
    private static final int CONST_INT3 = 9;
    private static final int CONST_INT4 = 7;
    private static final int CONST_INT5 = 20;
    private final ElementalAuraSpec elementalAuraSpec;

    public FrostAuraRenderer() {
        ElementalAuraSpec var2 = ElementalAuraSpec.evaluate();
        this(var2);
    }

    public FrostAuraRenderer(ElementalAuraSpec arg0) {
        this.elementalAuraSpec = arg0;
    }

    public void renderEffect(CosmeticDrawBuilder arg0) {
        int var21 = 0;
        int var72 = 0;
        double var42 = 0.0;
        if (arg0.enabledState) {
            var42 = arg0.doubleValue;
        } else {
            var42 = 0.0;
        }
        double var14 = var42;
        double var61 = this.elementalAuraSpec.radius();
        double var49 = (0.6 * var61);
        double var26 = (0.35 + var49);
        double var30 = this.elementalAuraSpec.bodyHeight();
        double[] var33 = arg0.computeVector1();
        this.drawGeometryPart(arg0, var26);
        var21 = 0;
        while (true) {
            if (var21 >= 9) break;
            int var11 = var21;
            this.drawGeometryPart(arg0, var33, var26, var30, var11, var14);
            var21 = (var21 + 1);
        }
        var72 = 0;
        while (true) {
            if (var72 >= 7) break;
            int var69 = var72;
            this.drawGeometryPart1(arg0, var33, var26, var30, var69, var14);
            var72 = (var72 + 1);
        }
        this.drawGeometryPart(arg0, var33, var26, var30, var14);
    }

    private void drawGeometryPart(CosmeticDrawBuilder arg0, double arg1) {
        int var17 = arg0.counterValue;
        int var19 = arg0.counterValue1;
        double var21 = (arg1 * 1.2);
        IGradientColorSampler var24 = (p0, p1) -> FrostAuraRenderer.lambda$d(var19, var17, p0, p1);
        GeometryPrimitives.drawGeometryPart(arg0, 0.02, 0.1, var21, 28, var24);
    }

    private void drawGeometryPart(CosmeticDrawBuilder arg0, double[] arg1, double arg2, double arg3, int arg4, double arg5) {
        int var123 = 0;
        double var124 = 0.0;
        double var214 = ((double) arg4);
        int var446 = (arg4 * 19);
        int var447 = (var446 + 1);
        double var448 = GeometryPrimitives.computeDoubleMetric(var447);
        double var450 = (var448 - 0.5);
        double var331 = (0.4 * var450);
        double var216 = (var214 + var331);
        double var56 = (6.283185307179586 * var216);
        double var58 = (var56 / 9.0);
        int var498 = 19;
        int var453 = (arg4 * var498);
        int var499 = 2;
        int var454 = (var453 + var499);
        double var455 = GeometryPrimitives.computeDoubleMetric(var454);
        double var335 = (0.2 * var455);
        double var222 = (0.9 + var335);
        double var62 = (arg2 * var222);
        int var500 = 19;
        int var458 = (arg4 * var500);
        int var501 = 3;
        int var459 = (var458 + var501);
        double var460 = GeometryPrimitives.computeDoubleMetric(var459);
        double var339 = (0.36 * var460);
        double var226 = (0.24 + var339);
        double var66 = (arg3 * var226);
        int var342 = (arg4 * 19);
        int var343 = (var342 + 4);
        double var344 = GeometryPrimitives.computeDoubleMetric(var343);
        double var230 = (0.32 * var344);
        double var70 = (0.1 + var230);
        int var347 = (arg4 * 19);
        int var348 = (var347 + 5);
        double var349 = GeometryPrimitives.computeDoubleMetric(var348);
        double var234 = (0.045 * var349);
        double var74 = (0.075 + var234);
        double var281 = Math.sin(var58);
        double var151 = (var62 * var281);
        double var285 = (var66 * 0.5);
        double var502 = var58;
        double var504 = Math.cos(var502);
        double var397 = (var62 * var504);
        CosmeticDrawBuilder var506 = arg0;
        double var507 = var506.computeDoubleMetric();
        double var77 = AuraRenderMath.computeDoubleMetric(arg1, var151, var285, var397, var507);
        int var81 = Double.compare(var77, 0.03);
        if (var81 <= 0) {
            return;
        }
        int var83 = arg0.counterValue1;
        int var84 = ColorMathUtils.computeColorOrIndex6(var83, 0.85);
        int var85 = ColorMathUtils.computeColorOrIndex5(var84, 0.78);
        int var87 = arg0.counterValue;
        int var88 = ColorMathUtils.computeColorOrIndex7(var87, 0.35);
        int var89 = ColorMathUtils.computeColorOrIndex5(var88, 0.86);
        int var91 = arg0.counterValue;
        int var92 = ColorMathUtils.computeColorOrIndex7(var91, 0.85);
        int var93 = ColorMathUtils.computeColorOrIndex5(var92, 0.94);
        IGradientColorSampler var95 = (p0, p1) -> FrostAuraRenderer.lambda$c(var85, var89, p0, p1);
        IGradientColorSampler var96 = AuraRenderMath.evaluate(var95, var77);
        IGradientColorSampler var98 = (p0, p1) -> FrostAuraRenderer.lambda$b(var89, var93, p0, p1);
        IGradientColorSampler var99 = AuraRenderMath.evaluate(var98, var77);
        CosmeticDrawBuilder var101 = arg0.drawGeometryPart();
        double var289 = Math.sin(var58);
        double var173 = (var62 * var289);
        double var509 = var58;
        double var511 = Math.cos(var509);
        double var401 = (var62 * var511);
        CosmeticDrawBuilder var103 = arg0.drawGeometryPart(var173, 0.0, var401);
        CosmeticDrawBuilder var105 = arg0.drawGeometryPart2(var58);
        CosmeticDrawBuilder var107 = arg0.drawGeometryPart3(var70);
        double var110 = (var66 * 0.72);
        GeometryPrimitives.drawGeometryPart(arg0, 0.0, var110, var74, 6, 0.5235987755982988, var96, false);
        double[] var183 = GeometryPrimitives.computeVector(0.0, var110, 0.0);
        double[] var242 = GeometryPrimitives.computeVector(0.0, var66, 0.0);
        GeometryPrimitives.drawGeometryPart(arg0, var183, var242, var74, 6, var99);
        IGradientColorSampler var115 = (p0, p1) -> FrostAuraRenderer.lambda$a(var85, var93, p0, p1);
        IGradientColorSampler var116 = AuraRenderMath.evaluate(var115, var77);
        double var189 = (var74 * 1.7);
        double var410 = (var74 * 0.4);
        double[] var191 = GeometryPrimitives.computeVector(var189, 0.0, var410);
        double var245 = (var74 * 3.0);
        double var357 = (var66 * 0.36);
        double var469 = (var74 * 1.5);
        double[] var247 = GeometryPrimitives.computeVector(var245, var357, var469);
        double var305 = (var74 * 0.75);
        GeometryPrimitives.drawGeometryPart(arg0, var191, var247, var305, 5, var116);
        double var194 = -(var74);
        double var196 = (var194 * 1.6);
        double var417 = -(var74);
        double var419 = (var417 * 0.5);
        double[] var198 = GeometryPrimitives.computeVector(var196, 0.0, var419);
        double var250 = -(var74);
        double var252 = (var250 * 2.6);
        double var363 = (var66 * 0.28);
        double var476 = -(var74);
        double var478 = (var476 * 1.9);
        double[] var254 = GeometryPrimitives.computeVector(var252, var363, var478);
        double var313 = (var74 * 0.65);
        GeometryPrimitives.drawGeometryPart(arg0, var198, var254, var313, 5, var116);
        boolean var120 = arg0.enabledState;
        if (var120) {
            double var273 = (arg5 * 2.3);
            double var386 = ((double) arg4);
            double var388 = (var386 * 2.1);
            double var275 = (var273 + var388);
            double var277 = Math.sin(var275);
            double var149 = Math.max(0.0, var277);
            var124 = Math.pow(var149, 10.0);
        } else {
            int var122 = (arg4 % 3);
            if (var122 == 0) {
                var123 = 1;
            } else {
                var123 = 0;
            }
            var124 = ((double) var123);
        }
        double var48 = var124;
        int var128 = Double.compare(var48, 0.05);
        if (var128 > 0) {
            double var259 = (0.07 * var48);
            double var131 = (0.05 + var259);
            double var135 = (var66 + 0.03);
            double var202 = (0.9 * var48);
            int var138 = ColorMathUtils.computeColorOrIndex5(-1, var202);
            int var139 = AuraRenderMath.computeColorOrIndex(var138, var77);
            int var141 = arg0.counterValue;
            int var142 = ColorMathUtils.computeColorOrIndex5(var141, 0.0);
            double var426 = (var131 * 1.9);
            double var319 = (var135 - var426);
            double[] var210 = GeometryPrimitives.computeVector(0.0, var319, 0.0);
            double var265 = (var131 * 0.5);
            double[] var267 = GeometryPrimitives.computeVector(var265, var135, 0.0);
            double var522 = (var131 * 1.9);
            double var432 = (var135 + var522);
            double[] var323 = GeometryPrimitives.computeVector(0.0, var432, 0.0);
            double var373 = -(var131);
            double var375 = (var373 * 0.5);
            double[] var377 = GeometryPrimitives.computeVector(var375, var135, 0.0);
            arg0.applyStep(var210, var267, var323, var377, var142, var139, var142, var139);
            double var437 = (var131 * 1.9);
            double var326 = (var135 - var437);
            double[] var213 = GeometryPrimitives.computeVector(0.0, var326, 0.0);
            double var490 = (var131 * 0.5);
            double[] var270 = GeometryPrimitives.computeVector(0.0, var135, var490);
            double var531 = (var131 * 1.9);
            double var443 = (var135 + var531);
            double[] var330 = GeometryPrimitives.computeVector(0.0, var443, 0.0);
            double var549 = -(var131);
            double var551 = (var549 * 0.5);
            double[] var382 = GeometryPrimitives.computeVector(0.0, var135, var551);
            arg0.applyStep(var213, var270, var330, var382, var142, var139, var142, var139);
        }
        CosmeticDrawBuilder var146 = arg0.drawGeometryPart1();
    }

    private void drawGeometryPart1(CosmeticDrawBuilder arg0, double[] arg1, double arg2, double arg3, int arg4, double arg5) {
        double var280 = 0.0;
        int var59 = 0;
        double var196 = 0.0;
        int var72 = 0;
        double var204 = 0.0;
        double var247 = 0.0;
        double var249 = 0.0;
        int var439 = (arg4 * 23);
        int var440 = (var439 + 1);
        double var441 = GeometryPrimitives.computeDoubleMetric(var440);
        double var373 = (0.6 * var441);
        double var251 = (0.7 + var373);
        double var75 = (0.1 * var251);
        if (arg0.enabledState) {
            double var200 = (arg5 * var75);
            double var336 = GeometryPrimitives.computeDoubleMetric(((arg4 * 23) + 2));
            double var202 = (var200 + var336);
            var204 = AuraRenderMath.computeDoubleMetric(var202);
        } else {
            var204 = ((((double) arg4) + 0.5) / 7.0);
        }
        double var33 = var204;
        double var86 = (arg3 + 0.3);
        double var261 = (1.0 - var33);
        double var92 = ((var86 * var261) - 0.05);
        if ((arg4 % 2) == 0) {
            var196 = 1.0;
        } else {
            var196 = -1.0;
        }
        double var39 = var196;
        double var268 = (((double) arg4) / 7.0);
        double var384 = (GeometryPrimitives.computeDoubleMetric(((arg4 * 23) + 3)) * 0.3);
        double var270 = (var268 + var384);
        double var98 = (6.283185307179586 * var270);
        if (arg0.enabledState) {
            double var278 = ((arg5 * 0.12) * var39);
            int var482 = (arg4 * 23);
            int var483 = (var482 + 4);
            double var484 = GeometryPrimitives.computeDoubleMetric(var483);
            double var447 = (0.8 * var484);
            double var392 = (0.6 + var447);
            var280 = (var278 * var392);
        } else {
            var280 = 0.0;
        }
        double var100 = (var98 + var280);
        int var450 = (arg4 * 23);
        int var451 = (var450 + 5);
        double var452 = GeometryPrimitives.computeDoubleMetric(var451);
        double var396 = (0.5 * var452);
        double var284 = (0.75 + var396);
        double var104 = (arg2 * var284);
        double var288 = Math.sin(var100);
        double var108 = (var104 * var288);
        double var292 = Math.cos(var100);
        double var112 = (var104 * var292);
        double var118 = GeometryPrimitives.computeDoubleMetric((var33 / 0.12));
        double var302 = GeometryPrimitives.computeDoubleMetric(((1.0 - var33) / 0.15));
        double var120 = (var118 * var302);
        double var458 = arg0.computeDoubleMetric();
        double var125 = (AuraRenderMath.computeDoubleMetric(arg1, var108, var92, var112, var458) * var120);
        if (Double.compare(var125, 0.03) <= 0) {
            return;
        }
        int var404 = ((arg4 * 23) + 6);
        double var405 = GeometryPrimitives.computeDoubleMetric(var404);
        double var310 = (0.09 * var405);
        double var132 = (0.17 + var310);
        int var138 = AuraRenderMath.computeColorOrIndex(ColorMathUtils.computeColorOrIndex5(ColorMathUtils.computeColorOrIndex7(arg0.counterValue, 0.65), 0.92), var125);
        int var141 = AuraRenderMath.computeColorOrIndex(ColorMathUtils.computeColorOrIndex5(-1, 0.95), var125);
        CosmeticDrawBuilder var143 = arg0.drawGeometryPart();
        CosmeticDrawBuilder var145 = arg0.drawGeometryPart(var108, var92, var112);
        boolean var221 = arg0.enabledState;
        if (var221) {
            double var243 = (arg5 * 1.3);
            double var245 = (var243 * var39);
            double var369 = ((double) arg4);
            var249 = (var245 + var369);
        } else {
            var249 = ((double) arg4);
        }
        CosmeticDrawBuilder var147 = arg0.drawGeometryPart2(var249);
        boolean var224 = arg0.enabledState;
        if (var224) {
            double var342 = (arg5 * 0.9);
            double var419 = ((double) arg4);
            double var421 = (var419 * 1.7);
            double var346 = Math.sin((var342 + var421));
            double var227 = (0.55 * var346);
            var247 = (var227 + 0.4);
        } else {
            var247 = 0.5;
        }
        CosmeticDrawBuilder var149 = arg0.drawGeometryPart3(var247);
        var59 = 0;
        while (true) {
            int var153 = var59;
            if (var153 >= 6) break;
            int var154 = var59;
            double var155 = ((double) var154);
            double var157 = (var155 * 3.141592653589793);
            double var159 = (var157 / 3.0);
            double var163 = Math.cos(var159);
            double var167 = Math.sin(var159);
            double var232 = (var163 * var132);
            double var234 = (var232 * 0.12);
            double var358 = ((var167 * var132) * 0.12);
            double var429 = (var163 * var132);
            double var466 = (var167 * var132);
            AuraRenderMath.drawGeometryPart(arg0, var234, var358, var429, var466, 0.026, 0.0, var138, var141);
            double var172 = (var163 * var132);
            double var174 = (var172 * 0.58);
            double var178 = (var167 * var132);
            double var180 = (var178 * 0.58);
            double var184 = (var132 * 0.32);
            var72 = -1;
            while (true) {
                int var187 = var72;
                if (var187 > 1) break;
                double var329 = (((double) var72) * 0.6);
                double var190 = (var159 + var329);
                double var470 = Math.cos(var190);
                double var472 = (var470 * var184);
                double var433 = (var174 + var472);
                double var495 = Math.sin(var190);
                double var497 = (var495 * var184);
                double var476 = (var180 + var497);
                double var501 = (0.026 * 0.8);
                AuraRenderMath.drawGeometryPart(arg0, var174, var180, var433, var476, var501, 0.0, var138, var141);
                var72 = (var72 + 2);
            }
            var59 = (var59 + 1);
        }
        double var437 = (var132 * 0.17);
        AuraRenderMath.drawGeometryPart(arg0, 0.0, 0.0, var437, 0.002, 6, 0.0, var141, var138);
        CosmeticDrawBuilder var195 = arg0.drawGeometryPart1();
    }

    private void drawGeometryPart(CosmeticDrawBuilder arg0, double[] arg1, double arg2, double arg3, double arg4) {
        double var135 = 0.0;
        double var137 = 0.0;
        int var23 = 0;
        double var186 = 0.0;
        double var237 = 0.0;
        var23 = 0;
        while (true) {
            if (var23 < 20) {
                double var260 = GeometryPrimitives.computeDoubleMetric(((var23 * 29) + 1));
                double var158 = (0.06 * var260);
                double var50 = (0.06 + var158);
                if (arg0.enabledState) {
                    double var56 = (arg4 * var50);
                    double var165 = GeometryPrimitives.computeDoubleMetric(((var23 * 29) + 2));
                    var135 = AuraRenderMath.computeDoubleMetric((var56 + var165));
                }
            } else {
                return;
                var135 = GeometryPrimitives.computeDoubleMetric(((var23 * 29) + 2));
            }
            double var26 = var135;
            double var170 = GeometryPrimitives.computeDoubleMetric(((var23 * 29) + 3));
            double var62 = (6.283185307179586 * var170);
            if (arg0.enabledState) {
                var237 = (arg4 * 0.08);
            } else {
                var237 = 0.0;
            }
            double var64 = (var62 + var237);
            double var319 = GeometryPrimitives.computeDoubleMetric(((var23 * 29) + 4));
            double var264 = (0.95 * var319);
            double var176 = (0.35 + var264);
            double var68 = (arg2 * var176);
            double var180 = Math.cos(var64);
            double var72 = (var68 * var180);
            if (arg0.enabledState) {
                double var268 = (arg4 * 0.8);
                double var324 = ((double) var23);
                double var272 = Math.sin((var268 + var324));
                var186 = (0.05 * var272);
            } else {
                var186 = 0.0;
            }
            double var74 = (var72 + var186);
            double var190 = Math.sin(var64);
            double var78 = (var68 * var190);
            double var82 = (arg3 + 0.3);
            double var196 = (1.0 - var26);
            double var84 = (var82 * var196);
            double var90 = GeometryPrimitives.computeDoubleMetric((var26 / 0.1));
            double var206 = GeometryPrimitives.computeDoubleMetric(((1.0 - var26) / 0.12));
            double var92 = (var90 * var206);
            double var96 = (0.9 * var92);
            double var351 = arg0.computeDoubleMetric();
            double var211 = AuraRenderMath.computeDoubleMetric(arg1, var74, var84, var78, var351);
            double var98 = (var96 * var211);
            if (arg0.enabledState) {
                double var299 = (GeometryPrimitives.computeDoubleMetric(((var23 * 29) + 5)) * 2.0);
                double var230 = (1.5 + var299);
                double var130 = (arg4 * var230);
                double var233 = ((double) var23);
                var137 = (var130 + var233);
            } else {
                var137 = ((double) var23);
            }
            double var42 = var137;
            double var330 = Math.abs(Math.sin(var42));
            double var282 = (0.75 * var330);
            double var215 = (0.25 + var282);
            double var105 = (var98 * var215);
            if (Double.compare(var105, 0.04) < 0) {
            } else {
                double var287 = GeometryPrimitives.computeDoubleMetric(((var23 * 29) + 6));
                double var221 = (0.014 * var287);
                double var112 = (0.018 + var221);
                CosmeticDrawBuilder var115 = arg0.drawGeometryPart();
                CosmeticDrawBuilder var117 = arg0.drawGeometryPart(var74, var84, var78);
                CosmeticDrawBuilder var119 = arg0.drawGeometryPart2(var42);
                double var145 = (var42 * 0.7);
                double var249 = ((double) var23);
                double var147 = (var145 + var249);
                CosmeticDrawBuilder var121 = arg0.drawGeometryPart3(var147);
                int var150 = arg0.counterValue;
                int var124 = ColorMathUtils.computeColorOrIndex5(ColorMathUtils.computeColorOrIndex(-1, var150, 0.4), var105);
                double[] var155 = GeometryPrimitives.computeVector(var112, 0.0, 0.0);
                double var334 = (var112 * 0.6);
                double[] var227 = GeometryPrimitives.computeVector(0.0, 0.0, var334);
                double[] var257 = GeometryPrimitives.computeVector(-(var112), 0.0, 0.0);
                double var349 = (-(var112) * 0.6);
                double[] var293 = GeometryPrimitives.computeVector(0.0, 0.0, var349);
                arg0.applyStep(var155, var227, var257, var293, var124);
                CosmeticDrawBuilder var127 = arg0.drawGeometryPart1();
            }
            var23 = (var23 + 1);
        }
    }

}
