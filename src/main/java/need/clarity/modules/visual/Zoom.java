package need.clarity.modules.visual;

import need.clarity.api.Hooks;
import need.clarity.modules.ModuleBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.OptionInstance;

public class Zoom extends ModuleBase {
    public static Zoom INSTANCE;
    private Integer fieldVal;
    private static float floatValue;
    private static float floatValue1;

    public Zoom() {
        super("Zoom", "Visual");
        INSTANCE = this;
        Hooks.Bool0 var10 = () -> Zoom.isEligible();
        Hooks.bool0("Zoom.isActive", var10);
        Hooks.Act1 var11 = (p0) -> Zoom.hook$isActive(p0);
        Hooks.act1("Zoom.scroll", var11);
        if (this.isEnabled()) {
            this.onEnable();
        }
    }

    public void onEnable() {
        Minecraft var1 = Minecraft.getInstance();
        if (var1 != null) {
            if (var1.options == null) {
            }
        }
        return;
        if (this.fieldVal == null) {
            Integer var26 = ((Integer) var1.options.fov().get());
            this.fieldVal = var26;
        }
        Integer var28 = 30;
        floatValue = ((float) ((Integer) this.getSetting("FOV", var28)));
        floatValue1 = ((float) ((Integer) var1.options.fov().get()));
    }

    public void onDisable() {
        Minecraft var1 = Minecraft.getInstance();
        if (var1 != null) {
            if (var1.options != null) {
                if (this.fieldVal == null) {
                }
            }
            return;
            OptionInstance var11 = var1.options.fov();
            Integer var13 = this.fieldVal;
            var11.set(var13);
            this.fieldVal = null;
            return;
        }
    }

    public void onTick() {
        if (!this.isEnabled()) {
            return;
        }
        Minecraft var6 = Minecraft.getInstance();
        if (var6 != null) {
            if (var6.options == null) {
            }
        }
        return;
        float var10 = floatValue1;
        float var2 = floatValue;
        float var3 = floatValue1;
        float var21 = ((var2 - var3) * 0.3F);
        floatValue1 = (var10 + var21);
        float var12 = floatValue1;
        float var22 = floatValue;
        if (Float.compare(Math.abs((var12 - var22)), 0.5F) < 0) {
            floatValue1 = floatValue;
        }
        OptionInstance var19 = var6.options.fov();
        Integer var26 = Math.round(floatValue1);
        var19.set(var26);
    }

    public static void applyStep(double arg0) {
        if (!isEligible()) {
            return;
        }
        float var4 = floatValue;
        float var13 = (((float) arg0) * 2.0F);
        float var11 = (var4 - var13);
        float var10 = Math.min(70.0F, var11);
        floatValue = Math.max(10.0F, var10);
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

    static {
        floatValue = 30.0F;
        floatValue1 = 70.0F;
    }

}
