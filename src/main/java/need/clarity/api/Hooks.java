package need.clarity.api;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class Hooks {
    private static final Map BOOL0;
    private static final Map BOOL1;
    private static final Map DBL0;
    private static final Map DBL1;
    private static final Map OBJ0;
    private static final Map OBJ1;
    private static final Map OBJ2;
    private static final Map DBL2;
    private static final Map ACT0;
    private static final Map ACT1;

    private Hooks() {
    }

    public static void bool0(String arg0, Hooks.Bool0 arg1) {
        Object var5 = BOOL0.put(arg0, arg1);
    }

    public static void bool1(String arg0, Hooks.Bool1 arg1) {
        Object var5 = BOOL1.put(arg0, arg1);
    }

    public static void dbl0(String arg0, Hooks.Dbl0 arg1) {
        Object var5 = DBL0.put(arg0, arg1);
    }

    public static void dbl1(String arg0, Hooks.Dbl1 arg1) {
        Object var5 = DBL1.put(arg0, arg1);
    }

    public static void obj0(String arg0, Hooks.Obj0 arg1) {
        Object var5 = OBJ0.put(arg0, arg1);
    }

    public static void obj1(String arg0, Hooks.Obj1 arg1) {
        Object var5 = OBJ1.put(arg0, arg1);
    }

    public static void obj2(String arg0, Hooks.Obj2 arg1) {
        Object var5 = OBJ2.put(arg0, arg1);
    }

    public static void dbl2(String arg0, Hooks.Dbl2 arg1) {
        Object var5 = DBL2.put(arg0, arg1);
    }

    public static void act0(String arg0, Hooks.Act0 arg1) {
        Object var5 = ACT0.put(arg0, arg1);
    }

    public static void act1(String arg0, Hooks.Act1 arg1) {
        Object var5 = ACT1.put(arg0, arg1);
    }

    public static boolean bool(String arg0, boolean arg1) {
        boolean var9 = false;
        Hooks.Bool0 var6 = ((Hooks.Bool0) BOOL0.get(arg0));
        if (var6 != null) {
            var9 = var6.get();
        } else {
            var9 = arg1;
        }
        return var9;
    }

    public static boolean bool1(String arg0, Object arg1, boolean arg2) {
        boolean var10 = false;
        Hooks.Bool1 var7 = ((Hooks.Bool1) BOOL1.get(arg0));
        if (var7 != null) {
            var10 = var7.get(arg1);
        } else {
            var10 = arg2;
        }
        return var10;
    }

    public static double dbl(String arg0, double arg1) {
        double var10 = 0.0;
        Hooks.Dbl0 var7 = ((Hooks.Dbl0) DBL0.get(arg0));
        if (var7 != null) {
            var10 = var7.get();
        } else {
            var10 = arg1;
        }
        return var10;
    }

    public static double dbl1(String arg0, Object arg1, double arg2) {
        double var11 = 0.0;
        Hooks.Dbl1 var8 = ((Hooks.Dbl1) DBL1.get(arg0));
        if (var8 != null) {
            var11 = var8.get(arg1);
        } else {
            var11 = arg2;
        }
        return var11;
    }

    public static Object obj(String arg0) {
        Object var8 = null;
        Hooks.Obj0 var5 = ((Hooks.Obj0) OBJ0.get(arg0));
        if (var5 != null) {
            var8 = var5.get();
        } else {
            var8 = null;
        }
        return var8;
    }

    public static Object obj1(String arg0, Object arg1) {
        Object var8 = null;
        Hooks.Obj1 var6 = ((Hooks.Obj1) OBJ1.get(arg0));
        if (var6 != null) {
            var8 = var6.get(arg1);
        } else {
            var8 = null;
        }
        return var8;
    }

    public static Object obj2(String arg0, Object arg1, Object arg2) {
        Object var11 = null;
        Hooks.Obj2 var8 = ((Hooks.Obj2) OBJ2.get(arg0));
        if (var8 != null) {
            var11 = var8.get(arg1, arg2);
        } else {
            var11 = null;
        }
        return var11;
    }

    public static double dbl2(String arg0, Object arg1, Object arg2, double arg3) {
        double var13 = 0.0;
        Hooks.Dbl2 var10 = ((Hooks.Dbl2) DBL2.get(arg0));
        if (var10 != null) {
            var13 = var10.get(arg1, arg2);
        } else {
            var13 = arg3;
        }
        return var13;
    }

    public static void run(String arg0) {
        Hooks.Act0 var5 = ((Hooks.Act0) ACT0.get(arg0));
        if (var5 != null) {
            var5.run();
        }
    }

    public static void run1(String arg0, Object arg1) {
        Hooks.Act1 var6 = ((Hooks.Act1) ACT1.get(arg0));
        if (var6 != null) {
            var6.run(arg1);
        }
    }

    static {
        ConcurrentHashMap var0 = new ConcurrentHashMap();
        BOOL0 = var0;
        ConcurrentHashMap var1 = new ConcurrentHashMap();
        BOOL1 = var1;
        ConcurrentHashMap var2 = new ConcurrentHashMap();
        DBL0 = var2;
        ConcurrentHashMap var3 = new ConcurrentHashMap();
        DBL1 = var3;
        ConcurrentHashMap var4 = new ConcurrentHashMap();
        OBJ0 = var4;
        ConcurrentHashMap var5 = new ConcurrentHashMap();
        OBJ1 = var5;
        ConcurrentHashMap var6 = new ConcurrentHashMap();
        OBJ2 = var6;
        ConcurrentHashMap var7 = new ConcurrentHashMap();
        DBL2 = var7;
        ConcurrentHashMap var8 = new ConcurrentHashMap();
        ACT0 = var8;
        ConcurrentHashMap var9 = new ConcurrentHashMap();
        ACT1 = var9;
    }

    public interface Act0 {
        void run();

    }

    public interface Act1 {
        void run(Object arg0);

    }

    public interface Bool0 {
        boolean get();

    }

    public interface Bool1 {
        boolean get(Object arg0);

    }

    public interface Dbl0 {
        double get();

    }

    public interface Dbl1 {
        double get(Object arg0);

    }

    public interface Dbl2 {
        double get(Object arg0, Object arg1);

    }

    public interface Obj0 {
        Object get();

    }

    public interface Obj1 {
        Object get(Object arg0);

    }

    public interface Obj2 {
        Object get(Object arg0, Object arg1);

    }

}
