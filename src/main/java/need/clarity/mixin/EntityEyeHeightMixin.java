package need.clarity.mixin;

import need.clarity.api.OvertickApi;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Pose;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class EntityEyeHeightMixin {
    public EntityEyeHeightMixin() {
    }

    @Inject(method = "getEyeHeight()F", at = @At("HEAD"), cancellable = true)
    private void cl$standingEyeWhileGlideHidden(CallbackInfoReturnable arg0) {
        Entity var2 = null;
        try {
            var2 = ((Entity) this);
            if (var2 != Minecraft.getInstance().player) {
            }
        } catch (Throwable ignored) {
        }
        return;
        try {
            if (OvertickApi.isEnabled()) {
                if (!OvertickApi.isElytraStopHeld()) {
                }
            }
            return;
            try {
                arg0.setReturnValue(var2.getEyeHeight(Pose.STANDING));
            } catch (Throwable ignored) {
            }
            return;
        } catch (Throwable ignored) {
        }
    }

}
