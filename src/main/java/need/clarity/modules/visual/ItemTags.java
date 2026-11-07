package need.clarity.modules.visual;

import need.clarity.modules.ModuleBase;

public class ItemTags extends ModuleBase {
    public static ItemTags INSTANCE;

    public ItemTags() {
        super("Item Tags", "Visual");
        INSTANCE = this;
    }

    public boolean isShowCount() {
        Boolean var7 = true;
        return ((Boolean) this.getSetting("Show Count", var7));
    }

    public boolean isBackground() {
        Boolean var7 = true;
        return ((Boolean) this.getSetting("Background", var7));
    }

    public float getScale() {
        Float var11 = 1.0F;
        float var10 = ((Float) this.getSetting("Scale", var11));
        float var7 = Math.min(1.5F, var10);
        return Math.max(0.5F, var7);
    }

    public float getMaxDistance() {
        Float var7 = 32.0F;
        return ((Float) this.getSetting("Max Distance", var7));
    }

}
