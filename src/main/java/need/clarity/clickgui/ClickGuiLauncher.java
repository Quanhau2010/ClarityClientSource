package need.clarity.clickgui;

import net.minecraft.client.Minecraft;

public final class ClickGuiLauncher {
    private static long lastToggleTime = 0L;

    private ClickGuiLauncher() {
    }

    public static void onRightShift() {
        long now = System.currentTimeMillis();
        if (now - lastToggleTime < 250L) {
            return;
        }
        lastToggleTime = now;

        try {
            System.out.println("[Clarity] Toggling ClickGUI...");
            Minecraft mc = Minecraft.getInstance();
            if (mc == null) return;
            if (mc.screen == null) {
                mc.setScreen(new ClickGuiScreen());
            } else if (mc.screen instanceof ClickGuiScreen) {
                mc.setScreen(null);
            }
        } catch (Throwable t) {
            System.err.println("[Clarity] Error toggling ClickGUI:");
            t.printStackTrace();
        }
    }
}
