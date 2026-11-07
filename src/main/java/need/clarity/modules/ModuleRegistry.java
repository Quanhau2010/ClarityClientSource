package need.clarity.modules;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import need.clarity.config.ClarityConfig;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.event.Event;

public final class ModuleRegistry {
    private static final Map modules;
    private static volatile boolean tickRegistered;
    private static volatile List tickOrder;

    private ModuleRegistry() {
    }

    public static void register(ModuleBase arg0) {
        Map var1 = modules;
        String var6 = arg0.getName();
        Object var4 = var1.put(var6, arg0);
        tickOrder = null;
    }

    public static List tickOrder() {
        List var2 = null;
        var2 = tickOrder;
        if (var2 == null) {
            Collection var10 = modules.values();
            ArrayList var4 = new ArrayList(var10);
            var2 = var4;
            List var5 = var2;
            Comparator var9 = Comparator.comparingInt((p0) -> ModuleRegistry.lambda$tickOrder$0(p0));
            var5.sort(var9);
            tickOrder = var2;
        }
        return var2;
    }

    public static ModuleBase get(String arg0) {
        return ((ModuleBase) modules.get(arg0));
    }

    public static List all() {
        return List.copyOf(modules.values());
    }

    public static void registerTickEvent() {
        if (tickRegistered) {
            return;
        }
        tickRegistered = true;
        Event var3 = ClientTickEvents.START_CLIENT_TICK;
        ClientTickEvents.StartTick var1 = (p0) -> ModuleRegistry.lambda$registerTickEvent$0(p0);
        var3.register(var1);
    }

    public static void syncFromConfig() {
        Iterator var5 = all().iterator();
        while (true) {
            if (!var5.hasNext()) break;
            ModuleBase var10 = ((ModuleBase) var5.next());
            boolean var13 = ClarityConfig.isEnabled(var10.getName());
            var10.setEnabled(var13);
        }
    }

    static {
        LinkedHashMap var0 = new LinkedHashMap();
        modules = var0;
        tickRegistered = false;
    }

}
