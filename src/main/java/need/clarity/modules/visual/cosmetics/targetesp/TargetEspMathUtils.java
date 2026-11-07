package need.clarity.modules.visual.cosmetics.targetesp;

import need.clarity.modules.visual.cosmetics.ColorMathUtils;
import need.clarity.modules.visual.cosmetics.CosmeticDrawBuilder;
import need.clarity.modules.visual.cosmetics.GeometryPrimitives;

public final class TargetEspMathUtils {
    static final double CONST_DBL = 6.283185307179586;
    private static final double CONST_DBL1 = 0.3;
    private static final double CONST_DBL2 = 0.85;

    private TargetEspMathUtils() {
    }

    static double computeDoubleMetric(double[] arg0, double arg1, double arg2, double arg3, double arg4) {
        if (arg0 == null) {
            return 1.0;
        }
        double var26 = (arg0[0] - arg1);
        double var31 = (arg0[1] - arg2);
        double var36 = (arg0[2] - arg3);
        double var40 = (var26 * var26);
        double var70 = (var31 * var31);
        double var42 = (var40 + var70);
        double var74 = (var36 * var36);
        return GeometryPrimitives.computeDoubleMetric((((Math.sqrt((var42 + var74)) * arg4) - 0.3) / 0.55));
    }

    static double drawGeometryPart(CosmeticDrawBuilder arg0, double arg1, double arg2) {
        double var14 = 0.0;
        if (arg0.enabledState) {
            double var24 = Math.sin(((arg0.doubleValue * arg1) + arg2));
            double var16 = (0.5 * var24);
            var14 = (0.5 + var16);
        } else {
            var14 = 0.5;
        }
        return var14;
    }

    static double computeDoubleMetric(double arg0) {
        double var8 = Math.floor(arg0);
        return (arg0 - var8);
    }

    static double computeDoubleMetric(double arg0, double arg1, double arg2) {
        double var14 = 0.0;
        if (Double.compare(arg0, arg1) < 0) {
            var14 = arg1;
        } else {
            if (Double.compare(arg0, arg2) > 0) {
                var14 = arg2;
            } else {
                var14 = arg0;
            }
            return var14;
        }
    }

    static double[][] computeMatrix(double[] arg0) {
        int var22 = 0;
        if (Double.compare(Math.abs(arg0[1]), 0.9) < 0) {
            var22 = new double[3];
            var22[0] = 0.0;
            var22[1] = 1.0;
            var22[2] = 0.0;
        } else {
            var22 = new double[3];
            var22[0] = 1.0;
            var22[1] = 0.0;
            var22[2] = 0.0;
        }
        double[] var8 = var22;
        double[] var18 = GeometryPrimitives.computeVector(GeometryPrimitives.computeVector1(arg0, var8));
        double[][] var20 = new double[2][];
        var20[0] = var18;
        double[] var38 = GeometryPrimitives.computeVector1(arg0, var18);
        var20[1] = var38;
        return var20;
    }

    static void drawGeometryPart(CosmeticDrawBuilder arg0, double[] arg1, double[] arg2, double arg3, int arg4, int arg5, boolean arg6) {
        int var29 = 0;
        int var323 = 0;
        double[][] var39 = computeMatrix(GeometryPrimitives.computeVector(GeometryPrimitives.computeVector(arg2, arg1)));
        double[][] var41 = new double[4][];
        double[][] var43 = new double[4][];
        double[] var45 = new double[4];
        var45[0] = 1.0;
        var45[1] = -1.0;
        var45[2] = -1.0;
        var45[3] = 1.0;
        double[] var47 = new double[4];
        var47[0] = 1.0;
        var47[1] = 1.0;
        var47[2] = -1.0;
        var47[3] = -1.0;
        var29 = 0;
        while (true) {
            if (var29 >= 4) break;
            int var79 = var29;
            double var53 = (var45[var79] * arg3);
            int var80 = var29;
            double var58 = (var47[var80] * arg3);
            int var81 = var29;
            double var110 = arg1[0];
            double var170 = (var39[0][0] * var53);
            double var112 = (var110 + var170);
            double var176 = (var39[1][0] * var58);
            double var114 = (var112 + var176);
            double var179 = arg1[1];
            double var229 = (var39[0][1] * var53);
            double var181 = (var179 + var229);
            double var235 = (var39[1][1] * var58);
            double var183 = (var181 + var235);
            double var238 = arg1[2];
            double var284 = (var39[0][2] * var53);
            double var240 = (var238 + var284);
            double var290 = (var39[1][2] * var58);
            double var242 = (var240 + var290);
            double[] var116 = GeometryPrimitives.computeVector(var114, var183, var242);
            var41[var81] = var116;
            int var82 = var29;
            double var118 = arg2[0];
            double var189 = (var39[0][0] * var53);
            double var120 = (var118 + var189);
            double var195 = (var39[1][0] * var58);
            double var122 = (var120 + var195);
            double var198 = arg2[1];
            double var252 = (var39[0][1] * var53);
            double var200 = (var198 + var252);
            double var258 = (var39[1][1] * var58);
            double var202 = (var200 + var258);
            double var261 = arg2[2];
            double var300 = (var39[0][2] * var53);
            double var263 = (var261 + var300);
            double var306 = (var39[1][2] * var58);
            double var265 = (var263 + var306);
            double[] var124 = GeometryPrimitives.computeVector(var122, var202, var265);
            var43[var82] = var124;
            var29 = (var29 + 1);
        }
        double[] var63 = new double[4];
        var63[0] = 1.0;
        var63[1] = 0.72;
        var63[2] = 0.58;
        var63[3] = 0.86;
        var323 = 0;
        while (true) {
            if (var323 >= 4) break;
            int var68 = ((var323 + 1) & 3);
            int var129 = var323;
            double var87 = var63[var129];
            int var70 = ColorMathUtils.computeColorOrIndex6(arg4, var87);
            int var130 = var323;
            double var90 = var63[var130];
            int var72 = ColorMathUtils.computeColorOrIndex6(arg5, var90);
            int var131 = var323;
            double[] var93 = var41[var131];
            double[] var133 = var41[var68];
            double[] var163 = var43[var68];
            int var219 = var323;
            double[] var206 = var43[var219];
            arg0.applyStep(var93, var133, var163, var206, var70, var70, var72, var72);
            var323 = (var323 + 1);
        }
        if (arg6) {
            int var76 = ColorMathUtils.computeColorOrIndex6(arg5, 1.1);
            double[] var97 = var43[0];
            double[] var136 = var43[1];
            double[] var166 = var43[2];
            double[] var209 = var43[3];
            arg0.applyStep(var97, var136, var166, var209, var76);
        }
    }

}
