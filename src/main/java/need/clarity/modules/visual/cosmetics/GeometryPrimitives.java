package need.clarity.modules.visual.cosmetics;

import java.util.function.BiConsumer;
import java.util.function.DoubleUnaryOperator;

public final class GeometryPrimitives {
    private static final double CONST_DBL = 6.283185307179586;

    private GeometryPrimitives() {
    }

    public static IGradientColorSampler evaluate(int arg0) {
        return (p0, p1) -> GeometryPrimitives.lambda$a(arg0, p0, p1);
    }

    public static IGradientColorSampler evaluate(int arg0, int arg1) {
        return (p0, p1) -> GeometryPrimitives.lambda$b(arg0, arg1, p0, p1);
    }

    public static IGradientColorSampler evaluate1(int arg0, int arg1) {
        return (p0, p1) -> GeometryPrimitives.lambda$a(arg0, arg1, p0, p1);
    }

    public static double[] computeVector(double arg0, double arg1, double arg2) {
        double[] var10 = new double[3];
        var10[0] = arg0;
        var10[1] = arg1;
        var10[2] = arg2;
        return var10;
    }

    public static double[] computeVector(double[] arg0, double[] arg1, double arg2) {
        double[] var12 = new double[3];
        double var15 = arg0[0];
        double var31 = arg1[0];
        double var53 = arg0[0];
        double var35 = ((var31 - var53) * arg2);
        double var17 = (var15 + var35);
        var12[0] = var17;
        double var20 = arg0[1];
        double var38 = arg1[1];
        double var58 = arg0[1];
        double var42 = ((var38 - var58) * arg2);
        double var22 = (var20 + var42);
        var12[1] = var22;
        double var25 = arg0[2];
        double var45 = arg1[2];
        double var63 = arg0[2];
        double var49 = ((var45 - var63) * arg2);
        double var27 = (var25 + var49);
        var12[2] = var27;
        return var12;
    }

    public static double[] computeVector(double[] arg0, double arg1, double arg2, double arg3) {
        double[] var13 = new double[3];
        double var18 = (arg0[0] + arg1);
        var13[0] = var18;
        double var23 = (arg0[1] + arg2);
        var13[1] = var23;
        double var28 = (arg0[2] + arg3);
        var13[2] = var28;
        return var13;
    }

    public static double[] computeVector(double[] arg0, double[] arg1, double[] arg2, double arg3) {
        double var19 = (1.0 - arg3);
        double[] var22 = new double[3];
        double var26 = (var19 * var19);
        double var55 = arg0[0];
        double var28 = (var26 * var55);
        double var61 = ((2.0 * var19) * arg3);
        double var114 = arg1[0];
        double var63 = (var61 * var114);
        double var30 = (var28 + var63);
        double var67 = (arg3 * arg3);
        double var119 = arg2[0];
        double var69 = (var67 * var119);
        double var32 = (var30 + var69);
        var22[0] = var32;
        double var36 = (var19 * var19);
        double var74 = arg0[1];
        double var38 = (var36 * var74);
        double var80 = ((2.0 * var19) * arg3);
        double var126 = arg1[1];
        double var82 = (var80 * var126);
        double var40 = (var38 + var82);
        double var86 = (arg3 * arg3);
        double var131 = arg2[1];
        double var88 = (var86 * var131);
        double var42 = (var40 + var88);
        var22[1] = var42;
        double var46 = (var19 * var19);
        double var93 = arg0[2];
        double var48 = (var46 * var93);
        double var99 = ((2.0 * var19) * arg3);
        double var138 = arg1[2];
        double var101 = (var99 * var138);
        double var50 = (var48 + var101);
        double var105 = (arg3 * arg3);
        double var143 = arg2[2];
        double var107 = (var105 * var143);
        double var52 = (var50 + var107);
        var22[2] = var52;
        return var22;
    }

    public static double computeDoubleMetric(double arg0) {
        double var10 = ColorMathUtils.computeDoubleMetric(arg0);
        double var14 = (var10 * var10);
        double var22 = (2.0 * var10);
        double var20 = (3.0 - var22);
        return (var14 * var20);
    }

    public static double computeDoubleMetric(int arg0) {
        long var9 = (((long) arg0) * -7046029254386353131L);
        long var33 = (var9 >>> 29);
        long var17 = ((var9 ^ var33) * -4658895280553007687L);
        long var39 = (var17 >>> 32);
        return (((double) ((var17 ^ var39) & 16777215L)) / 1.6777216E7);
    }

    public static double[] computeVector(double[] arg0, double[] arg1) {
        double[] var8 = new double[3];
        double var11 = arg0[0];
        double var27 = arg1[0];
        double var13 = (var11 - var27);
        var8[0] = var13;
        double var16 = arg0[1];
        double var30 = arg1[1];
        double var18 = (var16 - var30);
        var8[1] = var18;
        double var21 = arg0[2];
        double var33 = arg1[2];
        double var23 = (var21 - var33);
        var8[2] = var23;
        return var8;
    }

    public static double computeDoubleMetric(double[] arg0, double[] arg1) {
        double var8 = arg0[0];
        double var16 = arg1[0];
        double var10 = (var8 * var16);
        double var19 = arg0[1];
        double var30 = arg1[1];
        double var21 = (var19 * var30);
        double var12 = (var10 + var21);
        double var24 = arg0[2];
        double var33 = arg1[2];
        double var26 = (var24 * var33);
        return (var12 + var26);
    }

    public static double[] computeVector1(double[] arg0, double[] arg1) {
        double[] var10 = new double[3];
        double var13 = arg0[1];
        double var35 = arg1[2];
        double var15 = (var13 * var35);
        double var38 = arg0[2];
        double var63 = arg1[1];
        double var40 = (var38 * var63);
        double var17 = (var15 - var40);
        var10[0] = var17;
        double var20 = arg0[2];
        double var43 = arg1[0];
        double var22 = (var20 * var43);
        double var46 = arg0[0];
        double var66 = arg1[2];
        double var48 = (var46 * var66);
        double var24 = (var22 - var48);
        var10[1] = var24;
        double var27 = arg0[0];
        double var51 = arg1[1];
        double var29 = (var27 * var51);
        double var54 = arg0[1];
        double var69 = arg1[0];
        double var56 = (var54 * var69);
        double var31 = (var29 - var56);
        var10[2] = var31;
        return var10;
    }

    public static double[] computeVector(double[] arg0) {
        int var19 = 0;
        double var13 = Math.sqrt(computeDoubleMetric(arg0, arg0));
        if (Double.compare(var13, 1.0E-12) < 0) {
            var19 = new double[3];
            var19[0] = 0.0;
            var19[1] = 1.0;
            var19[2] = 0.0;
        } else {
            var19 = new double[3];
            double var29 = (arg0[0] / var13);
            var19[0] = var29;
            double var34 = (arg0[1] / var13);
            var19[1] = var34;
            double var39 = (arg0[2] / var13);
            var19[2] = var39;
        }
        return var19;
    }

    private static double[] computeVector(double[] arg0, double[] arg1, double arg2, double[] arg3, double arg4) {
        double[] var15 = new double[3];
        double var18 = arg0[0];
        double var42 = (arg1[0] * arg2);
        double var20 = (var18 + var42);
        double var47 = (arg3[0] * arg4);
        double var22 = (var20 + var47);
        var15[0] = var22;
        double var25 = arg0[1];
        double var52 = (arg1[1] * arg2);
        double var27 = (var25 + var52);
        double var57 = (arg3[1] * arg4);
        double var29 = (var27 + var57);
        var15[1] = var29;
        double var32 = arg0[2];
        double var62 = (arg1[2] * arg2);
        double var34 = (var32 + var62);
        double var67 = (arg3[2] * arg4);
        double var36 = (var34 + var67);
        var15[2] = var36;
        return var15;
    }

    private static double[][] computeMatrix(double[] arg0) {
        int var22 = 0;
        if (Double.compare(Math.abs(arg0[1]), 0.9) < 0) {
            var22 = new double[3];
            var22[0] = 0.0;
            var22[1] = 1.0;
            var22[2] = 0.0;
        } else {
            var22 = new double[3];
            var22[0] = 1.0;
            var22[1] = 0.0;
            var22[2] = 0.0;
        }
        double[] var8 = var22;
        double[] var18 = computeVector(computeVector1(arg0, var8));
        double[][] var20 = new double[2][];
        var20[0] = var18;
        double[] var38 = computeVector1(arg0, var18);
        var20[1] = var38;
        return var20;
    }

    public static void drawGeometryPart(CosmeticDrawBuilder arg0, double arg1, double arg2, double arg3, int arg4, IGradientColorSampler arg5) {
        int var30 = 0;
        var30 = 0;
        while (true) {
            if (var30 >= arg4) break;
            double var49 = ((double) var30);
            double var90 = ((double) arg4);
            double var51 = (var49 / var90);
            double var55 = ((double) (var30 + 1));
            double var93 = ((double) arg4);
            double var57 = (var55 / var93);
            double var61 = (var51 * 6.283185307179586);
            double var65 = (var57 * 6.283185307179586);
            double var69 = Math.cos(var61);
            double var73 = Math.sin(var61);
            double var77 = Math.cos(var65);
            double var81 = Math.sin(var65);
            double var87 = (arg2 * var69);
            double var118 = (arg2 * var73);
            double[] var89 = computeVector(var87, arg1, var118);
            double var101 = (arg3 * var69);
            double var126 = (arg3 * var73);
            double[] var103 = computeVector(var101, arg1, var126);
            double var108 = (arg3 * var77);
            double var138 = (arg3 * var81);
            double[] var110 = computeVector(var108, arg1, var138);
            double var115 = (arg2 * var77);
            double var146 = (arg2 * var81);
            double[] var117 = computeVector(var115, arg1, var146);
            int var125 = arg5.computeColorOrIndex(var51, 0.0);
            int var135 = arg5.computeColorOrIndex(var51, 1.0);
            int var143 = arg5.computeColorOrIndex(var57, 1.0);
            int var153 = arg5.computeColorOrIndex(var57, 0.0);
            arg0.applyStep(var89, var103, var110, var117, var125, var135, var143, var153);
            var30 = (var30 + 1);
        }
    }

    public static void drawGeometryPart(CosmeticDrawBuilder arg0, double arg1, double arg2, int arg3, IGradientColorSampler arg4) {
        int var31 = 0;
        double[] var36 = computeVector(0.0, arg1, 0.0);
        var31 = 0;
        while (true) {
            if (var31 >= arg3) break;
            double var40 = ((double) var31);
            double var54 = ((double) arg3);
            double var42 = (var40 / var54);
            double var46 = ((double) (var31 + 1));
            double var57 = ((double) arg3);
            double var48 = (var46 / var57);
            double var71 = Math.cos((var42 * 6.283185307179586));
            double var61 = (arg2 * var71);
            double var105 = Math.sin((var42 * 6.283185307179586));
            double var89 = (arg2 * var105);
            double[] var63 = computeVector(var61, arg1, var89);
            double var79 = Math.cos((var48 * 6.283185307179586));
            double var64 = (arg2 * var79);
            double var111 = Math.sin((var48 * 6.283185307179586));
            double var97 = (arg2 * var111);
            double[] var66 = computeVector(var64, arg1, var97);
            int var76 = arg4.computeColorOrIndex(var42, 0.0);
            int var86 = arg4.computeColorOrIndex(var42, 1.0);
            int var94 = arg4.computeColorOrIndex(var48, 1.0);
            arg0.applyStep(var36, var63, var66, var76, var86, var94);
            var31 = (var31 + 1);
        }
    }

    public static void drawGeometryPart(CosmeticDrawBuilder arg0, double arg1, int arg2, double arg3, double arg4, double arg5, IGradientColorSampler arg6) {
        double var65 = 0.0;
        int var34 = 0;
        double var62 = 0.0;
        int var43 = (arg2 * 2);
        double[] var46 = computeVector(0.0, arg1, 0.0);
        var34 = 0;
        while (true) {
            if (var34 >= var43) break;
            double var93 = ((double) var34);
            double var74 = (3.141592653589793 * var93);
            double var96 = ((double) arg2);
            double var76 = (var74 / var96);
            double var51 = (arg5 + var76);
            double var100 = ((double) (var34 + 1));
            double var80 = (3.141592653589793 * var100);
            double var103 = ((double) arg2);
            double var82 = (var80 / var103);
            double var55 = (arg5 + var82);
            if ((var34 % 2) == 0) {
                var65 = arg3;
            } else {
                var65 = arg4;
            }
            double var39 = var65;
            if (((var34 + 1) % 2) == 0) {
                var62 = arg3;
            } else {
                var62 = arg4;
            }
            double var41 = var62;
            double var107 = Math.cos(var51);
            double var86 = (var39 * var107);
            double var148 = Math.sin(var51);
            double var126 = (var39 * var148);
            double[] var88 = computeVector(var86, arg1, var126);
            double var115 = Math.cos(var55);
            double var89 = (var41 * var115);
            double var158 = Math.sin(var55);
            double var135 = (var41 * var158);
            double[] var91 = computeVector(var89, arg1, var135);
            double var120 = ((double) var34);
            double var138 = ((double) var43);
            double var122 = (var120 / var138);
            int var112 = arg6.computeColorOrIndex(var122, 0.0);
            double var129 = ((double) var34);
            double var151 = ((double) var43);
            double var131 = (var129 / var151);
            double var155 = (var39 / arg3);
            int var125 = arg6.computeColorOrIndex(var131, var155);
            double var144 = ((double) (var34 + 1));
            double var161 = ((double) var43);
            double var146 = (var144 / var161);
            double var165 = (var41 / arg3);
            int var134 = arg6.computeColorOrIndex(var146, var165);
            arg0.applyStep(var46, var88, var91, var112, var125, var134);
            var34 = (var34 + 1);
        }
    }

    public static void drawGeometryPart(CosmeticDrawBuilder arg0, double arg1, double arg2, double arg3, int arg4, IGradientColorSampler arg5, boolean arg6) {
        int var30 = 0;
        double var78 = (arg1 + arg3);
        double[] var39 = computeVector(0.0, var78, 0.0);
        double[] var42 = computeVector(0.0, arg1, 0.0);
        var30 = 0;
        while (true) {
            if (var30 >= arg4) break;
            double var46 = ((double) var30);
            double var83 = ((double) arg4);
            double var48 = (var46 / var83);
            double var52 = ((double) (var30 + 1));
            double var86 = ((double) arg4);
            double var54 = (var52 / var86);
            double var60 = ((var48 + var54) * 0.5);
            double var96 = Math.cos((var48 * 6.283185307179586));
            double var64 = (arg2 * var96);
            double var139 = Math.sin((var48 * 6.283185307179586));
            double var119 = (arg2 * var139);
            double[] var66 = computeVector(var64, arg1, var119);
            double var104 = Math.cos((var54 * 6.283185307179586));
            double var69 = (arg2 * var104);
            double var145 = Math.sin((var54 * 6.283185307179586));
            double var125 = (arg2 * var145);
            double[] var71 = computeVector(var69, arg1, var125);
            int var128 = arg5.computeColorOrIndex(var48, 0.0);
            int var132 = arg5.computeColorOrIndex(var54, 0.0);
            int var150 = arg5.computeColorOrIndex(var60, 1.0);
            arg0.applyStep(var66, var71, var39, var128, var132, var150);
            if (arg6) {
                int var130 = arg5.computeColorOrIndex(var60, 0.0);
                int var136 = arg5.computeColorOrIndex(var54, 0.0);
                int var154 = arg5.computeColorOrIndex(var48, 0.0);
                arg0.applyStep(var42, var71, var66, var130, var136, var154);
            }
            var30 = (var30 + 1);
        }
    }

    public static void drawGeometryPart(CosmeticDrawBuilder arg0, double arg1, double arg2, double arg3, double arg4, int arg5, IGradientColorSampler arg6, boolean arg7, boolean arg8) {
        int var34 = 0;
        var34 = 0;
        while (true) {
            if (var34 >= arg5) break;
            double var51 = ((double) var34);
            double var114 = ((double) arg5);
            double var53 = (var51 / var114);
            double var57 = ((double) (var34 + 1));
            double var117 = ((double) arg5);
            double var59 = (var57 / var117);
            double var65 = ((var53 + var59) * 0.5);
            double var71 = Math.cos((var53 * 6.283185307179586));
            double var77 = Math.sin((var53 * 6.283185307179586));
            double var83 = Math.cos((var59 * 6.283185307179586));
            double var89 = Math.sin((var59 * 6.283185307179586));
            double var105 = (arg3 * var71);
            double var190 = (arg3 * var77);
            double[] var107 = computeVector(var105, arg1, var190);
            double var133 = (arg3 * var83);
            double var218 = (arg3 * var89);
            double[] var135 = computeVector(var133, arg1, var218);
            double var154 = (arg4 * var83);
            double var246 = (arg4 * var89);
            double[] var156 = computeVector(var154, arg2, var246);
            double var175 = (arg4 * var71);
            double var270 = (arg4 * var77);
            double[] var177 = computeVector(var175, arg2, var270);
            int var197 = arg6.computeColorOrIndex(var53, 0.0);
            int var227 = arg6.computeColorOrIndex(var59, 0.0);
            int var251 = arg6.computeColorOrIndex(var59, 1.0);
            int var277 = arg6.computeColorOrIndex(var53, 1.0);
            arg0.applyStep(var107, var135, var156, var177, var197, var227, var251, var277);
            if (arg7) {
                if (Double.compare(arg3, 1.0E-9) > 0) {
                    double[] var110 = computeVector(0.0, arg1, 0.0);
                    double var140 = (arg3 * var83);
                    double var230 = (arg3 * var89);
                    double[] var142 = computeVector(var140, arg1, var230);
                    double var161 = (arg3 * var71);
                    double var254 = (arg3 * var77);
                    double[] var163 = computeVector(var161, arg1, var254);
                    int var183 = arg6.computeColorOrIndex(var65, 0.0);
                    int var207 = arg6.computeColorOrIndex(var59, 0.0);
                    int var235 = arg6.computeColorOrIndex(var53, 0.0);
                    arg0.applyStep(var110, var142, var163, var183, var207, var235);
                }
                if (arg8) {
                    if (Double.compare(arg4, 1.0E-9) > 0) {
                        double[] var113 = computeVector(0.0, arg2, 0.0);
                        double var147 = (arg4 * var71);
                        double var238 = (arg4 * var77);
                        double[] var149 = computeVector(var147, arg2, var238);
                        double var168 = (arg4 * var83);
                        double var262 = (arg4 * var89);
                        double[] var170 = computeVector(var168, arg2, var262);
                        int var189 = arg6.computeColorOrIndex(var65, 1.0);
                        int var217 = arg6.computeColorOrIndex(var53, 1.0);
                        int var243 = arg6.computeColorOrIndex(var59, 1.0);
                        arg0.applyStep(var113, var149, var170, var189, var217, var243);
                    }
                    var34 = (var34 + 1);
                }
            }
        }
    }

    public static void drawGeometryPart(CosmeticDrawBuilder arg0, double arg1, double arg2, double arg3, int arg4, int arg5, IGradientColorSampler arg6) {
        int var31 = 0;
        int var44 = 0;
        var31 = 0;
        while (true) {
            if (var31 >= arg4) break;
            double var59 = ((double) var31);
            double var131 = ((double) arg4);
            double var61 = (var59 / var131);
            double var65 = ((double) (var31 + 1));
            double var134 = ((double) arg4);
            double var67 = (var65 / var134);
            double var73 = Math.cos((var61 * 6.283185307179586));
            double var79 = Math.sin((var61 * 6.283185307179586));
            double var85 = Math.cos((var67 * 6.283185307179586));
            double var91 = Math.sin((var67 * 6.283185307179586));
            var44 = 0;
            while (true) {
                if (var44 >= arg5) break;
                double var96 = ((double) var44);
                double var145 = ((double) arg5);
                double var98 = (var96 / var145);
                double var102 = ((double) (var44 + 1));
                double var148 = ((double) arg5);
                double var104 = (var102 / var148);
                double var180 = Math.cos((var98 * 6.283185307179586));
                double var152 = (arg3 * var180);
                double var108 = (arg2 + var152);
                double var186 = Math.sin((var98 * 6.283185307179586));
                double var156 = (arg3 * var186);
                double var112 = (arg1 + var156);
                double var192 = Math.cos((var104 * 6.283185307179586));
                double var160 = (arg3 * var192);
                double var116 = (arg2 + var160);
                double var198 = Math.sin((var104 * 6.283185307179586));
                double var164 = (arg3 * var198);
                double var120 = (arg1 + var164);
                double var128 = (var108 * var73);
                double var209 = (var108 * var79);
                double[] var130 = computeVector(var128, var112, var209);
                double var168 = (var108 * var85);
                double var225 = (var108 * var91);
                double[] var170 = computeVector(var168, var112, var225);
                double var175 = (var116 * var85);
                double var237 = (var116 * var91);
                double[] var177 = computeVector(var175, var120, var237);
                double var206 = (var116 * var73);
                double var245 = (var116 * var79);
                double[] var208 = computeVector(var206, var120, var245);
                int var216 = arg6.computeColorOrIndex(var61, var98);
                int var234 = arg6.computeColorOrIndex(var67, var98);
                int var242 = arg6.computeColorOrIndex(var67, var104);
                int var252 = arg6.computeColorOrIndex(var61, var104);
                arg0.applyStep(var130, var170, var177, var208, var216, var234, var242, var252);
                var44 = (var44 + 1);
            }
            var31 = (var31 + 1);
        }
    }

    public static void drawGeometryPart(CosmeticDrawBuilder arg0, double arg1, double arg2, double arg3, double arg4, int arg5, int arg6, IGradientColorSampler arg7) {
        drawGeometryPart(arg0, arg1, arg2, arg3, arg4, arg4, arg4, arg5, arg6, arg7);
    }

    public static void drawGeometryPart(CosmeticDrawBuilder arg0, double arg1, double arg2, double arg3, double arg4, double arg5, double arg6, int arg7, int arg8, IGradientColorSampler arg9) {
        int var39 = 0;
        int var56 = 0;
        var39 = 0;
        while (true) {
            if (var39 < arg8) {
                double var71 = ((double) var39);
                double var151 = ((double) arg8);
                double var73 = (var71 / var151);
                double var77 = ((double) (var39 + 1));
                double var154 = ((double) arg8);
                double var79 = (var77 / var154);
                double var158 = (3.141592653589793 * var73);
                double var83 = (-1.5707963267948966 + var158);
                double var162 = (3.141592653589793 * var79);
                double var87 = (-1.5707963267948966 + var162);
                double var91 = Math.cos(var83);
                double var95 = Math.sin(var83);
                double var99 = Math.cos(var87);
                double var103 = Math.sin(var87);
                var56 = 0;
                while (true) {
                    if (var56 >= arg7) break;
                    double var108 = ((double) var56);
                    double var165 = ((double) arg7);
                    double var110 = (var108 / var165);
                    double var114 = ((double) (var56 + 1));
                    double var168 = ((double) arg7);
                    double var116 = (var114 / var168);
                    double var122 = Math.cos((var110 * 6.283185307179586));
                    double var128 = Math.sin((var110 * 6.283185307179586));
                    double var134 = Math.cos((var116 * 6.283185307179586));
                    double var140 = Math.sin((var116 * 6.283185307179586));
                    double var185 = ((arg4 * var91) * var122);
                    double var148 = (arg1 + var185);
                    double var217 = (arg5 * var95);
                    double var189 = (arg2 + var217);
                    double var263 = ((arg6 * var91) * var128);
                    double var221 = (arg3 + var263);
                    double[] var150 = computeVector(var148, var189, var221);
                    double var202 = ((arg4 * var91) * var134);
                    double var180 = (arg1 + var202);
                    double var239 = (arg5 * var95);
                    double var206 = (arg2 + var239);
                    double var285 = ((arg6 * var91) * var140);
                    double var243 = (arg3 + var285);
                    double[] var182 = computeVector(var180, var206, var243);
                    double var227 = ((arg4 * var99) * var134);
                    double var193 = (arg1 + var227);
                    double var271 = (arg5 * var103);
                    double var231 = (arg2 + var271);
                    double var313 = ((arg6 * var99) * var140);
                    double var275 = (arg3 + var313);
                    double[] var195 = computeVector(var193, var231, var275);
                    double var249 = ((arg4 * var99) * var122);
                    double var210 = (arg1 + var249);
                    double var293 = (arg5 * var103);
                    double var253 = (arg2 + var293);
                    double var327 = ((arg6 * var99) * var128);
                    double var297 = (arg3 + var327);
                    double[] var212 = computeVector(var210, var253, var297);
                    int var234 = arg9.computeColorOrIndex(var110, var73);
                    int var258 = arg9.computeColorOrIndex(var116, var73);
                    int var280 = arg9.computeColorOrIndex(var116, var79);
                    int var304 = arg9.computeColorOrIndex(var110, var79);
                    arg0.applyStep(var150, var182, var195, var212, var234, var258, var280, var304);
                    var56 = (var56 + 1);
                }
                return;
                var39 = (var39 + 1);
            }
        }
    }

    public static void drawGeometryPart(CosmeticDrawBuilder arg0, double arg1, double arg2, double arg3, int arg4, double arg5, IGradientColorSampler arg6, boolean arg7) {
        int var33 = 0;
        var33 = 0;
        while (true) {
            if (var33 >= arg4) break;
            double var56 = ((double) var33);
            double var118 = ((double) arg4);
            double var58 = (var56 / var118);
            double var62 = ((double) (var33 + 1));
            double var121 = ((double) arg4);
            double var64 = (var62 / var121);
            double var70 = ((var58 + var64) * 0.5);
            double var129 = (var58 * 6.283185307179586);
            double var74 = (arg5 + var129);
            double var133 = (var64 * 6.283185307179586);
            double var78 = (arg5 + var133);
            double var137 = (var70 * 6.283185307179586);
            double var84 = computeDoubleMetric1((arg5 + var137));
            double var88 = Math.cos(var74);
            double var92 = Math.sin(var74);
            double var96 = Math.cos(var78);
            double var100 = Math.sin(var78);
            double var109 = (arg3 * var88);
            double var202 = (arg3 * var92);
            double[] var111 = computeVector(var109, arg1, var202);
            double var141 = (arg3 * var96);
            double var237 = (arg3 * var100);
            double[] var143 = computeVector(var141, arg1, var237);
            double var158 = (arg3 * var96);
            double var274 = (arg3 * var100);
            double[] var160 = computeVector(var158, arg2, var274);
            double var185 = (arg3 * var88);
            double var305 = (arg3 * var92);
            double[] var187 = computeVector(var185, arg2, var305);
            int var210 = ColorMathUtils.computeColorOrIndex6(arg6.computeColorOrIndex(var70, 0.0), var84);
            int var249 = ColorMathUtils.computeColorOrIndex6(arg6.computeColorOrIndex(var70, 0.0), var84);
            int var282 = ColorMathUtils.computeColorOrIndex6(arg6.computeColorOrIndex(var70, 1.0), var84);
            int var315 = ColorMathUtils.computeColorOrIndex6(arg6.computeColorOrIndex(var70, 1.0), var84);
            arg0.applyStep(var111, var143, var160, var187, var210, var249, var282, var315);
            if (arg7) {
                double[] var114 = computeVector(0.0, arg1, 0.0);
                double var146 = (arg3 * var96);
                double var252 = (arg3 * var100);
                double[] var148 = computeVector(var146, arg1, var252);
                double var165 = (arg3 * var88);
                double var285 = (arg3 * var92);
                double[] var167 = computeVector(var165, arg1, var285);
                int var194 = ColorMathUtils.computeColorOrIndex6(arg6.computeColorOrIndex(var70, 0.0), 0.7);
                int var223 = ColorMathUtils.computeColorOrIndex6(arg6.computeColorOrIndex(var70, 0.0), 0.7);
                int var260 = ColorMathUtils.computeColorOrIndex6(arg6.computeColorOrIndex(var70, 0.0), 0.7);
                arg0.applyStep(var114, var148, var167, var194, var223, var260);
                double[] var117 = computeVector(0.0, arg2, 0.0);
                double var151 = (arg3 * var88);
                double var263 = (arg3 * var92);
                double[] var153 = computeVector(var151, arg2, var263);
                double var172 = (arg3 * var96);
                double var295 = (arg3 * var100);
                double[] var174 = computeVector(var172, arg2, var295);
                int var201 = ColorMathUtils.computeColorOrIndex6(arg6.computeColorOrIndex(var70, 1.0), 1.1);
                int var236 = ColorMathUtils.computeColorOrIndex6(arg6.computeColorOrIndex(var70, 1.0), 1.1);
                int var271 = ColorMathUtils.computeColorOrIndex6(arg6.computeColorOrIndex(var70, 1.0), 1.1);
                arg0.applyStep(var117, var153, var174, var201, var236, var271);
            }
            var33 = (var33 + 1);
        }
    }

    public static void drawGeometryPart(CosmeticDrawBuilder arg0, double arg1, double arg2, double arg3, double arg4, double arg5, double arg6, int arg7, int arg8) {
        int var26 = 0;
        double[][] var30 = new double[8][];
        double[] var95 = new double[3];
        var95[0] = arg1;
        var95[1] = arg2;
        var95[2] = arg3;
        var30[0] = var95;
        double[] var97 = new double[3];
        var97[0] = arg4;
        var97[1] = arg2;
        var97[2] = arg3;
        var30[1] = var97;
        double[] var99 = new double[3];
        var99[0] = arg4;
        var99[1] = arg5;
        var99[2] = arg3;
        var30[2] = var99;
        double[] var101 = new double[3];
        var101[0] = arg1;
        var101[1] = arg5;
        var101[2] = arg3;
        var30[3] = var101;
        double[] var103 = new double[3];
        var103[0] = arg1;
        var103[1] = arg2;
        var103[2] = arg6;
        var30[4] = var103;
        double[] var105 = new double[3];
        var105[0] = arg4;
        var105[1] = arg2;
        var105[2] = arg6;
        var30[5] = var105;
        double[] var107 = new double[3];
        var107[0] = arg4;
        var107[1] = arg5;
        var107[2] = arg6;
        var30[6] = var107;
        double[] var109 = new double[3];
        var109[0] = arg1;
        var109[1] = arg5;
        var109[2] = arg6;
        var30[7] = var109;
        int[][] var32 = new int[4][];
        int[] var111 = new int[4];
        var111[0] = 0;
        var111[1] = 1;
        var111[2] = 2;
        var111[3] = 3;
        var32[0] = var111;
        int[] var113 = new int[4];
        var113[0] = 5;
        var113[1] = 4;
        var113[2] = 7;
        var113[3] = 6;
        var32[1] = var113;
        int[] var115 = new int[4];
        var115[0] = 4;
        var115[1] = 0;
        var115[2] = 3;
        var115[3] = 7;
        var32[2] = var115;
        int[] var117 = new int[4];
        var117[0] = 1;
        var117[1] = 5;
        var117[2] = 6;
        var117[3] = 2;
        var32[3] = var117;
        double[] var34 = new double[4];
        var34[0] = 0.8;
        var34[1] = 0.8;
        var34[2] = 0.9;
        var34[3] = 0.9;
        var26 = 0;
        while (true) {
            if (var26 >= 4) break;
            int var51 = var26;
            int[] var38 = var32[var51];
            int var83 = var26;
            double var53 = var34[var83];
            int var40 = ColorMathUtils.computeColorOrIndex6(arg7, var53);
            int var84 = var26;
            double var56 = var34[var84];
            int var42 = ColorMathUtils.computeColorOrIndex6(arg8, var56);
            int var86 = var38[0];
            double[] var59 = var30[var86];
            int var128 = var38[1];
            double[] var88 = var30[var128];
            int var139 = var38[2];
            double[] var130 = var30[var139];
            int var189 = var38[3];
            double[] var141 = var30[var189];
            arg0.applyStep(var59, var88, var130, var141, var40, var40, var42, var42);
            var26 = (var26 + 1);
        }
        int var45 = ColorMathUtils.computeColorOrIndex6(arg8, 1.1);
        int var47 = ColorMathUtils.computeColorOrIndex6(arg7, 0.6);
        double[] var65 = var30[3];
        double[] var91 = var30[2];
        double[] var133 = var30[6];
        double[] var144 = var30[7];
        arg0.applyStep(var65, var91, var133, var144, var45);
        double[] var67 = var30[0];
        double[] var94 = var30[4];
        double[] var136 = var30[5];
        double[] var147 = var30[1];
        arg0.applyStep(var67, var94, var136, var147, var47);
    }

    public static void drawGeometryPart(CosmeticDrawBuilder arg0, double[] arg1, double[] arg2, double arg3, double arg4, int arg5, IGradientColorSampler arg6, boolean arg7) {
        int var31 = 0;
        double[][] var53 = computeMatrix(computeVector(computeVector(arg2, arg1)));
        var31 = 0;
        while (true) {
            if (var31 >= arg5) break;
            double var57 = ((double) var31);
            double var129 = ((double) arg5);
            double var59 = (var57 / var129);
            double var63 = ((double) (var31 + 1));
            double var132 = ((double) arg5);
            double var65 = (var63 / var132);
            double var71 = ((var59 + var65) * 0.5);
            double var77 = Math.cos((var59 * 6.283185307179586));
            double var83 = Math.sin((var59 * 6.283185307179586));
            double var89 = Math.cos((var65 * 6.283185307179586));
            double var95 = Math.sin((var65 * 6.283185307179586));
            double[] var119 = var53[0];
            double var149 = (arg3 * var77);
            double[] var176 = var53[1];
            double var196 = (arg3 * var83);
            double[] var98 = computeVector(arg1, var119, var149, var176, var196);
            double[] var121 = var53[0];
            double var154 = (arg3 * var89);
            double[] var180 = var53[1];
            double var201 = (arg3 * var95);
            double[] var100 = computeVector(arg1, var121, var154, var180, var201);
            double[] var123 = var53[0];
            double var159 = (arg4 * var77);
            double[] var184 = var53[1];
            double var206 = (arg4 * var83);
            double[] var102 = computeVector(arg2, var123, var159, var184, var206);
            double[] var125 = var53[0];
            double var164 = (arg4 * var89);
            double[] var188 = var53[1];
            double var211 = (arg4 * var95);
            double[] var104 = computeVector(arg2, var125, var164, var188, var211);
            int var214 = arg6.computeColorOrIndex(var59, 0.0);
            int var224 = arg6.computeColorOrIndex(var65, 0.0);
            int var242 = arg6.computeColorOrIndex(var65, 1.0);
            int var254 = arg6.computeColorOrIndex(var59, 1.0);
            arg0.applyStep(var98, var100, var104, var102, var214, var224, var242, var254);
            if (arg7) {
                if (Double.compare(arg3, 1.0E-9) > 0) {
                    int var191 = arg6.computeColorOrIndex(var71, 0.0);
                    int var218 = arg6.computeColorOrIndex(var65, 0.0);
                    int var228 = arg6.computeColorOrIndex(var59, 0.0);
                    arg0.applyStep(arg1, var100, var98, var191, var218, var228);
                }
                if (arg7) {
                    if (Double.compare(arg4, 1.0E-9) > 0) {
                        int var193 = arg6.computeColorOrIndex(var71, 1.0);
                        int var222 = arg6.computeColorOrIndex(var59, 1.0);
                        int var232 = arg6.computeColorOrIndex(var65, 1.0);
                        arg0.applyStep(arg2, var102, var104, var193, var222, var232);
                    }
                    var31 = (var31 + 1);
                }
            }
        }
    }

    public static void drawGeometryPart(CosmeticDrawBuilder arg0, double[] arg1, double[] arg2, double arg3, int arg4, IGradientColorSampler arg5) {
        int var23 = 0;
        double[][] var36 = computeMatrix(computeVector(computeVector(arg2, arg1)));
        var23 = 0;
        while (true) {
            if (var23 >= arg4) break;
            double var40 = ((double) var23);
            double var74 = ((double) arg4);
            double var42 = (var40 / var74);
            double var46 = ((double) (var23 + 1));
            double var77 = ((double) arg4);
            double var48 = (var46 / var77);
            double var54 = ((var42 + var48) * 0.5);
            double var60 = computeDoubleMetric1((var54 * 6.283185307179586));
            double[] var70 = var36[0];
            double var98 = Math.cos((var42 * 6.283185307179586));
            double var88 = (arg3 * var98);
            double[] var101 = var36[1];
            double var140 = Math.sin((var42 * 6.283185307179586));
            double var115 = (arg3 * var140);
            double[] var63 = computeVector(arg1, var70, var88, var101, var115);
            double[] var72 = var36[0];
            double var106 = Math.cos((var48 * 6.283185307179586));
            double var93 = (arg3 * var106);
            double[] var109 = var36[1];
            double var146 = Math.sin((var48 * 6.283185307179586));
            double var120 = (arg3 * var146);
            double[] var65 = computeVector(arg1, var72, var93, var109, var120);
            int var112 = ColorMathUtils.computeColorOrIndex6(arg5.computeColorOrIndex(var54, 0.0), var60);
            int var128 = ColorMathUtils.computeColorOrIndex6(arg5.computeColorOrIndex(var54, 0.0), var60);
            int var137 = ColorMathUtils.computeColorOrIndex6(arg5.computeColorOrIndex(var54, 1.0), var60);
            arg0.applyStep(var63, var65, arg2, var112, var128, var137);
            var23 = (var23 + 1);
        }
    }

    public static void drawGeometryPart(CosmeticDrawBuilder arg0, double[][] arg1, double arg2, double arg3, double[] arg4, IGradientColorSampler arg5) {
        int var27 = 0;
        double var52 = 0.0;
        int var220 = 0;
        int var32 = arg1.length;
        if (var32 < 2) {
            return;
        }
        double[][] var35 = new double[var32][];
        double[][] var37 = new double[var32][];
        var27 = 0;
        while (true) {
            if (var27 >= var32) break;
            int var78 = (var27 + 1);
            int var94 = (var32 - 1);
            int var79 = Math.min(var78, var94);
            double[] var41 = arg1[var79];
            int var97 = Math.max((var27 - 1), 0);
            double[] var81 = arg1[var97];
            double[] var43 = computeVector(computeVector(var41, var81));
            double[] var83 = new double[3];
            double var142 = var43[0];
            double var187 = computeDoubleMetric(arg4, var43);
            double var144 = (var142 * var187);
            var83[0] = var144;
            double var147 = var43[1];
            double var190 = computeDoubleMetric(arg4, var43);
            double var149 = (var147 * var190);
            var83[1] = var149;
            double var152 = var43[2];
            double var193 = computeDoubleMetric(arg4, var43);
            double var154 = (var152 * var193);
            var83[2] = var154;
            double[] var45 = computeVector(arg4, var83);
            if (Double.compare(computeDoubleMetric(var45, var45), 1.0E-10) < 0) {
                var52 = computeMatrix(var43)[0];
            } else {
                var52 = computeVector(var45);
            }
            double var223 = var52;
            double var102 = (arg3 - arg2);
            double var159 = ((double) var27);
            double var104 = (var102 * var159);
            double var164 = (((double) var32) - 1.0);
            double var106 = (var104 / var164);
            double var57 = ((arg2 + var106) * 0.5);
            int var86 = var27;
            int var135 = var27;
            double[] var112 = computeVector(arg1[var135], var223, var57, var223, 0.0);
            var35[var86] = var112;
            int var87 = var27;
            int var137 = var27;
            double[] var114 = arg1[var137];
            double var170 = -(var57);
            double[] var115 = computeVector(var114, var223, var170, var223, 0.0);
            var37[var87] = var115;
            var27 = (var27 + 1);
        }
        var220 = 0;
        while (true) {
            int var63 = var220;
            int var89 = (var32 - 1);
            if (var63 >= var89) break;
            double var65 = ((double) var220);
            double var120 = (((double) var32) - 1.0);
            double var67 = (var65 / var120);
            double var71 = ((double) (var220 + 1));
            double var125 = (((double) var32) - 1.0);
            double var73 = (var71 / var125);
            int var127 = var220;
            double[] var92 = var35[var127];
            int var139 = var220;
            double[] var129 = var37[var139];
            int var177 = (var220 + 1);
            double[] var141 = var37[var177];
            int var184 = (var220 + 1);
            double[] var179 = var35[var184];
            int var186 = arg5.computeColorOrIndex(0.0, var67);
            int var203 = arg5.computeColorOrIndex(1.0, var67);
            int var213 = arg5.computeColorOrIndex(1.0, var73);
            int var217 = arg5.computeColorOrIndex(0.0, var73);
            arg0.applyStep(var92, var129, var141, var179, var186, var203, var213, var217);
            var220 = (var220 + 1);
        }
    }

    public static double[][] computeMatrix(int arg0, double[][] arg1) {
        int var16 = 0;
        double[] var55 = null;
        if (arg1.length != 3) {
            if (arg1.length != 4) {
                IllegalArgumentException var24 = new IllegalArgumentException("3 or 4 control points");
                throw var24;
            }
        }
        double[][] var27 = new double[(arg0 + 1)][];
        var16 = 0;
        while (true) {
            if (var16 > arg0) break;
            double var32 = ((double) var16);
            double var46 = ((double) arg0);
            double var34 = (var32 / var46);
            double var38 = (1.0 - var34);
            int var44 = var16;
            if (arg1.length == 3) {
                double[] var54 = arg1[0];
                double[] var58 = arg1[1];
                double[] var63 = arg1[2];
                var55 = computeVector(var54, var58, var63, var34);
            } else {
                var55 = new double[3];
                double var66 = ((var38 * var38) * var38);
                double var111 = arg1[0][0];
                double var68 = (var66 * var111);
                double var119 = (((3.0 * var38) * var38) * var34);
                double var224 = arg1[1][0];
                double var121 = (var119 * var224);
                double var70 = (var68 + var121);
                double var129 = (((3.0 * var38) * var34) * var34);
                double var234 = arg1[2][0];
                double var131 = (var129 * var234);
                double var72 = (var70 + var131);
                double var137 = ((var34 * var34) * var34);
                double var242 = arg1[3][0];
                double var139 = (var137 * var242);
                double var74 = (var72 + var139);
                var55[0] = var74;
                double var80 = ((var38 * var38) * var38);
                double var147 = arg1[0][1];
                double var82 = (var80 * var147);
                double var155 = (((3.0 * var38) * var38) * var34);
                double var252 = arg1[1][1];
                double var157 = (var155 * var252);
                double var84 = (var82 + var157);
                double var165 = (((3.0 * var38) * var34) * var34);
                double var262 = arg1[2][1];
                double var167 = (var165 * var262);
                double var86 = (var84 + var167);
                double var173 = ((var34 * var34) * var34);
                double var270 = arg1[3][1];
                double var175 = (var173 * var270);
                double var88 = (var86 + var175);
                var55[1] = var88;
                double var94 = ((var38 * var38) * var38);
                double var183 = arg1[0][2];
                double var96 = (var94 * var183);
                double var191 = (((3.0 * var38) * var38) * var34);
                double var280 = arg1[1][2];
                double var193 = (var191 * var280);
                double var98 = (var96 + var193);
                double var201 = (((3.0 * var38) * var34) * var34);
                double var290 = arg1[2][2];
                double var203 = (var201 * var290);
                double var100 = (var98 + var203);
                double var209 = ((var34 * var34) * var34);
                double var298 = arg1[3][2];
                double var211 = (var209 * var298);
                double var102 = (var100 + var211);
                var55[2] = var102;
            }
            var27[var44] = var55;
            var16 = (var16 + 1);
        }
        return var27;
    }

    public static double[] computeVector(double[][] arg0, int arg1) {
        int var9 = (arg1 + 1);
        int var15 = (arg0.length - 1);
        int var10 = Math.min(var9, var15);
        double[] var6 = arg0[var10];
        int var18 = Math.max((arg1 - 1), 0);
        double[] var12 = arg0[var18];
        return computeVector(computeVector(var6, var12));
    }

    public static void drawGeometryPart(CosmeticDrawBuilder arg0, double[][] arg1, DoubleUnaryOperator arg2, int arg3, IGradientColorSampler arg4, boolean arg5, boolean arg6) {
        double[] var27 = null;
        int var28 = 0;
        int var38 = 0;
        int var379 = 0;
        int var51 = arg1.length;
        if (var51 < 2) {
            return;
        }
        double[][] var54 = new double[var51][];
        double[][] var56 = new double[var51][];
        var27 = computeMatrix(computeVector(arg1, 0))[0];
        var28 = 0;
        while (true) {
            if (var28 >= var51) break;
            int var137 = var28;
            double[] var64 = computeVector(arg1, var137);
            double var66 = computeDoubleMetric(var27, var64);
            double[] var69 = new double[3];
            double var200 = var27[0];
            double var264 = (var64[0] * var66);
            double var202 = (var200 - var264);
            var69[0] = var202;
            double var205 = var27[1];
            double var269 = (var64[1] * var66);
            double var207 = (var205 - var269);
            var69[1] = var207;
            double var210 = var27[2];
            double var274 = (var64[2] * var66);
            double var212 = (var210 - var274);
            var69[2] = var212;
            var27 = computeVector(var69);
            int var139 = var28;
            double[] var159 = var27;
            var54[var139] = var159;
            int var140 = var28;
            double[] var214 = var27;
            double[] var161 = computeVector1(var64, var214);
            var56[var140] = var161;
            var28 = (var28 + 1);
        }
        var379 = 0;
        while (true) {
            int var74 = var379;
            int var142 = (var51 - 1);
            if (var74 >= var142) break;
            double var76 = ((double) var379);
            double var166 = (((double) var51) - 1.0);
            double var78 = (var76 / var166);
            double var82 = ((double) (var379 + 1));
            double var171 = (((double) var51) - 1.0);
            double var84 = (var82 / var171);
            double var87 = arg2.applyAsDouble(var78);
            double var90 = arg2.applyAsDouble(var84);
            var38 = 0;
            while (true) {
                if (var38 >= arg3) break;
                double var95 = ((double) var38);
                double var174 = ((double) arg3);
                double var97 = (var95 / var174);
                double var101 = ((double) (var38 + 1));
                double var177 = ((double) arg3);
                double var103 = (var101 / var177);
                double var109 = Math.cos((var97 * 6.283185307179586));
                double var115 = Math.sin((var97 * 6.283185307179586));
                double var121 = Math.cos((var103 * 6.283185307179586));
                double var127 = Math.sin((var103 * 6.283185307179586));
                int var187 = var379;
                double[] var151 = arg1[var187];
                int var215 = var379;
                double[] var189 = var54[var215];
                double var218 = (var87 * var109);
                int var300 = var379;
                double[] var279 = var56[var300];
                double var303 = (var87 * var115);
                double[] var152 = computeVector(var151, var189, var218, var279, var303);
                int var220 = var379;
                double[] var191 = arg1[var220];
                int var240 = var379;
                double[] var222 = var54[var240];
                double var243 = (var87 * var121);
                int var327 = var379;
                double[] var308 = var56[var327];
                double var330 = (var87 * var127);
                double[] var192 = computeVector(var191, var222, var243, var308, var330);
                int var246 = (var379 + 1);
                double[] var224 = arg1[var246];
                int var282 = (var379 + 1);
                double[] var248 = var54[var282];
                double var285 = (var90 * var121);
                int var344 = (var379 + 1);
                double[] var335 = var56[var344];
                double var347 = (var90 * var127);
                double[] var225 = computeVector(var224, var248, var285, var335, var347);
                int var288 = (var379 + 1);
                double[] var250 = arg1[var288];
                int var312 = (var379 + 1);
                double[] var290 = var54[var312];
                double var315 = (var90 * var109);
                int var365 = (var379 + 1);
                double[] var352 = var56[var365];
                double var368 = (var90 * var115);
                double[] var251 = computeVector(var250, var290, var315, var352, var368);
                int var292 = arg4.computeColorOrIndex(var97, var78);
                int var320 = arg4.computeColorOrIndex(var103, var78);
                int var340 = arg4.computeColorOrIndex(var103, var84);
                int var358 = arg4.computeColorOrIndex(var97, var84);
                arg0.applyStep(var152, var192, var225, var251, var292, var320, var340, var358);
                var38 = (var38 + 1);
            }
            var379 = (var379 + 1);
        }
        if (arg5) {
            double[] var154 = arg1[0];
            double[] var195 = var54[0];
            double[] var228 = var56[0];
            double var254 = arg2.applyAsDouble(0.0);
            drawGeometryPart(arg0, var154, var195, var228, var254, arg3, arg4, 0.0);
        }
        if (arg6) {
            int var197 = (var51 - 1);
            double[] var156 = arg1[var197];
            int var231 = (var51 - 1);
            double[] var199 = var54[var231];
            int var258 = (var51 - 1);
            double[] var233 = var56[var258];
            double var260 = arg2.applyAsDouble(1.0);
            drawGeometryPart(arg0, var156, var199, var233, var260, arg3, arg4, 1.0);
        }
    }

    private static void drawGeometryPart(CosmeticDrawBuilder arg0, double[] arg1, double[] arg2, double[] arg3, double arg4, int arg5, IGradientColorSampler arg6, double arg7) {
        int var34 = 0;
        if (Double.compare(arg4, 1.0E-9) < 0) {
            return;
        }
        var34 = 0;
        while (true) {
            if (var34 >= arg5) break;
            double var43 = ((double) var34);
            double var57 = ((double) arg5);
            double var45 = (var43 / var57);
            double var49 = ((double) (var34 + 1));
            double var60 = ((double) arg5);
            double var51 = (var49 / var60);
            double var83 = Math.cos((var45 * 6.283185307179586));
            double var66 = (arg4 * var83);
            double var115 = Math.sin((var45 * 6.283185307179586));
            double var90 = (arg4 * var115);
            double[] var63 = computeVector(arg1, arg2, var66, arg3, var90);
            double var96 = Math.cos((var51 * 6.283185307179586));
            double var71 = (arg4 * var96);
            double var123 = Math.sin((var51 * 6.283185307179586));
            double var109 = (arg4 * var123);
            double[] var65 = computeVector(arg1, arg2, var71, arg3, var109);
            double var77 = ((var45 + var51) * 0.5);
            int var70 = arg6.computeColorOrIndex(var77, arg7);
            int var80 = arg6.computeColorOrIndex(var45, arg7);
            int var89 = arg6.computeColorOrIndex(var51, arg7);
            arg0.applyStep(arg1, var63, var65, var70, var80, var89);
            var34 = (var34 + 1);
        }
    }

    public static void drawGeometryPart(CosmeticDrawBuilder arg0, double[][] arg1, double arg2, double arg3, int arg4, IGradientColorSampler arg5, boolean arg6) {
        DoubleUnaryOperator var18 = (p0) -> GeometryPrimitives.lambda$c(arg2, arg3, p0);
        drawGeometryPart(arg0, arg1, var18, arg4, arg5, arg6, arg6);
    }

    public static void drawGeometryPart(CosmeticDrawBuilder arg0, double[][] arg1, double[] arg2, DoubleUnaryOperator arg3, DoubleUnaryOperator arg4, DoubleUnaryOperator arg5, int arg6, IGradientColorSampler arg7) {
        int var28 = 0;
        int var48 = 0;
        int var421 = 0;
        double var219 = 0.0;
        int var61 = arg1.length;
        if (var61 < 2) {
            return;
        }
        double[][] var64 = new double[var61][];
        double[][] var66 = new double[var61][];
        var28 = 0;
        while (true) {
            if (var28 >= var61) break;
            int var169 = var28;
            double[] var139 = computeVector(arg1, var169);
            double var141 = computeDoubleMetric(arg2, var139);
            double[] var144 = new double[3];
            double var237 = arg2[0];
            double var307 = (var139[0] * var141);
            double var239 = (var237 - var307);
            var144[0] = var239;
            double var242 = arg2[1];
            double var312 = (var139[1] * var141);
            double var244 = (var242 - var312);
            var144[1] = var244;
            double var247 = arg2[2];
            double var317 = (var139[2] * var141);
            double var249 = (var247 - var317);
            var144[2] = var249;
            int var171 = var28;
            if (Double.compare(computeDoubleMetric(var144, var144), 1.0E-10) < 0) {
                var219 = computeMatrix(var139)[0];
            } else {
                var219 = computeVector(var144);
            }
            var64[var171] = var219;
            int var172 = var28;
            int var253 = var28;
            double[] var217 = computeVector1(var64[var253], var139);
            var66[var172] = var217;
            var28 = (var28 + 1);
        }
        var421 = 0;
        while (true) {
            int var70 = var421;
            int var149 = (var61 - 1);
            if (var70 >= var149) break;
            double var72 = ((double) var421);
            double var176 = (((double) var61) - 1.0);
            double var74 = (var72 / var176);
            double var78 = ((double) (var421 + 1));
            double var181 = (((double) var61) - 1.0);
            double var80 = (var78 / var181);
            double var83 = arg3.applyAsDouble(var74);
            double var86 = arg4.applyAsDouble(var74);
            double var89 = arg5.applyAsDouble(var74);
            double var92 = arg3.applyAsDouble(var80);
            double var95 = arg4.applyAsDouble(var80);
            double var98 = arg5.applyAsDouble(var80);
            var48 = 0;
            while (true) {
                if (var48 >= arg6) break;
                double var103 = ((double) var48);
                double var184 = ((double) arg6);
                double var105 = (var103 / var184);
                double var109 = ((double) (var48 + 1));
                double var187 = ((double) arg6);
                double var111 = (var109 / var187);
                double var117 = Math.cos((var105 * 6.283185307179586));
                double var123 = Math.sin((var105 * 6.283185307179586));
                double var129 = Math.cos((var111 * 6.283185307179586));
                double var135 = Math.sin((var111 * 6.283185307179586));
                int var197 = var421;
                double[] var166 = arg1[var197];
                int var11 = var421;
                double[] var199 = var66[var11];
                int var15 = var421;
                double[] var280 = var66[var15];
                double[] var167 = computeVector(var166, var199, var89, var280, 0.0);
                int var222 = var421;
                double[] var201 = var64[var222];
                double var225 = (var83 * var117);
                int var321 = var421;
                double[] var284 = var66[var321];
                double var324 = (var86 * var123);
                double[] var168 = computeVector(var167, var201, var225, var284, var324);
                int var227 = var421;
                double[] var203 = arg1[var227];
                int var257 = var421;
                double[] var229 = var66[var257];
                int var16 = var421;
                double[] var327 = var66[var16];
                double[] var204 = computeVector(var203, var229, var89, var327, 0.0);
                int var260 = var421;
                double[] var231 = var64[var260];
                double var263 = (var83 * var129);
                int var354 = var421;
                double[] var331 = var66[var354];
                double var357 = (var86 * var135);
                double[] var205 = computeVector(var204, var231, var263, var331, var357);
                int var266 = (var421 + 1);
                double[] var233 = arg1[var266];
                int var287 = (var421 + 1);
                double[] var268 = var66[var287];
                int var378 = (var421 + 1);
                double[] var360 = var66[var378];
                double[] var234 = computeVector(var233, var268, var98, var360, 0.0);
                int var291 = (var421 + 1);
                double[] var270 = var64[var291];
                double var294 = (var92 * var129);
                int var382 = (var421 + 1);
                double[] var364 = var66[var382];
                double var385 = (var95 * var135);
                double[] var235 = computeVector(var234, var270, var294, var364, var385);
                int var297 = (var421 + 1);
                double[] var272 = arg1[var297];
                int var336 = (var421 + 1);
                double[] var299 = var66[var336];
                int var402 = (var421 + 1);
                double[] var388 = var66[var402];
                double[] var273 = computeVector(var272, var299, var98, var388, 0.0);
                int var340 = (var421 + 1);
                double[] var301 = var64[var340];
                double var343 = (var92 * var117);
                int var406 = (var421 + 1);
                double[] var392 = var66[var406];
                double var409 = (var95 * var123);
                double[] var274 = computeVector(var273, var301, var343, var392, var409);
                int var303 = arg7.computeColorOrIndex(var105, var74);
                int var348 = arg7.computeColorOrIndex(var111, var74);
                int var370 = arg7.computeColorOrIndex(var111, var80);
                int var398 = arg7.computeColorOrIndex(var105, var80);
                arg0.applyStep(var168, var205, var235, var274, var303, var348, var370, var398);
                var48 = (var48 + 1);
            }
            var421 = (var421 + 1);
        }
    }

    public static void drawGeometryPart(CosmeticDrawBuilder arg0, double[][] arg1, double[] arg2, DoubleUnaryOperator arg3, DoubleUnaryOperator arg4, int arg5, IGradientColorSampler arg6) {
        DoubleUnaryOperator var12 = (p0) -> GeometryPrimitives.lambda$c(p0);
        drawGeometryPart(arg0, arg1, arg2, arg3, arg4, var12, arg5, arg6);
    }

    public static void drawGeometryPart(CosmeticDrawBuilder arg0, double arg1, double arg2, double arg3, int arg4, double arg5, double arg6, int arg7, int arg8, IGradientColorSampler arg9) {
        int var47 = 0;
        int var52 = 0;
        var47 = 0;
        while (true) {
            if (var47 < arg7) {
                double var59 = ((double) var47);
                double var89 = ((double) arg7);
                double var61 = (var59 / var89);
                double var65 = ((double) (var47 + 1));
                double var92 = ((double) arg7);
                double var67 = (var65 / var92);
                var52 = 0;
                while (true) {
                    if (var52 >= arg8) break;
                    double var72 = ((double) var52);
                    double var95 = ((double) arg8);
                    double var74 = (var72 / var95);
                    double var78 = ((double) (var52 + 1));
                    double var98 = ((double) arg8);
                    double var80 = (var78 / var98);
                    double[] var88 = computeVector(arg1, arg2, arg3, arg4, arg5, arg6, var74, var61);
                    double[] var102 = computeVector(arg1, arg2, arg3, arg4, arg5, arg6, var80, var61);
                    double[] var105 = computeVector(arg1, arg2, arg3, arg4, arg5, arg6, var80, var67);
                    double[] var108 = computeVector(arg1, arg2, arg3, arg4, arg5, arg6, var74, var67);
                    int var112 = arg9.computeColorOrIndex(var74, var61);
                    int var118 = arg9.computeColorOrIndex(var80, var61);
                    int var124 = arg9.computeColorOrIndex(var80, var67);
                    int var133 = arg9.computeColorOrIndex(var74, var67);
                    arg0.applyStep(var88, var102, var105, var108, var112, var118, var124, var133);
                    var52 = (var52 + 1);
                }
                return;
                var47 = (var47 + 1);
            }
        }
    }

    private static double[] computeVector(double arg0, double arg1, double arg2, int arg3, double arg4, double arg5, double arg6, double arg7) {
        double var37 = (1.0 - arg7);
        double var28 = (arg1 * var37);
        double var71 = ((((double) arg3) * arg6) * 6.283185307179586);
        double var91 = ((arg4 * 6.283185307179586) * arg7);
        double var75 = Math.cos((var71 - var91));
        double var55 = (arg5 * var75);
        double var41 = (1.0 + var55);
        double var30 = (var28 * var41);
        double var47 = Math.cos((arg6 * 6.283185307179586));
        double var34 = (var30 * var47);
        double var61 = (arg2 * arg7);
        double var51 = (arg0 + var61);
        double var83 = Math.sin((arg6 * 6.283185307179586));
        double var65 = (var30 * var83);
        return computeVector(var34, var51, var65);
    }

    public static void drawGeometryPart(CosmeticDrawBuilder arg0, ParametricSurfaceSpec arg1, double[] arg2, double[] arg3, double arg4, double arg5, ISurfaceColorShader arg6) {
        double[] var20 = computeVector(arg3);
        drawGeometryPart(arg0, arg1, arg2, var20, arg4, arg5, 0, 1, arg6);
    }

    private static void drawGeometryPart(CosmeticDrawBuilder arg0, ParametricSurfaceSpec arg1, double[] arg2, double[] arg3, double arg4, double arg5, int arg6, int arg7, ISurfaceColorShader arg8) {
        double[] var30 = null;
        int var31 = 0;
        int var35 = 0;
        int var160 = 0;
        int var162 = 0;
        double var163 = 0.0;
        int var505 = 0;
        if (arg6 == 0) {
            var162 = arg1.counterValue2;
        } else {
            var162 = arg1.counterValue3;
        }
        int var25 = var162;
        double var205 = ((double) var25);
        double var57 = (arg4 / var205);
        int var60 = (var25 + 1);
        double[][] var61 = new double[var60][];
        int var63 = (var25 + 1);
        double[][] var64 = new double[var63][];
        var61[0] = arg2;
        var64[0] = arg3;
        var30 = arg3;
        var31 = 1;
        while (true) {
            int var69 = var31;
            if (var69 > var25) break;
            double[] var71 = new double[3];
            double var300 = var30[0];
            double[] var377 = arg1.coordsVector;
            double var378 = var377[0];
            double var442 = arg1.doubleValue6;
            double var380 = (var378 * var442);
            double var382 = (var380 * var57);
            double var302 = (var300 + var382);
            var71[0] = var302;
            double[] var304 = var30;
            double var305 = var304[1];
            double[] var385 = arg1.coordsVector;
            double var386 = var385[1];
            double var447 = arg1.doubleValue6;
            double var388 = (var386 * var447);
            double var390 = (var388 * var57);
            double var307 = (var305 + var390);
            var71[1] = var307;
            double[] var309 = var30;
            double var310 = var309[2];
            double[] var393 = arg1.coordsVector;
            double var394 = var393[2];
            double var452 = arg1.doubleValue6;
            double var396 = (var394 * var452);
            double var398 = (var396 * var57);
            double var312 = (var310 + var398);
            var71[2] = var312;
            double[] var72 = computeVector(var71);
            var30 = var72;
            int var169 = var31;
            double[] var212 = var30;
            var64[var169] = var212;
            int var170 = var31;
            int var314 = var31;
            int var315 = (var314 - 1);
            double[] var214 = var61[var315];
            double[] var316 = var30;
            double[] var421 = var30;
            double[] var215 = computeVector(var214, var316, var57, var421, 0.0);
            var61[var170] = var215;
            var31 = (var31 + 1);
        }
        double var217 = arg1.doubleValue5;
        double var77 = (arg5 * var217);
        int var172 = arg1.counterValue4;
        int var173 = (var172 - arg6);
        int var80 = Math.max(3, var173);
        DoubleUnaryOperator var222 = (p0) -> GeometryPrimitives.lambda$b(arg5, var77, p0);
        IGradientColorSampler var336 = (p0, p1) -> GeometryPrimitives.lambda$a(arg8, arg6, p0, p1);
        drawGeometryPart(arg0, var61, var222, var80, var336, false, true);
        int var176 = arg1.counterValue;
        if (arg6 < var176) {
            var160 = 1;
        } else {
            var160 = 0;
        }
        int var33 = var160;
        double[] var84 = var64[var25];
        if (var33 != 0) {
            var35 = 0;
            while (true) {
                int var87 = var35;
                int var179 = arg1.counterValue1;
                if (var87 >= var179) break;
                int var89 = arg1.counterValue1;
                if (var89 == 1) {
                    double var91 = arg1.doubleValue;
                    double var224 = arg1.doubleValue1;
                    double var93 = (var91 + var224);
                    var163 = (var93 * 0.5);
                } else {
                    double var140 = arg1.doubleValue;
                    double var282 = arg1.doubleValue1;
                    double var358 = arg1.doubleValue;
                    double var284 = (var282 - var358);
                    int var360 = var35;
                    double var361 = ((double) var360);
                    double var286 = (var284 * var361);
                    int var364 = arg1.counterValue1;
                    double var365 = ((double) var364);
                    double var367 = (var365 - 1.0);
                    double var288 = (var286 / var367);
                    var163 = (var140 + var288);
                }
                double var36 = var163;
                double var229 = ((double) var25);
                double var97 = (var36 * var229);
                int var101 = ((int) var97);
                int var182 = (var25 - 1);
                int var102 = Math.min(var101, var182);
                double var233 = ((double) var102);
                double var105 = (var97 - var233);
                double[] var108 = var61[var102];
                int var236 = (var102 + 1);
                double[] var185 = var61[var236];
                double[] var109 = computeVector(var108, var185, var105);
                double[] var111 = var64[var102];
                int var240 = (var102 + 1);
                double[] var188 = var64[var240];
                double[] var112 = computeVector(var111, var188, var105);
                double[] var113 = computeVector(var112);
                double[] var190 = arg1.coordsVector1;
                double[][] var115 = computeMatrix(var113, var190);
                double var117 = arg1.doubleValue8;
                int var244 = arg1.counterValue5;
                int var245 = (var244 * 977);
                int var322 = (arg7 * 31);
                int var246 = (var245 + var322);
                int var323 = var35;
                int var324 = (var323 * 7);
                int var247 = (var246 + var324);
                double var248 = computeDoubleMetric(var247);
                double var250 = (var248 - 0.5);
                double var119 = (var117 * var250);
                double[] var122 = arg1.coordsVector2;
                int var191 = var35;
                int var192 = (var191 + arg7);
                double[] var254 = arg1.coordsVector2;
                int var255 = var254.length;
                int var193 = (var192 % var255);
                double var123 = var122[var193];
                double var258 = (var119 * 2.0);
                double var125 = (var123 + var258);
                double[] var128 = new double[3];
                double var263 = Math.cos(arg1.doubleValue2);
                double[] var344 = var115[0];
                double var404 = arg1.doubleValue2;
                double var406 = Math.sin(var404);
                double var460 = Math.cos(var125);
                double var408 = (var406 * var460);
                double[] var129 = computeVector(var128, var113, var263, var344, var408);
                double[] var196 = var115[1];
                double var269 = Math.sin(arg1.doubleValue2);
                double var347 = Math.sin(var125);
                double var271 = (var269 * var347);
                double[] var350 = var115[1];
                double[] var130 = computeVector(var129, var196, var271, var350, 0.0);
                double[] var131 = computeVector(var130);
                double var274 = arg1.doubleValue3;
                double var134 = (arg4 * var274);
                double var353 = (var119 * 1.2);
                double var278 = (1.0 + var353);
                double var136 = (var134 * var278);
                double var467 = (var77 - arg5);
                double var469 = (var467 * var36);
                double var427 = (arg5 + var469);
                double var472 = arg1.doubleValue4;
                double var429 = (var427 * var472);
                int var475 = (arg6 + 1);
                int var481 = (arg7 * 8);
                int var492 = var35;
                int var482 = (var481 + var492);
                int var483 = (var482 + 1);
                drawGeometryPart(arg0, arg1, var109, var131, var136, var429, var475, var483, arg8);
                var35 = (var35 + 1);
            }
            boolean var143 = arg1.enabledState;
            if (var143) {
                double[] var199 = arg1.coordsVector1;
                double[][] var145 = computeMatrix(var84, var199);
                var505 = -1;
                while (true) {
                    int var147 = var505;
                    if (var147 > 1) break;
                    double[] var149 = new double[3];
                    double var293 = Math.cos(arg1.doubleValue7);
                    double[] var370 = var145[1];
                    int var414 = var505;
                    double var415 = ((double) var414);
                    double var463 = arg1.doubleValue7;
                    double var465 = Math.sin(var463);
                    double var417 = (var415 * var465);
                    double[] var150 = computeVector(var149, var84, var293, var370, var417);
                    double[] var151 = computeVector(var150);
                    double[] var296 = var61[var25];
                    double var434 = arg1.doubleValue3;
                    double var373 = (arg4 * var434);
                    double var375 = (var373 * 0.9);
                    double var440 = (var77 * 0.95);
                    int var479 = (arg6 + 1);
                    int var486 = (arg7 * 8);
                    int var487 = (var486 + 6);
                    int var497 = var505;
                    int var498 = (var497 + 1);
                    int var499 = (var498 / 2);
                    int var488 = (var487 + var499);
                    drawGeometryPart(arg0, arg1, var296, var151, var375, var440, var479, var488, arg8);
                    var505 = (var505 + 2);
                }
                if (var33 != 0) {
                    boolean var155 = arg1.enabledState;
                    if (!var155) {
                    }
                }
                BiConsumer var157 = arg1.vertexConsumer;
                if (var157 != null) {
                    BiConsumer var159 = arg1.vertexConsumer;
                    double[] var204 = var61[var25];
                    Integer var299 = arg6;
                    var159.accept(var204, var299);
                }
                return;
            }
        }
    }

    private static double[][] computeMatrix(double[] arg0, double[] arg1) {
        double var27 = 0.0;
        double var14 = computeDoubleMetric(arg1, arg0);
        double[] var17 = new double[3];
        double var36 = arg1[0];
        double var58 = (arg0[0] * var14);
        double var38 = (var36 - var58);
        var17[0] = var38;
        double var41 = arg1[1];
        double var63 = (arg0[1] * var14);
        double var43 = (var41 - var63);
        var17[1] = var43;
        double var46 = arg1[2];
        double var68 = (arg0[2] * var14);
        double var48 = (var46 - var68);
        var17[2] = var48;
        if (Double.compare(computeDoubleMetric(var17, var17), 1.0E-10) < 0) {
            var27 = computeMatrix(arg0)[0];
        } else {
            var27 = computeVector(var17);
        }
        double var76 = var27;
        double[][] var25 = new double[2][];
        var25[0] = var76;
        double[] var52 = computeVector1(arg0, var76);
        var25[1] = var52;
        return var25;
    }

    private static double computeDoubleMetric1(double arg0) {
        double var16 = Math.cos((arg0 - 0.8));
        double var12 = (0.22 * var16);
        return (0.78 + var12);
    }

}
