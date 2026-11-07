package need.clarity.modules.visual.cosmetics.killeffect;

import need.clarity.modules.visual.cosmetics.ColorMathUtils;
import need.clarity.modules.visual.cosmetics.CosmeticDrawBuilder;
import need.clarity.modules.visual.cosmetics.GeometryPrimitives;
import need.clarity.modules.visual.cosmetics.ICosmeticEffectRenderer;
import need.clarity.modules.visual.cosmetics.IGradientColorSampler;

public final class LightningStrikeKillRenderer implements ICosmeticEffectRenderer {
    public static final int CONST_INT = 7314431;
    public static final int CONST_INT1 = 15135487;
    static final int CONST_INT2 = 17;
    static final int CONST_INT3 = 5;
    static final int CONST_INT4 = 6;
    static final int CONST_INT5 = 9;
    static final int CONST_INT6 = 4;
    static final int CONST_INT7 = 24;
    static final int CONST_INT8 = 20;
    static final int CONST_INT9 = 16;
    static final int CONST_INT10 = 12;
    static final int CONST_INT11 = 10;
    public static final int CONST_INT12 = 347;
    private final KillEffectRenderSpec killEffectRenderSpec;

    public LightningStrikeKillRenderer() {
        KillEffectRenderSpec var2 = KillEffectRenderSpec.evaluate();
        this(var2);
    }

    public LightningStrikeKillRenderer(KillEffectRenderSpec arg0) {
        this.killEffectRenderSpec = arg0;
    }

    static double computeDoubleMetric(double arg0, double arg1) {
        if (Double.compare(arg0, 0.0) > 0) {
            if (Double.compare(arg0, arg1) >= 0) {
            }
        }
        return 0.0;
        double var26 = GeometryPrimitives.computeDoubleMetric((arg0 / 0.02));
        double var68 = GeometryPrimitives.computeDoubleMetric(((arg0 - 0.06) / 0.05));
        double var48 = (1.0 - var68);
        double var28 = (var26 * var48);
        double var56 = GeometryPrimitives.computeDoubleMetric(((arg0 - 0.11) / 0.015));
        double var32 = (0.85 * var56);
        double var76 = (arg0 - 0.14);
        double var88 = (arg1 - 0.14);
        double var80 = GeometryPrimitives.computeDoubleMetric((var76 / var88));
        double var60 = (1.0 - var80);
        double var34 = (var32 * var60);
        return Math.max(var28, var34);
    }

    private static double[][] computeMatrix(double[] arg0, double[] arg1, int arg2, double arg3, int arg4, int arg5) {
        int var21 = 0;
        double[][] var30 = new double[arg2][];
        var21 = 0;
        while (true) {
            if (var21 >= arg2) break;
            double var35 = ((double) var21);
            double var71 = (((double) arg2) - 1.0);
            double var37 = (var35 / var71);
            double var45 = Math.pow(Math.sin((3.141592653589793 * var37)), 0.7);
            int var152 = (var21 * 2);
            double var115 = GeometryPrimitives.computeDoubleMetric((arg4 + var152));
            double var81 = ((2.0 * var115) - 1.0);
            double var49 = (0.6 * var81);
            int var191 = (var21 * 2);
            double var160 = GeometryPrimitives.computeDoubleMetric((arg5 + var191));
            double var123 = ((2.0 * var160) - 1.0);
            double var85 = (0.4 * var123);
            double var55 = (((var49 + var85) * arg3) * var45);
            int var154 = (var21 * 2);
            double var128 = GeometryPrimitives.computeDoubleMetric(((arg4 + var154) + 1));
            double var95 = ((2.0 * var128) - 1.0);
            double var59 = (0.6 * var95);
            int var193 = (var21 * 2);
            double var168 = GeometryPrimitives.computeDoubleMetric(((arg5 + var193) + 1));
            double var136 = ((2.0 * var168) - 1.0);
            double var99 = (0.4 * var136);
            double var65 = (((var59 + var99) * arg3) * var45);
            int var68 = var21;
            double var106 = arg0[0];
            double var139 = arg1[0];
            double var173 = arg0[0];
            double var143 = ((var139 - var173) * var37);
            double var110 = ((var106 + var143) + var55);
            double var148 = arg0[1];
            double var178 = arg1[1];
            double var200 = arg0[1];
            double var182 = ((var178 - var200) * var37);
            double var150 = (var148 + var182);
            double var185 = arg0[2];
            double var205 = arg1[2];
            double var214 = arg0[2];
            double var209 = ((var205 - var214) * var37);
            double var189 = ((var185 + var209) + var65);
            double[] var112 = GeometryPrimitives.computeVector(var110, var150, var189);
            var30[var68] = var112;
            var21 = (var21 + 1);
        }
        return var30;
    }

    private static void drawGeometryPart(CosmeticDrawBuilder arg0, double[][] arg1, double[] arg2, double arg3, double arg4, int arg5, double arg6, boolean arg7, double[] arg8, double[] arg9) {
        if (Double.compare(arg6, 0.004) < 0) {
            return;
        }
        IGradientColorSampler var26 = (p0, p1) -> LightningStrikeKillRenderer.lambda$a(arg5, arg6, arg2, arg7, p0, p1);
        GeometryPrimitives.drawGeometryPart(arg0, arg1, arg3, arg4, arg8, var26);
        GeometryPrimitives.drawGeometryPart(arg0, arg1, arg3, arg4, arg9, var26);
    }

    public void renderEffect(CosmeticDrawBuilder arg0) {
        double var234 = 0.0;
        KillEffectRenderSpec var74 = this.killEffectRenderSpec;
        double var75 = var74.life();
        KillEffectRenderSpec var78 = this.killEffectRenderSpec;
        double var79 = var78.age();
        KillEffectRenderSpec var82 = this.killEffectRenderSpec;
        double var83 = var82.life();
        int var87 = Double.compare(var75, 1.0);
        if (var87 >= 0) {
            return;
        }
        KillEffectRenderSpec var89 = this.killEffectRenderSpec;
        double var90 = var89.height();
        KillEffectRenderSpec var93 = this.killEffectRenderSpec;
        double var94 = var93.computeDoubleMetric();
        KillEffectRenderSpec var97 = this.killEffectRenderSpec;
        IGroundHeightSampler var98 = var97.ground();
        double var100 = arg0.computeDoubleMetric();
        int var102 = Double.compare(var100, 1.0E-6);
        if (var102 > 0) {
            var234 = arg0.computeDoubleMetric();
        } else {
            var234 = 1.0;
        }
        double var36 = var234;
        double[] var105 = arg0.computeVector1();
        double var108 = (var90 / 1.8);
        double var110 = Math.pow(var108, 0.5);
        double var112 = KillEffectMathUtils.computeDoubleMetric(var110, 0.6, 2.2);
        double var394 = var83;
        double var276 = (0.45 * var394);
        double var116 = Math.min(0.42, var276);
        double var120 = computeDoubleMetric(var79, var116);
        double var396 = 0.72;
        double var282 = (var75 - var396);
        double var398 = 0.28;
        double var284 = (var282 / var398);
        double var286 = GeometryPrimitives.computeDoubleMetric(var284);
        double var124 = (1.0 - var286);
        KillEffectRenderSpec var127 = this.killEffectRenderSpec;
        int var128 = var127.seed();
        int var129 = (var128 * 61);
        int var130 = (var129 + 3);
        double var131 = GeometryPrimitives.computeDoubleMetric(var130);
        double var133 = (var131 * 6.283185307179586);
        double var137 = Math.max(var79, 0.0);
        double var139 = Math.min(var137, var116);
        double var141 = (var139 * 24.0);
        double var143 = Math.floor(var141);
        int var145 = ((int) var143);
        KillEffectRenderSpec var147 = this.killEffectRenderSpec;
        int var148 = var147.seed();
        int var149 = (var148 * 7);
        int var150 = (var149 + 1000);
        KillEffectRenderSpec var152 = this.killEffectRenderSpec;
        int var153 = var152.seed();
        int var154 = (var153 * 13);
        int var241 = (var145 * 1013);
        int var155 = (var154 + var241);
        int var156 = (var155 + 77);
        int var158 = arg0.counterValue;
        int var160 = arg0.counterValue;
        int var244 = arg0.counterValue1;
        int var161 = ColorMathUtils.computeColorOrIndex(var160, var244, 0.55);
        int var163 = arg0.counterValue1;
        int var164 = ColorMathUtils.computeColorOrIndex7(var163, 0.7);
        int var166 = arg0.counterValue1;
        int var167 = ColorMathUtils.computeColorOrIndex7(var166, 0.85);
        double var400 = var94;
        double var301 = (4.0 * var400);
        double var170 = (13.0 + var301);
        KillEffectRenderSpec var173 = this.killEffectRenderSpec;
        int var174 = var173.seed();
        int var175 = (var174 * 61);
        int var176 = (var175 + 1);
        double var177 = GeometryPrimitives.computeDoubleMetric(var176);
        double var179 = (var177 * 6.283185307179586);
        LightningStrikeKillRenderer var402 = this;
        KillEffectRenderSpec var403 = var402.killEffectRenderSpec;
        int var404 = var403.seed();
        int var405 = (var404 * 61);
        int var441 = 2;
        int var406 = (var405 + var441);
        double var407 = GeometryPrimitives.computeDoubleMetric(var406);
        double var307 = (1.6 * var407);
        double var183 = (1.6 + var307);
        double var186 = var98.computeDoubleMetric(0.0, 0.0);
        double var311 = Math.cos(var179);
        double var190 = (var183 * var311);
        double var409 = var170;
        double var315 = (var186 + var409);
        double var411 = var183;
        double var483 = Math.sin(var179);
        double var413 = (var411 * var483);
        double[] var192 = GeometryPrimitives.computeVector(var190, var315, var413);
        double var415 = 0.03;
        double var319 = (var186 + var415);
        double var417 = 0.0;
        double[] var195 = GeometryPrimitives.computeVector(0.0, var319, var417);
        double var442 = 0.8;
        double var492 = (0.2 * var94);
        double var444 = (var442 + var492);
        double var363 = (0.62 * var444);
        int var446 = var150;
        int var485 = var156;
        double[][] var197 = computeMatrix(var192, var195, 17, var363, var446, var485);
        double[] var199 = KillEffectMathUtils.computeVector(var105, var197, var36);
        double var419 = 0.0;
        double[] var202 = GeometryPrimitives.computeVector(1.0, 0.0, var419);
        double var421 = 1.0;
        double[] var205 = GeometryPrimitives.computeVector(0.0, 0.0, var421);
        double var447 = var79;
        double var494 = var75;
        this.drawGeometryPart(arg0, var98, var112, var447, var494);
        double var449 = var90;
        double var496 = var112;
        this.drawGeometryPart(arg0, var105, var36, var449, var496, var120);
        double var451 = var79;
        double var498 = var124;
        this.drawGeometryPart(arg0, var98, var112, var451, var498, var167);
        double var453 = var79;
        double var500 = var133;
        this.drawGeometryPart(arg0, var98, var112, var453, var500, var120, var124, var156, var164);
        double var455 = var79;
        int var502 = var167;
        this.drawGeometryPart(arg0, var98, var112, var455, var502);
        double var423 = 0.06;
        double var335 = (var79 - var423);
        double var425 = 0.12;
        double var337 = (var335 / var425);
        double var339 = GeometryPrimitives.computeDoubleMetric(var337);
        double var213 = (0.28 * var339);
        double var427 = var79;
        double var486 = var116;
        double var429 = (var427 - var486);
        double var488 = 0.5;
        double var431 = (var429 / var488);
        double var433 = GeometryPrimitives.computeDoubleMetric(var431);
        double var343 = (1.0 - var433);
        double var215 = (var213 * var343);
        double var217 = (var215 * var124);
        double var435 = var94;
        double var349 = (0.2 * var435);
        double var221 = (0.85 + var349);
        double var437 = 1.5;
        double var223 = KillEffectMathUtils.computeDoubleMetric(var221, 0.9, var437);
        double var457 = var223;
        double var377 = (0.05 * var457);
        double var459 = 0.16;
        double var503 = var223;
        double var461 = (var459 * var503);
        int var505 = var161;
        drawGeometryPart(arg0, var197, var199, var377, var461, var505, var217, true, var202, var205);
        int var228 = Double.compare(var120, 0.02);
        if (var228 > 0) {
            double var463 = var223;
            double var381 = (0.2 * var463);
            double var465 = 0.62;
            double var506 = var223;
            double var467 = (var465 * var506);
            int var508 = var158;
            double var519 = (0.15 * var120);
            drawGeometryPart(arg0, var197, var199, var381, var467, var508, var519, true, var202, var205);
            double var469 = var223;
            double var385 = (0.07 * var469);
            double var471 = 0.24;
            double var509 = var223;
            double var473 = (var471 * var509);
            int var511 = var161;
            double var523 = (0.5 * var120);
            drawGeometryPart(arg0, var197, var199, var385, var473, var511, var523, true, var202, var205);
            double var475 = var223;
            double var389 = (0.022 * var475);
            double var477 = 0.08;
            double var479 = (var477 * var223);
            double var527 = (1.0 * var120);
            drawGeometryPart(arg0, var197, var199, var389, var479, var167, var527, true, var202, var205);
            double var439 = var112;
            double var490 = var120;
            this.drawGeometryPart(arg0, var98, var197, var439, var490, var156, var161, var167, var105, var36);
        }
        double var481 = var79;
        this.drawGeometryPart(arg0, var98, var112, var481, var124, var133, var167, var161, var105, var36);
    }

    private void drawGeometryPart(CosmeticDrawBuilder arg0, IGroundHeightSampler arg1, double[][] arg2, double arg3, double arg4, int arg5, int arg6, int arg7, double[] arg8, double arg9) {
        int var30 = 0;
        double var39 = 0.0;
        int var51 = 0;
        var30 = 0;
        while (true) {
            if (var30 >= 5) break;
            int var56 = (this.killEffectRenderSpec.seed() * 43);
            int var129 = (var30 * 19);
            int var57 = (var56 + var129);
            int var131 = (var30 * 31);
            if (Double.compare(GeometryPrimitives.computeDoubleMetric(((arg5 + var131) + 5)), 0.8) > 0) {
            } else {
                int var156 = var30;
                int var134 = (2 * var156);
                int var65 = (3 + var134);
                double[] var67 = arg2[var65];
                double var72 = (GeometryPrimitives.computeDoubleMetric((var57 + 1)) * 6.283185307179586);
                double var242 = GeometryPrimitives.computeDoubleMetric((var57 + 2));
                double var161 = (0.55 * var242);
                double var76 = (0.45 + var161);
                double[] var79 = new double[3];
                double var206 = Math.cos(var72);
                double var278 = Math.sin(var76);
                double var208 = (var206 * var278);
                var79[0] = var208;
                double var214 = -(Math.cos(var76));
                var79[1] = var214;
                double var218 = Math.sin(var72);
                double var282 = Math.sin(var76);
                double var220 = (var218 * var282);
                var79[2] = var220;
                double var246 = GeometryPrimitives.computeDoubleMetric((var57 + 3));
                double var168 = (3.5 * var246);
                var39 = ((2.5 + var168) * arg3);
                double var138 = var67[0];
                double var223 = var67[2];
                double var89 = (arg1.computeDoubleMetric(var138, var223) + 0.05);
                double var92 = var67[1];
                double var176 = var79[1];
                double var249 = var39;
                double var178 = (var176 * var249);
                double var94 = (var92 + var178);
                if (Double.compare(var94, var89) < 0) {
                    double var99 = var39;
                    double var254 = (var67[1] - var89);
                    double var306 = (var67[1] - var94);
                    double var256 = (var254 / var306);
                    double var184 = Math.max(0.05, var256);
                    var39 = (var99 * var184);
                }
                double var104 = var67[0];
                double var187 = var79[0];
                double var258 = var39;
                double var189 = (var187 * var258);
                double var106 = (var104 + var189);
                double var192 = var67[1];
                double var261 = var79[1];
                double var308 = var39;
                double var263 = (var261 * var308);
                double var194 = (var192 + var263);
                double var266 = var67[2];
                double var311 = var79[2];
                double var333 = var39;
                double var313 = (var311 * var333);
                double var268 = (var266 + var313);
                double[] var108 = GeometryPrimitives.computeVector(var106, var194, var268);
                double var288 = var39;
                double var232 = ((0.3 * var288) / 3.0);
                int var295 = (this.killEffectRenderSpec.seed() * 5);
                int var317 = (var30 * 101);
                int var296 = (var295 + var317);
                int var330 = (var30 * 7919);
                int var319 = (arg5 + var330);
                double[][] var110 = computeMatrix(var67, var108, 6, var232, var296, var319);
                double[] var145 = GeometryPrimitives.computeVector(0.0, 1.0, 0.0);
                double[] var113 = GeometryPrimitives.computeVector(GeometryPrimitives.computeVector1(var79, var145));
                double[] var116 = GeometryPrimitives.computeVector(GeometryPrimitives.computeVector1(var79, var113));
                double[] var118 = KillEffectMathUtils.computeVector(arg8, var110, arg9);
                double[] var121 = new double[var110.length];
                var51 = 0;
                while (true) {
                    int var123 = var51;
                    int var149 = var110.length;
                    if (var123 >= var149) break;
                    int var150 = var51;
                    int var236 = var51;
                    double var200 = var118[var236];
                    double var321 = ((double) var51);
                    double var340 = (((double) var110.length) - 1.0);
                    double var323 = (var321 / var340);
                    double var274 = Math.pow((1.0 - var323), 0.8);
                    double var202 = (var200 * var274);
                    var121[var150] = var202;
                    var51 = (var51 + 1);
                }
                double var344 = (0.55 * arg4);
                drawGeometryPart(arg0, var110, var121, 0.12, 0.02, arg6, var344, false, var113, var116);
                double var348 = (1.0 * arg4);
                drawGeometryPart(arg0, var110, var121, 0.04, 0.008, arg7, var348, false, var113, var116);
            }
            var30 = (var30 + 1);
        }
    }

    private void drawGeometryPart(CosmeticDrawBuilder arg0, IGroundHeightSampler arg1, double arg2, double arg3, double arg4) {
        double var39 = GeometryPrimitives.computeDoubleMetric((arg3 / 0.06));
        double var25 = (0.38 * var39);
        double var55 = GeometryPrimitives.computeDoubleMetric(((arg4 - 0.3) / 0.7));
        double var43 = (1.0 - var55);
        double var27 = (var25 * var43);
        if (Double.compare(var27, 0.01) < 0) {
            return;
        }
        int var34 = ColorMathUtils.computeColorOrIndex8(arg0.counterValue, 0.9);
        double var65 = (0.5 * arg2);
        double var59 = (0.75 + var65);
        IGradientColorSampler var68 = (p0, p1) -> LightningStrikeKillRenderer.lambda$a(var34, var27, p0, p1);
        KillEffectMathUtils.drawGeometryPart(arg0, arg1, 0.03, var59, 20, var68);
    }

    private void drawGeometryPart(CosmeticDrawBuilder arg0, double[] arg1, double arg2, double arg3, double arg4, double arg5) {
        double var33 = (0.32 * arg5);
        double var56 = (0.5 * arg3);
        double var46 = KillEffectMathUtils.computeDoubleMetric(arg1, 0.0, var56, 0.0, arg2);
        double var35 = (var33 * var46);
        if (Double.compare(var35, 0.01) < 0) {
            return;
        }
        int var41 = arg0.counterValue;
        int var43 = arg0.counterValue1;
        double var54 = ((1.4 * arg3) + 0.5);
        double var64 = (0.45 * arg4);
        double var72 = (0.3 * arg4);
        IGradientColorSampler var77 = (p0, p1) -> LightningStrikeKillRenderer.lambda$d(var43, var41, var35, p0, p1);
        GeometryPrimitives.drawGeometryPart(arg0, 0.03, var54, var64, var72, 12, var77, false, false);
    }

    private void drawGeometryPart(CosmeticDrawBuilder arg0, IGroundHeightSampler arg1, double arg2, double arg3, double arg4, int arg5) {
        double var37 = KillEffectMathUtils.computeDoubleMetric((arg3 / 0.55), 0.0, 1.0);
        double var69 = Math.pow((1.0 - var37), 1.2);
        double var41 = (0.75 * var69);
        double var75 = GeometryPrimitives.computeDoubleMetric((arg3 / 0.01));
        double var45 = ((var41 * var75) * arg4);
        if (Double.compare(var45, 0.01) < 0) {
            return;
        }
        double var85 = ((2.6 * arg2) - 0.3);
        double var121 = Math.pow((1.0 - var37), 2.2);
        double var109 = (1.0 - var121);
        double var87 = (var85 * var109);
        double var52 = (0.3 + var87);
        double var56 = (0.09 * arg2);
        double var113 = (1.0 - var37);
        double var93 = (0.05 * var113);
        double var58 = (var56 + var93);
        int var61 = arg0.counterValue;
        double var127 = (var52 - var58);
        double var117 = Math.max(0.0, var127);
        double var139 = (var58 * 0.4);
        double var131 = (var52 + var139);
        IGradientColorSampler var142 = (p0, p1) -> LightningStrikeKillRenderer.lambda$c(var61, arg5, var45, p0, p1);
        KillEffectMathUtils.drawGeometryPart(arg0, arg1, 0.04, var117, var131, 24, var142);
    }

    private void drawGeometryPart(CosmeticDrawBuilder arg0, IGroundHeightSampler arg1, double arg2, double arg3, double arg4, double arg5, double arg6, int arg7, int arg8) {
        int var37 = 0;
        int var50 = 0;
        double var112 = 0.0;
        double var277 = GeometryPrimitives.computeDoubleMetric((arg3 / 0.9));
        double var253 = (1.0 - var277);
        double var211 = (0.4 * var253);
        double var136 = Math.max(arg5, var211);
        double var55 = (0.85 * var136);
        double var142 = GeometryPrimitives.computeDoubleMetric((arg3 / 0.015));
        double var59 = ((var55 * var142) * arg6);
        if (Double.compare(var59, 0.01) < 0) {
            return;
        }
        double var257 = (arg3 / 0.1);
        double var217 = Math.min(1.0, var257);
        double var152 = Math.pow((1.0 - var217), 2.0);
        double var66 = (1.0 - var152);
        int var69 = arg0.counterValue;
        var37 = 0;
        while (true) {
            if (var37 >= 9) break;
            int var75 = (this.killEffectRenderSpec.seed() * 29);
            int var130 = (var37 * 13);
            int var76 = (var75 + var130);
            double var222 = ((double) var37);
            double var285 = (GeometryPrimitives.computeDoubleMetric((var76 + 1)) - 0.5);
            double var261 = (0.4 * var285);
            double var224 = (var222 + var261);
            double var159 = ((6.283185307179586 * var224) / 9.0);
            double var79 = (arg4 + var159);
            double var230 = GeometryPrimitives.computeDoubleMetric((var76 + 2));
            double var163 = (1.7 * var230);
            double var87 = (((1.3 + var163) * arg2) * var66);
            double var91 = Math.cos(var79);
            double var95 = Math.sin(var79);
            double[] var98 = new double[4];
            double[] var100 = new double[4];
            double[] var102 = new double[4];
            var50 = 0;
            while (true) {
                if (var50 < 4) {
                    double var170 = ((double) var50);
                    double var109 = ((var87 * var170) / 3.0);
                    if (var50 == 0) {
                        var112 = 0.0;
                    }
                } else {
                    IGradientColorSampler var274 = (p0, p1) -> LightningStrikeKillRenderer.lambda$b(arg8, var69, var59, p0, p1);
                    KillEffectMathUtils.drawGeometryPart(arg0, arg1, 0.05, var98, var100, var102, var274);
                    var37 = (var37 + 1);
                }
            }
        }
        int var193 = (arg7 + 200);
        int var209 = (var37 * 7);
        int var194 = (var193 + var209);
        int var210 = var50;
        double var196 = GeometryPrimitives.computeDoubleMetric((var194 + var210));
        var112 = (((((2.0 * var196) - 1.0) * 0.28) * var87) / 1.5);
        double var53 = var112;
        int var132 = var50;
        double var176 = (var109 * var91);
        double var236 = (var53 * var95);
        double var178 = (var176 - var236);
        var98[var132] = var178;
        int var133 = var50;
        double var182 = (var109 * var95);
        double var242 = (var53 * var91);
        double var184 = (var182 + var242);
        var100[var133] = var184;
        int var134 = var50;
        double var188 = (0.085 * arg2);
        double var288 = ((double) var50);
        double var271 = ((0.7 * var288) / 3.0);
        double var248 = (1.0 - var271);
        double var190 = (var188 * var248);
        var102[var134] = var190;
        var50 = (var50 + 1);
    }

    private void drawGeometryPart(CosmeticDrawBuilder arg0, IGroundHeightSampler arg1, double arg2, double arg3, int arg4) {
        if (Double.compare(arg3, 0.22) >= 0) {
            return;
        }
        double var49 = GeometryPrimitives.computeDoubleMetric((arg3 / 0.22));
        double var37 = (1.0 - var49);
        double var28 = (0.85 * var37);
        double var43 = GeometryPrimitives.computeDoubleMetric((arg3 / 0.01));
        double var30 = (var28 * var43);
        int var33 = arg0.counterValue;
        double var59 = (0.6 * arg2);
        double var55 = (0.9 + var59);
        IGradientColorSampler var62 = (p0, p1) -> LightningStrikeKillRenderer.lambda$a(arg4, var33, var30, p0, p1);
        KillEffectMathUtils.drawGeometryPart(arg0, arg1, 0.06, var55, 16, var62);
    }

    private void drawGeometryPart(CosmeticDrawBuilder arg0, IGroundHeightSampler arg1, double arg2, double arg3, double arg4, double arg5, int arg6, int arg7, double[] arg8, double arg9) {
        int var31 = 0;
        var31 = 0;
        while (true) {
            if (var31 < 10) {
                int var58 = (this.killEffectRenderSpec.seed() * 53);
                int var131 = (var31 * 13);
                int var59 = (var58 + var131);
                double var235 = GeometryPrimitives.computeDoubleMetric((var59 + 1));
                double var147 = (0.3 * var235);
                double var62 = (0.06 + var147);
                double var239 = GeometryPrimitives.computeDoubleMetric((var59 + 2));
                double var151 = (0.7 * var239);
                double var66 = (0.5 + var151);
                double var72 = ((arg3 - var62) / var66);
                if (Double.compare(var72, 0.0) > 0) {
                    if (Double.compare(var72, 1.0) >= 0) {
                    }
                }
            } else {
                return;
                double var243 = GeometryPrimitives.computeDoubleMetric((var59 + 3));
                double var163 = (6.283185307179586 * var243);
                double var82 = (arg5 + var163);
                double var247 = GeometryPrimitives.computeDoubleMetric((var59 + 4));
                double var167 = (0.9 * var247);
                double var88 = ((0.2 + var167) * arg2);
                double var251 = (0.5 * var72);
                double var173 = (1.0 + var251);
                double var90 = (var88 * var173);
                double var177 = Math.cos(var82);
                double var94 = (var90 * var177);
                double var181 = Math.sin(var82);
                double var98 = (var90 * var181);
                double var103 = (arg1.computeDoubleMetric(var94, var98) + 0.2);
                double var333 = GeometryPrimitives.computeDoubleMetric((var59 + 5));
                double var300 = (2.0 * var333);
                double var255 = (1.0 + var300);
                double var187 = (var72 * var255);
                double var105 = (var103 + var187);
                double var195 = Math.pow(Math.sin((3.141592653589793 * var72)), 0.8);
                double var111 = ((0.9 * var195) * arg4);
                double var200 = KillEffectMathUtils.computeDoubleMetric(arg8, var94, var105, var98, arg9);
                double var113 = (var111 * var200);
                if (Double.compare(var113, 0.04) < 0) {
                } else {
                    double var120 = (0.035 * arg2);
                    double var263 = (0.5 * var72);
                    double var208 = (1.0 - var263);
                    double var122 = (var120 * var208);
                    int var126 = ColorMathUtils.computeColorOrIndex5(ColorMathUtils.computeColorOrIndex(arg6, arg7, var72), var113);
                    double[] var141 = GeometryPrimitives.computeVector((var94 - var122), var105, var98);
                    double var306 = (var122 * 1.6);
                    double var267 = (var105 - var306);
                    double[] var214 = GeometryPrimitives.computeVector(var94, var267, var98);
                    double[] var228 = GeometryPrimitives.computeVector((var94 + var122), var105, var98);
                    double var339 = (var122 * 1.6);
                    double var312 = (var105 + var339);
                    double[] var271 = GeometryPrimitives.computeVector(var94, var312, var98);
                    arg0.applyStep(var141, var214, var228, var271, var126);
                    double var293 = (var98 - var122);
                    double[] var144 = GeometryPrimitives.computeVector(var94, var105, var293);
                    double var316 = (var122 * 1.6);
                    double var274 = (var105 - var316);
                    double[] var217 = GeometryPrimitives.computeVector(var94, var274, var98);
                    double var330 = (var98 + var122);
                    double[] var233 = GeometryPrimitives.computeVector(var94, var105, var330);
                    double var347 = (var122 * 1.6);
                    double var322 = (var105 + var347);
                    double[] var278 = GeometryPrimitives.computeVector(var94, var322, var98);
                    arg0.applyStep(var144, var217, var233, var278, var126);
                }
                var31 = (var31 + 1);
            }
        }
    }

}
