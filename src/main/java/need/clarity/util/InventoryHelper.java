package need.clarity.util;

import java.lang.reflect.Field;
import need.clarity.mixin.InventoryAccessor;
import net.minecraft.world.entity.player.Inventory;

public final class InventoryHelper {
    private static Field selectedField;
    private static boolean reflectionFailed;

    private InventoryHelper() {
    }

    public static int getSelected(Inventory arg0) {
        try {
            int var4 = ((InventoryAccessor) arg0).getSelected();
        } catch (ClassCastException ignored) {
        }
        return var4;
    }

    public static void setSelected(Inventory arg0, int arg1) {
        try {
            ((InventoryAccessor) arg0).setSelected(arg1);
        } catch (ClassCastException ignored) {
        }
        setSelectedReflection(arg0, arg1);
    }

    private static Field resolveField(Inventory arg0) {
        int var66 = 0;
        int var7 = 0;
        if (selectedField != null) {
            return selectedField;
        }
        if (reflectionFailed) {
            return null;
        }
        String[] var12 = new String[3];
        var12[0] = "selected";
        var12[1] = "f_35978_";
        var12[2] = "field_7545";
        int var15 = var12.length;
        var7 = 0;
        while (true) {
            if (var7 >= var15) break;
            int var57 = var7;
            String var41 = var12[var57];
            try {
                Field var43 = Inventory.class.getDeclaredField(var41);
                var43.setAccessible(true);
                selectedField = var43;
            } catch (NoSuchFieldException ignored) {
            }
            return var43;
        }
        Field[] var19 = Inventory.class.getDeclaredFields();
        int var21 = var19.length;
        var66 = 0;
        while (true) {
            if (var66 >= var21) break;
            int var52 = var66;
            Field var27 = var19[var52];
            Class var29 = var27.getType();
            Class var53 = Integer.TYPE;
            if (var29 == var53) {
                var27.setAccessible(true);
                try {
                    int var34 = ((Integer) var27.get(arg0));
                    if (var34 >= 0) {
                        if (var34 <= 8) {
                            selectedField = var27;
                        }
                    }
                } catch (IllegalAccessException ignored) {
                }
                return var27;
            }
            var66 = (var66 + 1);
        }
        reflectionFailed = true;
        return null;
    }

    private static int getSelectedReflection(Inventory arg0) {
        Field var5 = resolveField(arg0);
        if (var5 == null) {
            return 0;
        }
        try {
            int var10 = ((Integer) var5.get(arg0));
        } catch (IllegalAccessException ignored) {
        }
        return var10;
    }

    private static void setSelectedReflection(Inventory arg0, int arg1) {
        Field var7 = resolveField(arg0);
        if (var7 == null) {
            return;
        }
        try {
            Integer var11 = arg1;
            var7.set(arg0, var11);
        } catch (IllegalAccessException ignored) {
        }
    }

    static {
        selectedField = null;
        reflectionFailed = false;
    }

}
