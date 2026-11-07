package need.clarity.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import need.clarity.api.Modules;
import need.clarity.api.RenderApi;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemInHandRenderer.class)
public class ViewModelMixin {
    @Unique
    private boolean clarity$pushed;

    public ViewModelMixin() {
        this.clarity$pushed = false;
    }

    @Inject(method = "renderArmWithItem", at = @At("HEAD"))
    private void clarity$vmHead(AbstractClientPlayer arg0, float arg1, float arg2, InteractionHand arg3, float arg4, ItemStack arg5, float arg6, PoseStack arg7, SubmitNodeCollector arg8, int arg9, CallbackInfo arg10) {
        this.clarity$pushed = false;
        if (!Modules.viewModelActive()) {
            return;
        }
        arg7.pushPose();
        arg7.translate(Modules.viewModelPosX(arg3), Modules.viewModelPosY(arg3), Modules.viewModelPosZ(arg3));
        arg7.mulPose(Axis.XP.rotationDegrees(Modules.viewModelRotX(arg3)));
        arg7.mulPose(Axis.YP.rotationDegrees(Modules.viewModelRotY(arg3)));
        arg7.mulPose(Axis.ZP.rotationDegrees(Modules.viewModelRotZ(arg3)));
        float var12 = Modules.viewModelScale(arg3);
        if (arg0 != null) {
            if (var12 != 1.0F) {
                HumanoidArm var13 = arg0.getMainArm();
                if (arg3 == InteractionHand.MAIN_HAND) {
                } else {
                }
                HumanoidArm var14 = var13.getOpposite();
                if (var14 == HumanoidArm.RIGHT) {
                } else {
                }
                float var15 = -1.0F;
                if (RenderApi.animShouldAnimate(var14)) {
                } else {
                }
                float var16 = arg6;
                float var17 = (-0.52F + (var16 * -0.6F));
                arg7.translate((var15 * 0.56F), var17, -0.72F);
                arg7.scale(var12, var12, var12);
                arg7.translate((-(var15) * 0.56F), -(var17), 0.72F);
            } else {
                arg7.scale(var12, var12, var12);
            }
            this.clarity$pushed = true;
            return;
        }
    }

    @Inject(method = "renderArmWithItem", at = @At("RETURN"))
    private void clarity$vmTail(AbstractClientPlayer arg0, float arg1, float arg2, InteractionHand arg3, float arg4, ItemStack arg5, float arg6, PoseStack arg7, SubmitNodeCollector arg8, int arg9, CallbackInfo arg10) {
        if (this.clarity$pushed) {
            arg7.popPose();
            this.clarity$pushed = false;
        }
    }

}
