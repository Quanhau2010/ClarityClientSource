package need.clarity.mixin;

import need.clarity.api.Modules;
import need.clarity.api.OvertickApi;
import need.clarity.api.PlayerModelApi;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.core.ClientAsset;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Avatar;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.PlayerSkin;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AvatarRenderer.class)
public class AvatarRendererMixin {
    public AvatarRendererMixin() {
    }

    @Inject(method = "extractRenderState", at = @At("HEAD"))
    private void cl$avatarRenderStateHead(Avatar arg0, AvatarRenderState arg1, float arg2, CallbackInfo arg3) {
    }

    @Inject(method = "extractRenderState", at = @At("TAIL"))
    private void cl$hideElytraVisual(Avatar arg0, AvatarRenderState arg1, float arg2, CallbackInfo arg3) {
        try {
            if (arg0 != Minecraft.getInstance().player) {
            }
        } catch (Throwable ignored) {
        }
        return;
        try {
            if (!OvertickApi.isEnabled()) {
            }
        } catch (Throwable ignored) {
        }
        return;
        try {
            if (!OvertickApi.isElytraStopHeld()) {
            }
        } catch (Throwable ignored) {
        }
        return;
        try {
            if (!arg1.isFallFlying) {
                if (arg1.pose != Pose.FALL_FLYING) {
                }
            }
        } catch (Throwable ignored) {
        }
        return;
        try {
            arg1.isFallFlying = false;
            arg1.pose = Pose.STANDING;
            arg1.fallFlyingTimeInTicks = 0.0F;
            arg1.shouldApplyFlyingYRot = false;
            arg1.flyingYRot = 0.0F;
        } catch (Throwable ignored) {
        }
    }

    @Inject(method = "extractRenderState", at = @At("TAIL"))
    private void cl$clarityCape(Avatar arg0, AvatarRenderState arg1, float arg2, CallbackInfo arg3) {
        Object var5 = null;
        try {
            if (arg0 != Minecraft.getInstance().player) {
            }
        } catch (Throwable ignored) {
        }
        return;
        try {
            if (!Modules.capesEnabled()) {
            }
        } catch (Throwable ignored) {
        }
        return;
        try {
            var5 = Modules.capesTexture();
            if ((var5 instanceof Identifier)) {
                Identifier var6 = ((Identifier) var5);
                if (arg1.skin == null) {
                }
            }
            return;
            try {
                ClientAsset.ResourceTexture var7 = new ClientAsset.ResourceTexture(var6, var6);
                arg1.skin = new PlayerSkin(arg1.skin.body(), var7, var7, arg1.skin.model(), arg1.skin.secure());
                arg1.showCape = true;
            } catch (Throwable ignored) {
            }
            return;
        } catch (Throwable ignored) {
        }
    }

    @Inject(method = "extractRenderState", at = @At("TAIL"))
    private void cl$playerModelHideBody(Avatar arg0, AvatarRenderState arg1, float arg2, CallbackInfo arg3) {
        try {
            if (arg0 == Minecraft.getInstance().player) {
                if (!PlayerModelApi.isActive()) {
                }
            }
            PlayerModelApi.forget(arg1);
        } catch (Throwable ignored) {
        }
        return;
        try {
            arg1.isInvisible = true;
            arg1.isInvisibleToPlayer = true;
            arg1.headEquipment = ItemStack.EMPTY;
            arg1.legsEquipment = ItemStack.EMPTY;
            arg1.feetEquipment = ItemStack.EMPTY;
            if (!arg1.chestEquipment.has(DataComponents.GLIDER)) {
                arg1.chestEquipment = ItemStack.EMPTY;
            }
            arg1.parrotOnLeftShoulder = null;
            arg1.parrotOnRightShoulder = null;
            PlayerModelApi.markModelState(arg1);
        } catch (Throwable ignored) {
        }
    }

}
