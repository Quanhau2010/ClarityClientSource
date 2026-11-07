package need.clarity.modules.combat;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import need.clarity.PerFrameEvents;
import need.clarity.mixin.KeyMappingAccessor;
import need.clarity.modules.ModuleBase;
import need.clarity.util.InventoryHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RespawnAnchorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.lwjgl.glfw.GLFW;

public class AutoAnchor extends ModuleBase {
    private static final int CONST_INT = 4;
    private static final float floatValue = 2.0F;
    private static final float floatValue1 = 5.0F;
    private AutoAnchorStage autoAnchorStage;
    private BlockPos targetBlockPos;
    private BlockPos targetBlockPos1;
    private int counterValue;
    private int counterValue1;
    private int counterValue2;
    private float floatValue2;
    private float floatValue3;
    private float floatValue4;
    private float floatValue5;
    private float floatValue6;
    private float floatValue7;
    private boolean enabledState;
    private static final Field reflectedField;

    private static Field evaluate() {
        try {
            Field var3 = Minecraft.class.getDeclaredField("rightClickDelay");
            var3.setAccessible(true);
        } catch (NoSuchFieldException ignored) {
        }
        return var3;
    }

    public AutoAnchor() {
        super("Auto Anchor", "Combat");
        AutoAnchorStage var19 = AutoAnchorStage.IDLE;
        this.autoAnchorStage = var19;
        this.targetBlockPos = null;
        this.targetBlockPos1 = null;
        this.counterValue = -1;
        this.counterValue1 = 0;
        this.counterValue2 = -1;
        this.floatValue2 = 0.0F;
        this.floatValue3 = 0.0F;
        this.floatValue4 = 0.0F;
        this.floatValue5 = 0.0F;
        this.floatValue6 = 0.0F;
        this.floatValue7 = 0.0F;
        this.enabledState = false;
        PerFrameEvents.register(() -> this.applyStep());
    }

    private void applyStep() {
        if (!this.isEnabled()) {
            return;
        }
        if (!this.isSafeAimMode()) {
            return;
        }
        Minecraft var13 = Minecraft.getInstance();
        if (var13.player == null) {
            return;
        }
        switch (this.autoAnchorStage.ordinal()) {
            case 2 -> {}
            case 3 -> {}
            case 4 -> {}
            case 5 -> {}
            case 8 -> {}
            case 9 -> {}
            case 10 -> {}
            case 11 -> {}
            case 12 -> {}
            default -> {}
        }
        float var33 = this.floatValue2;
        float var44 = this.floatValue3;
        AimAssist.applyStep(var33, var44);
        if (this.targetBlockPos != null) {
            BlockPos var45 = this.targetBlockPos;
            Direction var22 = this.evaluate(var13, var45);
            Vec3 var25 = var13.player.getEyePosition();
            Vec3 var36 = evaluate(this.targetBlockPos, var22);
            float[] var27 = calculateRotationsToVec(var25, var36);
            float var38 = var27[0];
            this.floatValue4 = var38;
            float var40 = var27[1];
            this.floatValue5 = var40;
        }
        float var31 = this.floatValue4;
        float var42 = this.floatValue5;
        AimAssist.applyStep(var31, var42);
    }

    public void onDisable() {
        Minecraft var1 = Minecraft.getInstance();
        this.applyStep18(var1);
        this.applyStep15();
        this.applyStep17();
    }

    public void onTick() {
        Minecraft var1 = Minecraft.getInstance();
        if (!this.isEnabled()) {
            return;
        }
        if (var1.player != null) {
            if (var1.level != null) {
                if (var1.gameMode == null) {
                }
            }
            return;
            if (var1.screen != null) {
                return;
            }
            String var18 = ((String) this.getSetting("Aim Mode", "None"));
            if ("Silent".equals(var18)) {
                this.applyStep7(var1);
            } else {
                this.applyStep(var1);
            }
            return;
        }
    }

    private void applyStep(Minecraft arg0) {
        boolean var7 = arg0.options.keyUse.isDown();
        AutoAnchorStage var9 = this.autoAnchorStage;
        AutoAnchorStage var3 = AutoAnchorStage.DONE;
        if (var9 == var3) {
            this.applyStep16(arg0);
            if (!var7) {
                this.applyStep15();
                this.applyStep17();
            }
            return;
        }
        if (!var7) {
            AutoAnchorStage var12 = this.autoAnchorStage;
            AutoAnchorStage var30 = AutoAnchorStage.IDLE;
            if (var12 != var30) {
                this.applyStep18(arg0);
                this.applyStep15();
                this.applyStep17();
                return;
            }
        }
        if (!var7) {
            return;
        }
        switch (this.autoAnchorStage.ordinal()) {
            case 0 -> {}
            case 1 -> {}
            case 2 -> {}
            case 3 -> {}
            case 4 -> {}
            case 5 -> {}
            default -> {}
        }
        this.applyStep1(arg0);
        this.applyStep2(arg0);
        this.applyStep6(arg0);
        this.applyStep5(arg0);
        this.applyStep4(arg0);
        this.applyStep3(arg0);
    }

    private void applyStep1(Minecraft arg0) {
        String var13 = ((String) this.getSetting("Anchor Mode", "Normal"));
        BlockHitResult var15 = this.findBlockHitResult1(arg0);
        if (var15 == null) {
            return;
        }
        BlockPos var18 = var15.getBlockPos();
        if (!(this.isEligible2(arg0, var18))) {
            return;
        }
        if (this.computeColorOrIndex(arg0, var18) != 0) {
            return;
        }
        int var24 = this.computeColorOrIndex1(arg0);
        if (var24 == -1) {
            return;
        }
        this.applyStep16(arg0);
        this.targetBlockPos = var18;
        this.setRightClickDelay1(arg0, var24);
        ItemStack var31 = arg0.player.getMainHandItem();
        Item var60 = Items.GLOWSTONE;
        if (!var31.is(var60)) {
            return;
        }
        if (!(this.evaluate(arg0, var15).consumesAction())) {
            this.applyStep18(arg0);
            this.applyStep17();
            return;
        }
        Integer var79 = 0;
        int var39 = computeColorOrIndex(this.getSetting("Explode Delay", var79));
        int var65 = arg0.player.tickCount;
        int var81 = Math.max(0, var39);
        int var66 = (var65 + var81);
        this.counterValue1 = var66;
        if ("Safe".equals(var13)) {
            if (!(this.isEligible1(arg0, var18))) {
                if (!(this.isEligible(arg0, var18))) {
                }
            }
            AutoAnchorStage var71 = AutoAnchorStage.EXPLODE_WAIT;
            this.autoAnchorStage = var71;
            AutoAnchorStage var70 = AutoAnchorStage.SAFE_ROTATE_TO_SIDE;
            this.autoAnchorStage = var70;
        } else {
            AutoAnchorStage var72 = AutoAnchorStage.EXPLODE_WAIT;
            this.autoAnchorStage = var72;
        }
    }

    private void applyStep2(Minecraft arg0) {
        this.applyStep16(arg0);
        int var9 = arg0.player.tickCount;
        int var35 = this.counterValue1;
        if (var9 < var35) {
            return;
        }
        if (this.targetBlockPos == null) {
            this.applyStep18(arg0);
            this.applyStep17();
            return;
        }
        BlockPos var46 = this.targetBlockPos;
        if (!(this.isEligible2(arg0, var46))) {
            this.applyStep18(arg0);
            this.applyStep17();
            return;
        }
        BlockHitResult var15 = this.findBlockHitResult1(arg0);
        if (var15 != null) {
            BlockPos var18 = var15.getBlockPos();
            BlockPos var39 = this.targetBlockPos;
            if (!var18.equals(var39)) {
            }
        }
        return;
        this.applyStep18(arg0);
        ItemStack var23 = arg0.player.getMainHandItem();
        Item var41 = Items.GLOWSTONE;
        if (var23.is(var41)) {
            return;
        }
        if (!(this.evaluate(arg0, var15).consumesAction())) {
            return;
        }
        AutoAnchorStage var43 = AutoAnchorStage.DONE;
        this.autoAnchorStage = var43;
    }

    private void applyStep3(Minecraft arg0) {
        int var48 = 0;
        float var51 = 0.0F;
        float var54 = 0.0F;
        this.applyStep16(arg0);
        int var18 = arg0.player.tickCount;
        int var56 = this.counterValue1;
        if (var18 < var56) {
            return;
        }
        boolean var20 = this.isSafeAimMode();
        Integer var73 = 10;
        float var23 = computeFloatMetric(this.getSetting("Safe Rotation Speed", var73));
        if (var20) {
            var54 = AimAssist.computeFloatMetric2();
        } else {
            var54 = arg0.player.getYRot();
        }
        float var9 = var54;
        if (var20) {
            var51 = AimAssist.computeFloatMetric3();
        } else {
            var51 = arg0.player.getXRot();
        }
        float var10 = var51;
        float var75 = this.floatValue2;
        float var27 = this.computeFloatMetric(var9, var75, var23);
        float var77 = this.floatValue3;
        float var29 = this.computeFloatMetric(var10, var77, var23);
        this.floatValue6 = var27;
        this.floatValue7 = var29;
        this.applyStep(arg0, var27, var29, var20);
        float var64 = this.floatValue2;
        float var80 = this.floatValue3;
        boolean var34 = this.isEligible(var64, var80, var27, var29);
        BlockHitResult var36 = this.findBlockHitResult1(arg0);
        if (var36 != null) {
            BlockPos var39 = var36.getBlockPos();
            Direction var67 = var36.getDirection();
            BlockPos var40 = var39.relative(var67);
            BlockPos var69 = this.targetBlockPos1;
            if (!var40.equals(var69)) {
                BlockPos var43 = var36.getBlockPos();
                BlockPos var71 = this.targetBlockPos1;
                if (var43.equals(var71)) {
                }
            }
            var48 = 1;
        } else {
            var48 = 0;
        }
        int var15 = var48;
        if (var34) {
            if (var15 != 0) {
                AutoAnchorStage var72 = AutoAnchorStage.SAFE_PLACE_GLOWSTONE;
                this.autoAnchorStage = var72;
            }
            return;
        }
    }

    private void applyStep4(Minecraft arg0) {
        this.applyStep16(arg0);
        boolean var11 = this.isSafeAimMode();
        float var56 = this.floatValue2;
        float var59 = this.floatValue3;
        this.applyStep(arg0, var56, var59, var11);
        BlockHitResult var14 = this.findBlockHitResult1(arg0);
        if (var14 != null) {
            BlockPos var17 = var14.getBlockPos();
            Direction var43 = var14.getDirection();
            BlockPos var18 = var17.relative(var43);
            BlockPos var45 = this.targetBlockPos1;
            if (!var18.equals(var45)) {
                BlockPos var21 = var14.getBlockPos();
                BlockPos var47 = this.targetBlockPos1;
                if (!var21.equals(var47)) {
                }
            }
        }
        AutoAnchorStage var55 = AutoAnchorStage.SAFE_ROTATE_TO_SIDE;
        this.autoAnchorStage = var55;
        return;
        ItemStack var25 = arg0.player.getMainHandItem();
        Item var48 = Items.GLOWSTONE;
        if (!var25.is(var48)) {
            int var33 = this.computeColorOrIndex1(arg0);
            if (var33 == -1) {
                this.applyStep18(arg0);
                this.applyStep15();
                this.applyStep17();
                return;
            }
            this.setRightClickDelay1(arg0, var33);
            return;
        }
        if (!(this.evaluate(arg0, var14).consumesAction())) {
            return;
        }
        AutoAnchorStage var50 = AutoAnchorStage.SAFE_ROTATE_TO_ANCHOR;
        this.autoAnchorStage = var50;
    }

    private void applyStep5(Minecraft arg0) {
        float var38 = 0.0F;
        float var57 = 0.0F;
        int var58 = 0;
        this.applyStep16(arg0);
        if (this.targetBlockPos != null) {
            BlockPos var79 = this.targetBlockPos;
            Direction var19 = this.evaluate(arg0, var79);
            Vec3 var22 = arg0.player.getEyePosition();
            Vec3 var62 = evaluate(this.targetBlockPos, var19);
            float[] var24 = calculateRotationsToVec(var22, var62);
            float var64 = var24[0];
            this.floatValue4 = var64;
            float var66 = var24[1];
            this.floatValue5 = var66;
        }
        boolean var28 = this.isSafeAimMode();
        Integer var84 = 10;
        float var31 = computeFloatMetric(this.getSetting("Safe Rotation Speed", var84));
        if (var28) {
            var57 = AimAssist.computeFloatMetric2();
        } else {
            var57 = arg0.player.getYRot();
        }
        float var98 = var57;
        if (var28) {
            var38 = AimAssist.computeFloatMetric3();
        } else {
            var38 = arg0.player.getXRot();
        }
        float var10 = var38;
        float var86 = this.floatValue4;
        float var40 = this.computeFloatMetric(var98, var86, var31);
        float var88 = this.floatValue5;
        float var42 = this.computeFloatMetric(var10, var88, var31);
        this.floatValue6 = var40;
        this.floatValue7 = var42;
        this.applyStep(arg0, var40, var42, var28);
        float var74 = this.floatValue4;
        float var91 = this.floatValue5;
        boolean var47 = this.isEligible(var74, var91, var40, var42);
        BlockHitResult var49 = this.findBlockHitResult1(arg0);
        if (var49 != null) {
            BlockPos var52 = var49.getBlockPos();
            BlockPos var77 = this.targetBlockPos;
            if (var52.equals(var77)) {
                var58 = 1;
            } else {
                var58 = 0;
            }
            int var15 = var58;
            if (var47) {
                if (var15 != 0) {
                    AutoAnchorStage var78 = AutoAnchorStage.SAFE_EXPLODE;
                    this.autoAnchorStage = var78;
                }
                return;
            }
        }
    }

    private void applyStep6(Minecraft arg0) {
        this.applyStep16(arg0);
        if (this.targetBlockPos != null) {
            BlockPos var72 = this.targetBlockPos;
            Direction var13 = this.evaluate(arg0, var72);
            Vec3 var16 = arg0.player.getEyePosition();
            Vec3 var55 = evaluate(this.targetBlockPos, var13);
            float[] var18 = calculateRotationsToVec(var16, var55);
            float var57 = var18[0];
            this.floatValue4 = var57;
            float var59 = var18[1];
            this.floatValue5 = var59;
        }
        boolean var22 = this.isSafeAimMode();
        float var77 = this.floatValue4;
        float var81 = this.floatValue5;
        this.applyStep(arg0, var77, var81, var22);
        if (this.targetBlockPos == null) {
            this.applyStep18(arg0);
            this.applyStep15();
            this.applyStep17();
            return;
        }
        BlockPos var79 = this.targetBlockPos;
        if (!(this.isEligible2(arg0, var79))) {
            this.applyStep18(arg0);
            this.applyStep15();
            this.applyStep17();
            return;
        }
        BlockHitResult var32 = this.findBlockHitResult1(arg0);
        if (var32 != null) {
            BlockPos var35 = var32.getBlockPos();
            BlockPos var65 = this.targetBlockPos;
            if (!var35.equals(var65)) {
            }
        }
        AutoAnchorStage var66 = AutoAnchorStage.SAFE_ROTATE_TO_ANCHOR;
        this.autoAnchorStage = var66;
        return;
        this.applyStep18(arg0);
        ItemStack var41 = arg0.player.getMainHandItem();
        Item var68 = Items.GLOWSTONE;
        if (var41.is(var68)) {
            return;
        }
        if (!(this.evaluate(arg0, var32).consumesAction())) {
            return;
        }
        this.applyStep15();
        AutoAnchorStage var70 = AutoAnchorStage.DONE;
        this.autoAnchorStage = var70;
    }

    private void applyStep7(Minecraft arg0) {
        int var12 = 0;
        String var35 = ((String) this.getSetting("Silent Bind", "None"));
        boolean var8 = isEligible(arg0, var35);
        if (var8) {
            if (!this.enabledState) {
                var12 = 1;
            } else {
                var12 = 0;
            }
            int var7 = var12;
            this.enabledState = var8;
            AutoAnchorStage var15 = this.autoAnchorStage;
            AutoAnchorStage var37 = AutoAnchorStage.IDLE;
            if (var15 == var37) {
                if (var7 != 0) {
                    if (!this.isEligible(arg0)) {
                        this.enabledState = false;
                        return;
                    }
                }
            }
            switch (this.autoAnchorStage.ordinal()) {
                case 6 -> {}
                case 7 -> {}
                case 8 -> {}
                case 9 -> {}
                case 10 -> {}
                case 11 -> {}
                case 12 -> {}
                case 13 -> {}
                default -> {}
            }
            this.applyStep15();
            this.applyStep17();
            if (var8) {
                this.enabledState = false;
                this.applyStep12(arg0);
                this.applyStep11(arg0);
                this.applyStep13(arg0);
                this.applyStep9(arg0);
                this.applyStep10(arg0);
                this.applyStep8(arg0);
                this.applyStep14(arg0);
            }
            return;
        }
    }

    private boolean isEligible(Minecraft arg0) {
        Player var13 = this.evaluate(arg0);
        if (var13 == null) {
            return false;
        }
        BlockPos var17 = this.evaluate(arg0, var13);
        if (var17 == null) {
            return false;
        }
        this.targetBlockPos = var17;
        Vec3 var22 = arg0.player.getEyePosition();
        Direction var24 = this.evaluate(arg0, var17);
        Vec3 var26 = evaluate(var17, var24);
        float[] var28 = calculateRotationsToVec(var22, var26);
        float var46 = var28[0];
        this.floatValue4 = var46;
        float var48 = var28[1];
        this.floatValue5 = var48;
        float var32 = this.floatValue4;
        float var50 = this.floatValue5;
        AimAssist.applyStep(var32, var50);
        Integer var61 = 0;
        int var35 = computeColorOrIndex(this.getSetting("Explode Delay", var61));
        int var54 = arg0.player.tickCount;
        int var63 = Math.max(0, var35);
        int var55 = (var54 + var63);
        this.counterValue1 = var55;
        AutoAnchorStage var56 = AutoAnchorStage.SILENT_PLACE_ANCHOR;
        this.autoAnchorStage = var56;
        return true;
    }

    private void applyStep8(Minecraft arg0) {
        int var19 = 0;
        int var22 = 0;
        if (this.targetBlockPos == null) {
            this.applyStep18(arg0);
            this.applyStep17();
            return;
        }
        BlockPos var165 = this.targetBlockPos;
        if (this.isEligible2(arg0, var165)) {
            AutoAnchorStage var163 = AutoAnchorStage.SILENT_CHARGE;
            this.autoAnchorStage = var163;
            return;
        }
        int var36 = this.computeColorOrIndex(arg0);
        if (var36 == -1) {
            this.applyStep18(arg0);
            this.applyStep17();
            return;
        }
        this.setRightClickDelay1(arg0, var36);
        ItemStack var41 = arg0.player.getMainHandItem();
        Item var133 = Items.RESPAWN_ANCHOR;
        if (!var41.is(var133)) {
            return;
        }
        Vec3 var45 = arg0.player.getEyePosition();
        var19 = null;
        Direction[] var50 = new Direction[6];
        Direction var5 = Direction.DOWN;
        var50[0] = var5;
        Direction var199 = Direction.NORTH;
        var50[1] = var199;
        Direction var200 = Direction.SOUTH;
        var50[2] = var200;
        Direction var201 = Direction.WEST;
        var50[3] = var201;
        Direction var202 = Direction.EAST;
        var50[4] = var202;
        Direction var203 = Direction.UP;
        var50[5] = var203;
        int var52 = var50.length;
        var22 = 0;
        while (true) {
            if (var22 >= var52) break;
            int var135 = var22;
            Direction var56 = var50[var135];
            BlockPos var59 = this.targetBlockPos.relative(var56);
            BlockState var62 = arg0.level.getBlockState(var59);
            if (!var62.isSolid()) {
            } else {
                Block var138 = Blocks.BEDROCK;
                if (var62.is(var138)) {
                } else {
                    Direction var68 = var56.getOpposite();
                    double var177 = (((double) var59.getX()) + 0.5);
                    double var210 = (((double) var68.getStepX()) * 0.5);
                    double var179 = (var177 + var210);
                    double var216 = (((double) var59.getY()) + 0.5);
                    double var243 = (((double) var68.getStepY()) * 0.5);
                    double var218 = (var216 + var243);
                    double var249 = (((double) var59.getZ()) + 0.5);
                    double var277 = (((double) var68.getStepZ()) * 0.5);
                    double var251 = (var249 + var277);
                    Vec3 var69 = new Vec3(var179, var218, var251);
                    if (Double.compare(var69.distanceToSqr(var45), 20.25) <= 0) {
                        var19 = var56;
                    } else {
                    }
                }
                var22 = (var22 + 1);
            }
        }
        if (var19 == null) {
            this.applyStep18(arg0);
            this.applyStep17();
            return;
        }
        BlockPos var77 = this.targetBlockPos;
        Object var140 = var19;
        BlockPos var78 = var77.relative(var140);
        Direction var80 = var19.getOpposite();
        double var187 = (((double) var78.getX()) + 0.5);
        double var226 = (((double) var80.getStepX()) * 0.5);
        double var189 = (var187 + var226);
        double var232 = (((double) var78.getY()) + 0.5);
        double var261 = (((double) var80.getStepY()) * 0.5);
        double var234 = (var232 + var261);
        double var267 = (((double) var78.getZ()) + 0.5);
        double var287 = (((double) var80.getStepZ()) * 0.5);
        double var269 = (var267 + var287);
        Vec3 var81 = new Vec3(var189, var234, var269);
        BlockHitResult var82 = new BlockHitResult(var81, var80, var78, false);
        float var85 = arg0.player.getYRot();
        float var88 = arg0.player.getXRot();
        float[] var92 = calculateRotationsToVec(arg0.player.getEyePosition(), var81);
        LocalPlayer var94 = arg0.player;
        float var143 = var92[0];
        var94.setYRot(var143);
        LocalPlayer var96 = arg0.player;
        float var145 = var92[1];
        var96.setXRot(var145);
        MultiPlayerGameMode var98 = arg0.gameMode;
        LocalPlayer var147 = arg0.player;
        InteractionHand var194 = InteractionHand.MAIN_HAND;
        InteractionResult var99 = var98.useItemOn(var147, var194, var82);
        arg0.player.setYRot(var85);
        arg0.player.setXRot(var88);
        if (!var99.consumesAction()) {
            return;
        }
        LocalPlayer var107 = arg0.player;
        InteractionHand var150 = InteractionHand.MAIN_HAND;
        var107.swing(var150);
        Vec3 var110 = arg0.player.getEyePosition();
        BlockPos var196 = this.targetBlockPos;
        Direction var112 = this.evaluate(arg0, var196);
        Vec3 var115 = evaluate(this.targetBlockPos, var112);
        float[] var117 = calculateRotationsToVec(var110, var115);
        float var155 = var117[0];
        this.floatValue4 = var155;
        float var157 = var117[1];
        this.floatValue5 = var157;
        float var121 = this.floatValue4;
        float var159 = this.floatValue5;
        AimAssist.applyStep(var121, var159);
        AutoAnchorStage var160 = AutoAnchorStage.SILENT_CHARGE;
        this.autoAnchorStage = var160;
    }

    private int computeColorOrIndex(Minecraft arg0) {
        int var4 = 0;
        var4 = 0;
        while (true) {
            if (var4 >= 9) break;
            Inventory var8 = arg0.player.getInventory();
            int var13 = var4;
            ItemStack var9 = var8.getItem(var13);
            Item var14 = Items.RESPAWN_ANCHOR;
            if (var9.is(var14)) {
                return var4;
            }
            var4 = (var4 + 1);
        }
        return -1;
    }

    private void applyStep9(Minecraft arg0) {
        if (this.targetBlockPos == null) {
            this.applyStep18(arg0);
            this.applyStep17();
            return;
        }
        BlockPos var90 = this.targetBlockPos;
        if (!(this.isEligible2(arg0, var90))) {
            this.applyStep18(arg0);
            this.applyStep17();
            return;
        }
        BlockPos var92 = this.targetBlockPos;
        if (this.computeColorOrIndex(arg0, var92) != 0) {
            String var52 = ((String) this.getSetting("Anchor Mode", "Normal"));
            if ("Safe".equals(var52)) {
                BlockPos var104 = this.targetBlockPos;
                if (!(this.isEligible1(arg0, var104))) {
                    BlockPos var106 = this.targetBlockPos;
                    if (!(this.isEligible(arg0, var106))) {
                    }
                }
                AutoAnchorStage var88 = AutoAnchorStage.SILENT_EXPLODE_WAIT;
                this.autoAnchorStage = var88;
            }
        } else {
            int var18 = this.computeColorOrIndex1(arg0);
            if (var18 == -1) {
                this.applyStep18(arg0);
                this.applyStep17();
                return;
            }
            this.setRightClickDelay1(arg0, var18);
            ItemStack var25 = arg0.player.getMainHandItem();
            Item var70 = Items.GLOWSTONE;
            if (!var25.is(var70)) {
                return;
            }
            BlockPos var95 = this.targetBlockPos;
            Direction var28 = this.evaluate(arg0, var95);
            BlockHitResult var31 = findBlockHitResult(this.targetBlockPos, var28);
            if (!(this.evaluate(arg0, var31).consumesAction())) {
                this.applyStep18(arg0);
                this.applyStep17();
                return;
            }
            String var40 = ((String) this.getSetting("Anchor Mode", "Normal"));
            if ("Safe".equals(var40)) {
                BlockPos var99 = this.targetBlockPos;
                if (!(this.isEligible1(arg0, var99))) {
                    BlockPos var101 = this.targetBlockPos;
                    if (!(this.isEligible(arg0, var101))) {
                    }
                }
                AutoAnchorStage var79 = AutoAnchorStage.SILENT_EXPLODE_WAIT;
                this.autoAnchorStage = var79;
                AutoAnchorStage var80 = AutoAnchorStage.SILENT_SAFE_ROTATE_TO_SIDE;
                this.autoAnchorStage = var80;
            } else {
                AutoAnchorStage var81 = AutoAnchorStage.SILENT_EXPLODE_WAIT;
                this.autoAnchorStage = var81;
            }
            return;
            AutoAnchorStage var87 = AutoAnchorStage.SILENT_SAFE_ROTATE_TO_SIDE;
            this.autoAnchorStage = var87;
            AutoAnchorStage var84 = AutoAnchorStage.SILENT_EXPLODE_WAIT;
            this.autoAnchorStage = var84;
        }
    }

    private void applyStep10(Minecraft arg0) {
        if (this.targetBlockPos != null) {
            BlockPos var82 = this.targetBlockPos;
            Direction var10 = this.evaluate(arg0, var82);
            Vec3 var13 = arg0.player.getEyePosition();
            Vec3 var57 = evaluate(this.targetBlockPos, var10);
            float[] var15 = calculateRotationsToVec(var13, var57);
            float var59 = var15[0];
            this.floatValue4 = var59;
            float var61 = var15[1];
            this.floatValue5 = var61;
            float var19 = this.floatValue4;
            float var63 = this.floatValue5;
            AimAssist.applyStep(var19, var63);
        }
        int var22 = arg0.player.tickCount;
        int var65 = this.counterValue1;
        if (var22 < var65) {
            return;
        }
        if (this.targetBlockPos == null) {
            this.applyStep18(arg0);
            this.applyStep17();
            return;
        }
        BlockPos var87 = this.targetBlockPos;
        if (!(this.isEligible2(arg0, var87))) {
            this.applyStep18(arg0);
            this.applyStep17();
            return;
        }
        if (this.counterValue2 < 0) {
            int var72 = (arg0.player.tickCount + 10);
            this.counterValue2 = var72;
        }
        int var36 = arg0.player.tickCount;
        int var74 = this.counterValue2;
        if (var36 > var74) {
            this.applyStep18(arg0);
            this.applyStep15();
            this.applyStep17();
            return;
        }
        this.applyStep18(arg0);
        ItemStack var43 = arg0.player.getMainHandItem();
        Item var77 = Items.GLOWSTONE;
        if (var43.is(var77)) {
            return;
        }
        BlockPos var90 = this.targetBlockPos;
        Direction var46 = this.evaluate(arg0, var90);
        BlockHitResult var49 = findBlockHitResult(this.targetBlockPos, var46);
        if (!(this.evaluate(arg0, var49).consumesAction())) {
            return;
        }
        AutoAnchorStage var81 = AutoAnchorStage.DONE;
        this.autoAnchorStage = var81;
    }

    private void applyStep11(Minecraft arg0) {
        int var13 = arg0.player.tickCount;
        int var27 = this.counterValue1;
        if (var13 < var27) {
            return;
        }
        Integer var35 = 10;
        float var16 = computeFloatMetric(this.getSetting("Safe Rotation Speed", var35));
        float var17 = AimAssist.computeFloatMetric2();
        float var18 = AimAssist.computeFloatMetric3();
        float var37 = this.floatValue2;
        float var20 = this.computeFloatMetric(var17, var37, var16);
        float var39 = this.floatValue3;
        float var22 = this.computeFloatMetric(var18, var39, var16);
        AimAssist.applyStep(var20, var22);
        float var33 = this.floatValue2;
        float var41 = this.floatValue3;
        if (this.isEligible(var33, var41, var20, var22)) {
            AutoAnchorStage var34 = AutoAnchorStage.SILENT_SAFE_PLACE_GLOWSTONE;
            this.autoAnchorStage = var34;
        }
    }

    private void applyStep12(Minecraft arg0) {
        float var7 = this.floatValue2;
        float var27 = this.floatValue3;
        AimAssist.applyStep(var7, var27);
        ItemStack var10 = arg0.player.getMainHandItem();
        Item var28 = Items.GLOWSTONE;
        if (!var10.is(var28)) {
            int var21 = this.computeColorOrIndex1(arg0);
            if (var21 == -1) {
                this.applyStep18(arg0);
                this.applyStep15();
                this.applyStep17();
                return;
            }
        }
        BlockHitResult var13 = this.findBlockHitResult(arg0);
        if (var13 == null) {
            return;
        }
        if (!(this.evaluate(arg0, var13).consumesAction())) {
            return;
        }
        AutoAnchorStage var31 = AutoAnchorStage.SILENT_SAFE_ROTATE_TO_ANCHOR;
        this.autoAnchorStage = var31;
        return;
        this.setRightClickDelay1(arg0, var21);
    }

    private void applyStep13(Minecraft arg0) {
        if (this.targetBlockPos != null) {
            BlockPos var49 = this.targetBlockPos;
            Direction var14 = this.evaluate(arg0, var49);
            Vec3 var17 = arg0.player.getEyePosition();
            Vec3 var37 = evaluate(this.targetBlockPos, var14);
            float[] var19 = calculateRotationsToVec(var17, var37);
            float var39 = var19[0];
            this.floatValue4 = var39;
            float var41 = var19[1];
            this.floatValue5 = var41;
        }
        Integer var54 = 10;
        float var24 = computeFloatMetric(this.getSetting("Safe Rotation Speed", var54));
        float var25 = AimAssist.computeFloatMetric2();
        float var26 = AimAssist.computeFloatMetric3();
        float var56 = this.floatValue4;
        float var28 = this.computeFloatMetric(var25, var56, var24);
        float var58 = this.floatValue5;
        float var30 = this.computeFloatMetric(var26, var58, var24);
        AimAssist.applyStep(var28, var30);
        float var47 = this.floatValue4;
        float var60 = this.floatValue5;
        if (this.isEligible(var47, var60, var28, var30)) {
            AutoAnchorStage var48 = AutoAnchorStage.SILENT_SAFE_EXPLODE;
            this.autoAnchorStage = var48;
        }
    }

    private void applyStep14(Minecraft arg0) {
        if (this.targetBlockPos != null) {
            BlockPos var80 = this.targetBlockPos;
            Direction var10 = this.evaluate(arg0, var80);
            Vec3 var13 = arg0.player.getEyePosition();
            Vec3 var57 = evaluate(this.targetBlockPos, var10);
            float[] var15 = calculateRotationsToVec(var13, var57);
            float var59 = var15[0];
            this.floatValue4 = var59;
            float var61 = var15[1];
            this.floatValue5 = var61;
        }
        float var19 = this.floatValue4;
        float var63 = this.floatValue5;
        AimAssist.applyStep(var19, var63);
        if (this.targetBlockPos == null) {
            this.applyStep18(arg0);
            this.applyStep15();
            this.applyStep17();
            return;
        }
        BlockPos var85 = this.targetBlockPos;
        if (!(this.isEligible2(arg0, var85))) {
            this.applyStep18(arg0);
            this.applyStep15();
            this.applyStep17();
            return;
        }
        if (this.counterValue2 < 0) {
            int var68 = (arg0.player.tickCount + 10);
            this.counterValue2 = var68;
        }
        int var29 = arg0.player.tickCount;
        int var70 = this.counterValue2;
        if (var29 > var70) {
            this.applyStep18(arg0);
            this.applyStep15();
            this.applyStep17();
            return;
        }
        this.applyStep18(arg0);
        ItemStack var36 = arg0.player.getMainHandItem();
        Item var73 = Items.GLOWSTONE;
        if (var36.is(var73)) {
            return;
        }
        BlockPos var88 = this.targetBlockPos;
        Direction var39 = this.evaluate(arg0, var88);
        BlockHitResult var42 = findBlockHitResult(this.targetBlockPos, var39);
        if (!(this.evaluate(arg0, var42).consumesAction())) {
            return;
        }
        this.applyStep15();
        AutoAnchorStage var77 = AutoAnchorStage.DONE;
        this.autoAnchorStage = var77;
    }

    private Player evaluate(Minecraft arg0) {
        Player var7 = null;
        double var8 = 0.0;
        List var15 = arg0.level.players();
        var7 = null;
        var8 = 1.7976931348623157E308;
        Iterator var20 = var15.iterator();
        while (true) {
            if (var20.hasNext()) {
                Player var25 = ((Player) var20.next());
                LocalPlayer var41 = arg0.player;
                if (var25 == var41) {
                }
            }
            return var7;
            double var29 = arg0.player.distanceToSqr(var25);
            double var4 = var8;
            if (Double.compare(var29, var4) >= 0) continue;
            if (Double.compare(var29, 64.0) >= 0) continue;
            var8 = var29;
            var7 = var25;
        }
    }

    private BlockPos evaluate(Minecraft arg0, LivingEntity arg1) {
        BlockPos var24 = null;
        double var25 = 0.0;
        int var27 = 0;
        int var28 = 0;
        int var29 = 0;
        int var33 = 0;
        int var36 = 0;
        Vec3 var45 = arg0.player.getEyePosition();
        BlockPos var50 = arg0.player.blockPosition();
        var24 = null;
        var25 = -1.0;
        var27 = -(5);
        while (true) {
            if (var27 <= 5) {
                var28 = -(5);
                while (true) {
                    if (var28 > 5) break;
                    var29 = -(5);
                    while (true) {
                        if (var29 <= 5) {
                            int var149 = var27;
                            int var5 = var28;
                            int var6 = var29;
                            BlockPos var65 = var50.offset(var149, var5, var6);
                            if (!arg0.level.getBlockState(var65).isAir()) {
                                if (!arg0.level.getBlockState(var65).canBeReplaced()) {
                                }
                            }
                        } else {
                            var28 = (var28 + 1);
                        }
                    }
                }
                return var24;
                var27 = (var27 + 1);
            }
        }
        AABB var74 = new AABB(var65);
        ClientLevel var76 = arg0.level;
        Predicate var203 = (p0) -> AutoAnchor.lambda$a(p0);
        if (!(var76.getEntitiesOfClass(LivingEntity.class, var74, var203).isEmpty())) {
        } else {
            Vec3 var80 = Vec3.atCenterOf(var65);
            if (Double.compare(var80.distanceToSqr(var45), 20.25) > 0) {
            } else {
                var33 = 0;
                Direction[] var87 = new Direction[6];
                Direction var204 = Direction.DOWN;
                var87[0] = var204;
                Direction var205 = Direction.NORTH;
                var87[1] = var205;
                Direction var206 = Direction.SOUTH;
                var87[2] = var206;
                Direction var207 = Direction.WEST;
                var87[3] = var207;
                Direction var208 = Direction.EAST;
                var87[4] = var208;
                Direction var209 = Direction.UP;
                var87[5] = var209;
                int var89 = var87.length;
                var36 = 0;
                while (true) {
                    if (var36 >= var89) break;
                    int var155 = var36;
                    Direction var93 = var87[var155];
                    BlockPos var95 = var65.relative(var93);
                    BlockState var98 = arg0.level.getBlockState(var95);
                    if (!var98.isSolid()) {
                    } else {
                        Block var158 = Blocks.BEDROCK;
                        if (var98.is(var158)) {
                        } else {
                            Direction var104 = var93.getOpposite();
                            double var178 = (((double) var95.getX()) + 0.5);
                            double var218 = (((double) var104.getStepX()) * 0.5);
                            double var180 = (var178 + var218);
                            double var224 = (((double) var95.getY()) + 0.5);
                            double var244 = (((double) var104.getStepY()) * 0.5);
                            double var226 = (var224 + var244);
                            double var250 = (((double) var95.getZ()) + 0.5);
                            double var270 = (((double) var104.getStepZ()) * 0.5);
                            double var252 = (var250 + var270);
                            Vec3 var105 = new Vec3(var180, var226, var252);
                            if (Double.compare(var105.distanceToSqr(var45), 20.25) <= 0) {
                                var33 = 1;
                            }
                        }
                    }
                    var36 = (var36 + 1);
                }
                if (var33 == 0) {
                } else {
                    Vec3 var161 = arg1.getEyePosition();
                    double var113 = var80.distanceTo(var161);
                    if (Double.compare(var113, 10.0) >= 0) {
                    } else {
                        double var211 = arg1.getX();
                        double var234 = arg1.getY();
                        double var259 = (((double) arg1.getBbHeight()) * 0.5);
                        double var236 = (var234 + var259);
                        double var262 = arg1.getZ();
                        Vec3 var162 = new Vec3(var211, var236, var262);
                        double var119 = var80.distanceTo(var162);
                        if (Double.compare(var119, 3.0) > 0) {
                        } else {
                            double var125 = this.computeDoubleMetric(arg0, var80, arg1);
                            if (Double.compare(var125, 0.0) <= 0) {
                            } else {
                                double var230 = (var113 / 10.0);
                                double var193 = (1.0 - var230);
                                double var134 = ((5.0 * var193) * var125);
                                double var199 = Math.max(0.5, var119);
                                double var138 = (var134 / var199);
                                double var201 = var25;
                                if (Double.compare(var138, var201) > 0) {
                                    var25 = var138;
                                    var24 = var65;
                                }
                                var29 = (var29 + 1);
                            }
                        }
                    }
                }
            }
        }
    }

    private double computeDoubleMetric(Minecraft arg0, Vec3 arg1, LivingEntity arg2) {
        double var73 = 0.0;
        int var14 = 0;
        int var15 = 0;
        int var18 = 0;
        int var23 = 0;
        int var28 = 0;
        AABB var33 = arg2.getBoundingBox();
        var14 = 0;
        var15 = 0;
        double[] var37 = new double[2];
        double var93 = var33.minX;
        var37[0] = var93;
        double var96 = var33.maxX;
        var37[1] = var96;
        int var39 = var37.length;
        var18 = 0;
        while (true) {
            if (var18 >= var39) break;
            int var76 = var18;
            double var43 = var37[var76];
            double[] var46 = new double[2];
            double var99 = var33.minY;
            var46[0] = var99;
            double var102 = var33.maxY;
            var46[1] = var102;
            int var48 = var46.length;
            var23 = 0;
            while (true) {
                if (var23 >= var48) break;
                int var78 = var23;
                double var52 = var46[var78];
                double[] var55 = new double[2];
                double var105 = var33.minZ;
                var55[0] = var105;
                double var108 = var33.maxZ;
                var55[1] = var108;
                int var57 = var55.length;
                var28 = 0;
                while (true) {
                    if (var28 >= var57) break;
                    int var80 = var28;
                    double var61 = var55[var80];
                    Vec3 var63 = new Vec3(var43, var52, var61);
                    ClientLevel var65 = arg0.level;
                    ClipContext.Block var9 = ClipContext.Block.COLLIDER;
                    ClipContext.Fluid var112 = ClipContext.Fluid.NONE;
                    ClipContext var81 = new ClipContext(arg1, var63, var9, var112, arg2);
                    HitResult.Type var68 = var65.clip(var81).getType();
                    HitResult.Type var82 = HitResult.Type.MISS;
                    if (var68 == var82) {
                        var14 = (var14 + 1);
                    }
                    var15 = (var15 + 1);
                    var28 = (var28 + 1);
                }
                var23 = (var23 + 1);
            }
            var18 = (var18 + 1);
        }
        if (var15 == 0) {
            var73 = 0.0;
        } else {
            double var71 = ((double) var14);
            double var91 = ((double) var15);
            var73 = (var71 / var91);
        }
        return var73;
    }

    private BlockHitResult findBlockHitResult(Minecraft arg0) {
        int var15 = 0;
        if (this.targetBlockPos1 == null) {
            return null;
        }
        HitResult var21 = arg0.hitResult;
        if ((var21 instanceof BlockHitResult)) {
            BlockHitResult var25 = ((BlockHitResult) var21);
            BlockPos var27 = var25.getBlockPos();
            Direction var52 = var25.getDirection();
            BlockPos var28 = var27.relative(var52);
            BlockPos var54 = this.targetBlockPos1;
            if (!var28.equals(var54)) {
                BlockPos var31 = var25.getBlockPos();
                BlockPos var56 = this.targetBlockPos1;
                if (var31.equals(var56)) {
                }
            }
            return var25;
        }
        Direction[] var33 = Direction.values();
        int var35 = var33.length;
        var15 = 0;
        while (true) {
            if (var15 >= var35) break;
            int var58 = var15;
            Direction var39 = var33[var58];
            BlockPos var42 = this.targetBlockPos1.relative(var39);
            BlockPos var61 = this.targetBlockPos;
            if (var42.equals(var61)) {
            } else {
                if (arg0.level.getBlockState(var42).isSolid()) {
                    Vec3 var63 = Vec3.atCenterOf(var42);
                    double var69 = (((double) var39.getOpposite().getStepX()) * 0.5);
                    double var78 = (((double) var39.getOpposite().getStepY()) * 0.5);
                    double var86 = (((double) var39.getOpposite().getStepZ()) * 0.5);
                    Vec3 var64 = var63.add(var69, var78, var86);
                    Direction var72 = var39.getOpposite();
                    BlockHitResult var49 = new BlockHitResult(var64, var72, var42, false);
                    return var49;
                }
            }
            var15 = (var15 + 1);
        }
        return null;
    }

    private static BlockHitResult findBlockHitResult(BlockPos arg0, Direction arg1) {
        Vec3 var13 = Vec3.atCenterOf(arg0);
        double var19 = (((double) arg1.getStepX()) * 0.5);
        double var25 = (((double) arg1.getStepY()) * 0.5);
        double var32 = (((double) arg1.getStepZ()) * 0.5);
        Vec3 var14 = var13.add(var19, var25, var32);
        BlockHitResult var15 = new BlockHitResult(var14, arg1, arg0, false);
        return var15;
    }

    private boolean isEligible(Minecraft arg0, BlockPos arg1) {
        BlockPos var19 = null;
        float var20 = 0.0F;
        int var21 = 0;
        int var22 = 0;
        int var23 = 0;
        Vec3 var25 = null;
        float var26 = 0.0F;
        int var29 = 0;
        Vec3 var42 = arg0.player.getEyePosition();
        Vec3 var44 = Vec3.atCenterOf(arg1);
        Direction var46 = this.evaluate(arg0, arg1);
        Vec3 var48 = evaluate(arg1, var46);
        float[] var50 = calculateRotationsToVec(var42, var48);
        float var163 = var50[0];
        this.floatValue4 = var163;
        float var165 = var50[1];
        this.floatValue5 = var165;
        float var55 = arg0.player.getYRot();
        float var58 = arg0.player.getXRot();
        AABB var61 = arg0.player.getBoundingBox();
        var19 = null;
        var20 = 3.4028235E38F;
        var21 = -1;
        while (true) {
            if (var21 > 1) break;
            var22 = -1;
            while (true) {
                if (var22 <= 1) {
                    var23 = -1;
                    while (true) {
                        if (var23 <= 1) {
                            if (var21 == 0) {
                                if (var22 == 0) {
                                    if (var23 == 0) {
                                    }
                                }
                            }
                        }
                    }
                } else {
                    var21 = (var21 + 1);
                }
            }
        }
        int var170 = var21;
        int var204 = var22;
        int var6 = var23;
        BlockPos var78 = arg1.offset(var170, var204, var6);
        if (!arg0.level.getBlockState(var78).isAir()) {
            if (!arg0.level.getBlockState(var78).canBeReplaced()) {
            } else {
            }
        }
        AABB var173 = new AABB(var78);
        if (var61.intersects(var173)) {
        } else {
            if (!(isEligible(var44, var42, var78))) {
            } else {
                var25 = null;
                var26 = 3.4028235E38F;
                Direction[] var93 = Direction.values();
                int var95 = var93.length;
                var29 = 0;
                while (true) {
                    if (var29 >= var95) break;
                    int var189 = var29;
                    Direction var114 = var93[var189];
                    BlockPos var116 = var78.relative(var114);
                    if (var116.equals(arg1)) {
                    } else {
                        if (!arg0.level.getBlockState(var116).isSolid()) {
                        } else {
                            double var127 = (((double) var78.getX()) + 0.5);
                            double var216 = (((double) var114.getStepX()) * 0.5);
                            double var129 = (var127 + var216);
                            double var135 = (((double) var78.getY()) + 0.5);
                            double var224 = (((double) var114.getStepY()) * 0.5);
                            double var137 = (var135 + var224);
                            double var143 = (((double) var78.getZ()) + 0.5);
                            double var232 = (((double) var114.getStepZ()) * 0.5);
                            double var145 = (var143 + var232);
                            Vec3 var147 = new Vec3(var129, var137, var145);
                            float[] var149 = calculateRotationsToVec(var42, var147);
                            float var154 = Math.abs(computeFloatMetric((var149[0] - var55)));
                            float var200 = Math.abs(computeFloatMetric((var149[1] - var58)));
                            float var155 = (var154 + var200);
                            float var201 = var26;
                            if (Float.compare(var155, var201) < 0) {
                                var26 = var155;
                                var25 = var147;
                            }
                            var29 = (var29 + 1);
                        }
                    }
                }
                if (var25 == null) {
                    if (var19 == null) {
                        return false;
                    }
                    BlockPos var167 = var19;
                    this.targetBlockPos1 = var167;
                    return true;
                    var22 = (var22 + 1);
                }
                Vec3 var176 = var25;
                float[] var100 = calculateRotationsToVec(var42, var176);
                float var105 = Math.abs(computeFloatMetric((var100[0] - var55)));
                float var183 = Math.abs(computeFloatMetric((var100[1] - var58)));
                float var106 = (var105 + var183);
                float var184 = var20;
                if (Float.compare(var106, var184) < 0) {
                    var20 = var106;
                    var19 = var78;
                    float var186 = var100[0];
                    this.floatValue2 = var186;
                    float var188 = var100[1];
                    this.floatValue3 = var188;
                }
                var23 = (var23 + 1);
            }
        }
    }

    private static boolean isEligible(Vec3 arg0, Vec3 arg1, BlockPos arg2) {
        double var29 = 0.0;
        double var31 = 0.0;
        double var33 = 0.0;
        double var35 = 0.0;
        double var359 = 0.0;
        double var361 = 0.0;
        double var363 = 0.0;
        double var365 = 0.0;
        double var41 = ((double) arg2.getX());
        double var45 = (var41 + 1.0);
        double var49 = ((double) arg2.getY());
        double var53 = (var49 + 1.0);
        double var57 = ((double) arg2.getZ());
        double var61 = (var57 + 1.0);
        double var64 = arg1.x;
        double var229 = arg0.x;
        double var66 = (var64 - var229);
        double var69 = arg1.y;
        double var232 = arg0.y;
        double var71 = (var69 - var232);
        double var74 = arg1.z;
        double var235 = arg0.z;
        double var76 = (var74 - var235);
        var29 = 0.0;
        var31 = 1.0;
        if (Double.compare(Math.abs(var66), 1.0E-10) < 0) {
            double var88 = arg0.x;
            double var241 = (var41 + 1.0E-4);
            if (Double.compare(var88, var241) > 0) {
                double var92 = arg0.x;
                double var245 = (var45 - 1.0E-4);
                if (Double.compare(var92, var245) >= 0) {
                }
            }
            return false;
        }
        double var312 = arg0.x;
        var361 = ((var41 - var312) / var66);
        double var317 = arg0.x;
        var365 = ((var45 - var317) / var66);
        double var203 = var361;
        double var321 = var365;
        if (Double.compare(var203, var321) > 0) {
            double var206 = var361;
            var361 = var365;
            var365 = var206;
        }
        double var212 = var29;
        double var325 = (var361 + 1.0E-4);
        var29 = Math.max(var212, var325);
        double var216 = var31;
        double var329 = (var365 - 1.0E-4);
        var31 = Math.min(var216, var329);
        double var220 = var29;
        double var331 = var31;
        if (Double.compare(var220, var331) > 0) {
            return false;
        }
        if (Double.compare(Math.abs(var71), 1.0E-10) < 0) {
            double var183 = arg0.y;
            double var305 = (var49 + 1.0E-4);
            if (Double.compare(var183, var305) > 0) {
                double var187 = arg0.y;
                double var309 = (var53 - 1.0E-4);
                if (Double.compare(var187, var309) >= 0) {
                }
            }
            return false;
        }
        double var250 = arg0.y;
        var33 = ((var49 - var250) / var71);
        double var255 = arg0.y;
        var35 = ((var53 - var255) / var71);
        double var113 = var33;
        double var259 = var35;
        if (Double.compare(var113, var259) > 0) {
            double var116 = var33;
            var33 = var35;
            var35 = var116;
        }
        double var122 = var29;
        double var263 = (var33 + 1.0E-4);
        var29 = Math.max(var122, var263);
        double var126 = var31;
        double var267 = (var35 - 1.0E-4);
        var31 = Math.min(var126, var267);
        double var130 = var29;
        double var269 = var31;
        if (Double.compare(var130, var269) > 0) {
            return false;
        }
        if (Double.compare(Math.abs(var76), 1.0E-10) < 0) {
            double var174 = arg0.z;
            double var297 = (var57 + 1.0E-4);
            if (Double.compare(var174, var297) > 0) {
                double var178 = arg0.z;
                double var301 = (var61 - 1.0E-4);
                if (Double.compare(var178, var301) >= 0) {
                }
            }
            return false;
        }
        double var274 = arg0.z;
        var359 = ((var57 - var274) / var76);
        double var279 = arg0.z;
        var363 = ((var61 - var279) / var76);
        double var151 = var359;
        double var283 = var363;
        if (Double.compare(var151, var283) > 0) {
            double var154 = var359;
            var359 = var363;
            var363 = var154;
        }
        double var160 = var29;
        double var287 = (var359 + 1.0E-4);
        double var162 = Math.max(var160, var287);
        double var164 = var31;
        double var291 = (var363 - 1.0E-4);
        double var166 = Math.min(var164, var291);
        if (Double.compare(var162, var166) > 0) {
            return false;
        }
        return true;
    }

    private Direction evaluate(Minecraft arg0, BlockPos arg1) {
        Direction var74 = null;
        int var75 = 0;
        int var14 = 0;
        float var15 = 0.0F;
        int var18 = 0;
        float var26 = arg0.player.getYRot();
        float var29 = arg0.player.getXRot();
        Vec3 var32 = arg0.player.getEyePosition();
        var14 = null;
        var15 = 3.4028235E38F;
        Direction[] var35 = Direction.values();
        int var37 = var35.length;
        var18 = 0;
        while (true) {
            if (var18 < var37) {
                int var76 = var18;
                Direction var41 = var35[var76];
                ClientLevel var43 = arg0.level;
                BlockPos var78 = arg1.relative(var41);
                if (var43.getBlockState(var78).isSolid()) {
                }
            } else {
                if (var14 != null) {
                    var74 = var14;
                } else {
                    var74 = Direction.UP;
                }
                return var74;
                Vec3 var47 = evaluate(arg1, var41);
                ClientLevel var49 = arg0.level;
                ClipContext.Block var8 = ClipContext.Block.COLLIDER;
                ClipContext.Fluid var9 = ClipContext.Fluid.NONE;
                LocalPlayer var94 = arg0.player;
                ClipContext var80 = new ClipContext(var32, var47, var8, var9, var94);
                BlockHitResult var50 = var49.clip(var80);
                HitResult.Type var52 = var50.getType();
                HitResult.Type var81 = HitResult.Type.MISS;
                if (var52 != var81) {
                    if ((var50 instanceof BlockHitResult)) {
                        if (((BlockHitResult) var50).getBlockPos().equals(arg1)) {
                        }
                    }
                }
                var75 = 1;
                var75 = 0;
                if (var75 == 0) {
                } else {
                    float[] var62 = calculateRotationsToVec(var32, var47);
                    float var67 = Math.abs(computeFloatMetric((var62[0] - var26)));
                    float var90 = Math.abs(computeFloatMetric((var62[1] - var29)));
                    float var68 = (var67 + var90);
                    float var91 = var15;
                    if (Float.compare(var68, var91) < 0) {
                        var15 = var68;
                        var14 = var41;
                    }
                    var18 = (var18 + 1);
                }
            }
        }
    }

    private static Vec3 evaluate(BlockPos arg0, Direction arg1) {
        Vec3 var10 = Vec3.atCenterOf(arg0);
        double var15 = (((double) arg1.getStepX()) * 0.5);
        double var21 = (((double) arg1.getStepY()) * 0.5);
        double var27 = (((double) arg1.getStepZ()) * 0.5);
        return var10.add(var15, var21, var27);
    }

    private boolean isEligible1(Minecraft arg0, BlockPos arg1) {
        int var77 = 0;
        int var20 = 0;
        int var22 = 0;
        int var26 = 0;
        if (arg0.level != null) {
            if (arg0.player == null) {
            }
        }
        return false;
        Vec3 var36 = arg0.player.getEyePosition();
        Vec3 var40 = arg0.player.position().add(0.0, 0.1, 0.0);
        Vec3 var43 = var36.add(var40).scale(0.5);
        Vec3[] var45 = new Vec3[3];
        var45[0] = var36;
        var45[1] = var43;
        var45[2] = var40;
        Vec3[] var47 = new Vec3[6];
        Direction[] var48 = Direction.values();
        var20 = 0;
        while (true) {
            if (var20 >= 6) break;
            int var94 = var20;
            int var105 = var20;
            Direction var104 = var48[var105];
            Vec3 var98 = evaluate(arg1, var104);
            var47[var94] = var98;
            var20 = (var20 + 1);
        }
        int var53 = var45.length;
        var22 = 0;
        while (true) {
            if (var22 >= var53) break;
            int var88 = var22;
            Vec3 var57 = var45[var88];
            int var60 = var47.length;
            var26 = 0;
            while (true) {
                if (var26 >= var60) break;
                int var90 = var26;
                Vec3 var64 = var47[var90];
                ClientLevel var66 = arg0.level;
                ClipContext.Block var106 = ClipContext.Block.COLLIDER;
                ClipContext.Fluid var12 = ClipContext.Fluid.NONE;
                LocalPlayer var107 = arg0.player;
                ClipContext var91 = new ClipContext(var57, var64, var106, var12, var107);
                BlockHitResult var67 = var66.clip(var91);
                HitResult.Type var69 = var67.getType();
                HitResult.Type var92 = HitResult.Type.MISS;
                if (var69 != var92) {
                    if ((var67 instanceof BlockHitResult)) {
                        if (((BlockHitResult) var67).getBlockPos().equals(arg1)) {
                        }
                    }
                }
                var77 = 1;
                var77 = 0;
                if (var77 != 0) {
                    return false;
                }
                var26 = (var26 + 1);
            }
            var22 = (var22 + 1);
        }
        return true;
    }

    private static boolean isEligible(Minecraft arg0, String arg1) {
        int var8 = 0;
        int var41 = 0;
        if (arg1 != null) {
            if (!arg1.isEmpty()) {
                if ("None".equalsIgnoreCase(arg1)) {
                }
            }
            return false;
            long var16 = arg0.getWindow().handle();
            var8 = -1;
            switch (arg1.hashCode()) {
                case -1387248810 -> {}
                case -1387248809 -> {}
                case -1387248808 -> {}
                case -1387248807 -> {}
                case -1387248806 -> {}
                default -> {}
            }
            if (arg1.equals("Mouse 3")) {
                var8 = 2;
                if (arg1.equals("Mouse 2")) {
                    var8 = 1;
                    if (arg1.equals("Mouse 1")) {
                        var8 = 0;
                        if (arg1.equals("Mouse 4")) {
                            var8 = 3;
                            if (arg1.equals("Mouse 5")) {
                                var8 = 4;
                            }
                            switch (var8) {
                                case 0 -> {}
                                case 1 -> {}
                                case 2 -> {}
                                case 3 -> {}
                                case 4 -> {}
                                default -> {}
                            }
                            if (GLFW.glfwGetMouseButton(var16, 1) == 1) {
                                var41 = 1;
                            } else {
                                var41 = 0;
                                if (GLFW.glfwGetMouseButton(var16, 4) == 1) {
                                    var41 = 1;
                                } else {
                                    var41 = 0;
                                    if (GLFW.glfwGetMouseButton(var16, 3) == 1) {
                                        var41 = 1;
                                    } else {
                                        var41 = 0;
                                        int var30 = computeColorOrIndex(arg1);
                                        if (var30 != -1) {
                                            if (GLFW.glfwGetKey(var16, var30) == 1) {
                                                var41 = 1;
                                            } else {
                                            }
                                        }
                                        var41 = 0;
                                        if (GLFW.glfwGetMouseButton(var16, 2) == 1) {
                                            var41 = 1;
                                        } else {
                                            var41 = 0;
                                            if (GLFW.glfwGetMouseButton(var16, 0) == 1) {
                                                var41 = 1;
                                            } else {
                                                var41 = 0;
                                            }
                                            return var41;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private static int computeColorOrIndex(String arg0) {
        int var5 = 0;
        int var25 = 0;
        var5 = -1;
        switch (arg0.hashCode()) {
            case -2099925287 -> {}
            case -1466726509 -> {}
            case -937491361 -> {}
            case 2219 -> {}
            case 2220 -> {}
            case 2221 -> {}
            case 2222 -> {}
            case 2223 -> {}
            case 2224 -> {}
            case 2225 -> {}
            case 2226 -> {}
            case 2227 -> {}
            case 2747 -> {}
            case 65929 -> {}
            case 68837 -> {}
            case 68838 -> {}
            case 68839 -> {}
            case 69819 -> {}
            case 69973 -> {}
            case 83829 -> {}
            case 2111115 -> {}
            case 2136258 -> {}
            case 2255103 -> {}
            case 2364455 -> {}
            case 67114680 -> {}
            case 78959100 -> {}
            case 79854690 -> {}
            case 80085222 -> {}
            case 353989386 -> {}
            case 861043148 -> {}
            case 2043376075 -> {}
            default -> {}
        }
        if (arg0.equals("F7")) {
            var5 = 25;
            if (arg0.equals("F2")) {
                var5 = 20;
                if (arg0.equals("Shift")) {
                    var5 = 3;
                    if (arg0.equals("Left")) {
                        var5 = 7;
                        if (arg0.equals("End")) {
                            var5 = 16;
                            if (arg0.equals("Delete")) {
                                var5 = 13;
                                if (arg0.equals("F5")) {
                                    var5 = 23;
                                    if (arg0.equals("Enter")) {
                                        var5 = 11;
                                        if (arg0.equals("F6")) {
                                            var5 = 24;
                                            if (arg0.equals("Up")) {
                                                var5 = 5;
                                                if (arg0.equals("Right")) {
                                                    var5 = 8;
                                                    if (arg0.equals("F9")) {
                                                        var5 = 27;
                                                        if (arg0.equals("Down")) {
                                                            var5 = 6;
                                                            if (arg0.equals("F4")) {
                                                                var5 = 22;
                                                                if (arg0.equals("Page Down")) {
                                                                    var5 = 18;
                                                                    if (arg0.equals("F10")) {
                                                                        var5 = 28;
                                                                        if (arg0.equals("Tab")) {
                                                                            var5 = 12;
                                                                            if (arg0.equals("Esc")) {
                                                                                var5 = 1;
                                                                                if (arg0.equals("Space")) {
                                                                                    var5 = 0;
                                                                                    if (arg0.equals("F1")) {
                                                                                        var5 = 19;
                                                                                        if (arg0.equals("Page Up")) {
                                                                                            var5 = 17;
                                                                                            if (arg0.equals("Backspace")) {
                                                                                                var5 = 10;
                                                                                                if (arg0.equals("Insert")) {
                                                                                                    var5 = 14;
                                                                                                    if (arg0.equals("F3")) {
                                                                                                        var5 = 21;
                                                                                                        if (arg0.equals("Home")) {
                                                                                                            var5 = 15;
                                                                                                            if (arg0.equals("Ctrl")) {
                                                                                                                var5 = 2;
                                                                                                                if (arg0.equals("F11")) {
                                                                                                                    var5 = 29;
                                                                                                                    if (arg0.equals("F8")) {
                                                                                                                        var5 = 26;
                                                                                                                        if (arg0.equals("Caps Lock")) {
                                                                                                                            var5 = 9;
                                                                                                                            if (arg0.equals("Alt")) {
                                                                                                                                var5 = 4;
                                                                                                                                if (arg0.equals("F12")) {
                                                                                                                                    var5 = 30;
                                                                                                                                }
                                                                                                                                switch (var5) {
                                                                                                                                    case 0 -> {}
                                                                                                                                    case 1 -> {}
                                                                                                                                    case 2 -> {}
                                                                                                                                    case 3 -> {}
                                                                                                                                    case 4 -> {}
                                                                                                                                    case 5 -> {}
                                                                                                                                    case 6 -> {}
                                                                                                                                    case 7 -> {}
                                                                                                                                    case 8 -> {}
                                                                                                                                    case 9 -> {}
                                                                                                                                    case 10 -> {}
                                                                                                                                    case 11 -> {}
                                                                                                                                    case 12 -> {}
                                                                                                                                    case 13 -> {}
                                                                                                                                    case 14 -> {}
                                                                                                                                    case 15 -> {}
                                                                                                                                    case 16 -> {}
                                                                                                                                    case 17 -> {}
                                                                                                                                    case 18 -> {}
                                                                                                                                    case 19 -> {}
                                                                                                                                    case 20 -> {}
                                                                                                                                    case 21 -> {}
                                                                                                                                    case 22 -> {}
                                                                                                                                    case 23 -> {}
                                                                                                                                    case 24 -> {}
                                                                                                                                    case 25 -> {}
                                                                                                                                    case 26 -> {}
                                                                                                                                    case 27 -> {}
                                                                                                                                    case 28 -> {}
                                                                                                                                    case 29 -> {}
                                                                                                                                    case 30 -> {}
                                                                                                                                    default -> {}
                                                                                                                                }
                                                                                                                                var25 = 257;
                                                                                                                                var25 = 32;
                                                                                                                                var25 = 342;
                                                                                                                                var25 = 341;
                                                                                                                                var25 = 264;
                                                                                                                                var25 = 259;
                                                                                                                                var25 = 261;
                                                                                                                                var25 = 266;
                                                                                                                                var25 = 292;
                                                                                                                                var25 = 300;
                                                                                                                                var25 = 267;
                                                                                                                                var25 = 291;
                                                                                                                                var25 = 299;
                                                                                                                                var25 = 290;
                                                                                                                                var25 = 263;
                                                                                                                                var25 = 258;
                                                                                                                                var25 = 268;
                                                                                                                                var25 = 262;
                                                                                                                                var25 = 340;
                                                                                                                                var25 = 297;
                                                                                                                                var25 = 293;
                                                                                                                                var25 = 298;
                                                                                                                                var25 = 301;
                                                                                                                                var25 = 280;
                                                                                                                                var25 = 269;
                                                                                                                                var25 = 295;
                                                                                                                                var25 = 260;
                                                                                                                                if (arg0.length() == 1) {
                                                                                                                                    int var17 = arg0.charAt(0);
                                                                                                                                    if (var17 >= 65) {
                                                                                                                                        if (var17 <= 90) {
                                                                                                                                            int var121 = (var17 - 65);
                                                                                                                                            var25 = (65 + var121);
                                                                                                                                        }
                                                                                                                                    } else {
                                                                                                                                        if (var17 >= 48) {
                                                                                                                                            if (var17 <= 57) {
                                                                                                                                                if (var17 == 48) {
                                                                                                                                                    var25 = 48;
                                                                                                                                                } else {
                                                                                                                                                    int var126 = (var17 - 49);
                                                                                                                                                    var25 = (49 + var126);
                                                                                                                                                    var25 = 296;
                                                                                                                                                    var25 = 256;
                                                                                                                                                    var25 = 265;
                                                                                                                                                    var25 = 294;
                                                                                                                                                }
                                                                                                                                            } else {
                                                                                                                                                var25 = -1;
                                                                                                                                            }
                                                                                                                                            return var25;
                                                                                                                                        }
                                                                                                                                    }
                                                                                                                                }
                                                                                                                            }
                                                                                                                        }
                                                                                                                    }
                                                                                                                }
                                                                                                            }
                                                                                                        }
                                                                                                    }
                                                                                                }
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean isSafeAimMode() {
        Object var6 = this.getSetting("Safe Aim Mode", "Silent");
        return "Silent".equals(var6);
    }

    private boolean isEligible(float arg0, float arg1, float arg2, float arg3) {
        int var16 = 0;
        if (Float.compare(Math.abs(computeFloatMetric((arg0 - arg2))), 2.0F) < 0) {
            if (Float.compare(Math.abs(computeFloatMetric((arg1 - arg3))), 2.0F) < 0) {
                var16 = 1;
            }
        } else {
            var16 = 0;
        }
        return var16;
    }

    private float computeFloatMetric(float arg0, float arg1, float arg2) {
        float var9 = computeFloatMetric((arg1 - arg0));
        float var12 = Math.min(Math.abs(var9), arg2);
        float var18 = Math.signum(var9);
        float var13 = (var12 * var18);
        return (arg0 + var13);
    }

    private void applyStep(Minecraft arg0, float arg1, float arg2, boolean arg3) {
        float var15 = Math.min(90.0F, arg2);
        float var8 = Math.max(-90.0F, var15);
        if (arg3) {
            AimAssist.applyStep(arg1, var8);
        } else {
            arg0.player.setYRot(arg1);
            arg0.player.setXRot(var8);
        }
    }

    private void applyStep15() {
        AimAssist.clearSilentRotation();
        this.targetBlockPos1 = null;
    }

    private static float[] calculateRotationsToVec(Vec3 arg0, Vec3 arg1) {
        Vec3 var12 = arg1.subtract(arg0);
        double var14 = var12.x;
        double var48 = var12.x;
        double var16 = (var14 * var48);
        double var51 = var12.z;
        double var75 = var12.z;
        double var53 = (var51 * var75);
        double var20 = Math.sqrt((var16 + var53));
        double var25 = (var12.x * -1.0);
        double var58 = var12.z;
        float var33 = ((float) ((Math.atan2(var25, var58) * 180.0) / 3.141592653589793));
        float var45 = ((float) (((Math.atan2(var12.y, var20) * -1.0) * 180.0) / 3.141592653589793));
        float[] var47 = new float[2];
        var47[0] = var33;
        var47[1] = var45;
        return var47;
    }

    private InteractionResult evaluate(Minecraft arg0, BlockHitResult arg1) {
        MultiPlayerGameMode var8 = arg0.gameMode;
        LocalPlayer var16 = arg0.player;
        InteractionHand var5 = InteractionHand.MAIN_HAND;
        InteractionResult var9 = var8.useItemOn(var16, var5, arg1);
        if (var9.consumesAction()) {
            LocalPlayer var13 = arg0.player;
            InteractionHand var17 = InteractionHand.MAIN_HAND;
            var13.swing(var17);
            this.setRightClickDelay(arg0, 4);
        }
        return var9;
    }

    private void applyStep16(Minecraft arg0) {
        ((KeyMappingAccessor) arg0.options.keyUse).setClickCount(0);
        this.setRightClickDelay(arg0, 4);
    }

    private void setRightClickDelay(Minecraft arg0, int arg1) {
        if (reflectedField == null) {
            return;
        }
        try {
            reflectedField.setInt(arg0, arg1);
        } catch (IllegalAccessException ignored) {
        }
    }

    private BlockHitResult findBlockHitResult1(Minecraft arg0) {
        HitResult var5 = arg0.hitResult;
        if ((var5 instanceof BlockHitResult)) {
            BlockHitResult var9 = ((BlockHitResult) var5);
            return var9;
        }
        return null;
    }

    private boolean isEligible2(Minecraft arg0, BlockPos arg1) {
        if (arg1 != null) {
            if (arg0.level == null) {
            }
        }
        return false;
        BlockState var9 = arg0.level.getBlockState(arg1);
        Block var12 = Blocks.RESPAWN_ANCHOR;
        return var9.is(var12);
    }

    private int computeColorOrIndex(Minecraft arg0, BlockPos arg1) {
        if (!(this.isEligible2(arg0, arg1))) {
            return 0;
        }
        BlockState var10 = arg0.level.getBlockState(arg1);
        IntegerProperty var15 = RespawnAnchorBlock.CHARGE;
        return ((Integer) var10.getValue(var15));
    }

    private void applyStep17() {
        AutoAnchorStage var2 = AutoAnchorStage.IDLE;
        this.autoAnchorStage = var2;
        this.targetBlockPos = null;
        this.targetBlockPos1 = null;
        this.counterValue1 = 0;
        this.counterValue2 = -1;
        this.floatValue2 = 0.0F;
        this.floatValue3 = 0.0F;
        this.floatValue4 = 0.0F;
        this.floatValue5 = 0.0F;
        this.floatValue6 = 0.0F;
        this.floatValue7 = 0.0F;
    }

    private void applyStep18(Minecraft arg0) {
        if (this.counterValue >= 0) {
            if (arg0.player != null) {
                Inventory var9 = arg0.player.getInventory();
                int var11 = this.counterValue;
                applyStep(var9, var11);
            }
            this.counterValue = -1;
            return;
        }
    }

    private int computeColorOrIndex1(Minecraft arg0) {
        int var4 = 0;
        var4 = 0;
        while (true) {
            if (var4 >= 9) break;
            Inventory var8 = arg0.player.getInventory();
            int var13 = var4;
            ItemStack var9 = var8.getItem(var13);
            Item var14 = Items.GLOWSTONE;
            if (var9.is(var14)) {
                return var4;
            }
            var4 = (var4 + 1);
        }
        return -1;
    }

    private void setRightClickDelay1(Minecraft arg0, int arg1) {
        if (this.counterValue == -1) {
            int var13 = computeColorOrIndex(arg0.player.getInventory());
            this.counterValue = var13;
        }
        applyStep(arg0.player.getInventory(), arg1);
    }

    private static int computeColorOrIndex(Inventory arg0) {
        return InventoryHelper.getSelected(arg0);
    }

    private static void applyStep(Inventory arg0, int arg1) {
        InventoryHelper.setSelected(arg0, arg1);
    }

    private static float computeFloatMetric(float arg0) {
        float var17 = 0.0F;
        var17 = (arg0 % 360.0F);
        if (Float.compare(var17, 180.0F) >= 0) {
            var17 = (var17 - 360.0F);
        }
        if (Float.compare(var17, -180.0F) < 0) {
            var17 = (var17 + 360.0F);
        }
        return var17;
    }

    private static int computeColorOrIndex(Object arg0) {
        if (arg0 == null) {
            return 0;
        }
        if ((arg0 instanceof Number)) {
            return ((Number) arg0);
        }
        try {
            int var12 = Integer.parseInt(arg0.toString());
        } catch (NumberFormatException ignored) {
        }
        return var12;
    }

    private static float computeFloatMetric(Object arg0) {
        if (arg0 == null) {
            return 0.0F;
        }
        if ((arg0 instanceof Number)) {
            return ((Number) arg0);
        }
        try {
            float var11 = Float.parseFloat(arg0.toString());
        } catch (NumberFormatException ignored) {
        }
        return var11;
    }

    static {
        reflectedField = evaluate();
    }

}
