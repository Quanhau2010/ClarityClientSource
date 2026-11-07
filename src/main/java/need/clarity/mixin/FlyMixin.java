package need.clarity.mixin;

import need.clarity.api.Modules;
import net.minecraft.client.Minecraft;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class FlyMixin {
    public FlyMixin() {
    }

    @Inject(method = "travel", at = @At("HEAD"))
    private void cl$flyNoGravity(Vec3 arg0, CallbackInfo arg1) {
        if (!Modules.flyActive()) {
            return;
        }
        Minecraft var3 = Minecraft.getInstance();
        if (var3.player != null) {
            if (this != var3.player) {
            }
        }
        return;
        LivingEntity var4 = ((LivingEntity) this);
        if (var4.getDeltaMovement().y < 0.0) {
            if (!Modules.flyJumpDown(var3)) {
                if (!Modules.flyDescendDown(var3)) {
                    var4.setDeltaMovement(var4.getDeltaMovement().x, 0.0, var4.getDeltaMovement().z);
                }
                return;
            }
        }
    }

    @Inject(method = "travel", at = @At("RETURN"))
    private void cl$flyApplyVelocity(Vec3 arg0, CallbackInfo arg1) {
        if (Modules.flyActive()) {
            if (!Modules.flyHasPending()) {
            }
        }
        return;
        Minecraft var3 = Minecraft.getInstance();
        if (var3.player != null) {
            if (this != var3.player) {
            }
        }
        return;
        LivingEntity var4 = ((LivingEntity) this);
        String var5 = Modules.flyMode();
        if ("Full".equals(var5)) {
            var4.setDeltaMovement(Modules.flyPendingX(), Modules.flyPendingY(), Modules.flyPendingZ());
        } else {
            Vec3 var6 = var4.getDeltaMovement();
            var4.setDeltaMovement(((var6.x * 0.91) + Modules.flyPendingX()), Modules.flyPendingY(), ((var6.z * 0.91) + Modules.flyPendingZ()));
        }
        Modules.flyClearPending();
    }

    @Inject(method = "causeFallDamage", at = @At("HEAD"), cancellable = true)
    private void cl$flyNoFallDamage(double arg0, float arg1, DamageSource arg2, CallbackInfoReturnable arg3) {
        if (!Modules.flyActive()) {
            return;
        }
        Minecraft var6 = Minecraft.getInstance();
        if (var6.player != null) {
            if (this != var6.player) {
            }
        }
        return;
        arg3.setReturnValue(false);
    }

}
