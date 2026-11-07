package need.clarity.mixin;

import need.clarity.api.Modules;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityRenderer.class)
public class NametagsMixin {
    public NametagsMixin() {
    }

    @Inject(method = "shouldShowName", at = @At("HEAD"), cancellable = true)
    private void clarity$hideVanillaNametag(Entity arg0, double arg1, CallbackInfoReturnable arg2) {
        if (Modules.nametagsActive()) {
            if (Modules.nametagsHideVanilla()) {
                arg2.setReturnValue(false);
            }
            return;
        }
    }

}
