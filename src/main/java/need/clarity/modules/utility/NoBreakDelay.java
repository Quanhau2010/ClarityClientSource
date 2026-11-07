package need.clarity.modules.utility;

import need.clarity.mixin.MultiPlayerGameModeAccessor;
import need.clarity.modules.ModuleBase;
import net.minecraft.client.Minecraft;

public class NoBreakDelay extends ModuleBase {
    private final Minecraft minecraft;

    public NoBreakDelay() {
        super("No Break Delay", "Utility");
        Minecraft var5 = Minecraft.getInstance();
        this.minecraft = var5;
    }

    public void onTick() {
        if (this.minecraft.gameMode == null) {
            return;
        }
        ((MultiPlayerGameModeAccessor) this.minecraft.gameMode).setDestroyDelay(0);
    }

}
