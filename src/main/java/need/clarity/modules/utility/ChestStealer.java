package need.clarity.modules.utility;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import need.clarity.api.Hooks;
import need.clarity.config.ClarityConfig;
import need.clarity.modules.ModuleBase;
import need.clarity.modules.movement.MoveFix;
import need.clarity.util.ContainerScreenHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.DefaultedRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.protocol.game.ServerboundContainerClosePacket;
import net.minecraft.resources.Identifier;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class ChestStealer extends ModuleBase {
    private static ChestStealer INSTANCE;
    private ChestStealerStage chestStealerStage;
    private long timestampMs;
    private boolean enabledState;
    private AbstractContainerMenu fieldVal;
    private final List entriesList;
    private int counterValue;
    private long timestampMs1;
    private boolean enabledState1;
    private static final Random fieldVal1;

    public ChestStealer() {
        super("Chest Stealer", "utility");
        ChestStealerStage var14 = ChestStealerStage.IDLE;
        this.chestStealerStage = var14;
        this.timestampMs = 0L;
        this.enabledState = false;
        this.fieldVal = null;
        ArrayList var19 = new ArrayList();
        this.entriesList = var19;
        this.counterValue = 0;
        this.timestampMs1 = 0L;
        this.enabledState1 = false;
        INSTANCE = this;
        Hooks.Bool0 var24 = () -> ChestStealer.isCollectionMode();
        Hooks.bool0("ChestStealer.tryCapture", var24);
    }

    public static boolean isCollectionMode() {
        ChestStealer var0 = INSTANCE;
        if (var0 != null) {
            if (!var0.isEnabled()) {
            }
        }
        return false;
        Object var17 = var0.getSetting("Collection Mode", "Normal");
        if (!"Silent".equals(var17)) {
            return false;
        }
        ChestStealerStage var11 = var0.chestStealerStage;
        ChestStealerStage var18 = ChestStealerStage.IDLE;
        if (var11 != var18) {
            return false;
        }
        var0.enabledState = true;
        return true;
    }

    public void onDisable() {
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
        Object var16 = this.getSetting("Collection Mode", "Normal");
        if ("Silent".equals(var16)) {
            this.applyStep1(var1);
        } else {
            this.applyStep(var1);
        }
    }

    private void applyStep(Minecraft arg0) {
        Screen var11 = arg0.screen;
        if ((var11 instanceof AbstractContainerScreen)) {
            AbstractContainerScreen var16 = ((AbstractContainerScreen) var11);
        } else {
            this.applyStep();
            return;
        }
        AbstractContainerMenu var18 = var16.getMenu();
        if (!(var18 instanceof ChestMenu)) {
            this.applyStep();
            return;
        }
        if (!this.enabledState1) {
            Set var68 = this.evaluate();
            this.applyStep(var18, var68);
            this.enabledState1 = true;
        }
        this.applyStep(var18);
        int var27 = this.counterValue;
        int var54 = this.entriesList.size();
        if (var27 >= var54) {
            this.applyStep();
            return;
        }
        long var29 = System.currentTimeMillis();
        int var32 = this.getItemDelayLo();
        long var70 = this.timestampMs1;
        long var35 = (var29 - var70);
        long var73 = ((long) var32);
        if (Long.compare(var35, var73) < 0) {
            return;
        }
        while (true) {
            this.applyStep(var18);
            int var40 = this.counterValue;
            int var58 = this.entriesList.size();
            if (var40 >= var58) {
                this.applyStep();
                return;
            }
            MultiPlayerGameMode var43 = arg0.gameMode;
            int var60 = var18.containerId;
            List var76 = this.entriesList;
            int var81 = this.counterValue;
            int var79 = ((Integer) var76.get(var81));
            LocalPlayer var83 = arg0.player;
            ContainerScreenHelper.shiftClick(var43, var60, var79, var83);
            int var62 = (this.counterValue + 1);
            this.counterValue = var62;
            if (var32 != 0) break;
            int var47 = this.counterValue;
            int var65 = this.entriesList.size();
            if (var47 < var65) continue;
        }
        this.timestampMs1 = var29;
    }

    private void applyStep1(Minecraft arg0) {
        int var18 = 0;
        switch (this.chestStealerStage.ordinal()) {
            case 0 -> {}
            case 1 -> {}
            case 2 -> {}
            default -> {}
        }
        this.applyStep3(arg0);
        this.applyStep2(arg0);
        this.applyStep4(arg0);
        Boolean var23 = true;
        if (((Boolean) this.getSetting("Bypass", var23))) {
            ChestStealerStage var15 = this.chestStealerStage;
            ChestStealerStage var20 = ChestStealerStage.IDLE;
            if (var15 != var20) {
                var18 = 1;
            } else {
                var18 = 0;
            }
            MoveFix.applyStep(var18);
            return;
        }
    }

    private void applyStep2(Minecraft arg0) {
        if (!this.enabledState) {
            return;
        }
        AbstractContainerMenu var10 = arg0.player.containerMenu;
        if (!(var10 instanceof ChestMenu)) {
            this.enabledState = false;
            return;
        }
        this.fieldVal = var10;
        Set var33 = this.evaluate();
        this.applyStep(var10, var33);
        this.enabledState = false;
        if (this.entriesList.isEmpty()) {
            this.applyStep5(arg0);
            this.applyStep();
            return;
        }
        long var27 = evaluate();
        long var35 = ((long) this.getItemDelayLo());
        long var29 = (var27 + var35);
        this.timestampMs = var29;
        ChestStealerStage var31 = ChestStealerStage.COLLECTING;
        this.chestStealerStage = var31;
    }

    private void applyStep3(Minecraft arg0) {
        AbstractContainerMenu var9 = arg0.player.containerMenu;
        InventoryMenu var31 = arg0.player.inventoryMenu;
        if (var9 == var31) {
            this.applyStep();
            return;
        }
        long var11 = evaluate();
        long var49 = this.timestampMs;
        if (Long.compare(var11, var49) < 0) {
            return;
        }
        int var15 = this.getItemDelayLo();
        int var19 = arg0.player.containerMenu.containerId;
        while (true) {
            AbstractContainerMenu var33 = this.fieldVal;
            this.applyStep(var33);
            int var22 = this.counterValue;
            int var36 = this.entriesList.size();
            if (var22 >= var36) {
                long var44 = evaluate();
                long var64 = ((long) var15);
                long var46 = (var44 + var64);
                this.timestampMs = var46;
                ChestStealerStage var48 = ChestStealerStage.WAIT_CLOSE;
                this.chestStealerStage = var48;
                return;
            }
            MultiPlayerGameMode var24 = arg0.gameMode;
            List var52 = this.entriesList;
            int var57 = this.counterValue;
            int var55 = ((Integer) var52.get(var57));
            LocalPlayer var59 = arg0.player;
            ContainerScreenHelper.shiftClick(var24, var19, var55, var59);
            int var39 = (this.counterValue + 1);
            this.counterValue = var39;
            if (var15 == 0) continue;
        }
        long var40 = evaluate();
        long var61 = ((long) var15);
        long var42 = (var40 + var61);
        this.timestampMs = var42;
    }

    private void applyStep4(Minecraft arg0) {
        AbstractContainerMenu var6 = arg0.player.containerMenu;
        InventoryMenu var14 = arg0.player.inventoryMenu;
        if (var6 == var14) {
            this.applyStep();
            return;
        }
        long var7 = evaluate();
        long var16 = this.timestampMs;
        if (Long.compare(var7, var16) < 0) {
            return;
        }
        this.applyStep5(arg0);
        this.applyStep();
    }

    private void applyStep(AbstractContainerMenu arg0, Set arg1) {
        int var38 = 0;
        int var9 = 0;
        this.entriesList.clear();
        this.counterValue = 0;
        Object var40 = this.getSetting("Item Mode", "All");
        if ("Whitelist".equals(var40)) {
            if (!arg1.isEmpty()) {
                var38 = 1;
            } else {
            }
        }
        var38 = 0;
        int var7 = var38;
        int var20 = (arg0.slots.size() - 36);
        var9 = 0;
        while (true) {
            if (var9 >= var20) break;
            int var43 = var9;
            ItemStack var25 = arg0.getSlot(var43).getItem();
            if (var25.isEmpty()) {
            } else {
                if (var7 != 0) {
                    Item var45 = var25.getItem();
                    if (!arg1.contains(var45)) {
                    } else {
                        List var32 = this.entriesList;
                        Integer var47 = var9;
                        boolean var33 = var32.add(var47);
                    }
                    var9 = (var9 + 1);
                }
            }
        }
        Object var49 = this.getSetting("Slot Order", "Normal");
        if ("Random".equals(var49)) {
            List var37 = this.entriesList;
            Random var50 = fieldVal1;
            Collections.shuffle(var37, var50);
        }
    }

    private void applyStep(AbstractContainerMenu arg0) {
        if (arg0 == null) {
            return;
        }
        while (true) {
            int var7 = this.counterValue;
            int var20 = this.entriesList.size();
            if (var7 >= var20) break;
            List var9 = this.entriesList;
            int var22 = this.counterValue;
            int var12 = ((Integer) var9.get(var22));
            int var25 = arg0.slots.size();
            if (var12 < var25) {
                if (!arg0.getSlot(var12).getItem().isEmpty()) {
                }
            } else {
                int var28 = (this.counterValue + 1);
                this.counterValue = var28;
            }
        }
    }

    private void applyStep5(Minecraft arg0) {
        if (arg0.player != null) {
            if (this.fieldVal == null) {
            }
        }
        return;
        ClientPacketListener var10 = arg0.player.connection;
        int var17 = this.fieldVal.containerId;
        ServerboundContainerClosePacket var3 = new ServerboundContainerClosePacket(var17);
        var10.send(var3);
        LocalPlayer var12 = arg0.player;
        InventoryMenu var15 = arg0.player.inventoryMenu;
        var12.containerMenu = var15;
    }

    private void applyStep() {
        MoveFix.applyStep(false);
        ChestStealerStage var2 = ChestStealerStage.IDLE;
        this.chestStealerStage = var2;
        this.timestampMs = 0L;
        this.enabledState = false;
        this.fieldVal = null;
        this.entriesList.clear();
        this.counterValue = 0;
        this.enabledState1 = false;
        this.timestampMs1 = 0L;
    }

    private Set evaluate() {
        String var12 = null;
        Object var61 = this.getSetting("Item Mode", "All");
        if (!"Whitelist".equals(var61)) {
            return Collections.emptySet();
        }
        try {
            JsonObject var17 = ClarityConfig.getCurrent().getAsJsonObject("Chest Stealer");
            if (var17 == null) {
                Set var60 = Collections.emptySet();
            }
        } catch (Exception ignored) {
        }
        return var60;
        try {
            JsonObject var20 = var17.getAsJsonObject("values");
            if (var20 == null) {
                Set var59 = Collections.emptySet();
            }
        } catch (Exception ignored) {
        }
        return var59;
        try {
            JsonElement var23 = var20.get("Items");
            if (var23 != null) {
                if (!var23.isJsonArray()) {
                }
            }
            Set var27 = Collections.emptySet();
        } catch (Exception ignored) {
        }
        return var27;
        try {
            JsonArray var31 = var23.getAsJsonArray();
            if (var31.size() == 0) {
                Set var34 = Collections.emptySet();
            }
        } catch (Exception ignored) {
        }
        return var34;
        try {
            HashSet var35 = new HashSet();
            Iterator var37 = var31.iterator();
            while (true) {
                if (!var37.hasNext()) break;
                JsonElement var42 = ((JsonElement) var37.next());
                if (!var42.isJsonPrimitive()) {
                }
                try {
                    var12 = var42.getAsString();
                    if (!var12.contains(":")) {
                        var12 = "minecraft:" + (var12);
                    }
                    DefaultedRegistry var51 = BuiltInRegistries.ITEM;
                    Identifier var67 = Identifier.parse(var12);
                    Item var53 = ((Item) var51.getValue(var67));
                    Item var68 = Items.AIR;
                    if (var53 == var68) continue;
                    boolean var56 = var35.add(var53);
                } catch (Exception ignored) {
                }
            }
        } catch (Exception ignored) {
        }
        return var35;
    }

    private int getItemDelayLo() {
        Integer var24 = 50;
        int var9 = ((Integer) this.getSetting("Item Delay_lo", var24));
        Integer var26 = 100;
        int var13 = ((Integer) this.getSetting("Item Delay_hi", var26));
        if (var9 >= var13) {
            return Math.max(0, var9);
        }
        Random var22 = fieldVal1;
        int var29 = ((var13 - var9) + 1);
        int var23 = var22.nextInt(var29);
        return (var9 + var23);
    }

    private static long evaluate() {
        return System.currentTimeMillis();
    }

    static {
        Random var0 = new Random();
        fieldVal1 = var0;
    }

}
