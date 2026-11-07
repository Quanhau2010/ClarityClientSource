package need.clarity.render;

import com.mojang.blaze3d.pipeline.DepthStencilState;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import need.clarity.api.Hooks;
import net.minecraft.client.renderer.rendertype.RenderSetup;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.resources.Identifier;
import sun.misc.Unsafe;

public final class ThroughWalls {
    private static final Map CACHE;
    private static final Map CONVERTED;
    private static final AtomicInteger COUNTER;
    private static final Set FAILED_LOGGED;
    private static final Unsafe UNSAFE;

    private ThroughWalls() {
    }

    private static Unsafe loadUnsafe() {
        Field var0 = null;
        try {
            var0 = Unsafe.class.getDeclaredField("theUnsafe");
            var0.setAccessible(true);
        } catch (Throwable ignored) {
        }
        return ((Unsafe) var0.get(null));
    }

    public static RenderType convert(RenderType arg0) {
        RenderType var2 = null;
        if (arg0 == null) {
            return null;
        }
        if (!ChamsRenderer.isOwnType(arg0)) {
            if (CONVERTED.containsKey(arg0)) {
            }
        }
        return arg0;
        RenderType var1 = ((RenderType) CACHE.get(arg0));
        if (var1 != null) {
            return var1;
        }
        try {
            var2 = build(arg0);
        } catch (Throwable ignored) {
        }
        if (FAILED_LOGGED.add(arg0)) {
        }
        var2 = arg0;
        CACHE.put(arg0, var2);
        if (var2 != arg0) {
            CONVERTED.put(var2, Boolean.TRUE);
        }
        return var2;
    }

    private static RenderType build(RenderType arg0) {
        Class var7 = null;
        if (UNSAFE == null) {
            throw new IllegalStateException("Unsafe unavailable");
        }
        Field var1 = findFieldOfType(arg0.getClass(), RenderSetup.class);
        if (var1 == null) {
            throw new IllegalStateException("RenderType has no RenderSetup field");
        }
        Object var2 = var1.get(arg0);
        Field var3 = findFieldOfType(var2.getClass(), RenderPipeline.class);
        if (var3 == null) {
            throw new IllegalStateException("RenderSetup has no RenderPipeline field");
        }
        Object var4 = var3.get(var2);
        int var5 = COUNTER.incrementAndGet();
        Object var6 = shallowCopy(var4);
        setDepthState(var6);
        var7 = var6.getClass();
        while (true) {
            if (var7 == null) break;
            if (var7 == Object.class) break;
            Field[] var8 = var7.getDeclaredFields();
            int var9 = var8.length;
            int var10 = 0;
            while (true) {
                if (var10 >= var9) break;
                Field var11 = var8[var10];
                if (!Modifier.isStatic(var11.getModifiers())) {
                    if (var11.getType() == Identifier.class) {
                        if (var11.getName().equalsIgnoreCase("location")) {
                            var11.setAccessible(true);
                            var11.set(var6, Identifier.parse("clarity:chams_tw_" + (var5)));
                        }
                        var10++;
                    }
                }
            }
            var7 = var7.getSuperclass();
        }
        var7 = shallowCopy(var2);
        var3.set(var7, var6);
        return RenderType.create("clarity:chams_tw_" + (var5), ((RenderSetup) var7));
    }

    private static void setDepthState(Object arg0) {
        int var2 = 0;
        Class var3 = null;
        DepthStencilState var1 = ChamsRenderer.throughWallsDepthState();
        var2 = null;
        var3 = arg0.getClass();
        while (true) {
            if (var3 == null) break;
            if (var3 == Object.class) break;
            Field[] var4 = var3.getDeclaredFields();
            int var5 = var4.length;
            int var6 = 0;
            while (true) {
                if (var6 >= var5) break;
                Field var7 = var4[var6];
                if (Modifier.isStatic(var7.getModifiers())) {
                } else {
                    int var8 = (var7.getType() == DepthStencilState.class);
                    if (var7.getType() == Optional.class) {
                        int var9 = (var7.getName().toLowerCase().contains("depth"));
                        if (var8 == 0) {
                            if (var9 != 0) {
                            }
                        }
                        var2 = var7;
                    }
                }
                var6++;
            }
            if (var2 != null) {
            } else {
                var3 = var3.getSuperclass();
            }
            if (var2 != null) break;
            throw new IllegalStateException("RenderPipeline has no depth state field");
            var2.setAccessible(true);
            if (var2.getType() != Optional.class) break;
            if (var1 == null) break;
            arg0.set(Optional.of(var1), Optional.empty());
            var2.set(arg0, var1);
            return;
        }
    }

    private static Field findFieldOfType(Class arg0, Class arg1) {
        Class var2 = null;
        var2 = arg0;
        while (true) {
            if (var2 == null) break;
            if (var2 == Object.class) break;
            Field[] var3 = var2.getDeclaredFields();
            int var4 = var3.length;
            int var5 = 0;
            while (true) {
                if (var5 >= var4) break;
                Field var6 = var3[var5];
                if (Modifier.isStatic(var6.getModifiers())) {
                } else {
                    if (arg1.isAssignableFrom(var6.getType())) {
                        var6.setAccessible(true);
                        return var6;
                    }
                    var5++;
                }
            }
            var2 = var2.getSuperclass();
        }
        return null;
    }

    private static Object shallowCopy(Object arg0) {
        Class var2 = null;
        Object var1 = UNSAFE.allocateInstance(arg0.getClass());
        var2 = arg0.getClass();
        while (true) {
            if (var2 == null) break;
            if (var2 == Object.class) break;
            Field[] var3 = var2.getDeclaredFields();
            int var4 = var3.length;
            int var5 = 0;
            while (true) {
                if (var5 >= var4) break;
                Field var6 = var3[var5];
                if (Modifier.isStatic(var6.getModifiers())) {
                } else {
                    var6.setAccessible(true);
                    var6.set(var1, var6.get(arg0));
                }
                var5++;
            }
            var2 = var2.getSuperclass();
        }
        return var1;
    }

    static {
        Hooks.obj1("ThroughWalls.convert", (p0) -> ThroughWalls.lambda$static$0(p0));
        CACHE = new ConcurrentHashMap();
        CONVERTED = new ConcurrentHashMap();
        COUNTER = new AtomicInteger();
        FAILED_LOGGED = ConcurrentHashMap.newKeySet();
        UNSAFE = loadUnsafe();
    }

}
