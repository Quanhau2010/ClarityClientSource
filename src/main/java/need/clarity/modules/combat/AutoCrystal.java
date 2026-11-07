package need.clarity.modules.combat;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Random;
import need.clarity.mixin.KeyMappingAccessor;
import need.clarity.modules.ModuleBase;
import need.clarity.util.InventoryHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class AutoCrystal extends ModuleBase {
    private AutoCrystalStage autoCrystalStage;
    private long timestampMs;
    private long timestampMs1;
    private long timestampMs2;
    private long timestampMs3;
    private int counterValue;
    private int counterValue1;
    private final Random fieldVal;
    private static final Field reflectedField;

    private static Field evaluate() {
        try {
            Field var3 = Minecraft.class.getDeclaredField("rightClickDelay");
            var3.setAccessible(true);
        } catch (NoSuchFieldException ignored) {
        }
        return var3;
    }

    public AutoCrystal() {
        super("Auto Crystal", "Combat");
        AutoCrystalStage var12 = AutoCrystalStage.IDLE;
        this.autoCrystalStage = var12;
        this.timestampMs = 0L;
        this.timestampMs1 = 0L;
        this.timestampMs2 = 0L;
        this.timestampMs3 = 0L;
        this.counterValue = -1;
        this.counterValue1 = -1;
        Random var23 = new Random();
        this.fieldVal = var23;
    }

    public void onDisable() {
        AimAssist.clearSilentRotation();
        this.timestampMs3 = 0L;
        Minecraft var6 = Minecraft.getInstance();
        this.applyStep(var6);
        this.applyStep();
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
            if (var1.player.isUsingItem()) {
                return;
            }
            long var22 = System.currentTimeMillis();
            if (Long.compare(this.timestampMs3, 0L) > 0) {
                long var57 = this.timestampMs3;
                if (Long.compare(var22, var57) >= 0) {
                    this.applyStep(var1);
                    this.timestampMs3 = 0L;
                }
                boolean var36 = var1.options.keyUse.isDown();
                if (!var36) {
                    AutoCrystalStage var39 = this.autoCrystalStage;
                    AutoCrystalStage var51 = AutoCrystalStage.IDLE;
                    if (var39 != var51) {
                        AimAssist.clearSilentRotation();
                        this.applyStep4(var1, var22);
                        this.applyStep();
                        return;
                    }
                }
                if (!var36) {
                    return;
                }
                switch (this.autoCrystalStage.ordinal()) {
                    case 0 -> {}
                    case 1 -> {}
                    case 2 -> {}
                    default -> {}
                }
                this.applyStep2(var1, var22);
                this.applyStep(var1, var22);
                this.applyStep3(var1, var22);
                return;
            }
        }
    }

    private void applyStep(Minecraft arg0, long arg1) {
        if (!this.isEligible(arg0)) {
            return;
        }
        if (!this.isEligible1(arg0)) {
            return;
        }
        this.applyStep1(arg0, arg1);
    }

    private boolean isEligible(Minecraft arg0) {
        HitResult var7 = arg0.hitResult;
        if ((var7 instanceof EntityHitResult)) {
            if ((((EntityHitResult) var7).getEntity() instanceof EndCrystal)) {
                return this.isEligible2(arg0);
            }
        }
        HitResult var18 = arg0.hitResult;
        if ((var18 instanceof BlockHitResult)) {
            BlockPos var25 = ((BlockHitResult) var18).getBlockPos();
            BlockState var28 = arg0.level.getBlockState(var25);
            if (var28.isAir()) {
                return false;
            }
            Block var40 = Blocks.OBSIDIAN;
            if (!var28.is(var40)) {
                Block var41 = Blocks.BEDROCK;
                if (var28.is(var41)) {
                }
            }
            return this.isEligible2(arg0);
            return false;
        }
        return false;
    }

    private void applyStep1(Minecraft arg0, long arg1) {
        HitResult var11 = arg0.hitResult;
        if ((var11 instanceof EntityHitResult)) {
            if ((((EntityHitResult) var11).getEntity() instanceof EndCrystal)) {
                long var49 = this.evaluate();
                long var38 = (arg1 + var49);
                this.timestampMs = var38;
                AutoCrystalStage var40 = AutoCrystalStage.SWITCH_CRYSTAL;
                this.autoCrystalStage = var40;
                return;
            }
        }
        HitResult var22 = arg0.hitResult;
        if ((var22 instanceof BlockHitResult)) {
            BlockHitResult var26 = ((BlockHitResult) var22);
        } else {
            return;
        }
        BlockPos var28 = var26.getBlockPos();
        BlockState var31 = arg0.level.getBlockState(var28);
        Block var42 = Blocks.OBSIDIAN;
        if (!var31.is(var42)) {
            Block var43 = Blocks.BEDROCK;
            if (var31.is(var43)) {
            }
        }
        long var52 = this.evaluate();
        long var46 = (arg1 + var52);
        this.timestampMs = var46;
        AutoCrystalStage var48 = AutoCrystalStage.SWITCH_CRYSTAL;
        this.autoCrystalStage = var48;
    }

    private void applyStep2(Minecraft arg0, long arg1) {
        long var28 = this.timestampMs;
        if (Long.compare(arg1, var28) < 0) {
            return;
        }
        Item var30 = Items.END_CRYSTAL;
        int var12 = this.findHotbarItemSlot(arg0, var30);
        if (var12 == -1) {
            this.applyStep();
            return;
        }
        this.setRightClickDelay(arg0, var12);
        this.timestampMs1 = 0L;
        long var32 = this.evaluate2();
        long var25 = (arg1 + var32);
        this.timestampMs2 = var25;
        AutoCrystalStage var27 = AutoCrystalStage.NORMAL;
        this.autoCrystalStage = var27;
    }

    private void applyStep3(Minecraft arg0, long arg1) {
        int var31 = 0;
        ItemStack var14 = arg0.player.getMainHandItem();
        Item var5 = Items.END_CRYSTAL;
        if (!var14.is(var5)) {
            Item var100 = Items.END_CRYSTAL;
            int var66 = this.findHotbarItemSlot(arg0, var100);
            if (var66 == -1) {
                this.applyStep();
                return;
            }
            this.setRightClickDelay(arg0, var66);
            return;
        }
        HitResult var17 = arg0.hitResult;
        if ((var17 instanceof BlockHitResult)) {
            BlockPos var23 = ((BlockHitResult) var17).getBlockPos();
            BlockState var26 = arg0.level.getBlockState(var23);
            Block var71 = Blocks.OBSIDIAN;
            if (!var26.is(var71)) {
                Block var72 = Blocks.BEDROCK;
                if (var26.is(var72)) {
                }
            }
            var31 = 1;
            var31 = 0;
            if (var31 != 0) {
                long var92 = this.timestampMs1;
                if (Long.compare(arg1, var92) >= 0) {
                    if (this.counterValue1 != -1) {
                        int var95 = this.counterValue1;
                        if (this.isEligible(arg0, var95)) {
                            return;
                        }
                        this.counterValue1 = -1;
                    }
                    if (!(this.isEligible(arg0, var23))) {
                        this.applyStep5(arg0);
                        long var102 = this.evaluate1();
                        long var80 = (arg1 + var102);
                        this.timestampMs1 = var80;
                    }
                    if ((var17 instanceof EntityHitResult)) {
                        Entity var50 = ((EntityHitResult) var17).getEntity();
                        if ((var50 instanceof EndCrystal)) {
                            EndCrystal var54 = ((EndCrystal) var50);
                            if (!var54.isRemoved()) {
                                if (var54.isAlive()) {
                                    long var98 = this.timestampMs2;
                                    if (Long.compare(arg1, var98) >= 0) {
                                        this.applyStep6(arg0);
                                        int var84 = var54.getId();
                                        this.counterValue1 = var84;
                                        long var105 = this.evaluate2();
                                        long var87 = (arg1 + var105);
                                        this.timestampMs2 = var87;
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

    public void applyStep(int arg0) {
        if (!this.isEnabled()) {
            return;
        }
        AutoCrystalStage var9 = this.autoCrystalStage;
        AutoCrystalStage var3 = AutoCrystalStage.IDLE;
        if (var9 != var3) {
            return;
        }
        long var10 = System.currentTimeMillis();
        this.counterValue = arg0;
        this.counterValue1 = -1;
        this.timestampMs1 = 0L;
        long var26 = this.evaluate2();
        long var23 = (var10 + var26);
        this.timestampMs2 = var23;
        AutoCrystalStage var25 = AutoCrystalStage.NORMAL;
        this.autoCrystalStage = var25;
    }

    private void applyStep() {
        AutoCrystalStage var2 = AutoCrystalStage.IDLE;
        this.autoCrystalStage = var2;
        this.timestampMs = 0L;
        this.counterValue1 = -1;
        this.timestampMs1 = 0L;
        this.timestampMs2 = 0L;
    }

    private void applyStep4(Minecraft arg0, long arg1) {
        if (this.counterValue >= 0) {
            if (arg0.player == null) {
            }
        }
        return;
        Boolean var33 = true;
        if (!((Boolean) this.getSetting("Swap Back", var33))) {
            this.counterValue = -1;
            return;
        }
        long var19 = this.evaluate3();
        if (Long.compare(var19, 0L) <= 0) {
            this.applyStep(arg0);
        } else {
            long var30 = (arg1 + var19);
            this.timestampMs3 = var30;
        }
    }

    private void applyStep(Minecraft arg0) {
        if (this.counterValue >= 0) {
            if (arg0.player != null) {
                Inventory var9 = arg0.player.getInventory();
                int var12 = this.counterValue;
                applyStep(var9, var12);
            }
            this.counterValue = -1;
            this.timestampMs3 = 0L;
            return;
        }
    }

    private void applyStep5(Minecraft arg0) {
        KeyMappingAccessor var7 = ((KeyMappingAccessor) arg0.options.keyUse);
        int var12 = (((KeyMappingAccessor) arg0.options.keyUse).getClickCount() + 1);
        var7.setClickCount(var12);
    }

    private void applyStep6(Minecraft arg0) {
        HitResult var7 = arg0.hitResult;
        if ((var7 instanceof EntityHitResult)) {
            EntityHitResult var11 = ((EntityHitResult) var7);
            MultiPlayerGameMode var13 = arg0.gameMode;
            LocalPlayer var16 = arg0.player;
            Entity var18 = var11.getEntity();
            var13.attack(var16, var18);
            LocalPlayer var15 = arg0.player;
            InteractionHand var17 = InteractionHand.MAIN_HAND;
            var15.swing(var17);
            return;
        }
    }

    private EndCrystal evaluate(Minecraft arg0, BlockPos arg1) {
        Iterator var11 = arg0.level.entitiesForRendering().iterator();
        while (true) {
            if (!var11.hasNext()) break;
            Entity var17 = ((Entity) var11.next());
            if (!(var17 instanceof EndCrystal)) continue;
            EndCrystal var21 = ((EndCrystal) var17);
            if (var21.isRemoved()) continue;
            if (!BlockPos.containing(var21.position()).below().equals(arg1)) continue;
        }
        return var21;
        return null;
    }

    private boolean isEligible(Minecraft arg0, BlockPos arg1) {
        int var7 = 0;
        if (this.evaluate(arg0, arg1) != null) {
            var7 = 1;
        } else {
            var7 = 0;
        }
        return var7;
    }

    private boolean isEligible(Minecraft arg0, int arg1) {
        int var26 = 0;
        Iterator var10 = arg0.level.entitiesForRendering().iterator();
        while (true) {
            if (!var10.hasNext()) break;
            Entity var15 = ((Entity) var10.next());
            if (var15.getId() != arg1) continue;
            if (!(var15 instanceof EndCrystal)) continue;
        }
        EndCrystal var21 = ((EndCrystal) var15);
        if (!var21.isRemoved()) {
            if (var21.isAlive()) {
                var26 = 1;
                return false;
            }
        }
        var26 = 0;
        return var26;
    }

    private boolean isEligible1(Minecraft arg0) {
        Boolean var56 = true;
        boolean var12 = ((Boolean) this.getSetting("Work with Crystal", var56));
        Boolean var58 = true;
        boolean var16 = ((Boolean) this.getSetting("Work with Sword", var58));
        Boolean var60 = true;
        boolean var20 = ((Boolean) this.getSetting("Work with Totem", var60));
        Boolean var62 = true;
        boolean var24 = ((Boolean) this.getSetting("Work with Fist", var62));
        if (!var12) {
            if (!var16) {
                if (!var20) {
                    if (!var24) {
                        return true;
                    }
                }
            }
            ItemStack var32 = arg0.player.getMainHandItem();
            if (var12) {
                Item var53 = Items.END_CRYSTAL;
                if (var32.is(var53)) {
                    return true;
                }
            }
            if (var16) {
                TagKey var54 = ItemTags.SWORDS;
                if (var32.is(var54)) {
                    return true;
                }
            }
            if (var20) {
                Item var55 = Items.TOTEM_OF_UNDYING;
                if (var32.is(var55)) {
                    return true;
                }
            }
            if (var24) {
                if (var32.isEmpty()) {
                    return true;
                }
            }
            return false;
        }
    }

    private boolean isEligible2(Minecraft arg0) {
        int var6 = 0;
        Item var4 = Items.END_CRYSTAL;
        if (this.findHotbarItemSlot(arg0, var4) != -1) {
            var6 = 1;
        } else {
            var6 = 0;
        }
        return var6;
    }

    private int findHotbarItemSlot(Minecraft arg0, Item arg1) {
        int var5 = 0;
        var5 = 0;
        while (true) {
            if (var5 < 9) {
                Inventory var9 = arg0.player.getInventory();
                int var14 = var5;
                if (var9.getItem(var14).is(arg1)) {
                    return var5;
                }
            }
            return -1;
            var5 = (var5 + 1);
        }
    }

    private void setRightClickDelay(Minecraft arg0, int arg1) {
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

    private long evaluate() {
        return this.evaluate("Switch Delay", 1L, 50L);
    }

    private long evaluate1() {
        return this.evaluate("Place Delay", 50L, 100L);
    }

    private long evaluate2() {
        return this.evaluate("Break Delay", 50L, 100L);
    }

    private long evaluate3() {
        return this.evaluate("Swap Back Delay", 50L, 100L);
    }

    private long evaluate(String arg0, long arg1, long arg2) {
        long var16 = 0L;
        String var39 = (arg0) + "_lo";
        Long var42 = arg1;
        long var19 = evaluate(this.getSetting(var39, var42));
        String var41 = (arg0) + "_hi";
        Long var45 = arg2;
        var16 = evaluate(this.getSetting(var41, var45));
        if (Long.compare(var16, var19) < 0) {
            var16 = var19;
        }
        long var48 = var16;
        if (var19 == var48) {
            return var19;
        }
        double var52 = this.fieldVal.nextDouble();
        double var62 = ((double) ((var16 - var19) + 1L));
        long var56 = ((long) (var52 * var62));
        return (var19 + var56);
    }

    private static long evaluate(Object arg0) {
        if (arg0 == null) {
            return 0L;
        }
        if ((arg0 instanceof Number)) {
            return ((Number) arg0);
        }
        try {
            long var9 = Long.parseLong(arg0.toString());
        } catch (NumberFormatException ignored) {
        }
        return var9;
    }

    static {
        reflectedField = evaluate();
    }

}
