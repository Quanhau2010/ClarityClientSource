package need.clarity.modules.movement;

import need.clarity.PerFrameEvents;
import need.clarity.PerMovementEvents;
import need.clarity.PerPacketEvents;
import need.clarity.PerPacketSendEvents;
import need.clarity.api.MovementEvent;
import need.clarity.event.EventPacketReceive;
import need.clarity.event.EventPacketSend;
import need.clarity.modules.ModuleBase;
import need.clarity.modules.ModuleRegistry;
import need.clarity.modules.combat.AimAssist;
import need.clarity.modules.utility.Overtick;
import need.clarity.util.InventoryHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundExplodePacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.network.protocol.game.ServerboundSetCarriedItemPacket;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public class WindCharge extends ModuleBase {
    public static WindCharge INSTANCE;
    private String labelText;
    private int counterValue;
    private int counterValue1;
    private boolean enabledState;
    private float floatValue;
    private float floatValue1;
    private float floatValue2;
    private float floatValue3;
    private float floatValue4;
    private boolean enabledState1;
    private int counterValue2;
    private int counterValue3;
    private int counterValue4;
    private int counterValue5;
    private boolean enabledState2;
    private boolean enabledState3;
    private int counterValue6;
    private volatile boolean enabledState4;
    private boolean enabledState5;
    private static final int CONST_INT = 20;

    public WindCharge() {
        super("WindCharge", "Utility");
        this.labelText = "IDLE";
        this.counterValue = 0;
        this.counterValue1 = 0;
        this.enabledState = false;
        this.enabledState1 = false;
        this.counterValue2 = -1;
        this.counterValue3 = -1;
        this.counterValue4 = -1;
        this.counterValue5 = -1;
        this.enabledState2 = false;
        this.enabledState3 = false;
        this.counterValue6 = 0;
        this.enabledState4 = false;
        this.enabledState5 = false;
        INSTANCE = this;
        PerFrameEvents.register(() -> this.applyStep());
        PerMovementEvents.register((p0) -> this.onMovementTick(p0));
        PerPacketEvents.register((p0) -> this.onPacketReceive(p0));
        PerPacketSendEvents.register((p0) -> this.onPacketSend(p0));
    }

    private void onPacketSend(EventPacketSend arg0) {
        Packet var6 = arg0.getPacket();
        if ((var6 instanceof ServerboundSetCarriedItemPacket)) {
            int var12 = ((ServerboundSetCarriedItemPacket) var6).getSlot();
            this.counterValue5 = var12;
        }
    }

    private void onPacketReceive(EventPacketReceive arg0) {
        int var5 = 0;
        int var26 = 0;
        if (!this.isEnabled()) {
            return;
        }
        try {
            Packet var10 = arg0.getPacket();
            var5 = (var10 instanceof ClientboundExplodePacket);
            if (var5 == 0) {
                if ((var10 instanceof ClientboundSetEntityMotionPacket)) {
                    ClientboundSetEntityMotionPacket var17 = ((ClientboundSetEntityMotionPacket) var10);
                    Minecraft var18 = Minecraft.getInstance();
                    if (var18.player != null) {
                        int var22 = var17.id();
                        int var28 = var18.player.getId();
                        if (var22 == var28) {
                            var26 = 1;
                        } else {
                            var26 = 0;
                        }
                        var5 = var26;
                    }
                }
                if (var5 != 0) {
                    this.enabledState4 = true;
                }
            }
        } catch (Throwable ignored) {
        }
    }

    private void onMovementTick(MovementEvent arg0) {
        int var6 = 0;
        Minecraft var2 = Minecraft.getInstance();
        if (var2.player == null) {
            return;
        }
        if (this.counterValue6 > 0) {
            int var12 = this.counterValue6;
            this.counterValue6 = 0;
            var6 = 0;
            while (true) {
                if (var6 >= var12) break;
                this.applyStep(var2);
                var6 = (var6 + 1);
            }
            if (this.enabledState2) {
                if (!this.isJump()) {
                }
            }
            return;
            if (var2.player.onGround()) {
                arg0.jump = true;
                this.enabledState2 = false;
            }
            return;
        }
    }

    private void applyStep() {
        if (this.isEnabled()) {
            if (this.isSilentAim()) {
                if (!this.enabledState1) {
                }
            }
            return;
            String var19 = this.labelText;
            if (!"IDLE".equals(var19)) {
                String var21 = this.labelText;
                if ("DONE".equals(var21)) {
                }
            }
            this.enabledState1 = false;
            return;
            if (Minecraft.getInstance().player == null) {
                return;
            }
            float var18 = this.floatValue3;
            float var24 = this.floatValue4;
            AimAssist.applyStep(var18, var24);
            return;
        }
    }

    private boolean isSilentAim() {
        Boolean var7 = true;
        return ((Boolean) this.getSetting("Silent Aim", var7));
    }

    private float getTargetPitch() {
        Float var7 = 90.0F;
        return ((Float) this.getSetting("Target Pitch", var7));
    }

    private int getAimTicks() {
        Integer var9 = 1;
        int var8 = ((Integer) this.getSetting("Aim Ticks", var9));
        return Math.max(1, var8);
    }

    private int getRestoreTicks() {
        Integer var9 = 1;
        int var8 = ((Integer) this.getSetting("Restore Ticks", var9));
        return Math.max(1, var8);
    }

    private int getShotDelay() {
        Integer var9 = 10;
        int var8 = ((Integer) this.getSetting("Shot Delay", var9));
        return Math.max(0, var8);
    }

    private boolean isCombo() {
        Boolean var7 = true;
        return ((Boolean) this.getSetting("Combo", var7));
    }

    private int getComboCount() {
        Integer var11 = 3;
        int var10 = ((Integer) this.getSetting("Combo Count", var11));
        int var7 = Math.max(2, var10);
        return Math.min(10, var7);
    }

    private boolean isJump() {
        Boolean var7 = true;
        return ((Boolean) this.getSetting("Jump", var7));
    }

    private int getUpRange() {
        Integer var9 = 3;
        int var8 = ((Integer) this.getSetting("Up Range", var9));
        return Math.max(1, var8);
    }

    private String getMode() {
        return ((String) this.getSetting("Mode", "Immediate"));
    }

    private int getDelay() {
        Integer var9 = 10;
        int var8 = ((Integer) this.getSetting("Delay", var9));
        return Math.max(1, var8);
    }

    public void onEnable() {
        float var51 = 0.0F;
        Minecraft var1 = Minecraft.getInstance();
        if (var1.player == null) {
            this.setEnabled(false);
            return;
        }
        int var27 = this.computeColorOrIndex();
        this.counterValue2 = var27;
        if (this.counterValue2 < 0) {
            this.setEnabled(false);
            return;
        }
        this.counterValue3 = -1;
        int var33 = var1.player.getInventory().getSelectedSlot();
        this.counterValue5 = var33;
        float var36 = var1.player.getYRot();
        this.floatValue = var36;
        float var39 = var1.player.getXRot();
        this.floatValue1 = var39;
        boolean var41 = this.isEligible();
        this.enabledState = var41;
        if (this.enabledState) {
            var51 = -90.0F;
        } else {
            float var53 = this.getTargetPitch();
            float var52 = Math.min(90.0F, var53);
            var51 = Math.max(-90.0F, var52);
        }
        this.floatValue2 = var51;
        this.labelText = "AIM";
        this.counterValue = 0;
        this.counterValue1 = 0;
        this.enabledState4 = false;
        this.enabledState5 = false;
        this.counterValue6 = 0;
        this.applyStep1();
    }

    public void onDisable() {
        Minecraft var1 = Minecraft.getInstance();
        this.enabledState1 = false;
        this.enabledState2 = false;
        this.counterValue6 = 0;
        this.applyStep2();
        if (var1.player != null) {
            int var17 = this.counterValue3;
            this.applyStep(var17);
            if (this.isSilentAim()) {
                AimAssist.clearSilentRotation();
            }
            this.labelText = "IDLE";
            return;
        }
    }

    private static Overtick evaluate() {
        Overtick var8 = null;
        ModuleBase var3 = ModuleRegistry.get("Overtick");
        if ((var3 instanceof Overtick)) {
            var8 = ((Overtick) var3);
        } else {
            var8 = null;
        }
        return var8;
    }

    private void applyStep1() {
        int var12 = 0;
        Overtick var1 = evaluate();
        if (var1 == null) {
            return;
        }
        if (!var1.isEnabled()) {
            var12 = 1;
        } else {
            var12 = 0;
        }
        this.enabledState3 = var12;
        if (this.enabledState3) {
            try {
                var1.setEnabled(true);
            } catch (Throwable ignored) {
            }
        }
    }

    private void applyStep2() {
        if (!this.enabledState3) {
            return;
        }
        Overtick var6 = evaluate();
        if (var6 != null) {
            try {
                var6.applyStep7();
            } catch (Throwable ignored) {
            }
            try {
                var6.applyStep8();
            } catch (Throwable ignored) {
            }
            if (var6.isEnabled()) {
                try {
                    var6.setEnabled(false);
                } catch (Throwable ignored) {
                }
            }
            this.enabledState3 = false;
            return;
        }
    }

    public void onTick() {
        int var7 = 0;
        String var148 = null;
        Minecraft var1 = Minecraft.getInstance();
        if (var1.player != null) {
            if (var1.level == null) {
            }
        }
        this.enabledState1 = false;
        if (this.isEnabled()) {
            this.setEnabled(false);
        }
        return;
        String var15 = this.labelText;
        var7 = -1;
        switch (var15.hashCode()) {
            case -1836143820 -> {}
            case 64805 -> {}
            case 2104194 -> {}
            case 2158134 -> {}
            case 2288686 -> {}
            case 2656629 -> {}
            case 1815502446 -> {}
            default -> {}
        }
        if (var15.equals("AIM")) {
            var7 = 0;
            if (var15.equals("FIRE")) {
                var7 = 2;
                boolean var125 = var15.equals("SWITCH");
                if (var125) {
                    var7 = 1;
                    if (var15.equals("WAIT")) {
                        var7 = 3;
                        if (var15.equals("JUMP")) {
                            var7 = 4;
                            if (var15.equals("RESTORE")) {
                                var7 = 5;
                                if (var15.equals("DONE")) {
                                    var7 = 6;
                                }
                                switch (var7) {
                                    case 0 -> {}
                                    case 1 -> {}
                                    case 2 -> {}
                                    case 3 -> {}
                                    case 4 -> {}
                                    case 5 -> {}
                                    case 6 -> {}
                                    default -> {}
                                }
                                int var179 = (this.counterValue + 1);
                                this.counterValue = var179;
                                float var182 = ((float) this.counterValue);
                                float var232 = ((float) this.getAimTicks());
                                float var183 = (var182 / var232);
                                float var86 = Math.min(1.0F, var183);
                                float var88 = this.floatValue1;
                                float var185 = this.floatValue2;
                                float var234 = this.floatValue1;
                                float var187 = ((var185 - var234) * var86);
                                float var89 = (var88 + var187);
                                float var189 = this.floatValue;
                                float var237 = this.computeFloatMetric(var89);
                                this.applyStep(var189, var237);
                                int var92 = this.counterValue;
                                int var191 = this.getAimTicks();
                                if (var92 >= var191) {
                                    this.counterValue = 0;
                                    this.labelText = "SWITCH";
                                    this.setEnabled(false);
                                    if (this.counterValue == 0) {
                                        float var158 = this.floatValue;
                                        float var249 = this.floatValue2;
                                        float var225 = this.computeFloatMetric(var249);
                                        this.applyStep(var158, var225);
                                        int var160 = (this.counterValue6 + 1);
                                        this.counterValue6 = var160;
                                        int var162 = (this.counterValue1 + 1);
                                        this.counterValue1 = var162;
                                    }
                                    if (this.isCombo()) {
                                        int var68 = this.counterValue1;
                                        int var164 = this.getComboCount();
                                        if (var68 < var164) {
                                            int var166 = (this.counterValue + 1);
                                            this.counterValue = var166;
                                            int var71 = this.counterValue;
                                            int var168 = this.getShotDelay();
                                            if (var71 > var168) {
                                                this.counterValue = 0;
                                            }
                                        } else {
                                        }
                                    }
                                    this.counterValue = 0;
                                    String var172 = this.getMode();
                                    if (!"Hold".equalsIgnoreCase(var172)) {
                                        this.applyStep2();
                                    }
                                    this.applyStep3();
                                    this.labelText = "WAIT";
                                    if (this.counterValue == 0) {
                                        int var196 = this.counterValue3;
                                        this.applyStep(var196);
                                    }
                                    int var198 = (this.counterValue + 1);
                                    this.counterValue = var198;
                                    float var201 = ((float) this.counterValue);
                                    float var241 = ((float) this.getRestoreTicks());
                                    float var202 = (var201 / var241);
                                    float var101 = Math.min(1.0F, var202);
                                    float var103 = this.floatValue2;
                                    float var204 = this.floatValue1;
                                    float var243 = this.floatValue2;
                                    float var206 = ((var204 - var243) * var101);
                                    float var104 = (var103 + var206);
                                    float var208 = this.floatValue;
                                    float var246 = this.computeFloatMetric(var104);
                                    this.applyStep(var208, var246);
                                    int var107 = this.counterValue;
                                    int var210 = this.getRestoreTicks();
                                    if (var107 >= var210) {
                                        float var212 = this.floatValue;
                                        float var248 = this.floatValue1;
                                        this.applyStep(var212, var248);
                                        if (this.isSilentAim()) {
                                            AimAssist.clearSilentRotation();
                                        }
                                        this.labelText = "DONE";
                                        this.enabledState2 = true;
                                        this.labelText = "RESTORE";
                                        if (this.counterValue1 == 0) {
                                            int var175 = this.counterValue2;
                                            this.applyStep(var175);
                                        }
                                        this.counterValue = 0;
                                        this.labelText = "FIRE";
                                        int var137 = (this.counterValue + 1);
                                        this.counterValue = var137;
                                        String var139 = this.getMode();
                                        boolean var27 = "Hold".equalsIgnoreCase(var139);
                                        Overtick var28 = evaluate();
                                        if (this.enabledState4) {
                                            if (!this.enabledState5) {
                                                this.enabledState2 = true;
                                                this.enabledState5 = true;
                                                this.counterValue = 0;
                                            }
                                        } else {
                                            if (var27) {
                                                if (var28 != null) {
                                                    if (!var28.isEligible2()) {
                                                    }
                                                }
                                                this.enabledState4 = false;
                                                this.enabledState5 = false;
                                                this.applyStep2();
                                                this.counterValue = 0;
                                                this.labelText = "RESTORE";
                                            } else {
                                                if (this.enabledState5) {
                                                    this.enabledState4 = false;
                                                    this.enabledState5 = false;
                                                    this.counterValue = 0;
                                                    this.labelText = "RESTORE";
                                                }
                                                int var37 = this.counterValue;
                                                int var223 = this.getDelay();
                                                int var144 = Math.max(20, var223);
                                                if (var37 > var144) {
                                                    if (!this.enabledState4) {
                                                        if (!this.enabledState5) {
                                                            this.applyStep2();
                                                            this.counterValue = 0;
                                                            if (this.isJump()) {
                                                                var148 = "JUMP";
                                                            } else {
                                                                var148 = "RESTORE";
                                                            }
                                                            this.labelText = var148;
                                                        }
                                                    }
                                                }
                                            }
                                        }
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

    private void applyStep(float arg0, float arg1) {
        Minecraft var3 = Minecraft.getInstance();
        if (var3.player == null) {
            return;
        }
        this.floatValue3 = arg0;
        this.floatValue4 = arg1;
        this.enabledState1 = true;
        if (this.isSilentAim()) {
            AimAssist.applyStep(arg0, arg1);
        } else {
            var3.player.setYRot(arg0);
            var3.player.setXRot(arg1);
        }
    }

    private float computeFloatMetric(float arg0) {
        float var5 = 0.0F;
        Minecraft var2 = Minecraft.getInstance();
        try {
            float var20 = ((((float) ((Double) var2.options.sensitivity().get())) * 0.6F) + 0.2F);
            var5 = ((((var20 * var20) * var20) * 8.0F) * 0.15F);
        } catch (Throwable ignored) {
        }
        var5 = 0.15F;
        if (Float.compare(var5, 1.0E-4F) < 0) {
            return arg0;
        }
        float var42 = var5;
        float var32 = ((float) Math.round((arg0 / var42)));
        float var43 = var5;
        return (var32 * var43);
    }

    private void applyStep(Minecraft arg0) {
        try {
            LocalPlayer var6 = arg0.player;
            if (var6 != null) {
                if (arg0.gameMode == null) {
                }
            }
            return;
            try {
                if (var6.isUsingItem()) {
                    var6.stopUsingItem();
                }
                MultiPlayerGameMode var14 = arg0.gameMode;
                InteractionHand var4 = InteractionHand.MAIN_HAND;
                InteractionResult var15 = var14.useItem(var6, var4);
            } catch (Throwable ignored) {
            }
            return;
        } catch (Throwable ignored) {
        }
    }

    private void applyStep3() {
        Minecraft var1 = Minecraft.getInstance();
        if (var1.player != null) {
            LocalPlayer var7 = var1.player;
            float var10 = this.floatValue;
            var7.setYRot(var10);
            LocalPlayer var9 = var1.player;
            float var12 = this.floatValue1;
            var9.setXRot(var12);
        }
    }

    private boolean isEligible() {
        int var6 = 0;
        Minecraft var1 = Minecraft.getInstance();
        if (var1.player != null) {
            if (var1.level == null) {
            }
        }
        return false;
        BlockPos var15 = var1.player.blockPosition();
        var6 = 1;
        while (true) {
            int var17 = var6;
            int var26 = this.getUpRange();
            if (var17 > var26) break;
            ClientLevel var19 = var1.level;
            int var3 = var6;
            BlockPos var28 = var15.above(var3);
            BlockState var20 = var19.getBlockState(var28);
            if (var20 != null) {
                if (!var20.isAir()) {
                    return true;
                }
            }
            var6 = (var6 + 1);
        }
        return false;
    }

    private int computeColorOrIndex() {
        int var5 = 0;
        Minecraft var1 = Minecraft.getInstance();
        if (var1.player == null) {
            return -1;
        }
        Inventory var11 = var1.player.getInventory();
        var5 = 0;
        while (true) {
            if (var5 >= 9) break;
            int var26 = var5;
            ItemStack var16 = var11.getItem(var26);
            if (!var16.isEmpty()) {
                if (String.valueOf(var16.getItem().getDescriptionId()).contains("wind_charge")) {
                    return var5;
                }
            }
            var5 = (var5 + 1);
        }
        return -1;
    }

    private void applyStep(int arg0) {
        if (arg0 >= 0) {
            if (arg0 > 8) {
            }
        }
        return;
        Minecraft var7 = Minecraft.getInstance();
        if (var7.player == null) {
            return;
        }
        if (this.counterValue3 == -1) {
            int var24 = var7.player.getInventory().getSelectedSlot();
            this.counterValue3 = var24;
        }
        int var26 = this.counterValue5;
        if (arg0 != var26) {
            int var28 = this.counterValue4;
            if (arg0 == var28) {
            }
        }
        return;
        this.counterValue4 = arg0;
        try {
            InventoryHelper.setSelected(var7.player.getInventory(), arg0);
        } catch (Throwable ignored) {
        }
    }

}
