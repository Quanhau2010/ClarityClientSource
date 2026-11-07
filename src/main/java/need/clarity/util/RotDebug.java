package need.clarity.util;

import need.clarity.config.ClarityConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public final class RotDebug {
    public static final float LIMIT = 80.0F;
    private static long last;

    private RotDebug() {
    }

    public static boolean enabled() {
        int var13 = 0;
        try {
            Object var4 = ClarityConfig.getSetting("Kill Aura", "Debug Pitch");
            if ((var4 instanceof Boolean)) {
                if (((Boolean) var4)) {
                }
            }
            var13 = 1;
            var13 = 0;
        } catch (Throwable ignored) {
        }
        return var13;
    }

    public static String callers(int arg0, int arg1) {
        int var6 = 0;
        Throwable var2 = new Throwable();
        StackTraceElement[] var8 = var2.getStackTrace();
        StringBuilder var9 = new StringBuilder();
        var6 = arg0;
        while (true) {
            int var11 = var6;
            int var31 = var8.length;
            if (var11 >= var31) break;
            int var12 = var6;
            int var33 = (arg0 + arg1);
            if (var12 >= var33) break;
            int var34 = var6;
            String var17 = var8[var34].getClassName();
            int var37 = (var17.lastIndexOf(46) + 1);
            String var19 = var17.substring(var37);
            if (var9.length() > 0) {
                StringBuilder var23 = var9.append(" <- ");
            }
            StringBuilder var26 = var9.append(var19).append(((char) 46));
            int var50 = var6;
            String var43 = var8[var50].getMethodName();
            StringBuilder var28 = var26.append(var43).append(((char) 58));
            int var51 = var6;
            int var47 = var8[var51].getLineNumber();
            StringBuilder var29 = var28.append(var47);
            var6 = (var6 + 1);
        }
        return var9.toString();
    }

    public static void report(String arg0) {
        try {
            long var1 = System.currentTimeMillis();
            long var4 = last;
            if (Long.compare((var1 - var4), 1000L) < 0) {
            }
        } catch (Throwable ignored) {
        }
        return;
        try {
            last = var1;
            String var18 = "[Clarity rot] " + (arg0);
            Minecraft var19 = Minecraft.getInstance();
            if (var19 != null) {
                if (var19.player != null) {
                    LocalPlayer var24 = var19.player;
                    MutableComponent var26 = Component.literal(var18);
                    var24.sendSystemMessage(var26);
                }
            }
        } catch (Throwable ignored) {
        }
    }

    static {
        last = 0L;
    }

}
