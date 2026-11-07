package need.clarity.render;

import java.util.Arrays;

public final class SwingEngine {
    public static final int X = 0;
    public static final int Y = 1;
    public static final int Z = 2;
    public static final int YAW = 3;
    public static final int ROLL = 4;
    public static final int CHOP = 5;
    public static final int FLIP = 6;
    public static final int N = 7;
    public static final String[] MODES;
    private static final float COMBO_WINDOW = 0.75F;
    private static final float RETURN_TIME = 0.35F;
    public static final float[] pose;
    private static final float[] vel;
    private static final float[] target;
    private static final float[] from;
    private static final float[] to;
    private static String mode;
    private static float lastP;
    private static int combo;
    private static float sinceEnd;
    private static boolean swinging;
    private static float momentum;
    private static float spin;
    private static float whirl;
    private static float time;

    private SwingEngine() {
    }

    public static void step(String arg0, float arg1, float arg2, float arg3) {
        int var15 = 0;
        int var16 = 0;
        int var36 = 0;
        int var185 = 0;
        float var65 = 0.0F;
        float var67 = 0.0F;
        int var92 = 0;
        int var93 = 0;
        String var5 = mode;
        if (!arg0.equals(var5)) {
            reset();
            mode = arg0;
        }
        float var95 = Math.min(arg2, 0.05F);
        float var21 = Math.max(0.0F, var95);
        time = (time + var21);
        if (Float.compare(arg1, 0.001F) > 0) {
            if (Float.compare(lastP, 0.001F) > 0) {
            }
        }
        float var100 = (lastP - 0.25F);
        if (Float.compare(arg1, var100) < 0) {
            if (Float.compare(arg1, 0.0F) > 0) {
                var92 = 1;
            }
        } else {
            var92 = 0;
        }
        int var9 = var92;
        if (var9 != 0) {
            if (!swinging) {
                if (Float.compare(sinceEnd, 0.75F) < 0) {
                }
            }
            var36 = 1;
            var36 = 0;
            if (var36 != 0) {
                var93 = (combo + 1);
            } else {
                var93 = 0;
            }
            combo = var93;
            swinging = true;
            float[] var40 = pose;
            float[] var147 = from;
            System.arraycopy(var40, 0, var147, 0, 7);
            String var105 = mode;
            if ("Momentum".equals(var105)) {
                float var107 = (momentum + 1.0F);
                momentum = Math.min(6.0F, var107);
            }
            String var108 = mode;
            if ("Whirlwind".equals(var108)) {
                whirl = (whirl + 90.0F);
            }
            if (swinging) {
                if (Float.compare(arg1, 0.001F) <= 0) {
                    if (var9 == 0) {
                        swinging = false;
                        sinceEnd = 0.0F;
                    }
                    if (!swinging) {
                        sinceEnd = (sinceEnd + var21);
                    }
                    lastP = arg1;
                    float var170 = (0.5F * arg3);
                    float var150 = (0.75F + var170);
                    compute(arg1, var21, var150);
                    var185 = 5;
                    while (true) {
                        if (var185 > 6) break;
                        while (true) {
                            float[] var82 = pose;
                            int var136 = var185;
                            float var83 = var82[var136];
                            float[] var137 = target;
                            int var163 = var185;
                            float var138 = var137[var163];
                            if (Float.compare((var83 - var138), 180.0F) <= 0) break;
                            float[] var86 = pose;
                            int var140 = var185;
                            float var165 = (var86[var140] - 360.0F);
                            var86[var140] = var165;
                        }
                        while (true) {
                            float[] var87 = pose;
                            int var141 = var185;
                            float var88 = var87[var141];
                            float[] var142 = target;
                            int var166 = var185;
                            float var143 = var142[var166];
                            if (Float.compare((var88 - var143), -180.0F) >= 0) break;
                            float[] var91 = pose;
                            int var145 = var185;
                            float var168 = (var91[var145] + 360.0F);
                            var91[var145] = var168;
                        }
                        var185 = (var185 + 1);
                    }
                    String var114 = mode;
                    boolean var63 = "Smooth".equals(var114);
                    if (var63) {
                        var65 = 20.0F;
                    } else {
                        var65 = 34.0F;
                    }
                    float var11 = var65;
                    if (var63) {
                        var67 = 0.55F;
                    } else {
                        var67 = 0.82F;
                    }
                    float var12 = var67;
                    int var121 = ((int) Math.ceil(((double) (var21 * 240.0F))));
                    int var69 = Math.max(1, var121);
                    float var123 = ((float) var69);
                    float var71 = (var21 / var123);
                    var15 = 0;
                    while (true) {
                        if (var15 >= var69) break;
                        var16 = 0;
                        while (true) {
                            if (var16 >= 7) break;
                            float var77 = (var11 * var11);
                            float[] var127 = target;
                            int var152 = var16;
                            float var128 = var127[var152];
                            float[] var153 = pose;
                            int var171 = var16;
                            float var154 = var153[var171];
                            float var129 = (var128 - var154);
                            float var78 = (var77 * var129);
                            float var132 = ((2.0F * var12) * var11);
                            float[] var157 = vel;
                            int var172 = var16;
                            float var158 = var157[var172];
                            float var133 = (var132 * var158);
                            float var79 = (var78 - var133);
                            float[] var80 = vel;
                            int var134 = var16;
                            float var159 = var80[var134];
                            float var174 = (var79 * var71);
                            float var160 = (var159 + var174);
                            var80[var134] = var160;
                            float[] var81 = pose;
                            int var135 = var16;
                            float var161 = var81[var135];
                            float[] var175 = vel;
                            int var182 = var16;
                            float var177 = (var175[var182] * var71);
                            float var162 = (var161 + var177);
                            var81[var135] = var162;
                            var16 = (var16 + 1);
                        }
                        var15 = (var15 + 1);
                    }
                    return;
                }
            }
        }
    }

    public static void reset() {
        Arrays.fill(pose, 0.0F);
        Arrays.fill(vel, 0.0F);
        Arrays.fill(target, 0.0F);
        Arrays.fill(from, 0.0F);
        Arrays.fill(to, 0.0F);
        combo = 0;
        sinceEnd = 99.0F;
        swinging = false;
        lastP = 0.0F;
        momentum = 0.0F;
        spin = 0.0F;
        whirl = 0.0F;
    }

    public static boolean handles(String arg0) {
        int var5 = 0;
        String[] var1 = MODES;
        int var8 = var1.length;
        var5 = 0;
        while (true) {
            if (var5 < var8) {
                int var17 = var5;
                if (var1[var17].equals(arg0)) {
                    return true;
                }
            }
            return false;
            var5 = (var5 + 1);
        }
    }

    private static float clamp01(float arg0) {
        float var6 = 0.0F;
        if (Float.compare(arg0, 0.0F) < 0) {
            var6 = 0.0F;
        } else {
            if (Float.compare(arg0, 1.0F) > 0) {
                var6 = 1.0F;
            } else {
                var6 = arg0;
            }
            return var6;
        }
    }

    private static float smooth(float arg0) {
        float var5 = clamp01(arg0);
        float var7 = (var5 * var5);
        float var11 = (2.0F * var5);
        float var10 = (3.0F - var11);
        return (var7 * var10);
    }

    private static float outCubic(float arg0) {
        float var7 = clamp01(arg0);
        float var4 = (1.0F - var7);
        float var10 = ((var4 * var4) * var4);
        return (1.0F - var10);
    }

    private static float inOut(float arg0) {
        float var13 = 0.0F;
        float var6 = clamp01(arg0);
        if (Float.compare(var6, 0.5F) < 0) {
            var13 = (((4.0F * var6) * var6) * var6);
        } else {
            float var25 = (((float) Math.pow(((double) ((-2.0F * var6) + 2.0F)), 3.0)) / 2.0F);
            var13 = (1.0F - var25);
        }
        return var13;
    }

    private static float outBack(float arg0) {
        float var6 = (clamp01(arg0) - 1.0F);
        float var15 = ((((1.9F + 1.0F) * var6) * var6) * var6);
        float var9 = (1.0F + var15);
        float var18 = ((1.9F * var6) * var6);
        return (var9 + var18);
    }

    private static float bump(float arg0) {
        double var10 = ((double) clamp01(arg0));
        return ((float) Math.sin((3.141592653589793 * var10)));
    }

    private static float sinD(float arg0) {
        return ((float) Math.sin(Math.toRadians(((double) arg0))));
    }

    private static float cosD(float arg0) {
        return ((float) Math.cos(Math.toRadians(((double) arg0))));
    }

    private static void set(float[] arg0, float arg1, float arg2, float arg3, float arg4, float arg5, float arg6, float arg7) {
        arg0[0] = arg1;
        arg0[1] = arg2;
        arg0[2] = arg3;
        arg0[3] = arg4;
        arg0[4] = arg5;
        arg0[5] = arg6;
        arg0[6] = arg7;
    }

    private static void blend(float arg0) {
        int var7 = 0;
        var7 = 0;
        while (true) {
            if (var7 >= 7) break;
            float[] var9 = target;
            int var10 = var7;
            float[] var3 = from;
            int var4 = var7;
            float var11 = var3[var4];
            float[] var13 = to;
            int var5 = var7;
            float var14 = var13[var5];
            float[] var17 = from;
            int var6 = var7;
            float var18 = var17[var6];
            float var16 = ((var14 - var18) * arg0);
            float var12 = (var11 + var16);
            var9[var10] = var12;
            var7 = (var7 + 1);
        }
    }

    private static boolean settle() {
        int var6 = 0;
        if (!swinging) {
            if (Float.compare(sinceEnd, 0.75F) < 0) {
            }
        }
        return false;
        float var13 = smooth(((sinceEnd - 0.75F) / 0.35F));
        var6 = 0;
        while (true) {
            if (var6 >= 7) break;
            float[] var21 = target;
            int var27 = var6;
            float[] var2 = to;
            int var3 = var6;
            float var28 = var2[var3];
            float var31 = (1.0F - var13);
            float var29 = (var28 * var31);
            var21[var27] = var29;
            var6 = (var6 + 1);
        }
        if (Float.compare(var13, 1.0F) >= 0) {
            combo = 0;
            Arrays.fill(to, 0.0F);
        }
        return true;
    }

    private static void compute(float arg0, float arg1, float arg2) {
        int var16 = 0;
        float var47 = 0.0F;
        float var90 = 0.0F;
        int var603 = 0;
        float var132 = 0.0F;
        float var167 = 0.0F;
        float var380 = 0.0F;
        double var20 = ((double) arg0);
        double var22 = Math.sqrt(var20);
        float var24 = ((float) var22);
        boolean var25 = swinging;
        if (var25) {
            var167 = arg0;
        } else {
            var167 = 1.0F;
        }
        float var13 = var167;
        float var27 = bump(arg0);
        String var28 = mode;
        var16 = -1;
        int var31 = var28.hashCode();
        switch (var31) {
            case -1814666802 -> {}
            case -1050807228 -> {}
            case -296651592 -> {}
            case -197379029 -> {}
            case -33440659 -> {}
            case 2587234 -> {}
            case 79973746 -> {}
            case 299651802 -> {}
            case 1295112555 -> {}
            default -> {}
        }
        boolean var147 = var28.equals("Smooth");
        if (var147) {
            var16 = 0;
            boolean var33 = var28.equals("Slice");
            if (var33) {
                var16 = 2;
                boolean var156 = var28.equals("Stab");
                if (var156) {
                    var16 = 1;
                    boolean var165 = var28.equals("Heavy Charge");
                    if (var165) {
                        var16 = 6;
                        boolean var162 = var28.equals("Blade Dance");
                        if (var162) {
                            var16 = 5;
                            boolean var144 = var28.equals("Windmill");
                            if (var144) {
                                var16 = 3;
                                boolean var159 = var28.equals("Momentum");
                                if (var159) {
                                    var16 = 7;
                                    boolean var150 = var28.equals("Combo Slash");
                                    if (var150) {
                                        var16 = 4;
                                        boolean var153 = var28.equals("Whirlwind");
                                        if (var153) {
                                            var16 = 8;
                                        }
                                        int var35 = var16;
                                        switch (var35) {
                                            case 0 -> {}
                                            case 1 -> {}
                                            case 2 -> {}
                                            case 3 -> {}
                                            case 4 -> {}
                                            case 5 -> {}
                                            case 6 -> {}
                                            case 7 -> {}
                                            case 8 -> {}
                                            default -> {}
                                        }
                                        float var210 = momentum;
                                        boolean var370 = swinging;
                                        if (var370) {
                                            var380 = 0.4F;
                                        } else {
                                            var380 = 1.3F;
                                        }
                                        float var301 = (arg1 * var380);
                                        float var211 = (var210 - var301);
                                        float var93 = Math.max(0.0F, var211);
                                        momentum = var93;
                                        float var94 = spin;
                                        float var212 = momentum;
                                        double var213 = ((double) var212);
                                        double var215 = Math.pow(var213, 1.35);
                                        float var217 = ((float) var215);
                                        float var218 = (var217 * 120.0F);
                                        float var219 = (var218 * arg1);
                                        float var95 = (var94 - var219);
                                        spin = var95;
                                        float var96 = momentum;
                                        int var97 = Float.compare(var96, 0.05F);
                                        if (var97 < 0) {
                                            float var98 = spin;
                                            float var99 = (var98 / 360.0F);
                                            int var100 = Math.round(var99);
                                            float var101 = ((float) var100);
                                            float var102 = (var101 * 360.0F);
                                            spin = var102;
                                        }
                                        float var103 = spin;
                                        int var104 = Float.compare(var103, -36000.0F);
                                        if (var104 < 0) {
                                            float var105 = spin;
                                            float var106 = (var105 + 36000.0F);
                                            spin = var106;
                                        }
                                        float var107 = momentum;
                                        float var108 = (var107 / 6.0F);
                                        float var599 = var108;
                                        float[] var109 = target;
                                        float var227 = (-0.06F * var599);
                                        float var306 = (0.1F * var27);
                                        float var228 = (var227 - var306);
                                        float var308 = (0.05F * var599);
                                        float var431 = var27;
                                        float var376 = (0.04F * var431);
                                        float var309 = (var308 + var376);
                                        float var432 = var599;
                                        float var378 = (-0.1F * var432);
                                        float var433 = 0.12F;
                                        float var501 = var27;
                                        float var434 = (var433 * var501);
                                        float var379 = (var378 - var434);
                                        float var435 = -10.0F;
                                        float var502 = var599;
                                        float var436 = (var435 * var502);
                                        float var503 = 20.0F;
                                        float var548 = var599;
                                        float var504 = (var503 * var548);
                                        float var549 = -20.0F;
                                        float var587 = var599;
                                        float var550 = (var549 * var587);
                                        float var588 = arg2;
                                        float var551 = (var550 * var588);
                                        float var589 = 30.0F;
                                        float var590 = (var589 * var27);
                                        float var552 = (var551 - var590);
                                        float var591 = spin;
                                        set(var109, var228, var309, var379, var436, var504, var552, var591);
                                        boolean var36 = settle();
                                        if (var36) {
                                            return;
                                        }
                                        int var37 = combo;
                                        int var38 = (var37 % 3);
                                        switch (var38) {
                                            case 0 -> {}
                                            case 1 -> {}
                                            default -> {}
                                        }
                                        float[] var48 = to;
                                        float var175 = (0.12F * arg2);
                                        float var466 = arg2;
                                        float var402 = (42.0F * var466);
                                        float var467 = -38.0F;
                                        float var522 = -18.0F;
                                        float var563 = 0.0F;
                                        set(var48, var175, 0.1F, -0.06F, var402, var467, var522, var563);
                                        float[] var49 = to;
                                        float var177 = (-0.26F * arg2);
                                        float var468 = arg2;
                                        float var404 = (-38.0F * var468);
                                        float var469 = 32.0F;
                                        float var523 = -68.0F;
                                        float var564 = arg2;
                                        float var524 = (var523 * var564);
                                        float var565 = 0.0F;
                                        set(var49, var177, -0.04F, -0.08F, var404, var469, var524, var565);
                                        float[] var40 = to;
                                        float var520 = (-100.0F * arg2);
                                        float var562 = -360.0F;
                                        set(var40, -0.05F, -0.06F, -0.18F, 0.0F, 0.0F, var520, var562);
                                        if (var38 == 2) {
                                            var47 = inOut(var13);
                                        } else {
                                            var47 = outBack(var13);
                                        }
                                        blend(var47);
                                        float[] var43 = target;
                                        float var259 = var43[2];
                                        float var329 = (0.1F * var27);
                                        float var260 = (var259 - var329);
                                        var43[2] = var260;
                                        if (var38 == 2) {
                                            float[] var45 = target;
                                            float var261 = var45[1];
                                            float var464 = arg0;
                                            float var521 = 1.6F;
                                            float var465 = (var464 * var521);
                                            float var399 = Math.min(1.0F, var465);
                                            float var400 = bump(var399);
                                            float var331 = (0.12F * var400);
                                            float var262 = (var261 + var331);
                                            var45[1] = var262;
                                            boolean var65 = settle();
                                            if (var65) {
                                                return;
                                            }
                                            int var66 = combo;
                                            int var67 = (var66 % 4);
                                            switch (var67) {
                                                case 0 -> {}
                                                case 1 -> {}
                                                case 2 -> {}
                                                default -> {}
                                            }
                                            float[] var72 = to;
                                            float var193 = (-0.3F * arg2);
                                            float var416 = -50.0F;
                                            float var481 = arg2;
                                            float var417 = (var416 * var481);
                                            float var482 = 20.0F;
                                            float var535 = -55.0F;
                                            float var575 = -360.0F;
                                            set(var72, var193, 0.0F, -0.08F, var417, var482, var535, var575);
                                            float var74 = outBack(var13);
                                            blend(var74);
                                            float[] var79 = to;
                                            float var420 = 0.0F;
                                            float var484 = 0.0F;
                                            float var537 = -90.0F;
                                            float var577 = arg2;
                                            float var538 = (var537 * var577);
                                            float var578 = -1080.0F;
                                            set(var79, 0.0F, 0.0F, 0.0F, var420, var484, var538, var578);
                                            float var81 = inOut(var13);
                                            blend(var81);
                                            float[] var82 = target;
                                            float var286 = var82[1];
                                            float var421 = var27;
                                            float var352 = (0.1F * var421);
                                            float var287 = (var286 + var352);
                                            var82[1] = var287;
                                            float[] var83 = target;
                                            float var288 = var83[2];
                                            float var422 = var27;
                                            float var354 = (0.15F * var422);
                                            float var289 = (var288 - var354);
                                            var83[2] = var289;
                                            float[] var75 = to;
                                            float var418 = 15.0F;
                                            float var483 = 10.0F;
                                            float var536 = -25.0F;
                                            float var576 = -360.0F;
                                            set(var75, 0.02F, 0.04F, -0.04F, var418, var483, var536, var576);
                                            float var77 = inOut(var13);
                                            blend(var77);
                                            float[] var78 = target;
                                            float var283 = var78[0];
                                            float var419 = var27;
                                            float var349 = (0.12F * var419);
                                            float var284 = (var283 - var349);
                                            var78[0] = var284;
                                            float[] var68 = to;
                                            float var413 = -8.0F;
                                            float var480 = 80.0F;
                                            float var534 = 10.0F;
                                            float var574 = -360.0F;
                                            set(var68, -0.05F, 0.04F, -0.1F, var413, var480, var534, var574);
                                            float var70 = outBack(var13);
                                            blend(var70);
                                            float[] var71 = target;
                                            float var278 = var71[2];
                                            float var414 = var27;
                                            float var344 = (0.3F * var414);
                                            float var415 = arg2;
                                            float var345 = (var344 * var415);
                                            float var279 = (var278 - var345);
                                            var71[2] = var279;
                                            float var141 = inOut(arg0);
                                            float var601 = var141;
                                            float[] var142 = target;
                                            float var323 = (0.55F * var601);
                                            float var248 = (0.15F - var323);
                                            float var249 = (var248 * var27);
                                            float var250 = (var249 * arg2);
                                            float var327 = (0.05F * var27);
                                            float var459 = var27;
                                            float var396 = (-0.18F * var459);
                                            float var460 = 55.0F;
                                            float var514 = 120.0F;
                                            float var558 = var601;
                                            float var515 = (var514 * var558);
                                            float var461 = (var460 - var515);
                                            float var516 = var27;
                                            float var462 = (var461 * var516);
                                            float var517 = arg2;
                                            float var463 = (var462 * var517);
                                            float var518 = -45.0F;
                                            float var559 = var27;
                                            float var519 = (var518 * var559);
                                            float var560 = -35.0F;
                                            float var595 = var27;
                                            float var561 = (var560 * var595);
                                            float var596 = 0.0F;
                                            set(var142, var250, var327, var396, var463, var519, var561, var596);
                                            int var85 = Float.compare(arg0, 0.35F);
                                            if (var85 < 0) {
                                                float var89 = (arg0 / 0.35F);
                                                var90 = outCubic(var89);
                                            } else {
                                                float var201 = (arg0 - 0.35F);
                                                float var202 = (var201 / 0.65F);
                                                float var203 = smooth(var202);
                                                var90 = (1.0F - var203);
                                            }
                                            float var598 = var90;
                                            float[] var87 = target;
                                            float var205 = (-0.08F * var598);
                                            float var294 = (0.06F * var598);
                                            float var423 = var598;
                                            float var357 = (-0.42F * var423);
                                            float var424 = arg2;
                                            float var358 = (var357 * var424);
                                            float var425 = -12.0F;
                                            float var485 = var598;
                                            float var426 = (var425 * var485);
                                            float var486 = 70.0F;
                                            float var539 = var598;
                                            float var487 = (var486 * var539);
                                            float var540 = 12.0F;
                                            float var579 = var598;
                                            float var541 = (var540 * var579);
                                            float var580 = 0.0F;
                                            set(var87, var205, var294, var358, var426, var487, var541, var580);
                                            float[] var64 = target;
                                            float var188 = (-0.12F * var27);
                                            float var276 = (0.1F * var27);
                                            float var341 = (-0.15F * var27);
                                            float var478 = -10.0F;
                                            float var531 = var27;
                                            float var479 = (var478 * var531);
                                            float var532 = -360.0F;
                                            float var571 = arg0;
                                            float var572 = inOut(var571);
                                            float var533 = (var532 * var572);
                                            float var573 = 0.0F;
                                            set(var64, var188, var276, var341, 0.0F, var479, var533, var573);
                                            boolean var111 = settle();
                                            if (var111) {
                                                return;
                                            }
                                            int var112 = combo;
                                            int var113 = (var112 % 4);
                                            int var600 = var113;
                                            if (var600 < 3) {
                                                int var116 = (var600 + 1);
                                                float var117 = ((float) var116);
                                                float[] var118 = to;
                                                float var234 = (-0.04F * var117);
                                                float var312 = (0.05F * var117);
                                                float var437 = var117;
                                                float var383 = (0.01F * var437);
                                                float var438 = -6.0F;
                                                float var505 = var117;
                                                float var439 = (var438 * var505);
                                                float var506 = 12.0F;
                                                float var553 = var117;
                                                float var507 = (var506 * var553);
                                                float var554 = arg2;
                                                float var508 = (var507 * var554);
                                                float var555 = 0.0F;
                                                float var592 = 0.0F;
                                                set(var118, var234, var312, var383, var439, var508, var555, var592);
                                                float var120 = outBack(var13);
                                                blend(var120);
                                                float[] var121 = target;
                                                float var313 = var121[5];
                                                float var440 = var27;
                                                float var385 = (14.0F * var440);
                                                float var314 = (var313 - var385);
                                                var121[5] = var314;
                                                float var123 = (0.0045F * var117);
                                                float var124 = (var123 * var117);
                                                float[] var125 = target;
                                                float var315 = var125[0];
                                                float var441 = time;
                                                float var509 = 61.0F;
                                                float var442 = (var441 * var509);
                                                double var443 = ((double) var442);
                                                double var445 = Math.sin(var443);
                                                float var447 = ((float) var445);
                                                float var387 = (var124 * var447);
                                                float var316 = (var315 + var387);
                                                var125[0] = var316;
                                                float[] var126 = target;
                                                float var317 = var126[1];
                                                float var448 = time;
                                                float var510 = 47.0F;
                                                float var449 = (var448 * var510);
                                                float var511 = 1.0F;
                                                float var450 = (var449 + var511);
                                                double var451 = ((double) var450);
                                                double var453 = Math.sin(var451);
                                                float var455 = ((float) var453);
                                                float var389 = (var124 * var455);
                                                float var318 = (var317 + var389);
                                                var126[1] = var318;
                                            } else {
                                                float[] var127 = to;
                                                float var456 = -6.0F;
                                                float var556 = -115.0F;
                                                float var593 = arg2;
                                                float var557 = (var556 * var593);
                                                float var594 = -90.0F;
                                                set(var127, -0.04F, -0.16F, -0.25F, var456, -12.0F, var557, var594);
                                                int var129 = Float.compare(var13, 0.45F);
                                                if (var129 < 0) {
                                                    float var131 = (var13 / 0.45F);
                                                    var132 = outCubic(var131);
                                                } else {
                                                    var132 = 1.0F;
                                                }
                                                blend(var132);
                                                float var134 = (var13 - 0.55F);
                                                float var135 = (var134 / 0.45F);
                                                float var136 = smooth(var135);
                                                float var602 = var136;
                                                var603 = 0;
                                                while (true) {
                                                    int var138 = var603;
                                                    if (var138 >= 7) break;
                                                    float[] var139 = target;
                                                    int var246 = var603;
                                                    float var320 = var139[var246];
                                                    float var457 = 0.75F;
                                                    float var513 = var602;
                                                    float var458 = (var457 * var513);
                                                    float var392 = (1.0F - var458);
                                                    float var321 = (var320 * var392);
                                                    var139[var246] = var321;
                                                    var603 = (var603 + 1);
                                                }
                                                float[] var91 = target;
                                                float var296 = bump(var24);
                                                float var208 = (-0.32F * var296);
                                                float var209 = (var208 * arg2);
                                                float var427 = 2.0F;
                                                float var360 = (var24 * var427);
                                                double var361 = ((double) var360);
                                                double var488 = 3.141592653589793;
                                                double var363 = (var361 * var488);
                                                double var365 = Math.sin(var363);
                                                float var367 = ((float) var365);
                                                float var299 = (0.2F * var367);
                                                float var428 = var27;
                                                float var369 = (-0.2F * var428);
                                                float var429 = -20.0F;
                                                float var490 = arg0;
                                                float var542 = arg0;
                                                float var491 = (var490 * var542);
                                                double var492 = ((double) var491);
                                                double var581 = 3.141592653589793;
                                                double var494 = (var492 * var581);
                                                double var496 = Math.sin(var494);
                                                float var498 = ((float) var496);
                                                float var430 = (var429 * var498);
                                                float var499 = -20.0F;
                                                float var543 = var24;
                                                float var544 = bump(var543);
                                                float var500 = (var499 * var544);
                                                float var545 = -80.0F;
                                                float var583 = var24;
                                                float var584 = bump(var583);
                                                float var546 = (var545 * var584);
                                                float var585 = arg2;
                                                float var547 = (var546 * var585);
                                                float var586 = 0.0F;
                                                set(var91, var209, var299, var369, var430, var500, var547, var586);
                                                boolean var50 = swinging;
                                                if (!var50) {
                                                    float var51 = sinceEnd;
                                                    int var52 = Float.compare(var51, 0.95F);
                                                    if (var52 > 0) {
                                                        float var53 = whirl;
                                                        float var54 = (var53 / 360.0F);
                                                        int var55 = Math.round(var54);
                                                        float var56 = ((float) var55);
                                                        float var57 = (var56 * 360.0F);
                                                        whirl = var57;
                                                    }
                                                    float var58 = whirl;
                                                    int var59 = Float.compare(var58, 36000.0F);
                                                    if (var59 > 0) {
                                                        float var60 = whirl;
                                                        float var61 = (var60 - 36000.0F);
                                                        whirl = var61;
                                                    }
                                                    float var62 = whirl;
                                                    float var597 = var62;
                                                    float[] var63 = target;
                                                    float var268 = sinD(var597);
                                                    float var184 = (0.18F * var268);
                                                    float var185 = (var184 * arg2);
                                                    float var271 = (0.08F * var27);
                                                    float var186 = (var185 - var271);
                                                    float var273 = (0.06F * var27);
                                                    float var470 = var597;
                                                    float var471 = cosD(var470);
                                                    float var406 = (1.0F - var471);
                                                    float var337 = (-0.06F * var406);
                                                    float var472 = var27;
                                                    float var408 = (0.1F * var472);
                                                    float var338 = (var337 - var408);
                                                    float var473 = var597;
                                                    float var474 = sinD(var473);
                                                    float var410 = (30.0F * var474);
                                                    float var475 = -15.0F;
                                                    float var525 = var27;
                                                    float var476 = (var475 * var525);
                                                    float var526 = 25.0F;
                                                    float var566 = var597;
                                                    float var567 = sinD(var566);
                                                    float var527 = (var526 * var567);
                                                    float var477 = (var476 + var527);
                                                    float var528 = -50.0F;
                                                    float var568 = var27;
                                                    float var529 = (var528 * var568);
                                                    float var569 = arg2;
                                                    float var530 = (var529 * var569);
                                                    float var570 = 0.0F;
                                                    set(var63, var186, var273, var338, var410, var477, var530, var570);
                                                    float[] var110 = target;
                                                    Arrays.fill(var110, 0.0F);
                                                }
                                            }
                                            return;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    static {
        String[] var3 = new String[9];
        var3[0] = "Smooth";
        var3[1] = "Stab";
        var3[2] = "Slice";
        var3[3] = "Windmill";
        var3[4] = "Combo Slash";
        var3[5] = "Blade Dance";
        var3[6] = "Heavy Charge";
        var3[7] = "Momentum";
        var3[8] = "Whirlwind";
        MODES = var3;
        pose = new float[7];
        vel = new float[7];
        target = new float[7];
        from = new float[7];
        to = new float[7];
        mode = "";
        lastP = 0.0F;
        combo = 0;
        sinceEnd = 99.0F;
        swinging = false;
        momentum = 0.0F;
        spin = 0.0F;
        whirl = 0.0F;
        time = 0.0F;
    }

}
