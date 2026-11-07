package need.clarity.modules.visual.cosmetics.hats;

import need.clarity.modules.visual.cosmetics.ColorMathUtils;
import need.clarity.modules.visual.cosmetics.CosmeticDrawBuilder;
import need.clarity.modules.visual.cosmetics.GeometryPrimitives;
import need.clarity.modules.visual.cosmetics.ICosmeticEffectRenderer;
import need.clarity.modules.visual.cosmetics.IGradientColorSampler;

public final class ChinaHatRenderer implements ICosmeticEffectRenderer {
    static final double CONST_DBL = 0.9375;
    static final double CONST_DBL1 = 0.65;
    static final double CONST_DBL2 = 0.3;
    static final double CONST_DBL3 = 0.22;
    static final double CONST_DBL4 = 0.28;
    static final int CONST_INT = 40;
    static final double CONST_DBL5 = 0.012;
    private final HatRenderSpec hatRenderSpec;
    public boolean enabledState;
    public boolean enabledState1;

    public ChinaHatRenderer() {
        HatRenderSpec var2 = HatRenderSpec.evaluate();
        this(var2);
    }

    public ChinaHatRenderer(HatRenderSpec arg0) {
        this.enabledState = true;
        this.enabledState1 = true;
        this.hatRenderSpec = arg0;
    }

    static int computeColorOrIndex(double arg0, int[] arg1) {
        int var16 = arg1.length;
        if (var16 == 1) {
            return arg1[0];
        }
        double var26 = (((arg0 % 360.0) + 360.0) % 360.0);
        double var56 = ((double) var16);
        double var30 = (360.0 / var56);
        int var37 = (((int) (var26 / var30)) % var16);
        int var40 = ((var37 + 1) % var16);
        int var42 = arg1[var37];
        int var50 = arg1[var40];
        double var65 = ((var26 % var30) / var30);
        return ColorMathUtils.computeColorOrIndex(var42, var50, var65);
    }

    private static double computeDoubleMetric(double arg0) {
        return ((arg0 - 0.25) * 360.0);
    }

    public void renderEffect(CosmeticDrawBuilder arg0) {
        double var68 = 0.0;
        double var70 = 0.0;
        if (this.hatRenderSpec.helmet()) {
            var70 = 0.28;
        } else {
            var70 = 0.22;
        }
        double var16 = var70;
        if (arg0.enabledState) {
            double var30 = arg0.doubleValue;
            double var83 = this.hatRenderSpec.chinaSpin();
            var68 = Math.toRadians(((var30 * var83) * 360.0));
        } else {
            var68 = 0.0;
        }
        double var18 = var68;
        int[] var38 = this.hatRenderSpec.gradient();
        double var43 = ColorMathUtils.computeDoubleMetric(this.hatRenderSpec.edgeAlpha());
        double var49 = ColorMathUtils.computeDoubleMetric(this.hatRenderSpec.centerAlpha());
        CosmeticDrawBuilder var52 = arg0.drawGeometryPart();
        CosmeticDrawBuilder var54 = arg0.drawGeometryPart(0.9375);
        double var87 = (var16 - 0.3);
        CosmeticDrawBuilder var56 = arg0.drawGeometryPart(0.0, var87, 0.0);
        CosmeticDrawBuilder var58 = arg0.drawGeometryPart2(var18);
        IGradientColorSampler var105 = (p0, p1) -> ChinaHatRenderer.lambda$b(var38, var43, var49, p0, p1);
        GeometryPrimitives.drawGeometryPart(arg0, 0.0, 0.65, 0.3, 40, var105, false);
        if (this.enabledState1) {
            IGradientColorSampler var107 = (p0, p1) -> ChinaHatRenderer.lambda$a(var38, var43, var49, p0, p1);
            GeometryPrimitives.drawGeometryPart(arg0, -0.0015, 0.6474, 0.3, 40, var107, false);
        }
        if (this.enabledState) {
            IGradientColorSampler var114 = (p0, p1) -> ChinaHatRenderer.lambda$a(var38, p0, p1);
            GeometryPrimitives.drawGeometryPart(arg0, 0.0, 0.65, 0.012, 40, 4, var114);
        }
        CosmeticDrawBuilder var67 = arg0.drawGeometryPart1();
    }

}
