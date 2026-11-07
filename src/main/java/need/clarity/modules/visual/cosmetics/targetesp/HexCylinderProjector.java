package need.clarity.modules.visual.cosmetics.targetesp;

import need.clarity.modules.visual.cosmetics.ColorMathUtils;
import need.clarity.modules.visual.cosmetics.CosmeticDrawBuilder;
import need.clarity.modules.visual.cosmetics.GeometryPrimitives;

public final class HexCylinderProjector {
    final int counterValue;
    final int counterValue1;
    final double doubleValue;
    final double doubleValue1;
    final double doubleValue2;
    final double doubleValue3;
    final double doubleValue4;
    final boolean enabledState;
    final double[] coordsVector;

    HexCylinderProjector(CosmeticDrawBuilder arg0, double arg1, double arg2, double arg3) {
        int var24 = arg0.counterValue;
        this.counterValue = var24;
        int var26 = arg0.counterValue1;
        this.counterValue1 = var26;
        boolean var28 = arg0.enabledState;
        this.enabledState = var28;
        if (this.enabledState) {
            double var50 = arg0.doubleValue;
        } else {
            var50 = 0.0;
        }
        this.doubleValue = var50;
        double var33 = arg0.computeDoubleMetric();
        this.doubleValue1 = var33;
        this.doubleValue2 = arg2;
        this.doubleValue4 = arg3;
        double[] var40 = arg0.computeVector1();
        this.coordsVector = var40;
        if (this.coordsVector != null) {
            double var45 = this.coordsVector[0];
            double var55 = this.coordsVector[2];
            double var47 = Math.hypot(var45, var55);
            double var59 = (arg1 * 0.98);
            if (var47 < var59) {
                double var52 = 0.55;
            } else {
                var52 = 1.0;
            }
            this.doubleValue3 = var52;
            return;
        }
    }

    ProjectedHexPoint evaluate(double[] arg0, int arg1) {
        double var136 = 0.0;
        double var138 = 0.0;
        double var140 = 0.0;
        int var192 = 0;
        ProjectedHexPoint var3 = new ProjectedHexPoint();
        var3.counterValue = arg1;
        double var144 = arg0[1];
        double var263 = this.doubleValue2;
        double var146 = (var144 / var263);
        var3.doubleValue = var146;
        double var36 = arg0[0];
        double var194 = arg0[2];
        double var38 = Math.hypot(var36, var194);
        if (Double.compare(var38, 1.0E-9) < 0) {
            var136 = 1.0;
        } else {
            var136 = (arg0[0] / var38);
        }
        double var19 = var136;
        if (Double.compare(var38, 1.0E-9) < 0) {
            var140 = 0.0;
        } else {
            var140 = (arg0[2] / var38);
        }
        double var21 = var140;
        if (this.coordsVector == null) {
            var3.doubleValue1 = 0.5;
            var3.enabledState = false;
            var3.doubleValue5 = 1.0;
        } else {
            double var95 = this.coordsVector[0];
            double var230 = arg0[0];
            double var97 = (var95 - var230);
            double var101 = this.coordsVector[1];
            double var233 = arg0[1];
            double var103 = (var101 - var233);
            double var107 = this.coordsVector[2];
            double var236 = arg0[2];
            double var109 = (var107 - var236);
            double var113 = (var97 * var97);
            double var242 = (var103 * var103);
            double var115 = (var113 + var242);
            double var246 = (var109 * var109);
            double var119 = Math.sqrt((var115 + var246));
            if (Double.compare(var119, 1.0E-9) < 0) {
                var138 = 0.5;
            } else {
                double var129 = (var97 * var19);
                double var257 = (var109 * var21);
                var138 = ((var129 + var257) / var119);
            }
            double var354 = var138;
            double var278 = Math.abs(var354);
            double var182 = (1.0 - var278);
            var3.doubleValue1 = var182;
            if (Double.compare(var354, 0.0) < 0) {
                var192 = 1;
            } else {
                var192 = 0;
            }
            var3.enabledState = var192;
            double[] var188 = this.coordsVector;
            double var251 = arg0[0];
            double var313 = arg0[1];
            double var328 = arg0[2];
            double var338 = this.doubleValue1;
            double var189 = TargetEspMathUtils.computeDoubleMetric(var188, var251, var313, var328, var338);
            var3.doubleValue5 = var189;
        }
        if (this.enabledState) {
            double var59 = -(this.doubleValue2);
            double var283 = this.doubleValue2;
            double var204 = (2.0 * var283);
            double var290 = ((this.doubleValue * 0.32) % 1.0);
            double var206 = (var204 * var290);
            double var61 = (var59 + var206);
            double var66 = (arg0[1] - var61);
            double var293 = this.doubleValue2;
            double var214 = ((0.16 * var293) + 0.08);
            double var68 = (var66 / var214);
            double var162 = Math.exp((-(var68) * var68));
            var3.doubleValue2 = var162;
            double var72 = arg0[2];
            double var217 = arg0[0];
            double var74 = Math.atan2(var72, var217);
            double var79 = (this.doubleValue * 0.28);
            double var223 = (var74 / 6.283185307179586);
            double var171 = Math.exp((-(TargetEspMathUtils.computeDoubleMetric((var79 - var223))) / 0.12));
            var3.doubleValue3 = var171;
            if (arg1 >= 0) {
                double var300 = this.doubleValue;
                double var343 = GeometryPrimitives.computeDoubleMetric(((arg1 * 7) + 2));
                double var330 = (1.6 * var343);
                double var321 = (0.9 + var330);
                double var302 = (var300 * var321);
                double var335 = GeometryPrimitives.computeDoubleMetric(((arg1 * 7) + 3));
                double var325 = (6.283 * var335);
                double var306 = Math.sin((var302 + var325));
                double var227 = (0.5 * var306);
                double var175 = Math.pow((0.5 + var227), 9.0);
                var3.doubleValue4 = var175;
            }
            return var3;
        }
    }

    private int computeColorOrIndex(ProjectedHexPoint arg0, double arg1) {
        int var11 = this.counterValue1;
        int var14 = this.counterValue;
        double var21 = arg0.doubleValue;
        double var19 = (0.5 * var21);
        double var17 = (0.5 + var19);
        return ColorMathUtils.computeColorOrIndex7(ColorMathUtils.computeColorOrIndex(var11, var14, var17), arg1);
    }

    int computeColorOrIndex(ProjectedHexPoint arg0, ProjectedHexPoint arg1) {
        double var79 = 0.0;
        double var133 = arg0.doubleValue1;
        double var93 = (0.3 * var133);
        double var51 = (0.1 + var93);
        int var20 = this.computeColorOrIndex(arg0, var51);
        double var99 = GeometryPrimitives.computeDoubleMetric(((arg1.counterValue * 7) + 1));
        double var55 = (0.3 * var99);
        double var23 = (0.85 + var55);
        double var102 = arg0.doubleValue1;
        double var59 = (0.2 * var102);
        double var105 = arg0.doubleValue1;
        double var61 = (var59 * var105);
        double var27 = (0.05 + var61);
        double var108 = arg0.doubleValue2;
        double var65 = (0.22 * var108);
        double var29 = (var27 + var65);
        double var111 = arg0.doubleValue3;
        double var69 = (0.24 * var111);
        double var31 = (var29 + var69);
        double var114 = arg1.doubleValue4;
        double var73 = (0.4 * var114);
        double var35 = ((var31 + var73) * var23);
        if (arg0.enabledState) {
            var79 = 0.6;
        } else {
            var79 = 1.0;
        }
        double var117 = this.doubleValue3;
        double var81 = (var79 * var117);
        double var120 = arg0.doubleValue5;
        double var83 = (var81 * var120);
        double var123 = this.doubleValue4;
        double var85 = (var83 * var123);
        double var39 = (var35 * var85);
        int var46 = ColorMathUtils.computeColorOrIndex7(this.counterValue, 0.6);
        double var128 = (arg0.doubleValue3 * 0.5);
        double var140 = (arg0.doubleValue2 * 0.4);
        double var130 = (var128 + var140);
        double var91 = Math.min(1.0, var130);
        int var42 = ColorMathUtils.computeColorOrIndex(var20, var46, var91);
        double var49 = Math.min(0.8, var39);
        return ColorMathUtils.computeColorOrIndex5(var42, var49);
    }

    int computeColorOrIndex(ProjectedHexPoint arg0) {
        double var55 = 0.0;
        double var79 = arg0.doubleValue1;
        double var57 = (0.3 * var79);
        double var33 = (0.35 + var57);
        int var14 = this.computeColorOrIndex(arg0, var33);
        double var62 = Math.pow(arg0.doubleValue1, 1.5);
        double var37 = (0.4 * var62);
        double var17 = (0.34 + var37);
        double var65 = arg0.doubleValue2;
        double var41 = (0.4 * var65);
        double var19 = (var17 + var41);
        double var68 = arg0.doubleValue3;
        double var45 = (0.35 * var68);
        double var21 = (var19 + var45);
        if (arg0.enabledState) {
            var55 = 0.65;
        } else {
            var55 = 1.0;
        }
        double var71 = this.doubleValue3;
        double var49 = (var55 * var71);
        double var74 = arg0.doubleValue5;
        double var51 = (var49 * var74);
        double var77 = this.doubleValue4;
        double var53 = (var51 * var77);
        double var25 = (var21 * var53);
        double var31 = Math.min(0.95, var25);
        return ColorMathUtils.computeColorOrIndex5(var14, var31);
    }

    int computeColorOrIndex(ProjectedHexPoint arg0, boolean arg1) {
        double var43 = 0.0;
        double var16 = 0.0;
        double var59 = 0.0;
        if (arg1) {
            var43 = 0.35;
        } else {
            var43 = 0.15;
        }
        int var14 = this.computeColorOrIndex(arg0, var43);
        if (arg1) {
            var16 = 0.05;
        } else {
            var16 = 0.2;
        }
        double var45 = arg0.doubleValue2;
        double var32 = (0.25 * var45);
        if (Double.compare(Math.abs(arg0.doubleValue), 0.9) > 0) {
            var59 = 0.0;
        } else {
            var59 = 1.0;
        }
        double var34 = (var32 * var59);
        double var18 = (var16 + var34);
        double var37 = this.doubleValue3;
        double var54 = arg0.doubleValue5;
        double var39 = (var37 * var54);
        double var57 = this.doubleValue4;
        double var41 = (var39 * var57);
        double var22 = (var18 * var41);
        double var28 = Math.min(0.5, var22);
        return ColorMathUtils.computeColorOrIndex5(var14, var28);
    }

}
