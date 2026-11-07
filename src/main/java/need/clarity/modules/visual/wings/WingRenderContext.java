package need.clarity.modules.visual.wings;

import need.clarity.modules.visual.cosmetics.CosmeticDrawBuilder;
import need.clarity.modules.visual.cosmetics.CosmeticSink;

public final class WingRenderContext extends CosmeticDrawBuilder {
    private double doubleValue;
    private double doubleValue1;
    private double doubleValue2;
    private double doubleValue3;
    private double doubleValue4;
    private double doubleValue5;
    private double doubleValue6;
    private double doubleValue7;
    private double doubleValue8;
    private double doubleValue9;
    private double doubleValue10;
    private double doubleValue11;
    public int counterValue;
    private static final double[] coordsVector;

    public WingRenderContext(CosmeticSink arg0, int arg1, int arg2, double arg3, double arg4, boolean arg5) {
        super(arg0, arg1, arg2, arg3, arg4, arg5);
        this.doubleValue8 = 1.0;
        this.doubleValue9 = 0.0;
        this.doubleValue10 = 1.0;
        this.doubleValue11 = 0.0;
    }

    public void applyStep(double[] arg0, double arg1, double arg2, double arg3, double arg4, double arg5, double arg6, double arg7, int arg8) {
        double var32 = arg0[0];
        this.doubleValue = var32;
        double var35 = arg0[1];
        this.doubleValue1 = var35;
        double var38 = arg0[2];
        this.doubleValue2 = var38;
        this.doubleValue3 = arg1;
        this.doubleValue4 = arg2;
        this.doubleValue5 = arg3;
        this.doubleValue6 = arg4;
        this.doubleValue7 = arg5;
        this.counterValue = arg8;
        double var53 = Math.cos(arg6);
        this.doubleValue8 = var53;
        double var57 = Math.sin(arg6);
        this.doubleValue9 = var57;
        double var61 = Math.cos(arg7);
        this.doubleValue10 = var61;
        double var65 = Math.sin(arg7);
        this.doubleValue11 = var65;
    }

    public double[] computeVector(double[] arg0) {
        double var26 = arg0[0];
        double var29 = arg0[1];
        double var32 = arg0[2];
        double var58 = this.doubleValue8;
        double var36 = (var26 * var58);
        double var95 = this.doubleValue9;
        double var62 = (var29 * var95);
        double var38 = (var36 - var62);
        double var65 = this.doubleValue9;
        double var42 = (var26 * var65);
        double var98 = this.doubleValue8;
        double var69 = (var29 * var98);
        double var44 = (var42 + var69);
        double var72 = this.doubleValue10;
        double var48 = (var38 * var72);
        double var101 = this.doubleValue11;
        double var76 = (var38 * var101);
        double var52 = (var32 + var76);
        double[] var55 = new double[3];
        double var81 = this.doubleValue;
        double var105 = (this.doubleValue3 * var48);
        double var127 = (this.doubleValue5 * var52);
        double var107 = (var105 - var127);
        double var130 = this.doubleValue7;
        double var109 = (var107 * var130);
        double var83 = (var81 + var109);
        var55[0] = var83;
        double var86 = this.doubleValue1;
        double var133 = this.doubleValue7;
        double var113 = (var44 * var133);
        double var88 = (var86 + var113);
        var55[1] = var88;
        double var91 = this.doubleValue2;
        double var118 = (this.doubleValue4 * var48);
        double var140 = (this.doubleValue6 * var52);
        double var120 = (var118 - var140);
        double var143 = this.doubleValue7;
        double var122 = (var120 * var143);
        double var93 = (var91 + var122);
        var55[2] = var93;
        return var55;
    }

    public void applyStep(double[] arg0, double arg1, double arg2, double arg3, double arg4, double arg5, int arg6, int arg7, int arg8, boolean arg9) {
        int var143 = 0;
        double var32 = 0.0;
        double var34 = 0.0;
        double var36 = 0.0;
        double[] var38 = null;
        double[] var39 = null;
        int var40 = 0;
        double[] var41 = null;
        int var42 = 0;
        int var57 = (coordsVector.length - 1);
        double var153 = ((double) var57);
        double var60 = (arg2 / var153);
        var32 = arg0[0];
        var34 = arg0[1];
        var36 = arg1;
        var38 = null;
        var39 = null;
        var40 = arg6;
        var41 = arg0;
        var42 = 0;
        while (true) {
            if (var42 <= var57) {
                double var83 = ((double) var42);
                double var163 = ((double) var57);
                double var85 = (var83 / var163);
                double var88 = arg0[2];
                double var169 = ((arg5 * var85) * var85);
                double var90 = (var88 + var169);
                double var96 = -(Math.sin(var36));
                double var100 = Math.cos(var36);
                double[] var171 = coordsVector;
                int var228 = var42;
                double var172 = var171[var228];
                double var106 = ((arg3 * var172) * 0.5);
                double var108 = var32;
                double var178 = (var96 * var106);
                double var110 = (var108 + var178);
                double var180 = var34;
                double var245 = (var100 * var106);
                double var182 = (var180 + var245);
                double[] var112 = WingColorMath.computeVector(var110, var182, var90);
                double var113 = var32;
                double var186 = (var96 * var106);
                double var115 = (var113 - var186);
                double var188 = var34;
                double var253 = (var100 * var106);
                double var190 = (var188 - var253);
                double[] var117 = WingColorMath.computeVector(var115, var190, var90);
                if (Double.compare(var85, 0.5) < 0) {
                    double var196 = (var85 * 2.0);
                    var143 = WingColorMath.computeColorOrIndex(arg6, arg7, var196);
                }
            } else {
                if (arg9) {
                    double var78 = arg0[0];
                    double var156 = arg0[1];
                    double var232 = (arg0[2] + 0.004);
                    double[] var80 = WingColorMath.computeVector(var78, var156, var232);
                    double var159 = var41[0];
                    double var235 = var41[1];
                    double var283 = (var41[2] + 0.004);
                    double[] var161 = WingColorMath.computeVector(var159, var235, var283);
                    double var226 = (arg3 * 0.09);
                    double var277 = (arg3 * 0.02);
                    int var293 = WingColorMath.computeColorOrIndex7(arg7, 0.6);
                    int var299 = WingColorMath.computeColorOrIndex(WingColorMath.computeColorOrIndex7(arg8, 0.5), 0.8);
                    this.applyStep(var80, var161, var226, var277, var293, var299);
                }
                return;
                double var221 = ((var85 - 0.5) * 2.0);
                var143 = WingColorMath.computeColorOrIndex(arg7, arg8, var221);
            }
            int var55 = var143;
            if (var42 > 0) {
                double[] var151 = var38;
                double[] var198 = var39;
                int var279 = var40;
                int var289 = var40;
                this.applyStep(var151, var198, var117, var112, var279, var289, var55, var55);
            }
            var38 = var112;
            var39 = var117;
            var40 = var55;
            double var127 = var32;
            double var199 = var34;
            var41 = WingColorMath.computeVector(var127, var199, var90);
            double var130 = var32;
            double var205 = (Math.cos(var36) * var60);
            var32 = (var130 + var205);
            double var134 = var34;
            double var211 = (Math.sin(var36) * var60);
            var34 = (var134 + var211);
            double var138 = var36;
            double var215 = (arg4 * var60);
            var36 = (var138 + var215);
            var42 = (var42 + 1);
        }
    }

    static {
        double[] var4 = new double[5];
        var4[0] = 0.55;
        var4[1] = 0.92;
        var4[2] = 1.0;
        var4[3] = 0.72;
        var4[4] = 0.0;
        coordsVector = var4;
    }

}
