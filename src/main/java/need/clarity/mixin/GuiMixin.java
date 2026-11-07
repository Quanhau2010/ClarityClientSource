package need.clarity.mixin;

import need.clarity.api.Modules;
import need.clarity.api.RenderApi;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public class GuiMixin {
    public GuiMixin() {
    }

    @Inject(method = "extractCrosshair", at = @At("HEAD"), cancellable = true)
    private void clarity$customCrosshair(GuiGraphicsExtractor arg0, DeltaTracker arg1, CallbackInfo arg2) {
        if (RenderApi.crosshairRender(arg0)) {
            arg2.cancel();
        }
    }

    @Inject(method = "extractConfusionOverlay", at = @At("HEAD"), cancellable = true)
    private void clarity$noNausea(GuiGraphicsExtractor arg0, float arg1, CallbackInfo arg2) {
        if (Modules.noRenderNausea()) {
            arg2.cancel();
        }
    }

    @Inject(method = "extractPortalOverlay", at = @At("HEAD"), cancellable = true)
    private void clarity$noPortal(GuiGraphicsExtractor arg0, float arg1, CallbackInfo arg2) {
        if (Modules.noRenderPortal()) {
            arg2.cancel();
        }
    }

    @Inject(method = "extractSpyglassOverlay", at = @At("HEAD"), cancellable = true)
    private void clarity$noSpyglass(GuiGraphicsExtractor arg0, float arg1, CallbackInfo arg2) {
        if (Modules.noRenderSpyglass()) {
            arg2.cancel();
        }
    }

    @Inject(method = "extractScoreboardSidebar", at = @At("HEAD"), cancellable = true)
    private void clarity$noScoreboard(GuiGraphicsExtractor arg0, DeltaTracker arg1, CallbackInfo arg2) {
        if (Modules.noRenderScoreboard()) {
            arg2.cancel();
        }
    }

    @Inject(method = "extractVignette", at = @At("HEAD"), cancellable = true)
    private void clarity$noVignette(GuiGraphicsExtractor arg0, Entity arg1, CallbackInfo arg2) {
        if (Modules.noRenderVignette()) {
            arg2.cancel();
        }
    }

}
