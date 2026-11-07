package need.clarity.modules.visual.cosmetics.aura;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.ToDoubleFunction;
import java.util.stream.Stream;
import need.clarity.modules.visual.cosmetics.ColorMathUtils;
import need.clarity.modules.visual.cosmetics.CosmeticDrawBuilder;
import need.clarity.modules.visual.cosmetics.GeometryPrimitives;

public final class SphereMeshProjector {
    final int counterValue;
    final int counterValue1;
    final int counterValue2;
    final double doubleValue;
    final double doubleValue1;
    final double doubleValue2;
    final double doubleValue3;
    final boolean enabledState;
    final double[] coordsVector;
    final double[][] vertexMatrix;
    final double[] coordsVector1;

    SphereMeshProjector(CosmeticDrawBuilder arg0, EnergySphereAuraSpec arg1) {
        int var69 = arg0.counterValue;
        this.counterValue = var69;
        int var71 = arg0.counterValue1;
        this.counterValue1 = var71;
        int var73 = arg0.counterValue;
        int var117 = arg0.counterValue1;
        int var75 = ColorMathUtils.computeColorOrIndex7(ColorMathUtils.computeColorOrIndex(var73, var117, 0.5), 0.82);
        this.counterValue2 = var75;
        boolean var77 = arg0.enabledState;
        this.enabledState = var77;
        if (this.enabledState) {
            double var115 = arg0.doubleValue;
        } else {
            var115 = 0.0;
        }
        this.doubleValue1 = var115;
        double var82 = arg0.computeDoubleMetric();
        this.doubleValue2 = var82;
        double var85 = arg1.radius();
        if (this.enabledState) {
            double var190 = Math.sin((this.doubleValue1 * 1.6));
            double var180 = (0.012 * var190);
            double var156 = (1.0 + var180);
        } else {
            var156 = 1.0;
        }
        double var87 = (var85 * var156);
        this.doubleValue = var87;
        double[] var90 = arg0.computeVector1();
        this.coordsVector = var90;
        if (this.coordsVector != null) {
            double[] var94 = this.coordsVector;
            double[] var121 = this.coordsVector;
            double var97 = Math.sqrt(GeometryPrimitives.computeDoubleMetric(var94, var121));
            double var149 = (arg1.radius() * 0.98);
            if (var97 < var149) {
                double var100 = 0.55;
            }
        } else {
            var100 = 1.0;
        }
        this.doubleValue3 = var100;
        ArrayList var31 = new ArrayList();
        ArrayList var32 = new ArrayList();
        Iterator var35 = arg1.ripples().iterator();
        while (true) {
            if (!var35.hasNext()) break;
            SphereRippleImpulse var40 = ((SphereRippleImpulse) var35.next());
            double var42 = var40.dx();
            double var123 = var40.dx();
            double var44 = (var42 * var123);
            double var126 = var40.dy();
            double var158 = var40.dy();
            double var128 = (var126 * var158);
            double var46 = (var44 + var128);
            double var131 = var40.dz();
            double var161 = var40.dz();
            double var133 = (var131 * var161);
            double var50 = Math.sqrt((var46 + var133));
            if (var50 < 1.0E-9) continue;
            if (var40.age() < 0.0) continue;
            if (var40.age() > 1.3) {
            }
            double[] var103 = new double[3];
            double var166 = (var40.dx() / var50);
            var103[0] = var166;
            double var171 = (var40.dy() / var50);
            var103[1] = var171;
            double var176 = (var40.dz() / var50);
            var103[2] = var176;
            boolean var64 = var31.add(var103);
            Double var107 = var40.age();
            boolean var66 = var32.add(var107);
        }
        double[][] var142 = new double[0][];
        double[][] var110 = ((double[][]) var31.toArray(var142));
        this.vertexMatrix = var110;
        Stream var112 = var32.stream();
        ToDoubleFunction var143 = Double::doubleValue;
        double[] var114 = var112.mapToDouble(var143).toArray();
        this.coordsVector1 = var114;
    }

    double computeDoubleMetric(double[] arg0) {
        double var12 = 0.0;
        int var14 = 0;
        var12 = 0.0;
        var14 = 0;
        while (true) {
            int var36 = var14;
            int var108 = this.vertexMatrix.length;
            if (var36 >= var108) break;
            double[][] var182 = this.vertexMatrix;
            int var9 = var14;
            double[] var183 = var182[var9];
            double var158 = GeometryPrimitives.computeDoubleMetric(arg0, var183);
            double var110 = Math.min(1.0, var158);
            double var41 = Math.acos(Math.max(-1.0, var110));
            double[] var44 = this.coordsVector1;
            int var109 = var14;
            double var45 = var44[var109];
            double var49 = (var45 / 1.3);
            double var53 = (3.2 * var45);
            double var118 = (0.25 * var49);
            double var57 = (0.4 + var118);
            double var63 = ((var41 - var53) / var57);
            double var126 = (var53 * 0.62);
            double var69 = ((var41 - var126) / var57);
            double var75 = Math.pow((1.0 - var49), 1.0);
            double var83 = Math.exp((-(var63) * var63));
            double var170 = Math.exp((-(var69) * var69));
            double var138 = (0.45 * var170);
            double var85 = (var83 + var138);
            double var95 = Math.exp(((-(var41) * var41) / 0.3));
            double var188 = (var45 / 0.3);
            double var174 = (1.0 - var188);
            double var146 = Math.max(0.0, var174);
            double var97 = (var95 * var146);
            double var99 = var12;
            double var154 = (((var85 * var75) * 1.2) + var97);
            var12 = (var99 + var154);
            var14 = (var14 + 1);
        }
        double var156 = var12;
        return Math.min(1.3, var156);
    }

    ProjectedVertexState evaluate(double[] arg0, double arg1) {
        int var142 = 0;
        double var100 = 0.0;
        double var108 = 0.0;
        ProjectedVertexState var4 = new ProjectedVertexState();
        double var103 = arg0[1];
        var4.doubleValue = var103;
        if (this.vertexMatrix.length == 0) {
            var108 = 0.0;
        } else {
            var108 = this.computeDoubleMetric(arg0);
        }
        var4.doubleValue3 = var108;
        double var32 = this.doubleValue;
        double var253 = var4.doubleValue3;
        double var221 = (0.035 * var253);
        double var150 = (arg1 + var221);
        double var34 = (var32 * var150);
        double var113 = (arg0[0] * var34);
        double var206 = (arg0[1] * var34);
        double var248 = (arg0[2] * var34);
        double[] var115 = GeometryPrimitives.computeVector(var113, var206, var248);
        var4.coordsVector = var115;
        if (this.coordsVector == null) {
            var4.doubleValue1 = 0.5;
            var4.enabledState = false;
            var4.doubleValue4 = 1.0;
        } else {
            double var60 = this.coordsVector[0];
            double var163 = var4.coordsVector[0];
            double var62 = (var60 - var163);
            double var66 = this.coordsVector[1];
            double var167 = var4.coordsVector[1];
            double var68 = (var66 - var167);
            double var72 = this.coordsVector[2];
            double var171 = var4.coordsVector[2];
            double var74 = (var72 - var171);
            double var78 = (var62 * var62);
            double var177 = (var68 * var68);
            double var80 = (var78 + var177);
            double var181 = (var74 * var74);
            double var84 = Math.sqrt((var80 + var181));
            if (Double.compare(var84, 1.0E-9) < 0) {
                var100 = 0.5;
            } else {
                double var186 = arg0[0];
                double var91 = (var62 * var186);
                double var236 = arg0[1];
                double var190 = (var68 * var236);
                double var93 = (var91 + var190);
                double var239 = arg0[2];
                double var194 = (var74 * var239);
                var100 = ((var93 + var194) / var84);
            }
            double var27 = var100;
            double var216 = Math.abs(var27);
            double var137 = (1.0 - var216);
            var4.doubleValue1 = var137;
            if (Double.compare(var27, 0.0) < 0) {
                var142 = 1;
            } else {
                var142 = 0;
            }
            var4.enabledState = var142;
            double[] var144 = this.coordsVector;
            double var200 = var4.coordsVector[0];
            double var243 = var4.coordsVector[1];
            double var262 = var4.coordsVector[2];
            double var265 = this.doubleValue2;
            double var145 = AuraRenderMath.computeDoubleMetric(var144, var200, var243, var262, var265);
            var4.doubleValue4 = var145;
        }
        var4.doubleValue2 = 0.0;
        if (this.enabledState) {
            double var229 = ((this.doubleValue1 * 0.22) % 1.0);
            double var155 = (2.1 * var229);
            double var47 = (-1.05 + var155);
            double var54 = ((arg0[1] - var47) / 0.16);
            double var130 = Math.exp((-(var54) * var54));
            var4.doubleValue2 = var130;
        }
        return var4;
    }

    double computeDoubleMetric(int arg0, boolean arg1) {
        double var35 = 0.0;
        double var16 = 0.0;
        double var51 = GeometryPrimitives.computeDoubleMetric(((arg0 * 7) + 1));
        double var37 = (0.3 * var51);
        var16 = (0.85 + var37);
        if (this.enabledState) {
            double var54 = this.doubleValue1;
            double var85 = GeometryPrimitives.computeDoubleMetric(((arg0 * 7) + 2));
            double var75 = (1.6 * var85);
            double var67 = (0.9 + var75);
            double var56 = (var54 * var67);
            double var80 = GeometryPrimitives.computeDoubleMetric(((arg0 * 7) + 3));
            double var71 = (6.283 * var80);
            double var60 = Math.sin((var56 + var71));
            double var41 = (0.5 * var60);
            double var26 = (0.5 + var41);
            double var28 = var16;
            double var64 = Math.pow(var26, 9.0);
            double var45 = (0.55 * var64);
            var16 = (var28 + var45);
        }
        if (arg1) {
            var35 = (var16 * 1.45);
        } else {
            var35 = var16;
        }
        return var35;
    }

    private int computeColorOrIndex(ProjectedVertexState arg0, double arg1) {
        int var12 = this.counterValue1;
        int var16 = this.counterValue;
        double var23 = arg0.doubleValue;
        double var21 = (0.5 * var23);
        double var19 = (0.5 + var21);
        return ColorMathUtils.computeColorOrIndex7(ColorMathUtils.computeColorOrIndex(var12, var16, var19), arg1);
    }

    int computeColorOrIndex(ProjectedVertexState arg0, double arg1, boolean arg2) {
        int var17 = 0;
        double var86 = 0.0;
        double var119 = arg0.doubleValue1;
        double var92 = (0.3 * var119);
        double var52 = (0.1 + var92);
        var17 = this.computeColorOrIndex(arg0, var52);
        if (arg2) {
            int var22 = var17;
            int var43 = this.counterValue;
            int var55 = this.counterValue1;
            double var123 = arg0.doubleValue;
            double var117 = (0.5 * var123);
            double var88 = (0.5 - var117);
            int var45 = ColorMathUtils.computeColorOrIndex7(ColorMathUtils.computeColorOrIndex(var43, var55, var88), 0.35);
            var17 = ColorMathUtils.computeColorOrIndex(var22, var45, 0.5);
        }
        int var24 = var17;
        int var47 = this.counterValue2;
        double var97 = (arg0.doubleValue3 * 0.8);
        double var62 = Math.min(1.0, var97);
        int var25 = ColorMathUtils.computeColorOrIndex(var24, var47, var62);
        double var100 = arg0.doubleValue1;
        double var66 = (0.22 * var100);
        double var103 = arg0.doubleValue1;
        double var68 = (var66 * var103);
        double var28 = (0.055 + var68);
        double var106 = arg0.doubleValue2;
        double var72 = (0.16 * var106);
        double var30 = (var28 + var72);
        double var109 = arg0.doubleValue3;
        double var76 = (0.7 * var109);
        double var34 = ((var30 + var76) * arg1);
        if (arg0.enabledState) {
            var86 = 0.6;
        } else {
            var86 = 1.0;
        }
        double var112 = this.doubleValue3;
        double var82 = (var86 * var112);
        double var115 = arg0.doubleValue4;
        double var84 = (var82 * var115);
        double var38 = (var34 * var84);
        double var50 = Math.min(0.85, var38);
        return ColorMathUtils.computeColorOrIndex5(var25, var50);
    }

    int computeColorOrIndex(ProjectedVertexState arg0) {
        double var61 = 0.0;
        double var87 = arg0.doubleValue1;
        double var63 = (0.3 * var87);
        double var37 = (0.35 + var63);
        int var14 = this.computeColorOrIndex(arg0, var37);
        int var32 = this.counterValue2;
        double var68 = (arg0.doubleValue3 * 0.9);
        double var41 = Math.min(1.0, var68);
        int var16 = ColorMathUtils.computeColorOrIndex(var14, var32, var41);
        double var73 = Math.pow(arg0.doubleValue1, 1.5);
        double var45 = (0.42 * var73);
        double var19 = (0.32 + var45);
        double var76 = arg0.doubleValue2;
        double var49 = (0.45 * var76);
        double var21 = (var19 + var49);
        double var79 = arg0.doubleValue3;
        double var53 = (0.6 * var79);
        double var23 = (var21 + var53);
        if (arg0.enabledState) {
            var61 = 0.65;
        } else {
            var61 = 1.0;
        }
        double var82 = this.doubleValue3;
        double var57 = (var61 * var82);
        double var85 = arg0.doubleValue4;
        double var59 = (var57 * var85);
        double var27 = (var23 * var59);
        double var35 = Math.min(0.95, var27);
        return ColorMathUtils.computeColorOrIndex5(var16, var35);
    }

}
