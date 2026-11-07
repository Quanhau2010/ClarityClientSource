package need.clarity.mixin;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import need.clarity.api.Modules;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = EntityRenderer.class, priority = 900)
public abstract class NameProtectNametagMixin {
    private static final Map NP_NAME_TAG_FIELD;

    public NameProtectNametagMixin() {
    }

    private static Field cl$npFindNameTagField(EntityRenderState arg0) {
        return ((Field) NP_NAME_TAG_FIELD.computeIfAbsent(arg0.getClass(), (p0) -> NameProtectNametagMixin.lambda$cl$npFindNameTagField$0(p0)));
    }

    @Inject(method = "extractRenderState", at = @At("RETURN"))
    private void cl$nameProtectNametag(Entity arg0, EntityRenderState arg1, float arg2, CallbackInfo arg3) {
        Object var7 = null;
        if (!Modules.nameProtectEnabled()) {
            return;
        }
        Minecraft var5 = Minecraft.getInstance();
        if (arg0 != var5.player) {
            return;
        }
        Field var6 = cl$npFindNameTagField(arg1);
        if (var6 == null) {
            return;
        }
        try {
            var7 = var6.get(arg1);
            if ((var7 instanceof Component)) {
                Component var8 = ((Component) var7);
            } else {
                return;
            }
            try {
                Component var9 = Modules.nameProtectReplaceInTree(var8);
                if (var9 != var8) {
                    var6.set(arg1, var9);
                }
                return;
            } catch (Exception ignored) {
            }
        } catch (Exception ignored) {
        }
    }

    static {
        NP_NAME_TAG_FIELD = new ConcurrentHashMap();
    }

}
