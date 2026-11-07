package need.clarity.render;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import need.clarity.api.Hooks;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderType;

public final class ChamsCollector implements InvocationHandler {
    public static volatile boolean active;
    public static volatile boolean hideArmour;
    public static volatile boolean armourThroughWalls;
    private static final Map ARMOUR_TYPES;
    private final Object delegate;

    private ChamsCollector(Object arg0) {
        this.delegate = arg0;
    }

    public static SubmitNodeCollector wrap(SubmitNodeCollector arg0) {
        if (arg0 != null) {
            if (!SubmitNodeCollector.class.isInterface()) {
            }
        }
        return arg0;
        if (isWrapped(arg0)) {
            return arg0;
        }
        try {
            new Class[1][0] = SubmitNodeCollector.class;
        } catch (Throwable ignored) {
        }
        return ((SubmitNodeCollector) Proxy.newProxyInstance(SubmitNodeCollector.class.getClassLoader(), new Class[1], new ChamsCollector(arg0)));
    }

    private static boolean isWrapped(Object arg0) {
        if (Proxy.isProxyClass(arg0.getClass())) {
            return ((Proxy.getInvocationHandler(arg0) instanceof ChamsCollector));
        }
    }

    public Object invoke(Object arg0, Method arg1, Object[] arg2) {
        Object[] var4 = null;
        int var5 = 0;
        RenderType var6 = null;
        boolean var7 = false;
        var4 = arg2;
        if (arg2 != null) {
            if (!active) {
                if (hideArmour) {
                }
            }
            var5 = 0;
            while (true) {
                if (var5 >= arg2.length) break;
                var7 = arg2[var5];
                if ((var7 instanceof RenderType)) {
                    var6 = ((RenderType) var7);
                    var7 = isArmourType(var6);
                    if (var7) {
                        if (hideArmour) {
                            return defaultValue(arg1.getReturnType());
                        }
                        if (!active) {
                        } else {
                            if (var7) {
                                if (!armourThroughWalls) {
                                } else {
                                    RenderType var8 = ThroughWalls.convert(var6);
                                    if (var8 != var6) {
                                        if (var4 == arg2) {
                                            var4 = ((Object[]) arg2.clone());
                                        }
                                        var4[var5] = var8;
                                    }
                                    var5++;
                                }
                            }
                        }
                    }
                }
            }
            try {
                var5 = arg1.invoke(this.delegate, var4);
            } catch (InvocationTargetException ignored) {
            }
            throw var6;
            if (var5 != null) {
                if (!isWrapped(var5)) {
                    var6 = arg1.getReturnType();
                    if (var6.isInterface()) {
                        if (var6 != Object.class) {
                            if ("order".equals(arg1.getName())) {
                                try {
                                    new Class[1][0] = var6;
                                } catch (Throwable ignored) {
                                }
                                return Proxy.newProxyInstance(var6.getClassLoader(), new Class[1], new ChamsCollector(var5));
                            }
                            return var5;
                        }
                    }
                }
            }
        }
    }

    private static boolean isArmourType(RenderType arg0) {
        int var2 = 0;
        String var3 = null;
        Boolean var1 = ((Boolean) ARMOUR_TYPES.get(arg0));
        if (var1 != null) {
            return var1;
        }
        try {
            var3 = renderTypeName(arg0).toLowerCase(Locale.ROOT);
            if (!var3.contains("armor")) {
                if (var3.contains("armour")) {
                }
            }
            var2 = 0;
        } catch (Throwable ignored) {
        }
        var3 = 1;
        var2 = 0;
        ARMOUR_TYPES.put(arg0, var2);
        return var2;
    }

    private static String renderTypeName(RenderType arg0) {
        Class var1 = null;
        var1 = arg0.getClass();
        while (true) {
            if (var1 == null) break;
            if (var1 == Object.class) break;
            Field[] var2 = var1.getDeclaredFields();
            int var3 = var2.length;
            int var4 = 0;
            while (true) {
                if (var4 >= var3) break;
                Field var5 = var2[var4];
                if (Modifier.isStatic(var5.getModifiers())) {
                } else {
                    if (var5.getType() == String.class) {
                        if (var5.getName().equalsIgnoreCase("name")) {
                            var5.setAccessible(true);
                            Object var6 = var5.get(arg0);
                            if (var6 != null) {
                                return ((String) var6);
                            }
                            var4++;
                        }
                    }
                }
            }
            var1 = var1.getSuperclass();
        }
        return String.valueOf(arg0);
    }

    private static Object defaultValue(Class arg0) {
        if (arg0.isPrimitive()) {
            if (arg0 == Void.TYPE) {
            }
        }
        return null;
        if (arg0 == Boolean.TYPE) {
            return false;
        }
        if (arg0 == Character.TYPE) {
            return Character.valueOf(0);
        }
        if (arg0 == Byte.TYPE) {
            return Byte.valueOf(0);
        }
        if (arg0 == Short.TYPE) {
            return Short.valueOf(0);
        }
        if (arg0 == Integer.TYPE) {
            return 0;
        }
        if (arg0 == Long.TYPE) {
            return 0L;
        }
        if (arg0 == Float.TYPE) {
            return 0.0F;
        }
        return 0.0;
    }

    static {
        Hooks.bool0("ChamsCollector.active", () -> ChamsCollector.lambda$static$0());
        Hooks.act1("ChamsCollector.setActive", (p0) -> ChamsCollector.lambda$static$1(p0));
        Hooks.bool0("ChamsCollector.hideArmour", () -> ChamsCollector.lambda$static$2());
        Hooks.act1("ChamsCollector.setHideArmour", (p0) -> ChamsCollector.lambda$static$3(p0));
        Hooks.bool0("ChamsCollector.armourThroughWalls", () -> ChamsCollector.lambda$static$4());
        Hooks.act1("ChamsCollector.setArmourThroughWalls", (p0) -> ChamsCollector.lambda$static$5(p0));
        Hooks.obj1("ChamsCollector.wrap", (p0) -> ChamsCollector.lambda$static$6(p0));
        active = false;
        hideArmour = false;
        armourThroughWalls = true;
        ARMOUR_TYPES = new ConcurrentHashMap();
    }

}
