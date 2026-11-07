package need.clarity.modules.visual.cosmetics.hats;

import need.clarity.modules.visual.cosmetics.ColorMathUtils;
import need.clarity.modules.visual.cosmetics.CosmeticDrawBuilder;
import need.clarity.modules.visual.cosmetics.GeometryPrimitives;
import need.clarity.modules.visual.cosmetics.IGradientColorSampler;

public final class CrownJewelGeometry {
    private static final double CONST_DBL = 6.283185307179586;

    private CrownJewelGeometry() {
    }

    static void drawGeometryPart(CosmeticDrawBuilder arg0, double arg1, double arg2, double arg3, double arg4, int arg5, int arg6, int arg7, double arg8) {
        int var33 = 0;
        int var40 = 0;
        double[] var82 = null;
        double var92 = 0.0;
        double var223 = 0.0;
        if (arg0.enabledState) {
            var92 = arg0.doubleValue;
        } else {
            var92 = 0.0;
        }
        double var29 = var92;
        double[] var48 = GeometryPrimitives.computeVector(0.0, arg3, 0.0);
        double var103 = -(arg4);
        double[] var51 = GeometryPrimitives.computeVector(0.0, var103, 0.0);
        var33 = 0;
        while (true) {
            if (var33 >= arg5) break;
            double var55 = ((double) var33);
            double var106 = ((double) arg5);
            double var59 = ((var55 / var106) * 6.283185307179586);
            double var63 = ((double) (var33 + 1));
            double var111 = ((double) arg5);
            double var67 = ((var63 / var111) * 6.283185307179586);
            double var117 = Math.cos(var59);
            double var71 = (arg1 * var117);
            double var206 = Math.sin(var59);
            double var165 = (arg2 * var206);
            double[] var73 = GeometryPrimitives.computeVector(var71, 0.0, var165);
            double var123 = Math.cos(var67);
            double var76 = (arg1 * var123);
            double var210 = Math.sin(var67);
            double var169 = (arg2 * var210);
            double[] var78 = GeometryPrimitives.computeVector(var76, 0.0, var169);
            var40 = 0;
            while (true) {
                if (var40 < 2) {
                    if (var40 == 0) {
                        var82 = var48;
                    }
                } else {
                    var33 = (var33 + 1);
                }
            }
        }
        var82 = var51;
        double[] var41 = var82;
        double[] var84 = GeometryPrimitives.computeVector(var78, var73);
        double[] var98 = GeometryPrimitives.computeVector(var41, var73);
        double[] var85 = GeometryPrimitives.computeVector1(var84, var98);
        double[] var87 = new double[3];
        double var133 = var73[0];
        double var177 = var78[0];
        double var135 = (var133 + var177);
        double var180 = var41[0];
        double var139 = ((var135 + var180) / 3.0);
        var87[0] = var139;
        double var142 = var73[1];
        double var185 = var78[1];
        double var144 = (var142 + var185);
        double var188 = var41[1];
        double var148 = ((var144 + var188) / 3.0);
        var87[1] = var148;
        double var151 = var73[2];
        double var193 = var78[2];
        double var153 = (var151 + var193);
        double var196 = var41[2];
        double var157 = ((var153 + var196) / 3.0);
        var87[2] = var157;
        int var26 = var33;
        int var226 = (var40 * arg5);
        int var225 = (var26 + var226);
        int var89 = drawGeometryPart(arg0, var87, var85, arg6, arg7, var29, arg8, var225);
        int var176 = ColorMathUtils.computeColorOrIndex6(var89, 0.88);
        int var205 = ColorMathUtils.computeColorOrIndex6(var89, 0.88);
        if (var40 == 0) {
            var223 = 1.18;
        } else {
            var223 = 1.04;
        }
        int var221 = ColorMathUtils.computeColorOrIndex6(var89, var223);
        arg0.applyStep(var73, var78, var41, var176, var205, var221);
        var40 = (var40 + 1);
    }

    static void drawGeometryPart(CosmeticDrawBuilder arg0, double arg1, double arg2, double arg3, int arg4, int arg5, int arg6, double arg7) {
        drawGeometryPart(arg0, arg1, arg1, arg2, arg3, arg4, arg5, arg6, arg7);
    }

    private static int drawGeometryPart(CosmeticDrawBuilder arg0, double[] arg1, double[] arg2, int arg3, int arg4, double arg5, double arg6, int arg7) {
        double var36 = arg0.computeDoubleMetric(arg1, arg2);
        double var75 = (0.65 * var36);
        double var40 = (0.5 + var75);
        double var44 = (1.0 - var36);
        double var81 = (1.0 - var36);
        double var46 = (var44 * var81);
        int var49 = ColorMathUtils.computeColorOrIndex6(arg3, var40);
        int var66 = ColorMathUtils.computeColorOrIndex7(arg4, 0.35);
        double var87 = (0.5 * var46);
        int var50 = ColorMathUtils.computeColorOrIndex(var49, var66, var87);
        double var57 = GeometryPrimitives.computeDoubleMetric(((var36 - 0.45) / 0.4));
        double var111 = ((arg5 * 3.1) + arg6);
        double var122 = (((double) arg7) * 2.1);
        double var115 = Math.sin((var111 + var122));
        double var107 = (0.5 * var115);
        double var105 = (0.5 + var107);
        double var103 = (0.65 * var105);
        double var95 = (0.35 + var103);
        double var71 = ColorMathUtils.computeDoubleMetric(((var57 * var95) * 0.85));
        return ColorMathUtils.computeColorOrIndex7(var50, var71);
    }

    static void drawGeometryPart(CosmeticDrawBuilder arg0, double[] arg1, double arg2, int arg3, double arg4) {
        int var71 = 0;
        double[] var21 = null;
        double[] var22 = null;
        if (Double.compare(arg4, 0.04) < 0) {
            return;
        }
        double[] var32 = new double[3];
        var32[0] = 1.0;
        var32[1] = 0.0;
        var32[2] = 0.0;
        var21 = var32;
        double[] var34 = new double[3];
        var34[0] = 0.0;
        var34[1] = 1.0;
        var34[2] = 0.0;
        var22 = var34;
        double[] var36 = arg0.computeVector1();
        if (var36 != null) {
            double[] var72 = arg1;
            double[] var40 = GeometryPrimitives.computeVector(GeometryPrimitives.computeVector(var36, var72));
            if (Double.compare(Math.abs(var40[1]), 0.95) > 0) {
                var71 = new double[3];
                var71[0] = 1.0;
                var71[1] = 0.0;
                var71[2] = 0.0;
            } else {
                var71 = new double[3];
                var71[0] = 0.0;
                var71[1] = 1.0;
                var71[2] = 0.0;
            }
            var21 = GeometryPrimitives.computeVector(GeometryPrimitives.computeVector1(var71, var40));
            double[] var75 = var21;
            var22 = GeometryPrimitives.computeVector1(var40, var75);
            double[] var53 = arg1;
            double var81 = ((var40[0] * arg2) * 0.2);
            double var141 = ((var40[1] * arg2) * 0.2);
            double var168 = ((var40[2] * arg2) * 0.2);
            arg1 = GeometryPrimitives.computeVector(var53, var81, var141, var168);
        }
        double var153 = (0.65 * arg4);
        double var107 = (0.35 + var153);
        double var57 = (arg2 * var107);
        double var61 = (var57 * 0.13);
        int var64 = ColorMathUtils.computeColorOrIndex7(arg3, 0.85);
        double var87 = ColorMathUtils.computeDoubleMetric(arg4);
        int var65 = ColorMathUtils.computeColorOrIndex5(var64, var87);
        int var67 = ColorMathUtils.computeColorOrIndex5(arg3, 0.0);
        double[] var91 = arg1;
        double[] var111 = var21;
        double[] var143 = var22;
        drawGeometryPart(arg0, var91, var111, var143, var57, var61, var65, var67);
        double[] var92 = arg1;
        double[] var112 = var22;
        double[] var144 = var21;
        double var159 = (var57 * 0.8);
        drawGeometryPart(arg0, var92, var112, var144, var159, var61, var65, var67);
    }

    private static void drawGeometryPart(CosmeticDrawBuilder arg0, double[] arg1, double[] arg2, double[] arg3, double arg4, double arg5, int arg6, int arg7) {
        double var31 = (arg2[0] * arg4);
        double var45 = (arg2[1] * arg4);
        double var76 = (arg2[2] * arg4);
        double[] var28 = GeometryPrimitives.computeVector(arg1, var31, var45, var76);
        double var38 = (arg3[0] * arg5);
        double var62 = (arg3[1] * arg5);
        double var95 = (arg3[2] * arg5);
        double[] var34 = GeometryPrimitives.computeVector(arg1, var38, var62, var95);
        double var53 = (-(arg2[0]) * arg4);
        double var86 = (-(arg2[1]) * arg4);
        double var116 = (-(arg2[2]) * arg4);
        double[] var41 = GeometryPrimitives.computeVector(arg1, var53, var86, var116);
        double var70 = (-(arg3[0]) * arg5);
        double var105 = (-(arg3[1]) * arg5);
        double var128 = (-(arg3[2]) * arg5);
        double[] var56 = GeometryPrimitives.computeVector(arg1, var70, var105, var128);
        arg0.applyStep(var28, var34, var41, var56, arg7, arg6, arg7, arg6);
    }

    static void drawGeometryPart(CosmeticDrawBuilder arg0, double[] arg1, double arg2, int arg3) {
        double[] var25 = null;
        double[] var26 = null;
        int var62 = 0;
        if (ColorMathUtils.computeColorOrIndex(arg3) >= 6) {
            if (Double.compare(arg2, 0.0) <= 0) {
            }
        }
        return;
        double[] var35 = new double[3];
        var35[0] = 1.0;
        var35[1] = 0.0;
        var35[2] = 0.0;
        var25 = var35;
        double[] var37 = new double[3];
        var37[0] = 0.0;
        var37[1] = 1.0;
        var37[2] = 0.0;
        var26 = var37;
        double[] var39 = arg0.computeVector1();
        if (var39 != null) {
            double[] var43 = GeometryPrimitives.computeVector(GeometryPrimitives.computeVector(var39, arg1));
            if (Double.compare(Math.abs(var43[1]), 0.95) > 0) {
                var62 = new double[3];
                var62[0] = 1.0;
                var62[1] = 0.0;
                var62[2] = 0.0;
            } else {
                var62 = new double[3];
                var62[0] = 0.0;
                var62[1] = 1.0;
                var62[2] = 0.0;
            }
            var25 = GeometryPrimitives.computeVector(GeometryPrimitives.computeVector1(var62, var43));
            double[] var66 = var25;
            var26 = GeometryPrimitives.computeVector1(var43, var66);
        }
        double var58 = (arg2 * 1.35);
        double var85 = (var26[0] * var58);
        double var125 = (var26[1] * var58);
        double var156 = (var26[2] * var58);
        double[] var68 = GeometryPrimitives.computeVector(arg1, var85, var125, var156);
        double var112 = (var25[0] * arg2);
        double var142 = (var25[1] * arg2);
        double var174 = (var25[2] * arg2);
        double[] var88 = GeometryPrimitives.computeVector(arg1, var112, var142, var174);
        double var133 = (-(var26[0]) * var58);
        double var166 = (-(var26[1]) * var58);
        double var194 = (-(var26[2]) * var58);
        double[] var115 = GeometryPrimitives.computeVector(arg1, var133, var166, var194);
        double var150 = (-(var25[0]) * arg2);
        double var184 = (-(var25[1]) * arg2);
        double var205 = (-(var25[2]) * arg2);
        double[] var136 = GeometryPrimitives.computeVector(arg1, var150, var184, var205);
        arg0.applyStep(var68, var88, var115, var136, arg3);
    }

    static double drawGeometryPart(CosmeticDrawBuilder arg0, double arg1, double arg2) {
        double var16 = 0.0;
        if (arg0.enabledState) {
            var16 = HatWaveMath.computeDoubleMetric(arg0.doubleValue, arg1, arg2, 0.16);
        } else {
            var16 = 0.0;
        }
        return var16;
    }

    static void drawGeometryPart(CosmeticDrawBuilder arg0, double arg1, double arg2, double arg3, double arg4, int arg5) {
        int var25 = ColorMathUtils.computeColorOrIndex7(arg5, 0.62);
        int var27 = ColorMathUtils.computeColorOrIndex7(arg5, 0.93);
        IGradientColorSampler var33 = (p0, p1) -> CrownJewelGeometry.lambda$a(var25, var27, p0, p1);
        GeometryPrimitives.drawGeometryPart(arg0, arg1, arg2, arg3, arg4, 5, 3, var33);
    }

    static int computeColorOrIndex(int arg0, double arg1, double arg2, double arg3, double arg4) {
        double var52 = (arg3 * arg4);
        double var31 = (HatWaveMath.computeDoubleMetric(((arg1 - var52) + 0.5)) - 0.5);
        double var37 = -((var31 / 0.09));
        double var62 = (var31 / 0.09);
        double var41 = Math.exp((var37 * var62));
        double var64 = (0.3 * arg2);
        double var46 = (0.74 + var64);
        int var44 = ColorMathUtils.computeColorOrIndex6(arg0, var46);
        double var50 = (0.42 * var41);
        return ColorMathUtils.computeColorOrIndex7(var44, var50);
    }

    static double[] computeVector(double arg0, double arg1, double arg2) {
        double var17 = Math.cos(arg1);
        double var14 = (arg0 * var17);
        double var23 = Math.sin(arg1);
        double var21 = (arg0 * var23);
        return GeometryPrimitives.computeVector(var14, arg2, var21);
    }

    static void drawGeometryPart(CosmeticDrawBuilder arg0, double arg1, double arg2, double arg3) {
        double[] var18 = computeVector(arg1, arg2, arg3);
        CosmeticDrawBuilder var20 = arg0.drawGeometryPart();
        double var27 = var18[0];
        double var36 = var18[1];
        double var41 = var18[2];
        CosmeticDrawBuilder var22 = arg0.drawGeometryPart(var27, var36, var41);
        double var31 = (1.5707963267948966 - arg2);
        CosmeticDrawBuilder var24 = arg0.drawGeometryPart2(var31);
        CosmeticDrawBuilder var26 = arg0.drawGeometryPart3(1.5707963267948966);
    }

}
