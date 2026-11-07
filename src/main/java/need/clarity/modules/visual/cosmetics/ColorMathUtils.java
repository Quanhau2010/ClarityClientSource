package need.clarity.modules.visual.cosmetics;

public final class ColorMathUtils {
    public static final int CONST_INT = -1;

    private ColorMathUtils() {
    }

    public static int computeColorOrIndex(int arg0) {
        return ((arg0 >>> 24) & 255);
    }

    public static int computeColorOrIndex1(int arg0) {
        return ((arg0 >>> 16) & 255);
    }

    public static int computeColorOrIndex2(int arg0) {
        return ((arg0 >>> 8) & 255);
    }

    public static int computeColorOrIndex3(int arg0) {
        return (arg0 & 255);
    }

    public static int computeColorOrIndex(int arg0, int arg1, int arg2, int arg3) {
        int var8 = (computeColorOrIndex4(arg0) << 24);
        int var14 = (computeColorOrIndex4(arg1) << 16);
        int var9 = (var8 | var14);
        int var17 = (computeColorOrIndex4(arg2) << 8);
        int var10 = (var9 | var17);
        int var19 = computeColorOrIndex4(arg3);
        return (var10 | var19);
    }

    public static int computeColorOrIndex4(int arg0) {
        int var4 = 0;
        if (arg0 < 0) {
            var4 = 0;
        } else {
            var4 = Math.min(arg0, 255);
        }
        return var4;
    }

    public static double computeDoubleMetric(double arg0) {
        double var7 = 0.0;
        if (Double.compare(arg0, 0.0) < 0) {
            var7 = 0.0;
        } else {
            var7 = Math.min(arg0, 1.0);
        }
        return var7;
    }

    public static int computeColorOrIndex(int arg0, int arg1, double arg2) {
        double var17 = computeDoubleMetric(arg2);
        double var21 = ((double) computeColorOrIndex(arg0));
        int var33 = computeColorOrIndex(arg1);
        int var46 = computeColorOrIndex(arg0);
        double var37 = (((double) (var33 - var46)) * var17);
        int var25 = ((int) (var21 + var37));
        double var28 = ((double) computeColorOrIndex1(arg0));
        int var48 = computeColorOrIndex1(arg1);
        int var62 = computeColorOrIndex1(arg0);
        double var52 = (((double) (var48 - var62)) * var17);
        int var32 = ((int) (var28 + var52));
        double var41 = ((double) computeColorOrIndex2(arg0));
        int var64 = computeColorOrIndex2(arg1);
        int var71 = computeColorOrIndex2(arg0);
        double var68 = (((double) (var64 - var71)) * var17);
        int var45 = ((int) (var41 + var68));
        double var56 = ((double) computeColorOrIndex3(arg0));
        int var73 = computeColorOrIndex3(arg1);
        int var80 = computeColorOrIndex3(arg0);
        double var77 = (((double) (var73 - var80)) * var17);
        int var60 = ((int) (var56 + var77));
        return computeColorOrIndex(var25, var32, var45, var60);
    }

    public static int computeColorOrIndex(int arg0, double arg1) {
        int var13 = ((int) (((double) computeColorOrIndex(arg0)) * arg1));
        int var15 = computeColorOrIndex1(arg0);
        int var17 = computeColorOrIndex2(arg0);
        int var18 = computeColorOrIndex3(arg0);
        return computeColorOrIndex(var13, var15, var17, var18);
    }

    public static int computeColorOrIndex5(int arg0, double arg1) {
        double var14 = computeDoubleMetric(arg1);
        int var11 = ((int) (255.0 * var14));
        int var13 = computeColorOrIndex1(arg0);
        int var17 = computeColorOrIndex2(arg0);
        int var18 = computeColorOrIndex3(arg0);
        return computeColorOrIndex(var11, var13, var17, var18);
    }

    public static int computeColorOrIndex6(int arg0, double arg1) {
        int var12 = computeColorOrIndex(arg0);
        int var19 = ((int) (((double) computeColorOrIndex1(arg0)) * arg1));
        int var25 = ((int) (((double) computeColorOrIndex2(arg0)) * arg1));
        int var32 = ((int) (((double) computeColorOrIndex3(arg0)) * arg1));
        return computeColorOrIndex(var12, var19, var25, var32);
    }

    public static int computeColorOrIndex7(int arg0, double arg1) {
        int var8 = ((arg0 & -16777216) | 16777215);
        return computeColorOrIndex(arg0, var8, arg1);
    }

    public static int computeColorOrIndex8(int arg0, double arg1) {
        int var7 = (arg0 & -16777216);
        return computeColorOrIndex(arg0, var7, arg1);
    }

}
