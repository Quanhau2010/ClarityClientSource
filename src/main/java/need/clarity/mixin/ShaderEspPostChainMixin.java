package need.clarity.mixin;

import java.util.Set;
import need.clarity.api.RenderApi;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.client.renderer.PostChainConfig;
import net.minecraft.client.renderer.Projection;
import net.minecraft.client.renderer.ProjectionMatrixBuffer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PostChain.class)
public abstract class ShaderEspPostChainMixin {
    public ShaderEspPostChainMixin() {
    }

    @Inject(method = "load(Lnet/minecraft/client/renderer/PostChainConfig;Lnet/minecraft/client/renderer/texture/TextureManager;Ljava/util/Set;Lnet/minecraft/resources/Identifier;Lnet/minecraft/client/renderer/Projection;Lnet/minecraft/client/renderer/ProjectionMatrixBuffer;)Lnet/minecraft/client/renderer/PostChain;", at = @At("RETURN"))
    private static void clarity$shaderEspRememberOutlineChain(PostChainConfig arg0, TextureManager arg1, Set arg2, Identifier arg3, Projection arg4, ProjectionMatrixBuffer arg5, CallbackInfoReturnable arg6) {
        PostChain var7 = ((PostChain) arg6.getReturnValue());
        if (var7 != null) {
            if (arg3 != null) {
                if (!"entity_outline".equals(arg3.getPath())) {
                }
            }
            return;
            RenderApi.shaderEspRegisterOutlinePasses(((PostChainAccessor) var7).clarity$getPasses());
            return;
        }
    }

}
