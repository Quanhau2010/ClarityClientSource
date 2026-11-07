package need.clarity.mixin;

import need.clarity.LocalPlayerRotationState;
import need.clarity.api.Movement;
import need.clarity.api.Rotation;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Player.class)
public class PlayerMixin {
    public PlayerMixin() {
    }

    @Redirect(method = "travel", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;getLookAngle()Lnet/minecraft/world/phys/Vec3;"))
    private Vec3 cl$redirectSwimmingLookVector(Player arg0) {
        Minecraft var2 = Minecraft.getInstance();
        if (arg0 == var2.player) {
            if (LocalPlayerRotationState.silentActive) {
                if (Movement.shouldApplyRotationFix()) {
                    return Rotation.getRotationVector(LocalPlayerRotationState.tickServerPitch, LocalPlayerRotationState.tickServerYaw);
                }
                return arg0.getLookAngle();
            }
        }
    }

    @Redirect(method = {"causeExtraKnockback", "doSweepAttack"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;getYRot()F"))
    private float cl$redirectAttackYaw(Player arg0) {
        Minecraft var2 = Minecraft.getInstance();
        if (arg0 == var2.player) {
            if (LocalPlayerRotationState.silentActive) {
                return arg0.getYRot();
            }
            return arg0.getYRot();
        }
    }

}
