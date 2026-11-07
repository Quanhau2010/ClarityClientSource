package need.clarity.modules.visual;

import need.clarity.api.Hooks;
import need.clarity.config.ClarityConfig;
import need.clarity.modules.ModuleBase;
import need.clarity.util.ThemeColors;

public class Ambience extends ModuleBase {
    private static Ambience INSTANCE;
    private static final int CONST_INT = 12638463;
    private static final int CONST_INT1 = 16777215;
    private static final int CONST_INT2 = 7907327;
    private static final int CONST_INT3 = 1714796;

    public Ambience() {
        super("Ambience", "Visual");
        INSTANCE = this;
        Hooks.Bool0 var19 = () -> Ambience.isOverrideTime();
        Hooks.bool0("Ambience.overrideTime", var19);
        Hooks.Dbl0 var20 = () -> Ambience.overrideTime();
        Hooks.dbl0("Ambience.dayTime", var20);
        Hooks.Bool0 var21 = () -> Ambience.dayTime();
        Hooks.bool0("Ambience.overrideWeather", var21);
        Hooks.Obj0 var22 = () -> Ambience.overrideWeather();
        Hooks.obj0("Ambience.weatherMode", var22);
        Hooks.Bool0 var23 = () -> Ambience.weatherMode();
        Hooks.bool0("Ambience.overrideFogColour", var23);
        Hooks.Obj0 var24 = () -> Ambience.overrideFogColour();
        Hooks.obj0("Ambience.fogColour", var24);
        Hooks.Bool0 var25 = () -> Ambience.fogColour();
        Hooks.bool0("Ambience.overrideFogDistance", var25);
        Hooks.Dbl0 var26 = () -> Ambience.overrideFogDistance();
        Hooks.dbl0("Ambience.fogDistanceChunks", var26);
        Hooks.Bool0 var27 = () -> Ambience.fogDistanceChunks();
        Hooks.bool0("Ambience.overrideLightmap", var27);
        Hooks.Obj0 var28 = () -> Ambience.overrideLightmap();
        Hooks.obj0("Ambience.lightmapColour", var28);
        Hooks.Bool0 var29 = () -> Ambience.lightmapColour();
        Hooks.bool0("Ambience.overrideSkyColour", var29);
        Hooks.Obj0 var30 = () -> Ambience.overrideSkyColour();
        Hooks.obj0("Ambience.skyColour", var30);
        Hooks.Bool0 var31 = () -> Ambience.skyColour();
        Hooks.bool0("Ambience.skyGradient", var31);
        Hooks.Obj0 var32 = () -> Ambience.skyGradient();
        Hooks.obj0("Ambience.skyGradientColour2", var32);
    }

    public void onEnable() {
        int var7 = 0;
        String[] var10 = new String[6];
        var10[0] = "Override Time";
        var10[1] = "Override Weather";
        var10[2] = "Override Fog Colour";
        var10[3] = "Override Fog Distance";
        var10[4] = "Override Lightmap";
        var10[5] = "Override Sky Colour";
        int var13 = var10.length;
        var7 = 0;
        while (true) {
            if (var7 >= var13) break;
            int var22 = var7;
            String var17 = var10[var22];
            try {
                String var19 = this.getName();
                Boolean var30 = true;
                ClarityConfig.putSetting(var19, var17, var30);
            } catch (Throwable ignored) {
            }
            var7 = (var7 + 1);
        }
    }

    public void onDisable() {
    }

    public static boolean isOverrideTime() {
        int var11 = 0;
        Ambience var0 = INSTANCE;
        if (var0 != null) {
            if (var0.isEnabled()) {
                Boolean var12 = false;
                if (((Boolean) var0.getSetting("Override Time", var12))) {
                    var11 = 1;
                } else {
                }
            }
            var11 = 0;
            return var11;
        }
    }

    public static long overrideTime() {
        int var7 = 0;
        Ambience var0 = INSTANCE;
        if (var0 != null) {
            Integer var18 = 6000;
            var7 = ((Integer) var0.getSetting("Time of Day", var18));
        } else {
            var7 = 6000;
        }
        return ((long) Math.clamp(((long) var7), 0, 23999));
    }

    public static float computeFloatMetric(float arg0) {
        if (!isOverrideTime()) {
            return arg0;
        }
        return (((float) overrideTime()) / 24000.0F);
    }

    public static boolean dayTime() {
        int var11 = 0;
        Ambience var0 = INSTANCE;
        if (var0 != null) {
            if (var0.isEnabled()) {
                Boolean var12 = false;
                if (((Boolean) var0.getSetting("Override Weather", var12))) {
                    var11 = 1;
                } else {
                    var11 = 0;
                }
                return var11;
            }
        }
    }

    public static String overrideWeather() {
        String var5 = null;
        Ambience var0 = INSTANCE;
        if (var0 != null) {
            var5 = ((String) var0.getSetting("Weather", "Clear"));
        } else {
            var5 = "Clear";
        }
        return var5;
    }

    public static boolean weatherMode() {
        int var11 = 0;
        Ambience var0 = INSTANCE;
        if (var0 != null) {
            if (var0.isEnabled()) {
                Boolean var12 = false;
                if (((Boolean) var0.getSetting("Override Fog Colour", var12))) {
                    var11 = 1;
                }
            }
        } else {
            var11 = 0;
        }
        return var11;
    }

    public static float[] overrideFogColour() {
        String var10 = null;
        Ambience var0 = INSTANCE;
        if (var0 != null) {
            var10 = ((String) var0.getSetting("Fog Colour", "#c0d8ff"));
        } else {
            var10 = "#c0d8ff";
        }
        return evaluate(var10, 12638463, false);
    }

    public static boolean fogColour() {
        int var11 = 0;
        Ambience var0 = INSTANCE;
        if (var0 != null) {
            if (var0.isEnabled()) {
                Boolean var12 = false;
                if (((Boolean) var0.getSetting("Override Fog Distance", var12))) {
                    var11 = 1;
                }
            } else {
            }
        }
        var11 = 0;
        return var11;
    }

    public static int overrideFogDistance() {
        int var11 = 0;
        Ambience var0 = INSTANCE;
        if (var0 != null) {
            Integer var13 = 16;
            var11 = ((Integer) var0.getSetting("Fog Distance", var13));
        } else {
            var11 = 16;
        }
        int var4 = ((byte) var11);
        return Math.max(0, var4);
    }

    public static boolean fogDistanceChunks() {
        int var11 = 0;
        Ambience var0 = INSTANCE;
        if (var0 != null) {
            if (var0.isEnabled()) {
                Boolean var12 = false;
                if (((Boolean) var0.getSetting("Override Lightmap", var12))) {
                    var11 = 1;
                } else {
                    var11 = 0;
                }
                return var11;
            }
        }
    }

    public static float[] overrideLightmap() {
        String var15 = null;
        Ambience var0 = INSTANCE;
        if (var0 != null) {
            var15 = ((String) var0.getSetting("Lightmap Colour", "#ffffff"));
        } else {
            var15 = "#ffffff";
        }
        int var12 = ThemeColors.resolveRgb(var15, 16777215, false);
        float[] var14 = new float[3];
        float var24 = (((float) ((var12 >> 16) & 255)) / 255.0F);
        var14[0] = var24;
        float var29 = (((float) ((var12 >> 8) & 255)) / 255.0F);
        var14[1] = var29;
        float var33 = (((float) (var12 & 255)) / 255.0F);
        var14[2] = var33;
        return var14;
    }

    public static boolean lightmapColour() {
        int var11 = 0;
        Ambience var0 = INSTANCE;
        if (var0 != null) {
            if (var0.isEnabled()) {
                Boolean var12 = false;
                if (((Boolean) var0.getSetting("Override Sky Colour", var12))) {
                    var11 = 1;
                }
            } else {
            }
        }
        var11 = 0;
        return var11;
    }

    public static float[] overrideSkyColour() {
        String var10 = null;
        Ambience var0 = INSTANCE;
        if (var0 != null) {
            var10 = ((String) var0.getSetting("Sky Colour", "#78a7ff"));
        } else {
            var10 = "#78a7ff";
        }
        return evaluate(var10, 7907327, false);
    }

    public static boolean skyColour() {
        int var11 = 0;
        Ambience var0 = INSTANCE;
        if (var0 != null) {
            if (var0.isEnabled()) {
                Boolean var12 = false;
                if (((Boolean) var0.getSetting("Sky Gradient", var12))) {
                    var11 = 1;
                }
            }
        } else {
            var11 = 0;
        }
        return var11;
    }

    public static float[] skyGradient() {
        String var10 = null;
        Ambience var0 = INSTANCE;
        if (var0 != null) {
            var10 = ((String) var0.getSetting("Sky Gradient Colour 2", "#1a2a6c"));
        } else {
            var10 = "#1a2a6c";
        }
        return evaluate(var10, 1714796, true);
    }

    private static float[] evaluate(String arg0, int arg1, boolean arg2) {
        int var9 = ThemeColors.resolveRgb(arg0, arg1, arg2);
        float[] var11 = new float[3];
        float var18 = (((float) ((var9 >> 16) & 255)) / 255.0F);
        var11[0] = var18;
        float var23 = (((float) ((var9 >> 8) & 255)) / 255.0F);
        var11[1] = var23;
        float var27 = (((float) (var9 & 255)) / 255.0F);
        var11[2] = var27;
        return var11;
    }

}
