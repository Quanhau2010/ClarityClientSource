package need.clarity.mixin;

import need.clarity.api.ChamsApi;
import need.clarity.api.RenderApi;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = LivingEntityRenderer.class, priority = 500)
public abstract class ChamsEntityMixin {
    public ChamsEntityMixin() {
    }

    @Shadow
    public abstract Identifier getTextureLocation(LivingEntityRenderState arg0);

    @Inject(method = "getRenderType", at = @At("HEAD"), cancellable = true)
    private void chams$renderType(LivingEntityRenderState arg0, boolean arg1, boolean arg2, boolean arg3, CallbackInfoReturnable arg4) {
        Integer var6 = ChamsApi.getStateTint(arg0);
        boolean var7 = ChamsApi.isThroughWalls();
        if (var6 != null) {
            RenderApi.chamsSetActive((var7));
            if (var6 != null) {
                RenderApi.chamsSetHideArmour((!ChamsApi.isRenderArmour()));
                RenderApi.chamsSetArmourThroughWalls(ChamsApi.isArmourThroughWalls());
                if (var6 == null) {
                    return;
                }
                if (ChamsApi.isNormal()) {
                    RenderType var8 = RenderTypes.entityTranslucentCullItemTarget(this.getTextureLocation(arg0));
                    if (var7) {
                    } else {
                    }
                    RenderApi.throughWallsConvert(var8).setReturnValue(var8);
                } else {
                    arg4.setReturnValue(RenderApi.chamsModelRenderType(var7));
                }
                return;
            }
        }
    }

    @Inject(method = "getModelTint", at = @At("HEAD"), cancellable = true)
    private void chams$modelTint(LivingEntityRenderState arg0, CallbackInfoReturnable arg1) {
        Integer var3 = ChamsApi.getStateTint(arg0);
        if (var3 != null) {
            arg1.setReturnValue(var3);
        }
    }

    @ModifyVariable(method = "submit", at = @At("HEAD"), argsOnly = true)
    private SubmitNodeCollector chams$wrapCollector(SubmitNodeCollector arg0) {
        if (!ChamsApi.isEnabled()) {
            return arg0;
        }
        if (!(this instanceof AvatarRenderer)) {
            return arg0;
        }
        return RenderApi.chamsWrap(arg0);
    }

}
