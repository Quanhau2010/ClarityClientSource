package need.clarity.mixin;

import need.clarity.LocalPlayerRotationState;
import need.clarity.api.Movement;
import need.clarity.api.Rotation;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class EntityMixin {
    public EntityMixin() {
    }

    private static Vec3 cl$serverLookVector(Entity arg0) {
        float var1 = 0.0F;
        float var2 = 0.0F;
        if (arg0 != Minecraft.getInstance().player) {
            return null;
        }
        if (!Rotation.hasSilentRotation()) {
            if (!Rotation.isActivelyTracking()) {
                return null;
            }
            if (LocalPlayerRotationState.tickServerTick == arg0.tickCount) {
                var1 = LocalPlayerRotationState.tickServerYaw;
                var2 = LocalPlayerRotationState.tickServerPitch;
            } else {
                var1 = Rotation.getSilentServerYaw();
                var2 = Rotation.getSilentServerPitch();
            }
            return Rotation.getRotationVector(var2, var1);
        }
    }

    @Inject(method = "getViewVector", at = @At("HEAD"), cancellable = true)
    private void cl$silentViewVector(float arg0, CallbackInfoReturnable arg1) {
        Vec3 var3 = cl$serverLookVector(((Entity) this));
        if (var3 != null) {
            arg1.setReturnValue(var3);
        }
    }

    @Inject(method = "getHeadLookAngle", at = @At("HEAD"), cancellable = true)
    private void cl$silentHeadLookAngle(CallbackInfoReturnable arg0) {
        Vec3 var2 = cl$serverLookVector(((Entity) this));
        if (var2 != null) {
            arg0.setReturnValue(var2);
        }
    }

    @Redirect(method = "moveRelative", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;getYRot()F"))
    private float cl$redirectMovementYaw(Entity arg0) {
        if (arg0 == Minecraft.getInstance().player) {
            if (Rotation.hasSilentRotation()) {
                if (Movement.shouldApplyRotationFix()) {
                    return Rotation.getSilentServerYaw();
                }
                return arg0.getYRot();
            }
        }
    }

}
