package need.clarity.modules.visual;

import need.clarity.modules.ModuleBase;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;

public class AutoF5 extends ModuleBase {
    public static AutoF5 INSTANCE;
    private CameraType fieldVal;
    private boolean enabledState;

    public AutoF5() {
        super("Auto F5", "Visual");
        this.enabledState = false;
        INSTANCE = this;
        Minecraft var7 = Minecraft.getInstance();
        if (this.isEnabled()) {
            if (var7 != null) {
                if (var7.options != null) {
                    CameraType var17 = var7.options.getCameraType();
                    this.fieldVal = var17;
                }
                return;
            }
        }
    }

    public void onEnable() {
        Minecraft var1 = Minecraft.getInstance();
        if (var1 != null) {
            CameraType var8 = var1.options.getCameraType();
            this.fieldVal = var8;
        }
        this.enabledState = false;
    }

    public void onDisable() {
        Minecraft var1 = Minecraft.getInstance();
        if (var1 != null) {
            if (this.enabledState) {
                if (this.fieldVal != null) {
                    Options var10 = var1.options;
                    CameraType var12 = this.fieldVal;
                    var10.setCameraType(var12);
                }
                this.enabledState = false;
                return;
            }
        }
    }

    public void onTick() {
        Minecraft var1 = Minecraft.getInstance();
        if (var1 != null) {
            if (var1.options == null) {
            }
        }
        return;
        if ((var1.screen instanceof AbstractContainerScreen)) {
            if (!this.enabledState) {
                CameraType var30 = var1.options.getCameraType();
                this.fieldVal = var30;
                this.enabledState = true;
            }
            if (var1.options.getCameraType().isFirstPerson()) {
                Options var21 = var1.options;
                CameraType var32 = CameraType.THIRD_PERSON_BACK;
                var21.setCameraType(var32);
            }
        } else {
            if (this.enabledState) {
                if (this.fieldVal != null) {
                    Options var27 = var1.options;
                    CameraType var34 = this.fieldVal;
                    var27.setCameraType(var34);
                }
                this.enabledState = false;
            }
            return;
        }
    }

}
