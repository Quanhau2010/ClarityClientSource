package need.clarity.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import need.clarity.api.PlayerModelApi;
import need.clarity.api.RenderApi;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.ArmedEntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemInHandLayer.class)
public abstract class ItemInHandLayerMixin {
    private static final String SUBMIT = "submit(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;ILnet/minecraft/client/renderer/entity/state/ArmedEntityRenderState;FF)V";

    public ItemInHandLayerMixin() {
    }

    @Inject(method = "submit(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;ILnet/minecraft/client/renderer/entity/state/ArmedEntityRenderState;FF)V", at = @At("HEAD"))
    private void cl$heldItemsBegin(PoseStack arg0, SubmitNodeCollector arg1, int arg2, ArmedEntityRenderState arg3, float arg4, float arg5, CallbackInfo arg6) {
        EntityModel var8 = null;
        RenderApi.modelHandsSetActive(false);
        try {
            if (!PlayerModelApi.isModelState(arg3)) {
            }
        } catch (Throwable ignored) {
        }
        return;
        try {
            var8 = ((RenderLayer) this).getParentModel();
            ModelPart var9 = var8.root();
            RenderApi.modelHandsSetRight(var9.getChild("right_arm"));
            RenderApi.modelHandsSetLeft(var9.getChild("left_arm"));
            RenderApi.modelHandsSetActive(true);
        } catch (Throwable ignored) {
        }
    }

    @Inject(method = "submit(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;ILnet/minecraft/client/renderer/entity/state/ArmedEntityRenderState;FF)V", at = @At("RETURN"))
    private void cl$heldItemsEnd(PoseStack arg0, SubmitNodeCollector arg1, int arg2, ArmedEntityRenderState arg3, float arg4, float arg5, CallbackInfo arg6) {
        RenderApi.modelHandsSetActive(false);
    }

}
