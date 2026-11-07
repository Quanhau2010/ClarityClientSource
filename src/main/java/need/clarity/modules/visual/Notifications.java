package need.clarity.modules.visual;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import need.clarity.api.Hooks;
import need.clarity.modules.ModuleBase;
import need.clarity.util.ThemeColors;
import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundEvent;

public class Notifications extends ModuleBase {
    public static Notifications INSTANCE;
    private static final List entriesList;

    public Notifications() {
        super("Notifications", "Visual");
        INSTANCE = this;
        Hooks.Act1 var6 = (p0) -> Notifications.lambda$a(p0);
        Hooks.act1("Notifications.moduleToggled", var6);
    }

    public long getDuration() {
        Float var13 = 3.0F;
        float var11 = ((Float) this.getSetting("Duration", var13));
        return ((long) (Math.max(0.5F, var11) * 1000.0F));
    }

    public String getPosition() {
        return ((String) this.getSetting("Position", "Bottom Right"));
    }

    public int getXOffset() {
        Integer var7 = 12;
        return ((Integer) this.getSetting("X Offset", var7));
    }

    public int getYOffset() {
        Integer var7 = 12;
        return ((Integer) this.getSetting("Y Offset", var7));
    }

    public float getScale() {
        Float var11 = 1.0F;
        float var10 = ((Float) this.getSetting("Scale", var11));
        float var7 = Math.min(2.0F, var10);
        return Math.max(0.5F, var7);
    }

    public float getOpacity() {
        Float var11 = 1.0F;
        float var10 = ((Float) this.getSetting("Opacity", var11));
        float var7 = Math.min(1.0F, var10);
        return Math.max(0.1F, var7);
    }

    public int getMaxVisible() {
        Integer var11 = 6;
        int var10 = ((Integer) this.getSetting("Max Visible", var11));
        int var7 = Math.min(10, var10);
        return Math.max(1, var7);
    }

    public String getAnimation() {
        return ((String) this.getSetting("Animation", "Slide"));
    }

    public boolean isShowEnabled() {
        Boolean var7 = true;
        return ((Boolean) this.getSetting("Show Enabled", var7));
    }

    public boolean isShowDisabled() {
        Boolean var7 = true;
        return ((Boolean) this.getSetting("Show Disabled", var7));
    }

    public boolean isCompact() {
        Boolean var7 = false;
        return ((Boolean) this.getSetting("Compact", var7));
    }

    public String getEnabledSound() {
        return ((String) this.getSetting("Enabled Sound", "None"));
    }

    public String getDisabledSound() {
        return ((String) this.getSetting("Disabled Sound", "None"));
    }

    public float getVolume() {
        Float var11 = 1.0F;
        float var10 = ((Float) this.getSetting("Volume", var11));
        float var7 = Math.min(2.0F, var10);
        return Math.max(0.0F, var7);
    }

    public float getPitch() {
        Float var11 = 1.0F;
        float var10 = ((Float) this.getSetting("Pitch", var11));
        float var7 = Math.min(2.0F, var10);
        return Math.max(0.5F, var7);
    }

    public String getStyle() {
        return ((String) this.getSetting("Style", "Modern"));
    }

    public int getBlurStrength() {
        Integer var11 = 2;
        int var10 = ((Integer) this.getSetting("Blur Strength", var11));
        int var7 = Math.min(4, var10);
        return Math.max(1, var7);
    }

    public float getBlurTint() {
        Float var11 = 0.35F;
        float var10 = ((Float) this.getSetting("Blur Tint", var11));
        float var7 = Math.min(0.9F, var10);
        return Math.max(0.0F, var7);
    }

    public boolean isStatusBar() {
        Boolean var7 = true;
        return ((Boolean) this.getSetting("Status Bar", var7));
    }

    public boolean isProgressBar() {
        Boolean var7 = false;
        return ((Boolean) this.getSetting("Progress Bar", var7));
    }

    public boolean isThemeColors() {
        Boolean var7 = false;
        return ((Boolean) this.getSetting("Theme Colors", var7));
    }

    public int getEnabledColor() {
        return (ThemeColors.resolveRgb(this.getSetting("Enabled Color", ""), -12845218, false) & 16777215);
    }

    public int getDisabledColor() {
        return (ThemeColors.resolveRgb(this.getSetting("Disabled Color", ""), -50373, true) & 16777215);
    }

    public void onDisable() {
        List var1 = entriesList;
        try {
            entriesList.clear();
        } catch (Throwable ignored) {
        }
        List var6 = var1;
        throw var6;
    }

    public static void applyStep(String arg0, boolean arg1) {
        String var21 = null;
        Notifications var2 = INSTANCE;
        if (var2 != null) {
            if (!var2.isEnabled()) {
            }
        }
        return;
        Minecraft var10 = Minecraft.getInstance();
        if (var10 != null) {
            if (var10.player == null) {
            }
        }
        return;
        if (arg1) {
            if (!var2.isShowEnabled()) {
                return;
            }
        }
        if (!arg1) {
            if (!var2.isShowDisabled()) {
                return;
            }
        }
        if (arg1) {
            var21 = "Enabled";
        } else {
            var21 = "Disabled";
        }
        applyStep(arg0, var21, arg1);
    }

    public static void applyStep(String arg0, String arg1, boolean arg2) {
        Notifications var3 = INSTANCE;
        if (var3 != null) {
            if (!var3.isEnabled()) {
            }
        }
        return;
        long var37 = var3.getDuration();
        NotificationToastEntry var15 = new NotificationToastEntry(arg0, arg1, arg2, var37);
        var3.applyStep(arg2);
        List var17 = entriesList;
        try {
            List var18 = entriesList;
            Predicate var32 = (p0) -> Notifications.lambda$a(arg0, p0);
            boolean var19 = var18.removeIf(var32);
            boolean var21 = entriesList.add(var15);
            while (true) {
                int var23 = entriesList.size();
                int var35 = var3.getMaxVisible();
                if (var23 <= var35) break;
                Object var29 = entriesList.remove(0);
            }
        } catch (Throwable ignored) {
        }
        List var25 = var17;
        throw var25;
    }

    private void applyStep(boolean arg0) {
        String var13 = null;
        if (arg0) {
            var13 = this.getEnabledSound();
        } else {
            var13 = this.getDisabledSound();
        }
        String var7 = var13;
        if (var7 != null) {
            if ("None".equals(var7)) {
            }
        }
        return;
        SoundEvent var18 = HitSounds.evaluate(var7);
        if (var18 == null) {
            return;
        }
        float var21 = this.getVolume();
        float var23 = this.getPitch();
        if (Float.compare(var21, 0.0F) <= 0) {
            return;
        }
        Minecraft var26 = Minecraft.getInstance();
        if (var26 == null) {
            return;
        }
        Runnable var32 = () -> Notifications.lambda$a(var26, var18, var23, var21);
        var26.execute(var32);
    }

    public static List evaluate() {
        long var0 = System.currentTimeMillis();
        List var7 = entriesList;
        try {
            List var8 = entriesList;
            Predicate var16 = (p0) -> Notifications.lambda$a(var0, p0);
            boolean var9 = var8.removeIf(var16);
            List var2 = entriesList;
            ArrayList var10 = new ArrayList(var2);
        } catch (Throwable ignored) {
        }
        return var10;
        List var11 = var7;
        throw var11;
    }

    public static List evaluate1() {
        int var35 = 0;
        int var40 = 0;
        int var17 = 0;
        String var50 = null;
        Notifications var0 = INSTANCE;
        if (var0 != null) {
            var40 = var0.getMaxVisible();
        } else {
            var40 = 3;
        }
        int var20 = Math.min(3, var40);
        long var23 = (System.currentTimeMillis() - 1000L);
        String[] var26 = new String[3];
        var26[0] = "Kill Aura";
        var26[1] = "Reach";
        var26[2] = "Sprint";
        ArrayList var27 = new ArrayList(var20);
        var17 = 0;
        while (true) {
            if (var17 >= var20) break;
            if ((var17 % 2) == 0) {
                var35 = 1;
            } else {
                var35 = 0;
            }
            int var18 = var35;
            int var5 = var17;
            String var48 = var26[var5];
            if (var18 != 0) {
                var50 = "Enabled";
            } else {
                var50 = "Disabled";
            }
            NotificationToastEntry var39 = new NotificationToastEntry(var48, var50, var18, var23, 3600000L);
            boolean var34 = var27.add(var39);
            var17 = (var17 + 1);
        }
        return var27;
    }

    public static boolean isEligible() {
        int var10 = 0;
        Notifications var0 = INSTANCE;
        if (var0 != null) {
            if (var0.isEnabled()) {
                String var12 = var0.getStyle();
                if (!"Blur".equals(var12)) {
                }
            }
            return false;
            if (!evaluate().isEmpty()) {
                var10 = 1;
            } else {
                var10 = 0;
            }
            return var10;
        }
    }

    public static boolean isEligible1() {
        int var7 = 0;
        Notifications var0 = INSTANCE;
        if (var0 != null) {
            if (!var0.isEnabled()) {
            }
        }
        return false;
        if (!evaluate().isEmpty()) {
            var7 = 1;
        } else {
            var7 = 0;
        }
        return var7;
    }

    static {
        ArrayList var0 = new ArrayList();
        entriesList = var0;
    }

}
