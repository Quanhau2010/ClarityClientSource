package need.clarity.modules.visual.cosmetics.aura;

import need.clarity.modules.visual.cosmetics.ColorMathUtils;
import need.clarity.modules.visual.cosmetics.CosmeticDrawBuilder;
import need.clarity.modules.visual.cosmetics.GeometryPrimitives;
import need.clarity.modules.visual.cosmetics.ICosmeticEffectRenderer;
import need.clarity.modules.visual.cosmetics.IGradientColorSampler;

public final class VoidAuraRenderer implements ICosmeticEffectRenderer {
    public static final int CONST_INT = 13073919;
    public static final int CONST_INT1 = 3805050;
    public static final int CONST_INT2 = 360;
    private static final int CONST_INT3 = 4;
    private static final int CONST_INT4 = 8;
    private static final int CONST_INT5 = 6;
    private static final int CONST_INT6 = 8;
    private static final int CONST_INT7 = 22;
    private static final double CONST_DBL = 0.012;
    private static final double CONST_DBL1 = 0.02;
    private static final double CONST_DBL2 = 0.028;
    private final ElementalAuraSpec elementalAuraSpec;

    public VoidAuraRenderer() {
        ElementalAuraSpec var2 = ElementalAuraSpec.evaluate();
        this(var2);
    }

    public VoidAuraRenderer(ElementalAuraSpec arg0) {
        this.elementalAuraSpec = arg0;
    }

    public void renderEffect(CosmeticDrawBuilder arg0) {
        int var83 = 0;
        int var24 = 0;
        double var46 = 0.0;
        if (arg0.enabledState) {
            var46 = arg0.doubleValue;
        } else {
            var46 = 0.0;
        }
        double var17 = var46;
        double var69 = this.elementalAuraSpec.radius();
        double var54 = (0.75 * var69);
        double var28 = (0.35 + var54);
        double var32 = this.elementalAuraSpec.bodyHeight();
        double[] var35 = arg0.computeVector1();
        this.drawGeometryPart(arg0, var35, var28, var17);
        var24 = 0;
        while (true) {
            if (var24 >= 6) break;
            int var80 = var24;
            this.drawGeometryPart(arg0, var35, var28, var32, var80, var17);
            var24 = (var24 + 1);
        }
        var83 = 0;
        while (true) {
            if (var83 >= 8) break;
            int var79 = var83;
            this.drawGeometryPart1(arg0, var35, var28, var32, var79, var17);
            var83 = (var83 + 1);
        }
        this.drawGeometryPart(arg0, var28, var32, var17);
        this.drawGeometryPart(arg0, var35, var28, var32, var17);
    }

    private void drawGeometryPart(CosmeticDrawBuilder arg0, double[] arg1, double arg2, double arg3) {
        double var150 = 0.0;
        double var217 = 0.0;
        double var219 = 0.0;
        int var40 = 0;
        double var43 = 0.0;
        double var45 = 0.0;
        int var47 = 0;
        if (arg0.enabledState) {
            double var253 = Math.sin((arg3 * 1.7));
            var219 = (0.025 * var253);
        } else {
            var219 = 0.0;
        }
        double var64 = (1.0 + var219);
        double var68 = (arg2 * var64);
        int var72 = ColorMathUtils.computeColorOrIndex6(arg0.counterValue1, 0.16);
        int var75 = ColorMathUtils.computeColorOrIndex6(arg0.counterValue1, 0.55);
        int var78 = ColorMathUtils.computeColorOrIndex7(arg0.counterValue, 0.2);
        double var221 = (var68 * 1.15);
        IGradientColorSampler var272 = (p0, p1) -> VoidAuraRenderer.lambda$b(var72, var75, p0, p1);
        GeometryPrimitives.drawGeometryPart(arg0, 0.012, var221, 24, var272);
        double var225 = (var68 * 0.8);
        double var260 = (var68 * 0.9);
        IGradientColorSampler var291 = (p0, p1) -> VoidAuraRenderer.drawGeometryPart(arg0, var78, arg3, p0, p1);
        GeometryPrimitives.drawGeometryPart(arg0, 0.028, var225, var260, 20, var291);
        double var229 = (var68 * 0.9);
        double var266 = (var68 * 1.0);
        IGradientColorSampler var293 = (p0, p1) -> VoidAuraRenderer.drawGeometryPart(var78, arg0, arg3, p0, p1);
        GeometryPrimitives.drawGeometryPart(arg0, 0.028, var229, var266, 20, var293);
        if (arg0.enabledState) {
            var150 = (arg3 * 0.7);
        } else {
            var150 = 0.0;
        }
        double var38 = var150;
        var40 = 0;
        while (true) {
            if (var40 >= 4) break;
            double var175 = ((double) var40);
            double var94 = (((6.283185307179586 * var175) / 4.0) + var38);
            var43 = 0.0;
            var45 = 0.0;
            var47 = 0;
            while (true) {
                if (var47 > 8) break;
                double var105 = (((double) var47) / 8.0);
                double var235 = (0.76 * var105);
                double var185 = (0.88 - var235);
                double var109 = (var68 * var185);
                double var189 = (2.0 * var105);
                double var113 = (var94 + var189);
                double var193 = Math.cos(var113);
                double var117 = (var109 * var193);
                double var197 = Math.sin(var113);
                double var121 = (var109 * var197);
                if (var47 > 0) {
                    double var128 = (((double) (var47 - 1)) / 8.0);
                    int var131 = drawGeometryPart(arg0, var78, var128);
                    int var133 = drawGeometryPart(arg0, var78, var105);
                    double var241 = (0.55 * var128);
                    double var207 = (1.0 - var241);
                    double var136 = (0.11 * var207);
                    double var167 = var43;
                    double var231 = var45;
                    AuraRenderMath.drawGeometryPart(arg0, var167, var231, var117, var121, var136, 0.02, var131, var133);
                }
                var43 = var117;
                var45 = var121;
                var47 = (var47 + 1);
            }
            var40 = (var40 + 1);
        }
        if (arg0.enabledState) {
            double var247 = Math.sin((arg3 * 4.0));
            var217 = (0.012 * var247);
        } else {
            var217 = 0.0;
        }
        double var145 = (0.055 + var217);
        int var302 = ColorMathUtils.computeColorOrIndex5(-1, 0.9);
        int var307 = ColorMathUtils.computeColorOrIndex5(arg0.counterValue, 0.0);
        AuraRenderMath.drawGeometryPart(arg0, 0.0, 0.0, var145, 0.028, 8, 0.0, var302, var307);
    }

    private static double computeDoubleMetric(double arg0, double arg1) {
        double var36 = (arg0 * 2.0);
        double var42 = (arg1 * 0.35);
        double var38 = (var36 - var42);
        double var32 = Math.cos((6.283185307179586 * var38));
        double var24 = (0.5 * var32);
        double var18 = (0.5 + var24);
        double var28 = (0.6 * var18);
        return (0.3 + var28);
    }

    private static int drawGeometryPart(CosmeticDrawBuilder arg0, int arg1, double arg2) {
        int var14 = arg0.counterValue;
        int var12 = ColorMathUtils.computeColorOrIndex(arg1, var14, arg2);
        double var21 = Math.pow((1.0 - arg2), 0.8);
        double var17 = (0.65 * var21);
        return ColorMathUtils.computeColorOrIndex5(var12, var17);
    }

    private void drawGeometryPart(CosmeticDrawBuilder arg0, double[] arg1, double arg2, double arg3, int arg4, double arg5) {
        int var49 = 0;
        double var84 = 0.0;
        double var230 = 0.0;
        double var150 = ((double) arg4);
        double var332 = (GeometryPrimitives.computeDoubleMetric(((arg4 * 31) + 1)) - 0.5);
        double var242 = (0.3 * var332);
        double var152 = (var150 + var242);
        double var54 = ((6.283185307179586 * var152) / 6.0);
        if (arg0.enabledState) {
            var230 = (arg5 * 0.18);
        } else {
            var230 = 0.0;
        }
        double var56 = (var54 + var230);
        double var337 = GeometryPrimitives.computeDoubleMetric(((arg4 * 31) + 2));
        double var248 = (0.12 * var337);
        double var162 = (0.8 + var248);
        double var60 = (arg2 * var162);
        double var342 = GeometryPrimitives.computeDoubleMetric(((arg4 * 31) + 3));
        double var252 = (0.42 * var342);
        double var166 = (0.5 + var252);
        double var64 = (arg3 * var166);
        double var68 = Math.cos(var56);
        double var72 = Math.sin(var56);
        double var76 = -(var72);
        if (arg0.enabledState) {
            double var170 = (arg5 * 1.3);
            double var259 = (((double) arg4) * 2.2);
            double var174 = Math.sin((var170 + var259));
            var84 = (0.1 * var174);
        } else {
            var84 = 0.0;
        }
        double var38 = var84;
        double var88 = (var60 * var68);
        double var263 = (var60 * var72);
        double[] var90 = GeometryPrimitives.computeVector(var88, 0.02, var263);
        double var95 = ((var60 + 0.05) * var68);
        double var186 = (var76 * 0.25);
        double var97 = (var95 + var186);
        double var190 = (var64 * 0.35);
        double var273 = ((var60 + 0.05) * var72);
        double var354 = (var68 * 0.25);
        double var275 = (var273 + var354);
        double[] var99 = GeometryPrimitives.computeVector(var97, var190, var275);
        double var104 = ((var60 * 0.65) * var68);
        double var279 = (0.35 + var38);
        double var198 = (var76 * var279);
        double var106 = (var104 + var198);
        double var202 = (var64 * 0.72);
        double var287 = ((var60 * 0.65) * var72);
        double var398 = (0.35 + var38);
        double var364 = (var68 * var398);
        double var289 = (var287 + var364);
        double[] var108 = GeometryPrimitives.computeVector(var106, var202, var289);
        double var113 = ((var60 * 0.28) * var68);
        double var210 = (var76 * 0.42);
        double var115 = (var113 + var210);
        double var297 = ((var60 * 0.28) * var72);
        double var372 = (var68 * 0.42);
        double var299 = (var297 + var372);
        double[] var117 = GeometryPrimitives.computeVector(var115, var64, var299);
        double[][] var140 = new double[4][];
        var140[0] = var90;
        var140[1] = var99;
        var140[2] = var108;
        var140[3] = var117;
        double[][] var120 = GeometryPrimitives.computeMatrix(5, var140);
        int var215 = (5 / 2);
        double var143 = var120[var215][0];
        int var306 = (5 / 2);
        double var238 = var120[var306][1];
        int var375 = (5 / 2);
        double var322 = var120[var375][2];
        double var392 = arg0.computeDoubleMetric();
        double var122 = AuraRenderMath.computeDoubleMetric(arg1, var143, var238, var322, var392);
        if (Double.compare(var122, 0.03) <= 0) {
            return;
        }
        double[] var129 = new double[(5 + 1)];
        var49 = 0;
        while (true) {
            if (var49 > 5) break;
            double var135 = ((double) var49);
            double var222 = ((double) 5);
            double var137 = (var135 / var222);
            int var149 = var49;
            double var383 = Math.pow(var137, 0.9);
            double var316 = (1.0 - var383);
            double var228 = ((0.22 * var316) + 0.01);
            var129[var149] = var228;
            var49 = (var49 + 1);
        }
        double[] var310 = GeometryPrimitives.computeVector(var76, 0.0, var68);
        this.drawGeometryPart(arg0, var120, var129, var310, var122);
        double[] var313 = GeometryPrimitives.computeVector(var68, 0.0, var72);
        this.drawGeometryPart(arg0, var120, var129, var313, var122);
    }

    private void drawGeometryPart(CosmeticDrawBuilder arg0, double[][] arg1, double[] arg2, double[] arg3, double arg4) {
        int var24 = 0;
        int var350 = 0;
        int var41 = arg1.length;
        double[][] var43 = new double[var41][];
        double[][] var45 = new double[var41][];
        var24 = 0;
        while (true) {
            if (var24 >= var41) break;
            int var104 = var24;
            double[] var49 = GeometryPrimitives.computeVector(arg1, var104);
            double var51 = GeometryPrimitives.computeDoubleMetric(arg3, var49);
            double[] var54 = new double[3];
            double var187 = arg3[0];
            double var261 = (var49[0] * var51);
            double var189 = (var187 - var261);
            var54[0] = var189;
            double var192 = arg3[1];
            double var266 = (var49[1] * var51);
            double var194 = (var192 - var266);
            var54[1] = var194;
            double var197 = arg3[2];
            double var271 = (var49[2] * var51);
            double var199 = (var197 - var271);
            var54[2] = var199;
            double[] var55 = GeometryPrimitives.computeVector(var54);
            int var106 = var24;
            double var59 = (arg2[var106] * 0.5);
            int var107 = var24;
            int var201 = var24;
            double var150 = arg1[var201][0];
            double var224 = (var55[0] * var59);
            double var152 = (var150 + var224);
            int var274 = var24;
            double var228 = arg1[var274][1];
            double var294 = (var55[1] * var59);
            double var230 = (var228 + var294);
            int var324 = var24;
            double var298 = arg1[var324][2];
            double var334 = (var55[2] * var59);
            double var300 = (var298 + var334);
            double[] var154 = GeometryPrimitives.computeVector(var152, var230, var300);
            var43[var107] = var154;
            int var108 = var24;
            int var203 = var24;
            double var157 = arg1[var203][0];
            double var235 = (var55[0] * var59);
            double var159 = (var157 - var235);
            int var277 = var24;
            double var239 = arg1[var277][1];
            double var307 = (var55[1] * var59);
            double var241 = (var239 - var307);
            int var327 = var24;
            double var311 = arg1[var327][2];
            double var341 = (var55[2] * var59);
            double var313 = (var311 - var341);
            double[] var161 = GeometryPrimitives.computeVector(var159, var241, var313);
            var45[var108] = var161;
            var24 = (var24 + 1);
        }
        int var65 = ColorMathUtils.computeColorOrIndex6(arg0.counterValue1, 0.22);
        int var68 = ColorMathUtils.computeColorOrIndex7(arg0.counterValue, 0.1);
        var350 = 0;
        while (true) {
            int var70 = var350;
            int var114 = (var41 - 1);
            if (var70 >= var114) break;
            double var72 = ((double) var350);
            double var166 = (((double) var41) - 1.0);
            double var74 = (var72 / var166);
            double var78 = ((double) (var350 + 1));
            double var171 = (((double) var41) - 1.0);
            double var80 = (var78 / var171);
            double var175 = Math.pow(var74, 2.6);
            double var84 = (1.0 - var175);
            double var179 = Math.pow(var80, 2.6);
            double var88 = (1.0 - var179);
            double var118 = (0.86 * var84);
            int var92 = AuraRenderMath.computeColorOrIndex(ColorMathUtils.computeColorOrIndex5(var65, var118), arg4);
            double var124 = (0.86 * var88);
            int var95 = AuraRenderMath.computeColorOrIndex(ColorMathUtils.computeColorOrIndex5(var65, var124), arg4);
            double var130 = (0.5 * var84);
            int var98 = AuraRenderMath.computeColorOrIndex(ColorMathUtils.computeColorOrIndex5(var68, var130), arg4);
            double var136 = (0.5 * var88);
            int var101 = AuraRenderMath.computeColorOrIndex(ColorMathUtils.computeColorOrIndex5(var68, var136), arg4);
            int var181 = var350;
            double[] var141 = var43[var181];
            int var213 = var350;
            double[] var183 = arg1[var213];
            int var252 = (var350 + 1);
            double[] var215 = arg1[var252];
            int var281 = (var350 + 1);
            double[] var254 = var43[var281];
            arg0.applyStep(var141, var183, var215, var254, var98, var92, var95, var101);
            int var184 = var350;
            double[] var143 = arg1[var184];
            int var216 = var350;
            double[] var186 = var45[var216];
            int var256 = (var350 + 1);
            double[] var218 = var45[var256];
            int var285 = (var350 + 1);
            double[] var258 = arg1[var285];
            arg0.applyStep(var143, var186, var218, var258, var92, var98, var101, var95);
            var350 = (var350 + 1);
        }
    }

    private void drawGeometryPart1(CosmeticDrawBuilder arg0, double[] arg1, double arg2, double arg3, int arg4, double arg5) {
        double var157 = 0.0;
        double var174 = 0.0;
        double var212 = 0.0;
        double var222 = 0.0;
        double var116 = 0.0;
        if ((arg4 % 2) == 0) {
            var116 = 1.0;
        } else {
            var116 = -1.0;
        }
        double var23 = var116;
        double var49 = (0.16 * var23);
        double var297 = GeometryPrimitives.computeDoubleMetric(((arg4 * 37) + 1));
        double var239 = (0.6 * var297);
        double var161 = (0.7 + var239);
        double var51 = (var49 * var161);
        double var164 = ((double) arg4);
        double var304 = (GeometryPrimitives.computeDoubleMetric(((arg4 * 37) + 2)) - 0.5);
        double var243 = (0.3 * var304);
        double var166 = (var164 + var243);
        double var57 = ((6.283185307179586 * var166) / 8.0);
        if (arg0.enabledState) {
            var174 = (arg5 * var51);
        } else {
            var174 = 0.0;
        }
        double var59 = (var57 + var174);
        double var309 = GeometryPrimitives.computeDoubleMetric(((arg4 * 37) + 3));
        double var249 = (0.34 * var309);
        double var178 = (0.72 + var249);
        double var63 = (arg2 * var178);
        double var314 = GeometryPrimitives.computeDoubleMetric(((arg4 * 37) + 4));
        double var253 = (0.7 * var314);
        double var182 = (0.16 + var253);
        double var67 = (arg3 * var182);
        if (arg0.enabledState) {
            double var257 = (arg5 * 1.1);
            double var321 = (((double) arg4) * 1.9);
            double var261 = Math.sin((var257 + var321));
            var212 = (0.07 * var261);
        } else {
            var212 = 0.0;
        }
        double var69 = (var67 + var212);
        double var266 = GeometryPrimitives.computeDoubleMetric(((arg4 * 37) + 5));
        double var190 = (0.16 * var266);
        double var73 = (0.22 + var190);
        double var77 = (var73 * 0.34);
        double var196 = Math.cos(var59);
        double var81 = (var63 * var196);
        double var200 = Math.sin(var59);
        double var85 = (var63 * var200);
        double var339 = arg0.computeDoubleMetric();
        double var88 = AuraRenderMath.computeDoubleMetric(arg1, var81, var69, var85, var339);
        if (Double.compare(var88, 0.03) <= 0) {
            return;
        }
        int var96 = ColorMathUtils.computeColorOrIndex5(ColorMathUtils.computeColorOrIndex6(arg0.counterValue1, 0.35), 0.92);
        int var100 = ColorMathUtils.computeColorOrIndex5(ColorMathUtils.computeColorOrIndex7(arg0.counterValue, 0.15), 0.96);
        IGradientColorSampler var103 = AuraRenderMath.evaluate((p0, p1) -> VoidAuraRenderer.lambda$a(var96, var100, p0, p1), var88);
        CosmeticDrawBuilder var105 = arg0.drawGeometryPart();
        CosmeticDrawBuilder var107 = arg0.drawGeometryPart(var81, var69, var85);
        if (arg0.enabledState) {
            double var155 = ((arg5 * 0.9) * var23);
            double var237 = ((double) arg4);
            var157 = (var155 + var237);
        } else {
            var157 = ((double) arg4);
        }
        CosmeticDrawBuilder var109 = arg0.drawGeometryPart2(var157);
        double var141 = (((double) ((arg4 % 3) - 1)) * 0.35);
        if (arg0.enabledState) {
            double var283 = (arg5 * 0.8);
            double var344 = ((double) arg4);
            double var287 = Math.sin((var283 + var344));
            var222 = (0.25 * var287);
        } else {
            var222 = 0.0;
        }
        double var143 = (var141 + var222);
        CosmeticDrawBuilder var111 = arg0.drawGeometryPart4(var143);
        double[] var147 = GeometryPrimitives.computeVector(0.0, 0.0, 0.0);
        double[] var208 = GeometryPrimitives.computeVector(0.0, var73, 0.0);
        GeometryPrimitives.drawGeometryPart(arg0, var147, var208, var77, 4, var103);
        double[] var150 = GeometryPrimitives.computeVector(0.0, 0.0, 0.0);
        double var274 = (-(var73) * 0.75);
        double[] var211 = GeometryPrimitives.computeVector(0.0, var274, 0.0);
        GeometryPrimitives.drawGeometryPart(arg0, var150, var211, var77, 4, var103);
        CosmeticDrawBuilder var115 = arg0.drawGeometryPart1();
    }

    private void drawGeometryPart(CosmeticDrawBuilder arg0, double arg1, double arg2, double arg3) {
        double var71 = 0.0;
        double var83 = 0.0;
        double var36 = (arg1 * 0.92);
        CosmeticDrawBuilder var39 = arg0.drawGeometryPart();
        double var73 = (arg2 * 0.5);
        CosmeticDrawBuilder var41 = arg0.drawGeometryPart(0.0, var73, 0.0);
        if (arg0.enabledState) {
            var71 = (arg3 * 0.35);
        } else {
            var71 = 0.6;
        }
        CosmeticDrawBuilder var43 = arg0.drawGeometryPart2(var71);
        if (arg0.enabledState) {
            double var91 = Math.sin((arg3 * 0.6));
            var83 = (0.08 * var91);
        } else {
            var83 = 0.0;
        }
        double var65 = (1.05 + var83);
        CosmeticDrawBuilder var45 = arg0.drawGeometryPart4(var65);
        double[] var47 = arg0.computeVector1();
        double var49 = arg0.computeDoubleMetric();
        int var53 = ColorMathUtils.computeColorOrIndex7(arg0.counterValue, 0.35);
        int var55 = arg0.counterValue;
        double var95 = (var36 + 0.1);
        IGradientColorSampler var100 = (p0, p1) -> VoidAuraRenderer.lambda$a(arg3, var47, var36, var49, var55, var53, p0, p1);
        GeometryPrimitives.drawGeometryPart(arg0, 0.0, var36, var95, 28, var100);
        CosmeticDrawBuilder var58 = arg0.drawGeometryPart1();
    }

    private void drawGeometryPart(CosmeticDrawBuilder arg0, double[] arg1, double arg2, double arg3, double arg4) {
        int var25 = 0;
        double var107 = 0.0;
        var25 = 0;
        while (true) {
            if (var25 < 22) {
                double var214 = GeometryPrimitives.computeDoubleMetric(((var25 * 41) + 1));
                double var124 = (2.0 * var214);
                double var48 = (2.5 + var124);
                if (arg0.enabledState) {
                    double var103 = (arg4 / var48);
                    double var191 = GeometryPrimitives.computeDoubleMetric(((var25 * 41) + 2));
                    var107 = AuraRenderMath.computeDoubleMetric((var103 + var191));
                }
            } else {
                return;
                var107 = GeometryPrimitives.computeDoubleMetric(((var25 * 41) + 2));
            }
            double var28 = var107;
            double var129 = GeometryPrimitives.computeDoubleMetric(((var25 * 41) + 3));
            double var57 = (6.283185307179586 * var129);
            double var133 = (var28 * 3.4);
            double var59 = (var57 + var133);
            double var63 = (arg2 * 1.5);
            double var141 = Math.pow((1.0 - var28), 1.3);
            double var67 = ((var63 * var141) + 0.05);
            double var147 = Math.cos(var59);
            double var71 = (var67 * var147);
            double var151 = Math.sin(var59);
            double var75 = (var67 * var151);
            double var159 = (((var28 * var28) * arg3) * 0.6);
            double var79 = (0.03 + var159);
            double var230 = Math.pow(var28, 0.6);
            double var165 = Math.sin((3.141592653589793 * var230));
            double var83 = (0.9 * var165);
            double var318 = arg0.computeDoubleMetric();
            double var168 = AuraRenderMath.computeDoubleMetric(arg1, var71, var79, var75, var318);
            double var85 = (var83 * var168);
            if (Double.compare(var85, 0.04) < 0) {
            } else {
                double var272 = GeometryPrimitives.computeDoubleMetric(((var25 * 41) + 4));
                double var234 = (0.5 * var272);
                double var174 = (0.8 + var234);
                double var92 = (0.026 * var174);
                int var96 = ColorMathUtils.computeColorOrIndex7(arg0.counterValue, 0.2);
                double var178 = (var28 * var28);
                int var98 = ColorMathUtils.computeColorOrIndex5(ColorMathUtils.computeColorOrIndex(var96, -1, var178), var85);
                double[] var120 = GeometryPrimitives.computeVector((var71 - var92), var79, var75);
                double var276 = (var92 * 1.6);
                double var240 = (var79 - var276);
                double[] var182 = GeometryPrimitives.computeVector(var71, var240, var75);
                double[] var204 = GeometryPrimitives.computeVector((var71 + var92), var79, var75);
                double var306 = (var92 * 1.6);
                double var282 = (var79 + var306);
                double[] var244 = GeometryPrimitives.computeVector(var71, var282, var75);
                arg0.applyStep(var120, var182, var204, var244, var98);
                double var264 = (var75 - var92);
                double[] var123 = GeometryPrimitives.computeVector(var71, var79, var264);
                double var286 = (var92 * 1.6);
                double var247 = (var79 - var286);
                double[] var185 = GeometryPrimitives.computeVector(var71, var247, var75);
                double var302 = (var75 + var92);
                double[] var209 = GeometryPrimitives.computeVector(var71, var79, var302);
                double var314 = (var92 * 1.6);
                double var292 = (var79 + var314);
                double[] var251 = GeometryPrimitives.computeVector(var71, var292, var75);
                arg0.applyStep(var123, var185, var209, var251, var98);
            }
            var25 = (var25 + 1);
        }
    }

}
