package need.clarity.modules.visual;

import need.clarity.modules.ModuleBase;
import need.clarity.util.ThemeColors;
import need.clarity.util.ThemeTextures;

public class ThemeTexturesModule extends ModuleBase {
    private float floatValue;
    private int counterValue;
    private long timestampMs;

    public ThemeTexturesModule() {
        super("Theme Textures", "Visual");
        this.floatValue = -1.0F;
        this.counterValue = -2147483648;
        this.timestampMs = 0L;
        this.applyStep();
    }

    private float getStrength() {
        Float var7 = 0.6F;
        return ((Float) this.getSetting("Strength", var7));
    }

    private String getColorMode() {
        return ((String) this.getSetting("Color Mode", "Theme"));
    }

    private boolean isEligible() {
        int var4 = 0;
        String var5 = this.getColorMode();
        if (!"Custom".equalsIgnoreCase(var5)) {
            var4 = 1;
        } else {
            var4 = 0;
        }
        return var4;
    }

    private int getColor() {
        int var7 = 0;
        if (this.isEligible()) {
            var7 = ThemeColors.getAccentRgb();
        } else {
            var7 = ThemeColors.parseHexRgb(this.getSetting("Color", "#7AA2F7"), 8037111);
        }
        return var7;
    }

    private void applyStep() {
        int var6 = this.getColor();
        boolean var8 = this.isEnabled();
        float var11 = this.getStrength();
        boolean var15 = this.isEligible();
        ThemeTextures.set(var8, var11, var15, var6);
        float var13 = this.getStrength();
        this.floatValue = var13;
        this.counterValue = var6;
    }

    public void onEnable() {
        this.applyStep();
        ThemeTextures.reload();
    }

    public void onDisable() {
        this.applyStep();
        ThemeTextures.reload();
    }

    public void onTick() {
        int var25 = 0;
        float var6 = this.getStrength();
        float var26 = this.floatValue;
        if (Float.compare(Math.abs((var6 - var26)), 0.001F) <= 0) {
            int var11 = this.getColor();
            int var29 = this.counterValue;
            if (var11 != var29) {
            }
        }
        var25 = 1;
        var25 = 0;
        if (var25 != 0) {
            this.applyStep();
            long var30 = System.currentTimeMillis();
            this.timestampMs = var30;
        }
        if (this.timestampMs != 0L) {
            long var19 = System.currentTimeMillis();
            long var35 = this.timestampMs;
            if (Long.compare((var19 - var35), 400L) > 0) {
                this.timestampMs = 0L;
                ThemeTextures.reload();
            }
            return;
        }
    }

}
