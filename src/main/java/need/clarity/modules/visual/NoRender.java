package need.clarity.modules.visual;

import need.clarity.api.Hooks;
import need.clarity.modules.ModuleBase;

public class NoRender extends ModuleBase {
    public static volatile NoRender INSTANCE;

    public NoRender() {
        super("NoRender", "Visual");
        INSTANCE = this;
        Hooks.Bool0 var15 = () -> this.isNauseaOverlay();
        Hooks.bool0("NoRender.nausea", var15);
        Hooks.Bool0 var17 = () -> this.nausea();
        Hooks.bool0("NoRender.portal", var17);
        Hooks.Bool0 var19 = () -> this.portal();
        Hooks.bool0("NoRender.spyglass", var19);
        Hooks.Bool0 var21 = () -> this.spyglass();
        Hooks.bool0("NoRender.scoreboard", var21);
        Hooks.Bool0 var23 = () -> this.scoreboard();
        Hooks.bool0("NoRender.vignette", var23);
        Hooks.Bool0 var25 = () -> this.vignette();
        Hooks.bool0("NoRender.fire", var25);
        Hooks.Bool0 var27 = () -> this.fire();
        Hooks.bool0("NoRender.totem", var27);
        Hooks.Bool0 var29 = () -> this.totem();
        Hooks.bool0("NoRender.explosion", var29);
        Hooks.Bool1 var30 = (p0) -> NoRender.hook$explosion(p0);
        Hooks.bool1("NoRender.hideExplosionAt", var30);
    }

    public boolean vignette() {
        if (!this.isEnabled()) {
            return false;
        }
        Boolean var10 = true;
        return ((Boolean) this.getSetting("Fire Overlay", var10));
    }

    public boolean isNauseaOverlay() {
        if (!this.isEnabled()) {
            return false;
        }
        Boolean var10 = true;
        return ((Boolean) this.getSetting("Nausea Overlay", var10));
    }

    public boolean nausea() {
        if (!this.isEnabled()) {
            return false;
        }
        Boolean var10 = true;
        return ((Boolean) this.getSetting("Portal Overlay", var10));
    }

    public boolean portal() {
        if (!this.isEnabled()) {
            return false;
        }
        Boolean var10 = true;
        return ((Boolean) this.getSetting("Spyglass Overlay", var10));
    }

    public boolean spyglass() {
        if (!this.isEnabled()) {
            return false;
        }
        Boolean var10 = false;
        return ((Boolean) this.getSetting("Scoreboard", var10));
    }

    public boolean scoreboard() {
        if (!this.isEnabled()) {
            return false;
        }
        Boolean var10 = false;
        return ((Boolean) this.getSetting("Vignette", var10));
    }

    public boolean fire() {
        if (!this.isEnabled()) {
            return false;
        }
        Boolean var10 = false;
        return ((Boolean) this.getSetting("Totem Animation", var10));
    }

    public static boolean hideExplosionParticlesAt(double arg0, double arg1, double arg2) {
        NoRender var6 = INSTANCE;
        if (var6 != null) {
            if (var6.totem()) {
                return true;
            }
        }
        return CrystalView.isEligible1(arg0, arg1, arg2);
    }

    public boolean totem() {
        if (!this.isEnabled()) {
            return false;
        }
        Boolean var10 = false;
        return ((Boolean) this.getSetting("Explosion Particles", var10));
    }

}
