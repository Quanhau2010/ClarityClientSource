package need.clarity.modules.visual;

import need.clarity.api.Hooks;
import need.clarity.modules.ModuleBase;

public class NoSwing extends ModuleBase {
    public static NoSwing INSTANCE;

    public NoSwing() {
        super("No Swing", "Visual");
        INSTANCE = this;
        Hooks.Bool0 var6 = () -> NoSwing.isEligible();
        Hooks.bool0("NoSwing.isActive", var6);
    }

    public static boolean isEligible() {
        int var3 = 0;
        if (INSTANCE != null) {
            if (INSTANCE.isEnabled()) {
                var3 = 1;
            } else {
                var3 = 0;
            }
            return var3;
        }
    }

}
