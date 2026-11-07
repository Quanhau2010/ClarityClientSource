package need.clarity.modules.visual;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import need.clarity.config.ClarityConfig;
import need.clarity.modules.ModuleBase;
import need.clarity.modules.ModuleRegistry;
import need.clarity.util.ThemeColors;

public class KeybindList extends ModuleBase {
    public static KeybindList INSTANCE;

    public KeybindList() {
        super("Keybind List", "Visual");
        INSTANCE = this;
    }

    public String getAnchor() {
        return ((String) this.getSetting("Anchor", "Top Left"));
    }

    public int getXOffset() {
        Integer var7 = 12;
        return ((Integer) this.getSetting("X Offset", var7));
    }

    public int getYOffset() {
        Integer var7 = 48;
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

    public float getTextSize() {
        Float var11 = 12.0F;
        float var10 = ((Float) this.getSetting("Text Size", var11));
        float var7 = Math.min(18.0F, var10);
        return Math.max(8.0F, var7);
    }

    public float getRowHeight() {
        Float var9 = 20.0F;
        float var8 = ((Float) this.getSetting("Row Height", var9));
        return Math.max(12.0F, var8);
    }

    public float getRowGap() {
        Float var9 = 0.0F;
        float var8 = ((Float) this.getSetting("Row Gap", var9));
        return Math.max(0.0F, var8);
    }

    public float getPadding() {
        Float var9 = 10.0F;
        float var8 = ((Float) this.getSetting("Padding", var9));
        return Math.max(2.0F, var8);
    }

    public float getMinWidth() {
        Float var9 = 0.0F;
        float var8 = ((Float) this.getSetting("Min Width", var9));
        return Math.max(0.0F, var8);
    }

    public float getCornerRadius() {
        Float var9 = 8.0F;
        float var8 = ((Float) this.getSetting("Corner Radius", var9));
        return Math.max(0.0F, var8);
    }

    public String getSort() {
        return ((String) this.getSetting("Sort", "Name"));
    }

    public boolean isOnlyEnabled() {
        Boolean var7 = false;
        return ((Boolean) this.getSetting("Only Enabled", var7));
    }

    public int getMaxRows() {
        Integer var9 = 20;
        int var8 = ((Integer) this.getSetting("Max Rows", var9));
        return Math.max(1, var8);
    }

    public boolean isShowTitle() {
        Boolean var7 = true;
        return ((Boolean) this.getSetting("Show Title", var7));
    }

    public String getTitleText() {
        return ((String) this.getSetting("Title Text", "Keybinds"));
    }

    public boolean isTitleDivider() {
        Boolean var7 = true;
        return ((Boolean) this.getSetting("Title Divider", var7));
    }

    public String getKeyStyle() {
        return ((String) this.getSetting("Key Style", "Pill"));
    }

    public boolean isUppercaseKeys() {
        Boolean var7 = true;
        return ((Boolean) this.getSetting("Uppercase Keys", var7));
    }

    public String getKeyAlign() {
        return ((String) this.getSetting("Key Align", "Right"));
    }

    public boolean isShowMode() {
        Boolean var7 = false;
        return ((Boolean) this.getSetting("Show Mode", var7));
    }

    public boolean isStateDot() {
        Boolean var7 = true;
        return ((Boolean) this.getSetting("State Dot", var7));
    }

    public boolean isRowHighlight() {
        Boolean var7 = false;
        return ((Boolean) this.getSetting("Row Highlight", var7));
    }

    public boolean isDimDisabled() {
        Boolean var7 = true;
        return ((Boolean) this.getSetting("Dim Disabled", var7));
    }

    public boolean isBackground() {
        Boolean var7 = true;
        return ((Boolean) this.getSetting("Background", var7));
    }

    public float getBackgroundOpacity() {
        Float var11 = 0.92F;
        float var10 = ((Float) this.getSetting("Background Opacity", var11));
        float var7 = Math.min(1.0F, var10);
        return Math.max(0.0F, var7);
    }

    public boolean isBorder() {
        Boolean var7 = true;
        return ((Boolean) this.getSetting("Border", var7));
    }

    public boolean isHighlight() {
        Boolean var7 = true;
        return ((Boolean) this.getSetting("Highlight", var7));
    }

    public boolean isBlur() {
        Boolean var7 = false;
        return ((Boolean) this.getSetting("Blur", var7));
    }

    public int getBlurStrength() {
        Integer var11 = 2;
        int var10 = ((Integer) this.getSetting("Blur Strength", var11));
        int var7 = Math.min(4, var10);
        return Math.max(1, var7);
    }

    public float getBlurTint() {
        Float var11 = 0.45F;
        float var10 = ((Float) this.getSetting("Blur Tint", var11));
        float var7 = Math.min(1.0F, var10);
        return Math.max(0.0F, var7);
    }

    public boolean isThemeColors() {
        Boolean var7 = true;
        return ((Boolean) this.getSetting("Theme Colors", var7));
    }

    public int getNameColor() {
        return (ThemeColors.resolveRgb(this.getSetting("Name Color", ""), -1184272, false) & 16777215);
    }

    public int getKeyColor() {
        return (ThemeColors.resolveRgb(this.getSetting("Key Color", ""), -8740105, false) & 16777215);
    }

    public int getBackgroundColor() {
        return (ThemeColors.resolveRgb(this.getSetting("Background Color", ""), -15328474, false) & 16777215);
    }

    public int getEnabledColor() {
        return (ThemeColors.resolveRgb(this.getSetting("Enabled Color", ""), -12845218, false) & 16777215);
    }

    public int getDisabledColor() {
        return (ThemeColors.resolveRgb(this.getSetting("Disabled Color", ""), -50373, false) & 16777215);
    }

    public List evaluate() {
        ArrayList var1 = new ArrayList();
        KeybindListEntry var14 = new KeybindListEntry("Kill Aura", "R", "toggle", true);
        boolean var8 = var1.add(var14);
        KeybindListEntry var15 = new KeybindListEntry("Sprint", "G", "toggle", true);
        boolean var10 = var1.add(var15);
        KeybindListEntry var16 = new KeybindListEntry("Zoom", "C", "hold", false);
        boolean var12 = var1.add(var16);
        return var1;
    }

    public List evaluate1() {
        ArrayList var64 = null;
        int var13 = 0;
        boolean var58 = false;
        Map var1 = ClarityConfig.getKeybinds();
        boolean var16 = this.isOnlyEnabled();
        ArrayList var17 = new ArrayList();
        Iterator var20 = var1.entrySet().iterator();
        while (true) {
            if (!var20.hasNext()) break;
            Map.Entry var50 = ((Map.Entry) var20.next());
            String var53 = ((String) var50.getKey());
            ModuleBase var55 = ModuleRegistry.get(var53);
            if (var55 != null) {
                var58 = var55.isEnabled();
            } else {
                var58 = ClarityConfig.isEnabled(var53);
            }
            int var14 = var58;
            if (var16) {
                if (var14 == 0) {
                }
            }
            String var81 = ((String) var50.getValue());
            String var82 = resolveLabel(var53);
            KeybindListEntry var76 = new KeybindListEntry(var53, var81, var82, var14);
            boolean var62 = var17.add(var76);
        }
        String var24 = this.getSort();
        Comparator var25 = (p0, p1) -> KeybindList.lambda$a(p0, p1);
        var13 = -1;
        switch (var24.hashCode()) {
            case -2022496506 -> {}
            case -206144303 -> {}
            case 75327 -> {}
            default -> {}
        }
        if (var24.equals("Key")) {
            var13 = 0;
            if (var24.equals("Length")) {
                var13 = 1;
                if (var24.equals("Enabled First")) {
                    var13 = 2;
                }
                switch (var13) {
                    case 0 -> {}
                    case 1 -> {}
                    case 2 -> {}
                    default -> {}
                }
                Comparator var70 = (p0, p1) -> KeybindList.lambda$c(var25, p0, p1);
                var17.sort(var70);
                Comparator var67 = (p0, p1) -> KeybindList.lambda$a(var25, p0, p1);
                var17.sort(var67);
                Comparator var72 = (p0, p1) -> KeybindList.lambda$b(var25, p0, p1);
                var17.sort(var72);
                var17.sort(var25);
                int var36 = this.getMaxRows();
                if (var17.size() > var36) {
                    List var77 = var17.subList(0, var36);
                    var64 = new ArrayList(var77);
                } else {
                    var64 = var17;
                }
                return var64;
            }
        }
    }

    private static String resolveLabel(String arg0) {
        try {
            JsonObject var5 = ClarityConfig.getCurrent().getAsJsonObject(arg0);
            if (var5 == null) {
            }
        } catch (Exception ignored) {
        }
        return "toggle";
        try {
            JsonElement var11 = var5.get("bindMode");
            if (var11 != null) {
                if (var11.isJsonPrimitive()) {
                    String var16 = var11.getAsString();
                    Locale var19 = Locale.ROOT;
                    String var17 = var16.toLowerCase(var19);
                }
            }
        } catch (Exception ignored) {
        }
        return var17;
        return "toggle";
    }

}
