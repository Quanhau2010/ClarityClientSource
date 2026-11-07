package need.clarity.mixin;

import need.clarity.api.RenderApi;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderer.class)
public abstract class ShaderEspEntityMixin {
    public ShaderEspEntityMixin() {
    }

    @Inject(method = "extractRenderState(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/client/renderer/entity/state/EntityRenderState;F)V", at = @At("TAIL"))
    private void clarity$shaderEspTag(Entity arg0, EntityRenderState arg1, float arg2, CallbackInfo arg3) {
        RenderApi.shaderEspTagState(arg0, arg1);
    }

}
