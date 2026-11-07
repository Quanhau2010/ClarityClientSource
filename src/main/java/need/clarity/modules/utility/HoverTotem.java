package need.clarity.modules.utility;

import java.util.List;
import java.util.Random;
import need.clarity.modules.ModuleBase;
import need.clarity.util.ContainerScreenHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class HoverTotem extends ModuleBase {
    private boolean enabledState;
    private long timestampMs;
    private boolean enabledState1;
    private long timestampMs1;
    private int counterValue;
    private final Random fieldVal;

    public HoverTotem() {
        super("Hover Totem", "Utility");
        this.enabledState = false;
        this.timestampMs = -1L;
        this.enabledState1 = false;
        this.timestampMs1 = -1L;
        this.counterValue = -1;
        Random var17 = new Random();
        this.fieldVal = var17;
    }

    public void onDisable() {
        this.applyStep();
    }

    public void onTick() {
        Minecraft var1 = Minecraft.getInstance();
        if (var1.player != null) {
            if (var1.gameMode == null) {
            }
        }
        return;
        Screen var12 = var1.screen;
        if ((var12 instanceof AbstractContainerScreen)) {
            AbstractContainerScreen var16 = ((AbstractContainerScreen) var12);
        } else {
            this.applyStep();
            return;
        }
        this.applyStep(var1, var16);
    }

    private void applyStep(Minecraft arg0, AbstractContainerScreen arg1) {
        int var21 = 0;
        Slot var14 = ContainerScreenHelper.getHoveredSlot(arg1);
        if (var14 != null) {
            if (var14.hasItem()) {
                ItemStack var19 = var14.getItem();
                Item var4 = Items.TOTEM_OF_UNDYING;
                if (var19.is(var4)) {
                    var21 = 1;
                } else {
                    var21 = 0;
                }
                if (var21 != 0) {
                    int var25 = computeColorOrIndex(arg1, var14);
                    if ((var14.container instanceof Inventory)) {
                        if (var14.getContainerSlot() == 40) {
                            this.applyStep();
                            return;
                        }
                    }
                    if (this.counterValue != -1) {
                        int var74 = this.counterValue;
                        if (var25 != var74) {
                            this.applyStep();
                        }
                        long var36 = System.currentTimeMillis();
                        ItemStack var40 = arg0.player.getOffhandItem();
                        Item var75 = Items.TOTEM_OF_UNDYING;
                        if (!var40.is(var75)) {
                            if (!this.enabledState) {
                                this.enabledState = true;
                                long var105 = this.evaluate();
                                long var79 = (var36 + var105);
                                this.timestampMs = var79;
                                this.counterValue = var25;
                            }
                            if (this.enabledState) {
                                long var97 = this.timestampMs;
                                if (Long.compare(var36, var97) >= 0) {
                                    ItemStack var54 = arg0.player.getOffhandItem();
                                    Item var82 = Items.TOTEM_OF_UNDYING;
                                    if (!var54.is(var82)) {
                                        MultiPlayerGameMode var57 = arg0.gameMode;
                                        int var85 = arg1.getMenu().containerId;
                                        LocalPlayer var108 = arg0.player;
                                        ContainerScreenHelper.swapToOffhand(var57, var85, var25, var108);
                                    }
                                    this.enabledState = false;
                                    this.timestampMs = -1L;
                                }
                                Boolean var101 = false;
                                if (!((Boolean) this.getSetting("Fill Hotbar", var101))) {
                                    this.enabledState1 = false;
                                    this.timestampMs1 = -1L;
                                } else {
                                    Object var91 = this.getSetting("Fill Hotbar Mode", "Slot");
                                    if ("Empty".equals(var91)) {
                                        this.applyStep1(arg0, arg1, var25, var36);
                                    } else {
                                        this.applyStep(arg0, arg1, var25, var36);
                                    }
                                }
                            }
                        }
                    }
                } else {
                    this.applyStep();
                }
                return;
            }
        }
    }

    private void applyStep(Minecraft arg0, AbstractContainerScreen arg1, int arg2, long arg3) {
        ItemStack var13 = arg0.player.getOffhandItem();
        Item var7 = Items.TOTEM_OF_UNDYING;
        if (!var13.is(var7)) {
            this.enabledState1 = false;
            this.timestampMs1 = -1L;
            return;
        }
        Integer var61 = 1;
        int var21 = (((Integer) this.getSetting("Slot", var61)) - 1);
        ItemStack var25 = arg0.player.getInventory().getItem(var21);
        Item var46 = Items.TOTEM_OF_UNDYING;
        if (var25.is(var46)) {
            this.enabledState1 = false;
            this.timestampMs1 = -1L;
            return;
        }
        if (!this.enabledState1) {
            this.enabledState1 = true;
            long var66 = this.evaluate();
            long var53 = (arg3 + var66);
            this.timestampMs1 = var53;
        }
        long var63 = this.timestampMs1;
        if (Long.compare(arg3, var63) >= 0) {
            MultiPlayerGameMode var37 = arg0.gameMode;
            int var57 = arg1.getMenu().containerId;
            LocalPlayer var69 = arg0.player;
            ContainerScreenHelper.swapToHotbar(var37, var57, arg2, var21, var69);
            this.enabledState1 = false;
            this.timestampMs1 = -1L;
        }
    }

    private void applyStep1(Minecraft arg0, AbstractContainerScreen arg1, int arg2, long arg3) {
        if (!this.enabledState1) {
            this.enabledState1 = true;
            long var33 = this.evaluate();
            long var22 = (arg3 + var33);
            this.timestampMs1 = var22;
        }
        long var30 = this.timestampMs1;
        if (Long.compare(arg3, var30) >= 0) {
            MultiPlayerGameMode var17 = arg0.gameMode;
            int var26 = arg1.getMenu().containerId;
            LocalPlayer var36 = arg0.player;
            ContainerScreenHelper.shiftClick(var17, var26, arg2, var36);
            this.enabledState1 = false;
            this.timestampMs1 = -1L;
        }
    }

    private static int computeColorOrIndex(AbstractContainerScreen arg0, Slot arg1) {
        return arg0.getMenu().slots.indexOf(arg1);
    }

    private void applyStep() {
        this.enabledState = false;
        this.timestampMs = -1L;
        this.enabledState1 = false;
        this.timestampMs1 = -1L;
        this.counterValue = -1;
    }

    private long evaluate() {
        long var40 = 0L;
        long var73 = 0L;
        long var75 = 0L;
        Object var14 = this.getSetting("Hover Delay", null);
        if ((var14 instanceof List)) {
            List var22 = ((List) var14);
            if (var22.size() >= 2) {
                var73 = evaluate(var22.get(0));
                var75 = evaluate(var22.get(1));
            }
        } else {
            var73 = evaluate(var14);
            var75 = var73;
        }
        long var30 = var75;
        long var53 = var73;
        if (Long.compare(var30, var53) < 0) {
            var75 = var73;
        }
        long var35 = var73;
        long var55 = var75;
        if (var35 == var55) {
            var40 = var73;
        } else {
            long var38 = var73;
            double var59 = this.fieldVal.nextDouble();
            long var4 = var75;
            long var6 = var73;
            double var69 = ((double) ((var4 - var6) + 1L));
            long var63 = ((long) (var59 * var69));
            var40 = (var38 + var63);
        }
        return var40;
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

}
