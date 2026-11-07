package need.clarity.modules.visual.kenny;

public final class Matrix4x4Math {
    private static final double[] coordsVector;

    private Matrix4x4Math() {
    }

    public static double[] computeVector() {
        double[] var4 = new double[12];
        var4[0] = 1.0;
        var4[1] = 0.0;
        var4[2] = 0.0;
        var4[3] = 0.0;
        var4[4] = 0.0;
        var4[5] = 1.0;
        var4[6] = 0.0;
        var4[7] = 0.0;
        var4[8] = 0.0;
        var4[9] = 0.0;
        var4[10] = 1.0;
        var4[11] = 0.0;
        return var4;
    }

    public static void applyStep(double[] arg0) {
        arg0[0] = 1.0;
        arg0[1] = 0.0;
        arg0[2] = 0.0;
        arg0[3] = 0.0;
        arg0[4] = 0.0;
        arg0[5] = 1.0;
        arg0[6] = 0.0;
        arg0[7] = 0.0;
        arg0[8] = 0.0;
        arg0[9] = 0.0;
        arg0[10] = 1.0;
        arg0[11] = 0.0;
    }

    public static void applyStep(double[] arg0, double[] arg1) {
        System.arraycopy(arg0, 0, arg1, 0, 12);
    }

    public static void applyStep(double[] arg0, double[] arg1, double[] arg2) {
        int var12 = 0;
        double[] var3 = coordsVector;
        var12 = 0;
        while (true) {
            if (var12 >= 3) break;
            int var24 = (var12 * 4);
            double var26 = arg0[var24];
            int var42 = (var24 + 1);
            double var29 = arg0[var42];
            int var44 = (var24 + 2);
            double var32 = arg0[var44];
            double var92 = arg1[0];
            double var56 = (var26 * var92);
            double var143 = arg1[4];
            double var96 = (var29 * var143);
            double var58 = (var56 + var96);
            double var146 = arg1[8];
            double var100 = (var32 * var146);
            double var60 = (var58 + var100);
            var3[var24] = var60;
            int var47 = (var24 + 1);
            double var103 = arg1[1];
            double var65 = (var26 * var103);
            double var149 = arg1[5];
            double var107 = (var29 * var149);
            double var67 = (var65 + var107);
            double var152 = arg1[9];
            double var111 = (var32 * var152);
            double var69 = (var67 + var111);
            var3[var47] = var69;
            int var49 = (var24 + 2);
            double var114 = arg1[2];
            double var74 = (var26 * var114);
            double var155 = arg1[6];
            double var118 = (var29 * var155);
            double var76 = (var74 + var118);
            double var158 = arg1[10];
            double var122 = (var32 * var158);
            double var78 = (var76 + var122);
            var3[var49] = var78;
            int var51 = (var24 + 3);
            double var125 = arg1[3];
            double var83 = (var26 * var125);
            double var161 = arg1[7];
            double var129 = (var29 * var161);
            double var85 = (var83 + var129);
            double var164 = arg1[11];
            double var133 = (var32 * var164);
            double var87 = (var85 + var133);
            int var142 = (var24 + 3);
            double var136 = arg0[var142];
            double var89 = (var87 + var136);
            var3[var51] = var89;
            var12 = (var12 + 1);
        }
        System.arraycopy(var3, 0, arg2, 0, 12);
    }

    public static void applyStep(double[] arg0, double arg1, double arg2, double arg3) {
        double var20 = arg0[3];
        double var34 = (arg0[0] * arg1);
        double var63 = (arg0[1] * arg2);
        double var36 = (var34 + var63);
        double var68 = (arg0[2] * arg3);
        double var38 = (var36 + var68);
        double var22 = (var20 + var38);
        arg0[3] = var22;
        double var24 = arg0[7];
        double var43 = (arg0[4] * arg1);
        double var75 = (arg0[5] * arg2);
        double var45 = (var43 + var75);
        double var80 = (arg0[6] * arg3);
        double var47 = (var45 + var80);
        double var26 = (var24 + var47);
        arg0[7] = var26;
        double var28 = arg0[11];
        double var52 = (arg0[8] * arg1);
        double var87 = (arg0[9] * arg2);
        double var54 = (var52 + var87);
        double var92 = (arg0[10] * arg3);
        double var56 = (var54 + var92);
        double var30 = (var28 + var56);
        arg0[11] = var30;
    }

    public static void applyStep(double[] arg0, double arg1) {
        double var25 = (arg0[0] * arg1);
        arg0[0] = var25;
        double var29 = (arg0[1] * arg1);
        arg0[1] = var29;
        double var33 = (arg0[2] * arg1);
        arg0[2] = var33;
        double var37 = (arg0[4] * arg1);
        arg0[4] = var37;
        double var41 = (arg0[5] * arg1);
        arg0[5] = var41;
        double var45 = (arg0[6] * arg1);
        arg0[6] = var45;
        double var49 = (arg0[8] * arg1);
        arg0[8] = var49;
        double var53 = (arg0[9] * arg1);
        arg0[9] = var53;
        double var57 = (arg0[10] * arg1);
        arg0[10] = var57;
    }

    public static void applyStep1(double[] arg0, double arg1) {
        int var16 = 0;
        if (arg1 == 0.0) {
            return;
        }
        double var24 = Math.cos(arg1);
        double var28 = Math.sin(arg1);
        var16 = 0;
        while (true) {
            if (var16 >= 12) break;
            int var41 = (var16 + 1);
            double var33 = arg0[var41];
            int var43 = (var16 + 2);
            double var36 = arg0[var43];
            int var45 = (var16 + 1);
            double var53 = (var33 * var24);
            double var68 = (var36 * var28);
            double var55 = (var53 + var68);
            arg0[var45] = var55;
            int var47 = (var16 + 2);
            double var62 = (-(var33) * var28);
            double var74 = (var36 * var24);
            double var64 = (var62 + var74);
            arg0[var47] = var64;
            var16 = (var16 + 4);
        }
    }

    public static void applyStep2(double[] arg0, double arg1) {
        int var16 = 0;
        if (arg1 == 0.0) {
            return;
        }
        double var24 = Math.cos(arg1);
        double var28 = Math.sin(arg1);
        var16 = 0;
        while (true) {
            if (var16 >= 12) break;
            int var40 = var16;
            double var33 = arg0[var40];
            int var42 = (var16 + 2);
            double var36 = arg0[var42];
            int var43 = var16;
            double var49 = (var33 * var24);
            double var62 = (var36 * var28);
            double var51 = (var49 - var62);
            arg0[var43] = var51;
            int var45 = (var16 + 2);
            double var56 = (var33 * var28);
            double var68 = (var36 * var24);
            double var58 = (var56 + var68);
            arg0[var45] = var58;
            var16 = (var16 + 4);
        }
    }

    public static void applyStep3(double[] arg0, double arg1) {
        int var16 = 0;
        if (arg1 == 0.0) {
            return;
        }
        double var24 = Math.cos(arg1);
        double var28 = Math.sin(arg1);
        var16 = 0;
        while (true) {
            if (var16 >= 12) break;
            int var40 = var16;
            double var33 = arg0[var40];
            int var42 = (var16 + 1);
            double var36 = arg0[var42];
            int var43 = var16;
            double var49 = (var33 * var24);
            double var64 = (var36 * var28);
            double var51 = (var49 + var64);
            arg0[var43] = var51;
            int var45 = (var16 + 1);
            double var58 = (-(var33) * var28);
            double var70 = (var36 * var24);
            double var60 = (var58 + var70);
            arg0[var45] = var60;
            var16 = (var16 + 4);
        }
    }

    public static void applyStep4(double[] arg0, double arg1, double arg2, double arg3) {
        applyStep(arg0);
        arg0[3] = arg1;
        arg0[7] = arg2;
        arg0[11] = arg3;
    }

    public static double computeDoubleMetric(double[] arg0, double arg1, double arg2, double arg3) {
        double var16 = (arg0[0] * arg1);
        double var27 = (arg0[1] * arg2);
        double var18 = (var16 + var27);
        double var32 = (arg0[2] * arg3);
        double var20 = (var18 + var32);
        double var35 = arg0[3];
        return (var20 + var35);
    }

    public static double computeDoubleMetric1(double[] arg0, double arg1, double arg2, double arg3) {
        double var16 = (arg0[4] * arg1);
        double var27 = (arg0[5] * arg2);
        double var18 = (var16 + var27);
        double var32 = (arg0[6] * arg3);
        double var20 = (var18 + var32);
        double var35 = arg0[7];
        return (var20 + var35);
    }

    public static double computeDoubleMetric2(double[] arg0, double arg1, double arg2, double arg3) {
        double var16 = (arg0[8] * arg1);
        double var27 = (arg0[9] * arg2);
        double var18 = (var16 + var27);
        double var32 = (arg0[10] * arg3);
        double var20 = (var18 + var32);
        double var35 = arg0[11];
        return (var20 + var35);
    }

    static {
        coordsVector = new double[12];
    }

}
