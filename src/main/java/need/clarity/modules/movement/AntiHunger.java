package need.clarity.modules.movement;

import need.clarity.api.Hooks;
import need.clarity.modules.ModuleBase;
import net.minecraft.client.Minecraft;

public class AntiHunger extends ModuleBase {
    public static AntiHunger INSTANCE;

    public AntiHunger() {
        super("Anti Hunger", "Movement");
        INSTANCE = this;
        Hooks.Bool0 var6 = () -> AntiHunger.lambda$b();
        Hooks.bool0("AntiHunger.spoof", var6);
    }

    public boolean isEligible() {
        Minecraft var1 = Minecraft.getInstance();
        if (var1.player == null) {
            return false;
        }
        if (!var1.player.onGround()) {
            return false;
        }
        if (Double.compare(var1.player.fallDistance, 0.5) > 0) {
            return false;
        }
        if (var1.gameMode != null) {
            if (var1.gameMode.isDestroying()) {
                return false;
            }
        }
        return true;
    }

}
