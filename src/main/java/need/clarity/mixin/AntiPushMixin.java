package need.clarity.mixin;

import need.clarity.api.Modules;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public class AntiPushMixin {
    public AntiPushMixin() {
    }

    @Inject(method = "push(DDD)V", at = @At("HEAD"), cancellable = true)
    private void cl$antiPushEntity(double arg0, double arg1, double arg2, CallbackInfo arg3) {
        if (!Modules.antiPushShouldCancel()) {
            return;
        }
        Minecraft var8 = Minecraft.getInstance();
        if (var8.player == null) {
            return;
        }
        if (this != var8.player) {
            return;
        }
        arg3.cancel();
    }

}
