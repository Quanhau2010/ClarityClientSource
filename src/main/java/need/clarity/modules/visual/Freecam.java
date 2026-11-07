package need.clarity.modules.visual;

import need.clarity.PerFrameEvents;
import need.clarity.api.Bus;
import need.clarity.api.IFreecam;
import need.clarity.modules.ModuleBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class Freecam extends ModuleBase {
    private static Freecam INSTANCE;
    public static volatile Vec3 positionVec;
    public static volatile float floatValue;
    public static volatile float floatValue1;
    private static volatile Vec3 positionVec1;
    private static volatile float floatValue2;
    private static volatile float floatValue3;
    private static volatile boolean enabledState;
    private long timestampMs;
    private ClientLevel fieldVal;
    private LocalPlayer fieldVal1;
    private final Runnable fieldVal2;

    public Freecam() {
        super("Freecam", "Visual");
        this.timestampMs = -1L;
        Runnable var11 = () -> this.applyStep();
        this.fieldVal2 = var11;
        INSTANCE = this;
        FreecamCapabilityProvider var12 = new FreecamCapabilityProvider();
        Bus.register(IFreecam.class, var12);
    }

    public static boolean isEligible() {
        return enabledState;
    }

    public static Vec3 evaluate() {
        return positionVec;
    }

    public static Vec3 evaluate1() {
        return positionVec1;
    }

    public static float computeFloatMetric() {
        return floatValue2;
    }

    public static float computeFloatMetric1() {
        return floatValue3;
    }

    private static Freecam evaluate() {
        return INSTANCE;
    }

    public static boolean isFreezePlayer() {
        int var9 = 0;
        Freecam var0 = evaluate();
        if (var0 != null) {
            Boolean var10 = true;
            if (((Boolean) var0.getSetting("Freeze Player", var10))) {
            }
        }
        var9 = 1;
        var9 = 0;
        return var9;
    }

    public static boolean isNoClip() {
        int var9 = 0;
        Freecam var0 = evaluate();
        if (var0 != null) {
            Boolean var10 = true;
            if (((Boolean) var0.getSetting("No Clip", var10))) {
            }
        }
        var9 = 1;
        var9 = 0;
        return var9;
    }

    public static float getSpeed() {
        float var8 = 0.0F;
        Freecam var0 = evaluate();
        if (var0 == null) {
            var8 = 5.0F;
        } else {
            Float var9 = 5.0F;
            var8 = ((Float) var0.getSetting("Speed", var9));
        }
        return var8;
    }

    public static float getVerticalSpeed() {
        float var5 = 0.0F;
        Freecam var0 = evaluate();
        if (var0 == null) {
            var5 = 5.0F;
        } else {
            Float var9 = 5.0F;
            var5 = ((Float) var0.getSetting("Vertical Speed", var9));
        }
        return var5;
    }

    public void onEnable() {
        Minecraft var1 = Minecraft.getInstance();
        if (var1.player != null) {
            if (var1.level == null) {
            }
        }
        return;
        this.applyStep(var1);
    }

    public void onDisable() {
        enabledState = false;
        this.timestampMs = -1L;
        this.fieldVal = null;
        this.fieldVal1 = null;
        PerFrameEvents.unregister(this.fieldVal2);
        Minecraft var10 = Minecraft.getInstance();
        if (var10.player == null) {
            return;
        }
        LocalPlayer var14 = var10.player;
        Vec3 var27 = Vec3.ZERO;
        var14.setDeltaMovement(var27);
        var10.player.resetFallDistance();
        LocalPlayer var18 = var10.player;
        float var28 = floatValue2;
        var18.setYRot(var28);
        LocalPlayer var20 = var10.player;
        float var29 = floatValue3;
        var20.setXRot(var29);
        LocalPlayer var22 = var10.player;
        float var30 = floatValue2;
        var22.yRotO = var30;
        LocalPlayer var24 = var10.player;
        float var31 = floatValue3;
        var24.xRotO = var31;
    }

    public void onTick() {
        Minecraft var1 = Minecraft.getInstance();
        if (var1.player != null) {
            if (var1.level == null) {
            }
        }
        if (enabledState) {
            this.setEnabled(false);
        }
        return;
        if (!enabledState) {
            this.applyStep(var1);
            return;
        }
        LocalPlayer var13 = var1.player;
        LocalPlayer var45 = this.fieldVal1;
        if (var13 == var45) {
            ClientLevel var15 = var1.level;
            ClientLevel var47 = this.fieldVal;
            if (var15 != var47) {
            }
        }
        this.setEnabled(false);
        return;
        if (!var1.player.isRemoved()) {
            if (var1.player.isDeadOrDying()) {
            }
        }
        this.setEnabled(false);
        return;
        if (!isFreezePlayer()) {
            return;
        }
        LocalPlayer var25 = var1.player;
        Vec3 var49 = Vec3.ZERO;
        var25.setDeltaMovement(var49);
        var1.player.resetFallDistance();
        LocalPlayer var29 = var1.player;
        float var50 = floatValue2;
        var29.setYRot(var50);
        LocalPlayer var31 = var1.player;
        float var51 = floatValue3;
        var31.setXRot(var51);
        LocalPlayer var33 = var1.player;
        float var52 = floatValue2;
        var33.yRotO = var52;
        LocalPlayer var35 = var1.player;
        float var53 = floatValue3;
        var35.xRotO = var53;
        if (!var1.player.isPassenger()) {
            LocalPlayer var40 = var1.player;
            double var55 = positionVec1.x;
            double var59 = positionVec1.y;
            double var61 = positionVec1.z;
            var40.setPos(var55, var59, var61);
        }
    }

    private void applyStep(Minecraft arg0) {
        if (enabledState) {
            return;
        }
        positionVec = arg0.player.getEyePosition(1.0F);
        positionVec1 = arg0.player.position();
        floatValue2 = arg0.player.getYRot();
        floatValue3 = arg0.player.getXRot();
        floatValue = floatValue2;
        floatValue1 = floatValue3;
        enabledState = true;
        long var33 = System.nanoTime();
        this.timestampMs = var33;
        ClientLevel var36 = arg0.level;
        this.fieldVal = var36;
        LocalPlayer var38 = arg0.player;
        this.fieldVal1 = var38;
        if (isFreezePlayer()) {
            LocalPlayer var26 = arg0.player;
            Vec3 var39 = Vec3.ZERO;
            var26.setDeltaMovement(var39);
            arg0.player.resetFallDistance();
            LocalPlayer var30 = arg0.player;
            double var41 = positionVec1.x;
            double var43 = positionVec1.y;
            double var45 = positionVec1.z;
            var30.setPos(var41, var43, var45);
        }
        PerFrameEvents.register(this.fieldVal2);
    }

    private void applyStep() {
        double var12 = 0.0;
        double var21 = 0.0;
        double var39 = 0.0;
        double var41 = 0.0;
        double var43 = 0.0;
        Vec3 var238 = null;
        if (!enabledState) {
            return;
        }
        Minecraft var48 = Minecraft.getInstance();
        if (var48.player != null) {
            if (var48.level == null) {
            }
        }
        return;
        long var53 = System.nanoTime();
        long var242 = this.timestampMs;
        var12 = (((double) (var53 - var242)) / 1.0E9);
        this.timestampMs = var53;
        if (Double.compare(var12, 0.0) <= 0) {
            return;
        }
        if (Double.compare(var12, 0.1) > 0) {
            var12 = 0.1;
        }
        if (var48.screen == null) {
            if (var48.isPaused()) {
            }
        }
        return;
        boolean var79 = var48.options.keyUp.isDown();
        boolean var83 = var48.options.keyDown.isDown();
        boolean var87 = var48.options.keyLeft.isDown();
        boolean var91 = var48.options.keyRight.isDown();
        boolean var95 = var48.options.keyJump.isDown();
        boolean var99 = var48.options.keyShift.isDown();
        boolean var103 = var48.options.keySprint.isDown();
        if (!var79) {
            if (!var83) {
                if (!var87) {
                    if (!var91) {
                        if (!var95) {
                            if (!var99) {
                                return;
                            }
                        }
                    }
                }
                double var113 = (((double) getSpeed()) * 2.0);
                double var252 = var12;
                var21 = (var113 * var252);
                double var120 = (((double) getVerticalSpeed()) * 2.0);
                double var256 = var12;
                double var122 = (var120 * var256);
                if (var103) {
                    var21 = (var21 * 2.0);
                }
                double var132 = Math.toRadians(((double) floatValue));
                double var136 = Math.sin(var132);
                double var140 = Math.cos(var132);
                double var144 = -(var136);
                double var150 = -(var140);
                double var154 = -(var136);
                var39 = 0.0;
                var41 = 0.0;
                var43 = 0.0;
                if (var79) {
                    var39 = (var39 + var144);
                    var43 = (var43 + var140);
                }
                if (var83) {
                    var39 = (var39 - var144);
                    var43 = (var43 - var140);
                }
                if (var87) {
                    var39 = (var39 - var150);
                    var43 = (var43 - var154);
                }
                if (var91) {
                    var39 = (var39 + var150);
                    var43 = (var43 + var154);
                }
                double var198 = var39;
                double var276 = var39;
                double var200 = (var198 * var276);
                double var278 = var43;
                double var5 = var43;
                double var280 = (var278 * var5);
                double var204 = Math.sqrt((var200 + var280));
                if (Double.compare(var204, 1.0) > 0) {
                    var39 = (var39 / var204);
                    var43 = (var43 / var204);
                }
                double var217 = var39;
                double var288 = var21;
                double var219 = (var217 * var288);
                double var221 = var43;
                double var290 = var21;
                double var223 = (var221 * var290);
                if (var95) {
                    var41 = (var41 + var122);
                }
                if (var99) {
                    var41 = (var41 - var122);
                }
                double var299 = var41;
                Vec3 var235 = new Vec3(var219, var299, var223);
                if (isNoClip()) {
                    var238 = positionVec.add(var235);
                } else {
                    Vec3 var241 = positionVec;
                    var238 = evaluate(var48, var241, var235);
                }
                positionVec = var238;
                return;
            }
        }
    }

    private static Vec3 evaluate(Minecraft arg0, Vec3 arg1, Vec3 arg2) {
        double var23 = arg2.x;
        Vec3 var5 = new Vec3(var23, 0.0, 0.0);
        Vec3 var13 = evaluate2(arg0, arg1, var5);
        double var30 = arg2.y;
        Vec3 var21 = new Vec3(0.0, var30, 0.0);
        Vec3 var15 = evaluate2(arg0, var13, var21);
        double var37 = arg2.z;
        Vec3 var22 = new Vec3(0.0, 0.0, var37);
        return evaluate2(arg0, var15, var22);
    }

    private static Vec3 evaluate2(Minecraft arg0, Vec3 arg1, Vec3 arg2) {
        if (Double.compare(arg2.lengthSqr(), 1.0E-8) < 0) {
            return arg1;
        }
        Vec3 var19 = arg1.add(arg2);
        ClientLevel var21 = arg0.level;
        ClipContext.Block var9 = ClipContext.Block.COLLIDER;
        ClipContext.Fluid var10 = ClipContext.Fluid.NONE;
        LocalPlayer var38 = arg0.player;
        ClipContext var32 = new ClipContext(arg1, var19, var9, var10, var38);
        BlockHitResult var22 = var21.clip(var32);
        HitResult.Type var24 = var22.getType();
        HitResult.Type var33 = HitResult.Type.MISS;
        if (var24 == var33) {
            return var19;
        }
        Vec3 var27 = arg2.normalize();
        Vec3 var29 = var22.getLocation();
        Vec3 var35 = var27.scale(0.01);
        return var29.subtract(var35);
    }

    static {
        positionVec = Vec3.ZERO;
        floatValue = 0.0F;
        floatValue1 = 0.0F;
        positionVec1 = Vec3.ZERO;
        floatValue2 = 0.0F;
        floatValue3 = 0.0F;
        enabledState = false;
    }

}
