package need.clarity;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public final class WorldRenderEvent {
    private static final List LISTENERS;

    private WorldRenderEvent() {
    }

    public static void register(WorldRenderEvent.Listener arg0) {
        if (!LISTENERS.contains(arg0)) {
            boolean var5 = LISTENERS.add(arg0);
        }
    }

    public static void unregister(WorldRenderEvent.Listener arg0) {
        boolean var3 = LISTENERS.remove(arg0);
    }

    public static void fire(PoseStack arg0, float arg1) {
        Iterator var8 = LISTENERS.iterator();
        while (true) {
            if (!var8.hasNext()) break;
            WorldRenderEvent.Listener var13 = ((WorldRenderEvent.Listener) var8.next());
            try {
                var13.onWorldRender(arg0, arg1);
            } catch (Throwable ignored) {
            }
        }
    }

    static {
        CopyOnWriteArrayList var0 = new CopyOnWriteArrayList();
        LISTENERS = var0;
    }

    public interface Listener {
        void onWorldRender(PoseStack arg0, float arg1);

    }

}
