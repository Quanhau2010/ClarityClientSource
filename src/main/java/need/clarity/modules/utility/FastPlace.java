package need.clarity.modules.utility;

import java.lang.reflect.Field;
import need.clarity.modules.ModuleBase;
import net.minecraft.client.Minecraft;

public class FastPlace extends ModuleBase {
    private static final Field reflectedField;

    private static Field evaluate() {
        try {
            Field var3 = Minecraft.class.getDeclaredField("rightClickDelay");
            var3.setAccessible(true);
        } catch (NoSuchFieldException ignored) {
        }
        return var3;
    }

    public FastPlace() {
        super("Fast Place", "Utility");
    }

    public void onTick() {
        int var5 = 0;
        if (reflectedField == null) {
            return;
        }
        Minecraft var7 = Minecraft.getInstance();
        if (var7.player != null) {
            if (var7.level == null) {
            }
        }
        return;
        Integer var29 = 0;
        var5 = ((Integer) this.getSetting("Delay", var29));
        if (var5 < 0) {
            var5 = 0;
        }
        if (var5 > 4) {
            var5 = 4;
        }
        try {
            int var21 = reflectedField.getInt(var7);
            int var27 = var5;
            if (var21 > var27) {
                Field var23 = reflectedField;
                int var30 = var5;
                var23.setInt(var7, var30);
            }
        } catch (IllegalAccessException ignored) {
        }
    }

    static {
        reflectedField = evaluate();
    }

}
