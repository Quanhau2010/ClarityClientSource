package need.clarity.modules.visual.kenny;

import com.mojang.blaze3d.vertex.VertexConsumer;

public final class KennyCharacterRenderer {
    private static final double[] coordsVector;
    private static final int[] intBuffer;
    private static final double[] coordsVector1;

    private KennyCharacterRenderer() {
    }

    public static void applyStep(VertexConsumer arg0, double[] arg1, double[] arg2, double[][] arg3, float arg4) {
        int var130 = 0;
        int var140 = 0;
        int var141 = 0;
        int var14 = 0;
        int var18 = 0;
        int var22 = 0;
        double var19 = computeDoubleMetric(arg1, 4);
        double var89 = computeDoubleMetric(arg1, 5);
        if (Double.compare(var19, var89) >= 0) {
            var22 = 4;
        } else {
            var22 = 5;
        }
        int var11 = var22;
        applyStep(arg0, arg1, arg2, arg3, var11, arg4);
        if (var11 == 4) {
            var130 = 5;
        } else {
            var130 = 4;
        }
        applyStep(arg0, arg1, arg2, arg3, var130, arg4);
        int[] var25 = intBuffer;
        double[] var26 = coordsVector1;
        var25[0] = 0;
        var25[1] = 2;
        var25[2] = 3;
        var14 = 0;
        while (true) {
            if (var14 >= 3) break;
            int var66 = var14;
            int var131 = var14;
            int var123 = var25[var131];
            double var97 = computeDoubleMetric(arg1, var123);
            var26[var66] = var97;
            var14 = (var14 + 1);
        }
        var140 = 1;
        while (true) {
            if (var140 >= 3) break;
            int var68 = var140;
            int var36 = var25[var68];
            int var69 = var140;
            double var38 = var26[var69];
            var18 = (var140 - 1);
            while (true) {
                if (var18 < 0) break;
                int var71 = var18;
                if (Double.compare(var26[var71], var38) >= 0) break;
                int var77 = (var18 + 1);
                int var124 = var18;
                int var108 = var25[var124];
                var25[var77] = var108;
                int var79 = (var18 + 1);
                int var125 = var18;
                double var111 = var26[var125];
                var26[var79] = var111;
                var18 = (var18 + -1);
            }
            int var73 = (var18 + 1);
            var25[var73] = var36;
            int var75 = (var18 + 1);
            var26[var75] = var38;
            var140 = (var140 + 1);
        }
        var141 = 0;
        while (true) {
            if (var141 >= 3) break;
            int var138 = var141;
            int var134 = var25[var138];
            applyStep(arg0, arg1, arg2, arg3, var134, arg4);
            var141 = (var141 + 1);
        }
        applyStep(arg0, arg1, arg2, arg3, 1, arg4);
        double[] var83 = arg3[1];
        if (isEligible(arg1, var83)) {
            double[] var85 = arg3[1];
            double[] var116 = coordsVector;
            Matrix4x4Math.applyStep(arg1, var85, var116);
            double[] var86 = coordsVector;
            double[] var117 = coordsVector;
            Matrix4x4Math.applyStep(arg2, var86, var117);
            CompiledMeshPart var58 = KennyModelParts.evaluate();
            double[] var118 = coordsVector;
            var58.applyStep(arg0, var118, arg4);
        }
    }

    private static void applyStep(VertexConsumer arg0, double[] arg1, double[] arg2, double[][] arg3, int arg4, float arg5) {
        double[] var13 = arg3[arg4];
        double[] var16 = coordsVector;
        Matrix4x4Math.applyStep(arg1, var13, var16);
        double[] var14 = coordsVector;
        double[] var17 = coordsVector;
        Matrix4x4Math.applyStep(arg2, var14, var17);
        CompiledMeshPart var12 = KennyModelParts.evaluate(arg4);
        double[] var18 = coordsVector;
        var12.applyStep(arg0, var18, arg5);
    }

    private static double computeDoubleMetric(double[] arg0, int arg1) {
        double[] var16 = KennyModelParts.vertexMatrix[arg1];
        double var35 = var16[0];
        double var55 = var16[1];
        double var69 = var16[2];
        double var18 = Matrix4x4Math.computeDoubleMetric(arg0, var35, var55, var69);
        double var38 = var16[0];
        double var58 = var16[1];
        double var72 = var16[2];
        double var21 = Matrix4x4Math.computeDoubleMetric1(arg0, var38, var58, var72);
        double var41 = var16[0];
        double var61 = var16[1];
        double var75 = var16[2];
        double var24 = Matrix4x4Math.computeDoubleMetric2(arg0, var41, var61, var75);
        double var28 = (var18 * var18);
        double var49 = (var21 * var21);
        double var30 = (var28 + var49);
        double var53 = (var24 * var24);
        return (var30 + var53);
    }

    private static boolean isEligible(double[] arg0, double[] arg1) {
        int var41 = 0;
        double[] var4 = coordsVector;
        Matrix4x4Math.applyStep(arg0, arg1, var4);
        double var13 = coordsVector[0];
        double var45 = coordsVector[0];
        double var15 = (var13 * var45);
        double var48 = coordsVector[4];
        double var81 = coordsVector[4];
        double var50 = (var48 * var81);
        double var17 = (var15 + var50);
        double var53 = coordsVector[8];
        double var84 = coordsVector[8];
        double var55 = (var53 * var84);
        double var19 = (var17 + var55);
        if (Double.compare(var19, 1.0E-12) < 0) {
            return true;
        }
        double var26 = coordsVector[2];
        double var60 = coordsVector[3];
        double var28 = (var26 * var60);
        double var63 = coordsVector[6];
        double var87 = coordsVector[7];
        double var65 = (var63 * var87);
        double var30 = (var28 + var65);
        double var68 = coordsVector[10];
        double var90 = coordsVector[11];
        double var70 = (var68 * var90);
        if (Double.compare((-((var30 + var70)) / var19), 0.3) >= 0) {
            var41 = 1;
        } else {
            var41 = 0;
        }
        return var41;
    }

    static {
        coordsVector = new double[12];
        intBuffer = new int[3];
        coordsVector1 = new double[3];
    }

}
