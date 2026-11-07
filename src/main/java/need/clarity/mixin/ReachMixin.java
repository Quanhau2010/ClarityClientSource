package need.clarity.mixin;

import need.clarity.api.Modules;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ClipContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Minecraft.class)
public class ReachMixin {
    public ReachMixin() {
    }

    @ModifyArg(method = "pick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/BlockGetter;clip(Lnet/minecraft/world/level/ClipContext;)Lnet/minecraft/world/phys/BlockHitResult;"), index = 0, require = 0)
    private ClipContext reach$modifyClipContext(ClipContext arg0) {
        return arg0;
    }

    @ModifyArg(method = "handleKeybinds", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;pick(F)V"), index = 0, require = 0)
    private float reach$extendBlockReach(float arg0) {
        float var2 = Modules.reachBlock();
        if (var2 > 0.0F) {
        } else {
        }
        return arg0;
    }

    @Redirect(method = "startAttack", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;distanceTo(Lnet/minecraft/world/entity/Entity;)F"), require = 0)
    private float reach$redirectEntityDistance(Entity arg0, Entity arg1) {
        float var3 = arg0.distanceTo(arg1);
        float var4 = Modules.reachEntity();
        if (var4 <= 0.0F) {
            return var3;
        }
        if (var3 <= var4) {
            return Math.min(var3, 3.0F);
        }
        return var3;
    }

}
