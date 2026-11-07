package need.clarity.modules.visual.cosmetics.targetesp;

import need.clarity.modules.visual.cosmetics.CosmeticDrawBuilder;
import need.clarity.modules.visual.cosmetics.GeometryPrimitives;
import need.clarity.modules.visual.cosmetics.ICosmeticEffectRenderer;

public final class HexShieldTargetRenderer implements ICosmeticEffectRenderer {
    static final int CONST_INT = 92;
    private static final double CONST_DBL;
    private static final double CONST_DBL1 = 0.12;
    private static final double CONST_DBL2 = 0.3;
    private static final double CONST_DBL3 = 0.011;
    private static final int CONST_INT1 = 12;
    private final TargetEspRenderSpec targetEspRenderSpec;

    public HexShieldTargetRenderer() {
        TargetEspRenderSpec var2 = TargetEspRenderSpec.evaluate();
        this(var2);
    }

    public HexShieldTargetRenderer(TargetEspRenderSpec arg0) {
        this.targetEspRenderSpec = arg0;
    }

    static HexGridDimensions evaluate(double arg0, double arg1) {
        double var18 = 0.0;
        int var20 = 0;
        double var27 = (6.283185307179586 * arg0);
        double var72 = Math.sqrt(((var27 * arg1) / 79.672));
        var18 = Math.max(0.26, var72);
        var20 = 0;
        while (true) {
            if (var20 >= 60) break;
            double var103 = var18;
            int var83 = ((int) Math.round(((var27 / var103) / 2.0)));
            int var56 = (2 * var83);
            int var37 = Math.max(8, var56);
            double var85 = ((double) var37);
            double var40 = (var27 / var85);
            double var87 = CONST_DBL;
            double var44 = (var40 / var87);
            double var94 = (1.5 * var44);
            int var63 = ((int) Math.ceil((arg1 / var94)));
            int var47 = Math.max(3, var63);
            if ((var37 * var47) <= 96) {
                HexGridDimensions var54 = new HexGridDimensions(var37, var47, var40);
                return var54;
            }
            var18 = (var18 * 1.06);
            var20 = (var20 + 1);
        }
        double var101 = (var27 / 8.0);
        HexGridDimensions var35 = new HexGridDimensions(8, 3, var101);
        return var35;
    }

    private static double computeDoubleMetric(double arg0) {
        double var16 = Math.abs(arg0);
        double var10 = Math.min(1.0, var16);
        double var24 = (((0.3 * var10) * var10) * var10);
        return (1.0 - var24);
    }

    public void renderEffect(CosmeticDrawBuilder arg0) {
        int var41 = 0;
        int var44 = 0;
        int var50 = 0;
        int var53 = 0;
        double var164 = 0.0;
        int var446 = 0;
        boolean var54 = arg0.enabledState;
        if (var54) {
            var164 = arg0.doubleValue;
        } else {
            var164 = 0.0;
        }
        double var19 = var164;
        TargetEspRenderSpec var57 = this.targetEspRenderSpec;
        double var58 = var57.age();
        TargetEspRenderSpec var61 = this.targetEspRenderSpec;
        double var62 = var61.width();
        double var64 = (var62 + 0.3);
        TargetEspRenderSpec var67 = this.targetEspRenderSpec;
        double var68 = var67.height();
        double var70 = (var68 + 0.15);
        double var206 = (var70 * 2.0);
        HexGridDimensions var74 = evaluate(var64, var206);
        int var76 = var74.columns();
        int var78 = var74.rows();
        double var80 = var74.cell();
        double var208 = CONST_DBL;
        double var84 = (var80 / var208);
        double var88 = (2.0 * var70);
        double var213 = ((double) var78);
        double var90 = (var88 / var213);
        double var217 = (1.5 * var84);
        double var94 = (var90 / var217);
        HexCylinderProjector var96 = new HexCylinderProjector(arg0, var64, var70, var58);
        CosmeticDrawBuilder var98 = arg0.drawGeometryPart();
        double var272 = (0.22 * var58);
        double var168 = (0.78 + var272);
        CosmeticDrawBuilder var100 = arg0.drawGeometryPart(var168);
        TargetEspRenderSpec var275 = this.targetEspRenderSpec;
        double var276 = var275.phase();
        double var172 = (6.283185307179586 * var276);
        double var174 = (var172 * 0.35);
        CosmeticDrawBuilder var102 = arg0.drawGeometryPart2(var174);
        double[] var104 = new double[6];
        double[] var106 = new double[6];
        var41 = 0;
        while (true) {
            int var108 = var41;
            if (var108 >= 6) break;
            int var253 = var41;
            double var254 = ((double) var253);
            double var256 = (var254 * 3.141592653589793);
            double var258 = (var256 / 3.0);
            double var160 = (1.5707963267948966 + var258);
            double var447 = var160;
            int var200 = var41;
            double var354 = Math.cos(var447);
            double var262 = (var84 * var354);
            var104[var200] = var262;
            int var201 = var41;
            double var358 = Math.sin(var447);
            double var266 = (var84 * var358);
            double var268 = (var266 * var94);
            var106[var201] = var268;
            var41 = (var41 + 1);
        }
        var446 = 0;
        while (true) {
            int var110 = var446;
            if (var110 >= var78) break;
            double var113 = -(var70);
            int var220 = var446;
            double var221 = ((double) var220);
            double var223 = (var221 + 0.5);
            double var225 = (var223 * var90);
            double var115 = (var113 + var225);
            var44 = 0;
            while (true) {
                int var118 = var44;
                if (var118 >= var76) break;
                int var119 = var44;
                double var120 = ((double) var119);
                int var320 = var446;
                int var321 = (var320 & 1);
                double var322 = ((double) var321);
                double var229 = (0.5 * var322);
                double var122 = (var120 + var229);
                double var124 = (var122 * var80);
                int var126 = var446;
                int var127 = (var126 * var76);
                int var180 = var44;
                int var128 = (var127 + var180);
                double[][] var130 = new double[6][];
                double[][] var132 = new double[6][];
                var50 = 0;
                while (true) {
                    int var134 = var50;
                    if (var134 >= 6) break;
                    int var196 = var50;
                    int var405 = var50;
                    double var381 = var104[var405];
                    double var302 = (var124 + var381);
                    int var434 = var50;
                    double var416 = var106[var434];
                    double var385 = (var115 + var416);
                    double[] var248 = computeVector(var96, var302, var385, var64, var70, 1.004);
                    var130[var196] = var248;
                    int var197 = var50;
                    int var406 = var50;
                    double var388 = var104[var406];
                    double var390 = (var388 * 0.88);
                    double var306 = (var124 + var390);
                    int var435 = var50;
                    double var423 = var106[var435];
                    double var425 = (var423 * 0.88);
                    double var394 = (var115 + var425);
                    double[] var250 = computeVector(var96, var306, var394, var64, var70, 1.0);
                    var132[var197] = var250;
                    var50 = (var50 + 1);
                }
                double[] var136 = computeVector(var96, var124, var115, var64, var70, 1.0);
                double[] var449 = var136;
                ProjectedHexPoint var138 = var96.evaluate(var449, var128);
                int[] var140 = new int[6];
                var53 = 0;
                while (true) {
                    int var142 = var53;
                    if (var142 >= 6) break;
                    int var186 = var53;
                    int var367 = var53;
                    double[] var325 = var132[var367];
                    ProjectedHexPoint var283 = var96.evaluate(var325, var128);
                    int var235 = var96.computeColorOrIndex(var283, var138);
                    var140[var186] = var235;
                    var53 = (var53 + 1);
                }
                double[] var188 = var132[0];
                double[] var238 = var132[1];
                double[] var286 = var132[2];
                double[] var329 = var132[3];
                int var371 = var140[0];
                int var401 = var140[1];
                int var411 = var140[2];
                int var430 = var140[3];
                arg0.applyStep(var188, var238, var286, var329, var371, var401, var411, var430);
                double[] var190 = var132[3];
                double[] var241 = var132[4];
                double[] var289 = var132[5];
                double[] var332 = var132[0];
                int var374 = var140[3];
                int var404 = var140[4];
                int var414 = var140[5];
                int var433 = var140[0];
                arg0.applyStep(var190, var241, var289, var332, var374, var404, var414, var433);
                double[] var291 = var130[0];
                double[] var335 = var130[1];
                this.drawGeometryPart(arg0, var96, var291, var335);
                double[] var293 = var130[5];
                double[] var338 = var130[0];
                this.drawGeometryPart(arg0, var96, var293, var338);
                double[] var295 = var130[4];
                double[] var341 = var130[5];
                this.drawGeometryPart(arg0, var96, var295, var341);
                int var149 = var446;
                if (var149 == 0) {
                    double[] var297 = var130[2];
                    double[] var344 = var130[3];
                    this.drawGeometryPart(arg0, var96, var297, var344);
                    double[] var299 = var130[3];
                    double[] var347 = var130[4];
                    this.drawGeometryPart(arg0, var96, var299, var347);
                }
                var44 = (var44 + 1);
            }
            var446 = (var446 + 1);
        }
        this.drawGeometryPart(arg0, var96, var70, var64);
        double var312 = -(var70);
        this.drawGeometryPart(arg0, var96, var312, var64);
        CosmeticDrawBuilder var157 = arg0.drawGeometryPart1();
    }

    private static double[] computeVector(HexCylinderProjector arg0, double arg1, double arg2, double arg3, double arg4, double arg5) {
        double var25 = -(arg4);
        double var44 = Math.min(arg4, arg2);
        double var27 = Math.max(var25, var44);
        double var31 = (arg1 / arg3);
        double var52 = computeDoubleMetric((var27 / arg4));
        double var37 = ((arg3 * var52) * arg5);
        double var58 = Math.cos(var31);
        double var41 = (var37 * var58);
        double var68 = Math.sin(var31);
        double var66 = (var37 * var68);
        return GeometryPrimitives.computeVector(var41, var27, var66);
    }

    private void drawGeometryPart(CosmeticDrawBuilder arg0, HexCylinderProjector arg1, double[] arg2, double[] arg3) {
        double var28 = arg2[0];
        double var72 = arg3[0];
        double var32 = ((var28 + var72) * 0.5);
        double var115 = arg2[2];
        double var152 = arg3[2];
        double var119 = ((var115 + var152) * 0.5);
        double[] var34 = GeometryPrimitives.computeVector(var32, 0.0, var119);
        double[] var36 = GeometryPrimitives.computeVector(arg3, arg2);
        double[] var39 = GeometryPrimitives.computeVector1(GeometryPrimitives.computeVector(var34), var36);
        double var43 = Math.sqrt(GeometryPrimitives.computeDoubleMetric(var39, var39));
        if (Double.compare(var43, 1.0E-9) < 0) {
            return;
        }
        double var50 = (0.011 / var43);
        double[] var53 = new double[3];
        double var95 = (var39[0] * var50);
        var53[0] = var95;
        double var100 = (var39[1] * var50);
        var53[1] = var100;
        double var105 = (var39[2] * var50);
        var53[2] = var105;
        ProjectedHexPoint var55 = arg1.evaluate(arg2, -1);
        ProjectedHexPoint var57 = arg1.evaluate(arg3, -1);
        int var59 = arg1.computeColorOrIndex(var55);
        int var61 = arg1.computeColorOrIndex(var57);
        double var88 = var53[0];
        double var125 = var53[1];
        double var157 = var53[2];
        double[] var71 = GeometryPrimitives.computeVector(arg2, var88, var125, var157);
        double var111 = -(var53[0]);
        double var145 = -(var53[1]);
        double var171 = -(var53[2]);
        double[] var91 = GeometryPrimitives.computeVector(arg2, var111, var145, var171);
        double var131 = -(var53[0]);
        double var163 = -(var53[1]);
        double var181 = -(var53[2]);
        double[] var114 = GeometryPrimitives.computeVector(arg3, var131, var163, var181);
        double var149 = var53[0];
        double var175 = var53[1];
        double var186 = var53[2];
        double[] var134 = GeometryPrimitives.computeVector(arg3, var149, var175, var186);
        arg0.applyStep(var71, var91, var114, var134, var59, var59, var61, var61);
    }

    private void drawGeometryPart(CosmeticDrawBuilder arg0, HexCylinderProjector arg1, double arg2, double arg3) {
        double[] var25 = null;
        int var28 = 0;
        double var73 = computeDoubleMetric(1.0);
        double var36 = ((arg3 * var73) * 1.004);
        var25 = null;
        double[] var64 = GeometryPrimitives.computeVector(0.0, arg2, 0.0);
        ProjectedHexPoint var40 = arg1.evaluate(var64, -1);
        int var42 = arg1.computeColorOrIndex(var40, true);
        var28 = 0;
        while (true) {
            if (var28 > 12) break;
            double var80 = ((double) var28);
            double var49 = ((6.283185307179586 * var80) / 12.0);
            double var86 = Math.cos(var49);
            double var53 = (var36 * var86);
            double var106 = Math.sin(var49);
            double var98 = (var36 * var106);
            double[] var55 = GeometryPrimitives.computeVector(var53, arg2, var98);
            if (var25 != null) {
                ProjectedHexPoint var58 = arg1.evaluate(var55, -1);
                double[] var68 = var25;
                ProjectedHexPoint var60 = arg1.evaluate(var68, -1);
                double[] var71 = GeometryPrimitives.computeVector(0.0, arg2, 0.0);
                double[] var92 = var25;
                int var105 = arg1.computeColorOrIndex(var60, false);
                int var110 = arg1.computeColorOrIndex(var58, false);
                arg0.applyStep(var71, var92, var55, var42, var105, var110);
                double[] var97 = var25;
                this.drawGeometryPart(arg0, arg1, var97, var55);
            }
            var25 = var55;
            var28 = (var28 + 1);
        }
    }

    static {
        CONST_DBL = Math.sqrt(3.0);
    }

}
