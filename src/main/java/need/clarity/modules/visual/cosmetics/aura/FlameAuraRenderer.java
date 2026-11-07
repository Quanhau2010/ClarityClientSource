package need.clarity.modules.visual.cosmetics.aura;

import need.clarity.modules.visual.cosmetics.ColorMathUtils;
import need.clarity.modules.visual.cosmetics.CosmeticDrawBuilder;
import need.clarity.modules.visual.cosmetics.GeometryPrimitives;
import need.clarity.modules.visual.cosmetics.ICosmeticEffectRenderer;
import need.clarity.modules.visual.cosmetics.IGradientColorSampler;

public final class FlameAuraRenderer implements ICosmeticEffectRenderer {
    public static final int CONST_INT = 16761395;
    public static final int CONST_INT1 = 16726802;
    public static final int CONST_INT2 = 412;
    private static final int CONST_INT3 = 10;
    private static final int CONST_INT4 = 10;
    private static final int CONST_INT5 = 16;
    private static final double CONST_DBL = 0.02;
    private final ElementalAuraSpec elementalAuraSpec;

    public FlameAuraRenderer() {
        ElementalAuraSpec var2 = ElementalAuraSpec.evaluate();
        this(var2);
    }

    public FlameAuraRenderer(ElementalAuraSpec arg0) {
        this.elementalAuraSpec = arg0;
    }

    public void renderEffect(CosmeticDrawBuilder arg0) {
        int var264 = 0;
        double var92 = 0.0;
        int var29 = 0;
        if (arg0.enabledState) {
            var92 = arg0.doubleValue;
        } else {
            var92 = 0.0;
        }
        double var22 = var92;
        double var153 = this.elementalAuraSpec.radius();
        double var99 = (0.27 * var153);
        double var42 = (0.35 + var99);
        double var46 = this.elementalAuraSpec.bodyHeight();
        double[] var49 = arg0.computeVector1();
        double var156 = AuraRenderMath.drawGeometryPart(arg0, 3.1, 0.0);
        this.drawGeometryPart(arg0, var42, var156);
        var29 = 0;
        while (true) {
            if (var29 >= 10) break;
            double var104 = ((double) var29);
            double var202 = (GeometryPrimitives.computeDoubleMetric(((var29 * 13) + 1)) - 0.5);
            double var160 = (0.35 * var202);
            double var106 = (var104 + var160);
            double var57 = ((6.283185307179586 * var106) / 10.0);
            double var207 = GeometryPrimitives.computeDoubleMetric(((var29 * 13) + 2));
            double var164 = (0.12 * var207);
            double var112 = (0.94 + var164);
            double var61 = (var42 * var112);
            double var212 = GeometryPrimitives.computeDoubleMetric(((var29 * 13) + 3));
            double var168 = (0.47 * var212);
            double var116 = (0.58 + var168);
            double var65 = (var46 * var116);
            double var217 = GeometryPrimitives.computeDoubleMetric(((var29 * 13) + 4));
            double var172 = (0.3 * var217);
            double var120 = (0.85 + var172);
            double var69 = (0.28 * var120);
            double var126 = Math.sqrt(this.elementalAuraSpec.radius());
            double var71 = (var69 * var126);
            double var182 = Math.cos(var57);
            double var144 = (var61 * var182);
            double var234 = Math.sin(var57);
            double var186 = (var61 * var234);
            int var18 = var29;
            this.drawGeometryPart(arg0, var49, var144, var186, var65, var71, var18, var22, true);
            var29 = (var29 + 1);
        }
        var264 = 0;
        while (true) {
            if (var264 >= 10) break;
            double var132 = (((double) var264) + 0.5);
            double var80 = ((6.283185307179586 * var132) / 10.0);
            double var84 = (var42 * 1.06);
            double var222 = GeometryPrimitives.computeDoubleMetric(((var264 * 17) + 5));
            double var178 = (0.2 * var222);
            double var140 = (0.2 + var178);
            double var88 = (var46 * var140);
            double var190 = Math.cos(var80);
            double var148 = (var84 * var190);
            double var242 = Math.sin(var80);
            double var194 = (var84 * var242);
            double var256 = Math.sqrt(this.elementalAuraSpec.radius());
            double var250 = (0.17 * var256);
            int var259 = (var264 + 40);
            this.drawGeometryPart(arg0, var49, var148, var194, var88, var250, var259, var22, false);
            var264 = (var264 + 1);
        }
        this.drawGeometryPart(arg0, var49, var42, var46, var22);
    }

    private void drawGeometryPart(CosmeticDrawBuilder arg0, double arg1, double arg2) {
        double var40 = (0.1 * arg2);
        double var27 = (0.34 + var40);
        int var31 = ColorMathUtils.computeColorOrIndex7(arg0.counterValue, 0.2);
        int var33 = arg0.counterValue1;
        double var42 = (arg1 - 0.28);
        IGradientColorSampler var55 = (p0, p1) -> FlameAuraRenderer.lambda$c(var33, var31, var27, p0, p1);
        GeometryPrimitives.drawGeometryPart(arg0, 0.02, var42, arg1, 20, var55);
        double var50 = (arg1 + 0.32);
        IGradientColorSampler var57 = (p0, p1) -> FlameAuraRenderer.lambda$b(var31, var33, var27, p0, p1);
        GeometryPrimitives.drawGeometryPart(arg0, 0.02, arg1, var50, 20, var57);
    }

    private void drawGeometryPart(CosmeticDrawBuilder arg0, double[] arg1, double arg2, double arg3, double arg4, double arg5, int arg6, double arg7, boolean arg8) {
        int var50 = 0;
        double var61 = 0.0;
        double var63 = 0.0;
        double var191 = 0.0;
        int var193 = 0;
        int var470 = 0;
        if (arg8) {
            var193 = 4;
        } else {
            var193 = 3;
        }
        int var31 = var193;
        double var71 = (arg2 * arg2);
        double var210 = (arg3 * arg3);
        double var75 = Math.sqrt((var71 + var210));
        double var79 = (arg2 / var75);
        double var83 = (arg3 / var75);
        double var87 = -(var83);
        if (arg0.enabledState) {
            double var328 = (arg7 * 8.0);
            double var424 = (((double) arg6) * 2.1);
            double var332 = Math.sin((var328 + var424));
            double var218 = (0.1 * var332);
            double var95 = (0.86 + var218);
            double var336 = (arg7 * 13.7);
            double var431 = (((double) arg6) * 0.9);
            double var340 = Math.sin((var336 + var431));
            double var222 = (0.06 * var340);
            var191 = (var95 + var222);
        } else {
            var191 = 0.93;
        }
        double var42 = var191;
        double var99 = (arg4 * var42);
        double var311 = (var99 * 0.5);
        double var455 = arg0.computeDoubleMetric();
        double var102 = AuraRenderMath.computeDoubleMetric(arg1, arg2, var311, arg3, var455);
        if (Double.compare(var102, 0.03) <= 0) {
            return;
        }
        double[][] var109 = new double[(var31 + 1)][];
        double[] var112 = new double[(var31 + 1)];
        var50 = 0;
        while (true) {
            if (var50 > var31) break;
            double var116 = ((double) var50);
            double var229 = ((double) var31);
            double var118 = (var116 / var229);
            double var122 = (0.2 * var99);
            double var235 = Math.pow(var118, 1.7);
            double var124 = (var122 * var235);
            double var128 = (-0.14 * var99);
            double var241 = Math.pow(var118, 1.5);
            double var130 = (var128 * var241);
            var61 = 0.0;
            var63 = 0.0;
            if (arg0.enabledState) {
                double var140 = (0.06 * var118);
                double var247 = (arg7 * 3.1);
                double var351 = (((double) arg6) * 1.7);
                double var249 = (var247 + var351);
                double var355 = (var118 * 2.6);
                double var253 = Math.sin((var249 - var355));
                var61 = (var140 * var253);
                double var146 = (0.05 * var118);
                double var259 = (arg7 * 2.3);
                double var362 = (((double) arg6) * 2.3);
                double var261 = (var259 + var362);
                double var366 = (var118 * 2.1);
                double var265 = Math.sin((var261 - var366));
                var63 = (var146 * var265);
            }
            double var368 = var61;
            double var269 = (var124 + var368);
            double var152 = (var87 * var269);
            double var441 = var63;
            double var372 = (var130 + var441);
            double var273 = (var79 * var372);
            double var154 = (var152 + var273);
            double var374 = var61;
            double var277 = (var124 + var374);
            double var158 = (var79 * var277);
            double var443 = var63;
            double var378 = (var130 + var443);
            double var281 = (var83 * var378);
            double var160 = (var158 + var281);
            int var197 = var50;
            double var285 = (arg2 + var154);
            double var447 = (var99 * var118);
            double var384 = (0.02 + var447);
            double var451 = (arg3 + var160);
            double[] var287 = GeometryPrimitives.computeVector(var285, var384, var451);
            var109[var197] = var287;
            int var198 = var50;
            double var388 = computeDoubleMetric(var118);
            double var290 = (arg5 * var388);
            var112[var198] = var290;
            var50 = (var50 + 1);
        }
        double[] var166 = GeometryPrimitives.computeVector(var87, 0.0, var79);
        double[] var169 = GeometryPrimitives.computeVector(var79, 0.0, var83);
        this.drawGeometryPart(arg0, var109, var112, var166, var102, 0.85);
        this.drawGeometryPart(arg0, var109, var112, var169, var102, 0.85);
        if (arg8) {
            double[][] var176 = new double[(3 + 1)][];
            double[] var179 = new double[(3 + 1)];
            var470 = 0;
            while (true) {
                if (var470 > 3) break;
                double var183 = ((double) var470);
                double var299 = ((double) 3);
                double var185 = (var183 / var299);
                int var204 = var470;
                double var317 = (var185 * 0.75);
                double var415 = ((double) var31);
                int var323 = ((int) Math.round((var317 * var415)));
                double[] var302 = var109[var323];
                var176[var204] = var302;
                int var205 = var470;
                double var305 = (arg5 * 0.42);
                double var402 = computeDoubleMetric((var185 * 0.8));
                double var307 = (var305 * var402);
                var179[var205] = var307;
                var470 = (var470 + 1);
            }
            this.drawGeometryPart(arg0, var176, var179, var166, var102);
            this.drawGeometryPart(arg0, var176, var179, var169, var102);
        }
    }

    private static double computeDoubleMetric(double arg0) {
        if (Double.compare(arg0, 0.28) < 0) {
            double var43 = Math.sin((((arg0 / 0.28) * 3.141592653589793) / 2.0));
            double var31 = (0.4 * var43);
            return (0.6 + var31);
        }
        double var25 = ((1.0 - arg0) / 0.72);
        return Math.pow(Math.max(0.0, var25), 0.9);
    }

    private void drawGeometryPart(CosmeticDrawBuilder arg0, double[][] arg1, double[] arg2, double[] arg3, double arg4, double arg5) {
        int var25 = 0;
        int var34 = arg1.length;
        int var37 = ColorMathUtils.computeColorOrIndex7(arg0.counterValue, 0.15);
        int var39 = arg0.counterValue1;
        double[][] var41 = new double[var34][];
        double[][] var43 = new double[var34][];
        applyStep(arg1, arg2, arg3, var41, var43);
        var25 = 0;
        while (true) {
            int var46 = var25;
            int var70 = (var34 - 1);
            if (var46 >= var70) break;
            double var48 = ((double) var25);
            double var86 = (((double) var34) - 1.0);
            double var50 = (var48 / var86);
            double var54 = ((double) (var25 + 1));
            double var91 = (((double) var34) - 1.0);
            double var56 = (var54 / var91);
            int var59 = computeColorOrIndex(var37, var39, var50, arg5, arg4);
            int var61 = computeColorOrIndex(var37, var39, var56, arg5, arg4);
            int var63 = ColorMathUtils.computeColorOrIndex(var59, 0.22);
            int var65 = ColorMathUtils.computeColorOrIndex(var61, 0.22);
            int var97 = var25;
            double[] var79 = var41[var97];
            int var103 = var25;
            double[] var99 = arg1[var103];
            int var118 = (var25 + 1);
            double[] var105 = arg1[var118];
            int var126 = (var25 + 1);
            double[] var120 = var41[var126];
            arg0.applyStep(var79, var99, var105, var120, var63, var59, var61, var65);
            int var100 = var25;
            double[] var81 = arg1[var100];
            int var106 = var25;
            double[] var102 = var43[var106];
            int var122 = (var25 + 1);
            double[] var108 = var43[var122];
            int var130 = (var25 + 1);
            double[] var124 = arg1[var130];
            arg0.applyStep(var81, var102, var108, var124, var59, var63, var65, var61);
            var25 = (var25 + 1);
        }
    }

    private void drawGeometryPart(CosmeticDrawBuilder arg0, double[][] arg1, double[] arg2, double[] arg3, double arg4) {
        int var23 = 0;
        int var30 = arg1.length;
        double[][] var32 = new double[var30][];
        double[][] var34 = new double[var30][];
        applyStep(arg1, arg2, arg3, var32, var34);
        int var38 = ColorMathUtils.computeColorOrIndex7(arg0.counterValue, 0.75);
        int var41 = ColorMathUtils.computeColorOrIndex7(arg0.counterValue, 0.25);
        var23 = 0;
        while (true) {
            int var43 = var23;
            int var69 = (var30 - 1);
            if (var43 >= var69) break;
            double var45 = ((double) var23);
            double var91 = (((double) var30) - 1.0);
            double var47 = (var45 / var91);
            double var51 = ((double) (var23 + 1));
            double var96 = (((double) var30) - 1.0);
            double var53 = (var51 / var96);
            int var56 = ColorMathUtils.computeColorOrIndex(var38, var41, var47);
            double var124 = Math.pow(var47, 1.4);
            double var107 = (1.0 - var124);
            double var74 = (0.95 * var107);
            int var58 = AuraRenderMath.computeColorOrIndex(ColorMathUtils.computeColorOrIndex5(var56, var74), arg4);
            int var60 = ColorMathUtils.computeColorOrIndex(var38, var41, var53);
            double var128 = Math.pow(var53, 1.4);
            double var111 = (1.0 - var128);
            double var81 = (0.95 * var111);
            int var62 = AuraRenderMath.computeColorOrIndex(ColorMathUtils.computeColorOrIndex5(var60, var81), arg4);
            int var102 = var23;
            double[] var86 = var32[var102];
            int var113 = var23;
            double[] var104 = var34[var113];
            int var121 = (var23 + 1);
            double[] var115 = var34[var121];
            int var132 = (var23 + 1);
            double[] var123 = var32[var132];
            arg0.applyStep(var86, var104, var115, var123, var58, var58, var62, var62);
            var23 = (var23 + 1);
        }
    }

    private static int computeColorOrIndex(int arg0, int arg1, double arg2, double arg3, double arg4) {
        double var25 = GeometryPrimitives.computeDoubleMetric(((arg2 - 0.06) / 0.62));
        int var28 = ColorMathUtils.computeColorOrIndex(arg0, arg1, var25);
        double var43 = Math.pow(arg2, 3.2);
        double var41 = (1.0 - var43);
        double var33 = (arg3 * var41);
        return AuraRenderMath.computeColorOrIndex(ColorMathUtils.computeColorOrIndex5(var28, var33), arg4);
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

    private void drawGeometryPart(CosmeticDrawBuilder arg0, double[] arg1, double arg2, double arg3, double arg4) {
        int var23 = 0;
        double var56 = 0.0;
        var23 = 0;
        while (true) {
            if (var23 < 16) {
                double var213 = GeometryPrimitives.computeDoubleMetric(((var23 * 11) + 1));
                double var123 = (2.0 * var213);
                double var46 = (2.2 + var123);
                if (arg0.enabledState) {
                    double var52 = (arg4 / var46);
                    double var130 = GeometryPrimitives.computeDoubleMetric(((var23 * 11) + 2));
                    var56 = AuraRenderMath.computeDoubleMetric((var52 + var130));
                }
            } else {
                return;
                var56 = GeometryPrimitives.computeDoubleMetric(((var23 * 11) + 2));
            }
            double var26 = var56;
            double var135 = GeometryPrimitives.computeDoubleMetric(((var23 * 11) + 3));
            double var60 = (6.283185307179586 * var135);
            double var139 = (var26 * 1.4);
            double var62 = (var60 + var139);
            double var276 = GeometryPrimitives.computeDoubleMetric(((var23 * 11) + 4));
            double var219 = (0.55 * var276);
            double var143 = (0.55 + var219);
            double var66 = (arg2 * var143);
            double var223 = (0.25 * var26);
            double var147 = (1.0 - var223);
            double var68 = (var66 * var147);
            double var151 = Math.cos(var62);
            double var72 = (var68 * var151);
            double var155 = Math.sin(var62);
            double var76 = (var68 * var155);
            double var227 = (arg3 + 0.35);
            double var159 = (var26 * var227);
            double var80 = (0.02 + var159);
            double var231 = Math.pow(var26, 0.7);
            double var165 = Math.sin((3.141592653589793 * var231));
            double var84 = (0.95 * var165);
            double var338 = arg0.computeDoubleMetric();
            double var168 = AuraRenderMath.computeDoubleMetric(arg1, var72, var80, var76, var338);
            double var86 = (var84 * var168);
            if (Double.compare(var86, 0.04) < 0) {
            } else {
                double var235 = (0.6 * var26);
                double var174 = (1.0 - var235);
                double var93 = (0.028 * var174);
                double var289 = GeometryPrimitives.computeDoubleMetric(((var23 * 11) + 6));
                double var239 = (0.5 * var289);
                double var178 = (0.8 + var239);
                double var95 = (var93 * var178);
                int var99 = ColorMathUtils.computeColorOrIndex7(arg0.counterValue, 0.5);
                int var110 = arg0.counterValue1;
                double var184 = GeometryPrimitives.computeDoubleMetric((var26 * 1.2));
                int var101 = ColorMathUtils.computeColorOrIndex5(ColorMathUtils.computeColorOrIndex(var99, var110, var184), var86);
                double[] var117 = GeometryPrimitives.computeVector((var72 - var95), var80, var76);
                double var293 = (var95 * 1.6);
                double var245 = (var80 - var293);
                double[] var188 = GeometryPrimitives.computeVector(var72, var245, var76);
                double[] var205 = GeometryPrimitives.computeVector((var72 + var95), var80, var76);
                double var326 = (var95 * 1.6);
                double var299 = (var80 + var326);
                double[] var249 = GeometryPrimitives.computeVector(var72, var299, var76);
                arg0.applyStep(var117, var188, var205, var249, var101);
                double var269 = (var76 - var95);
                double[] var120 = GeometryPrimitives.computeVector(var72, var80, var269);
                double var303 = (var95 * 1.6);
                double var252 = (var80 - var303);
                double[] var191 = GeometryPrimitives.computeVector(var72, var252, var76);
                double var322 = (var76 + var95);
                double[] var210 = GeometryPrimitives.computeVector(var72, var80, var322);
                double var334 = (var95 * 1.6);
                double var309 = (var80 + var334);
                double[] var256 = GeometryPrimitives.computeVector(var72, var309, var76);
                arg0.applyStep(var120, var191, var210, var256, var101);
            }
            var23 = (var23 + 1);
        }
    }

}
