package need.clarity.api;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class Bus {
    private static final Map REG;

    private Bus() {
    }

    public static void register(Class arg0, Object arg1) {
        if (arg0 != null) {
            if (arg1 != null) {
                Object var7 = REG.put(arg0, arg1);
            }
            return;
        }
    }

    public static void unregister(Class arg0) {
        if (arg0 != null) {
            Object var4 = REG.remove(arg0);
        }
    }

    public static Object get(Class arg0) {
        Object var4 = null;
        if (arg0 == null) {
            var4 = null;
        } else {
            var4 = REG.get(arg0);
        }
        return var4;
    }

    public static boolean has(Class arg0) {
        int var5 = 0;
        if (arg0 != null) {
            if (REG.containsKey(arg0)) {
                var5 = 1;
            } else {
                var5 = 0;
            }
            return var5;
        }
    }

    public static IRotation rotation() {
        return ((IRotation) get(IRotation.class));
    }

    public static IMovement movement() {
        return ((IMovement) get(IMovement.class));
    }

    public static IChams chams() {
        return ((IChams) get(IChams.class));
    }

    public static IFreecam freecam() {
        return ((IFreecam) get(IFreecam.class));
    }

    public static IPlayerModel playerModel() {
        return ((IPlayerModel) get(IPlayerModel.class));
    }

    public static IOvertick overtick() {
        return ((IOvertick) get(IOvertick.class));
    }

    static {
        ConcurrentHashMap var0 = new ConcurrentHashMap();
        REG = var0;
    }

}
