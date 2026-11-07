package need.clarity.modules.visual.cosmetics.aura;

import need.clarity.modules.visual.cosmetics.ColorMathUtils;
import need.clarity.modules.visual.cosmetics.CosmeticDrawBuilder;
import need.clarity.modules.visual.cosmetics.GeometryPrimitives;
import need.clarity.modules.visual.cosmetics.IGradientColorSampler;

public final class AuraRenderMath {
    static final double CONST_DBL = 6.283185307179586;
    private static final double CONST_DBL1 = 0.3;
    private static final double CONST_DBL2 = 0.85;

    private AuraRenderMath() {
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

    static double drawGeometryPart(CosmeticDrawBuilder arg0) {
        double[] var9 = arg0.computeVector1();
        double var12 = arg0.computeDoubleMetric();
        return computeDoubleMetric(var9, 0.0, 0.0, 0.0, var12);
    }

    static int computeColorOrIndex(int arg0, double arg1) {
        int var11 = 0;
        if (Double.compare(arg1, 0.999) >= 0) {
            var11 = arg0;
        } else {
            var11 = ColorMathUtils.computeColorOrIndex(arg0, arg1);
        }
        return var11;
    }

    static IGradientColorSampler evaluate(IGradientColorSampler arg0, double arg1) {
        IGradientColorSampler var11 = null;
        if (Double.compare(arg1, 0.999) >= 0) {
            var11 = arg0;
        } else {
            var11 = (p0, p1) -> AuraRenderMath.lambda$a(arg0, arg1, p0, p1);
        }
        return var11;
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

    static void drawGeometryPart(CosmeticDrawBuilder arg0, double arg1, double arg2, double arg3, double arg4, double arg5, double arg6, int arg7, int arg8) {
        double var47 = (arg3 - arg1);
        double var51 = (arg4 - arg2);
        double var55 = (var47 * var47);
        double var92 = (var51 * var51);
        double var59 = Math.sqrt((var55 + var92));
        if (Double.compare(var59, 1.0E-9) < 0) {
            return;
        }
        double var72 = (((-(var51) / var59) * arg5) * 0.5);
        double var80 = (((var47 / var59) * arg5) * 0.5);
        double var83 = (arg1 + var72);
        double var129 = (arg2 + var80);
        double[] var85 = GeometryPrimitives.computeVector(var83, arg6, var129);
        double var110 = (arg1 - var72);
        double var136 = (arg2 - var80);
        double[] var112 = GeometryPrimitives.computeVector(var110, arg6, var136);
        double var117 = (arg3 - var72);
        double var145 = (arg4 - var80);
        double[] var119 = GeometryPrimitives.computeVector(var117, arg6, var145);
        double var126 = (arg3 + var72);
        double var150 = (arg4 + var80);
        double[] var128 = GeometryPrimitives.computeVector(var126, arg6, var150);
        arg0.applyStep(var85, var112, var119, var128, arg7, arg7, arg8, arg8);
    }

    static void drawGeometryPart(CosmeticDrawBuilder arg0, double arg1, double arg2, double arg3, double arg4, int arg5, double arg6, int arg7, int arg8) {
        int var37 = 0;
        double[] var42 = GeometryPrimitives.computeVector(arg1, arg4, arg2);
        var37 = 0;
        while (true) {
            if (var37 >= arg5) break;
            double var76 = ((double) var37);
            double var57 = (6.283185307179586 * var76);
            double var79 = ((double) arg5);
            double var59 = (var57 / var79);
            double var47 = (arg6 + var59);
            double var83 = ((double) (var37 + 1));
            double var63 = (6.283185307179586 * var83);
            double var86 = ((double) arg5);
            double var65 = (var63 / var86);
            double var51 = (arg6 + var65);
            double var102 = Math.cos(var47);
            double var90 = (arg3 * var102);
            double var69 = (arg1 + var90);
            double var119 = Math.sin(var47);
            double var115 = (arg3 * var119);
            double var106 = (arg2 + var115);
            double[] var71 = GeometryPrimitives.computeVector(var69, arg4, var106);
            double var109 = Math.cos(var51);
            double var97 = (arg3 * var109);
            double var72 = (arg1 + var97);
            double var121 = Math.sin(var51);
            double var117 = (arg3 * var121);
            double var113 = (arg2 + var117);
            double[] var74 = GeometryPrimitives.computeVector(var72, arg4, var113);
            arg0.applyStep(var42, var71, var74, arg7, arg8, arg8);
            var37 = (var37 + 1);
        }
    }

    static void drawGeometryPart(CosmeticDrawBuilder arg0, double arg1, double arg2, double arg3, double arg4, double arg5, int arg6, int arg7) {
        double var41 = (arg5 * 0.5);
        double[] var47 = GeometryPrimitives.computeVector((arg1 - var41), arg2, arg3);
        double[] var55 = GeometryPrimitives.computeVector((arg1 + var41), arg2, arg3);
        double var85 = (var41 * 0.4);
        double var63 = (arg1 + var85);
        double var89 = (arg2 + arg4);
        double[] var65 = GeometryPrimitives.computeVector(var63, var89, arg3);
        double var103 = (var41 * 0.4);
        double var75 = (arg1 - var103);
        double var107 = (arg2 + arg4);
        double[] var77 = GeometryPrimitives.computeVector(var75, var107, arg3);
        arg0.applyStep(var47, var55, var65, var77, arg6, arg6, arg7, arg7);
        double var94 = (arg3 - var41);
        double[] var50 = GeometryPrimitives.computeVector(arg1, arg2, var94);
        double var112 = (arg3 + var41);
        double[] var58 = GeometryPrimitives.computeVector(arg1, arg2, var112);
        double var98 = (arg2 + arg4);
        double var147 = (var41 * 0.4);
        double var130 = (arg3 + var147);
        double[] var70 = GeometryPrimitives.computeVector(arg1, var98, var130);
        double var116 = (arg2 + arg4);
        double var149 = (var41 * 0.4);
        double var144 = (arg3 - var149);
        double[] var82 = GeometryPrimitives.computeVector(arg1, var116, var144);
        arg0.applyStep(var50, var58, var70, var82, arg6, arg6, arg7, arg7);
    }

}
