package need.clarity.mixin;

import need.clarity.LocalPlayerRotationState;
import need.clarity.api.Modules;
import need.clarity.api.Movement;
import need.clarity.api.Rotation;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    public LivingEntityMixin() {
    }

    @ModifyArg(method = "jumpFromGround", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;addDeltaMovement(Lnet/minecraft/world/phys/Vec3;)V"))
    private Vec3 cl$adjustSprintJumpVelocity(Vec3 arg0) {
        Minecraft var2 = Minecraft.getInstance();
        if (var2.player != null) {
            if (this != var2.player) {
            }
        }
        return arg0;
        if (LocalPlayerRotationState.silentActive) {
            if (!Movement.shouldApplyRotationFix()) {
            }
        }
        return arg0;
        double var3 = Math.sqrt(((arg0.x * arg0.x) + (arg0.z * arg0.z)));
        if (var3 < 1.0E-6) {
            return arg0;
        }
        float var5 = (LocalPlayerRotationState.tickServerYaw * 0.017453292F);
        double var6 = ((((double) Mth.sin(((double) var5))) * -1.0) * var3);
        double var8 = (((double) Mth.cos(((double) var5))) * var3);
        return new Vec3(var6, arg0.y, var8);
    }

    @Redirect(method = "updateFallFlyingMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;getXRot()F"))
    private float cl$redirectElytraPitch(LivingEntity arg0) {
        Minecraft var2 = Minecraft.getInstance();
        if (arg0 == var2.player) {
            if (LocalPlayerRotationState.silentActive) {
                if (Movement.shouldApplyRotationFix()) {
                    return LocalPlayerRotationState.tickServerPitch;
                }
                return arg0.getXRot();
            }
        }
    }

    @Redirect(method = "updateFallFlyingMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;getLookAngle()Lnet/minecraft/world/phys/Vec3;"))
    private Vec3 cl$redirectElytraLookVector(LivingEntity arg0) {
        Minecraft var2 = Minecraft.getInstance();
        if (arg0 == var2.player) {
            if (LocalPlayerRotationState.silentActive) {
                if (Movement.shouldApplyRotationFix()) {
                    return Rotation.getRotationVector(LocalPlayerRotationState.tickServerPitch, LocalPlayerRotationState.tickServerYaw);
                }
                return arg0.getLookAngle();
            }
        }
    }

    @Inject(method = "getCurrentSwingDuration", at = @At("HEAD"), cancellable = true)
    private void cl$overrideSwingDuration(CallbackInfoReturnable arg0) {
        Minecraft var2 = Minecraft.getInstance();
        if (var2.player != null) {
            if (this != var2.player) {
            }
        }
        return;
        if (!Modules.animationsOverrideSwing()) {
            return;
        }
        arg0.setReturnValue(Modules.animationsSwingSpeed());
    }

}
