package need.clarity.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import need.clarity.api.SkyApi;
import net.minecraft.client.renderer.SkyRenderer;
import net.minecraft.world.level.MoonPhase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SkyRenderer.class)
public abstract class SkyShadersSkyRendererMixin {
    public SkyShadersSkyRendererMixin() {
    }

    @Inject(method = "renderSkyDisc(I)V", at = @At("HEAD"), cancellable = true)
    private void clarity$skyDisc(int arg0, CallbackInfo arg1) {
        if (SkyApi.tryRender(false)) {
            arg1.cancel();
        }
    }

    @Inject(method = "renderEndSky()V", at = @At("HEAD"), cancellable = true)
    private void clarity$endSky(CallbackInfo arg0) {
        if (SkyApi.tryRender(true)) {
            arg0.cancel();
        }
    }

    @Inject(method = "renderDarkDisc()V", at = @At("HEAD"), cancellable = true)
    private void clarity$darkDisc(CallbackInfo arg0) {
        if (SkyApi.isActiveThisFrame()) {
            arg0.cancel();
        }
    }

    @Inject(method = "renderSunriseAndSunset(Lcom/mojang/blaze3d/vertex/PoseStack;FI)V", at = @At("HEAD"), cancellable = true)
    private void clarity$sunrise(PoseStack arg0, float arg1, int arg2, CallbackInfo arg3) {
        if (SkyApi.isActiveThisFrame()) {
            arg3.cancel();
        }
    }

    @Inject(method = "renderSunMoonAndStars(Lcom/mojang/blaze3d/vertex/PoseStack;FFFLnet/minecraft/world/level/MoonPhase;FF)V", at = @At("HEAD"), cancellable = true)
    private void clarity$sunMoonStars(PoseStack arg0, float arg1, float arg2, float arg3, MoonPhase arg4, float arg5, float arg6, CallbackInfo arg7) {
        if (SkyApi.isActiveThisFrame()) {
            if (!SkyApi.keepSunMoon()) {
                arg7.cancel();
            }
            return;
        }
    }

    @Inject(method = "renderStars(FLcom/mojang/blaze3d/vertex/PoseStack;)V", at = @At("HEAD"), cancellable = true)
    private void clarity$stars(float arg0, PoseStack arg1, CallbackInfo arg2) {
        if (SkyApi.isActiveThisFrame()) {
            arg2.cancel();
        }
    }

    @Inject(method = "renderEndFlash(Lcom/mojang/blaze3d/vertex/PoseStack;FFF)V", at = @At("HEAD"), cancellable = true)
    private void clarity$endFlash(PoseStack arg0, float arg1, float arg2, float arg3, CallbackInfo arg4) {
        if (SkyApi.isActiveThisFrame()) {
            arg4.cancel();
        }
    }

}
