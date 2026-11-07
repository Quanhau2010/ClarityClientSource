package need.clarity.modules.movement;

import need.clarity.PerMovementEvents;
import need.clarity.api.MovementEvent;
import need.clarity.modules.ModuleBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;

public class Sprint extends ModuleBase {
    public Sprint() {
        super("Sprint", "Movement");
    }

    public void onDisable() {
        Minecraft var1 = Minecraft.getInstance();
        if (var1.player != null) {
            var1.player.setSprinting(false);
        }
    }

    public void onTick() {
        Minecraft var1 = Minecraft.getInstance();
        if (var1.player != null) {
            if (var1.level == null) {
            }
        }
        return;
        LocalPlayer var11 = var1.player;
        if (this.isEligible(var1, var11)) {
            var11.setSprinting(true);
        }
    }

    private boolean isEligible(Minecraft arg0, Player arg1) {
        boolean var38 = false;
        MovementEvent var3 = PerMovementEvents.lastEvent;
        if (var3 != null) {
            var38 = var3.forward;
        } else {
            var38 = arg0.options.keyUp.isDown();
        }
        if (!var38) {
            return false;
        }
        if (arg1.getFoodData().getFoodLevel() <= 6) {
            return false;
        }
        if (arg1.isShiftKeyDown()) {
            return false;
        }
        if (arg1.isBlocking()) {
            return false;
        }
        if (arg1.isUsingItem()) {
            return false;
        }
        Holder var39 = MobEffects.BLINDNESS;
        if (arg1.hasEffect(var39)) {
            return false;
        }
        if (!arg1.onGround()) {
            if (!arg1.isSprinting()) {
                return false;
            }
        }
        return true;
    }

}
