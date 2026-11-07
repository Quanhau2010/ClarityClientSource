package need.clarity.mixin;

import need.clarity.api.RenderApi;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityRenderDispatcher.class)
public abstract class CorpseHideMixin {
    public CorpseHideMixin() {
    }

    @Inject(method = "shouldRender(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/client/renderer/culling/Frustum;DDD)Z", at = @At("HEAD"), cancellable = true)
    private void clarity$hideCorpse(Entity arg0, Frustum arg1, double arg2, double arg3, double arg4, CallbackInfoReturnable arg5) {
        if (arg0 != null) {
            if (RenderApi.shaderEspIsHidden(arg0.getId())) {
                arg5.setReturnValue(false);
            }
            return;
        }
    }

}
