package need.clarity.mixin;

import need.clarity.api.Modules;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Camera.class)
public abstract class CameraTweaksMixin {
    @Shadow
    private Entity entity;

    public CameraTweaksMixin() {
    }

    @Shadow
    protected abstract void move(float arg0, float arg1, float arg2);

    @Shadow
    protected abstract float getMaxZoom(float arg0);

    @Inject(method = "alignWithEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Camera;getMaxZoom(F)F"), cancellable = true)
    private void clarity$cameraTweaks(float arg0, CallbackInfo arg1) {
        float var4 = 0.0F;
        LivingEntity var5 = null;
        Entity var6 = null;
        if (!Modules.cameraTweaksActive()) {
            return;
        }
        Minecraft var3 = Minecraft.getInstance();
        if (var3 != null) {
            if (var3.options.getCameraType().isFirstPerson()) {
            }
        }
        return;
        var4 = 1.0F;
        var6 = this.entity;
        if ((var6 instanceof LivingEntity)) {
            var5 = ((LivingEntity) var6);
            var4 = ((var5.getScale() * ((float) var5.getAttributeValue(Attributes.CAMERA_DISTANCE))) / 4.0F);
        }
        if (this.entity != null) {
            if (this.entity.isPassenger()) {
                var6 = this.entity.getVehicle();
                if ((var6 instanceof LivingEntity)) {
                    var5 = ((LivingEntity) var6);
                    var6 = (var5.getScale() * ((float) var5.getAttributeValue(Attributes.CAMERA_DISTANCE)));
                    if ((var6 / 4.0F) > var4) {
                        var4 = (var6 / 4.0F);
                    }
                    if (Modules.cameraTweaksCustomEnabled()) {
                    } else {
                    }
                    var5 = 4.0F;
                    var6 = (var5 * var4);
                    boolean var7 = Modules.cameraTweaksNoClip();
                    if (var7) {
                    } else {
                    }
                    -(var6).move(-(this.getMaxZoom(var6)), 0.0F, 0.0F);
                    arg1.cancel();
                    return;
                }
            }
        }
    }

}
