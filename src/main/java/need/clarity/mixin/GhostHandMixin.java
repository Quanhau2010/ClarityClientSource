package need.clarity.mixin;

import need.clarity.api.Modules;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class GhostHandMixin {
    public GhostHandMixin() {
    }

    @Inject(method = "handleKeybinds", at = @At("HEAD"))
    private void cl$ghostHandHandleKeybinds(CallbackInfo arg0) {
        Minecraft var2 = ((Minecraft) this);
        if (var2.player != null) {
            if (var2.level == null) {
            }
        }
        return;
        if (!Modules.ghostHandEnabled()) {
            return;
        }
        Modules.ghostHandPick(var2);
    }

}
