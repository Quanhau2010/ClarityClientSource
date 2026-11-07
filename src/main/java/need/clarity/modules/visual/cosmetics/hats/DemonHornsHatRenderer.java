package need.clarity.modules.visual.cosmetics.hats;

import java.util.function.DoubleUnaryOperator;
import need.clarity.modules.visual.cosmetics.ColorMathUtils;
import need.clarity.modules.visual.cosmetics.CosmeticDrawBuilder;
import need.clarity.modules.visual.cosmetics.GeometryPrimitives;
import need.clarity.modules.visual.cosmetics.ICosmeticEffectRenderer;
import need.clarity.modules.visual.cosmetics.IGradientColorSampler;

public final class DemonHornsHatRenderer implements ICosmeticEffectRenderer {
    static final int CONST_INT = 10;
    static final int CONST_INT1 = 6;
    private static final double CONST_DBL = 6.283185307179586;
    private final HatRenderSpec hatRenderSpec;

    public DemonHornsHatRenderer() {
        HatRenderSpec var2 = HatRenderSpec.evaluate();
        this(var2);
    }

    public DemonHornsHatRenderer(HatRenderSpec arg0) {
        this.hatRenderSpec = arg0;
    }

    public void renderEffect(CosmeticDrawBuilder arg0) {
        int var32 = 0;
        double var16 = 0.0;
        int var14 = 0;
        if (arg0.enabledState) {
            var16 = arg0.doubleValue;
        } else {
            var16 = 0.0;
        }
        double var10 = var16;
        double var41 = Math.sin((var10 * 2.2));
        double var34 = (0.5 * var41);
        double var20 = (0.5 + var34);
        var14 = 0;
        while (true) {
            if (var14 >= 2) break;
            if (var14 == 1) {
                var32 = 1;
            } else {
                var32 = 0;
            }
            CosmeticDrawBuilder var27 = arg0.drawGeometryPart(var32);
            int var45 = var14;
            this.drawGeometryPart(arg0, var10, var20, var45);
            var14 = (var14 + 1);
        }
        CosmeticDrawBuilder var25 = arg0.drawGeometryPart(false);
    }

    private void drawGeometryPart(CosmeticDrawBuilder arg0, double arg1, double arg2, int arg3) {
        int var37 = 0;
        int var44 = arg0.counterValue;
        int var46 = arg0.counterValue1;
        double[][] var97 = new double[4][];
        double[] var205 = GeometryPrimitives.computeVector(0.13, -0.03, 0.03);
        var97[0] = var205;
        double[] var208 = GeometryPrimitives.computeVector(0.2, 0.13, 0.02);
        var97[1] = var208;
        double[] var211 = GeometryPrimitives.computeVector(0.37, 0.27, 0.05);
        var97[2] = var211;
        double[] var214 = GeometryPrimitives.computeVector(0.22, 0.45, 0.13);
        var97[3] = var214;
        double[][] var48 = GeometryPrimitives.computeMatrix(10, var97);
        double[] var50 = var48[10];
        IGradientColorSampler var52 = (p0, p1) -> DemonHornsHatRenderer.lambda$a(var44, var46, arg2, p0, p1);
        DoubleUnaryOperator var130 = (p0) -> DemonHornsHatRenderer.lambda$a(p0);
        GeometryPrimitives.drawGeometryPart(arg0, var48, var130, 6, var52, false, true);
        double[] var55 = GeometryPrimitives.computeVector(var48, 0);
        double[] var57 = var48[0];
        double var108 = (-(var55[0]) * 0.05);
        double var178 = (-(var55[1]) * 0.05);
        double var239 = (-(var55[2]) * 0.05);
        double[] var58 = GeometryPrimitives.computeVector(var57, var108, var178, var239);
        double[] var60 = var48[0];
        double var114 = (var55[0] * 0.045);
        double var185 = (var55[1] * 0.045);
        double var246 = (var55[2] * 0.045);
        double[] var61 = GeometryPrimitives.computeVector(var60, var114, var185, var246);
        IGradientColorSampler var307 = (p0, p1) -> DemonHornsHatRenderer.lambda$a(ColorMathUtils.computeColorOrIndex8(var44, 0.6), p0, p1);
        GeometryPrimitives.drawGeometryPart(arg0, var58, var61, 0.076, 0.062, 8, var307, true);
        double var136 = (0.2 * arg2);
        double var67 = (1.0 + var136);
        double var120 = var50[0];
        double var192 = (var50[1] + 0.006);
        double var253 = var50[2];
        double var288 = (0.026 * var67);
        IGradientColorSampler var321 = (p0, p1) -> DemonHornsHatRenderer.lambda$b(var46, arg2, p0, p1);
        GeometryPrimitives.drawGeometryPart(arg0, var120, var192, var253, var288, 8, 4, var321);
        var37 = 0;
        while (true) {
            if (var37 >= 3) break;
            double var74 = (arg1 * 0.55);
            double var144 = (((double) var37) / 3.0);
            double var76 = (var74 + var144);
            double var149 = (((double) arg3) * 0.17);
            double var80 = HatWaveMath.computeDoubleMetric((var76 + var149));
            double var84 = (var80 * 5.0);
            double var156 = (((double) var37) * 2.0);
            double var86 = (var84 + var156);
            double var90 = (1.0 - var80);
            double var229 = (var80 * 6.0);
            double var162 = Math.min(1.0, var229);
            double var94 = ((var90 * var162) * 0.9);
            double var124 = var50[0];
            double var257 = Math.sin(var86);
            double var196 = (0.02 * var257);
            double var126 = (var124 + var196);
            double var201 = (var50[1] + 0.03);
            double var263 = (var80 * 0.2);
            double var203 = (var201 + var263);
            double var266 = var50[2];
            double var313 = Math.cos(var86);
            double var294 = (0.02 * var313);
            double var268 = (var266 + var294);
            double var324 = (0.5 * var80);
            double var317 = (1.0 - var324);
            double var298 = (0.011 * var317);
            IGradientColorSampler var327 = (p0, p1) -> DemonHornsHatRenderer.lambda$a(var46, var94, p0, p1);
            GeometryPrimitives.drawGeometryPart(arg0, var126, var203, var268, var298, 4, 2, var327);
            var37 = (var37 + 1);
        }
    }

}
