package need.clarity.render.handshader;

import net.minecraft.world.item.ItemDisplayContext;

public class HandShaderConfig {
    public static boolean enabled;
    public static boolean applyToArm;
    public static boolean applyToItems;
    public static int color;
    public static double opacity;
    public static boolean textured;
    public static boolean fullbright;
    public static boolean outline;
    public static int outlineColor;
    public static double outlineWidth;
    public static boolean outlineGlow;
    public static double glowSpread;
    public static boolean antialiasing;

    public HandShaderConfig() {
    }

    public static int getArmColor() {
        int var19 = ((int) Math.round((opacity * 255.0)));
        int var11 = Math.min(255, var19);
        int var9 = (Math.max(1, var11) << 24);
        int var14 = (color & 16777215);
        return (var9 | var14);
    }

    public static int getEffectiveOutlineColor() {
        return outlineColor;
    }

    public static int getEffectiveLight(int arg0) {
        if (fullbright) {
            return 15728880;
        }
        return arg0;
    }

    public static int getItemColor(int arg0) {
        int var21 = ((arg0 >> 24) & 255);
        int var24 = ((arg0 >> 16) & 255);
        int var27 = ((arg0 >> 8) & 255);
        int var29 = (arg0 & 255);
        int var101 = ((int) Math.round((opacity * 255.0)));
        int var71 = Math.min(255, var101);
        int var31 = Math.max(1, var71);
        int var32 = color;
        int var35 = ((var32 >> 16) & 255);
        int var38 = ((var32 >> 8) & 255);
        int var40 = (var32 & 255);
        int var43 = ((var21 * var31) / 255);
        if (!textured) {
            int var46 = (var43 << 24);
            int var81 = (var35 << 16);
            int var47 = (var46 | var81);
            int var83 = (var38 << 8);
            return ((var47 | var83) | var40);
        }
        int var52 = ((var24 * var35) / 255);
        int var55 = ((var27 * var38) / 255);
        int var58 = ((var29 * var40) / 255);
        int var60 = (var43 << 24);
        int var93 = (var52 << 16);
        int var61 = (var60 | var93);
        int var95 = (var55 << 8);
        return ((var61 | var95) | var58);
    }

    public static boolean shouldApplyToItem(ItemDisplayContext arg0) {
        int var6 = 0;
        if (enabled) {
            if (applyToItems) {
                if (arg0 != null) {
                    if (arg0.firstPerson()) {
                        var6 = 1;
                    }
                }
            }
        } else {
            var6 = 0;
        }
        return var6;
    }

    static {
        enabled = false;
        applyToArm = true;
        applyToItems = true;
        color = -1727996673;
        opacity = 0.6;
        textured = true;
        fullbright = false;
        outline = true;
        outlineColor = -16720641;
        outlineWidth = 1.0;
        outlineGlow = true;
        glowSpread = 1.0;
        antialiasing = true;
    }

}
