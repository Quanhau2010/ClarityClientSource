package need.clarity.mixin;

import need.clarity.api.Modules;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.PowderSnowBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PowderSnowBlock.class)
public class PowderSnowBlockMixin {
    public PowderSnowBlockMixin() {
    }

    @Inject(method = "canEntityWalkOnPowderSnow", at = @At("HEAD"), cancellable = true)
    private static void cl$antiPowderSnow(Entity arg0, CallbackInfoReturnable arg1) {
        if (!Modules.antiPowderEnabled()) {
            return;
        }
        if (arg0 != Minecraft.getInstance().player) {
            return;
        }
        arg1.setReturnValue(true);
    }

}
