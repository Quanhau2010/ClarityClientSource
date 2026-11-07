package need.clarity.modules.visual;

import java.util.Iterator;
import need.clarity.modules.ModuleBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;

public class TrueSight extends ModuleBase {
    public static TrueSight INSTANCE;

    public TrueSight() {
        super("True Sight", "Visual");
        INSTANCE = this;
    }

    public void onTick() {
        Minecraft var1 = Minecraft.getInstance();
        if (var1 != null) {
            if (var1.level != null) {
                if (var1.player == null) {
                }
            }
            return;
            Boolean var34 = true;
            boolean var16 = ((Boolean) this.getSetting("Hide Self", var34));
            Iterator var20 = var1.level.entitiesForRendering().iterator();
            while (true) {
                if (!var20.hasNext()) break;
                Entity var25 = ((Entity) var20.next());
                LocalPlayer var32 = var1.player;
                if (var25 == var32) {
                    if (var16) {
                    }
                }
                if (!var25.isInvisible()) continue;
                var25.setInvisible(false);
            }
            return;
        }
    }

}
