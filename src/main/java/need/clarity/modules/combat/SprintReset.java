package need.clarity.modules.combat;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;
import need.clarity.EventStartAttack;
import need.clarity.PerAttackEvents;
import need.clarity.PerMovementEvents;
import need.clarity.api.MovementEvent;
import need.clarity.modules.ModuleBase;
import need.clarity.modules.combat.sprintreset.AbstractSprintResetMode;
import need.clarity.modules.combat.sprintreset.STapSprintResetMode;
import need.clarity.modules.combat.sprintreset.SprintResetContext;
import need.clarity.modules.combat.sprintreset.WTapSprintResetMode;

public class SprintReset extends ModuleBase {
    private static final String labelText = "W-Tap";
    private static final String labelText1 = "S-Tap";
    private final Map entriesMap;

    public SprintReset() {
        super("Sprint Reset", "Combat");
        LinkedHashMap var16 = new LinkedHashMap();
        this.entriesMap = var16;
        SprintResetContext.sprintReset = this;
        Map var7 = this.entriesMap;
        WTapSprintResetMode var19 = new WTapSprintResetMode();
        Object var8 = var7.put("W-Tap", var19);
        Map var10 = this.entriesMap;
        STapSprintResetMode var20 = new STapSprintResetMode();
        Object var11 = var10.put("S-Tap", var20);
        PerAttackEvents.register((p0) -> this.onStartAttack(p0));
        PerMovementEvents.register((p0) -> this.onMovementTick(p0));
    }

    public void onEnable() {
        Collection var4 = this.entriesMap.values();
        Consumer var2 = AbstractSprintResetMode::onEnable;
        var4.forEach(var2);
    }

    public void onDisable() {
        Collection var4 = this.entriesMap.values();
        Consumer var2 = AbstractSprintResetMode::onDisable;
        var4.forEach(var2);
    }

    private void onStartAttack(EventStartAttack arg0) {
        if (!this.isEnabled()) {
            return;
        }
        AbstractSprintResetMode var7 = this.getMode();
        if (var7 != null) {
            var7.onStartAttack(arg0);
        }
    }

    private void onMovementTick(MovementEvent arg0) {
        if (!this.isEnabled()) {
            return;
        }
        AbstractSprintResetMode var7 = this.getMode();
        if (var7 != null) {
            var7.onMovementTick(arg0);
        }
    }

    private AbstractSprintResetMode getMode() {
        String var6 = ((String) this.getSetting("Mode", "W-Tap"));
        return ((AbstractSprintResetMode) this.entriesMap.get(var6));
    }

    public Object getSetting(String arg0, Object arg1) {
        return super.getSetting(arg0, arg1);
    }

}
