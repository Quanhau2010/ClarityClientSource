package need.clarity.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import need.clarity.api.FreecamApi;
import need.clarity.api.Modules;
import need.clarity.keybind.KeybindHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.input.MouseButtonInfo;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MouseHandler.class)
public class MouseHandlerMixin {
    public MouseHandlerMixin() {
    }

    @WrapOperation(method = "turnPlayer(D)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;turn(DD)V"))
    private void clarity$freecamRedirectTurn(LocalPlayer arg0, double arg1, double arg2, Operation arg3) {
        if (!FreecamApi.isActive()) {
            new Object[3][0] = arg0;
            new Object[3][1] = arg1;
            new Object[3][2] = arg2;
            arg3.call(new Object[3]);
            return;
        }
        FreecamApi.setCameraYaw(Mth.wrapDegrees((FreecamApi.getCameraYaw() + ((float) (arg1 * 0.15)))));
        FreecamApi.setCameraPitch(Mth.clamp((FreecamApi.getCameraPitch() + ((float) (arg2 * 0.15))), -90.0F, 90.0F));
    }

    @Inject(method = "onScroll", at = @At("HEAD"), cancellable = true)
    private void clarity$zoomScroll(long arg0, double arg1, double arg2, CallbackInfo arg3) {
        Minecraft var8 = Minecraft.getInstance();
        if (var8 == null) {
            return;
        }
        if (Modules.hudLayoutIsEditing()) {
            if (var8.screen == null) {
                arg3.cancel();
                return;
            }
            if (var8.screen != null) {
                return;
            }
            if (Modules.zoomActive()) {
                if (arg2 != 0.0) {
                    Modules.zoomScroll(arg2);
                }
                return;
            }
        }
    }

    @Inject(method = "onButton", at = @At("HEAD"), cancellable = true)
    private void onMouseButton(long arg0, MouseButtonInfo arg1, int arg2, CallbackInfo arg3) {
        Minecraft var6 = Minecraft.getInstance();
        if (var6.player == null) {
            return;
        }
        int var7 = arg1.button();
        if (Modules.hudLayoutIsEditing()) {
            if (var6.screen != null) {
                Modules.hudLayoutExitNoGrab();
            } else {
                Modules.hudLayoutOnMouseButton(var7, arg2);
                arg3.cancel();
                return;
            }
            if (var6.screen != null) {
                return;
            }
            if (arg2 == 1) {
                KeybindHandler.onMouseEvent(var7, true);
            } else {
                if (arg2 == 0) {
                    KeybindHandler.onMouseEvent(var7, false);
                }
                return;
            }
        }
    }

}
