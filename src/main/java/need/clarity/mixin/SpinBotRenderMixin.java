package need.clarity.mixin;

import need.clarity.api.RenderApi;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.world.entity.Avatar;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = AvatarRenderer.class, priority = 5000)
public class SpinBotRenderMixin {
    public SpinBotRenderMixin() {
    }

    @Inject(method = "extractRenderState", at = @At("TAIL"))
    private void cl$spinBotOverride(Avatar arg0, AvatarRenderState arg1, float arg2, CallbackInfo arg3) {
        if (!RenderApi.spinBotClientMode()) {
            return;
        }
        if (arg0 != Minecraft.getInstance().player) {
            return;
        }
        arg1.bodyRot = RenderApi.spinBotYaw(arg2);
        arg1.yRot = 0.0F;
    }

}
