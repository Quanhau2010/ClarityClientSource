package need.clarity.modules.visual.cosmetics.hats;

import java.util.function.DoubleUnaryOperator;
import need.clarity.modules.visual.cosmetics.ColorMathUtils;
import need.clarity.modules.visual.cosmetics.CosmeticDrawBuilder;
import need.clarity.modules.visual.cosmetics.GeometryPrimitives;
import need.clarity.modules.visual.cosmetics.ICosmeticEffectRenderer;
import need.clarity.modules.visual.cosmetics.IGradientColorSampler;

public final class TiaraCrownRenderer implements ICosmeticEffectRenderer {
    static final double CONST_DBL = 0.262;
    static final double CONST_DBL1;
    static final double CONST_DBL2 = 0.05;
    static final int CONST_INT = 34;
    private static final double CONST_DBL3 = 6.283185307179586;
    private final HatRenderSpec hatRenderSpec;

    public TiaraCrownRenderer() {
        HatRenderSpec var2 = HatRenderSpec.evaluate();
        this(var2);
    }

    public TiaraCrownRenderer(HatRenderSpec arg0) {
        this.hatRenderSpec = arg0;
    }

    private static double computeDoubleMetric(double arg0) {
        double var22 = (Math.abs(arg0) / 1.5);
        double var18 = GeometryPrimitives.computeDoubleMetric((1.0 - var22));
        double var14 = (0.056 * var18);
        return (0.03 + var14);
    }

    private static double[] computeVector(double arg0, double arg1, double arg2) {
        double var16 = (0.262 + arg2);
        double var25 = Math.sin(arg0);
        double var20 = (var16 * var25);
        double var31 = Math.cos(arg0);
        double var29 = (var16 * var31);
        return GeometryPrimitives.computeVector(var20, arg1, var29);
    }

    public void renderEffect(CosmeticDrawBuilder arg0) {
        int var36 = 0;
        int var41 = 0;
        int var43 = 0;
        int var44 = 0;
        double var192 = 0.0;
        double var742 = 0.0;
        int var783 = 0;
        int var788 = 0;
        int var800 = 0;
        double var344 = 0.0;
        boolean var56 = arg0.enabledState;
        if (var56) {
            var192 = arg0.doubleValue;
        } else {
            var192 = 0.0;
        }
        double var31 = var192;
        int var58 = arg0.counterValue;
        int var60 = arg0.counterValue1;
        int var62 = ColorMathUtils.computeColorOrIndex7(var58, 0.55);
        CosmeticDrawBuilder var64 = arg0.drawGeometryPart();
        HatRenderSpec var342 = this.hatRenderSpec;
        boolean var343 = var342.helmet();
        if (var343) {
            var344 = 0.05;
        } else {
            var344 = 0.0;
        }
        CosmeticDrawBuilder var66 = arg0.drawGeometryPart(0.0, var344, 0.0);
        var36 = 0;
        while (true) {
            int var68 = var36;
            if (var68 >= 34) break;
            int var158 = var36;
            double var159 = ((double) var158);
            double var161 = (var159 / 34.0);
            int var163 = var36;
            int var164 = (var163 + 1);
            double var165 = ((double) var164);
            double var167 = (var165 / 34.0);
            double var781 = var167;
            double var169 = CONST_DBL1;
            double var171 = -(var169);
            double var461 = CONST_DBL1;
            double var326 = (2.0 * var461);
            double var463 = var161;
            double var328 = (var326 * var463);
            double var173 = (var171 + var328);
            double var786 = var173;
            double var175 = CONST_DBL1;
            double var177 = -(var175);
            double var465 = CONST_DBL1;
            double var332 = (2.0 * var465);
            double var467 = var781;
            double var334 = (var332 * var467);
            double var179 = (var177 + var334);
            double var793 = var179;
            double var183 = computeDoubleMetric(var786);
            double var798 = var183;
            double var187 = computeDoubleMetric(var793);
            double var803 = var187;
            double var403 = 0.0;
            double[] var234 = computeVector(var786, var403, 0.0);
            double var469 = 0.0;
            double[] var338 = computeVector(var793, var469, 0.0);
            double var405 = var793;
            double var627 = 0.0;
            double[] var407 = computeVector(var405, var803, var627);
            double var471 = var786;
            double var652 = 0.0;
            double[] var473 = computeVector(var471, var798, var652);
            double var654 = 0.25;
            double var698 = var31;
            double var733 = 0.1;
            int var529 = CrownJewelGeometry.computeColorOrIndex(var58, var161, var654, var698, var733);
            double var629 = var781;
            double var682 = 0.25;
            double var718 = var31;
            double var754 = 0.1;
            int var601 = CrownJewelGeometry.computeColorOrIndex(var58, var629, var682, var718, var754);
            int var631 = var58;
            double var656 = var781;
            double var700 = 0.85;
            double var735 = var31;
            double var764 = 0.1;
            int var632 = CrownJewelGeometry.computeColorOrIndex(var631, var656, var700, var735, var764);
            int var658 = var58;
            double var684 = var161;
            double var720 = 0.85;
            double var756 = var31;
            double var770 = 0.1;
            int var659 = CrownJewelGeometry.computeColorOrIndex(var658, var684, var720, var756, var770);
            arg0.applyStep(var234, var338, var407, var473, var529, var601, var632, var659);
            double var408 = 0.0;
            double[] var237 = computeVector(var786, var408, 0.003);
            double var474 = 0.0;
            double[] var341 = computeVector(var793, var474, 0.003);
            double var410 = var793;
            double var633 = 0.003;
            double[] var412 = computeVector(var410, 0.013, var633);
            double var476 = var786;
            double var660 = 0.003;
            double[] var478 = computeVector(var476, 0.013, var660);
            int var535 = ColorMathUtils.computeColorOrIndex7(var58, 0.2);
            double var662 = 0.8;
            double var702 = var31;
            double var737 = 0.1;
            int var536 = CrownJewelGeometry.computeColorOrIndex(var535, var161, var662, var702, var737);
            double var635 = 0.2;
            int var611 = ColorMathUtils.computeColorOrIndex7(var58, var635);
            double var637 = var781;
            double var686 = 0.8;
            double var722 = var31;
            double var758 = 0.1;
            int var612 = CrownJewelGeometry.computeColorOrIndex(var611, var637, var686, var722, var758);
            int var639 = var58;
            double var664 = 0.2;
            int var640 = ColorMathUtils.computeColorOrIndex7(var639, var664);
            double var666 = var781;
            double var704 = 1.0;
            double var739 = var31;
            double var766 = 0.1;
            int var641 = CrownJewelGeometry.computeColorOrIndex(var640, var666, var704, var739, var766);
            int var668 = var58;
            double var688 = 0.2;
            int var669 = ColorMathUtils.computeColorOrIndex7(var668, var688);
            double var690 = var161;
            double var724 = 1.0;
            double var760 = var31;
            double var772 = 0.1;
            int var670 = CrownJewelGeometry.computeColorOrIndex(var669, var690, var724, var760, var772);
            arg0.applyStep(var237, var341, var412, var478, var536, var612, var641, var670);
            var36 = (var36 + 1);
        }
        double var71 = computeDoubleMetric(0.0);
        double var779 = var71;
        double[][] var74 = new double[9][];
        double[] var347 = new double[2];
        int var479 = 0;
        var347[var479] = -0.068;
        int var480 = 1;
        var347[var480] = 0.0;
        var74[0] = var347;
        double[] var349 = new double[2];
        int var481 = 0;
        var349[var481] = -0.06;
        int var482 = 1;
        var349[var482] = 0.05;
        var74[1] = var349;
        double[] var351 = new double[2];
        int var483 = 0;
        var351[var483] = -0.04;
        int var484 = 1;
        var351[var484] = 0.102;
        var74[2] = var351;
        double[] var353 = new double[2];
        int var485 = 0;
        var353[var485] = -0.016;
        int var486 = 1;
        var353[var486] = 0.152;
        var74[3] = var353;
        double[] var355 = new double[2];
        int var487 = 0;
        var355[var487] = 0.0;
        int var488 = 1;
        var355[var488] = 0.186;
        var74[4] = var355;
        double[] var357 = new double[2];
        int var489 = 0;
        var357[var489] = 0.016;
        int var490 = 1;
        var357[var490] = 0.152;
        var74[5] = var357;
        double[] var359 = new double[2];
        int var491 = 0;
        var359[var491] = 0.04;
        int var492 = 1;
        var359[var492] = 0.102;
        var74[6] = var359;
        double[] var361 = new double[2];
        int var493 = 0;
        var361[var493] = 0.06;
        int var494 = 1;
        var361[var494] = 0.05;
        var74[7] = var361;
        double[] var363 = new double[2];
        int var495 = 0;
        var363[var495] = 0.068;
        int var496 = 1;
        var363[var496] = 0.0;
        var74[8] = var363;
        double var413 = 0.012;
        double var248 = (var779 - var413);
        double var415 = 0.004;
        double[] var77 = computeVector(0.0, var248, var415);
        var41 = 0;
        while (true) {
            int var79 = var41;
            int var198 = var74.length;
            int var199 = (var198 - 1);
            if (var79 >= var199) break;
            int var224 = var41;
            double[] var133 = var74[var224];
            double var134 = var133[0];
            double var136 = (var134 / 0.262);
            double[][] var447 = var74;
            int var515 = var41;
            double[] var448 = var447[var515];
            double var449 = var448[1];
            double var300 = (var779 + var449);
            double var451 = 0.004;
            double[] var138 = computeVector(var136, var300, var451);
            double[] var785 = var138;
            int var226 = var41;
            int var227 = (var226 + 1);
            double[] var140 = var74[var227];
            double var141 = var140[0];
            double var143 = (var141 / 0.262);
            double[][] var453 = var74;
            int var517 = var41;
            int var518 = (var517 + 1);
            double[] var454 = var453[var518];
            double var455 = var454[1];
            double var307 = (var779 + var455);
            double var457 = 0.004;
            double[] var145 = computeVector(var143, var307, var457);
            double[] var789 = var145;
            int var146 = var41;
            double var147 = ((double) var146);
            int var310 = var74.length;
            int var400 = 1;
            int var311 = (var310 - var400);
            double var312 = ((double) var311);
            double var149 = (var147 / var312);
            double var791 = var149;
            int var151 = var41;
            int var152 = (var151 + 1);
            double var153 = ((double) var152);
            int var315 = var74.length;
            int var401 = 1;
            int var316 = (var315 - var401);
            double var317 = ((double) var316);
            double var155 = (var153 / var317);
            double var796 = var155;
            double[] var402 = var789;
            int var459 = var58;
            double var623 = 0.35;
            double var676 = var31;
            double var708 = 0.1;
            int var460 = CrownJewelGeometry.computeColorOrIndex(var459, 0.5, var623, var676, var708);
            double var648 = 0.4;
            int var744 = var41;
            double[] var726 = var74[var744];
            int var745 = 1;
            double var727 = var726[var745];
            double var692 = (0.5 * var727);
            double var729 = 0.186;
            double var694 = (var692 / var729);
            double var650 = (var648 + var694);
            double var696 = var31;
            double var731 = 0.1;
            int var523 = CrownJewelGeometry.computeColorOrIndex(var58, var791, var650, var696, var731);
            double var625 = var796;
            double var678 = 0.4;
            double var710 = 0.5;
            double[][] var746 = var74;
            int var24 = var41;
            int var769 = 1;
            int var762 = (var24 + var769);
            double[] var747 = var746[var762];
            int var763 = 1;
            double var748 = var747[var763];
            double var712 = (var710 * var748);
            double var750 = 0.186;
            double var714 = (var712 / var750);
            double var680 = (var678 + var714);
            double var716 = var31;
            double var752 = 0.1;
            int var593 = CrownJewelGeometry.computeColorOrIndex(var58, var625, var680, var716, var752);
            arg0.applyStep(var77, var785, var402, var460, var523, var593);
            var41 = (var41 + 1);
        }
        double var497 = 0.066;
        double var366 = (var779 + var497);
        double var499 = 0.026;
        drawGeometryPart(arg0, 0.0, var366, var499, 0.05, 0.028, 0.01, 0.0, var60, var62, var58, var31, true);
        var783 = -1;
        while (true) {
            int var82 = var783;
            if (var82 > 1) break;
            int var395 = var783;
            double var396 = ((double) var395);
            double var222 = (0.55 * var396);
            double var398 = 0.038;
            double var621 = 0.019;
            double var674 = 0.013;
            double var706 = 0.006;
            int var741 = var783;
            if (var741 > 0) {
                var742 = 1.4;
            } else {
                var742 = 0.6;
            }
            int var768 = var60;
            int var774 = var62;
            int var775 = var58;
            double var776 = var31;
            int var778 = 0;
            drawGeometryPart(arg0, var222, var398, 0.019, var621, var674, var706, var742, var768, var774, var775, var776, var778);
            var783 = (var783 + 2);
        }
        double[] var84 = new double[3];
        var84[0] = 0.62;
        var84[1] = 1.0;
        var84[2] = 1.38;
        double[] var784 = var84;
        double[] var86 = new double[3];
        var86[0] = 0.052;
        var86[1] = 0.042;
        var86[2] = 0.032;
        var43 = 0;
        while (true) {
            int var88 = var43;
            int var204 = var784.length;
            if (var88 >= var204) break;
            var44 = -1;
            while (true) {
                int var90 = var44;
                if (var90 > 1) break;
                int var206 = var43;
                double var92 = var784[var206];
                int var257 = var44;
                double var258 = ((double) var257);
                double var94 = (var92 * var258);
                double var262 = computeDoubleMetric(var94);
                double var417 = 0.004;
                double var264 = (var262 - var417);
                double var419 = 0.0;
                double[] var98 = computeVector(var94, var264, var419);
                double var382 = Math.sin(var94);
                double var209 = (0.006 * var382);
                int var421 = var43;
                double var385 = var86[var421];
                double var501 = 0.006;
                double var615 = Math.cos(var94);
                double var503 = (var501 * var615);
                double[] var100 = GeometryPrimitives.computeVector(var98, var209, var385, var503);
                double var422 = var94;
                double var268 = (0.5 * var422);
                double var424 = CONST_DBL1;
                double var270 = (var268 / var424);
                double var103 = (0.5 + var270);
                int var505 = 4;
                double var671 = var31;
                IGradientColorSampler var572 = (p0, p1) -> TiaraCrownRenderer.lambda$a(var58, var103, var671, p0, p1);
                GeometryPrimitives.drawGeometryPart(arg0, var98, var100, 0.0135, var505, var572);
                double var213 = var100[0];
                double[] var389 = var100;
                int var426 = 1;
                double var390 = var389[var426];
                double var506 = 0.007;
                double var392 = (var390 + var506);
                double[] var508 = var100;
                double var509 = var508[2];
                double var619 = 0.0125;
                int var673 = var60;
                CrownJewelGeometry.drawGeometryPart(arg0, var213, var392, var509, var619, var673);
                var44 = (var44 + 2);
            }
            var43 = (var43 + 1);
        }
        var788 = -1;
        while (true) {
            int var108 = var788;
            if (var108 > 1) break;
            int var109 = var788;
            int var790 = var109;
            int var795 = 14;
            int var112 = (var795 + 1);
            double[][] var113 = new double[var112][];
            var800 = 0;
            while (true) {
                int var115 = var800;
                if (var115 > var795) break;
                int var116 = var800;
                double var117 = ((double) var116);
                double var275 = ((double) var795);
                double var119 = (var117 / var275);
                double var801 = var119;
                double var427 = 0.8;
                double var429 = (var427 * var801);
                double var279 = (1.0 - var429);
                double var123 = (0.026 * var279);
                double var431 = 6.283185307179586;
                double var283 = (var801 * var431);
                double var433 = 1.9;
                double var285 = (var283 * var433);
                double var127 = (3.141592653589793 - var285);
                int var218 = var800;
                int var287 = var788;
                double var288 = ((double) var287);
                double var435 = 0.088;
                double var642 = Math.cos(var127);
                double var578 = (var123 * var642);
                double var437 = (var435 + var578);
                double var290 = (var288 * var437);
                double var439 = 0.262;
                double var292 = (var290 / var439);
                double var441 = 0.046;
                double var644 = var127;
                double var646 = Math.sin(var644);
                double var582 = (var123 * var646);
                double var443 = (var441 + var582);
                double[] var294 = computeVector(var292, var443, 0.005);
                var113[var218] = var294;
                var800 = (var800 + 1);
            }
            DoubleUnaryOperator var295 = (p0) -> TiaraCrownRenderer.lambda$b(p0);
            int var394 = 3;
            int var445 = var58;
            int var511 = var790;
            IGradientColorSampler var446 = (p0, p1) -> TiaraCrownRenderer.lambda$a(var445, var511, var31, p0, p1);
            GeometryPrimitives.drawGeometryPart(arg0, var113, var295, var394, var446, false, false);
            var788 = (var788 + 2);
        }
    }

    private static void drawGeometryPart(CosmeticDrawBuilder arg0, double arg1, double arg2, double arg3, double arg4, double arg5, double arg6, double arg7, int arg8, int arg9, int arg10, double arg11, boolean arg12) {
        double var83 = 0.0;
        double var53 = 0.0;
        double var55 = (1.5707963267948966 - arg1);
        CrownJewelGeometry.drawGeometryPart(arg0, 0.267, var55, arg2);
        if (!arg12) {
            double var59 = (arg3 * 0.98);
            double var72 = (arg3 * 1.36);
            IGradientColorSampler var88 = (p0, p1) -> TiaraCrownRenderer.lambda$a(arg10, arg11, p0, p1);
            GeometryPrimitives.drawGeometryPart(arg0, 0.004, var59, var72, 8, var88);
        }
        CrownJewelGeometry.drawGeometryPart(arg0, arg3, arg4, arg5, arg6, 6, arg8, arg9, arg7);
        double[] var52 = GeometryPrimitives.computeVector(0.0, arg5, 0.0);
        if (arg12) {
            var53 = 0.1;
        } else {
            var53 = 0.055;
        }
        int var65 = ColorMathUtils.computeColorOrIndex7(arg8, 0.6);
        if (arg12) {
            var83 = 2.6;
        } else {
            var83 = 3.4;
        }
        double var91 = (arg7 * 0.3);
        double var81 = CrownJewelGeometry.drawGeometryPart(arg0, var83, var91);
        CrownJewelGeometry.drawGeometryPart(arg0, var52, var53, var65, var81);
        CosmeticDrawBuilder var45 = arg0.drawGeometryPart1();
    }

    static {
        CONST_DBL1 = Math.toRadians(112.0);
    }

}
