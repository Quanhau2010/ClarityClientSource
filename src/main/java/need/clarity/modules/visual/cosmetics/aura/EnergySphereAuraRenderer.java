package need.clarity.modules.visual.cosmetics.aura;

import need.clarity.modules.visual.cosmetics.CosmeticDrawBuilder;
import need.clarity.modules.visual.cosmetics.GeometryPrimitives;
import need.clarity.modules.visual.cosmetics.ICosmeticEffectRenderer;

public final class EnergySphereAuraRenderer implements ICosmeticEffectRenderer {
    public static final double CONST_DBL = 1.3;
    private static final double CONST_DBL1 = 3.2;
    private static final double CONST_DBL2 = 0.013;
    private final EnergySphereAuraSpec energySphereAuraSpec;

    public EnergySphereAuraRenderer() {
        EnergySphereAuraSpec var2 = EnergySphereAuraSpec.evaluate();
        this(var2);
    }

    public EnergySphereAuraRenderer(EnergySphereAuraSpec arg0) {
        this.energySphereAuraSpec = arg0;
    }

    public static int computeColorOrIndex(int arg0) {
        return GeodesicSphereTopology.evaluate(arg0).computeColorOrIndex();
    }

    public static int[] evaluate(int arg0) {
        int var7 = 0;
        int var8 = 0;
        int var11 = 0;
        int var12 = 0;
        int var15 = 0;
        int var88 = 0;
        GeodesicSphereTopology var17 = GeodesicSphereTopology.evaluate(arg0);
        int[] var21 = new int[var17.vertexMatrix1.length];
        var7 = 99;
        var8 = 0;
        int[][] var25 = var17.fieldVal;
        int var27 = var25.length;
        var11 = 0;
        while (true) {
            if (var11 < var27) {
                int var58 = var11;
                int[] var42 = var25[var58];
                int var43 = var7;
                int var60 = var42.length;
                var7 = Math.min(var43, var60);
                int var45 = var8;
                int var62 = var42.length;
                var8 = Math.max(var45, var62);
                int var49 = var42.length;
                var15 = 0;
                while (true) {
                    if (var15 >= var49) break;
                    int var64 = var15;
                    int var53 = var42[var64];
                    int var73 = (var21[var53] + 1);
                    var21[var53] = var73;
                    var15 = (var15 + 1);
                }
                var88 = 0;
                int var33 = var21.length;
                var12 = 0;
                while (true) {
                    if (var12 >= var33) break;
                    int var56 = var12;
                    if (var21[var56] != 3) {
                        var88 = (var88 + 1);
                    }
                    var12 = (var12 + 1);
                }
                int[] var40 = new int[7];
                int var75 = var17.vertexMatrix.length;
                var40[0] = var75;
                int var78 = var17.vertexMatrix1.length;
                var40[1] = var78;
                int var81 = var17.fieldVal1.length;
                var40[2] = var81;
                int var83 = var17.computeColorOrIndex1();
                var40[3] = var83;
                int var84 = var7;
                var40[4] = var84;
                int var85 = var8;
                var40[5] = var85;
                int var86 = var88;
                var40[6] = var86;
                return var40;
                var11 = (var11 + 1);
            }
        }
    }

    public void renderEffect(CosmeticDrawBuilder arg0) {
        int var15 = 0;
        int var17 = 0;
        int var26 = 0;
        int var83 = 0;
        int var394 = 0;
        int var404 = 0;
        GeodesicSphereTopology var34 = GeodesicSphereTopology.evaluate(this.energySphereAuraSpec.frequency());
        EnergySphereAuraSpec var196 = this.energySphereAuraSpec;
        SphereMeshProjector var35 = new SphereMeshProjector(arg0, var196);
        ProjectedVertexState[] var39 = new ProjectedVertexState[var34.vertexMatrix1.length];
        var15 = 0;
        while (true) {
            int var41 = var15;
            int var119 = var39.length;
            if (var41 >= var119) break;
            int var159 = var15;
            double[][] var257 = var34.vertexMatrix1;
            int var296 = var15;
            double[] var258 = var257[var296];
            ProjectedVertexState var195 = var35.evaluate(var258, 1.004);
            var39[var159] = var195;
            var15 = (var15 + 1);
        }
        int[][] var43 = var34.fieldVal1;
        int var45 = var43.length;
        var17 = 0;
        while (true) {
            if (var17 >= var45) break;
            int var145 = var17;
            int[] var85 = var43[var145];
            int var147 = var85[0];
            ProjectedVertexState var87 = var39[var147];
            int var149 = var85[1];
            ProjectedVertexState var89 = var39[var149];
            double[] var91 = new double[3];
            double[][] var212 = var34.vertexMatrix1;
            int var273 = var85[0];
            double[] var213 = var212[var273];
            double var214 = var213[0];
            double[][] var304 = var34.vertexMatrix1;
            int var343 = var85[1];
            double var306 = var304[var343][0];
            double var216 = (var214 + var306);
            var91[0] = var216;
            double[][] var219 = var34.vertexMatrix1;
            int var276 = var85[0];
            double[] var220 = var219[var276];
            double var221 = var220[1];
            double[][] var310 = var34.vertexMatrix1;
            int var346 = var85[1];
            double[] var311 = var310[var346];
            double var312 = var311[1];
            double var223 = (var221 + var312);
            var91[1] = var223;
            double[][] var226 = var34.vertexMatrix1;
            int var279 = var85[0];
            double[] var227 = var226[var279];
            double var228 = var227[2];
            double[][] var316 = var34.vertexMatrix1;
            int var349 = var85[1];
            double[] var317 = var316[var349];
            double var318 = var317[2];
            double var230 = (var228 + var318);
            var91[2] = var230;
            double[] var92 = GeometryPrimitives.computeVector(var91);
            double[] var94 = var89.coordsVector;
            double[] var151 = var87.coordsVector;
            double[] var95 = GeometryPrimitives.computeVector(var94, var151);
            double[] var97 = GeometryPrimitives.computeVector1(var92, var95);
            double var99 = GeometryPrimitives.computeDoubleMetric(var97, var97);
            double var101 = Math.sqrt(var99);
            int var105 = Double.compare(var101, 1.0E-9);
            if (var105 < 0) {
            } else {
                double var108 = (0.013 / var101);
                double[] var111 = new double[3];
                double var233 = var97[0];
                double var235 = (var233 * var108);
                var111[0] = var235;
                double var238 = var97[1];
                double var240 = (var238 * var108);
                var111[1] = var240;
                double var243 = var97[2];
                double var245 = (var243 * var108);
                var111[2] = var245;
                int var113 = var35.computeColorOrIndex(var87);
                int var115 = var35.computeColorOrIndex(var89);
                double[] var157 = var87.coordsVector;
                double var189 = var111[0];
                double var285 = var111[1];
                double var352 = var111[2];
                double[] var158 = GeometryPrimitives.computeVector(var157, var189, var285, var352);
                double[] var192 = var87.coordsVector;
                double var249 = var111[0];
                double var251 = -(var249);
                double var328 = var111[1];
                double var330 = -(var328);
                double var370 = var111[2];
                double var372 = -(var370);
                double[] var193 = GeometryPrimitives.computeVector(var192, var251, var330, var372);
                double[] var254 = var89.coordsVector;
                double var291 = -(var111[0]);
                double var356 = var111[1];
                double var358 = -(var356);
                double var383 = var111[2];
                double var385 = -(var383);
                double[] var255 = GeometryPrimitives.computeVector(var254, var291, var358, var385);
                double[] var294 = var89.coordsVector;
                double var334 = var111[0];
                double var376 = var111[1];
                double var391 = var111[2];
                double[] var295 = GeometryPrimitives.computeVector(var294, var334, var376, var391);
                arg0.applyStep(var158, var193, var255, var295, var113, var113, var115, var115);
            }
            var17 = (var17 + 1);
        }
        var394 = 0;
        while (true) {
            int var49 = var394;
            double[][] var122 = var34.vertexMatrix;
            int var123 = var122.length;
            if (var49 >= var123) break;
            int[][] var51 = var34.fieldVal;
            int var124 = var394;
            int[] var52 = var51[var124];
            int var54 = var52.length;
            if (var54 == 5) {
                var83 = 1;
            } else {
                var83 = 0;
            }
            int var18 = var83;
            int var126 = var394;
            double var57 = var35.computeDoubleMetric(var126, var18);
            double[][] var128 = var34.vertexMatrix;
            int var161 = var394;
            double[] var129 = var128[var161];
            ProjectedVertexState var60 = var35.evaluate(var129, 0.998);
            int var62 = var35.computeColorOrIndex(var60, var57, var18);
            ProjectedVertexState[] var64 = new ProjectedVertexState[var54];
            int[] var66 = new int[var54];
            var26 = 0;
            while (true) {
                int var68 = var26;
                if (var68 >= var54) break;
                int var132 = var26;
                double[][][] var198 = var34.meshTensor;
                int var259 = var394;
                double[][] var199 = var198[var259];
                int var260 = var26;
                double[] var200 = var199[var260];
                ProjectedVertexState var167 = var35.evaluate(var200, 0.998);
                var64[var132] = var167;
                int var133 = var26;
                int var263 = var26;
                ProjectedVertexState var202 = var64[var263];
                int var169 = var35.computeColorOrIndex(var202, var57, var18);
                var66[var133] = var169;
                var26 = (var26 + 1);
            }
            var404 = 0;
            while (true) {
                int var72 = var404;
                int var73 = (var72 + 2);
                if (var73 > var54) break;
                int var74 = var404;
                int var75 = (var74 + 1);
                int var76 = (var75 % var54);
                int var77 = var404;
                int var78 = (var77 + 2);
                int var79 = (var78 % var54);
                double[] var141 = var60.coordsVector;
                int var203 = var404;
                ProjectedVertexState var171 = var64[var203];
                double[] var172 = var171.coordsVector;
                ProjectedVertexState var205 = var64[var76];
                double[] var206 = var205.coordsVector;
                double[] var269 = var64[var79].coordsVector;
                int var8 = var404;
                int var338 = var66[var8];
                int var363 = var66[var76];
                int var380 = var66[var79];
                arg0.applyStep(var141, var172, var206, var269, var62, var338, var363, var380);
                var404 = (var404 + 2);
            }
            int var81 = var404;
            if (var81 < var54) {
                double[] var144 = var60.coordsVector;
                int var207 = var404;
                ProjectedVertexState var174 = var64[var207];
                double[] var175 = var174.coordsVector;
                ProjectedVertexState var209 = var64[0];
                double[] var210 = var209.coordsVector;
                int var339 = var404;
                int var301 = var66[var339];
                int var341 = var66[0];
                arg0.applyStep(var144, var175, var210, var62, var301, var341);
            }
            var394 = (var394 + 1);
        }
    }

}
