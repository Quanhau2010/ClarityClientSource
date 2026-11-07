package need.clarity.modules.visual.cosmetics.hats;

import java.util.function.DoubleUnaryOperator;
import need.clarity.modules.visual.cosmetics.ColorMathUtils;
import need.clarity.modules.visual.cosmetics.CosmeticDrawBuilder;
import need.clarity.modules.visual.cosmetics.GeometryPrimitives;
import need.clarity.modules.visual.cosmetics.ICosmeticEffectRenderer;
import need.clarity.modules.visual.cosmetics.IGradientColorSampler;

public final class FoxEarsHatRenderer implements ICosmeticEffectRenderer {
    private static final double CONST_DBL = 6.283185307179586;
    private static final int CONST_INT = -15331057;
    private final HatRenderSpec hatRenderSpec;

    public FoxEarsHatRenderer() {
        HatRenderSpec var2 = HatRenderSpec.evaluate();
        this(var2);
    }

    public FoxEarsHatRenderer(HatRenderSpec arg0) {
        this.hatRenderSpec = arg0;
    }

    public void renderEffect(CosmeticDrawBuilder arg0) {
        int var22 = 0;
        int var8 = 0;
        double var10 = 0.0;
        if (arg0.enabledState) {
            var10 = arg0.doubleValue;
        } else {
            var10 = 0.0;
        }
        double var6 = var10;
        var8 = 0;
        while (true) {
            if (var8 >= 2) break;
            if (var8 == 1) {
                var22 = 1;
            } else {
                var22 = 0;
            }
            CosmeticDrawBuilder var15 = arg0.drawGeometryPart(var22);
            int var5 = var8;
            this.drawGeometryPart(arg0, var6, var5);
            var8 = (var8 + 1);
        }
        CosmeticDrawBuilder var18 = arg0.drawGeometryPart(false);
    }

    private void drawGeometryPart(CosmeticDrawBuilder arg0, double arg1, int arg2) {
        double var97 = 0.0;
        int var34 = 0;
        int var39 = 0;
        double var50 = 0.0;
        int var43 = arg0.counterValue;
        int var45 = arg0.counterValue1;
        if (arg0.enabledState) {
            double var195 = (((double) arg2) * 1.1);
            double var147 = (5.3 + var195);
            double var200 = (((double) arg2) * 0.55);
            var97 = HatWaveMath.computeDoubleMetric(arg1, var147, var200, 0.14);
        } else {
            var97 = 0.0;
        }
        double var26 = var97;
        if (arg0.enabledState) {
            double var139 = (arg1 * 0.9);
            double var190 = (((double) arg2) * 2.3);
            double var143 = Math.sin((var139 + var190));
            var50 = (0.04 * var143);
        } else {
            var50 = 0.0;
        }
        double var28 = var50;
        CosmeticDrawBuilder var53 = arg0.drawGeometryPart();
        CosmeticDrawBuilder var55 = arg0.drawGeometryPart(0.135, -0.012, -0.01);
        double var151 = (0.34 * var26);
        double var103 = ((-0.14 + var151) + var28);
        CosmeticDrawBuilder var57 = arg0.drawGeometryPart4(var103);
        double var107 = (-0.1 * var26);
        CosmeticDrawBuilder var59 = arg0.drawGeometryPart3(var107);
        double[][] var110 = new double[3][];
        double[] var173 = GeometryPrimitives.computeVector(0.0, 0.0, 0.0);
        var110[0] = var173;
        double[] var176 = GeometryPrimitives.computeVector(0.03, 0.19, -0.02);
        var110[1] = var176;
        double[] var179 = GeometryPrimitives.computeVector(0.085, 0.38, -0.055);
        var110[2] = var179;
        double[][] var61 = GeometryPrimitives.computeMatrix(6, var110);
        int var63 = ColorMathUtils.computeColorOrIndex7(var45, 0.55);
        IGradientColorSampler var65 = (p0, p1) -> FoxEarsHatRenderer.lambda$b(var43, p0, p1);
        IGradientColorSampler var67 = (p0, p1) -> FoxEarsHatRenderer.lambda$a(var63, p0, p1);
        DoubleUnaryOperator var124 = (p0) -> FoxEarsHatRenderer.lambda$b(p0);
        DoubleUnaryOperator var160 = (p0) -> FoxEarsHatRenderer.lambda$a(p0);
        EarLobedMeshBuilder.drawGeometryPart(arg0, var61, var124, var160, 8, var65, var67, 0.68, 0.5, 0.66);
        var34 = 0;
        while (true) {
            if (var34 >= 3) break;
            int var115 = var34;
            int var74 = (1 + var115);
            double var78 = (((double) var74) / 6.0);
            double[] var81 = var61[var74];
            var39 = -1;
            while (true) {
                if (var39 > 1) break;
                double var131 = Math.pow((1.0 - var78), 0.85);
                double var88 = ((0.088 * var131) * 0.42);
                double var121 = (((double) var39) * var88);
                double[] var91 = GeometryPrimitives.computeVector(var81, var121, 0.0, 0.018000000000000002);
                double var169 = (((double) -(var39)) * 0.006);
                double[] var136 = GeometryPrimitives.computeVector(var91, var169, 0.034, 0.008);
                int var218 = ColorMathUtils.computeColorOrIndex6(var63, 0.9);
                int var233 = ColorMathUtils.computeColorOrIndex7(var63, 0.6);
                IGradientColorSampler var219 = GeometryPrimitives.evaluate(var218, var233);
                GeometryPrimitives.drawGeometryPart(arg0, var91, var136, 0.009, 3, var219);
                var39 = (var39 + 2);
            }
            var34 = (var34 + 1);
        }
        CosmeticDrawBuilder var72 = arg0.drawGeometryPart1();
    }

}
