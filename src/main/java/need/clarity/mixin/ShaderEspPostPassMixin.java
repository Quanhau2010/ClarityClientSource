package need.clarity.mixin;

import need.clarity.api.RenderApi;
import net.minecraft.client.renderer.PostPass;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PostPass.class)
public abstract class ShaderEspPostPassMixin {
    public ShaderEspPostPassMixin() {
    }

    @Inject(method = "lambda$addToFrame$1(Lcom/mojang/blaze3d/resource/ResourceHandle;Lcom/mojang/blaze3d/buffers/GpuBufferSlice;Ljava/util/Map;)V", at = @At("HEAD"), cancellable = true)
    private void clarity$shaderEspOutlinePass(CallbackInfo arg0) {
        if (RenderApi.shaderEspOnOutlinePass(this)) {
            arg0.cancel();
        }
    }

}
