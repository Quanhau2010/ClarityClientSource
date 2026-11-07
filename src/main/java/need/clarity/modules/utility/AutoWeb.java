package need.clarity.modules.utility;

import java.lang.reflect.Field;
import need.clarity.modules.ModuleBase;
import need.clarity.modules.combat.AimAssist;
import need.clarity.util.InventoryHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class AutoWeb extends ModuleBase {
    public static AutoWeb INSTANCE;
    private static final Field rightClickDelayField;
    private int counterValue;
    private int counterValue1;

    public AutoWeb() {
        super("Auto Web", "Utility");
        this.counterValue = -1;
        this.counterValue1 = 0;
        INSTANCE = this;
    }

    private boolean isSilentAim() {
        Boolean var7 = true;
        return ((Boolean) this.getSetting("Silent Aim", var7));
    }

    private boolean isOnlyFalling() {
        Boolean var7 = false;
        return ((Boolean) this.getSetting("Only Falling", var7));
    }

    private boolean isSwap() {
        Boolean var7 = true;
        return ((Boolean) this.getSetting("Swap", var7));
    }

    private int getPlaceDelay() {
        Integer var9 = 0;
        int var8 = ((Integer) this.getSetting("Place Delay", var9));
        return Math.max(0, var8);
    }

    public void onEnable() {
        int var11 = 0;
        Minecraft var1 = Minecraft.getInstance();
        if (var1.player == null) {
            var11 = -1;
        } else {
            var11 = InventoryHelper.getSelected(var1.player.getInventory());
        }
        this.counterValue = var11;
        this.counterValue1 = 0;
    }

    public void onDisable() {
        Minecraft var1 = Minecraft.getInstance();
        if (var1.player != null) {
            if (this.counterValue != -1) {
                Inventory var10 = var1.player.getInventory();
                int var15 = this.counterValue;
                InventoryHelper.setSelected(var10, var15);
            }
            this.counterValue = -1;
            if (this.isSilentAim()) {
                AimAssist.clearSilentRotation();
            }
            return;
        }
    }

    public void onTick() {
        LivingEntity var7 = null;
        Minecraft var1 = Minecraft.getInstance();
        if (var1.player != null) {
            if (var1.level == null) {
            }
        }
        return;
        if (this.counterValue1 > 0) {
            int var86 = (this.counterValue1 - 1);
            this.counterValue1 = var86;
            return;
        }
        var7 = null;
        try {
            var7 = AimAssist.getCurrentTarget();
        } catch (Throwable ignored) {
        }
        if (var7 == null) {
            return;
        }
        if (this.isOnlyFalling()) {
            if (Double.compare(var7.getDeltaMovement().y, -0.1) > 0) {
                return;
            }
        }
        BlockPos var30 = var7.blockPosition();
        if (!var1.level.getBlockState(var30).canBeReplaced()) {
            return;
        }
        int var36 = computeColorOrIndex(var1);
        if (var36 < 0) {
            return;
        }
        if (this.isSwap()) {
            if (this.counterValue == -1) {
                int var71 = InventoryHelper.getSelected(var1.player.getInventory());
                this.counterValue = var71;
            }
            InventoryHelper.setSelected(var1.player.getInventory(), var36);
        }
        BlockHitResult var47 = findBlockHitResult(var1, var30);
        if (var47 == null) {
            return;
        }
        if (this.isSilentAim()) {
            Vec3 var53 = var1.player.getEyePosition();
            Vec3 var75 = var47.getLocation();
            float[] var54 = calculateRotationsToVec(var53, var75);
            float var56 = var54[0];
            float var78 = var54[1];
            AimAssist.applyStep(var56, var78);
        }
        setRightClickDelay(var1, 0);
        MultiPlayerGameMode var59 = var1.gameMode;
        LocalPlayer var81 = var1.player;
        InteractionHand var88 = InteractionHand.MAIN_HAND;
        if (var59.useItemOn(var81, var88, var47).consumesAction()) {
            LocalPlayer var64 = var1.player;
            InteractionHand var82 = InteractionHand.MAIN_HAND;
            var64.swing(var82);
        }
        int var84 = this.getPlaceDelay();
        this.counterValue1 = var84;
    }

    private static BlockHitResult findBlockHitResult(Minecraft arg0, BlockPos arg1) {
        int var14 = 0;
        double var21 = arg0.player.blockInteractionRange();
        double var60 = arg0.player.blockInteractionRange();
        double var23 = (var21 * var60);
        Vec3 var27 = arg0.player.getEyePosition();
        Direction[] var28 = Direction.values();
        int var30 = var28.length;
        var14 = 0;
        while (true) {
            if (var14 < var30) {
                int var53 = var14;
                Direction var34 = var28[var53];
                BlockPos var36 = arg1.relative(var34);
                if (!arg0.level.getBlockState(var36).isSolid()) {
                }
            } else {
                return null;
                Direction var42 = var34.getOpposite();
                double var64 = ((double) var42.getStepX());
                double var72 = ((double) var42.getStepY());
                double var76 = ((double) var42.getStepZ());
                Vec3 var43 = new Vec3(var64, var72, var76);
                Vec3 var45 = Vec3.atCenterOf(var36);
                Vec3 var57 = var43.scale(0.5);
                Vec3 var46 = var45.add(var57);
                if (Double.compare(var27.distanceToSqr(var46), var23) <= 0) break;
            }
            var14 = (var14 + 1);
        }
        BlockHitResult var51 = new BlockHitResult(var46, var42, var36, false);
        return var51;
    }

    private static int computeColorOrIndex(Minecraft arg0) {
        int var4 = 0;
        Inventory var7 = arg0.player.getInventory();
        var4 = 0;
        while (true) {
            if (var4 >= 9) break;
            int var18 = var4;
            ItemStack var12 = var7.getItem(var18);
            if (!var12.isEmpty()) {
                Item var19 = Items.COBWEB;
                if (var12.is(var19)) {
                    return var4;
                }
            }
            var4 = (var4 + 1);
        }
        return -1;
    }

    private static float[] calculateRotationsToVec(Vec3 arg0, Vec3 arg1) {
        Vec3 var12 = arg1.subtract(arg0);
        double var14 = var12.x;
        double var24 = var12.x;
        double var16 = (var14 * var24);
        double var27 = var12.z;
        double var56 = var12.z;
        double var29 = (var27 * var56);
        double var20 = Math.sqrt((var16 + var29));
        float[] var23 = new float[2];
        double var35 = (var12.x * -1.0);
        double var59 = var12.z;
        float var43 = ((float) ((Math.atan2(var35, var59) * 180.0) / 3.141592653589793));
        var23[0] = var43;
        float var55 = ((float) (((Math.atan2(var12.y, var20) * -1.0) * 180.0) / 3.141592653589793));
        var23[1] = var55;
        return var23;
    }

    private static void setRightClickDelay(Minecraft arg0, int arg1) {
        if (rightClickDelayField == null) {
            return;
        }
        try {
            rightClickDelayField.setInt(arg0, arg1);
        } catch (IllegalAccessException ignored) {
        }
    }

    static {
        Field var2 = null;
        var2 = null;
        try {
            var2 = Minecraft.class.getDeclaredField("rightClickDelay");
            var2.setAccessible(true);
        } catch (NoSuchFieldException ignored) {
        }
        rightClickDelayField = var2;
    }

}
