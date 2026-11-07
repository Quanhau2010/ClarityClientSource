package need.clarity.modules.visual.cosmetics.aura;

import need.clarity.modules.visual.cosmetics.ColorMathUtils;
import need.clarity.modules.visual.cosmetics.CosmeticDrawBuilder;
import need.clarity.modules.visual.cosmetics.GeometryPrimitives;
import need.clarity.modules.visual.cosmetics.ICosmeticEffectRenderer;
import need.clarity.modules.visual.cosmetics.IGradientColorSampler;

public final class OrbsOrbitRenderer implements ICosmeticEffectRenderer {
    static final int CONST_INT = 12;
    private static final double CONST_DBL = 0.055;
    private static final double CONST_DBL1 = 0.2;
    private static final double CONST_DBL2 = 0.9;
    private final OrbitAuraSpec orbitAuraSpec;

    public OrbsOrbitRenderer() {
        OrbitAuraSpec var2 = OrbitAuraSpec.evaluate();
        this(var2);
    }

    public OrbsOrbitRenderer(OrbitAuraSpec arg0) {
        this.orbitAuraSpec = arg0;
    }

    private static double computeDoubleMetric(double arg0, double arg1) {
        double var14 = (3.0 * arg0);
        double var22 = (arg1 * 0.9);
        double var18 = Math.sin((var14 + var22));
        return (0.2 * var18);
    }

    public void renderEffect(CosmeticDrawBuilder arg0) {
        int var142 = 0;
        int var143 = 0;
        int var144 = 0;
        int var145 = 0;
        int var146 = 0;
        int var30 = 0;
        int var39 = 0;
        double var59 = 0.0;
        int var77 = 0;
        int var363 = 0;
        double var117 = 0.0;
        int var48 = this.orbitAuraSpec.computeColorOrIndex(12);
        int var51 = this.orbitAuraSpec.computeColorOrIndex1(var48);
        if (var51 == 2) {
            var144 = 8;
        } else {
            if (var51 == 1) {
                var144 = 6;
            } else {
                var144 = 5;
            }
            int var20 = var144;
            if (var51 == 2) {
                var146 = 4;
            } else {
                var146 = 3;
            }
            int var21 = var146;
            if (var51 == 2) {
                var145 = 6;
            } else {
                if (var51 == 1) {
                    var145 = 4;
                } else {
                    var145 = 3;
                }
                int var22 = var145;
                if (var51 == 2) {
                    var143 = 4;
                } else {
                    var143 = 3;
                }
                int var23 = var143;
                if (arg0.enabledState) {
                    var59 = arg0.doubleValue;
                } else {
                    var59 = 0.0;
                }
                double var24 = var59;
                double var63 = this.orbitAuraSpec.radius();
                double var67 = this.orbitAuraSpec.computeDoubleMetric();
                var30 = 0;
                while (true) {
                    if (var30 >= var48) break;
                    if ((var30 % 2) == 0) {
                        var142 = arg0.counterValue;
                    } else {
                        var142 = arg0.counterValue1;
                    }
                    int var31 = var142;
                    if ((var30 % 2) == 0) {
                        var77 = arg0.counterValue1;
                    } else {
                        var77 = arg0.counterValue;
                    }
                    int var32 = var77;
                    OrbitAuraSpec var79 = this.orbitAuraSpec;
                    int var156 = var30;
                    double var80 = var79.computeDoubleMetric(var156, var48);
                    double var177 = Math.sin(var80);
                    double var84 = (var63 * var177);
                    double var181 = computeDoubleMetric(var80, var24);
                    double var297 = Math.cos(var80);
                    double var240 = (var63 * var297);
                    double[] var86 = GeometryPrimitives.computeVector(var84, var181, var240);
                    double[] var88 = arg0.computeVector1();
                    double var158 = var86[0];
                    double var226 = var86[1];
                    double var284 = var86[2];
                    double var321 = arg0.computeDoubleMetric();
                    double var89 = AuraRenderMath.computeDoubleMetric(var88, var158, var226, var284, var321);
                    if (Double.compare(var89, 0.02) <= 0) {
                    } else {
                        double[][] var96 = new double[(var22 + 1)][];
                        var39 = 0;
                        while (true) {
                            if (var39 > var22) break;
                            double var215 = (var67 * 0.9);
                            double var271 = ((double) var39);
                            double var217 = (var215 * var271);
                            double var274 = ((double) var22);
                            double var219 = (var217 / var274);
                            double var135 = (var80 - var219);
                            int var173 = var39;
                            double var278 = Math.sin(var135);
                            double var223 = (var63 * var278);
                            double var282 = computeDoubleMetric(var135, var24);
                            double var340 = Math.cos(var135);
                            double var319 = (var63 * var340);
                            double[] var225 = GeometryPrimitives.computeVector(var223, var282, var319);
                            var96[var173] = var225;
                            var39 = (var39 + 1);
                        }
                        var363 = 0;
                        while (true) {
                            if (var363 >= var22) break;
                            int var101 = var363;
                            double var244 = ((double) var363);
                            double var301 = ((double) var22);
                            double var246 = (var244 / var301);
                            double var188 = (1.0 - var246);
                            double var106 = ((0.030250000000000003 * var188) + 0.003);
                            double var250 = ((double) (var363 + 1));
                            double var304 = ((double) var22);
                            double var252 = (var250 / var304);
                            double var194 = (1.0 - var252);
                            double var112 = ((0.030250000000000003 * var194) + 0.003);
                            int var198 = var363;
                            double[] var164 = var96[var198];
                            int var229 = (var363 + 1);
                            double[] var200 = var96[var229];
                            IGradientColorSampler var335 = (p0, p1) -> OrbsOrbitRenderer.lambda$a(var101, var22, var31, var32, var89, p0, p1);
                            GeometryPrimitives.drawGeometryPart(arg0, var164, var200, var106, var112, var23, var335, false);
                            var363 = (var363 + 1);
                        }
                        if (arg0.enabledState) {
                            double var262 = (var24 * 3.0);
                            double var313 = (((double) var30) * 1.3);
                            double var266 = Math.sin((var262 + var313));
                            double var211 = (0.1 * var266);
                            var117 = (1.0 + var211);
                        } else {
                            var117 = 1.0;
                        }
                        double var364 = var117;
                        double var121 = (0.055 * var364);
                        int var124 = ColorMathUtils.computeColorOrIndex7(var31, 0.35);
                        double var168 = var86[0];
                        double var233 = var86[1];
                        double var290 = var86[2];
                        IGradientColorSampler var353 = AuraRenderMath.evaluate((p0, p1) -> OrbsOrbitRenderer.lambda$a(var124, p0, p1), var89);
                        GeometryPrimitives.drawGeometryPart(arg0, var168, var233, var290, var121, var20, var21, var353);
                        double var329 = (((double) var30) * 1.7);
                        double var257 = AuraRenderMath.drawGeometryPart(arg0, 2.2, var329);
                        double var206 = (0.08 * var257);
                        double var128 = (0.16 + var206);
                        double var171 = var86[0];
                        double var236 = var86[1];
                        double var295 = var86[2];
                        double var333 = (var121 * 2.1);
                        IGradientColorSampler var356 = AuraRenderMath.evaluate((p0, p1) -> OrbsOrbitRenderer.lambda$a(var31, var128, p0, p1), var89);
                        GeometryPrimitives.drawGeometryPart(arg0, var171, var236, var295, var333, var20, var21, var356);
                    }
                    var30 = (var30 + 1);
                }
                return;
            }
        }
    }

}
