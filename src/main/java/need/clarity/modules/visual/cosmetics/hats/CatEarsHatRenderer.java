package need.clarity.modules.visual.cosmetics.hats;

import java.util.function.DoubleUnaryOperator;
import need.clarity.modules.visual.cosmetics.ColorMathUtils;
import need.clarity.modules.visual.cosmetics.CosmeticDrawBuilder;
import need.clarity.modules.visual.cosmetics.GeometryPrimitives;
import need.clarity.modules.visual.cosmetics.ICosmeticEffectRenderer;
import need.clarity.modules.visual.cosmetics.IGradientColorSampler;

public final class CatEarsHatRenderer implements ICosmeticEffectRenderer {
    private static final double CONST_DBL = 6.283185307179586;
    private final HatRenderSpec hatRenderSpec;

    public CatEarsHatRenderer() {
        HatRenderSpec var2 = HatRenderSpec.evaluate();
        this(var2);
    }

    public CatEarsHatRenderer(HatRenderSpec arg0) {
        this.hatRenderSpec = arg0;
    }

    public void renderEffect(CosmeticDrawBuilder arg0) {
        int var22 = 0;
        int var8 = 0;
        double var11 = 0.0;
        if (arg0.enabledState) {
            var11 = arg0.doubleValue;
        } else {
            var11 = 0.0;
        }
        double var6 = var11;
        var8 = 0;
        while (true) {
            if (var8 >= 2) break;
            if (var8 == 1) {
                var22 = 1;
            } else {
                var22 = 0;
            }
            CosmeticDrawBuilder var18 = arg0.drawGeometryPart(var22);
            int var5 = var8;
            this.drawGeometryPart(arg0, var6, var5);
            var8 = (var8 + 1);
        }
        CosmeticDrawBuilder var16 = arg0.drawGeometryPart(false);
    }

    private void drawGeometryPart(CosmeticDrawBuilder arg0, double arg1, int arg2) {
        double var78 = 0.0;
        int var36 = 0;
        double var47 = 0.0;
        int var38 = arg0.counterValue;
        int var40 = arg0.counterValue1;
        if (arg0.enabledState) {
            double var153 = ((((double) arg2) * 0.37) + 0.1);
            var78 = HatWaveMath.computeDoubleMetric(arg1, 4.3, var153, 0.09);
        } else {
            var78 = 0.0;
        }
        double var28 = var78;
        if (arg0.enabledState) {
            double var97 = (arg1 * 1.1);
            double var134 = (((double) arg2) * 1.7);
            double var101 = Math.sin((var97 + var134));
            var47 = (0.03 * var101);
        } else {
            var47 = 0.0;
        }
        double var30 = var47;
        CosmeticDrawBuilder var50 = arg0.drawGeometryPart();
        CosmeticDrawBuilder var52 = arg0.drawGeometryPart(0.145, -0.012, 0.005);
        double var115 = (0.3 * var28);
        double var84 = ((-0.16 + var115) + var30);
        CosmeticDrawBuilder var54 = arg0.drawGeometryPart4(var84);
        double var88 = (0.06 * var28);
        CosmeticDrawBuilder var56 = arg0.drawGeometryPart3(var88);
        double[][] var91 = new double[3][];
        double[] var138 = GeometryPrimitives.computeVector(0.0, 0.0, 0.0);
        var91[0] = var138;
        double[] var141 = GeometryPrimitives.computeVector(0.022, 0.12, 0.005);
        var91[1] = var141;
        double[] var144 = GeometryPrimitives.computeVector(0.055, 0.26, -0.012);
        var91[2] = var144;
        double[][] var58 = GeometryPrimitives.computeMatrix(4, var91);
        IGradientColorSampler var60 = (p0, p1) -> CatEarsHatRenderer.lambda$b(var38, p0, p1);
        IGradientColorSampler var62 = (p0, p1) -> CatEarsHatRenderer.lambda$a(var40, p0, p1);
        DoubleUnaryOperator var103 = (p0) -> CatEarsHatRenderer.lambda$b(p0);
        DoubleUnaryOperator var124 = (p0) -> CatEarsHatRenderer.lambda$a(p0);
        EarLobedMeshBuilder.drawGeometryPart(arg0, var58, var103, var124, 8, var60, var62, 0.7, 0.5, 0.8);
        int var65 = ColorMathUtils.computeColorOrIndex7(var40, 0.7);
        var36 = -1;
        while (true) {
            if (var36 > 1) break;
            double var105 = ((double) var36);
            double[] var74 = GeometryPrimitives.computeVector((0.016 * var105), 0.012, 0.034);
            double var159 = ((double) var36);
            double var127 = (0.008 * var159);
            double var197 = ((double) Math.abs(var36));
            double var184 = (0.008 * var197);
            double var163 = (0.05 - var184);
            double[] var110 = GeometryPrimitives.computeVector(var74, var127, var163, 0.012);
            IGradientColorSampler var175 = GeometryPrimitives.evaluate(ColorMathUtils.computeColorOrIndex6(var65, 0.8), var65);
            GeometryPrimitives.drawGeometryPart(arg0, var74, var110, 0.008, 3, var175);
            var36 = (var36 + 1);
        }
        CosmeticDrawBuilder var69 = arg0.drawGeometryPart1();
    }

}
