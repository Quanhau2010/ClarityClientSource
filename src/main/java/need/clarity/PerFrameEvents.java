package need.clarity;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import need.clarity.sec.Diag;

public final class PerFrameEvents {
    private static final List CALLBACKS;
    private static long lastDiag;

    private PerFrameEvents() {
    }

    public static void register(Runnable arg0) {
        if (!CALLBACKS.contains(arg0)) {
            boolean var5 = CALLBACKS.add(arg0);
        }
    }

    public static void unregister(Runnable arg0) {
        boolean var3 = CALLBACKS.remove(arg0);
    }

    public static void fire() {
        int var3 = 0;
        var3 = 0;
        Iterator var9 = CALLBACKS.iterator();
        while (true) {
            if (!var9.hasNext()) break;
            Runnable var14 = ((Runnable) var9.next());
            var3 = (var3 + 1);
            try {
                var14.run();
            } catch (Throwable ignored) {
            }
            try {
                Diag.log("perframe callback error: " + (String.valueOf(var16)));
            } catch (Throwable ignored) {
            }
        }
        try {
            if (Diag.enabled()) {
                long var22 = System.currentTimeMillis();
                long var1 = lastDiag;
                if (Long.compare((var22 - var1), 5000L) >= 0) {
                    lastDiag = var22;
                    Diag.log("perframe callbacks=" + (var3));
                }
            }
        } catch (Throwable ignored) {
        }
    }

    static {
        CopyOnWriteArrayList var0 = new CopyOnWriteArrayList();
        CALLBACKS = var0;
        lastDiag = 0L;
    }

}
