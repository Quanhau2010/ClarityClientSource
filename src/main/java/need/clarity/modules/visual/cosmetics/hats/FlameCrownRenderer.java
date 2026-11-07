package need.clarity.modules.visual.cosmetics.hats;

import java.util.function.DoubleUnaryOperator;
import need.clarity.modules.visual.cosmetics.ColorMathUtils;
import need.clarity.modules.visual.cosmetics.CosmeticDrawBuilder;
import need.clarity.modules.visual.cosmetics.GeometryPrimitives;
import need.clarity.modules.visual.cosmetics.ICosmeticEffectRenderer;
import need.clarity.modules.visual.cosmetics.IGradientColorSampler;

public final class FlameCrownRenderer implements ICosmeticEffectRenderer {
    static final double CONST_DBL = 0.262;
    static final double CONST_DBL1 = 0.285;
    static final double CONST_DBL2 = 0.075;
    static final double CONST_DBL3 = 0.06;
    static final int CONST_INT = 28;
    static final int CONST_INT1 = 7;
    static final int CONST_INT2 = 5;
    static final int CONST_INT3 = 12;
    static final double[] coordsVector;
    private static final double CONST_DBL4 = 6.283185307179586;
    private final HatRenderSpec hatRenderSpec;

    public FlameCrownRenderer() {
        HatRenderSpec var2 = HatRenderSpec.evaluate();
        this(var2);
    }

    public FlameCrownRenderer(HatRenderSpec arg0) {
        this.hatRenderSpec = arg0;
    }

    public void renderEffect(CosmeticDrawBuilder arg0) {
        double var130 = 0.0;
        double var259 = 0.0;
        int var271 = 0;
        int var27 = 0;
        int var429 = 0;
        int var430 = 0;
        if (arg0.enabledState) {
            var130 = arg0.doubleValue;
        } else {
            var130 = 0.0;
        }
        double var22 = var130;
        int var46 = arg0.counterValue;
        int var48 = arg0.counterValue1;
        int var50 = ColorMathUtils.computeColorOrIndex8(var48, 0.78);
        CosmeticDrawBuilder var52 = arg0.drawGeometryPart();
        if (this.hatRenderSpec.helmet()) {
            var259 = 0.06;
        } else {
            var259 = 0.0;
        }
        CosmeticDrawBuilder var54 = arg0.drawGeometryPart(0.0, var259, 0.0);
        IGradientColorSampler var415 = (p0, p1) -> FlameCrownRenderer.lambda$b(var22, var50, var48, p0, p1);
        GeometryPrimitives.drawGeometryPart(arg0, 0.0, 0.075, 0.262, 0.285, 28, var415, false, false);
        IGradientColorSampler var418 = (p0, p1) -> FlameCrownRenderer.lambda$a(var22, var48, var46, p0, p1);
        GeometryPrimitives.drawGeometryPart(arg0, 0.062, 0.079, 0.283, 0.29, 28, var418, false, false);
        IGradientColorSampler var420 = (p0, p1) -> FlameCrownRenderer.lambda$a(var48, p0, p1);
        GeometryPrimitives.drawGeometryPart(arg0, 0.0, 0.014, 0.265, 0.272, 28, var420, false, false);
        var27 = 0;
        while (true) {
            if (var27 >= 7) break;
            int var230 = var27;
            double var348 = ((((double) var27) * 6.283185307179586) / 7.0);
            double var257 = (1.5707963267948966 + var348);
            double[] var350 = coordsVector;
            int var377 = var27;
            double var351 = var350[var377];
            this.drawGeometryPart(arg0, var230, var257, var351, var22, var46, var48);
            var27 = (var27 + 1);
        }
        var429 = 0;
        while (true) {
            if (var429 >= 7) break;
            double var213 = (((((double) var429) + 0.5) * 6.283185307179586) / 7.0);
            double var112 = (1.5707963267948966 + var213);
            double var304 = (var22 * 7.3);
            double var371 = (((double) var429) * 2.3);
            double var308 = Math.sin((var304 + var371));
            double var217 = (0.15 * var308);
            double var116 = (0.85 + var217);
            double[] var120 = CrownJewelGeometry.computeVector(0.27299999999999996, var112, 0.071);
            double var223 = (var22 * 3.1);
            double var317 = (((double) var429) * 1.7);
            double var227 = Math.sin((var223 + var317));
            double var123 = (0.014 * var227);
            double var243 = Math.cos(var112);
            double var155 = (0.028 * var243);
            double var332 = Math.sin(var112);
            double var247 = (var123 * var332);
            double var157 = (var155 - var247);
            double var251 = (0.095 * var116);
            double var385 = Math.sin(var112);
            double var338 = (0.028 * var385);
            double var412 = Math.cos(var112);
            double var389 = (var123 * var412);
            double var340 = (var338 + var389);
            double[] var126 = GeometryPrimitives.computeVector(var120, var157, var251, var340);
            IGradientColorSampler var376 = (p0, p1) -> FlameCrownRenderer.lambda$a(var48, var46, p0, p1);
            GeometryPrimitives.drawGeometryPart(arg0, var120, var126, 0.03, 3, var376);
            var429 = (var429 + 1);
        }
        var430 = 0;
        while (true) {
            if (var430 >= 12) break;
            double var69 = GeometryPrimitives.computeDoubleMetric(((var430 * 7) + 3));
            double var354 = GeometryPrimitives.computeDoubleMetric((var430 + 41));
            double var261 = (0.1 * var354);
            double var161 = (0.2 + var261);
            double var77 = computeDoubleMetric(((var22 * var161) + var69));
            double var170 = ((((double) var430) * 6.283185307179586) / 12.0);
            double var81 = (1.5707963267948966 + var170);
            double var174 = (var22 * 0.7);
            if ((var430 % 2) == 0) {
                var271 = 1;
            } else {
                var271 = -1;
            }
            double var272 = ((double) var271);
            double var176 = (var174 * var272);
            double var83 = (var81 + var176);
            double var180 = (var77 * 1.4);
            double var85 = (var83 + var180);
            double var279 = GeometryPrimitives.computeDoubleMetric(((var430 * 5) + 11));
            double var184 = (0.19 * var279);
            double var89 = (0.07 + var184);
            double var188 = (0.03 * var77);
            double var91 = (var89 + var188);
            double var400 = GeometryPrimitives.computeDoubleMetric(((var430 * 3) + 17));
            double var358 = (0.24 * var400);
            double var285 = (0.3 + var358);
            double var192 = (var77 * var285);
            double var95 = (0.14500000000000002 + var192);
            double var101 = GeometryPrimitives.computeDoubleMetric((var77 * 7.0));
            double var289 = (var77 * var77);
            double var198 = (1.0 - var289);
            double var103 = (var101 * var198);
            int var108 = ColorMathUtils.computeColorOrIndex5(ColorMathUtils.computeColorOrIndex(ColorMathUtils.computeColorOrIndex7(var46, 0.55), var48, var77), var103);
            double[] var152 = CrownJewelGeometry.computeVector(var91, var85, var95);
            double var364 = (0.55 * var77);
            double var293 = (1.0 - var364);
            double var204 = (0.011 * var293);
            CrownJewelGeometry.drawGeometryPart(arg0, var152, var204, var108);
            var430 = (var430 + 1);
        }
        CosmeticDrawBuilder var65 = arg0.drawGeometryPart1();
    }

    private static double computeDoubleMetric(double arg0) {
        double var8 = Math.floor(arg0);
        return (arg0 - var8);
    }

    private void drawGeometryPart(CosmeticDrawBuilder arg0, int arg1, double arg2, double arg3, double arg4, int arg5, int arg6) {
        int var57 = 0;
        double[] var114 = null;
        double var181 = (arg4 * 6.3);
        double var283 = (((double) arg1) * 1.9);
        double var185 = Math.sin((var181 + var283));
        double var122 = (0.14 * var185);
        double var189 = (arg4 * 3.7);
        double var290 = (((double) arg1) * 0.7);
        double var193 = Math.sin((var189 + var290));
        double var124 = (var122 * var193);
        double var58 = (0.86 + var124);
        double var197 = (arg4 * 11.0);
        double var297 = (((double) arg1) * 2.7);
        double var201 = Math.sin((var197 + var297));
        double var128 = (0.05 * var201);
        double var60 = (var58 + var128);
        double var132 = Math.max(0.55, var60);
        double var64 = (arg3 * var132);
        double var136 = (0.05 * arg3);
        double var68 = (0.035 + var136);
        double var140 = (arg4 * 2.1);
        double var212 = (((double) arg1) * 1.3);
        double var144 = Math.sin((var140 + var212));
        double var72 = (0.03 * var144);
        double var216 = (arg4 * 3.6);
        double var306 = (((double) arg1) * 0.9);
        double var220 = Math.sin((var216 + var306));
        double var148 = (0.02 * var220);
        double var74 = (var72 + var148);
        double var78 = Math.cos(arg2);
        double var82 = Math.sin(arg2);
        double var86 = -(var82);
        double[] var92 = CrownJewelGeometry.computeVector(0.26499999999999996, arg2, 0.071);
        double[][] var115 = new double[4][];
        var115[0] = var92;
        double var247 = ((var78 * var68) * 0.15);
        double var321 = ((var86 * var74) * 0.2);
        double var249 = (var247 + var321);
        double var325 = (var64 * 0.35);
        double var384 = ((var82 * var68) * 0.15);
        double var423 = ((var78 * var74) * 0.2);
        double var386 = (var384 + var423);
        double[] var226 = GeometryPrimitives.computeVector(var92, var249, var325, var386);
        var115[1] = var226;
        double var255 = ((var78 * var68) * 0.6);
        double var335 = ((var86 * var74) * 0.7);
        double var257 = (var255 + var335);
        double var339 = (var64 * 0.72);
        double var398 = ((var82 * var68) * 0.6);
        double var433 = ((var78 * var74) * 0.7);
        double var400 = (var398 + var433);
        double[] var228 = GeometryPrimitives.computeVector(var92, var257, var339, var400);
        var115[2] = var228;
        double var261 = (var78 * var68);
        double var345 = (var86 * var74);
        double var263 = (var261 + var345);
        double var406 = (var82 * var68);
        double var439 = (var78 * var74);
        double var408 = (var406 + var439);
        double[] var230 = GeometryPrimitives.computeVector(var92, var263, var64, var408);
        var115[3] = var230;
        double[][] var94 = GeometryPrimitives.computeMatrix(5, var115);
        if ((arg1 % 2) == 0) {
            var114 = GeometryPrimitives.computeVector(var86, 0.0, var78);
        } else {
            var114 = GeometryPrimitives.computeVector(var78, 0.0, var82);
        }
        double[] var52 = var114;
        double var156 = (0.05 * arg3);
        double var101 = (0.058 + var156);
        DoubleUnaryOperator var169 = (p0) -> FlameCrownRenderer.lambda$d(var101, p0);
        DoubleUnaryOperator var237 = (p0) -> FlameCrownRenderer.lambda$c(var101, p0);
        IGradientColorSampler var309 = (p0, p1) -> FlameCrownRenderer.lambda$a(arg6, arg5, arg4, arg1, p0, p1);
        GeometryPrimitives.drawGeometryPart(arg0, var94, var52, var169, var237, 5, var309);
        double[][] var107 = new double[var94.length][];
        var57 = 0;
        while (true) {
            int var109 = var57;
            int var119 = var94.length;
            if (var109 >= var119) break;
            int var121 = var57;
            int var241 = var57;
            double var175 = var94[var241][0];
            double var268 = var92[0];
            double var179 = ((var175 - var268) * 0.6);
            int var313 = var57;
            double var274 = var94[var313][1];
            double var353 = var92[1];
            double var278 = ((var274 - var353) * 0.6);
            int var374 = var57;
            double var359 = var94[var374][2];
            double var412 = var92[2];
            double var363 = ((var359 - var412) * 0.6);
            double[] var161 = GeometryPrimitives.computeVector(var92, var179, var278, var363);
            var107[var121] = var161;
            var57 = (var57 + 1);
        }
        DoubleUnaryOperator var172 = (p0) -> FlameCrownRenderer.lambda$b(var101, p0);
        DoubleUnaryOperator var240 = (p0) -> FlameCrownRenderer.lambda$a(var101, p0);
        IGradientColorSampler var311 = (p0, p1) -> FlameCrownRenderer.lambda$a(arg5, arg4, arg1, p0, p1);
        GeometryPrimitives.drawGeometryPart(arg0, var107, var52, var172, var240, 4, var311);
    }

    static {
        double[] var4 = new double[7];
        var4[0] = 0.34;
        var4[1] = 0.22;
        var4[2] = 0.29;
        var4[3] = 0.2;
        var4[4] = 0.2;
        var4[5] = 0.29;
        var4[6] = 0.22;
        coordsVector = var4;
    }

}
