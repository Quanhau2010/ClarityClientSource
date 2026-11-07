package need.clarity.util;

import com.mojang.blaze3d.platform.NativeImage;
import java.util.concurrent.CompletableFuture;
import need.clarity.api.Hooks;
import net.minecraft.client.Minecraft;

public final class ThemeTextures {
    private static volatile boolean enabled;
    private static volatile float strength;
    private static volatile float tintR;
    private static volatile float tintG;
    private static volatile float tintB;

    private ThemeTextures() {
    }

    public static void set(boolean arg0, float arg1, boolean arg2, int arg3) {
        int var10 = 0;
        enabled = arg0;
        float var11 = Math.min(1.0F, arg1);
        strength = Math.max(0.0F, var11);
        if (arg2) {
            var10 = ThemeColors.getAccentRgb();
        } else {
            var10 = arg3;
        }
        setAccent(var10);
    }

    public static void refreshAccent() {
        setAccent(ThemeColors.getAccentRgb());
    }

    private static void setAccent(int arg0) {
        float var7 = 0.0F;
        int var9 = ((arg0 >> 16) & 255);
        int var12 = ((arg0 >> 8) & 255);
        int var14 = (arg0 & 255);
        int var35 = Math.max(var12, var14);
        var7 = ((float) Math.max(var9, var35));
        if (Float.compare(var7, 1.0F) < 0) {
            var7 = 1.0F;
        }
        float var22 = ((float) var9);
        float var37 = var7;
        tintR = (var22 / var37);
        float var25 = ((float) var12);
        float var38 = var7;
        tintG = (var25 / var38);
        float var28 = ((float) var14);
        float var39 = var7;
        tintB = (var28 / var39);
    }

    public static boolean enabled() {
        return enabled;
    }

    public static void reload() {
        try {
            Minecraft var0 = Minecraft.getInstance();
            if (var0 != null) {
                CompletableFuture var4 = var0.reloadResourcePacks();
            }
        } catch (Throwable ignored) {
        }
    }

    public static boolean shouldRecolor(String arg0) {
        int var13 = 0;
        if (arg0 == null) {
            return false;
        }
        if (!arg0.startsWith("block/")) {
            if (arg0.startsWith("item/")) {
            }
        }
        return true;
        if (!arg0.startsWith("textures/gui/")) {
            if (arg0.contains("/gui/")) {
            }
        }
        var13 = 1;
        var13 = 0;
        return var13;
    }

    public static void recolor(NativeImage arg0) {
        int var13 = 0;
        int var14 = 0;
        if (enabled) {
            if (arg0 == null) {
            }
        }
        return;
        float var25 = strength;
        if (Float.compare(var25, 0.001F) <= 0) {
            return;
        }
        float var28 = tintR;
        float var29 = tintG;
        float var30 = tintB;
        try {
            int var32 = arg0.getWidth();
            int var34 = arg0.getHeight();
            var13 = 0;
            while (true) {
                if (var13 >= var34) break;
                var14 = 0;
                while (true) {
                    if (var14 >= var32) break;
                    int var73 = var14;
                    int var3 = var13;
                    int var41 = arg0.getPixel(var73, var3);
                    int var44 = ((var41 >>> 24) & 255);
                    if (var44 == 0) {
                    } else {
                        int var48 = ((var41 >>> 16) & 255);
                        int var51 = ((var41 >>> 8) & 255);
                        int var53 = (var41 & 255);
                        float var82 = ((float) var48);
                        float var55 = (0.299F * var82);
                        float var101 = ((float) var51);
                        float var84 = (0.587F * var101);
                        float var56 = (var55 + var84);
                        float var103 = ((float) var53);
                        float var86 = (0.114F * var103);
                        float var57 = (var56 + var86);
                        float var59 = ((float) var48);
                        float var88 = (var57 * var28);
                        float var106 = ((float) var48);
                        float var90 = ((var88 - var106) * var25);
                        int var61 = ((int) (var59 + var90));
                        float var63 = ((float) var51);
                        float var92 = (var57 * var29);
                        float var110 = ((float) var51);
                        float var94 = ((var92 - var110) * var25);
                        int var65 = ((int) (var63 + var94));
                        float var67 = ((float) var53);
                        float var96 = (var57 * var30);
                        float var114 = ((float) var53);
                        float var98 = ((var96 - var114) * var25);
                        int var69 = ((int) (var67 + var98));
                        int var99 = var14;
                        int var116 = var13;
                        int var117 = (var44 << 24);
                        int var122 = (var61 << 16);
                        int var118 = (var117 | var122);
                        int var124 = (var65 << 8);
                        int var120 = ((var118 | var124) | var69);
                        arg0.setPixel(var99, var116, var120);
                    }
                    var14 = (var14 + 1);
                }
                var13 = (var13 + 1);
            }
            return;
        } catch (Throwable ignored) {
        }
    }

    static {
        enabled = false;
        strength = 0.6F;
        tintR = 0.48F;
        tintG = 0.64F;
        tintB = 1.0F;
        Hooks.Bool1 var1 = (p0) -> ThemeTextures.lambda$static$0(p0);
        Hooks.bool1("ThemeTextures.shouldRecolor", var1);
        Hooks.Act1 var8 = (p0) -> ThemeTextures.lambda$static$1(p0);
        Hooks.act1("ThemeTextures.recolor", var8);
    }

}
