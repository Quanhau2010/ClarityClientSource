package need.clarity.modules.movement;

import need.clarity.PerMovementEvents;
import need.clarity.api.Hooks;
import need.clarity.api.MovementEvent;
import need.clarity.modules.ModuleBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import org.lwjgl.glfw.GLFW;

public class Fly extends ModuleBase {
    private static Fly INSTANCE;
    public static volatile double doubleValue;
    public static volatile double doubleValue1;
    public static volatile double doubleValue2;
    public static volatile boolean enabledState;

    public Fly() {
        super("Fly", "Movement");
        INSTANCE = this;
        Hooks.Bool0 var14 = () -> Fly.isEligible();
        Hooks.bool0("Fly.isActive", var14);
        Hooks.Bool1 var15 = (p0) -> Fly.hook$isActive(p0);
        Hooks.bool1("Fly.isJumpDown", var15);
        Hooks.Bool1 var16 = (p0) -> Fly.hook$isJumpDown(p0);
        Hooks.bool1("Fly.isDescendDown", var16);
        Hooks.Bool0 var17 = () -> Fly.isDescendDown();
        Hooks.bool0("Fly.hasPending", var17);
        Hooks.Obj0 var18 = () -> Fly.hasPending();
        Hooks.obj0("Fly.mode", var18);
        Hooks.Dbl0 var19 = () -> Fly.mode();
        Hooks.dbl0("Fly.pendingVelX", var19);
        Hooks.Dbl0 var20 = () -> Fly.pendingVelX();
        Hooks.dbl0("Fly.pendingVelY", var20);
        Hooks.Dbl0 var21 = () -> Fly.pendingVelY();
        Hooks.dbl0("Fly.pendingVelZ", var21);
        Hooks.Act0 var22 = () -> Fly.pendingVelZ();
        Hooks.act0("Fly.clearPending", var22);
    }

    public static boolean isEligible() {
        int var3 = 0;
        if (INSTANCE != null) {
            if (INSTANCE.isEnabled()) {
                var3 = 1;
            } else {
            }
        }
        var3 = 0;
        return var3;
    }

    public static String hasPending() {
        String var5 = null;
        if (INSTANCE == null) {
            var5 = "Creative";
        } else {
            var5 = ((String) INSTANCE.getSetting("Mode", "Creative"));
        }
        return var5;
    }

    public static float getSpeed() {
        float var6 = 0.0F;
        if (INSTANCE == null) {
            var6 = 5.0F;
        } else {
            Fly var3 = INSTANCE;
            Float var7 = 5.0F;
            var6 = ((Float) var3.getSetting("Speed", var7));
        }
        return var6;
    }

    public void onEnable() {
        Minecraft var1 = Minecraft.getInstance();
        if (var1.player == null) {
            return;
        }
        LocalPlayer var10 = var1.player;
        double var14 = var1.player.getDeltaMovement().x;
        double var18 = var1.player.getDeltaMovement().z;
        var10.setDeltaMovement(var14, 0.0, var18);
        enabledState = false;
    }

    public void onDisable() {
        Minecraft var1 = Minecraft.getInstance();
        if (var1.player == null) {
            return;
        }
        var1.player.setDeltaMovement(0.0, 0.0, 0.0);
        enabledState = false;
    }

    public void onTick() {
        boolean var65 = false;
        boolean var66 = false;
        boolean var67 = false;
        boolean var36 = false;
        boolean var41 = false;
        boolean var63 = false;
        Minecraft var1 = Minecraft.getInstance();
        if (var1.player == null) {
            return;
        }
        LocalPlayer var23 = var1.player;
        float var24 = getSpeed();
        MovementEvent var25 = PerMovementEvents.lastEvent;
        if (var25 != null) {
            var67 = var25.forward;
        } else {
            var67 = var1.options.keyUp.isDown();
        }
        int var13 = var67;
        if (var25 != null) {
            var63 = var25.back;
        } else {
            var63 = var1.options.keyDown.isDown();
        }
        int var14 = var63;
        if (var25 != null) {
            var65 = var25.left;
        } else {
            var65 = var1.options.keyLeft.isDown();
        }
        int var15 = var65;
        if (var25 != null) {
            var36 = var25.right;
        } else {
            var36 = var1.options.keyRight.isDown();
        }
        int var16 = var36;
        if (var25 != null) {
            var66 = var25.jump;
        } else {
            var66 = var1.options.keyJump.isDown();
        }
        int var17 = var66;
        if (var25 != null) {
            var41 = var25.sneak;
        } else {
            var41 = var1.options.keyShift.isDown();
        }
        int var18 = var41;
        String var42 = hasPending();
        if ("Full".equals(var42)) {
            applyStep(var23, var24, var13, var14, var15, var16, var17, var18);
        } else {
            applyStep1(var23, var24, var13, var14, var15, var16, var17, var18);
        }
        enabledState = true;
    }

    private static void applyStep(LocalPlayer arg0, float arg1, boolean arg2, boolean arg3, boolean arg4, boolean arg5, boolean arg6, boolean arg7) {
        double var18 = 0.0;
        double var20 = 0.0;
        double var152 = 0.0;
        float var111 = 0.0F;
        float var112 = 0.0F;
        float var114 = 0.0F;
        float var120 = 0.0F;
        double var121 = ((double) arg1);
        double var30 = (0.05 * var121);
        if (arg2) {
            var111 = 1.0F;
        } else {
            var111 = 0.0F;
        }
        if (arg3) {
            var120 = 1.0F;
        } else {
            var120 = 0.0F;
        }
        float var33 = (var111 - var120);
        if (arg4) {
            var112 = 1.0F;
        } else {
            var112 = 0.0F;
        }
        if (arg5) {
            var114 = 1.0F;
        } else {
            var114 = 0.0F;
        }
        float var35 = (var112 - var114);
        var18 = 0.0;
        var20 = 0.0;
        if (var33 == 0.0F) {
            if (var35 != 0.0F) {
            }
        }
        float var45 = (var33 * var33);
        float var119 = (var35 * var35);
        double var49 = Math.sqrt(((double) (var45 + var119)));
        float var56 = ((float) (((double) var33) / var49));
        float var62 = ((float) (((double) var35) / var49));
        double var67 = Math.toRadians(((double) arg0.getYRot()));
        double var71 = Math.sin(var67);
        double var75 = Math.cos(var67);
        double var78 = ((double) var56);
        double var130 = -(var71);
        double var80 = (var78 * var130);
        double var135 = (((double) var62) * var75);
        var18 = ((var80 + var135) * var30);
        double var89 = (((double) var56) * var75);
        double var144 = (((double) var62) * var71);
        var20 = ((var89 + var144) * var30);
        var152 = 0.0;
        if (arg6) {
            var152 = var30;
        }
        if (arg7) {
            var152 = -(var30);
        }
        doubleValue = var18;
        doubleValue1 = var152;
        doubleValue2 = var20;
    }

    private static void applyStep1(LocalPlayer arg0, float arg1, boolean arg2, boolean arg3, boolean arg4, boolean arg5, boolean arg6, boolean arg7) {
        double var18 = 0.0;
        double var20 = 0.0;
        double var152 = 0.0;
        float var35 = 0.0F;
        float var112 = 0.0F;
        float var114 = 0.0F;
        float var120 = 0.0F;
        double var121 = ((double) arg1);
        double var30 = (0.05 * var121);
        if (arg2) {
            var112 = 1.0F;
        } else {
            var112 = 0.0F;
        }
        if (arg3) {
            var120 = 1.0F;
        } else {
            var120 = 0.0F;
        }
        float var33 = (var112 - var120);
        if (arg4) {
            var35 = 1.0F;
        } else {
            var35 = 0.0F;
        }
        if (arg5) {
            var114 = 1.0F;
        } else {
            var114 = 0.0F;
        }
        float var36 = (var35 - var114);
        var18 = 0.0;
        var20 = 0.0;
        if (var33 == 0.0F) {
            if (var36 != 0.0F) {
            }
        }
        float var46 = (var33 * var33);
        float var119 = (var36 * var36);
        double var50 = Math.sqrt(((double) (var46 + var119)));
        float var57 = ((float) (((double) var33) / var50));
        float var63 = ((float) (((double) var36) / var50));
        double var68 = Math.toRadians(((double) arg0.getYRot()));
        double var72 = Math.sin(var68);
        double var76 = Math.cos(var68);
        double var79 = ((double) var57);
        double var130 = -(var72);
        double var81 = (var79 * var130);
        double var135 = (((double) var63) * var76);
        var18 = ((var81 + var135) * var30);
        double var90 = (((double) var57) * var76);
        double var144 = (((double) var63) * var72);
        var20 = ((var90 + var144) * var30);
        var152 = 0.0;
        if (arg6) {
            var152 = var30;
        }
        if (arg7) {
            var152 = -(var30);
        }
        doubleValue = var18;
        doubleValue1 = var152;
        doubleValue2 = var20;
    }

    public static boolean isEligible(Minecraft arg0) {
        int var12 = 0;
        if (GLFW.glfwGetKey(arg0.getWindow().handle(), 32) == 1) {
            var12 = 1;
        } else {
            var12 = 0;
        }
        return var12;
    }

    public static boolean isEligible1(Minecraft arg0) {
        MovementEvent var1 = PerMovementEvents.lastEvent;
        if (var1 != null) {
            return var1.sneak;
        }
        return arg0.options.keyShift.isDown();
    }

    static {
        doubleValue = 0.0;
        doubleValue1 = 0.0;
        doubleValue2 = 0.0;
        enabledState = false;
    }

}
