package need.clarity.modules.visual;

import need.clarity.api.Hooks;
import need.clarity.modules.ModuleBase;

public class Nametags extends ModuleBase {
    public static Nametags INSTANCE;

    public Nametags() {
        super("Nametags", "Visual");
        INSTANCE = this;
        Hooks.Bool0 var7 = () -> Nametags.isEligible();
        Hooks.bool0("Nametags.active", var7);
        Hooks.Bool0 var8 = () -> Nametags.active();
        Hooks.bool0("Nametags.hideVanilla", var8);
    }

    public boolean isHealth() {
        Boolean var7 = true;
        return ((Boolean) this.getSetting("Health", var7));
    }

    public boolean isDistance() {
        Boolean var7 = true;
        return ((Boolean) this.getSetting("Distance", var7));
    }

    public boolean isArmour() {
        Boolean var7 = true;
        return ((Boolean) this.getSetting("Armour", var7));
    }

    public boolean isHeldItem() {
        Boolean var7 = false;
        return ((Boolean) this.getSetting("Held Item", var7));
    }

    public boolean isPing() {
        Boolean var7 = false;
        return ((Boolean) this.getSetting("Ping", var7));
    }

    public static boolean active() {
        int var7 = 0;
        if (INSTANCE != null) {
            Nametags var3 = INSTANCE;
            Boolean var8 = true;
            if (((Boolean) var3.getSetting("Hide Vanilla", var8))) {
                var7 = 1;
            } else {
                var7 = 0;
            }
            return var7;
        }
    }

    public static boolean isEligible() {
        int var3 = 0;
        if (INSTANCE != null) {
            if (INSTANCE.isEnabled()) {
                var3 = 1;
            } else {
                var3 = 0;
            }
            return var3;
        }
    }

    public boolean isMobOwners() {
        Boolean var7 = false;
        return ((Boolean) this.getSetting("Mob Owners", var7));
    }

    public boolean isBackground() {
        Boolean var7 = true;
        return ((Boolean) this.getSetting("Background", var7));
    }

    public boolean isHideSelf() {
        Boolean var7 = true;
        return ((Boolean) this.getSetting("Hide Self", var7));
    }

    public float getScale() {
        Float var11 = 1.0F;
        float var10 = ((Float) this.getSetting("Scale", var11));
        float var7 = Math.min(1.5F, var10);
        return Math.max(0.5F, var7);
    }

    public float getMaxDistance() {
        Float var7 = 64.0F;
        return ((Float) this.getSetting("Max Distance", var7));
    }

}
