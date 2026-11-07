package need.clarity.modules.visual.cosmetics.hats;

import java.util.function.DoubleUnaryOperator;
import need.clarity.modules.visual.cosmetics.ColorMathUtils;
import need.clarity.modules.visual.cosmetics.CosmeticDrawBuilder;
import need.clarity.modules.visual.cosmetics.GeometryPrimitives;
import need.clarity.modules.visual.cosmetics.ICosmeticEffectRenderer;
import need.clarity.modules.visual.cosmetics.IGradientColorSampler;

public final class FloatingCrownRenderer implements ICosmeticEffectRenderer {
    static final double CONST_DBL = 0.24;
    static final double CONST_DBL1 = 0.262;
    static final double CONST_DBL2 = 0.05;
    static final double CONST_DBL3 = 0.2;
    static final double CONST_DBL4 = 0.06;
    static final int CONST_INT = 28;
    static final int CONST_INT1 = 6;
    static final int CONST_INT2 = 6;
    private static final double CONST_DBL5 = 6.283185307179586;
    private final HatRenderSpec hatRenderSpec;

    public FloatingCrownRenderer() {
        HatRenderSpec var2 = HatRenderSpec.evaluate();
        this(var2);
    }

    public FloatingCrownRenderer(HatRenderSpec arg0) {
        this.hatRenderSpec = arg0;
    }

    public void renderEffect(CosmeticDrawBuilder arg0) {
        double var531 = 0.0;
        int var39 = 0;
        double var62 = 0.0;
        double var215 = 0.0;
        double var217 = 0.0;
        double var219 = 0.0;
        double var221 = 0.0;
        int var793 = 0;
        int var794 = 0;
        boolean var60 = arg0.enabledState;
        if (var60) {
            var62 = arg0.doubleValue;
        } else {
            var62 = 0.0;
        }
        double var27 = var62;
        int var65 = arg0.counterValue;
        int var67 = arg0.counterValue1;
        int var69 = ColorMathUtils.computeColorOrIndex7(var65, 0.55);
        int var71 = ColorMathUtils.computeColorOrIndex7(var67, 0.6);
        HatRenderSpec var73 = this.hatRenderSpec;
        boolean var74 = var73.helmet();
        if (var74) {
            var215 = 0.06;
        } else {
            var215 = 0.0;
        }
        double var33 = var215;
        boolean var76 = arg0.enabledState;
        if (var76) {
            HatRenderSpec var78 = this.hatRenderSpec;
            boolean var79 = var78.bob();
            if (var79) {
                double var315 = (var27 * 1.7);
                double var317 = Math.sin(var315);
                var221 = (0.018 * var317);
            } else {
            }
        }
        var221 = 0.0;
        double var35 = var221;
        double var319 = 0.2;
        double var465 = var27;
        double var467 = (var465 * 2.2);
        double var469 = Math.sin(var467);
        double var321 = (var319 * var469);
        double var84 = (0.8 + var321);
        CosmeticDrawBuilder var87 = arg0.drawGeometryPart();
        CosmeticDrawBuilder var89 = arg0.drawGeometryPart(0.0, var33, 0.0);
        double var386 = 0.2;
        double var388 = (var386 + var35);
        IGradientColorSampler var733 = (p0, p1) -> FloatingCrownRenderer.lambda$a(var27, var71, var67, var84, p0, p1);
        int var779 = 0;
        GeometryPrimitives.drawGeometryPart(arg0, 0.01, var388, 0.285, 0.235, 24, var733, false, var779);
        double var390 = 0.27;
        IGradientColorSampler var596 = (p0, p1) -> FloatingCrownRenderer.lambda$b(var67, var71, var84, p0, p1);
        GeometryPrimitives.drawGeometryPart(arg0, 0.008, var390, 24, var596);
        var39 = 0;
        while (true) {
            int var93 = var39;
            if (var93 >= 2) break;
            double var323 = 0.45;
            double var96 = (var27 * var323);
            int var325 = var39;
            double var326 = ((double) var325);
            double var471 = 0.5;
            double var328 = (var326 * var471);
            double var98 = (var96 + var328);
            double var100 = computeDoubleMetric(var98);
            double var330 = 0.1;
            double var473 = var100;
            double var332 = (var330 * var473);
            double var104 = (0.28200000000000003 + var332);
            double var334 = var100;
            double var108 = (1.0 - var334);
            double var336 = 1.0;
            double var475 = var100;
            double var338 = (var336 - var475);
            double var110 = (var108 * var338);
            double var340 = var100;
            double var477 = 8.0;
            double var342 = (var340 * var477);
            double var344 = GeometryPrimitives.computeDoubleMetric(var342);
            double var112 = (var110 * var344);
            double var392 = var35;
            double var234 = (0.2 + var392);
            double var394 = 0.006;
            double var236 = (var234 - var394);
            double var396 = var104;
            double var546 = (var104 + 0.03);
            double var723 = var112;
            IGradientColorSampler var665 = (p0, p1) -> FloatingCrownRenderer.lambda$d(var71, var723, p0, p1);
            GeometryPrimitives.drawGeometryPart(arg0, var236, var396, var546, 28, var665);
            var39 = (var39 + 1);
        }
        boolean var116 = arg0.enabledState;
        if (var116) {
            var217 = 1.0;
        } else {
            var217 = 0.0;
        }
        double var791 = var217;
        CosmeticDrawBuilder var118 = arg0.drawGeometryPart();
        double var398 = 0.2;
        double var400 = (var398 + var35);
        CosmeticDrawBuilder var120 = arg0.drawGeometryPart(0.0, var400, 0.0);
        double var402 = var791;
        double var242 = (0.045 * var402);
        double var404 = var27;
        double var406 = (var404 * 1.3);
        double var408 = Math.sin(var406);
        double var244 = (var242 * var408);
        CosmeticDrawBuilder var122 = arg0.drawGeometryPart3(var244);
        double var410 = var791;
        double var248 = (0.045 * var410);
        double var412 = var27;
        double var414 = (var412 * 1.1);
        double var416 = Math.cos(var414);
        double var250 = (var248 * var416);
        CosmeticDrawBuilder var124 = arg0.drawGeometryPart4(var250);
        FloatingCrownRenderer var418 = this;
        HatRenderSpec var419 = var418.hatRenderSpec;
        double var420 = var419.spin();
        double var254 = (var27 * var420);
        double var422 = 6.283185307179586;
        double var256 = (var254 * var422);
        CosmeticDrawBuilder var126 = arg0.drawGeometryPart2(var256);
        double var424 = 0.05;
        int var725 = 28;
        int var734 = var65;
        double var774 = var27;
        IGradientColorSampler var735 = (p0, p1) -> FloatingCrownRenderer.lambda$c(var734, var774, p0, p1);
        int var776 = 0;
        int var780 = 0;
        GeometryPrimitives.drawGeometryPart(arg0, 0.0, var424, 0.24, 0.262, var725, var735, var776, var780);
        IGradientColorSampler var129 = (p0, p1) -> FloatingCrownRenderer.lambda$b(var65, var27, p0, p1);
        double var426 = 0.011;
        int var726 = 28;
        IGradientColorSampler var736 = var129;
        int var777 = 0;
        int var781 = 0;
        GeometryPrimitives.drawGeometryPart(arg0, 0.0, var426, 0.243, 0.251, var726, var736, var777, var781);
        double var428 = 0.05;
        int var727 = 28;
        IGradientColorSampler var737 = var129;
        int var778 = 0;
        int var782 = 0;
        GeometryPrimitives.drawGeometryPart(arg0, 0.03900000000000001, var428, 0.254, 0.266, var727, var737, var778, var782);
        var793 = 0;
        while (true) {
            int var133 = var793;
            if (var133 >= 6) break;
            int var346 = var793;
            double var347 = ((double) var346);
            double var479 = 6.283185307179586;
            double var349 = (var347 * var479);
            double var481 = 6.0;
            double var351 = (var349 / var481);
            double var136 = (1.5707963267948966 + var351);
            int var138 = var793;
            int var139 = (var138 % 2);
            if (var139 == 0) {
                var219 = 0.235;
            } else {
                var219 = 0.155;
            }
            double var49 = var219;
            double var142 = Math.cos(var136);
            double var146 = Math.sin(var136);
            double[][] var269 = new double[4][];
            int var430 = 0;
            double var483 = 0.252;
            double var485 = (var483 * var142);
            double var601 = (0.05 - 0.004);
            double var738 = var146;
            double var670 = (0.252 * var738);
            double[] var487 = GeometryPrimitives.computeVector(var485, var601, var670);
            var269[var430] = var487;
            int var431 = 1;
            double var488 = 0.276;
            double var490 = (var488 * var142);
            double var740 = var49;
            double var674 = (0.4 * var740);
            double var607 = (0.05 + var674);
            double var742 = var146;
            double var678 = (0.276 * var742);
            double[] var492 = GeometryPrimitives.computeVector(var490, var607, var678);
            var269[var431] = var492;
            int var432 = 2;
            double var493 = 0.258;
            double var495 = (var493 * var142);
            double var744 = var49;
            double var682 = (0.75 * var744);
            double var613 = (0.05 + var682);
            double var746 = var146;
            double var686 = (0.258 * var746);
            double[] var497 = GeometryPrimitives.computeVector(var495, var613, var686);
            var269[var432] = var497;
            int var433 = 3;
            double var498 = 0.262;
            double var617 = (0.07 * var49);
            double var619 = (var617 / 0.235);
            double var500 = (var498 - var619);
            double var502 = (var500 * var142);
            double var625 = (0.05 + var49);
            double var748 = 0.07;
            double var783 = var49;
            double var750 = (var748 * var783);
            double var785 = 0.235;
            double var752 = (var750 / var785);
            double var696 = (0.262 - var752);
            double var754 = var146;
            double var698 = (var696 * var754);
            double[] var504 = GeometryPrimitives.computeVector(var502, var625, var698);
            var269[var433] = var504;
            double[][] var151 = GeometryPrimitives.computeMatrix(6, var269);
            int var152 = var793;
            double var353 = var146;
            double var355 = -(var353);
            double var505 = 0.0;
            double[] var357 = GeometryPrimitives.computeVector(var355, var505, var142);
            DoubleUnaryOperator var434 = (p0) -> FloatingCrownRenderer.lambda$c(p0);
            DoubleUnaryOperator var507 = (p0) -> FloatingCrownRenderer.lambda$b(p0);
            IGradientColorSampler var630 = (p0, p1) -> FloatingCrownRenderer.lambda$a(var65, var152, var27, p0, p1);
            GeometryPrimitives.drawGeometryPart(arg0, var151, var357, var434, var507, 4, var630);
            double var435 = var136;
            CrownJewelGeometry.drawGeometryPart(arg0, 0.264, var435, 0.026);
            double var437 = 0.015;
            int var728 = var69;
            int var756 = var793;
            double var757 = ((double) var756);
            double var787 = 1.9;
            double var759 = (var757 * var787);
            CrownJewelGeometry.drawGeometryPart(arg0, 0.021, var437, 0.008, 4, var67, var728, var759);
            CosmeticDrawBuilder var157 = arg0.drawGeometryPart1();
            int var158 = var793;
            int var275 = 2;
            int var159 = (var158 % var275);
            if (var159 == 0) {
                int var276 = 6;
                double[] var161 = var151[var276];
                double[] var277 = var161;
                double var358 = 0.0;
                double var508 = 0.006;
                double[] var278 = GeometryPrimitives.computeVector(var277, var358, var508, 0.0);
                double var360 = 0.07;
                int var510 = var71;
                int var703 = var793;
                double var704 = ((double) var703);
                double var761 = 0.17;
                double var706 = (var704 * var761);
                double var568 = CrownJewelGeometry.drawGeometryPart(arg0, 3.4, var706);
                CrownJewelGeometry.drawGeometryPart(arg0, var278, var360, var510, var568);
            }
            var793 = (var793 + 1);
        }
        var794 = 0;
        while (true) {
            int var164 = var794;
            int var279 = 6;
            if (var164 >= var279) break;
            int var362 = var794;
            double var363 = ((double) var362);
            double var511 = 0.5;
            double var365 = (var363 + var511);
            double var367 = (var365 * 6.283185307179586);
            double var369 = (var367 / 6.0);
            double var167 = (1.5707963267948966 + var369);
            double var797 = var167;
            double var371 = 0.018;
            double var519 = (var27 * 1.9);
            int var637 = var794;
            double var638 = ((double) var637);
            double var640 = (var638 * 1.3);
            double var521 = (var519 + var640);
            double var523 = Math.sin(var521);
            double var373 = (var371 * var523);
            double var171 = (0.175 + var373);
            double var799 = var171;
            double var375 = var797;
            double[] var175 = CrownJewelGeometry.computeVector(0.31, var375, var799);
            double[] var801 = var175;
            CosmeticDrawBuilder var177 = arg0.drawGeometryPart();
            double[] var280 = var801;
            int var377 = 0;
            double var281 = var280[var377];
            double[] var439 = var801;
            double var440 = var439[1];
            double var571 = var801[2];
            CosmeticDrawBuilder var179 = arg0.drawGeometryPart(var281, var440, var571);
            double var283 = var27;
            double var442 = 1.5;
            double var285 = (var283 * var442);
            int var444 = var794;
            double var445 = ((double) var444);
            double var287 = (var285 + var445);
            CosmeticDrawBuilder var181 = arg0.drawGeometryPart2(var287);
            double var289 = 0.024;
            double var447 = 0.05;
            int var729 = var69;
            int var763 = var794;
            double var764 = ((double) var763);
            double var789 = 2.3;
            double var766 = (var764 * var789);
            CrownJewelGeometry.drawGeometryPart(arg0, var289, var447, 0.04, 4, var67, var729, var766);
            CosmeticDrawBuilder var184 = arg0.drawGeometryPart1();
            double[] var291 = var801;
            double var378 = 0.055;
            int var711 = var794;
            double var712 = ((double) var711);
            double var768 = 0.21;
            double var714 = (var712 * var768);
            double var770 = 0.4;
            double var716 = (var714 + var770);
            double var576 = CrownJewelGeometry.drawGeometryPart(arg0, 2.7, var716);
            CrownJewelGeometry.drawGeometryPart(arg0, var291, var378, var71, var576);
            var794 = (var794 + 1);
        }
        double var380 = 0.02;
        boolean var530 = arg0.enabledState;
        if (var530) {
            double var537 = (var27 * 1.4);
            var531 = Math.sin(var537);
        } else {
            var531 = 0.0;
        }
        double var382 = (var380 * var531);
        double var188 = (0.2 + var382);
        double var795 = var188;
        CosmeticDrawBuilder var191 = arg0.drawGeometryPart();
        double var292 = 0.0;
        double var449 = var795;
        CosmeticDrawBuilder var193 = arg0.drawGeometryPart(var292, var449, 0.0);
        double var294 = var27;
        double var296 = -(var294);
        double var451 = 0.9;
        double var298 = (var296 * var451);
        CosmeticDrawBuilder var195 = arg0.drawGeometryPart2(var298);
        double var300 = 0.052;
        double var453 = 0.072;
        int var730 = var69;
        double var772 = 9.0;
        CrownJewelGeometry.drawGeometryPart(arg0, var300, var453, 0.062, 6, var67, var730, var772);
        CosmeticDrawBuilder var198 = arg0.drawGeometryPart1();
        double var302 = 0.02;
        double var455 = var795;
        double var457 = (var455 + 0.03);
        double[] var304 = GeometryPrimitives.computeVector(var302, var457, 0.03);
        double var384 = 0.11;
        int var534 = ColorMathUtils.computeColorOrIndex7(var67, 0.7);
        double var589 = CrownJewelGeometry.drawGeometryPart(arg0, 2.9, 0.05);
        CrownJewelGeometry.drawGeometryPart(arg0, var304, var384, var534, var589);
        CosmeticDrawBuilder var201 = arg0.drawGeometryPart();
        double var305 = 0.0;
        double var459 = var795;
        CosmeticDrawBuilder var203 = arg0.drawGeometryPart(var305, var459, 0.0);
        double var307 = 0.9;
        CosmeticDrawBuilder var205 = arg0.drawGeometryPart3(var307);
        double var309 = var27;
        double var461 = 0.8;
        double var311 = (var309 * var461);
        CosmeticDrawBuilder var207 = arg0.drawGeometryPart2(var311);
        double var313 = 0.0;
        double var463 = 0.088;
        double var731 = var27;
        IGradientColorSampler var722 = (p0, p1) -> FloatingCrownRenderer.lambda$a(var65, var731, p0, p1);
        GeometryPrimitives.drawGeometryPart(arg0, var313, var463, 0.097, 20, var722);
        CosmeticDrawBuilder var210 = arg0.drawGeometryPart1();
        CosmeticDrawBuilder var212 = arg0.drawGeometryPart1();
        CosmeticDrawBuilder var214 = arg0.drawGeometryPart1();
    }

    private static double computeDoubleMetric(double arg0) {
        double var8 = Math.floor(arg0);
        return (arg0 - var8);
    }

}
