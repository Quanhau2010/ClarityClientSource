package need.clarity.modules.visual;

import need.clarity.modules.ModuleBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.OptionInstance;

public class NoBob extends ModuleBase {
    public static NoBob INSTANCE;
    private Boolean fieldVal;

    public NoBob() {
        super("No Bob", "Visual");
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
            Boolean var16 = ((Boolean) var1.options.bobView().get());
            this.fieldVal = var16;
        }
        OptionInstance var12 = var1.options.bobView();
        Boolean var18 = false;
        var12.set(var18);
    }

    private void applyStep1() {
        Minecraft var1 = Minecraft.getInstance();
        if (var1 != null) {
            if (var1.options != null) {
                if (this.fieldVal == null) {
                }
            }
            return;
            OptionInstance var11 = var1.options.bobView();
            Boolean var13 = this.fieldVal;
            var11.set(var13);
            this.fieldVal = null;
            return;
        }
    }

}
