package need.clarity.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import need.clarity.api.Modules;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.ScreenEffectRenderer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ScreenEffectRenderer.class)
public class ScreenEffectRendererMixin {
    public ScreenEffectRendererMixin() {
    }

    @Inject(method = "renderFire", at = @At("HEAD"), cancellable = true)
    private static void clarity$noFire(PoseStack arg0, MultiBufferSource arg1, TextureAtlasSprite arg2, CallbackInfo arg3) {
        if (Modules.noRenderFire()) {
            arg3.cancel();
        }
    }

    @Inject(method = "renderItemActivationAnimation", at = @At("HEAD"), cancellable = true)
    private void clarity$noTotemAnimation(PoseStack arg0, float arg1, SubmitNodeCollector arg2, CallbackInfo arg3) {
        if (Modules.noRenderTotem()) {
            arg3.cancel();
        }
    }

}
