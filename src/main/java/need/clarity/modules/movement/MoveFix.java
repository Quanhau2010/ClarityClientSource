package need.clarity.modules.movement;

import need.clarity.LocalPlayerRotationState;
import need.clarity.PerMovementEvents;
import need.clarity.api.Bus;
import need.clarity.api.IMovement;
import need.clarity.api.MovementEvent;
import need.clarity.api.Rotation;
import need.clarity.modules.ModuleBase;
import net.minecraft.util.Mth;

public class MoveFix extends ModuleBase {
    private static MoveFix INSTANCE;
    private static volatile boolean enabledState;

    public MoveFix() {
        super("MoveFix", "Movement");
        INSTANCE = this;
        MoveFixCapabilityProvider var8 = new MoveFixCapabilityProvider();
        Bus.register(IMovement.class, var8);
        PerMovementEvents.register((p0) -> this.onMovementTick(p0));
    }

    public static void applyStep(boolean arg0) {
        enabledState = arg0;
    }

    public static boolean isEligible() {
        return enabledState;
    }

    public static boolean isMode() {
        int var14 = 0;
        if (INSTANCE != null) {
            if (!INSTANCE.isEnabled()) {
            }
        }
        return false;
        if (!Rotation.hasSilentRotation()) {
            return false;
        }
        String var10 = ((String) INSTANCE.getSetting("Mode", "Silent"));
        if (!"None".equals(var10)) {
            var14 = 1;
        } else {
            var14 = 0;
        }
        return var14;
    }

    public static boolean isMode1() {
        if (!isMode()) {
            return false;
        }
        if (INSTANCE == null) {
            return false;
        }
        Object var9 = INSTANCE.getSetting("Mode", "Silent");
        return "Silent".equals(var9);
    }

    private void onMovementTick(MovementEvent arg0) {
        float var10 = 0.0F;
        float var11 = 0.0F;
        float var12 = 0.0F;
        float var13 = 0.0F;
        float var14 = 0.0F;
        if (!isMode1()) {
            return;
        }
        if (!LocalPlayerRotationState.silentActive) {
            return;
        }
        arg0.syncFloatsFromBooleans();
        float var20 = arg0.forwardAmount;
        float var22 = arg0.sidewaysAmount;
        if (var20 == 0.0F) {
            if (var22 == 0.0F) {
                return;
            }
        }
        float var27 = LocalPlayerRotationState.realYaw;
        float var28 = LocalPlayerRotationState.tickServerYaw;
        float var34 = ((float) Math.toDegrees(computeDoubleMetric(var27, var20, var22)));
        var10 = 0.0F;
        var11 = 0.0F;
        var12 = 3.4028235E38F;
        var13 = -1.0F;
        while (true) {
            if (Float.compare(var13, 1.0F) > 0) break;
            var14 = -1.0F;
            while (true) {
                if (Float.compare(var14, 1.0F) > 0) break;
                if (var13 == 0.0F) {
                    if (var14 == 0.0F) {
                    }
                } else {
                    float var78 = var13;
                    float var83 = var14;
                    float var57 = ((float) Math.toDegrees(computeDoubleMetric(var28, var78, var83)));
                    float var61 = Math.abs(Mth.wrapDegrees((var34 - var57)));
                    float var80 = var12;
                    if (Float.compare(var61, var80) >= 0) {
                    } else {
                        var12 = var61;
                        var10 = var13;
                        var11 = var14;
                    }
                    var14 = (var14 + 1.0F);
                }
            }
            var13 = (var13 + 1.0F);
        }
        float var81 = var10;
        arg0.forwardAmount = var81;
        float var82 = var11;
        arg0.sidewaysAmount = var82;
        arg0.syncBooleansFromFloats();
    }

    private static double computeDoubleMetric(float arg0, float arg1, float arg2) {
        float var6 = 0.0F;
        if (Float.compare(arg1, 0.0F) < 0) {
            arg0 = (arg0 + 180.0F);
        }
        var6 = 1.0F;
        if (Float.compare(arg1, 0.0F) < 0) {
            var6 = -0.5F;
        } else {
            if (Float.compare(arg1, 0.0F) > 0) {
                var6 = 0.5F;
            }
            if (Float.compare(arg2, 0.0F) > 0) {
                float var16 = arg0;
                float var5 = var6;
                float var34 = (90.0F * var5);
                arg0 = (var16 - var34);
            }
            if (Float.compare(arg2, 0.0F) < 0) {
                float var20 = arg0;
                float var39 = var6;
                float var37 = (90.0F * var39);
                arg0 = (var20 + var37);
            }
            return Math.toRadians(((double) arg0));
        }
    }

    static {
        enabledState = false;
    }

}
