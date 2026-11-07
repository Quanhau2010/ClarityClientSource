package need.clarity.modules.movement;

import need.clarity.api.Hooks;
import need.clarity.modules.ModuleBase;

public class AntiPush extends ModuleBase {
    public static AntiPush INSTANCE;

    public AntiPush() {
        super("Anti Push", "Movement");
        INSTANCE = this;
        Hooks.Bool0 var6 = () -> AntiPush.isEligible();
        Hooks.bool0("AntiPush.shouldCancel", var6);
    }

    public static boolean isEligible() {
        int var3 = 0;
        if (INSTANCE != null) {
            if (INSTANCE.isEnabled()) {
                var3 = 1;
            }
        } else {
            var3 = 0;
        }
        return var3;
    }

}
