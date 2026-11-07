package need.clarity.mixin;

import need.clarity.api.FreecamApi;
import net.minecraft.client.player.ClientInput;
import net.minecraft.client.player.KeyboardInput;
import net.minecraft.world.entity.player.Input;
import net.minecraft.world.phys.Vec2;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = KeyboardInput.class, priority = 900)
public class FreecamInputMixin {
    public FreecamInputMixin() {
    }

    @Inject(method = "tick", at = @At("RETURN"))
    private void clarity$freecamFreezeInput(CallbackInfo arg0) {
        if (FreecamApi.isActive()) {
            if (!FreecamApi.shouldFreezePlayer()) {
            }
        }
        return;
        ClientInput var2 = ((ClientInput) this);
        var2.keyPresses = Input.EMPTY;
        ((ClientInputAccessor) var2).setMoveVector(Vec2.ZERO);
    }

}
