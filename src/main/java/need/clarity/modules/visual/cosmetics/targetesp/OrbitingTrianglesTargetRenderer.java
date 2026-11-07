package need.clarity.modules.visual.cosmetics.targetesp;

import need.clarity.modules.visual.cosmetics.ColorMathUtils;
import need.clarity.modules.visual.cosmetics.CosmeticDrawBuilder;
import need.clarity.modules.visual.cosmetics.GeometryPrimitives;
import need.clarity.modules.visual.cosmetics.ICosmeticEffectRenderer;

public final class OrbitingTrianglesTargetRenderer implements ICosmeticEffectRenderer {
    private static final int CONST_INT = 5;
    private static final int CONST_INT1 = 6;
    private static final double CONST_DBL = 0.13;
    private static final double CONST_DBL1;
    private final TargetEspRenderSpec targetEspRenderSpec;

    public OrbitingTrianglesTargetRenderer() {
        TargetEspRenderSpec var2 = TargetEspRenderSpec.evaluate();
        this(var2);
    }

    public OrbitingTrianglesTargetRenderer(TargetEspRenderSpec arg0) {
        this.targetEspRenderSpec = arg0;
    }

    public void renderEffect(CosmeticDrawBuilder arg0) {
        double var297 = 0.0;
        double var299 = 0.0;
        int var43 = 0;
        int var55 = 0;
        double var65 = 0.0;
        if (arg0.enabledState) {
            var65 = arg0.doubleValue;
        } else {
            var65 = 0.0;
        }
        double var22 = var65;
        double var69 = this.targetEspRenderSpec.age();
        double var73 = this.targetEspRenderSpec.computeDoubleMetric1();
        double var77 = (1.0 - var69);
        double var81 = this.targetEspRenderSpec.computeDoubleMetric();
        double var208 = this.targetEspRenderSpec.phase();
        double var87 = ((6.283185307179586 * var208) * 0.6);
        double var216 = ((var77 * 2.4) * var81);
        double var89 = (var87 - var216);
        double var95 = TargetEspMathUtils.computeDoubleMetric((0.42 * var73), 0.22, 1.4);
        double var99 = this.targetEspRenderSpec.width();
        double var224 = (0.26 * var73);
        double var101 = (var99 + var224);
        double var230 = ((var77 * 1.2) * var73);
        double var103 = (var101 + var230);
        double var109 = (this.targetEspRenderSpec.height() * 0.82);
        double[] var112 = arg0.computeVector1();
        double var114 = arg0.computeDoubleMetric();
        var43 = 0;
        while (true) {
            if (var43 >= 5) break;
            double var123 = ((((double) var43) + 0.5) / 5.0);
            double var131 = (((var123 - 0.5) * 2.0) * var109);
            if (arg0.enabledState) {
                double var248 = (0.05 * var73);
                double var326 = (var22 * 1.5);
                double var371 = (((double) var43) * 1.7);
                double var330 = Math.sin((var326 + var371));
                var299 = (var248 * var330);
            } else {
                var299 = 0.0;
            }
            double var133 = (var131 + var299);
            if (arg0.enabledState) {
                double var254 = (0.04 * var73);
                double var336 = (var22 * 1.1);
                double var378 = (((double) var43) * 2.3);
                double var340 = Math.sin((var336 + var378));
                var297 = (var254 * var340);
            } else {
                var297 = 0.0;
            }
            double var137 = (var103 + var297);
            double var343 = ((double) var43);
            double var260 = ((6.283185307179586 * var343) / 5.0);
            double var141 = (var89 + var260);
            double var264 = this.targetEspRenderSpec.phase();
            double var147 = ((6.283185307179586 * var264) * 1.8);
            double var271 = (((double) var43) * 1.3);
            double var149 = (var147 + var271);
            int var152 = arg0.counterValue;
            int var190 = arg0.counterValue1;
            int var153 = ColorMathUtils.computeColorOrIndex(var152, var190, var123);
            var55 = 6;
            while (true) {
                if (var55 < 0) break;
                double var280 = ((((double) var55) * 0.13) * var81);
                double var158 = (var141 - var280);
                double var284 = Math.cos(var158);
                double var162 = (var137 * var284);
                double var288 = Math.sin(var158);
                double var166 = (var137 * var288);
                double var291 = TargetEspMathUtils.computeDoubleMetric(var112, var162, var133, var166, var114);
                double var170 = (var69 * var291);
                if (Double.compare(var170, 0.01) < 0) {
                } else {
                    CosmeticDrawBuilder var176 = arg0.drawGeometryPart();
                    CosmeticDrawBuilder var178 = arg0.drawGeometryPart(var162, var133, var166);
                    double var197 = ((1.5707963267948966 - var158) + var149);
                    CosmeticDrawBuilder var180 = arg0.drawGeometryPart2(var197);
                    CosmeticDrawBuilder var182 = arg0.drawGeometryPart3(0.42);
                    if (var55 == 0) {
                        int var398 = var43;
                        this.drawGeometryPart(arg0, var95, var153, var123, var170, var398);
                    } else {
                        double var387 = ((double) var55);
                        double var364 = (0.09 * var387);
                        double var309 = (1.0 - var364);
                        double var203 = (var95 * var309);
                        double var396 = (((double) var55) / 7.0);
                        double var382 = (1.0 - var396);
                        double var357 = ((0.3 * var382) * var170);
                        drawGeometryPart(arg0, var203, var153, var357);
                    }
                    CosmeticDrawBuilder var186 = arg0.drawGeometryPart1();
                }
                var55 = (var55 + -1);
            }
            var43 = (var43 + 1);
        }
    }

    private static double[][] computeMatrix(double arg0, double arg1) {
        double var6 = CONST_DBL1;
        double var20 = (arg0 / var6);
        double var10 = CONST_DBL1;
        double var30 = (2.0 * var10);
        double var24 = (arg0 / var30);
        double[][] var27 = new double[3][];
        double var46 = (arg1 * var20);
        double[] var35 = GeometryPrimitives.computeVector(0.0, var46, 0.0);
        var27[0] = var35;
        double var38 = (-0.5 * arg0);
        double var54 = (-(arg1) * var24);
        double[] var40 = GeometryPrimitives.computeVector(var38, var54, 0.0);
        var27[1] = var40;
        double var43 = (0.5 * arg0);
        double var62 = (-(arg1) * var24);
        double[] var45 = GeometryPrimitives.computeVector(var43, var62, 0.0);
        var27[2] = var45;
        return var27;
    }

    private static void drawGeometryPart(CosmeticDrawBuilder arg0, double arg1, int arg2, double arg3) {
        int var22 = 0;
        double[][] var23 = computeMatrix(arg1, 1.0);
        double[] var26 = GeometryPrimitives.computeVector(0.0, 0.0, 0.0);
        int var28 = ColorMathUtils.computeColorOrIndex5(arg2, arg3);
        double var36 = (arg3 * 0.2);
        int var30 = ColorMathUtils.computeColorOrIndex5(arg2, var36);
        var22 = 0;
        while (true) {
            if (var22 >= 3) break;
            int var44 = var22;
            double[] var43 = var23[var44];
            int var49 = ((var22 + 1) % 3);
            double[] var46 = var23[var49];
            arg0.applyStep(var26, var43, var46, var30, var28, var28);
            var22 = (var22 + 1);
        }
    }

    private void drawGeometryPart(CosmeticDrawBuilder arg0, double arg1, int arg2, double arg3, double arg4, int arg5) {
        int var32 = 0;
        int var35 = 0;
        int var243 = 0;
        int var248 = 0;
        double[][] var37 = computeMatrix(arg1, 1.0);
        double[] var40 = GeometryPrimitives.computeVector(0.0, 0.0, 0.0);
        double[] var137 = new double[3];
        var137[0] = 0.0;
        var137[1] = 0.0;
        var137[2] = 1.0;
        double var44 = Math.pow(arg0.computeDoubleMetric(var40, var137), 3.0);
        double var251 = (((double) arg5) * 1.9);
        double var211 = TargetEspMathUtils.drawGeometryPart(arg0, 2.4, var251);
        double var142 = (0.25 * var211);
        double var48 = (0.75 + var142);
        double var146 = (0.35 * var44);
        double var50 = (var48 + var146);
        double var87 = (0.35 * var44);
        int var53 = ColorMathUtils.computeColorOrIndex7(arg2, var87);
        double var170 = (0.4 * var50);
        double var93 = (Math.min(1.0, var170) * arg4);
        int var54 = ColorMathUtils.computeColorOrIndex5(var53, var93);
        double var97 = (0.04 * arg4);
        int var56 = ColorMathUtils.computeColorOrIndex5(arg2, var97);
        var32 = 0;
        while (true) {
            if (var32 >= 3) break;
            int var204 = var32;
            double[] var165 = var37[var204];
            int var219 = ((var32 + 1) % 3);
            double[] var206 = var37[var219];
            arg0.applyStep(var40, var165, var206, var56, var54, var54);
            var32 = (var32 + 1);
        }
        double var150 = this.targetEspRenderSpec.computeDoubleMetric1();
        double var61 = (0.0105 * var150);
        double var178 = (0.45 * var44);
        double var102 = (0.3 + var178);
        int var64 = ColorMathUtils.computeColorOrIndex7(arg2, var102);
        double var182 = (0.92 * var50);
        double var108 = (Math.min(1.0, var182) * arg4);
        int var65 = ColorMathUtils.computeColorOrIndex5(var64, var108);
        int var111 = arg0.counterValue1;
        int var67 = ColorMathUtils.computeColorOrIndex(arg2, var111, 0.35);
        double var188 = (0.78 * var50);
        double var116 = (Math.min(1.0, var188) * arg4);
        int var68 = ColorMathUtils.computeColorOrIndex5(var67, var116);
        var35 = 0;
        while (true) {
            if (var35 >= 3) break;
            int var82 = ((var35 + 1) % 3);
            int var161 = var35;
            double[] var132 = var37[var161];
            double[] var163 = var37[var82];
            if (var35 == 0) {
                var243 = var65;
            } else {
                var243 = var68;
            }
            if (var82 == 0) {
                var248 = var65;
            } else {
                var248 = var68;
            }
            TargetEspMathUtils.drawGeometryPart(arg0, var132, var163, var61, var243, var248, false);
            var35 = (var35 + 1);
        }
        double[][] var75 = computeMatrix((arg1 * 0.44), -1.0);
        int var77 = ColorMathUtils.computeColorOrIndex7(arg2, 0.65);
        double var194 = (0.85 * var50);
        double var125 = (Math.min(1.0, var194) * arg4);
        int var78 = ColorMathUtils.computeColorOrIndex5(var77, var125);
        double[] var128 = var75[0];
        double[] var160 = var75[1];
        double[] var200 = var75[2];
        int var238 = ColorMathUtils.computeColorOrIndex6(var78, 0.8);
        int var245 = ColorMathUtils.computeColorOrIndex6(var78, 0.8);
        arg0.applyStep(var128, var160, var200, var78, var238, var245);
    }

    static {
        CONST_DBL1 = Math.sqrt(3.0);
    }

}
