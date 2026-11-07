package need.clarity.mixin;

import need.clarity.api.FreecamApi;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Camera.class)
public abstract class FreecamCameraMixin {
    @Shadow
    private boolean detached;

    public FreecamCameraMixin() {
    }

    @Shadow
    protected abstract void setPosition(Vec3 arg0);

    @Shadow
    protected abstract void setRotation(float arg0, float arg1);

    @Inject(method = "update(Lnet/minecraft/client/DeltaTracker;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Camera;alignWithEntity(F)V", shift = At.Shift.AFTER))
    private void clarity$freecamOverrideCamera(DeltaTracker arg0, CallbackInfo arg1) {
        if (!FreecamApi.isActive()) {
            return;
        }
        this.detached = true;
        this.setPosition(FreecamApi.getCameraPos());
        this.setRotation(FreecamApi.getCameraYaw(), FreecamApi.getCameraPitch());
    }

    @Inject(method = "position", at = @At("HEAD"), cancellable = true)
    private void clarity$freecamPosition(CallbackInfoReturnable arg0) {
        if (!FreecamApi.isActive()) {
            return;
        }
        arg0.setReturnValue(FreecamApi.getCameraPos());
    }

}
