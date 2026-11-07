package need.clarity.mixin;

import need.clarity.clickgui.ClickGuiRenderer;
import need.clarity.clickgui.ClickGuiScreen;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class ClickGuiSceneMixin {
    public ClickGuiSceneMixin() {
    }

    @Inject(method = "renderLevel", at = @At("TAIL"))
    private void clarity$captureClickGuiScene(DeltaTracker arg0, CallbackInfo arg1) {
        Screen var4 = Minecraft.getInstance().screen;
        if ((var4 instanceof ClickGuiScreen)) {
            ClickGuiRenderer.captureScene(((ClickGuiScreen) var4).gui());
        }
    }

}
