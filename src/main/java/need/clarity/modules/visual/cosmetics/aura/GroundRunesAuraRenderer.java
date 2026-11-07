package need.clarity.modules.visual.cosmetics.aura;

import need.clarity.modules.visual.cosmetics.ColorMathUtils;
import need.clarity.modules.visual.cosmetics.CosmeticDrawBuilder;
import need.clarity.modules.visual.cosmetics.GeometryPrimitives;
import need.clarity.modules.visual.cosmetics.ICosmeticEffectRenderer;
import need.clarity.modules.visual.cosmetics.IGradientColorSampler;

public final class GroundRunesAuraRenderer implements ICosmeticEffectRenderer {
    public static final int CONST_INT = 446;
    private static final double CONST_DBL = 0.03;
    private static final double CONST_DBL1 = 0.003;
    private static final int CONST_INT1 = 14;
    private static final int CONST_INT2 = 36;
    private static final int CONST_INT3 = 14;
    private static final double CONST_DBL2 = 3.4;
    private static final double[][][] meshTensor;
    private final GroundRunesAuraSpec groundRunesAuraSpec;

    public GroundRunesAuraRenderer() {
        GroundRunesAuraSpec var2 = GroundRunesAuraSpec.evaluate();
        this(var2);
    }

    public GroundRunesAuraRenderer(GroundRunesAuraSpec arg0) {
        this.groundRunesAuraSpec = arg0;
    }

    public void renderEffect(CosmeticDrawBuilder arg0) {
        double var84 = 0.0;
        double var86 = 0.0;
        double var33 = this.groundRunesAuraSpec.radius();
        if (arg0.enabledState) {
            var84 = arg0.doubleValue;
        } else {
            var84 = 0.0;
        }
        double var19 = var84;
        double var43 = ((this.groundRunesAuraSpec.phase() * 0.35) * 6.283185307179586);
        double var55 = (((-(this.groundRunesAuraSpec.phase()) * 0.6) * 6.283185307179586) + 0.5235987755982988);
        if (arg0.enabledState) {
            var86 = (var19 * 0.3);
        } else {
            var86 = 0.15;
        }
        double var25 = var86;
        double var60 = AuraRenderMath.drawGeometryPart(arg0, 1.6, 0.0);
        int var64 = ColorMathUtils.computeColorOrIndex7(arg0.counterValue, 0.25);
        int var66 = arg0.counterValue1;
        int var69 = ColorMathUtils.computeColorOrIndex7(arg0.counterValue1, 0.6);
        drawGeometryPart(arg0, var33, var60);
        drawGeometryPart(arg0, var33, var25, var64, var66, var69);
        drawGeometryPart(arg0, var33, var43, var66);
        this.drawGeometryPart(arg0, var33, var43, var19, var69);
        this.drawGeometryPart(arg0, var33, var55, var19, var64, var66, var69);
        if (arg0.enabledState) {
            drawGeometryPart1(arg0, var33, var19, var64);
        } else {
            drawGeometryPart2(arg0, var33, 0.5, var64);
        }
        this.drawGeometryPart(arg0, var33, var55, var64, var69);
        this.drawGeometryPart3(arg0, var33, var19, var69);
    }

    private static void drawGeometryPart(CosmeticDrawBuilder arg0, double arg1, double arg2) {
        double var25 = (0.07 * arg2);
        double var22 = (0.2 + var25);
        double var27 = (arg1 * 1.03);
        IGradientColorSampler var30 = (p0, p1) -> GroundRunesAuraRenderer.drawGeometryPart(arg0, var22, p0, p1);
        GeometryPrimitives.drawGeometryPart(arg0, 0.03, var27, 36, var30);
    }

    private static IGradientColorSampler evaluate(int arg0, int arg1, double arg2, double arg3, double arg4) {
        return (p0, p1) -> GroundRunesAuraRenderer.lambda$a(arg2, arg0, arg1, arg3, arg4, p0, p1);
    }

    private static void drawGeometryPart(CosmeticDrawBuilder arg0, double arg1, double arg2, int arg3, int arg4, int arg5) {
        double var31 = (arg1 * 0.955);
        double var43 = (arg1 * 1.0);
        IGradientColorSampler var64 = evaluate(arg3, arg5, arg2, 0.55, 1.0);
        GeometryPrimitives.drawGeometryPart(arg0, 0.033, var31, var43, 48, var64);
        double var35 = (arg1 * 0.87);
        double var49 = (arg1 * 0.885);
        double var75 = (-(arg2) * 0.7);
        IGradientColorSampler var66 = evaluate(arg4, arg5, var75, 0.45, 0.85);
        GeometryPrimitives.drawGeometryPart(arg0, 0.033, var35, var49, 48, var66);
        double var39 = (arg1 * 0.7);
        double var55 = (arg1 * 0.715);
        double var79 = (arg2 * 0.5);
        IGradientColorSampler var68 = evaluate(arg4, arg5, var79, 0.45, 0.85);
        GeometryPrimitives.drawGeometryPart(arg0, 0.033, var39, var55, 40, var68);
    }

    private static void drawGeometryPart(CosmeticDrawBuilder arg0, double arg1, double arg2, int arg3) {
        double var107 = 0.0;
        int var111 = 0;
        int var112 = 0;
        int var49 = 0;
        double var83 = 0.0;
        double var85 = 0.0;
        int var25 = 0;
        int var37 = ColorMathUtils.computeColorOrIndex5(arg3, 0.55);
        int var40 = ColorMathUtils.computeColorOrIndex5(ColorMathUtils.computeColorOrIndex7(arg3, 0.3), 0.85);
        var25 = 0;
        while (true) {
            if (var25 < 36) {
                double var91 = ((double) var25);
                double var79 = ((6.283185307179586 * var91) / 36.0);
                double var45 = (arg2 + var79);
                if ((var25 % 3) == 0) {
                    var49 = 1;
                }
            } else {
                return;
                var49 = 0;
            }
            int var28 = var49;
            if (var28 != 0) {
                var85 = 0.895;
            } else {
                var85 = 0.915;
            }
            double var52 = (arg1 * var85);
            if (var28 != 0) {
                var83 = 0.945;
            } else {
                var83 = 0.935;
            }
            double var56 = (arg1 * var83);
            double var60 = Math.cos(var45);
            double var64 = Math.sin(var45);
            double var75 = (var52 * var60);
            double var89 = (var52 * var64);
            double var97 = (var56 * var60);
            double var101 = (var56 * var64);
            if (var28 != 0) {
                var107 = 0.014;
            } else {
                var107 = 0.01;
            }
            double var105 = (arg1 * var107);
            if (var28 != 0) {
                var111 = var40;
            } else {
                var111 = var37;
            }
            if (var28 != 0) {
                var112 = var40;
            } else {
                var112 = var37;
            }
            AuraRenderMath.drawGeometryPart(arg0, var75, var89, var97, var101, var105, 0.036, var111, var112);
            var25 = (var25 + 1);
        }
    }

    private void drawGeometryPart(CosmeticDrawBuilder arg0, double arg1, double arg2, double arg3, int arg4) {
        double var163 = 0.0;
        int var44 = 0;
        int var57 = 0;
        double var67 = (arg1 * 0.7925);
        double var71 = (arg1 * 0.052);
        double var75 = (arg1 * 0.045);
        double var79 = (arg1 * 0.012);
        var44 = 0;
        while (true) {
            if (var44 >= 14) break;
            double var280 = ((double) var44);
            double var194 = ((6.283185307179586 * var280) / 14.0);
            double var87 = (arg2 + var194);
            double var91 = Math.cos(var87);
            double var95 = Math.sin(var87);
            if (arg0.enabledState) {
                double var286 = (arg3 * 2.0);
                double var357 = (((double) var44) * 0.9);
                double var290 = Math.sin((var286 + var357));
                double var198 = (0.5 * var290);
                var163 = (0.5 + var198);
            } else {
                var163 = 0.5;
            }
            double var51 = var163;
            int var102 = arg0.counterValue1;
            int var166 = arg0.counterValue;
            int var104 = ColorMathUtils.computeColorOrIndex5(ColorMathUtils.computeColorOrIndex(var102, var166, 0.3), 0.85);
            int var106 = arg0.counterValue1;
            double var294 = (0.6 * var51);
            double var204 = (0.25 + var294);
            int var108 = ColorMathUtils.computeColorOrIndex5(ColorMathUtils.computeColorOrIndex(var106, arg4, var204), 1.0);
            double[][][] var109 = meshTensor;
            int var172 = var44;
            int var207 = meshTensor.length;
            int var173 = (var172 % var207);
            double[][] var110 = var109[var173];
            int var112 = var110.length;
            var57 = 0;
            while (true) {
                if (var57 >= var112) break;
                int var181 = var57;
                double[] var129 = var110[var181];
                double var132 = (var67 * var91);
                double var214 = -(var95);
                double var297 = var129[0];
                double var218 = ((var214 * var297) * var75);
                double var134 = (var132 + var218);
                double var302 = var129[1];
                double var224 = ((var91 * var302) * var71);
                double var136 = (var134 + var224);
                double var140 = (var67 * var95);
                double var307 = var129[0];
                double var232 = ((var91 * var307) * var75);
                double var142 = (var140 + var232);
                double var312 = var129[1];
                double var238 = ((var95 * var312) * var71);
                double var144 = (var142 + var238);
                double var148 = (var67 * var91);
                double var244 = -(var95);
                double var317 = var129[2];
                double var248 = ((var244 * var317) * var75);
                double var150 = (var148 + var248);
                double var322 = var129[3];
                double var254 = ((var91 * var322) * var71);
                double var152 = (var150 + var254);
                double var156 = (var67 * var95);
                double var327 = var129[2];
                double var262 = ((var91 * var327) * var75);
                double var158 = (var156 + var262);
                double var332 = var129[3];
                double var268 = ((var95 * var332) * var71);
                double var160 = (var158 + var268);
                AuraRenderMath.drawGeometryPart(arg0, var136, var144, var152, var160, var79, 0.036, var104, var108);
                var57 = (var57 + 1);
            }
            double var117 = (var87 + 0.2243994752564138);
            double var121 = Math.cos(var117);
            double var125 = Math.sin(var117);
            double var179 = ((arg1 * 0.73) * var121);
            double var276 = ((arg1 * 0.73) * var125);
            double var342 = ((arg1 * 0.855) * var121);
            double var367 = ((arg1 * 0.855) * var125);
            double var375 = (arg1 * 0.006);
            int var384 = ColorMathUtils.computeColorOrIndex5(arg0.counterValue1, 0.45);
            int var388 = ColorMathUtils.computeColorOrIndex5(arg0.counterValue1, 0.45);
            AuraRenderMath.drawGeometryPart(arg0, var179, var276, var342, var367, var375, 0.036, var384, var388);
            var44 = (var44 + 1);
        }
    }

    private void drawGeometryPart(CosmeticDrawBuilder arg0, double arg1, double arg2, double arg3, int arg4, int arg5, int arg6) {
        int var43 = 0;
        int var45 = 0;
        int var46 = 0;
        int var47 = 0;
        double var49 = (arg1 * 0.61);
        double var55 = (arg1 * 0.013);
        double[][] var58 = new double[6][];
        var43 = 0;
        while (true) {
            if (var43 >= 6) break;
            int var110 = var43;
            double[] var121 = new double[2];
            double var244 = ((((double) var43) * 3.141592653589793) / 3.0);
            double var205 = Math.cos((arg2 + var244));
            double var172 = (var49 * var205);
            var121[0] = var172;
            double var251 = ((((double) var43) * 3.141592653589793) / 3.0);
            double var211 = Math.sin((arg2 + var251));
            double var176 = (var49 * var211);
            var121[1] = var176;
            var58[var110] = var121;
            var43 = (var43 + 1);
        }
        int var62 = ColorMathUtils.computeColorOrIndex5(arg4, 0.95);
        int var64 = ColorMathUtils.computeColorOrIndex5(arg5, 0.95);
        var45 = 0;
        while (true) {
            if (var45 >= 2) break;
            var46 = 0;
            while (true) {
                if (var46 >= 3) break;
                int var103 = var45;
                int var136 = var46;
                int var116 = (2 * var136);
                int var104 = (var103 + var116);
                double[] var79 = var58[var104];
                int var105 = var45;
                int var139 = ((var46 + 1) % 3);
                int var118 = (2 * var139);
                int var106 = (var105 + var118);
                double[] var81 = var58[var106];
                double var108 = var79[0];
                double var141 = var79[1];
                double var168 = var81[0];
                double var199 = var81[1];
                AuraRenderMath.drawGeometryPart(arg0, var108, var141, var168, var199, var55, 0.036, var62, var64);
                var46 = (var46 + 1);
            }
            var45 = (var45 + 1);
        }
        double var68 = AuraRenderMath.drawGeometryPart(arg0, 2.4, 0.0);
        var47 = 0;
        while (true) {
            if (var47 >= 6) break;
            int var113 = var47;
            double var100 = var58[var113][0];
            int var18 = var47;
            double var134 = var58[var18][1];
            double var232 = (0.006 * var68);
            double var192 = (0.03 + var232);
            double var165 = (arg1 * var192);
            double var196 = (0.036 + 0.003);
            int var271 = ColorMathUtils.computeColorOrIndex5(-1, 0.95);
            int var277 = ColorMathUtils.computeColorOrIndex5(arg6, 0.85);
            AuraRenderMath.drawGeometryPart(arg0, var100, var134, var165, var196, 6, arg2, var271, var277);
            var47 = (var47 + 1);
        }
        double var124 = (arg1 * 0.338);
        double var151 = (arg1 * 0.354);
        int var213 = ColorMathUtils.computeColorOrIndex5(arg5, 0.9);
        int var220 = ColorMathUtils.computeColorOrIndex5(arg6, 0.9);
        IGradientColorSampler var214 = GeometryPrimitives.evaluate1(var213, var220);
        GeometryPrimitives.drawGeometryPart(arg0, 0.036, var124, var151, 32, var214);
        double var128 = (arg1 * 0.09);
        double var157 = (arg1 * 0.108);
        IGradientColorSampler var217 = GeometryPrimitives.evaluate(ColorMathUtils.computeColorOrIndex5(arg6, 0.95));
        GeometryPrimitives.drawGeometryPart(arg0, 0.036, var128, var157, 20, var217);
        double var225 = (0.012 * var68);
        double var184 = (0.045 + var225);
        double var161 = (arg1 * var184);
        double var188 = (0.036 + 0.003);
        int var269 = ColorMathUtils.computeColorOrIndex5(-1, 0.95);
        int var273 = ColorMathUtils.computeColorOrIndex5(arg6, 0.8);
        AuraRenderMath.drawGeometryPart(arg0, 0.0, 0.0, var161, var188, 8, 0.0, var269, var273);
    }

    private static void drawGeometryPart1(CosmeticDrawBuilder arg0, double arg1, double arg2, int arg3) {
        double var15 = AuraRenderMath.computeDoubleMetric((arg2 / 3.4));
        drawGeometryPart2(arg0, arg1, var15, arg3);
    }

    private static void drawGeometryPart2(CosmeticDrawBuilder arg0, double arg1, double arg2, int arg3) {
        double var63 = GeometryPrimitives.computeDoubleMetric(arg2);
        double var51 = (0.86 * var63);
        double var39 = (0.12 + var51);
        double var29 = (arg1 * var39);
        double var45 = Math.pow((1.0 - arg2), 1.5);
        double var33 = (0.65 * var45);
        if (Double.compare(var33, 0.02) < 0) {
            return;
        }
        double var57 = (arg1 * 0.012);
        double var49 = (var29 - var57);
        double var67 = (arg1 * 0.012);
        double var61 = (var29 + var67);
        IGradientColorSampler var72 = GeometryPrimitives.evaluate(ColorMathUtils.computeColorOrIndex5(ColorMathUtils.computeColorOrIndex7(arg3, 0.4), var33));
        GeometryPrimitives.drawGeometryPart(arg0, 0.039, var49, var61, 40, var72);
    }

    private void drawGeometryPart(CosmeticDrawBuilder arg0, double arg1, double arg2, int arg3, int arg4) {
        int var28 = 0;
        double var35 = (arg1 * 0.61);
        var28 = 0;
        while (true) {
            if (var28 >= 6) break;
            double var62 = ((((double) var28) * 3.141592653589793) / 3.0);
            double var41 = (arg2 + var62);
            double var70 = (((double) var28) * 1.1);
            double var44 = AuraRenderMath.drawGeometryPart(arg0, 2.0, var70);
            double var66 = (0.26 * var44);
            double var48 = (0.32 + var66);
            double var74 = Math.cos(var41);
            double var55 = (var35 * var74);
            double var86 = Math.sin(var41);
            double var84 = (var35 * var86);
            int var91 = ColorMathUtils.computeColorOrIndex5(ColorMathUtils.computeColorOrIndex(arg3, arg4, 0.5), 0.55);
            int var95 = ColorMathUtils.computeColorOrIndex5(arg3, 0.0);
            AuraRenderMath.drawGeometryPart(arg0, var55, 0.03, var84, var48, 0.075, var91, var95);
            var28 = (var28 + 1);
        }
    }

    private void drawGeometryPart3(CosmeticDrawBuilder arg0, double arg1, double arg2, int arg3) {
        int var24 = 0;
        double var108 = 0.0;
        double[] var47 = arg0.computeVector1();
        var24 = 0;
        while (true) {
            if (var24 < 14) {
                double var118 = GeometryPrimitives.computeDoubleMetric(((var24 * 3) + 1));
                double var52 = (6.283185307179586 * var118);
                double var189 = GeometryPrimitives.computeDoubleMetric(((var24 * 3) + 2));
                double var166 = (0.65 * var189);
                double var122 = (0.2 + var166);
                double var56 = (arg1 * var122);
                double var171 = GeometryPrimitives.computeDoubleMetric(((var24 * 3) + 3));
                double var126 = (2.0 * var171);
                double var60 = (2.4 + var126);
                if (arg0.enabledState) {
                    double var66 = (arg2 / var60);
                    double var133 = GeometryPrimitives.computeDoubleMetric(((var24 * 7) + 5));
                    var108 = AuraRenderMath.computeDoubleMetric((var66 + var133));
                }
            } else {
                return;
                var108 = GeometryPrimitives.computeDoubleMetric(((var24 * 7) + 5));
            }
            double var31 = var108;
            double var137 = Math.cos(var52);
            double var72 = (var56 * var137);
            double var141 = Math.sin(var52);
            double var76 = (var56 * var141);
            double var145 = (var31 * 1.25);
            double var80 = (0.03 + var145);
            double var86 = Math.sin((3.141592653589793 * var31));
            double var193 = arg0.computeDoubleMetric();
            double var89 = AuraRenderMath.computeDoubleMetric(var47, var72, var80, var76, var193);
            double var95 = ((0.85 * var86) * var89);
            if (Double.compare(var95, 0.03) < 0) {
            } else {
                double var178 = GeometryPrimitives.computeDoubleMetric(((var24 * 5) + 9));
                double var157 = (0.12 * var178);
                double var102 = (0.16 + var157);
                int var198 = ColorMathUtils.computeColorOrIndex5(ColorMathUtils.computeColorOrIndex7(arg3, 0.4), var95);
                int var203 = ColorMathUtils.computeColorOrIndex5(arg0.counterValue1, 0.0);
                AuraRenderMath.drawGeometryPart(arg0, var72, var80, var76, var102, 0.024, var198, var203);
            }
            var24 = (var24 + 1);
        }
    }

    static {
        double[][][] var8 = new double[8][][];
        double[][] var16 = new double[3][];
        double[] var54 = new double[4];
        var54[0] = 0.0;
        double var196 = -1.0;
        var54[1] = var196;
        double var198 = 0.0;
        var54[2] = var198;
        double var200 = 1.0;
        var54[3] = var200;
        var16[0] = var54;
        double[] var56 = new double[4];
        double var202 = 0.0;
        var56[0] = var202;
        double var204 = 1.0;
        var56[1] = var204;
        double var206 = 0.7;
        var56[2] = var206;
        double var208 = 0.4;
        var56[3] = var208;
        var16[1] = var56;
        double[] var58 = new double[4];
        double var210 = 0.0;
        var58[0] = var210;
        double var212 = 0.2;
        var58[1] = var212;
        double var214 = 0.7;
        var58[2] = var214;
        double var216 = -0.2;
        var58[3] = var216;
        var16[2] = var58;
        var8[0] = var16;
        double[][] var18 = new double[3][];
        double[] var60 = new double[4];
        double var218 = -0.6;
        var60[0] = var218;
        double var220 = -1.0;
        var60[1] = var220;
        double var222 = 0.0;
        var60[2] = var222;
        double var224 = 1.0;
        var60[3] = var224;
        var18[0] = var60;
        double[] var62 = new double[4];
        double var226 = 0.0;
        var62[0] = var226;
        double var228 = 1.0;
        var62[1] = var228;
        double var230 = 0.6;
        var62[2] = var230;
        double var232 = -1.0;
        var62[3] = var232;
        var18[1] = var62;
        double[] var64 = new double[4];
        double var234 = -0.35;
        var64[0] = var234;
        double var236 = -0.15;
        var64[1] = var236;
        double var238 = 0.35;
        var64[2] = var238;
        double var240 = -0.15;
        var64[3] = var240;
        var18[2] = var64;
        var8[1] = var18;
        double[][] var20 = new double[3][];
        double[] var66 = new double[4];
        double var242 = -0.6;
        var66[0] = var242;
        double var244 = 1.0;
        var66[1] = var244;
        double var246 = 0.6;
        var66[2] = var246;
        double var248 = 1.0;
        var66[3] = var248;
        var20[0] = var66;
        double[] var68 = new double[4];
        double var250 = 0.0;
        var68[0] = var250;
        double var252 = 1.0;
        var68[1] = var252;
        double var254 = 0.0;
        var68[2] = var254;
        var68[3] = -1.0;
        var20[1] = var68;
        double[] var70 = new double[4];
        var70[0] = -0.6;
        var70[1] = -1.0;
        var70[2] = 0.6;
        var70[3] = -1.0;
        var20[2] = var70;
        var8[2] = var20;
        double[][] var22 = new double[2][];
        double[] var72 = new double[4];
        var72[0] = -0.6;
        var72[1] = -1.0;
        var72[2] = 0.6;
        var72[3] = 1.0;
        var22[0] = var72;
        double[] var74 = new double[4];
        var74[0] = 0.6;
        var74[1] = -1.0;
        var74[2] = -0.6;
        var74[3] = 1.0;
        var22[1] = var74;
        var8[3] = var22;
        double[][] var24 = new double[4][];
        double[] var76 = new double[4];
        var76[0] = 0.0;
        var76[1] = 1.0;
        var76[2] = 0.6;
        var76[3] = 0.0;
        var24[0] = var76;
        double[] var78 = new double[4];
        var78[0] = 0.6;
        var78[1] = 0.0;
        var78[2] = 0.0;
        var78[3] = -1.0;
        var24[1] = var78;
        double[] var80 = new double[4];
        var80[0] = 0.0;
        var80[1] = -1.0;
        var80[2] = -0.6;
        var80[3] = 0.0;
        var24[2] = var80;
        double[] var82 = new double[4];
        var82[0] = -0.6;
        var82[1] = 0.0;
        var82[2] = 0.0;
        var82[3] = 1.0;
        var24[3] = var82;
        var8[4] = var24;
        double[][] var26 = new double[3][];
        double[] var84 = new double[4];
        var84[0] = -0.5;
        var84[1] = 1.0;
        var84[2] = -0.5;
        var84[3] = -1.0;
        var26[0] = var84;
        double[] var86 = new double[4];
        var86[0] = -0.5;
        var86[1] = 0.0;
        var86[2] = 0.5;
        var86[3] = 1.0;
        var26[1] = var86;
        double[] var88 = new double[4];
        var88[0] = -0.5;
        int var169 = 1;
        var88[var169] = 0.0;
        int var170 = 2;
        var88[var170] = 0.5;
        int var171 = 3;
        var88[var171] = -1.0;
        var26[2] = var88;
        var8[5] = var26;
        double[][] var28 = new double[3][];
        double[] var90 = new double[4];
        int var172 = 0;
        var90[var172] = -0.6;
        int var173 = 1;
        var90[var173] = -0.6;
        int var174 = 2;
        var90[var174] = 0.6;
        int var175 = 3;
        var90[var175] = -0.6;
        var28[0] = var90;
        double[] var92 = new double[4];
        int var176 = 0;
        var92[var176] = 0.0;
        int var177 = 1;
        var92[var177] = -0.6;
        int var178 = 2;
        var92[var178] = 0.0;
        int var179 = 3;
        var92[var179] = 1.0;
        var28[1] = var92;
        double[] var94 = new double[4];
        int var180 = 0;
        var94[var180] = -0.4;
        int var181 = 1;
        var94[var181] = 0.4;
        int var182 = 2;
        var94[var182] = 0.4;
        int var183 = 3;
        var94[var183] = 0.4;
        var28[2] = var94;
        var8[6] = var28;
        double[][] var30 = new double[3][];
        double[] var96 = new double[4];
        int var184 = 0;
        var96[var184] = -0.6;
        int var185 = 1;
        var96[var185] = 1.0;
        int var186 = 2;
        var96[var186] = 0.6;
        int var187 = 3;
        var96[var187] = 0.2;
        var30[0] = var96;
        double[] var98 = new double[4];
        int var188 = 0;
        var98[var188] = 0.6;
        int var189 = 1;
        var98[var189] = 0.2;
        int var190 = 2;
        var98[var190] = -0.6;
        int var191 = 3;
        var98[var191] = -0.2;
        var30[1] = var98;
        double[] var100 = new double[4];
        int var192 = 0;
        var100[var192] = -0.6;
        int var193 = 1;
        var100[var193] = -0.2;
        int var194 = 2;
        var100[var194] = 0.6;
        int var195 = 3;
        var100[var195] = -1.0;
        var30[2] = var100;
        var8[7] = var30;
        meshTensor = var8;
    }

}
