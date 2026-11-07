package need.clarity.modules.combat;

import java.util.Random;
import java.util.WeakHashMap;
import need.clarity.modules.ModuleBase;
import need.clarity.util.InventoryHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

public class ShieldBreaker extends ModuleBase {
    private static final WeakHashMap fieldVal;
    private static final long timestampMs = 600L;
    private ShieldBreakerStage shieldBreakerStage;
    private long timestampMs1;
    private int counterValue;
    private final Random fieldVal1;

    public ShieldBreaker() {
        super("Shield Breaker", "Combat");
        ShieldBreakerStage var8 = ShieldBreakerStage.IDLE;
        this.shieldBreakerStage = var8;
        this.timestampMs1 = 0L;
        this.counterValue = -1;
        Random var12 = new Random();
        this.fieldVal1 = var12;
    }

    public void onEnable() {
        Minecraft var2 = Minecraft.getInstance();
        this.applyStep(var2);
    }

    public void onDisable() {
        Minecraft var1 = Minecraft.getInstance();
        if (this.counterValue != -1) {
            if (var1.player != null) {
                Inventory var10 = var1.player.getInventory();
                int var13 = this.counterValue;
                applyStep(var10, var13);
            }
            this.applyStep(var1);
            return;
        }
    }

    private void applyStep(Minecraft arg0) {
        if (this.counterValue != -1) {
            if (arg0.player != null) {
                Inventory var9 = arg0.player.getInventory();
                int var14 = this.counterValue;
                applyStep(var9, var14);
            }
            ShieldBreakerStage var15 = ShieldBreakerStage.IDLE;
            this.shieldBreakerStage = var15;
            this.timestampMs1 = 0L;
            this.counterValue = -1;
            return;
        }
    }

    public void onTick() {
        Minecraft var1 = Minecraft.getInstance();
        if (!this.isEnabled()) {
            return;
        }
        LocalPlayer var24 = var1.player;
        if (var24 != null) {
            if (var1.level == null) {
            }
        }
        return;
        Boolean var161 = true;
        if (((Boolean) this.getSetting("Not in GUI", var161))) {
            if (var1.screen != null) {
                return;
            }
        }
        Boolean var163 = true;
        if (((Boolean) this.getSetting("Not While Using", var163))) {
            if (var24.isUsingItem()) {
                ShieldBreakerStage var43 = this.shieldBreakerStage;
                ShieldBreakerStage var125 = ShieldBreakerStage.IDLE;
                if (var43 != var125) {
                    if (this.counterValue != -1) {
                        Inventory var47 = var24.getInventory();
                        int var128 = this.counterValue;
                        applyStep(var47, var128);
                        this.counterValue = -1;
                    }
                    ShieldBreakerStage var130 = ShieldBreakerStage.IDLE;
                    this.shieldBreakerStage = var130;
                }
                return;
            }
        }
        String var52 = ((String) this.getSetting("Mode", "Normal"));
        Boolean var166 = false;
        boolean var56 = ((Boolean) this.getSetting("Swap Back", var166));
        Integer var168 = 50;
        int var60 = ((Integer) this.getSetting("Delay_lo", var168));
        Integer var170 = 75;
        int var64 = ((Integer) this.getSetting("Delay_hi", var170));
        Integer var172 = 50;
        int var68 = ((Integer) this.getSetting("Swap Back Delay_lo", var172));
        Integer var174 = 75;
        int var72 = ((Integer) this.getSetting("Swap Back Delay_hi", var174));
        long var73 = System.currentTimeMillis();
        ShieldBreakerStage var76 = this.shieldBreakerStage;
        ShieldBreakerStage var137 = ShieldBreakerStage.WAITING_TO_RESTORE;
        if (var76 == var137) {
            long var176 = this.timestampMs1;
            long var79 = (var73 - var176);
            long var180 = ((long) this.computeColorOrIndex(var68, var72));
            if (Long.compare(var79, var180) >= 0) {
                if (this.counterValue != -1) {
                    Inventory var85 = var24.getInventory();
                    int var140 = this.counterValue;
                    applyStep(var85, var140);
                    this.counterValue = -1;
                }
                ShieldBreakerStage var142 = ShieldBreakerStage.IDLE;
                this.shieldBreakerStage = var142;
            }
            return;
        }
        ShieldBreakerStage var89 = this.shieldBreakerStage;
        ShieldBreakerStage var143 = ShieldBreakerStage.WAITING_TO_ATTACK;
        if (var89 == var143) {
            long var184 = this.timestampMs1;
            long var109 = (var73 - var184);
            long var188 = ((long) this.computeColorOrIndex(var60, var64));
            if (Long.compare(var109, var188) >= 0) {
                LivingEntity var113 = evaluate(var1);
                if (var113 != null) {
                    if (!isEligible(var113)) {
                    }
                }
                if (this.counterValue != -1) {
                    Inventory var121 = var24.getInventory();
                    int var158 = this.counterValue;
                    applyStep(var121, var158);
                    this.counterValue = -1;
                }
                ShieldBreakerStage var160 = ShieldBreakerStage.IDLE;
                this.shieldBreakerStage = var160;
                return;
            }
        }
        LivingEntity var91 = evaluate(var1);
        if (var91 != null) {
            if (!isEligible(var91)) {
            }
        }
        return;
        int var97 = computeColorOrIndex(var24.getInventory());
        if (var97 < 0) {
            return;
        }
        int var146 = computeColorOrIndex1(var24.getInventory());
        this.counterValue = var146;
        int var148 = this.counterValue;
        if (var97 == var148) {
            this.counterValue = -1;
            this.applyStep(var1, var24, var91, var52, var56);
        } else {
            applyStep(var24.getInventory(), var97);
            ShieldBreakerStage var152 = ShieldBreakerStage.WAITING_TO_ATTACK;
            this.shieldBreakerStage = var152;
            this.timestampMs1 = var73;
        }
        return;
        this.applyStep(var1, var24, var113, var52, var56);
    }

    private void applyStep(Minecraft arg0, LocalPlayer arg1, LivingEntity arg2, String arg3, boolean arg4) {
        if (computeColorOrIndex(arg1.getInventory()) < 0) {
            if (this.counterValue != -1) {
                Inventory var16 = arg1.getInventory();
                int var38 = this.counterValue;
                applyStep(var16, var38);
                this.counterValue = -1;
            }
            ShieldBreakerStage var40 = ShieldBreakerStage.IDLE;
            this.shieldBreakerStage = var40;
            return;
        }
        arg0.gameMode.attack(arg1, arg2);
        InteractionHand var42 = InteractionHand.MAIN_HAND;
        arg1.swing(var42);
        arg1.resetAttackStrengthTicker();
        if ("Stun".equals(arg3)) {
            arg0.gameMode.attack(arg1, arg2);
            InteractionHand var45 = InteractionHand.MAIN_HAND;
            arg1.swing(var45);
            arg1.resetAttackStrengthTicker();
        }
        applyStep(arg2);
        if (arg4) {
            if (this.counterValue != -1) {
                ShieldBreakerStage var47 = ShieldBreakerStage.WAITING_TO_RESTORE;
                this.shieldBreakerStage = var47;
                long var48 = System.currentTimeMillis();
                this.timestampMs1 = var48;
            } else {
                this.counterValue = -1;
                ShieldBreakerStage var51 = ShieldBreakerStage.IDLE;
                this.shieldBreakerStage = var51;
            }
            return;
        }
    }

    public static boolean isEligible(LivingEntity arg0) {
        if (arg0 == null) {
            return false;
        }
        Long var10 = ((Long) fieldVal.get(arg0));
        if (var10 != null) {
            long var12 = System.currentTimeMillis();
            long var30 = var10;
            if (Long.compare((var12 - var30), 600L) < 0) {
                if (!arg0.isUsingItem()) {
                    Object var26 = fieldVal.remove(arg0);
                } else {
                    return false;
                }
            }
            Object var20 = fieldVal.remove(arg0);
        }
        return arg0.isBlocking();
    }

    public static void applyStep(LivingEntity arg0) {
        WeakHashMap var1 = fieldVal;
        Long var6 = System.currentTimeMillis();
        Object var5 = var1.put(arg0, var6);
    }

    private static LivingEntity evaluate(Minecraft arg0) {
        Entity var4 = arg0.crosshairPickEntity;
        if ((var4 instanceof LivingEntity)) {
            return ((LivingEntity) var4);
        }
        return null;
    }

    private static int computeColorOrIndex(Inventory arg0) {
        int var3 = 0;
        var3 = 0;
        while (true) {
            if (var3 < 9) {
                int var14 = var3;
                ItemStack var7 = arg0.getItem(var14);
                if (!var7.isEmpty()) {
                    TagKey var15 = ItemTags.AXES;
                    if (var7.is(var15)) {
                        return var3;
                    }
                }
            }
            return -1;
            var3 = (var3 + 1);
        }
    }

    private static int computeColorOrIndex1(Inventory arg0) {
        return InventoryHelper.getSelected(arg0);
    }

    private static void applyStep(Inventory arg0, int arg1) {
        InventoryHelper.setSelected(arg0, arg1);
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
        WeakHashMap var0 = new WeakHashMap();
        fieldVal = var0;
    }

}
