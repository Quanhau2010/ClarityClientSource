package need.clarity.modules.visual.cosmetics.hats;

import java.util.function.DoubleUnaryOperator;
import need.clarity.modules.visual.cosmetics.ColorMathUtils;
import need.clarity.modules.visual.cosmetics.CosmeticDrawBuilder;
import need.clarity.modules.visual.cosmetics.GeometryPrimitives;
import need.clarity.modules.visual.cosmetics.ICosmeticEffectRenderer;
import need.clarity.modules.visual.cosmetics.IGradientColorSampler;

public final class VikingHornsHatRenderer implements ICosmeticEffectRenderer {
    private static final double CONST_DBL = 6.283185307179586;
    private final HatRenderSpec hatRenderSpec;

    public VikingHornsHatRenderer() {
        HatRenderSpec var2 = HatRenderSpec.evaluate();
        this(var2);
    }

    public VikingHornsHatRenderer(HatRenderSpec arg0) {
        this.hatRenderSpec = arg0;
    }

    public void renderEffect(CosmeticDrawBuilder arg0) {
        int var78 = 0;
        int var26 = 0;
        double var57 = 0.0;
        if (arg0.enabledState) {
            var57 = arg0.doubleValue;
        } else {
            var57 = 0.0;
        }
        double var18 = var57;
        int var31 = arg0.counterValue;
        int var33 = arg0.counterValue1;
        int var35 = ColorMathUtils.computeColorOrIndex8(var33, 0.45);
        int var37 = ColorMathUtils.computeColorOrIndex7(var33, 0.2);
        IGradientColorSampler var40 = (p0, p1) -> VikingHornsHatRenderer.lambda$a(var18, var35, var37, p0, p1);
        GeometryPrimitives.drawGeometryPart(arg0, -0.105, -0.025, 0.345, 4, 0.7853981633974483, var40, false);
        IGradientColorSampler var43 = (p0, p1) -> VikingHornsHatRenderer.lambda$a(var37, p0, p1);
        GeometryPrimitives.drawGeometryPart(arg0, -0.113, -0.1, 0.352, 4, 0.7853981633974483, var43, false);
        GeometryPrimitives.drawGeometryPart(arg0, -0.03, -0.017, 0.352, 4, 0.7853981633974483, var43, false);
        double[][] var80 = new double[3][];
        double[] var129 = GeometryPrimitives.computeVector(0.0, -0.03, -0.245);
        var80[0] = var129;
        double[] var132 = GeometryPrimitives.computeVector(0.0, 0.13, 0.0);
        var80[1] = var132;
        double[] var135 = GeometryPrimitives.computeVector(0.0, -0.03, 0.245);
        var80[2] = var135;
        double[][] var68 = GeometryPrimitives.computeMatrix(10, var80);
        DoubleUnaryOperator var81 = (p0) -> VikingHornsHatRenderer.lambda$b(p0);
        IGradientColorSampler var106 = (p0, p1) -> VikingHornsHatRenderer.lambda$b(var35, var37, p0, p1);
        GeometryPrimitives.drawGeometryPart(arg0, var68, var81, 5, var106, false, false);
        var26 = 0;
        while (true) {
            if (var26 >= 2) break;
            if (var26 == 1) {
                var78 = 1;
            } else {
                var78 = 0;
            }
            CosmeticDrawBuilder var52 = arg0.drawGeometryPart(var78);
            double[] var74 = GeometryPrimitives.computeVector(0.236, -0.065, 0.0);
            double[] var85 = GeometryPrimitives.computeVector(0.3, -0.065, 0.0);
            IGradientColorSampler var170 = (p0, p1) -> VikingHornsHatRenderer.lambda$a(var35, var37, p0, p1);
            GeometryPrimitives.drawGeometryPart(arg0, var74, var85, 0.068, 0.056, 8, var170, true);
            double[][] var76 = new double[4][];
            double[] var111 = GeometryPrimitives.computeVector(0.29, -0.065, 0.0);
            var76[0] = var111;
            double[] var114 = GeometryPrimitives.computeVector(0.54, -0.09, 0.0);
            var76[1] = var114;
            double[] var117 = GeometryPrimitives.computeVector(0.66, 0.05, 0.03);
            var76[2] = var117;
            double[] var120 = GeometryPrimitives.computeVector(0.55, 0.34, 0.08);
            var76[3] = var120;
            double[][] var55 = GeometryPrimitives.computeMatrix(12, var76);
            DoubleUnaryOperator var86 = (p0) -> VikingHornsHatRenderer.lambda$a(p0);
            IGradientColorSampler var122 = (p0, p1) -> VikingHornsHatRenderer.lambda$a(var31, var18, p0, p1);
            GeometryPrimitives.drawGeometryPart(arg0, var55, var86, 6, var122, false, true);
            var26 = (var26 + 1);
        }
        CosmeticDrawBuilder var50 = arg0.drawGeometryPart(false);
    }

}
