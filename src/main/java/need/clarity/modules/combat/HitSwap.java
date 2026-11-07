package need.clarity.modules.combat;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.Iterator;
import java.util.Optional;
import java.util.Random;
import need.clarity.EventStartAttack;
import need.clarity.PerAttackEvents;
import need.clarity.config.ClarityConfig;
import need.clarity.modules.ModuleBase;
import need.clarity.util.InventoryHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;

public class HitSwap extends ModuleBase {
    private static final double CONST_DBL = -0.25;
    private HitSwapStage hitSwapStage;
    private long timestampMs;
    private int counterValue;
    private int counterValue1;
    private LivingEntity targetEntity;
    private final Random fieldVal;

    public HitSwap() {
        super("Hit Swap", "Combat");
        HitSwapStage var12 = HitSwapStage.IDLE;
        this.hitSwapStage = var12;
        this.timestampMs = 0L;
        this.counterValue = -1;
        this.counterValue1 = -1;
        this.targetEntity = null;
        Random var18 = new Random();
        this.fieldVal = var18;
        PerAttackEvents.register((p0) -> this.onStartAttack(p0));
    }

    public void onEnable() {
        Minecraft var2 = Minecraft.getInstance();
        this.applyStep(var2);
    }

    public void onDisable() {
        Minecraft var2 = Minecraft.getInstance();
        this.applyStep(var2);
    }

    private void applyStep(Minecraft arg0) {
        if (arg0.player != null) {
            if (this.counterValue >= 0) {
                Inventory var9 = arg0.player.getInventory();
                int var15 = this.counterValue;
                InventoryHelper.setSelected(var9, var15);
            }
            HitSwapStage var16 = HitSwapStage.IDLE;
            this.hitSwapStage = var16;
            this.timestampMs = 0L;
            this.counterValue = -1;
            this.counterValue1 = -1;
            this.targetEntity = null;
            return;
        }
    }

    private void onStartAttack(EventStartAttack arg0) {
        int var83 = 0;
        int var84 = 0;
        if (!this.isEnabled()) {
            return;
        }
        HitSwapStage var16 = this.hitSwapStage;
        HitSwapStage var3 = HitSwapStage.IDLE;
        if (var16 != var3) {
            return;
        }
        Entity var18 = arg0.getTarget();
        if (var18 == null) {
            return;
        }
        if (!this.isEligible(var18)) {
            return;
        }
        if ((var18 instanceof LivingEntity)) {
            LivingEntity var25 = ((LivingEntity) var18);
        } else {
            return;
        }
        Minecraft var26 = Minecraft.getInstance();
        if (var26.player == null) {
            return;
        }
        Boolean var107 = false;
        if (((Boolean) this.getSetting("Only Weapon", var107))) {
            if (!isEligible1(var26.player)) {
                return;
            }
        }
        Boolean var109 = false;
        if (((Boolean) this.getSetting("Stun Slam", var109))) {
            if (isEligible(var25)) {
                var84 = 1;
            } else {
            }
        }
        var84 = 0;
        int var9 = var84;
        if (var9 == 0) {
            Boolean var111 = false;
            if (((Boolean) this.getSetting("Check Shield", var111))) {
                var83 = 1;
            } else {
            }
        }
        var83 = 0;
        if (var83 != 0) {
            if (isEligible(var25)) {
                return;
            }
        }
        int var92 = InventoryHelper.getSelected(var26.player.getInventory());
        this.counterValue = var92;
        LocalPlayer var94 = var26.player;
        boolean var52 = this.isEligible(var94);
        Inventory var114 = var26.player.getInventory();
        int var96 = this.computeColorOrIndex(var114, var52);
        this.counterValue1 = var96;
        if (this.counterValue1 < 0) {
            return;
        }
        if (var9 != 0) {
            int var60 = computeColorOrIndex(var26.player.getInventory());
            if (var60 < 0) {
                return;
            }
            this.targetEntity = var25;
            int var99 = this.counterValue;
            if (var60 != var99) {
                InventoryHelper.setSelected(var26.player.getInventory(), var60);
            }
            HitSwapStage var101 = HitSwapStage.SLAM_WAIT_TO_FIRE;
            this.applyStep(var101);
        } else {
            Inventory var70 = var26.player.getInventory();
            int var103 = this.counterValue1;
            InventoryHelper.setSelected(var70, var103);
            Integer var116 = 0;
            int var74 = ((Integer) this.getSetting("Swap Delay_lo", var116));
            Integer var118 = 0;
            int var78 = ((Integer) this.getSetting("Swap Delay_hi", var118));
            if (var74 == 0) {
                if (var78 == 0) {
                    this.getSwapBack();
                } else {
                    HitSwapStage var106 = HitSwapStage.WAIT_SWAP;
                    this.applyStep(var106);
                }
                return;
            }
        }
    }

    public void onTick() {
        if (!this.isEnabled()) {
            return;
        }
        Minecraft var13 = Minecraft.getInstance();
        if (var13.player == null) {
            return;
        }
        long var16 = System.currentTimeMillis();
        Integer var111 = 50;
        int var21 = ((Integer) this.getSetting("Step Delay", var111));
        switch (this.hitSwapStage.ordinal()) {
            case 1 -> {}
            case 2 -> {}
            case 3 -> {}
            case 4 -> {}
            default -> {}
        }
        Integer var121 = 100;
        int var55 = ((Integer) this.getSetting("Swap Back Delay_lo", var121));
        Integer var123 = 150;
        int var59 = ((Integer) this.getSetting("Swap Back Delay_hi", var123));
        long var125 = this.timestampMs;
        long var62 = (var16 - var125);
        long var129 = ((long) this.computeColorOrIndex(var55, var59));
        if (Long.compare(var62, var129) >= 0) {
            this.applyStep1(var13);
            long var113 = this.timestampMs;
            long var27 = (var16 - var113);
            long var116 = ((long) var21);
            if (Long.compare(var27, var116) >= 0) {
                if (this.counterValue1 >= 0) {
                    Inventory var34 = var13.player.getInventory();
                    int var95 = this.counterValue1;
                    InventoryHelper.setSelected(var34, var95);
                }
                if (this.targetEntity != null) {
                    if (this.targetEntity.isAlive()) {
                        LivingEntity var97 = this.targetEntity;
                        applyStep(var13, var97);
                    }
                    this.targetEntity = null;
                    Boolean var119 = true;
                    if (((Boolean) this.getSetting("Swap Back", var119))) {
                        if (this.counterValue >= 0) {
                            HitSwapStage var100 = HitSwapStage.SLAM_WAIT_RESTORE;
                            this.applyStep(var100);
                        } else {
                            this.counterValue = -1;
                            HitSwapStage var102 = HitSwapStage.IDLE;
                            this.hitSwapStage = var102;
                            Integer var143 = 0;
                            int var83 = ((Integer) this.getSetting("Swap Delay_lo", var143));
                            Integer var145 = 0;
                            int var87 = ((Integer) this.getSetting("Swap Delay_hi", var145));
                            long var147 = this.timestampMs;
                            long var90 = (var16 - var147);
                            long var151 = ((long) this.computeColorOrIndex(var83, var87));
                            if (Long.compare(var90, var151) >= 0) {
                                this.getSwapBack();
                                Integer var132 = 100;
                                int var69 = ((Integer) this.getSetting("Swap Back Delay_lo", var132));
                                Integer var134 = 150;
                                int var73 = ((Integer) this.getSetting("Swap Back Delay_hi", var134));
                                long var136 = this.timestampMs;
                                long var76 = (var16 - var136);
                                long var140 = ((long) this.computeColorOrIndex(var69, var73));
                                if (Long.compare(var76, var140) >= 0) {
                                    this.applyStep1(var13);
                                }
                                return;
                            }
                        }
                    }
                }
            }
        }
    }

    private void applyStep(HitSwapStage arg0) {
        this.hitSwapStage = arg0;
        long var5 = System.currentTimeMillis();
        this.timestampMs = var5;
    }

    private void getSwapBack() {
        Boolean var17 = true;
        if (((Boolean) this.getSetting("Swap Back", var17))) {
            if (this.counterValue >= 0) {
                HitSwapStage var14 = HitSwapStage.WAIT_RESTORE;
                this.applyStep(var14);
            } else {
            }
        }
        this.counterValue = -1;
        HitSwapStage var16 = HitSwapStage.IDLE;
        this.hitSwapStage = var16;
    }

    private void applyStep1(Minecraft arg0) {
        if (this.counterValue >= 0) {
            if (arg0.player != null) {
                Inventory var9 = arg0.player.getInventory();
                int var12 = this.counterValue;
                InventoryHelper.setSelected(var9, var12);
                this.counterValue = -1;
            }
            HitSwapStage var14 = HitSwapStage.IDLE;
            this.hitSwapStage = var14;
            return;
        }
    }

    private static void applyStep(Minecraft arg0, LivingEntity arg1) {
        if (arg0.player == null) {
            return;
        }
        MultiPlayerGameMode var7 = arg0.gameMode;
        LocalPlayer var12 = arg0.player;
        var7.attack(var12, arg1);
        LocalPlayer var9 = arg0.player;
        InteractionHand var13 = InteractionHand.MAIN_HAND;
        var9.swing(var13);
        arg0.player.resetAttackStrengthTicker();
    }

    private static boolean isEligible(LivingEntity arg0) {
        int var15 = 0;
        if (!arg0.isUsingItem()) {
            return false;
        }
        ItemStack var6 = arg0.getUseItem();
        if (!var6.isEmpty()) {
            Item var2 = Items.SHIELD;
            if (!var6.is(var2)) {
            }
        }
        return false;
        if (arg0.getTicksUsingItem() >= 5) {
            var15 = 1;
        } else {
            var15 = 0;
        }
        return var15;
    }

    private boolean isEligible(LocalPlayer arg0) {
        int var34 = 0;
        int var7 = 0;
        String var13 = ((String) this.getSetting("Mode", "Breach"));
        var7 = -1;
        switch (var13.hashCode()) {
            case -1077596984 -> {}
            case -505546721 -> {}
            default -> {}
        }
        if (var13.equals("Density")) {
            var7 = 0;
            if (var13.equals("Dynamic")) {
                var7 = 1;
            }
            switch (var7) {
                case 0 -> {}
                case 1 -> {}
                default -> {}
            }
            double var24 = arg0.getDeltaMovement().y;
            Double var43 = -0.25;
            double var29 = ((Double) this.getSetting("Dynamic Threshold", var43));
            if (Double.compare(var24, var29) < 0) {
                var34 = 1;
            } else {
                var34 = 0;
                var34 = 1;
                var34 = 0;
            }
            return var34;
        }
    }

    private int computeColorOrIndex(Inventory arg0, boolean arg1) {
        int var65 = 0;
        int var10 = 0;
        int var11 = 0;
        Minecraft var3 = Minecraft.getInstance();
        if (var3.level == null) {
            return -1;
        }
        String var17 = ((String) this.getSetting("Mode", "Breach"));
        if ("Any".equals(var17)) {
            var65 = 0;
            while (true) {
                if (var65 >= 9) break;
                int var63 = var65;
                ItemStack var47 = arg0.getItem(var63);
                if (!var47.isEmpty()) {
                    Item var64 = Items.MACE;
                    if (var47.is(var64)) {
                        return var65;
                    }
                }
                var65 = (var65 + 1);
            }
            try {
                RegistryAccess var22 = var3.level.registryAccess();
                ResourceKey var56 = Registries.ENCHANTMENT;
                Registry var23 = var22.lookupOrThrow(var56);
            } catch (Exception ignored) {
            }
            Holder var27 = evaluate(var23, arg1);
            var10 = -1;
            var11 = 0;
            while (true) {
                if (var11 >= 9) break;
                int var59 = var11;
                ItemStack var32 = arg0.getItem(var59);
                if (!var32.isEmpty()) {
                    Item var60 = Items.MACE;
                    if (!var32.is(var60)) {
                    } else {
                        if (var27 != null) {
                            if (EnchantmentHelper.getItemEnchantmentLevel(var27, var32) > 0) {
                                return var11;
                            }
                            if (var10 < 0) {
                                var10 = var11;
                            }
                            var11 = (var11 + 1);
                        }
                    }
                }
            }
            return var10;
            return -1;
        }
    }

    private static int computeColorOrIndex(Inventory arg0) {
        int var3 = 0;
        var3 = 0;
        while (true) {
            if (var3 < 9) {
                int var14 = var3;
                ItemStack var8 = arg0.getItem(var14);
                if (!var8.isEmpty()) {
                    TagKey var15 = ItemTags.AXES;
                    if (var8.is(var15)) {
                        return var3;
                    }
                }
            }
            return -1;
            var3 = (var3 + 1);
        }
    }

    private static Holder evaluate(Registry arg0, boolean arg1) {
        Optional var6 = null;
        try {
            if (arg1) {
                ResourceKey var14 = Enchantments.DENSITY;
                var6 = arg0.get(var14);
            } else {
                ResourceKey var3 = Enchantments.BREACH;
                var6 = arg0.get(var3);
            }
            Holder var9 = ((Holder) var6.orElse(null));
        } catch (Exception ignored) {
        }
        return var9;
    }

    private static boolean isEligible1(LocalPlayer arg0) {
        int var16 = 0;
        ItemStack var4 = arg0.getMainHandItem();
        if (var4.isEmpty()) {
            return false;
        }
        TagKey var2 = ItemTags.SWORDS;
        if (!var4.is(var2)) {
            TagKey var18 = ItemTags.AXES;
            if (!var4.is(var18)) {
                TagKey var19 = ItemTags.MACE_ENCHANTABLE;
                if (!var4.is(var19)) {
                    if ((var4.getItem() instanceof TridentItem)) {
                    }
                }
                var16 = 1;
                var16 = 0;
                return var16;
            }
        }
    }

    private boolean isEligible(Entity arg0) {
        boolean var7 = this.isEligible("Players");
        boolean var9 = this.isEligible("Mobs");
        boolean var11 = this.isEligible("Entities");
        if ((arg0 instanceof Player)) {
            return var7;
        }
        if ((arg0 instanceof Mob)) {
            return var9;
        }
        if ((arg0 instanceof LivingEntity)) {
            return var11;
        }
        return var11;
    }

    private boolean isEligible(String arg0) {
        try {
            JsonObject var9 = ClarityConfig.getCurrent().getAsJsonObject("Hit Swap");
            if (var9 == null) {
            }
        } catch (Exception ignored) {
        }
        return true;
        try {
            JsonObject var15 = var9.getAsJsonObject("values");
            if (var15 == null) {
            }
        } catch (Exception ignored) {
        }
        return true;
        try {
            JsonElement var19 = var15.get("Targets");
            if (var19 != null) {
                if (!var19.isJsonArray()) {
                }
            }
        } catch (Exception ignored) {
        }
        return true;
        try {
            Iterator var25 = var19.getAsJsonArray().iterator();
            while (true) {
                if (!var25.hasNext()) break;
                JsonElement var30 = ((JsonElement) var25.next());
                if (!var30.isJsonPrimitive()) continue;
                if (!var30.getAsString().equals(arg0)) continue;
            }
            return true;
            return false;
        } catch (Exception ignored) {
        }
    }

    private int computeColorOrIndex(int arg0, int arg1) {
        if (arg1 <= arg0) {
            return arg0;
        }
        Random var11 = this.fieldVal;
        int var14 = ((arg1 - arg0) + 1);
        int var12 = var11.nextInt(var14);
        return (arg0 + var12);
    }

}
