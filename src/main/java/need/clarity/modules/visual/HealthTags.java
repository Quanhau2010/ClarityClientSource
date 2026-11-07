package need.clarity.modules.visual;

import need.clarity.api.Hooks;
import need.clarity.modules.ModuleBase;
import net.minecraft.world.entity.LivingEntity;

public class HealthTags extends ModuleBase {
    private static HealthTags INSTANCE;

    public HealthTags() {
        super("Health Tags", "Visual");
        INSTANCE = this;
        Hooks.Bool0 var8 = () -> HealthTags.lambda$b();
        Hooks.bool0("HealthTags.enabled", var8);
        Hooks.Dbl1 var9 = (p0) -> HealthTags.hook$enabled(p0);
        Hooks.dbl1("HealthTags.displayHp", var9);
        Hooks.Obj2 var10 = (p0, p1) -> HealthTags.hook$displayHp(p0, p1);
        Hooks.obj2("HealthTags.color", var10);
    }

    public static HealthTags evaluate() {
        return INSTANCE;
    }

    public static int computeColorOrIndex(float arg0, float arg1) {
        if (Float.compare(arg1, 0.0F) <= 0) {
            return -1;
        }
        float var8 = (arg0 / arg1);
        if (Float.compare(var8, 0.8F) >= 0) {
            return -11141291;
        }
        if (Float.compare(var8, 0.5F) >= 0) {
            return -171;
        }
        if (Float.compare(var8, 0.25F) >= 0) {
            return -22016;
        }
        return -43691;
    }

    public static int computeColorOrIndex(LivingEntity arg0) {
        int var5 = Math.round(arg0.getHealth());
        return Math.max(0, var5);
    }

}
