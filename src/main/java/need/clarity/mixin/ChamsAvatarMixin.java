package need.clarity.mixin;

import need.clarity.api.ChamsApi;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.world.entity.Avatar;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AvatarRenderer.class)
public abstract class ChamsAvatarMixin {
    public ChamsAvatarMixin() {
    }

    @Inject(method = "extractRenderState", at = @At("TAIL"))
    private void chams$tagState(Avatar arg0, AvatarRenderState arg1, float arg2, CallbackInfo arg3) {
        ChamsApi.updateState(arg0, arg1);
    }

}
