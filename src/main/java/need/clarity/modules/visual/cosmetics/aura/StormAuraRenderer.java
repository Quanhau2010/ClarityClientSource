package need.clarity.modules.visual.cosmetics.aura;

import need.clarity.modules.visual.cosmetics.ColorMathUtils;
import need.clarity.modules.visual.cosmetics.CosmeticDrawBuilder;
import need.clarity.modules.visual.cosmetics.GeometryPrimitives;
import need.clarity.modules.visual.cosmetics.ICosmeticEffectRenderer;
import need.clarity.modules.visual.cosmetics.IGradientColorSampler;

public final class StormAuraRenderer implements ICosmeticEffectRenderer {
    public static final int CONST_INT = 13624319;
    public static final int CONST_INT1 = 5661583;
    public static final int CONST_INT2 = 384;
    private static final int CONST_INT3 = 7;
    private static final int CONST_INT4 = 30;
    private static final int CONST_INT5 = 5;
    private static final int CONST_INT6 = 3;
    private static final int CONST_INT7 = 10;
    private static final double CONST_DBL = 1.9;
    private static final double CONST_DBL1 = 0.3;
    private final ElementalAuraSpec elementalAuraSpec;

    public StormAuraRenderer() {
        ElementalAuraSpec var2 = ElementalAuraSpec.evaluate();
        this(var2);
    }

    public StormAuraRenderer(ElementalAuraSpec arg0) {
        this.elementalAuraSpec = arg0;
    }

    public void renderEffect(CosmeticDrawBuilder arg0) {
        int var132 = 0;
        int var133 = 0;
        double var72 = 0.0;
        int var30 = 0;
        if (arg0.enabledState) {
            var72 = arg0.doubleValue;
        } else {
            var72 = 0.0;
        }
        double var16 = var72;
        double var109 = this.elementalAuraSpec.radius();
        double var82 = (0.45 * var109);
        double var34 = (0.35 + var82);
        double var38 = this.elementalAuraSpec.bodyHeight();
        double var113 = this.elementalAuraSpec.radius();
        double var86 = (0.3 * var113);
        double var42 = (0.55 + var86);
        double var46 = (var38 + 0.55);
        double var50 = (var42 * 1.15);
        double[] var53 = arg0.computeVector1();
        StormCloudCell var55 = this.drawGeometryPart(arg0, var16, var34);
        double var126 = var55.flash;
        this.drawGeometryPart(arg0, var53, var42, var46, var126, var16);
        var30 = 0;
        while (true) {
            if (var30 >= 30) break;
            int var128 = var30;
            this.drawGeometryPart(arg0, var53, var50, var46, var128, var16);
            var30 = (var30 + 1);
        }
        var132 = 0;
        while (true) {
            if (var132 >= 5) break;
            int var117 = var132;
            this.drawGeometryPart(arg0, var50, var117, var16);
            var132 = (var132 + 1);
        }
        var133 = 0;
        while (true) {
            if (var133 >= 3) break;
            int var129 = var133;
            this.drawGeometryPart1(arg0, var53, var34, var38, var129, var16);
            var133 = (var133 + 1);
        }
        if (Double.compare(var55.flash, 0.02) > 0) {
            this.drawGeometryPart(arg0, var55, var42, var46);
        }
    }

    private StormCloudCell drawGeometryPart(CosmeticDrawBuilder arg0, double arg1, double arg2) {
        double var26 = 0.0;
        int var92 = 0;
        double var93 = 0.0;
        if (arg0.enabledState) {
            var92 = ((int) Math.floor((arg1 / 1.9)));
        } else {
            var92 = 1;
        }
        int var21 = ((byte) var92);
        double var44 = (GeometryPrimitives.computeDoubleMetric(((var21 * 7) + 1)) * 1.4999999999999998);
        if (arg0.enabledState) {
            double var101 = (((double) var21) * 1.9);
            var93 = ((arg1 - var101) - var44);
        } else {
            var93 = 0.0;
        }
        double var24 = var93;
        var26 = 0.0;
        if (arg0.enabledState) {
            if (Double.compare(var24, 0.0) >= 0) {
                if (Double.compare(var24, 0.3) < 0) {
                    if (Double.compare(var24, 0.07) < 0) {
                        var26 = 1.0;
                    } else {
                        if (Double.compare(var24, 0.13) < 0) {
                            var26 = 0.25;
                        } else {
                            if (Double.compare(var24, 0.23) < 0) {
                                var26 = 0.9;
                            } else {
                                double var144 = ((var24 - 0.23) / 0.06999999999999998);
                                double var129 = (1.0 - var144);
                                var26 = (0.9 * var129);
                            }
                        }
                    }
                }
            }
        } else {
            var26 = 1.0;
        }
        double var108 = GeometryPrimitives.computeDoubleMetric(((var21 * 7) + 2));
        double var60 = (6.283185307179586 * var108);
        double var162 = GeometryPrimitives.computeDoubleMetric(((var21 * 7) + 3));
        double var148 = (0.6 * var162);
        double var136 = (0.9 + var148);
        double var114 = ((arg2 * var136) + 0.25);
        double var64 = Math.max(0.55, var114);
        double var132 = var26;
        double var154 = Math.cos(var60);
        double var146 = (var64 * var154);
        double var167 = Math.sin(var60);
        double var158 = (var64 * var167);
        StormCloudCell var66 = new StormCloudCell(var21, var132, var146, var158);
        return var66;
    }

    private void drawGeometryPart(CosmeticDrawBuilder arg0, double[] arg1, double arg2, double arg3, double arg4, double arg5) {
        int var40 = 0;
        double var41 = 0.0;
        double var43 = 0.0;
        double var45 = 0.0;
        double var47 = 0.0;
        double var235 = 0.0;
        double var239 = 0.0;
        var40 = 0;
        while (true) {
            if (var40 < 7) {
                if (var40 == 0) {
                    var41 = 0.0;
                    var43 = 0.0;
                    var45 = arg3;
                    var47 = (arg2 * 0.62);
                } else {
                    if (var40 <= 5) {
                        double var203 = ((double) (var40 - 1));
                        double var126 = ((6.283185307179586 * var203) / 5.0);
                        if (arg0.enabledState) {
                            var239 = (arg5 * 0.1);
                        } else {
                            var239 = 0.0;
                        }
                        double var128 = (var126 + var239);
                        double var132 = (arg2 * 0.58);
                        double var284 = GeometryPrimitives.computeDoubleMetric(((var40 * 59) + 1));
                        double var258 = (0.2 * var284);
                        double var213 = (0.9 + var258);
                        double var134 = (var132 * var213);
                        double var217 = Math.cos(var128);
                        var41 = (var134 * var217);
                        double var221 = Math.sin(var128);
                        var43 = (var134 * var221);
                        double var314 = (GeometryPrimitives.computeDoubleMetric(((var40 * 59) + 2)) - 0.5);
                        double var288 = (0.16 * var314);
                        double var262 = (-0.05 + var288);
                        double var225 = (arg2 * var262);
                        double var146 = (arg3 + var225);
                        if (arg0.enabledState) {
                            double var270 = (arg5 * 0.9);
                            double var300 = (((double) var40) * 1.7);
                            double var274 = Math.sin((var270 + var300));
                            var235 = (0.03 * var274);
                        } else {
                            var235 = 0.0;
                        }
                        var45 = (var146 + var235);
                        double var293 = GeometryPrimitives.computeDoubleMetric(((var40 * 59) + 3));
                        double var266 = (0.14 * var293);
                        double var231 = (0.34 + var266);
                        var47 = (arg2 * var231);
                    }
                }
            } else {
                return;
                double var104 = (arg2 * 0.12);
                double var185 = Math.cos((arg5 * 0.1));
                var41 = (var104 * var185);
                double var110 = (arg2 * 0.12);
                double var193 = Math.sin((arg5 * 0.1));
                var43 = (var110 * var193);
                double var197 = (arg2 * 0.24);
                var45 = (arg3 + var197);
                var47 = (arg2 * 0.36);
            }
            double var74 = (var47 * 0.72);
            double var154 = var41;
            double var15 = var45;
            double var18 = var43;
            double var302 = arg0.computeDoubleMetric();
            double var77 = AuraRenderMath.computeDoubleMetric(arg1, var154, var15, var18, var302);
            if (Double.compare(var77, 0.03) <= 0) {
            } else {
                double var248 = GeometryPrimitives.computeDoubleMetric(((var40 * 59) + 4));
                double var173 = (0.16 * var248);
                double var84 = (0.92 + var173);
                int var87 = arg0.counterValue1;
                double var158 = (0.5 * var84);
                int var88 = ColorMathUtils.computeColorOrIndex6(var87, var158);
                int var90 = arg0.counterValue1;
                int var161 = arg0.counterValue;
                int var91 = ColorMathUtils.computeColorOrIndex(var90, var161, 0.45);
                int var94 = ColorMathUtils.computeColorOrIndex7(arg0.counterValue, 0.7);
                double var98 = (((double) var40) * 1.9);
                double var164 = var41;
                double var243 = var45;
                double var280 = var43;
                double var304 = var47;
                double var27 = var47;
                IGradientColorSampler var321 = AuraRenderMath.evaluate((p0, p1) -> StormAuraRenderer.lambda$a(var98, var88, var91, var94, arg4, p0, p1), var77);
                GeometryPrimitives.drawGeometryPart(arg0, var164, var243, var280, var304, var74, var27, 7, 4, var321);
            }
            var40 = (var40 + 1);
        }
    }

    private void drawGeometryPart(CosmeticDrawBuilder arg0, double[] arg1, double arg2, double arg3, int arg4, double arg5) {
        double var124 = 0.0;
        double var231 = GeometryPrimitives.computeDoubleMetric(((arg4 * 61) + 1));
        double var144 = (0.5 * var231);
        double var56 = (0.9 + var144);
        if (arg0.enabledState) {
            double var62 = (arg5 * var56);
            double var151 = GeometryPrimitives.computeDoubleMetric(((arg4 * 61) + 2));
            var124 = AuraRenderMath.computeDoubleMetric((var62 + var151));
        } else {
            var124 = GeometryPrimitives.computeDoubleMetric(((arg4 * 61) + 2));
        }
        double var30 = var124;
        double var156 = GeometryPrimitives.computeDoubleMetric(((arg4 * 61) + 3));
        double var68 = (6.283185307179586 * var156);
        double var160 = (arg2 - 0.35);
        double var240 = Math.sqrt(GeometryPrimitives.computeDoubleMetric(((arg4 * 61) + 4)));
        double var162 = (var160 * var240);
        double var72 = (0.35 + var162);
        double var76 = (arg3 - 0.2);
        double var168 = (1.0 - var30);
        double var82 = ((var76 * var168) + 0.03);
        double var174 = Math.cos(var68);
        double var90 = (var72 * var174);
        double var246 = (var76 - var82);
        double var178 = (0.1 * var246);
        double var92 = (var90 + var178);
        double var182 = Math.sin(var68);
        double var96 = (var72 * var182);
        double var319 = arg0.computeDoubleMetric();
        double var99 = AuraRenderMath.computeDoubleMetric(arg1, var92, var82, var96, var319);
        double var190 = GeometryPrimitives.computeDoubleMetric(((var82 - 0.02) / 0.15));
        double var101 = (var99 * var190);
        if (Double.compare(var101, 0.04) < 0) {
            return;
        }
        int var110 = ColorMathUtils.computeColorOrIndex7(arg0.counterValue, 0.5);
        double var130 = (0.8 * var101);
        int var111 = ColorMathUtils.computeColorOrIndex5(var110, var130);
        int var114 = ColorMathUtils.computeColorOrIndex5(arg0.counterValue, 0.0);
        double var117 = (0.1 * 0.3);
        double[] var138 = GeometryPrimitives.computeVector((var92 - 0.007), var82, var96);
        double[] var200 = GeometryPrimitives.computeVector((var92 + 0.007), var82, var96);
        double var219 = ((var92 + 0.007) + var117);
        double var283 = (var82 + 0.3);
        double[] var221 = GeometryPrimitives.computeVector(var219, var283, var96);
        double var260 = ((var92 - 0.007) + var117);
        double var305 = (var82 + 0.3);
        double[] var262 = GeometryPrimitives.computeVector(var260, var305, var96);
        arg0.applyStep(var138, var200, var221, var262, var111, var111, var114, var114);
        double var288 = (var96 - 0.007);
        double[] var141 = GeometryPrimitives.computeVector(var92, var82, var288);
        double var310 = (var96 + 0.007);
        double[] var203 = GeometryPrimitives.computeVector(var92, var82, var310);
        double var226 = (var92 + var117);
        double var294 = (var82 + 0.3);
        double var332 = (var96 + 0.007);
        double[] var228 = GeometryPrimitives.computeVector(var226, var294, var332);
        double var267 = (var92 + var117);
        double var316 = (var82 + 0.3);
        double var344 = (var96 - 0.007);
        double[] var269 = GeometryPrimitives.computeVector(var267, var316, var344);
        arg0.applyStep(var141, var203, var228, var269, var111, var111, var114, var114);
    }

    private void drawGeometryPart(CosmeticDrawBuilder arg0, double arg1, int arg2, double arg3) {
        double var72 = 0.0;
        double var129 = GeometryPrimitives.computeDoubleMetric(((arg2 * 67) + 1));
        double var82 = (0.7 * var129);
        double var32 = (0.9 + var82);
        if (arg0.enabledState) {
            double var38 = (arg3 / var32);
            double var89 = GeometryPrimitives.computeDoubleMetric(((arg2 * 67) + 2));
            var72 = AuraRenderMath.computeDoubleMetric((var38 + var89));
        } else {
            var72 = 0.35;
        }
        double var21 = var72;
        double var94 = GeometryPrimitives.computeDoubleMetric(((arg2 * 67) + 3));
        double var44 = (6.283185307179586 * var94);
        double var98 = (arg1 - 0.35);
        double var138 = Math.sqrt(GeometryPrimitives.computeDoubleMetric(((arg2 * 67) + 4)));
        double var100 = (var98 * var138);
        double var48 = (0.35 + var100);
        double var104 = (0.2 * var21);
        double var52 = (0.04 + var104);
        double var110 = Math.pow((1.0 - var21), 1.2);
        double var56 = (0.55 * var110);
        if (Double.compare(var56, 0.03) < 0) {
            return;
        }
        int var64 = ColorMathUtils.computeColorOrIndex5(ColorMathUtils.computeColorOrIndex7(arg0.counterValue, 0.4), var56);
        CosmeticDrawBuilder var66 = arg0.drawGeometryPart();
        double var119 = Math.cos(var44);
        double var78 = (var48 * var119);
        double var159 = Math.sin(var44);
        double var151 = (var48 * var159);
        CosmeticDrawBuilder var68 = arg0.drawGeometryPart(var78, 0.018, var151);
        double var125 = (var52 - 0.018);
        double var157 = (var52 + 0.018);
        IGradientColorSampler var164 = (p0, p1) -> StormAuraRenderer.lambda$a(var64, p0, p1);
        GeometryPrimitives.drawGeometryPart(arg0, 0.0, var125, var157, 8, var164);
        CosmeticDrawBuilder var71 = arg0.drawGeometryPart1();
    }

    private void drawGeometryPart1(CosmeticDrawBuilder arg0, double[] arg1, double arg2, double arg3, int arg4, double arg5) {
        int var35 = 0;
        double var222 = 0.0;
        double var123 = 0.0;
        int var381 = 0;
        double var322 = ((double) arg4);
        double var255 = (0.3 * var322);
        double var154 = (0.18 + var255);
        double var52 = ((arg3 * var154) + 0.1);
        if ((arg4 % 2) == 0) {
            var123 = 1.0;
        } else {
            var123 = -1.0;
        }
        double var27 = var123;
        double var161 = GeometryPrimitives.computeDoubleMetric(((arg4 * 71) + 1));
        double var58 = (6.283185307179586 * var161);
        if (arg0.enabledState) {
            double var379 = ((double) arg4);
            double var352 = (0.5 * var379);
            double var298 = (2.0 + var352);
            var222 = ((arg5 * var298) * var27);
        } else {
            var222 = 0.0;
        }
        double var60 = (var58 + var222);
        double var325 = ((double) arg4);
        double var259 = (0.1 * var325);
        double var167 = (0.95 + var259);
        double var64 = (arg2 * var167);
        int var68 = ColorMathUtils.computeColorOrIndex7(arg0.counterValue, 0.3);
        double[][] var70 = new double[11][];
        var35 = 0;
        while (true) {
            if (var35 > 10) break;
            double var116 = (((double) var35) / 10.0);
            double var211 = (((var27 * var116) * 6.283185307179586) * 0.55);
            double var120 = (var60 + var211);
            int var153 = var35;
            double var290 = Math.cos(var120);
            double var215 = (var64 * var290);
            double var372 = (var116 - 0.5);
            double var344 = (0.12 * var372);
            double var294 = (var52 + var344);
            double var376 = Math.sin(var120);
            double var348 = (var64 * var376);
            double[] var217 = GeometryPrimitives.computeVector(var215, var294, var348);
            var70[var153] = var217;
            var35 = (var35 + 1);
        }
        var381 = 0;
        while (true) {
            if (var381 >= 10) break;
            double var78 = (((double) var381) / 10.0);
            double var84 = (((double) (var381 + 1)) / 10.0);
            int var173 = var381;
            double var132 = var70[var173][0];
            int var261 = var381;
            double var227 = var70[var261][1];
            int var327 = var381;
            double var303 = var70[var327][2];
            double var354 = arg0.computeDoubleMetric();
            double var87 = AuraRenderMath.computeDoubleMetric(arg1, var132, var227, var303, var354);
            if (Double.compare(var87, 0.03) <= 0) {
            } else {
                double var183 = Math.pow(Math.sin((3.141592653589793 * var78)), 0.8);
                double var96 = ((0.055 * var183) + 0.004);
                double var193 = Math.pow(Math.sin((3.141592653589793 * var84)), 0.8);
                double var102 = ((0.055 * var193) + 0.004);
                double var233 = Math.sin((3.141592653589793 * var78));
                double var138 = ((0.38 * var233) * var87);
                int var105 = ColorMathUtils.computeColorOrIndex5(var68, var138);
                double var241 = Math.sin((3.141592653589793 * var84));
                double var144 = ((0.38 * var241) * var87);
                int var107 = ColorMathUtils.computeColorOrIndex5(var68, var144);
                int var146 = var381;
                double[] var109 = var70[var146];
                int var148 = (var381 + 1);
                double[] var111 = var70[var148];
                double var150 = var109[0];
                double var248 = (var109[1] + var96);
                double var312 = var109[2];
                double[] var152 = GeometryPrimitives.computeVector(var150, var248, var312);
                double var200 = var109[0];
                double var275 = (var109[1] - var96);
                double var333 = var109[2];
                double[] var202 = GeometryPrimitives.computeVector(var200, var275, var333);
                double var252 = var111[0];
                double var318 = (var111[1] - var102);
                double var360 = var111[2];
                double[] var254 = GeometryPrimitives.computeVector(var252, var318, var360);
                double var279 = var111[0];
                double var339 = (var111[1] + var102);
                double var367 = var111[2];
                double[] var281 = GeometryPrimitives.computeVector(var279, var339, var367);
                arg0.applyStep(var152, var202, var254, var281, var105, var105, var107, var107);
            }
            var381 = (var381 + 1);
        }
    }

    private void drawGeometryPart(CosmeticDrawBuilder arg0, StormCloudCell arg1, double arg2, double arg3) {
        int var265 = 0;
        int var40 = 0;
        int var44 = 0;
        int var51 = arg1.id();
        double var53 = arg1.flash();
        double var58 = (arg1.gx() * 0.35);
        double var147 = (arg3 - 0.12);
        double var218 = (arg1.gz() * 0.35);
        double[] var60 = GeometryPrimitives.computeVector(var58, var147, var218);
        double var62 = arg1.gx();
        double var221 = arg1.gz();
        double[] var64 = GeometryPrimitives.computeVector(var62, 0.03, var221);
        double[][] var68 = new double[(8 + 1)][];
        var40 = 0;
        while (true) {
            if (var40 <= 8) {
                double var90 = ((double) var40);
                double var172 = ((double) 8);
                double var92 = (var90 / var172);
                double var100 = (Math.sin((3.141592653589793 * var92)) * 0.9);
                if (var40 > 0) {
                    if (var40 < 8) {
                        var265 = 1;
                    }
                }
            } else {
                this.drawGeometryPart(arg0, var68, 0.036, var53);
                double var155 = GeometryPrimitives.computeDoubleMetric(((var51 * 53) + 40));
                double var74 = (6.283185307179586 * var155);
                double[][] var77 = new double[5][];
                double[] var158 = var68[3];
                var77[0] = var158;
                var44 = 1;
                while (true) {
                    if (var44 > 4) break;
                    double var84 = (((double) var44) / 4.0);
                    int var132 = var44;
                    double var163 = var68[3][0];
                    double var229 = ((Math.cos(var74) * 0.32) * var84);
                    double var165 = (var163 + var229);
                    int var233 = ((var51 * 53) + 41);
                    int var268 = var44;
                    double var239 = ((GeometryPrimitives.computeDoubleMetric((var233 + var268)) - 0.5) * 0.14);
                    double var167 = (var165 + var239);
                    double var243 = var68[3][1];
                    double var294 = (((var68[3][1] - 0.35) * var84) * 0.85);
                    double var245 = (var243 - var294);
                    double var298 = var68[3][2];
                    double var341 = ((Math.sin(var74) * 0.32) * var84);
                    double var300 = (var298 + var341);
                    int var345 = ((var51 * 53) + 51);
                    int var368 = var44;
                    double var351 = ((GeometryPrimitives.computeDoubleMetric((var345 + var368)) - 0.5) * 0.14);
                    double var302 = (var300 + var351);
                    double[] var169 = GeometryPrimitives.computeVector(var167, var245, var302);
                    var77[var132] = var169;
                    var44 = (var44 + 1);
                }
                this.drawGeometryPart(arg0, var77, 0.022, var53);
                double var135 = arg1.gx();
                double var212 = arg1.gz();
                double var386 = (0.55 * var53);
                int var385 = ColorMathUtils.computeColorOrIndex5(-1, var386);
                int var390 = ColorMathUtils.computeColorOrIndex5(arg0.counterValue, 0.0);
                AuraRenderMath.drawGeometryPart(arg0, var135, var212, 0.55, 0.03, 10, 0.0, var385, var390);
                return;
                var265 = 0;
            }
            double var249 = ((double) var265);
            double var180 = (0.1 * var249);
            double var102 = (var100 + var180);
            int var105 = (var51 * 53);
            int var139 = (var40 * 2);
            double var113 = (((GeometryPrimitives.computeDoubleMetric((var105 + var139)) - 0.5) * 0.36) * var102);
            int var116 = (var51 * 53);
            int var142 = (var40 * 2);
            double var125 = (((GeometryPrimitives.computeDoubleMetric(((var116 + var142) + 1)) - 0.5) * 0.36) * var102);
            int var144 = var40;
            double var197 = var60[0];
            double var252 = var64[0];
            double var305 = var60[0];
            double var256 = ((var252 - var305) * var92);
            double var201 = ((var197 + var256) + var113);
            double var261 = var60[1];
            double var310 = var64[1];
            double var354 = var60[1];
            double var314 = ((var310 - var354) * var92);
            double var263 = (var261 + var314);
            double var317 = var60[2];
            double var359 = var64[2];
            double var381 = var60[2];
            double var363 = ((var359 - var381) * var92);
            double var321 = ((var317 + var363) + var125);
            double[] var203 = GeometryPrimitives.computeVector(var201, var263, var321);
            var68[var144] = var203;
            var40 = (var40 + 1);
        }
    }

    private void drawGeometryPart(CosmeticDrawBuilder arg0, double[][] arg1, double arg2, double arg3) {
        int var24 = 0;
        int var29 = 0;
        int var58 = 0;
        double var61 = 0.0;
        int var63 = arg0.counterValue;
        int var34 = ColorMathUtils.computeColorOrIndex5(ColorMathUtils.computeColorOrIndex(-1, var63, 0.2), arg3);
        int var36 = arg0.counterValue;
        double var68 = (0.32 * arg3);
        int var37 = ColorMathUtils.computeColorOrIndex5(var36, var68);
        var24 = 0;
        while (true) {
            int var39 = var24;
            int var72 = (arg1.length - 1);
            if (var39 >= var72) break;
            int var73 = var24;
            double[] var41 = arg1[var73];
            int var75 = (var24 + 1);
            double[] var43 = arg1[var75];
            double var133 = ((double) var24);
            double var91 = (0.4 * var133);
            double var139 = (((double) arg1.length) - 1.0);
            double var93 = (var91 / var139);
            double var46 = (1.0 - var93);
            var29 = 0;
            while (true) {
                if (var29 >= 2) break;
                if (var29 == 0) {
                    var61 = (arg2 * 2.0);
                } else {
                    var61 = (arg2 * 0.5);
                }
                double var53 = (var61 * var46);
                if (var29 == 0) {
                    var58 = var37;
                } else {
                    var58 = var34;
                }
                int var32 = var58;
                double var80 = (var41[0] - var53);
                double var116 = var41[1];
                double var163 = var41[2];
                double[] var82 = GeometryPrimitives.computeVector(var80, var116, var163);
                double var103 = (var41[0] + var53);
                double var145 = var41[1];
                double var186 = var41[2];
                double[] var105 = GeometryPrimitives.computeVector(var103, var145, var186);
                double var122 = (var43[0] + var53);
                double var169 = var43[1];
                double var205 = var43[2];
                double[] var124 = GeometryPrimitives.computeVector(var122, var169, var205);
                double var151 = (var43[0] - var53);
                double var192 = var43[1];
                double var218 = var43[2];
                double[] var153 = GeometryPrimitives.computeVector(var151, var192, var218);
                arg0.applyStep(var82, var105, var124, var153, var32);
                double var84 = var41[0];
                double var126 = var41[1];
                double var176 = (var41[2] - var53);
                double[] var86 = GeometryPrimitives.computeVector(var84, var126, var176);
                double var108 = var41[0];
                double var156 = var41[1];
                double var198 = (var41[2] + var53);
                double[] var110 = GeometryPrimitives.computeVector(var108, var156, var198);
                double var130 = var43[0];
                double var180 = var43[1];
                double var214 = (var43[2] + var53);
                double[] var132 = GeometryPrimitives.computeVector(var130, var180, var214);
                double var160 = var43[0];
                double var202 = var43[1];
                double var226 = (var43[2] - var53);
                double[] var162 = GeometryPrimitives.computeVector(var160, var202, var226);
                arg0.applyStep(var86, var110, var132, var162, var32);
                var29 = (var29 + 1);
            }
            var24 = (var24 + 1);
        }
    }

}
