package need.clarity.modules.utility;

import java.util.Optional;
import java.util.function.Function;
import need.clarity.modules.ModuleBase;
import need.clarity.util.InventoryHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class AutoTool extends ModuleBase {
    private final Minecraft minecraft;
    private boolean enabledState;
    private int counterValue;
    private long timestampMs;
    private long timestampMs1;
    private boolean enabledState1;

    public AutoTool() {
        super("Auto Tool", "Utility");
        Minecraft var10 = Minecraft.getInstance();
        this.minecraft = var10;
        this.enabledState = false;
        this.counterValue = -1;
        this.timestampMs = -1L;
        this.timestampMs1 = -1L;
        this.enabledState1 = false;
    }

    public void onDisable() {
        this.enabledState = false;
        this.counterValue = -1;
        this.timestampMs = -1L;
        this.timestampMs1 = -1L;
        this.enabledState1 = false;
    }

    public void onTick() {
        int var90 = 0;
        if (this.minecraft.player != null) {
            if (this.minecraft.level != null) {
                if (this.minecraft.gameMode == null) {
                }
            }
            return;
            if (this.minecraft.screen == null) {
                if (this.minecraft.options.keyAttack.isDown()) {
                    if (this.minecraft.hitResult != null) {
                        HitResult.Type var31 = this.minecraft.hitResult.getType();
                        HitResult.Type var2 = HitResult.Type.BLOCK;
                        if (var31 == var2) {
                            var90 = 1;
                        } else {
                        }
                    }
                    var90 = 0;
                    int var5 = var90;
                    long var32 = System.currentTimeMillis();
                    if (var5 != 0) {
                        if (!this.enabledState) {
                            int var95 = InventoryHelper.getSelected(this.minecraft.player.getInventory());
                            this.counterValue = var95;
                            this.timestampMs1 = -1L;
                            this.enabledState1 = false;
                            Integer var124 = 0;
                            long var139 = ((long) ((Integer) this.getSetting("Swap Delay", var124)));
                            long var102 = (var32 + var139);
                            this.timestampMs = var102;
                        }
                        if (var5 != 0) {
                            if (this.timestampMs != -1L) {
                                long var137 = this.timestampMs;
                                if (Long.compare(var32, var137) >= 0) {
                                    this.timestampMs = -1L;
                                    this.applyStep();
                                }
                            }
                        } else {
                            if (this.enabledState) {
                                this.timestampMs = -1L;
                                if (this.enabledState1) {
                                    Boolean var126 = false;
                                    if (((Boolean) this.getSetting("Swap Back", var126))) {
                                        if (this.counterValue != -1) {
                                            Integer var128 = 0;
                                            long var142 = ((long) ((Integer) this.getSetting("Swap Back Delay", var128)));
                                            long var111 = (var32 + var142);
                                            this.timestampMs1 = var111;
                                        } else {
                                            this.counterValue = -1;
                                        }
                                        this.enabledState1 = false;
                                    }
                                }
                            }
                            if (var5 == 0) {
                                if (this.timestampMs1 != -1L) {
                                    long var132 = this.timestampMs1;
                                    if (Long.compare(var32, var132) >= 0) {
                                        if (this.counterValue != -1) {
                                            Inventory var76 = this.minecraft.player.getInventory();
                                            int var116 = this.counterValue;
                                            InventoryHelper.setSelected(var76, var116);
                                        }
                                        this.counterValue = -1;
                                        this.timestampMs1 = -1L;
                                    }
                                    this.enabledState = var5;
                                    return;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void applyStep() {
        float var9 = 0.0F;
        int var10 = 0;
        int var11 = 0;
        if (this.minecraft.player != null) {
            if (this.minecraft.level == null) {
            }
        }
        return;
        if (this.minecraft.hitResult != null) {
            HitResult.Type var25 = this.minecraft.hitResult.getType();
            HitResult.Type var2 = HitResult.Type.BLOCK;
            if (var25 != var2) {
            }
        }
        return;
        BlockPos var30 = ((BlockHitResult) this.minecraft.hitResult).getBlockPos();
        BlockState var34 = this.minecraft.level.getBlockState(var30);
        Boolean var80 = false;
        boolean var38 = ((Boolean) this.getSetting("Only Pickaxe", var80));
        Inventory var42 = this.minecraft.player.getInventory();
        int var44 = InventoryHelper.getSelected(var42);
        ItemStack var70 = var42.getItem(var44);
        var9 = this.computeFloatMetric(var70, var34);
        var10 = -1;
        var11 = 0;
        while (true) {
            if (var11 >= 9) break;
            if (var11 == var44) {
            } else {
                int var73 = var11;
                ItemStack var52 = var42.getItem(var73);
                if (var52.isEmpty()) {
                } else {
                    if (var38) {
                        TagKey var74 = ItemTags.PICKAXES;
                        if (!var52.is(var74)) {
                        } else {
                            float var59 = this.computeFloatMetric(var52, var34);
                            float var76 = var9;
                            if (Float.compare(var59, var76) > 0) {
                                var9 = var59;
                                var10 = var11;
                            }
                            var11 = (var11 + 1);
                        }
                    }
                }
            }
        }
        if (var10 != -1) {
            int var78 = var10;
            InventoryHelper.setSelected(var42, var78);
            this.enabledState1 = true;
        }
    }

    private float computeFloatMetric(ItemStack arg0, BlockState arg1) {
        float var6 = 0.0F;
        if (arg0.isEmpty()) {
            return 1.0F;
        }
        var6 = arg0.getDestroySpeed(arg1);
        if (Float.compare(var6, 1.0F) > 0) {
            if (this.minecraft.level != null) {
                try {
                    RegistryAccess var21 = this.minecraft.level.registryAccess();
                    ResourceKey var37 = Registries.ENCHANTMENT;
                    Registry var22 = var21.lookupOrThrow(var37);
                    ResourceKey var38 = Enchantments.EFFICIENCY;
                    Optional var24 = var22.get(var38);
                    Function var40 = (p0) -> AutoTool.lambda$a(arg0, p0);
                    Optional var26 = var24.map(var40);
                    Integer var42 = 0;
                    int var29 = ((Integer) var26.orElse(var42));
                    if (var29 > 0) {
                        float var31 = var6;
                        float var46 = ((float) ((var29 * var29) + 1));
                        var6 = (var31 + var46);
                    }
                } catch (Exception ignored) {
                }
            }
            return var6;
        }
    }

}
