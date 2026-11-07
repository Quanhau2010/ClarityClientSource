package need.clarity.modules.visual;

import need.clarity.modules.ModuleBase;

public class TargetHud extends ModuleBase {
    public static TargetHud INSTANCE;

    public TargetHud() {
        super("Target Hud", "Visual");
        INSTANCE = this;
    }

    public int getX() {
        Integer var7 = 12;
        return ((Integer) this.getSetting("X", var7));
    }

    public int getY() {
        Integer var7 = 12;
        return ((Integer) this.getSetting("Y", var7));
    }

    public String getAnchor() {
        return ((String) this.getSetting("Anchor", "Bottom Right"));
    }

    public int getWidth() {
        Integer var9 = 224;
        int var8 = ((Integer) this.getSetting("Width", var9));
        return Math.max(120, var8);
    }

    public boolean isShowHealth() {
        Boolean var7 = true;
        return ((Boolean) this.getSetting("Show Health", var7));
    }

    public boolean isShowDistance() {
        Boolean var7 = true;
        return ((Boolean) this.getSetting("Show Distance", var7));
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

    public boolean isShowGear() {
        Boolean var7 = true;
        return ((Boolean) this.getSetting("Show Gear", var7));
    }

}
