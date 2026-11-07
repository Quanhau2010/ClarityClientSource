package need.clarity.modules.visual.cosmetics.killeffect;

import need.clarity.modules.visual.cosmetics.CosmeticDrawBuilder;
import need.clarity.modules.visual.cosmetics.GeometryPrimitives;
import need.clarity.modules.visual.cosmetics.IGradientColorSampler;

public final class KillEffectMathUtils {
    static final double CONST_DBL = 6.283185307179586;
    static final double CONST_DBL1 = 0.03;
    private static final double CONST_DBL2 = 0.3;
    private static final double CONST_DBL3 = 0.85;

    private KillEffectMathUtils() {
    }

    static double computeDoubleMetric(double[] arg0, double arg1, double arg2, double arg3, double arg4) {
        if (arg0 == null) {
            return 1.0;
        }
        double var28 = (arg0[0] - arg1);
        double var33 = (arg0[1] - arg2);
        double var38 = (arg0[2] - arg3);
        double var42 = (var28 * var28);
        double var70 = (var33 * var33);
        double var44 = (var42 + var70);
        double var74 = (var38 * var38);
        return GeometryPrimitives.computeDoubleMetric((((Math.sqrt((var44 + var74)) * arg4) - 0.3) / 0.55));
    }

    static double computeDoubleMetric(double arg0) {
        double var8 = Math.floor(arg0);
        return (arg0 - var8);
    }

    static double computeDoubleMetric(double arg0, double arg1, double arg2) {
        double var11 = 0.0;
        if (Double.compare(arg0, arg1) < 0) {
            var11 = arg1;
        } else {
            if (Double.compare(arg0, arg2) > 0) {
                var11 = arg2;
            } else {
                var11 = arg0;
            }
            return var11;
        }
    }

    static double[] computeVector(IGroundHeightSampler arg0, double arg1, double arg2, double arg3) {
        double var19 = (arg0.computeDoubleMetric(arg2, arg3) + arg1);
        return GeometryPrimitives.computeVector(arg2, var19, arg3);
    }

    static void drawGeometryPart(CosmeticDrawBuilder arg0, IGroundHeightSampler arg1, double arg2, double arg3, double arg4, int arg5, IGradientColorSampler arg6) {
        int var159 = 0;
        int var32 = 0;
        double var89 = (arg3 + 1.0E-6);
        if (Double.compare(arg4, var89) <= 0) {
            return;
        }
        double[][] var42 = new double[(arg5 + 1)][];
        double[][] var45 = new double[(arg5 + 1)][];
        var32 = 0;
        while (true) {
            if (var32 > arg5) break;
            double var64 = ((double) (var32 % arg5));
            double var101 = ((double) arg5);
            double var68 = ((var64 / var101) * 6.283185307179586);
            double var72 = Math.cos(var68);
            double var76 = Math.sin(var68);
            int var87 = var32;
            double var125 = (arg3 * var72);
            double var141 = (arg3 * var76);
            double[] var106 = computeVector(arg1, arg2, var125, var141);
            var42[var87] = var106;
            int var88 = var32;
            double var129 = (arg4 * var72);
            double var147 = (arg4 * var76);
            double[] var108 = computeVector(arg1, arg2, var129, var147);
            var45[var88] = var108;
            var32 = (var32 + 1);
        }
        var159 = 0;
        while (true) {
            if (var159 >= arg5) break;
            double var51 = ((double) var159);
            double var92 = ((double) arg5);
            double var53 = (var51 / var92);
            double var57 = ((double) (var159 + 1));
            double var95 = ((double) arg5);
            double var59 = (var57 / var95);
            int var97 = var159;
            double[] var85 = var42[var97];
            int var15 = var159;
            double[] var99 = var45[var15];
            int var116 = (var159 + 1);
            double[] var110 = var45[var116];
            int var120 = (var159 + 1);
            double[] var118 = var42[var120];
            int var122 = arg6.computeColorOrIndex(var53, 0.0);
            int var134 = arg6.computeColorOrIndex(var53, 1.0);
            int var136 = arg6.computeColorOrIndex(var59, 1.0);
            int var152 = arg6.computeColorOrIndex(var59, 0.0);
            arg0.applyStep(var85, var99, var110, var118, var122, var134, var136, var152);
            var159 = (var159 + 1);
        }
    }

    static void drawGeometryPart(CosmeticDrawBuilder arg0, IGroundHeightSampler arg1, double arg2, double arg3, int arg4, IGradientColorSampler arg5) {
        int var29 = 0;
        int var120 = 0;
        if (Double.compare(arg3, 1.0E-6) <= 0) {
            return;
        }
        double[] var36 = computeVector(arg1, arg2, 0.0, 0.0);
        double[][] var39 = new double[(arg4 + 1)][];
        var29 = 0;
        while (true) {
            if (var29 > arg4) break;
            double var44 = ((double) (var29 % arg4));
            double var73 = ((double) arg4);
            double var48 = ((var44 / var73) * 6.283185307179586);
            int var68 = var29;
            double var106 = Math.cos(var48);
            double var97 = (arg3 * var106);
            double var116 = Math.sin(var48);
            double var110 = (arg3 * var116);
            double[] var78 = computeVector(arg1, arg2, var97, var110);
            var39[var68] = var78;
            var29 = (var29 + 1);
        }
        var120 = 0;
        while (true) {
            if (var120 >= arg4) break;
            double var54 = ((double) var120);
            double var80 = ((double) arg4);
            double var56 = (var54 / var80);
            double var60 = ((double) (var120 + 1));
            double var83 = ((double) arg4);
            double var62 = (var60 / var83);
            int var89 = var120;
            double[] var86 = var39[var89];
            int var92 = (var120 + 1);
            double[] var91 = var39[var92];
            int var94 = arg5.computeColorOrIndex(var56, 0.0);
            int var103 = arg5.computeColorOrIndex(var56, 1.0);
            int var105 = arg5.computeColorOrIndex(var62, 1.0);
            arg0.applyStep(var36, var86, var91, var94, var103, var105);
            var120 = (var120 + 1);
        }
    }

    static double computeDoubleMetric(double[] arg0, double arg1) {
        int var17 = arg0.length;
        if (var17 == 1) {
            return arg0[0];
        }
        double var24 = computeDoubleMetric(arg1, 0.0, 1.0);
        double var48 = ((double) (var17 - 1));
        double var26 = (var24 * var48);
        int var29 = (var17 - 2);
        int var44 = ((int) var26);
        int var30 = Math.min(var29, var44);
        double var51 = ((double) var30);
        double var33 = (var26 - var51);
        double var36 = arg0[var30];
        int var61 = (var30 + 1);
        double var54 = arg0[var61];
        double var64 = arg0[var30];
        double var58 = ((var54 - var64) * var33);
        return (var36 + var58);
    }

    static double[] computeVector(double[] arg0, double[][] arg1, double arg2) {
        int var16 = 0;
        double[] var18 = new double[arg1.length];
        var16 = 0;
        while (true) {
            int var20 = var16;
            int var23 = arg1.length;
            if (var20 >= var23) break;
            int var24 = var16;
            int var8 = var16;
            double var28 = arg1[var8][0];
            int var10 = var16;
            double var32 = arg1[var10][1];
            int var12 = var16;
            double var36 = arg1[var12][2];
            double var25 = computeDoubleMetric(arg0, var28, var32, var36, arg2);
            var18[var24] = var25;
            var16 = (var16 + 1);
        }
        return var18;
    }

    static void drawGeometryPart(CosmeticDrawBuilder arg0, IGroundHeightSampler arg1, double arg2, double[] arg3, double[] arg4, double[] arg5, IGradientColorSampler arg6) {
        int var29 = 0;
        double var105 = 0.0;
        double var107 = 0.0;
        int var240 = 0;
        int var45 = arg3.length;
        if (var45 < 2) {
            return;
        }
        double[][] var48 = new double[var45][];
        double[][] var50 = new double[var45][];
        var29 = 0;
        while (true) {
            if (var29 >= var45) break;
            int var69 = Math.max((var29 - 1), 0);
            int var71 = (var29 + 1);
            int var119 = (var45 - 1);
            int var72 = Math.min(var71, var119);
            double var74 = arg3[var72];
            double var139 = arg3[var69];
            double var76 = (var74 - var139);
            double var79 = arg4[var72];
            double var142 = arg4[var69];
            double var81 = (var79 - var142);
            double var85 = Math.hypot(var76, var81);
            if (Double.compare(var85, 1.0E-9) < 0) {
                var105 = 0.0;
            } else {
                var105 = (-(var81) / var85);
            }
            double var39 = var105;
            if (Double.compare(var85, 1.0E-9) < 0) {
                var107 = 1.0;
            } else {
                var107 = (var76 / var85);
            }
            double var41 = var107;
            int var165 = var29;
            double var153 = arg5[var165];
            double var99 = (0.5 * var153);
            int var122 = var29;
            int var194 = var29;
            double var181 = arg3[var194];
            double var200 = (var39 * var99);
            double var183 = (var181 + var200);
            int var220 = var29;
            double var203 = arg4[var220];
            double var228 = (var41 * var99);
            double var205 = (var203 + var228);
            double[] var156 = computeVector(arg1, arg2, var183, var205);
            var48[var122] = var156;
            int var123 = var29;
            int var195 = var29;
            double var186 = arg3[var195];
            double var209 = (var39 * var99);
            double var188 = (var186 - var209);
            int var221 = var29;
            double var212 = arg4[var221];
            double var234 = (var41 * var99);
            double var214 = (var212 - var234);
            double[] var158 = computeVector(arg1, arg2, var188, var214);
            var50[var123] = var158;
            var29 = (var29 + 1);
        }
        var240 = 0;
        while (true) {
            int var54 = var240;
            int var111 = (var45 - 1);
            if (var54 >= var111) break;
            double var56 = ((double) var240);
            double var127 = (((double) var45) - 1.0);
            double var58 = (var56 / var127);
            double var62 = ((double) (var240 + 1));
            double var132 = (((double) var45) - 1.0);
            double var64 = (var62 / var132);
            int var134 = var240;
            double[] var114 = var48[var134];
            int var11 = var240;
            double[] var136 = var50[var11];
            int var173 = (var240 + 1);
            double[] var162 = var50[var173];
            int var177 = (var240 + 1);
            double[] var175 = var48[var177];
            int var179 = arg6.computeColorOrIndex(0.0, var58);
            int var193 = arg6.computeColorOrIndex(1.0, var58);
            int var197 = arg6.computeColorOrIndex(1.0, var64);
            int var219 = arg6.computeColorOrIndex(0.0, var64);
            arg0.applyStep(var114, var136, var162, var175, var179, var193, var197, var219);
            var240 = (var240 + 1);
        }
    }

}
