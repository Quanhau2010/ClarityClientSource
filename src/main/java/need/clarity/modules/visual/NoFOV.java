package need.clarity.modules.visual;

import need.clarity.modules.ModuleBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.OptionInstance;

public class NoFOV extends ModuleBase {
    public static NoFOV INSTANCE;
    private Double fieldVal;

    public NoFOV() {
        super("No FOV", "Visual");
        INSTANCE = this;
        if (this.isEnabled()) {
            this.applyStep();
        }
    }

    public void onEnable() {
        this.applyStep();
    }

    public void onDisable() {
        this.applyStep1();
    }

    private void applyStep() {
        Minecraft var1 = Minecraft.getInstance();
        if (var1 != null) {
            if (var1.options == null) {
            }
        }
        return;
        if (this.fieldVal == null) {
            Double var16 = ((Double) var1.options.fovEffectScale().get());
            this.fieldVal = var16;
        }
        OptionInstance var12 = var1.options.fovEffectScale();
        Double var19 = 0.0;
        var12.set(var19);
    }

    private void applyStep1() {
        Minecraft var1 = Minecraft.getInstance();
        if (var1 != null) {
            if (var1.options != null) {
                if (this.fieldVal == null) {
                }
            }
            return;
            OptionInstance var11 = var1.options.fovEffectScale();
            Double var13 = this.fieldVal;
            var11.set(var13);
            this.fieldVal = null;
            return;
        }
    }

}
