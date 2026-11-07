package need.clarity.modules.visual.cosmetics;

import java.util.ArrayDeque;
import java.util.function.Predicate;

public class CosmeticDrawBuilder {
    public final int counterValue;
    public final int counterValue1;
    public final double doubleValue;
    public final boolean enabledState;
    public double doubleValue1;
    public double[] coordsVector;
    public Predicate filterPredicate;
    protected final CosmeticSink cosmeticSink;
    protected final double doubleValue2;
    private AnchorTransform anchorTransform;
    private double doubleValue3;
    private boolean enabledState1;
    private double[] coordsVector1;
    private final ArrayDeque transformStack;

    public CosmeticDrawBuilder(CosmeticSink arg0, int arg1, int arg2, double arg3, double arg4, boolean arg5) {
        AnchorTransform var10 = AnchorTransform.evaluate();
        this.anchorTransform = var10;
        this.doubleValue3 = 1.0;
        this.enabledState1 = false;
        double[] var25 = computeVector();
        this.coordsVector1 = var25;
        ArrayDeque var26 = new ArrayDeque();
        this.transformStack = var26;
        this.cosmeticSink = arg0;
        int var29 = (arg1 | -16777216);
        this.counterValue = var29;
        int var31 = (arg2 | -16777216);
        this.counterValue1 = var31;
        this.doubleValue2 = arg3;
        this.doubleValue = arg4;
        this.enabledState = arg5;
    }

    private static double[] computeVector() {
        double[] var4 = new double[12];
        var4[0] = 1.0;
        var4[1] = 0.0;
        var4[2] = 0.0;
        var4[3] = 0.0;
        var4[4] = 1.0;
        var4[5] = 0.0;
        var4[6] = 0.0;
        var4[7] = 0.0;
        var4[8] = 1.0;
        var4[9] = 0.0;
        var4[10] = 0.0;
        var4[11] = 0.0;
        return var4;
    }

    public void applyStep(AnchorTransform arg0, double arg1) {
        this.anchorTransform = arg0;
        this.doubleValue3 = arg1;
        double[] var13 = computeVector();
        this.coordsVector1 = var13;
        this.transformStack.clear();
        this.enabledState1 = false;
    }

    public double computeDoubleMetric() {
        return this.doubleValue3;
    }

    public CosmeticDrawBuilder drawGeometryPart(boolean arg0) {
        this.enabledState1 = arg0;
        return this;
    }

    public CosmeticDrawBuilder drawGeometryPart() {
        ArrayDeque var3 = this.transformStack;
        double[] var7 = ((double[]) this.coordsVector1.clone());
        var3.push(var7);
        return this;
    }

    public CosmeticDrawBuilder drawGeometryPart1() {
        double[] var6 = ((double[]) this.transformStack.pop());
        this.coordsVector1 = var6;
        return this;
    }

    public CosmeticDrawBuilder drawGeometryPart(double arg0, double arg1, double arg2) {
        double[] var16 = this.coordsVector1;
        double var24 = var16[9];
        double var39 = (this.coordsVector1[0] * arg0);
        double var71 = (this.coordsVector1[1] * arg1);
        double var41 = (var39 + var71);
        double var77 = (this.coordsVector1[2] * arg2);
        double var43 = (var41 + var77);
        double var26 = (var24 + var43);
        var16[9] = var26;
        double[] var18 = this.coordsVector1;
        double var28 = var18[10];
        double var49 = (this.coordsVector1[3] * arg0);
        double var85 = (this.coordsVector1[4] * arg1);
        double var51 = (var49 + var85);
        double var91 = (this.coordsVector1[5] * arg2);
        double var53 = (var51 + var91);
        double var30 = (var28 + var53);
        var18[10] = var30;
        double[] var20 = this.coordsVector1;
        double var32 = var20[11];
        double var59 = (this.coordsVector1[6] * arg0);
        double var99 = (this.coordsVector1[7] * arg1);
        double var61 = (var59 + var99);
        double var105 = (this.coordsVector1[8] * arg2);
        double var63 = (var61 + var105);
        double var34 = (var32 + var63);
        var20[11] = var34;
        return this;
    }

    public CosmeticDrawBuilder drawGeometryPart(double arg0) {
        int var7 = 0;
        var7 = 0;
        while (true) {
            if (var7 >= 9) break;
            double[] var11 = this.coordsVector1;
            int var12 = var7;
            double var15 = (var11[var12] * arg0);
            var11[var12] = var15;
            var7 = (var7 + 1);
        }
        return this;
    }

    public CosmeticDrawBuilder drawGeometryPart2(double arg0) {
        double var13 = Math.cos(arg0);
        double var17 = Math.sin(arg0);
        double[] var21 = new double[9];
        var21[0] = var13;
        var21[1] = 0.0;
        var21[2] = var17;
        var21[3] = 0.0;
        var21[4] = 1.0;
        var21[5] = 0.0;
        double var42 = -(var17);
        var21[6] = var42;
        var21[7] = 0.0;
        var21[8] = var13;
        return this.drawGeometryPart(var21);
    }

    public CosmeticDrawBuilder drawGeometryPart3(double arg0) {
        double var13 = Math.cos(arg0);
        double var17 = Math.sin(arg0);
        double[] var21 = new double[9];
        var21[0] = 1.0;
        var21[1] = 0.0;
        var21[2] = 0.0;
        var21[3] = 0.0;
        var21[4] = var13;
        double var40 = -(var17);
        var21[5] = var40;
        var21[6] = 0.0;
        var21[7] = var17;
        var21[8] = var13;
        return this.drawGeometryPart(var21);
    }

    public CosmeticDrawBuilder drawGeometryPart4(double arg0) {
        double var13 = Math.cos(arg0);
        double var17 = Math.sin(arg0);
        double[] var21 = new double[9];
        var21[0] = var13;
        double var32 = -(var17);
        var21[1] = var32;
        var21[2] = 0.0;
        var21[3] = var17;
        var21[4] = var13;
        var21[5] = 0.0;
        var21[6] = 0.0;
        var21[7] = 0.0;
        var21[8] = 1.0;
        return this.drawGeometryPart(var21);
    }

    private CosmeticDrawBuilder drawGeometryPart(double[] arg0) {
        int var12 = 0;
        int var13 = 0;
        double[] var14 = new double[12];
        var12 = 0;
        while (true) {
            if (var12 >= 3) break;
            var13 = 0;
            while (true) {
                if (var13 >= 3) break;
                int var27 = (var12 * 3);
                int var33 = var13;
                int var28 = (var27 + var33);
                double[] var35 = this.coordsVector1;
                int var56 = (var12 * 3);
                double var36 = var35[var56];
                int var7 = var13;
                double var61 = arg0[var7];
                double var38 = (var36 * var61);
                double[] var64 = this.coordsVector1;
                int var77 = ((var12 * 3) + 1);
                double var65 = var64[var77];
                int var10 = var13;
                int var90 = (3 + var10);
                double var83 = arg0[var90];
                double var67 = (var65 * var83);
                double var40 = (var38 + var67);
                double[] var70 = this.coordsVector1;
                int var80 = ((var12 * 3) + 2);
                double var71 = var70[var80];
                int var93 = var13;
                int var92 = (6 + var93);
                double var88 = arg0[var92];
                double var73 = (var71 * var88);
                double var42 = (var40 + var73);
                var14[var28] = var42;
                var13 = (var13 + 1);
            }
            var12 = (var12 + 1);
        }
        double var46 = this.coordsVector1[9];
        var14[9] = var46;
        double var50 = this.coordsVector1[10];
        var14[10] = var50;
        double var54 = this.coordsVector1[11];
        var14[11] = var54;
        this.coordsVector1 = var14;
        return this;
    }

    public double[] computeVector1() {
        double var26 = 0.0;
        if (this.coordsVector != null) {
            if (this.doubleValue3 == 0.0) {
            }
        }
        return null;
        double var61 = this.coordsVector[0];
        AnchorTransform var204 = this.anchorTransform;
        double var205 = var204.doubleValue;
        double var63 = (var61 - var205);
        double var67 = this.coordsVector[1];
        AnchorTransform var208 = this.anchorTransform;
        double var209 = var208.doubleValue1;
        double var69 = (var67 - var209);
        double var73 = this.coordsVector[2];
        AnchorTransform var212 = this.anchorTransform;
        double var213 = var212.doubleValue2;
        double var75 = (var73 - var213);
        double var216 = this.doubleValue3;
        double var79 = (1.0 / var216);
        double var85 = (this.anchorTransform.doubleValue3 * var63);
        AnchorTransform var221 = this.anchorTransform;
        double var222 = var221.doubleValue4;
        double var224 = (var222 * var69);
        double var87 = (var85 + var224);
        AnchorTransform var227 = this.anchorTransform;
        double var228 = var227.doubleValue5;
        double var230 = (var228 * var75);
        var26 = ((var87 + var230) * var79);
        double var97 = (this.anchorTransform.doubleValue6 * var63);
        AnchorTransform var237 = this.anchorTransform;
        double var238 = var237.doubleValue7;
        double var240 = (var238 * var69);
        double var99 = (var97 + var240);
        AnchorTransform var243 = this.anchorTransform;
        double var244 = var243.doubleValue8;
        double var246 = (var244 * var75);
        double var103 = ((var99 + var246) * var79);
        double var109 = (this.anchorTransform.doubleValue9 * var63);
        AnchorTransform var253 = this.anchorTransform;
        double var254 = var253.doubleValue10;
        double var256 = (var254 * var69);
        double var111 = (var109 + var256);
        double var262 = (this.anchorTransform.doubleValue11 * var75);
        double var115 = ((var111 + var262) * var79);
        if (this.enabledState1) {
            var26 = -(var26);
        }
        double var123 = var26;
        double var268 = this.coordsVector1[9];
        double var125 = (var123 - var268);
        double var272 = this.coordsVector1[10];
        double var129 = (var103 - var272);
        double var276 = this.coordsVector1[11];
        double var133 = (var115 - var276);
        double var137 = this.coordsVector1[0];
        double var141 = this.coordsVector1[1];
        double var145 = this.coordsVector1[2];
        double var149 = this.coordsVector1[3];
        double var153 = this.coordsVector1[4];
        double var157 = this.coordsVector1[5];
        double var161 = this.coordsVector1[6];
        double var165 = this.coordsVector1[7];
        double var169 = this.coordsVector1[8];
        double var280 = (var153 * var169);
        double var357 = (var157 * var165);
        double var282 = (var280 - var357);
        double var173 = (var137 * var282);
        double var361 = (var149 * var169);
        double var451 = (var157 * var161);
        double var363 = (var361 - var451);
        double var286 = (var141 * var363);
        double var175 = (var173 - var286);
        double var367 = (var149 * var165);
        double var457 = (var153 * var161);
        double var369 = (var367 - var457);
        double var290 = (var145 * var369);
        double var177 = (var175 + var290);
        int var183 = Double.compare(Math.abs(var177), 1.0E-12);
        if (var183 < 0) {
            return null;
        }
        double var186 = (1.0 / var177);
        double[] var189 = new double[3];
        double var303 = (var153 * var169);
        double var373 = (var157 * var165);
        double var307 = ((var303 - var373) * var125);
        double var379 = (var145 * var165);
        double var463 = (var141 * var169);
        double var383 = ((var379 - var463) * var129);
        double var309 = (var307 + var383);
        double var387 = (var141 * var157);
        double var471 = (var145 * var153);
        double var391 = ((var387 - var471) * var133);
        double var313 = ((var309 + var391) * var186);
        var189[0] = var313;
        double var317 = (var157 * var161);
        double var399 = (var149 * var169);
        double var321 = ((var317 - var399) * var125);
        double var405 = (var137 * var169);
        double var481 = (var145 * var161);
        double var409 = ((var405 - var481) * var129);
        double var323 = (var321 + var409);
        double var413 = (var145 * var149);
        double var489 = (var137 * var157);
        double var417 = ((var413 - var489) * var133);
        double var327 = ((var323 + var417) * var186);
        var189[1] = var327;
        double var331 = (var149 * var165);
        double var425 = (var153 * var161);
        double var335 = ((var331 - var425) * var125);
        double var431 = (var141 * var161);
        double var499 = (var137 * var165);
        double var435 = ((var431 - var499) * var129);
        double var337 = (var335 + var435);
        double var439 = (var137 * var153);
        double var507 = (var141 * var149);
        double var441 = (var439 - var507);
        double var443 = (var441 * var133);
        double var341 = ((var337 + var443) * var186);
        var189[2] = var341;
        return var189;
    }

    public double computeDoubleMetric(double[] arg0, double[] arg1) {
        double[] var21 = this.computeVector1();
        if (var21 == null) {
            return 0.5;
        }
        double var26 = var21[0];
        double var84 = arg0[0];
        double var28 = (var26 - var84);
        double var31 = var21[1];
        double var87 = arg0[1];
        double var33 = (var31 - var87);
        double var36 = var21[2];
        double var90 = arg0[2];
        double var38 = (var36 - var90);
        double var42 = (var28 * var28);
        double var96 = (var33 * var33);
        double var44 = (var42 + var96);
        double var100 = (var38 * var38);
        double var48 = Math.sqrt((var44 + var100));
        double var51 = arg1[0];
        double var103 = arg1[0];
        double var53 = (var51 * var103);
        double var106 = arg1[1];
        double var143 = arg1[1];
        double var108 = (var106 * var143);
        double var55 = (var53 + var108);
        double var111 = arg1[2];
        double var146 = arg1[2];
        double var113 = (var111 * var146);
        double var59 = Math.sqrt((var55 + var113));
        if (Double.compare(var48, 1.0E-9) >= 0) {
            if (Double.compare(var59, 1.0E-9) < 0) {
            }
        }
        return 0.5;
        double var120 = arg1[0];
        double var69 = (var28 * var120);
        double var149 = arg1[1];
        double var124 = (var33 * var149);
        double var71 = (var69 + var124);
        double var152 = arg1[2];
        double var128 = (var38 * var152);
        double var73 = (var71 + var128);
        double var132 = (var48 * var59);
        return Math.abs((var73 / var132));
    }

    public double[] computeVector(double[] arg0) {
        double var9 = 0.0;
        double var16 = this.coordsVector1[0];
        double var65 = arg0[0];
        double var18 = (var16 * var65);
        double var69 = this.coordsVector1[1];
        double var139 = arg0[1];
        double var71 = (var69 * var139);
        double var20 = (var18 + var71);
        double var75 = this.coordsVector1[2];
        double var142 = arg0[2];
        double var77 = (var75 * var142);
        double var22 = (var20 + var77);
        double var81 = this.coordsVector1[9];
        var9 = (var22 + var81);
        double var28 = this.coordsVector1[3];
        double var84 = arg0[0];
        double var30 = (var28 * var84);
        double var88 = this.coordsVector1[4];
        double var145 = arg0[1];
        double var90 = (var88 * var145);
        double var32 = (var30 + var90);
        double var94 = this.coordsVector1[5];
        double var148 = arg0[2];
        double var96 = (var94 * var148);
        double var34 = (var32 + var96);
        double var100 = this.coordsVector1[10];
        double var36 = (var34 + var100);
        double var40 = this.coordsVector1[6];
        double var103 = arg0[0];
        double var42 = (var40 * var103);
        double var107 = this.coordsVector1[7];
        double var151 = arg0[1];
        double var109 = (var107 * var151);
        double var44 = (var42 + var109);
        double var113 = this.coordsVector1[8];
        double var154 = arg0[2];
        double var115 = (var113 * var154);
        double var46 = (var44 + var115);
        double var119 = this.coordsVector1[11];
        double var48 = (var46 + var119);
        if (this.enabledState1) {
            var9 = -(var9);
        }
        AnchorTransform var57 = this.anchorTransform;
        double var61 = var9;
        double var133 = this.doubleValue3;
        double var63 = (var61 * var133);
        double var162 = this.doubleValue3;
        double var137 = (var36 * var162);
        double var168 = this.doubleValue3;
        double var166 = (var48 * var168);
        return var57.computeVector(var63, var137, var166);
    }

    protected final int computeColorOrIndex(int arg0) {
        int var10 = 0;
        if (Double.compare(this.doubleValue2, 0.999) >= 0) {
            var10 = arg0;
        } else {
            double var11 = this.doubleValue2;
            var10 = ColorMathUtils.computeColorOrIndex(arg0, var11);
        }
        return var10;
    }

    public void applyStep(double[] arg0, double[] arg1, double[] arg2, double[] arg3, int arg4, int arg5, int arg6, int arg7) {
        double[] var22 = this.computeVector(arg0);
        double[] var24 = this.computeVector(arg1);
        double[] var26 = this.computeVector(arg2);
        double[] var28 = this.computeVector(arg3);
        if (this.filterPredicate != null) {
            Predicate var32 = this.filterPredicate;
            double[][] var40 = new double[4][];
            var40[0] = var22;
            var40[1] = var24;
            var40[2] = var26;
            var40[3] = var28;
            if (!var32.test(var40)) {
                return;
            }
        }
        CosmeticSink var35 = this.cosmeticSink;
        int var51 = this.computeColorOrIndex(arg4);
        int var53 = this.computeColorOrIndex(arg5);
        int var55 = this.computeColorOrIndex(arg6);
        int var57 = this.computeColorOrIndex(arg7);
        var35.quad(var22, var24, var26, var28, var51, var53, var55, var57);
    }

    public void applyStep(double[] arg0, double[] arg1, double[] arg2, double[] arg3, int arg4) {
        this.applyStep(arg0, arg1, arg2, arg3, arg4, arg4, arg4, arg4);
    }

    public void applyStep(double[] arg0, double[] arg1, double[] arg2, int arg3, int arg4, int arg5) {
        this.applyStep(arg0, arg1, arg2, arg2, arg3, arg4, arg5, arg5);
    }

    public void applyStep(double[] arg0, double[] arg1, double[] arg2, int arg3) {
        this.applyStep(arg0, arg1, arg2, arg3, arg3, arg3);
    }

    public void applyStep(double[] arg0, double[] arg1, double arg2, double arg3, int arg4, int arg5) {
        int var36 = 0;
        double var39 = arg1[0];
        double var85 = arg0[0];
        double var41 = (var39 - var85);
        double var44 = arg1[1];
        double var88 = arg0[1];
        double var46 = (var44 - var88);
        double var50 = (var41 * var41);
        double var94 = (var46 * var46);
        double var54 = Math.sqrt((var50 + var94));
        if (Double.compare(var54, 1.0E-9) < 0) {
            return;
        }
        double var63 = (-(var46) / var54);
        double var67 = (var41 / var54);
        var36 = -1;
        while (true) {
            if (var36 > 1) break;
            double var72 = this.doubleValue1;
            double var133 = ((double) var36);
            double var104 = (0.005 * var133);
            double var74 = (var72 + var104);
            double var80 = arg0[0];
            double var118 = ((var63 * arg2) * 0.5);
            double var82 = (var80 + var118);
            double var121 = arg0[1];
            double var160 = ((var67 * arg2) * 0.5);
            double var123 = (var121 + var160);
            double var165 = (arg0[2] + var74);
            double[] var84 = GeometryPrimitives.computeVector(var82, var123, var165);
            double var108 = arg0[0];
            double var140 = ((var63 * arg2) * 0.5);
            double var110 = (var108 - var140);
            double var143 = arg0[1];
            double var189 = ((var67 * arg2) * 0.5);
            double var145 = (var143 - var189);
            double var194 = (arg0[2] + var74);
            double[] var112 = GeometryPrimitives.computeVector(var110, var145, var194);
            double var127 = arg1[0];
            double var172 = ((var63 * arg3) * 0.5);
            double var129 = (var127 - var172);
            double var175 = arg1[1];
            double var222 = ((var67 * arg3) * 0.5);
            double var177 = (var175 - var222);
            double var227 = (arg1[2] + var74);
            double[] var131 = GeometryPrimitives.computeVector(var129, var177, var227);
            double var149 = arg1[0];
            double var201 = ((var63 * arg3) * 0.5);
            double var151 = (var149 + var201);
            double var204 = arg1[1];
            double var244 = ((var67 * arg3) * 0.5);
            double var206 = (var204 + var244);
            double var249 = (arg1[2] + var74);
            double[] var153 = GeometryPrimitives.computeVector(var151, var206, var249);
            this.applyStep(var84, var112, var131, var153, arg4, arg4, arg5, arg5);
            var36 = (var36 + 2);
        }
    }

}
