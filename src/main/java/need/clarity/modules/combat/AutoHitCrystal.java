package need.clarity.modules.combat;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Random;
import need.clarity.mixin.KeyMappingAccessor;
import need.clarity.modules.ModuleBase;
import need.clarity.modules.ModuleRegistry;
import need.clarity.util.InventoryHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
import net.minecraft.world.phys.Vec3;

public class AutoHitCrystal extends ModuleBase {
    private AutoHitCrystalStage autoHitCrystalStage;
    private long timestampMs;
    private long timestampMs1;
    private long timestampMs2;
    private boolean enabledState;
    private BlockPos targetBlockPos;
    private BlockPos targetBlockPos1;
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

    public AutoHitCrystal() {
        super("Auto Hit Crystal", "Combat");
        AutoHitCrystalStage var14 = AutoHitCrystalStage.NONE;
        this.autoHitCrystalStage = var14;
        this.timestampMs = 0L;
        this.timestampMs1 = 0L;
        this.timestampMs2 = 0L;
        this.enabledState = false;
        this.targetBlockPos = null;
        this.targetBlockPos1 = null;
        this.counterValue = -1;
        this.counterValue1 = -1;
        Random var26 = new Random();
        this.fieldVal = var26;
    }

    public void onDisable() {
        Minecraft var2 = Minecraft.getInstance();
        this.applyStep(var2);
        this.applyStep6();
    }

    public void onTick() {
        int var10 = 0;
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
            long var24 = System.currentTimeMillis();
            boolean var29 = var1.options.keyUse.isDown();
            if (!var29) {
                AutoHitCrystalStage var32 = this.autoHitCrystalStage;
                AutoHitCrystalStage var2 = AutoHitCrystalStage.NONE;
                if (var32 != var2) {
                    this.applyStep(var1);
                    this.applyStep6();
                    return;
                }
            }
            if (!var29) {
                return;
            }
            var10 = 0;
            while (true) {
                AutoHitCrystalStage var38 = this.autoHitCrystalStage;
                switch (this.autoHitCrystalStage.ordinal()) {
                    case 0 -> {}
                    case 1 -> {}
                    case 2 -> {}
                    case 3 -> {}
                    case 4 -> {}
                    default -> {}
                }
                this.applyStep3(var1, var24);
                this.applyStep2(var1, var24);
                this.applyStep4(var1, var24);
                this.applyStep(var1, var24);
                this.applyStep5(var1, var24);
                if (this.autoHitCrystalStage == var38) break;
                AutoHitCrystalStage var46 = this.autoHitCrystalStage;
                AutoHitCrystalStage var60 = AutoHitCrystalStage.NONE;
                if (var46 == var60) break;
                AutoHitCrystalStage var48 = this.autoHitCrystalStage;
                AutoHitCrystalStage var61 = AutoHitCrystalStage.DONE;
                if (var48 == var61) break;
                long var68 = this.timestampMs;
                if (Long.compare(var24, var68) < 0) break;
                var10 = (var10 + 1);
                if (var10 < 10) continue;
            }
            return;
        }
    }

    private void applyStep(Minecraft arg0, long arg1) {
        if (!this.isEligible1(arg0)) {
            return;
        }
        if (!this.isEligible(arg0)) {
            return;
        }
        this.applyStep1(arg0, arg1);
    }

    private boolean isEligible(Minecraft arg0) {
        int var54 = 0;
        BlockPos var59 = null;
        HitResult var10 = arg0.hitResult;
        if ((var10 instanceof EntityHitResult)) {
            if ((((EntityHitResult) var10).getEntity() instanceof EndCrystal)) {
                return this.isEligible2(arg0);
            }
        }
        HitResult var21 = arg0.hitResult;
        if ((var21 instanceof BlockHitResult)) {
            BlockHitResult var25 = ((BlockHitResult) var21);
            BlockPos var27 = var25.getBlockPos();
            BlockState var30 = arg0.level.getBlockState(var27);
            if (var30.isAir()) {
                return false;
            }
            Block var61 = Blocks.OBSIDIAN;
            if (!var30.is(var61)) {
                Block var62 = Blocks.BEDROCK;
                if (var30.is(var62)) {
                }
            }
            return this.isEligible2(arg0);
            if (this.isEligible3(arg0)) {
                if (!this.isEligible2(arg0)) {
                }
            }
            return false;
            Direction var44 = var25.getDirection();
            if (var30.canBeReplaced()) {
                var59 = var27;
            } else {
                var59 = var27.relative(var44);
            }
            BlockPos var8 = var59;
            BlockState var49 = arg0.level.getBlockState(var8);
            if (!var49.isAir()) {
                if (var49.canBeReplaced()) {
                }
            }
            var54 = 1;
            var54 = 0;
            return var54;
        }
        return false;
    }

    private void applyStep1(Minecraft arg0, long arg1) {
        BlockPos var45 = null;
        HitResult var13 = arg0.hitResult;
        if ((var13 instanceof EntityHitResult)) {
            if ((((EntityHitResult) var13).getEntity() instanceof EndCrystal)) {
                this.timestampMs = arg1;
                AutoHitCrystalStage var60 = AutoHitCrystalStage.SWITCH_CRYSTAL;
                this.autoHitCrystalStage = var60;
                return;
            }
        }
        HitResult var24 = arg0.hitResult;
        if ((var24 instanceof BlockHitResult)) {
            BlockHitResult var28 = ((BlockHitResult) var24);
        } else {
            return;
        }
        BlockPos var30 = var28.getBlockPos();
        BlockState var33 = arg0.level.getBlockState(var30);
        Block var62 = Blocks.OBSIDIAN;
        if (!var33.is(var62)) {
            Block var63 = Blocks.BEDROCK;
            if (var33.is(var63)) {
            }
        }
        this.timestampMs = arg1;
        AutoHitCrystalStage var75 = AutoHitCrystalStage.SWITCH_CRYSTAL;
        this.autoHitCrystalStage = var75;
        return;
        if (var33.isAir()) {
            return;
        }
        Direction var41 = var28.getDirection();
        if (var33.canBeReplaced()) {
            var45 = var30;
        } else {
            var45 = var30.relative(var41);
        }
        BlockPos var11 = var45;
        BlockState var48 = arg0.level.getBlockState(var11);
        if (!var48.isAir()) {
            if (!var48.canBeReplaced()) {
                return;
            }
        }
        this.targetBlockPos = var11;
        BlockPos var68 = var11.below();
        this.targetBlockPos1 = var68;
        this.enabledState = false;
        this.timestampMs = arg1;
        AutoHitCrystalStage var72 = AutoHitCrystalStage.SWITCH_OBSIDIAN;
        this.autoHitCrystalStage = var72;
    }

    private void applyStep2(Minecraft arg0, long arg1) {
        int var9 = 0;
        long var39 = this.timestampMs;
        if (Long.compare(arg1, var39) < 0) {
            return;
        }
        Item var41 = Items.OBSIDIAN;
        var9 = this.findHotbarItemSlot(arg0, var41);
        if (var9 == -1) {
            Item var42 = Items.BEDROCK;
            var9 = this.findHotbarItemSlot(arg0, var42);
        }
        if (var9 == -1) {
            this.applyStep6();
            return;
        }
        int var43 = var9;
        this.setRightClickDelay1(arg0, var43);
        ((KeyMappingAccessor) arg0.options.keyUse).setClickCount(0);
        this.setRightClickDelay(arg0, 4);
        this.enabledState = false;
        long var45 = this.evaluate();
        long var36 = (arg1 + var45);
        this.timestampMs = var36;
        AutoHitCrystalStage var38 = AutoHitCrystalStage.PLACE_OBSIDIAN;
        this.autoHitCrystalStage = var38;
    }

    private void applyStep3(Minecraft arg0, long arg1) {
        long var103 = this.timestampMs;
        if (Long.compare(arg1, var103) < 0) {
            return;
        }
        if (this.enabledState) {
            if (this.targetBlockPos != null) {
                ClientLevel var25 = arg0.level;
                BlockPos var76 = this.targetBlockPos;
                BlockState var26 = var25.getBlockState(var76);
                Block var77 = Blocks.OBSIDIAN;
                if (!var26.is(var77)) {
                    Block var78 = Blocks.BEDROCK;
                    if (var26.is(var78)) {
                    }
                }
                this.enabledState = false;
                this.timestampMs = arg1;
                AutoHitCrystalStage var82 = AutoHitCrystalStage.SWITCH_CRYSTAL;
                this.autoHitCrystalStage = var82;
                return;
            }
        }
        if (this.targetBlockPos == null) {
            HitResult var37 = arg0.hitResult;
            if ((var37 instanceof BlockHitResult)) {
                BlockHitResult var41 = ((BlockHitResult) var37);
            } else {
                this.applyStep6();
                return;
            }
            BlockPos var84 = this.evaluate(arg0, var41);
            this.targetBlockPos = var84;
            if (this.targetBlockPos == null) {
                this.applyStep6();
                return;
            }
            BlockPos var87 = this.targetBlockPos.below();
            this.targetBlockPos1 = var87;
        }
        ClientLevel var48 = arg0.level;
        BlockPos var89 = this.targetBlockPos;
        BlockState var49 = var48.getBlockState(var89);
        Block var90 = Blocks.OBSIDIAN;
        if (!var49.is(var90)) {
            Block var91 = Blocks.BEDROCK;
            if (var49.is(var91)) {
            }
        }
        this.enabledState = false;
        this.targetBlockPos = null;
        this.targetBlockPos1 = null;
        this.timestampMs = arg1;
        AutoHitCrystalStage var102 = AutoHitCrystalStage.SWITCH_CRYSTAL;
        this.autoHitCrystalStage = var102;
        return;
        if (!var49.isAir()) {
            if (!var49.canBeReplaced()) {
                this.applyStep6();
                return;
            }
        }
        double var111 = (((double) this.targetBlockPos.getX()) + 0.5);
        double var121 = ((double) this.targetBlockPos.getY());
        double var129 = (((double) this.targetBlockPos.getZ()) + 0.5);
        Vec3 var58 = new Vec3(var111, var121, var129);
        Direction var116 = Direction.UP;
        BlockPos var124 = this.targetBlockPos1;
        BlockHitResult var59 = new BlockHitResult(var58, var116, var124, false);
        MultiPlayerGameMode var61 = arg0.gameMode;
        LocalPlayer var93 = arg0.player;
        InteractionHand var114 = InteractionHand.MAIN_HAND;
        if (var61.useItemOn(var93, var114, var59).consumesAction()) {
            LocalPlayer var66 = arg0.player;
            InteractionHand var94 = InteractionHand.MAIN_HAND;
            var66.swing(var94);
            this.setRightClickDelay(arg0, 4);
        }
        this.enabledState = true;
    }

    private void applyStep4(Minecraft arg0, long arg1) {
        long var31 = this.timestampMs;
        if (Long.compare(arg1, var31) < 0) {
            return;
        }
        Item var33 = Items.END_CRYSTAL;
        int var12 = this.findHotbarItemSlot(arg0, var33);
        if (var12 == -1) {
            this.applyStep6();
            return;
        }
        this.setRightClickDelay1(arg0, var12);
        this.timestampMs1 = 0L;
        long var35 = this.evaluate2();
        long var26 = (arg1 + var35);
        this.timestampMs2 = var26;
        this.timestampMs = arg1;
        AutoHitCrystalStage var30 = AutoHitCrystalStage.DONE;
        this.autoHitCrystalStage = var30;
    }

    private void applyStep5(Minecraft arg0, long arg1) {
        int var72 = 0;
        ItemStack var13 = arg0.player.getMainHandItem();
        Item var5 = Items.END_CRYSTAL;
        if (!var13.is(var5)) {
            Item var6 = Items.END_CRYSTAL;
            int var16 = this.findHotbarItemSlot(arg0, var6);
            if (var16 == -1) {
                this.applyStep6();
                return;
            }
        }
        HitResult var21 = arg0.hitResult;
        if ((var21 instanceof BlockHitResult)) {
            BlockPos var27 = ((BlockHitResult) var21).getBlockPos();
            BlockState var30 = arg0.level.getBlockState(var27);
            Block var77 = Blocks.OBSIDIAN;
            if (!var30.is(var77)) {
                Block var78 = Blocks.BEDROCK;
                if (var30.is(var78)) {
                }
            }
            var72 = 1;
            var72 = 0;
            if (var72 != 0) {
                long var97 = this.timestampMs1;
                if (Long.compare(arg1, var97) >= 0) {
                    if (this.counterValue1 != -1) {
                        int var100 = this.counterValue1;
                        if (this.isEligible(arg0, var100)) {
                            return;
                        }
                        this.counterValue1 = -1;
                    }
                    if (!(this.isEligible(arg0, var27))) {
                        this.applyStep7(arg0);
                        long var105 = this.evaluate1();
                        long var86 = (arg1 + var105);
                        this.timestampMs1 = var86;
                    }
                    HitResult var49 = arg0.hitResult;
                    if ((var49 instanceof EntityHitResult)) {
                        Entity var55 = ((EntityHitResult) var49).getEntity();
                        if ((var55 instanceof EndCrystal)) {
                            EndCrystal var59 = ((EndCrystal) var55);
                            if (!var59.isRemoved()) {
                                if (var59.isAlive()) {
                                    long var103 = this.timestampMs2;
                                    if (Long.compare(arg1, var103) >= 0) {
                                        this.applyStep8(arg0);
                                        int var90 = var59.getId();
                                        this.counterValue1 = var90;
                                        long var108 = this.evaluate2();
                                        long var93 = (arg1 + var108);
                                        this.timestampMs2 = var93;
                                        this.applyStep();
                                        this.applyStep6();
                                    }
                                    return;
                                    this.setRightClickDelay1(arg0, var16);
                                    return;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void applyStep() {
        AutoCrystal var5 = ((AutoCrystal) ModuleRegistry.get("Auto Crystal"));
        if (var5 != null) {
            if (var5.isEnabled()) {
                int var12 = this.counterValue;
                var5.applyStep(var12);
                this.counterValue = -1;
            }
        } else {
            Minecraft var14 = Minecraft.getInstance();
            this.applyStep(var14);
        }
    }

    private void applyStep6() {
        AutoHitCrystalStage var2 = AutoHitCrystalStage.NONE;
        this.autoHitCrystalStage = var2;
        this.timestampMs = 0L;
        this.timestampMs1 = 0L;
        this.timestampMs2 = 0L;
        this.enabledState = false;
        this.targetBlockPos = null;
        this.targetBlockPos1 = null;
        this.counterValue1 = -1;
    }

    private void applyStep(Minecraft arg0) {
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

    private void applyStep7(Minecraft arg0) {
        KeyMappingAccessor var7 = ((KeyMappingAccessor) arg0.options.keyUse);
        int var12 = (((KeyMappingAccessor) arg0.options.keyUse).getClickCount() + 1);
        var7.setClickCount(var12);
    }

    private void applyStep8(Minecraft arg0) {
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

    private BlockPos evaluate(Minecraft arg0, BlockHitResult arg1) {
        BlockPos var22 = null;
        try {
            BlockPos var8 = arg1.getBlockPos();
            BlockState var11 = arg0.level.getBlockState(var8);
            if (var11.isAir()) {
            }
        } catch (Exception ignored) {
        }
        return null;
        try {
            Direction var18 = arg1.getDirection();
            if (var11.canBeReplaced()) {
                var22 = var8;
            } else {
                var22 = var8.relative(var18);
            }
            return var22;
        } catch (Exception ignored) {
        }
    }

    private EndCrystal evaluate(Minecraft arg0, BlockPos arg1) {
        Iterator var11 = arg0.level.entitiesForRendering().iterator();
        while (true) {
            if (!var11.hasNext()) break;
            Entity var16 = ((Entity) var11.next());
            if (!(var16 instanceof EndCrystal)) continue;
            EndCrystal var20 = ((EndCrystal) var16);
            if (var20.isRemoved()) continue;
            if (!BlockPos.containing(var20.position()).below().equals(arg1)) continue;
        }
        return var20;
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
        int var27 = 0;
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
                var27 = 1;
                return false;
            }
            var27 = 0;
            return var27;
        }
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

    private boolean isEligible1(Minecraft arg0) {
        Boolean var68 = true;
        boolean var13 = ((Boolean) this.getSetting("Work with Crystal", var68));
        Boolean var70 = true;
        boolean var17 = ((Boolean) this.getSetting("Work with Obsidian", var70));
        Boolean var72 = true;
        boolean var21 = ((Boolean) this.getSetting("Work with Sword", var72));
        Boolean var74 = true;
        boolean var25 = ((Boolean) this.getSetting("Work with Totem", var74));
        Boolean var76 = true;
        boolean var29 = ((Boolean) this.getSetting("Work with Fist", var76));
        if (!var13) {
            if (!var17) {
                if (!var21) {
                    if (!var25) {
                        if (!var29) {
                            return true;
                        }
                    }
                }
                ItemStack var38 = arg0.player.getMainHandItem();
                if (var13) {
                    Item var64 = Items.END_CRYSTAL;
                    if (var38.is(var64)) {
                        return true;
                    }
                }
                if (var17) {
                    Item var65 = Items.OBSIDIAN;
                    if (var38.is(var65)) {
                        return true;
                    }
                }
                if (var21) {
                    TagKey var66 = ItemTags.SWORDS;
                    if (var38.is(var66)) {
                        return true;
                    }
                }
                if (var25) {
                    Item var67 = Items.TOTEM_OF_UNDYING;
                    if (var38.is(var67)) {
                        return true;
                    }
                }
                if (var29) {
                    if (var38.isEmpty()) {
                        return true;
                    }
                }
                return false;
            }
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

    private boolean isEligible3(Minecraft arg0) {
        int var8 = 0;
        Item var4 = Items.OBSIDIAN;
        if (this.findHotbarItemSlot(arg0, var4) == -1) {
            Item var12 = Items.BEDROCK;
            if (this.findHotbarItemSlot(arg0, var12) != -1) {
            }
        }
        var8 = 1;
        var8 = 0;
        return var8;
    }

    private int findHotbarItemSlot(Minecraft arg0, Item arg1) {
        int var5 = 0;
        var5 = 0;
        while (true) {
            if (var5 >= 9) break;
            Inventory var9 = arg0.player.getInventory();
            int var14 = var5;
            if (var9.getItem(var14).is(arg1)) {
                return var5;
            }
            var5 = (var5 + 1);
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

    private long evaluate() {
        return this.evaluate("Place Obsidian Delay", 0L, 25L);
    }

    private long evaluate1() {
        return this.evaluate("Place Delay", 0L, 25L);
    }

    private long evaluate2() {
        return this.evaluate("Break Delay", 0L, 25L);
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
            long var12 = Long.parseLong(arg0.toString());
        } catch (NumberFormatException ignored) {
        }
        return var12;
    }

    static {
        reflectedField = evaluate();
    }

}
