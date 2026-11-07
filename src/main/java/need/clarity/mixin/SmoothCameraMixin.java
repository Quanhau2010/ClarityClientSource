package need.clarity.mixin;

import need.clarity.api.Modules;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Camera.class, priority = 900)
public abstract class SmoothCameraMixin {
    public SmoothCameraMixin() {
    }

    @Shadow
    protected abstract void setRotation(float arg0, float arg1);

    @Shadow
    protected abstract void setPosition(Vec3 arg0);

    @Shadow
    public abstract float yRot();

    @Shadow
    public abstract float xRot();

    @Shadow
    public abstract Vec3 position();

    @Shadow
    public abstract boolean isDetached();

    @Shadow
    public abstract Entity entity();

    @Shadow
    public abstract float getCameraEntityPartialTicks(DeltaTracker arg0);

    @Inject(method = "alignWithEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Camera;getMaxZoom(F)F"))
    private void clarity$smoothCamera(float arg0, CallbackInfo arg1) {
        float[] var4 = null;
        Minecraft var3 = Minecraft.getInstance();
        if (var3 != null) {
            if (var3.level != null) {
                if (var3.player == null) {
                }
            }
            return;
            try {
                var4 = Modules.smoothCameraSmooth(this.yRot(), this.xRot());
                if (var4 != null) {
                    this.setRotation(var4[0], var4[1]);
                }
                return;
            } catch (Throwable ignored) {
            }
        }
    }

    @Inject(method = "update(Lnet/minecraft/client/DeltaTracker;)V", at = @At("RETURN"))
    private void clarity$smoothCameraPosition(DeltaTracker arg0, CallbackInfo arg1) {
        Vec3 var5 = null;
        Minecraft var3 = Minecraft.getInstance();
        Entity var4 = this.entity();
        if (var3 != null) {
            if (var3.level != null) {
                if (var3.player != null) {
                    if (var4 != null) {
                        if (!this.isDetached()) {
                        }
                    }
                    Modules.smoothCameraPosition(null, null, 0.0F);
                    return;
                    try {
                        var5 = Modules.smoothCameraPosition(this.position(), var4, this.getCameraEntityPartialTicks(arg0));
                        if (var5 != null) {
                            this.setPosition(var5);
                        }
                        Modules.smoothCameraPosition(null, null, 0.0F);
                        return;
                    } catch (Throwable ignored) {
                    }
                }
            }
        }
    }

}
