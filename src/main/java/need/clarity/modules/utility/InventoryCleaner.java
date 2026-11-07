package need.clarity.modules.utility;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import need.clarity.config.ClarityConfig;
import need.clarity.modules.ModuleBase;
import need.clarity.modules.movement.MoveFix;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.core.DefaultedRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.HashedPatchMap;
import net.minecraft.network.HashedStack;
import net.minecraft.network.protocol.game.ServerboundContainerClickPacket;
import net.minecraft.network.protocol.game.ServerboundContainerClosePacket;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerInput;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class InventoryCleaner extends ModuleBase {
    private InventoryCleanerStage inventoryCleanerStage;
    private long timestampMs;
    private final List entriesList;
    private int counterValue;
    private long timestampMs1;
    private static final Random fieldVal;

    public InventoryCleaner() {
        super("Inventory Cleaner", "utility");
        InventoryCleanerStage var9 = InventoryCleanerStage.IDLE;
        this.inventoryCleanerStage = var9;
        this.timestampMs = 0L;
        ArrayList var12 = new ArrayList();
        this.entriesList = var12;
        this.counterValue = 0;
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
        Set var13 = this.evaluate();
        if (var13.isEmpty()) {
            return;
        }
        Boolean var24 = false;
        if (((Boolean) this.getSetting("Silent", var24))) {
            this.applyStep1(var1, var13);
        } else {
            this.applyStep(var1, var13);
        }
    }

    private void applyStep(Minecraft arg0, Set arg1) {
        int var32 = 0;
        int var10 = 0;
        int var44 = 0;
        int var13 = 0;
        if (!(arg0.screen instanceof InventoryScreen)) {
            return;
        }
        long var17 = System.currentTimeMillis();
        int var20 = this.computeColorOrIndex("Item Delay_lo", "Item Delay_hi");
        long var54 = this.timestampMs1;
        long var23 = (var17 - var54);
        long var57 = ((long) var20);
        if (Long.compare(var23, var57) < 0) {
            return;
        }
        Inventory var28 = arg0.player.getInventory();
        var10 = 0;
        while (true) {
            if (var10 >= 2) break;
            if (var10 == 0) {
                var32 = 9;
            } else {
                var32 = 0;
            }
            int var11 = var32;
            if (var10 == 0) {
                var44 = 36;
            } else {
                var44 = 9;
            }
            int var12 = var44;
            var13 = var11;
            while (true) {
                if (var13 >= var12) break;
                int var47 = var13;
                ItemStack var37 = var28.getItem(var47);
                if (var37.isEmpty()) {
                } else {
                    Item var49 = var37.getItem();
                    if (arg1.contains(var49)) break;
                }
                var13 = (var13 + 1);
            }
            var10 = (var10 + 1);
        }
        return;
        int var59 = var13;
        this.setRightClickDelay(arg0, var59);
        this.timestampMs1 = var17;
    }

    private void applyStep1(Minecraft arg0, Set arg1) {
        int var26 = 0;
        if (arg0.screen != null) {
            if (!(arg0.screen instanceof InventoryScreen)) {
                InventoryCleanerStage var12 = this.inventoryCleanerStage;
                InventoryCleanerStage var4 = InventoryCleanerStage.IDLE;
                if (var12 != var4) {
                    this.applyStep7(arg0);
                }
                MoveFix.applyStep(false);
                return;
            }
        }
        switch (this.inventoryCleanerStage.ordinal()) {
            case 0 -> {}
            case 1 -> {}
            case 2 -> {}
            case 3 -> {}
            default -> {}
        }
        this.applyStep3(arg0, arg1);
        this.applyStep4(arg0, arg1);
        this.applyStep2(arg0, arg1);
        this.applyStep(arg0);
        Boolean var38 = true;
        if (((Boolean) this.getSetting("Bypass", var38))) {
            InventoryCleanerStage var25 = this.inventoryCleanerStage;
            InventoryCleanerStage var33 = InventoryCleanerStage.IDLE;
            if (var25 != var33) {
                var26 = 1;
            }
        } else {
            var26 = 0;
        }
        MoveFix.applyStep(var26);
    }

    private void applyStep2(Minecraft arg0, Set arg1) {
        int var39 = 0;
        int var40 = 0;
        int var9 = 0;
        int var12 = 0;
        this.entriesList.clear();
        this.counterValue = 0;
        Inventory var18 = arg0.player.getInventory();
        var9 = 0;
        while (true) {
            if (var9 < 2) {
                if (var9 == 0) {
                    var40 = 9;
                }
            } else {
                if (this.entriesList.isEmpty()) {
                    return;
                }
                long var42 = evaluate();
                long var54 = ((long) this.computeColorOrIndex("Open Delay_lo", "Open Delay_hi"));
                long var44 = (var42 + var54);
                this.timestampMs = var44;
                InventoryCleanerStage var46 = InventoryCleanerStage.WAIT_OPEN;
                this.inventoryCleanerStage = var46;
                return;
                var40 = 0;
            }
            int var10 = var40;
            if (var9 == 0) {
                var39 = 36;
            } else {
                var39 = 9;
            }
            int var11 = var39;
            var12 = var10;
            while (true) {
                if (var12 >= var11) break;
                int var48 = var12;
                ItemStack var31 = var18.getItem(var48);
                if (var31.isEmpty()) {
                } else {
                    Item var50 = var31.getItem();
                    if (!arg1.contains(var50)) {
                    } else {
                        List var37 = this.entriesList;
                        Integer var52 = var12;
                        boolean var38 = var37.add(var52);
                    }
                    var12 = (var12 + 1);
                }
            }
            var9 = (var9 + 1);
        }
    }

    private void applyStep3(Minecraft arg0, Set arg1) {
        long var3 = evaluate();
        long var48 = this.timestampMs;
        if (Long.compare(var3, var48) < 0) {
            return;
        }
        this.applyStep5(arg0, arg1);
        int var13 = this.counterValue;
        int var26 = this.entriesList.size();
        if (var13 >= var26) {
            long var43 = evaluate();
            long var68 = ((long) this.computeColorOrIndex("Close Delay_lo", "Close Delay_hi"));
            long var45 = (var43 + var68);
            this.timestampMs = var45;
            InventoryCleanerStage var47 = InventoryCleanerStage.WAIT_CLOSE;
            this.inventoryCleanerStage = var47;
            return;
        }
        List var52 = this.entriesList;
        int var57 = this.counterValue;
        int var55 = ((Integer) var52.get(var57));
        this.setRightClickDelay(arg0, var55);
        int var29 = (this.counterValue + 1);
        this.counterValue = var29;
        int var17 = this.counterValue;
        int var32 = this.entriesList.size();
        if (var17 < var32) {
            long var38 = evaluate();
            long var64 = ((long) this.computeColorOrIndex("Silent Item Delay_lo", "Silent Item Delay_hi"));
            long var40 = (var38 + var64);
            this.timestampMs = var40;
            InventoryCleanerStage var42 = InventoryCleanerStage.THROWING;
            this.inventoryCleanerStage = var42;
        } else {
            long var33 = evaluate();
            long var60 = ((long) this.computeColorOrIndex("Close Delay_lo", "Close Delay_hi"));
            long var35 = (var33 + var60);
            this.timestampMs = var35;
            InventoryCleanerStage var37 = InventoryCleanerStage.WAIT_CLOSE;
            this.inventoryCleanerStage = var37;
        }
    }

    private void applyStep4(Minecraft arg0, Set arg1) {
        long var3 = evaluate();
        long var46 = this.timestampMs;
        if (Long.compare(var3, var46) < 0) {
            return;
        }
        this.applyStep5(arg0, arg1);
        int var13 = this.counterValue;
        int var25 = this.entriesList.size();
        if (var13 >= var25) {
            long var26 = evaluate();
            long var56 = ((long) this.computeColorOrIndex("Close Delay_lo", "Close Delay_hi"));
            long var28 = (var26 + var56);
            this.timestampMs = var28;
            InventoryCleanerStage var30 = InventoryCleanerStage.WAIT_CLOSE;
            this.inventoryCleanerStage = var30;
            return;
        }
        List var50 = this.entriesList;
        int var59 = this.counterValue;
        int var53 = ((Integer) var50.get(var59));
        this.setRightClickDelay(arg0, var53);
        int var33 = (this.counterValue + 1);
        this.counterValue = var33;
        int var19 = this.counterValue;
        int var36 = this.entriesList.size();
        if (var19 < var36) {
            long var42 = evaluate();
            long var66 = ((long) this.computeColorOrIndex("Silent Item Delay_lo", "Silent Item Delay_hi"));
            long var44 = (var42 + var66);
            this.timestampMs = var44;
        } else {
            long var37 = evaluate();
            long var62 = ((long) this.computeColorOrIndex("Close Delay_lo", "Close Delay_hi"));
            long var39 = (var37 + var62);
            this.timestampMs = var39;
            InventoryCleanerStage var41 = InventoryCleanerStage.WAIT_CLOSE;
            this.inventoryCleanerStage = var41;
        }
    }

    private void applyStep5(Minecraft arg0, Set arg1) {
        while (true) {
            int var8 = this.counterValue;
            int var24 = this.entriesList.size();
            if (var8 < var24) {
                List var10 = this.entriesList;
                int var26 = this.counterValue;
                int var13 = ((Integer) var10.get(var26));
                ItemStack var17 = arg0.player.getInventory().getItem(var13);
                if (!var17.isEmpty()) {
                    Item var29 = var17.getItem();
                    if (arg1.contains(var29)) {
                    }
                }
            }
            return;
            int var31 = (this.counterValue + 1);
            this.counterValue = var31;
        }
    }

    private void applyStep(Minecraft arg0) {
        long var2 = evaluate();
        long var9 = this.timestampMs;
        if (Long.compare(var2, var9) < 0) {
            return;
        }
        this.applyStep6(arg0);
        this.applyStep();
    }

    private void setRightClickDelay(Minecraft arg0, int arg1) {
        int var19 = computeColorOrIndex(arg1);
        if (arg0.player.getInventory().getItem(arg1).isEmpty()) {
            return;
        }
        int var29 = arg0.player.inventoryMenu.containerId;
        int var33 = arg0.player.inventoryMenu.getStateId();
        HashedPatchMap.HashGenerator var37 = arg0.player.connection.decoratedHashOpsGenenerator();
        Int2ObjectArrayMap var38 = new Int2ObjectArrayMap();
        HashedStack var50 = HashedStack.create(ItemStack.EMPTY, var37);
        Object var40 = var38.put(var19, var50);
        ClientPacketListener var43 = arg0.player.connection;
        int var53 = ((short) var19);
        ContainerInput var10 = ContainerInput.THROW;
        HashedStack var12 = HashedStack.EMPTY;
        ServerboundContainerClickPacket var48 = new ServerboundContainerClickPacket(var29, var33, var53, 1, var10, var38, var12);
        var43.send(var48);
        Inventory var46 = arg0.player.getInventory();
        ItemStack var51 = ItemStack.EMPTY;
        var46.setItem(arg1, var51);
    }

    private void applyStep6(Minecraft arg0) {
        ClientPacketListener var6 = arg0.player.connection;
        int var9 = arg0.player.inventoryMenu.containerId;
        ServerboundContainerClosePacket var3 = new ServerboundContainerClosePacket(var9);
        var6.send(var3);
    }

    private void applyStep7(Minecraft arg0) {
        InventoryCleanerStage var4 = this.inventoryCleanerStage;
        InventoryCleanerStage var3 = InventoryCleanerStage.IDLE;
        if (var4 != var3) {
            this.applyStep6(arg0);
        }
        MoveFix.applyStep(false);
        this.applyStep();
    }

    private void applyStep() {
        InventoryCleanerStage var2 = InventoryCleanerStage.IDLE;
        this.inventoryCleanerStage = var2;
        this.timestampMs = 0L;
        this.counterValue = 0;
        this.entriesList.clear();
    }

    private Set evaluate() {
        String var10 = null;
        try {
            JsonObject var12 = ClarityConfig.getCurrent().getAsJsonObject("Inventory Cleaner");
            if (var12 == null) {
                Set var14 = Collections.emptySet();
            }
        } catch (Exception ignored) {
        }
        return var14;
        try {
            JsonObject var18 = var12.getAsJsonObject("values");
            if (var18 == null) {
                Set var55 = Collections.emptySet();
            }
        } catch (Exception ignored) {
        }
        return var55;
        try {
            JsonElement var21 = var18.get("Items");
            if (var21 != null) {
                if (!var21.isJsonArray()) {
                }
            }
            Set var54 = Collections.emptySet();
        } catch (Exception ignored) {
        }
        return var54;
        try {
            JsonArray var26 = var21.getAsJsonArray();
            if (var26.size() == 0) {
                Set var53 = Collections.emptySet();
            }
        } catch (Exception ignored) {
        }
        return var53;
        try {
            HashSet var29 = new HashSet();
            Iterator var31 = var26.iterator();
            while (true) {
                if (!var31.hasNext()) break;
                JsonElement var37 = ((JsonElement) var31.next());
                if (!var37.isJsonPrimitive()) {
                }
                try {
                    var10 = var37.getAsString();
                    if (!var10.contains(":")) {
                        var10 = "minecraft:" + (var10);
                    }
                    DefaultedRegistry var46 = BuiltInRegistries.ITEM;
                    Identifier var60 = Identifier.parse(var10);
                    Item var48 = ((Item) var46.getValue(var60));
                    Item var61 = Items.AIR;
                    if (var48 == var61) continue;
                    boolean var51 = var29.add(var48);
                } catch (Exception ignored) {
                }
            }
        } catch (Exception ignored) {
        }
        return var29;
    }

    private static int computeColorOrIndex(int arg0) {
        int var4 = 0;
        if (arg0 < 9) {
            var4 = (arg0 + 36);
        } else {
            var4 = arg0;
        }
        return var4;
    }

    private int computeColorOrIndex(String arg0, String arg1) {
        Integer var26 = 0;
        int var11 = ((Integer) this.getSetting(arg0, var26));
        Integer var28 = 0;
        int var15 = ((Integer) this.getSetting(arg1, var28));
        if (var11 >= var15) {
            return Math.max(0, var11);
        }
        Random var24 = fieldVal;
        int var31 = ((var15 - var11) + 1);
        int var25 = var24.nextInt(var31);
        return (var11 + var25);
    }

    private static long evaluate() {
        return System.currentTimeMillis();
    }

    static {
        Random var0 = new Random();
        fieldVal = var0;
    }

}
