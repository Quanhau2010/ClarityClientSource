package need.clarity.modules.visual.cosmetics.aura;

import need.clarity.modules.visual.cosmetics.ColorMathUtils;
import need.clarity.modules.visual.cosmetics.CosmeticDrawBuilder;
import need.clarity.modules.visual.cosmetics.GeometryPrimitives;
import need.clarity.modules.visual.cosmetics.ICosmeticEffectRenderer;
import need.clarity.modules.visual.cosmetics.IGradientColorSampler;

public final class ShardsOrbitRenderer implements ICosmeticEffectRenderer {
    static final int CONST_INT = 12;
    private final OrbitAuraSpec orbitAuraSpec;

    public ShardsOrbitRenderer() {
        OrbitAuraSpec var2 = OrbitAuraSpec.evaluate();
        this(var2);
    }

    public ShardsOrbitRenderer(OrbitAuraSpec arg0) {
        this.orbitAuraSpec = arg0;
    }

    public void renderEffect(CosmeticDrawBuilder arg0) {
        int var20 = 0;
        int var92 = 0;
        double var93 = 0.0;
        int var34 = this.orbitAuraSpec.computeColorOrIndex(12);
        int var37 = this.orbitAuraSpec.computeColorOrIndex1(var34);
        if (var37 == 2) {
            var92 = 6;
        } else {
            if (var37 == 1) {
                var92 = 5;
            } else {
                var92 = 4;
            }
            int var15 = var92;
            if (arg0.enabledState) {
                var93 = arg0.doubleValue;
            } else {
                var93 = 0.0;
            }
            double var16 = var93;
            double var45 = this.orbitAuraSpec.radius();
            var20 = 0;
            while (true) {
                if (var20 >= var34) break;
                OrbitAuraSpec var50 = this.orbitAuraSpec;
                int var99 = var20;
                double var51 = var50.computeDoubleMetric(var99, var34);
                double var157 = GeometryPrimitives.computeDoubleMetric(((var20 * 5) + 3));
                double var114 = (0.35 * var157);
                double var55 = (0.85 + var114);
                double var118 = (var16 * 1.3);
                double var164 = (((double) var20) * 1.9);
                double var122 = Math.sin((var118 + var164));
                double var59 = (0.11 * var122);
                double var131 = ((GeometryPrimitives.computeDoubleMetric(((var20 * 7) + 11)) - 0.5) * 0.26);
                double var61 = (var59 + var131);
                double var190 = ((double) (var20 % 3));
                double var172 = (0.4 * var190);
                double var135 = (1.2 + var172);
                double var65 = (var16 * var135);
                double var138 = ((double) var20);
                double var67 = (var65 + var138);
                double var176 = (var16 * 3.1);
                double var197 = (((double) var20) * 1.7);
                double var180 = Math.sin((var176 + var197));
                double var142 = (0.22 * var180);
                double var71 = (1.0 + var142);
                CosmeticDrawBuilder var74 = arg0.drawGeometryPart();
                double var149 = Math.sin(var51);
                double var102 = (var45 * var149);
                double var203 = Math.cos(var51);
                double var185 = (var45 * var203);
                CosmeticDrawBuilder var76 = arg0.drawGeometryPart(var102, var61, var185);
                CosmeticDrawBuilder var78 = arg0.drawGeometryPart2(var51);
                CosmeticDrawBuilder var80 = arg0.drawGeometryPart3(0.22);
                CosmeticDrawBuilder var82 = arg0.drawGeometryPart2(var67);
                double var84 = AuraRenderMath.drawGeometryPart(arg0);
                if (Double.compare(var84, 0.02) > 0) {
                    drawGeometryPart(arg0, var55, var71, var15, var84);
                }
                CosmeticDrawBuilder var91 = arg0.drawGeometryPart1();
                var20 = (var20 + 1);
            }
            return;
        }
    }

    private static void drawGeometryPart(CosmeticDrawBuilder arg0, double arg1, double arg2, int arg3, double arg4) {
        double var35 = (0.4 * arg1);
        double var39 = (0.22 * arg1);
        double var43 = (0.068 * arg1);
        int var46 = arg0.counterValue;
        int var48 = arg0.counterValue1;
        int var52 = ColorMathUtils.computeColorOrIndex6(ColorMathUtils.computeColorOrIndex7(ColorMathUtils.computeColorOrIndex(var46, var48, 0.3), 0.12), arg2);
        int var54 = ColorMathUtils.computeColorOrIndex7(var48, 0.3);
        int var56 = ColorMathUtils.computeColorOrIndex8(var46, 0.45);
        double[] var59 = GeometryPrimitives.computeVector(0.0, 0.0, 0.0);
        double[] var100 = GeometryPrimitives.computeVector(0.0, var35, 0.0);
        IGradientColorSampler var153 = AuraRenderMath.evaluate((p0, p1) -> ShardsOrbitRenderer.lambda$c(var52, var54, p0, p1), arg4);
        GeometryPrimitives.drawGeometryPart(arg0, var59, var100, var43, arg3, var153);
        double var128 = -(var39);
        double[] var103 = GeometryPrimitives.computeVector(0.0, var128, 0.0);
        IGradientColorSampler var158 = AuraRenderMath.evaluate((p0, p1) -> ShardsOrbitRenderer.lambda$b(var52, var56, p0, p1), arg4);
        GeometryPrimitives.drawGeometryPart(arg0, var59, var103, var43, arg3, var158);
        int var63 = ColorMathUtils.computeColorOrIndex7(var48, 0.3);
        IGradientColorSampler var66 = AuraRenderMath.evaluate((p0, p1) -> ShardsOrbitRenderer.lambda$a(var46, var63, p0, p1), arg4);
        double[] var86 = GeometryPrimitives.computeVector(0.0, -0.01, 0.0);
        double var132 = (var35 * 1.18);
        double[] var106 = GeometryPrimitives.computeVector(0.0, var132, 0.0);
        double var116 = (var43 * 1.6);
        GeometryPrimitives.drawGeometryPart(arg0, var86, var106, var116, arg3, var66);
        double[] var89 = GeometryPrimitives.computeVector(0.0, -0.01, 0.0);
        double var138 = (-(var39) * 1.18);
        double[] var109 = GeometryPrimitives.computeVector(0.0, var138, 0.0);
        double var122 = (var43 * 1.6);
        GeometryPrimitives.drawGeometryPart(arg0, var89, var109, var122, arg3, var66);
    }

}
