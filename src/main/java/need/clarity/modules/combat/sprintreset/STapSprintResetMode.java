package need.clarity.modules.combat.sprintreset;

import need.clarity.EventStartAttack;
import need.clarity.api.MovementEvent;
import need.clarity.modules.combat.SprintReset;

public class STapSprintResetMode extends AbstractSprintResetMode {
    public STapSprintResetMode() {
        super();
    }

    protected boolean shouldTriggerReset(EventStartAttack arg0) {
        int var9 = 0;
        if (minecraft.player != null) {
            if (Float.compare(minecraft.player.zza, 0.0F) > 0) {
                var9 = 1;
            } else {
            }
        }
        var9 = 0;
        return var9;
    }

    protected void applyMovementReset(MovementEvent arg0) {
        int var44 = 0;
        int var45 = 0;
        int var19 = 0;
        if (SprintResetContext.sprintReset != null) {
            SprintReset var10 = SprintResetContext.sprintReset;
            Integer var58 = 3;
            var45 = ((Integer) var10.getSetting("S-Tap Duration", var58));
        } else {
            var45 = 3;
        }
        int var5 = ((byte) var45);
        if (SprintResetContext.sprintReset != null) {
            SprintReset var41 = SprintResetContext.sprintReset;
            Integer var62 = 0;
            var44 = ((Integer) var41.getSetting("S-Tap Reaction", var62));
        } else {
            var44 = 0;
        }
        int var6 = var44;
        if (SprintResetContext.sprintReset != null) {
            SprintReset var15 = SprintResetContext.sprintReset;
            Boolean var60 = false;
            if (((Boolean) var15.getSetting("S-Tap Ground Only", var60))) {
                var19 = 1;
            } else {
            }
        }
        var19 = 0;
        int var7 = var19;
        int var22 = minecraft.player.tickCount;
        int var48 = this.counterValue;
        int var23 = (var22 - var48);
        int var25 = (var6 + var5);
        if (var23 > var25) {
            this.counterValue = -1;
            return;
        }
        if (var23 <= var6) {
            return;
        }
        if (var7 != 0) {
            if (!minecraft.player.onGround()) {
                return;
            }
        }
        arg0.forward = false;
        arg0.sprint = false;
        arg0.back = true;
        if (minecraft.player.isSprinting()) {
            minecraft.player.setSprinting(false);
        }
    }

}
