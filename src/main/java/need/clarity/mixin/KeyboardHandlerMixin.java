package need.clarity.mixin;

import need.clarity.api.Modules;
import need.clarity.keybind.KeybindHandler;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.input.KeyEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyboardHandler.class)
public class KeyboardHandlerMixin {
    public KeyboardHandlerMixin() {
    }

    @Inject(method = "keyPress", at = @At("HEAD"), cancellable = true)
    private void onKeyPress(long arg0, int arg1, KeyEvent arg2, CallbackInfo arg3) {
        Minecraft var6 = Minecraft.getInstance();
        if (var6.player == null) {
            return;
        }
        int var7 = arg2.key();

        // Direct Right Shift (344) or Right Control (345) toggle for ClickGUI
        if (arg1 == 1) { // GLFW_PRESS
            if (var7 == 344 || var7 == 345) {
                try {
                    System.out.println("[Clarity] Key pressed: " + var7 + ", toggling ClickGUI!");
                    need.clarity.clickgui.ClickGuiLauncher.onRightShift();
                    arg3.cancel();
                    return;
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }
        }

        if (arg1 == 0) { // GLFW_RELEASE
            if (var6.screen == null || Modules.hudLayoutIsEditing()) {
                KeybindHandler.onKeyEvent(var7, false);
            }
        }
        if (Modules.hudLayoutIsEditing()) {
            if (var6.screen != null) {
                Modules.hudLayoutExitNoGrab();
            } else {
                if (arg1 == 1) {
                    if (var7 == 345 || var7 == 256) {
                        Modules.hudLayoutExit();
                    }
                }
                arg3.cancel();
                return;
            }
        }
        if (var6.screen != null) {
            return;
        }
        if (arg1 == 1) {
            KeybindHandler.onKeyEvent(var7, true);
        } else if (arg1 == 0) {
            KeybindHandler.onKeyEvent(var7, false);
        }
    }
}
