package need.clarity.mixin;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import need.clarity.api.Modules;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderer.class)
public abstract class HealthTagsMixin {
    private static final Map NAME_TAG_FIELD;

    public HealthTagsMixin() {
    }

    private static Field findNameTagField(EntityRenderState arg0) {
        return ((Field) NAME_TAG_FIELD.computeIfAbsent(arg0.getClass(), (p0) -> HealthTagsMixin.lambda$findNameTagField$0(p0)));
    }

    @Inject(method = "extractRenderState", at = @At("RETURN"))
    private void clarity$appendHealthTag(Entity arg0, EntityRenderState arg1, float arg2, CallbackInfo arg3) {
        Object var8 = null;
        if (!Modules.healthTagsEnabled()) {
            return;
        }
        if ((arg0 instanceof LivingEntity)) {
            LivingEntity var5 = ((LivingEntity) arg0);
        } else {
            return;
        }
        Minecraft var6 = Minecraft.getInstance();
        if (arg0 == var6.player) {
            return;
        }
        Field var7 = findNameTagField(arg1);
        if (var7 == null) {
            return;
        }
        try {
            var8 = var7.get(arg1);
            if ((var8 instanceof Component)) {
                Component var9 = ((Component) var8);
            } else {
                return;
            }
            try {
                int var10 = Modules.healthTagsDisplayHp(var5);
                int var12 = (Modules.healthTagsColor(var5.getHealth(), var5.getMaxHealth()) & 16777215);
                MutableComponent var13 = Component.literal("  " + (var10)).setStyle(Style.EMPTY.withColor(var12));
                var7.set(arg1, var9.copy().append(var13));
            } catch (Exception ignored) {
            }
            return;
        } catch (Exception ignored) {
        }
    }

    static {
        NAME_TAG_FIELD = new ConcurrentHashMap();
    }

}
