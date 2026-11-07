package need.clarity.modules.visual.cosmetics.killeffect;

import need.clarity.modules.visual.cosmetics.ColorMathUtils;
import need.clarity.modules.visual.cosmetics.CosmeticDrawBuilder;
import need.clarity.modules.visual.cosmetics.GeometryPrimitives;
import need.clarity.modules.visual.cosmetics.ICosmeticEffectRenderer;
import need.clarity.modules.visual.cosmetics.IGradientColorSampler;

public final class BlackHoleKillRenderer implements ICosmeticEffectRenderer {
    public static final int CONST_INT = 10837247;
    public static final int CONST_INT1 = 16760952;
    private static final int CONST_INT2 = 12;
    private static final int CONST_INT3 = 6;
    private static final int CONST_INT4 = 32;
    private static final int CONST_INT5 = 4;
    private static final int CONST_INT6 = 20;
    private static final int CONST_INT7 = 20;
    private static final int CONST_INT8 = 28;
    private static final int CONST_INT9 = 16;
    private static final int CONST_INT10 = 2;
    private static final int CONST_INT11 = 24;
    private static final int CONST_INT12 = 24;
    private static final int CONST_INT13 = 8;
    private static final int CONST_INT14 = 6;
    private static final int CONST_INT15 = 14;
    private static final int CONST_INT16 = 20;
    public static final int CONST_INT17 = 500;
    private final KillEffectRenderSpec killEffectRenderSpec;

    public BlackHoleKillRenderer() {
        KillEffectRenderSpec var2 = KillEffectRenderSpec.evaluate();
        this(var2);
    }

    public BlackHoleKillRenderer(KillEffectRenderSpec arg0) {
        this.killEffectRenderSpec = arg0;
    }

    private static double[][] computeMatrix(double[] arg0) {
        int var26 = 0;
        double[] var27 = null;
        if (arg0 == null) {
            var27 = new double[3];
            var27[0] = 0.0;
            var27[1] = 0.0;
            var27[2] = -1.0;
        } else {
            var27 = GeometryPrimitives.computeVector(arg0);
        }
        double[] var8 = var27;
        if (Double.compare(Math.abs(var8[1]), 0.97) > 0) {
            var26 = new double[3];
            var26[0] = 1.0;
            var26[1] = 0.0;
            var26[2] = 0.0;
        } else {
            var26 = new double[3];
            var26[0] = 0.0;
            var26[1] = 1.0;
            var26[2] = 0.0;
        }
        double[] var21 = GeometryPrimitives.computeVector(GeometryPrimitives.computeVector1(var26, var8));
        double[][] var23 = new double[3][];
        var23[0] = var21;
        double[] var47 = GeometryPrimitives.computeVector1(var8, var21);
        var23[1] = var47;
        var23[2] = var8;
        return var23;
    }

    private static double[] computeVector(double[][] arg0, double arg1, double arg2) {
        double var25 = (Math.cos(arg1) * arg2);
        double var31 = (Math.sin(arg1) * arg2);
        double var37 = (arg0[0][0] * var25);
        double var51 = (arg0[1][0] * var31);
        double var39 = (var37 + var51);
        double var57 = (arg0[0][1] * var25);
        double var70 = (arg0[1][1] * var31);
        double var59 = (var57 + var70);
        double var76 = (arg0[0][2] * var25);
        double var89 = (arg0[1][2] * var31);
        double var78 = (var76 + var89);
        return GeometryPrimitives.computeVector(var39, var59, var78);
    }

    private static void drawGeometryPart(CosmeticDrawBuilder arg0, double[][] arg1, double arg2, double arg3, int arg4, IGradientColorSampler arg5) {
        int var31 = 0;
        double var66 = (arg2 + 1.0E-6);
        if (Double.compare(arg3, var66) <= 0) {
            return;
        }
        var31 = 0;
        while (true) {
            if (var31 >= arg4) break;
            double var44 = ((double) var31);
            double var69 = ((double) arg4);
            double var46 = (var44 / var69);
            double var50 = ((double) (var31 + 1));
            double var72 = ((double) arg4);
            double var52 = (var50 / var72);
            double var56 = (var46 * 6.283185307179586);
            double var60 = (var52 * 6.283185307179586);
            double[] var65 = computeVector(arg1, var56, arg2);
            double[] var81 = computeVector(arg1, var56, arg3);
            double[] var83 = computeVector(arg1, var60, arg3);
            double[] var89 = computeVector(arg1, var60, arg2);
            int var93 = arg5.computeColorOrIndex(var46, 0.0);
            int var97 = arg5.computeColorOrIndex(var46, 1.0);
            int var101 = arg5.computeColorOrIndex(var52, 1.0);
            int var105 = arg5.computeColorOrIndex(var52, 0.0);
            arg0.applyStep(var65, var81, var83, var89, var93, var97, var101, var105);
            var31 = (var31 + 1);
        }
    }

    public void renderEffect(CosmeticDrawBuilder arg0) {
        double var87 = 0.0;
        double var165 = 0.0;
        double var62 = this.killEffectRenderSpec.life();
        double var66 = this.killEffectRenderSpec.age();
        if (Double.compare(var62, 1.0) >= 0) {
            return;
        }
        double var73 = this.killEffectRenderSpec.height();
        double var81 = KillEffectMathUtils.computeDoubleMetric(Math.pow((var73 / 1.8), 0.6), 0.55, 2.6);
        if (Double.compare(arg0.computeDoubleMetric(), 1.0E-6) > 0) {
            var87 = arg0.computeDoubleMetric();
        } else {
            var87 = 1.0;
        }
        double var44 = var87;
        IGroundHeightSampler var91 = this.killEffectRenderSpec.ground();
        double var99 = (GeometryPrimitives.computeDoubleMetric(((this.killEffectRenderSpec.seed() * 71) + 5)) * 6.283185307179586);
        if (Double.compare(GeometryPrimitives.computeDoubleMetric(((this.killEffectRenderSpec.seed() * 71) + 6)), 0.5) < 0) {
            var165 = -1.0;
        } else {
            var165 = 1.0;
        }
        double var49 = var165;
        double var113 = GeometryPrimitives.computeDoubleMetric((var62 / 0.06));
        double var251 = GeometryPrimitives.computeDoubleMetric(((var62 - 0.94) / 0.06));
        double var200 = (1.0 - var251);
        double var115 = (var113 * var200);
        double var121 = GeometryPrimitives.computeDoubleMetric((var62 / 0.16));
        double var129 = GeometryPrimitives.computeDoubleMetric(((var62 - 0.72) / 0.2));
        double var135 = ((0.42 * var81) * var121);
        double var255 = (0.92 * var129);
        double var214 = (1.0 - var255);
        double var137 = (var135 * var214);
        double var141 = (0.52 * var73);
        double var220 = (0.3 * var81);
        double var263 = GeometryPrimitives.computeDoubleMetric((var62 / 0.35));
        double var222 = (var220 * var263);
        double var143 = (var141 + var222);
        if (Double.compare(var115, 0.004) > 0) {
            this.drawGeometryPart(arg0, var91, var81, var66, var99, var49, var115, var121);
        }
        if (Double.compare(var115, 0.004) > 0) {
            if (Double.compare(var137, 0.01) > 0) {
                CosmeticDrawBuilder var156 = arg0.drawGeometryPart();
                CosmeticDrawBuilder var158 = arg0.drawGeometryPart(0.0, var143, 0.0);
                double var239 = (0.35 * var66);
                double var176 = (var99 + var239);
                CosmeticDrawBuilder var159 = var158.drawGeometryPart2(var176);
                double var277 = Math.sin((var66 * 0.8));
                double var243 = (0.1 * var277);
                double var180 = (0.42 + var243);
                CosmeticDrawBuilder var160 = var159.drawGeometryPart3(var180);
                this.drawGeometryPart(arg0, var44, var81, var137, var66, var62, var99, var49, var115, var121, var129);
                CosmeticDrawBuilder var163 = arg0.drawGeometryPart1();
            }
            this.drawGeometryPart(arg0, var143, var81, var62, var99);
            return;
        }
    }

    private void drawGeometryPart(CosmeticDrawBuilder arg0, IGroundHeightSampler arg1, double arg2, double arg3, double arg4, double arg5, double arg6, double arg7) {
        int var37 = 0;
        int var43 = 0;
        int var50 = arg0.counterValue;
        int var52 = arg0.counterValue1;
        int var55 = ColorMathUtils.computeColorOrIndex8(arg0.counterValue, 0.9);
        double var58 = (0.3 * arg6);
        double var149 = ((1.6 * arg2) * arg7);
        IGradientColorSampler var199 = (p0, p1) -> BlackHoleKillRenderer.lambda$c(var55, var58, p0, p1);
        KillEffectMathUtils.drawGeometryPart(arg0, arg1, 0.03, var149, 20, var199);
        double var63 = (0.5 * arg6);
        var37 = 0;
        while (true) {
            if (var37 >= 6) break;
            double var152 = ((double) var37);
            double var115 = ((6.283185307179586 * var152) / 6.0);
            double var69 = (arg4 + var115);
            double[] var72 = new double[15];
            double[] var74 = new double[15];
            double[] var76 = new double[15];
            var43 = 0;
            while (true) {
                if (var43 > 14) break;
                double var83 = (((double) var43) / 14.0);
                double var89 = ((3.6 * arg2) * arg7);
                double var159 = (0.9 * var83);
                double var127 = (1.0 - var159);
                double var91 = (var89 * var127);
                double var163 = (arg3 * 0.8);
                double var193 = ((var83 * 2.2) * 3.141592653589793);
                double var165 = (var163 + var193);
                double var131 = (arg5 * var165);
                double var95 = (var69 + var131);
                int var104 = var43;
                double var169 = Math.cos(var95);
                double var135 = (var91 * var169);
                var72[var104] = var135;
                int var105 = var43;
                double var173 = Math.sin(var95);
                double var139 = (var91 * var173);
                var74[var105] = var139;
                int var106 = var43;
                double var143 = (0.1 * arg2);
                double var197 = (0.6 * var83);
                double var179 = (1.0 - var197);
                double var145 = (var143 * var179);
                var76[var106] = var145;
                var43 = (var43 + 1);
            }
            IGradientColorSampler var201 = (p0, p1) -> BlackHoleKillRenderer.lambda$d(var50, var52, var63, p0, p1);
            KillEffectMathUtils.drawGeometryPart(arg0, arg1, 0.04, var72, var74, var76, var201);
            var37 = (var37 + 1);
        }
    }

    private void drawGeometryPart(CosmeticDrawBuilder arg0, double arg1, double arg2, double arg3, double arg4, double arg5, double arg6, double arg7, double arg8, double arg9, double arg10) {
        int var69 = 0;
        int var72 = 0;
        int var81 = 0;
        int var258 = 0;
        double var259 = 0.0;
        int var790 = 0;
        int var798 = 0;
        int var443 = 0;
        double[] var90 = arg0.computeVector1();
        double[][] var92 = computeMatrix(var90);
        double var94 = KillEffectMathUtils.computeDoubleMetric(var90, 0.0, 0.0, 0.0, arg1);
        int var97 = arg0.counterValue;
        int var99 = arg0.counterValue1;
        int var101 = arg0.counterValue1;
        int var102 = ColorMathUtils.computeColorOrIndex7(var101, 0.75);
        double var105 = (0.2 * arg8);
        double var107 = (var105 * var94);
        double[][] var110 = new double[3][];
        double[][] var444 = var92;
        double[] var445 = var444[0];
        var110[0] = var445;
        double[][] var446 = var92;
        int var504 = 1;
        double[] var447 = var446[var504];
        var110[1] = var447;
        double[][] var448 = var92;
        int var505 = 2;
        double[] var449 = var448[var505];
        var110[2] = var449;
        double var113 = (-0.1 * arg3);
        CosmeticDrawBuilder var116 = arg0.drawGeometryPart();
        double[] var264 = var92[2];
        int var318 = 0;
        double var265 = var264[var318];
        double var450 = var113;
        double var267 = (var265 * var450);
        double[][] var452 = var92;
        int var506 = 2;
        double[] var453 = var452[var506];
        int var507 = 1;
        double var454 = var453[var507];
        double var456 = (var454 * var113);
        double[] var594 = var92[2];
        double var595 = var594[2];
        double var597 = (var595 * var113);
        CosmeticDrawBuilder var118 = arg0.drawGeometryPart(var267, var456, var597);
        double var319 = arg3;
        double var121 = (3.2 * var319);
        var69 = 0;
        while (true) {
            int var124 = var69;
            if (var124 >= 16) break;
            int var125 = var69;
            double var126 = ((double) var125);
            double var321 = 16.0;
            double var128 = (var126 / var321);
            int var130 = var69;
            int var131 = (var130 + 1);
            double var132 = ((double) var131);
            double var323 = 16.0;
            double var134 = (var132 / var323);
            double var458 = 0.0;
            double[] var273 = GeometryPrimitives.computeVector(0.0, var458, 0.0);
            double[][] var325 = var110;
            double var460 = var128;
            double var462 = (var460 * 6.283185307179586);
            double[] var326 = computeVector(var325, var462, var121);
            double[][] var464 = var110;
            double var508 = var134;
            double var510 = (var508 * 6.283185307179586);
            double[] var465 = computeVector(var464, var510, var121);
            double var607 = (var107 * 0.9);
            int var513 = ColorMathUtils.computeColorOrIndex5(var99, var607);
            int var610 = ColorMathUtils.computeColorOrIndex5(var97, 0.0);
            int var654 = ColorMathUtils.computeColorOrIndex5(var97, 0.0);
            arg0.applyStep(var273, var326, var465, var513, var610, var654);
            var69 = (var69 + 1);
        }
        CosmeticDrawBuilder var138 = arg0.drawGeometryPart1();
        var790 = 0;
        while (true) {
            int var140 = var790;
            if (var140 >= 2) break;
            double var404 = 0.55;
            double var226 = (arg4 * var404);
            int var406 = var790;
            double var407 = ((double) var406);
            double var409 = (var407 * 0.5);
            double var228 = (var226 + var409);
            double var230 = KillEffectMathUtils.computeDoubleMetric(var228);
            double var793 = var230;
            double var411 = 1.6;
            double var577 = (4.2 * var793);
            double var413 = (var411 + var577);
            double var234 = (arg3 * var413);
            double var800 = var234;
            double var415 = 1.0;
            double var417 = (var415 - var793);
            double var238 = (0.28 * var417);
            double var419 = var793;
            double var421 = (var419 / 0.1);
            double var423 = GeometryPrimitives.computeDoubleMetric(var421);
            double var240 = (var238 * var423);
            double var425 = arg8;
            double var242 = (var240 * var425);
            double var427 = var94;
            double var244 = (var242 * var427);
            double var429 = arg9;
            double var246 = (var244 * var429);
            double var804 = var246;
            double var431 = 0.01;
            int var250 = Double.compare(var804, var431);
            if (var250 < 0) {
            } else {
                double var433 = arg3;
                double var253 = (0.06 * var433);
                double var435 = 1.0;
                double var437 = (var435 + var793);
                double var255 = (var253 * var437);
                double var439 = var800;
                double var441 = (var439 - var255);
                double var589 = (var800 + var255);
                int var721 = var97;
                double var734 = var804;
                IGradientColorSampler var722 = (p0, p1) -> BlackHoleKillRenderer.lambda$b(var721, var734, p0, p1);
                drawGeometryPart(arg0, var92, var441, var589, 24, var722);
            }
            var790 = (var790 + 1);
        }
        double var327 = arg3;
        double var143 = (1.55 * var327);
        double var785 = var143;
        double var329 = arg3;
        double var147 = (3.9 * var329);
        double var787 = var147;
        double[] var150 = new double[3];
        int var331 = 0;
        double var466 = 0.0;
        var150[var331] = var466;
        int var332 = 1;
        double var468 = 0.35;
        var150[var332] = var468;
        int var333 = 2;
        double var470 = 1.0;
        var150[var333] = var470;
        double[][][] var152 = new double[3][33][];
        double[][][] var789 = var152;
        int[][] var154 = new int[3][33];
        double var334 = 1.7;
        double var157 = (arg6 * var334);
        double var791 = var157;
        var72 = 0;
        while (true) {
            int var160 = var72;
            if (var160 >= 3) break;
            double var336 = var787;
            double var338 = (var336 - var785);
            int var611 = var72;
            double var517 = var150[var611];
            double var340 = (var338 * var517);
            double var163 = (var785 + var340);
            double var796 = var163;
            int var278 = var72;
            double var166 = var150[var278];
            double var342 = 0.35;
            int var168 = Double.compare(var166, var342);
            if (var168 < 0) {
                double[] var374 = var150;
                int var490 = var72;
                double var375 = var374[var490];
                double var377 = (var375 / 0.35);
                var258 = ColorMathUtils.computeColorOrIndex(var102, var99, var377);
            } else {
                double[] var344 = var150;
                int var472 = var72;
                double var345 = var344[var472];
                double var347 = (var345 - 0.35);
                double var349 = (var347 / 0.65);
                var258 = ColorMathUtils.computeColorOrIndex(var99, var97, var349);
            }
            int var76 = var258;
            int var170 = var72;
            if (var170 == 0) {
                var259 = 0.92;
            } else {
                int var196 = var72;
                if (var196 == 1) {
                    var259 = 0.8;
                } else {
                    var259 = 0.0;
                }
                double var77 = var259;
                var81 = 0;
                while (true) {
                    int var172 = var81;
                    if (var172 > 32) break;
                    int var173 = var81;
                    int var174 = (var173 % 32);
                    double var175 = ((double) var174);
                    double var351 = 32.0;
                    double var177 = (var175 / var351);
                    double var353 = 6.283185307179586;
                    double var179 = (var177 * var353);
                    int var282 = var72;
                    double[][] var182 = var789[var282];
                    int var283 = var81;
                    double var355 = var796;
                    double var525 = Math.cos(var179);
                    double var357 = (var355 * var525);
                    double var723 = Math.sin(var179);
                    double var657 = (var796 * var723);
                    double[] var359 = GeometryPrimitives.computeVector(var357, 0.0, var657);
                    var182[var283] = var359;
                    double var360 = 0.5;
                    double var531 = (2.0 * var179);
                    double var725 = arg4;
                    double var663 = (arg7 * var725);
                    double var727 = 8.0;
                    int var43 = var72;
                    double var763 = var150[var43];
                    double var748 = (0.5 + var763);
                    double var729 = (var727 / var748);
                    double var665 = (var663 * var729);
                    double var533 = (var531 - var665);
                    double[] var731 = var150;
                    int var37 = var72;
                    double var732 = var731[var37];
                    double var669 = (5.0 * var732);
                    double var535 = (var533 + var669);
                    double var537 = Math.sin(var535);
                    double var362 = (var360 * var537);
                    double var185 = (0.5 + var362);
                    double var364 = 0.28;
                    double var541 = (var179 - var791);
                    double var543 = Math.cos(var541);
                    double var366 = (var364 * var543);
                    double var189 = (0.72 + var366);
                    int var368 = var72;
                    double[][] var285 = var789[var368];
                    int var369 = var81;
                    double[] var286 = var285[var369];
                    int var370 = 0;
                    double var287 = var286[var370];
                    double[][][] var473 = var789;
                    int var545 = var72;
                    double[][] var474 = var473[var545];
                    int var546 = var81;
                    double[] var475 = var474[var546];
                    double var476 = var475[1];
                    int var673 = var72;
                    double[][] var613 = var789[var673];
                    int var674 = var81;
                    double[] var614 = var613[var674];
                    double var615 = var614[2];
                    double var192 = KillEffectMathUtils.computeDoubleMetric(var90, var287, var476, var615, arg1);
                    int var289 = var72;
                    int[] var195 = var154[var289];
                    int var290 = var81;
                    int var371 = var76;
                    double var478 = 0.65;
                    double var619 = (0.65 * var185);
                    double var621 = (var619 * var189);
                    double var480 = (var478 + var621);
                    int var372 = ColorMathUtils.computeColorOrIndex6(var371, var480);
                    double var482 = var77;
                    double var736 = var185;
                    double var704 = (0.4 * var736);
                    double var625 = (0.6 + var704);
                    double var484 = (var482 * var625);
                    double var486 = (var484 * arg8);
                    double var488 = (var486 * var192);
                    int var373 = ColorMathUtils.computeColorOrIndex5(var372, var488);
                    var195[var290] = var373;
                    var81 = (var81 + 1);
                }
                var72 = (var72 + 1);
            }
        }
        byte[] var199 = new boolean[32];
        byte[] var795 = var199;
        var798 = 0;
        while (true) {
            int var201 = var798;
            if (var201 >= 32) break;
            if (var90 == null) {
                int var297 = var798;
                int var389 = 1;
                var795[var297] = var389;
            } else {
                double[][] var204 = var789[1];
                int var295 = var798;
                double[] var205 = var204[var295];
                int var296 = var798;
                double[] var379 = var205;
                int var491 = 0;
                double var380 = var379[var491];
                double var551 = var90[0];
                double var382 = (var380 * var551);
                double var554 = var205[1];
                double var677 = var90[1];
                double var556 = (var554 * var677);
                double var384 = (var382 + var556);
                double var559 = var205[2];
                int var707 = 2;
                double var680 = var90[var707];
                double var561 = (var559 * var680);
                double var386 = (var384 + var561);
                int var388 = Double.compare(var386, 0.0);
                if (var388 < 0) {
                    var443 = 1;
                } else {
                    var443 = 0;
                }
                var795[var296] = var443;
            }
            var798 = (var798 + 1);
        }
        double[][][] var390 = var789;
        int[][] var492 = var154;
        this.drawGeometryPart(arg0, var390, var492, var795, true);
        double[] var391 = var90;
        double var493 = arg2;
        double var708 = arg4;
        double var738 = arg5;
        double var765 = var94;
        this.drawGeometryPart(arg0, var391, var493, arg3, var708, var738, arg8, var765, true);
        int var211 = ColorMathUtils.computeColorOrIndex7(var97, 0.2);
        int var799 = var211;
        double var495 = 0.0;
        double var710 = arg3;
        int var740 = 12;
        int var750 = 6;
        double var752 = arg3;
        CosmeticDrawBuilder var767 = arg0;
        double var774 = arg8;
        IGradientColorSampler var754 = (p0, p1) -> BlackHoleKillRenderer.drawGeometryPart(var752, var767, var799, var774, var90, arg1, p0, p1);
        GeometryPrimitives.drawGeometryPart(arg0, 0.0, var495, 0.0, var710, var740, var750, var754);
        double var392 = arg8;
        double var215 = (0.95 * var392);
        double var394 = var94;
        double var217 = (var215 * var394);
        double var802 = var217;
        double var396 = 1.1;
        double var398 = (var396 * arg3);
        double var570 = (1.3 * arg3);
        double var712 = arg4;
        int var741 = var102;
        int var751 = var97;
        double var755 = var802;
        IGradientColorSampler var714 = (p0, p1) -> BlackHoleKillRenderer.lambda$a(var712, var741, var751, var755, p0, p1);
        drawGeometryPart(arg0, var92, var398, var570, 28, var714);
        double[] var400 = var90;
        double var497 = arg2;
        double var715 = arg4;
        double var742 = arg5;
        double var757 = arg8;
        double var768 = var94;
        int var776 = 0;
        this.drawGeometryPart(arg0, var400, var497, arg3, var715, var742, var757, var768, var776);
        double[][][] var401 = var789;
        int[][] var499 = var154;
        this.drawGeometryPart(arg0, var401, var499, var795, false);
        double[] var402 = var90;
        double var500 = arg1;
        double var717 = arg3;
        double var744 = var785;
        double var759 = var787;
        double var770 = arg4;
        double var777 = arg6;
        double var781 = arg7;
        this.drawGeometryPart(arg0, var402, var500, arg2, var717, var744, var759, var770, var777, var781, arg8, var102, var97);
        double[] var403 = var90;
        double var502 = arg1;
        double var719 = var785;
        double var746 = var787;
        double var761 = arg4;
        double var772 = arg7;
        double var779 = arg8;
        int var783 = var102;
        int var784 = var97;
        this.drawGeometryPart(arg0, var403, var502, arg2, var719, var746, var761, var772, var779, var783, var784);
    }

    private void drawGeometryPart(CosmeticDrawBuilder arg0, double[][][] arg1, int[][] arg2, boolean[] arg3, boolean arg4) {
        int var17 = 0;
        int var18 = 0;
        var17 = 0;
        while (true) {
            if (var17 < 32) {
                int var25 = var17;
                if (arg3[var25] != arg4) {
                }
            } else {
                return;
                var18 = 0;
                while (true) {
                    if (var18 >= 2) break;
                    int var8 = var18;
                    double[][] var29 = arg1[var8];
                    int var31 = var17;
                    double[] var30 = var29[var31];
                    int var35 = (var18 + 1);
                    double[][] var33 = arg1[var35];
                    int var36 = var17;
                    double[] var34 = var33[var36];
                    int var41 = (var18 + 1);
                    double[][] var38 = arg1[var41];
                    int var43 = (var17 + 1);
                    double[] var39 = var38[var43];
                    int var48 = var18;
                    double[][] var45 = arg1[var48];
                    int var50 = (var17 + 1);
                    double[] var46 = var45[var50];
                    int var54 = var18;
                    int[] var52 = arg2[var54];
                    int var55 = var17;
                    int var53 = var52[var55];
                    int var59 = (var18 + 1);
                    int[] var57 = arg2[var59];
                    int var60 = var17;
                    int var58 = var57[var60];
                    int var65 = (var18 + 1);
                    int[] var62 = arg2[var65];
                    int var67 = (var17 + 1);
                    int var63 = var62[var67];
                    int var72 = var18;
                    int[] var69 = arg2[var72];
                    int var74 = (var17 + 1);
                    int var70 = var69[var74];
                    arg0.applyStep(var30, var34, var39, var46, var53, var58, var63, var70);
                    var18 = (var18 + 1);
                }
                var17 = (var17 + 1);
            }
        }
    }

    private void drawGeometryPart(CosmeticDrawBuilder arg0, double[] arg1, double arg2, double arg3, double arg4, double arg5, double arg6, double arg7, boolean arg8) {
        int var38 = 0;
        int var43 = 0;
        int var108 = 0;
        int var109 = 0;
        double var52 = GeometryPrimitives.computeDoubleMetric(((arg5 - 0.12) / 0.1));
        double var179 = GeometryPrimitives.computeDoubleMetric(((arg5 - 0.7) / 0.12));
        double var118 = (1.0 - var179);
        double var54 = (var52 * var118);
        if (Double.compare(((var54 * arg6) * arg7), 0.01) < 0) {
            return;
        }
        int var65 = ColorMathUtils.computeColorOrIndex7(arg0.counterValue1, 0.5);
        int var67 = arg0.counterValue;
        var38 = -1;
        while (true) {
            if (var38 > 1) break;
            double var183 = (arg4 * 9.0);
            double var227 = (((double) var38) * 1.9);
            double var187 = Math.sin((var183 + var227));
            double var128 = (0.15 * var187);
            double var193 = Math.sin((arg4 * 5.3));
            double var130 = (var128 * var193);
            double var72 = (0.85 + var130);
            double var134 = (1.4 * var72);
            double var80 = (((1.6 + var134) * arg2) * var54);
            if (arg1 != null) {
                double var84 = ((double) var38);
                double var141 = arg1[1];
                if (Double.compare((var84 * var141), 0.0) < 0) {
                    var109 = 1;
                }
            } else {
                var109 = 0;
            }
            var43 = var109;
            if (arg1 == null) {
                if (var38 < 0) {
                    var108 = 1;
                } else {
                    var108 = 0;
                }
                var43 = var108;
            }
            if (var43 != arg8) {
            } else {
                double var98 = (((0.55 * var54) * arg6) * arg7);
                double var156 = ((((double) var38) * 0.9) * arg3);
                double[] var102 = GeometryPrimitives.computeVector(0.0, var156, 0.0);
                double var159 = ((double) var38);
                double var207 = ((0.9 * arg3) + var80);
                double var161 = (var159 * var207);
                double[] var105 = GeometryPrimitives.computeVector(0.0, var161, 0.0);
                double var169 = (0.28 * arg3);
                IGradientColorSampler var236 = (p0, p1) -> BlackHoleKillRenderer.lambda$c(var65, var67, var98, p0, p1);
                GeometryPrimitives.drawGeometryPart(arg0, var102, var105, var169, 6, var236);
                double var212 = ((double) var38);
                double var239 = (0.9 * arg3);
                double var255 = (var80 * 1.15);
                double var241 = (var239 + var255);
                double var214 = (var212 * var241);
                double[] var166 = GeometryPrimitives.computeVector(0.0, var214, 0.0);
                double var173 = (0.11 * arg3);
                IGradientColorSampler var246 = (p0, p1) -> BlackHoleKillRenderer.lambda$a(var65, var98, p0, p1);
                GeometryPrimitives.drawGeometryPart(arg0, var102, var166, var173, 4, var246);
            }
            var38 = (var38 + 2);
        }
    }

    private void drawGeometryPart(CosmeticDrawBuilder arg0, double[] arg1, double arg2, double arg3, double arg4, double arg5, double arg6, double arg7, double arg8, double arg9, double arg10, int arg11, int arg12) {
        int var35 = 0;
        int var39 = 0;
        int var42 = 0;
        int var379 = 0;
        var35 = 0;
        while (true) {
            if (var35 < 4) {
                double var212 = ((double) var35);
                double var131 = ((6.283185307179586 * var212) / 4.0);
                double var54 = (arg8 + var131);
                double[][] var57 = new double[21][];
                var39 = 0;
                while (true) {
                    if (var39 > 20) break;
                    double var105 = (((double) var39) / 20.0);
                    double var109 = (arg6 * 1.9);
                    double var184 = (arg5 * 1.02);
                    double var260 = (arg6 * 1.9);
                    double var188 = ((var184 - var260) * var105);
                    double var111 = (var109 + var188);
                    double var266 = (arg7 * 1.1);
                    double var358 = Math.pow(var105, 0.85);
                    double var325 = (8.168140899333462 * var358);
                    double var268 = (var266 + var325);
                    double var192 = (arg9 * var268);
                    double var115 = (var54 + var192);
                    int var128 = var39;
                    double var272 = Math.cos(var115);
                    double var196 = (var111 * var272);
                    double var362 = Math.sin(var115);
                    double var329 = (var111 * var362);
                    double[] var198 = GeometryPrimitives.computeVector(var196, 0.0, var329);
                    var57[var128] = var198;
                    var39 = (var39 + 1);
                }
                return;
                double[][] var61 = new double[21][];
                double[][] var63 = new double[21][];
                int[] var65 = new int[21];
                var42 = 0;
                while (true) {
                    if (var42 > 20) break;
                    int var123 = var42;
                    double[] var72 = GeometryPrimitives.computeVector(var57, var123);
                    double[] var77 = GeometryPrimitives.computeVector(GeometryPrimitives.computeVector1(GeometryPrimitives.computeVector(0.0, 1.0, 0.0), var72));
                    double var81 = (((double) var42) / 20.0);
                    double var85 = (0.05 * arg3);
                    double var224 = (0.5 * var81);
                    double var144 = (1.0 - var224);
                    double var87 = (var85 * var144);
                    double var297 = computeDoubleMetric(var81);
                    double var228 = (0.4 * var297);
                    double var148 = (0.6 + var228);
                    double var89 = (var87 * var148);
                    int var125 = var42;
                    int var201 = var42;
                    double var152 = var57[var201][0];
                    double var233 = (var77[0] * var89);
                    double var154 = (var152 + var233);
                    int var281 = var42;
                    double var237 = var57[var281][1];
                    int var333 = var42;
                    double var303 = var57[var333][2];
                    double var347 = (var77[2] * var89);
                    double var305 = (var303 + var347);
                    double[] var156 = GeometryPrimitives.computeVector(var154, var237, var305);
                    var61[var125] = var156;
                    int var126 = var42;
                    int var203 = var42;
                    double var159 = var57[var203][0];
                    double var242 = (var77[0] * var89);
                    double var161 = (var159 - var242);
                    int var284 = var42;
                    double var246 = var57[var284][1];
                    int var335 = var42;
                    double var311 = var57[var335][2];
                    double var352 = (var77[2] * var89);
                    double var313 = (var311 - var352);
                    double[] var163 = GeometryPrimitives.computeVector(var161, var246, var313);
                    var63[var126] = var163;
                    double var95 = (0.6 * arg10);
                    double var250 = Math.pow(var81, 0.7);
                    double var170 = Math.sin((3.141592653589793 * var250));
                    double var97 = (var95 * var170);
                    int var252 = var42;
                    double var207 = var57[var252][0];
                    int var317 = var42;
                    double var288 = var57[var317][1];
                    int var354 = var42;
                    double var339 = var57[var354][2];
                    double var173 = KillEffectMathUtils.computeDoubleMetric(arg1, var207, var288, var339, arg2);
                    double var99 = (var97 * var173);
                    int var127 = var42;
                    int var177 = ColorMathUtils.computeColorOrIndex5(ColorMathUtils.computeColorOrIndex(arg12, arg11, var81), var99);
                    var65[var127] = var177;
                    var42 = (var42 + 1);
                }
                var379 = 0;
                while (true) {
                    if (var379 >= 20) break;
                    int var133 = var379;
                    double[] var122 = var61[var133];
                    int var27 = var379;
                    double[] var135 = var63[var27];
                    int var217 = (var379 + 1);
                    double[] var200 = var63[var217];
                    int var277 = (var379 + 1);
                    double[] var219 = var61[var277];
                    int var290 = var379;
                    int var279 = var65[var290];
                    int var31 = var379;
                    int var292 = var65[var31];
                    int var341 = (var379 + 1);
                    int var332 = var65[var341];
                    int var365 = (var379 + 1);
                    int var343 = var65[var365];
                    arg0.applyStep(var122, var135, var200, var219, var279, var292, var332, var343);
                    var379 = (var379 + 1);
                }
                var35 = (var35 + 1);
            }
        }
    }

    private static double computeDoubleMetric(double arg0) {
        double var10 = (arg0 * 2.0);
        return Math.min(1.0, var10);
    }

    private void drawGeometryPart(CosmeticDrawBuilder arg0, double[] arg1, double arg2, double arg3, double arg4, double arg5, double arg6, double arg7, double arg8, int arg9, int arg10) {
        int var37 = 0;
        var37 = 0;
        while (true) {
            if (var37 < 20) {
                int var71 = (this.killEffectRenderSpec.seed() * 97);
                int var179 = (var37 * 19);
                int var72 = (var71 + var179);
                double var307 = GeometryPrimitives.computeDoubleMetric((var72 + 1));
                double var197 = (0.45 * var307);
                double var75 = (0.35 + var197);
                double var79 = GeometryPrimitives.computeDoubleMetric((var72 + 2));
                double var85 = (GeometryPrimitives.computeDoubleMetric((var72 + 3)) * 6.283185307179586);
                double var93 = KillEffectMathUtils.computeDoubleMetric(((arg6 * var75) + var79));
                double var97 = (arg5 * 1.8);
                double var209 = (arg4 * 1.05);
                double var313 = (arg5 * 1.8);
                double var211 = (var209 - var313);
                double var317 = Math.pow(var93, 1.15);
                double var213 = (var211 * var317);
                double var99 = (var97 + var213);
                double var321 = (arg6 * 0.9);
                double var395 = ((var93 * 1.7) * 3.141592653589793);
                double var323 = (var321 + var395);
                double var217 = (arg7 * var323);
                double var103 = (var85 + var217);
                double var113 = (((GeometryPrimitives.computeDoubleMetric((var72 + 4)) - 0.5) * 0.25) * var99);
                double var227 = (1.0 - var93);
                double var115 = (var113 * var227);
                double var231 = Math.cos(var103);
                double var119 = (var99 * var231);
                double var399 = Math.sin(var103);
                double var329 = (var99 * var399);
                double[] var121 = GeometryPrimitives.computeVector(var119, var115, var329);
                double var124 = -(arg7);
                double var237 = Math.sin(var103);
                double var126 = (var124 * var237);
                double var403 = Math.cos(var103);
                double var333 = (arg7 * var403);
                double[] var128 = GeometryPrimitives.computeVector(var126, 0.0, var333);
                double var243 = (0.32 * var93);
                double var133 = ((0.05 + var243) * arg3);
                double var137 = (0.02 * arg3);
                double var251 = (1.0 + var93);
                double var139 = (var137 * var251);
                double var143 = (0.9 * arg8);
                double var261 = Math.pow(Math.sin((3.141592653589793 * var93)), 0.9);
                double var145 = (var143 * var261);
                double var296 = var121[0];
                double var379 = var121[1];
                double var434 = var121[2];
                double var264 = KillEffectMathUtils.computeDoubleMetric(arg1, var296, var379, var434, arg2);
                double var147 = (var145 * var264);
                if (Double.compare(var147, 0.03) < 0) {
                }
            } else {
                return;
                double var154 = Math.cos(var103);
                double var346 = Math.sin(var103);
                double[] var156 = GeometryPrimitives.computeVector(var154, 0.0, var346);
                double var158 = var121[0];
                double var273 = (var128[0] * var133);
                double var160 = (var158 - var273);
                double var276 = var121[1];
                double var351 = var121[2];
                double var409 = (var128[2] * var133);
                double var353 = (var351 - var409);
                double[] var162 = GeometryPrimitives.computeVector(var160, var276, var353);
                double var164 = var121[0];
                double var283 = ((var128[0] * var133) * 0.5);
                double var166 = (var164 - var283);
                double var286 = var121[1];
                double var360 = var121[2];
                double var416 = ((var128[2] * var133) * 0.5);
                double var362 = (var360 - var416);
                double[] var168 = GeometryPrimitives.computeVector(var166, var286, var362);
                int var170 = ColorMathUtils.computeColorOrIndex5(arg9, var147);
                int var172 = ColorMathUtils.computeColorOrIndex(arg10, arg9, 0.5);
                double var190 = (var147 * 0.6);
                int var173 = ColorMathUtils.computeColorOrIndex5(var172, var190);
                int var175 = ColorMathUtils.computeColorOrIndex5(arg10, 0.0);
                double var291 = var168[0];
                double var367 = (var156[0] * var139);
                double var293 = (var291 + var367);
                double var370 = var168[1];
                double var421 = var168[2];
                double var454 = (var156[2] * var139);
                double var423 = (var421 + var454);
                double[] var295 = GeometryPrimitives.computeVector(var293, var370, var423);
                double var373 = var168[0];
                double var428 = (var156[0] * var139);
                double var375 = (var373 - var428);
                double var431 = var168[1];
                double var459 = var168[2];
                double var469 = (var156[2] * var139);
                double var461 = (var459 - var469);
                double[] var377 = GeometryPrimitives.computeVector(var375, var431, var461);
                arg0.applyStep(var162, var295, var121, var377, var175, var173, var170, var173);
            }
            var37 = (var37 + 1);
        }
    }

    private void drawGeometryPart(CosmeticDrawBuilder arg0, double arg1, double arg2, double arg3, double arg4) {
        int var41 = 0;
        if (Double.compare(arg3, 0.9) <= 0) {
            return;
        }
        double var57 = ((arg3 - 0.9) / 0.1);
        double var150 = Math.pow((1.0 - var57), 1.2);
        double var61 = (0.9 * var150);
        double var156 = GeometryPrimitives.computeDoubleMetric((var57 / 0.05));
        double var63 = (var61 * var156);
        if (Double.compare(var63, 0.01) < 0) {
            return;
        }
        CosmeticDrawBuilder var69 = arg0.drawGeometryPart();
        CosmeticDrawBuilder var71 = arg0.drawGeometryPart(0.0, arg1, 0.0);
        double[][] var75 = computeMatrix(arg0.computeVector1());
        double var281 = Math.pow((1.0 - var57), 2.0);
        double var236 = (1.0 - var281);
        double var162 = (1.7 * var236);
        double var80 = ((0.25 + var162) * arg2);
        double var84 = (0.08 * arg2);
        double var240 = (0.5 * var57);
        double var170 = (1.0 - var240);
        double var86 = (var84 * var170);
        int var90 = ColorMathUtils.computeColorOrIndex7(arg0.counterValue1, 0.85);
        int var92 = arg0.counterValue;
        double var174 = (var80 - var86);
        double var246 = (var80 + var86);
        IGradientColorSampler var302 = (p0, p1) -> BlackHoleKillRenderer.lambda$b(var92, var90, var63, p0, p1);
        drawGeometryPart(arg0, var75, var174, var246, 24, var302);
        var41 = 0;
        while (true) {
            if (var41 >= 8) break;
            int var99 = (this.killEffectRenderSpec.seed() * 83);
            int var134 = (var41 * 31);
            int var100 = (var99 + var134);
            double var105 = (GeometryPrimitives.computeDoubleMetric((var100 + 1)) * 6.283185307179586);
            double var181 = GeometryPrimitives.computeDoubleMetric((var100 + 2));
            double var113 = Math.asin(((2.0 * var181) - 1.0));
            double[] var116 = new double[3];
            double var204 = Math.cos(var113);
            double var262 = Math.cos(var105);
            double var206 = (var204 * var262);
            var116[0] = var206;
            double var210 = Math.sin(var113);
            var116[1] = var210;
            double var214 = Math.cos(var113);
            double var266 = Math.sin(var105);
            double var216 = (var214 * var266);
            var116[2] = var216;
            double var119 = (0.3 * var80);
            double var290 = GeometryPrimitives.computeDoubleMetric((var100 + 3));
            double var250 = (0.35 * var290);
            double var192 = (1.05 + var250);
            double var123 = (var80 * var192);
            double var139 = (var116[0] * var119);
            double var223 = (var116[1] * var119);
            double var273 = (var116[2] * var119);
            double[] var141 = GeometryPrimitives.computeVector(var139, var223, var273);
            double var198 = (var116[0] * var123);
            double var258 = (var116[1] * var123);
            double var298 = (var116[2] * var123);
            double[] var200 = GeometryPrimitives.computeVector(var198, var258, var298);
            double var228 = (0.05 * arg2);
            IGradientColorSampler var301 = (p0, p1) -> BlackHoleKillRenderer.lambda$a(var90, var92, var63, p0, p1);
            GeometryPrimitives.drawGeometryPart(arg0, var141, var200, var228, 3, var301);
            var41 = (var41 + 1);
        }
        CosmeticDrawBuilder var127 = arg0.drawGeometryPart1();
    }

}
