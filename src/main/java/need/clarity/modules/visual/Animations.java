package need.clarity.modules.visual;

import need.clarity.api.Hooks;
import need.clarity.modules.ModuleBase;

public class Animations extends ModuleBase {
    public static final String labelText = "Simple";
    public static final int CONST_INT = 20;
    public static final boolean enabledState = false;
    public static final int CONST_INT1 = 6;
    public static volatile Animations INSTANCE;

    public Animations() {
        super("Animations", "Visual");
        INSTANCE = this;
        Hooks.Bool0 var10 = () -> Animations.isEligible();
        Hooks.bool0("Animations.overrideSwing", var10);
        Hooks.Dbl0 var11 = () -> Animations.overrideSwing();
        Hooks.dbl0("Animations.swingSpeed", var11);
        Hooks.Bool0 var12 = () -> Animations.swingSpeed();
        Hooks.bool0("Animations.enabled", var12);
        Hooks.Obj0 var13 = () -> Animations.enabled();
        Hooks.obj0("Animations.mode", var13);
        Hooks.Dbl0 var14 = () -> Animations.mode();
        Hooks.dbl0("Animations.strength", var14);
    }

    public String getMode() {
        String var12 = null;
        Object var6 = this.getSetting("Mode", "Simple");
        if (var6 == null) {
            return "Simple";
        }
        String var9 = var6.toString();
        if ("Simple".equals(var9)) {
            var12 = "Mode 1";
        } else {
            var12 = var9;
        }
        return var12;
    }

    public int getStrength() {
        int var18 = 0;
        Integer var19 = 20;
        Object var8 = this.getSetting("Strength", var19);
        if ((var8 instanceof Number)) {
            var18 = ((Number) var8);
        } else {
            var18 = 20;
        }
        return Math.clamp(((long) ((byte) var18)), 20, 75);
    }

    public boolean isSwingSpeed() {
        boolean var9 = false;
        Boolean var13 = false;
        Object var6 = this.getSetting("Swing Speed", var13);
        if ((var6 instanceof Boolean)) {
            var9 = ((Boolean) var6);
        } else {
            var9 = false;
        }
        return var9;
    }

    public int getSwingSpeedValue() {
        int var18 = 0;
        Integer var19 = 6;
        Object var8 = this.getSetting("Swing Speed Value", var19);
        if ((var8 instanceof Number)) {
            var18 = ((Number) var8);
        } else {
            var18 = 6;
        }
        return Math.clamp(((long) var18), 1, 20);
    }

    public static boolean isEligible() {
        int var7 = 0;
        Animations var0 = INSTANCE;
        if (var0 != null) {
            if (var0.isEnabled()) {
                if (var0.isSwingSpeed()) {
                    var7 = 1;
                }
            }
        } else {
            var7 = 0;
        }
        return var7;
    }

    public static int overrideSwing() {
        int var3 = 0;
        Animations var0 = INSTANCE;
        if (var0 != null) {
            var3 = var0.getSwingSpeedValue();
        } else {
            var3 = 6;
        }
        return var3;
    }

}
