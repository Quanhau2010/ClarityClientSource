package need.clarity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import need.clarity.event.EventPacketReceive;

public final class PerPacketEvents {
    private static final List LISTENERS;

    private PerPacketEvents() {
    }

    public static void register(Consumer arg0) {
        boolean var3 = LISTENERS.add(arg0);
    }

    public static void unregister(Consumer arg0) {
        boolean var3 = LISTENERS.remove(arg0);
    }

    public static EventPacketReceive fire(EventPacketReceive arg0) {
        Iterator var6 = LISTENERS.iterator();
        while (true) {
            if (var6.hasNext()) {
                Consumer var12 = ((Consumer) var6.next());
                try {
                    var12.accept(arg0);
                } catch (Throwable ignored) {
                }
            }
            return arg0;
        }
    }

    static {
        ArrayList var0 = new ArrayList();
        LISTENERS = var0;
    }

}
