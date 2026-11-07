package need.clarity.mixin;

import need.clarity.api.Modules;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class NoSwingMixin {
    public NoSwingMixin() {
    }

    @Inject(method = "swing(Lnet/minecraft/world/InteractionHand;)V", at = @At("HEAD"), cancellable = true)
    private void clarity$noSwing(InteractionHand arg0, CallbackInfo arg1) {
        if (!Modules.noSwingActive()) {
            return;
        }
        if (this == Minecraft.getInstance().player) {
            arg1.cancel();
        }
    }

}
