package need.clarity.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import need.clarity.api.RenderApi;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.world.entity.HumanoidArm;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ItemInHandRenderer.class)
public abstract class AnimationsHeldItemRendererMixin {
    public AnimationsHeldItemRendererMixin() {
    }

    @Redirect(method = "renderArmWithItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemInHandRenderer;applyItemArmTransform(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/world/entity/HumanoidArm;F)V", ordinal = 4))
    private void cl$skipDefaultEquipOffset(ItemInHandRenderer arg0, PoseStack arg1, HumanoidArm arg2, float arg3) {
        if (!RenderApi.animShouldAnimate(arg2)) {
            ((AnimationsHeldItemRendererInvoker) arg0).cl$applyItemArmTransform(arg1, arg2, arg3);
        }
    }

    @Redirect(method = "renderArmWithItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemInHandRenderer;swingArm(FLcom/mojang/blaze3d/vertex/PoseStack;ILnet/minecraft/world/entity/HumanoidArm;)V", ordinal = 2))
    private void cl$replaceSwingArm(ItemInHandRenderer arg0, float arg1, PoseStack arg2, int arg3, HumanoidArm arg4) {
        if (!(RenderApi.animApply(arg2, arg4, arg1))) {
            ((AnimationsHeldItemRendererInvoker) arg0).cl$swingArm(arg1, arg2, arg3, arg4);
        }
    }

}
