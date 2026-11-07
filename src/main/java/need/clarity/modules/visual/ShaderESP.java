package need.clarity.modules.visual;

import need.clarity.modules.ModuleBase;
import need.clarity.util.ThemeColors;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.scores.PlayerTeam;

public class ShaderESP extends ModuleBase {
    public static final String[] fieldVal;
    private static ShaderESP INSTANCE;

    public ShaderESP() {
        super("Shader ESP", "Visual");
        INSTANCE = this;
    }

    public static boolean isEligible() {
        int var5 = 0;
        ShaderESP var0 = INSTANCE;
        if (var0 != null) {
            if (var0.isEnabled()) {
                var5 = 1;
            } else {
            }
        }
        var5 = 0;
        return var5;
    }

    public static String resolveLabel() {
        int var5 = 0;
        String var8 = ((String) evaluate("Mode", "Glow"));
        String[] var9 = fieldVal;
        int var11 = var9.length;
        var5 = 0;
        while (true) {
            if (var5 >= var11) break;
            int var21 = var5;
            String var15 = var9[var21];
            if (var15.equals(var8)) {
                return var15;
            }
            var5 = (var5 + 1);
        }
        return "Glow";
    }

    public static String resolveLabel1() {
        return ((String) evaluate("Color Mode", "Custom"));
    }

    public static boolean isEligible1() {
        String var1 = resolveLabel1();
        return "Chroma".equals(var1);
    }

    public static float computeFloatMetric() {
        Float var7 = 12.0F;
        return computeFloatMetric(((Float) evaluate("Glow Radius", var7)), 1.0F, 60.0F);
    }

    public static float computeFloatMetric1() {
        Float var7 = 1.0F;
        return computeFloatMetric(((Float) evaluate("Intensity", var7)), 0.0F, 3.0F);
    }

    public static float computeFloatMetric2() {
        Float var7 = 0.15F;
        return computeFloatMetric(((Float) evaluate("Fill Opacity", var7)), 0.0F, 1.0F);
    }

    public static boolean isEligible2() {
        Boolean var5 = true;
        return ((Boolean) evaluate("Outline", var5));
    }

    public static float computeFloatMetric3() {
        Float var7 = 10.0F;
        return computeFloatMetric(((Float) evaluate("Blur", var7)), 0.0F, 40.0F);
    }

    public static float computeFloatMetric4() {
        Float var7 = 0.35F;
        return computeFloatMetric(((Float) evaluate("Tint Amount", var7)), 0.0F, 1.0F);
    }

    public static float computeFloatMetric5() {
        Float var7 = 1.0F;
        return computeFloatMetric(((Float) evaluate("Speed", var7)), 0.0F, 5.0F);
    }

    public static boolean isEligible3() {
        int var5 = 0;
        Boolean var6 = false;
        if (!((Boolean) evaluate("Self", var6))) {
            var5 = 1;
        } else {
            var5 = 0;
        }
        return var5;
    }

    public static boolean isEligible4() {
        Boolean var5 = false;
        return ((Boolean) evaluate("Hide Teammates", var5));
    }

    public static boolean isEligible5() {
        Boolean var5 = true;
        return ((Boolean) evaluate("Show Invisible", var5));
    }

    public static float computeFloatMetric6() {
        Float var7 = 0.0F;
        float var6 = ((Float) evaluate("Max Distance", var7));
        return Math.max(0.0F, var6);
    }

    public static int getColor() {
        if (INSTANCE == null) {
            return 9075967;
        }
        return ThemeColors.resolveRgb(INSTANCE.getSetting("Color", null), 9075967, false);
    }

    public static boolean isEligible(Player arg0) {
        int var33 = 0;
        Minecraft var1 = Minecraft.getInstance();
        if (var1 != null) {
            if (var1.player != null) {
                if (arg0 == null) {
                }
            }
            return false;
            LocalPlayer var11 = var1.player;
            if (arg0 == var11) {
                if (isEligible3()) {
                    return false;
                }
            }
            if (arg0.isAlive()) {
                if (arg0.isSpectator()) {
                }
            }
            return false;
            if (arg0.isInvisible()) {
                if (!isEligible5()) {
                    return false;
                }
            }
            if (arg0 != var11) {
                if (isEligible4()) {
                    if (isEligible(arg0, var11)) {
                        return false;
                    }
                }
                float var27 = computeFloatMetric6();
                if (Float.compare(var27, 0.5F) > 0) {
                    if (Float.compare(var11.distanceTo(arg0), var27) <= 0) {
                    }
                }
                var33 = 1;
                var33 = 0;
                return var33;
            }
        }
    }

    public static int computeColorOrIndex(Player arg0) {
        int var7 = 0;
        int var40 = 0;
        float var27 = 0.0F;
        String var1 = resolveLabel1();
        var7 = -1;
        switch (var1.hashCode()) {
            case -2137395588 -> {}
            case 2602621 -> {}
            default -> {}
        }
        if (var1.equals("Team")) {
            var7 = 0;
            if (var1.equals("Health")) {
                var7 = 1;
            }
            switch (var7) {
                case 0 -> {}
                case 1 -> {}
                default -> {}
            }
            if (Float.compare(arg0.getMaxHealth(), 0.0F) > 0) {
                float var38 = arg0.getHealth();
                float var56 = arg0.getMaxHealth();
                var27 = computeFloatMetric((var38 / var56), 0.0F, 1.0F);
            } else {
                var27 = 1.0F;
            }
            float var66 = var27;
            float var64 = (2.0F * var66);
            float var59 = (2.0F - var64);
            float var48 = Math.min(1.0F, var59);
            int var30 = Math.round((255.0F * var48));
            float var61 = (2.0F * var66);
            float var50 = Math.min(1.0F, var61);
            int var33 = Math.round((255.0F * var50));
            int var35 = (var30 << 16);
            int var53 = (var33 << 8);
            var40 = ((var35 | var53) | 48);
            int var20 = (arg0.getTeamColor() & 16777215);
            if (var20 == 16777215) {
                if (arg0.getTeam() == null) {
                    var40 = getColor();
                }
            } else {
                var40 = var20;
                var40 = getColor();
            }
            return var40;
        }
    }

    private static Object evaluate(String arg0, Object arg1) {
        ShaderESP var2 = INSTANCE;
        if (var2 == null) {
            return arg1;
        }
        Object var10 = var2.getSetting(arg0, arg1);
        if ((arg1 instanceof Float)) {
            if ((var10 instanceof Number)) {
                return ((Number) var10);
            }
        }
        if ((arg1 instanceof Boolean)) {
            if (!(var10 instanceof Boolean)) {
                return arg1;
            }
        }
        if ((arg1 instanceof String)) {
            if (!(var10 instanceof String)) {
                return arg1;
            }
        }
        return var10;
    }

    private static float computeFloatMetric(float arg0, float arg1, float arg2) {
        float var8 = 0.0F;
        if (Float.isNaN(arg0)) {
            var8 = arg1;
        } else {
            float var9 = Math.min(arg2, arg0);
            var8 = Math.max(arg1, var9);
        }
        return var8;
    }

    private static boolean isEligible(Player arg0, Player arg1) {
        int var18 = 0;
        PlayerTeam var7 = arg0.getTeam();
        PlayerTeam var9 = arg1.getTeam();
        if (var7 != null) {
            if (var9 == null) {
            }
        }
        return false;
        String var13 = var7.getName();
        if (var13 != null) {
            String var19 = var9.getName();
            if (var13.equals(var19)) {
                var18 = 1;
            }
        } else {
            var18 = 0;
        }
        return var18;
    }

    static {
        String[] var3 = new String[6];
        var3[0] = "Glow";
        var3[1] = "Blur";
        var3[2] = "Galaxy";
        var3[3] = "Prism";
        var3[4] = "Neon";
        var3[5] = "Scan";
        fieldVal = var3;
    }

}
