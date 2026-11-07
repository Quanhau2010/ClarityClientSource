package need.clarity.modules.utility;

import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import need.clarity.modules.ModuleBase;
import need.clarity.modules.movement.MoveFix;
import need.clarity.util.ContainerScreenHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.HashedPatchMap;
import net.minecraft.network.HashedStack;
import net.minecraft.network.protocol.game.ServerboundContainerClickPacket;
import net.minecraft.network.protocol.game.ServerboundContainerClosePacket;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerInput;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;

public class AutoArmour extends ModuleBase {
    private static final int[] intBuffer;
    private static final EquipmentSlot[] fieldVal;
    private AutoArmourStage autoArmourStage;
    private long timestampMs;
    private final List entriesList;
    private long timestampMs1;
    private static final Random fieldVal1;

    public AutoArmour() {
        super("Auto Armour", "utility");
        AutoArmourStage var8 = AutoArmourStage.IDLE;
        this.autoArmourStage = var8;
        this.timestampMs = 0L;
        ArrayList var11 = new ArrayList();
        this.entriesList = var11;
        this.timestampMs1 = 0L;
    }

    public void onDisable() {
        MoveFix.applyStep(false);
        this.applyStep();
    }

    public void onTick() {
        Minecraft var1 = Minecraft.getInstance();
        if (!this.isEnabled()) {
            return;
        }
        if (var1.player != null) {
            if (var1.level == null) {
            }
        }
        return;
        Boolean var19 = false;
        if (((Boolean) this.getSetting("Silent", var19))) {
            this.applyStep1(var1);
        } else {
            this.applyStep(var1);
        }
    }

    private void applyStep(Minecraft arg0) {
        int var9 = 0;
        if (!(arg0.screen instanceof InventoryScreen)) {
            return;
        }
        long var13 = System.currentTimeMillis();
        int var16 = this.computeColorOrIndex("Delay_lo", "Delay_hi");
        long var43 = this.timestampMs1;
        long var19 = (var13 - var43);
        long var46 = ((long) var16);
        if (Long.compare(var19, var46) < 0) {
            return;
        }
        var9 = 0;
        while (true) {
            int var23 = var9;
            int var33 = intBuffer.length;
            if (var23 >= var33) break;
            int var48 = var9;
            int var25 = this.computeColorOrIndex(arg0, var48);
            if (var25 == -1) {
            } else {
                if (arg0.gameMode != null) {
                    MultiPlayerGameMode var30 = arg0.gameMode;
                    int var39 = arg0.player.inventoryMenu.containerId;
                    int var50 = this.computeColorOrIndex(var25);
                    LocalPlayer var52 = arg0.player;
                    ContainerScreenHelper.shiftClick(var30, var39, var50, var52);
                    this.timestampMs1 = var13;
                    return;
                }
            }
            var9 = (var9 + 1);
        }
    }

    private void applyStep1(Minecraft arg0) {
        int var27 = 0;
        if (arg0.screen != null) {
            if (!(arg0.screen instanceof InventoryScreen)) {
                AutoArmourStage var11 = this.autoArmourStage;
                AutoArmourStage var3 = AutoArmourStage.IDLE;
                if (var11 != var3) {
                    this.applyStep6(arg0);
                }
                MoveFix.applyStep(false);
                return;
            }
        }
        switch (this.autoArmourStage.ordinal()) {
            case 0 -> {}
            case 1 -> {}
            case 2 -> {}
            default -> {}
        }
        this.applyStep3(arg0);
        this.applyStep2(arg0);
        this.applyStep4(arg0);
        Boolean var34 = true;
        if (((Boolean) this.getSetting("Bypass", var34))) {
            AutoArmourStage var24 = this.autoArmourStage;
            AutoArmourStage var31 = AutoArmourStage.IDLE;
            if (var24 != var31) {
                var27 = 1;
            }
        } else {
            var27 = 0;
        }
        MoveFix.applyStep(var27);
    }

    private void applyStep2(Minecraft arg0) {
        int var8 = 0;
        this.entriesList.clear();
        var8 = 0;
        while (true) {
            int var12 = var8;
            int var24 = intBuffer.length;
            if (var12 < var24) {
                int var4 = var8;
                int var14 = this.computeColorOrIndex(arg0, var4);
                if (var14 == -1) {
                }
            } else {
                if (this.entriesList.isEmpty()) {
                    return;
                }
                long var28 = evaluate();
                long var35 = ((long) this.computeColorOrIndex("Open Delay_lo", "Open Delay_hi"));
                long var30 = (var28 + var35);
                this.timestampMs = var30;
                AutoArmourStage var32 = AutoArmourStage.WAIT_OPEN;
                this.autoArmourStage = var32;
                return;
                List var17 = this.entriesList;
                int var5 = var8;
                ArmourSwapAction var27 = new ArmourSwapAction(var5, var14);
                boolean var18 = var17.add(var27);
            }
            var8 = (var8 + 1);
        }
    }

    private void applyStep3(Minecraft arg0) {
        long var2 = evaluate();
        long var56 = this.timestampMs;
        if (Long.compare(var2, var56) < 0) {
            return;
        }
        Iterator var17 = this.entriesList.iterator();
        while (true) {
            if (!var17.hasNext()) break;
            ArmourSwapAction var22 = ((ArmourSwapAction) var17.next());
            Inventory var25 = arg0.player.getInventory();
            int var44 = var22.counterValue1;
            ItemStack var26 = var25.getItem(var44);
            if (var26.isEmpty()) continue;
            EquipmentSlot[] var58 = fieldVal;
            int var64 = var22.counterValue;
            EquipmentSlot var59 = var58[var64];
            if (!(this.isEligible(var26, var59))) {
            }
            EquipmentSlot[] var31 = fieldVal;
            int var47 = var22.counterValue;
            EquipmentSlot var32 = var31[var47];
            if (!arg0.player.getItemBySlot(var32).isEmpty()) {
                int var61 = var22.counterValue;
                this.setRightClickDelay(arg0, var61);
            }
            int var63 = var22.counterValue1;
            int var66 = var22.counterValue;
            this.applyStep(arg0, var63, var66);
        }
        this.entriesList.clear();
        long var51 = evaluate();
        long var69 = ((long) this.computeColorOrIndex("Close Delay_lo", "Close Delay_hi"));
        long var53 = (var51 + var69);
        this.timestampMs = var53;
        AutoArmourStage var55 = AutoArmourStage.WAIT_CLOSE;
        this.autoArmourStage = var55;
    }

    private void applyStep4(Minecraft arg0) {
        long var2 = evaluate();
        long var9 = this.timestampMs;
        if (Long.compare(var2, var9) < 0) {
            return;
        }
        this.applyStep5(arg0);
        this.applyStep();
    }

    private int computeColorOrIndex(Minecraft arg0, int arg1) {
        int var8 = 0;
        int var9 = 0;
        int var11 = 0;
        int var45 = 0;
        int var14 = 0;
        int var46 = 0;
        EquipmentSlot var17 = fieldVal[arg1];
        ItemStack var20 = arg0.player.getItemBySlot(var17);
        var8 = -1;
        var9 = this.computeColorOrIndex(var20);
        Inventory var26 = arg0.player.getInventory();
        var11 = 0;
        while (true) {
            if (var11 < 2) {
                if (var11 == 0) {
                    var46 = 9;
                }
            } else {
                return var8;
                var46 = 0;
            }
            int var12 = var46;
            if (var11 == 0) {
                var45 = 36;
            } else {
                var45 = 9;
            }
            int var13 = var45;
            var14 = var12;
            while (true) {
                if (var14 >= var13) break;
                int var51 = var14;
                ItemStack var34 = var26.getItem(var51);
                if (var34.isEmpty()) {
                } else {
                    if (!(this.isEligible(var34, var17))) {
                    } else {
                        int var40 = this.computeColorOrIndex(var34, var17);
                        int var54 = var9;
                        if (var40 > var54) {
                            var9 = var40;
                            var8 = var14;
                        }
                        var14 = (var14 + 1);
                    }
                }
            }
            var11 = (var11 + 1);
        }
    }

    private boolean isEligible(ItemStack arg0, EquipmentSlot arg1) {
        boolean var10 = false;
        if (arg0.isEmpty()) {
            return false;
        }
        int[] var6 = EquipmentSlotSwitchMap.intBuffer;
        int var14 = arg1.ordinal();
        switch (var6[var14]) {
            case 1 -> {}
            case 2 -> {}
            case 3 -> {}
            case 4 -> {}
            default -> {}
        }
        TagKey var18 = ItemTags.CHEST_ARMOR;
        var10 = arg0.is(var18);
        TagKey var15 = ItemTags.HEAD_ARMOR;
        var10 = arg0.is(var15);
        TagKey var17 = ItemTags.LEG_ARMOR;
        var10 = arg0.is(var17);
        TagKey var16 = ItemTags.FOOT_ARMOR;
        var10 = arg0.is(var16);
        var10 = false;
        return var10;
    }

    private int computeColorOrIndex(ItemStack arg0) {
        double var8 = 0.0;
        double var10 = 0.0;
        double var12 = 0.0;
        if (arg0.isEmpty()) {
            return 0;
        }
        var8 = 0.0;
        var10 = 0.0;
        var12 = 0.0;
        try {
            DataComponentType var3 = DataComponents.ATTRIBUTE_MODIFIERS;
            ItemAttributeModifiers var29 = ((ItemAttributeModifiers) arg0.get(var3));
            if (var29 == null) {
            }
        } catch (Throwable ignored) {
        }
        return 0;
        try {
            Iterator var36 = var29.modifiers().iterator();
            while (true) {
                if (!var36.hasNext()) break;
                ItemAttributeModifiers.Entry var41 = ((ItemAttributeModifiers.Entry) var36.next());
                Holder var43 = var41.attribute();
                double var46 = var41.modifier().amount();
                Holder var76 = Attributes.ARMOR;
                if (var43.is(var76)) {
                    var8 = (var8 + var46);
                }
                Holder var77 = Attributes.ARMOR_TOUGHNESS;
                if (var43.is(var77)) {
                    var10 = (var10 + var46);
                }
                Holder var78 = Attributes.KNOCKBACK_RESISTANCE;
                if (!var43.is(var78)) continue;
                var12 = (var12 + var46);
            }
            double var68 = (var8 * 1000.0);
            double var87 = (var10 * 100.0);
            double var70 = (var68 + var87);
            double var91 = (var12 * 10.0);
            return ((int) (var70 + var91));
        } catch (Throwable ignored) {
        }
    }

    private int computeColorOrIndex(ItemStack arg0, EquipmentSlot arg1) {
        double var9 = 0.0;
        double var11 = 0.0;
        double var13 = 0.0;
        if (arg0.isEmpty()) {
            return 0;
        }
        var9 = 0.0;
        var11 = 0.0;
        var13 = 0.0;
        try {
            DataComponentType var4 = DataComponents.ATTRIBUTE_MODIFIERS;
            ItemAttributeModifiers var31 = ((ItemAttributeModifiers) arg0.get(var4));
            if (var31 == null) {
            }
        } catch (Throwable ignored) {
        }
        return 0;
        try {
            Iterator var38 = var31.modifiers().iterator();
            while (true) {
                if (!var38.hasNext()) break;
                ItemAttributeModifiers.Entry var52 = ((ItemAttributeModifiers.Entry) var38.next());
                if (!var52.slot().test(arg1)) {
                }
                Holder var57 = var52.attribute();
                double var60 = var52.modifier().amount();
                Holder var81 = Attributes.ARMOR;
                if (var57.is(var81)) {
                    var9 = (var9 + var60);
                }
                Holder var82 = Attributes.ARMOR_TOUGHNESS;
                if (var57.is(var82)) {
                    var11 = (var11 + var60);
                }
                Holder var83 = Attributes.KNOCKBACK_RESISTANCE;
                if (!var57.is(var83)) continue;
                var13 = (var13 + var60);
            }
            double var43 = (var9 * 1000.0);
            double var86 = (var11 * 100.0);
            double var45 = (var43 + var86);
            double var90 = (var13 * 10.0);
            return ((int) (var45 + var90));
        } catch (Throwable ignored) {
        }
    }

    private void setRightClickDelay(Minecraft arg0, int arg1) {
        EquipmentSlot var19 = fieldVal[arg1];
        int var21 = intBuffer[arg1];
        int var25 = arg0.player.inventoryMenu.containerId;
        int var29 = arg0.player.inventoryMenu.getStateId();
        HashedPatchMap.HashGenerator var33 = arg0.player.connection.decoratedHashOpsGenenerator();
        Int2ObjectArrayMap var34 = new Int2ObjectArrayMap();
        HashedStack var46 = HashedStack.create(ItemStack.EMPTY, var33);
        Object var36 = var34.put(var21, var46);
        ClientPacketListener var39 = arg0.player.connection;
        int var49 = ((short) var21);
        ContainerInput var10 = ContainerInput.QUICK_MOVE;
        HashedStack var12 = HashedStack.EMPTY;
        ServerboundContainerClickPacket var44 = new ServerboundContainerClickPacket(var25, var29, var49, 0, var10, var34, var12);
        var39.send(var44);
        LocalPlayer var41 = arg0.player;
        ItemStack var47 = ItemStack.EMPTY;
        var41.setItemSlot(var19, var47);
    }

    private void applyStep(Minecraft arg0, int arg1, int arg2) {
        EquipmentSlot var22 = fieldVal[arg2];
        int var24 = intBuffer[arg2];
        int var26 = this.computeColorOrIndex(arg1);
        ItemStack var30 = arg0.player.getInventory().getItem(arg1);
        int var34 = arg0.player.inventoryMenu.containerId;
        int var38 = arg0.player.inventoryMenu.getStateId();
        HashedPatchMap.HashGenerator var42 = arg0.player.connection.decoratedHashOpsGenenerator();
        Int2ObjectArrayMap var43 = new Int2ObjectArrayMap();
        HashedStack var64 = HashedStack.create(ItemStack.EMPTY, var42);
        Object var45 = var43.put(var26, var64);
        HashedStack var66 = HashedStack.create(var30, var42);
        Object var47 = var43.put(var24, var66);
        ClientPacketListener var50 = arg0.player.connection;
        int var72 = ((short) var26);
        ContainerInput var11 = ContainerInput.QUICK_MOVE;
        HashedStack var13 = HashedStack.EMPTY;
        ServerboundContainerClickPacket var61 = new ServerboundContainerClickPacket(var34, var38, var72, 0, var11, var43, var13);
        var50.send(var61);
        Inventory var53 = arg0.player.getInventory();
        ItemStack var67 = ItemStack.EMPTY;
        var53.setItem(arg1, var67);
        LocalPlayer var55 = arg0.player;
        ItemStack var69 = var30.copy();
        var55.setItemSlot(var22, var69);
    }

    private void applyStep5(Minecraft arg0) {
        ClientPacketListener var6 = arg0.player.connection;
        int var9 = arg0.player.inventoryMenu.containerId;
        ServerboundContainerClosePacket var3 = new ServerboundContainerClosePacket(var9);
        var6.send(var3);
    }

    private void applyStep6(Minecraft arg0) {
        AutoArmourStage var4 = this.autoArmourStage;
        AutoArmourStage var3 = AutoArmourStage.IDLE;
        if (var4 != var3) {
            this.applyStep5(arg0);
        }
        MoveFix.applyStep(false);
        this.applyStep();
    }

    private void applyStep() {
        AutoArmourStage var2 = AutoArmourStage.IDLE;
        this.autoArmourStage = var2;
        this.timestampMs = 0L;
        this.entriesList.clear();
    }

    private int computeColorOrIndex(int arg0) {
        int var5 = 0;
        if (arg0 < 9) {
            var5 = (arg0 + 36);
        } else {
            var5 = arg0;
        }
        return var5;
    }

    private int computeColorOrIndex(String arg0, String arg1) {
        Integer var26 = 0;
        int var11 = ((Integer) this.getSetting(arg0, var26));
        Integer var28 = 0;
        int var15 = ((Integer) this.getSetting(arg1, var28));
        if (var11 >= var15) {
            return Math.max(0, var11);
        }
        Random var23 = fieldVal1;
        int var31 = ((var15 - var11) + 1);
        int var24 = var23.nextInt(var31);
        return (var11 + var24);
    }

    private static long evaluate() {
        return System.currentTimeMillis();
    }

    static {
        int[] var3 = new int[4];
        var3[0] = 5;
        var3[1] = 6;
        var3[2] = 7;
        var3[3] = 8;
        intBuffer = var3;
        EquipmentSlot[] var5 = new EquipmentSlot[4];
        EquipmentSlot var17 = EquipmentSlot.HEAD;
        var5[0] = var17;
        EquipmentSlot var18 = EquipmentSlot.CHEST;
        var5[1] = var18;
        EquipmentSlot var19 = EquipmentSlot.LEGS;
        var5[2] = var19;
        EquipmentSlot var20 = EquipmentSlot.FEET;
        var5[3] = var20;
        fieldVal = var5;
        Random var6 = new Random();
        fieldVal1 = var6;
    }

}
