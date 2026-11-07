package need.clarity.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import need.clarity.config.ClarityConfig;

public final class ThemeColors {
    public static final int DEFAULT_ACCENT_RGB = 8037111;
    public static final int DEFAULT_ACCENT2_RGB = 4020641;

    private ThemeColors() {
    }

    private static JsonObject themeObject() {
        try {
            JsonElement var3 = ClarityConfig.getCurrent().get("__theme");
            if (var3 != null) {
                if (var3.isJsonObject()) {
                    JsonObject var8 = var3.getAsJsonObject();
                }
            }
        } catch (Throwable ignored) {
        }
        return var8;
        return null;
    }

    public static boolean isEnabled() {
        JsonObject var0 = themeObject();
        if (var0 == null) {
            return true;
        }
        try {
            JsonElement var6 = var0.get("enabled");
            if (var6 != null) {
                if (!var6.isJsonPrimitive()) {
                }
            }
        } catch (Throwable ignored) {
        }
        return true;
        try {
            boolean var11 = var6.getAsBoolean();
        } catch (Throwable ignored) {
        }
        return var11;
    }

    private static int readHex(String arg0, int arg1) {
        JsonObject var2 = themeObject();
        if (var2 == null) {
            return arg1;
        }
        try {
            JsonElement var10 = var2.get(arg0);
            if (var10 != null) {
                if (var10.isJsonPrimitive()) {
                    int var16 = parseHexRgb(var10.getAsString(), arg1);
                }
            }
        } catch (Throwable ignored) {
        }
        return var16;
        return arg1;
    }

    public static int getAccentRgb() {
        return readHex("accent", 8037111);
    }

    public static int getAccent2Rgb() {
        return readHex("accent2", 4020641);
    }

    public static int getAccentArgb() {
        int var1 = getAccentRgb();
        return (-16777216 | var1);
    }

    public static int getAccent2Argb() {
        int var1 = getAccent2Rgb();
        return (-16777216 | var1);
    }

    public static int getSurfaceRgb() {
        return readHex("surface", 1448742);
    }

    public static int getBorderRgb() {
        return readHex("border", 2765122);
    }

    public static int getTextRgb() {
        return readHex("text", 15592944);
    }

    public static int getMutedRgb() {
        return readHex("muted", 9145235);
    }

    public static boolean isLight() {
        int var25 = 0;
        int var0 = getSurfaceRgb();
        int var10 = ((var0 >> 16) & 255);
        int var13 = ((var0 >> 8) & 255);
        int var15 = (var0 & 255);
        double var30 = ((double) var10);
        double var18 = (0.299 * var30);
        double var42 = ((double) var13);
        double var34 = (0.587 * var42);
        double var20 = (var18 + var34);
        double var45 = ((double) var15);
        double var38 = (0.114 * var45);
        if (Double.compare((var20 + var38), 140.0) > 0) {
            var25 = 1;
        } else {
            var25 = 0;
        }
        return var25;
    }

    public static int resolveRgb(Object arg0, int arg1, boolean arg2) {
        int var6 = themedRgb(arg0, arg2);
        if (var6 >= 0) {
            return var6;
        }
        return parseHexRgb(arg0, arg1);
    }

    public static int resolveArgb(Object arg0, int arg1, boolean arg2) {
        int var17 = 0;
        int var12 = 0;
        if (isThemeValue(arg0)) {
            if (arg0.toString().trim().equalsIgnoreCase("theme2")) {
                var12 = getAccent2Argb();
            } else {
                var12 = getAccentArgb();
            }
            return var12;
        }
        if (isEnabled()) {
            if (arg2) {
                var17 = getAccent2Argb();
            } else {
                var17 = getAccentArgb();
            }
            return var17;
        }
        return parseArgb(arg0, arg1);
    }

    public static boolean isThemeValue(Object arg0) {
        int var12 = 0;
        if (arg0 == null) {
            return false;
        }
        String var7 = arg0.toString().trim();
        if (!var7.equalsIgnoreCase("theme")) {
            if (var7.equalsIgnoreCase("theme2")) {
            }
        }
        var12 = 1;
        var12 = 0;
        return var12;
    }

    private static int themedRgb(Object arg0, boolean arg1) {
        int var14 = 0;
        int var15 = 0;
        if (isThemeValue(arg0)) {
            if (arg0.toString().trim().equalsIgnoreCase("theme2")) {
                var14 = getAccent2Rgb();
            } else {
                var14 = getAccentRgb();
            }
            return var14;
        }
        if (isEnabled()) {
            if (arg1) {
                var15 = getAccent2Rgb();
            } else {
                var15 = getAccentRgb();
            }
            return var15;
        }
        return -1;
    }

    private static boolean looksLikeHex(String arg0) {
        int var4 = 0;
        String var8 = null;
        if (arg0 == null) {
            return false;
        }
        if (arg0.startsWith("#")) {
            var8 = arg0.substring(1);
        } else {
            var8 = arg0;
        }
        String var3 = var8;
        if (var3.length() != 6) {
            if (var3.length() != 8) {
                return false;
            }
        }
        var4 = 0;
        while (true) {
            int var15 = var4;
            int var26 = var3.length();
            if (var15 >= var26) break;
            int var27 = var4;
            if (Character.digit(var3.charAt(var27), 16) < 0) {
                return false;
            }
            var4 = (var4 + 1);
        }
        return true;
    }

    public static int parseHexRgb(Object arg0, int arg1) {
        String var4 = null;
        if (arg0 == null) {
            return arg1;
        }
        var4 = arg0.toString().trim();
        if (var4.startsWith("#")) {
            var4 = var4.substring(1);
        }
        try {
            if (var4.length() == 6) {
                int var19 = (((int) Long.parseLong(var4, 16)) & 16777215);
            }
        } catch (NumberFormatException ignored) {
        }
        return var19;
        try {
            if (var4.length() == 8) {
                int var28 = (((int) Long.parseLong(var4, 16)) & 16777215);
            }
        } catch (NumberFormatException ignored) {
        }
        return var28;
        return arg1;
    }

    public static int parseArgb(Object arg0, int arg1) {
        String var6 = null;
        if (arg0 == null) {
            return arg1;
        }
        var6 = arg0.toString().trim();
        if (var6.startsWith("#")) {
            var6 = var6.substring(1);
        }
        try {
            if (var6.length() == 6) {
                long var35 = Long.parseLong(var6, 16);
                int var30 = ((int) (4278190080L | var35));
            }
        } catch (NumberFormatException ignored) {
        }
        return var30;
        try {
            if (var6.length() == 8) {
                int var23 = ((int) Long.parseLong(var6, 16));
            }
        } catch (NumberFormatException ignored) {
        }
        return var23;
        return arg1;
    }

}
