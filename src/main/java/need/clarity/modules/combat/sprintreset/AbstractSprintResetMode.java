package need.clarity.modules.combat.sprintreset;

import need.clarity.EventStartAttack;
import need.clarity.api.MovementEvent;
import need.clarity.modules.combat.SprintReset;
import net.minecraft.client.Minecraft;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public abstract class AbstractSprintResetMode {
    protected static final Minecraft minecraft;
    protected int counterValue;

    public AbstractSprintResetMode() {
        this.counterValue = -1;
    }

    public void onEnable() {
        this.counterValue = -1;
    }

    public void onDisable() {
        this.counterValue = -1;
    }

    public void onStartAttack(EventStartAttack arg0) {
        if (minecraft.player != null) {
            if (minecraft.level == null) {
            }
        }
        return;
        if (minecraft.screen != null) {
            return;
        }
        if (!this.shouldTriggerReset(arg0)) {
            return;
        }
        if (SprintResetContext.sprintReset != null) {
            SprintReset var16 = SprintResetContext.sprintReset;
            Boolean var44 = false;
            if (((Boolean) var16.getSetting("Only Sword", var44))) {
                if (!this.isOnlySwordEnabled()) {
                    return;
                }
            }
        }
        if (minecraft.hitResult == null) {
            return;
        }
        HitResult var25 = minecraft.hitResult;
        if ((var25 instanceof EntityHitResult)) {
            EntityHitResult var29 = ((EntityHitResult) var25);
            Entity var31 = var29.getEntity();
            if ((var31 instanceof LivingEntity)) {
                LivingEntity var35 = ((LivingEntity) var31);
                if (!this.isValidWeaponAndTarget(var35)) {
                    return;
                }
                int var43 = minecraft.player.tickCount;
                this.counterValue = var43;
                return;
            }
            return;
        }
    }

    public void onMovementTick(MovementEvent arg0) {
        if (minecraft.player != null) {
            if (minecraft.level == null) {
            }
        }
        return;
        if (minecraft.screen != null) {
            return;
        }
        if (minecraft.player.isDeadOrDying()) {
            return;
        }
        if (this.counterValue == -1) {
            return;
        }
        this.applyMovementReset(arg0);
    }

    protected boolean shouldTriggerReset(EventStartAttack arg0) {
        return true;
    }

    protected abstract void applyMovementReset(MovementEvent arg0);

    protected boolean isValidWeaponAndTarget(LivingEntity arg0) {
        int var10 = 0;
        if (arg0 == null) {
            return false;
        }
        if (arg0.isAlive()) {
            if (!arg0.isRemoved()) {
                if (!arg0.isDeadOrDying()) {
                    var10 = 1;
                } else {
                    var10 = 0;
                }
                return var10;
            }
        }
    }

    private boolean isOnlySwordEnabled() {
        if (minecraft.player == null) {
            return false;
        }
        ItemStack var7 = minecraft.player.getMainHandItem();
        if (var7.isEmpty()) {
            return false;
        }
        if ((var7.getItem() instanceof TridentItem)) {
            return true;
        }
        TagKey var2 = ItemTags.SWORDS;
        return var7.is(var2);
    }

    static {
        minecraft = Minecraft.getInstance();
    }

}
