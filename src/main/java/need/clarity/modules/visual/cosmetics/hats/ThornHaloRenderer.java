package need.clarity.modules.visual.cosmetics.hats;

import java.util.function.DoubleFunction;
import need.clarity.modules.visual.cosmetics.ColorMathUtils;
import need.clarity.modules.visual.cosmetics.CosmeticDrawBuilder;
import need.clarity.modules.visual.cosmetics.GeometryPrimitives;
import need.clarity.modules.visual.cosmetics.ICosmeticEffectRenderer;
import need.clarity.modules.visual.cosmetics.IGradientColorSampler;

public final class ThornHaloRenderer implements ICosmeticEffectRenderer {
    static final double CONST_DBL = 0.27;
    static final double CONST_DBL1 = 0.3;
    static final double CONST_DBL2 = 0.02;
    static final int CONST_INT = 48;
    static final int CONST_INT1 = 5;
    static final int CONST_INT2 = 16;
    private static final double CONST_DBL3 = 6.283185307179586;
    private final HatRenderSpec hatRenderSpec;

    public ThornHaloRenderer() {
        HatRenderSpec var2 = HatRenderSpec.evaluate();
        this(var2);
    }

    public ThornHaloRenderer(HatRenderSpec arg0) {
        this.hatRenderSpec = arg0;
    }

    private static double[] computeVector(int arg0, double arg1) {
        double var20 = ((((double) arg0) / 48.0) * 6.283185307179586);
        double var49 = Math.sin(((5.0 * var20) + arg1));
        double var35 = (0.014 * var49);
        double var24 = (0.3 + var35);
        double var39 = Math.cos(var20);
        double var28 = (var24 * var39);
        double var53 = (3.0 * var20);
        double var69 = (arg1 * 0.7);
        double var57 = Math.sin((var53 + var69));
        double var43 = (0.022 * var57);
        double var73 = Math.sin(var20);
        double var61 = (var24 * var73);
        return GeometryPrimitives.computeVector(var28, var43, var61);
    }

    private static double computeDoubleMetric(int arg0) {
        double var26 = (((double) arg0) / 48.0);
        double var22 = Math.sin(((7.0 * var26) * 6.283185307179586));
        double var16 = (0.18 * var22);
        double var14 = (1.0 + var16);
        return (0.02 * var14);
    }

    public void renderEffect(CosmeticDrawBuilder arg0) {
        double var128 = 0.0;
        double var130 = 0.0;
        int var132 = 0;
        double var133 = 0.0;
        int var281 = 0;
        int var31 = 0;
        double var162 = 0.0;
        double var179 = 0.0;
        double var58 = 0.0;
        double var96 = 0.0;
        double var124 = 0.0;
        if (arg0.enabledState) {
            var133 = arg0.doubleValue;
        } else {
            var133 = 0.0;
        }
        double var20 = var133;
        if (this.hatRenderSpec.bob()) {
            if (arg0.enabledState) {
                var58 = (Math.sin((var20 * 1.8)) * 0.016);
            } else {
                var58 = 0.0;
            }
            double var22 = var58;
            if (arg0.enabledState) {
                var128 = (var20 * 0.9);
            } else {
                var128 = 0.0;
            }
            double var24 = var128;
            if (arg0.enabledState) {
                double var188 = this.hatRenderSpec.spin();
                var124 = ((var20 * var188) * 6.283185307179586);
            } else {
                var124 = 0.0;
            }
            double var26 = var124;
            int var65 = arg0.counterValue;
            int var67 = arg0.counterValue1;
            CosmeticDrawBuilder var69 = arg0.drawGeometryPart();
            double var194 = (0.27 + var22);
            CosmeticDrawBuilder var71 = arg0.drawGeometryPart(0.0, var194, 0.0);
            CosmeticDrawBuilder var73 = arg0.drawGeometryPart2(var26);
            DoubleFunction var75 = (p0) -> ThornHaloRenderer.lambda$a(var65, var67, p0);
            var31 = 0;
            while (true) {
                if (var31 >= 48) break;
                IGradientColorSampler var118 = (p0, p1) -> ThornHaloRenderer.lambda$a(var31, var75, p0, p1);
                double[] var161 = computeVector(var31, var24);
                double[] var185 = computeVector((var31 + 1), var24);
                double var229 = computeDoubleMetric(var31);
                double var257 = computeDoubleMetric((var31 + 1));
                GeometryPrimitives.drawGeometryPart(arg0, var161, var185, var229, var257, 5, var118, false);
                var31 = (var31 + 1);
            }
            var281 = 0;
            while (true) {
                if (var281 >= 16) break;
                int var82 = ((var281 * 48) / 16);
                double var88 = ((((double) var82) / 48.0) * 6.283185307179586);
                if ((var281 % 2) == 0) {
                    var132 = 1;
                } else {
                    var132 = 0;
                }
                int var35 = var132;
                if (var35 != 0) {
                    var130 = 42.0;
                } else {
                    var130 = 12.0;
                }
                double var93 = Math.toRadians(var130);
                if (var35 != 0) {
                    var96 = 0.135;
                } else {
                    var96 = 0.085;
                }
                if (arg0.enabledState) {
                    double var263 = (var20 * 2.4);
                    double var276 = (((double) var281) * 0.9);
                    double var267 = Math.sin((var263 + var276));
                    double var233 = (0.1 * var267);
                    var179 = (1.0 + var233);
                } else {
                    var179 = 1.0;
                }
                double var98 = (var96 * var179);
                double[] var101 = computeVector(var82, var24);
                double[] var103 = new double[3];
                double var198 = Math.cos(var88);
                double var239 = Math.cos(var93);
                double var200 = (var198 * var239);
                var103[0] = var200;
                double var204 = Math.sin(var93);
                var103[1] = var204;
                double var208 = Math.sin(var88);
                double var243 = Math.cos(var93);
                double var210 = (var208 * var243);
                var103[2] = var210;
                double var148 = (var103[0] * var98);
                double var217 = (var103[1] * var98);
                double var250 = (var103[2] * var98);
                double[] var105 = GeometryPrimitives.computeVector(var101, var148, var217, var250);
                double var153 = (((double) var82) / 48.0);
                int var109 = ((Integer) var75.apply(var153));
                if (var35 != 0) {
                    var162 = 0.65;
                } else {
                    var162 = 0.5;
                }
                int var111 = ColorMathUtils.computeColorOrIndex7(var67, var162);
                IGradientColorSampler var260 = (p0, p1) -> ThornHaloRenderer.lambda$a(var109, var111, p0, p1);
                GeometryPrimitives.drawGeometryPart(arg0, var101, var105, 0.021, 4, var260);
                var281 = (var281 + 1);
            }
            IGradientColorSampler var279 = (p0, p1) -> ThornHaloRenderer.lambda$a(var75, p0, p1);
            GeometryPrimitives.drawGeometryPart(arg0, -0.012, 0.27999999999999997, 0.44, 36, var279);
            CosmeticDrawBuilder var115 = arg0.drawGeometryPart1();
            return;
        }
    }

}
