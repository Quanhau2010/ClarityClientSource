package need.clarity.mixin;

import need.clarity.EventStartAttack;
import need.clarity.PerAttackEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Minecraft.class)
public class MinecraftStartAttackMixin {
    @Shadow
    public LocalPlayer player;
    @Shadow
    public HitResult hitResult;

    public MinecraftStartAttackMixin() {
    }

    @Inject(method = "startAttack", at = @At("HEAD"))
    private void cl$onStartAttack(CallbackInfoReturnable arg0) {
        Entity var2 = null;
        if (this.player == null) {
            return;
        }
        var2 = null;
        if (this.hitResult != null) {
            if (this.hitResult.getType() == HitResult.Type.ENTITY) {
                var2 = ((EntityHitResult) this.hitResult).getEntity();
            }
            PerAttackEvents.fire(new EventStartAttack(this.player, var2));
            return;
        }
    }

}
