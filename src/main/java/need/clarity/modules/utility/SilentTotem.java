package need.clarity.modules.utility;

import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import java.util.Random;
import need.clarity.modules.ModuleBase;
import need.clarity.modules.movement.MoveFix;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.HashedPatchMap;
import net.minecraft.network.HashedStack;
import net.minecraft.network.protocol.game.ServerboundContainerClickPacket;
import net.minecraft.network.protocol.game.ServerboundContainerClosePacket;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerInput;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class SilentTotem extends ModuleBase {
    private SilentTotemStage silentTotemStage;
    private long timestampMs;
    private static final Random fieldVal;

    public SilentTotem() {
        super("Silent Totem", "utility");
        SilentTotemStage var6 = SilentTotemStage.IDLE;
        this.silentTotemStage = var6;
        this.timestampMs = 0L;
    }

    public void onDisable() {
        MoveFix.applyStep(false);
        SilentTotemStage var2 = SilentTotemStage.IDLE;
        this.silentTotemStage = var2;
    }

    public void onTick() {
        int var33 = 0;
        Minecraft var1 = Minecraft.getInstance();
        if (!this.isEnabled()) {
            return;
        }
        if (var1.player != null) {
            if (var1.level == null) {
            }
        }
        return;
        if (var1.screen != null) {
            if (!(var1.screen instanceof InventoryScreen)) {
                SilentTotemStage var18 = this.silentTotemStage;
                SilentTotemStage var2 = SilentTotemStage.IDLE;
                if (var18 != var2) {
                    this.applyStep4(var1);
                }
                return;
            }
        }
        switch (this.silentTotemStage.ordinal()) {
            case 0 -> {}
            case 1 -> {}
            case 2 -> {}
            default -> {}
        }
        this.applyStep1(var1);
        this.applyStep(var1);
        this.applyStep2(var1);
        Boolean var40 = true;
        if (((Boolean) this.getSetting("Bypass", var40))) {
            SilentTotemStage var30 = this.silentTotemStage;
            SilentTotemStage var37 = SilentTotemStage.IDLE;
            if (var30 != var37) {
                var33 = 1;
            }
        } else {
            var33 = 0;
        }
        MoveFix.applyStep(var33);
    }

    private void applyStep(Minecraft arg0) {
        if (this.isEligible(arg0)) {
            return;
        }
        if (this.computeColorOrIndex(arg0) == -1) {
            return;
        }
        long var14 = evaluate();
        long var20 = ((long) this.computeColorOrIndex("Open Delay_lo", "Open Delay_hi"));
        long var16 = (var14 + var20);
        this.timestampMs = var16;
        SilentTotemStage var18 = SilentTotemStage.WAIT_OPEN;
        this.silentTotemStage = var18;
    }

    private void applyStep1(Minecraft arg0) {
        int var24 = 0;
        long var2 = evaluate();
        long var37 = this.timestampMs;
        if (Long.compare(var2, var37) < 0) {
            return;
        }
        if (this.isEligible(arg0)) {
            this.applyStep4(arg0);
            return;
        }
        int var15 = this.computeColorOrIndex(arg0);
        if (var15 == -1) {
            SilentTotemStage var27 = SilentTotemStage.IDLE;
            this.silentTotemStage = var27;
            return;
        }
        if (var15 < 9) {
            var24 = (var15 + 36);
        } else {
            var24 = var15;
        }
        int var10 = var24;
        this.applyStep(arg0, var10, var15);
        long var31 = evaluate();
        long var42 = ((long) this.computeColorOrIndex("Close Delay_lo", "Close Delay_hi"));
        long var33 = (var31 + var42);
        this.timestampMs = var33;
        SilentTotemStage var35 = SilentTotemStage.WAIT_CLOSE;
        this.silentTotemStage = var35;
    }

    private void applyStep2(Minecraft arg0) {
        long var2 = evaluate();
        long var10 = this.timestampMs;
        if (Long.compare(var2, var10) < 0) {
            return;
        }
        this.applyStep3(arg0);
        SilentTotemStage var9 = SilentTotemStage.IDLE;
        this.silentTotemStage = var9;
    }

    private void applyStep(Minecraft arg0, int arg1, int arg2) {
        ItemStack var67 = null;
        ItemStack var58 = null;
        int var22 = arg0.player.inventoryMenu.containerId;
        int var26 = arg0.player.inventoryMenu.getStateId();
        HashedPatchMap.HashGenerator var30 = arg0.player.connection.decoratedHashOpsGenenerator();
        ItemStack var34 = arg0.player.getInventory().getItem(arg2);
        ItemStack var37 = arg0.player.getOffhandItem();
        Int2ObjectArrayMap var38 = new Int2ObjectArrayMap();
        if (var37.isEmpty()) {
            var58 = ItemStack.EMPTY;
        } else {
            var58 = var37;
        }
        HashedStack var59 = HashedStack.create(var58, var30);
        Object var40 = var38.put(arg1, var59);
        HashedStack var61 = HashedStack.create(var34, var30);
        Object var42 = var38.put(45, var61);
        ClientPacketListener var45 = arg0.player.connection;
        int var70 = ((short) arg1);
        ContainerInput var11 = ContainerInput.SWAP;
        HashedStack var13 = HashedStack.EMPTY;
        ServerboundContainerClickPacket var54 = new ServerboundContainerClickPacket(var22, var26, var70, 40, var11, var38, var13);
        var45.send(var54);
        Inventory var48 = arg0.player.getInventory();
        if (var37.isEmpty()) {
            var67 = ItemStack.EMPTY;
        } else {
            var67 = var37.copy();
        }
        var48.setItem(arg2, var67);
        Inventory var51 = arg0.player.getInventory();
        ItemStack var65 = var34.copy();
        var51.setItem(40, var65);
    }

    private void applyStep3(Minecraft arg0) {
        ClientPacketListener var6 = arg0.player.connection;
        int var9 = arg0.player.inventoryMenu.containerId;
        ServerboundContainerClosePacket var3 = new ServerboundContainerClosePacket(var9);
        var6.send(var3);
    }

    private void applyStep4(Minecraft arg0) {
        SilentTotemStage var4 = this.silentTotemStage;
        SilentTotemStage var3 = SilentTotemStage.IDLE;
        if (var4 != var3) {
            this.applyStep3(arg0);
        }
        MoveFix.applyStep(false);
        SilentTotemStage var9 = SilentTotemStage.IDLE;
        this.silentTotemStage = var9;
    }

    private boolean isEligible(Minecraft arg0) {
        ItemStack var5 = arg0.player.getOffhandItem();
        Item var3 = Items.TOTEM_OF_UNDYING;
        return var5.is(var3);
    }

    private int computeColorOrIndex(Minecraft arg0) {
        int var5 = 0;
        int var26 = 0;
        Inventory var7 = arg0.player.getInventory();
        var5 = 9;
        while (true) {
            if (var5 >= 36) break;
            int var21 = var5;
            ItemStack var11 = var7.getItem(var21);
            Item var22 = Items.TOTEM_OF_UNDYING;
            if (var11.is(var22)) {
                return var5;
            }
            var5 = (var5 + 1);
        }
        var26 = 0;
        while (true) {
            if (var26 >= 9) break;
            int var24 = var26;
            ItemStack var18 = var7.getItem(var24);
            Item var25 = Items.TOTEM_OF_UNDYING;
            if (var18.is(var25)) {
                return var26;
            }
            var26 = (var26 + 1);
        }
        return -1;
    }

    private int computeColorOrIndex(String arg0, String arg1) {
        Integer var26 = 0;
        int var11 = ((Integer) this.getSetting(arg0, var26));
        Integer var28 = 0;
        int var15 = ((Integer) this.getSetting(arg1, var28));
        if (var11 >= var15) {
            return Math.max(0, var11);
        }
        Random var23 = fieldVal;
        int var31 = ((var15 - var11) + 1);
        int var24 = var23.nextInt(var31);
        return (var11 + var24);
    }

    private static long evaluate() {
        return System.currentTimeMillis();
    }

    static {
        Random var0 = new Random();
        fieldVal = var0;
    }

}
