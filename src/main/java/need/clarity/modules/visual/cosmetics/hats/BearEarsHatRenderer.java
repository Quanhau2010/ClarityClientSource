package need.clarity.modules.visual.cosmetics.hats;

import need.clarity.modules.visual.cosmetics.CosmeticDrawBuilder;
import need.clarity.modules.visual.cosmetics.GeometryPrimitives;
import need.clarity.modules.visual.cosmetics.ICosmeticEffectRenderer;
import need.clarity.modules.visual.cosmetics.IGradientColorSampler;

public final class BearEarsHatRenderer implements ICosmeticEffectRenderer {
    private static final double CONST_DBL = 6.283185307179586;
    private final HatRenderSpec hatRenderSpec;

    public BearEarsHatRenderer() {
        HatRenderSpec var2 = HatRenderSpec.evaluate();
        this(var2);
    }

    public BearEarsHatRenderer(HatRenderSpec arg0) {
        this.hatRenderSpec = arg0;
    }

    public void renderEffect(CosmeticDrawBuilder arg0) {
        double var18 = 0.0;
        int var21 = 0;
        int var8 = 0;
        if (arg0.enabledState) {
            var18 = arg0.doubleValue;
        } else {
            var18 = 0.0;
        }
        double var6 = var18;
        var8 = 0;
        while (true) {
            if (var8 >= 2) break;
            if (var8 == 1) {
                var21 = 1;
            } else {
                var21 = 0;
            }
            CosmeticDrawBuilder var13 = arg0.drawGeometryPart(var21);
            int var5 = var8;
            this.drawGeometryPart(arg0, var6, var5);
            var8 = (var8 + 1);
        }
        CosmeticDrawBuilder var16 = arg0.drawGeometryPart(false);
    }

    private void drawGeometryPart(CosmeticDrawBuilder arg0, double arg1, int arg2) {
        double var37 = 0.0;
        int var30 = arg0.counterValue;
        int var32 = arg0.counterValue1;
        if (arg0.enabledState) {
            double var77 = (((double) arg2) * 0.45);
            var37 = HatWaveMath.computeDoubleMetric(arg1, 4.7, var77, 0.1);
        } else {
            var37 = 0.0;
        }
        double var28 = var37;
        CosmeticDrawBuilder var40 = arg0.drawGeometryPart();
        CosmeticDrawBuilder var42 = arg0.drawGeometryPart(0.18, -0.01, -0.005);
        double var65 = (0.22 * var28);
        double var53 = (-0.35 + var65);
        CosmeticDrawBuilder var44 = arg0.drawGeometryPart4(var53);
        double var69 = (0.06 * var28);
        double var57 = (1.0 + var69);
        CosmeticDrawBuilder var46 = arg0.drawGeometryPart(var57);
        IGradientColorSampler var97 = (p0, p1) -> BearEarsHatRenderer.lambda$b(var30, p0, p1);
        GeometryPrimitives.drawGeometryPart(arg0, 0.0, 0.024, 0.0, 0.125, 0.125, 0.06, 10, 5, var97);
        IGradientColorSampler var99 = (p0, p1) -> BearEarsHatRenderer.lambda$a(var32, p0, p1);
        GeometryPrimitives.drawGeometryPart(arg0, 0.0, 0.02, 0.032, 0.076, 0.076, 0.046, 8, 4, var99);
        CosmeticDrawBuilder var50 = arg0.drawGeometryPart1();
    }

}
