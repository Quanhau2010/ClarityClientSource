package need.clarity.modules.visual;

import java.util.Locale;
import need.clarity.api.Hooks;
import need.clarity.modules.ModuleBase;
import need.clarity.util.ThemeColors;

public class SkyShaders extends ModuleBase {
    public static final String[] fieldVal;
    private static SkyShaders INSTANCE;
    private static float[] floatBuffer;
    private static float[] floatBuffer1;
    private static long timestampMs;

    public SkyShaders() {
        super("Sky Shaders", "Visual");
        INSTANCE = this;
        Hooks.Bool0 var11 = () -> SkyShaders.matchFog();
        Hooks.bool0("SkyShaders.matchFog", var11);
        Hooks.Bool0 var12 = () -> SkyShaders.hideClouds();
        Hooks.bool0("SkyShaders.hideClouds", var12);
        Hooks.Bool0 var13 = () -> SkyShaders.keepSunMoon();
        Hooks.bool0("SkyShaders.keepSunMoon", var13);
        Hooks.Obj0 var14 = () -> SkyShaders.preset();
        Hooks.obj0("SkyShaders.preset", var14);
        Hooks.Obj0 var15 = () -> SkyShaders.colors();
        Hooks.obj0("SkyShaders.colors", var15);
        Hooks.Obj1 var16 = (p0) -> SkyShaders.hook$colors(p0);
        Hooks.obj1("SkyShaders.horizon", var16);
    }

    public static boolean active() {
        int var5 = 0;
        SkyShaders var0 = INSTANCE;
        if (var0 != null) {
            if (var0.isEnabled()) {
                var5 = 1;
            } else {
            }
        }
        var5 = 0;
        return var5;
    }

    public static String preset() {
        String var21 = null;
        int var6 = 0;
        if (INSTANCE != null) {
            var21 = ((String) INSTANCE.getSetting("Preset", "Aurora"));
        } else {
            var21 = "Aurora";
        }
        String var3 = var21;
        String[] var10 = fieldVal;
        int var12 = var10.length;
        var6 = 0;
        while (true) {
            if (var6 >= var12) break;
            int var23 = var6;
            String var17 = var10[var23];
            if (var17.equals(var3)) {
                return var17;
            }
            var6 = (var6 + 1);
        }
        return "Aurora";
    }

    public static String resolveLabel(String arg0) {
        Locale var2 = Locale.ROOT;
        return arg0.toLowerCase(var2).replace(" ", "");
    }

    public static float computeFloatMetric() {
        Float var7 = 1.0F;
        return computeFloatMetric(((Float) evaluate("Speed", var7)), 0.0F, 5.0F);
    }

    public static float computeFloatMetric1() {
        Float var7 = 1.0F;
        return computeFloatMetric(((Float) evaluate("Brightness", var7)), 0.0F, 3.0F);
    }

    public static float computeFloatMetric2() {
        Float var7 = 1.0F;
        return computeFloatMetric(((Float) evaluate("Intensity", var7)), 0.0F, 3.0F);
    }

    public static float computeFloatMetric3() {
        Float var7 = 1.0F;
        return computeFloatMetric(((Float) evaluate("Scale", var7)), 0.25F, 3.0F);
    }

    public static float computeFloatMetric4() {
        Float var7 = 1.0F;
        return computeFloatMetric(((Float) evaluate("Stars", var7)), 0.0F, 3.0F);
    }

    public static float computeFloatMetric5() {
        Float var7 = 0.3F;
        return computeFloatMetric(((Float) evaluate("Sky Rotation", var7)), -5.0F, 5.0F);
    }

    public static float computeFloatMetric6() {
        Float var7 = 0.5F;
        return computeFloatMetric(((Float) evaluate("Horizon Blend", var7)), 0.0F, 1.0F);
    }

    public static boolean matchFog() {
        Boolean var5 = true;
        return ((Boolean) evaluate("Match Fog", var5));
    }

    public static boolean keepSunMoon() {
        Boolean var5 = false;
        return ((Boolean) evaluate("Sun & Moon", var5));
    }

    public static boolean hideClouds() {
        int var6 = 0;
        if (active()) {
            Boolean var7 = true;
            if (((Boolean) evaluate("Hide Clouds", var7))) {
                var6 = 1;
            }
        } else {
            var6 = 0;
        }
        return var6;
    }

    public static boolean isEligible() {
        Boolean var5 = true;
        return ((Boolean) evaluate("In The End", var5));
    }

    public static float computeFloatMetric7() {
        float var16 = 0.0F;
        String var20 = null;
        int var5 = 0;
        if (INSTANCE != null) {
            var20 = ((String) INSTANCE.getSetting("Resolution", "Full"));
        } else {
            var20 = "Full";
        }
        String var3 = var20;
        var5 = -1;
        switch (var3.hashCode()) {
            case 52458 -> {}
            case 54535 -> {}
            default -> {}
        }
        if (var3.equals("75%")) {
            var5 = 0;
            if (var3.equals("50%")) {
                var5 = 1;
            }
            switch (var5) {
                case 0 -> {}
                case 1 -> {}
                default -> {}
            }
            var16 = 0.5F;
            var16 = 0.75F;
            var16 = 1.0F;
            return var16;
        }
    }

    private static int[] evaluate(String arg0) {
        int var6 = 0;
        int var44 = 0;
        var6 = -1;
        switch (arg0.hashCode()) {
            case -1965582497 -> {}
            case -1710645595 -> {}
            case -1315838013 -> {}
            case -1301352427 -> {}
            case 77382433 -> {}
            case 296012929 -> {}
            case 785408423 -> {}
            case 2125565744 -> {}
            default -> {}
        }
        if (arg0.equals("Black Hole")) {
            var6 = 1;
            if (arg0.equals("Galaxy")) {
                var6 = 0;
                if (arg0.equals("Dreamscape")) {
                    var6 = 6;
                    if (arg0.equals("Thunderstorm")) {
                        var6 = 2;
                        if (arg0.equals("Synthwave")) {
                            var6 = 4;
                            if (arg0.equals("Nebula")) {
                                var6 = 3;
                                if (arg0.equals("Blood Moon")) {
                                    var6 = 5;
                                    if (arg0.equals("Prism")) {
                                        var6 = 7;
                                    }
                                    switch (var6) {
                                        case 0 -> {}
                                        case 1 -> {}
                                        case 2 -> {}
                                        case 3 -> {}
                                        case 4 -> {}
                                        case 5 -> {}
                                        case 6 -> {}
                                        case 7 -> {}
                                        default -> {}
                                    }
                                    var44 = new int[2];
                                    var44[0] = 16767392;
                                    var44[1] = 16738847;
                                    var44 = new int[2];
                                    var44[0] = 16763274;
                                    var44[1] = 9413375;
                                    var44 = new int[2];
                                    var44[0] = 13227775;
                                    var44[1] = 5923712;
                                    var44 = new int[2];
                                    var44[0] = 16726831;
                                    var44[1] = 5900810;
                                    var44 = new int[2];
                                    var44[0] = 16722541;
                                    var44[1] = 383464;
                                    var44 = new int[2];
                                    var44[0] = 8300543;
                                    var44[1] = 16757703;
                                    var44 = new int[2];
                                    var44[0] = 16727450;
                                    var44[1] = 3065343;
                                    var44 = new int[2];
                                    int var76 = ThemeColors.getAccentRgb();
                                    var44[0] = var76;
                                    int var77 = ThemeColors.getAccent2Rgb();
                                    var44[1] = var77;
                                    var44 = new int[2];
                                    var44[0] = 2883464;
                                    var44[1] = 9063935;
                                    return var44;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static float[][] colors() {
        int var6 = 0;
        int var7 = 0;
        String var0 = preset();
        if ("Prism".equals(var0)) {
            Boolean var39 = true;
            if (((Boolean) evaluate("Natural Colors", var39))) {
                return evaluate();
            }
        }
        int[] var18 = evaluate(var0);
        var6 = var18[0];
        var7 = var18[1];
        Boolean var43 = true;
        if (!((Boolean) evaluate("Natural Colors", var43))) {
            if (INSTANCE != null) {
                Object var29 = INSTANCE.getSetting("Color", null);
                Object var31 = INSTANCE.getSetting("Second Color", null);
                int var46 = var6;
                var6 = ThemeColors.resolveRgb(var29, var46, false);
                int var47 = var7;
                var7 = ThemeColors.resolveRgb(var31, var47, true);
            }
            float[][] var37 = new float[2][];
            float[] var53 = evaluate(var6);
            var37[0] = var53;
            float[] var55 = evaluate(var7);
            var37[1] = var55;
            return var37;
        }
    }

    public static float[] horizon(String arg0, float[] arg1, float[] arg2) {
        float[] var51 = null;
        int var8 = 0;
        var8 = -1;
        switch (arg0.hashCode()) {
            case -1965582497 -> {}
            case -1710645595 -> {}
            case -1315838013 -> {}
            case -1301352427 -> {}
            case 77382433 -> {}
            case 296012929 -> {}
            case 785408423 -> {}
            case 2125565744 -> {}
            default -> {}
        }
        if (arg0.equals("Nebula")) {
            var8 = 3;
            if (arg0.equals("Synthwave")) {
                var8 = 4;
                if (arg0.equals("Galaxy")) {
                    var8 = 0;
                    if (arg0.equals("Blood Moon")) {
                        var8 = 5;
                        if (arg0.equals("Black Hole")) {
                            var8 = 1;
                            if (arg0.equals("Thunderstorm")) {
                                var8 = 2;
                                if (arg0.equals("Dreamscape")) {
                                    var8 = 6;
                                    if (arg0.equals("Prism")) {
                                        var8 = 7;
                                    }
                                    switch (var8) {
                                        case 0 -> {}
                                        case 1 -> {}
                                        case 2 -> {}
                                        case 3 -> {}
                                        case 4 -> {}
                                        case 5 -> {}
                                        case 6 -> {}
                                        case 7 -> {}
                                        default -> {}
                                    }
                                    var51 = evaluate(arg2, 0.8F);
                                    var51 = evaluate(arg2, 0.45F);
                                    float[] var55 = evaluate(16769216);
                                    var51 = evaluate(arg2, var55, 0.25F);
                                    var51 = evaluate(evaluate(657936), arg2, 0.06F);
                                    var51 = evaluate(arg1, 0.8F);
                                    float[] var21 = evaluate(789014);
                                    float[] var57 = evaluate(arg1, arg2, 0.5F);
                                    var51 = evaluate(var21, var57, 0.08F);
                                    float[] var26 = evaluate(460815);
                                    float[] var61 = evaluate(arg1, arg2, 0.5F);
                                    var51 = evaluate(var26, var61, 0.14F);
                                    var51 = evaluate(evaluate(790300), arg2, 0.1F);
                                    var51 = evaluate(evaluate(726562), arg1, 0.08F);
                                    return var51;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static synchronized float[][] evaluate() {
        float var39 = 0.0F;
        float[] var13 = evaluate(ThemeColors.getAccentRgb());
        float[] var15 = evaluate(ThemeColors.getAccent2Rgb());
        long var16 = System.nanoTime();
        if (floatBuffer == null) {
            floatBuffer = ((float[]) var13.clone());
            floatBuffer1 = ((float[]) var15.clone());
        }
        if (timestampMs == 0L) {
            var39 = 0.0F;
        } else {
            long var5 = timestampMs;
            float var43 = (((float) (var16 - var5)) / 1.0E9F);
            var39 = Math.min(0.1F, var43);
        }
        float var11 = var39;
        timestampMs = var16;
        float var50 = ((float) Math.exp(((double) (var11 * 3.2F))));
        float var32 = (1.0F - var50);
        floatBuffer = evaluate(floatBuffer, var13, var32);
        floatBuffer1 = evaluate(floatBuffer1, var15, var32);
        float[][] var38 = new float[2][];
        float[] var61 = ((float[]) floatBuffer.clone());
        var38[0] = var61;
        float[] var64 = ((float[]) floatBuffer1.clone());
        var38[1] = var64;
        return var38;
    }

    private static Object evaluate(String arg0, Object arg1) {
        SkyShaders var2 = INSTANCE;
        if (var2 == null) {
            return arg1;
        }
        Object var11 = var2.getSetting(arg0, arg1);
        if ((arg1 instanceof Float)) {
            if ((var11 instanceof Number)) {
                return ((Number) var11);
            }
        }
        if ((arg1 instanceof Boolean)) {
            if (!(var11 instanceof Boolean)) {
                return arg1;
            }
        }
        return var11;
    }

    private static float computeFloatMetric(float arg0, float arg1, float arg2) {
        if (Float.isNaN(arg0)) {
            return arg1;
        }
        float var10 = Math.min(arg2, arg0);
        return Math.max(arg1, var10);
    }

    private static float[] evaluate(int arg0) {
        float[] var5 = new float[3];
        float var11 = (((float) ((arg0 >> 16) & 255)) / 255.0F);
        var5[0] = var11;
        float var16 = (((float) ((arg0 >> 8) & 255)) / 255.0F);
        var5[1] = var16;
        float var20 = (((float) (arg0 & 255)) / 255.0F);
        var5[2] = var20;
        return var5;
    }

    private static float[] evaluate(float[] arg0, float[] arg1, float arg2) {
        float[] var9 = new float[3];
        float var12 = arg0[0];
        float var21 = arg1[0];
        float var35 = arg0[0];
        float var23 = ((var21 - var35) * arg2);
        float var13 = (var12 + var23);
        var9[0] = var13;
        float var15 = arg0[1];
        float var26 = arg1[1];
        float var39 = arg0[1];
        float var28 = ((var26 - var39) * arg2);
        float var16 = (var15 + var28);
        var9[1] = var16;
        float var18 = arg0[2];
        float var31 = arg1[2];
        float var43 = arg0[2];
        float var33 = ((var31 - var43) * arg2);
        float var19 = (var18 + var33);
        var9[2] = var19;
        return var9;
    }

    private static float[] evaluate(float[] arg0, float arg1) {
        float[] var6 = new float[3];
        float var10 = (arg0[0] * arg1);
        var6[0] = var10;
        float var13 = (arg0[1] * arg1);
        var6[1] = var13;
        float var16 = (arg0[2] * arg1);
        var6[2] = var16;
        return var6;
    }

    static {
        String[] var3 = new String[9];
        var3[0] = "Aurora";
        var3[1] = "Galaxy";
        var3[2] = "Black Hole";
        var3[3] = "Thunderstorm";
        var3[4] = "Nebula";
        var3[5] = "Synthwave";
        var3[6] = "Blood Moon";
        var3[7] = "Dreamscape";
        var3[8] = "Prism";
        fieldVal = var3;
        timestampMs = 0L;
    }

}
