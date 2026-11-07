package need.clarity.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import need.clarity.api.Modules;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EndCrystalRenderer;
import net.minecraft.client.renderer.entity.state.EndCrystalRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EndCrystalRenderer.class)
public class EndCrystalRendererMixin {
    public EndCrystalRendererMixin() {
    }

    @Inject(method = "submit(Lnet/minecraft/client/renderer/entity/state/EndCrystalRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;Lnet/minecraft/client/renderer/state/level/CameraRenderState;)V", at = @At("HEAD"), cancellable = true)
    private void cl$crystalView(EndCrystalRenderState arg0, PoseStack arg1, SubmitNodeCollector arg2, CameraRenderState arg3, CallbackInfo arg4) {
        if (arg0.beamOffset == null) {
            if (Modules.crystalViewActive()) {
                arg4.cancel();
            }
            return;
        }
    }

}
