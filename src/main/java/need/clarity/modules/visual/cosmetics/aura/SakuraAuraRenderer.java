package need.clarity.modules.visual.cosmetics.aura;

import need.clarity.modules.visual.cosmetics.ColorMathUtils;
import need.clarity.modules.visual.cosmetics.CosmeticDrawBuilder;
import need.clarity.modules.visual.cosmetics.GeometryPrimitives;
import need.clarity.modules.visual.cosmetics.ICosmeticEffectRenderer;
import need.clarity.modules.visual.cosmetics.IGradientColorSampler;

public final class SakuraAuraRenderer implements ICosmeticEffectRenderer {
    public static final int CONST_INT = 16763103;
    public static final int CONST_INT1 = 16735902;
    public static final int CONST_INT2 = 284;
    private static final int CONST_INT3 = 2;
    private static final int CONST_INT4 = 22;
    private static final int CONST_INT5 = 4;
    private static final int CONST_INT6 = 14;
    private static final int CONST_INT7 = 28;
    private static final int CONST_INT8 = 15;
    private static final int CONST_INT9 = 16771238;
    private final ElementalAuraSpec elementalAuraSpec;

    public SakuraAuraRenderer() {
        ElementalAuraSpec var2 = ElementalAuraSpec.evaluate();
        this(var2);
    }

    public SakuraAuraRenderer(ElementalAuraSpec arg0) {
        this.elementalAuraSpec = arg0;
    }

    public void renderEffect(CosmeticDrawBuilder arg0) {
        int var100 = 0;
        int var101 = 0;
        int var102 = 0;
        int var24 = 0;
        int var25 = 0;
        double var27 = 0.0;
        if (arg0.enabledState) {
            var27 = arg0.doubleValue;
        } else {
            var27 = 0.0;
        }
        double var17 = var27;
        double var82 = this.elementalAuraSpec.radius();
        double var66 = (0.5 * var82);
        double var31 = (0.45 + var66);
        double var35 = this.elementalAuraSpec.bodyHeight();
        double[] var38 = arg0.computeVector1();
        this.drawGeometryPart(arg0, var31);
        var24 = 0;
        while (true) {
            if (var24 >= 28) break;
            int var86 = var24;
            this.drawGeometryPart(arg0, var31, var86, var17);
            var24 = (var24 + 1);
        }
        var100 = 0;
        while (true) {
            if (var100 >= 2) break;
            int var11 = var100;
            this.drawGeometryPart(arg0, var31, var35, var11, var17);
            var25 = 0;
            while (true) {
                if (var25 >= 22) break;
                int var93 = var100;
                int var14 = var25;
                this.drawGeometryPart(arg0, var38, var31, var35, var93, var14, var17);
                var25 = (var25 + 1);
            }
            var100 = (var100 + 1);
        }
        var101 = 0;
        while (true) {
            if (var101 >= 14) break;
            int var94 = var101;
            this.drawGeometryPart(arg0, var38, var31, var35, var94, var17);
            var101 = (var101 + 1);
        }
        var102 = 0;
        while (true) {
            if (var102 >= 4) break;
            int var95 = var102;
            this.drawGeometryPart1(arg0, var38, var31, var35, var95, var17);
            var102 = (var102 + 1);
        }
    }

    private void drawGeometryPart(CosmeticDrawBuilder arg0, double arg1) {
        int var17 = arg0.counterValue;
        int var19 = arg0.counterValue1;
        double var21 = (arg1 * 1.2);
        IGradientColorSampler var24 = (p0, p1) -> SakuraAuraRenderer.lambda$a(var19, var17, p0, p1);
        GeometryPrimitives.drawGeometryPart(arg0, 0.014, 0.1, var21, 24, var24);
    }

    private void drawGeometryPart(CosmeticDrawBuilder arg0, double arg1, double arg2, double arg3) {
        int var32 = arg0.counterValue1;
        int var85 = arg0.counterValue;
        int var36 = AuraRenderMath.computeColorOrIndex(ColorMathUtils.computeColorOrIndex5(ColorMathUtils.computeColorOrIndex6(ColorMathUtils.computeColorOrIndex(var32, var85, 0.15), arg2), 0.95), arg3);
        int var38 = arg0.counterValue1;
        int var93 = arg0.counterValue;
        int var42 = AuraRenderMath.computeColorOrIndex(ColorMathUtils.computeColorOrIndex5(ColorMathUtils.computeColorOrIndex6(ColorMathUtils.computeColorOrIndex(var38, var93, 0.55), arg2), 0.95), arg3);
        int var48 = AuraRenderMath.computeColorOrIndex(ColorMathUtils.computeColorOrIndex5(ColorMathUtils.computeColorOrIndex6(ColorMathUtils.computeColorOrIndex7(arg0.counterValue, 0.45), arg2), 0.93), arg3);
        int var54 = AuraRenderMath.computeColorOrIndex(ColorMathUtils.computeColorOrIndex5(ColorMathUtils.computeColorOrIndex6(ColorMathUtils.computeColorOrIndex7(arg0.counterValue, 0.25), arg2), 0.9), arg3);
        double var57 = (0.5 * arg1);
        double[] var61 = GeometryPrimitives.computeVector(0.0, 0.0, 0.0);
        double var64 = -(var57);
        double var126 = (0.1 * arg1);
        double var155 = (0.5 * arg1);
        double[] var66 = GeometryPrimitives.computeVector(var64, var126, var155);
        double var130 = (0.1 * arg1);
        double var161 = (0.5 * arg1);
        double[] var69 = GeometryPrimitives.computeVector(var57, var130, var161);
        double var72 = (-0.55 * var57);
        double var136 = (0.18 * arg1);
        double[] var74 = GeometryPrimitives.computeVector(var72, var136, arg1);
        double var77 = (0.55 * var57);
        double var142 = (0.18 * arg1);
        double[] var79 = GeometryPrimitives.computeVector(var77, var142, arg1);
        double var146 = (0.1 * arg1);
        double var175 = (0.86 * arg1);
        double[] var82 = GeometryPrimitives.computeVector(0.0, var146, var175);
        arg0.applyStep(var61, var66, var74, var82, var36, var42, var48, var54);
        arg0.applyStep(var61, var82, var79, var69, var36, var54, var48, var42);
    }

    private double drawGeometryPart(CosmeticDrawBuilder arg0) {
        double[] var25 = GeometryPrimitives.computeVector(0.0, 0.0, 0.0);
        double[] var26 = GeometryPrimitives.computeVector(0.0, 1.0, 0.0);
        double var23 = arg0.computeDoubleMetric(var25, var26);
        double var21 = (0.34 * var23);
        return (0.78 + var21);
    }

    private double drawGeometryPart(CosmeticDrawBuilder arg0, int arg1, int arg2, double arg3) {
        double var26 = 0.0;
        double var12 = ((double) arg2);
        double var38 = ((double) arg1);
        double var28 = (0.5 * var38);
        double var16 = ((var12 + var28) / 22.0);
        if (arg0.enabledState) {
            double var36 = (arg3 * 0.075);
            var26 = AuraRenderMath.computeDoubleMetric((var16 + var36));
        } else {
            var26 = (var16 % 1.0);
        }
        return var26;
    }

    private double[] computeVector(int arg0, double arg1, double arg2, double arg3, double arg4, boolean arg5) {
        double var68 = 0.0;
        double var87 = 0.0;
        double var41 = ((double) arg0);
        double var24 = (3.141592653589793 * var41);
        double var47 = ((arg1 * 6.283185307179586) * 1.6);
        double var26 = (var24 + var47);
        if (arg5) {
            var68 = (arg2 * 0.5);
        } else {
            var68 = 0.0;
        }
        double var28 = (var26 + var68);
        double var74 = (0.55 * arg1);
        double var52 = (0.62 + var74);
        double var32 = (arg3 * var52);
        if (arg5) {
            double var97 = Math.sin(((arg1 * 9.0) + arg2));
            var87 = (0.06 * var97);
        } else {
            var87 = 0.0;
        }
        double var56 = (1.0 + var87);
        double var34 = (var32 * var56);
        double var60 = Math.cos(var28);
        double var38 = (var34 * var60);
        double var101 = (arg4 + 0.25);
        double var81 = (arg1 * var101);
        double var64 = (0.03 + var81);
        double var105 = Math.sin(var28);
        double var85 = (var34 * var105);
        return GeometryPrimitives.computeVector(var38, var64, var85);
    }

    private void drawGeometryPart(CosmeticDrawBuilder arg0, double[] arg1, double arg2, double arg3, int arg4, int arg5, double arg6) {
        double var158 = 0.0;
        double var111 = 0.0;
        double var177 = 0.0;
        double var38 = this.drawGeometryPart(arg0, arg4, arg5, arg6);
        boolean var241 = arg0.enabledState;
        double[] var41 = this.computeVector(arg4, var38, arg6, arg2, arg3, var241);
        double var46 = GeometryPrimitives.computeDoubleMetric((var38 / 0.08));
        double var128 = GeometryPrimitives.computeDoubleMetric(((1.0 - var38) / 0.15));
        double var48 = (var46 * var128);
        double var87 = var41[0];
        double var146 = var41[1];
        double var195 = var41[2];
        double var228 = arg0.computeDoubleMetric();
        double var53 = (AuraRenderMath.computeDoubleMetric(arg1, var87, var146, var195, var228) * var48);
        if (Double.compare(var53, 0.03) <= 0) {
            return;
        }
        int var60 = ((arg4 * 50) + arg5);
        double var189 = GeometryPrimitives.computeDoubleMetric(((var60 * 17) + 1));
        double var137 = (0.05 * var189);
        double var63 = (0.13 + var137);
        double var66 = var41[2];
        double var140 = var41[0];
        double var68 = Math.atan2(var66, var140);
        CosmeticDrawBuilder var71 = arg0.drawGeometryPart();
        double var93 = var41[0];
        double var150 = var41[1];
        double var200 = var41[2];
        CosmeticDrawBuilder var73 = arg0.drawGeometryPart(var93, var150, var200);
        double var99 = (-(var68) + 1.5707963267948966);
        if (arg0.enabledState) {
            var158 = (arg6 * 1.4);
        } else {
            var158 = 0.0;
        }
        double var101 = (var99 + var158);
        double var161 = ((double) var60);
        double var103 = (var101 + var161);
        CosmeticDrawBuilder var75 = arg0.drawGeometryPart2(var103);
        if (arg0.enabledState) {
            double var218 = (arg6 * 2.0);
            double var239 = (((double) var60) * 1.3);
            double var222 = Math.sin((var218 + var239));
            var177 = (0.9 * var222);
        } else {
            var177 = 0.3;
        }
        double var107 = (0.6 + var177);
        CosmeticDrawBuilder var77 = arg0.drawGeometryPart3(var107);
        if (arg0.enabledState) {
            double var169 = (arg6 * 1.5);
            double var214 = (((double) var60) * 0.7);
            double var173 = Math.sin((var169 + var214));
            var111 = (0.6 * var173);
        } else {
            var111 = 0.2;
        }
        CosmeticDrawBuilder var79 = arg0.drawGeometryPart4(var111);
        double var206 = (-0.5 * var63);
        CosmeticDrawBuilder var81 = arg0.drawGeometryPart(0.0, 0.0, var206);
        double var193 = this.drawGeometryPart(arg0);
        this.drawGeometryPart(arg0, var63, var193, var53);
        CosmeticDrawBuilder var84 = arg0.drawGeometryPart1();
    }

    private void drawGeometryPart(CosmeticDrawBuilder arg0, double arg1, double arg2, int arg3, double arg4) {
        int var26 = 0;
        int var29 = 0;
        double[][] var40 = new double[(15 + 1)][];
        var26 = 0;
        while (true) {
            if (var26 > 15) break;
            int var97 = var26;
            double var199 = ((double) var26);
            double var179 = (0.94 * var199);
            double var202 = ((double) 15);
            double var181 = (var179 / var202);
            double var148 = (0.03 + var181);
            boolean var206 = arg0.enabledState;
            double[] var114 = this.computeVector(arg3, var148, arg4, arg1, arg2, var206);
            var40[var97] = var114;
            var26 = (var26 + 1);
        }
        int var47 = ColorMathUtils.computeColorOrIndex7(arg0.counterValue, 0.2);
        int var49 = arg0.counterValue1;
        var29 = 0;
        while (true) {
            if (var29 >= 15) break;
            double var53 = ((double) var29);
            double var98 = ((double) 15);
            double var55 = (var53 / var98);
            double var59 = ((double) (var29 + 1));
            double var101 = ((double) 15);
            double var61 = (var59 / var101);
            int var64 = ColorMathUtils.computeColorOrIndex(var49, var47, var55);
            double var117 = Math.sin((3.141592653589793 * var55));
            double var83 = (0.28 * var117);
            int var65 = ColorMathUtils.computeColorOrIndex5(var64, var83);
            int var67 = ColorMathUtils.computeColorOrIndex(var49, var47, var61);
            double var123 = Math.sin((3.141592653589793 * var61));
            double var88 = (0.28 * var123);
            int var68 = ColorMathUtils.computeColorOrIndex5(var67, var88);
            int var90 = var29;
            double[] var70 = var40[var90];
            int var92 = (var29 + 1);
            double[] var72 = var40[var92];
            double var94 = var70[0];
            double var128 = (var70[1] + 0.07);
            double var155 = var70[2];
            double[] var96 = GeometryPrimitives.computeVector(var94, var128, var155);
            double var110 = var70[0];
            double var139 = (var70[1] - 0.07);
            double var168 = var70[2];
            double[] var112 = GeometryPrimitives.computeVector(var110, var139, var168);
            double var132 = var72[0];
            double var161 = (var72[1] - 0.07);
            double var188 = var72[2];
            double[] var134 = GeometryPrimitives.computeVector(var132, var161, var188);
            double var143 = var72[0];
            double var174 = (var72[1] + 0.07);
            double var195 = var72[2];
            double[] var145 = GeometryPrimitives.computeVector(var143, var174, var195);
            arg0.applyStep(var96, var112, var134, var145, var65, var65, var68, var68);
            var29 = (var29 + 1);
        }
    }

    private void drawGeometryPart(CosmeticDrawBuilder arg0, double[] arg1, double arg2, double arg3, int arg4, double arg5) {
        double var146 = 0.0;
        double var56 = 0.0;
        double var223 = 0.0;
        double var234 = 0.0;
        double var125 = 0.0;
        double var255 = 0.0;
        double var259 = GeometryPrimitives.computeDoubleMetric(((arg4 * 43) + 1));
        double var153 = (0.06 * var259);
        double var46 = (0.1 + var153);
        if (arg0.enabledState) {
            double var52 = (arg5 * var46);
            double var160 = GeometryPrimitives.computeDoubleMetric(((arg4 * 43) + 2));
            var56 = AuraRenderMath.computeDoubleMetric((var52 + var160));
        } else {
            var56 = GeometryPrimitives.computeDoubleMetric(((arg4 * 43) + 2));
        }
        double var26 = var56;
        double var165 = GeometryPrimitives.computeDoubleMetric(((arg4 * 43) + 3));
        double var60 = (6.283185307179586 * var165);
        double var169 = (arg2 * 1.25);
        double var268 = Math.sqrt(GeometryPrimitives.computeDoubleMetric(((arg4 * 43) + 4)));
        double var171 = (var169 * var268);
        double var64 = Math.max(0.36, var171);
        if (arg0.enabledState) {
            double var175 = (arg5 * 1.1);
            double var275 = (((double) arg4) * 1.7);
            double var179 = Math.sin((var175 + var275));
            var125 = (0.1 * var179);
        } else {
            var125 = 0.0;
        }
        double var32 = var125;
        double var183 = Math.cos(var60);
        double var74 = ((var64 * var183) + var32);
        double var189 = Math.sin(var60);
        double var78 = (var64 * var189);
        if (arg0.enabledState) {
            double var293 = (arg5 * 0.9);
            double var333 = ((double) arg4);
            double var297 = Math.cos((var293 + var333));
            var223 = (0.08 * var297);
        } else {
            var223 = 0.0;
        }
        double var80 = (var78 + var223);
        double var84 = (arg3 + 0.6);
        double var197 = (1.0 - var26);
        double var88 = ((var84 * var197) + 0.03);
        double var94 = GeometryPrimitives.computeDoubleMetric((var26 / 0.1));
        double var209 = GeometryPrimitives.computeDoubleMetric(((1.0 - var26) / 0.12));
        double var96 = (var94 * var209);
        double var335 = arg0.computeDoubleMetric();
        double var101 = (AuraRenderMath.computeDoubleMetric(arg1, var74, var88, var80, var335) * var96);
        if (Double.compare(var101, 0.03) <= 0) {
            return;
        }
        double var286 = GeometryPrimitives.computeDoubleMetric(((arg4 * 43) + 5));
        double var217 = (0.05 * var286);
        double var108 = (0.09 + var217);
        CosmeticDrawBuilder var111 = arg0.drawGeometryPart();
        CosmeticDrawBuilder var113 = arg0.drawGeometryPart(var74, var88, var80);
        if (arg0.enabledState) {
            var234 = (arg5 * 1.1);
        } else {
            var234 = 0.0;
        }
        double var134 = (var60 + var234);
        double var237 = ((double) arg4);
        double var136 = (var134 + var237);
        CosmeticDrawBuilder var115 = arg0.drawGeometryPart2(var136);
        if (arg0.enabledState) {
            double var310 = (arg5 * 1.8);
            double var342 = (((double) arg4) * 2.1);
            double var314 = Math.sin((var310 + var342));
            var255 = (1.0 * var314);
        } else {
            var255 = 0.2;
        }
        double var140 = (0.8 + var255);
        CosmeticDrawBuilder var117 = arg0.drawGeometryPart3(var140);
        if (arg0.enabledState) {
            double var245 = (arg5 * 1.3);
            double var319 = ((double) arg4);
            double var249 = Math.sin((var245 + var319));
            var146 = (0.7 * var249);
        } else {
            var146 = 0.1;
        }
        CosmeticDrawBuilder var119 = arg0.drawGeometryPart4(var146);
        double var323 = (-0.5 * var108);
        CosmeticDrawBuilder var121 = arg0.drawGeometryPart(0.0, 0.0, var323);
        double var289 = this.drawGeometryPart(arg0);
        this.drawGeometryPart(arg0, var108, var289, var101);
        CosmeticDrawBuilder var124 = arg0.drawGeometryPart1();
    }

    private void drawGeometryPart(CosmeticDrawBuilder arg0, double arg1, int arg2, double arg3) {
        double var20 = 0.0;
        double var62 = 0.0;
        double var28 = (arg1 * 1.3);
        double var80 = Math.sqrt(GeometryPrimitives.computeDoubleMetric(((arg2 * 41) + 1)));
        var20 = (var28 * var80);
        if (Double.compare(var20, 0.32) < 0) {
            var20 = (var20 + 0.32);
        }
        double var89 = GeometryPrimitives.computeDoubleMetric(((arg2 * 41) + 2));
        double var41 = (6.283185307179586 * var89);
        double var127 = GeometryPrimitives.computeDoubleMetric(((arg2 * 41) + 3));
        double var93 = (0.05 * var127);
        double var45 = (0.08 + var93);
        if (arg0.enabledState) {
            double var97 = (arg3 * 0.7);
            double var134 = (((double) arg2) * 1.3);
            double var101 = Math.sin((var97 + var134));
            var62 = (0.05 * var101);
        } else {
            var62 = 0.0;
        }
        double var26 = var62;
        CosmeticDrawBuilder var52 = arg0.drawGeometryPart();
        double var9 = var20;
        double var110 = Math.cos(var41);
        double var64 = (var9 * var110);
        double var156 = ((double) (arg2 % 3));
        double var143 = (0.004 * var156);
        double var114 = (0.016 + var143);
        double var145 = var20;
        double var160 = Math.sin(var41);
        double var147 = (var145 * var160);
        CosmeticDrawBuilder var54 = arg0.drawGeometryPart(var64, var114, var147);
        double var119 = GeometryPrimitives.computeDoubleMetric(((arg2 * 41) + 4));
        double var70 = ((6.283185307179586 * var119) + var26);
        CosmeticDrawBuilder var56 = arg0.drawGeometryPart2(var70);
        double var151 = (-0.5 * var45);
        CosmeticDrawBuilder var58 = arg0.drawGeometryPart(0.0, 0.0, var151);
        this.drawGeometryPart(arg0, var45, 0.9, 1.0);
        CosmeticDrawBuilder var61 = arg0.drawGeometryPart1();
    }

    private void drawGeometryPart1(CosmeticDrawBuilder arg0, double[] arg1, double arg2, double arg3, int arg4, double arg5) {
        double var157 = 0.0;
        int var46 = 0;
        double var189 = 0.0;
        double var211 = 0.0;
        double var213 = 0.0;
        double var151 = ((double) arg4);
        double var50 = ((6.283185307179586 * var151) / 4.0);
        if (arg0.enabledState) {
            var157 = (arg5 * 0.22);
        } else {
            var157 = 0.0;
        }
        double var52 = (var50 + var157);
        double var56 = (arg2 * 1.05);
        double var262 = ((double) ((arg4 * 3) % 4));
        double var217 = ((0.52 * var262) / 3.0);
        double var163 = (0.24 + var217);
        double var60 = (arg3 * var163);
        if (arg0.enabledState) {
            double var228 = (arg5 * 1.2);
            double var273 = (((double) arg4) * 1.9);
            double var232 = Math.sin((var228 + var273));
            var189 = (0.06 * var232);
        } else {
            var189 = 0.0;
        }
        double var62 = (var60 + var189);
        double var169 = Math.cos(var52);
        double var66 = (var56 * var169);
        double var173 = Math.sin(var52);
        double var70 = (var56 * var173);
        double var277 = arg0.computeDoubleMetric();
        double var73 = AuraRenderMath.computeDoubleMetric(arg1, var66, var62, var70, var277);
        if (Double.compare(var73, 0.03) <= 0) {
            return;
        }
        double var222 = GeometryPrimitives.computeDoubleMetric(((arg4 * 47) + 1));
        double var179 = (0.03 * var222);
        double var80 = (0.17 + var179);
        CosmeticDrawBuilder var83 = arg0.drawGeometryPart();
        CosmeticDrawBuilder var85 = arg0.drawGeometryPart(var66, var62, var70);
        double var118 = -(var52);
        if (arg0.enabledState) {
            double var242 = (arg5 * 0.8);
            double var282 = ((double) arg4);
            double var246 = Math.sin((var242 + var282));
            var213 = (0.6 * var246);
        } else {
            var213 = 0.0;
        }
        double var120 = (var118 + var213);
        double var198 = ((double) arg4);
        double var122 = (var120 + var198);
        CosmeticDrawBuilder var87 = arg0.drawGeometryPart2(var122);
        if (arg0.enabledState) {
            double var254 = (arg5 * 1.1);
            double var293 = (((double) arg4) * 2.3);
            double var258 = Math.sin((var254 + var293));
            var211 = (0.25 * var258);
        } else {
            var211 = 0.0;
        }
        double var126 = (-0.9 + var211);
        CosmeticDrawBuilder var89 = arg0.drawGeometryPart3(var126);
        double var91 = this.drawGeometryPart(arg0);
        var46 = 0;
        while (true) {
            if (var46 >= 5) break;
            CosmeticDrawBuilder var106 = arg0.drawGeometryPart();
            double var205 = ((double) var46);
            double var146 = ((6.283185307179586 * var205) / 5.0);
            CosmeticDrawBuilder var108 = arg0.drawGeometryPart2(var146);
            CosmeticDrawBuilder var110 = arg0.drawGeometryPart3(-0.35);
            this.drawGeometryPart(arg0, var80, var91, var73);
            CosmeticDrawBuilder var113 = arg0.drawGeometryPart1();
            var46 = (var46 + 1);
        }
        int var97 = AuraRenderMath.computeColorOrIndex(ColorMathUtils.computeColorOrIndex5(16771238, 0.95), var73);
        int var135 = arg0.counterValue1;
        int var101 = AuraRenderMath.computeColorOrIndex(ColorMathUtils.computeColorOrIndex5(ColorMathUtils.computeColorOrIndex(16771238, var135, 0.4), 0.9), var73);
        double var250 = (var80 * 0.22);
        AuraRenderMath.drawGeometryPart(arg0, 0.0, 0.0, var250, 0.006, 5, 0.0, var97, var101);
        CosmeticDrawBuilder var104 = arg0.drawGeometryPart1();
    }

}
