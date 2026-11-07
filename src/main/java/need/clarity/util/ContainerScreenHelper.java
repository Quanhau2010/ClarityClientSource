package need.clarity.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;

public final class ContainerScreenHelper {
    private static final Field HOVERED_SLOT;
    private static final Method HANDLE_INV_CLICK;
    private static final Object CLICK_TYPE_SWAP;
    private static final Object CLICK_TYPE_QUICK_MOVE;

    private ContainerScreenHelper() {
    }

    public static Slot getHoveredSlot(AbstractContainerScreen arg0) {
        if (HOVERED_SLOT != null) {
            if (arg0 == null) {
            }
        }
        return null;
        try {
            Slot var7 = ((Slot) HOVERED_SLOT.get(arg0));
        } catch (Throwable ignored) {
        }
        return var7;
    }

    public static void swapToOffhand(MultiPlayerGameMode arg0, int arg1, int arg2, LocalPlayer arg3) {
        Object var8 = CLICK_TYPE_SWAP;
        invokeClick(arg0, arg1, arg2, 40, var8, arg3);
    }

    public static void swapToHotbar(MultiPlayerGameMode arg0, int arg1, int arg2, int arg3, LocalPlayer arg4) {
        Object var9 = CLICK_TYPE_SWAP;
        invokeClick(arg0, arg1, arg2, arg3, var9, arg4);
    }

    public static void shiftClick(MultiPlayerGameMode arg0, int arg1, int arg2, LocalPlayer arg3) {
        Object var8 = CLICK_TYPE_QUICK_MOVE;
        invokeClick(arg0, arg1, arg2, 0, var8, arg3);
    }

    private static void invokeClick(MultiPlayerGameMode arg0, int arg1, int arg2, int arg3, Object arg4, LocalPlayer arg5) {
        if (HANDLE_INV_CLICK != null) {
            if (arg4 != null) {
                if (arg0 == null) {
                }
            }
            return;
            try {
                Method var14 = HANDLE_INV_CLICK;
                Object[] var17 = new Object[5];
                Integer var22 = arg1;
                var17[0] = var22;
                Integer var24 = arg2;
                var17[1] = var24;
                Integer var26 = arg3;
                var17[2] = var26;
                var17[3] = arg4;
                var17[4] = arg5;
                Object var15 = var14.invoke(arg0, var17);
            } catch (Throwable ignored) {
            }
            return;
        }
    }

    static {
        Field var2 = null;
        Class var3 = null;
        Object var5 = null;
        Object var6 = null;
        int var9 = 0;
        int var102 = 0;
        int var106 = 0;
        var2 = null;
        var3 = AbstractContainerScreen.class;
        while (true) {
            if (var3 == null) break;
            try {
                Field var16 = var3.getDeclaredField("hoveredSlot");
                var16.setAccessible(true);
                var2 = var16;
            } catch (NoSuchFieldException ignored) {
            }
            Field[] var66 = var3.getDeclaredFields();
            int var68 = var66.length;
            var106 = 0;
            while (true) {
                if (var106 >= var68) break;
                int var99 = var106;
                Field var74 = var66[var99];
                if (var74.getType() == Slot.class) {
                    var74.setAccessible(true);
                    var2 = var74;
                } else {
                    var106 = (var106 + 1);
                }
            }
            var3 = var3.getSuperclass();
        }
        HOVERED_SLOT = var2;
        var102 = null;
        var5 = null;
        var6 = null;
        try {
            Method[] var24 = MultiPlayerGameMode.class.getDeclaredMethods();
            int var26 = var24.length;
            var9 = 0;
            while (true) {
                if (var9 >= var26) break;
                int var81 = var9;
                Method var30 = var24[var81];
                Class[] var32 = var30.getParameterTypes();
                if (var32.length == 5) {
                    Class var36 = var32[0];
                    Class var84 = Integer.TYPE;
                    if (var36 == var84) {
                        Class var38 = var32[1];
                        Class var86 = Integer.TYPE;
                        if (var38 == var86) {
                            Class var40 = var32[2];
                            Class var88 = Integer.TYPE;
                            if (var40 == var88) {
                                if (var32[3].isEnum()) {
                                    if (var32[4] == Player.class) {
                                        Class var47 = var30.getReturnType();
                                        Class var92 = Void.TYPE;
                                        if (var47 == var92) {
                                            var30.setAccessible(true);
                                            var102 = var30;
                                            Object[] var52 = var32[3].getEnumConstants();
                                            if (var52 == null) break;
                                            if (var52.length <= 2) break;
                                            var6 = var52[1];
                                            var5 = var52[2];
                                        } else {
                                            var9 = (var9 + 1);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            HANDLE_INV_CLICK = var102;
            CLICK_TYPE_SWAP = var5;
            CLICK_TYPE_QUICK_MOVE = var6;
            return;
        } catch (Throwable ignored) {
        }
    }

}
