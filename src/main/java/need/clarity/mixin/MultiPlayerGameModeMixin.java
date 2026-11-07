package need.clarity.mixin;

import need.clarity.LocalPlayerRotationState;
import need.clarity.api.Rotation;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MultiPlayerGameMode.class)
public class MultiPlayerGameModeMixin {
    @Unique
    private boolean mpgm$swapActive;
    @Unique
    private float mpgm$savedYRot;
    @Unique
    private float mpgm$savedXRot;

    public MultiPlayerGameModeMixin() {
        this.mpgm$swapActive = false;
        this.mpgm$savedYRot = 0.0F;
        this.mpgm$savedXRot = 0.0F;
    }

    @Inject(method = "useItem", at = @At("HEAD"))
    private void mpgm$useItemHead(Player arg0, InteractionHand arg1, CallbackInfoReturnable arg2) {
        float var4 = 0.0F;
        float var5 = 0.0F;
        if (!Rotation.hasSilentRotation()) {
            this.mpgm$swapActive = false;
            return;
        }
        if (LocalPlayerRotationState.tickServerTick == arg0.tickCount) {
            var4 = LocalPlayerRotationState.tickServerYaw;
            var5 = LocalPlayerRotationState.tickServerPitch;
        } else {
            var4 = Rotation.getSilentServerYaw();
            var5 = Rotation.getSilentServerPitch();
        }
        float var6 = arg0.getYRot();
        float var7 = arg0.getXRot();
        float var8 = wrapDeg((var4 - var6));
        float var9 = wrapDeg((var5 - var7));
        if (Math.abs(var8) < 0.05F) {
            if (Math.abs(var9) < 0.05F) {
                this.mpgm$swapActive = false;
                return;
            }
            this.mpgm$savedYRot = var6;
            this.mpgm$savedXRot = var7;
            this.mpgm$swapActive = true;
            Rotation.commitUseItemRotation(var4, var5);
            arg0.setYRot(var4);
            arg0.setXRot(var5);
            return;
        }
    }

    @Inject(method = "useItem", at = @At("RETURN"))
    private void mpgm$useItemReturn(Player arg0, InteractionHand arg1, CallbackInfoReturnable arg2) {
        if (!this.mpgm$swapActive) {
            return;
        }
        this.mpgm$swapActive = false;
        arg0.setYRot(this.mpgm$savedYRot);
        arg0.setXRot(this.mpgm$savedXRot);
    }

    private static float wrapDeg(float arg0) {
        arg0 = (arg0 % 360.0F);
        if (arg0 >= 180.0F) {
            arg0 = (arg0 - 360.0F);
        }
        if (arg0 < -180.0F) {
            arg0 = (arg0 + 360.0F);
        }
        return arg0;
    }

}
