package need.clarity.modules.visual;

import need.clarity.api.Hooks;
import need.clarity.modules.ModuleBase;

public class NoHurtCam extends ModuleBase {
    public static NoHurtCam INSTANCE;

    public NoHurtCam() {
        super("No Hurt Cam", "Visual");
        INSTANCE = this;
        Hooks.Bool0 var6 = () -> NoHurtCam.isEligible();
        Hooks.bool0("NoHurtCam.isActive", var6);
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
