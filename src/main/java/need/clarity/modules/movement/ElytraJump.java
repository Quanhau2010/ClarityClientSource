package need.clarity.modules.movement;

import need.clarity.modules.ModuleBase;
import need.clarity.util.ContainerScreenHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.protocol.game.ServerboundPlayerCommandPacket;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class ElytraJump extends ModuleBase {
    private static final String labelText = "Auto Swap";
    private boolean enabledState;

    public ElytraJump() {
        super("Elytra Jump", "Movement");
        this.enabledState = false;
    }

    private int computeColorOrIndex(Item arg0) {
        int var5 = 0;
        int var6 = 0;
        Minecraft var2 = Minecraft.getInstance();
        var5 = -1;
        var6 = 0;
        while (true) {
            if (var6 >= 36) break;
            Inventory var12 = var2.player.getInventory();
            int var19 = var6;
            if (var12.getItem(var19).getItem() == arg0) {
                var5 = var6;
            } else {
                var6 = (var6 + 1);
            }
        }
        if (var5 < 9) {
            if (var5 != -1) {
                var5 = (var5 + 36);
            }
            return var5;
        }
    }

    private int computeColorOrIndex() {
        int var8 = 0;
        int var10 = 0;
        Minecraft var1 = Minecraft.getInstance();
        Item[] var13 = new Item[6];
        Item var3 = Items.NETHERITE_CHESTPLATE;
        var13[0] = var3;
        Item var43 = Items.DIAMOND_CHESTPLATE;
        var13[1] = var43;
        Item var44 = Items.GOLDEN_CHESTPLATE;
        var13[2] = var44;
        Item var45 = Items.IRON_CHESTPLATE;
        var13[3] = var45;
        Item var46 = Items.LEATHER_CHESTPLATE;
        var13[4] = var46;
        Item var47 = Items.CHAINMAIL_CHESTPLATE;
        var13[5] = var47;
        int var16 = var13.length;
        var8 = 0;
        while (true) {
            if (var8 < var16) {
                int var33 = var8;
                Item var20 = var13[var33];
                var10 = 0;
                while (true) {
                    if (var10 < 36) {
                        Inventory var25 = var1.player.getInventory();
                        int var35 = var10;
                        if (var25.getItem(var35).getItem() == var20) {
                            if (var10 < 9) {
                                var10 = (var10 + 36);
                            }
                            return var10;
                        }
                    }
                }
            }
            return -1;
            var8 = (var8 + 1);
        }
        var10 = (var10 + 1);
    }

    private void applyStep() {
        int var20 = 0;
        int var21 = 0;
        Minecraft var1 = Minecraft.getInstance();
        Item var2 = Items.ELYTRA;
        int var10 = this.computeColorOrIndex(var2);
        if (var10 != -1) {
            if (var1.gameMode != null) {
                if (var10 >= 36) {
                    var21 = (var10 - 36);
                } else {
                    var21 = var10;
                }
                int var7 = var21;
                if (var7 < 9) {
                    var20 = (var7 + 36);
                } else {
                    var20 = var7;
                }
                int var8 = var20;
                MultiPlayerGameMode var18 = var1.gameMode;
                int var29 = var1.player.inventoryMenu.containerId;
                LocalPlayer var31 = var1.player;
                ContainerScreenHelper.shiftClick(var18, var29, var8, var31);
            }
            return;
        }
    }

    private void applyStep1() {
        int var17 = 0;
        int var21 = 0;
        Minecraft var1 = Minecraft.getInstance();
        int var10 = this.computeColorOrIndex();
        if (var10 != -1) {
            if (var1.gameMode != null) {
                if (var10 >= 36) {
                    var21 = (var10 - 36);
                } else {
                    var21 = var10;
                }
                int var7 = var21;
                if (var7 < 9) {
                    var17 = (var7 + 36);
                } else {
                    var17 = var7;
                }
                int var8 = var17;
                MultiPlayerGameMode var19 = var1.gameMode;
                int var28 = var1.player.inventoryMenu.containerId;
                LocalPlayer var30 = var1.player;
                ContainerScreenHelper.shiftClick(var19, var28, var8, var30);
            }
            return;
        }
    }

    public void onEnable() {
        this.enabledState = false;
        Boolean var11 = false;
        if (((Boolean) this.getSetting("Auto Swap", var11))) {
            this.applyStep();
        }
        super.onEnable();
    }

    public void onDisable() {
        Minecraft var1 = Minecraft.getInstance();
        if (var1.player == null) {
            return;
        }
        Boolean var19 = false;
        if (((Boolean) this.getSetting("Auto Swap", var19))) {
            LocalPlayer var12 = var1.player;
            EquipmentSlot var17 = EquipmentSlot.CHEST;
            Item var14 = var12.getItemBySlot(var17).getItem();
            Item var18 = Items.ELYTRA;
            if (var14 == var18) {
                this.applyStep1();
            }
            super.onDisable();
            return;
        }
    }

    public void onTick() {
        Minecraft var1 = Minecraft.getInstance();
        if (var1.player != null) {
            if (var1.level == null) {
            }
        }
        return;
        if (!var1.player.getAbilities().flying) {
            if (var1.player.onGround()) {
                if (!var1.player.isInWater()) {
                    if (!var1.player.isInLava()) {
                        LocalPlayer var27 = var1.player;
                        EquipmentSlot var2 = EquipmentSlot.CHEST;
                        Item var29 = var27.getItemBySlot(var2).getItem();
                        Item var116 = Items.ELYTRA;
                        if (var29 == var116) {
                            if (!var1.options.keyJump.isDown()) {
                                var1.player.jumpFromGround();
                            }
                            if (!var1.player.getAbilities().flying) {
                                if (!var1.player.onGround()) {
                                    if (!var1.player.isInWater()) {
                                        if (!var1.player.isFallFlying()) {
                                            LocalPlayer var50 = var1.player;
                                            EquipmentSlot var117 = EquipmentSlot.CHEST;
                                            Item var52 = var50.getItemBySlot(var117).getItem();
                                            Item var118 = Items.ELYTRA;
                                            if (var52 == var118) {
                                                var1.player.startFallFlying();
                                                ClientPacketListener var57 = var1.player.connection;
                                                LocalPlayer var147 = var1.player;
                                                ServerboundPlayerCommandPacket.Action var4 = ServerboundPlayerCommandPacket.Action.START_FALL_FLYING;
                                                ServerboundPlayerCommandPacket var119 = new ServerboundPlayerCommandPacket(var147, var4);
                                                var57.send(var119);
                                            }
                                            if (!var1.player.onGround()) {
                                                if (!var1.player.isInWater()) {
                                                    if (var1.player.isInLava()) {
                                                    }
                                                }
                                                this.enabledState = false;
                                                if (var1.player.hurtTime > 0) {
                                                    Boolean var144 = false;
                                                    if (((Boolean) this.getSetting("Auto Swap", var144))) {
                                                        LocalPlayer var76 = var1.player;
                                                        EquipmentSlot var122 = EquipmentSlot.CHEST;
                                                        Item var78 = var76.getItemBySlot(var122).getItem();
                                                        Item var123 = Items.ELYTRA;
                                                        if (var78 == var123) {
                                                            this.applyStep1();
                                                            return;
                                                        }
                                                    }
                                                }
                                                if (var1.player.getInventory() != null) {
                                                    Item var124 = Items.ELYTRA;
                                                    if (this.computeColorOrIndex(var124) == 38) {
                                                        if (var1.options.keyJump.isDown()) {
                                                            if (!var1.player.isFallFlying()) {
                                                                var1.options.keyJump.setDown(false);
                                                            }
                                                            LocalPlayer var96 = var1.player;
                                                            EquipmentSlot var127 = EquipmentSlot.CHEST;
                                                            Item var98 = var96.getItemBySlot(var127).getItem();
                                                            Item var128 = Items.ELYTRA;
                                                            if (var98 == var128) {
                                                                LocalPlayer var105 = var1.player;
                                                                double var151 = var1.player.getDeltaMovement().y;
                                                                var105.setDeltaMovement(0.0, var151, 0.0);
                                                                var1.options.keyJump.setDown(true);
                                                                if (var1.player.isFallFlying()) {
                                                                    float var137 = ((float) (Math.random() * 0.0010000000474974513));
                                                                    float var113 = (0.06F + var137);
                                                                    LocalPlayer var115 = var1.player;
                                                                    double var141 = var1.player.getDeltaMovement().x;
                                                                    double var158 = var1.player.getDeltaMovement().y;
                                                                    double var163 = ((double) var113);
                                                                    double var160 = (var158 + var163);
                                                                    double var168 = var1.player.getDeltaMovement().z;
                                                                    var115.setDeltaMovement(var141, var160, var168);
                                                                }
                                                            } else {
                                                                Boolean var146 = false;
                                                                if (((Boolean) this.getSetting("Auto Swap", var146))) {
                                                                    this.applyStep();
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
    }

}
