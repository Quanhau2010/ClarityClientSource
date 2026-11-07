package need.clarity.mixin;

import need.clarity.api.Modules;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerboundAttackPacket;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Connection.class)
public class CrystalOptimizerMixin {
    public CrystalOptimizerMixin() {
    }

    @Inject(method = "send(Lnet/minecraft/network/protocol/Packet;)V", at = @At("HEAD"))
    private void clarity$onPacketSend(Packet arg0, CallbackInfo arg1) {
        ServerboundAttackPacket var4 = null;
        int var5 = 0;
        int var6 = 0;
        if ((arg0 instanceof ServerboundAttackPacket)) {
            var4 = ((ServerboundAttackPacket) arg0);
            try {
            } catch (Throwable ignored) {
            }
            var5 = var4.entityId();
            var6 = var5;
            if (1 != 0) {
                int var3 = var5;
            } else {
                return;
            }
            if (!Modules.crystalOptimizerActive()) {
                return;
            }
            var4 = Minecraft.getInstance();
            if (var4.level == null) {
                return;
            }
            var5 = var4.level.getEntity(var3);
            if ((var5 instanceof EndCrystal)) {
                var6 = ((EndCrystal) var5);
            } else {
                return;
            }
            LocalPlayer var7 = var4.player;
            if (var7 == null) {
                return;
            }
            if (this.clarity$canDestroyCrystal(var7)) {
                this.clarity$destroyCrystal(var6);
                this.clarity$retargetCrosshair(var4, var6);
            }
            return;
        }
    }

    @Unique
    private boolean clarity$canDestroyCrystal(LocalPlayer arg0) {
        double var2 = 0.0;
        var2 = arg0.getAttributeBaseValue(Attributes.ATTACK_DAMAGE);
        var2 = (var2 + this.clarity$getWeaponDamage(arg0.getMainHandItem()));
        MobEffectInstance var4 = arg0.getEffect(MobEffects.STRENGTH);
        if (var4 != null) {
            var2 = (var2 + (3.0 * ((double) (var4.getAmplifier() + 1))));
        }
        MobEffectInstance var5 = arg0.getEffect(MobEffects.WEAKNESS);
        if (var5 != null) {
            var2 = (var2 - (4.0 * ((double) (var5.getAmplifier() + 1))));
        }
        return (var2 > 0.0);
    }

    @Unique
    private double clarity$getWeaponDamage(ItemStack arg0) {
        if (arg0.isEmpty()) {
            return 0.0;
        }
        new double[1][0] = 0.0;
        double[] var2 = new double[1];
        arg0.forEachModifier(EquipmentSlot.MAINHAND, (p0, p1) -> CrystalOptimizerMixin.lambda$clarity$getWeaponDamage$0(var2, p0, p1));
        return var2[0];
    }

    @Unique
    private void clarity$destroyCrystal(EndCrystal arg0) {
        arg0.remove(Entity.RemovalReason.KILLED);
        arg0.gameEvent(GameEvent.ENTITY_DIE);
    }

    @Unique
    private void clarity$retargetCrosshair(Minecraft arg0, EndCrystal arg1) {
        LocalPlayer var3 = arg0.player;
        if (var3 != null) {
            if (arg0.hitResult != null) {
                if (arg0.crosshairPickEntity != arg1) {
                }
            }
            return;
            HitResult var4 = var3.pick(var3.blockInteractionRange(), 1.0F, false);
            arg0.crosshairPickEntity = null;
            arg0.hitResult = var4;
            return;
        }
    }

}
