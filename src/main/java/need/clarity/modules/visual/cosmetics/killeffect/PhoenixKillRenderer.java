package need.clarity.modules.visual.cosmetics.killeffect;

import java.util.function.DoubleUnaryOperator;
import need.clarity.modules.visual.cosmetics.ColorMathUtils;
import need.clarity.modules.visual.cosmetics.CosmeticDrawBuilder;
import need.clarity.modules.visual.cosmetics.GeometryPrimitives;
import need.clarity.modules.visual.cosmetics.ICosmeticEffectRenderer;
import need.clarity.modules.visual.cosmetics.IGradientColorSampler;

public final class PhoenixKillRenderer implements ICosmeticEffectRenderer {
    public static final int CONST_INT = 16738842;
    public static final int CONST_INT1 = 16773280;
    public static final int CONST_INT2 = 7;
    static final int CONST_INT3 = 6;
    static final int CONST_INT4 = 5;
    static final int CONST_INT5 = 5;
    static final int CONST_INT6 = 4;
    static final int CONST_INT7 = 6;
    static final int CONST_INT8 = 3;
    static final int CONST_INT9 = 4;
    static final int CONST_INT10 = 3;
    static final int CONST_INT11 = 2;
    static final int CONST_INT12 = 7;
    static final int CONST_INT13 = 8;
    static final int CONST_INT14 = 7;
    static final int CONST_INT15 = 6;
    static final int CONST_INT16 = 3;
    static final int CONST_INT17 = 4;
    static final int CONST_INT18 = 16;
    static final int CONST_INT19 = 28;
    static final int CONST_INT20 = 16;
    static final int CONST_INT21 = 14;
    static final int CONST_INT22 = 14;
    static final int CONST_INT23 = 22;
    static final int CONST_INT24 = 8;
    private static final int CONST_INT25 = 59;
    public static final int CONST_INT26 = 386;
    private final KillEffectRenderSpec killEffectRenderSpec;

    public static int computeColorOrIndex(int arg0) {
        int var12 = 0;
        if (Double.compare(GeometryPrimitives.computeDoubleMetric(((arg0 * 67) + 4)), 0.5) < 0) {
            var12 = 0;
        } else {
            var12 = 1;
        }
        return (2 + var12);
    }

    public static double computeDoubleMetric(double arg0, int arg1) {
        double var18 = KillEffectMathUtils.computeDoubleMetric(((arg0 - 0.12) / 0.78), 0.0, 1.0);
        double var26 = GeometryPrimitives.computeDoubleMetric(((arg0 - 0.8) / 0.15));
        double var69 = ((double) computeColorOrIndex(arg1));
        double var62 = Math.cos(((6.283185307179586 * var69) * var18));
        double var46 = (0.62 * var62);
        double var30 = (0.12 + var46);
        double var50 = (1.0 - var26);
        double var32 = (var30 * var50);
        double var54 = (0.15 * var26);
        return (var32 + var54);
    }

    public PhoenixKillRenderer() {
        KillEffectRenderSpec var2 = KillEffectRenderSpec.evaluate();
        this(var2);
    }

    public PhoenixKillRenderer(KillEffectRenderSpec arg0) {
        this.killEffectRenderSpec = arg0;
    }

    private static double[] computeVector(double[] arg0, double[] arg1, double arg2) {
        double[] var12 = new double[3];
        double var15 = arg0[0];
        double var33 = (arg1[0] * arg2);
        double var17 = (var15 + var33);
        var12[0] = var17;
        double var20 = arg0[1];
        double var38 = (arg1[1] * arg2);
        double var22 = (var20 + var38);
        var12[1] = var22;
        double var25 = arg0[2];
        double var43 = (arg1[2] * arg2);
        double var27 = (var25 + var43);
        var12[2] = var27;
        return var12;
    }

    private static double[] computeVector(double[] arg0, double arg1, double[] arg2, double arg3) {
        double[] var15 = new double[3];
        double var20 = (arg0[0] * arg1);
        double var43 = (arg2[0] * arg3);
        double var22 = (var20 + var43);
        var15[0] = var22;
        double var27 = (arg0[1] * arg1);
        double var50 = (arg2[1] * arg3);
        double var29 = (var27 + var50);
        var15[1] = var29;
        double var34 = (arg0[2] * arg1);
        double var57 = (arg2[2] * arg3);
        double var36 = (var34 + var57);
        var15[2] = var36;
        return var15;
    }

    private static double[] computeVector(double[] arg0, double[] arg1) {
        double[] var20 = null;
        double var14 = Math.sqrt(GeometryPrimitives.computeDoubleMetric(arg0, arg0));
        if (Double.compare(var14, 1.0E-9) < 0) {
            var20 = arg1;
        } else {
            var20 = new double[3];
            double var26 = (arg0[0] / var14);
            var20[0] = var26;
            double var31 = (arg0[1] / var14);
            var20[1] = var31;
            double var36 = (arg0[2] / var14);
            var20[2] = var36;
        }
        return var20;
    }

    static double[] computeVector1(double[] arg0, double[] arg1) {
        double var31 = arg1[0];
        double var87 = arg1[2];
        double var33 = Math.atan2(var31, var87);
        double var42 = Math.asin(KillEffectMathUtils.computeDoubleMetric(-(arg1[1]), -1.0, 1.0));
        double var46 = Math.cos(var33);
        double var50 = Math.sin(var33);
        double var54 = Math.cos(var42);
        double var58 = Math.sin(var42);
        double var92 = arg0[0];
        double var62 = (var46 * var92);
        double var125 = arg0[2];
        double var96 = (var50 * var125);
        double var64 = (var62 - var96);
        double var67 = arg0[1];
        double var99 = arg0[0];
        double var71 = (var50 * var99);
        double var128 = arg0[2];
        double var103 = (var46 * var128);
        double var73 = (var71 + var103);
        double var79 = (var54 * var67);
        double var109 = (var58 * var73);
        double var81 = (var79 + var109);
        double[] var84 = new double[3];
        var84[0] = var33;
        var84[1] = var42;
        double var122 = Math.atan2(var81, var64);
        var84[2] = var122;
        return var84;
    }

    private double computeDoubleMetric() {
        double var14 = this.killEffectRenderSpec.height();
        double var9 = (0.95 * var14);
        double var21 = this.killEffectRenderSpec.width();
        double var18 = (0.25 * var21);
        double var11 = (var9 + var18);
        return Math.max(0.1, var11);
    }

    private double[] computeVector(double arg0, double arg1, double arg2) {
        double var92 = 0.0;
        double var25 = this.killEffectRenderSpec.height();
        double var29 = (arg0 * arg0);
        double var95 = (0.7 * arg0);
        double var58 = (1.7 - var95);
        double var31 = (var29 * var58);
        double var35 = (1.15 * arg1);
        double var64 = Math.pow(arg0, 1.4);
        double var37 = (var35 * var64);
        double var46 = (GeometryPrimitives.computeDoubleMetric(((this.killEffectRenderSpec.seed() * 67) + 1)) * 6.283185307179586);
        if (Double.compare(GeometryPrimitives.computeDoubleMetric(((this.killEffectRenderSpec.seed() * 67) + 2)), 0.5) < 0) {
            var92 = -1.0;
        } else {
            var92 = 1.0;
        }
        double var80 = (((var92 * 0.9) * 3.141592653589793) * arg0);
        double var48 = (var46 + var80);
        double var84 = Math.cos(var48);
        double var52 = (var37 * var84);
        double var109 = (0.5 * var25);
        double var88 = (arg2 + var109);
        double var117 = (((2.6 * var25) + arg1) * var31);
        double var90 = (var88 + var117);
        double var133 = Math.sin(var48);
        double var121 = (var37 * var133);
        return GeometryPrimitives.computeVector(var52, var90, var121);
    }

    public void renderEffect(CosmeticDrawBuilder arg0) {
        double[] var53 = null;
        double[] var59 = null;
        double var60 = 0.0;
        double[] var62 = null;
        double var412 = 0.0;
        double var414 = 0.0;
        KillEffectRenderSpec var110 = this.killEffectRenderSpec;
        double var111 = var110.life();
        KillEffectRenderSpec var114 = this.killEffectRenderSpec;
        double var115 = var114.age();
        int var119 = Double.compare(var111, 1.0);
        if (var119 >= 0) {
            return;
        }
        double var121 = this.computeDoubleMetric();
        KillEffectRenderSpec var124 = this.killEffectRenderSpec;
        IGroundHeightSampler var125 = var124.ground();
        double var127 = var125.computeDoubleMetric(0.0, 0.0);
        double var130 = arg0.computeDoubleMetric();
        int var132 = Double.compare(var130, 1.0E-6);
        if (var132 > 0) {
            var414 = arg0.computeDoubleMetric();
        } else {
            var414 = 1.0;
        }
        double var43 = var414;
        double[] var135 = arg0.computeVector1();
        int var137 = arg0.counterValue1;
        int var138 = ColorMathUtils.computeColorOrIndex7(var137, 0.8);
        int var140 = arg0.counterValue1;
        int var142 = arg0.counterValue;
        double var145 = (var111 / 0.04);
        double var147 = GeometryPrimitives.computeDoubleMetric(var145);
        double var702 = var121;
        this.drawGeometryPart(arg0, var125, var702, var111, var138, var140, var142);
        int var152 = Double.compare(var147, 0.01);
        if (var152 < 0) {
            return;
        }
        double var967 = 0.12;
        double var912 = (var111 / var967);
        double var914 = GeometryPrimitives.computeDoubleMetric(var912);
        double var745 = (0.45 * var914);
        double var493 = (0.55 + var745);
        double var155 = (var121 * var493);
        double var704 = var121;
        double var880 = var127;
        double[] var158 = this.computeVector(var111, var704, var880);
        var53 = var158;
        double[] var159 = var53;
        double var160 = var159[1];
        double var162 = (var160 - var127);
        double var747 = var155;
        double var499 = (0.3 * var747);
        double var164 = (var162 - var499);
        double var749 = var155;
        double var503 = (1.6 * var749);
        double var166 = (var164 / var503);
        double var751 = 1.0;
        double var168 = KillEffectMathUtils.computeDoubleMetric(var166, 0.15, var751);
        KillEffectRenderSpec var171 = this.killEffectRenderSpec;
        int var172 = var171.seed();
        int var173 = (var172 * 67);
        int var174 = (var173 + 3);
        double var175 = GeometryPrimitives.computeDoubleMetric(var174);
        double var177 = (var175 * 6.283185307179586);
        double var181 = Math.cos(var177);
        double var753 = var177;
        double var755 = Math.sin(var753);
        double[] var183 = GeometryPrimitives.computeVector(var181, 0.0, var755);
        var59 = var183;
        var60 = 0.0;
        double var189 = Math.cos(var177);
        double var191 = -(var189);
        double var757 = var177;
        double var759 = Math.sin(var757);
        double var761 = -(var759);
        double[] var193 = GeometryPrimitives.computeVector(var191, 0.25, var761);
        var62 = var193;
        if (var135 != null) {
            double var196 = var135[0];
            double[] var513 = var53;
            int var706 = 0;
            double var514 = var513[var706];
            double var198 = (var196 - var514);
            double var201 = var135[1];
            double[] var516 = var53;
            int var707 = 1;
            double var517 = var516[var707];
            double var203 = (var201 - var517);
            double var206 = var135[2];
            double[] var519 = var53;
            int var708 = 2;
            double var520 = var519[var708];
            double var208 = (var206 - var520);
            double var212 = Math.hypot(var198, var208);
            double var216 = (var212 / 1.5);
            double var763 = 1.0;
            double var218 = KillEffectMathUtils.computeDoubleMetric(var216, 0.0, var763);
            var60 = var218;
            int var222 = Double.compare(var212, 1.0E-6);
            if (var222 > 0) {
                double var225 = -(var198);
                double var227 = (var225 / var212);
                double var765 = var208;
                double var767 = -(var765);
                double var916 = var212;
                double var769 = (var767 / var916);
                double[] var229 = GeometryPrimitives.computeVector(var227, 0.0, var769);
                var59 = var229;
            }
            double var771 = var208;
            double[] var232 = GeometryPrimitives.computeVector(var198, var203, var771);
            double[] var427 = var62;
            double[] var233 = computeVector(var232, var427);
            var62 = var233;
        }
        double var709 = var111;
        double var882 = 0.01;
        double var711 = (var709 + var882);
        double var430 = Math.min(1.0, var711);
        double var713 = var121;
        double var884 = var127;
        double[] var235 = this.computeVector(var430, var713, var884);
        double var715 = var111;
        double var886 = 0.01;
        double var717 = (var715 - var886);
        double var434 = Math.max(0.0, var717);
        double var719 = var121;
        double var888 = var127;
        double[] var237 = this.computeVector(var434, var719, var888);
        double var239 = var235[0];
        double[] var536 = var237;
        int var721 = 0;
        double var537 = var536[var721];
        double var241 = (var239 - var537);
        double var539 = 0.0;
        double[] var773 = var235;
        int var890 = 2;
        double var774 = var773[var890];
        double[] var918 = var237;
        int var959 = 2;
        double var919 = var918[var959];
        double var776 = (var774 - var919);
        double[] var243 = GeometryPrimitives.computeVector(var241, var539, var776);
        double[] var244 = computeVector(var243, var183);
        double[] var245 = var59;
        double var438 = var60;
        double[] var722 = var183;
        double var778 = 1.0;
        double var921 = var60;
        double var780 = (var778 - var921);
        double[] var246 = computeVector(var245, var438, var722, var780);
        double[] var723 = var244;
        double var782 = 0.38;
        double[] var247 = computeVector(var246, 0.62, var723, var782);
        double[] var248 = computeVector(var247, var183);
        double var541 = 0.3;
        double var784 = var111;
        double var786 = GeometryPrimitives.computeDoubleMetric(var784);
        double var543 = (var541 * var786);
        double var251 = (1.15 - var543);
        double var254 = var248[0];
        double var545 = var251;
        double var547 = Math.cos(var545);
        double var256 = (var254 * var547);
        double var549 = var251;
        double var551 = Math.sin(var549);
        double[] var788 = var248;
        int var891 = 2;
        double var789 = var788[var891];
        double var923 = var251;
        double var925 = Math.cos(var923);
        double var791 = (var789 * var925);
        double[] var258 = GeometryPrimitives.computeVector(var256, var551, var791);
        double var553 = 1.0;
        double var793 = 0.0;
        double[] var261 = GeometryPrimitives.computeVector(0.0, var553, var793);
        double[] var263 = GeometryPrimitives.computeVector1(var261, var258);
        double var724 = 0.0;
        double var892 = 0.0;
        double[] var447 = GeometryPrimitives.computeVector(1.0, var724, var892);
        double[] var264 = computeVector(var263, var447);
        double[] var266 = GeometryPrimitives.computeVector1(var258, var264);
        double[] var267 = var62;
        double[] var726 = var266;
        double var795 = 0.2;
        double[] var268 = computeVector(var267, 0.8, var726, var795);
        double[] var269 = computeVector(var268, var266);
        double[] var555 = var258;
        double[] var727 = var269;
        double var556 = GeometryPrimitives.computeDoubleMetric(var555, var727);
        double var558 = -(var556);
        double[] var271 = computeVector(var258, var269, var558);
        double var560 = 1.0;
        double var797 = 0.0;
        double var927 = 0.0;
        double[] var562 = GeometryPrimitives.computeVector(var560, var797, var927);
        double[] var454 = GeometryPrimitives.computeVector1(var269, var562);
        double var563 = 0.0;
        double var799 = 0.0;
        double var929 = 1.0;
        double[] var565 = GeometryPrimitives.computeVector(var563, var799, var929);
        double[] var455 = computeVector(var454, var565);
        double[] var272 = computeVector(var271, var455);
        double[] var274 = GeometryPrimitives.computeVector1(var269, var272);
        double var728 = 0.0;
        double var894 = 0.0;
        double[] var459 = GeometryPrimitives.computeVector(1.0, var728, var894);
        double[] var275 = computeVector(var274, var459);
        double[] var277 = computeVector1(var275, var272);
        double var566 = 0.02;
        double var280 = (var111 - var566);
        double var568 = 0.16;
        double var282 = (var280 / var568);
        double var284 = GeometryPrimitives.computeDoubleMetric(var282);
        double var570 = var155;
        double var288 = (1.3 * var570);
        double var572 = var168;
        double var290 = (var288 * var572);
        double var574 = 0.0;
        double[] var801 = var272;
        int var896 = 1;
        double var802 = var801[var896];
        double var576 = Math.max(var574, var802);
        double var804 = 0.25;
        double var578 = (var576 + var804);
        double var292 = (var290 * var578);
        double var580 = 0.35;
        double var806 = 0.8;
        double var931 = var284;
        double var808 = (var806 * var931);
        double var582 = (var580 + var808);
        double var296 = (var155 * var582);
        double[] var584 = var275;
        int var730 = 1;
        double var585 = var584[var730];
        double var587 = Math.abs(var585);
        double var298 = (var296 * var587);
        double var589 = 0.25;
        double var810 = var155;
        double var591 = (var589 * var810);
        double[] var812 = var269;
        int var897 = 1;
        double var813 = var812[var897];
        double var815 = Math.abs(var813);
        double var593 = (var591 * var815);
        double var300 = (var298 + var593);
        double var595 = 1.3;
        double var817 = var155;
        double var597 = (var595 * var817);
        double var819 = 0.0;
        double[] var933 = var272;
        int var960 = 1;
        double var934 = var933[var960];
        double var821 = Math.max(var819, var934);
        double var599 = (var597 * var821);
        double var302 = (var300 + var599);
        double var601 = var127;
        double var823 = var292;
        double var936 = var302;
        double var825 = Math.max(var823, var936);
        double var603 = (var601 + var825);
        double var827 = 0.03;
        double var605 = (var603 + var827);
        double[] var829 = var53;
        int var898 = 1;
        double var830 = var829[var898];
        double var607 = (var605 - var830);
        double var306 = Math.max(0.0, var607);
        double var609 = 0.0;
        int var310 = Double.compare(var306, var609);
        if (var310 > 0) {
            double[] var311 = var53;
            double var312 = var311[0];
            double[] var611 = var53;
            int var731 = 1;
            double var612 = var611[var731];
            double var832 = var306;
            double var614 = (var612 + var832);
            double[] var834 = var53;
            int var899 = 2;
            double var835 = var834[var899];
            double[] var314 = GeometryPrimitives.computeVector(var312, var614, var835);
            var53 = var314;
        }
        KillEffectRenderSpec var316 = this.killEffectRenderSpec;
        int var317 = var316.seed();
        int var318 = computeColorOrIndex(var317);
        double var616 = 0.02;
        double var321 = (var111 - var616);
        double var618 = 0.16;
        double var323 = (var321 / var618);
        double var325 = GeometryPrimitives.computeDoubleMetric(var323);
        double var620 = 0.12;
        double var329 = (var111 - var620);
        double var622 = 0.78;
        double var331 = (var329 / var622);
        double var624 = 0.0;
        double var837 = 1.0;
        double var333 = KillEffectMathUtils.computeDoubleMetric(var331, var624, var837);
        int var626 = var318;
        double var627 = ((double) var626);
        double var337 = (6.283185307179586 * var627);
        double var629 = var333;
        double var339 = (var337 * var629);
        double var631 = 0.8;
        double var343 = (var111 - var631);
        double var633 = 0.15;
        double var345 = (var343 / var633);
        double var347 = GeometryPrimitives.computeDoubleMetric(var345);
        double var635 = 1.0;
        double var839 = var325;
        double var637 = (var635 - var839);
        double var351 = (0.95 * var637);
        double var639 = var111;
        PhoenixKillRenderer var841 = this;
        KillEffectRenderSpec var842 = var841.killEffectRenderSpec;
        int var843 = var842.seed();
        double var641 = computeDoubleMetric(var639, var843);
        double var844 = var325;
        double var643 = (var641 * var844);
        double var353 = (var351 + var643);
        double var645 = 0.12;
        int var357 = Double.compare(var111, var645);
        if (var357 > 0) {
            double var647 = 0.9;
            int var360 = Double.compare(var111, var647);
            if (var360 < 0) {
                double var649 = var339;
                double var651 = Math.sin(var649);
                double var363 = (-0.62 * var651);
                double var653 = 6.283185307179586;
                double var365 = (var363 * var653);
                int var655 = var318;
                double var656 = ((double) var655);
                double var367 = (var365 * var656);
                double var658 = 0.78;
                double var369 = (var367 / var658);
                double var660 = var325;
                double var371 = (var369 * var660);
                double var662 = 1.0;
                double var846 = var347;
                double var664 = (var662 - var846);
                var412 = (var371 * var664);
            } else {
                var412 = 0.0;
            }
            double var99 = var412;
            double var666 = var155;
            double var375 = (0.03 * var666);
            double var668 = var339;
            double var670 = Math.cos(var668);
            double var377 = (var375 * var670);
            double var672 = var325;
            double var379 = (var377 * var672);
            double var674 = 1.0;
            double var848 = var347;
            double var676 = (var674 - var848);
            double var381 = (var379 * var676);
            double var678 = 1.6;
            double var850 = var111;
            double var938 = 0.5;
            double var852 = (var850 - var938);
            double var940 = 0.5;
            double var854 = (var852 / var940);
            double var856 = GeometryPrimitives.computeDoubleMetric(var854);
            double var680 = (var678 * var856);
            double var385 = (1.3 - var680);
            double var682 = 1.0;
            double var858 = var111;
            double var942 = 0.8;
            double var860 = (var858 - var942);
            double var944 = 0.2;
            double var862 = (var860 / var944);
            double var864 = GeometryPrimitives.computeDoubleMetric(var862);
            double var684 = (var682 - var864);
            double var389 = (var147 * var684);
            double[] var462 = var53;
            int var686 = 0;
            double var463 = var462[var686];
            double[] var732 = var53;
            int var866 = 1;
            double var733 = var732[var866];
            double[] var900 = var53;
            int var946 = 2;
            double var901 = var900[var946];
            double var961 = var43;
            double var392 = KillEffectMathUtils.computeDoubleMetric(var135, var463, var733, var901, var961);
            double[] var687 = var53;
            double var735 = var155;
            double var903 = var127;
            double var963 = var111;
            int var982 = var138;
            this.drawGeometryPart(arg0, var687, var735, var903, var963, var982, var142, var392);
            double[] var688 = var53;
            double var737 = var155;
            double var905 = var127;
            double[] var965 = var62;
            double var969 = var111;
            double var984 = var389;
            double var986 = (var984 * var392);
            int var1000 = var138;
            this.drawGeometryPart(arg0, var688, var737, var905, var965, var969, var986, var1000, var142);
            CosmeticDrawBuilder var397 = arg0.drawGeometryPart();
            double[] var467 = var53;
            int var689 = 0;
            double var468 = var467[var689];
            double[] var739 = var53;
            int var867 = 1;
            double var740 = var739[var867];
            double var907 = var381;
            double var742 = (var740 + var907);
            double[] var909 = var53;
            int var947 = 2;
            double var910 = var909[var947];
            CosmeticDrawBuilder var399 = arg0.drawGeometryPart(var468, var742, var910);
            int var690 = 0;
            double var471 = var277[var690];
            CosmeticDrawBuilder var400 = var399.drawGeometryPart2(var471);
            int var691 = 1;
            double var474 = var277[var691];
            CosmeticDrawBuilder var401 = var400.drawGeometryPart3(var474);
            int var692 = 2;
            double var477 = var277[var692];
            CosmeticDrawBuilder var402 = var401.drawGeometryPart4(var477);
            double[] var404 = arg0.computeVector1();
            double var693 = var155;
            double var868 = var389;
            double var948 = var392;
            double var870 = (var868 * var948);
            int var950 = var138;
            int var966 = var140;
            int var971 = var142;
            double[] var983 = var404;
            double var988 = var43;
            this.drawGeometryPart(arg0, var693, var870, var950, var966, var971, var983, var988);
            double var695 = var155;
            double var872 = var168;
            double var951 = var115;
            double var972 = var147;
            double var990 = var392;
            double var974 = (var972 * var990);
            double var992 = var385;
            int var1001 = var138;
            int var1007 = var140;
            this.drawGeometryPart(arg0, var695, var872, var951, var974, var992, var1001, var1007, var142, var404, var43);
            double var697 = 1.0;
            double var874 = var155;
            double var953 = var325;
            double var976 = var353;
            double var994 = var99;
            double var1002 = var115;
            double var1009 = var147;
            double var1019 = var392;
            double var1011 = (var1009 * var1019);
            double var1021 = var385;
            this.drawGeometryPart(arg0, var697, var874, var953, var976, var994, var1002, var1011, var1021, var138, var140, var142, var404, var43);
            double var699 = -1.0;
            double var876 = var155;
            double var955 = var325;
            double var978 = var353;
            double var996 = var99;
            double var1004 = var115;
            double var1013 = var147;
            double var1023 = var392;
            double var1015 = (var1013 * var1023);
            this.drawGeometryPart(arg0, var699, var876, var955, var978, var996, var1004, var1015, var385, var138, var140, var142, var404, var43);
            CosmeticDrawBuilder var410 = arg0.drawGeometryPart1();
            double[] var701 = var135;
            IGroundHeightSampler var744 = var125;
            double var878 = var121;
            double var957 = var127;
            double var980 = var115;
            double var998 = var111;
            int var1006 = var138;
            int var1008 = var142;
            double var1017 = var43;
            this.drawGeometryPart(arg0, var701, var744, var878, var957, var980, var998, var1006, var1008, var1017);
            return;
        }
    }

    private static double computeDoubleMetric(double arg0) {
        double var15 = 0.0;
        if (Double.compare(arg0, 0.3) < 0) {
            double var27 = GeometryPrimitives.computeDoubleMetric((arg0 / 0.3));
            double var19 = (0.65 * var27);
            var15 = (0.35 + var19);
        } else {
            double var35 = Math.pow(((arg0 - 0.3) / 0.7), 1.4);
            double var23 = (0.97 * var35);
            var15 = (1.0 - var23);
        }
        return var15;
    }

    private void drawGeometryPart(CosmeticDrawBuilder arg0, double[] arg1, double[] arg2, double arg3, double arg4, double[] arg5, double[] arg6, double arg7, int arg8, double arg9, double arg10, int arg11, int arg12, int arg13, double[] arg14, double arg15) {
        int var39 = 0;
        int var43 = 0;
        int var313 = 0;
        int var108 = 0;
        if (Double.compare(arg9, 0.01) >= 0) {
            if (Double.compare(arg3, 1.0E-4) < 0) {
            }
        }
        return;
        double[][] var57 = new double[(arg8 + 1)][];
        var39 = 0;
        while (true) {
            if (var39 > arg8) break;
            double var61 = ((double) var39);
            double var141 = ((double) arg8);
            double var63 = (var61 / var141);
            int var115 = var39;
            double var220 = (arg3 * var63);
            double[] var144 = computeVector(arg1, arg2, var220);
            double var228 = (((arg7 * arg3) * var63) * var63);
            double[] var145 = computeVector(var144, arg6, var228);
            var57[var115] = var145;
            var39 = (var39 + 1);
        }
        int var231 = (arg8 / 2);
        double var204 = var57[var231][0];
        int var277 = (arg8 / 2);
        double var255 = var57[var277][1];
        int var298 = (arg8 / 2);
        double var289 = var57[var298][2];
        double var147 = KillEffectMathUtils.computeDoubleMetric(arg14, var204, var255, var289, arg15);
        double var68 = (arg9 * var147);
        if (Double.compare(var68, 0.01) < 0) {
            return;
        }
        double[][] var75 = new double[(arg8 + 1)][];
        double[][] var78 = new double[(arg8 + 1)][];
        int[] var81 = new int[(arg8 + 1)];
        var43 = 0;
        while (true) {
            if (var43 > arg8) break;
            double var85 = ((double) var43);
            double var152 = ((double) arg8);
            double var87 = (var85 / var152);
            int var122 = Math.min((var43 + 1), arg8);
            double[] var90 = var57[var122];
            int var158 = Math.max((var43 - 1), 0);
            double[] var124 = var57[var158];
            double[] var92 = computeVector(GeometryPrimitives.computeVector(var90, var124), arg2);
            double var162 = -(GeometryPrimitives.computeDoubleMetric(arg5, var92));
            double[] var94 = computeVector(arg5, var92, var162);
            double[] var166 = GeometryPrimitives.computeVector(0.0, 1.0, 0.0);
            double[] var128 = GeometryPrimitives.computeVector1(var92, var166);
            double[] var169 = GeometryPrimitives.computeVector(1.0, 0.0, 0.0);
            double[] var129 = computeVector(var128, var169);
            double[] var95 = computeVector(var94, var129);
            double var98 = (0.5 * arg4);
            double var174 = computeDoubleMetric(var87);
            double var100 = (var98 * var174);
            int var130 = var43;
            int var209 = var43;
            double[] var178 = computeVector(var57[var209], var95, var100);
            var75[var130] = var178;
            int var131 = var43;
            int var211 = var43;
            double[] var180 = var57[var211];
            double var241 = -(var100);
            double[] var181 = computeVector(var180, var95, var241);
            var78[var131] = var181;
            if (Double.compare(var87, 0.25) < 0) {
                double var196 = (var87 / 0.25);
                var108 = ColorMathUtils.computeColorOrIndex(arg11, arg12, var196);
            } else {
                double var190 = GeometryPrimitives.computeDoubleMetric(((var87 - 0.25) / 0.6));
                var108 = ColorMathUtils.computeColorOrIndex(arg12, arg13, var190);
            }
            int var50 = var108;
            int var133 = var43;
            double var263 = GeometryPrimitives.computeDoubleMetric(((arg10 - var87) / 0.3));
            double var215 = (var68 * var263);
            int var193 = ColorMathUtils.computeColorOrIndex5(var50, var215);
            var81[var133] = var193;
            var43 = (var43 + 1);
        }
        var313 = 0;
        while (true) {
            if (var313 >= arg8) break;
            int var198 = var313;
            double[] var137 = var75[var198];
            int var217 = var313;
            double[] var200 = var78[var217];
            int var250 = (var313 + 1);
            double[] var219 = var78[var250];
            int var267 = (var313 + 1);
            double[] var252 = var75[var267];
            int var284 = var313;
            int var269 = var81[var284];
            int var295 = var313;
            int var286 = var81[var295];
            int var301 = (var313 + 1);
            int var297 = var81[var301];
            int var308 = (var313 + 1);
            int var303 = var81[var308];
            arg0.applyStep(var137, var200, var219, var252, var269, var286, var297, var303);
            var313 = (var313 + 1);
        }
    }

    private double computeDoubleMetric(int arg0, double arg1) {
        double var38 = GeometryPrimitives.computeDoubleMetric(arg0);
        double var32 = (7.0 * var38);
        double var26 = (7.0 + var32);
        double var20 = (arg1 * var26);
        double var36 = GeometryPrimitives.computeDoubleMetric((arg0 + 1));
        double var30 = (6.283185307179586 * var36);
        double var24 = Math.sin((var20 + var30));
        double var18 = (0.5 * var24);
        return (0.5 + var18);
    }

    private void drawGeometryPart(CosmeticDrawBuilder arg0, double arg1, double arg2, int arg3, int arg4, int arg5, double[] arg6, double arg7) {
        int var385 = 0;
        double var148 = 0.0;
        int var48 = 0;
        if (Double.compare(arg2, 0.01) < 0) {
            return;
        }
        double[][] var58 = new double[5][];
        var48 = 0;
        while (true) {
            if (var48 >= 5) break;
            double var64 = (((double) var48) / 4.0);
            int var96 = var48;
            double var354 = Math.sin((3.141592653589793 * var64));
            double var312 = (0.03 * var354);
            double var242 = (0.02 + var312);
            double var180 = (arg1 * var242);
            double var358 = (0.62 * var64);
            double var316 = (-0.32 + var358);
            double var246 = (arg1 * var316);
            double[] var114 = GeometryPrimitives.computeVector(0.0, var180, var246);
            var58[var96] = var114;
            var48 = (var48 + 1);
        }
        DoubleUnaryOperator var117 = (p0) -> PhoenixKillRenderer.lambda$c(arg1, p0);
        IGradientColorSampler var183 = (p0, p1) -> PhoenixKillRenderer.lambda$a(arg5, arg4, arg3, arg2, p0, p1);
        GeometryPrimitives.drawGeometryPart(arg0, var58, var117, 6, var183, true, true);
        double[][] var69 = new double[4][];
        double var209 = (arg1 * 0.03);
        double var278 = (arg1 * 0.27);
        double[] var152 = GeometryPrimitives.computeVector(0.0, var209, var278);
        var69[0] = var152;
        double var213 = (arg1 * 0.11);
        double var284 = (arg1 * 0.36);
        double[] var155 = GeometryPrimitives.computeVector(0.0, var213, var284);
        var69[1] = var155;
        double var217 = (arg1 * 0.2);
        double var290 = (arg1 * 0.41);
        double[] var158 = GeometryPrimitives.computeVector(0.0, var217, var290);
        var69[2] = var158;
        double var221 = (arg1 * 0.25);
        double var296 = (arg1 * 0.47);
        double[] var161 = GeometryPrimitives.computeVector(0.0, var221, var296);
        var69[3] = var161;
        DoubleUnaryOperator var124 = (p0) -> PhoenixKillRenderer.lambda$b(arg1, p0);
        IGradientColorSampler var185 = (p0, p1) -> PhoenixKillRenderer.lambda$f(arg4, arg3, arg2, p0, p1);
        GeometryPrimitives.drawGeometryPart(arg0, var69, var124, 5, var185, false, false);
        double var165 = (arg1 * 0.26);
        double var229 = (arg1 * 0.52);
        double var302 = (arg1 * 0.058);
        double var346 = (arg1 * 0.058);
        double var366 = (arg1 * 0.072);
        IGradientColorSampler var380 = (p0, p1) -> PhoenixKillRenderer.lambda$e(arg4, arg3, arg2, p0, p1);
        GeometryPrimitives.drawGeometryPart(arg0, 0.0, var165, var229, var302, var346, var366, 6, 3, var380);
        double var169 = (arg1 * 0.255);
        double var235 = (arg1 * 0.565);
        double[] var103 = GeometryPrimitives.computeVector(0.0, var169, var235);
        double var188 = (arg1 * 0.232);
        double var257 = (arg1 * 0.675);
        double[] var127 = GeometryPrimitives.computeVector(0.0, var188, var257);
        double var173 = (arg1 * 0.028);
        IGradientColorSampler var260 = (p0, p1) -> PhoenixKillRenderer.lambda$d(arg4, arg5, arg2, p0, p1);
        GeometryPrimitives.drawGeometryPart(arg0, var103, var127, var173, 4, var260);
        var385 = 0;
        while (true) {
            if (var385 >= 3) break;
            double[] var87 = GeometryPrimitives.computeVector((Math.sin((((double) (var385 - 1)) * 0.3)) * 0.5), 0.55, -0.8);
            double[] var108 = GeometryPrimitives.computeVector(0.0, 0.0, -1.0);
            double[] var88 = computeVector(var87, var108);
            if (var385 == 1) {
                var148 = 0.3;
            } else {
                var148 = 0.23;
            }
            double var91 = (arg1 * var148);
            int var310 = (this.killEffectRenderSpec.seed() * 71);
            int var324 = (var385 * 5);
            int var311 = (var310 + var324);
            double var327 = this.killEffectRenderSpec.age();
            double var262 = this.computeDoubleMetric(var311, var327);
            double var194 = (0.1 * var262);
            double var137 = (0.9 + var194);
            double var93 = (var91 * var137);
            double var145 = ((((double) (var385 - 1)) * 0.012) * arg1);
            double var202 = (arg1 * 0.305);
            double var268 = (arg1 * 0.5);
            double[] var147 = GeometryPrimitives.computeVector(var145, var202, var268);
            double var272 = (arg1 * 0.035);
            double[] var335 = GeometryPrimitives.computeVector(1.0, 0.0, 0.0);
            double[] var351 = GeometryPrimitives.computeVector(0.0, 1.0, 0.0);
            this.drawGeometryPart(arg0, var147, var88, var93, var272, var335, var351, 0.1, 2, arg2, 1.5, arg3, arg4, arg5, arg6, arg7);
            var385 = (var385 + 1);
        }
    }

    private void drawGeometryPart(CosmeticDrawBuilder arg0, double arg1, double arg2, double arg3, double arg4, double arg5, int arg6, int arg7, int arg8, double[] arg9, double arg10) {
        int var42 = 0;
        int var55 = 0;
        double var131 = (arg1 * 0.01);
        double var206 = (arg1 * -0.3);
        double[] var62 = GeometryPrimitives.computeVector(0.0, var131, var206);
        var42 = 0;
        while (true) {
            if (var42 >= 7) break;
            int var68 = (this.killEffectRenderSpec.seed() * 73);
            int var124 = (var42 * 11);
            int var69 = (var68 + var124);
            double var73 = (((double) var42) - 3.0);
            double var77 = (var73 * 0.13);
            double var80 = this.computeDoubleMetric(var69, arg3);
            int var127 = (var69 + 5);
            double var83 = this.computeDoubleMetric(var127, arg3);
            double var87 = (arg1 * arg2);
            double var269 = Math.abs(var73);
            double var210 = (0.22 * var269);
            double var147 = (1.3 - var210);
            double var89 = (var87 * var147);
            double var214 = (0.12 * var80);
            double var151 = (0.88 + var214);
            double var91 = (var89 * var151);
            double[][] var94 = new double[8][];
            var55 = 0;
            while (true) {
                if (var55 > 7) break;
                double var100 = (((double) var55) / 7.0);
                double var106 = ((0.1 * var91) * var100);
                double var161 = (8.168140899333462 * var100);
                double var220 = (arg3 * 5.0);
                double var163 = (var161 - var220);
                double var225 = (((double) var42) * 0.9);
                double var167 = Math.sin((var163 + var225));
                double var108 = (var106 * var167);
                double var114 = ((0.08 * var91) * var100);
                double var175 = (6.911503837897546 * var100);
                double var231 = (arg3 * 4.0);
                double var177 = (var175 - var231);
                double var236 = (((double) var42) * 0.7);
                double var181 = Math.sin((var177 + var236));
                double var116 = (var114 * var181);
                double var189 = (((0.15 * var91) * var100) * var100);
                double var118 = (var116 - var189);
                int var129 = var55;
                double var192 = var62[0];
                double var250 = ((Math.sin(var77) * var91) * var100);
                double var196 = ((var192 + var250) + var108);
                double var257 = (var62[1] + var118);
                double var288 = var62[2];
                double var306 = ((Math.cos(var77) * var91) * var100);
                double var290 = (var288 - var306);
                double[] var198 = GeometryPrimitives.computeVector(var196, var257, var290);
                var94[var129] = var198;
                var55 = (var55 + 1);
            }
            double var308 = Math.abs(var73);
            double var294 = (0.01 * var308);
            double var261 = (0.075 - var294);
            double var202 = (arg1 * var261);
            double var312 = (0.22 * var83);
            double var298 = (0.78 + var312);
            double var265 = (arg4 * var298);
            this.drawGeometryPart(arg0, var94, var202, var265, arg5, arg6, arg7, arg8, arg9, arg10);
            var42 = (var42 + 1);
        }
    }

    private void drawGeometryPart(CosmeticDrawBuilder arg0, double[][] arg1, double arg2, double arg3, double arg4, int arg5, int arg6, int arg7, double[] arg8, double arg9) {
        int var261 = 0;
        int var31 = 0;
        int var89 = 0;
        int var39 = arg1.length;
        if (Double.compare(arg3, 0.01) < 0) {
            return;
        }
        int var188 = (var39 / 2);
        double var167 = arg1[var188][0];
        int var231 = (var39 / 2);
        double var212 = arg1[var231][1];
        int var248 = (var39 / 2);
        double var239 = arg1[var248][2];
        double var112 = KillEffectMathUtils.computeDoubleMetric(arg8, var167, var212, var239, arg9);
        double var45 = (arg3 * var112);
        if (Double.compare(var45, 0.01) < 0) {
            return;
        }
        double[][] var51 = new double[var39][];
        double[][] var53 = new double[var39][];
        int[] var55 = new int[var39];
        var31 = 0;
        while (true) {
            if (var31 >= var39) break;
            double var59 = ((double) var31);
            double var119 = (((double) var39) - 1.0);
            double var61 = (var59 / var119);
            int var91 = (var31 + 1);
            int var123 = (var39 - 1);
            int var92 = Math.min(var91, var123);
            double[] var64 = arg1[var92];
            int var126 = Math.max((var31 - 1), 0);
            double[] var94 = arg1[var126];
            double[] var65 = GeometryPrimitives.computeVector(var64, var94);
            double[] var97 = GeometryPrimitives.computeVector(0.0, 0.0, -1.0);
            double[] var66 = computeVector(var65, var97);
            double[] var69 = GeometryPrimitives.computeVector(1.0, 0.0, 0.0);
            double var132 = -(var66[0]);
            double[] var70 = computeVector(var69, var66, var132);
            double[] var101 = GeometryPrimitives.computeVector(1.0, 0.0, 0.0);
            double[] var71 = computeVector(var70, var101);
            double var74 = (0.5 * arg2);
            double var138 = computeDoubleMetric(var61);
            double var76 = (var74 * var138);
            int var102 = var31;
            int var177 = var31;
            double[] var142 = computeVector(arg1[var177], var71, var76);
            var51[var102] = var142;
            int var103 = var31;
            int var179 = var31;
            double[] var144 = arg1[var179];
            double var198 = -(var76);
            double[] var145 = computeVector(var144, var71, var198);
            var53[var103] = var145;
            if (Double.compare(var61, 0.2) < 0) {
                double var150 = (var61 / 0.2);
                var89 = ColorMathUtils.computeColorOrIndex(arg5, arg6, var150);
            } else {
                double var160 = GeometryPrimitives.computeDoubleMetric(((var61 - 0.2) / 0.65));
                var89 = ColorMathUtils.computeColorOrIndex(arg6, arg7, var160);
            }
            int var38 = var89;
            int var105 = var31;
            double var224 = GeometryPrimitives.computeDoubleMetric(((arg4 - var61) / 0.3));
            double var183 = (var45 * var224);
            int var153 = ColorMathUtils.computeColorOrIndex5(var38, var183);
            var55[var105] = var153;
            var31 = (var31 + 1);
        }
        var261 = 0;
        while (true) {
            int var87 = var261;
            int var108 = (var39 - 1);
            if (var87 >= var108) break;
            int var163 = var261;
            double[] var110 = var51[var163];
            int var185 = var261;
            double[] var165 = var53[var185];
            int var207 = (var261 + 1);
            double[] var187 = var53[var207];
            int var228 = (var261 + 1);
            double[] var209 = var51[var228];
            int var234 = var261;
            int var230 = var55[var234];
            int var245 = var261;
            int var236 = var55[var245];
            int var251 = (var261 + 1);
            int var247 = var55[var251];
            int var258 = (var261 + 1);
            int var253 = var55[var258];
            arg0.applyStep(var110, var165, var187, var209, var230, var236, var247, var253);
            var261 = (var261 + 1);
        }
    }

    private static double[] computeVector(double arg0, double[] arg1, double[] arg2) {
        double[] var15 = GeometryPrimitives.computeVector1(arg1, arg2);
        double[] var26 = GeometryPrimitives.computeVector(0.0, 0.0, -1.0);
        double[] var16 = computeVector(var15, var26);
        double var19 = -(arg0);
        double var27 = var16[0];
        double var21 = (var19 * var27);
        double var31 = -(arg0);
        double var36 = var16[1];
        double var33 = (var31 * var36);
        double var40 = -(arg0);
        double var45 = var16[2];
        double var42 = (var40 * var45);
        return GeometryPrimitives.computeVector(var21, var33, var42);
    }

    private void drawGeometryPart(CosmeticDrawBuilder arg0, double arg1, double arg2, double arg3, double arg4, double arg5, double arg6, double arg7, double arg8, int arg9, int arg10, int arg11, double[] arg12, double arg13) {
        int var85 = 0;
        int var676 = 0;
        int var677 = 0;
        int var293 = 0;
        int var96 = Double.compare(arg7, 0.01);
        if (var96 < 0) {
            return;
        }
        double var296 = (1.0 - arg3);
        double var99 = (1.25 * var296);
        double var422 = arg3;
        double var300 = (0.3 * var422);
        double var101 = (var99 + var300);
        double var105 = Math.cos(arg4);
        double var109 = Math.sin(arg4);
        double var113 = Math.cos(var101);
        double var117 = Math.sin(var101);
        double var121 = (arg1 * var113);
        double var123 = (var121 * var105);
        double var424 = var109;
        double var308 = (var113 * var424);
        double var426 = var117;
        double var428 = -(var426);
        double[] var125 = GeometryPrimitives.computeVector(var123, var308, var428);
        double[] var249 = GeometryPrimitives.computeVector(arg1, 0.0, 0.0);
        double[] var126 = computeVector(var125, var249);
        double var129 = -(arg1);
        double var131 = (var129 * var109);
        double var430 = 0.0;
        double[] var133 = GeometryPrimitives.computeVector(var131, var105, var430);
        double[] var405 = var133;
        double[] var136 = computeVector(arg1, var126, var405);
        double var139 = (0.3 * arg3);
        double var252 = Math.cos(var139);
        double[] var406 = var136;
        double var432 = var139;
        double var434 = Math.sin(var432);
        double[] var142 = computeVector(var126, var252, var406, var434);
        double[] var143 = computeVector(var142, var126);
        double[] var407 = var133;
        double[] var146 = computeVector(arg1, var143, var407);
        double var149 = (0.3 * arg2);
        double var153 = (0.27 * arg2);
        double var157 = (arg1 * 0.09);
        double var159 = (var157 * arg2);
        double var436 = arg2;
        double var328 = (0.03 * var436);
        double var438 = 0.1;
        double var440 = (var438 * arg2);
        double[] var161 = GeometryPrimitives.computeVector(var159, var328, var440);
        double var330 = var149;
        double[] var163 = computeVector(var161, var126, var330);
        double var332 = var153;
        double[] var165 = computeVector(var163, var143, var332);
        double var334 = -0.03;
        double var442 = arg5;
        double var336 = (var334 * var442);
        double var444 = -0.3;
        double var507 = 0.3;
        double var338 = KillEffectMathUtils.computeDoubleMetric(var336, var444, var507);
        double var168 = (-0.08 + var338);
        double var340 = 0.3;
        double var446 = 0.7;
        double var509 = arg3;
        double var448 = (var446 * var509);
        double var342 = (var340 + var448);
        double var170 = (var168 * var342);
        KillEffectRenderSpec var173 = this.killEffectRenderSpec;
        int var174 = var173.seed();
        int var175 = (var174 * 79);
        double var408 = 0.0;
        int var260 = Double.compare(arg1, var408);
        if (var260 > 0) {
            var293 = 0;
        } else {
            var293 = 500;
        }
        int var176 = (var175 + var293);
        double var344 = 0.45;
        int var178 = ColorMathUtils.computeColorOrIndex(arg9, arg10, var344);
        double[][] var263 = new double[3][];
        int var410 = 0;
        double[] var450 = var161;
        var263[var410] = var450;
        int var411 = 1;
        double[] var451 = var163;
        var263[var411] = var451;
        int var412 = 2;
        double[] var452 = var165;
        var263[var412] = var452;
        double var346 = arg2;
        DoubleUnaryOperator var348 = (p0) -> PhoenixKillRenderer.lambda$a(var346, p0);
        int var413 = 4;
        int var453 = arg9;
        int var505 = arg10;
        double var511 = arg7;
        IGradientColorSampler var454 = (p0, p1) -> PhoenixKillRenderer.lambda$a(var453, var505, var511, arg8, p0, p1);
        int var506 = 0;
        GeometryPrimitives.drawGeometryPart(arg0, var263, var348, var413, var454, var506, false);
        double var349 = 0.88;
        double var455 = arg3;
        double var351 = (var349 * var455);
        double var182 = (0.12 + var351);
        var85 = 0;
        while (true) {
            int var185 = var85;
            if (var185 >= 7) break;
            int var186 = var85;
            double var187 = ((double) var186);
            double var353 = 6.0;
            double var189 = (var187 / var353);
            int var355 = var85;
            int var414 = 7;
            int var356 = (var355 * var414);
            int var266 = (var176 + var356);
            int var357 = 100;
            int var267 = (var266 + var357);
            double var358 = arg6;
            double var192 = this.computeDoubleMetric(var267, var358);
            double var360 = 0.22;
            double var457 = var189;
            double var362 = (var360 * var457);
            double var196 = (1.32 - var362);
            double var364 = var182;
            double var198 = (var196 * var364);
            double var270 = Math.cos(var198);
            double[] var415 = var136;
            double var459 = var198;
            double var461 = Math.sin(var459);
            double[] var201 = computeVector(var126, var270, var415, var461);
            double var366 = var149;
            double var463 = 0.12;
            double var516 = (0.84 * var189);
            double var465 = (var463 + var516);
            double var368 = (var366 * var465);
            double[] var203 = computeVector(var161, var126, var368);
            double[] var205 = GeometryPrimitives.computeVector1(var201, var133);
            double[] var370 = var203;
            double[] var416 = var201;
            double var467 = arg2;
            double var560 = (0.1 * var189);
            double var520 = (0.27 + var560);
            double var469 = (var467 * var520);
            double var564 = (0.1 * var192);
            double var524 = (0.9 + var564);
            double var471 = (var469 * var524);
            double var528 = (arg2 * 0.105);
            int var53 = var85;
            int var660 = (var53 * 7);
            int var652 = (var176 + var660);
            int var653 = (var652 + 101);
            double var644 = this.computeDoubleMetric(var653, arg6);
            double var633 = (0.2 * var644);
            double var619 = (0.8 + var633);
            double var609 = (arg7 * var619);
            this.drawGeometryPart(arg0, var370, var416, var471, var528, var205, var133, var170, 3, var609, arg8, var178, arg10, arg11, arg12, arg13);
            var85 = (var85 + 1);
        }
        var676 = 0;
        while (true) {
            int var208 = var676;
            if (var208 >= 8) break;
            int var209 = var676;
            double var210 = ((double) var209);
            double var371 = 7.0;
            double var212 = (var210 / var371);
            int var373 = var676;
            int var417 = 7;
            int var374 = (var373 * var417);
            int var277 = (var176 + var374);
            double var375 = arg6;
            double var215 = this.computeDoubleMetric(var277, var375);
            double var377 = 0.78;
            double var473 = var212;
            double var379 = (var377 * var473);
            double var219 = (0.06 + var379);
            double var381 = var182;
            double var221 = (var219 * var381);
            double var280 = Math.cos(var221);
            double[] var418 = var146;
            double var475 = var221;
            double var477 = Math.sin(var475);
            double[] var224 = computeVector(var143, var280, var418, var477);
            double var383 = var153;
            double var479 = 1.0;
            double var532 = (0.85 * var212);
            double var481 = (var479 - var532);
            double var385 = (var383 * var481);
            double[] var226 = computeVector(var163, var143, var385);
            double[] var228 = GeometryPrimitives.computeVector1(var224, var133);
            double[] var387 = var226;
            double[] var419 = var224;
            double var483 = arg2;
            double var573 = (0.12 * var212);
            double var536 = (0.44 - var573);
            double var485 = (var483 * var536);
            double var577 = (0.12 * var215);
            double var540 = (0.88 + var577);
            double var487 = (var485 * var540);
            double var544 = (arg2 * 0.088);
            double var601 = (var170 * 1.3);
            int var666 = var676;
            int var667 = (var666 * 7);
            int var656 = (var176 + var667);
            int var657 = (var656 + 1);
            double var648 = this.computeDoubleMetric(var657, arg6);
            double var638 = (0.22 * var648);
            double var625 = (0.78 + var638);
            double var613 = (arg7 * var625);
            this.drawGeometryPart(arg0, var387, var419, var487, var544, var228, var133, var601, 3, var613, arg8, arg9, arg10, arg11, arg12, arg13);
            var676 = (var676 + 1);
        }
        var677 = 0;
        while (true) {
            int var231 = var677;
            if (var231 >= 6) break;
            int var232 = var677;
            double var233 = ((double) var232);
            double var388 = 5.0;
            double var235 = (var233 / var388);
            double var390 = 0.22;
            double var489 = var235;
            double var392 = (var390 * var489);
            double var239 = (1.32 - var392);
            double var394 = var182;
            double var241 = (var239 * var394);
            double var288 = Math.cos(var241);
            double[] var420 = var136;
            double var491 = var241;
            double var493 = Math.sin(var491);
            double[] var244 = computeVector(var126, var288, var420, var493);
            double var396 = var149;
            double var495 = 0.1;
            double var548 = (0.8 * var235);
            double var497 = (var495 + var548);
            double var398 = (var396 * var497);
            double[] var246 = computeVector(var161, var126, var398);
            double var400 = 0.012;
            double var499 = arg2;
            double var402 = (var400 * var499);
            double[] var247 = computeVector(var246, var133, var402);
            double[] var404 = var247;
            double[] var421 = var244;
            double var501 = arg2;
            double var503 = (var501 * 0.17);
            double var554 = (arg2 * 0.09);
            double[] var587 = GeometryPrimitives.computeVector1(var244, var133);
            double var617 = (arg7 * 0.95);
            this.drawGeometryPart(arg0, var404, var421, var503, var554, var587, var133, var170, 1, var617, arg8, arg9, arg9, arg10, arg12, arg13);
            var677 = (var677 + 1);
        }
    }

    private void drawGeometryPart(CosmeticDrawBuilder arg0, IGroundHeightSampler arg1, double arg2, double arg3, int arg4, int arg5, int arg6) {
        double var94 = GeometryPrimitives.computeDoubleMetric((arg3 / 0.05));
        double var38 = (0.36 * var94);
        double var158 = GeometryPrimitives.computeDoubleMetric(((arg3 - 0.35) / 0.5));
        double var98 = (1.0 - var158);
        double var40 = (var38 * var98);
        if (Double.compare(var40, 0.01) > 0) {
            int var46 = ColorMathUtils.computeColorOrIndex8(arg6, 0.9);
            double var162 = (0.85 * arg2);
            IGradientColorSampler var222 = (p0, p1) -> PhoenixKillRenderer.lambda$a(var46, var40, p0, p1);
            KillEffectMathUtils.drawGeometryPart(arg0, arg1, 0.03, var162, 16, var222);
        }
        if (Double.compare(arg3, 0.1) < 0) {
            double var53 = (arg3 / 0.1);
            double var166 = GeometryPrimitives.computeDoubleMetric(var53);
            double var110 = (1.0 - var166);
            double var57 = (0.9 * var110);
            double var116 = GeometryPrimitives.computeDoubleMetric((arg3 / 0.008));
            double var59 = (var57 * var116);
            if (Double.compare(var59, 0.01) > 0) {
                double var227 = (0.9 * var53);
                double var203 = (0.5 + var227);
                double var172 = (arg2 * var203);
                IGradientColorSampler var224 = (p0, p1) -> PhoenixKillRenderer.lambda$c(arg4, arg6, var59, p0, p1);
                KillEffectMathUtils.drawGeometryPart(arg0, arg1, 0.06, var172, 16, var224);
            }
            double var69 = KillEffectMathUtils.computeDoubleMetric((arg3 / 0.34), 0.0, 1.0);
            double var130 = Math.pow((1.0 - var69), 1.2);
            double var73 = (0.95 * var130);
            double var136 = GeometryPrimitives.computeDoubleMetric((arg3 / 0.01));
            double var75 = (var73 * var136);
            if (Double.compare(var75, 0.01) > 0) {
                double var234 = Math.pow((1.0 - var69), 2.2);
                double var208 = (1.0 - var234);
                double var184 = (1.45 * var208);
                double var142 = (0.25 + var184);
                double var82 = (arg2 * var142);
                double var212 = (0.5 * var69);
                double var188 = (1.0 - var212);
                double var148 = ((0.1 * var188) + 0.03);
                double var86 = (arg2 * var148);
                double var216 = (var82 - var86);
                double var194 = Math.max(0.0, var216);
                double var242 = (var86 * 0.5);
                double var220 = (var82 + var242);
                IGradientColorSampler var246 = (p0, p1) -> PhoenixKillRenderer.lambda$b(arg6, arg5, var75, p0, p1);
                KillEffectMathUtils.drawGeometryPart(arg0, arg1, 0.04, var194, var220, 28, var246);
            }
            return;
        }
    }

    private void drawGeometryPart(CosmeticDrawBuilder arg0, double[] arg1, double arg2, double arg3, double arg4, int arg5, int arg6, double arg7) {
        double var68 = GeometryPrimitives.computeDoubleMetric((arg4 / 0.05));
        double var34 = (0.3 * var68);
        double var99 = GeometryPrimitives.computeDoubleMetric(((arg4 - 0.55) / 0.35));
        double var72 = (1.0 - var99);
        double var38 = ((var34 * var72) * arg7);
        if (Double.compare(var38, 0.01) < 0) {
            return;
        }
        double var44 = arg1[1];
        double var80 = (0.3 * arg2);
        double var46 = (var44 - var80);
        double var86 = ((arg3 + 0.03) + 0.02);
        double var48 = Math.max(var46, var86);
        double var52 = (0.85 * arg2);
        CosmeticDrawBuilder var55 = arg0.drawGeometryPart();
        double var62 = arg1[0];
        double var107 = arg1[2];
        CosmeticDrawBuilder var57 = arg0.drawGeometryPart(var62, 0.0, var107);
        IGradientColorSampler var114 = (p0, p1) -> PhoenixKillRenderer.lambda$a(arg5, arg6, var38, p0, p1);
        GeometryPrimitives.drawGeometryPart(arg0, var48, var52, 14, var114);
        CosmeticDrawBuilder var60 = arg0.drawGeometryPart1();
    }

    private void drawGeometryPart(CosmeticDrawBuilder arg0, double[] arg1, double arg2, double arg3, double[] arg4, double arg5, double arg6, int arg7, int arg8) {
        double[] var93 = null;
        int var37 = 0;
        int var40 = 0;
        double var184 = GeometryPrimitives.computeDoubleMetric(((arg5 - 0.6) / 0.35));
        double var149 = (1.0 - var184);
        double var109 = (0.2 * var149);
        double var41 = (arg6 * var109);
        if (Double.compare(var41, 0.01) < 0) {
            return;
        }
        if (Double.compare(Math.abs(arg4[1]), 0.97) > 0) {
            var93 = GeometryPrimitives.computeVector(1.0, 0.0, 0.0);
        } else {
            var93 = GeometryPrimitives.computeVector(0.0, 1.0, 0.0);
        }
        double[] var56 = GeometryPrimitives.computeVector1(var93, arg4);
        double[] var97 = GeometryPrimitives.computeVector(1.0, 0.0, 0.0);
        double[] var57 = computeVector(var56, var97);
        double[] var59 = GeometryPrimitives.computeVector1(arg4, var57);
        double var62 = (0.95 * arg2);
        double var124 = ((arg1[1] - arg3) - 0.05);
        double var64 = Math.min(var62, var124);
        if (Double.compare(var64, 0.05) < 0) {
            return;
        }
        double var159 = Math.min(arg2, var64);
        double var130 = (-0.12 * var159);
        double[] var70 = computeVector(arg1, arg4, var130);
        double[][] var72 = new double[15][];
        var37 = 0;
        while (true) {
            if (var37 > 14) break;
            double var81 = ((((double) (var37 % 14)) / 14.0) * 6.283185307179586);
            int var102 = var37;
            double var165 = (Math.cos(var81) * var64);
            double[] var137 = computeVector(var70, var57, var165);
            double var171 = (Math.sin(var81) * var64);
            double[] var138 = computeVector(var137, var59, var171);
            var72[var102] = var138;
            var37 = (var37 + 1);
        }
        int var85 = ColorMathUtils.computeColorOrIndex5(arg7, var41);
        int var87 = ColorMathUtils.computeColorOrIndex5(arg8, 0.0);
        var40 = 0;
        while (true) {
            if (var40 >= 14) break;
            int var146 = var40;
            double[] var140 = var72[var146];
            int var174 = (var40 + 1);
            double[] var148 = var72[var174];
            arg0.applyStep(var70, var140, var148, var85, var87, var87);
            var40 = (var40 + 1);
        }
    }

    private void drawGeometryPart(CosmeticDrawBuilder arg0, double[] arg1, IGroundHeightSampler arg2, double arg3, double arg4, double arg5, double arg6, int arg7, int arg8, double arg9) {
        double var1024 = 0.0;
        double var1026 = 0.0;
        int var35 = 0;
        int var1014 = 0;
        double var1022 = 0.0;
        double var72 = (arg3 * 0.024);
        var35 = 0;
        while (true) {
            int var75 = var35;
            if (var75 < 22) {
                KillEffectRenderSpec var182 = this.killEffectRenderSpec;
                int var183 = var182.seed();
                int var184 = (var183 * 83);
                int var319 = var35;
                int var320 = (var319 * 13);
                int var185 = (var184 + var320);
                int var1015 = var185;
                int var737 = var1015;
                int var866 = 1;
                int var738 = (var737 + var866);
                double var739 = GeometryPrimitives.computeDoubleMetric(var738);
                double var464 = (0.62 * var739);
                double var188 = (0.03 + var464);
                double var1016 = var188;
                int var741 = var1015;
                int var867 = 2;
                int var742 = (var741 + var867);
                double var743 = GeometryPrimitives.computeDoubleMetric(var742);
                double var468 = (0.25 * var743);
                double var192 = (0.2 + var468);
                double var1018 = var192;
                double var196 = (arg6 - var1016);
                double var198 = (var196 / var1018);
                double var1020 = var198;
                int var202 = Double.compare(var1020, 0.0);
                if (var202 > 0) {
                    int var205 = Double.compare(var1020, 1.0);
                    if (var205 >= 0) {
                    } else {
                        int var206 = var35;
                        int var207 = (var206 % 3);
                        if (var207 == 0) {
                            int var262 = (var1015 + 3);
                            double var263 = GeometryPrimitives.computeDoubleMetric(var262);
                            double var265 = (var263 * 6.283185307179586);
                            int var831 = var1015;
                            int var888 = 4;
                            int var832 = (var831 + var888);
                            double var833 = GeometryPrimitives.computeDoubleMetric(var832);
                            double var572 = (0.85 * var833);
                            double var269 = (0.15 + var572);
                            double var271 = (var269 * arg3);
                            double var835 = 0.4;
                            double var968 = var1020;
                            double var837 = (var835 * var968);
                            double var578 = (0.9 + var837);
                            double var273 = (var271 * var578);
                            double var582 = Math.cos(var265);
                            double var277 = (var273 * var582);
                            var1022 = var277;
                            double var586 = Math.sin(var265);
                            double var281 = (var273 * var586);
                            var1026 = var281;
                            double var339 = var1022;
                            double var645 = var1026;
                            double var284 = arg2.computeDoubleMetric(var339, var645);
                            double var839 = arg3;
                            double var590 = (0.15 * var839);
                            double var286 = (var284 + var590);
                            double var841 = 0.9;
                            double var970 = 1.6;
                            int var1004 = var1015;
                            int var1013 = 5;
                            int var1005 = (var1004 + var1013);
                            double var1006 = GeometryPrimitives.computeDoubleMetric(var1005);
                            double var972 = (var970 * var1006);
                            double var843 = (var841 + var972);
                            double var594 = (var1020 * var843);
                            double var845 = arg3;
                            double var596 = (var594 * var845);
                            double var288 = (var286 + var596);
                            var1024 = var288;
                        } else {
                            double var868 = arg4;
                            double[] var209 = this.computeVector(var1016, arg3, var868);
                            double var211 = var209[0];
                            int var479 = (var1015 + 3);
                            double var480 = GeometryPrimitives.computeDoubleMetric(var479);
                            double var745 = 0.5;
                            double var482 = (var480 - var745);
                            double var747 = 0.9;
                            double var484 = (var482 * var747);
                            double var749 = arg3;
                            double var486 = (var484 * var749);
                            double var213 = (var211 + var486);
                            double var751 = arg3;
                            double var490 = (0.25 * var751);
                            double var753 = var1020;
                            double var922 = 5.0;
                            double var755 = (var753 * var922);
                            int var924 = var35;
                            double var925 = ((double) var924);
                            double var757 = (var755 + var925);
                            double var759 = Math.sin(var757);
                            double var492 = (var490 * var759);
                            double var215 = (var213 + var492);
                            var1022 = var215;
                            double var218 = var209[2];
                            int var495 = (var1015 + 4);
                            double var496 = GeometryPrimitives.computeDoubleMetric(var495);
                            double var761 = 0.5;
                            double var498 = (var496 - var761);
                            double var763 = 0.9;
                            double var500 = (var498 * var763);
                            double var765 = arg3;
                            double var502 = (var500 * var765);
                            double var220 = (var218 + var502);
                            double var767 = arg3;
                            double var506 = (0.25 * var767);
                            double var769 = var1020;
                            double var927 = 5.0;
                            double var771 = (var769 * var927);
                            int var929 = var35;
                            double var930 = ((double) var929);
                            double var990 = 1.3;
                            double var932 = (var930 * var990);
                            double var773 = (var771 + var932);
                            double var775 = Math.cos(var773);
                            double var508 = (var506 * var775);
                            double var222 = (var220 + var508);
                            var1026 = var222;
                            double var225 = var209[1];
                            int var511 = (var1015 + 6);
                            double var512 = GeometryPrimitives.computeDoubleMetric(var511);
                            double var777 = 0.5;
                            double var514 = (var512 - var777);
                            double var779 = 0.5;
                            double var516 = (var514 * var779);
                            double var781 = arg3;
                            double var518 = (var516 * var781);
                            double var227 = (var225 + var518);
                            double var783 = 0.5;
                            double var934 = 1.1;
                            int var992 = var1015;
                            int var1008 = 5;
                            int var993 = (var992 + var1008);
                            double var994 = GeometryPrimitives.computeDoubleMetric(var993);
                            double var936 = (var934 * var994);
                            double var785 = (var783 + var936);
                            double var522 = (var1020 * var785);
                            double var787 = arg3;
                            double var524 = (var522 * var787);
                            double var229 = (var227 + var524);
                            double var627 = var1022;
                            double var870 = var1026;
                            double var527 = arg2.computeDoubleMetric(var627, var870);
                            double var789 = 0.03;
                            double var529 = (var527 + var789);
                            double var791 = 1.7;
                            double var938 = var72;
                            double var793 = (var791 * var938);
                            double var940 = 1.4;
                            double var795 = (var793 * var940);
                            double var531 = (var529 + var795);
                            double var233 = Math.max(var229, var531);
                            var1024 = var233;
                        }
                        double var797 = var1020;
                        double var535 = (3.141592653589793 * var797);
                        double var537 = Math.sin(var535);
                        double var799 = 0.8;
                        double var539 = Math.pow(var537, var799);
                        double var237 = (0.95 * var539);
                        double var801 = arg6;
                        double var942 = 0.85;
                        double var803 = (var801 - var942);
                        double var944 = 0.15;
                        double var805 = (var803 / var944);
                        double var807 = GeometryPrimitives.computeDoubleMetric(var805);
                        double var543 = (1.0 - var807);
                        double var239 = (var237 * var543);
                        double var629 = var1022;
                        double var872 = var1024;
                        double var978 = var1026;
                        double var1009 = arg9;
                        double var546 = KillEffectMathUtils.computeDoubleMetric(arg1, var629, var872, var978, var1009);
                        double var241 = (var239 * var546);
                        int var245 = Double.compare(var241, 0.04);
                        if (var245 < 0) {
                        }
                    }
                }
            } else {
                var1014 = 0;
                while (true) {
                    int var77 = var1014;
                    if (var77 >= 8) break;
                    KillEffectRenderSpec var79 = this.killEffectRenderSpec;
                    int var80 = var79.seed();
                    int var81 = (var80 * 89);
                    int var292 = var1014;
                    int var293 = (var292 * 19);
                    int var82 = (var81 + var293);
                    int var647 = (var82 + 1);
                    double var648 = GeometryPrimitives.computeDoubleMetric(var647);
                    double var344 = (0.55 * var648);
                    double var85 = (0.1 + var344);
                    int var847 = 2;
                    int var651 = (var82 + var847);
                    double var652 = GeometryPrimitives.computeDoubleMetric(var651);
                    double var348 = (0.2 * var652);
                    double var89 = (0.25 + var348);
                    double var93 = (arg6 - var85);
                    double var95 = (var93 / var89);
                    int var99 = Double.compare(var95, 0.0);
                    if (var99 > 0) {
                        int var102 = Double.compare(var95, 1.0);
                        if (var102 >= 0) {
                        } else {
                            double var848 = arg4;
                            double[] var104 = this.computeVector(var85, arg3, var848);
                            double var106 = var104[0];
                            int var359 = (var82 + 3);
                            double var360 = GeometryPrimitives.computeDoubleMetric(var359);
                            double var362 = (var360 - 0.5);
                            double var364 = (var362 * 1.0);
                            double var366 = (var364 * arg3);
                            double var108 = (var106 + var366);
                            double var370 = (0.2 * arg3);
                            double var664 = (var95 * 4.0);
                            int var889 = var1014;
                            double var890 = ((double) var889);
                            double var666 = (var664 + var890);
                            double var668 = Math.sin(var666);
                            double var372 = (var370 * var668);
                            double var110 = (var108 + var372);
                            double var113 = var104[2];
                            int var375 = (var82 + 4);
                            double var376 = GeometryPrimitives.computeDoubleMetric(var375);
                            double var378 = (var376 - 0.5);
                            double var380 = (var378 * 1.0);
                            double var382 = (var380 * arg3);
                            double var115 = (var113 + var382);
                            double var386 = (0.2 * arg3);
                            double var892 = 4.0;
                            double var680 = (var95 * var892);
                            int var894 = var1014;
                            double var895 = ((double) var894);
                            double var682 = (var680 + var895);
                            double var684 = Math.cos(var682);
                            double var388 = (var386 * var684);
                            double var117 = (var115 + var388);
                            double var120 = var104[1];
                            double var392 = (var95 * 0.7);
                            double var394 = (var392 * arg3);
                            double var122 = (var120 - var394);
                            int var397 = (var82 + 5);
                            double var398 = GeometryPrimitives.computeDoubleMetric(var397);
                            double var400 = (var398 - 0.5);
                            double var402 = (var400 * 0.4);
                            double var404 = (var402 * arg3);
                            double var124 = (var122 + var404);
                            double var850 = var117;
                            double var407 = arg2.computeDoubleMetric(var110, var850);
                            double var409 = (var407 + 0.03);
                            double var698 = 0.075;
                            double var897 = arg3;
                            double var700 = (var698 * var897);
                            double var411 = (var409 + var700);
                            double var128 = Math.max(var124, var411);
                            double var702 = var95;
                            double var415 = (3.141592653589793 * var702);
                            double var417 = Math.sin(var415);
                            double var132 = (0.85 * var417);
                            double var704 = arg6;
                            double var899 = 0.85;
                            double var706 = (var704 - var899);
                            double var901 = 0.15;
                            double var708 = (var706 / var901);
                            double var710 = GeometryPrimitives.computeDoubleMetric(var708);
                            double var421 = (1.0 - var710);
                            double var134 = (var132 * var421);
                            double var852 = var128;
                            double var424 = KillEffectMathUtils.computeDoubleMetric(arg1, var110, var852, var117, arg9);
                            double var136 = (var134 * var424);
                            int var140 = Double.compare(var136, 0.04);
                            if (var140 < 0) {
                            } else {
                                int var142 = (var82 + 6);
                                double var143 = GeometryPrimitives.computeDoubleMetric(var142);
                                double var145 = (var143 * 6.283185307179586);
                                double var712 = 4.0;
                                int var903 = var82;
                                int var974 = 7;
                                int var904 = (var903 + var974);
                                double var905 = GeometryPrimitives.computeDoubleMetric(var904);
                                double var714 = (var712 * var905);
                                double var432 = (3.0 + var714);
                                double var716 = var95;
                                double var434 = (var432 * var716);
                                double var147 = (var145 + var434);
                                int var150 = (var82 + 8);
                                double var151 = GeometryPrimitives.computeDoubleMetric(var150);
                                double var153 = (var151 - 0.5);
                                double var155 = (var153 * 1.6);
                                double var159 = Math.cos(var147);
                                double var442 = Math.cos(var155);
                                double var161 = (var159 * var442);
                                double var446 = Math.sin(var155);
                                double var718 = var147;
                                double var720 = Math.sin(var718);
                                double var907 = var155;
                                double var909 = Math.cos(var907);
                                double var722 = (var720 * var909);
                                double[] var163 = GeometryPrimitives.computeVector(var161, var446, var722);
                                double var854 = 0.2;
                                double[] var303 = GeometryPrimitives.computeVector(0.3, 1.0, var854);
                                double[] var165 = GeometryPrimitives.computeVector1(var163, var303);
                                double var856 = 0.0;
                                double[] var306 = GeometryPrimitives.computeVector(1.0, 0.0, var856);
                                double[] var166 = computeVector(var165, var306);
                                double var169 = (0.075 * arg3);
                                double var173 = (0.02 * arg3);
                                double var724 = 0.6;
                                double var911 = var95;
                                double var726 = (var724 * var911);
                                double var454 = (0.3 + var726);
                                int var176 = ColorMathUtils.computeColorOrIndex(arg7, arg8, var454);
                                int var177 = ColorMathUtils.computeColorOrIndex5(var176, var136);
                                double var312 = (var136 * 0.6);
                                int var179 = ColorMathUtils.computeColorOrIndex5(arg8, var312);
                                double var858 = var117;
                                double[] var316 = GeometryPrimitives.computeVector(var110, var128, var858);
                                double var615 = -(var169);
                                double[] var317 = computeVector(var316, var163, var615);
                                double var728 = var128;
                                double var913 = var117;
                                double[] var459 = GeometryPrimitives.computeVector(var110, var728, var913);
                                double var730 = var173;
                                double[] var460 = computeVector(var459, var166, var730);
                                double var860 = var128;
                                double var975 = var117;
                                double[] var620 = GeometryPrimitives.computeVector(var110, var860, var975);
                                double[] var732 = var163;
                                double var862 = var169;
                                double[] var621 = computeVector(var620, var732, var862);
                                double var733 = var110;
                                double var915 = var128;
                                double[] var735 = GeometryPrimitives.computeVector(var733, var915, var117);
                                double[] var864 = var166;
                                double var917 = var173;
                                double var919 = -(var917);
                                double[] var736 = computeVector(var735, var864, var919);
                                int var865 = var177;
                                int var921 = var177;
                                int var977 = var179;
                                int var989 = var177;
                                arg0.applyStep(var317, var460, var621, var736, var865, var921, var977, var989);
                            }
                            var1014 = (var1014 + 1);
                        }
                    }
                }
                return;
                double var809 = 0.8;
                int var946 = var1015;
                int var980 = 7;
                int var947 = (var946 + var980);
                double var948 = GeometryPrimitives.computeDoubleMetric(var947);
                double var811 = (var809 * var948);
                double var552 = (0.6 + var811);
                double var248 = (var72 * var552);
                double var813 = 0.5;
                double var950 = var1020;
                double var815 = (var813 * var950);
                double var556 = (1.0 - var815);
                double var250 = (var248 * var556);
                double var254 = (var250 * 1.7);
                int var257 = ColorMathUtils.computeColorOrIndex(arg7, arg8, var1020);
                int var258 = ColorMathUtils.computeColorOrIndex5(var257, var241);
                double var330 = var1022;
                double var332 = (var330 - var250);
                double var633 = var1024;
                double var874 = var1026;
                double[] var334 = GeometryPrimitives.computeVector(var332, var633, var874);
                double var562 = var1022;
                double var817 = var1024;
                double var952 = var254;
                double var819 = (var817 - var952);
                double var954 = var1026;
                double[] var564 = GeometryPrimitives.computeVector(var562, var819, var954);
                double var635 = var1022;
                double var876 = var250;
                double var637 = (var635 + var876);
                double var878 = var1024;
                double var981 = var1026;
                double[] var639 = GeometryPrimitives.computeVector(var637, var878, var981);
                double var821 = var1022;
                double var956 = var1024;
                double var996 = var254;
                double var958 = (var956 + var996);
                double var998 = var1026;
                double[] var823 = GeometryPrimitives.computeVector(var821, var958, var998);
                int var880 = var258;
                arg0.applyStep(var334, var564, var639, var823, var880);
                double var335 = var1022;
                double var640 = var1024;
                double var881 = var1026;
                double var983 = var250;
                double var883 = (var881 - var983);
                double[] var337 = GeometryPrimitives.computeVector(var335, var640, var883);
                double var565 = var1022;
                double var824 = var1024;
                double var960 = var254;
                double var826 = (var824 - var960);
                double var962 = var1026;
                double[] var567 = GeometryPrimitives.computeVector(var565, var826, var962);
                double var642 = var1022;
                double var885 = var1024;
                double var985 = var1026;
                double var1011 = var250;
                double var987 = (var985 + var1011);
                double[] var644 = GeometryPrimitives.computeVector(var642, var885, var987);
                double var828 = var1022;
                double var964 = var1024;
                double var1000 = var254;
                double var966 = (var964 + var1000);
                double var1002 = var1026;
                double[] var830 = GeometryPrimitives.computeVector(var828, var966, var1002);
                int var887 = var258;
                arg0.applyStep(var337, var567, var644, var830, var887);
            }
            var35 = (var35 + 1);
        }
    }

}
