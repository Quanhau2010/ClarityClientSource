package need.clarity.modules.utility;

import need.clarity.PerFrameEvents;
import need.clarity.mixin.KeyMappingAccessor;
import need.clarity.modules.ModuleBase;
import need.clarity.modules.combat.AimAssist;
import need.clarity.util.InventoryHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class MLG extends ModuleBase {
    private MLGStage mLGStage;
    private int counterValue;
    private int counterValue1;
    private boolean enabledState;
    private long timestampMs;
    private boolean enabledState1;
    private int counterValue2;
    private int counterValue3;
    private boolean enabledState2;
    private float floatValue;
    private float floatValue1;
    private boolean enabledState3;
    private float floatValue2;

    public MLG() {
        super("MLG", "utility");
        MLGStage var19 = MLGStage.IDLE;
        this.mLGStage = var19;
        this.counterValue = -1;
        this.counterValue1 = 0;
        this.enabledState = false;
        this.timestampMs = 0L;
        this.enabledState1 = false;
        this.counterValue2 = -1;
        this.counterValue3 = 0;
        this.enabledState2 = false;
        this.floatValue = 0.0F;
        this.floatValue1 = 0.0F;
        this.enabledState3 = false;
        this.floatValue2 = 512.0F;
        PerFrameEvents.register(() -> this.applyStep());
    }

    public void onEnable() {
        this.applyStep1();
    }

    public void onDisable() {
        Minecraft var1 = Minecraft.getInstance();
        if (this.counterValue >= 0) {
            if (var1.player != null) {
                Inventory var10 = var1.player.getInventory();
                int var13 = this.counterValue;
                InventoryHelper.setSelected(var10, var13);
            }
            this.getSilentRotations();
            this.applyStep2();
            return;
        }
    }

    public void onTick() {
        float var167 = 0.0F;
        int var172 = 0;
        int var189 = 0;
        Minecraft var1 = Minecraft.getInstance();
        if (var1.player != null) {
            if (var1.level == null) {
            }
        }
        return;
        if (this.counterValue1 > 0) {
            int var191 = (this.counterValue1 - 1);
            this.counterValue1 = var191;
            if (this.counterValue1 == 0) {
                if (this.counterValue >= 0) {
                    Boolean var258 = false;
                    if (((Boolean) this.getSetting("Swap Back", var258))) {
                        this.enabledState = true;
                        long var255 = System.currentTimeMillis();
                        this.timestampMs = var255;
                    } else {
                        Inventory var36 = var1.player.getInventory();
                        int var194 = this.counterValue;
                        InventoryHelper.setSelected(var36, var194);
                        this.counterValue = -1;
                    }
                    if (this.enabledState) {
                        if (this.counterValue >= 0) {
                            Integer var260 = 0;
                            int var45 = ((Integer) this.getSetting("Swap Back Delay", var260));
                            long var46 = System.currentTimeMillis();
                            long var262 = this.timestampMs;
                            long var48 = (var46 - var262);
                            long var265 = ((long) var45);
                            if (Long.compare(var48, var265) >= 0) {
                                Inventory var53 = var1.player.getInventory();
                                int var198 = this.counterValue;
                                InventoryHelper.setSelected(var53, var198);
                                this.counterValue = -1;
                                this.enabledState = false;
                            }
                            MLGStage var57 = this.mLGStage;
                            MLGStage var201 = MLGStage.PICKUP;
                            if (var57 == var201) {
                                this.applyStep(var1);
                                return;
                            }
                            if (!var1.player.onGround()) {
                                if (!var1.player.isInWater()) {
                                    if (var1.player.isInLava()) {
                                    }
                                }
                                MLGStage var69 = this.mLGStage;
                                MLGStage var203 = MLGStage.DONE;
                                if (var69 != var203) {
                                    MLGStage var71 = this.mLGStage;
                                    MLGStage var204 = MLGStage.WAITING;
                                    if (var71 != var204) {
                                        MLGStage var73 = this.mLGStage;
                                        MLGStage var205 = MLGStage.AIMING;
                                        if (var73 == var205) {
                                        }
                                    }
                                    Boolean var268 = false;
                                    boolean var77 = ((Boolean) this.getSetting("Pick Up Water", var268));
                                    Boolean var270 = true;
                                    boolean var81 = ((Boolean) this.getSetting("Water Bucket", var270));
                                    if (var77) {
                                        if (var81) {
                                            if (this.enabledState1) {
                                                if (var1.player.isInWater()) {
                                                    this.getSilentRotations();
                                                    this.enabledState2 = false;
                                                    MLGStage var209 = MLGStage.PICKUP;
                                                    this.mLGStage = var209;
                                                    Integer var272 = 0;
                                                    Object var93 = this.getSetting("Pick Up Delay", var272);
                                                    Integer var94 = ((Integer) var93);
                                                    int var95 = var94;
                                                    int var212 = (var95 / 50);
                                                    this.counterValue3 = var212;
                                                    return;
                                                }
                                                MLGStage var98 = this.mLGStage;
                                                MLGStage var213 = MLGStage.IDLE;
                                                if (var98 != var213) {
                                                    this.getSilentRotations();
                                                    this.applyStep1();
                                                }
                                                return;
                                                LocalPlayer var102 = var1.player;
                                                Vec3 var103 = var102.getDeltaMovement();
                                                double var104 = var103.y;
                                                int var108 = Double.compare(var104, 0.0);
                                                if (var108 >= 0) {
                                                    MLGStage var110 = this.mLGStage;
                                                    MLGStage var214 = MLGStage.DONE;
                                                    if (var110 == var214) {
                                                        this.enabledState2 = false;
                                                    }
                                                    MLGStage var113 = this.mLGStage;
                                                    MLGStage var216 = MLGStage.IDLE;
                                                    if (var113 == var216) {
                                                        return;
                                                    }
                                                }
                                                float var218 = this.computeFloatMetric(var1);
                                                this.floatValue2 = var218;
                                                Float var278 = 5.0F;
                                                Object var116 = this.getSetting("Fall Threshold", var278);
                                                Float var117 = ((Float) var116);
                                                float var118 = var117;
                                                LocalPlayer var120 = var1.player;
                                                double var121 = var120.fallDistance;
                                                float var123 = ((float) var121);
                                                float var221 = this.floatValue2;
                                                float var125 = (var123 + var221);
                                                int var127 = Float.compare(var125, var118);
                                                if (var127 < 0) {
                                                    MLGStage var129 = this.mLGStage;
                                                    MLGStage var223 = MLGStage.DONE;
                                                    if (var129 == var223) {
                                                        this.enabledState2 = false;
                                                    }
                                                    MLGStage var132 = this.mLGStage;
                                                    MLGStage var225 = MLGStage.IDLE;
                                                    if (var132 == var225) {
                                                        return;
                                                    }
                                                }
                                                Boolean var280 = false;
                                                if (((Boolean) this.getSetting("Only Lethal Fall", var280))) {
                                                    if (!(this.isEligible(var1, var123))) {
                                                        MLGStage var141 = this.mLGStage;
                                                        MLGStage var228 = MLGStage.IDLE;
                                                        if (var141 == var228) {
                                                            return;
                                                        }
                                                        MLGStage var143 = this.mLGStage;
                                                        MLGStage var229 = MLGStage.AIMING;
                                                        if (var143 == var229) {
                                                            this.applyStep1();
                                                            return;
                                                        }
                                                    }
                                                    int var146 = this.computeColorOrIndex(var1);
                                                    if (var146 < 0) {
                                                        this.enabledState2 = false;
                                                        return;
                                                    }
                                                    float var283 = this.floatValue2;
                                                    float var149 = this.computeFloatMetric(var104, var283);
                                                    switch (this.mLGStage.ordinal()) {
                                                        case 0 -> {}
                                                        case 1 -> {}
                                                        case 2 -> {}
                                                        case 3 -> {}
                                                        case 4 -> {}
                                                        default -> {}
                                                    }
                                                    this.enabledState2 = true;
                                                    if (this.counterValue1 <= 0) {
                                                        MLGStage var252 = MLGStage.DONE;
                                                        this.mLGStage = var252;
                                                        this.enabledState2 = true;
                                                        float var240 = this.floatValue1;
                                                        float var162 = Math.abs((90.0F - var240));
                                                        if (Float.compare(var149, 1.0F) <= 0) {
                                                            var167 = 90.0F;
                                                        } else {
                                                            if (Float.compare(var149, 3.0F) <= 0) {
                                                                var167 = 30.0F;
                                                            } else {
                                                                var167 = 5.0F;
                                                            }
                                                            float var15 = var167;
                                                            if (Float.compare(var162, var15) <= 0) {
                                                                var189 = 1;
                                                            } else {
                                                                var189 = 0;
                                                            }
                                                            int var16 = var189;
                                                            if (Float.compare(var149, 5.0F) <= 0) {
                                                                var172 = 1;
                                                            } else {
                                                                var172 = 0;
                                                            }
                                                            int var17 = var172;
                                                            if (var16 != 0) {
                                                                if (var17 == 0) {
                                                                }
                                                            }
                                                            if (Float.compare(var149, 2.0F) <= 0) {
                                                                MLGStage var246 = MLGStage.PLACING;
                                                                this.mLGStage = var246;
                                                                this.enabledState2 = true;
                                                                this.setRightClickDelay(var1, var146);
                                                                MLGStage var249 = MLGStage.WAITING;
                                                                this.mLGStage = var249;
                                                                MLGStage var233 = MLGStage.AIMING;
                                                                this.mLGStage = var233;
                                                                this.enabledState3 = false;
                                                                this.enabledState2 = true;
                                                                this.enabledState1 = false;
                                                                this.counterValue2 = -1;
                                                                this.enabledState2 = true;
                                                            }
                                                            return;
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

    private void applyStep(Minecraft arg0) {
        int var5 = 0;
        int var6 = 0;
        if (this.counterValue3 > 0) {
            int var89 = (this.counterValue3 - 1);
            this.counterValue3 = var89;
            return;
        }
        if (!arg0.player.isInWater()) {
            MLGStage var86 = MLGStage.IDLE;
            this.mLGStage = var86;
            this.enabledState1 = false;
            return;
        }
        var5 = -1;
        if (this.counterValue2 >= 0) {
            if (this.counterValue2 < 9) {
                Inventory var20 = arg0.player.getInventory();
                int var68 = this.counterValue2;
                ItemStack var21 = var20.getItem(var68);
                Item var69 = Items.BUCKET;
                if (var21.is(var69)) {
                    var5 = this.counterValue2;
                }
                if (var5 < 0) {
                    var6 = 0;
                    while (true) {
                        if (var6 >= 9) break;
                        Inventory var30 = arg0.player.getInventory();
                        int var71 = var6;
                        ItemStack var31 = var30.getItem(var71);
                        Item var72 = Items.BUCKET;
                        if (var31.is(var72)) {
                            var5 = var6;
                        } else {
                            var6 = (var6 + 1);
                        }
                    }
                }
                if (var5 < 0) {
                    MLGStage var73 = MLGStage.IDLE;
                    this.mLGStage = var73;
                    this.enabledState1 = false;
                    return;
                }
                int var40 = InventoryHelper.getSelected(arg0.player.getInventory());
                if (this.counterValue < 0) {
                    this.counterValue = var40;
                }
                Inventory var46 = arg0.player.getInventory();
                int var76 = var5;
                InventoryHelper.setSelected(var46, var76);
                Boolean var90 = true;
                if (((Boolean) this.getSetting("Silent Rotations", var90))) {
                    AimAssist.applyStep(arg0.player.getYRot(), 89.0F);
                }
                KeyMappingAccessor var58 = ((KeyMappingAccessor) arg0.options.keyUse);
                int var81 = (var58.getClickCount() + 1);
                var58.setClickCount(var81);
                this.counterValue1 = 2;
                MLGStage var83 = MLGStage.IDLE;
                this.mLGStage = var83;
                this.enabledState1 = false;
                this.counterValue2 = -1;
                return;
            }
        }
    }

    private void applyStep() {
        if (this.isEnabled()) {
            if (!this.enabledState2) {
            }
        }
        return;
        Minecraft var17 = Minecraft.getInstance();
        if (var17.player == null) {
            return;
        }
        Boolean var83 = true;
        boolean var23 = ((Boolean) this.getSetting("Silent Rotations", var83));
        Integer var85 = 10;
        float var28 = ((float) ((Integer) this.getSetting("Aim Speed", var85)));
        if (!this.enabledState3) {
            this.enabledState3 = true;
            if (var23) {
                if (AimAssist.isActivelyTracking()) {
                    float var60 = AimAssist.computeFloatMetric2();
                    this.floatValue = var60;
                    float var61 = AimAssist.computeFloatMetric3();
                    this.floatValue1 = var61;
                } else {
                }
            }
            float var79 = var17.player.getYRot();
            this.floatValue = var79;
            float var82 = var17.player.getXRot();
            this.floatValue1 = var82;
        }
        float var38 = this.floatValue;
        float var40 = (var28 / 3.0F);
        float var64 = this.floatValue1;
        float var42 = (90.0F - var64);
        float var45 = Math.min(Math.abs(var42), var40);
        float var67 = Math.signum(var42);
        float var46 = (var45 * var67);
        float var89 = (this.floatValue1 + var46);
        float var87 = Math.min(90.0F, var89);
        float var69 = Math.max(-90.0F, var87);
        this.floatValue1 = var69;
        this.floatValue = var38;
        if (var23) {
            float var51 = this.floatValue;
            float var72 = this.floatValue1;
            AimAssist.applyStep(var51, var72);
        } else {
            LocalPlayer var53 = var17.player;
            float var74 = this.floatValue;
            var53.setYRot(var74);
            LocalPlayer var55 = var17.player;
            float var76 = this.floatValue1;
            var55.setXRot(var76);
        }
    }

    private void setRightClickDelay(Minecraft arg0, int arg1) {
        int var10 = InventoryHelper.getSelected(arg0.player.getInventory());
        if (var10 != arg1) {
            this.counterValue = var10;
            InventoryHelper.setSelected(arg0.player.getInventory(), arg1);
        } else {
            this.counterValue = -1;
        }
        ItemStack var37 = arg0.player.getInventory().getItem(arg1);
        Item var48 = Items.WATER_BUCKET;
        boolean var38 = var37.is(var48);
        this.enabledState1 = var38;
        this.counterValue2 = arg1;
        if (Float.compare(this.floatValue2, 4.5F) <= 0) {
            BlockHitResult var22 = this.findBlockHitResult(arg0);
            if (var22 != null) {
                arg0.hitResult = var22;
            }
            KeyMappingAccessor var28 = ((KeyMappingAccessor) arg0.options.keyUse);
            int var45 = (var28.getClickCount() + 1);
            var28.setClickCount(var45);
            this.counterValue1 = 2;
            return;
        }
    }

    private float computeFloatMetric(double arg0, float arg1) {
        double var10 = 0.0;
        double var12 = 0.0;
        int var14 = 0;
        if (Float.compare(arg1, 0.0F) <= 0) {
            return 0.0F;
        }
        var10 = 0.0;
        var12 = arg0;
        var14 = 0;
        while (true) {
            if (var14 >= 2000) break;
            var12 = ((var12 - 0.08) * 0.98);
            double var29 = var10;
            double var42 = var12;
            var10 = (var29 + var42);
            var14 = (var14 + 1);
            double var33 = var10;
            double var47 = (((double) arg1) * -1.0);
            if (Double.compare(var33, var47) > 0) continue;
        }
        return ((float) var14);
        return 2000.0F;
    }

    private float computeFloatMetric(Minecraft arg0) {
        Vec3 var16 = arg0.player.position();
        Vec3 var18 = var16.add(0.0, -512.0, 0.0);
        ClientLevel var20 = arg0.level;
        ClipContext.Block var38 = ClipContext.Block.COLLIDER;
        ClipContext.Fluid var10 = ClipContext.Fluid.NONE;
        LocalPlayer var39 = arg0.player;
        ClipContext var31 = new ClipContext(var16, var18, var38, var10, var39);
        BlockHitResult var21 = var20.clip(var31);
        HitResult.Type var23 = var21.getType();
        HitResult.Type var32 = HitResult.Type.MISS;
        if (var23 == var32) {
            return 512.0F;
        }
        double var26 = var16.y;
        double var35 = var21.getLocation().y;
        return ((float) (var26 - var35));
    }

    private BlockHitResult findBlockHitResult(Minecraft arg0) {
        Vec3 var16 = arg0.player.position();
        Vec3 var18 = var16.add(0.0, -512.0, 0.0);
        ClientLevel var20 = arg0.level;
        ClipContext.Block var34 = ClipContext.Block.COLLIDER;
        ClipContext.Fluid var10 = ClipContext.Fluid.NONE;
        LocalPlayer var36 = arg0.player;
        ClipContext var26 = new ClipContext(var16, var18, var34, var10, var36);
        BlockHitResult var21 = var20.clip(var26);
        HitResult.Type var23 = var21.getType();
        HitResult.Type var27 = HitResult.Type.MISS;
        if (var23 == var27) {
            return null;
        }
        Vec3 var29 = var21.getLocation();
        Direction var31 = Direction.UP;
        BlockPos var33 = var21.getBlockPos();
        BlockHitResult var25 = new BlockHitResult(var29, var31, var33, false);
        return var25;
    }

    private boolean isEligible(Minecraft arg0, float arg1) {
        int var13 = 0;
        float var14 = this.floatValue2;
        float var16 = ((arg1 + var14) - 3.0F);
        float var10 = Math.max(0.0F, var16);
        float var19 = arg0.player.getHealth();
        if (Float.compare(var10, var19) >= 0) {
            var13 = 1;
        } else {
            var13 = 0;
        }
        return var13;
    }

    private int computeColorOrIndex(Minecraft arg0) {
        int var5 = 0;
        Boolean var22 = true;
        if (!((Boolean) this.getSetting("Water Bucket", var22))) {
            return -1;
        }
        var5 = 0;
        while (true) {
            if (var5 >= 9) break;
            Inventory var13 = arg0.player.getInventory();
            int var20 = var5;
            ItemStack var14 = var13.getItem(var20);
            Item var21 = Items.WATER_BUCKET;
            if (var14.is(var21)) {
                return var5;
            }
            var5 = (var5 + 1);
        }
        return -1;
    }

    private void getSilentRotations() {
        Boolean var9 = true;
        if (((Boolean) this.getSetting("Silent Rotations", var9))) {
            AimAssist.clearSilentRotation();
        }
    }

    private void applyStep1() {
        MLGStage var2 = MLGStage.IDLE;
        this.mLGStage = var2;
        this.enabledState2 = false;
        this.enabledState3 = false;
        this.enabledState1 = false;
        this.counterValue2 = -1;
        this.counterValue3 = 0;
        this.floatValue2 = 512.0F;
    }

    private void applyStep2() {
        this.applyStep1();
        this.counterValue = -1;
        this.counterValue1 = 0;
        this.enabledState = false;
        this.timestampMs = 0L;
    }

}
