package need.clarity.mixin;

import need.clarity.clickgui.ClickGuiLauncher;
import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class ClientTickMixin {
    @Unique
    private static boolean cl$lastShiftState = false;
    @Unique
    private static boolean cl$lastCtrlState = false;

    public ClientTickMixin() {
    }

    @Inject(at = @At("HEAD"), method = "tick")
    private void onTick(CallbackInfo arg0) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.getWindow() != null) {
            mc.getWindow().setTitle("Clarity Client");
            long handle = mc.getWindow().handle();
            if (handle != 0L && mc.player != null) {
                // Direct GLFW hardware key check
                boolean shiftDown = GLFW.glfwGetKey(handle, 344) == 1; // GLFW_KEY_RIGHT_SHIFT
                boolean ctrlDown = GLFW.glfwGetKey(handle, 345) == 1;  // GLFW_KEY_RIGHT_CONTROL

                if (shiftDown && !cl$lastShiftState) {
                    ClickGuiLauncher.onRightShift();
                } else if (ctrlDown && !cl$lastCtrlState) {
                    ClickGuiLauncher.onRightShift();
                }

                cl$lastShiftState = shiftDown;
                cl$lastCtrlState = ctrlDown;
            }
        }
    }
}
