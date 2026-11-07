package need.clarity.mixin;

import need.clarity.api.FreecamApi;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Player.class, priority = 900)
public class FreecamPlayerMixin {
    public FreecamPlayerMixin() {
    }

    @Inject(method = "travel", at = @At("HEAD"), cancellable = true)
    private void clarity$freecamFreezeTravel(Vec3 arg0, CallbackInfo arg1) {
        if (FreecamApi.isActive()) {
            if (FreecamApi.shouldFreezePlayer()) {
                arg1.cancel();
            }
            return;
        }
    }

}
