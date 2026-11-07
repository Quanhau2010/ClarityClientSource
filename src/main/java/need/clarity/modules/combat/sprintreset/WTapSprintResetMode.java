package need.clarity.modules.combat.sprintreset;

import need.clarity.EventStartAttack;
import need.clarity.api.MovementEvent;
import need.clarity.modules.combat.SprintReset;

public class WTapSprintResetMode extends AbstractSprintResetMode {
    public WTapSprintResetMode() {
        super();
    }

    protected boolean shouldTriggerReset(EventStartAttack arg0) {
        int var9 = 0;
        if (minecraft.player != null) {
            if (Float.compare(minecraft.player.zza, 0.0F) > 0) {
                var9 = 1;
            }
        } else {
            var9 = 0;
        }
        return var9;
    }

    protected void applyMovementReset(MovementEvent arg0) {
        int var42 = 0;
        int var43 = 0;
        int var44 = 0;
        if (SprintResetContext.sprintReset != null) {
            SprintReset var10 = SprintResetContext.sprintReset;
            Integer var56 = 3;
            var43 = ((Integer) var10.getSetting("W-Tap Duration", var56));
        } else {
            var43 = 3;
        }
        int var5 = ((byte) var43);
        if (SprintResetContext.sprintReset != null) {
            SprintReset var39 = SprintResetContext.sprintReset;
            Integer var60 = 0;
            var42 = ((Integer) var39.getSetting("W-Tap Reaction", var60));
        } else {
            var42 = 0;
        }
        int var6 = var42;
        if (SprintResetContext.sprintReset != null) {
            SprintReset var15 = SprintResetContext.sprintReset;
            Boolean var58 = false;
            if (((Boolean) var15.getSetting("W-Tap Ground Only", var58))) {
                var44 = 1;
            } else {
            }
        }
        var44 = 0;
        int var7 = var44;
        int var21 = minecraft.player.tickCount;
        int var47 = this.counterValue;
        int var22 = (var21 - var47);
        int var24 = (var6 + var5);
        if (var22 > var24) {
            this.counterValue = -1;
            return;
        }
        if (var22 <= var6) {
            return;
        }
        if (var7 != 0) {
            if (!minecraft.player.onGround()) {
                return;
            }
        }
        arg0.forward = false;
        arg0.sprint = false;
        if (minecraft.player.isSprinting()) {
            minecraft.player.setSprinting(false);
        }
    }

}
