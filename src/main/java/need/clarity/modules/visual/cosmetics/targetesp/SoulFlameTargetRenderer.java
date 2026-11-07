package need.clarity.modules.visual.cosmetics.targetesp;

import java.util.function.DoubleUnaryOperator;
import need.clarity.modules.visual.cosmetics.ColorMathUtils;
import need.clarity.modules.visual.cosmetics.CosmeticDrawBuilder;
import need.clarity.modules.visual.cosmetics.GeometryPrimitives;
import need.clarity.modules.visual.cosmetics.ICosmeticEffectRenderer;
import need.clarity.modules.visual.cosmetics.IGradientColorSampler;

public final class SoulFlameTargetRenderer implements ICosmeticEffectRenderer {
    public static final int CONST_INT = 11860223;
    public static final int CONST_INT1 = 2788607;
    public static final int CONST_INT2 = 420;
    private static final int CONST_INT3 = 8;
    private static final int CONST_INT4 = 8;
    private static final int CONST_INT5 = 4;
    private static final int CONST_INT6 = 8;
    private static final int CONST_INT7 = 18;
    private static final int CONST_INT8 = 20;
    private static final int CONST_INT9 = 24;
    private static final double CONST_DBL = 0.02;
    private final TargetEspRenderSpec targetEspRenderSpec;

    public SoulFlameTargetRenderer() {
        TargetEspRenderSpec var2 = TargetEspRenderSpec.evaluate();
        this(var2);
    }

    public SoulFlameTargetRenderer(TargetEspRenderSpec arg0) {
        this.targetEspRenderSpec = arg0;
    }

    public void renderEffect(CosmeticDrawBuilder arg0) {
        double var288 = 0.0;
        double var196 = 0.0;
        int var70 = 0;
        double var198 = 0.0;
        int var466 = 0;
        int var467 = 0;
        if (arg0.enabledState) {
            var198 = arg0.doubleValue;
        } else {
            var198 = 0.0;
        }
        double var41 = var198;
        double var83 = this.targetEspRenderSpec.age();
        double var87 = this.targetEspRenderSpec.computeDoubleMetric1();
        if (Double.compare(arg0.computeDoubleMetric(), 1.0E-6) > 0) {
            var196 = arg0.computeDoubleMetric();
        } else {
            var196 = 1.0;
        }
        double var47 = var196;
        double var210 = this.targetEspRenderSpec.phase();
        double var96 = (6.283185307179586 * var210);
        TargetEspRenderSpec var99 = this.targetEspRenderSpec;
        double var100 = var99.computeDoubleMetric();
        TargetEspRenderSpec var103 = this.targetEspRenderSpec;
        double var104 = var103.height();
        double var108 = (-0.5 * var104);
        double var110 = (var108 / var47);
        double var114 = Math.max(var104, 0.9);
        TargetEspRenderSpec var117 = this.targetEspRenderSpec;
        double var118 = var117.width();
        double var220 = (0.08 * var87);
        double var120 = (var118 + var220);
        double var124 = GeometryPrimitives.computeDoubleMetric(var83);
        double var224 = (0.85 * var124);
        double var128 = (0.15 + var224);
        double var228 = (0.6 * var83);
        double var132 = (0.4 + var228);
        boolean var231 = arg0.enabledState;
        if (var231) {
            double var367 = (var41 * 2.2);
            double var369 = Math.sin(var367);
            double var330 = (0.08 * var369);
            var288 = (0.92 + var330);
        } else {
            var288 = 1.0;
        }
        double var134 = (var132 * var288);
        double[] var137 = arg0.computeVector1();
        int var140 = ColorMathUtils.computeColorOrIndex7(arg0.counterValue, 0.35);
        int var142 = arg0.counterValue1;
        this.drawGeometryPart(arg0, var110, var120, var87, var134, var83, var140, var142, var41);
        var70 = 0;
        while (true) {
            if (var70 >= 8) break;
            int var234 = var70;
            double var235 = ((double) var234);
            double var343 = (GeometryPrimitives.computeDoubleMetric(((var70 * 13) + 1)) - 0.5);
            double var304 = (0.3 * var343);
            double var237 = (var235 + var304);
            double var150 = ((6.283185307179586 * var237) / 8.0);
            double var243 = (var96 * 0.1);
            double var152 = (var150 + var243);
            double var348 = GeometryPrimitives.computeDoubleMetric(((var70 * 13) + 2));
            double var310 = (0.12 * var348);
            double var247 = (0.94 + var310);
            double var156 = (var120 * var247);
            double var353 = GeometryPrimitives.computeDoubleMetric(((var70 * 13) + 3));
            double var314 = (0.34 * var353);
            double var251 = (0.74 + var314);
            double var162 = ((var114 * var251) * var128);
            int var355 = var70;
            int var356 = (var355 * 13);
            int var357 = (var356 + 4);
            double var358 = GeometryPrimitives.computeDoubleMetric(var357);
            double var318 = (0.3 * var358);
            double var257 = (0.85 + var318);
            double var166 = (0.26 * var257);
            double var261 = Math.sqrt(var87);
            double var168 = (var166 * var261);
            double var396 = Math.cos(var152);
            double var380 = (var156 * var396);
            double var414 = Math.sin(var152);
            double var400 = (var156 * var414);
            int var28 = var70;
            this.drawGeometryPart(arg0, var137, var47, var110, var380, var400, var162, var168, var28, var41, var100, var134, var140, var142, true);
            var70 = (var70 + 1);
        }
        var466 = 0;
        while (true) {
            if (var466 >= 8) break;
            double var269 = (((double) var466) + 0.5);
            double var181 = ((6.283185307179586 * var269) / 8.0);
            double var275 = (var96 * 0.1);
            double var183 = (var181 + var275);
            double var187 = (var120 * 1.08);
            int var360 = var466;
            int var361 = (var360 * 17);
            int var362 = (var361 + 5);
            double var363 = GeometryPrimitives.computeDoubleMetric(var362);
            double var326 = (0.18 * var363);
            double var281 = (0.18 + var326);
            double var193 = ((var114 * var281) * var128);
            double var408 = Math.cos(var183);
            double var390 = (var187 * var408);
            double var424 = Math.sin(var183);
            double var412 = (var187 * var424);
            double var443 = Math.sqrt(var87);
            double var435 = (0.16 * var443);
            int var446 = (var466 + 40);
            this.drawGeometryPart(arg0, var137, var47, var110, var390, var412, var193, var435, var446, var41, var100, var134, var140, var142, false);
            var466 = (var466 + 1);
        }
        var467 = 0;
        while (true) {
            if (var467 >= 4) break;
            int var432 = var467;
            this.drawGeometryPart(arg0, var137, var47, var110, var120, var114, var87, var432, var41, var96, var100, var134, var140, var142);
            var467 = (var467 + 1);
        }
        this.drawGeometryPart(arg0, var137, var47, var110, var120, var114, var87, var41, var96, var134, var140, var142);
    }

    private void drawGeometryPart(CosmeticDrawBuilder arg0, double arg1, double arg2, double arg3, double arg4, double arg5, int arg6, int arg7, double arg8) {
        double var103 = 0.0;
        double var105 = 0.0;
        double var55 = (arg1 + 0.02);
        if (arg0.enabledState) {
            double var171 = Math.sin((arg8 * 2.6));
            double var117 = (0.15 * var171);
            var105 = (0.85 + var117);
        } else {
            var105 = 1.0;
        }
        double var39 = var105;
        double var155 = (arg2 * 1.1);
        IGradientColorSampler var205 = (p0, p1) -> SoulFlameTargetRenderer.lambda$a(arg6, arg7, var39, arg4, p0, p1);
        GeometryPrimitives.drawGeometryPart(arg0, var55, var155, 20, var205);
        if (arg0.enabledState) {
            var103 = TargetEspMathUtils.computeDoubleMetric((arg8 / 1.9));
        } else {
            var103 = 0.35;
        }
        double var41 = var103;
        double var175 = (0.55 * var41);
        double var121 = (0.95 + var175);
        double var66 = (arg2 * var121);
        double var70 = (0.05 * arg3);
        double var179 = (0.5 * var41);
        double var127 = (1.0 - var179);
        double var72 = (var70 * var127);
        double var133 = Math.pow((1.0 - var41), 1.5);
        double var76 = (0.55 * var133);
        double var139 = GeometryPrimitives.computeDoubleMetric((var41 * 8.0));
        double var80 = ((var76 * var139) * arg4);
        double var109 = (var55 + 0.01);
        double var161 = (var66 - var72);
        double var196 = (var66 + var72);
        IGradientColorSampler var219 = (p0, p1) -> SoulFlameTargetRenderer.lambda$b(arg6, var80, p0, p1);
        GeometryPrimitives.drawGeometryPart(arg0, var109, var161, var196, 24, var219);
        double var85 = (1.0 - arg5);
        if (Double.compare(var85, 0.01) > 0) {
            double var189 = (1.6 * arg5);
            double var149 = (1.0 + var189);
            double var92 = (arg2 * var149);
            double var96 = (0.09 * arg3);
            double var113 = (var55 + 0.02);
            double var167 = (var92 - var96);
            double var202 = (var92 + var96);
            IGradientColorSampler var221 = (p0, p1) -> SoulFlameTargetRenderer.lambda$a(arg6, var85, p0, p1);
            GeometryPrimitives.drawGeometryPart(arg0, var113, var167, var202, 24, var221);
        }
    }

    private void drawGeometryPart(CosmeticDrawBuilder arg0, double[] arg1, double arg2, double arg3, double arg4, double arg5, double arg6, double arg7, int arg8, double arg9, double arg10, double arg11, int arg12, int arg13, boolean arg14) {
        int var65 = 0;
        double var193 = 0.0;
        int var70 = 0;
        double var73 = 0.0;
        double var75 = 0.0;
        int var77 = 0;
        if (arg14) {
            var77 = 4;
        } else {
            var77 = 3;
        }
        int var46 = var77;
        double var80 = (arg4 * arg4);
        double var211 = (arg5 * arg5);
        double var84 = Math.sqrt((var80 + var211));
        double var88 = (arg4 / var84);
        double var92 = (arg5 / var84);
        double var96 = -(var92);
        if (arg0.enabledState) {
            double var321 = (arg9 * 7.3);
            double var423 = (((double) arg8) * 2.1);
            double var325 = Math.sin((var321 + var423));
            double var219 = (0.1 * var325);
            double var104 = (0.84 + var219);
            double var329 = (arg9 * 12.1);
            double var430 = (((double) arg8) * 0.9);
            double var333 = Math.sin((var329 + var430));
            double var223 = (0.06 * var333);
            var193 = (var104 + var223);
        } else {
            var193 = 0.9;
        }
        double var57 = var193;
        double var108 = (arg6 * var57);
        double var403 = (var108 * 0.5);
        double var304 = (arg3 + var403);
        double var111 = TargetEspMathUtils.computeDoubleMetric(arg1, arg4, var304, arg5, arg2);
        if (Double.compare(var111, 0.03) > 0) {
            if (Double.compare(var108, 0.001) < 0) {
            }
        }
        return;
        double[][] var121 = new double[(var46 + 1)][];
        double[] var124 = new double[(var46 + 1)];
        var65 = 0;
        while (true) {
            if (var65 > var46) break;
            double var155 = ((double) var65);
            double var251 = ((double) var46);
            double var157 = (var155 / var251);
            double var163 = ((arg10 * 0.22) * var108);
            double var259 = Math.pow(var157, 1.8);
            double var165 = (var163 * var259);
            double var169 = (-0.22 * var108);
            double var265 = Math.pow(var157, 1.4);
            double var171 = (var169 * var265);
            var73 = 0.0;
            var75 = 0.0;
            if (arg0.enabledState) {
                double var181 = (0.06 * var157);
                double var271 = (arg9 * 3.0);
                double var360 = (((double) arg8) * 1.7);
                double var273 = (var271 + var360);
                double var364 = (var157 * 2.4);
                double var277 = Math.sin((var273 - var364));
                var73 = (var181 * var277);
                double var187 = (0.05 * var157);
                double var283 = (arg9 * 2.4);
                double var371 = (((double) arg8) * 2.3);
                double var285 = (var283 + var371);
                double var375 = (var157 * 1.9);
                double var287 = (var285 - var375);
                double var383 = Math.sin(((var157 * 3.141592653589793) * 1.4));
                double var291 = Math.sin((var287 + var383));
                var75 = (var187 * var291);
            }
            int var207 = var65;
            double var478 = var73;
            double var448 = (var165 + var478);
            double var387 = (var96 * var448);
            double var295 = (arg4 + var387);
            double var480 = var75;
            double var452 = (var171 + var480);
            double var391 = (var88 * var452);
            double var297 = (var295 + var391);
            double var395 = (arg3 + 0.02);
            double var458 = (var108 * var157);
            double var397 = (var395 + var458);
            double var44 = var73;
            double var498 = (var165 + var44);
            double var486 = (var88 * var498);
            double var462 = (arg5 + var486);
            double var504 = var75;
            double var502 = (var171 + var504);
            double var490 = (var92 * var502);
            double var464 = (var462 + var490);
            double[] var299 = GeometryPrimitives.computeVector(var297, var397, var464);
            var121[var207] = var299;
            int var208 = var65;
            double var401 = computeDoubleMetric(var157);
            double var302 = (arg7 * var401);
            var124[var208] = var302;
            var65 = (var65 + 1);
        }
        double[] var129 = GeometryPrimitives.computeVector(var96, 0.0, var88);
        double[] var132 = GeometryPrimitives.computeVector(var88, 0.0, var92);
        this.drawGeometryPart(arg0, var121, var124, var129, var111, arg11, arg12, arg13);
        if (arg14) {
            this.drawGeometryPart(arg0, var121, var124, var132, var111, arg11, arg12, arg13);
            double[][] var139 = new double[(3 + 1)][];
            double[] var142 = new double[(3 + 1)];
            var70 = 0;
            while (true) {
                if (var70 > 3) break;
                double var148 = ((double) var70);
                double var240 = ((double) 3);
                double var150 = (var148 / var240);
                int var205 = var70;
                double var312 = (var150 * 0.7);
                double var418 = ((double) var46);
                int var318 = ((int) Math.round((var312 * var418)));
                double[] var243 = var121[var318];
                var139[var205] = var243;
                int var206 = var70;
                double var246 = (arg7 * 0.4);
                double var349 = computeDoubleMetric((var150 * 0.75));
                double var248 = (var246 * var349);
                var142[var206] = var248;
                var70 = (var70 + 1);
            }
            this.drawGeometryPart(arg0, var139, var142, var129, var111, arg11, arg12);
            this.drawGeometryPart(arg0, var139, var142, var132, var111, arg11, arg12);
        }
    }

    private static double computeDoubleMetric(double arg0) {
        if (Double.compare(arg0, 0.22) < 0) {
            double var39 = Math.sin((((arg0 / 0.22) * 3.141592653589793) / 2.0));
            double var23 = (0.45 * var39);
            return (0.55 + var23);
        }
        double var29 = ((1.0 - arg0) / 0.78);
        return Math.pow(Math.max(0.0, var29), 1.3);
    }

    private void drawGeometryPart(CosmeticDrawBuilder arg0, double[][] arg1, double[] arg2, double[] arg3, double arg4, double arg5, int arg6, int arg7) {
        int var24 = 0;
        int var33 = arg1.length;
        double[][] var35 = new double[var33][];
        double[][] var37 = new double[var33][];
        applyStep(arg1, arg2, arg3, var35, var37);
        var24 = 0;
        while (true) {
            int var40 = var24;
            int var63 = (var33 - 1);
            if (var40 >= var63) break;
            double var42 = ((double) var24);
            double var79 = (((double) var33) - 1.0);
            double var44 = (var42 / var79);
            double var48 = ((double) (var24 + 1));
            double var84 = (((double) var33) - 1.0);
            double var50 = (var48 / var84);
            int var53 = computeColorOrIndex(arg6, arg7, var44, arg4, arg5);
            int var55 = computeColorOrIndex(arg6, arg7, var50, arg4, arg5);
            int var57 = ColorMathUtils.computeColorOrIndex(var53, 0.22);
            int var59 = ColorMathUtils.computeColorOrIndex(var55, 0.22);
            int var90 = var24;
            double[] var72 = var35[var90];
            int var96 = var24;
            double[] var92 = arg1[var96];
            int var111 = (var24 + 1);
            double[] var98 = arg1[var111];
            int var119 = (var24 + 1);
            double[] var113 = var35[var119];
            arg0.applyStep(var72, var92, var98, var113, var57, var53, var55, var59);
            int var93 = var24;
            double[] var74 = arg1[var93];
            int var99 = var24;
            double[] var95 = var37[var99];
            int var115 = (var24 + 1);
            double[] var101 = var37[var115];
            int var123 = (var24 + 1);
            double[] var117 = arg1[var123];
            arg0.applyStep(var74, var95, var101, var117, var53, var57, var59, var55);
            var24 = (var24 + 1);
        }
    }

    private void drawGeometryPart(CosmeticDrawBuilder arg0, double[][] arg1, double[] arg2, double[] arg3, double arg4, double arg5, int arg6) {
        int var26 = 0;
        int var33 = arg1.length;
        double[][] var35 = new double[var33][];
        double[][] var37 = new double[var33][];
        applyStep(arg1, arg2, arg3, var35, var37);
        int var40 = ColorMathUtils.computeColorOrIndex7(arg6, 0.65);
        int var42 = ColorMathUtils.computeColorOrIndex7(arg6, 0.15);
        var26 = 0;
        while (true) {
            int var44 = var26;
            int var68 = (var33 - 1);
            if (var44 >= var68) break;
            double var46 = ((double) var26);
            double var94 = (((double) var33) - 1.0);
            double var48 = (var46 / var94);
            double var52 = ((double) (var26 + 1));
            double var99 = (((double) var33) - 1.0);
            double var54 = (var52 / var99);
            int var57 = ColorMathUtils.computeColorOrIndex(var40, var42, var48);
            double var135 = Math.pow(var48, 1.4);
            double var110 = (1.0 - var135);
            double var77 = (((0.92 * var110) * arg4) * arg5);
            int var58 = ColorMathUtils.computeColorOrIndex5(var57, var77);
            int var60 = ColorMathUtils.computeColorOrIndex(var40, var42, var54);
            double var139 = Math.pow(var54, 1.4);
            double var118 = (1.0 - var139);
            double var86 = (((0.92 * var118) * arg4) * arg5);
            int var61 = ColorMathUtils.computeColorOrIndex5(var60, var86);
            int var105 = var26;
            double[] var89 = var35[var105];
            int var124 = var26;
            double[] var107 = var37[var124];
            int var132 = (var26 + 1);
            double[] var126 = var37[var132];
            int var143 = (var26 + 1);
            double[] var134 = var35[var143];
            arg0.applyStep(var89, var107, var126, var134, var58, var58, var61, var61);
            var26 = (var26 + 1);
        }
    }

    private static int computeColorOrIndex(int arg0, int arg1, double arg2, double arg3, double arg4) {
        double var25 = GeometryPrimitives.computeDoubleMetric(((arg2 - 0.06) / 0.62));
        int var28 = ColorMathUtils.computeColorOrIndex(arg0, arg1, var25);
        double var48 = Math.pow(arg2, 2.6);
        double var42 = (1.0 - var48);
        double var36 = (((0.8 * var42) * arg3) * arg4);
        return ColorMathUtils.computeColorOrIndex5(var28, var36);
    }

    private static void applyStep(double[][] arg0, double[] arg1, double[] arg2, double[][] arg3, double[][] arg4) {
        int var19 = 0;
        var19 = 0;
        while (true) {
            int var26 = var19;
            int var43 = arg0.length;
            if (var26 >= var43) break;
            int var44 = var19;
            double[] var28 = GeometryPrimitives.computeVector(arg0, var44);
            double var30 = GeometryPrimitives.computeDoubleMetric(arg2, var28);
            double[] var33 = new double[3];
            double var67 = arg2[0];
            double var111 = (var28[0] * var30);
            double var69 = (var67 - var111);
            var33[0] = var69;
            double var72 = arg2[1];
            double var116 = (var28[1] * var30);
            double var74 = (var72 - var116);
            var33[1] = var74;
            double var77 = arg2[2];
            double var121 = (var28[2] * var30);
            double var79 = (var77 - var121);
            var33[2] = var79;
            double[] var35 = GeometryPrimitives.computeVector(var33);
            int var46 = var19;
            double var39 = (arg1[var46] * 0.5);
            int var47 = var19;
            int var81 = var19;
            double var55 = arg0[var81][0];
            double var90 = (var35[0] * var39);
            double var57 = (var55 + var90);
            int var124 = var19;
            double var94 = arg0[var124][1];
            double var136 = (var35[1] * var39);
            double var96 = (var94 + var136);
            int var162 = var19;
            double var140 = arg0[var162][2];
            double var170 = (var35[2] * var39);
            double var142 = (var140 + var170);
            double[] var59 = GeometryPrimitives.computeVector(var57, var96, var142);
            arg3[var47] = var59;
            int var48 = var19;
            int var83 = var19;
            double var62 = arg0[var83][0];
            double var101 = (var35[0] * var39);
            double var64 = (var62 - var101);
            int var127 = var19;
            double var105 = arg0[var127][1];
            double var149 = (var35[1] * var39);
            double var107 = (var105 - var149);
            int var165 = var19;
            double var153 = arg0[var165][2];
            double var177 = (var35[2] * var39);
            double var155 = (var153 - var177);
            double[] var66 = GeometryPrimitives.computeVector(var64, var107, var155);
            arg4[var48] = var66;
            var19 = (var19 + 1);
        }
    }

    private void drawGeometryPart(CosmeticDrawBuilder arg0, double[] arg1, double arg2, double arg3, double arg4, double arg5, double arg6, int arg7, double arg8, double arg9, double arg10, double arg11, int arg12, int arg13) {
        int var56 = 0;
        double var79 = 0.0;
        double var484 = 0.0;
        double var312 = GeometryPrimitives.computeDoubleMetric(((arg7 * 19) + 2));
        double var197 = (0.07 * var312);
        double var73 = (0.17 + var197);
        if (arg0.enabledState) {
            double var166 = (arg8 * var73);
            double var281 = GeometryPrimitives.computeDoubleMetric(((arg7 * 19) + 1));
            var79 = TargetEspMathUtils.computeDoubleMetric((var166 + var281));
        } else {
            double var317 = GeometryPrimitives.computeDoubleMetric(((arg7 * 19) + 1));
            double var201 = (0.5 * var317);
            var79 = (0.35 + var201);
        }
        double var45 = var79;
        if (Double.compare(var45, 0.07) < 0) {
            return;
        }
        double var86 = (1.12 * arg5);
        double var210 = (((double) arg7) / 4.0);
        double var90 = (6.283185307179586 * var210);
        int var214 = ((arg7 * 19) + 3);
        double var215 = GeometryPrimitives.computeDoubleMetric(var214);
        double var92 = (var90 + var215);
        double var100 = GeometryPrimitives.computeDoubleMetric(((1.0 - var45) / 0.3));
        double var223 = (var45 / 0.1);
        double var225 = GeometryPrimitives.computeDoubleMetric(var223);
        double var102 = (var100 * var225);
        if (Double.compare(var102, 0.03) < 0) {
            return;
        }
        double var109 = Math.min(0.34, var45);
        double[][] var112 = new double[8][];
        var56 = 0;
        while (true) {
            if (var56 >= 8) break;
            double var423 = (((double) var56) / 7.0);
            double var346 = (1.0 - var423);
            double var253 = (var109 * var346);
            double var151 = (var45 - var253);
            double var261 = (((arg10 * var151) * 1.25) * 6.283185307179586);
            double var155 = (var92 + var261);
            double var265 = (arg9 * 0.55);
            double var157 = (var155 + var265);
            double var427 = (var151 * 5.0);
            double var476 = ((double) arg7);
            double var478 = (var476 * 1.7);
            double var429 = (var427 + var478);
            boolean var481 = arg0.enabledState;
            if (var481) {
                var484 = (arg8 * 0.9);
            } else {
                var484 = 0.0;
            }
            double var433 = Math.sin((var429 + var484));
            double var358 = (0.1 * var433);
            double var269 = (1.16 + var358);
            double var161 = (arg4 * var269);
            int var196 = var56;
            double var362 = Math.cos(var157);
            double var273 = (var161 * var362);
            double var366 = (arg3 + 0.02);
            double var439 = (var151 * var86);
            double var368 = (var366 + var439);
            double var490 = Math.sin(var157);
            double var443 = (var161 * var490);
            double[] var275 = GeometryPrimitives.computeVector(var273, var368, var443);
            var112[var196] = var275;
            var56 = (var56 + 1);
        }
        double[] var116 = var112[7];
        double var172 = var116[0];
        double var285 = var116[1];
        double var374 = var116[2];
        double var118 = TargetEspMathUtils.computeDoubleMetric(arg1, var172, var285, var374, arg2);
        if (Double.compare(var118, 0.03) <= 0) {
            return;
        }
        double var125 = (0.03 * arg6);
        double var395 = GeometryPrimitives.computeDoubleMetric(((arg7 * 19) + 4));
        double var326 = (0.3 * var395);
        double var238 = (0.85 + var326);
        double var127 = (var125 * var238);
        int var130 = ColorMathUtils.computeColorOrIndex7(arg12, 0.6);
        DoubleUnaryOperator var242 = (p0) -> SoulFlameTargetRenderer.lambda$a(var127, p0);
        IGradientColorSampler var329 = (p0, p1) -> SoulFlameTargetRenderer.lambda$a(arg13, arg12, var102, var118, arg11, p0, p1);
        GeometryPrimitives.drawGeometryPart(arg0, var112, var242, 3, var329, false, false);
        double var134 = (var127 * 2.0);
        double var137 = var116[0];
        double var140 = var116[1];
        double var143 = var116[2];
        double var186 = (((0.95 * var102) * var118) * arg11);
        int var146 = ColorMathUtils.computeColorOrIndex5(var130, var186);
        double[] var192 = GeometryPrimitives.computeVector((var137 - var134), var140, var143);
        double var402 = (var134 * 1.5);
        double var332 = (var140 - var402);
        double[] var247 = GeometryPrimitives.computeVector(var137, var332, var143);
        double[] var302 = GeometryPrimitives.computeVector((var137 + var134), var140, var143);
        double var459 = (var134 * 1.5);
        double var408 = (var140 + var459);
        double[] var336 = GeometryPrimitives.computeVector(var137, var408, var143);
        arg0.applyStep(var192, var247, var302, var336, var146);
        double var387 = (var143 - var134);
        double[] var195 = GeometryPrimitives.computeVector(var137, var140, var387);
        double var412 = (var134 * 1.5);
        double var339 = (var140 - var412);
        double[] var250 = GeometryPrimitives.computeVector(var137, var339, var143);
        double var453 = (var143 + var134);
        double[] var307 = GeometryPrimitives.computeVector(var137, var140, var453);
        double var467 = (var134 * 1.5);
        double var418 = (var140 + var467);
        double[] var343 = GeometryPrimitives.computeVector(var137, var418, var143);
        arg0.applyStep(var195, var250, var307, var343, var146);
    }

    private void drawGeometryPart(CosmeticDrawBuilder arg0, double[] arg1, double arg2, double arg3, double arg4, double arg5, double arg6, double arg7, double arg8, double arg9, int arg10, int arg11) {
        int var36 = 0;
        double var66 = 0.0;
        var36 = 0;
        while (true) {
            if (var36 < 18) {
                double var242 = GeometryPrimitives.computeDoubleMetric(((var36 * 11) + 1));
                double var142 = (2.0 * var242);
                double var59 = (2.2 + var142);
                if (arg0.enabledState) {
                    double var123 = (arg7 / var59);
                    double var219 = GeometryPrimitives.computeDoubleMetric(((var36 * 11) + 2));
                    var66 = TargetEspMathUtils.computeDoubleMetric((var123 + var219));
                }
            } else {
                return;
                var66 = GeometryPrimitives.computeDoubleMetric(((var36 * 11) + 2));
            }
            double var39 = var66;
            double var147 = GeometryPrimitives.computeDoubleMetric(((var36 * 11) + 3));
            double var70 = (6.283185307179586 * var147);
            double var151 = (var39 * 1.4);
            double var72 = (var70 + var151);
            double var155 = (arg8 * 0.2);
            double var74 = (var72 + var155);
            double var307 = GeometryPrimitives.computeDoubleMetric(((var36 * 11) + 4));
            double var250 = (0.65 * var307);
            double var159 = (0.5 + var250);
            double var78 = (arg4 * var159);
            double var254 = (0.3 * var39);
            double var163 = (1.0 - var254);
            double var80 = (var78 * var163);
            double var167 = Math.cos(var74);
            double var84 = (var80 * var167);
            double var171 = Math.sin(var74);
            double var88 = (var80 * var171);
            double var92 = (arg3 + 0.02);
            double var258 = (arg5 + 0.3);
            double var177 = (var39 * var258);
            double var94 = (var92 + var177);
            double var262 = Math.pow(var39, 0.7);
            double var183 = Math.sin((3.141592653589793 * var262));
            double var98 = (0.95 * var183);
            double var186 = TargetEspMathUtils.computeDoubleMetric(arg1, var84, var94, var88, arg2);
            double var102 = ((var98 * var186) * arg9);
            if (Double.compare(var102, 0.04) < 0) {
            } else {
                double var109 = (0.026 * arg6);
                double var266 = (0.6 * var39);
                double var196 = (1.0 - var266);
                double var111 = (var109 * var196);
                double var320 = GeometryPrimitives.computeDoubleMetric(((var36 * 11) + 6));
                double var270 = (0.5 * var320);
                double var200 = (0.8 + var270);
                double var113 = (var111 * var200);
                int var116 = ColorMathUtils.computeColorOrIndex7(arg10, 0.5);
                double var206 = GeometryPrimitives.computeDoubleMetric((var39 * 1.2));
                int var118 = ColorMathUtils.computeColorOrIndex5(ColorMathUtils.computeColorOrIndex(var116, arg11, var206), var102);
                double[] var138 = GeometryPrimitives.computeVector((var84 - var113), var94, var88);
                double var324 = (var113 * 1.6);
                double var276 = (var94 - var324);
                double[] var210 = GeometryPrimitives.computeVector(var84, var276, var88);
                double[] var232 = GeometryPrimitives.computeVector((var84 + var113), var94, var88);
                double var357 = (var113 * 1.6);
                double var330 = (var94 + var357);
                double[] var280 = GeometryPrimitives.computeVector(var84, var330, var88);
                arg0.applyStep(var138, var210, var232, var280, var118);
                double var300 = (var88 - var113);
                double[] var141 = GeometryPrimitives.computeVector(var84, var94, var300);
                double var334 = (var113 * 1.6);
                double var283 = (var94 - var334);
                double[] var213 = GeometryPrimitives.computeVector(var84, var283, var88);
                double var353 = (var88 + var113);
                double[] var237 = GeometryPrimitives.computeVector(var84, var94, var353);
                double var365 = (var113 * 1.6);
                double var340 = (var94 + var365);
                double[] var287 = GeometryPrimitives.computeVector(var84, var340, var88);
                arg0.applyStep(var141, var213, var237, var287, var118);
            }
            var36 = (var36 + 1);
        }
    }

}
