package need.clarity.modules.movement;

import need.clarity.PerFrameEvents;
import need.clarity.api.Hooks;
import need.clarity.modules.ModuleBase;
import need.clarity.modules.combat.AimAssist;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;

public class SpinBot extends ModuleBase {
    public static SpinBot INSTANCE;
    private boolean enabledState;
    private int counterValue;
    private float floatValue;

    public SpinBot() {
        super("Spin Bot", "Movement");
        INSTANCE = this;
        Hooks.Bool0 var9 = () -> SpinBot.isClientMode();
        Hooks.bool0("SpinBot.clientMode", var9);
        Hooks.Dbl1 var10 = (p0) -> SpinBot.hook$clientMode(p0);
        Hooks.dbl1("SpinBot.yaw", var10);
        PerFrameEvents.register(() -> this.applyStep());
    }

    public void onEnable() {
        this.enabledState = false;
    }

    public void onDisable() {
        this.enabledState = false;
    }

    public static boolean isClientMode() {
        int var10 = 0;
        SpinBot var0 = INSTANCE;
        if (var0 != null) {
            if (var0.isEnabled()) {
                Object var11 = var0.getSetting("Mode", "Server");
                if ("Client".equals(var11)) {
                    var10 = 1;
                }
            } else {
            }
        }
        var10 = 0;
        return var10;
    }

    public static float yaw(float arg0) {
        SpinBot var1 = INSTANCE;
        Minecraft var6 = Minecraft.getInstance();
        if (var1 != null) {
            if (var6.player == null) {
            }
        }
        return 0.0F;
        LocalPlayer var13 = var6.player;
        return var1.computeFloatMetric(var13, arg0);
    }

    private float computeFloatMetric(LocalPlayer arg0, float arg1) {
        double var12 = 0.0;
        float var57 = 0.0F;
        if (!this.enabledState) {
            this.enabledState = true;
            int var59 = arg0.tickCount;
            this.counterValue = var59;
            float var61 = arg0.getYRot();
            this.floatValue = var61;
        }
        Float var96 = 30.0F;
        float var78 = ((Float) this.getSetting("Speed", var96));
        float var63 = Math.min(180.0F, var78);
        float var20 = Math.max(1.0F, var63);
        Object var65 = this.getSetting("Direction", "Right");
        if ("Left".equals(var65)) {
            var57 = -1.0F;
        } else {
            var57 = 1.0F;
        }
        float var9 = var57;
        int var24 = arg0.tickCount;
        int var67 = this.counterValue;
        double var28 = ((double) (((float) (var24 - var67)) + arg1));
        double var81 = ((double) var20);
        var12 = (var28 * var81);
        if (Double.compare(var12, 360000.0) > 0) {
            double var86 = ((double) var20);
            int var41 = ((int) (360000.0 / var86));
            int var70 = (this.counterValue + var41);
            this.counterValue = var70;
            int var44 = arg0.tickCount;
            int var72 = this.counterValue;
            double var48 = ((double) (((float) (var44 - var72)) + arg1));
            double var90 = ((double) var20);
            var12 = (var48 * var90);
        }
        float var55 = this.floatValue;
        float var94 = ((float) var12);
        float var75 = (var9 * var94);
        return (var55 + var75);
    }

    private void applyStep() {
        float var56 = 0.0F;
        int var12 = 0;
        if (!this.isEnabled()) {
            return;
        }
        Minecraft var14 = Minecraft.getInstance();
        LocalPlayer var16 = var14.player;
        if (var16 != null) {
            if (var14.level == null) {
            }
        }
        this.enabledState = false;
        return;
        float var22 = var14.getDeltaTracker().getGameTimeDeltaPartialTick(false);
        float var24 = this.computeFloatMetric(var16, var22);
        Object var59 = this.getSetting("Mode", "Server");
        if ("Client".equals(var59)) {
            var16.yBodyRotO = var24;
            var16.yBodyRot = var24;
            var16.yHeadRotO = var24;
            var16.yHeadRot = var24;
            return;
        }
        Boolean var73 = true;
        if (((Boolean) this.getSetting("Yield To Combat", var73))) {
            if (AimAssist.isActivelyTracking()) {
                return;
            }
        }
        String var38 = ((String) this.getSetting("Pitch", "Real"));
        var12 = -1;
        switch (var38.hashCode()) {
            case 2747 -> {}
            case 2136258 -> {}
            case 2781896 -> {}
            default -> {}
        }
        if (var38.equals("Up")) {
            var12 = 1;
            if (var38.equals("Down")) {
                var12 = 0;
                if (var38.equals("Zero")) {
                    var12 = 2;
                }
                switch (var12) {
                    case 0 -> {}
                    case 1 -> {}
                    case 2 -> {}
                    default -> {}
                }
                var56 = 0.0F;
                var56 = -90.0F;
                var56 = 90.0F;
                var56 = var16.getXRot();
                float var10 = var56;
                AimAssist.applyStep(var24, var10);
                return;
            }
        }
    }

}
