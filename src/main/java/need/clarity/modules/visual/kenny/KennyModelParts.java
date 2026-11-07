package need.clarity.modules.visual.kenny;


public final class KennyModelParts {
    private static final int CONST_INT = -423139;
    private static final int CONST_INT1 = -2594292;
    private static final int CONST_INT2 = -9551068;
    private static final int CONST_INT3 = -405080;
    private static final int CONST_INT4 = -1;
    private static final int CONST_INT5 = -16119286;
    private static final double CONST_DBL = 0.024;
    private static final double CONST_DBL1 = 0.27;
    private static final double CONST_DBL2 = 0.1;
    private static final double CONST_DBL3 = 0.27;
    private static final double CONST_DBL4 = 0.3;
    private static final double CONST_DBL5 = 0.4;
    private static final double CONST_DBL6 = 0.28;
    private static final double CONST_DBL7 = 0.4;
    private static final double CONST_DBL8 = 0.3;
    private static final double CONST_DBL9 = 0.28;
    public static final int CONST_INT6 = 0;
    public static final int CONST_INT7 = 1;
    public static final int CONST_INT8 = 2;
    public static final int CONST_INT9 = 3;
    public static final int CONST_INT10 = 4;
    public static final int CONST_INT11 = 5;
    public static final int CONST_INT12 = 6;
    private static final int CONST_INT13 = 0;
    private static final int CONST_INT14 = 1;
    private static final int CONST_INT15 = 2;
    private static final int CONST_INT16 = 3;
    private static final int CONST_INT17 = 4;
    private static final int CONST_INT18 = 5;
    static final double CONST_DBL10 = 0.3;
    static final double CONST_DBL11 = 0.28;
    private static CompiledMeshPart[] fieldVal;
    static final double[][] vertexMatrix;

    private KennyModelParts() {
    }

    public static CompiledMeshPart[] evaluate() {
        int var31 = 0;
        int var34 = 0;
        int var246 = 0;
        CompiledMeshPart[] var0 = fieldVal;
        if (var0 != null) {
            return var0;
        }
        KennyMeshBuilder var38 = new KennyMeshBuilder();
        var38.applyStep(-16119286);
        CompiledMeshPart[] var41 = new CompiledMeshPart[5];
        var38.applyStep();
        Runnable var168 = () -> KennyModelParts.lambda$a(var38, 0.19, 0.24, 0.21);
        var38.applyStep(0.024, var168);
        double[][] var53 = new double[(8 + 1)][];
        var31 = 0;
        while (true) {
            if (var31 > 8) break;
            double var58 = (0.19 - 0.2);
            double var196 = ((double) var31);
            double var144 = (0.36 * var196);
            double var199 = ((double) 8);
            double var146 = (var144 / var199);
            double var60 = (var58 + var146);
            double var66 = ((var60 - 0.19) / 0.24);
            int var108 = var31;
            double[] var153 = new double[3];
            var153[0] = 0.0;
            var153[1] = var60;
            double var240 = (var66 * var66);
            double var237 = (1.0 - var240);
            double var231 = Math.sqrt(Math.max(0.0, var237));
            double var217 = ((0.21 * var231) + 0.006);
            var153[2] = var217;
            var53[var108] = var153;
            var31 = (var31 + 1);
        }
        var38.applyStep(var53, 0.014, -16119286);
        CompiledMeshPart var157 = var38.evaluate();
        var41[0] = var157;
        var38.applyStep();
        var38.applyStep(0.0, 0.28, 0.0);
        Runnable var172 = () -> KennyModelParts.lambda$e(var38);
        var38.applyStep(0.024, var172);
        CompiledMeshPart var159 = var38.evaluate();
        var41[1] = var159;
        var38.applyStep();
        var38.applyStep(0.0, 0.28, 0.0);
        Runnable var176 = () -> KennyModelParts.lambda$b(var38, -0.01);
        var38.applyStep(0.016, var176);
        Runnable var178 = () -> KennyModelParts.lambda$a(var38, -0.01);
        var38.applyStep(0.014, var178);
        var34 = -1;
        while (true) {
            if (var34 > 1) break;
            double var86 = (((double) var34) * 0.088);
            Runnable var180 = () -> KennyModelParts.lambda$a(var38, var86, -0.01);
            var38.applyStep(0.01, var180);
            var34 = (var34 + 2);
        }
        var246 = -1;
        while (true) {
            if (var246 > 1) break;
            double var129 = (((double) var246) * 0.068);
            double var185 = (-0.01 + 0.005);
            var38.applyStep(var129, var185, 0.376, 0.02, 8, 5, -16119286);
            var246 = (var246 + 2);
        }
        CompiledMeshPart var163 = var38.evaluate();
        var41[2] = var163;
        var38.applyStep();
        Runnable var188 = () -> KennyModelParts.lambda$d(var38);
        var38.applyStep(0.024, var188);
        Runnable var190 = () -> KennyModelParts.lambda$c(var38);
        var38.applyStep(0.024, var190);
        CompiledMeshPart var165 = var38.evaluate();
        var41[3] = var165;
        var38.applyStep();
        Runnable var192 = () -> KennyModelParts.lambda$b(var38);
        var38.applyStep(0.024, var192);
        Runnable var194 = () -> KennyModelParts.lambda$a(var38);
        var38.applyStep(0.024, var194);
        CompiledMeshPart var167 = var38.evaluate();
        var41[4] = var167;
        fieldVal = var41;
        return var41;
    }

    public static void applyStep(double arg0, double arg1, double arg2, boolean arg3, double arg4, double arg5, double[][] arg6) {
        double var71 = 0.0;
        double var43 = ((Math.sin(arg0) * arg1) * 0.7);
        if (arg3) {
            var71 = 0.4;
        } else {
            var71 = 0.0;
        }
        double var33 = var71;
        double var50 = Math.sin((arg2 * 3.141592653589793));
        double[] var53 = arg6[0];
        Matrix4x4Math.applyStep4(var53, 0.0, 0.27, 0.0);
        Matrix4x4Math.applyStep1(var53, var33);
        double[] var57 = arg6[1];
        double var140 = Math.cos(var33);
        double var124 = (0.4 * var140);
        double var114 = (0.27 + var124);
        double var144 = Math.sin(var33);
        double var128 = (0.4 * var144);
        Matrix4x4Math.applyStep4(var57, 0.0, var114, var128);
        Matrix4x4Math.applyStep2(var57, arg4);
        Matrix4x4Math.applyStep1(var57, arg5);
        double[] var85 = arg6[2];
        double var134 = -(var43);
        double var146 = (var50 * 1.6);
        double var136 = (var134 - var146);
        applyStep(var53, var85, 0.27, 0.28, var136);
        double[] var87 = arg6[3];
        applyStep(var53, var87, -0.27, -0.28, var43);
        Matrix4x4Math.applyStep4(arg6[4], 0.1, 0.27, 0.0);
        Matrix4x4Math.applyStep1(arg6[4], var43);
        Matrix4x4Math.applyStep4(arg6[5], -0.1, 0.27, 0.0);
        double[] var70 = arg6[5];
        double var100 = -(var43);
        Matrix4x4Math.applyStep1(var70, var100);
    }

    private static void applyStep(double[] arg0, double[] arg1, double arg2, double arg3, double arg4) {
        Matrix4x4Math.applyStep(arg0, arg1);
        Matrix4x4Math.applyStep(arg1, arg2, 0.3, 0.0);
        Matrix4x4Math.applyStep3(arg1, arg3);
        Matrix4x4Math.applyStep1(arg1, arg4);
    }

    public static void applyStep(double[] arg0, double[] arg1) {
        double var33 = Matrix4x4Math.computeDoubleMetric(arg0, 0.0, -0.3, 0.0);
        arg1[0] = var33;
        double var36 = Matrix4x4Math.computeDoubleMetric1(arg0, 0.0, -0.3, 0.0);
        arg1[1] = var36;
        double var39 = Matrix4x4Math.computeDoubleMetric2(arg0, 0.0, -0.3, 0.0);
        arg1[2] = var39;
        double var42 = arg0[0];
        arg1[3] = var42;
        double var45 = arg0[4];
        arg1[4] = var45;
        double var48 = arg0[8];
        arg1[5] = var48;
        double var51 = arg0[1];
        arg1[6] = var51;
        double var54 = arg0[5];
        arg1[7] = var54;
        double var57 = arg0[9];
        arg1[8] = var57;
        double var60 = arg0[2];
        arg1[9] = var60;
        double var63 = arg0[6];
        arg1[10] = var63;
        double var66 = arg0[10];
        arg1[11] = var66;
    }

    static CompiledMeshPart evaluate(int arg0) {
        CompiledMeshPart var9 = null;
        CompiledMeshPart[] var1 = evaluate();
        switch (arg0) {
            case 0 -> {}
            case 1 -> {}
            case 2 -> {}
            case 3 -> {}
            default -> {}
        }
        var9 = var1[0];
        var9 = var1[1];
        var9 = var1[3];
        var9 = var1[4];
        return var9;
    }

    static CompiledMeshPart evaluate() {
        return evaluate()[2];
    }

    static {
        double[][] var6 = new double[6][];
        double[] var12 = new double[3];
        var12[0] = 0.0;
        var12[1] = 0.47000000000000003;
        var12[2] = 0.0;
        var6[0] = var12;
        var6[1] = null;
        double[] var15 = new double[3];
        var15[0] = 0.27;
        var15[1] = 0.47000000000000003;
        var15[2] = 0.0;
        var6[2] = var15;
        double[] var17 = new double[3];
        var17[0] = -0.27;
        var17[1] = 0.47000000000000003;
        var17[2] = 0.0;
        var6[3] = var17;
        double[] var19 = new double[3];
        var19[0] = 0.1;
        var19[1] = 0.13;
        var19[2] = 0.0;
        var6[4] = var19;
        double[] var21 = new double[3];
        var21[0] = -0.1;
        var21[1] = 0.13;
        var21[2] = 0.0;
        var6[5] = var21;
        vertexMatrix = var6;
    }

}
