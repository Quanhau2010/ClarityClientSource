package need.clarity.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import need.clarity.api.Modules;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class NoHurtCamMixin {
    public NoHurtCamMixin() {
    }

    @Inject(method = "bobHurt", at = @At("HEAD"), cancellable = true)
    private void clarity$noHurtCam(CameraRenderState arg0, PoseStack arg1, CallbackInfo arg2) {
        if (Modules.noHurtCamActive()) {
            arg2.cancel();
        }
    }

}
