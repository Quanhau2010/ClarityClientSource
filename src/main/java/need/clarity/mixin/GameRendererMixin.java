package need.clarity.mixin;

import need.clarity.PerFrameEvents;
import need.clarity.api.Modules;
import need.clarity.api.RenderApi;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = GameRenderer.class, priority = 1001)
public class GameRendererMixin {
    public GameRendererMixin() {
    }

    @Inject(method = "renderLevel", at = @At("HEAD"))
    public void clarity$firePerFrame(DeltaTracker arg0, CallbackInfo arg1) {
        PerFrameEvents.fire();
    }

    @Inject(method = "renderLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GameRenderer;renderItemInHand(Lnet/minecraft/client/renderer/state/level/CameraRenderState;FLorg/joml/Matrix4fc;)V"))
    public void beforeRenderHand(DeltaTracker arg0, CallbackInfo arg1) {
        if (!RenderApi.shaderIsInitialized()) {
            RenderApi.shaderInitIfNeeded();
        }
        if (Modules.handShadersActive()) {
            if ("Invert".equals(Modules.handShadersShader())) {
                if (Modules.handShadersInvertHideHand()) {
                    RenderApi.forceColorMaskOff(true);
                }
                RenderApi.shaderCaptureBackground();
            }
        }
    }

    @Inject(method = "renderLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GameRenderer;renderItemInHand(Lnet/minecraft/client/renderer/state/level/CameraRenderState;FLorg/joml/Matrix4fc;)V", shift = At.Shift.AFTER))
    public void afterRenderHand(DeltaTracker arg0, CallbackInfo arg1) {
        if (Modules.handShadersActive()) {
            if (RenderApi.isColorMaskForcedOff()) {
                RenderApi.forceColorMaskOff(false);
            }
            RenderApi.shaderCheckFramebuffers();
            Modules.handShadersRender();
        }
    }

}
