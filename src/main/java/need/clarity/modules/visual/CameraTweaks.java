package need.clarity.modules.visual;

import need.clarity.api.Hooks;
import need.clarity.modules.ModuleBase;

public class CameraTweaks extends ModuleBase {
    private static CameraTweaks INSTANCE;

    public CameraTweaks() {
        super("Camera Tweaks", "Visual");
        INSTANCE = this;
        Hooks.Bool0 var9 = () -> CameraTweaks.isEligible();
        Hooks.bool0("CameraTweaks.isActive", var9);
        Hooks.Bool0 var10 = () -> CameraTweaks.isActive();
        Hooks.bool0("CameraTweaks.customDistanceEnabled", var10);
        Hooks.Dbl0 var11 = () -> CameraTweaks.customDistanceEnabled();
        Hooks.dbl0("CameraTweaks.customDistance", var11);
        Hooks.Bool0 var12 = () -> CameraTweaks.customDistance();
        Hooks.bool0("CameraTweaks.noCameraClip", var12);
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

    public static boolean customDistance() {
        if (INSTANCE == null) {
            return false;
        }
        CameraTweaks var4 = INSTANCE;
        Boolean var8 = true;
        return ((Boolean) var4.getSetting("No Camera Clip", var8));
    }

    public static boolean isActive() {
        if (INSTANCE == null) {
            return false;
        }
        CameraTweaks var3 = INSTANCE;
        Boolean var8 = false;
        return ((Boolean) var3.getSetting("Camera Distance", var8));
    }

    public static float customDistanceEnabled() {
        if (INSTANCE == null) {
            return 4.0F;
        }
        CameraTweaks var3 = INSTANCE;
        Float var8 = 4.0F;
        return ((Float) var3.getSetting("Distance", var8));
    }

}
