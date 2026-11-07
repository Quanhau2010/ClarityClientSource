package need.clarity.modules.combat;

import need.clarity.api.Hooks;
import need.clarity.modules.ModuleBase;
import need.clarity.modules.ModuleRegistry;

public class Reach extends ModuleBase {
    public Reach() {
        super("Reach", "Combat");
        Hooks.Dbl0 var6 = () -> Reach.getReach();
        Hooks.dbl0("Reach.block", var6);
        Hooks.Dbl0 var7 = () -> Reach.block();
        Hooks.dbl0("Reach.entity", var7);
    }

    public static float getReach() {
        Reach var5 = ((Reach) ModuleRegistry.get("Reach"));
        if (var5 != null) {
            if (!var5.isEnabled()) {
            }
        }
        return -1.0F;
        Float var14 = 4.5F;
        return ((Float) var5.getSetting("Block Reach", var14));
    }

    public static float block() {
        Reach var5 = ((Reach) ModuleRegistry.get("Reach"));
        if (var5 != null) {
            if (!var5.isEnabled()) {
            }
        }
        return -1.0F;
        Float var14 = 3.0F;
        return ((Float) var5.getSetting("Entity Reach", var14));
    }

}
