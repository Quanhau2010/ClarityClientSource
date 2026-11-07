package need.clarity.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import need.clarity.api.RenderApi;
import net.minecraft.client.model.geom.ModelPart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ModelPart.class)
public abstract class ModelPartHandMixin {
    public ModelPartHandMixin() {
    }

    @Inject(method = "translateAndRotate", at = @At("HEAD"), cancellable = true)
    private void cl$modelHand(PoseStack arg0, CallbackInfo arg1) {
        if (!RenderApi.modelHandsActive()) {
            return;
        }
        try {
            if (RenderApi.modelHandsApply(((ModelPart) this), arg0)) {
                arg1.cancel();
            }
            return;
        } catch (Throwable ignored) {
        }
    }

}
