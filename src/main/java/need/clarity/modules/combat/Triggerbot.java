package need.clarity.modules.combat;

import java.util.Random;
import need.clarity.EventStartAttack;
import need.clarity.PerAttackEvents;
import need.clarity.modules.ModuleBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class Triggerbot extends ModuleBase {
    private final Minecraft minecraft;
    private final Random fieldVal;
    private boolean enabledState;
    private long timestampMs;

    public Triggerbot() {
        super("Triggerbot", "Combat");
        Minecraft var8 = Minecraft.getInstance();
        this.minecraft = var8;
        Random var9 = new Random();
        this.fieldVal = var9;
        this.enabledState = false;
        this.timestampMs = -1L;
    }

    public void onDisable() {
        this.enabledState = false;
        this.timestampMs = -1L;
        if (!AimAssist.isEligible2()) {
            AimAssist.applyStep1();
        }
    }

    public void onTick() {
        long var142 = 0L;
        if (this.minecraft.player != null) {
            if (this.minecraft.level == null) {
            }
        }
        return;
        Boolean var183 = true;
        if (((Boolean) this.getSetting("Not in GUI", var183))) {
            if (this.minecraft.screen != null) {
                return;
            }
        }
        Boolean var185 = true;
        if (((Boolean) this.getSetting("Not While Using", var185))) {
            if (this.minecraft.player.isUsingItem()) {
                return;
            }
        }
        HitResult var41 = this.minecraft.hitResult;
        if (var41 != null) {
            HitResult.Type var44 = var41.getType();
            HitResult.Type var145 = HitResult.Type.ENTITY;
            if (var44 != var145) {
            }
        }
        this.applyStep();
        if (!AimAssist.isEligible2()) {
            AimAssist.applyStep1();
        }
        return;
        Entity var47 = ((EntityHitResult) var41).getEntity();
        if ((var47 instanceof LivingEntity)) {
            LivingEntity var53 = ((LivingEntity) var47);
        } else {
            this.applyStep();
            if (!AimAssist.isEligible2()) {
                AimAssist.applyStep1();
            }
            return;
        }
        LocalPlayer var148 = this.minecraft.player;
        if (var53 != var148) {
            if (!var53.isAlive()) {
            }
        }
        this.applyStep();
        if (!AimAssist.isEligible2()) {
            AimAssist.applyStep1();
        }
        return;
        if (!AimAssist.isEligible2()) {
            AimAssist.applyStep(var53);
        }
        Boolean var187 = false;
        if (((Boolean) this.getSetting("Only Weapon", var187))) {
            ItemStack var153 = this.minecraft.player.getMainHandItem();
            if (!this.isEligible(var153)) {
                this.applyStep();
                return;
            }
        }
        Boolean var189 = true;
        if (((Boolean) this.getSetting("Check Shielding", var189))) {
            if (var53.isBlocking()) {
                this.applyStep();
                return;
            }
        }
        Float var191 = 0.9F;
        float var76 = ((Float) this.getSetting("Cooldown Threshold_lo", var191));
        Float var193 = 1.0F;
        float var80 = ((Float) this.getSetting("Cooldown Threshold_hi", var193));
        float var159 = this.fieldVal.nextFloat();
        float var209 = (var80 - var76);
        float var195 = Math.max(0.0F, var209);
        float var160 = (var159 * var195);
        float var82 = (var76 + var160);
        if (Float.compare(this.minecraft.player.getAttackStrengthScale(0.0F), var82) < 0) {
            this.applyStep();
            return;
        }
        Boolean var197 = true;
        if (((Boolean) this.getSetting("Prioritize Criticals", var197))) {
            if (!this.isEligible()) {
                this.applyStep();
                return;
            }
        }
        Integer var199 = 0;
        int var99 = ((Integer) this.getSetting("Extra Delay_lo", var199));
        Integer var201 = 0;
        int var103 = ((Integer) this.getSetting("Extra Delay_hi", var201));
        long var104 = System.currentTimeMillis();
        if (!this.enabledState) {
            if (var103 > var99) {
                float var111 = this.fieldVal.nextFloat();
                float var169 = ((float) (var103 - var99));
                long var113 = ((long) (var111 * var169));
                long var204 = ((long) var99);
                var142 = (var113 + var204);
            } else {
                var142 = ((long) var99);
            }
            long var16 = var142;
            long var172 = (var104 + var16);
            this.timestampMs = var172;
            this.enabledState = true;
        }
        long var207 = this.timestampMs;
        if (Long.compare(var104, var207) < 0) {
            return;
        }
        HitResult var122 = this.minecraft.hitResult;
        if (var122 != null) {
            HitResult.Type var125 = var122.getType();
            HitResult.Type var175 = HitResult.Type.ENTITY;
            if (var125 == var175) {
                Entity var128 = ((EntityHitResult) var122).getEntity();
                if ((var128 instanceof LivingEntity)) {
                    if (var128.isAlive()) {
                        LocalPlayer var178 = this.minecraft.player;
                        if (var128 != var178) {
                            this.applyStep(var128);
                        }
                        this.enabledState = false;
                        this.timestampMs = -1L;
                        return;
                    }
                }
            }
        }
    }

    private boolean isEligible() {
        int var33 = 0;
        if (this.minecraft.player == null) {
            return false;
        }
        if (this.minecraft.player.onGround()) {
            return true;
        }
        if (!this.minecraft.player.isInWater()) {
            if (this.minecraft.player.isInLava()) {
            }
        }
        return false;
        if (this.minecraft.player.isPassenger()) {
            return false;
        }
        if (Double.compare(this.minecraft.player.getDeltaMovement().y, -0.1) <= 0) {
            var33 = 1;
        } else {
            var33 = 0;
        }
        return var33;
    }

    private boolean isEligible(ItemStack arg0) {
        if (arg0.isEmpty()) {
            return false;
        }
        Item var9 = arg0.getItem();
        if ((var9 instanceof TridentItem)) {
            return true;
        }
        String var14 = BuiltInRegistries.ITEM.getKey(var9).toString();
        TagKey var26 = ItemTags.SWORDS;
        if (arg0.is(var26)) {
            return true;
        }
        TagKey var27 = ItemTags.AXES;
        if (arg0.is(var27)) {
            return true;
        }
        TagKey var28 = ItemTags.MACE_ENCHANTABLE;
        if (arg0.is(var28)) {
            return true;
        }
        return false;
    }

    private void applyStep(Entity arg0) {
        if (this.minecraft.gameMode != null) {
            if (this.minecraft.player == null) {
            }
        }
        return;
        LocalPlayer var22 = this.minecraft.player;
        EventStartAttack var10 = new EventStartAttack(var22, arg0);
        PerAttackEvents.fire(var10);
        MultiPlayerGameMode var13 = this.minecraft.gameMode;
        LocalPlayer var19 = this.minecraft.player;
        var13.attack(var19, arg0);
        LocalPlayer var16 = this.minecraft.player;
        InteractionHand var20 = InteractionHand.MAIN_HAND;
        var16.swing(var20);
    }

    private void applyStep() {
        this.enabledState = false;
        this.timestampMs = -1L;
    }

}
