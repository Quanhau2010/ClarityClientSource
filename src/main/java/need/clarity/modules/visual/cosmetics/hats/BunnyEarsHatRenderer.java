package need.clarity.modules.visual.cosmetics.hats;

import java.util.function.DoubleUnaryOperator;
import need.clarity.modules.visual.cosmetics.CosmeticDrawBuilder;
import need.clarity.modules.visual.cosmetics.GeometryPrimitives;
import need.clarity.modules.visual.cosmetics.ICosmeticEffectRenderer;
import need.clarity.modules.visual.cosmetics.IGradientColorSampler;

public final class BunnyEarsHatRenderer implements ICosmeticEffectRenderer {
    private static final double CONST_DBL = 6.283185307179586;
    private final HatRenderSpec hatRenderSpec;

    public BunnyEarsHatRenderer() {
        HatRenderSpec var2 = HatRenderSpec.evaluate();
        this(var2);
    }

    public BunnyEarsHatRenderer(HatRenderSpec arg0) {
        this.hatRenderSpec = arg0;
    }

    private static double computeDoubleMetric(double arg0) {
        double var26 = GeometryPrimitives.computeDoubleMetric((arg0 / 0.5));
        double var16 = (0.3 * var26);
        double var12 = (0.7 + var16);
        double var34 = Math.pow(arg0, 4.5);
        double var30 = (1.0 - var34);
        double var22 = Math.sqrt(Math.max(0.0, var30));
        return (var12 * var22);
    }

    public void renderEffect(CosmeticDrawBuilder arg0) {
        double var18 = 0.0;
        int var22 = 0;
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
                var22 = 1;
            } else {
                var22 = 0;
            }
            CosmeticDrawBuilder var14 = arg0.drawGeometryPart(var22);
            int var5 = var8;
            this.drawGeometryPart(arg0, var6, var5);
            var8 = (var8 + 1);
        }
        CosmeticDrawBuilder var17 = arg0.drawGeometryPart(false);
    }

    private void drawGeometryPart(CosmeticDrawBuilder arg0, double arg1, int arg2) {
        double var42 = 0.0;
        double var46 = 0.0;
        int var37 = arg0.counterValue;
        int var39 = arg0.counterValue1;
        if (arg0.enabledState) {
            double var121 = ((((double) arg2) * 0.5) + 0.2);
            var42 = HatWaveMath.computeDoubleMetric(arg1, 6.1, var121, 0.2);
        } else {
            var42 = 0.0;
        }
        double var30 = var42;
        if (arg0.enabledState) {
            double var65 = (arg1 * 1.2);
            double var84 = (((double) arg2) * 1.9);
            var46 = (Math.sin((var65 + var84)) * 0.012);
        } else {
            var46 = 0.0;
        }
        double var32 = var46;
        CosmeticDrawBuilder var49 = arg0.drawGeometryPart();
        CosmeticDrawBuilder var51 = arg0.drawGeometryPart(0.075, -0.012, -0.01);
        CosmeticDrawBuilder var53 = arg0.drawGeometryPart4(-0.1);
        double[][] var76 = new double[4][];
        double[] var95 = GeometryPrimitives.computeVector(0.0, 0.0, 0.0);
        var76[0] = var95;
        double[] var100 = GeometryPrimitives.computeVector((0.012 + var32), 0.2, -0.03);
        var76[1] = var100;
        double var103 = (0.055 + var32);
        double var132 = (0.05 * var30);
        double var105 = (var103 + var132);
        double var167 = (0.02 * var30);
        double var157 = (-0.05 - var167);
        double[] var107 = GeometryPrimitives.computeVector(var105, 0.42, var157);
        var76[2] = var107;
        double var138 = (0.03 * var30);
        double var110 = (0.115 + var138);
        double var163 = (0.05 * var30);
        double var142 = (0.5 - var163);
        double[] var112 = GeometryPrimitives.computeVector(var110, var142, 0.0);
        var76[3] = var112;
        double[][] var55 = GeometryPrimitives.computeMatrix(9, var76);
        IGradientColorSampler var57 = (p0, p1) -> BunnyEarsHatRenderer.lambda$b(var37, p0, p1);
        IGradientColorSampler var59 = (p0, p1) -> BunnyEarsHatRenderer.lambda$a(var39, p0, p1);
        DoubleUnaryOperator var78 = (p0) -> BunnyEarsHatRenderer.lambda$c(p0);
        DoubleUnaryOperator var94 = (p0) -> BunnyEarsHatRenderer.lambda$b(p0);
        EarLobedMeshBuilder.drawGeometryPart(arg0, var55, var78, var94, 8, var57, var59, 0.7, 0.5, 0.86);
        CosmeticDrawBuilder var62 = arg0.drawGeometryPart1();
    }

}
