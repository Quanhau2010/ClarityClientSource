package need.clarity.modules.visual.cosmetics.hats;

import need.clarity.modules.visual.cosmetics.CosmeticDrawBuilder;
import need.clarity.modules.visual.cosmetics.GeometryPrimitives;
import need.clarity.modules.visual.cosmetics.ICosmeticEffectRenderer;
import need.clarity.modules.visual.cosmetics.IGradientColorSampler;

public final class ClassicRingHaloRenderer implements ICosmeticEffectRenderer {
    static final double CONST_DBL = 0.3;
    static final double CONST_DBL1 = 0.3;
    static final double CONST_DBL2 = 0.022;
    static final int CONST_INT = 36;
    static final int CONST_INT1 = 6;
    private final HatRenderSpec hatRenderSpec;

    public ClassicRingHaloRenderer() {
        HatRenderSpec var2 = HatRenderSpec.evaluate();
        this(var2);
    }

    public ClassicRingHaloRenderer(HatRenderSpec arg0) {
        this.hatRenderSpec = arg0;
    }

    private static double computeDoubleMetric(double arg0) {
        double var8 = Math.floor(arg0);
        return (arg0 - var8);
    }

    public void renderEffect(CosmeticDrawBuilder arg0) {
        double var85 = 0.0;
        double var89 = 0.0;
        double var91 = 0.0;
        double var93 = 0.0;
        double var95 = 0.0;
        if (arg0.enabledState) {
            var93 = arg0.doubleValue;
        } else {
            var93 = 0.0;
        }
        double var18 = var93;
        if (this.hatRenderSpec.bob()) {
            if (arg0.enabledState) {
                var89 = (Math.sin((var18 * 2.0)) * 0.018);
            }
        } else {
            var89 = 0.0;
        }
        double var20 = var89;
        if (arg0.enabledState) {
            double var139 = Math.sin((var18 * 1.6));
            double var113 = (0.02 * var139);
            var91 = (1.0 + var113);
        } else {
            var91 = 1.0;
        }
        double var22 = var91;
        if (arg0.enabledState) {
            var95 = (var18 * 0.1);
        } else {
            var95 = 0.0;
        }
        double var24 = var95;
        if (arg0.enabledState) {
            double var118 = this.hatRenderSpec.spin();
            var85 = (((var18 * var118) * 3.141592653589793) * 2.0);
        } else {
            var85 = 0.0;
        }
        double var26 = var85;
        int var55 = arg0.counterValue;
        int var57 = arg0.counterValue1;
        CosmeticDrawBuilder var59 = arg0.drawGeometryPart();
        double var126 = (0.3 + var20);
        CosmeticDrawBuilder var61 = arg0.drawGeometryPart(0.0, var126, 0.0);
        CosmeticDrawBuilder var63 = arg0.drawGeometryPart2(var26);
        CosmeticDrawBuilder var65 = arg0.drawGeometryPart(var22);
        IGradientColorSampler var68 = (p0, p1) -> ClassicRingHaloRenderer.lambda$a(var24, var55, var57, var18, p0, p1);
        GeometryPrimitives.drawGeometryPart(arg0, 0.0, 0.3, 0.022, 36, 6, var68);
        IGradientColorSampler var71 = (p0, p1) -> ClassicRingHaloRenderer.lambda$c(var68, p0, p1);
        IGradientColorSampler var73 = (p0, p1) -> ClassicRingHaloRenderer.lambda$b(var68, p0, p1);
        GeometryPrimitives.drawGeometryPart(arg0, 0.0, 0.18, 0.3, 36, var71);
        GeometryPrimitives.drawGeometryPart(arg0, 0.0, 0.3, 0.42, 36, var73);
        IGradientColorSampler var160 = (p0, p1) -> ClassicRingHaloRenderer.lambda$a(var68, p0, p1);
        GeometryPrimitives.drawGeometryPart(arg0, 0.0, 0.3, 0.0572, 28, 6, var160);
        CosmeticDrawBuilder var78 = arg0.drawGeometryPart1();
    }

}
