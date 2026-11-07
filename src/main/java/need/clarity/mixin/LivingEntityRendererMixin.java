package need.clarity.mixin;

import need.clarity.api.Modules;
import need.clarity.api.Rotation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin {
    public LivingEntityRendererMixin() {
    }

    @Inject(method = "extractRenderState(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;F)V", at = @At("TAIL"))
    private void cl$silentRotPitch(LivingEntity arg0, LivingEntityRenderState arg1, float arg2, CallbackInfo arg3) {
        if (!(arg0 instanceof LocalPlayer)) {
            return;
        }
        if (arg0 != Minecraft.getInstance().player) {
            return;
        }
        if (!Modules.serverRotationsApply()) {
            return;
        }
        if (!Rotation.hasSilentRotation()) {
            return;
        }
        arg1.xRot = Modules.serverRotationsPitchLerp(arg2);
    }

}
