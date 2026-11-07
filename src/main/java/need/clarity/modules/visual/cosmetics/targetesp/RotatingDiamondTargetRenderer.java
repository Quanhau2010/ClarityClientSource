package need.clarity.modules.visual.cosmetics.targetesp;

import need.clarity.modules.visual.cosmetics.ColorMathUtils;
import need.clarity.modules.visual.cosmetics.CosmeticDrawBuilder;
import need.clarity.modules.visual.cosmetics.GeometryPrimitives;
import need.clarity.modules.visual.cosmetics.ICosmeticEffectRenderer;

public final class RotatingDiamondTargetRenderer implements ICosmeticEffectRenderer {
    private static final int CONST_INT = 6;
    private final TargetEspRenderSpec targetEspRenderSpec;

    public RotatingDiamondTargetRenderer() {
        TargetEspRenderSpec var2 = TargetEspRenderSpec.evaluate();
        this(var2);
    }

    public RotatingDiamondTargetRenderer(TargetEspRenderSpec arg0) {
        this.targetEspRenderSpec = arg0;
    }

    public void renderEffect(CosmeticDrawBuilder arg0) {
        double var44 = 0.0;
        double var117 = 0.0;
        double var121 = 0.0;
        if (arg0.enabledState) {
            var44 = arg0.doubleValue;
        } else {
            var44 = 0.0;
        }
        double var21 = var44;
        double var48 = this.targetEspRenderSpec.age();
        double var52 = this.targetEspRenderSpec.computeDoubleMetric1();
        double var56 = (0.16 * var52);
        double var60 = (0.3 * var52);
        double var64 = (0.4 * var52);
        if (arg0.enabledState) {
            double var114 = (0.045 * var52);
            double var179 = Math.sin((var21 * 2.1));
            var117 = (var114 * var179);
        } else {
            var117 = 0.0;
        }
        double var33 = var117;
        double var70 = this.targetEspRenderSpec.height();
        double var145 = (0.12 * var52);
        double var78 = (((var70 + var145) + var64) + var33);
        double var157 = (((1.0 - var48) * 0.9) * var52);
        double var80 = (var78 + var157);
        double var161 = this.targetEspRenderSpec.phase();
        double var84 = (6.283185307179586 * var161);
        CosmeticDrawBuilder var89 = arg0.drawGeometryPart();
        CosmeticDrawBuilder var91 = arg0.drawGeometryPart(0.0, var80, 0.0);
        if (arg0.enabledState) {
            double var189 = Math.sin((var21 * 1.4));
            var121 = (0.1 * var189);
        } else {
            var121 = 0.0;
        }
        CosmeticDrawBuilder var93 = arg0.drawGeometryPart4(var121);
        this.drawGeometryPart(arg0, var52, var56, var64, var80, var48);
        CosmeticDrawBuilder var96 = arg0.drawGeometryPart();
        CosmeticDrawBuilder var98 = arg0.drawGeometryPart2(var84);
        this.drawGeometryPart(arg0, var56, var60, var64, var48);
        CosmeticDrawBuilder var101 = arg0.drawGeometryPart1();
        CosmeticDrawBuilder var103 = arg0.drawGeometryPart();
        double var133 = ((-(var84) * 1.7) + 0.5);
        CosmeticDrawBuilder var105 = arg0.drawGeometryPart2(var133);
        double var169 = (var56 * 0.46);
        double var205 = (var60 * 0.5);
        double var219 = (var64 * 0.5);
        this.drawGeometryPart1(arg0, var169, var205, var219, var48);
        CosmeticDrawBuilder var108 = arg0.drawGeometryPart1();
        this.drawGeometryPart(arg0, var21, var52, var56, var60, var64, var48);
        CosmeticDrawBuilder var111 = arg0.drawGeometryPart1();
    }

    private static double[][] computeMatrix(double arg0, double arg1) {
        int var14 = 0;
        double[][] var17 = new double[6][];
        var14 = 0;
        while (true) {
            if (var14 >= 6) break;
            double var36 = ((double) var14);
            double var29 = ((6.283185307179586 * var36) / 6.0);
            double var22 = (arg1 + var29);
            int var26 = var14;
            double var42 = Math.cos(var22);
            double var33 = (arg0 * var42);
            double var48 = Math.sin(var22);
            double var46 = (arg0 * var48);
            double[] var35 = GeometryPrimitives.computeVector(var33, 0.0, var46);
            var17[var26] = var35;
            var14 = (var14 + 1);
        }
        return var17;
    }

    private void drawGeometryPart(CosmeticDrawBuilder arg0, double arg1, double arg2, double arg3, double arg4) {
        int var33 = 0;
        int var37 = 0;
        double[][] var39 = computeMatrix(arg1, 0.0);
        double[] var42 = GeometryPrimitives.computeVector(0.0, arg2, 0.0);
        double var108 = -(arg3);
        double[] var45 = GeometryPrimitives.computeVector(0.0, var108, 0.0);
        int var47 = arg0.counterValue;
        int var49 = arg0.counterValue1;
        int var77 = arg0.counterValue;
        int var50 = ColorMathUtils.computeColorOrIndex(var49, var77, 0.3);
        var33 = 0;
        while (true) {
            if (var33 >= 6) break;
            int var74 = ((var33 + 1) % 6);
            int var138 = var33;
            double[] var124 = var39[var138];
            double[] var140 = var39[var74];
            int var161 = ColorMathUtils.computeColorOrIndex7(var47, 0.55);
            int var175 = var33;
            this.drawGeometryPart(arg0, var124, var140, var42, var47, var161, var175, arg4, false);
            double[] var126 = var39[var74];
            int var148 = var33;
            double[] var143 = var39[var148];
            int var163 = ColorMathUtils.computeColorOrIndex7(var50, 0.5);
            int var178 = var33;
            this.drawGeometryPart(arg0, var126, var143, var45, var50, var163, var178, arg4, true);
            var33 = (var33 + 1);
        }
        int var55 = ColorMathUtils.computeColorOrIndex7(arg0.counterValue, 0.75);
        double var83 = (0.92 * arg4);
        int var56 = ColorMathUtils.computeColorOrIndex5(var55, var83);
        int var58 = ColorMathUtils.computeColorOrIndex7(var50, 0.65);
        double var89 = (0.85 * arg4);
        int var59 = ColorMathUtils.computeColorOrIndex5(var58, var89);
        double var114 = this.targetEspRenderSpec.computeDoubleMetric1();
        double var62 = (0.0065 * var114);
        var37 = 0;
        while (true) {
            if (var37 >= 6) break;
            int var68 = ((var37 + 1) % 6);
            int var116 = var37;
            double[] var95 = var39[var116];
            double var164 = (0.98 * arg4);
            int var156 = ColorMathUtils.computeColorOrIndex5(var56, var164);
            TargetEspMathUtils.drawGeometryPart(arg0, var95, var42, var62, var56, var156, false);
            int var118 = var37;
            double[] var97 = var39[var118];
            double var169 = (0.98 * arg4);
            int var158 = ColorMathUtils.computeColorOrIndex5(var59, var169);
            TargetEspMathUtils.drawGeometryPart(arg0, var97, var45, var62, var59, var158, false);
            int var120 = var37;
            double[] var99 = var39[var120];
            double[] var122 = var39[var68];
            double var136 = (var62 * 1.2);
            TargetEspMathUtils.drawGeometryPart(arg0, var99, var122, var136, var56, var56, false);
            var37 = (var37 + 1);
        }
    }

    private void drawGeometryPart(CosmeticDrawBuilder arg0, double[] arg1, double[] arg2, double[] arg3, int arg4, int arg5, int arg6, double arg7, boolean arg8) {
        int var172 = 0;
        double var30 = arg1[0];
        double var85 = arg2[0];
        double var32 = (var30 + var85);
        double var88 = arg3[0];
        double var36 = ((var32 + var88) / 3.0);
        double var93 = arg1[1];
        double var135 = arg2[1];
        double var95 = (var93 + var135);
        double var138 = arg3[1];
        double var99 = ((var95 + var138) / 3.0);
        double var143 = arg1[2];
        double var173 = arg2[2];
        double var145 = (var143 + var173);
        double var176 = arg3[2];
        double var149 = ((var145 + var176) / 3.0);
        double[] var38 = GeometryPrimitives.computeVector(var36, var99, var149);
        double[] var40 = GeometryPrimitives.computeVector(arg2, arg1);
        double[] var70 = GeometryPrimitives.computeVector(arg3, arg1);
        double[] var41 = GeometryPrimitives.computeVector1(var40, var70);
        double var45 = Math.pow(arg0.computeDoubleMetric(var38, var41), 6.0);
        int var152 = (arg6 * 5);
        if (arg8) {
            var172 = 2;
        } else {
            var172 = 0;
        }
        double var155 = ((double) ((var152 + var172) % 6));
        double var109 = ((0.3 * var155) / 5.0);
        double var49 = (0.68 + var109);
        int var53 = ColorMathUtils.computeColorOrIndex6(ColorMathUtils.computeColorOrIndex(arg4, arg5, 0.15), var49);
        int var76 = ColorMathUtils.computeColorOrIndex7(arg4, 0.85);
        double var117 = (0.55 * var45);
        int var55 = ColorMathUtils.computeColorOrIndex(var53, var76, var117);
        double var121 = (0.22 * var45);
        double var60 = ((0.7 + var121) * arg7);
        int var63 = ColorMathUtils.computeColorOrIndex5(var55, var60);
        int var65 = ColorMathUtils.computeColorOrIndex(var55, arg5, 0.65);
        double var132 = (var60 + 0.12);
        double var82 = Math.min(1.0, var132);
        int var66 = ColorMathUtils.computeColorOrIndex5(var65, var82);
        arg0.applyStep(arg1, arg2, arg3, var63, var63, var66);
    }

    private void drawGeometryPart1(CosmeticDrawBuilder arg0, double arg1, double arg2, double arg3, double arg4) {
        double var133 = 0.0;
        int var26 = 0;
        int var30 = 0;
        double[][] var34 = new double[4][];
        var26 = 0;
        while (true) {
            if (var26 >= 4) break;
            double var104 = ((double) var26);
            double var66 = ((6.283185307179586 * var104) / 4.0);
            int var88 = var26;
            double var129 = Math.cos(var66);
            double var110 = (arg1 * var129);
            double var155 = Math.sin(var66);
            double var151 = (arg1 * var155);
            double[] var112 = GeometryPrimitives.computeVector(var110, 0.0, var151);
            var34[var88] = var112;
            var26 = (var26 + 1);
        }
        double[] var39 = GeometryPrimitives.computeVector(0.0, arg2, 0.0);
        double var91 = -(arg3);
        double[] var42 = GeometryPrimitives.computeVector(0.0, var91, 0.0);
        int var45 = ColorMathUtils.computeColorOrIndex7(arg0.counterValue, 0.85);
        double var73 = (0.95 * arg4);
        int var46 = ColorMathUtils.computeColorOrIndex5(var45, var73);
        int var49 = ColorMathUtils.computeColorOrIndex7(arg0.counterValue1, 0.75);
        double var79 = (0.9 * arg4);
        int var50 = ColorMathUtils.computeColorOrIndex5(var49, var79);
        var30 = 0;
        while (true) {
            if (var30 >= 4) break;
            int var55 = ((var30 + 1) & 3);
            if ((var30 & 1) == 0) {
                var133 = 1.0;
            } else {
                var133 = 0.6;
            }
            double var95 = (0.25 * var133);
            double var58 = (0.75 + var95);
            int var97 = var30;
            double[] var85 = var34[var97];
            double[] var99 = var34[var55];
            int var124 = ColorMathUtils.computeColorOrIndex6(var46, var58);
            int var138 = ColorMathUtils.computeColorOrIndex6(var46, var58);
            int var144 = ColorMathUtils.computeColorOrIndex7(var46, 0.4);
            arg0.applyStep(var85, var99, var39, var124, var138, var144);
            double[] var87 = var34[var55];
            int var117 = var30;
            double[] var102 = var34[var117];
            int var126 = ColorMathUtils.computeColorOrIndex6(var50, var58);
            int var142 = ColorMathUtils.computeColorOrIndex6(var50, var58);
            int var148 = ColorMathUtils.computeColorOrIndex7(var50, 0.4);
            arg0.applyStep(var87, var102, var42, var126, var142, var148);
            var30 = (var30 + 1);
        }
    }

    private void drawGeometryPart(CosmeticDrawBuilder arg0, double arg1, double arg2, double arg3, double arg4, double arg5) {
        double var39 = -(arg3);
        double var84 = this.targetEspRenderSpec.height();
        double var47 = (-((arg4 - var84)) + 0.02);
        double var90 = (var39 - 0.05);
        if (Double.compare(var47, var90) > 0) {
            return;
        }
        int var54 = ColorMathUtils.computeColorOrIndex7(arg0.counterValue, 0.4);
        double var71 = (0.3 * arg5);
        int var55 = ColorMathUtils.computeColorOrIndex5(var54, var71);
        int var58 = ColorMathUtils.computeColorOrIndex5(arg0.counterValue1, 0.0);
        double var61 = (arg2 * 0.1);
        double var65 = (arg2 * 0.95);
        double[] var79 = GeometryPrimitives.computeVector(-(var61), var39, 0.0);
        double[] var98 = GeometryPrimitives.computeVector(var61, var39, 0.0);
        double[] var106 = GeometryPrimitives.computeVector(var65, var47, 0.0);
        double[] var118 = GeometryPrimitives.computeVector(-(var65), var47, 0.0);
        arg0.applyStep(var79, var98, var106, var118, var55, var55, var58, var58);
        double var129 = -(var61);
        double[] var82 = GeometryPrimitives.computeVector(0.0, var39, var129);
        double[] var101 = GeometryPrimitives.computeVector(0.0, var39, var61);
        double[] var111 = GeometryPrimitives.computeVector(0.0, var47, var65);
        double var149 = -(var65);
        double[] var123 = GeometryPrimitives.computeVector(0.0, var47, var149);
        arg0.applyStep(var82, var101, var111, var123, var55, var55, var58, var58);
    }

    private void drawGeometryPart(CosmeticDrawBuilder arg0, double arg1, double arg2, double arg3, double arg4, double arg5, double arg6) {
        double var259 = 0.0;
        double var146 = 0.0;
        double var148 = 0.0;
        int var31 = 0;
        double var58 = 0.0;
        int var81 = 0;
        var31 = 0;
        while (true) {
            if (var31 >= 4) break;
            double var107 = (((double) var31) / 4.0);
            if (arg0.enabledState) {
                var259 = arg1;
            } else {
                var259 = 0.0;
            }
            double var172 = (0.11 * var259);
            double var225 = this.targetEspRenderSpec.computeDoubleMetric();
            double var174 = (var172 * var225);
            double var109 = (var107 + var174);
            double var48 = (6.283185307179586 * var109);
            double var114 = (((double) var31) * 0.7);
            double var50 = (var48 + var114);
            double var54 = (arg3 * 2.15);
            if ((var31 % 2) == 0) {
                var58 = 0.55;
            } else {
                var58 = -0.45;
            }
            if ((var31 % 2) == 0) {
                var148 = arg4;
            } else {
                var148 = arg5;
            }
            double var60 = (var58 * var148);
            if (arg0.enabledState) {
                double var124 = (0.05 * arg2);
                double var182 = (arg1 * 1.7);
                double var232 = (((double) var31) * 1.9);
                double var186 = Math.sin((var182 + var232));
                var146 = (var124 * var186);
            } else {
                var146 = 0.0;
            }
            double var62 = (var60 + var146);
            double var66 = (0.032 * arg2);
            double var292 = (((double) var31) * 1.7);
            double var235 = TargetEspMathUtils.drawGeometryPart(arg0, 3.1, var292);
            double var190 = (0.5 * var235);
            double var130 = (0.7 + var190);
            double var68 = (var66 * var130);
            double var134 = Math.cos(var50);
            double var72 = (var54 * var134);
            double var138 = Math.sin(var50);
            double var76 = (var54 * var138);
            if ((var31 % 2) == 0) {
                var81 = arg0.counterValue;
            } else {
                var81 = arg0.counterValue1;
            }
            int var82 = ColorMathUtils.computeColorOrIndex7(var81, 0.7);
            double var272 = (((double) var31) * 1.7);
            double var206 = TargetEspMathUtils.drawGeometryPart(arg0, 3.1, var272);
            double var152 = (0.4 * var206);
            double var95 = ((0.55 + var152) * arg6);
            int var83 = ColorMathUtils.computeColorOrIndex5(var82, var95);
            double[] var101 = GeometryPrimitives.computeVector((var72 - var68), var62, var76);
            double var241 = (var68 * 1.5);
            double var194 = (var62 + var241);
            double[] var142 = GeometryPrimitives.computeVector(var72, var194, var76);
            double[] var164 = GeometryPrimitives.computeVector((var72 + var68), var62, var76);
            double var278 = (var68 * 1.5);
            double var247 = (var62 - var278);
            double[] var198 = GeometryPrimitives.computeVector(var72, var247, var76);
            arg0.applyStep(var101, var142, var164, var198, var83);
            double var217 = (var76 - var68);
            double[] var104 = GeometryPrimitives.computeVector(var72, var62, var217);
            double var251 = (var68 * 1.5);
            double var201 = (var62 + var251);
            double[] var145 = GeometryPrimitives.computeVector(var72, var201, var76);
            double var267 = (var76 + var68);
            double[] var169 = GeometryPrimitives.computeVector(var72, var62, var267);
            double var286 = (var68 * 1.5);
            double var257 = (var62 - var286);
            double[] var205 = GeometryPrimitives.computeVector(var72, var257, var76);
            arg0.applyStep(var104, var145, var169, var205, var83);
            var31 = (var31 + 1);
        }
    }

}
