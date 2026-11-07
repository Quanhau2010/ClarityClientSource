package need.clarity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import need.clarity.api.MovementEvent;

public final class PerMovementEvents {
    private static final List LISTENERS;
    public static MovementEvent lastEvent;

    private PerMovementEvents() {
    }

    public static void register(Consumer arg0) {
        boolean var3 = LISTENERS.add(arg0);
    }

    public static void fire(MovementEvent arg0) {
        lastEvent = arg0;
        Iterator var7 = LISTENERS.iterator();
        while (true) {
            if (var7.hasNext()) {
                Consumer var12 = ((Consumer) var7.next());
                try {
                    var12.accept(arg0);
                } catch (Throwable ignored) {
                }
            }
            return;
        }
    }

    static {
        ArrayList var0 = new ArrayList();
        LISTENERS = var0;
        lastEvent = null;
    }

}
