package need.clarity.modules.visual.cosmetics.killeffect;

import need.clarity.modules.visual.cosmetics.ColorMathUtils;
import need.clarity.modules.visual.cosmetics.CosmeticDrawBuilder;
import need.clarity.modules.visual.cosmetics.GeometryPrimitives;
import need.clarity.modules.visual.cosmetics.ICosmeticEffectRenderer;
import need.clarity.modules.visual.cosmetics.IGradientColorSampler;

public final class AscendKillRenderer implements ICosmeticEffectRenderer {
    public static final int CONST_INT = 16763213;
    public static final int CONST_INT1 = 16774876;
    static final int CONST_INT2 = 4;
    static final int CONST_INT3 = 24;
    static final int CONST_INT4 = 14;
    static final int CONST_INT5 = 12;
    static final int CONST_INT6 = 20;
    static final int CONST_INT7 = 24;
    static final int CONST_INT8 = 16;
    public static final int CONST_INT9 = 456;
    static final double[] coordsVector;
    static final double[] coordsVector1;
    private final KillEffectRenderSpec killEffectRenderSpec;

    public AscendKillRenderer() {
        KillEffectRenderSpec var2 = KillEffectRenderSpec.evaluate();
        this(var2);
    }

    public AscendKillRenderer(KillEffectRenderSpec arg0) {
        this.killEffectRenderSpec = arg0;
    }

    static double computeDoubleMetric(int arg0) {
        double var10 = ((double) arg0);
        double var8 = (0.07 * var10);
        return (0.12 + var8);
    }

    public static double computeDoubleMetric(int arg0, double arg1) {
        double var13 = computeDoubleMetric(arg0);
        double var7 = (arg1 - var13);
        double var21 = computeDoubleMetric(arg0);
        double var17 = (1.0 - var21);
        return KillEffectMathUtils.computeDoubleMetric((var7 / var17), 0.0, 1.0);
    }

    private static double computeDoubleMetric1(int arg0, double arg1) {
        double var14 = (1.0 - arg1);
        double var24 = ((double) arg0);
        double var22 = (0.25 * var24);
        double var20 = (2.4 - var22);
        double var16 = Math.pow(var14, var20);
        return (1.0 - var16);
    }

    private static double computeDoubleMetric2(int arg0, double arg1) {
        double var18 = ((double) arg0);
        double var14 = (0.015 * var18);
        return GeometryPrimitives.computeDoubleMetric(((arg1 - var14) / 0.09));
    }

    static double computeDoubleMetric(double arg0, double arg1) {
        double var10 = (0.5 * arg0);
        double var18 = (0.15 * arg1);
        return ((var10 + var18) + 0.1);
    }

    private double[] computeVector(int arg0, double arg1, double arg2, double arg3, double arg4) {
        double var40 = this.killEffectRenderSpec.height();
        double var43 = computeDoubleMetric1(arg0, arg2);
        double var80 = coordsVector[arg0];
        double var130 = coordsVector1[arg0];
        double var170 = coordsVector[arg0];
        double var134 = ((var130 - var170) * var43);
        double var84 = ((var80 + var134) * var40);
        double var47 = (arg4 + var84);
        double var50 = computeDoubleMetric2(arg0, arg1);
        double var90 = Math.pow((1.0 - var50), 3.0);
        double var54 = (1.0 - var90);
        double var175 = ((double) arg0);
        double var144 = (0.07 * var175);
        double var94 = (1.0 - var144);
        double var58 = (arg3 * var94);
        double var148 = (0.7 * var54);
        double var98 = (0.3 + var148);
        double var60 = (var58 * var98);
        double var197 = Math.min(1.0, var50);
        double var183 = Math.sin((3.141592653589793 * var197));
        double var152 = (0.1 * var183);
        double var102 = (1.0 + var152);
        double var62 = (var60 * var102);
        double var191 = GeometryPrimitives.computeDoubleMetric(((arg2 - 0.25) / 0.75));
        double var156 = (0.62 * var191);
        double var106 = (1.0 - var156);
        double var64 = (var62 * var106);
        double var67 = this.computeDoubleMetric3(arg0, arg1);
        double var73 = ((0.1 * arg3) * var43);
        double[] var76 = new double[4];
        var76[0] = var47;
        var76[1] = var64;
        double var162 = Math.cos((var67 + 1.0));
        double var124 = (var73 * var162);
        var76[2] = var124;
        double var168 = Math.sin((var67 + 1.0));
        double var128 = (var73 * var168);
        var76[3] = var128;
        return var76;
    }

    private double computeDoubleMetric3(int arg0, double arg1) {
        double var33 = 0.0;
        int var18 = (this.killEffectRenderSpec.seed() * 47);
        int var36 = (arg0 * 13);
        int var19 = (var18 + var36);
        if (Double.compare(GeometryPrimitives.computeDoubleMetric((var19 + 2)), 0.5) < 0) {
            var33 = -1.0;
        } else {
            var33 = 1.0;
        }
        double var14 = var33;
        double var29 = (GeometryPrimitives.computeDoubleMetric((var19 + 1)) * 6.283185307179586);
        double var60 = GeometryPrimitives.computeDoubleMetric((var19 + 3));
        double var57 = (0.6 * var60);
        double var51 = (0.8 + var57);
        double var49 = (((var14 * var51) * 6.283185307179586) * arg1);
        return (var29 + var49);
    }

    public void renderEffect(CosmeticDrawBuilder arg0) {
        double var163 = 0.0;
        double var168 = 0.0;
        double var43 = 0.0;
        int var45 = 0;
        double var61 = this.killEffectRenderSpec.life();
        double var65 = this.killEffectRenderSpec.age();
        if (Double.compare(var61, 1.0) >= 0) {
            return;
        }
        double var72 = this.killEffectRenderSpec.width();
        double var76 = this.killEffectRenderSpec.height();
        IGroundHeightSampler var80 = this.killEffectRenderSpec.ground();
        if (Double.compare(arg0.computeDoubleMetric(), 1.0E-6) > 0) {
            var168 = arg0.computeDoubleMetric();
        } else {
            var168 = 1.0;
        }
        double var34 = var168;
        double[] var86 = arg0.computeVector1();
        double var89 = computeDoubleMetric(var72, var76);
        double var92 = var80.computeDoubleMetric(0.0, 0.0);
        int var96 = ColorMathUtils.computeColorOrIndex7(arg0.counterValue1, 0.55);
        int var98 = arg0.counterValue;
        this.drawGeometryPart(arg0, var80, var89, var61, var96, var98);
        var43 = var92;
        var45 = 0;
        while (true) {
            if (var45 >= 4) break;
            double var105 = computeDoubleMetric(var45, var61);
            double var108 = computeDoubleMetric2(var45, var61);
            double var260 = GeometryPrimitives.computeDoubleMetric(((var105 - 0.45) / 0.55));
            double var204 = (1.0 - var260);
            double var112 = (var108 * var204);
            int var178 = var45;
            double[] var115 = this.computeVector(var178, var61, var105, var89, var92);
            if (var45 == 0) {
                if (Double.compare(var112, 0.02) > 0) {
                    var43 = var115[0];
                }
                if (Double.compare(var112, 0.01) >= 0) {
                    if (Double.compare(var115[1], 0.001) < 0) {
                    } else {
                        int var133 = (this.killEffectRenderSpec.seed() * 47);
                        int var183 = (var45 * 13);
                        int var134 = (var133 + var183);
                        if (Double.compare(GeometryPrimitives.computeDoubleMetric((var134 + 4)), 0.5) < 0) {
                            var163 = -1.0;
                        } else {
                            var163 = 1.0;
                        }
                        double var54 = var163;
                        double var295 = GeometryPrimitives.computeDoubleMetric((var134 + 5));
                        double var266 = (0.22 * var295);
                        double var219 = (0.12 + var266);
                        double var142 = (var54 * var219);
                        double var225 = GeometryPrimitives.computeDoubleMetric((var105 / 0.5));
                        double var144 = (var142 * var225);
                        double var150 = (GeometryPrimitives.computeDoubleMetric((var134 + 6)) * 6.283185307179586);
                        double var272 = GeometryPrimitives.computeDoubleMetric((var134 + 7));
                        double var235 = (((1.5 + var272) * 6.283185307179586) * var61);
                        double var152 = (var150 + var235);
                        CosmeticDrawBuilder var155 = arg0.drawGeometryPart();
                        double var187 = var115[2];
                        double var247 = var115[0];
                        double var283 = var115[3];
                        CosmeticDrawBuilder var157 = arg0.drawGeometryPart(var187, var247, var283);
                        int var238 = var45;
                        double var190 = this.computeDoubleMetric3(var238, var61);
                        CosmeticDrawBuilder var159 = var157.drawGeometryPart2(var190).drawGeometryPart4(var144);
                        double var240 = var115[1];
                        double var315 = ((var115[0] - var92) - 0.03);
                        this.drawGeometryPart(arg0, var240, var112, var152, var34, var315, var96, var98);
                        CosmeticDrawBuilder var162 = arg0.drawGeometryPart1();
                    }
                    var45 = (var45 + 1);
                }
            }
        }
        double var17 = var43;
        this.drawGeometryPart(arg0, var86, var34, var89, var92, var17, var61, var96, var98);
        this.drawGeometryPart(arg0, var86, var34, var89, var92, var65, var61, var96);
    }

    private static double[][] computeMatrix(double[] arg0) {
        double[] var26 = null;
        int var27 = 0;
        if (arg0 == null) {
            var26 = new double[3];
            var26[0] = 0.0;
            var26[1] = 0.0;
            var26[2] = -1.0;
        } else {
            var26 = GeometryPrimitives.computeVector(arg0);
        }
        double[] var8 = var26;
        if (Double.compare(Math.abs(var8[1]), 0.97) > 0) {
            var27 = new double[3];
            var27[0] = 1.0;
            var27[1] = 0.0;
            var27[2] = 0.0;
        } else {
            var27 = new double[3];
            var27[0] = 0.0;
            var27[1] = 1.0;
            var27[2] = 0.0;
        }
        double[] var21 = GeometryPrimitives.computeVector(GeometryPrimitives.computeVector1(var27, var8));
        double[][] var23 = new double[3][];
        var23[0] = var21;
        double[] var47 = GeometryPrimitives.computeVector1(var8, var21);
        var23[1] = var47;
        var23[2] = var8;
        return var23;
    }

    private void drawGeometryPart(CosmeticDrawBuilder arg0, double arg1, double arg2, double arg3, double arg4, double arg5, int arg6, int arg7) {
        int var66 = 0;
        int var69 = 0;
        double[] var72 = arg0.computeVector1();
        double var77 = ((0.05 * arg1) + 0.01);
        double var83 = ((0.22 * arg1) + 0.03);
        double var89 = ((0.7 * var77) + 0.012);
        double var192 = (arg1 - var77);
        double var93 = Math.max(0.0, var192);
        double var97 = (arg1 + var77);
        double var285 = (var97 + var83);
        IGradientColorSampler var336 = (p0, p1) -> AscendKillRenderer.lambda$a(var97, var83, arg2, var72, arg4, arg7, arg6, p0, p1);
        GeometryPrimitives.drawGeometryPart(arg0, 0.0, var97, var285, 24, var336);
        IGradientColorSampler var339 = (p0, p1) -> AscendKillRenderer.lambda$a(var93, var97, arg3, arg2, var72, arg4, arg6, arg7, p0, p1);
        GeometryPrimitives.drawGeometryPart(arg0, 0.0, var93, var97, 24, var339);
        double var198 = (0.4 * var77);
        double var103 = (arg1 + var198);
        double var169 = -(var89);
        IGradientColorSampler var360 = (p0, p1) -> AscendKillRenderer.lambda$a(arg3, arg2, var72, var103, arg4, arg6, arg7, p0, p1);
        GeometryPrimitives.drawGeometryPart(arg0, var169, var89, var103, var103, 24, var360, false, false);
        double[] var245 = GeometryPrimitives.computeVector(0.0, 0.0, 0.0);
        double[] var257 = GeometryPrimitives.computeVector(0.0, 1.0, 0.0);
        double var201 = arg0.computeDoubleMetric(var245, var257);
        double var108 = (1.0 - var201);
        double var262 = ((0.2 * var108) * var108);
        double var205 = (0.07 + var262);
        double var112 = (arg2 * var205);
        double var208 = KillEffectMathUtils.computeDoubleMetric(var72, 0.0, 0.0, 0.0, arg4);
        double var114 = (var112 * var208);
        if (Double.compare(var114, 0.01) > 0) {
            if (Double.compare(arg5, 0.05) > 0) {
                double[][] var123 = computeMatrix(var72);
                double var128 = Math.min((1.05 * arg1), arg5);
                double[][] var131 = new double[15][];
                var66 = 0;
                while (true) {
                    if (var66 > 14) break;
                    double var150 = ((((double) (var66 % 14)) / 14.0) * 6.283185307179586);
                    double var156 = (Math.cos(var150) * var128);
                    double var162 = (Math.sin(var150) * var128);
                    int var179 = var66;
                    double var234 = (var123[0][0] * var156);
                    double var275 = (var123[1][0] * var162);
                    double var236 = (var234 + var275);
                    double var281 = (var123[0][1] * var156);
                    double var314 = (var123[1][1] * var162);
                    double var283 = (var281 + var314);
                    double var320 = (var123[0][2] * var156);
                    double var350 = (var123[1][2] * var162);
                    double var322 = (var320 + var350);
                    double[] var238 = GeometryPrimitives.computeVector(var236, var283, var322);
                    var131[var179] = var238;
                    var66 = (var66 + 1);
                }
                int var135 = ColorMathUtils.computeColorOrIndex5(arg6, var114);
                int var137 = ColorMathUtils.computeColorOrIndex5(arg7, 0.0);
                double[] var140 = GeometryPrimitives.computeVector(0.0, 0.0, 0.0);
                var69 = 0;
                while (true) {
                    if (var69 >= 14) break;
                    int var248 = var69;
                    double[] var221 = var131[var248];
                    int var267 = (var69 + 1);
                    double[] var250 = var131[var267];
                    arg0.applyStep(var140, var221, var250, var135, var137, var137);
                    var69 = (var69 + 1);
                }
            }
            return;
        }
    }

    private void drawGeometryPart(CosmeticDrawBuilder arg0, double[] arg1, double arg2, double arg3, double arg4, double arg5, double arg6, int arg7, int arg8) {
        double var78 = GeometryPrimitives.computeDoubleMetric((arg6 / 0.06));
        double var42 = (0.85 * var78);
        double var109 = GeometryPrimitives.computeDoubleMetric(((arg6 - 0.62) / 0.33));
        double var82 = (1.0 - var109);
        double var44 = (var42 * var82);
        double var122 = this.killEffectRenderSpec.height();
        double var113 = (0.5 * var122);
        double var85 = KillEffectMathUtils.computeDoubleMetric(arg1, 0.0, var113, 0.0, arg2);
        double var46 = (var44 * var85);
        double var50 = (arg4 + 0.03);
        if (Double.compare(var46, 0.01) >= 0) {
            double var93 = (var50 + 0.1);
            if (Double.compare(arg5, var93) <= 0) {
            }
        }
        return;
        int var61 = ColorMathUtils.computeColorOrIndex(arg7, arg8, 0.45);
        double var68 = (0.2 * var46);
        int var62 = ColorMathUtils.computeColorOrIndex5(var61, var68);
        double var72 = (0.035 * var46);
        int var64 = ColorMathUtils.computeColorOrIndex5(arg8, var72);
        double var117 = (0.7 * arg3);
        double var130 = (0.45 * arg3);
        IGradientColorSampler var135 = (p0, p1) -> AscendKillRenderer.lambda$a(var62, var64, p0, p1);
        GeometryPrimitives.drawGeometryPart(arg0, var50, arg5, var117, var130, 12, var135, false, false);
    }

    private void drawGeometryPart(CosmeticDrawBuilder arg0, IGroundHeightSampler arg1, double arg2, double arg3, int arg4, int arg5) {
        double var35 = GeometryPrimitives.computeDoubleMetric((arg3 / 0.04));
        double var87 = GeometryPrimitives.computeDoubleMetric(((arg3 - 0.3) / 0.35));
        double var61 = (1.0 - var87);
        double var37 = (var35 * var61);
        if (Double.compare(var37, 0.01) < 0) {
            return;
        }
        double var111 = (arg3 / 0.4);
        double var91 = Math.min(1.0, var111);
        double var69 = Math.pow((1.0 - var91), 2.0);
        double var44 = (1.0 - var69);
        double var97 = (0.45 * var44);
        double var73 = (0.85 + var97);
        double var48 = (arg2 * var73);
        double var54 = ((0.05 * arg2) + 0.02);
        double var117 = (var48 - var54);
        double var101 = Math.max(0.0, var117);
        double var121 = (var48 + var54);
        IGradientColorSampler var133 = (p0, p1) -> AscendKillRenderer.lambda$b(arg4, arg5, var37, p0, p1);
        KillEffectMathUtils.drawGeometryPart(arg0, arg1, 0.04, var101, var121, 24, var133);
        double var105 = (1.25 * arg2);
        IGradientColorSampler var126 = (p0, p1) -> AscendKillRenderer.lambda$a(arg4, arg5, var37, p0, p1);
        KillEffectMathUtils.drawGeometryPart(arg0, arg1, 0.03, var105, 16, var126);
    }

    private void drawGeometryPart(CosmeticDrawBuilder arg0, double[] arg1, double arg2, double arg3, double arg4, double arg5, double arg6, int arg7) {
        int var33 = 0;
        double var292 = 0.0;
        double var59 = this.killEffectRenderSpec.height();
        double var67 = KillEffectMathUtils.computeDoubleMetric(Math.sqrt((arg3 / 0.67)), 0.6, 3.5);
        var33 = 0;
        while (true) {
            if (var33 >= 20) break;
            int var74 = (this.killEffectRenderSpec.seed() * 53);
            int var159 = var33;
            int var160 = (var159 * 17);
            int var75 = (var74 + var160);
            int var77 = (var33 % 4);
            double var79 = computeDoubleMetric(var77, arg6);
            int var443 = (var75 + 1);
            double var444 = GeometryPrimitives.computeDoubleMetric(var443);
            double var325 = (0.3 * var444);
            double var191 = (0.03 + var325);
            double var81 = (var79 - var191);
            if (Double.compare(var81, 0.0) <= 0) {
            } else {
                double[] var87 = this.computeVector(var77, arg6, var81, arg3, arg4);
                double var92 = (GeometryPrimitives.computeDoubleMetric((var75 + 2)) * 6.283185307179586);
                int var200 = (var75 + 3);
                double var201 = GeometryPrimitives.computeDoubleMetric(var200);
                int var203 = Double.compare(var201, 0.5);
                if (var203 < 0) {
                    var292 = -1.0;
                } else {
                    var292 = 1.0;
                }
                double var515 = GeometryPrimitives.computeDoubleMetric((var75 + 4));
                double var450 = (0.4 * var515);
                double var333 = (0.4 + var450);
                double var204 = (var292 * var333);
                double var206 = (var204 * 6.283185307179586);
                double var208 = (var206 * arg6);
                double var94 = (var92 + var208);
                double var341 = GeometryPrimitives.computeDoubleMetric((var75 + 5));
                double var212 = (0.8 * var341);
                double var98 = (0.25 + var212);
                double var215 = var87[1];
                double var100 = (var98 * var215);
                double var103 = var87[2];
                double var345 = Math.cos(var94);
                double var219 = (var100 * var345);
                double var105 = (var103 + var219);
                double var108 = var87[3];
                double var349 = Math.sin(var94);
                double var223 = (var100 * var349);
                double var110 = (var108 + var223);
                double var113 = var87[0];
                int var226 = (var75 + 6);
                double var227 = GeometryPrimitives.computeDoubleMetric(var226);
                double var229 = (var227 - 0.65);
                double var231 = (var229 * 0.1);
                double var233 = (var231 * var59);
                double var115 = (var113 + var233);
                double var545 = GeometryPrimitives.computeDoubleMetric((var75 + 7));
                double var519 = (6.0 * var545);
                double var454 = (5.0 + var519);
                double var359 = (arg5 * var454);
                double var523 = GeometryPrimitives.computeDoubleMetric((var75 + 8));
                double var458 = (6.283185307179586 * var523);
                double var363 = Math.sin((var359 + var458));
                double var237 = (0.5 * var363);
                double var121 = Math.pow((0.5 + var237), 2.0);
                double var367 = (0.75 * var121);
                double var243 = (0.25 + var367);
                double var125 = (0.95 * var243);
                double var247 = (var81 / 0.1);
                double var249 = GeometryPrimitives.computeDoubleMetric(var247);
                double var127 = (var125 * var249);
                double var377 = GeometryPrimitives.computeDoubleMetric(((var81 - 0.6) / 0.4));
                double var253 = (1.0 - var377);
                double var129 = (var127 * var253);
                double var256 = computeDoubleMetric2(var77, arg6);
                double var131 = (var129 * var256);
                double var381 = (arg6 - 0.8);
                double var383 = (var381 / 0.2);
                double var385 = GeometryPrimitives.computeDoubleMetric(var383);
                double var260 = (1.0 - var385);
                double var133 = (var131 * var260);
                double var263 = KillEffectMathUtils.computeDoubleMetric(arg1, var105, var115, var110, arg2);
                double var135 = (var133 * var263);
                int var139 = Double.compare(var135, 0.04);
                if (var139 < 0) {
                } else {
                    double var142 = (0.045 * var67);
                    int var471 = (var75 + 9);
                    double var472 = GeometryPrimitives.computeDoubleMetric(var471);
                    double var389 = (0.8 * var472);
                    double var271 = (0.6 + var389);
                    double var144 = (var142 * var271);
                    double var393 = (0.4 * var81);
                    double var275 = (1.0 - var393);
                    double var146 = (var144 * var275);
                    double var150 = (var146 * 1.7);
                    int var170 = arg0.counterValue;
                    int var396 = (var75 + 10);
                    double var397 = GeometryPrimitives.computeDoubleMetric(var396);
                    double var281 = (0.25 * var397);
                    int var153 = ColorMathUtils.computeColorOrIndex(arg7, var170, var281);
                    int var154 = ColorMathUtils.computeColorOrIndex5(var153, var135);
                    double var175 = (var105 - var146);
                    double[] var177 = GeometryPrimitives.computeVector(var175, var115, var110);
                    double var401 = (var115 - var150);
                    double[] var285 = GeometryPrimitives.computeVector(var105, var401, var110);
                    double[] var308 = GeometryPrimitives.computeVector((var105 + var146), var115, var110);
                    double var482 = (var115 + var150);
                    double[] var405 = GeometryPrimitives.computeVector(var105, var482, var110);
                    arg0.applyStep(var177, var285, var308, var405, var154);
                    double var430 = (var110 - var146);
                    double[] var180 = GeometryPrimitives.computeVector(var105, var115, var430);
                    double var408 = (var115 - var150);
                    double[] var288 = GeometryPrimitives.computeVector(var105, var408, var110);
                    double var509 = (var110 + var146);
                    double[] var313 = GeometryPrimitives.computeVector(var105, var115, var509);
                    double var490 = (var115 + var150);
                    double[] var412 = GeometryPrimitives.computeVector(var105, var490, var110);
                    arg0.applyStep(var180, var288, var313, var412, var154);
                    double var183 = (var105 - var150);
                    double[] var185 = GeometryPrimitives.computeVector(var183, var115, var110);
                    double var494 = (var110 - var146);
                    double[] var291 = GeometryPrimitives.computeVector(var105, var115, var494);
                    double[] var322 = GeometryPrimitives.computeVector((var105 + var150), var115, var110);
                    double var537 = (var110 + var146);
                    double[] var417 = GeometryPrimitives.computeVector(var105, var115, var537);
                    int var442 = ColorMathUtils.computeColorOrIndex(var154, 0.6);
                    arg0.applyStep(var185, var291, var322, var417, var442);
                }
                var33 = (var33 + 1);
            }
        }
    }

    static {
        double[] var4 = new double[4];
        var4[0] = 0.8;
        var4[1] = 0.64;
        var4[2] = 0.48;
        var4[3] = 0.32;
        coordsVector = var4;
        double[] var6 = new double[4];
        var6[0] = 3.0;
        var6[1] = 2.83;
        var6[2] = 2.67;
        var6[3] = 2.5;
        coordsVector1 = var6;
    }

}
