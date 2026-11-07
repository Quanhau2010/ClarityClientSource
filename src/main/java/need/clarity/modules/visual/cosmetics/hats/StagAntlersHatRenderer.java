package need.clarity.modules.visual.cosmetics.hats;

import need.clarity.modules.visual.cosmetics.CosmeticDrawBuilder;
import need.clarity.modules.visual.cosmetics.GeometryPrimitives;
import need.clarity.modules.visual.cosmetics.ICosmeticEffectRenderer;
import need.clarity.modules.visual.cosmetics.IGradientColorSampler;
import need.clarity.modules.visual.cosmetics.ISurfaceColorShader;
import need.clarity.modules.visual.cosmetics.ParametricSurfaceSpec;

public final class StagAntlersHatRenderer implements ICosmeticEffectRenderer {
    private static final double CONST_DBL = 6.283185307179586;
    private final HatRenderSpec hatRenderSpec;

    public StagAntlersHatRenderer() {
        HatRenderSpec var2 = HatRenderSpec.evaluate();
        this(var2);
    }

    public StagAntlersHatRenderer(HatRenderSpec arg0) {
        this.hatRenderSpec = arg0;
    }

    static ParametricSurfaceSpec evaluate() {
        ParametricSurfaceSpec var0 = new ParametricSurfaceSpec();
        var0.counterValue = 1;
        var0.counterValue1 = 3;
        var0.counterValue2 = 8;
        var0.counterValue3 = 4;
        var0.counterValue4 = 6;
        var0.doubleValue5 = 0.28;
        var0.doubleValue = 0.26;
        var0.doubleValue1 = 0.76;
        var0.doubleValue2 = 0.85;
        var0.doubleValue3 = 0.46;
        var0.doubleValue4 = 0.74;
        var0.doubleValue6 = 1.15;
        double[] var45 = new double[3];
        var45[0] = -0.42;
        var45[1] = 0.0;
        var45[2] = 0.9;
        double[] var46 = GeometryPrimitives.computeVector(var45);
        var0.coordsVector = var46;
        double[] var48 = new double[3];
        var48[0] = 0.0;
        var48[1] = 0.0;
        var48[2] = 1.0;
        var0.coordsVector1 = var48;
        double[] var50 = new double[3];
        var50[0] = 0.0;
        var50[1] = 0.45;
        var50[2] = -0.35;
        var0.coordsVector2 = var50;
        var0.enabledState = true;
        var0.doubleValue7 = 0.45;
        var0.doubleValue8 = 0.3;
        var0.counterValue5 = 7;
        return var0;
    }

    public void renderEffect(CosmeticDrawBuilder arg0) {
        int var64 = 0;
        int var29 = 0;
        double var31 = 0.0;
        if (arg0.enabledState) {
            var31 = arg0.doubleValue;
        } else {
            var31 = 0.0;
        }
        double var41 = ((HatWaveMath.computeDoubleMetric((var31 * 0.3)) * 1.4) - 0.2);
        int var44 = arg0.counterValue;
        int var46 = arg0.counterValue1;
        ISurfaceColorShader var48 = (p0, p1, p2) -> StagAntlersHatRenderer.lambda$a(var44, var46, var41, p0, p1, p2);
        var29 = 0;
        while (true) {
            if (var29 >= 2) break;
            if (var29 == 1) {
                var64 = 1;
            } else {
                var64 = 0;
            }
            CosmeticDrawBuilder var54 = arg0.drawGeometryPart(var64);
            ParametricSurfaceSpec var61 = evaluate();
            double[] var74 = GeometryPrimitives.computeVector(0.12, -0.02, -0.03);
            double[] var75 = new double[3];
            var75[0] = 0.3;
            var75[1] = 1.0;
            var75[2] = -0.22;
            GeometryPrimitives.drawGeometryPart(arg0, var61, var74, var75, 0.5, 0.036, var48);
            IGradientColorSampler var92 = (p0, p1) -> StagAntlersHatRenderer.lambda$a(var44, p0, p1);
            GeometryPrimitives.drawGeometryPart(arg0, 0.12, -0.005, -0.03, 0.052, 0.036, 0.052, 8, 4, var92);
            var29 = (var29 + 1);
        }
        CosmeticDrawBuilder var52 = arg0.drawGeometryPart(false);
    }

}
