package need.clarity.modules.visual.cosmetics.killeffect;

import need.clarity.modules.visual.cosmetics.ColorMathUtils;
import need.clarity.modules.visual.cosmetics.CosmeticDrawBuilder;
import need.clarity.modules.visual.cosmetics.GeometryPrimitives;
import need.clarity.modules.visual.cosmetics.ICosmeticEffectRenderer;
import need.clarity.modules.visual.cosmetics.IGradientColorSampler;

public final class ShatterKillRenderer implements ICosmeticEffectRenderer {
    public static final int CONST_INT = 72;
    public static final int CONST_INT1 = 30;
    public static final int CONST_INT2 = 7;
    public static final int CONST_INT3 = 4;
    public static final int CONST_INT4 = 20;
    public static final int CONST_INT5 = 8;
    public static final int CONST_INT6 = 4;
    public static final int CONST_INT7 = 542;
    static final double CONST_DBL = 2.2;
    private static final double CONST_DBL1 = 0.1;
    private static final double CONST_DBL2 = 0.36;
    private static final double CONST_DBL3 = 1.8;
    private static final double CONST_DBL4 = 1.2;
    private final KillEffectRenderSpec killEffectRenderSpec;

    public ShatterKillRenderer() {
        KillEffectRenderSpec var2 = KillEffectRenderSpec.evaluate();
        this(var2);
    }

    public ShatterKillRenderer(KillEffectRenderSpec arg0) {
        this.killEffectRenderSpec = arg0;
    }

    public static int[] evaluate(double arg0, double arg1) {
        double var11 = 0.0;
        int var13 = 0;
        var11 = Math.cbrt((((arg0 * arg0) * arg1) / 64.0));
        var13 = 0;
        while (true) {
            if (var13 >= 60) break;
            double var9 = var11;
            int var47 = ((int) Math.round((arg0 / var9)));
            int var27 = Math.max(2, var47);
            double var68 = var11;
            int var54 = ((int) Math.round((arg1 / var68)));
            int var29 = Math.max(2, var54);
            if (((var27 * var27) * var29) <= 72) {
                int[] var34 = new int[2];
                var34[0] = var27;
                var34[1] = var29;
                return var34;
            }
            var11 = (var11 * 1.07);
            var13 = (var13 + 1);
        }
        int[] var40 = new int[2];
        var40[0] = 2;
        var40[1] = 2;
        return var40;
    }

    public void renderEffect(CosmeticDrawBuilder arg0) {
        double var160 = 0.0;
        double var151 = 0.0;
        double var67 = this.killEffectRenderSpec.life();
        double var71 = this.killEffectRenderSpec.age();
        double var75 = this.killEffectRenderSpec.life();
        double var79 = this.killEffectRenderSpec.width();
        double var83 = this.killEffectRenderSpec.height();
        if (Double.compare(var67, 1.0) >= 0) {
            return;
        }
        double var92 = GeometryPrimitives.computeDoubleMetric((var71 / 0.03));
        double var225 = GeometryPrimitives.computeDoubleMetric(((var67 - 0.58) / 0.42));
        double var172 = (1.0 - var225);
        double var94 = (var92 * var172);
        if (Double.compare(var94, 0.004) < 0) {
            return;
        }
        double var103 = KillEffectMathUtils.computeDoubleMetric((2.2 / var75), 0.5, 2.5);
        double var107 = (var71 * var103);
        double var184 = (var107 - 0.1);
        double var111 = Math.max(0.0, var184);
        double var119 = KillEffectMathUtils.computeDoubleMetric(Math.pow((var83 / 1.8), 0.45), 0.6, 2.2);
        double var123 = (13.0 * var119);
        IGroundHeightSampler var127 = this.killEffectRenderSpec.ground();
        if (Double.compare(arg0.computeDoubleMetric(), 1.0E-6) > 0) {
            var160 = arg0.computeDoubleMetric();
        } else {
            var160 = 1.0;
        }
        double var59 = var160;
        double[] var134 = arg0.computeVector1();
        double var142 = (GeometryPrimitives.computeDoubleMetric(((this.killEffectRenderSpec.seed() * 41) + 7)) * 6.283185307179586);
        if (Double.compare(var107, 0.1) < 0) {
            var151 = GeometryPrimitives.computeDoubleMetric((var107 / 0.1));
        } else {
            double var214 = GeometryPrimitives.computeDoubleMetric(((var107 - 0.1) / 0.35));
            var151 = (1.0 - var214);
        }
        double var64 = var151;
        this.drawGeometryPart(arg0, var127, var119, var111, var94);
        this.drawGeometryPart(arg0, var127, var119, var67, var111, var142);
        this.drawGeometryPart(arg0, var134, var127, var79, var83, var119, var123, var59, var71, var107, var111, var67, var94, var64);
        this.drawGeometryPart1(arg0, var127, var119, var123, var111, var94);
        this.drawGeometryPart(arg0, var79, var83, var111);
    }

    private void drawGeometryPart(CosmeticDrawBuilder arg0, IGroundHeightSampler arg1, double arg2, double arg3, double arg4) {
        double var49 = GeometryPrimitives.computeDoubleMetric((arg3 / 0.02));
        double var28 = (0.45 * var49);
        double var67 = GeometryPrimitives.computeDoubleMetric((arg3 / 0.7));
        double var53 = (1.0 - var67);
        double var32 = ((var28 * var53) * arg4);
        if (Double.compare(arg3, 0.0) > 0) {
            if (Double.compare(var32, 0.01) < 0) {
            }
        }
        return;
        int var42 = ColorMathUtils.computeColorOrIndex7(arg0.counterValue1, 0.6);
        int var44 = arg0.counterValue;
        double var75 = (0.8 * arg2);
        double var71 = (0.55 + var75);
        IGradientColorSampler var78 = (p0, p1) -> ShatterKillRenderer.lambda$a(var42, var44, var32, p0, p1);
        KillEffectMathUtils.drawGeometryPart(arg0, arg1, 0.03, var71, 20, var78);
    }

    private void drawGeometryPart(CosmeticDrawBuilder arg0, IGroundHeightSampler arg1, double arg2, double arg3, double arg4, double arg5) {
        int var39 = 0;
        int var52 = 0;
        double var125 = 0.0;
        if (Double.compare(arg4, 0.0) <= 0) {
            return;
        }
        double var270 = (arg4 / 0.28);
        double var221 = Math.min(1.0, var270);
        double var151 = Math.pow((1.0 - var221), 3.0);
        double var60 = (1.0 - var151);
        double var157 = GeometryPrimitives.computeDoubleMetric((arg4 / 0.02));
        double var64 = (0.9 * var157);
        double var233 = GeometryPrimitives.computeDoubleMetric(((arg3 - 0.5) / 0.5));
        double var161 = (1.0 - var233);
        double var66 = (var64 * var161);
        if (Double.compare(var66, 0.01) < 0) {
            return;
        }
        int var73 = ColorMathUtils.computeColorOrIndex7(arg0.counterValue1, 0.6);
        int var75 = arg0.counterValue;
        double var80 = GeometryPrimitives.computeDoubleMetric((arg3 / 0.5));
        var39 = 0;
        while (true) {
            if (var39 >= 7) break;
            int var87 = (this.killEffectRenderSpec.seed() * 89);
            int var141 = (var39 * 23);
            int var88 = (var87 + var141);
            double var236 = ((double) var39);
            double var300 = (GeometryPrimitives.computeDoubleMetric((var88 + 1)) - 0.5);
            double var278 = (0.35 * var300);
            double var238 = (var236 + var278);
            double var172 = ((6.283185307179586 * var238) / 7.0);
            double var91 = (arg5 + var172);
            double var244 = GeometryPrimitives.computeDoubleMetric((var88 + 2));
            double var176 = (1.4 * var244);
            double var99 = (((0.9 + var176) * arg2) * var60);
            double var103 = Math.cos(var91);
            double var107 = Math.sin(var91);
            double[] var110 = new double[5];
            double[] var112 = new double[5];
            double[] var114 = new double[5];
            var52 = 0;
            while (true) {
                if (var52 > 4) break;
                double var185 = ((double) var52);
                double var122 = ((var99 * var185) / 4.0);
                if (var52 == 0) {
                    var125 = 0.0;
                } else {
                    int var210 = (var88 + 10);
                    int var220 = var52;
                    double var212 = GeometryPrimitives.computeDoubleMetric((var210 + var220));
                    var125 = ((((2.0 * var212) - 1.0) * 0.16) * var99);
                }
                double var55 = var125;
                int var144 = var52;
                double var191 = (var122 * var103);
                double var251 = (var55 * var107);
                double var193 = (var191 - var251);
                var110[var144] = var193;
                int var145 = var52;
                double var197 = (var122 * var107);
                double var257 = (var55 * var103);
                double var199 = (var197 + var257);
                var112[var145] = var199;
                int var146 = var52;
                double var203 = (0.11 * arg2);
                double var304 = ((double) var52);
                double var289 = ((0.8 * var304) / 4.0);
                double var263 = (1.0 - var289);
                double var205 = (var203 * var263);
                double var310 = (1.0 - arg3);
                double var293 = (0.4 * var310);
                double var267 = (0.6 + var293);
                double var207 = (var205 * var267);
                var114[var146] = var207;
                var52 = (var52 + 1);
            }
            IGradientColorSampler var295 = (p0, p1) -> ShatterKillRenderer.lambda$a(var73, var75, var80, var66, p0, p1);
            KillEffectMathUtils.drawGeometryPart(arg0, arg1, 0.04, var110, var112, var114, var295);
            var39 = (var39 + 1);
        }
    }

    private static double computeDoubleMetric(double arg0, double arg1, double arg2) {
        double var20 = (arg1 * arg1);
        double var30 = (2.0 * arg2);
        double var36 = Math.max(0.0, arg0);
        double var32 = (var30 * var36);
        double var24 = Math.sqrt((var20 + var32));
        return ((arg1 + var24) / arg2);
    }

    private static double computeDoubleMetric(double arg0, double arg1, double arg2, double arg3) {
        double var19 = 0.0;
        double var21 = 0.0;
        int var23 = 0;
        double var26 = computeDoubleMetric(arg1, arg2, arg3);
        if (Double.compare(arg0, var26) <= 0) {
            double var113 = (arg2 * arg0);
            double var81 = (arg1 + var113);
            double var121 = (((0.5 * arg3) * arg0) * arg0);
            double var83 = (var81 - var121);
            return Math.max(0.0, var83);
        }
        double var87 = (arg3 * var26);
        var19 = (-((arg2 - var87)) * 0.36);
        var21 = (arg0 - var26);
        var23 = 0;
        while (true) {
            if (var23 >= 4) break;
            if (Double.compare(var19, 0.5) < 0) {
                return 0.0;
            }
            double var95 = var19;
            double var60 = ((2.0 * var95) / arg3);
            if (Double.compare(var21, var60) < 0) {
                double var101 = var19;
                double var125 = var21;
                double var103 = (var101 * var125);
                double var129 = (0.5 * arg3);
                double var143 = var21;
                double var131 = (var129 * var143);
                double var145 = var21;
                double var133 = (var131 * var145);
                double var105 = (var103 - var133);
                return Math.max(0.0, var105);
            }
            var21 = (var21 - var60);
            var19 = (var19 * 0.36);
            var23 = (var23 + 1);
        }
        return 0.0;
    }

    private void drawGeometryPart(CosmeticDrawBuilder arg0, double[] arg1, IGroundHeightSampler arg2, double arg3, double arg4, double arg5, double arg6, double arg7, double arg8, double arg9, double arg10, double arg11, double arg12, double arg13) {
        int var67 = 0;
        int var68 = 0;
        int var69 = 0;
        double var171 = 0.0;
        double var408 = 0.0;
        double var926 = 0.0;
        double var422 = 0.0;
        int[] var141 = evaluate(arg3, arg4);
        int var143 = var141[0];
        int var145 = var141[1];
        int var471 = var143;
        double var472 = ((double) var471);
        double var148 = (arg3 / var472);
        int var474 = var145;
        double var475 = ((double) var474);
        double var152 = (arg4 / var475);
        double var477 = var148;
        double var479 = Math.min(var477, var152);
        double var156 = (0.47 * var479);
        double var481 = 1.0;
        double var872 = (arg11 - 0.7);
        double var903 = 0.3;
        double var874 = (var872 / var903);
        double var876 = GeometryPrimitives.computeDoubleMetric(var874);
        double var761 = (0.55 * var876);
        double var483 = (var481 - var761);
        double var160 = (var156 * var483);
        double var485 = 0.0;
        int var166 = Double.compare(arg10, var485);
        if (var166 <= 0) {
            double var487 = var156;
            double var169 = (0.06 * var487);
            double var489 = arg9;
            double var491 = (var489 / 0.1);
            double var493 = GeometryPrimitives.computeDoubleMetric(var491);
            var171 = (var169 * var493);
        } else {
            var171 = 0.0;
        }
        double var65 = var171;
        var67 = 0;
        while (true) {
            int var174 = var67;
            if (var174 >= var145) break;
            var68 = 0;
            while (true) {
                int var176 = var68;
                if (var176 >= var143) break;
                var69 = 0;
                while (true) {
                    int var178 = var69;
                    if (var178 < var143) {
                        int var179 = var67;
                        int var180 = (var179 * var143);
                        int var429 = var68;
                        int var181 = (var180 + var429);
                        int var182 = (var181 * var143);
                        int var431 = var69;
                        int var183 = (var182 + var431);
                        KillEffectRenderSpec var185 = this.killEffectRenderSpec;
                        int var186 = var185.seed();
                        int var187 = (var186 * 131);
                        int var495 = 17;
                        int var434 = (var183 * var495);
                        int var188 = (var187 + var434);
                        double var496 = arg3;
                        double var191 = (-0.5 * var496);
                        int var498 = var69;
                        double var499 = ((double) var498);
                        double var765 = 0.5;
                        double var501 = (var499 + var765);
                        double var767 = var148;
                        double var503 = (var501 * var767);
                        double var193 = (var191 + var503);
                        int var195 = var67;
                        double var196 = ((double) var195);
                        double var505 = 0.5;
                        double var198 = (var196 + var505);
                        double var507 = var152;
                        double var200 = (var198 * var507);
                        double var509 = arg3;
                        double var204 = (-0.5 * var509);
                        int var511 = var68;
                        double var512 = ((double) var511);
                        double var769 = 0.5;
                        double var514 = (var512 + var769);
                        double var771 = var148;
                        double var516 = (var514 * var771);
                        double var206 = (var204 + var516);
                        double var210 = Math.hypot(var193, var206);
                        double var773 = arg4;
                        double var522 = (0.5 * var773);
                        double var214 = (var200 - var522);
                        int var218 = Double.compare(var210, 1.0E-4);
                        if (var218 > 0) {
                            double var420 = Math.atan2(var206, var193);
                            int var714 = (var188 + 1);
                            double var715 = GeometryPrimitives.computeDoubleMetric(var714);
                            double var845 = 0.5;
                            double var717 = (var715 - var845);
                            double var847 = 0.9;
                            double var719 = (var717 * var847);
                            var422 = (var420 + var719);
                        }
                    } else {
                        var68 = (var68 + 1);
                    }
                }
            }
            int var220 = (var188 + 1);
            double var221 = GeometryPrimitives.computeDoubleMetric(var220);
            var422 = (var221 * 6.283185307179586);
            double var82 = var422;
            int var775 = var188;
            int var776 = (var775 + 2);
            double var777 = GeometryPrimitives.computeDoubleMetric(var776);
            double var530 = (2.4 * var777);
            double var225 = (1.3 + var530);
            double var227 = (var225 * arg5);
            double var779 = 0.45;
            double var878 = 1.0;
            double var905 = var210;
            double var918 = (0.5 * arg3);
            double var922 = 1.0E-6;
            double var920 = (var918 + var922);
            double var907 = (var905 / var920);
            double var880 = Math.min(var878, var907);
            double var781 = (var779 * var880);
            double var536 = (0.55 + var781);
            double var229 = (var227 * var536);
            int var783 = var188;
            int var849 = 3;
            int var784 = (var783 + var849);
            double var785 = GeometryPrimitives.computeDoubleMetric(var784);
            double var540 = (3.4 * var785);
            double var233 = (2.6 + var540);
            double var787 = var214;
            double var544 = (2.0 * var787);
            double var789 = arg4;
            double var546 = (var544 / var789);
            double var235 = (var233 + var546);
            double var237 = (var235 * arg5);
            double var552 = Math.cos(var82);
            double var241 = (var229 * var552);
            double var556 = Math.sin(var82);
            double var245 = (var229 * var556);
            double var791 = arg10;
            double var560 = (-1.8 * var791);
            double var562 = Math.exp(var560);
            double var249 = (1.0 - var562);
            double var251 = (var249 / 1.8);
            double var793 = var251;
            double var568 = (var241 * var793);
            double var255 = (var193 + var568);
            double var795 = var251;
            double var572 = (var245 * var795);
            double var259 = (var206 + var572);
            double var263 = (var200 - var156);
            double var797 = arg6;
            double var267 = computeDoubleMetric(var263, var237, var797);
            double var799 = var267;
            double var580 = (-1.8 * var799);
            double var582 = Math.exp(var580);
            double var271 = (1.0 - var582);
            double var273 = (var271 / 1.8);
            double var850 = var273;
            double var721 = (var241 * var850);
            double var438 = (var193 + var721);
            double var852 = var245;
            double var854 = (var852 * var273);
            double var725 = (var206 + var854);
            double var276 = arg2.computeDoubleMetric(var438, var725);
            double var801 = var276;
            double var588 = (var263 - var801);
            double var280 = Math.max(0.0, var588);
            int var284 = Double.compare(arg10, 0.0);
            if (var284 <= 0) {
                var926 = var263;
            } else {
                double var843 = var280;
                double var894 = var237;
                double var913 = arg6;
                double var706 = computeDoubleMetric(arg10, var843, var894, var913);
                double var412 = (var276 + var706);
                double var924 = var412;
                double var870 = var259;
                double var709 = arg2.computeDoubleMetric(var255, var870);
                double var416 = Math.max(var924, var709);
                var926 = var416;
            }
            double var803 = arg6;
            double var289 = computeDoubleMetric(var280, var237, var803);
            double var291 = (var289 + 0.5);
            double var805 = var291;
            double var598 = (arg10 / var805);
            double var295 = Math.min(1.0, var598);
            double var807 = var295;
            double var602 = (1.0 - var807);
            double var809 = 3.0;
            double var604 = Math.pow(var602, var809);
            double var299 = (1.0 - var604);
            int var302 = (var188 + 7);
            double var303 = GeometryPrimitives.computeDoubleMetric(var302);
            int var305 = Double.compare(var303, 0.5);
            if (var305 < 0) {
                var408 = -1.0;
            } else {
                var408 = 1.0;
            }
            double var114 = var408;
            int var811 = 4;
            int var728 = (var188 + var811);
            double var729 = GeometryPrimitives.computeDoubleMetric(var728);
            double var856 = 4.0;
            double var731 = (var729 * var856);
            int var733 = ((int) var731);
            int var609 = (2 + var733);
            double var610 = ((double) var609);
            double var308 = (var114 * var610);
            double var310 = (var308 * 1.5707963267948966);
            double var312 = (var310 * var299);
            double var316 = -(var114);
            int var812 = 5;
            int var735 = (var188 + var812);
            double var736 = GeometryPrimitives.computeDoubleMetric(var735);
            double var858 = 4.0;
            double var738 = (var736 * var858);
            int var740 = ((int) var738);
            int var617 = (1 + var740);
            double var618 = ((double) var617);
            double var318 = (var316 * var618);
            double var320 = (var318 * 1.5707963267948966);
            double var322 = (var320 * var299);
            int var625 = (var188 + 6);
            double var626 = GeometryPrimitives.computeDoubleMetric(var625);
            double var326 = (2.0 * var626);
            double var328 = (var326 - 1.0);
            double var330 = (var328 * 3.141592653589793);
            double var332 = (var330 * 1.5);
            double var334 = (var332 * var299);
            double var338 = Math.cos(var312);
            double var638 = Math.sin(var322);
            double var340 = (var338 * var638);
            double var342 = Math.abs(var340);
            double var642 = Math.cos(var312);
            double var813 = var322;
            double var815 = Math.cos(var813);
            double var644 = (var642 * var815);
            double var646 = Math.abs(var644);
            double var344 = (var342 + var646);
            double var650 = Math.sin(var312);
            double var652 = Math.abs(var650);
            double var346 = (var344 + var652);
            double var348 = var926;
            double var817 = var346;
            double var656 = (var160 * var817);
            double var350 = (var348 + var656);
            double var819 = 71.0;
            double var660 = (arg8 * var819);
            int var821 = var183;
            double var822 = ((double) var821);
            double var882 = 1.7;
            double var824 = (var822 * var882);
            double var662 = (var660 + var824);
            double var664 = Math.sin(var662);
            double var354 = (var65 * var664);
            double var826 = 63.0;
            double var668 = (arg8 * var826);
            int var828 = var183;
            double var829 = ((double) var828);
            double var884 = 2.3;
            double var831 = (var829 * var884);
            double var670 = (var668 + var831);
            double var672 = Math.cos(var670);
            double var358 = (var65 * var672);
            double var362 = (0.85 * arg12);
            double var860 = var350;
            double var896 = var259;
            double var677 = KillEffectMathUtils.computeDoubleMetric(arg1, var255, var860, var896, arg7);
            double var364 = (var362 * var677);
            int var368 = Double.compare(var364, 0.01);
            if (var368 < 0) {
            } else {
                int var369 = var67;
                double var370 = ((double) var369);
                double var372 = (var370 + 0.5);
                double var684 = ((double) var145);
                double var374 = (var372 / var684);
                int var377 = arg0.counterValue;
                int var442 = arg0.counterValue1;
                int var378 = ColorMathUtils.computeColorOrIndex(var377, var442, var374);
                double var833 = 5.0;
                double var886 = 5.0;
                int var909 = var188;
                int var915 = 9;
                int var910 = (var909 + var915);
                double var911 = GeometryPrimitives.computeDoubleMetric(var910);
                double var888 = (var886 * var911);
                double var835 = (var833 + var888);
                double var690 = (arg8 * var835);
                double var837 = 6.283185307179586;
                int var890 = var188;
                int var898 = 10;
                int var891 = (var890 + var898);
                double var892 = GeometryPrimitives.computeDoubleMetric(var891);
                double var839 = (var837 * var892);
                double var692 = (var690 + var839);
                double var694 = Math.sin(var692);
                double var381 = Math.max(0.0, var694);
                double var383 = Math.pow(var381, 12.0);
                double var387 = (arg13 * 0.55);
                double var841 = 0.5;
                double var702 = (var383 * var841);
                double var389 = (var387 + var702);
                double var443 = 0.82;
                int var392 = ColorMathUtils.computeColorOrIndex6(var378, var443);
                double var445 = var389;
                double var447 = (var445 * 0.6);
                int var393 = ColorMathUtils.computeColorOrIndex7(var392, var447);
                double var449 = var364;
                int var394 = ColorMathUtils.computeColorOrIndex5(var393, var449);
                double var451 = 0.3;
                double var453 = (var451 + var389);
                int var396 = ColorMathUtils.computeColorOrIndex7(var378, var453);
                double var455 = var364;
                int var397 = ColorMathUtils.computeColorOrIndex5(var396, var455);
                CosmeticDrawBuilder var399 = arg0.drawGeometryPart();
                double var457 = var255;
                double var459 = (var457 + var354);
                double var862 = var259;
                double var899 = var358;
                double var864 = (var862 + var899);
                CosmeticDrawBuilder var401 = arg0.drawGeometryPart(var459, var350, var864);
                double var461 = var334;
                CosmeticDrawBuilder var402 = var401.drawGeometryPart2(var461);
                double var463 = var312;
                CosmeticDrawBuilder var403 = var402.drawGeometryPart3(var463);
                double var465 = var322;
                CosmeticDrawBuilder var404 = var403.drawGeometryPart4(var465);
                double var467 = var160;
                double var469 = -(var467);
                double var754 = -(var160);
                double var866 = var160;
                double var868 = -(var866);
                double var901 = var160;
                double var916 = var160;
                GeometryPrimitives.drawGeometryPart(arg0, var469, var754, var868, var901, var916, var160, var394, var397);
                CosmeticDrawBuilder var407 = arg0.drawGeometryPart1();
            }
            var69 = (var69 + 1);
            var67 = (var67 + 1);
        }
    }

    private void drawGeometryPart1(CosmeticDrawBuilder arg0, IGroundHeightSampler arg1, double arg2, double arg3, double arg4, double arg5) {
        int var33 = 0;
        if (Double.compare(arg4, 0.0) <= 0) {
            return;
        }
        double var246 = GeometryPrimitives.computeDoubleMetric((arg4 / 0.02));
        double var72 = (arg5 * var246);
        double var384 = GeometryPrimitives.computeDoubleMetric((arg4 / 1.2));
        double var250 = (1.0 - var384);
        double var74 = (var72 * var250);
        if (Double.compare(var74, 0.01) < 0) {
            return;
        }
        double var81 = this.killEffectRenderSpec.width();
        double var85 = this.killEffectRenderSpec.height();
        int var89 = ColorMathUtils.computeColorOrIndex7(arg0.counterValue1, 0.7);
        double var218 = (0.95 * var74);
        int var90 = ColorMathUtils.computeColorOrIndex5(var89, var218);
        int var92 = arg0.counterValue;
        double var222 = (0.55 * var74);
        int var93 = ColorMathUtils.computeColorOrIndex5(var92, var222);
        var33 = 0;
        while (true) {
            if (var33 >= 30) break;
            int var99 = (this.killEffectRenderSpec.seed() * 337);
            int var227 = (var33 * 29);
            int var100 = (var99 + var227);
            double var109 = (((GeometryPrimitives.computeDoubleMetric((var100 + 1)) - 0.5) * var81) * 0.8);
            double var452 = GeometryPrimitives.computeDoubleMetric((var100 + 2));
            double var388 = (0.7 * var452);
            double var263 = (0.2 + var388);
            double var113 = (var85 * var263);
            double var123 = (((GeometryPrimitives.computeDoubleMetric((var100 + 3)) - 0.5) * var81) * 0.8);
            double var129 = (GeometryPrimitives.computeDoubleMetric((var100 + 4)) * 6.283185307179586);
            double var392 = GeometryPrimitives.computeDoubleMetric((var100 + 5));
            double var275 = (0.75 * var392);
            double var133 = (0.15 + var275);
            double var396 = GeometryPrimitives.computeDoubleMetric((var100 + 6));
            double var279 = (3.0 * var396);
            double var139 = ((2.5 + var279) * arg2);
            double var285 = Math.cos(var133);
            double var143 = (var139 * var285);
            double var289 = Math.cos(var129);
            double var145 = (var143 * var289);
            double var293 = Math.cos(var133);
            double var149 = (var139 * var293);
            double var297 = Math.sin(var129);
            double var151 = (var149 * var297);
            double var301 = Math.sin(var133);
            double var155 = (var139 * var301);
            double var305 = (1.0 * arg2);
            double var157 = (var155 + var305);
            double var311 = Math.exp((-1.2 * arg4));
            double var163 = ((1.0 - var311) / 1.2);
            double var317 = (var145 * var163);
            double var167 = (var109 + var317);
            double var321 = (var151 * var163);
            double var171 = (var123 + var321);
            double var325 = (var157 * arg4);
            double var175 = (var113 + var325);
            double var333 = (((0.5 * arg3) * arg4) * arg4);
            double var177 = (var175 - var333);
            double var338 = (arg1.computeDoubleMetric(var167, var171) + 0.03);
            double var179 = Math.max(var177, var338);
            double var418 = GeometryPrimitives.computeDoubleMetric((var100 + 7));
            double var342 = (0.22 * var418);
            double var185 = ((0.16 + var342) * arg2);
            double var189 = (0.4 * var185);
            double var195 = (GeometryPrimitives.computeDoubleMetric((var100 + 8)) * 6.283185307179586);
            double var456 = GeometryPrimitives.computeDoubleMetric((var100 + 9));
            double var422 = (6.0 * var456);
            double var354 = ((4.0 + var422) * arg4);
            double var197 = (var195 + var354);
            double var203 = (GeometryPrimitives.computeDoubleMetric((var100 + 11)) * 6.283185307179586);
            double var460 = GeometryPrimitives.computeDoubleMetric((var100 + 12));
            double var428 = (5.0 * var460);
            double var362 = ((3.0 + var428) * arg4);
            double var205 = (var203 + var362);
            CosmeticDrawBuilder var208 = arg0.drawGeometryPart();
            CosmeticDrawBuilder var212 = arg0.drawGeometryPart(var167, var179, var171).drawGeometryPart2(var197).drawGeometryPart3(var205);
            double[] var241 = GeometryPrimitives.computeVector(0.0, var185, 0.0);
            double var366 = -(var189);
            double var434 = (-0.4 * var185);
            double[] var368 = GeometryPrimitives.computeVector(var366, var434, 0.0);
            double var447 = (-0.4 * var185);
            double[] var379 = GeometryPrimitives.computeVector(var189, var447, 0.0);
            arg0.applyStep(var241, var368, var379, var90, var93, var93);
            CosmeticDrawBuilder var215 = arg0.drawGeometryPart1();
            var33 = (var33 + 1);
        }
    }

    private void drawGeometryPart(CosmeticDrawBuilder arg0, double arg1, double arg2, double arg3) {
        if (Double.compare(arg3, 0.0) > 0) {
            if (Double.compare(arg3, 0.24) >= 0) {
            }
        }
        return;
        double var54 = (arg3 / 0.24);
        double var88 = Math.max((0.55 * arg2), arg1);
        double var58 = (0.4 * var88);
        double var122 = Math.pow((1.0 - var54), 2.0);
        double var118 = (1.0 - var122);
        double var110 = (1.5 * var118);
        double var92 = (0.35 + var110);
        double var60 = (var58 * var92);
        double var98 = Math.pow((1.0 - var54), 1.5);
        double var64 = (0.55 * var98);
        double var68 = (0.5 * arg2);
        double var104 = (var60 + 0.03);
        double var70 = Math.max(var68, var104);
        int var74 = ColorMathUtils.computeColorOrIndex7(arg0.counterValue1, 0.8);
        int var76 = arg0.counterValue;
        IGradientColorSampler var127 = (p0, p1) -> ShatterKillRenderer.drawGeometryPart(arg0, var60, var70, var76, var74, var64, p0, p1);
        GeometryPrimitives.drawGeometryPart(arg0, 0.0, var70, 0.0, var60, 8, 4, var127);
    }

}
