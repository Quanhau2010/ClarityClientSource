package need.clarity.clickgui;

import java.util.Map;

public final class Theme {
    public int accent;
    public int accent2;
    public int bg;
    public int surface;
    public int border;
    public int text;
    public int muted;
    public boolean light;

    public Theme() {
    }

    public static Theme from(Map arg0) {
        Theme var1 = new Theme();
        var1.accent = (-16777216 | hex(((String) arg0.get("accent")), 8037111));
        var1.accent2 = (-16777216 | hex(((String) arg0.get("accent2")), 4020641));
        var1.bg = (-16777216 | hex(((String) arg0.get("background")), 724503));
        var1.surface = (-16777216 | hex(((String) arg0.get("surface")), 1448742));
        var1.border = (-16777216 | hex(((String) arg0.get("border")), 2765122));
        var1.light = (luma(var1.surface) > 0.55F);
        if (var1.light) {
        } else {
        }
        -16777216.text = (((String) arg0.get("text")) | hex(1447965, 15592944));
        if (var1.light) {
        } else {
        }
        -16777216.muted = (((String) arg0.get("muted")) | hex(6120048, 9145235));
        if (var1.light) {
            if (luma(var1.text) > 0.6F) {
                var1.text = -15329251;
                var1.muted = -10657168;
            }
            return var1;
        }
    }

    public static Theme lerp(Theme arg0, Theme arg1, float arg2) {
        Theme var3 = new Theme();
        var3.accent = mix(arg0.accent, arg1.accent, arg2);
        var3.accent2 = mix(arg0.accent2, arg1.accent2, arg2);
        var3.bg = mix(arg0.bg, arg1.bg, arg2);
        var3.surface = mix(arg0.surface, arg1.surface, arg2);
        var3.border = mix(arg0.border, arg1.border, arg2);
        var3.text = mix(arg0.text, arg1.text, arg2);
        var3.muted = mix(arg0.muted, arg1.muted, arg2);
        if (arg2 < 0.5F) {
        } else {
        }
        arg0.light.light = arg1.light;
        return var3;
    }

    public static int hex(String arg0, int arg1) {
        if (arg0 == null) {
            return arg1;
        }
        arg0 = arg0.trim();
        if (arg0.startsWith("#")) {
            arg0 = arg0.substring(1);
        }
        if (arg0.length() == 3) {
            arg0 = (arg0.charAt(0)) + (arg0.charAt(0)) + (arg0.charAt(1)) + (arg0.charAt(1)) + (arg0.charAt(2)) + (arg0.charAt(2));
        }
        try {
        } catch (Exception ignored) {
        }
        return (Integer.parseInt(arg0.substring(0, Math.min(6, arg0.length())), 16) & 16777215);
    }

    public static String toHex(int arg0) {
        new Object[1][0] = (arg0 & 16777215);
        return String.format("#%06X", new Object[1]);
    }

    public static int a(int arg0, float arg1) {
        return ((Math.round((((float) ((arg0 >>> 24) & 255)) * Math.max(0.0F, Math.min(1.0F, arg1)))) << 24) | (arg0 & 16777215));
    }

    public static int mix(int arg0, int arg1, float arg2) {
        arg2 = Math.max(0.0F, Math.min(1.0F, arg2));
        int var3 = Math.round((((float) ((arg0 >>> 24) & 255)) + (((float) (((arg1 >>> 24) & 255) - ((arg0 >>> 24) & 255))) * arg2)));
        int var4 = Math.round((((float) ((arg0 >> 16) & 255)) + (((float) (((arg1 >> 16) & 255) - ((arg0 >> 16) & 255))) * arg2)));
        int var5 = Math.round((((float) ((arg0 >> 8) & 255)) + (((float) (((arg1 >> 8) & 255) - ((arg0 >> 8) & 255))) * arg2)));
        int var6 = Math.round((((float) (arg0 & 255)) + (((float) ((arg1 & 255) - (arg0 & 255))) * arg2)));
        return ((((var3 << 24) | (var4 << 16)) | (var5 << 8)) | var6);
    }

    public static float luma(int arg0) {
        return ((((((float) ((arg0 >> 16) & 255)) * 0.299F) + (((float) ((arg0 >> 8) & 255)) * 0.587F)) + (((float) (arg0 & 255)) * 0.114F)) / 255.0F);
    }

    public static int onColor(int arg0) {
        if (luma(arg0) > 0.62F) {
        } else {
        }
        return -1;
    }

    public static float[] rgbToHsv(int arg0) {
        float var7 = 0.0F;
        float var1 = (((float) ((arg0 >> 16) & 255)) / 255.0F);
        float var2 = (((float) ((arg0 >> 8) & 255)) / 255.0F);
        float var3 = (((float) (arg0 & 255)) / 255.0F);
        float var4 = Math.max(var1, Math.max(var2, var3));
        float var5 = Math.min(var1, Math.min(var2, var3));
        float var6 = (var4 - var5);
        var7 = 0.0F;
        if (var6 > 1.0E-6F) {
            if (var4 == var1) {
                var7 = (((var2 - var3) / var6) % 6.0F);
            } else {
                if (var4 == var2) {
                    var7 = (((var3 - var1) / var6) + 2.0F);
                } else {
                    var7 = (((var1 - var2) / var6) + 4.0F);
                }
                var7 = (var7 / 6.0F);
                if (var7 < 0.0F) {
                    var7 = (var7 + 1.0F);
                }
                new float[3][0] = var7;
                if (var4 <= 0.0F) {
                } else {
                }
                1[0.0F] = (var6 / var4);
                new float[3][2] = var4;
                return new float[3];
            }
        }
    }

    public static int hsvToRgb(float arg0, float arg1, float arg2) {
        float var6 = 0.0F;
        float var7 = 0.0F;
        float var8 = 0.0F;
        arg0 = (((arg0 % 1.0F) + 1.0F) % 1.0F);
        float var3 = (arg2 * arg1);
        float var4 = (var3 * (1.0F - Math.abs((((arg0 * 6.0F) % 2.0F) - 1.0F))));
        float var5 = (arg2 - var3);
        switch (((int) (arg0 * 6.0F))) {
            case 0 -> {}
            case 1 -> {}
            case 2 -> {}
            case 3 -> {}
            case 4 -> {}
            default -> {}
        }
        var6 = var3;
        var7 = var4;
        var8 = 0.0F;
        var6 = var4;
        var7 = var3;
        var8 = 0.0F;
        var6 = 0.0F;
        var7 = var3;
        var8 = var4;
        var6 = 0.0F;
        var7 = var4;
        var8 = var3;
        var6 = var4;
        var7 = 0.0F;
        var8 = var3;
        var6 = var3;
        var7 = 0.0F;
        var8 = var4;
        return (((Math.round(((var6 + var5) * 255.0F)) << 16) | (Math.round(((var7 + var5) * 255.0F)) << 8)) | Math.round(((var8 + var5) * 255.0F)));
    }

}
