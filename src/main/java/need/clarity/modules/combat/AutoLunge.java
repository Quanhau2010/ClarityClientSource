package need.clarity.modules.combat;

import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.Optional;
import java.util.Random;
import need.clarity.PerFrameEvents;
import need.clarity.keybind.KeybindHandler;
import need.clarity.mixin.KeyMappingAccessor;
import need.clarity.modules.ModuleBase;
import need.clarity.util.InventoryHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
import net.minecraft.network.protocol.game.ServerboundSetCarriedItemPacket;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import org.lwjgl.glfw.GLFW;

public class AutoLunge extends ModuleBase {
    private static final ResourceKey fieldVal;
    private AutoLungeStage autoLungeStage;
    private long timestampMs;
    private long timestampMs1;
    private int counterValue;
    private boolean enabledState;
    private boolean enabledState1;
    private int counterValue1;
    private int counterValue2;
    private boolean enabledState2;
    private boolean enabledState3;
    private long timestampMs2;
    private long timestampMs3;
    private final Random fieldVal1;
    private static Method fieldVal2;
    private static boolean enabledState4;

    public AutoLunge() {
        super("Auto Lunge", "combat");
        AutoLungeStage var19 = AutoLungeStage.IDLE;
        this.autoLungeStage = var19;
        this.timestampMs = 0L;
        this.timestampMs1 = 0L;
        this.counterValue = -1;
        this.enabledState = false;
        this.enabledState1 = false;
        this.counterValue1 = -1;
        this.counterValue2 = -1;
        this.enabledState2 = false;
        this.enabledState3 = false;
        this.timestampMs2 = 0L;
        this.timestampMs3 = 50L;
        Random var35 = new Random();
        this.fieldVal1 = var35;
        PerFrameEvents.register(() -> this.applyStep());
    }

    public void onEnable() {
        AutoLungeStage var2 = AutoLungeStage.IDLE;
        this.autoLungeStage = var2;
        this.timestampMs = 0L;
        this.counterValue = -1;
        this.enabledState = false;
        this.enabledState1 = false;
    }

    public void onDisable() {
        Minecraft var1 = Minecraft.getInstance();
        this.applyStep1(var1);
        if (var1.player != null) {
            if (this.counterValue != -1) {
                Inventory var11 = var1.player.getInventory();
                int var18 = this.counterValue;
                InventoryHelper.setSelected(var11, var18);
            }
            AutoLungeStage var19 = AutoLungeStage.IDLE;
            this.autoLungeStage = var19;
            this.counterValue = -1;
            this.enabledState = false;
            this.enabledState1 = false;
            return;
        }
    }

    private void applyStep() {
        int var38 = 0;
        int var111 = 0;
        int var112 = 0;
        int var49 = 0;
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
            return;
        }
        LocalPlayer var24 = var1.player;
        long var25 = System.currentTimeMillis();
        AutoLungeStage var28 = this.autoLungeStage;
        AutoLungeStage var2 = AutoLungeStage.IDLE;
        if (var28 == var2) {
            Object var114 = this.getSetting("Mode", "Normal");
            if ("Grim".equals(var114)) {
                this.applyStep(var1);
                return;
            }
        }
        AutoLungeStage var32 = this.autoLungeStage;
        AutoLungeStage var115 = AutoLungeStage.SWAP_BACK_WAITING;
        if (var32 == var115) {
            if (((KeyMappingAccessor) var1.options.keyAttack).getClickCount() == 0) {
                var38 = 1;
            }
        } else {
            AutoLungeStage var62 = this.autoLungeStage;
            AutoLungeStage var124 = AutoLungeStage.HOLD_LOOP_WAITING;
            if (var62 == var124) {
                String var94 = ((String) this.getSetting("Lunge Bind", "NONE"));
                if (!(isEligible(var1, var94))) {
                    AutoLungeStage var138 = AutoLungeStage.IDLE;
                    this.autoLungeStage = var138;
                    this.enabledState1 = false;
                    return;
                }
            }
            AutoLungeStage var64 = this.autoLungeStage;
            AutoLungeStage var125 = AutoLungeStage.SWAP_WAITING;
            if (var64 == var125) {
                long var167 = this.timestampMs;
                long var88 = (var25 - var167);
                long var170 = this.timestampMs1;
                if (Long.compare(var88, var170) >= 0) {
                    this.applyStep(var1, var24);
                }
                return;
            }
            String var67 = ((String) this.getSetting("Lunge Bind", "NONE"));
            boolean var69 = isEligible(var1, var67);
            Boolean var163 = false;
            if (((Boolean) this.getSetting("Bind Hold Loop", var163))) {
                if (!var69) {
                    this.enabledState = false;
                    this.enabledState1 = false;
                    return;
                }
            }
            if (var69) {
                if (!this.enabledState) {
                    var112 = 1;
                } else {
                }
            }
            var112 = 0;
            int var14 = var112;
            this.enabledState = var69;
            if (var14 == 0) {
                return;
                this.enabledState1 = true;
                this.applyStep(var1, var24, var25);
            } else {
                this.applyStep(var1, var24, var25);
            }
            return;
            long var175 = this.timestampMs;
            long var101 = (var25 - var175);
            long var178 = this.timestampMs1;
            if (Long.compare(var101, var178) >= 0) {
                float var105 = var24.getAttackStrengthScale(0.0F);
                float var142 = this.getCooldown();
                if (Float.compare(var105, var142) >= 0) {
                    this.applyStep(var1, var24);
                } else {
                    AutoLungeStage var143 = AutoLungeStage.IDLE;
                    this.autoLungeStage = var143;
                    this.enabledState1 = false;
                }
                return;
                var38 = 0;
            }
        }
        int var11 = var38;
        long var148 = this.timestampMs;
        long var41 = (var25 - var148);
        long var151 = this.timestampMs1;
        if (Long.compare(var41, var151) >= 0) {
            var111 = 1;
        } else {
            var111 = 0;
        }
        int var12 = var111;
        long var154 = this.timestampMs;
        long var46 = (var25 - var154);
        long var159 = (this.timestampMs1 + 500L);
        if (Long.compare(var46, var159) >= 0) {
            var49 = 1;
        } else {
            var49 = 0;
        }
        int var13 = var49;
        if (var11 != 0) {
            if (var12 == 0) {
            }
        }
        if (var13 != 0) {
            if (this.counterValue != -1) {
                Inventory var56 = var24.getInventory();
                int var118 = this.counterValue;
                InventoryHelper.setSelected(var56, var118);
                this.counterValue = -1;
            }
            AutoLungeStage var120 = AutoLungeStage.IDLE;
            this.autoLungeStage = var120;
            this.enabledState1 = true;
            this.timestampMs = var25;
        }
    }

    private void applyStep(Minecraft arg0) {
        String var23 = ((String) this.getSetting("Lunge Bind", "NONE"));
        boolean var8 = isEligible(arg0, var23);
        Boolean var30 = false;
        if (((Boolean) this.getSetting("Bind Hold Loop", var30))) {
            this.enabledState3 = var8;
            if (!var8) {
                this.enabledState = false;
            }
        } else {
            if (var8) {
                if (!this.enabledState) {
                    this.enabledState2 = true;
                }
                this.enabledState = var8;
            }
        }
    }

    public void onTick() {
        int var10 = 0;
        int var57 = 0;
        Minecraft var1 = Minecraft.getInstance();
        if (this.isEnabled()) {
            Object var58 = this.getSetting("Mode", "Normal");
            if ("Grim".equals(var58)) {
                var57 = 1;
            } else {
            }
        }
        var57 = 0;
        int var9 = var57;
        var10 = 0;
        if (var9 != 0) {
            if (var1.player != null) {
                if (var1.level != null) {
                    if (var1.screen == null) {
                        long var26 = System.currentTimeMillis();
                        Boolean var74 = false;
                        if (((Boolean) this.getSetting("Bind Hold Loop", var74))) {
                            if (this.enabledState3) {
                                long var78 = this.timestampMs2;
                                long var50 = (var26 - var78);
                                long var81 = this.timestampMs3;
                                if (Long.compare(var50, var81) >= 0) {
                                    var10 = 1;
                                }
                            }
                        } else {
                            if (this.enabledState2) {
                                this.enabledState2 = false;
                                var10 = 1;
                            }
                            if (var10 != 0) {
                                this.timestampMs2 = var26;
                                Integer var91 = 0;
                                int var86 = ((Integer) this.getSetting("Hold Loop Delay_lo", var91));
                                Integer var93 = 100;
                                int var90 = ((Integer) this.getSetting("Hold Loop Delay_hi", var93));
                                int var76 = this.computeColorOrIndex(var86, var90);
                                long var65 = ((long) Math.max(50, var76));
                                this.timestampMs3 = var65;
                            }
                        }
                    } else {
                        this.enabledState2 = false;
                        if (var9 == 0) {
                            this.enabledState3 = false;
                        }
                        if (this.counterValue1 != -1) {
                            if (var10 == 0) {
                                this.applyStep1(var1);
                            }
                            if (var10 != 0) {
                                LocalPlayer var70 = var1.player;
                                this.applyStep(var70);
                            }
                            return;
                        }
                    }
                }
            }
        }
    }

    private void applyStep(LocalPlayer arg0) {
        int var22 = 0;
        int var14 = this.computeColorOrIndex(arg0);
        if (var14 < 0) {
            return;
        }
        Inventory var17 = arg0.getInventory();
        int var19 = InventoryHelper.getSelected(var17);
        if (this.counterValue1 != -1) {
            var22 = this.counterValue1;
        } else {
            var22 = var19;
        }
        int var11 = var22;
        Boolean var54 = true;
        boolean var26 = ((Boolean) this.getSetting("Swap Back", var54));
        ClientPacketListener var28 = arg0.connection;
        PrintStream var29 = System.out;
        String var44 = "[AutoLunge] DEBUG grim: selected=" + (var19) + " spearSlot=" + (var14) + " home=" + (var11) + " swapBack=" + (var26);
        var29.println(var44);
        if (var14 != var19) {
            InventoryHelper.setSelected(var17, var14);
            Minecraft var47 = Minecraft.getInstance();
            this.setRightClickDelay(var47, var14);
        }
        ServerboundPlayerActionPacket.Action var57 = ServerboundPlayerActionPacket.Action.STAB;
        BlockPos var58 = BlockPos.ZERO;
        Direction var7 = Direction.DOWN;
        ServerboundPlayerActionPacket var48 = new ServerboundPlayerActionPacket(var57, var58, var7);
        var28.send(var48);
        InteractionHand var49 = InteractionHand.MAIN_HAND;
        arg0.swing(var49);
        if (var14 != var11) {
            if (var26) {
                this.counterValue1 = var11;
                this.counterValue2 = var14;
            } else {
            }
        }
        this.counterValue1 = -1;
    }

    private void setRightClickDelay(Minecraft arg0, int arg1) {
        if (!enabledState4) {
            enabledState4 = true;
            try {
                Class[] var27 = new Class[0];
                Method var10 = MultiPlayerGameMode.class.getDeclaredMethod("ensureHasSentCarriedItem", var27);
                var10.setAccessible(true);
                fieldVal2 = var10;
            } catch (Throwable ignored) {
            }
        }
        if (fieldVal2 != null) {
            if (arg0.gameMode != null) {
                try {
                    Method var16 = fieldVal2;
                    MultiPlayerGameMode var25 = arg0.gameMode;
                    Object[] var29 = new Object[0];
                    Object var17 = var16.invoke(var25, var29);
                } catch (Throwable ignored) {
                }
                return;
            }
            ClientPacketListener var21 = arg0.player.connection;
            ServerboundSetCarriedItemPacket var26 = new ServerboundSetCarriedItemPacket(arg1);
            var21.send(var26);
            return;
        }
    }

    private void applyStep1(Minecraft arg0) {
        int var8 = this.counterValue1;
        this.counterValue1 = -1;
        if (var8 >= 0) {
            if (arg0.player == null) {
            }
        }
        return;
        Inventory var15 = arg0.player.getInventory();
        int var17 = InventoryHelper.getSelected(var15);
        int var23 = this.counterValue2;
        if (var17 == var23) {
            if (var17 == var8) {
            }
        }
        return;
        InventoryHelper.setSelected(var15, var8);
        this.setRightClickDelay(arg0, var8);
    }

    private float getCooldown() {
        Integer var16 = 85;
        return (((float) Math.clamp(((long) ((Integer) this.getSetting("Cooldown %", var16))), 0, 100)) / 100.0F);
    }

    private void applyStep(Minecraft arg0, LocalPlayer arg1, long arg2) {
        if (this.computeColorOrIndex(arg1) < 0) {
            return;
        }
        Boolean var79 = false;
        if (((Boolean) this.getSetting("Bind Hold Loop", var79))) {
            if (this.enabledState1) {
                float var25 = arg1.getAttackStrengthScale(0.0F);
                float var51 = this.getCooldown();
                if (Float.compare(var25, var51) >= 0) {
                    AutoLungeStage var28 = this.autoLungeStage;
                    AutoLungeStage var52 = AutoLungeStage.IDLE;
                    if (var28 == var52) {
                        int var55 = InventoryHelper.getSelected(arg1.getInventory());
                        this.counterValue = var55;
                        AutoLungeStage var56 = AutoLungeStage.HOLD_LOOP_WAITING;
                        this.autoLungeStage = var56;
                        this.timestampMs = arg2;
                        Integer var99 = 0;
                        int var83 = ((Integer) this.getSetting("Hold Loop Delay_lo", var99));
                        Integer var103 = 100;
                        int var95 = ((Integer) this.getSetting("Hold Loop Delay_hi", var103));
                        long var61 = ((long) this.computeColorOrIndex(var83, var95));
                        this.timestampMs1 = var61;
                    }
                    return;
                }
            }
        }
        float var34 = arg1.getAttackStrengthScale(0.0F);
        float var65 = this.getCooldown();
        if (Float.compare(var34, var65) < 0) {
            return;
        }
        int var68 = InventoryHelper.getSelected(arg1.getInventory());
        this.counterValue = var68;
        Integer var97 = 0;
        int var72 = ((Integer) this.getSetting("Swap Delay_lo", var97));
        Integer var102 = 0;
        int var88 = ((Integer) this.getSetting("Swap Delay_hi", var102));
        long var39 = ((long) this.computeColorOrIndex(var72, var88));
        if (var39 == 0L) {
            this.applyStep(arg0, arg1);
        } else {
            AutoLungeStage var73 = AutoLungeStage.SWAP_WAITING;
            this.autoLungeStage = var73;
            this.timestampMs = arg2;
            this.timestampMs1 = var39;
        }
    }

    private void applyStep(Minecraft arg0, LocalPlayer arg1) {
        Inventory var12 = arg1.getInventory();
        Boolean var71 = true;
        boolean var16 = ((Boolean) this.getSetting("Swap Back", var71));
        int var18 = this.computeColorOrIndex(arg1);
        if (var18 < 0) {
            if (this.counterValue != -1) {
                int var67 = this.counterValue;
                InventoryHelper.setSelected(var12, var67);
                this.counterValue = -1;
            }
            AutoLungeStage var69 = AutoLungeStage.IDLE;
            this.autoLungeStage = var69;
            this.enabledState1 = false;
            return;
        }
        InventoryHelper.setSelected(var12, var18);
        KeyMappingAccessor var24 = ((KeyMappingAccessor) arg0.options.keyAttack);
        int var50 = (((KeyMappingAccessor) arg0.options.keyAttack).getClickCount() + 1);
        var24.setClickCount(var50);
        if (var16) {
            if (this.counterValue != -1) {
                if (this.counterValue != var18) {
                    AutoLungeStage var53 = AutoLungeStage.SWAP_BACK_WAITING;
                    this.autoLungeStage = var53;
                    long var54 = System.currentTimeMillis();
                    this.timestampMs = var54;
                    Integer var81 = 100;
                    int var76 = ((Integer) this.getSetting("Swap Back Delay_lo", var81));
                    Integer var83 = 100;
                    int var80 = ((Integer) this.getSetting("Swap Back Delay_hi", var83));
                    long var58 = ((long) this.computeColorOrIndex(var76, var80));
                    this.timestampMs1 = var58;
                } else {
                }
            }
            this.counterValue = -1;
            AutoLungeStage var61 = AutoLungeStage.IDLE;
            this.autoLungeStage = var61;
            this.enabledState1 = true;
            long var63 = System.currentTimeMillis();
            this.timestampMs = var63;
            return;
        }
    }

    private int computeColorOrIndex(LocalPlayer arg0) {
        int var6 = 0;
        int var7 = 0;
        int var8 = 0;
        Inventory var11 = arg0.getInventory();
        var6 = -1;
        var7 = 0;
        var8 = 0;
        while (true) {
            if (var8 < 9) {
                int var26 = var8;
                ItemStack var17 = var11.getItem(var26);
                if (var17.isEmpty()) {
                }
            } else {
                return var6;
                int var21 = this.computeColorOrIndex(var17, arg0);
                int var28 = var7;
                if (var21 > var28) {
                    var7 = var21;
                    var6 = var8;
                }
                var8 = (var8 + 1);
            }
        }
    }

    private int computeColorOrIndex(ItemStack arg0, LocalPlayer arg1) {
        try {
            RegistryAccess var11 = arg1.level().registryAccess();
            ResourceKey var4 = Registries.ENCHANTMENT;
            Registry var13 = var11.lookupOrThrow(var4);
            ResourceKey var31 = fieldVal;
            Optional var15 = var13.get(var31);
            if (var15.isEmpty()) {
            }
        } catch (Exception ignored) {
        }
        return 0;
        try {
            Holder var20 = ((Holder) var15.get());
            DataComponentType var32 = DataComponents.ENCHANTMENTS;
            ItemEnchantments var23 = ((ItemEnchantments) arg0.get(var32));
            if (var23 == null) {
            }
        } catch (Exception ignored) {
        }
        return 0;
        try {
            int var26 = var23.getLevel(var20);
        } catch (Exception ignored) {
        }
        return var26;
    }

    private static boolean isEligible(Minecraft arg0, String arg1) {
        int var32 = 0;
        int var25 = 0;
        if (arg1 != null) {
            if (!arg1.isEmpty()) {
                if (!"NONE".equalsIgnoreCase(arg1)) {
                    if ("None".equalsIgnoreCase(arg1)) {
                    }
                }
                return false;
                long var17 = arg0.getWindow().handle();
                int var20 = KeybindHandler.mouseButtonIndex(arg1);
                if (var20 >= 0) {
                    if (GLFW.glfwGetMouseButton(var17, var20) == 1) {
                        var25 = 1;
                    }
                } else {
                    int var27 = KeybindHandler.glfwKeyCode(arg1);
                    if (var27 == -1) {
                        return false;
                    }
                    if (GLFW.glfwGetKey(var17, var27) == 1) {
                        var32 = 1;
                    } else {
                        var32 = 0;
                    }
                    return var32;
                    var25 = 0;
                }
                return var25;
            }
        }
    }

    private int computeColorOrIndex(int arg0, int arg1) {
        if (arg1 <= arg0) {
            return arg0;
        }
        Random var11 = this.fieldVal1;
        int var14 = ((arg1 - arg0) + 1);
        int var12 = var11.nextInt(var14);
        return (arg0 + var12);
    }

    static {
        ResourceKey var0 = Registries.ENCHANTMENT;
        Identifier var3 = Identifier.withDefaultNamespace("lunge");
        fieldVal = ResourceKey.create(var0, var3);
    }

}
