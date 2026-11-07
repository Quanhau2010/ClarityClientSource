package need.clarity.modules.visual;

import need.clarity.api.Hooks;
import need.clarity.modules.ModuleBase;
import net.minecraft.world.InteractionHand;

public class ViewModel extends ModuleBase {
    public static volatile ViewModel INSTANCE;

    public ViewModel() {
        super("ViewModel", "Visual");
        INSTANCE = this;
        Hooks.Bool0 var13 = () -> ViewModel.isEligible();
        Hooks.bool0("ViewModel.isActive", var13);
        Hooks.Dbl1 var14 = (p0) -> ViewModel.hook$isActive(p0);
        Hooks.dbl1("ViewModel.posX", var14);
        Hooks.Dbl1 var15 = (p0) -> ViewModel.hook$posX(p0);
        Hooks.dbl1("ViewModel.posY", var15);
        Hooks.Dbl1 var16 = (p0) -> ViewModel.hook$posY(p0);
        Hooks.dbl1("ViewModel.posZ", var16);
        Hooks.Dbl1 var17 = (p0) -> ViewModel.hook$posZ(p0);
        Hooks.dbl1("ViewModel.rotX", var17);
        Hooks.Dbl1 var18 = (p0) -> ViewModel.hook$rotX(p0);
        Hooks.dbl1("ViewModel.rotY", var18);
        Hooks.Dbl1 var19 = (p0) -> ViewModel.hook$rotY(p0);
        Hooks.dbl1("ViewModel.rotZ", var19);
        Hooks.Dbl1 var20 = (p0) -> ViewModel.hook$rotZ(p0);
        Hooks.dbl1("ViewModel.scale", var20);
    }

    public boolean isMirror() {
        Boolean var7 = false;
        return ((Boolean) this.getSetting("Mirror", var7));
    }

    public float computeFloatMetric(InteractionHand arg0) {
        float var21 = 0.0F;
        float var13 = 0.0F;
        if (this.isMirror()) {
            Float var27 = 0.0F;
            float var10 = ((Float) this.getSetting("PosX", var27));
            InteractionHand var22 = InteractionHand.MAIN_HAND;
            if (arg0 == var22) {
                var13 = var10;
            }
        } else {
            InteractionHand var24 = InteractionHand.MAIN_HAND;
            if (arg0 == var24) {
                Float var31 = 0.0F;
                var21 = ((Float) this.getSetting("Main PosX", var31));
            } else {
                Float var29 = 0.0F;
                var21 = ((Float) this.getSetting("Off PosX", var29));
            }
            return var21;
            var13 = (var10 * -1.0F);
        }
        return var13;
    }

    public float computeFloatMetric1(InteractionHand arg0) {
        float var13 = 0.0F;
        if (this.isMirror()) {
            Float var25 = 0.0F;
            return ((Float) this.getSetting("PosY", var25));
        }
        InteractionHand var3 = InteractionHand.MAIN_HAND;
        if (arg0 == var3) {
            Float var23 = 0.0F;
            var13 = ((Float) this.getSetting("Main PosY", var23));
        } else {
            Float var21 = 0.0F;
            var13 = ((Float) this.getSetting("Off PosY", var21));
        }
        return var13;
    }

    public float computeFloatMetric2(InteractionHand arg0) {
        float var17 = 0.0F;
        if (this.isMirror()) {
            Float var21 = 0.0F;
            return ((Float) this.getSetting("PosZ", var21));
        }
        InteractionHand var18 = InteractionHand.MAIN_HAND;
        if (arg0 == var18) {
            Float var23 = 0.0F;
            var17 = ((Float) this.getSetting("Main PosZ", var23));
        } else {
            Float var25 = 0.0F;
            var17 = ((Float) this.getSetting("Off PosZ", var25));
        }
        return var17;
    }

    public float computeFloatMetric3(InteractionHand arg0) {
        float var17 = 0.0F;
        if (this.isMirror()) {
            Float var25 = 0.0F;
            return ((Float) this.getSetting("RotX", var25));
        }
        InteractionHand var3 = InteractionHand.MAIN_HAND;
        if (arg0 == var3) {
            Float var23 = 0.0F;
            var17 = ((Float) this.getSetting("Main RotX", var23));
        } else {
            Float var21 = 0.0F;
            var17 = ((Float) this.getSetting("Off RotX", var21));
        }
        return var17;
    }

    public float computeFloatMetric4(InteractionHand arg0) {
        float var17 = 0.0F;
        if (this.isMirror()) {
            Float var21 = 0.0F;
            return ((Float) this.getSetting("RotY", var21));
        }
        InteractionHand var18 = InteractionHand.MAIN_HAND;
        if (arg0 == var18) {
            Float var25 = 0.0F;
            var17 = ((Float) this.getSetting("Main RotY", var25));
        } else {
            Float var23 = 0.0F;
            var17 = ((Float) this.getSetting("Off RotY", var23));
        }
        return var17;
    }

    public float computeFloatMetric5(InteractionHand arg0) {
        float var17 = 0.0F;
        if (this.isMirror()) {
            Float var25 = 0.0F;
            return ((Float) this.getSetting("RotZ", var25));
        }
        InteractionHand var3 = InteractionHand.MAIN_HAND;
        if (arg0 == var3) {
            Float var21 = 0.0F;
            var17 = ((Float) this.getSetting("Main RotZ", var21));
        } else {
            Float var23 = 0.0F;
            var17 = ((Float) this.getSetting("Off RotZ", var23));
        }
        return var17;
    }

    public float computeFloatMetric6(InteractionHand arg0) {
        float var17 = 0.0F;
        if (this.isMirror()) {
            Float var21 = 1.0F;
            return ((Float) this.getSetting("Scale", var21));
        }
        InteractionHand var18 = InteractionHand.MAIN_HAND;
        if (arg0 == var18) {
            Float var25 = 1.0F;
            var17 = ((Float) this.getSetting("Main Scale", var25));
        } else {
            Float var23 = 1.0F;
            var17 = ((Float) this.getSetting("Off Scale", var23));
        }
        return var17;
    }

    public static boolean isEligible() {
        int var5 = 0;
        ViewModel var0 = INSTANCE;
        if (var0 != null) {
            if (var0.isEnabled()) {
                var5 = 1;
            } else {
                var5 = 0;
            }
            return var5;
        }
    }

}
