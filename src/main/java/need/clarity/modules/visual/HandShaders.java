package need.clarity.modules.visual;

import need.clarity.api.Hooks;
import need.clarity.modules.ModuleBase;
import need.clarity.render.ShaderHelper;
import need.clarity.util.ThemeColors;

public class HandShaders extends ModuleBase {
    public static final String[] fieldVal;
    public static HandShaders INSTANCE;

    public HandShaders() {
        super("Hand Shaders", "Visual");
        INSTANCE = this;
        Hooks.Bool0 var35 = () -> HandShaders.lambda$ah();
        Hooks.bool0("HandShaders.enabled", var35);
        Hooks.Obj0 var36 = () -> HandShaders.enabled();
        Hooks.obj0("HandShaders.shader", var36);
        Hooks.Bool0 var37 = () -> HandShaders.shader();
        Hooks.bool0("HandShaders.glowArm", var37);
        Hooks.Bool0 var38 = () -> HandShaders.glowArm();
        Hooks.bool0("HandShaders.glowItems", var38);
        Hooks.Dbl0 var39 = () -> HandShaders.glowItems();
        Hooks.dbl0("HandShaders.glowColor", var39);
        Hooks.Dbl0 var40 = () -> HandShaders.glowColor();
        Hooks.dbl0("HandShaders.glowOpacity", var40);
        Hooks.Bool0 var41 = () -> HandShaders.glowOpacity();
        Hooks.bool0("HandShaders.glowTextured", var41);
        Hooks.Bool0 var42 = () -> HandShaders.glowTextured();
        Hooks.bool0("HandShaders.glowFullbright", var42);
        Hooks.Bool0 var43 = () -> HandShaders.glowFullbright();
        Hooks.bool0("HandShaders.glowOutline", var43);
        Hooks.Dbl0 var44 = () -> HandShaders.glowOutline();
        Hooks.dbl0("HandShaders.glowOutlineColor", var44);
        Hooks.Dbl0 var45 = () -> HandShaders.glowOutlineColor();
        Hooks.dbl0("HandShaders.glowOutlineWidth", var45);
        Hooks.Bool0 var46 = () -> HandShaders.glowOutlineWidth();
        Hooks.bool0("HandShaders.glowBloom", var46);
        Hooks.Dbl0 var47 = () -> HandShaders.glowBloom();
        Hooks.dbl0("HandShaders.glowSpread", var47);
        Hooks.Bool0 var48 = () -> HandShaders.glowSpread();
        Hooks.bool0("HandShaders.glowAA", var48);
        Hooks.Bool0 var49 = () -> HandShaders.glowAA();
        Hooks.bool0("HandShaders.invertHideHand", var49);
        Hooks.Act0 var50 = () -> HandShaders.invertHideHand();
        Hooks.act0("HandShaders.render", var50);
        Hooks.Dbl0 var51 = () -> HandShaders.render();
        Hooks.dbl0("HandShaders.chromaSpeed", var51);
        Hooks.Dbl0 var52 = () -> HandShaders.chromaSpeed();
        Hooks.dbl0("HandShaders.chromaSat", var52);
        Hooks.Dbl0 var53 = () -> HandShaders.chromaSat();
        Hooks.dbl0("HandShaders.chromaBright", var53);
        Hooks.Dbl0 var54 = () -> HandShaders.chromaBright();
        Hooks.dbl0("HandShaders.invertIntensity", var54);
        Hooks.Bool0 var55 = () -> HandShaders.invertIntensity();
        Hooks.bool0("HandShaders.invertHue", var55);
        Hooks.Bool0 var56 = () -> HandShaders.invertHue();
        Hooks.bool0("HandShaders.invertSat", var56);
        Hooks.Bool0 var57 = () -> HandShaders.invertSat();
        Hooks.bool0("HandShaders.invertBright", var57);
        Hooks.Dbl0 var58 = () -> HandShaders.invertBright();
        Hooks.dbl0("HandShaders.invertEdgeGlow", var58);
        Hooks.Dbl0 var59 = () -> HandShaders.invertEdgeGlow();
        Hooks.dbl0("HandShaders.invertGlowColor", var59);
        Hooks.Dbl0 var60 = () -> HandShaders.invertGlowColor();
        Hooks.dbl0("HandShaders.outlineColor", var60);
        Hooks.Dbl0 var61 = () -> HandShaders.outlineColor();
        Hooks.dbl0("HandShaders.outlineThickness", var61);
        Hooks.Bool0 var62 = () -> HandShaders.outlineThickness();
        Hooks.bool0("HandShaders.tintEnabled", var62);
        Hooks.Dbl0 var63 = () -> HandShaders.tintEnabled();
        Hooks.dbl0("HandShaders.tintColor", var63);
        Hooks.Dbl0 var64 = () -> HandShaders.tintColor();
        Hooks.dbl0("HandShaders.tintAmount", var64);
    }

    public void applyStep() {
        ShaderHelper.render();
    }

    public static String enabled() {
        String var3 = null;
        if (INSTANCE != null) {
            var3 = ((String) INSTANCE.getSetting("Shader", "Chroma"));
        } else {
            var3 = "None";
        }
        return var3;
    }

    private static int computeColorOrIndex(String arg0, int arg1) {
        String var28 = null;
        if (arg0 != null) {
            if (arg0.isEmpty()) {
            }
        }
        return arg1;
        if (arg0.startsWith("#")) {
            var28 = arg0.substring(1);
        } else {
            var28 = arg0;
        }
        String var6 = var28;
        try {
            if (var6.length() == 6) {
                long var34 = Long.parseLong(var6, 16);
                int var18 = ((int) (4278190080L | var34));
            }
        } catch (NumberFormatException ignored) {
        }
        return var18;
        try {
            if (var6.length() == 8) {
                int var26 = ((int) Long.parseLong(var6, 16));
            }
        } catch (NumberFormatException ignored) {
        }
        return var26;
        return arg1;
    }

    private static int computeColorOrIndex1(String arg0, int arg1) {
        String var7 = ((String) INSTANCE.getSetting(arg0, ""));
        if (!var7.isEmpty()) {
            return computeColorOrIndex(var7, arg1);
        }
        return arg1;
    }

    private static int computeColorOrIndex(String arg0, int arg1, boolean arg2) {
        String var12 = null;
        String var8 = ((String) INSTANCE.getSetting(arg0, ""));
        if (var8.isEmpty()) {
            var12 = null;
        } else {
            var12 = var8;
        }
        return ThemeColors.resolveArgb(var12, arg1, arg2);
    }

    public static float render() {
        HandShaders var0 = INSTANCE;
        Float var6 = 0.3F;
        return ((Float) var0.getSetting("Chroma Speed", var6));
    }

    public static float chromaSpeed() {
        HandShaders var0 = INSTANCE;
        Float var6 = 1.0F;
        return ((Float) var0.getSetting("Chroma Saturation", var6));
    }

    public static float chromaSat() {
        HandShaders var0 = INSTANCE;
        Float var6 = 1.0F;
        return ((Float) var0.getSetting("Chroma Brightness", var6));
    }

    public static boolean glowAA() {
        HandShaders var0 = INSTANCE;
        Boolean var6 = true;
        return ((Boolean) var0.getSetting("Hide Hand", var6));
    }

    public static float chromaBright() {
        HandShaders var0 = INSTANCE;
        Float var6 = 1.0F;
        return ((Float) var0.getSetting("Invert Intensity", var6));
    }

    public static boolean invertIntensity() {
        HandShaders var0 = INSTANCE;
        Boolean var6 = false;
        return ((Boolean) var0.getSetting("Invert Hue", var6));
    }

    public static boolean invertHue() {
        HandShaders var0 = INSTANCE;
        Boolean var6 = false;
        return ((Boolean) var0.getSetting("Invert Saturation", var6));
    }

    public static boolean invertSat() {
        HandShaders var0 = INSTANCE;
        Boolean var6 = false;
        return ((Boolean) var0.getSetting("Invert Brightness", var6));
    }

    public static float invertBright() {
        HandShaders var0 = INSTANCE;
        Float var6 = 0.5F;
        return ((Float) var0.getSetting("Edge Glow", var6));
    }

    public static int computeColorOrIndex() {
        return computeColorOrIndex1("Invert Glow Color", -16776961);
    }

    public static boolean shader() {
        HandShaders var0 = INSTANCE;
        Boolean var6 = true;
        return ((Boolean) var0.getSetting("Apply To Arm", var6));
    }

    public static boolean glowArm() {
        HandShaders var0 = INSTANCE;
        Boolean var6 = true;
        return ((Boolean) var0.getSetting("Apply To Items", var6));
    }

    public static int computeColorOrIndex2() {
        return computeColorOrIndex1("Glow Color", -16720641);
    }

    public static double glowColor() {
        HandShaders var0 = INSTANCE;
        Float var8 = 0.6F;
        return ((double) ((Float) var0.getSetting("Opacity", var8)));
    }

    public static boolean glowOpacity() {
        HandShaders var0 = INSTANCE;
        Boolean var6 = true;
        return ((Boolean) var0.getSetting("Textured", var6));
    }

    public static boolean glowTextured() {
        HandShaders var0 = INSTANCE;
        Boolean var6 = false;
        return ((Boolean) var0.getSetting("Fullbright", var6));
    }

    public static boolean glowFullbright() {
        HandShaders var0 = INSTANCE;
        Boolean var6 = true;
        return ((Boolean) var0.getSetting("Outline", var6));
    }

    public static int computeColorOrIndex3() {
        return computeColorOrIndex1("Glow Outline Color", -16720641);
    }

    public static double glowOutlineColor() {
        HandShaders var0 = INSTANCE;
        Float var8 = 1.0F;
        return ((double) ((Float) var0.getSetting("Outline Width", var8)));
    }

    public static boolean glowOutlineWidth() {
        HandShaders var0 = INSTANCE;
        Boolean var6 = true;
        return ((Boolean) var0.getSetting("Bloom", var6));
    }

    public static double glowBloom() {
        HandShaders var0 = INSTANCE;
        Float var8 = 1.0F;
        return ((double) ((Float) var0.getSetting("Glow Spread", var8)));
    }

    public static boolean glowSpread() {
        HandShaders var0 = INSTANCE;
        Boolean var6 = true;
        return ((Boolean) var0.getSetting("Antialiasing", var6));
    }

    public static int computeColorOrIndex4() {
        return computeColorOrIndex("Outline Color", -1, false);
    }

    public static float outlineColor() {
        HandShaders var0 = INSTANCE;
        Float var6 = 1.5F;
        return ((Float) var0.getSetting("Outline Thickness", var6));
    }

    public static boolean outlineThickness() {
        HandShaders var0 = INSTANCE;
        Boolean var6 = false;
        return ((Boolean) var0.getSetting("Tint", var6));
    }

    public static int computeColorOrIndex5() {
        return computeColorOrIndex("Tint Color", -65536, true);
    }

    public static float tintColor() {
        HandShaders var0 = INSTANCE;
        Float var6 = 0.4F;
        return ((Float) var0.getSetting("Tint Amount", var6));
    }

    public static boolean isBlurTarget() {
        Object var5 = INSTANCE.getSetting("Blur Target", "Behind Hand");
        return "Hand Itself".equals(var5);
    }

    public static float getPixelSize() {
        HandShaders var0 = INSTANCE;
        Float var6 = 6.0F;
        return ((Float) var0.getSetting("Pixel Size", var6));
    }

    public static float getBlur() {
        HandShaders var0 = INSTANCE;
        Float var6 = 10.0F;
        return ((Float) var0.getSetting("Blur", var6));
    }

    public static int computeColorOrIndex6() {
        return computeColorOrIndex("Glass Tint", -3151617, false);
    }

    public static float getGlassTintAmount() {
        HandShaders var0 = INSTANCE;
        Float var6 = 0.2F;
        return ((Float) var0.getSetting("Glass Tint Amount", var6));
    }

    public static float getRefraction() {
        HandShaders var0 = INSTANCE;
        Float var6 = 1.0F;
        return ((Float) var0.getSetting("Refraction", var6));
    }

    public static float getDetail() {
        HandShaders var0 = INSTANCE;
        Float var6 = 0.35F;
        return ((Float) var0.getSetting("Detail", var6));
    }

    public static float getRimLight() {
        HandShaders var0 = INSTANCE;
        Float var6 = 0.6F;
        return ((Float) var0.getSetting("Rim Light", var6));
    }

    public static String getGlowMode() {
        return ((String) INSTANCE.getSetting("Glow Mode", "Custom"));
    }

    public static int computeColorOrIndex7() {
        return computeColorOrIndex("Glow Color", -8740097, false);
    }

    public static float getGlowRadius() {
        HandShaders var0 = INSTANCE;
        Float var6 = 18.0F;
        return ((Float) var0.getSetting("Glow Radius", var6));
    }

    public static float getGlowIntensity() {
        HandShaders var0 = INSTANCE;
        Float var6 = 1.0F;
        return ((Float) var0.getSetting("Glow Intensity", var6));
    }

    public static boolean isGlowPulse() {
        HandShaders var0 = INSTANCE;
        Boolean var6 = false;
        return ((Boolean) var0.getSetting("Glow Pulse", var6));
    }

    public static String getWispMode() {
        return ((String) INSTANCE.getSetting("Wisp Mode", "Custom"));
    }

    public static int computeColorOrIndex8() {
        return computeColorOrIndex("Wisp Color", -7701249, false);
    }

    public static float getTrailLength() {
        HandShaders var0 = INSTANCE;
        Float var6 = 0.45F;
        return ((Float) var0.getSetting("Trail Length", var6));
    }

    public static float getTurbulence() {
        HandShaders var0 = INSTANCE;
        Float var6 = 1.0F;
        return ((Float) var0.getSetting("Turbulence", var6));
    }

    public static float getInertia() {
        HandShaders var0 = INSTANCE;
        Float var6 = 1.0F;
        return ((Float) var0.getSetting("Inertia", var6));
    }

    public static float getWispIntensity() {
        HandShaders var0 = INSTANCE;
        Float var6 = 1.0F;
        return ((Float) var0.getSetting("Wisp Intensity", var6));
    }

    public static int computeColorOrIndex9() {
        return computeColorOrIndex("Holo Color", -13047553, false);
    }

    public static float getHoloOpacity() {
        HandShaders var0 = INSTANCE;
        Float var6 = 0.55F;
        return ((Float) var0.getSetting("Holo Opacity", var6));
    }

    public static boolean isScanlines() {
        HandShaders var0 = INSTANCE;
        Boolean var6 = true;
        return ((Boolean) var0.getSetting("Scanlines", var6));
    }

    public static boolean isGlitch() {
        HandShaders var0 = INSTANCE;
        Boolean var6 = true;
        return ((Boolean) var0.getSetting("Glitch", var6));
    }

    public static float getHalo() {
        HandShaders var0 = INSTANCE;
        Float var6 = 0.6F;
        return ((Float) var0.getSetting("Halo", var6));
    }

    static {
        String[] var3 = new String[13];
        var3[0] = "None";
        var3[1] = "Chroma";
        var3[2] = "Invert";
        var3[3] = "Outline";
        var3[4] = "Glass";
        var3[5] = "Glow";
        var3[6] = "Hologram";
        var3[7] = "Prism";
        var3[8] = "Blur";
        var3[9] = "Pixelate";
        var3[10] = "Galaxy";
        var3[11] = "Neon";
        var3[12] = "Wisp";
        fieldVal = var3;
    }

}
