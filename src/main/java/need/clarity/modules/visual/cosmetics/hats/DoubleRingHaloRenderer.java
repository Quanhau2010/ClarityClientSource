package need.clarity.modules.visual.cosmetics.hats;

import need.clarity.modules.visual.cosmetics.ColorMathUtils;
import need.clarity.modules.visual.cosmetics.CosmeticDrawBuilder;
import need.clarity.modules.visual.cosmetics.GeometryPrimitives;
import need.clarity.modules.visual.cosmetics.ICosmeticEffectRenderer;
import need.clarity.modules.visual.cosmetics.IGradientColorSampler;

public final class DoubleRingHaloRenderer implements ICosmeticEffectRenderer {
    static final double CONST_DBL = 0.2;
    static final double CONST_DBL1 = 0.33;
    static final double CONST_DBL2 = 0.024;
    static final double CONST_DBL3 = 0.37;
    static final double CONST_DBL4 = 0.24;
    static final double CONST_DBL5 = 0.016;
    static final int CONST_INT = 4;
    private static final double CONST_DBL6 = 6.283185307179586;
    private final HatRenderSpec hatRenderSpec;

    public DoubleRingHaloRenderer() {
        HatRenderSpec var2 = HatRenderSpec.evaluate();
        this(var2);
    }

    public DoubleRingHaloRenderer(HatRenderSpec arg0) {
        this.hatRenderSpec = arg0;
    }

    private static double computeDoubleMetric(double arg0) {
        double var8 = Math.floor(arg0);
        return (arg0 - var8);
    }

    private static IGradientColorSampler evaluate(int arg0, int arg1, double arg2, double arg3, double arg4) {
        return (p0, p1) -> DoubleRingHaloRenderer.lambda$a(arg2, arg0, arg1, arg3, arg4, p0, p1);
    }

    public void renderEffect(CosmeticDrawBuilder arg0) {
        double var129 = 0.0;
        int var135 = 0;
        double var144 = 0.0;
        double var150 = 0.0;
        double var152 = 0.0;
        double var154 = 0.0;
        double var156 = 0.0;
        int var37 = 0;
        if (arg0.enabledState) {
            var150 = arg0.doubleValue;
        } else {
            var150 = 0.0;
        }
        double var20 = var150;
        if (this.hatRenderSpec.bob()) {
            if (arg0.enabledState) {
                var156 = (Math.sin((var20 * 1.9)) * 0.016);
            } else {
                var156 = 0.0;
            }
            double var22 = var156;
            if (arg0.enabledState) {
                double var276 = Math.sin((var20 * 1.5));
                double var228 = (0.02 * var276);
                var154 = (1.0 + var228);
            } else {
                var154 = 1.0;
            }
            double var24 = var154;
            if (arg0.enabledState) {
                var152 = (var20 * 0.1);
            } else {
                var152 = 0.0;
            }
            double var26 = var152;
            if (arg0.enabledState) {
                double var220 = this.hatRenderSpec.spin();
                var144 = ((var20 * var220) * 6.283185307179586);
            } else {
                var144 = 0.0;
            }
            double var28 = var144;
            int var72 = arg0.counterValue;
            int var74 = arg0.counterValue1;
            CosmeticDrawBuilder var76 = arg0.drawGeometryPart();
            CosmeticDrawBuilder var78 = arg0.drawGeometryPart(0.0, var22, 0.0);
            CosmeticDrawBuilder var80 = arg0.drawGeometryPart(var24);
            IGradientColorSampler var82 = evaluate(var72, var74, var26, 1.0, var20);
            double var191 = -(var26);
            IGradientColorSampler var84 = evaluate(var74, var72, var191, -1.0, var20);
            GeometryPrimitives.drawGeometryPart(arg0, 0.2, 0.33, 0.024, 36, 5, var82);
            GeometryPrimitives.drawGeometryPart(arg0, 0.37, 0.24, 0.016, 32, 4, var84);
            IGradientColorSampler var88 = (p0, p1) -> DoubleRingHaloRenderer.lambda$c(var82, p0, p1);
            IGradientColorSampler var90 = (p0, p1) -> DoubleRingHaloRenderer.lambda$b(var82, p0, p1);
            IGradientColorSampler var92 = (p0, p1) -> DoubleRingHaloRenderer.lambda$a(var84, p0, p1);
            GeometryPrimitives.drawGeometryPart(arg0, 0.2, 0.33, 0.43000000000000005, 36, var88);
            GeometryPrimitives.drawGeometryPart(arg0, 0.2, 0.25, 0.33, 36, var90);
            GeometryPrimitives.drawGeometryPart(arg0, 0.37, 0.24, 0.32, 32, var92);
            var37 = 0;
            while (true) {
                if (var37 >= 4) break;
                double var198 = ((((double) var37) * 6.283185307179586) / 4.0);
                double var102 = (var28 + var198);
                double var106 = Math.cos(var102);
                double var110 = Math.sin(var102);
                double var114 = (0.33 * var106);
                double var253 = (0.33 * var110);
                double[] var116 = GeometryPrimitives.computeVector(var114, 0.2, var253);
                double var119 = (0.24 * var106);
                double var257 = (0.24 * var110);
                double[] var121 = GeometryPrimitives.computeVector(var119, 0.37, var257);
                int var123 = ColorMathUtils.computeColorOrIndex6(var72, 0.9);
                int var125 = ColorMathUtils.computeColorOrIndex7(var74, 0.25);
                IGradientColorSampler var323 = GeometryPrimitives.evaluate(var123, var125);
                GeometryPrimitives.drawGeometryPart(arg0, var116, var121, 0.0065, 0.0055, 4, var323, false);
                if (arg0.enabledState) {
                    double var266 = (var20 * 1.6);
                    double var307 = (((double) var37) * 1.57);
                    double var270 = Math.sin((var266 + var307));
                    double var216 = (0.5 * var270);
                    var129 = (0.5 + var216);
                } else {
                    var129 = 0.5;
                }
                double var48 = var129;
                double var261 = (0.84 * var48);
                double var211 = (0.08 + var261);
                double[] var132 = GeometryPrimitives.computeVector(var116, var121, var211);
                if ((var37 % 2) == 0) {
                    var135 = var72;
                } else {
                    var135 = var74;
                }
                int var136 = ColorMathUtils.computeColorOrIndex7(var135, 0.4);
                double var183 = var132[0];
                double var243 = var132[1];
                double var291 = var132[2];
                IGradientColorSampler var330 = (p0, p1) -> DoubleRingHaloRenderer.lambda$a(var136, p0, p1);
                GeometryPrimitives.drawGeometryPart(arg0, var183, var243, var291, 0.021, 6, 4, var330);
                var37 = (var37 + 1);
            }
            CosmeticDrawBuilder var99 = arg0.drawGeometryPart1();
            return;
        }
    }

}
