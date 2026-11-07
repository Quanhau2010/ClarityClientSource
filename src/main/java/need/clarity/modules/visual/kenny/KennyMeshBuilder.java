package need.clarity.modules.visual.kenny;

import java.util.Arrays;

public final class KennyMeshBuilder {
    private static final double CONST_DBL = 6.283185307179586;
    private double doubleValue;
    private int counterValue;
    private final double[] coordsVector;
    private float[] floatBuffer;
    private int[] intBuffer;
    private int counterValue1;
    private final double[] coordsVector1;
    private final double[] coordsVector2;
    private final int[] intBuffer1;

    KennyMeshBuilder() {
        this.doubleValue = 0.0;
        this.counterValue = -16119286;
        double[] var13 = Matrix4x4Math.computeVector();
        this.coordsVector = var13;
        float[] var15 = new float[12288];
        this.floatBuffer = var15;
        int[] var17 = new int[4096];
        this.intBuffer = var17;
        double[] var19 = new double[12];
        this.coordsVector1 = var19;
        double[] var21 = new double[12];
        this.coordsVector2 = var21;
        int[] var23 = new int[4];
        this.intBuffer1 = var23;
    }

    void applyStep() {
        this.counterValue1 = 0;
        this.doubleValue = 0.0;
        Matrix4x4Math.applyStep(this.coordsVector);
    }

    CompiledMeshPart evaluate() {
        CompiledMeshPart var10 = null;
        if (this.counterValue1 == 0) {
            var10 = CompiledMeshPart.INSTANCE;
        } else {
            float[] var11 = this.floatBuffer;
            int var14 = (this.counterValue1 * 3);
            float[] var12 = Arrays.copyOf(var11, var14);
            int[] var16 = this.intBuffer;
            int var19 = this.counterValue1;
            int[] var17 = Arrays.copyOf(var16, var19);
            var10 = new CompiledMeshPart(var12, var17);
        }
        CompiledMeshPart var6 = var10;
        this.counterValue1 = 0;
        return var6;
    }

    void applyStep(int arg0) {
        this.counterValue = arg0;
    }

    void applyStep(double arg0, double arg1, double arg2) {
        Matrix4x4Math.applyStep(this.coordsVector, arg0, arg1, arg2);
    }

    void applyStep(double arg0, Runnable arg1) {
        this.doubleValue = arg0;
        try {
            arg1.run();
        } catch (Throwable ignored) {
        }
        this.doubleValue = 0.0;
        arg1.run();
        return;
        this.doubleValue = 0.0;
        throw var9;
    }

    private void applyStep(int arg0, double arg1, double arg2, double arg3, double arg4, double arg5, double arg6, int arg7) {
        int var20 = (arg0 * 3);
        this.coordsVector1[var20] = arg1;
        double[] var24 = this.coordsVector1;
        int var37 = (var20 + 1);
        var24[var37] = arg2;
        double[] var26 = this.coordsVector1;
        int var39 = (var20 + 2);
        var26[var39] = arg3;
        this.coordsVector2[var20] = arg4;
        double[] var30 = this.coordsVector2;
        int var42 = (var20 + 1);
        var30[var42] = arg5;
        double[] var32 = this.coordsVector2;
        int var44 = (var20 + 2);
        var32[var44] = arg6;
        this.intBuffer1[arg0] = arg7;
    }

    private void applyStep1() {
        int var134 = 0;
        int var148 = 0;
        double var34 = this.coordsVector1[6];
        double var167 = this.coordsVector1[0];
        double var36 = (var34 - var167);
        double var40 = this.coordsVector1[7];
        double var171 = this.coordsVector1[1];
        double var42 = (var40 - var171);
        double var46 = this.coordsVector1[8];
        double var175 = this.coordsVector1[2];
        double var48 = (var46 - var175);
        double var52 = this.coordsVector1[9];
        double var179 = this.coordsVector1[3];
        double var54 = (var52 - var179);
        double var58 = this.coordsVector1[10];
        double var183 = this.coordsVector1[4];
        double var60 = (var58 - var183);
        double var64 = this.coordsVector1[11];
        double var187 = this.coordsVector1[5];
        double var66 = (var64 - var187);
        double var70 = (var42 * var66);
        double var193 = (var48 * var60);
        double var72 = (var70 - var193);
        double var76 = (var48 * var54);
        double var199 = (var36 * var66);
        double var78 = (var76 - var199);
        double var82 = (var36 * var60);
        double var205 = (var42 * var54);
        double var84 = (var82 - var205);
        double var88 = (var72 * var72);
        double var211 = (var78 * var78);
        double var90 = (var88 + var211);
        double var215 = (var84 * var84);
        if (Double.compare((var90 + var215), 1.0E-22) < 0) {
            return;
        }
        double var97 = this.coordsVector2[0];
        double var221 = this.coordsVector2[3];
        double var99 = (var97 + var221);
        double var225 = this.coordsVector2[6];
        double var101 = (var99 + var225);
        double var229 = this.coordsVector2[9];
        double var103 = (var101 + var229);
        double var107 = this.coordsVector2[1];
        double var233 = this.coordsVector2[4];
        double var109 = (var107 + var233);
        double var237 = this.coordsVector2[7];
        double var111 = (var109 + var237);
        double var241 = this.coordsVector2[10];
        double var113 = (var111 + var241);
        double var117 = this.coordsVector2[2];
        double var245 = this.coordsVector2[5];
        double var119 = (var117 + var245);
        double var249 = this.coordsVector2[8];
        double var121 = (var119 + var249);
        double var253 = this.coordsVector2[11];
        double var123 = (var121 + var253);
        double var127 = (var72 * var103);
        double var259 = (var78 * var113);
        double var129 = (var127 + var259);
        double var263 = (var84 * var123);
        if (Double.compare((var129 + var263), 0.0) >= 0) {
            var134 = 1;
        } else {
            var134 = 0;
        }
        int var31 = var134;
        if (Double.compare(this.doubleValue, 0.0) > 0) {
            var148 = 1;
        } else {
            var148 = 0;
        }
        int var32 = var148;
        if (var31 == var32) {
            this.applyStep2(0);
            this.applyStep2(1);
            this.applyStep2(2);
            this.applyStep2(3);
        } else {
            this.applyStep2(0);
            this.applyStep2(3);
            this.applyStep2(2);
            this.applyStep2(1);
        }
    }

    private void applyStep2(int arg0) {
        int var107 = 0;
        int var19 = this.counterValue1;
        int var46 = this.intBuffer.length;
        if (var19 == var46) {
            float[] var48 = this.floatBuffer;
            int var75 = (this.counterValue1 * 6);
            float[] var49 = Arrays.copyOf(var48, var75);
            this.floatBuffer = var49;
            int[] var51 = this.intBuffer;
            int var78 = (this.counterValue1 * 2);
            int[] var52 = Arrays.copyOf(var51, var78);
            this.intBuffer = var52;
        }
        int var23 = (arg0 * 3);
        double var26 = this.coordsVector1[var23];
        double[] var29 = this.coordsVector1;
        int var56 = (var23 + 1);
        double var30 = var29[var56];
        double[] var33 = this.coordsVector1;
        int var58 = (var23 + 2);
        double var34 = var33[var58];
        float[] var37 = this.floatBuffer;
        int var61 = (this.counterValue1 * 3);
        float var86 = ((float) Matrix4x4Math.computeDoubleMetric(this.coordsVector, var26, var30, var34));
        var37[var61] = var86;
        float[] var39 = this.floatBuffer;
        int var65 = ((this.counterValue1 * 3) + 1);
        float var93 = ((float) Matrix4x4Math.computeDoubleMetric1(this.coordsVector, var26, var30, var34));
        var39[var65] = var93;
        float[] var41 = this.floatBuffer;
        int var69 = ((this.counterValue1 * 3) + 2);
        float var100 = ((float) Matrix4x4Math.computeDoubleMetric2(this.coordsVector, var26, var30, var34));
        var41[var69] = var100;
        int[] var43 = this.intBuffer;
        int var71 = this.counterValue1;
        if (Double.compare(this.doubleValue, 0.0) > 0) {
            var107 = this.counterValue;
        } else {
            var107 = this.intBuffer1[arg0];
        }
        var43[var71] = var107;
        int var73 = (this.counterValue1 + 1);
        this.counterValue1 = var73;
    }

    void applyStep(double arg0, double arg1, double arg2, double arg3, int arg4, int arg5, int arg6) {
        this.applyStep(arg0, arg1, arg2, arg3, arg3, arg3, arg4, arg5, arg6);
    }

    void applyStep(double arg0, double arg1, double arg2, double arg3, double arg4, double arg5, int arg6, int arg7, int arg8) {
        int var41 = 0;
        int var54 = 0;
        double var67 = this.doubleValue;
        double var71 = (arg3 + var67);
        double var75 = (arg4 + var67);
        double var79 = (arg5 + var67);
        var41 = 0;
        while (true) {
            if (var41 < arg7) {
                double var185 = ((double) var41);
                double var157 = (3.141592653589793 * var185);
                double var188 = ((double) arg7);
                double var159 = (var157 / var188);
                double var85 = (-1.5707963267948966 + var159);
                double var192 = ((double) (var41 + 1));
                double var163 = (3.141592653589793 * var192);
                double var195 = ((double) arg7);
                double var165 = (var163 / var195);
                double var89 = (-1.5707963267948966 + var165);
                double var93 = Math.cos(var85);
                double var97 = Math.sin(var85);
                double var101 = Math.cos(var89);
                double var105 = Math.sin(var89);
                var54 = 0;
                while (true) {
                    if (var54 >= arg6) break;
                    double var112 = (((double) var54) * 6.283185307179586);
                    double var170 = ((double) arg6);
                    double var114 = (var112 / var170);
                    double var120 = (((double) (var54 + 1)) * 6.283185307179586);
                    double var175 = ((double) arg6);
                    double var122 = (var120 / var175);
                    double var126 = Math.cos(var114);
                    double var130 = Math.sin(var114);
                    double var134 = Math.cos(var122);
                    double var138 = Math.sin(var122);
                    this.applyStep(0, arg0, arg1, arg2, var71, var75, var79, var93, var97, var126, var130, arg8);
                    this.applyStep(1, arg0, arg1, arg2, var71, var75, var79, var93, var97, var134, var138, arg8);
                    this.applyStep(2, arg0, arg1, arg2, var71, var75, var79, var101, var105, var134, var138, arg8);
                    this.applyStep(3, arg0, arg1, arg2, var71, var75, var79, var101, var105, var126, var130, arg8);
                    this.applyStep1();
                    var54 = (var54 + 1);
                }
                return;
                var41 = (var41 + 1);
            }
        }
    }

    private void applyStep(int arg0, double arg1, double arg2, double arg3, double arg4, double arg5, double arg6, double arg7, double arg8, double arg9, double arg10, int arg11) {
        double var46 = (arg7 * arg9);
        double var52 = (arg7 * arg10);
        double var61 = (arg4 * var46);
        double var59 = (arg1 + var61);
        double var69 = (arg5 * arg8);
        double var65 = (arg2 + var69);
        double var77 = (arg6 * var52);
        double var73 = (arg3 + var77);
        double var81 = (var46 / arg4);
        double var87 = (arg8 / arg5);
        double var91 = (var52 / arg6);
        this.applyStep(arg0, var59, var65, var73, var81, var87, var91, arg11);
    }

    void applyStep(double arg0, double arg1, double arg2, int arg3, int arg4) {
        int var26 = 0;
        double var39 = this.doubleValue;
        double var43 = (arg0 - var39);
        double var47 = (arg1 + var39);
        double var51 = (arg2 + var39);
        var26 = 0;
        while (true) {
            if (var26 >= arg3) break;
            double var58 = (((double) var26) * 6.283185307179586);
            double var121 = ((double) arg3);
            double var60 = (var58 / var121);
            double var66 = (((double) (var26 + 1)) * 6.283185307179586);
            double var126 = ((double) arg3);
            double var68 = (var66 / var126);
            double var72 = Math.cos(var60);
            double var76 = Math.sin(var60);
            double var80 = Math.cos(var68);
            double var84 = Math.sin(var68);
            double var130 = (var51 * var72);
            double var214 = (var51 * var76);
            this.applyStep(0, var130, var43, var214, var72, 0.0, var76, arg4);
            double var134 = (var51 * var80);
            double var218 = (var51 * var84);
            this.applyStep(1, var134, var43, var218, var80, 0.0, var84, arg4);
            double var138 = (var51 * var80);
            double var222 = (var51 * var84);
            this.applyStep(2, var138, var47, var222, var80, 0.0, var84, arg4);
            double var142 = (var51 * var72);
            double var226 = (var51 * var76);
            this.applyStep(3, var142, var47, var226, var72, 0.0, var76, arg4);
            this.applyStep1();
            this.applyStep(0, 0.0, var43, 0.0, 0.0, -1.0, 0.0, arg4);
            double var148 = (var51 * var80);
            double var232 = (var51 * var84);
            this.applyStep(1, var148, var43, var232, 0.0, -1.0, 0.0, arg4);
            double var152 = (var51 * var72);
            double var236 = (var51 * var76);
            this.applyStep(2, var152, var43, var236, 0.0, -1.0, 0.0, arg4);
            double var156 = (var51 * var72);
            double var240 = (var51 * var76);
            this.applyStep(3, var156, var43, var240, 0.0, -1.0, 0.0, arg4);
            this.applyStep1();
            this.applyStep(0, 0.0, var47, 0.0, 0.0, 1.0, 0.0, arg4);
            double var162 = (var51 * var72);
            double var246 = (var51 * var76);
            this.applyStep(1, var162, var47, var246, 0.0, 1.0, 0.0, arg4);
            double var166 = (var51 * var80);
            double var250 = (var51 * var84);
            this.applyStep(2, var166, var47, var250, 0.0, 1.0, 0.0, arg4);
            double var170 = (var51 * var80);
            double var254 = (var51 * var84);
            this.applyStep(3, var170, var47, var254, 0.0, 1.0, 0.0, arg4);
            this.applyStep1();
            var26 = (var26 + 1);
        }
    }

    void applyStep(double[][] arg0, double arg1, int arg2) {
        int var22 = 0;
        var22 = 0;
        while (true) {
            int var26 = (var22 + 1);
            int var37 = arg0.length;
            if (var26 >= var37) break;
            int var38 = var22;
            double[] var28 = arg0[var38];
            int var40 = (var22 + 1);
            double[] var30 = arg0[var40];
            double var46 = var28[0];
            double var68 = (arg1 / 2.0);
            double var48 = (var46 - var68);
            double var71 = var28[1];
            double var98 = var28[2];
            this.applyStep(0, var48, var71, var98, 0.0, 0.0, 1.0, arg2);
            double var51 = var28[0];
            double var75 = (arg1 / 2.0);
            double var53 = (var51 + var75);
            double var78 = var28[1];
            double var103 = var28[2];
            this.applyStep(1, var53, var78, var103, 0.0, 0.0, 1.0, arg2);
            double var56 = var30[0];
            double var82 = (arg1 / 2.0);
            double var58 = (var56 + var82);
            double var85 = var30[1];
            double var108 = var30[2];
            this.applyStep(2, var58, var85, var108, 0.0, 0.0, 1.0, arg2);
            double var61 = var30[0];
            double var89 = (arg1 / 2.0);
            double var63 = (var61 - var89);
            double var92 = var30[1];
            double var113 = var30[2];
            this.applyStep(3, var63, var92, var113, 0.0, 0.0, 1.0, arg2);
            this.applyStep1();
            var22 = (var22 + 1);
        }
    }

}
