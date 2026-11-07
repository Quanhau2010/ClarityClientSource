package need.clarity.modules.utility;

import java.lang.reflect.Field;
import need.clarity.modules.ModuleBase;
import need.clarity.modules.combat.AimAssist;
import need.clarity.util.InventoryHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class AntiCob extends ModuleBase {
    public static AntiCob INSTANCE;
    private static final Field rightClickDelayField;
    private int counterValue;
    private boolean enabledState;
    private int counterValue1;
    private BlockPos targetBlockPos;

    public AntiCob() {
        super("Anti Cob", "Utility");
        this.counterValue = -1;
        this.enabledState = false;
        this.counterValue1 = 0;
        this.targetBlockPos = null;
        INSTANCE = this;
    }

    private String getMode() {
        return ((String) this.getSetting("Mode", "Water"));
    }

    private boolean isSilentRotations() {
        Boolean var7 = true;
        return ((Boolean) this.getSetting("Silent Rotations", var7));
    }

    private int getPickUpDelay() {
        Integer var9 = 10;
        int var8 = ((Integer) this.getSetting("Pick Up Delay", var9));
        return Math.max(1, var8);
    }

    public void onEnable() {
        int var12 = 0;
        Minecraft var1 = Minecraft.getInstance();
        if (var1.player == null) {
            var12 = -1;
        } else {
            var12 = InventoryHelper.getSelected(var1.player.getInventory());
        }
        this.counterValue = var12;
        this.enabledState = false;
        this.counterValue1 = 0;
        this.targetBlockPos = null;
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
            if (this.isSilentRotations()) {
                AimAssist.clearSilentRotation();
            }
            return;
        }
    }

    public void onTick() {
        int var94 = 0;
        Minecraft var1 = Minecraft.getInstance();
        if (var1.player != null) {
            if (var1.level == null) {
            }
        }
        return;
        BlockPos var19 = var1.player.blockPosition();
        BlockState var22 = var1.level.getBlockState(var19);
        Block var95 = Blocks.COBWEB;
        if (!var22.is(var95)) {
            ClientLevel var25 = var1.level;
            BlockPos var97 = var19.above();
            BlockState var26 = var25.getBlockState(var97);
            Block var98 = Blocks.COBWEB;
            if (var26.is(var98)) {
            }
        }
        var94 = 1;
        var94 = 0;
        if (var94 == 0) {
            this.enabledState = false;
            this.counterValue1 = 0;
            return;
        }
        String var100 = this.getMode();
        if ("Teleport".equalsIgnoreCase(var100)) {
            try {
                LocalPlayer var32 = var1.player;
                double var103 = var1.player.getX();
                double var148 = (var1.player.getY() + 1.2);
                double var154 = var1.player.getZ();
                var32.setPos(var103, var148, var154);
            } catch (Throwable ignored) {
            }
        } else {
            if (!this.enabledState) {
                Item var105 = Items.WATER_BUCKET;
                int var37 = findHotbarItemSlot(var1, var105);
                if (var37 < 0) {
                    return;
                }
                if (this.counterValue == -1) {
                    int var110 = InventoryHelper.getSelected(var1.player.getInventory());
                    this.counterValue = var110;
                }
                InventoryHelper.setSelected(var1.player.getInventory(), var37);
                BlockHitResult var46 = findBlockHitResult(var1, var19);
                if (var46 == null) {
                    return;
                }
                if (this.isSilentRotations()) {
                    Vec3 var52 = var1.player.getEyePosition();
                    Vec3 var114 = var46.getLocation();
                    float[] var53 = calculateRotationsToVec(var52, var114);
                    float var55 = var53[0];
                    float var117 = var53[1];
                    AimAssist.applyStep(var55, var117);
                }
                setRightClickDelay(var1, 0);
                MultiPlayerGameMode var58 = var1.gameMode;
                LocalPlayer var120 = var1.player;
                InteractionHand var142 = InteractionHand.MAIN_HAND;
                if (var58.useItemOn(var120, var142, var46).consumesAction()) {
                    LocalPlayer var63 = var1.player;
                    InteractionHand var121 = InteractionHand.MAIN_HAND;
                    var63.swing(var121);
                    this.enabledState = true;
                    this.counterValue1 = 0;
                    this.targetBlockPos = var19;
                }
            } else {
                int var126 = (this.counterValue1 + 1);
                this.counterValue1 = var126;
                int var69 = this.counterValue1;
                int var128 = this.getPickUpDelay();
                if (var69 >= var128) {
                    Item var129 = Items.BUCKET;
                    int var71 = findHotbarItemSlot(var1, var129);
                    if (var71 >= 0) {
                        if (this.targetBlockPos != null) {
                            InventoryHelper.setSelected(var1.player.getInventory(), var71);
                            BlockPos var132 = this.targetBlockPos;
                            BlockHitResult var79 = findBlockHitResult(var1, var132);
                            if (var79 != null) {
                                setRightClickDelay(var1, 0);
                                MultiPlayerGameMode var83 = var1.gameMode;
                                LocalPlayer var135 = var1.player;
                                InteractionHand var144 = InteractionHand.MAIN_HAND;
                                if (var83.useItemOn(var135, var144, var79).consumesAction()) {
                                    LocalPlayer var88 = var1.player;
                                    InteractionHand var136 = InteractionHand.MAIN_HAND;
                                    var88.swing(var136);
                                }
                                this.enabledState = false;
                                this.counterValue1 = 0;
                                this.targetBlockPos = null;
                            }
                        }
                    }
                }
                return;
            }
        }
    }

    private static int findHotbarItemSlot(Minecraft arg0, Item arg1) {
        int var5 = 0;
        Inventory var8 = arg0.player.getInventory();
        var5 = 0;
        while (true) {
            if (var5 < 9) {
                int var19 = var5;
                ItemStack var12 = var8.getItem(var19);
                if (!var12.isEmpty()) {
                    if (var12.is(arg1)) {
                        return var5;
                    }
                }
            }
            return -1;
            var5 = (var5 + 1);
        }
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
                Direction var35 = var28[var53];
                BlockPos var37 = arg1.relative(var35);
                if (!arg0.level.getBlockState(var37).isSolid()) {
                } else {
                    Direction var43 = var35.getOpposite();
                    double var64 = ((double) var43.getStepX());
                    double var72 = ((double) var43.getStepY());
                    double var76 = ((double) var43.getStepZ());
                    Vec3 var44 = new Vec3(var64, var72, var76);
                    Vec3 var46 = Vec3.atCenterOf(var37);
                    Vec3 var57 = var44.scale(0.5);
                    Vec3 var47 = var46.add(var57);
                    if (Double.compare(var27.distanceToSqr(var47), var23) > 0) {
                    } else {
                        BlockHitResult var52 = new BlockHitResult(var47, var43, var37, false);
                        return var52;
                    }
                }
            }
            return null;
            var14 = (var14 + 1);
        }
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
