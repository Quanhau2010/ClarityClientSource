package need.clarity.mixin;

import need.clarity.api.Modules;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.inventory.ChestMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class SetScreenMixin {
    @Shadow
    public Screen screen;
    @Shadow
    public MouseHandler mouseHandler;

    public SetScreenMixin() {
    }

    @Inject(method = "setScreen", at = @At("RETURN"))
    private void cl$suppressChestScreenAfterOpen(Screen arg0, CallbackInfo arg1) {
        if ((arg0 instanceof AbstractContainerScreen)) {
            AbstractContainerScreen var3 = ((AbstractContainerScreen) arg0);
        } else {
            return;
        }
        if (!(var3.getMenu() instanceof ChestMenu)) {
            return;
        }
        if (!Modules.chestStealerTryCapture()) {
            return;
        }
        this.screen = null;
        this.mouseHandler.grabMouse();
    }

}
