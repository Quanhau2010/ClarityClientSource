package need.clarity.mixin;

import need.clarity.api.OvertickApi;
import net.minecraft.client.renderer.entity.layers.CapeLayer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(CapeLayer.class)
public class CapeLayerMixin {
    public CapeLayerMixin() {
    }

    @Redirect(method = "submit(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;ILnet/minecraft/client/renderer/entity/state/AvatarRenderState;FF)V", at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/entity/state/AvatarRenderState;isInvisible:Z", opcode = 180))
    private boolean cl$overtickGhostCape(AvatarRenderState arg0) {
        if (!arg0.isInvisible) {
            return false;
        }
        if (OvertickApi.isGhostState(arg0)) {
            return false;
        }
        return true;
    }

}
