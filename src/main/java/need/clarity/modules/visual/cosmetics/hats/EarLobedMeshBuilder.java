package need.clarity.modules.visual.cosmetics.hats;

import java.util.Arrays;
import java.util.function.DoubleUnaryOperator;
import need.clarity.modules.visual.cosmetics.CosmeticDrawBuilder;
import need.clarity.modules.visual.cosmetics.GeometryPrimitives;
import need.clarity.modules.visual.cosmetics.IGradientColorSampler;

public final class EarLobedMeshBuilder {
    private static final double[] coordsVector;

    private EarLobedMeshBuilder() {
    }

    static void drawGeometryPart(CosmeticDrawBuilder arg0, double[][] arg1, DoubleUnaryOperator arg2, DoubleUnaryOperator arg3, int arg4, IGradientColorSampler arg5, IGradientColorSampler arg6, double arg7, double arg8, double arg9) {
        double[] var15 = coordsVector;
        GeometryPrimitives.drawGeometryPart(arg0, arg1, var15, arg2, arg3, arg4, arg5);
        int var30 = arg1.length;
        double var69 = ((double) (arg1.length - 1));
        int var55 = ((int) Math.round((arg9 * var69)));
        int var45 = (Math.max(2, var55) + 1);
        int var31 = Math.min(var30, var45);
        double[][] var34 = ((double[][]) Arrays.copyOf(arg1, var31));
        double var37 = ((double) (var31 - 1));
        double var60 = ((double) (arg1.length - 1));
        double var39 = (var37 / var60);
        DoubleUnaryOperator var41 = (p0) -> EarLobedMeshBuilder.lambda$a(p0);
        double[] var62 = coordsVector;
        DoubleUnaryOperator var65 = (p0) -> EarLobedMeshBuilder.lambda$b(arg2, var39, arg7, var41, p0);
        DoubleUnaryOperator var74 = (p0) -> EarLobedMeshBuilder.lambda$a(arg3, var39, arg8, var41, p0);
        DoubleUnaryOperator var79 = (p0) -> EarLobedMeshBuilder.lambda$a(arg3, var39, arg8, p0);
        IGradientColorSampler var86 = (p0, p1) -> EarLobedMeshBuilder.lambda$a(arg6, var39, p0, p1);
        GeometryPrimitives.drawGeometryPart(arg0, var34, var62, var65, var74, var79, arg4, var86);
    }

    static {
        double[] var4 = new double[3];
        var4[0] = 1.0;
        var4[1] = 0.0;
        var4[2] = 0.0;
        coordsVector = var4;
    }

}
