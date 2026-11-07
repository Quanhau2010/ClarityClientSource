package need.clarity.modules.visual;

import need.clarity.modules.ModuleBase;

public class HUD extends ModuleBase {
    public static HUD INSTANCE;

    public HUD() {
        super("HUD", "Visual");
        INSTANCE = this;
    }

    public boolean isWatermark() {
        Boolean var7 = true;
        return ((Boolean) this.getSetting("Watermark", var7));
    }

    public String getWatermarkText() {
        return ((String) this.getSetting("Watermark Text", "Clarity"));
    }

    public boolean isFPS() {
        Boolean var7 = true;
        return ((Boolean) this.getSetting("FPS", var7));
    }

    public boolean isPing() {
        Boolean var7 = true;
        return ((Boolean) this.getSetting("Ping", var7));
    }

    public boolean isCoords() {
        Boolean var7 = true;
        return ((Boolean) this.getSetting("Coords", var7));
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

    public boolean isBackground() {
        Boolean var7 = true;
        return ((Boolean) this.getSetting("Background", var7));
    }

    public boolean isBorder() {
        Boolean var7 = true;
        return ((Boolean) this.getSetting("Border", var7));
    }

    public boolean isSeparators() {
        Boolean var7 = true;
        return ((Boolean) this.getSetting("Separators", var7));
    }

    public boolean isHighlight() {
        Boolean var7 = true;
        return ((Boolean) this.getSetting("Highlight", var7));
    }

    public int getX() {
        Integer var7 = 12;
        return ((Integer) this.getSetting("X", var7));
    }

    public int getY() {
        Integer var7 = 12;
        return ((Integer) this.getSetting("Y", var7));
    }

    public float getPaddingX() {
        Float var7 = 13.0F;
        return ((Float) this.getSetting("Padding X", var7));
    }

    public float getPaddingY() {
        Float var7 = 6.0F;
        return ((Float) this.getSetting("Padding Y", var7));
    }

    public float getItemGap() {
        Float var7 = 13.0F;
        return ((Float) this.getSetting("Item Gap", var7));
    }

    public float getCornerRadius() {
        Float var7 = 5.0F;
        return ((Float) this.getSetting("Corner Radius", var7));
    }

    public boolean isAutoScale() {
        Boolean var7 = true;
        return ((Boolean) this.getSetting("Auto Scale", var7));
    }

    public float getOverlayScale() {
        Float var7 = 1.0F;
        return ((Float) this.getSetting("Overlay Scale", var7));
    }

    public String getSupersample() {
        return ((String) this.getSetting("Supersample", "2x"));
    }

    public float getScale() {
        Float var11 = 1.0F;
        float var10 = ((Float) this.getSetting("Scale", var11));
        float var7 = Math.min(1.5F, var10);
        return Math.max(0.5F, var7);
    }

}
