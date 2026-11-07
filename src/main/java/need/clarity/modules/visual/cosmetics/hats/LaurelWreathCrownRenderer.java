package need.clarity.modules.visual.cosmetics.hats;

import java.util.function.DoubleUnaryOperator;
import need.clarity.modules.visual.cosmetics.ColorMathUtils;
import need.clarity.modules.visual.cosmetics.CosmeticDrawBuilder;
import need.clarity.modules.visual.cosmetics.GeometryPrimitives;
import need.clarity.modules.visual.cosmetics.ICosmeticEffectRenderer;
import need.clarity.modules.visual.cosmetics.IGradientColorSampler;

public final class LaurelWreathCrownRenderer implements ICosmeticEffectRenderer {
    static final double CONST_DBL = 0.268;
    static final double CONST_DBL1 = 0.05;
    static final int CONST_INT = 15;
    private static final double CONST_DBL2 = 6.283185307179586;
    private final HatRenderSpec hatRenderSpec;

    public LaurelWreathCrownRenderer() {
        HatRenderSpec var2 = HatRenderSpec.evaluate();
        this(var2);
    }

    public LaurelWreathCrownRenderer(HatRenderSpec arg0) {
        this.hatRenderSpec = arg0;
    }

    private static double[] computeVector(double arg0) {
        double var35 = (143.0 * arg0);
        double var18 = Math.toRadians((165.0 - var35));
        double var57 = Math.cos(var18);
        double var39 = (0.024 * var57);
        double var22 = (0.036 + var39);
        double var65 = Math.sin(((arg0 * 6.283185307179586) * 3.0));
        double var43 = (0.006 * var65);
        double var24 = (var22 + var43);
        double var73 = Math.sin(((arg0 * 6.283185307179586) * 2.5));
        double var47 = (0.008 * var73);
        double var28 = (0.268 + var47);
        double var51 = Math.sin(var18);
        double var32 = (var28 * var51);
        double var87 = Math.cos(var18);
        double var77 = (var28 * var87);
        return GeometryPrimitives.computeVector(var32, var24, var77);
    }

    public void renderEffect(CosmeticDrawBuilder arg0) {
        int var22 = 0;
        int var25 = 0;
        int var27 = 0;
        double var30 = 0.0;
        int var68 = 0;
        double var90 = 0.0;
        if (arg0.enabledState) {
            var30 = arg0.doubleValue;
        } else {
            var30 = 0.0;
        }
        double var18 = var30;
        int var33 = arg0.counterValue;
        int var35 = arg0.counterValue1;
        CosmeticDrawBuilder var37 = arg0.drawGeometryPart();
        if (this.hatRenderSpec.helmet()) {
            var90 = 0.05;
        } else {
            var90 = 0.0;
        }
        CosmeticDrawBuilder var39 = arg0.drawGeometryPart(0.0, var90, 0.0);
        var22 = 0;
        while (true) {
            if (var22 >= 2) break;
            if (var22 == 1) {
                var68 = 1;
            } else {
                var68 = 0;
            }
            CosmeticDrawBuilder var43 = arg0.drawGeometryPart(var68);
            int var10 = var22;
            this.drawGeometryPart(arg0, var18, var33, var35, var10);
            var22 = (var22 + 1);
        }
        CosmeticDrawBuilder var46 = arg0.drawGeometryPart(false);
        IGradientColorSampler var213 = (p0, p1) -> LaurelWreathCrownRenderer.lambda$c(var35, p0, p1);
        GeometryPrimitives.drawGeometryPart(arg0, 0.0, 0.028, -0.272, 0.022, 5, 3, var213);
        var25 = -1;
        while (true) {
            if (var25 > 1) break;
            double[][] var74 = new double[3][];
            double var127 = ((double) var25);
            double var99 = (0.006 * var127);
            double var131 = (0.028 - 0.005);
            double var170 = (-0.272 - 0.006);
            double[] var101 = GeometryPrimitives.computeVector(var99, var131, var170);
            var74[0] = var101;
            double var134 = ((double) var25);
            double var104 = (0.03 * var134);
            double var138 = (0.028 - 0.08);
            double var176 = (-0.272 - 0.012);
            double[] var106 = GeometryPrimitives.computeVector(var104, var138, var176);
            var74[1] = var106;
            double var141 = ((double) var25);
            double var109 = (0.022 * var141);
            double var145 = (0.028 - 0.2);
            double var182 = (-0.272 - 0.006);
            double[] var111 = GeometryPrimitives.computeVector(var109, var145, var182);
            var74[2] = var111;
            double[][] var57 = GeometryPrimitives.computeMatrix(6, var74);
            if (arg0.enabledState) {
                var27 = 1;
                while (true) {
                    int var61 = var27;
                    int var76 = var57.length;
                    if (var61 >= var76) break;
                    int var77 = var27;
                    double[] var63 = var57[var77];
                    double var82 = var63[0];
                    double var115 = (((double) var25) * 0.01);
                    double var152 = (((double) var27) / 6.0);
                    double var117 = (var115 * var152);
                    double var156 = (var18 * 1.7);
                    double var191 = (((double) var27) * 0.6);
                    double var158 = (var156 + var191);
                    double var194 = ((double) var25);
                    double var162 = Math.sin((var158 + var194));
                    double var119 = (var117 * var162);
                    double var84 = (var82 + var119);
                    var63[0] = var84;
                    var27 = (var27 + 1);
                }
                double[] var165 = new double[3];
                var165[0] = 1.0;
                var165[1] = 0.0;
                var165[2] = 0.0;
                IGradientColorSampler var167 = (p0, p1) -> LaurelWreathCrownRenderer.lambda$b(var35, p0, p1);
                GeometryPrimitives.drawGeometryPart(arg0, var57, 0.046, 0.034, var165, var167);
                var25 = (var25 + 2);
            }
        }
        CosmeticDrawBuilder var55 = arg0.drawGeometryPart1();
    }

    private static double[] computeVector(double[] arg0, double arg1) {
        double[] var12 = new double[3];
        double var15 = arg0[0];
        double var35 = Math.cos(arg1);
        double var17 = (var15 * var35);
        double var38 = arg0[2];
        double var52 = Math.sin(arg1);
        double var40 = (var38 * var52);
        double var19 = (var17 + var40);
        var12[0] = var19;
        double var22 = arg0[1];
        var12[1] = var22;
        double var27 = -(arg0[0]);
        double var44 = Math.sin(arg1);
        double var29 = (var27 * var44);
        double var47 = arg0[2];
        double var56 = Math.cos(arg1);
        double var49 = (var47 * var56);
        double var31 = (var29 + var49);
        var12[2] = var31;
        return var12;
    }

    private void drawGeometryPart(CosmeticDrawBuilder arg0, double arg1, int arg2, int arg3, int arg4) {
        int var24 = 0;
        int var26 = 0;
        double var542 = 0.0;
        double[] var42 = null;
        double[] var43 = null;
        double[] var49 = null;
        double var118 = 0.0;
        int var692 = 0;
        double var210 = 0.0;
        double var216 = 0.0;
        double var218 = 0.0;
        double[] var220 = null;
        double[][] var70 = new double[16][];
        var24 = 0;
        while (true) {
            int var72 = var24;
            if (var72 > 15) break;
            int var221 = var24;
            int var9 = var24;
            double var304 = ((double) var9);
            double var306 = (var304 / 15.0);
            double[] var308 = computeVector(var306);
            var70[var221] = var308;
            var24 = (var24 + 1);
        }
        int var75 = ColorMathUtils.computeColorOrIndex6(arg2, 0.5);
        DoubleUnaryOperator var309 = (p0) -> LaurelWreathCrownRenderer.lambda$b(p0);
        int var493 = var75;
        IGradientColorSampler var494 = (p0, p1) -> LaurelWreathCrownRenderer.lambda$a(var493, p0, p1);
        GeometryPrimitives.drawGeometryPart(arg0, var70, var309, 3, var494, false, false);
        double[] var78 = new double[3];
        double var386 = 0.0;
        var78[0] = var386;
        double var388 = 1.0;
        var78[1] = var388;
        double var390 = 0.0;
        var78[2] = var390;
        var26 = 0;
        while (true) {
            int var80 = var26;
            if (var80 >= 15) break;
            int var81 = var26;
            double var82 = ((double) var81);
            double var84 = (var82 + 0.5);
            double var86 = (var84 / 15.0);
            double[] var90 = computeVector(var86);
            double var495 = 0.03333333333333333;
            double var319 = (var86 + var495);
            double var95 = Math.min(1.0, var319);
            double[] var97 = computeVector(var95);
            double var392 = var86;
            double var394 = (var392 - 0.03333333333333333);
            double var228 = Math.max(0.0, var394);
            double[] var230 = computeVector(var228);
            double[] var98 = GeometryPrimitives.computeVector(var97, var230);
            double[] var99 = GeometryPrimitives.computeVector(var98);
            double var497 = var86;
            double var323 = (143.0 * var497);
            double var102 = (165.0 - var323);
            double var104 = Math.toRadians(var102);
            double[] var107 = new double[3];
            double var396 = var104;
            double var398 = Math.sin(var396);
            var107[0] = var398;
            double var400 = 0.0;
            var107[1] = var400;
            double var402 = var104;
            double var404 = Math.cos(var402);
            var107[2] = var404;
            double var499 = 3.141592653589793;
            double var501 = (var499 * var86);
            double var503 = Math.sin(var501);
            double var330 = (0.02 * var503);
            double var110 = (0.09 + var330);
            boolean var115 = arg0.enabledState;
            if (var115) {
                double var505 = 1.5;
                double var334 = (arg1 * var505);
                int var507 = var26;
                double var508 = ((double) var507);
                double var510 = (var508 * 0.9);
                double var336 = (var334 + var510);
                double var513 = ((double) arg4);
                double var515 = (var513 * 1.3);
                double var338 = (var336 + var515);
                double var340 = Math.sin(var338);
                var118 = (0.07 * var340);
            } else {
                var118 = 0.0;
            }
            double var40 = var118;
            double var233 = Math.toRadians(36.0);
            double var406 = var40;
            double var235 = (var233 + var406);
            double[] var121 = computeVector(var99, var235);
            var42 = var121;
            double var239 = Math.toRadians(-36.0);
            double var408 = var40;
            double var241 = (var239 + var408);
            double[] var123 = computeVector(var99, var241);
            var43 = var123;
            double[] var124 = var42;
            double var125 = GeometryPrimitives.computeDoubleMetric(var124, var107);
            double[] var342 = var43;
            double[] var410 = var107;
            double var343 = GeometryPrimitives.computeDoubleMetric(var342, var410);
            int var127 = Double.compare(var125, var343);
            if (var127 < 0) {
                double[] var128 = var42;
                double[] var129 = var43;
                var42 = var129;
                var43 = var128;
            }
            var692 = 0;
            while (true) {
                int var132 = var692;
                if (var132 >= 3) break;
                int var140 = var692;
                if (var140 == 2) {
                    int var141 = var26;
                    int var142 = (var141 % 2);
                    if (var142 == 1) {
                    }
                } else {
                    int var143 = var692;
                    if (var143 == 0) {
                        double[] var214 = var42;
                        Object var215 = var214.clone();
                        var220 = ((double[]) var215);
                    } else {
                        int var144 = var692;
                        if (var144 == 1) {
                            double[] var212 = var43;
                            Object var213 = var212.clone();
                            var220 = ((double[]) var213);
                        } else {
                            double var421 = 0.6;
                            double var258 = (var40 * var421);
                            var220 = computeVector(var99, var258);
                        }
                        double[] var45 = var220;
                        double var350 = var45[1];
                        int var529 = var692;
                        if (var529 == 2) {
                            var542 = 0.95;
                        } else {
                            var542 = 0.55;
                        }
                        double var352 = (var350 + var542);
                        var45[1] = var352;
                        double[] var148 = GeometryPrimitives.computeVector(var45);
                        int var149 = var692;
                        if (var149 == 2) {
                            var216 = 0.78;
                        } else {
                            var216 = 1.0;
                        }
                        double var46 = var216;
                        double[] var151 = GeometryPrimitives.computeVector1(var148, var78);
                        double[] var152 = GeometryPrimitives.computeVector(var151);
                        double[] var154 = GeometryPrimitives.computeVector1(var152, var148);
                        double[] var155 = GeometryPrimitives.computeVector(var154);
                        var49 = var155;
                        double[] var156 = var49;
                        double var157 = var156[1];
                        int var159 = Double.compare(var157, 0.0);
                        if (var159 < 0) {
                            double[] var161 = new double[3];
                            double[] var423 = var49;
                            double var424 = var423[0];
                            double var426 = -(var424);
                            var161[0] = var426;
                            double[] var428 = var49;
                            double var429 = var428[1];
                            double var431 = -(var429);
                            var161[1] = var431;
                            double[] var433 = var49;
                            double var434 = var433[2];
                            double var436 = -(var434);
                            var161[2] = var436;
                            var49 = var161;
                        }
                        int var162 = var692;
                        if (var162 == 0) {
                            var210 = 0.004;
                        } else {
                            int var163 = var692;
                            if (var163 == 1) {
                                var210 = -0.004;
                            } else {
                                var210 = 0.0;
                            }
                            double var50 = var210;
                            double var267 = var107[0];
                            double var438 = var50;
                            double var269 = (var267 * var438);
                            double var440 = 0.002;
                            double var557 = var107[2];
                            double var559 = (var557 * var50);
                            double[] var165 = GeometryPrimitives.computeVector(var90, var269, var440, var559);
                            double var360 = var46;
                            double var168 = (var110 * var360);
                            double var362 = var168;
                            double var172 = (0.42 * var362);
                            double var364 = var46;
                            double var176 = (0.043 * var364);
                            double var366 = 0.5;
                            double var178 = (var176 * var366);
                            double var368 = 0.043;
                            double var182 = (0.26 * var368);
                            double var370 = var46;
                            double var184 = (var182 * var370);
                            int var372 = 0;
                            double var272 = var148[var372];
                            double var442 = var172;
                            double var274 = (var272 * var442);
                            double[] var444 = var148;
                            double var445 = var444[1];
                            double var447 = (var445 * var172);
                            double var564 = var148[2];
                            double var566 = (var564 * var172);
                            double[] var187 = GeometryPrimitives.computeVector(var165, var274, var447, var566);
                            int var373 = 0;
                            double var277 = var152[var373];
                            double var449 = var178;
                            double var279 = (var277 * var449);
                            double[] var451 = var49;
                            double var452 = var451[0];
                            double var454 = (var452 * var184);
                            double var281 = (var279 + var454);
                            double[] var456 = var152;
                            double var457 = var456[1];
                            double var459 = (var457 * var178);
                            double[] var572 = var49;
                            double var573 = var572[1];
                            double var575 = (var573 * var184);
                            double var461 = (var459 + var575);
                            double var578 = var152[2];
                            double var580 = (var578 * var178);
                            double[] var654 = var49;
                            double var655 = var654[2];
                            double var657 = (var655 * var184);
                            double var582 = (var580 + var657);
                            double[] var189 = GeometryPrimitives.computeVector(var187, var281, var461, var582);
                            int var374 = 0;
                            double var284 = var152[var374];
                            double var286 = -(var284);
                            double var463 = var178;
                            double var288 = (var286 * var463);
                            double[] var465 = var49;
                            double var466 = var465[0];
                            double var468 = (var466 * var184);
                            double var290 = (var288 + var468);
                            double[] var470 = var152;
                            double var471 = var470[1];
                            double var473 = -(var471);
                            double var475 = (var473 * var178);
                            double[] var588 = var49;
                            double var589 = var588[1];
                            double var591 = (var589 * var184);
                            double var477 = (var475 + var591);
                            double var594 = var152[2];
                            double var596 = -(var594);
                            double var598 = (var596 * var178);
                            double[] var663 = var49;
                            double var664 = var663[2];
                            double var666 = (var664 * var184);
                            double var600 = (var598 + var666);
                            double[] var191 = GeometryPrimitives.computeVector(var187, var290, var477, var600);
                            int var375 = 0;
                            double var293 = var148[var375];
                            double var479 = var168;
                            double var295 = (var293 * var479);
                            double[] var481 = var148;
                            double var482 = var481[1];
                            double var484 = (var482 * var168);
                            double var605 = var148[2];
                            double var607 = (var605 * var168);
                            double[] var193 = GeometryPrimitives.computeVector(var165, var295, var484, var607);
                            double var195 = ((double) arg4);
                            double var376 = var86;
                            double var197 = (var195 + var376);
                            double var378 = 2.0;
                            double var199 = (var197 / var378);
                            int var380 = var692;
                            double var381 = ((double) var380);
                            double var383 = (var381 * 0.03);
                            double var201 = (var199 + var383);
                            double var486 = 0.05;
                            int var204 = CrownJewelGeometry.computeColorOrIndex(arg2, var201, var486, arg1, 0.1);
                            double var488 = 0.5;
                            int var206 = CrownJewelGeometry.computeColorOrIndex(arg2, var201, var488, arg1, 0.1);
                            double var490 = 0.95;
                            int var208 = CrownJewelGeometry.computeColorOrIndex(arg2, var201, var490, arg1, 0.1);
                            double[] var385 = var189;
                            double[] var492 = var193;
                            int var639 = ColorMathUtils.computeColorOrIndex6(var206, 0.86);
                            int var682 = ColorMathUtils.computeColorOrIndex6(var206, 1.12);
                            arg0.applyStep(var165, var385, var492, var191, var204, var639, var208, var682);
                        }
                    }
                }
                var692 = (var692 + 1);
            }
            int var133 = var26;
            int var134 = (var133 % 4);
            if (var134 == 3) {
                boolean var136 = arg0.enabledState;
                if (var136) {
                    double var519 = (arg1 * 2.0);
                    int var624 = var26;
                    double var625 = ((double) var624);
                    double var521 = (var519 + var625);
                    double var628 = ((double) arg4);
                    double var523 = (var521 + var628);
                    double var525 = Math.sin(var523);
                    double var347 = (0.1 * var525);
                    var218 = (0.9 + var347);
                } else {
                    var218 = 1.0;
                }
                double var693 = var218;
                double var248 = var90[0];
                double[] var411 = var107;
                double var412 = var411[0];
                double var414 = (var412 * 0.016);
                double var250 = (var248 - var414);
                double[] var416 = var90;
                double var417 = var416[1];
                double var419 = (var417 + 0.01);
                double var551 = var90[2];
                double var640 = var107[2];
                double var642 = (var640 * 0.016);
                double var553 = (var551 - var642);
                IGradientColorSampler var690 = (p0, p1) -> LaurelWreathCrownRenderer.lambda$a(arg3, var693, p0, p1);
                GeometryPrimitives.drawGeometryPart(arg0, var250, var419, var553, 0.0125, 5, 3, var690);
            }
            var26 = (var26 + 1);
        }
    }

}
