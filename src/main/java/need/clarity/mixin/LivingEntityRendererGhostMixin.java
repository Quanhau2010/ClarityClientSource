package need.clarity.mixin;

import need.clarity.api.OvertickApi;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererGhostMixin {
    public LivingEntityRendererGhostMixin() {
    }

    @Shadow
    public abstract Identifier getTextureLocation(LivingEntityRenderState arg0);

    @Inject(method = "getRenderType", at = @At("HEAD"), cancellable = true)
    private void cl$ghostRenderType(LivingEntityRenderState arg0, boolean arg1, boolean arg2, boolean arg3, CallbackInfoReturnable arg4) {
        if (OvertickApi.isGhostState(arg0)) {
            arg4.setReturnValue(RenderTypes.entityTranslucentCullItemTarget(this.getTextureLocation(arg0)));
        }
    }

    @Inject(method = "getModelTint", at = @At("HEAD"), cancellable = true)
    private void cl$ghostModelTint(LivingEntityRenderState arg0, CallbackInfoReturnable arg1) {
        if (OvertickApi.isGhostState(arg0)) {
            int var3 = OvertickApi.ghostOpacityAlpha();
            arg1.setReturnValue(((var3 << 24) | 16777215));
        }
    }

}
