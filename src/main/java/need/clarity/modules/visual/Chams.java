package need.clarity.modules.visual;

import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import need.clarity.api.Bus;
import need.clarity.api.IChams;
import need.clarity.modules.ModuleBase;
import need.clarity.util.ThemeColors;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.Avatar;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.scores.PlayerTeam;

public class Chams extends ModuleBase {
    public static final String[] fieldVal;
    public static Chams INSTANCE;
    private static final Map entriesMap;

    public static void applyStep(Avatar arg0, AvatarRenderState arg1) {
        if (INSTANCE != null) {
            if (INSTANCE.isEnabled()) {
                if (!isEligible(arg0)) {
                }
            }
            Object var12 = entriesMap.remove(arg1);
            return;
            Map var9 = entriesMap;
            Integer var15 = computeColorOrIndex(arg0);
            Object var10 = var9.put(arg1, var15);
            return;
        }
    }

    public static Integer evaluate(LivingEntityRenderState arg0) {
        if (INSTANCE != null) {
            if (!INSTANCE.isEnabled()) {
            }
        }
        return null;
        return ((Integer) entriesMap.get(arg0));
    }

    private static boolean isEligible(Avatar arg0) {
        Minecraft var1 = Minecraft.getInstance();
        if (var1 != null) {
            if (var1.player != null) {
                if (!(arg0 instanceof Player)) {
                }
            }
            return false;
            LocalPlayer var13 = var1.player;
            if (arg0 == var13) {
                if (isHideSelf()) {
                    return false;
                }
            }
            if (arg0.isAlive()) {
                if (arg0.isSpectator()) {
                }
            }
            return false;
            if (arg0 != var13) {
                if (isHideTeammates()) {
                    if (isEligible(arg0, var13)) {
                        return false;
                    }
                }
                float var27 = getMaxDistance();
                if (Float.compare(var27, 0.5F) > 0) {
                    if (Float.compare(var13.distanceTo(arg0), var27) > 0) {
                        return false;
                    }
                }
                return true;
            }
        }
    }

    private static int computeColorOrIndex(Avatar arg0) {
        int var69 = 0;
        if (isEligible()) {
            int var41 = Math.round((computeFloatMetric(getNormalOpacity()) * 255.0F));
            return ((Math.max(8, var41) << 24) | 16777215);
        }
        if (isChroma()) {
            double var26 = (((double) System.nanoTime()) / 1.0E9);
            double var55 = ((double) getChromaSpeed());
            double var30 = (var26 * var55);
            double var60 = (arg0.getX() * 0.03);
            double var32 = (var30 + var60);
            double var65 = (arg0.getZ() * 0.03);
            var69 = computeColorOrIndex(((float) (var32 + var65)));
        } else {
            var69 = (getColor() & 16777215);
        }
        int var48 = Math.round((computeFloatMetric(getOpacity()) * 255.0F));
        int var20 = (Math.max(8, var48) << 24);
        int var50 = var69;
        return (var20 | var50);
    }

    public Chams() {
        super("Chams", "Visual");
        INSTANCE = this;
        ChamsCapabilityProvider var6 = new ChamsCapabilityProvider();
        Bus.register(IChams.class, var6);
    }

    public static String getColor() {
        int var6 = 0;
        if (INSTANCE == null) {
            return "Color";
        }
        String var11 = ((String) INSTANCE.getSetting("Mode", "Color"));
        String[] var12 = fieldVal;
        int var14 = var12.length;
        var6 = 0;
        while (true) {
            if (var6 >= var14) break;
            int var24 = var6;
            if (var12[var24].equals(var11)) {
                return var11;
            }
            var6 = (var6 + 1);
        }
        return "Color";
    }

    public static boolean isEligible() {
        String var1 = getColor();
        return "Normal".equals(var1);
    }

    public static int getColor() {
        if (INSTANCE == null) {
            return -43776;
        }
        return ThemeColors.resolveArgb(((String) INSTANCE.getSetting("Color", "")), -43776, false);
    }

    public static float getOpacity() {
        float var6 = 0.0F;
        if (INSTANCE != null) {
            Chams var3 = INSTANCE;
            Float var7 = 0.85F;
            var6 = ((Float) var3.getSetting("Opacity", var7));
        } else {
            var6 = 0.85F;
        }
        return var6;
    }

    public static float getNormalOpacity() {
        float var6 = 0.0F;
        if (INSTANCE != null) {
            Chams var3 = INSTANCE;
            Float var7 = 1.0F;
            var6 = ((Float) var3.getSetting("Normal Opacity", var7));
        } else {
            var6 = 1.0F;
        }
        return var6;
    }

    public static boolean isRenderArmour() {
        int var8 = 0;
        if (!isEligible()) {
            if (INSTANCE != null) {
                Chams var4 = INSTANCE;
                Boolean var9 = false;
                if (((Boolean) var4.getSetting("Render Armour", var9))) {
                }
            }
        }
        var8 = 1;
        var8 = 0;
        return var8;
    }

    public static boolean isArmourThroughWalls() {
        int var8 = 0;
        if (!isEligible()) {
            if (INSTANCE != null) {
                Chams var4 = INSTANCE;
                Boolean var9 = true;
                if (((Boolean) var4.getSetting("Armour Through Walls", var9))) {
                }
            }
            var8 = 1;
            var8 = 0;
            return var8;
        }
    }

    public static boolean isThroughWalls() {
        int var7 = 0;
        if (INSTANCE != null) {
            Chams var3 = INSTANCE;
            Boolean var8 = true;
            if (((Boolean) var3.getSetting("Through Walls", var8))) {
                var7 = 1;
            }
        } else {
            var7 = 0;
        }
        return var7;
    }

    public static boolean isHideSelf() {
        int var7 = 0;
        if (INSTANCE != null) {
            Chams var3 = INSTANCE;
            Boolean var8 = true;
            if (((Boolean) var3.getSetting("Hide Self", var8))) {
            }
        }
        var7 = 1;
        var7 = 0;
        return var7;
    }

    public static boolean isHideTeammates() {
        int var7 = 0;
        if (INSTANCE != null) {
            Chams var3 = INSTANCE;
            Boolean var8 = false;
            if (((Boolean) var3.getSetting("Hide Teammates", var8))) {
                var7 = 1;
            } else {
            }
        }
        var7 = 0;
        return var7;
    }

    public static boolean isChroma() {
        int var7 = 0;
        if (INSTANCE != null) {
            Chams var3 = INSTANCE;
            Boolean var8 = false;
            if (((Boolean) var3.getSetting("Chroma", var8))) {
                var7 = 1;
            } else {
                var7 = 0;
            }
            return var7;
        }
    }

    public static float getChromaSpeed() {
        float var6 = 0.0F;
        if (INSTANCE != null) {
            Chams var3 = INSTANCE;
            Float var7 = 0.3F;
            var6 = ((Float) var3.getSetting("Chroma Speed", var7));
        } else {
            var6 = 0.3F;
        }
        return var6;
    }

    public static float getMaxDistance() {
        float var6 = 0.0F;
        if (INSTANCE != null) {
            Chams var3 = INSTANCE;
            Float var7 = 0.0F;
            var6 = ((Float) var3.getSetting("Max Distance", var7));
        } else {
            var6 = 0.0F;
        }
        return var6;
    }

    private static float computeFloatMetric(float arg0) {
        float var5 = Math.min(1.0F, arg0);
        return Math.max(0.0F, var5);
    }

    private static boolean isEligible(Entity arg0, Entity arg1) {
        int var18 = 0;
        PlayerTeam var7 = arg0.getTeam();
        PlayerTeam var9 = arg1.getTeam();
        if (var7 != null) {
            if (var9 == null) {
            }
        }
        return false;
        String var14 = var7.getName();
        if (var14 != null) {
            String var19 = var9.getName();
            if (var14.equals(var19)) {
                var18 = 1;
            }
        } else {
            var18 = 0;
        }
        return var18;
    }

    private static int computeColorOrIndex(float arg0) {
        float var75 = 0.0F;
        float var76 = 0.0F;
        float var77 = 0.0F;
        float var52 = ((float) Math.floor(((double) arg0)));
        float var12 = ((arg0 - var52) * 6.0F);
        int var14 = ((int) var12);
        float var55 = ((float) var14);
        float var16 = (var12 - var55);
        float var57 = (1.0F - var16);
        float var19 = (1.0F * var57);
        float var71 = (1.0F - var16);
        float var59 = (1.0F - var71);
        float var21 = (1.0F * var59);
        switch ((var14 % 6)) {
            case 0 -> {}
            case 1 -> {}
            case 2 -> {}
            case 3 -> {}
            case 4 -> {}
            default -> {}
        }
        var75 = var21;
        var76 = 0.0F;
        var77 = 1.0F;
        var75 = 0.0F;
        var76 = var19;
        var77 = 1.0F;
        var75 = 1.0F;
        var76 = var21;
        var77 = 0.0F;
        var75 = var19;
        var76 = 1.0F;
        var77 = 0.0F;
        var75 = 0.0F;
        var76 = 1.0F;
        var77 = var21;
        var75 = 1.0F;
        var76 = 0.0F;
        var77 = var19;
        int var30 = (((int) (var75 * 255.0F)) << 16);
        int var66 = (((int) (var76 * 255.0F)) << 8);
        int var31 = (var30 | var66);
        int var69 = ((int) (var77 * 255.0F));
        return (var31 | var69);
    }

    static {
        String[] var3 = new String[2];
        var3[0] = "Color";
        var3[1] = "Normal";
        fieldVal = var3;
        WeakHashMap var4 = new WeakHashMap();
        entriesMap = Collections.synchronizedMap(var4);
    }

}
