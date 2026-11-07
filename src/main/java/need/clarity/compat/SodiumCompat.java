package need.clarity.compat;

public final class SodiumCompat {
    private static final boolean SODIUM_PRESENT;

    private SodiumCompat() {
    }

    private static boolean detect() {
        int var6 = 0;
        String[] var9 = new String[2];
        var9[0] = "net.caffeinemc.mods.sodium.client.SodiumClientMod";
        var9[1] = "me.jellysquid.mods.sodium.client.SodiumClientMod";
        int var12 = var9.length;
        var6 = 0;
        while (true) {
            if (var6 < var12) {
                int var23 = var6;
                String var17 = var9[var23];
                try {
                    Class var19 = Class.forName(var17);
                } catch (ClassNotFoundException ignored) {
                }
                return true;
            }
            return false;
        }
    }

    public static boolean isSodiumPresent() {
        return SODIUM_PRESENT;
    }

    static {
        SODIUM_PRESENT = detect();
    }

}
