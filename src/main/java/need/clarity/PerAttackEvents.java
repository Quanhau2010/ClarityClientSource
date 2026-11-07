package need.clarity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

public final class PerAttackEvents {
    private static final List LISTENERS;

    private PerAttackEvents() {
    }

    public static void register(Consumer arg0) {
        boolean var3 = LISTENERS.add(arg0);
    }

    public static void fire(EventStartAttack arg0) {
        Iterator var6 = LISTENERS.iterator();
        while (true) {
            if (var6.hasNext()) {
                Consumer var11 = ((Consumer) var6.next());
                try {
                    var11.accept(arg0);
                } catch (Throwable ignored) {
                }
            }
            return;
        }
    }

    static {
        ArrayList var0 = new ArrayList();
        LISTENERS = var0;
    }

}
