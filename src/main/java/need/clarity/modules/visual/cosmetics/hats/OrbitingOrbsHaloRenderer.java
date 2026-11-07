package need.clarity.modules.visual.cosmetics.hats;

import need.clarity.modules.visual.cosmetics.ColorMathUtils;
import need.clarity.modules.visual.cosmetics.CosmeticDrawBuilder;
import need.clarity.modules.visual.cosmetics.GeometryPrimitives;
import need.clarity.modules.visual.cosmetics.ICosmeticEffectRenderer;
import need.clarity.modules.visual.cosmetics.IGradientColorSampler;

public final class OrbitingOrbsHaloRenderer implements ICosmeticEffectRenderer {
    static final int CONST_INT = 5;
    static final double CONST_DBL = 0.33;
    static final double CONST_DBL1 = 0.3;
    static final double CONST_DBL2 = 0.04;
    static final int CONST_INT1 = 8;
    static final double CONST_DBL3 = 0.85;
    static final double CONST_DBL4 = 2.5;
    private static final double CONST_DBL5 = 6.283185307179586;
    private final HatRenderSpec hatRenderSpec;

    public OrbitingOrbsHaloRenderer() {
        HatRenderSpec var2 = HatRenderSpec.evaluate();
        this(var2);
    }

    public OrbitingOrbsHaloRenderer(HatRenderSpec arg0) {
        this.hatRenderSpec = arg0;
    }

    private static double computeDoubleMetric(int arg0, double arg1) {
        double var13 = (arg1 * 1.7);
        double var22 = (((double) arg0) * 1.26);
        double var17 = Math.sin((var13 + var22));
        return (0.045 * var17);
    }

    public void renderEffect(CosmeticDrawBuilder arg0) {
        double var141 = 0.0;
        double var143 = 0.0;
        int var30 = 0;
        int var36 = 0;
        double var180 = 0.0;
        double var188 = 0.0;
        double var63 = 0.0;
        int var335 = 0;
        int var80 = 0;
        int var83 = 0;
        if (arg0.enabledState) {
            var141 = arg0.doubleValue;
        } else {
            var141 = 0.0;
        }
        double var20 = var141;
        if (this.hatRenderSpec.bob()) {
            if (arg0.enabledState) {
                var143 = (Math.sin((var20 * 1.8)) * 0.014);
            } else {
                var143 = 0.0;
            }
            double var22 = var143;
            double var166 = this.hatRenderSpec.spin();
            double var56 = ((6.283185307179586 * var166) * 2.5);
            if (Double.compare(this.hatRenderSpec.spin(), 0.0) < 0) {
                var63 = -1.0;
            } else {
                var63 = 1.0;
            }
            double var26 = var63;
            int var66 = arg0.counterValue;
            int var68 = arg0.counterValue1;
            CosmeticDrawBuilder var70 = arg0.drawGeometryPart();
            double var223 = (0.3 + var22);
            CosmeticDrawBuilder var72 = arg0.drawGeometryPart(0.0, var223, 0.0);
            IGradientColorSampler var310 = (p0, p1) -> OrbitingOrbsHaloRenderer.lambda$a(var66, var68, p0, p1);
            GeometryPrimitives.drawGeometryPart(arg0, 0.0, 0.322, 0.338, 36, var310);
            var30 = 0;
            while (true) {
                if (var30 >= 5) break;
                if ((var30 % 2) == 0) {
                    var80 = var66;
                } else {
                    var80 = var68;
                }
                int var31 = var80;
                if ((var30 % 2) == 0) {
                    var83 = var68;
                } else {
                    var83 = var66;
                }
                int var32 = var83;
                double var89 = ((((double) var30) * 6.283185307179586) / 5.0);
                if (arg0.enabledState) {
                    var180 = (var20 * var56);
                } else {
                    var180 = 0.0;
                }
                double var91 = (var89 + var180);
                double[][] var94 = new double[9][];
                var36 = 0;
                while (true) {
                    if (var36 > 8) break;
                    double var128 = (((double) var36) / 8.0);
                    double var212 = ((var26 * 0.85) * var128);
                    double var132 = (var91 - var212);
                    int var164 = var36;
                    double var261 = Math.cos(var132);
                    double var216 = (0.33 * var261);
                    int var263 = var30;
                    double var308 = (0.5 * var128);
                    double var281 = (var20 - var308);
                    double var264 = computeDoubleMetric(var263, var281);
                    double var321 = Math.sin(var132);
                    double var297 = (0.33 * var321);
                    double[] var218 = GeometryPrimitives.computeVector(var216, var264, var297);
                    var94[var164] = var218;
                    var36 = (var36 + 1);
                }
                var335 = 0;
                while (true) {
                    if (var335 >= 8) break;
                    int var109 = var335;
                    double var246 = (((double) var335) / 8.0);
                    double var192 = (1.0 - var246);
                    double var114 = ((0.022000000000000002 * var192) + 0.003);
                    double var252 = (((double) (var335 + 1)) / 8.0);
                    double var198 = (1.0 - var252);
                    double var120 = ((0.022000000000000002 * var198) + 0.003);
                    IGradientColorSampler var123 = (p0, p1) -> OrbitingOrbsHaloRenderer.lambda$a(var109, var31, var32, p0, p1);
                    int var203 = var335;
                    double[] var163 = var94[var203];
                    int var234 = (var335 + 1);
                    double[] var205 = var94[var234];
                    GeometryPrimitives.drawGeometryPart(arg0, var163, var205, var114, var120, 4, var123, false);
                    var335 = (var335 + 1);
                }
                double[] var100 = var94[0];
                if (arg0.enabledState) {
                    double var283 = (var20 * 3.0);
                    double var316 = (((double) var30) * 1.3);
                    double var287 = Math.sin((var283 + var316));
                    double var239 = (0.1 * var287);
                    var188 = (1.0 + var239);
                } else {
                    var188 = 1.0;
                }
                double var103 = (0.04 * var188);
                int var106 = ColorMathUtils.computeColorOrIndex7(var31, 0.35);
                double var156 = var100[0];
                double var228 = var100[1];
                double var271 = var100[2];
                IGradientColorSampler var332 = (p0, p1) -> OrbitingOrbsHaloRenderer.lambda$b(var106, p0, p1);
                GeometryPrimitives.drawGeometryPart(arg0, var156, var228, var271, var103, 8, 4, var332);
                double var159 = var100[0];
                double var231 = var100[1];
                double var274 = var100[2];
                double var303 = (var103 * 2.0);
                IGradientColorSampler var334 = (p0, p1) -> OrbitingOrbsHaloRenderer.lambda$a(var31, p0, p1);
                GeometryPrimitives.drawGeometryPart(arg0, var159, var231, var274, var303, 8, 4, var334);
                var30 = (var30 + 1);
            }
            CosmeticDrawBuilder var77 = arg0.drawGeometryPart1();
            return;
        }
    }

}
