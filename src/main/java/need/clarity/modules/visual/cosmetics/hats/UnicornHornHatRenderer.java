package need.clarity.modules.visual.cosmetics.hats;

import need.clarity.modules.visual.cosmetics.ColorMathUtils;
import need.clarity.modules.visual.cosmetics.CosmeticDrawBuilder;
import need.clarity.modules.visual.cosmetics.GeometryPrimitives;
import need.clarity.modules.visual.cosmetics.ICosmeticEffectRenderer;
import need.clarity.modules.visual.cosmetics.IGradientColorSampler;

public final class UnicornHornHatRenderer implements ICosmeticEffectRenderer {
    static final double CONST_DBL = 0.56;
    static final double CONST_DBL1 = 0.062;
    static final double CONST_DBL2 = 0.32;
    private static final double CONST_DBL3 = 6.283185307179586;
    private final HatRenderSpec hatRenderSpec;

    public UnicornHornHatRenderer() {
        HatRenderSpec var2 = HatRenderSpec.evaluate();
        this(var2);
    }

    public UnicornHornHatRenderer(HatRenderSpec arg0) {
        this.hatRenderSpec = arg0;
    }

    public void renderEffect(CosmeticDrawBuilder arg0) {
        double var134 = 0.0;
        int var136 = 0;
        int var38 = 0;
        int var52 = 0;
        if (arg0.enabledState) {
            var134 = arg0.doubleValue;
        } else {
            var134 = 0.0;
        }
        double var29 = var134;
        int var55 = arg0.counterValue;
        int var57 = arg0.counterValue1;
        double var257 = Math.sin((var29 * 2.0));
        double var170 = (0.5 * var257);
        double var60 = (0.5 + var170);
        CosmeticDrawBuilder var63 = arg0.drawGeometryPart();
        CosmeticDrawBuilder var65 = arg0.drawGeometryPart(0.0, -0.03, 0.17);
        CosmeticDrawBuilder var67 = arg0.drawGeometryPart3(0.32);
        IGradientColorSampler var357 = (p0, p1) -> UnicornHornHatRenderer.lambda$a(var57, var55, var60, p0, p1);
        GeometryPrimitives.drawGeometryPart(arg0, 0.0, 0.062, 0.56, 3, 2.2, 0.16, 9, 12, var357);
        double[] var143 = GeometryPrimitives.computeVector(0.0, -0.02, 0.0);
        double[] var174 = GeometryPrimitives.computeVector(0.0, 0.05, 0.0);
        IGradientColorSampler var333 = (p0, p1) -> UnicornHornHatRenderer.lambda$b(-1592772, p0, p1);
        GeometryPrimitives.drawGeometryPart(arg0, var143, var174, 0.072, 0.066, 8, var333, true);
        double[] var146 = GeometryPrimitives.computeVector(0.0, 0.05, 0.0);
        double[] var177 = GeometryPrimitives.computeVector(0.0, 0.062, 0.0);
        IGradientColorSampler var335 = (p0, p1) -> UnicornHornHatRenderer.lambda$a(-1592772, p0, p1);
        GeometryPrimitives.drawGeometryPart(arg0, var146, var177, 0.074, 0.074, 8, var335, true);
        double var349 = (0.2 * var60);
        double var343 = (1.0 + var349);
        double var326 = (0.017 * var343);
        IGradientColorSampler var352 = (p0, p1) -> UnicornHornHatRenderer.lambda$a(var55, var60, p0, p1);
        GeometryPrimitives.drawGeometryPart(arg0, 0.0, 0.56, 0.0, var326, 8, 4, var352);
        double var180 = this.hatRenderSpec.spin();
        double var77 = ((var29 * var180) * 4.0);
        var38 = 0;
        while (true) {
            if (var38 >= 3) break;
            double var85 = (var29 * 0.33);
            double var189 = (((double) var38) / 3.0);
            double var89 = HatWaveMath.computeDoubleMetric((var85 + var189));
            double var193 = (var89 * 0.4600000000000001);
            double var93 = (0.06 + var193);
            double var271 = ((var89 * 6.283185307179586) * 2.0);
            double var316 = ((double) var38);
            double var275 = Math.sin((var271 + var316));
            double var197 = (0.03 * var275);
            double var97 = (0.1 + var197);
            double var101 = (var77 * 6.283185307179586);
            double var204 = (((double) var38) * 2.1);
            double var103 = (var101 + var204);
            double var208 = (var89 * 3.0);
            double var105 = (var103 + var208);
            double var214 = Math.sin((3.141592653589793 * var89));
            double var109 = (0.052 * var214);
            if (Double.compare(var109, 0.004) < 0) {
            } else {
                if (var38 == 1) {
                    var136 = var57;
                } else {
                    var136 = var55;
                }
                int var115 = ColorMathUtils.computeColorOrIndex7(var136, 0.75);
                double var239 = Math.sin((3.141592653589793 * var89));
                double var155 = (0.95 * var239);
                int var116 = ColorMathUtils.computeColorOrIndex5(var115, var155);
                double var119 = (var29 * 1.4);
                double var221 = ((double) var38);
                double var121 = (var119 + var221);
                var52 = 0;
                while (true) {
                    if (var52 >= 2) break;
                    CosmeticDrawBuilder var126 = arg0.drawGeometryPart();
                    double var243 = Math.cos(var105);
                    double var160 = (var97 * var243);
                    double var330 = Math.sin(var105);
                    double var301 = (var97 * var330);
                    CosmeticDrawBuilder var127 = var126.drawGeometryPart(var160, var93, var301);
                    double var252 = ((((double) var52) * 3.141592653589793) / 2.0);
                    double var164 = (var121 + var252);
                    CosmeticDrawBuilder var129 = var127.drawGeometryPart2(var164).drawGeometryPart3(1.5707963267948966);
                    double var320 = (var109 * 0.22);
                    IGradientColorSampler var348 = GeometryPrimitives.evaluate(var116);
                    GeometryPrimitives.drawGeometryPart(arg0, 0.0, 4, var109, var320, 0.7853981633974483, var348);
                    CosmeticDrawBuilder var132 = arg0.drawGeometryPart1();
                    var52 = (var52 + 1);
                }
            }
            var38 = (var38 + 1);
        }
        CosmeticDrawBuilder var82 = arg0.drawGeometryPart1();
    }

}
