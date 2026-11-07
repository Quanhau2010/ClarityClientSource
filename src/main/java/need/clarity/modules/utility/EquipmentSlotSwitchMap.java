package need.clarity.modules.utility;

import net.minecraft.world.entity.EquipmentSlot;

public class EquipmentSlotSwitchMap {

    static {
        intBuffer = new int[EquipmentSlot.values().length];
        try {
            int[] var6 = intBuffer;
            int var14 = EquipmentSlot.HEAD.ordinal();
            var6[var14] = 1;
        } catch (NoSuchFieldError ignored) {
        }
        try {
            int[] var7 = intBuffer;
            int var16 = EquipmentSlot.CHEST.ordinal();
            var7[var16] = 2;
        } catch (NoSuchFieldError ignored) {
        }
        try {
            int[] var9 = intBuffer;
            int var18 = EquipmentSlot.LEGS.ordinal();
            var9[var18] = 3;
        } catch (NoSuchFieldError ignored) {
        }
        try {
            int[] var10 = intBuffer;
            int var20 = EquipmentSlot.FEET.ordinal();
            var10[var20] = 4;
        } catch (NoSuchFieldError ignored) {
        }
    }

}
