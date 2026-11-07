package need.clarity.modules.visual.cosmetics.aura;

import need.clarity.modules.visual.cosmetics.ColorMathUtils;
import need.clarity.modules.visual.cosmetics.CosmeticDrawBuilder;
import need.clarity.modules.visual.cosmetics.GeometryPrimitives;
import need.clarity.modules.visual.cosmetics.ICosmeticEffectRenderer;
import need.clarity.modules.visual.cosmetics.IGradientColorSampler;

public final class RingsOrbitRenderer implements ICosmeticEffectRenderer {
    static final int CONST_INT = 6;
    private static final double CONST_DBL = 0.018;
    private static final double CONST_DBL1 = 0.015;
    private final OrbitAuraSpec orbitAuraSpec;

    public RingsOrbitRenderer() {
        OrbitAuraSpec var2 = OrbitAuraSpec.evaluate();
        this(var2);
    }

    public RingsOrbitRenderer(OrbitAuraSpec arg0) {
        this.orbitAuraSpec = arg0;
    }

    public void renderEffect(CosmeticDrawBuilder arg0) {
        double var67 = 0.0;
        double var78 = 0.0;
        int var23 = 0;
        double var105 = 0.0;
        int var44 = 0;
        double var112 = 0.0;
        double var59 = 0.0;
        int var39 = this.orbitAuraSpec.computeColorOrIndex(6);
        int var42 = this.orbitAuraSpec.computeColorOrIndex1(var39);
        if (var42 == 2) {
            var44 = 36;
        } else {
            if (var42 == 1) {
                var44 = 30;
            } else {
                var44 = 24;
            }
            int var16 = var44;
            if (arg0.enabledState) {
                var112 = arg0.doubleValue;
            } else {
                var112 = 0.0;
            }
            double var17 = var112;
            double var50 = this.orbitAuraSpec.phase();
            double var54 = this.orbitAuraSpec.radius();
            var23 = 0;
            while (true) {
                if (var23 >= var39) break;
                if (var39 == 1) {
                    var59 = 0.0;
                } else {
                    double var110 = ((double) var23);
                    double var167 = ((double) (var39 - 1));
                    var59 = (var110 / var167);
                }
                double var24 = var59;
                double var170 = (0.22 * var24);
                double var131 = (0.8 + var170);
                double var63 = (var54 * var131);
                if ((var23 % 2) == 0) {
                    var67 = 1.0;
                } else {
                    var67 = -1.0;
                }
                double var28 = var67;
                double var72 = (((double) var23) * 2.399);
                double var141 = (((6.283185307179586 * var50) * 0.35) * var28);
                double var74 = (var72 + var141);
                if (var23 == 0) {
                    if (var39 >= 3) {
                        var78 = 0.0;
                    } else {
                    }
                }
                double var163 = (0.85 * var24);
                var78 = (0.5 + var163);
                double var32 = var78;
                double var82 = (6.283185307179586 * var50);
                double var195 = ((double) var23);
                double var180 = (0.3 * var195);
                double var147 = (1.0 + var180);
                double var86 = ((var82 * var147) * var28);
                if (arg0.enabledState) {
                    double var153 = (var17 * 1.3);
                    double var187 = (((double) var23) * 1.9);
                    double var157 = Math.sin((var153 + var187));
                    var105 = (0.03 * var157);
                } else {
                    var105 = 0.0;
                }
                double var36 = var105;
                CosmeticDrawBuilder var93 = arg0.drawGeometryPart();
                CosmeticDrawBuilder var95 = arg0.drawGeometryPart(0.0, var36, 0.0);
                CosmeticDrawBuilder var97 = arg0.drawGeometryPart2(var74);
                CosmeticDrawBuilder var99 = arg0.drawGeometryPart3(var32);
                CosmeticDrawBuilder var101 = arg0.drawGeometryPart2(var86);
                int var129 = var23;
                drawGeometryPart(arg0, var129, var63, var16, var28);
                CosmeticDrawBuilder var104 = arg0.drawGeometryPart1();
                var23 = (var23 + 1);
            }
            return;
        }
    }

    private static void drawGeometryPart(CosmeticDrawBuilder arg0, int arg1, double arg2, int arg3, double arg4) {
        int var38 = 0;
        int var63 = 0;
        int var33 = arg0.counterValue;
        int var35 = arg0.counterValue1;
        if ((arg1 % 2) == 0) {
            var38 = var33;
        } else {
            var38 = var35;
        }
        int var23 = var38;
        if ((arg1 % 2) == 0) {
            var63 = var35;
        } else {
            var63 = var33;
        }
        int var24 = var63;
        double[] var42 = arg0.computeVector1();
        double var44 = arg0.computeDoubleMetric();
        IGradientColorSampler var48 = (p0, p1) -> RingsOrbitRenderer.lambda$a(arg4, var24, var23, var42, arg2, var44, p0, p1);
        double var81 = (arg2 - 0.018);
        double var95 = (arg2 + 0.018);
        GeometryPrimitives.drawGeometryPart(arg0, 0.0, var81, var95, arg3, var48);
        IGradientColorSampler var51 = (p0, p1) -> RingsOrbitRenderer.lambda$a(var48, p0, p1);
        GeometryPrimitives.drawGeometryPart(arg0, -0.015, 0.015, arg2, arg2, arg3, var51, false, false);
        double var54 = AuraRenderMath.computeDoubleMetric(var42, arg2, 0.0, 0.0, var44);
        if (Double.compare(var54, 0.02) > 0) {
            IGradientColorSampler var122 = AuraRenderMath.evaluate((p0, p1) -> RingsOrbitRenderer.lambda$b(ColorMathUtils.computeColorOrIndex7(var23, 0.55), p0, p1), var54);
            GeometryPrimitives.drawGeometryPart(arg0, arg2, 0.0, 0.0, 0.028, 6, 3, var122);
            IGradientColorSampler var125 = AuraRenderMath.evaluate((p0, p1) -> RingsOrbitRenderer.lambda$a(var23, p0, p1), var54);
            GeometryPrimitives.drawGeometryPart(arg0, arg2, 0.0, 0.0, 0.06, 6, 3, var125);
        }
    }

}
