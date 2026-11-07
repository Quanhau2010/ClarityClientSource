package need.clarity.modules.movement;

import need.clarity.modules.ModuleBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffects;

public class AntiLevitation extends ModuleBase {
    public AntiLevitation() {
        super("Anti Levitation", "Movement");
    }

    public void onTick() {
        Minecraft var1 = Minecraft.getInstance();
        if (var1.player == null) {
            return;
        }
        LocalPlayer var7 = var1.player;
        Holder var2 = MobEffects.LEVITATION;
        boolean var8 = var7.removeEffect(var2);
    }

}
