package need.clarity.modules.visual.cosmetics;


public final class AnchorTransform {
    public final double doubleValue;
    public final double doubleValue1;
    public final double doubleValue2;
    public final double doubleValue3;
    public final double doubleValue4;
    public final double doubleValue5;
    public final double doubleValue6;
    public final double doubleValue7;
    public final double doubleValue8;
    public final double doubleValue9;
    public final double doubleValue10;
    public final double doubleValue11;

    public AnchorTransform(double arg0, double arg1, double arg2, double arg3, double arg4, double arg5, double arg6, double arg7, double arg8, double arg9, double arg10, double arg11) {
        this.doubleValue = arg0;
        this.doubleValue1 = arg1;
        this.doubleValue2 = arg2;
        this.doubleValue3 = arg3;
        this.doubleValue4 = arg4;
        this.doubleValue5 = arg5;
        this.doubleValue6 = arg6;
        this.doubleValue7 = arg7;
        this.doubleValue8 = arg8;
        this.doubleValue9 = arg9;
        this.doubleValue10 = arg10;
        this.doubleValue11 = arg11;
    }

    public static AnchorTransform evaluate() {
        AnchorTransform var0 = new AnchorTransform(0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0);
        return var0;
    }

    public double[] computeVector(double arg0, double arg1, double arg2) {
        double[] var13 = new double[3];
        double var16 = this.doubleValue;
        double var44 = (this.doubleValue3 * arg0);
        double var18 = (var16 + var44);
        double var49 = (this.doubleValue6 * arg1);
        double var20 = (var18 + var49);
        double var54 = (this.doubleValue9 * arg2);
        double var22 = (var20 + var54);
        var13[0] = var22;
        double var25 = this.doubleValue1;
        double var59 = (this.doubleValue4 * arg0);
        double var27 = (var25 + var59);
        double var64 = (this.doubleValue7 * arg1);
        double var29 = (var27 + var64);
        double var69 = (this.doubleValue10 * arg2);
        double var31 = (var29 + var69);
        var13[1] = var31;
        double var34 = this.doubleValue2;
        double var74 = (this.doubleValue5 * arg0);
        double var36 = (var34 + var74);
        double var79 = (this.doubleValue8 * arg1);
        double var38 = (var36 + var79);
        double var84 = (this.doubleValue11 * arg2);
        double var40 = (var38 + var84);
        var13[2] = var40;
        return var13;
    }

    public double[] computeVector(double[] arg0) {
        double var10 = arg0[0];
        double var12 = arg0[1];
        double var14 = arg0[2];
        return this.computeVector(var10, var12, var14);
    }

    public AnchorTransform evaluate(double arg0, double arg1, double arg2) {
        double[] var28 = this.computeVector(arg0, arg1, arg2);
        double var30 = var28[0];
        double var33 = var28[1];
        double var36 = var28[2];
        double var38 = this.doubleValue3;
        double var40 = this.doubleValue4;
        double var42 = this.doubleValue5;
        double var44 = this.doubleValue6;
        double var46 = this.doubleValue7;
        double var48 = this.doubleValue8;
        double var50 = this.doubleValue9;
        double var52 = this.doubleValue10;
        double var54 = this.doubleValue11;
        AnchorTransform var29 = new AnchorTransform(var30, var33, var36, var38, var40, var42, var44, var46, var48, var50, var52, var54);
        return var29;
    }

}
