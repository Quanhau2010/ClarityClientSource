package need.clarity.modules.movement;

import need.clarity.api.Hooks;
import need.clarity.modules.ModuleBase;

public class AntiPowderSnow extends ModuleBase {
    public static AntiPowderSnow INSTANCE;

    public AntiPowderSnow() {
        super("Anti Powder Snow", "Movement");
        INSTANCE = this;
        Hooks.Bool0 var6 = () -> AntiPowderSnow.lambda$a();
        Hooks.bool0("AntiPowderSnow.enabled", var6);
    }

}
