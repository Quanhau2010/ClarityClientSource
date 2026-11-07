package need.clarity.modules.utility;

import java.lang.reflect.Field;
import need.clarity.modules.ModuleBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.LivingEntity;

public class NoJumpDelay extends ModuleBase {
    private static final Field reflectedField;

    private static Field evaluate() {
        try {
            Field var3 = LivingEntity.class.getDeclaredField("noJumpDelay");
            var3.setAccessible(true);
        } catch (NoSuchFieldException ignored) {
        }
        return var3;
    }

    public NoJumpDelay() {
        super("No Jump Delay", "Utility");
    }

    public void onTick() {
        Minecraft var1 = Minecraft.getInstance();
        if (var1.player != null) {
            if (var1.level == null) {
            }
        }
        return;
        if (reflectedField == null) {
            return;
        }
        try {
            Field var11 = reflectedField;
            LocalPlayer var13 = var1.player;
            var11.setInt(var13, 0);
        } catch (IllegalAccessException ignored) {
        }
    }

    static {
        reflectedField = evaluate();
    }

}
