package need.clarity.mixin;

import need.clarity.api.RenderApi;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public class ArrayListRenderMixin {
    public ArrayListRenderMixin() {
    }

    @Inject(method = "extractRenderState", at = @At("TAIL"))
    private void clarity$arrayListOverlay(GuiGraphicsExtractor arg0, DeltaTracker arg1, CallbackInfo arg2) {
        Minecraft var4 = Minecraft.getInstance();
        if (var4 == null) {
            return;
        }
        RenderApi.arrayListRender(arg0, ((float) var4.getWindow().getGuiScaledWidth()), ((float) var4.getWindow().getGuiScaledHeight()), ((float) var4.getWindow().getWidth()), ((float) var4.getWindow().getHeight()));
    }

}
