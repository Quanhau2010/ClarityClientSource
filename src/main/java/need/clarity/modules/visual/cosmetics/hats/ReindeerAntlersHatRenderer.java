package need.clarity.modules.visual.cosmetics.hats;

import java.util.ArrayList;
import java.util.function.BiConsumer;
import java.util.function.DoubleUnaryOperator;
import need.clarity.modules.visual.cosmetics.ColorMathUtils;
import need.clarity.modules.visual.cosmetics.CosmeticDrawBuilder;
import need.clarity.modules.visual.cosmetics.GeometryPrimitives;
import need.clarity.modules.visual.cosmetics.ICosmeticEffectRenderer;
import need.clarity.modules.visual.cosmetics.IGradientColorSampler;
import need.clarity.modules.visual.cosmetics.ISurfaceColorShader;
import need.clarity.modules.visual.cosmetics.ParametricSurfaceSpec;

public final class ReindeerAntlersHatRenderer implements ICosmeticEffectRenderer {
    private static final double CONST_DBL = 6.283185307179586;
    private final HatRenderSpec hatRenderSpec;

    public ReindeerAntlersHatRenderer() {
        HatRenderSpec var2 = HatRenderSpec.evaluate();
        this(var2);
    }

    public ReindeerAntlersHatRenderer(HatRenderSpec arg0) {
        this.hatRenderSpec = arg0;
    }

    public void renderEffect(CosmeticDrawBuilder arg0) {
        int var143 = 0;
        int var29 = 0;
        int var32 = 0;
        double var290 = 0.0;
        int var341 = 0;
        double var89 = 0.0;
        int var108 = 0;
        double var109 = 0.0;
        if (arg0.enabledState) {
            var109 = arg0.doubleValue;
        } else {
            var109 = 0.0;
        }
        double var22 = var109;
        int var41 = arg0.counterValue;
        int var43 = arg0.counterValue1;
        ParametricSurfaceSpec var44 = new ParametricSurfaceSpec();
        var44.counterValue = 1;
        var44.counterValue1 = 3;
        var44.counterValue2 = 9;
        var44.counterValue3 = 4;
        var44.counterValue4 = 5;
        var44.doubleValue5 = 0.32;
        var44.doubleValue = 0.38;
        var44.doubleValue1 = 0.88;
        var44.doubleValue2 = 0.7;
        var44.doubleValue3 = 0.36;
        var44.doubleValue4 = 0.8;
        var44.doubleValue6 = 1.7;
        double[] var131 = new double[3];
        var131[0] = 0.0;
        var131[1] = 0.1;
        var131[2] = 1.0;
        var44.coordsVector = var131;
        double[] var133 = new double[3];
        var133[0] = 0.0;
        var133[1] = 0.0;
        var133[2] = 1.0;
        var44.coordsVector1 = var133;
        double[] var135 = new double[3];
        var135[0] = 0.0;
        var135[1] = 0.35;
        var135[2] = -0.35;
        var44.coordsVector2 = var135;
        var44.doubleValue8 = 0.2;
        var44.counterValue5 = 3;
        ArrayList var62 = new ArrayList();
        BiConsumer var140 = (p0, p1) -> ReindeerAntlersHatRenderer.lambda$a(var62, p0, p1);
        var44.vertexConsumer = var140;
        ISurfaceColorShader var65 = (p0, p1, p2) -> ReindeerAntlersHatRenderer.lambda$a(var41, p0, p1, p2);
        var29 = 0;
        while (true) {
            if (var29 >= 2) break;
            if (var29 == 1) {
                var143 = 1;
            } else {
                var143 = 0;
            }
            CosmeticDrawBuilder var69 = arg0.drawGeometryPart(var143);
            var62.clear();
            double[] var174 = GeometryPrimitives.computeVector(0.12, -0.02, -0.03);
            double[] var196 = new double[3];
            var196[0] = 0.4;
            var196[1] = 0.85;
            var196[2] = -0.5;
            GeometryPrimitives.drawGeometryPart(arg0, var44, var174, var196, 0.62, 0.036, var65);
            double[][] var146 = new double[3][];
            double[] var242 = GeometryPrimitives.computeVector(0.115, -0.01, 0.05);
            var146[0] = var242;
            double[] var245 = GeometryPrimitives.computeVector(0.13, 0.04, 0.16);
            var146[1] = var245;
            double[] var248 = GeometryPrimitives.computeVector(0.13, 0.1, 0.24);
            var146[2] = var248;
            double[][] var73 = GeometryPrimitives.computeMatrix(6, var146);
            double[] var176 = new double[3];
            var176[0] = 0.0;
            var176[1] = 1.0;
            var176[2] = 0.0;
            DoubleUnaryOperator var200 = (p0) -> ReindeerAntlersHatRenderer.lambda$b(p0);
            DoubleUnaryOperator var252 = (p0) -> ReindeerAntlersHatRenderer.lambda$a(p0);
            IGradientColorSampler var307 = (p0, p1) -> ReindeerAntlersHatRenderer.lambda$c(var41, p0, p1);
            GeometryPrimitives.drawGeometryPart(arg0, var73, var176, var200, var252, 6, var307);
            double[] var76 = var73[6];
            var32 = -1;
            while (true) {
                if (var32 > 1) break;
                double var209 = (((double) var32) * 0.032);
                double[] var101 = GeometryPrimitives.computeVector(var76, 0.0, var209, -0.01);
                double var212 = ((double) var32);
                double var167 = (0.006 * var212);
                if (var32 == 0) {
                    var290 = 0.016;
                } else {
                    var290 = 0.0;
                }
                double var216 = (0.04 + var290);
                double[] var103 = GeometryPrimitives.computeVector(var101, var167, var216, 0.052);
                IGradientColorSampler var324 = (p0, p1) -> ReindeerAntlersHatRenderer.lambda$b(var41, p0, p1);
                GeometryPrimitives.drawGeometryPart(arg0, var101, var103, 0.011, 4, var324);
                var32 = (var32 + 1);
            }
            var341 = 0;
            while (true) {
                int var80 = var341;
                int var151 = var62.size();
                if (var80 >= var151) break;
                int var154 = var341;
                double[] var84 = ((double[]) var62.get(var154));
                if (arg0.enabledState) {
                    double var255 = (var22 * 3.0);
                    double var313 = (((double) var341) * 1.7);
                    double var257 = (var255 + var313);
                    double var318 = (((double) var29) * 0.9);
                    double var261 = Math.sin((var257 + var318));
                    double var179 = (0.5 * var261);
                    var89 = (0.5 + var179);
                } else {
                    var89 = 1.0;
                }
                double var34 = var89;
                if ((var341 % 2) == 0) {
                    var108 = ColorMathUtils.computeColorOrIndex7(var43, 0.25);
                } else {
                    var108 = ColorMathUtils.computeColorOrIndex7(-7512, 0.2);
                }
                int var36 = var108;
                double var265 = (0.3 * var34);
                double var183 = (0.85 + var265);
                double var96 = (0.02 * var183);
                double var159 = var84[0];
                double var204 = var84[1];
                double var280 = var84[2];
                IGradientColorSampler var339 = (p0, p1) -> ReindeerAntlersHatRenderer.lambda$a(var36, var34, p0, p1);
                GeometryPrimitives.drawGeometryPart(arg0, var159, var204, var280, var96, 5, 3, var339);
                var341 = (var341 + 1);
            }
            IGradientColorSampler var340 = (p0, p1) -> ReindeerAntlersHatRenderer.lambda$a(var41, p0, p1);
            GeometryPrimitives.drawGeometryPart(arg0, 0.12, -0.005, -0.03, 0.05, 0.034, 0.05, 6, 3, var340);
            var29 = (var29 + 1);
        }
        CosmeticDrawBuilder var106 = arg0.drawGeometryPart(false);
    }

}
