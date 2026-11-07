package need.clarity.mixin;

import need.clarity.LocalPlayerRotationState;
import need.clarity.api.Modules;
import need.clarity.api.Rotation;
import need.clarity.sec.Diag;
import need.clarity.util.RotationGcd;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LocalPlayer.class)
public abstract class LocalPlayerMixin {
    @Shadow
    public float yBob;
    @Shadow
    public float yBobO;
    @Shadow
    public float xBob;
    @Shadow
    public float xBobO;
    @Unique
    private boolean cl$silentApplied;
    @Unique
    private float cl$tickServerYaw;
    @Unique
    private float cl$tickServerPitch;
    @Unique
    private float cl$sYaw;
    @Unique
    private float cl$sPitch;
    @Unique
    private float cl$sRenderYaw;
    @Unique
    private float cl$sRenderPitch;
    @Unique
    private float cl$sHeadYaw;
    @Unique
    private float cl$sBodyYaw;
    @Unique
    private boolean cl$haveLast;
    @Unique
    private float cl$lastYaw;
    @Unique
    private float cl$lastPitch;
    @Unique
    private static final float cl$BODY_FOLLOW = 0.22F;
    @Unique
    private static final float cl$MAX_HEAD_YAW = 50.0F;
    @Unique
    private long cl$lastSendDiag;

    public LocalPlayerMixin() {
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void cl$silentRotationHead(CallbackInfo arg0) {
        this.cl$silentApplied = false;
        LocalPlayerRotationState.silentActive = false;
        LocalPlayer var2 = ((LocalPlayer) this);
        if (!Rotation.hasSilentRotation()) {
            this.cl$haveLast = true;
            this.cl$lastYaw = var2.getYRot();
            this.cl$lastPitch = var2.getXRot();
            return;
        }
        if (!this.cl$haveLast) {
            this.cl$lastYaw = var2.getYRot();
            this.cl$lastPitch = var2.getXRot();
            this.cl$haveLast = true;
        }
        float var3 = Rotation.getSilentServerYaw();
        float var4 = Mth.clamp(Rotation.getSilentServerPitch(), -90.0F, 90.0F);
        if (Float.isFinite(var3)) {
            if (!Float.isFinite(var4)) {
            }
        }
        this.cl$lastYaw = var2.getYRot();
        this.cl$lastPitch = var2.getXRot();
        return;
        if (RotationGcd.enabled()) {
            this.cl$tickServerYaw = RotationGcd.snapYaw(this.cl$lastYaw, var3);
            this.cl$tickServerPitch = RotationGcd.snapPitch(this.cl$lastPitch, var4);
        } else {
            this.cl$tickServerYaw = var3;
            this.cl$tickServerPitch = var4;
        }
        this.cl$lastYaw = this.cl$tickServerYaw;
        this.cl$lastPitch = this.cl$tickServerPitch;
        Rotation.commitSnappedRotation(this.cl$tickServerYaw, this.cl$tickServerPitch);
        this.cl$sYaw = var2.getYRot();
        this.cl$sPitch = var2.getXRot();
        this.cl$sRenderYaw = this.yBob;
        this.cl$sRenderPitch = this.xBob;
        this.cl$sHeadYaw = var2.yHeadRot;
        this.cl$sBodyYaw = var2.yBodyRot;
        LocalPlayerRotationState.tickServerYaw = this.cl$tickServerYaw;
        LocalPlayerRotationState.tickServerPitch = this.cl$tickServerPitch;
        LocalPlayerRotationState.realYaw = this.cl$sYaw;
        LocalPlayerRotationState.silentActive = true;
        LocalPlayerRotationState.tickServerTick = var2.tickCount;
        Modules.serverRotationsCaptureLast(Modules.serverRotationsPitchLerp(1.0F));
        var2.setYRot(this.cl$tickServerYaw);
        var2.setXRot(this.cl$tickServerPitch);
        Modules.serverRotationsCaptureCurrent(this.cl$tickServerPitch);
        this.cl$silentApplied = true;
    }

    @Inject(method = "tick", at = @At("RETURN"))
    private void cl$silentRotationReturn(CallbackInfo arg0) {
        float var4 = 0.0F;
        LocalPlayerRotationState.silentActive = false;
        if (!this.cl$silentApplied) {
            return;
        }
        this.cl$silentApplied = false;
        LocalPlayer var2 = ((LocalPlayer) this);
        var2.setYRot(this.cl$sYaw);
        var2.setXRot(this.cl$sPitch);
        var2.yRotO = this.cl$sYaw;
        var2.xRotO = this.cl$sPitch;
        if (Modules.serverRotationsApply()) {
        } else {
        }
        float var3 = this.cl$sYaw;
        var2.yHeadRotO = this.cl$sHeadYaw;
        var2.yHeadRot = var3;
        var4 = (this.cl$sBodyYaw + (Mth.wrapDegrees((var3 - this.cl$sBodyYaw)) * 0.22F));
        float var5 = Mth.wrapDegrees((var3 - var4));
        if (var5 > 50.0F) {
            var4 = (var3 - 50.0F);
        } else {
            if (var5 < -50.0F) {
                var4 = (var3 + 50.0F);
            }
            var2.yBodyRotO = this.cl$sBodyYaw;
            var2.yBodyRot = var4;
            this.yBobO = this.cl$sRenderYaw;
            this.xBobO = this.cl$sRenderPitch;
            this.yBob = (this.cl$sRenderYaw + ((this.cl$sYaw - this.cl$sRenderYaw) * 0.5F));
            this.xBob = (this.cl$sRenderPitch + ((this.cl$sPitch - this.cl$sRenderPitch) * 0.5F));
            return;
        }
    }

    @Inject(method = "sendPosition", at = @At("HEAD"), require = 0)
    private void cl$diagSendPosition(CallbackInfo arg0) {
        if (!Diag.enabled()) {
            return;
        }
        long var2 = System.currentTimeMillis();
        if ((var2 - this.cl$lastSendDiag) < 1000L) {
            return;
        }
        this.cl$lastSendDiag = var2;
        LocalPlayer var4 = ((LocalPlayer) this);
        Diag.log("sendPosition yaw=" + (var4.getYRot()) + " pitch=" + (var4.getXRot()) + " silentActive=" + (LocalPlayerRotationState.silentActive) + " committed=" + (LocalPlayerRotationState.tickServerYaw) + "," + (LocalPlayerRotationState.tickServerPitch) + " hasSilent=" + (Rotation.hasSilentRotation()));
    }

}
