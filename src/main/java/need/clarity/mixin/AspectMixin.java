package need.clarity.mixin;

import need.clarity.api.Modules;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Camera.class)
public abstract class AspectMixin {
    public AspectMixin() {
    }

    @Inject(method = "extractRenderState", at = @At("TAIL"))
    private void clarity$aspect(CameraRenderState arg0, float arg1, CallbackInfo arg2) {
        if (!Modules.aspectActive()) {
            return;
        }
        if (arg0.projectionMatrix == null) {
            return;
        }
        arg0.projectionMatrix.scale(Modules.aspectFactor(), 1.0F, 1.0F);
    }

}
