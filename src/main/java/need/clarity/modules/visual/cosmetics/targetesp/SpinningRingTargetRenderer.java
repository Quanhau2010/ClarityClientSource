package need.clarity.modules.visual.cosmetics.targetesp;

import java.util.function.DoubleUnaryOperator;
import need.clarity.modules.visual.cosmetics.ColorMathUtils;
import need.clarity.modules.visual.cosmetics.CosmeticDrawBuilder;
import need.clarity.modules.visual.cosmetics.GeometryPrimitives;
import need.clarity.modules.visual.cosmetics.ICosmeticEffectRenderer;
import need.clarity.modules.visual.cosmetics.IGradientColorSampler;

public final class SpinningRingTargetRenderer implements ICosmeticEffectRenderer {
    private static final double CONST_DBL = 0.42;
    private static final double CONST_DBL1 = 0.78;
    private static final double CONST_DBL2 = 0.83;
    private static final int CONST_INT = 12;
    private final TargetEspRenderSpec targetEspRenderSpec;

    public SpinningRingTargetRenderer() {
        TargetEspRenderSpec var2 = TargetEspRenderSpec.evaluate();
        this(var2);
    }

    public SpinningRingTargetRenderer(TargetEspRenderSpec arg0) {
        this.targetEspRenderSpec = arg0;
    }

    public void renderEffect(CosmeticDrawBuilder arg0) {
        double var96 = 0.0;
        double var98 = 0.0;
        double var234 = 0.0;
        double var248 = 0.0;
        if (arg0.enabledState) {
            var96 = arg0.doubleValue;
        } else {
            var96 = 0.0;
        }
        double var24 = var96;
        double var47 = this.targetEspRenderSpec.age();
        double var51 = this.targetEspRenderSpec.computeDoubleMetric1();
        double var55 = (1.0 - var47);
        double var103 = this.targetEspRenderSpec.phase();
        double var59 = (6.283185307179586 * var103);
        double var63 = this.targetEspRenderSpec.computeDoubleMetric();
        if (arg0.enabledState) {
            double var107 = this.targetEspRenderSpec.height();
            double var69 = (0.1 * var107);
            double var113 = Math.sin((var24 * 0.8));
            var98 = (var69 * var113);
        } else {
            var98 = 0.0;
        }
        double var36 = var98;
        double var75 = (this.targetEspRenderSpec.width() * 1.1);
        double var119 = Math.cos(0.83);
        double var77 = (var75 / var119);
        double var123 = this.targetEspRenderSpec.width();
        double var147 = (0.12 * var51);
        double var125 = (var123 + var147);
        double var79 = Math.max(var77, var125);
        double var83 = this.targetEspRenderSpec.width();
        double var129 = (0.3 * var51);
        double var85 = (var83 + var129);
        double var153 = (0.16 * var51);
        double var133 = (var79 + var153);
        double var87 = Math.max(var85, var133);
        double var137 = (var55 * 0.8);
        double var91 = (1.0 + var137);
        double var159 = (var87 * var91);
        double var171 = (0.026 * var51);
        if (arg0.enabledState) {
            var248 = Math.sin((var24 * 0.9));
        } else {
            var248 = 0.0;
        }
        double var193 = (0.06 * var248);
        double var181 = (0.42 + var193);
        double var197 = (var55 * 1.1);
        double var183 = (var181 + var197);
        double var201 = (var59 * 0.12);
        int var264 = arg0.counterValue1;
        int var267 = arg0.counterValue;
        this.drawGeometryPart(arg0, var36, var159, var171, var183, var201, var59, var63, var264, var267, true, 32, 4, var47);
        double var143 = -(var36);
        double var163 = (var79 * var91);
        double var177 = (0.023 * var51);
        if (arg0.enabledState) {
            var234 = Math.sin(((var24 * 1.3) + 1.0));
        } else {
            var234 = 0.0;
        }
        double var205 = (0.05 * var234);
        double var189 = (0.78 + var205);
        double var209 = (var55 * 1.1);
        double var191 = (var189 + var209);
        double var240 = (var59 * 0.2);
        double var213 = (1.2 - var240);
        double var246 = (-(var59) * 1.35);
        double var262 = -(var63);
        int var266 = arg0.counterValue;
        int var269 = arg0.counterValue1;
        this.drawGeometryPart(arg0, var143, var163, var177, var191, var213, var246, var262, var266, var269, false, 0, 3, var47);
    }

    private void drawGeometryPart(CosmeticDrawBuilder arg0, double arg1, double arg2, double arg3, double arg4, double arg5, double arg6, double arg7, int arg8, int arg9, boolean arg10, int arg11, int arg12, double arg13) {
        int var243 = 0;
        int var55 = 0;
        CosmeticDrawBuilder var67 = arg0.drawGeometryPart();
        CosmeticDrawBuilder var69 = arg0.drawGeometryPart(0.0, arg1, 0.0);
        CosmeticDrawBuilder var71 = arg0.drawGeometryPart2(arg5);
        CosmeticDrawBuilder var73 = arg0.drawGeometryPart3(arg4);
        CosmeticDrawBuilder var75 = arg0.drawGeometryPart2(arg6);
        double[] var77 = arg0.computeVector1();
        double var79 = arg0.computeDoubleMetric();
        double var83 = this.targetEspRenderSpec.computeDoubleMetric1();
        DoubleUnaryOperator var87 = (p0) -> SpinningRingTargetRenderer.lambda$a(arg7, p0);
        DoubleUnaryOperator var89 = (p0) -> SpinningRingTargetRenderer.lambda$a(var77, arg2, var79, p0);
        if (arg10) {
            var243 = 40;
        } else {
            var243 = 32;
        }
        IGradientColorSampler var252 = (p0, p1) -> SpinningRingTargetRenderer.lambda$b(var87, arg8, arg9, var89, arg13, p0, p1);
        GeometryPrimitives.drawGeometryPart(arg0, 0.0, arg2, arg3, var243, arg12, var252);
        if (arg10) {
            double var222 = (arg3 * 3.0);
            IGradientColorSampler var254 = (p0, p1) -> SpinningRingTargetRenderer.lambda$a(var87, arg8, arg9, var89, arg13, p0, p1);
            GeometryPrimitives.drawGeometryPart(arg0, 0.0, arg2, var222, arg11, 3, var254);
            double var95 = (0.075 * var83);
            double var99 = (arg3 * 0.95);
            var55 = 0;
            while (true) {
                if (var55 >= 12) break;
                double var108 = (((double) var55) / 12.0);
                double var112 = (6.283185307179586 * var108);
                double var115 = var87.applyAsDouble(var108);
                double var118 = var89.applyAsDouble(var108);
                double var167 = (arg3 * 0.8);
                double var122 = (arg2 + var167);
                double var171 = Math.cos(var112);
                double var124 = (var122 * var171);
                double var225 = (arg3 * 0.8);
                double var202 = (arg2 + var225);
                double var229 = Math.sin(var112);
                double var204 = (var202 * var229);
                double[] var126 = GeometryPrimitives.computeVector(var124, 0.0, var204);
                double var131 = ((arg2 + arg3) + var95);
                double var181 = Math.cos(var112);
                double var133 = (var131 * var181);
                double var210 = ((arg2 + arg3) + var95);
                double var237 = Math.sin(var112);
                double var212 = (var210 * var237);
                double[] var135 = GeometryPrimitives.computeVector(var133, 0.0, var212);
                int var157 = ColorMathUtils.computeColorOrIndex7(arg9, 0.5);
                double var216 = (0.6 * var115);
                double var189 = (0.4 + var216);
                IGradientColorSampler var242 = (p0, p1) -> SpinningRingTargetRenderer.lambda$a(ColorMathUtils.computeColorOrIndex(arg8, var157, var189), var118, arg13, p0, p1);
                GeometryPrimitives.drawGeometryPart(arg0, var126, var135, var99, 3, var242);
                var55 = (var55 + 1);
            }
        }
        CosmeticDrawBuilder var104 = arg0.drawGeometryPart1();
    }

}
