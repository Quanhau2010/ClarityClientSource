package need.clarity.modules.visual;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.ArrayList;
import java.util.Iterator;
import need.clarity.WorldRenderEvent;
import need.clarity.api.Hooks;
import need.clarity.modules.ModuleBase;
import need.clarity.render.PlayerESPRenderer;
import need.clarity.util.ClarityRenderUtils;
import need.clarity.util.ThemeColors;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.scores.PlayerTeam;

public class PlayerESP extends ModuleBase {
    public static PlayerESP INSTANCE;
    private final WorldRenderEvent.Listener fieldVal;
    private boolean enabledState;

    public PlayerESP() {
        super("Player ESP", "Visual");
        WorldRenderEvent.Listener var19 = (p0, p1) -> this.renderWorldEffect(p0, p1);
        this.fieldVal = var19;
        this.enabledState = false;
        INSTANCE = this;
        if (this.isEnabled()) {
            this.applyStep();
        }
        Hooks.Obj0 var21 = () -> PlayerESP.getMode();
        Hooks.obj0("PlayerESP.mode", var21);
        Hooks.Obj0 var22 = () -> PlayerESP.mode();
        Hooks.obj0("PlayerESP.boxStyle", var22);
        Hooks.Bool0 var23 = () -> PlayerESP.boxStyle();
        Hooks.bool0("PlayerESP.outlineEnabled", var23);
        Hooks.Dbl0 var24 = () -> PlayerESP.outlineEnabled();
        Hooks.dbl0("PlayerESP.outlineThickness", var24);
        Hooks.Bool0 var25 = () -> PlayerESP.outlineThickness();
        Hooks.bool0("PlayerESP.fillEnabled", var25);
        Hooks.Dbl0 var26 = () -> PlayerESP.fillEnabled();
        Hooks.dbl0("PlayerESP.fillColor", var26);
        Hooks.Dbl0 var27 = () -> PlayerESP.fillColor();
        Hooks.dbl0("PlayerESP.fillOpacity", var27);
        Hooks.Bool0 var28 = () -> PlayerESP.fillOpacity();
        Hooks.bool0("PlayerESP.throughWalls", var28);
    }

    public void onEnable() {
        this.applyStep();
    }

    public void onDisable() {
        this.applyStep1();
    }

    private void applyStep() {
        if (this.enabledState) {
            return;
        }
        this.enabledState = true;
        WorldRenderEvent.register(this.fieldVal);
    }

    private void applyStep1() {
        if (!this.enabledState) {
            return;
        }
        this.enabledState = false;
        WorldRenderEvent.unregister(this.fieldVal);
    }

    private void renderWorldEffect(PoseStack arg0, float arg1) {
        float var36 = 0.0F;
        float var57 = 0.0F;
        Vec3 var97 = null;
        int var234 = 0;
        if (!this.isEnabled()) {
            return;
        }
        Minecraft var40 = Minecraft.getInstance();
        if (var40 != null) {
            if (var40.level != null) {
                if (var40.player == null) {
                }
            }
            return;
            boolean var46 = isHideSelf();
            boolean var47 = isHideInvisible();
            boolean var48 = isHideTeammates();
            boolean var49 = isSmooth();
            boolean var50 = isHealthColor();
            boolean var51 = isChroma();
            float var52 = getChromaSpeed();
            float var53 = getMaxDistance();
            boolean var54 = isDistanceFade();
            if (Float.compare(var53, 0.5F) > 0) {
                var57 = var53;
            } else {
                var57 = 32.0F;
            }
            float var21 = var57;
            int var58 = getOutlineColor();
            int var59 = getHealthLow();
            int var60 = getHealthHigh();
            double var65 = (((double) System.nanoTime()) / 1.0E9);
            Vec3 var67 = ClarityRenderUtils.getCameraPos();
            LocalPlayer var69 = var40.player;
            ArrayList var70 = new ArrayList();
            Iterator var74 = var40.level.players().iterator();
            while (true) {
                if (!var74.hasNext()) break;
                Player var82 = ((Player) var74.next());
                if (var82 == var69) {
                    if (var46) {
                    }
                }
                if (!var82.isAlive()) continue;
                if (var82.isSpectator()) {
                }
                if (var47) {
                    if (var82.isInvisible()) {
                    }
                }
                if (var48) {
                    if (isEligible(var82, var69)) {
                    }
                }
                if (var49) {
                    var97 = var82.getPosition(arg1);
                } else {
                    var97 = var82.position();
                }
                Vec3 var32 = var97;
                AABB var99 = var82.getBoundingBox();
                double var158 = var32.x;
                double var208 = var82.getX();
                double var160 = (var158 - var208);
                double var211 = var32.y;
                double var225 = var82.getY();
                double var213 = (var211 - var225);
                double var228 = var32.z;
                double var232 = var82.getZ();
                double var230 = (var228 - var232);
                AABB var100 = var99.move(var160, var213, var230);
                double var102 = var67.distanceTo(var32);
                if (Float.compare(var53, 0.5F) > 0) {
                    double var178 = ((double) var53);
                    if (Double.compare(var102, var178) > 0) {
                    }
                }
                var36 = 1.0F;
                if (var54) {
                    double var216 = ((double) var21);
                    double var182 = (var102 / var216);
                    var36 = ((float) (1.0 - var182));
                    if (Float.compare(var36, 0.0F) < 0) {
                        var36 = 0.0F;
                    }
                    if (Float.compare(var36, 1.0F) > 0) {
                        var36 = 1.0F;
                    }
                    if (var51) {
                        double var185 = ((double) var52);
                        double var125 = (var65 * var185);
                        double var190 = (var32.x * 0.03);
                        double var127 = (var125 + var190);
                        double var195 = (var32.y * 0.02);
                        double var129 = (var127 + var195);
                        double var200 = (var32.z * 0.03);
                        var234 = computeColorOrIndex(((float) (var129 + var200)), 1.0F, 1.0F);
                    } else {
                        if (var50) {
                            float var146 = var82.getHealth();
                            float var205 = var82.getMaxHealth();
                            float var173 = Math.max(1.0F, var205);
                            float var207 = computeFloatMetric((var146 / var173));
                            var234 = computeColorOrIndex(var59, var60, var207);
                        } else {
                            var234 = (var58 & 16777215);
                        }
                        float var167 = var36;
                        int var140 = (Math.round((255.0F * var167)) << 24);
                        int var170 = (var234 & 16777215);
                        int var141 = (var140 | var170);
                        ClarityRenderUtils.ESPEntry var171 = new ClarityRenderUtils.ESPEntry(var100, var141);
                        boolean var143 = var70.add(var171);
                    }
                }
            }
            if (var70.isEmpty()) {
                return;
            }
            PlayerESPRenderer.render(arg0, var70, arg1);
            return;
        }
    }

    private static boolean isEligible(Player arg0, Player arg1) {
        int var21 = 0;
        PlayerTeam var8 = arg0.getTeam();
        PlayerTeam var10 = arg1.getTeam();
        if (var8 != null) {
            if (var10 == null) {
            }
        }
        return false;
        String var14 = var8.getName();
        String var16 = var10.getName();
        if (var14 != null) {
            if (var14.equals(var16)) {
                var21 = 1;
            }
        } else {
            var21 = 0;
        }
        return var21;
    }

    private static float computeFloatMetric(float arg0) {
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

    private static int computeColorOrIndex(int arg0, int arg1, float arg2) {
        float var18 = (((float) ((arg0 >> 16) & 255)) / 255.0F);
        float var23 = (((float) ((arg0 >> 8) & 255)) / 255.0F);
        float var27 = (((float) (arg0 & 255)) / 255.0F);
        float var32 = (((float) ((arg1 >> 16) & 255)) / 255.0F);
        float var37 = (((float) ((arg1 >> 8) & 255)) / 255.0F);
        float var41 = (((float) (arg1 & 255)) / 255.0F);
        float var75 = ((var32 - var18) * arg2);
        int var45 = Math.round(((var18 + var75) * 255.0F));
        float var79 = ((var37 - var23) * arg2);
        int var49 = Math.round(((var23 + var79) * 255.0F));
        float var83 = ((var41 - var27) * arg2);
        int var53 = Math.round(((var27 + var83) * 255.0F));
        int var55 = (var45 << 16);
        int var87 = (var49 << 8);
        return ((var55 | var87) | var53);
    }

    private static int computeColorOrIndex(float arg0, float arg1, float arg2) {
        float var91 = 0.0F;
        float var92 = 0.0F;
        float var93 = 0.0F;
        float var66 = ((float) Math.floor(((double) arg0)));
        float var18 = ((arg0 - var66) * 6.0F);
        int var20 = ((int) var18);
        float var69 = ((float) var20);
        float var22 = (var18 - var69);
        float var71 = (1.0F - arg1);
        float var24 = (arg2 * var71);
        float var85 = (arg1 * var22);
        float var73 = (1.0F - var85);
        float var26 = (arg2 * var73);
        float var90 = (1.0F - var22);
        float var87 = (arg1 * var90);
        float var75 = (1.0F - var87);
        float var28 = (arg2 * var75);
        switch ((var20 % 6)) {
            case 0 -> {}
            case 1 -> {}
            case 2 -> {}
            case 3 -> {}
            case 4 -> {}
            default -> {}
        }
        var91 = arg2;
        var92 = var28;
        var93 = var24;
        var91 = var24;
        var92 = var26;
        var93 = arg2;
        var91 = var26;
        var92 = arg2;
        var93 = var24;
        var91 = var24;
        var92 = arg2;
        var93 = var28;
        var91 = var28;
        var92 = var24;
        var93 = arg2;
        var91 = arg2;
        var92 = var24;
        var93 = var26;
        int var36 = Math.round((var91 * 255.0F));
        int var39 = Math.round((var92 * 255.0F));
        int var42 = Math.round((var93 * 255.0F));
        int var44 = (var36 << 16);
        int var82 = (var39 << 8);
        return ((var44 | var82) | var42);
    }

    public static String getMode() {
        String var14 = null;
        if (INSTANCE != null) {
            var14 = ((String) INSTANCE.getSetting("Mode", "Outline"));
        } else {
            var14 = "Outline";
        }
        String var3 = var14;
        if (!"Box".equals(var3)) {
            if (!"3D".equals(var3)) {
                if ("Outline".equals(var3)) {
                }
            }
            return var3;
            return "Outline";
        }
    }

    public static boolean boxStyle() {
        PlayerESP var0 = INSTANCE;
        Boolean var6 = true;
        return ((Boolean) var0.getSetting("Outline", var6));
    }

    public static int getOutlineColor() {
        return computeColorOrIndex(((String) INSTANCE.getSetting("Outline Color", "")), -16733441, false);
    }

    public static float outlineEnabled() {
        PlayerESP var0 = INSTANCE;
        Float var6 = 2.0F;
        return ((Float) var0.getSetting("Outline Thickness", var6));
    }

    public static boolean outlineThickness() {
        PlayerESP var0 = INSTANCE;
        Boolean var6 = false;
        return ((Boolean) var0.getSetting("Fill", var6));
    }

    public static int getFillColor() {
        return computeColorOrIndex(((String) INSTANCE.getSetting("Fill Color", "")), -16733441, true);
    }

    public static float fillColor() {
        PlayerESP var0 = INSTANCE;
        Float var6 = 0.25F;
        return ((Float) var0.getSetting("Fill Opacity", var6));
    }

    public static String mode() {
        return ((String) INSTANCE.getSetting("Box Style", "Full"));
    }

    public static boolean isChroma() {
        PlayerESP var0 = INSTANCE;
        Boolean var6 = false;
        return ((Boolean) var0.getSetting("Chroma", var6));
    }

    public static float getChromaSpeed() {
        PlayerESP var0 = INSTANCE;
        Float var6 = 0.3F;
        return ((Float) var0.getSetting("Chroma Speed", var6));
    }

    public static boolean isHealthColor() {
        PlayerESP var0 = INSTANCE;
        Boolean var6 = false;
        return ((Boolean) var0.getSetting("Health Color", var6));
    }

    public static int getHealthLow() {
        return computeColorOrIndex(((String) INSTANCE.getSetting("Health Low", "")), -50373, true);
    }

    public static int getHealthHigh() {
        return computeColorOrIndex(((String) INSTANCE.getSetting("Health High", "")), -12845218, false);
    }

    public static float getMaxDistance() {
        PlayerESP var0 = INSTANCE;
        Float var6 = 0.0F;
        return ((Float) var0.getSetting("Max Distance", var6));
    }

    public static boolean isDistanceFade() {
        PlayerESP var0 = INSTANCE;
        Boolean var6 = false;
        return ((Boolean) var0.getSetting("Distance Fade", var6));
    }

    public static boolean fillOpacity() {
        PlayerESP var0 = INSTANCE;
        Boolean var6 = true;
        return ((Boolean) var0.getSetting("Through Walls", var6));
    }

    public static boolean isHideInvisible() {
        PlayerESP var0 = INSTANCE;
        Boolean var6 = true;
        return ((Boolean) var0.getSetting("Hide Invisible", var6));
    }

    public static boolean isHideTeammates() {
        PlayerESP var0 = INSTANCE;
        Boolean var6 = false;
        return ((Boolean) var0.getSetting("Hide Teammates", var6));
    }

    public static boolean isHideSelf() {
        PlayerESP var0 = INSTANCE;
        Boolean var6 = true;
        return ((Boolean) var0.getSetting("Hide Self", var6));
    }

    public static boolean isSmooth() {
        PlayerESP var0 = INSTANCE;
        Boolean var6 = true;
        return ((Boolean) var0.getSetting("Smooth", var6));
    }

    private static int computeColorOrIndex(String arg0, int arg1, boolean arg2) {
        return ThemeColors.resolveArgb(arg0, arg1, arg2);
    }

}
