package need.clarity.modules.visual;

import java.lang.reflect.Field;
import need.clarity.modules.ModuleBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

public class Fullbright extends ModuleBase {
    private static final double CONST_DBL = 16.0;
    private double doubleValue;
    private static Field valueField;

    public Fullbright() {
        super("Fullbright", "Visual");
        this.doubleValue = 1.0;
    }

    public void onEnable() {
        Minecraft var1 = Minecraft.getInstance();
        if (var1.player == null) {
            return;
        }
        if (this.isEligible(var1)) {
            if (var1.options != null) {
                double var18 = ((Double) var1.options.gamma().get());
                this.doubleValue = var18;
                applyStep(var1, 16.0);
            }
        } else {
            applyStep(var1);
        }
    }

    public void onDisable() {
        Minecraft var1 = Minecraft.getInstance();
        if (var1.player == null) {
            return;
        }
        if (var1.options != null) {
            double var10 = this.doubleValue;
            applyStep(var1, var10);
        }
        applyStep1(var1);
    }

    public void onTick() {
        Minecraft var1 = Minecraft.getInstance();
        if (var1.player == null) {
            return;
        }
        if (this.isEligible(var1)) {
            applyStep1(var1);
            if (var1.options != null) {
                applyStep(var1, 16.0);
            }
        } else {
            if (var1.options != null) {
                if (Double.compare(((Double) var1.options.gamma().get()), 1.0) > 0) {
                    double var27 = this.doubleValue;
                    applyStep(var1, var27);
                }
                applyStep(var1);
            }
        }
    }

    private boolean isEligible(Minecraft arg0) {
        int var10 = 0;
        Object var6 = this.getSetting("Mode", "Gamma");
        if (var6 != null) {
            String var12 = var6.toString();
            if ("Gamma".equals(var12)) {
            }
        }
        var10 = 1;
        var10 = 0;
        return var10;
    }

    private static void applyStep(Minecraft arg0) {
        if (arg0.player == null) {
            return;
        }
        Holder var12 = MobEffects.NIGHT_VISION;
        MobEffectInstance var15 = arg0.player.getEffect(var12);
        if (var15 != null) {
            if (var15.getDuration() < 40) {
            }
        }
        LocalPlayer var20 = arg0.player;
        MobEffectInstance var23 = new MobEffectInstance(var12, 2147483647, 0, false, false, false);
        boolean var21 = var20.addEffect(var23);
    }

    private static void applyStep1(Minecraft arg0) {
        if (arg0.player == null) {
            return;
        }
        LocalPlayer var5 = arg0.player;
        Holder var2 = MobEffects.NIGHT_VISION;
        boolean var6 = var5.removeEffect(var2);
    }

    private static void applyStep(Minecraft arg0, double arg1) {
        if (arg0.options == null) {
            return;
        }
        if (valueField == null) {
            OptionInstance var15 = arg0.options.gamma();
            Double var20 = Math.min(arg1, 1.0);
            var15.set(var20);
            return;
        }
        try {
            Field var16 = valueField;
            OptionInstance var23 = arg0.options.gamma();
            Double var24 = arg1;
            var16.set(var23, var24);
        } catch (IllegalAccessException ignored) {
        }
    }

    static {
        try {
            valueField = OptionInstance.class.getDeclaredField("value");
            valueField.setAccessible(true);
        } catch (NoSuchFieldException ignored) {
        }
        try {
            valueField = OptionInstance.class.getDeclaredField("f_231548_");
            valueField.setAccessible(true);
        } catch (NoSuchFieldException ignored) {
        }
    }

}
