package need.clarity.modules.visual.cosmetics.targetesp;

import need.clarity.modules.visual.cosmetics.ColorMathUtils;
import need.clarity.modules.visual.cosmetics.CosmeticDrawBuilder;
import need.clarity.modules.visual.cosmetics.GeometryPrimitives;
import need.clarity.modules.visual.cosmetics.ICosmeticEffectRenderer;
import need.clarity.modules.visual.cosmetics.IGradientColorSampler;

public final class LightPillarTargetRenderer implements ICosmeticEffectRenderer {
    private static final int CONST_INT = 28;
    private static final int CONST_INT1 = 18;
    private static final int CONST_INT2 = 28;
    private static final int CONST_INT3 = 24;
    private static final int CONST_INT4 = 6;
    private static final int CONST_INT5 = 18;
    private static final double CONST_DBL = 1.7;
    private final TargetEspRenderSpec targetEspRenderSpec;

    public LightPillarTargetRenderer() {
        TargetEspRenderSpec var2 = TargetEspRenderSpec.evaluate();
        this(var2);
    }

    public LightPillarTargetRenderer(TargetEspRenderSpec arg0) {
        this.targetEspRenderSpec = arg0;
    }

    public void renderEffect(CosmeticDrawBuilder arg0) {
        double var1080 = 0.0;
        int var65 = 0;
        int var72 = 0;
        int var1130 = 0;
        int var1131 = 0;
        double var185 = 0.0;
        double var198 = 0.0;
        double var354 = 0.0;
        int var356 = 0;
        double var358 = 0.0;
        double var361 = 0.0;
        double var363 = 0.0;
        double var365 = 0.0;
        boolean var90 = arg0.enabledState;
        if (var90) {
            var361 = arg0.doubleValue;
        } else {
            var361 = 0.0;
        }
        double var31 = var361;
        TargetEspRenderSpec var92 = this.targetEspRenderSpec;
        double var93 = var92.age();
        TargetEspRenderSpec var96 = this.targetEspRenderSpec;
        double var97 = var96.computeDoubleMetric1();
        double var100 = arg0.computeDoubleMetric();
        int var102 = Double.compare(var100, 1.0E-6);
        if (var102 > 0) {
            var358 = arg0.computeDoubleMetric();
        } else {
            var358 = 1.0;
        }
        double var37 = var358;
        TargetEspRenderSpec var434 = this.targetEspRenderSpec;
        double var435 = var434.phase();
        double var105 = (6.283185307179586 * var435);
        TargetEspRenderSpec var108 = this.targetEspRenderSpec;
        double var109 = var108.width();
        double var439 = (0.16 * var97);
        double var111 = (var109 + var439);
        TargetEspRenderSpec var442 = this.targetEspRenderSpec;
        double var443 = var442.height();
        double var115 = (-0.5 * var443);
        double var445 = var37;
        double var117 = (var115 / var445);
        TargetEspRenderSpec var120 = this.targetEspRenderSpec;
        double var121 = var120.height();
        double var447 = var37;
        double var123 = (var121 / var447);
        double var449 = 2.6;
        double var724 = var97;
        double var451 = (var449 * var724);
        double var125 = (var123 + var451);
        double var129 = GeometryPrimitives.computeDoubleMetric(var93);
        double var453 = 0.12;
        double var726 = 0.88;
        double var728 = (var726 * var129);
        double var455 = (var453 + var728);
        double var133 = (var125 * var455);
        double var457 = var133;
        double var137 = (var117 + var457);
        double var459 = 0.65;
        double var730 = var93;
        double var461 = (var459 * var730);
        double var141 = (0.35 + var461);
        double[] var144 = arg0.computeVector1();
        if (var144 != null) {
            double var147 = var144[0];
            double[] var463 = var144;
            double var464 = var463[2];
            double var149 = Math.hypot(var147, var464);
            double var466 = var111;
            double var732 = 1.05;
            double var468 = (var466 * var732);
            int var151 = Double.compare(var149, var468);
            if (var151 < 0) {
                double var153 = var144[1];
                double var470 = var117;
                double var734 = 0.2;
                double var472 = (var470 - var734);
                int var155 = Double.compare(var153, var472);
                if (var155 > 0) {
                    double var157 = var144[1];
                    double var474 = var137;
                    int var159 = Double.compare(var157, var474);
                    if (var159 < 0) {
                        var356 = 1;
                    } else {
                    }
                }
                var356 = 0;
                int var56 = var356;
                if (var56 != 0) {
                    var354 = 0.3;
                } else {
                    var354 = 1.0;
                }
                double var57 = var354;
                int var162 = arg0.counterValue;
                int var164 = arg0.counterValue1;
                int var370 = arg0.counterValue1;
                double var476 = 0.4;
                int var371 = ColorMathUtils.computeColorOrIndex7(var370, var476);
                double var478 = 0.5;
                int var165 = ColorMathUtils.computeColorOrIndex(var164, var371, var478);
                double var656 = var137;
                double var989 = (var111 * 0.86);
                IGradientColorSampler var1100 = (p0, p1) -> LightPillarTargetRenderer.lambda$a(var105, var31, var162, var165, var141, var57, p0, p1);
                GeometryPrimitives.drawGeometryPart(arg0, var117, var656, var111, var989, 28, var1100, false, false);
                double var658 = var137;
                double var842 = 0.94;
                double var660 = (var658 * var842);
                double var844 = var117;
                double var991 = 0.06;
                double var846 = (var844 * var991);
                double var662 = (var660 + var846);
                double var848 = var111;
                double var993 = 0.52;
                double var850 = (var848 * var993);
                double var995 = var111;
                double var997 = (var995 * 0.4);
                IGradientColorSampler var1103 = (p0, p1) -> LightPillarTargetRenderer.lambda$a(var105, var162, var165, var141, var57, p0, p1);
                GeometryPrimitives.drawGeometryPart(arg0, var117, var662, var850, var997, 18, var1103, false, false);
                double var480 = var97;
                double var170 = (0.028 * var480);
                double var664 = var117;
                double var852 = 0.0;
                double[] var378 = GeometryPrimitives.computeVector(0.0, var664, var852);
                double var482 = 0.0;
                double var736 = var137;
                double var911 = 0.93;
                double var738 = (var736 * var911);
                double var913 = var117;
                double var915 = (var913 * 0.07);
                double var740 = (var738 + var915);
                double var917 = 0.0;
                double[] var484 = GeometryPrimitives.computeVector(var482, var740, var917);
                double var666 = var170;
                int var854 = var162;
                double var919 = 0.7;
                int var855 = ColorMathUtils.computeColorOrIndex7(var854, var919);
                double var921 = 0.85;
                double var923 = (var921 * var141);
                if (var56 != 0) {
                    var1080 = 0.4;
                } else {
                    var1080 = 1.0;
                }
                double var925 = (var923 * var1080);
                int var856 = ColorMathUtils.computeColorOrIndex5(var855, var925);
                int var927 = var165;
                double var999 = 0.5;
                int var928 = ColorMathUtils.computeColorOrIndex7(var927, var999);
                double var1001 = 0.0;
                int var929 = ColorMathUtils.computeColorOrIndex5(var928, var1001);
                int var1003 = 0;
                TargetEspMathUtils.drawGeometryPart(arg0, var378, var484, var666, var856, var929, var1003);
                LightPillarTargetRenderer var485 = this;
                TargetEspRenderSpec var486 = var485.targetEspRenderSpec;
                double var487 = var486.computeDoubleMetric();
                double var175 = (0.9 * var487);
                var65 = 0;
                while (true) {
                    int var178 = var65;
                    if (var178 >= 6) break;
                    double var627 = 0.45;
                    double var326 = (var105 * var627);
                    double var629 = 6.283185307179586;
                    int var818 = var65;
                    double var819 = ((double) var818);
                    double var631 = (var629 * var819);
                    double var821 = 6.0;
                    double var633 = (var631 / var821);
                    double var328 = (var326 + var633);
                    double var635 = var175;
                    double var332 = (var328 + var635);
                    double var637 = var97;
                    double var336 = (0.05 * var637);
                    double var639 = var111;
                    double var823 = 0.2;
                    double var641 = Math.max(var639, var823);
                    double var338 = (var336 / var641);
                    double var643 = 0.8;
                    double var342 = (var111 * var643);
                    double var645 = var133;
                    double var825 = 0.82;
                    double var647 = (var645 * var825);
                    double var346 = (var117 + var647);
                    int var349 = ColorMathUtils.computeColorOrIndex7(var162, 0.35);
                    double var705 = var141;
                    double var420 = (0.3 * var705);
                    double var707 = var57;
                    double var422 = (var420 * var707);
                    int var350 = ColorMathUtils.computeColorOrIndex5(var349, var422);
                    int var352 = ColorMathUtils.computeColorOrIndex5(var165, 0.0);
                    double var709 = 0.98;
                    double var428 = (var111 * var709);
                    double var711 = var328;
                    double var894 = var338;
                    double var713 = (var711 - var894);
                    double var715 = Math.cos(var713);
                    double var430 = (var428 * var715);
                    double var717 = var117;
                    double var896 = var111;
                    double var898 = (var896 * 0.98);
                    double var1031 = (var328 - var338);
                    double var1033 = Math.sin(var1031);
                    double var900 = (var898 * var1033);
                    double[] var432 = GeometryPrimitives.computeVector(var430, var717, var900);
                    double var649 = var111;
                    double var827 = 0.98;
                    double var651 = (var649 * var827);
                    double var829 = var328;
                    double var972 = var338;
                    double var831 = (var829 + var972);
                    double var833 = Math.cos(var831);
                    double var653 = (var651 * var833);
                    double var835 = var117;
                    double var974 = var111;
                    double var976 = (var974 * 0.98);
                    double var1069 = (var328 + var338);
                    double var1071 = Math.sin(var1069);
                    double var978 = (var976 * var1071);
                    double[] var655 = GeometryPrimitives.computeVector(var653, var835, var978);
                    double var719 = var342;
                    double var902 = var332;
                    double var904 = (var902 + var338);
                    double var906 = Math.cos(var904);
                    double var721 = (var719 * var906);
                    double var908 = var346;
                    double var1096 = (var332 + var338);
                    double var1098 = Math.sin(var1096);
                    double var1039 = (var342 * var1098);
                    double[] var723 = GeometryPrimitives.computeVector(var721, var908, var1039);
                    double var837 = var342;
                    double var980 = var332;
                    double var982 = (var980 - var338);
                    double var984 = Math.cos(var982);
                    double var839 = (var837 * var984);
                    double var986 = var346;
                    double var1112 = (var332 - var338);
                    double var1114 = Math.sin(var1112);
                    double var1077 = (var342 * var1114);
                    double[] var841 = GeometryPrimitives.computeVector(var839, var986, var1077);
                    int var910 = var350;
                    int var988 = var350;
                    arg0.applyStep(var432, var655, var723, var841, var910, var988, var352, var352);
                    var65 = (var65 + 1);
                }
                double var489 = var37;
                double var181 = (0.02 / var489);
                boolean var184 = arg0.enabledState;
                if (var184) {
                    double var623 = 0.15;
                    double var812 = var31;
                    double var970 = 2.6;
                    double var814 = (var812 * var970);
                    double var816 = Math.sin(var814);
                    double var625 = (var623 * var816);
                    var185 = (0.85 + var625);
                } else {
                    var185 = 1.0;
                }
                double var68 = var185;
                double var668 = var181;
                double var382 = (var117 + var668);
                double var670 = var111;
                double var857 = 1.05;
                double var672 = (var670 * var857);
                int var859 = 24;
                int var930 = var162;
                double var1004 = var68;
                IGradientColorSampler var931 = (p0, p1) -> LightPillarTargetRenderer.lambda$a(var930, var1004, var141, p0, p1);
                GeometryPrimitives.drawGeometryPart(arg0, var382, var672, var859, var931);
                var72 = 0;
                while (true) {
                    int var189 = var72;
                    if (var189 >= 2) break;
                    boolean var191 = arg0.enabledState;
                    if (var191) {
                        double var491 = 1.7;
                        double var194 = (var31 / var491);
                        double var493 = 0.5;
                        int var742 = var72;
                        double var743 = ((double) var742);
                        double var495 = (var493 * var743);
                        double var196 = (var194 + var495);
                        var198 = TargetEspMathUtils.computeDoubleMetric(var196);
                    } else {
                        double var521 = 0.5;
                        int var759 = var72;
                        double var760 = ((double) var759);
                        double var523 = (var521 * var760);
                        var198 = (0.25 + var523);
                    }
                    double var73 = var198;
                    double var497 = 0.7;
                    double var745 = 1.5;
                    double var932 = var73;
                    double var747 = (var745 * var932);
                    double var499 = (var497 + var747);
                    double var202 = (var111 * var499);
                    double var501 = var97;
                    double var206 = (0.07 * var501);
                    double var503 = 1.0;
                    double var749 = 0.5;
                    double var934 = var73;
                    double var751 = (var749 * var934);
                    double var505 = (var503 - var751);
                    double var208 = (var206 * var505);
                    double var507 = 1.0;
                    double var753 = var73;
                    double var509 = (var507 - var753);
                    double var755 = 1.6;
                    double var511 = Math.pow(var509, var755);
                    double var212 = (0.6 * var511);
                    double var513 = var73;
                    double var757 = 8.0;
                    double var515 = (var513 * var757);
                    double var517 = GeometryPrimitives.computeDoubleMetric(var515);
                    double var214 = (var212 * var517);
                    double var519 = var141;
                    double var216 = (var214 * var519);
                    int var219 = ColorMathUtils.computeColorOrIndex7(var162, 0.3);
                    int var220 = ColorMathUtils.computeColorOrIndex5(var219, var216);
                    double var674 = var181;
                    double var860 = 1.5;
                    double var676 = (var674 * var860);
                    double var391 = (var117 + var676);
                    double var678 = var202;
                    double var862 = var208;
                    double var680 = (var678 - var862);
                    double var864 = var202;
                    double var1006 = var208;
                    double var866 = (var864 + var1006);
                    int var1008 = 28;
                    IGradientColorSampler var1046 = (p0, p1) -> LightPillarTargetRenderer.lambda$b(var220, p0, p1);
                    GeometryPrimitives.drawGeometryPart(arg0, var391, var680, var866, var1008, var1046);
                    var72 = (var72 + 1);
                }
                var1130 = 0;
                while (true) {
                    int var225 = var1130;
                    if (var225 >= 2) break;
                    boolean var294 = arg0.enabledState;
                    if (var294) {
                        double var593 = 3.4;
                        double var297 = (var31 / var593);
                        double var595 = 0.5;
                        int var800 = var1130;
                        double var801 = ((double) var800);
                        double var597 = (var595 * var801);
                        double var299 = (var297 + var597);
                        var365 = TargetEspMathUtils.computeDoubleMetric(var299);
                    } else {
                        double var619 = 0.4;
                        int var809 = var1130;
                        double var810 = ((double) var809);
                        double var621 = (var619 * var810);
                        var365 = (0.3 + var621);
                    }
                    double var1134 = var365;
                    double var599 = var133;
                    double var803 = var1134;
                    double var601 = (var599 * var803);
                    double var303 = (var117 + var601);
                    double var603 = var1134;
                    double var805 = 3.141592653589793;
                    double var605 = (var603 * var805);
                    double var607 = Math.sin(var605);
                    double var307 = (0.55 * var607);
                    double var609 = 1.0;
                    double var807 = var1134;
                    double var611 = (var609 - var807);
                    double var309 = (var307 * var611);
                    double var613 = var141;
                    double var311 = (var309 * var613);
                    double var615 = var57;
                    double var313 = (var311 * var615);
                    double var617 = var1134;
                    int var316 = ColorMathUtils.computeColorOrIndex(var162, var165, var617);
                    int var317 = ColorMathUtils.computeColorOrIndex7(var316, 0.45);
                    int var318 = ColorMathUtils.computeColorOrIndex5(var317, var313);
                    double var699 = var111;
                    double var884 = 1.01;
                    double var701 = (var699 * var884);
                    double var886 = 0.04;
                    double var1022 = var97;
                    double var888 = (var886 * var1022);
                    double var703 = (var701 - var888);
                    double var890 = var111;
                    double var892 = (var890 * 1.01);
                    IGradientColorSampler var1064 = (p0, p1) -> LightPillarTargetRenderer.lambda$a(var318, p0, p1);
                    GeometryPrimitives.drawGeometryPart(arg0, var303, var703, var892, 28, var1064);
                    var1130 = (var1130 + 1);
                }
                var1131 = 0;
                while (true) {
                    int var227 = var1131;
                    if (var227 >= 18) break;
                    boolean var229 = arg0.enabledState;
                    if (var229) {
                        double var525 = 0.2;
                        double var762 = 0.28;
                        int var936 = var1131;
                        int var1009 = 40;
                        int var937 = (var936 + var1009);
                        double var938 = GeometryPrimitives.computeDoubleMetric(var937);
                        double var764 = (var762 * var938);
                        double var527 = (var525 + var764);
                        double var232 = (var31 * var527);
                        int var529 = var1131;
                        int var682 = 80;
                        int var530 = (var529 + var682);
                        double var531 = GeometryPrimitives.computeDoubleMetric(var530);
                        double var234 = (var232 + var531);
                        var363 = TargetEspMathUtils.computeDoubleMetric(var234);
                    } else {
                        int var291 = var1131;
                        int var292 = (var291 + 80);
                        var363 = GeometryPrimitives.computeDoubleMetric(var292);
                    }
                    double var1132 = var363;
                    int var533 = var1131;
                    double var534 = GeometryPrimitives.computeDoubleMetric(var533);
                    double var238 = (6.283185307179586 * var534);
                    double var536 = var105;
                    double var766 = 0.3;
                    double var538 = (var536 * var766);
                    double var240 = (var238 + var538);
                    double var540 = var1132;
                    double var768 = 1.5;
                    double var542 = (var540 * var768);
                    double var242 = (var240 + var542);
                    double var544 = 0.2;
                    double var770 = 0.7;
                    int var940 = var1131;
                    int var1010 = 20;
                    int var941 = (var940 + var1010);
                    double var942 = GeometryPrimitives.computeDoubleMetric(var941);
                    double var772 = (var770 * var942);
                    double var546 = (var544 + var772);
                    double var246 = (var111 * var546);
                    double var548 = var242;
                    double var550 = Math.cos(var548);
                    double var250 = (var246 * var550);
                    double var552 = var242;
                    double var554 = Math.sin(var552);
                    double var254 = (var246 * var554);
                    double var556 = 0.05;
                    double var258 = (var117 + var556);
                    double var558 = var1132;
                    double var774 = var133;
                    double var560 = (var558 * var774);
                    double var776 = 0.92;
                    double var562 = (var560 * var776);
                    double var260 = (var258 + var562);
                    double var564 = var97;
                    double var264 = (0.028 * var564);
                    double var566 = 1.0;
                    double var778 = 0.45;
                    double var944 = var1132;
                    double var780 = (var778 * var944);
                    double var568 = (var566 - var780);
                    double var266 = (var264 * var568);
                    double var570 = 0.7;
                    double var782 = 0.6;
                    int var946 = var1131;
                    int var1011 = 60;
                    int var947 = (var946 + var1011);
                    double var948 = GeometryPrimitives.computeDoubleMetric(var947);
                    double var784 = (var782 * var948);
                    double var572 = (var570 + var784);
                    double var268 = (var266 * var572);
                    double var574 = 3.141592653589793;
                    double var272 = (var1132 * var574);
                    double var274 = Math.sin(var272);
                    double var576 = 0.85;
                    double var276 = (var274 * var576);
                    double var578 = var141;
                    double var278 = (var276 * var578);
                    double[] var580 = var144;
                    double var683 = var250;
                    double var868 = var260;
                    double var1012 = var254;
                    double var581 = TargetEspMathUtils.computeDoubleMetric(var580, var683, var868, var1012, var37);
                    double var280 = (var278 * var581);
                    double var583 = 0.02;
                    int var284 = Double.compare(var280, var583);
                    if (var284 < 0) {
                    } else {
                        double var585 = var1132;
                        int var286 = ColorMathUtils.computeColorOrIndex(var162, var165, var585);
                        int var287 = ColorMathUtils.computeColorOrIndex7(var286, 0.55);
                        int var288 = ColorMathUtils.computeColorOrIndex5(var287, var280);
                        double var685 = var268;
                        double var402 = (var250 - var685);
                        double var687 = var260;
                        double var870 = var254;
                        double[] var404 = GeometryPrimitives.computeVector(var402, var687, var870);
                        double var587 = var250;
                        double var786 = var260;
                        double var950 = var268;
                        double var952 = (var950 * 1.6);
                        double var788 = (var786 + var952);
                        double var954 = var254;
                        double[] var589 = GeometryPrimitives.computeVector(var587, var788, var954);
                        double var689 = var250;
                        double var872 = var268;
                        double var691 = (var689 + var872);
                        double var874 = var260;
                        double var1014 = var254;
                        double[] var693 = GeometryPrimitives.computeVector(var691, var874, var1014);
                        double var790 = var250;
                        double var956 = var260;
                        double var1051 = (var268 * 1.6);
                        double var958 = (var956 - var1051);
                        double[] var792 = GeometryPrimitives.computeVector(var790, var958, var254);
                        int var876 = var288;
                        arg0.applyStep(var404, var589, var693, var792, var876);
                        double var694 = var260;
                        double var877 = var254;
                        double var1016 = var268;
                        double var879 = (var877 - var1016);
                        double[] var407 = GeometryPrimitives.computeVector(var250, var694, var879);
                        double var590 = var250;
                        double var793 = var260;
                        double var960 = var268;
                        double var962 = (var960 * 1.6);
                        double var795 = (var793 + var962);
                        double var964 = var254;
                        double[] var592 = GeometryPrimitives.computeVector(var590, var795, var964);
                        double var696 = var250;
                        double var881 = var260;
                        double var1018 = var254;
                        double var1020 = (var1018 + var268);
                        double[] var698 = GeometryPrimitives.computeVector(var696, var881, var1020);
                        double var797 = var250;
                        double var966 = var260;
                        double var1059 = (var268 * 1.6);
                        double var968 = (var966 - var1059);
                        double[] var799 = GeometryPrimitives.computeVector(var797, var968, var254);
                        int var883 = var288;
                        arg0.applyStep(var407, var592, var698, var799, var883);
                    }
                    var1131 = (var1131 + 1);
                }
                return;
            }
        }
    }

}
