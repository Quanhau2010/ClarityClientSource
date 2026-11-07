package need.clarity.modules.visual.cosmetics.targetesp;

import need.clarity.modules.visual.cosmetics.ColorMathUtils;
import need.clarity.modules.visual.cosmetics.CosmeticDrawBuilder;
import need.clarity.modules.visual.cosmetics.GeometryPrimitives;
import need.clarity.modules.visual.cosmetics.ICosmeticEffectRenderer;

public final class CornerBracketsTargetRenderer implements ICosmeticEffectRenderer {
    private static final double CONST_DBL = 2.4;
    private static final double CONST_DBL1 = 0.026;
    private static final double CONST_DBL2 = 0.062;
    private static final double CONST_DBL3 = 0.052;
    private final TargetEspRenderSpec targetEspRenderSpec;

    public CornerBracketsTargetRenderer() {
        TargetEspRenderSpec var2 = TargetEspRenderSpec.evaluate();
        this(var2);
    }

    public CornerBracketsTargetRenderer(TargetEspRenderSpec arg0) {
        this.targetEspRenderSpec = arg0;
    }

    public void renderEffect(CosmeticDrawBuilder arg0) {
        int var58 = 0;
        int var59 = 0;
        int var60 = 0;
        int var61 = 0;
        int var75 = 0;
        double var79 = 0.0;
        double var225 = 0.0;
        double var227 = 0.0;
        boolean var77 = arg0.enabledState;
        if (var77) {
            var79 = arg0.doubleValue;
        } else {
            var79 = 0.0;
        }
        double var25 = var79;
        TargetEspRenderSpec var82 = this.targetEspRenderSpec;
        double var83 = var82.age();
        TargetEspRenderSpec var86 = this.targetEspRenderSpec;
        double var87 = var86.computeDoubleMetric1();
        boolean var90 = arg0.enabledState;
        if (var90) {
            double var467 = 2.4;
            double var359 = (var25 / var467);
            double var361 = TargetEspMathUtils.computeDoubleMetric(var359);
            double var223 = (-7.0 * var361);
            var227 = Math.exp(var223);
        } else {
            var227 = 0.0;
        }
        double var31 = var227;
        boolean var92 = arg0.enabledState;
        if (var92) {
            double var465 = 1.9;
            double var353 = (var25 * var465);
            double var355 = Math.sin(var353);
            var225 = (0.01 * var355);
        } else {
            var225 = 0.0;
        }
        double var33 = var225;
        double var288 = (0.075 * var31);
        double var95 = (var33 + var288);
        double var431 = var83;
        double var292 = (1.0 - var431);
        double var433 = 0.55;
        double var294 = (var292 * var433);
        double var97 = (var95 + var294);
        TargetEspRenderSpec var100 = this.targetEspRenderSpec;
        double var101 = var100.width();
        double var103 = (var101 + var97);
        TargetEspRenderSpec var106 = this.targetEspRenderSpec;
        double var107 = var106.height();
        double var109 = (var107 + var97);
        TargetEspRenderSpec var112 = this.targetEspRenderSpec;
        double var113 = var112.width();
        double var115 = (var113 + var97);
        double var119 = (0.72 * var103);
        double var435 = 0.7;
        double var437 = (var435 * var87);
        double var121 = computeDoubleMetric(var119, 0.14, var437);
        double var125 = (0.72 * var115);
        double var439 = 0.7;
        double var441 = (var439 * var87);
        double var127 = computeDoubleMetric(var125, 0.14, var441);
        double var131 = (0.6 * var109);
        double var443 = 0.7;
        double var445 = (var443 * var87);
        double var133 = computeDoubleMetric(var131, 0.16, var445);
        double var137 = (0.026 * var87);
        double var141 = (0.062 * var87);
        double var145 = (0.052 * var87);
        double[] var148 = arg0.computeVector1();
        double var150 = arg0.computeDoubleMetric();
        var58 = 0;
        var59 = -1;
        while (true) {
            int var154 = var59;
            if (var154 > 1) break;
            var60 = -1;
            while (true) {
                int var156 = var60;
                if (var156 <= 1) {
                    var61 = -1;
                    while (true) {
                        int var158 = var61;
                        if (var158 <= 1) {
                            int var159 = var59;
                            double var160 = ((double) var159);
                            double var162 = (var160 * var103);
                            int var322 = var60;
                            double var323 = ((double) var322);
                            double var447 = var109;
                            double var325 = (var323 * var447);
                            int var449 = var61;
                            double var450 = ((double) var449);
                            double var452 = (var450 * var115);
                            double[] var164 = GeometryPrimitives.computeVector(var162, var325, var452);
                            CosmeticDrawBuilder var454 = arg0;
                            int var13 = var58;
                            double var530 = ((double) var13);
                            double var532 = (var530 * 0.9);
                            double var455 = TargetEspMathUtils.drawGeometryPart(var454, 2.3, var532);
                            double var329 = (0.2 * var455);
                            double var167 = (0.8 + var329);
                            double var457 = var31;
                            double var333 = (0.3 * var457);
                            double var169 = (var167 + var333);
                            int var459 = 0;
                            double var363 = var164[var459];
                            double[] var469 = var164;
                            double var470 = var469[1];
                            double var535 = var164[2];
                            double var336 = TargetEspMathUtils.computeDoubleMetric(var148, var363, var470, var535, var150);
                            double var173 = (var83 * var336);
                            var58 = (var58 + 1);
                            int var177 = Double.compare(var173, 0.01);
                            if (var177 < 0) {
                            }
                        }
                    }
                } else {
                    var59 = (var59 + 2);
                }
            }
        }
        var60 = (var60 + 2);
        int var179 = arg0.counterValue;
        double var472 = 0.45;
        double var474 = (var472 * var31);
        double var367 = (0.3 + var474);
        double var233 = Math.min(1.0, var367);
        int var180 = ColorMathUtils.computeColorOrIndex7(var179, var233);
        int var182 = arg0.counterValue;
        int var183 = ColorMathUtils.computeColorOrIndex(var182, var180, 0.5);
        double var476 = var169;
        double var371 = (0.95 * var476);
        double var238 = Math.min(1.0, var371);
        double var240 = (var238 * var173);
        int var184 = ColorMathUtils.computeColorOrIndex5(var183, var240);
        int var186 = arg0.counterValue1;
        double var244 = (0.6 * var173);
        int var187 = ColorMathUtils.computeColorOrIndex5(var186, var244);
        int var189 = arg0.counterValue;
        double var248 = (0.3 * var169);
        double var250 = (var248 * var173);
        int var190 = ColorMathUtils.computeColorOrIndex5(var189, var250);
        int var192 = arg0.counterValue1;
        double var254 = (0.05 * var173);
        int var193 = ColorMathUtils.computeColorOrIndex5(var192, var254);
        double[][] var195 = new double[3][];
        int var460 = 0;
        double var384 = var164[var460];
        int var478 = var59;
        double var479 = ((double) var478);
        double var481 = (var479 * var121);
        double var386 = (var384 - var481);
        double[] var483 = var164;
        double var484 = var483[1];
        double var542 = var164[2];
        double[] var388 = GeometryPrimitives.computeVector(var386, var484, var542);
        var195[0] = var388;
        int var461 = 0;
        double var390 = var164[var461];
        double[] var486 = var164;
        double var487 = var486[1];
        int var544 = var60;
        double var545 = ((double) var544);
        double var547 = (var545 * var133);
        double var489 = (var487 - var547);
        double var550 = var164[2];
        double[] var392 = GeometryPrimitives.computeVector(var390, var489, var550);
        var195[1] = var392;
        int var462 = 0;
        double var394 = var164[var462];
        double[] var491 = var164;
        double var492 = var491[1];
        double var553 = var164[2];
        int var582 = var61;
        double var583 = ((double) var582);
        double var585 = (var583 * var127);
        double var555 = (var553 - var585);
        double[] var396 = GeometryPrimitives.computeVector(var394, var492, var555);
        var195[2] = var396;
        int var198 = var195.length;
        var75 = 0;
        while (true) {
            int var200 = var75;
            if (var200 >= var198) break;
            int var285 = var75;
            double[] var216 = var195[var285];
            TargetEspMathUtils.drawGeometryPart(arg0, var164, var216, var137, var184, var187, true);
            TargetEspMathUtils.drawGeometryPart(arg0, var164, var216, var141, var190, var193, true);
            var75 = (var75 + 1);
        }
        int var202 = arg0.counterValue;
        double var494 = var31;
        double var399 = (0.3 * var494);
        double var259 = (0.6 + var399);
        int var203 = ColorMathUtils.computeColorOrIndex7(var202, var259);
        double var496 = var169;
        double var403 = (0.95 * var496);
        double var263 = Math.min(1.0, var403);
        double var265 = (var263 * var173);
        int var204 = ColorMathUtils.computeColorOrIndex5(var203, var265);
        double var268 = var164[0];
        double var270 = (var268 - var145);
        int var463 = 1;
        double var410 = var164[var463];
        double var498 = var145;
        double var412 = (var410 - var498);
        double[] var500 = var164;
        double var501 = var500[2];
        double var503 = (var501 - var145);
        double var560 = var164[0];
        double var562 = (var560 + var145);
        double var590 = var164[1];
        double var592 = (var590 + var145);
        double var605 = var164[2];
        double var607 = (var605 + var145);
        int var618 = ColorMathUtils.computeColorOrIndex6(var204, 0.85);
        GeometryPrimitives.drawGeometryPart(arg0, var270, var412, var503, var562, var592, var607, var618, var204);
        int var207 = arg0.counterValue;
        int var208 = ColorMathUtils.computeColorOrIndex7(var207, 0.3);
        double var505 = var31;
        double var416 = (0.25 * var505);
        double var276 = (0.18 + var416);
        double var278 = (var276 * var173);
        int var209 = ColorMathUtils.computeColorOrIndex5(var208, var278);
        double var212 = (var145 * 1.9);
        double var281 = var164[0];
        double var283 = (var281 - var212);
        int var464 = 1;
        double var423 = var164[var464];
        double var507 = var212;
        double var425 = (var423 - var507);
        double[] var509 = var164;
        double var510 = var509[2];
        double var512 = (var510 - var212);
        double var567 = var164[0];
        double var569 = (var567 + var212);
        double var597 = var164[1];
        double var599 = (var597 + var212);
        double var612 = var164[2];
        double var614 = (var612 + var212);
        GeometryPrimitives.drawGeometryPart(arg0, var283, var425, var512, var569, var599, var614, var209, var209);
        var61 = (var61 + 2);
    }

    private static double computeDoubleMetric(double arg0, double arg1, double arg2) {
        double var14 = 0.0;
        if (Double.compare(arg0, arg1) < 0) {
            var14 = arg1;
        } else {
            if (Double.compare(arg0, arg2) > 0) {
                var14 = arg2;
            } else {
                var14 = arg0;
            }
            return var14;
        }
    }

}
