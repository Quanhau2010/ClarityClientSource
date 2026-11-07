package need.clarity.modules.combat;

import com.mojang.blaze3d.vertex.PoseStack;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Predicate;
import need.clarity.WorldRenderEvent;
import need.clarity.modules.ModuleBase;
import need.clarity.modules.utility.Targets;
import need.clarity.util.ClarityRenderUtils;
import need.clarity.util.InventoryHelper;
import need.clarity.util.ThemeColors;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.DefaultedRegistry;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class AutoTrap extends ModuleBase {
    private static AutoTrap INSTANCE;
    private static final long timestampMs = 750L;
    private static final int CONST_INT = 90;
    private BlockPos targetBlockPos;
    private int counterValue;
    private static final int CONST_INT1 = 4;
    private boolean enabledState;
    private int counterValue1;
    private int counterValue2;
    private boolean enabledState1;
    private final List entriesList;
    private static final Field rightClickDelayField;
    private int counterValue3;

    public AutoTrap() {
        super("Auto Trap", "Combat");
        this.targetBlockPos = null;
        this.counterValue = -1;
        this.enabledState = false;
        this.counterValue1 = 0;
        this.counterValue2 = -1;
        this.enabledState1 = false;
        CopyOnWriteArrayList var21 = new CopyOnWriteArrayList();
        this.entriesList = var21;
        this.counterValue3 = -1;
        INSTANCE = this;
        WorldRenderEvent.register((p0, p1) -> this.renderWorldEffect(p0, p1));
    }

    public static AutoTrap evaluate() {
        return INSTANCE;
    }

    public void onDisable() {
        Minecraft var1 = Minecraft.getInstance();
        this.applyStep(var1);
        AimAssist.clearSilentRotation();
        this.applyStep();
    }

    private void applyStep() {
        this.targetBlockPos = null;
        this.counterValue = -1;
        this.counterValue1 = 0;
        this.enabledState = false;
        this.enabledState1 = false;
        this.counterValue3 = -1;
        this.entriesList.clear();
    }

    public void onTick() {
        Set var62 = null;
        Minecraft var1 = Minecraft.getInstance();
        if (var1.player != null) {
            if (var1.level != null) {
                if (var1.screen != null) {
                }
            }
            return;
            int var25 = ((int) var1.level.getGameTime());
            if (this.enabledState1) {
                if (this.isEligible(var1)) {
                    this.counterValue2 = -1;
                    this.enabledState1 = false;
                    AimAssist.clearSilentRotation();
                    return;
                }
            }
            int var71 = this.counterValue1;
            if (var25 < var71) {
                return;
            }
            Player var30 = evaluate(var1);
            if (var30 == null) {
                this.applyStep2(var1);
                return;
            }
            Boolean var92 = false;
            boolean var35 = ((Boolean) this.getSetting("Block Whitelist", var92));
            if (var35) {
                var62 = this.evaluate();
            } else {
                var62 = Collections.emptySet();
            }
            Set var11 = var62;
            Boolean var94 = false;
            boolean var40 = ((Boolean) this.getSetting("Piercing", var94));
            List var42 = this.evaluate(var1, var30, var35, var11, var40);
            if (var42.isEmpty()) {
                if (isEligible(var1, var30)) {
                    if (this.counterValue2 != -1) {
                        if (!this.enabledState1) {
                            this.setRightClickDelay(var1, var25);
                        }
                        this.targetBlockPos = null;
                        this.counterValue = -1;
                    }
                }
                return;
            }
            String var56 = ((String) this.getSetting("Mode", "Instant"));
            if ("Legit".equals(var56)) {
                this.applyStep1(var1, var42, var25);
            } else {
                this.applyStep(var1, var42, var25);
            }
            return;
            int var87 = this.counterValue1;
            if (var25 >= var87) {
                this.applyStep(var1);
                AimAssist.clearSilentRotation();
                this.enabledState1 = false;
            }
            return;
        }
    }

    private void applyStep(Minecraft arg0, List arg1, int arg2) {
        Iterator var9 = arg1.iterator();
        while (true) {
            if (!var9.hasNext()) break;
            AutoTrapPlacement var17 = ((AutoTrapPlacement) var9.next());
            int var25 = var17.slot();
            this.setRightClickDelay1(arg0, var25);
            this.applyStep(arg0, var17);
        }
        this.setRightClickDelay(arg0, arg2);
        this.targetBlockPos = null;
        this.enabledState = false;
    }

    private void applyStep1(Minecraft arg0, List arg1, int arg2) {
        AutoTrapPlacement var8 = null;
        if (this.counterValue2 != -1) {
            if (this.isEligible(arg0)) {
                this.counterValue2 = -1;
                this.enabledState = false;
            }
            var8 = null;
            if (this.targetBlockPos != null) {
                Iterator var22 = arg1.iterator();
                while (true) {
                    if (!var22.hasNext()) break;
                    AutoTrapPlacement var27 = ((AutoTrapPlacement) var22.next());
                    BlockPos var29 = var27.pos();
                    BlockPos var89 = this.targetBlockPos;
                    if (!var29.equals(var89)) continue;
                }
                var8 = var27;
                if (var8 != null) {
                    if (this.counterValue >= 0) {
                        int var118 = this.counterValue;
                        if ((arg2 - var118) > 4) {
                            var8 = null;
                            this.targetBlockPos = null;
                            this.counterValue = -1;
                            this.enabledState = false;
                            if (!arg1.isEmpty()) {
                                Iterator var77 = arg1.iterator();
                                if (var77.hasNext()) {
                                    var8 = ((AutoTrapPlacement) var77.next());
                                }
                                if (var8 == null) {
                                    return;
                                }
                            }
                        }
                    }
                }
                this.enabledState = false;
                this.counterValue = -1;
            }
            if (var8 == null) {
                var8 = ((AutoTrapPlacement) arg1.get(0));
                this.enabledState = false;
                this.counterValue = -1;
            }
            BlockPos var96 = var8.pos();
            this.targetBlockPos = var96;
            Vec3 var44 = arg0.player.getEyePosition();
            Vec3 var99 = var8.hit().getLocation();
            float[] var46 = calculateRotationsToVec(var44, var99);
            float var48 = var46[0];
            float var102 = var46[1];
            AimAssist.applyStep(var48, var102);
            int var124 = var8.slot();
            this.setRightClickDelay1(arg0, var124);
            if (!this.enabledState) {
                Integer var126 = 0;
                int var55 = ((Integer) this.getSetting("Swap Delay_lo", var126));
                Integer var131 = 0;
                int var108 = ((Integer) this.getSetting("Swap Delay_hi", var131));
                int var58 = computeColorOrIndex(computeColorOrIndex(var55, var108));
                this.enabledState = true;
                if (var58 > 0) {
                    int var111 = (arg2 + var58);
                    this.counterValue1 = var111;
                    return;
                }
            }
            AutoTrapPlacement var129 = var8;
            this.applyStep(arg0, var129);
            this.counterValue = arg2;
            int var115 = (arg2 + 1);
            this.counterValue1 = var115;
            this.enabledState = false;
            return;
        }
    }

    private void setRightClickDelay(Minecraft arg0, int arg1) {
        if (this.counterValue2 == -1) {
            return;
        }
        Boolean var36 = true;
        if (!((Boolean) this.getSetting("Swap Back", var36))) {
            this.counterValue2 = -1;
            return;
        }
        Integer var38 = 50;
        int var17 = ((Integer) this.getSetting("Swap Back Delay_lo", var38));
        Integer var41 = 150;
        int var29 = ((Integer) this.getSetting("Swap Back Delay_hi", var41));
        int var31 = computeColorOrIndex(computeColorOrIndex(var17, var29));
        int var20 = Math.max(1, var31);
        int var33 = (arg1 + var20);
        this.counterValue1 = var33;
        this.enabledState1 = true;
    }

    private static boolean isEligible(Minecraft arg0, Player arg1) {
        Iterator var7 = evaluate(arg1).iterator();
        while (true) {
            if (!var7.hasNext()) break;
            BlockPos var13 = ((BlockPos) var7.next());
            if (!arg0.level.getBlockState(var13).canBeReplaced()) continue;
        }
        return false;
        return true;
    }

    private List evaluate(Minecraft arg0, Player arg1, boolean arg2, Set arg3, boolean arg4) {
        ArrayList var6 = new ArrayList();
        HashSet var18 = new HashSet();
        Iterator var21 = evaluate(arg1).iterator();
        while (true) {
            if (!var21.hasNext()) break;
            BlockPos var26 = ((BlockPos) var21.next());
            if (!arg0.level.getBlockState(var26).canBeReplaced()) {
            }
            int var33 = computeColorOrIndex(arg0, arg2, arg3);
            if (var33 == -1) {
            }
            BlockHitResult var36 = findBlockHitResult(arg0, var26, var18, arg4);
            if (var36 == null) {
            }
            AutoTrapPlacement var47 = new AutoTrapPlacement(var26, var36, var33);
            boolean var39 = var6.add(var47);
            boolean var41 = var18.add(var26);
        }
        return var6;
    }

    private static List evaluate(Player arg0) {
        double var6 = arg0.getX();
        double var12 = arg0.getY();
        double var50 = arg0.getZ();
        BlockPos var8 = BlockPos.containing(var6, var12, var50);
        BlockPos[] var10 = new BlockPos[11];
        BlockPos var25 = var8.below();
        var10[0] = var25;
        BlockPos var27 = var8.north();
        var10[1] = var27;
        BlockPos var29 = var8.south();
        var10[2] = var29;
        BlockPos var31 = var8.east();
        var10[3] = var31;
        BlockPos var33 = var8.west();
        var10[4] = var33;
        BlockPos var36 = var8.north().above();
        var10[5] = var36;
        BlockPos var39 = var8.south().above();
        var10[6] = var39;
        BlockPos var42 = var8.east().above();
        var10[7] = var42;
        BlockPos var45 = var8.west().above();
        var10[8] = var45;
        BlockPos var47 = var8.above(1);
        var10[9] = var47;
        BlockPos var49 = var8.above(2);
        var10[10] = var49;
        return List.of(var10);
    }

    private static BlockHitResult findBlockHitResult(Minecraft arg0, BlockPos arg1, Set arg2, boolean arg3) {
        int var18 = 0;
        double var28 = arg0.player.blockInteractionRange();
        double var91 = arg0.player.blockInteractionRange();
        double var30 = (var28 * var91);
        Vec3 var34 = arg0.player.getEyePosition();
        Vec3 var36 = Vec3.atCenterOf(arg1);
        Direction[] var37 = Direction.values();
        int var39 = var37.length;
        var18 = 0;
        while (true) {
            if (var18 < var39) {
                int var79 = var18;
                Direction var44 = var37[var79];
                BlockPos var46 = arg1.relative(var44);
                boolean var50 = arg0.level.getBlockState(var46).isSolid();
                boolean var52 = arg2.contains(var46);
                if (!var50) {
                    if (!var52) {
                    }
                }
            } else {
                return null;
                Direction var56 = var44.getOpposite();
                double var95 = ((double) var56.getStepX());
                double var107 = ((double) var56.getStepY());
                double var115 = ((double) var56.getStepZ());
                Vec3 var57 = new Vec3(var95, var107, var115);
                Vec3 var59 = Vec3.atCenterOf(var46);
                Vec3 var84 = var57.scale(0.5);
                Vec3 var60 = var59.add(var84);
                if (Double.compare(var34.distanceToSqr(var60), var30) > 0) {
                } else {
                    if (!arg3) {
                        if (var52) {
                        }
                    }
                    BlockHitResult var78 = new BlockHitResult(var60, var56, var46, false);
                    return var78;
                    ClientLevel var68 = arg0.level;
                    ClipContext.Block var9 = ClipContext.Block.OUTLINE;
                    ClipContext.Fluid var117 = ClipContext.Fluid.NONE;
                    LocalPlayer var118 = arg0.player;
                    ClipContext var86 = new ClipContext(var34, var36, var9, var117, var118);
                    BlockHitResult var69 = var68.clip(var86);
                    HitResult.Type var71 = var69.getType();
                    HitResult.Type var87 = HitResult.Type.BLOCK;
                    if (var71 == var87) {
                        if (var69.getBlockPos().equals(var46)) {
                            if (var69.getDirection() == var56) {
                                Vec3 var102 = var69.getLocation();
                                BlockHitResult var77 = new BlockHitResult(var102, var56, var46, false);
                                return var77;
                            }
                        }
                    }
                }
            }
            var18 = (var18 + 1);
        }
    }

    private static int computeColorOrIndex(Minecraft arg0, boolean arg1, Set arg2) {
        int var6 = 0;
        Inventory var10 = arg0.player.getInventory();
        var6 = 0;
        while (true) {
            if (var6 >= 9) break;
            int var31 = var6;
            ItemStack var15 = var10.getItem(var31);
            if (!var15.isEmpty()) {
                if (!(var15.getItem() instanceof BlockItem)) {
                } else {
                    if (arg1) {
                        if (arg2.isEmpty()) {
                        }
                    }
                    return var6;
                    DefaultedRegistry var24 = BuiltInRegistries.ITEM;
                    Item var33 = var15.getItem();
                    String var26 = var24.getKey(var33).getPath();
                    if (arg2.contains(var26)) {
                        return var6;
                    }
                }
                var6 = (var6 + 1);
            }
        }
        return -1;
    }

    private Set evaluate() {
        ArrayList var3 = new ArrayList();
        List var12 = ((List) this.getSetting("Whitelist Blocks", var3));
        HashSet var13 = new HashSet();
        Iterator var15 = var12.iterator();
        while (true) {
            if (!var15.hasNext()) break;
            Object var20 = var15.next();
            if ((var20 instanceof String)) {
                String var24 = ((String) var20);
                boolean var26 = var13.add(var24);
            }
            if (!(var20 instanceof Map)) continue;
            Object var32 = ((Map) var20).get("id");
            if (var32 == null) continue;
            String var39 = var32.toString();
            boolean var35 = var13.add(var39);
        }
        return var13;
    }

    private void applyStep(Minecraft arg0, AutoTrapPlacement arg1) {
        MultiPlayerGameMode var10 = arg0.gameMode;
        LocalPlayer var24 = arg0.player;
        InteractionHand var5 = InteractionHand.MAIN_HAND;
        BlockHitResult var32 = arg1.hit();
        if (!(var10.useItemOn(var24, var5, var32).consumesAction())) {
            return;
        }
        LocalPlayer var15 = arg0.player;
        InteractionHand var25 = InteractionHand.MAIN_HAND;
        var15.swing(var25);
        this.setRightClickDelay2(arg0, 4);
        Boolean var31 = false;
        if (((Boolean) this.getSetting("Render", var31))) {
            List var22 = this.entriesList;
            BlockPos var34 = arg1.pos();
            long var7 = System.currentTimeMillis();
            AutoTrapPlacedBlock var28 = new AutoTrapPlacedBlock(var34, var7);
            boolean var23 = var22.add(var28);
        }
    }

    private static Player evaluate(Minecraft arg0) {
        Player var5 = null;
        double var6 = 0.0;
        var5 = null;
        var6 = 1.7976931348623157E308;
        Iterator var17 = arg0.level.players().iterator();
        while (true) {
            if (!var17.hasNext()) break;
            Player var23 = ((Player) var17.next());
            LocalPlayer var36 = arg0.player;
            if (!(Targets.isEligible(var23, var36))) {
            }
            double var28 = arg0.player.distanceToSqr(var23);
            double var3 = var6;
            if (Double.compare(var28, var3) >= 0) continue;
            var6 = var28;
            var5 = var23;
        }
        return var5;
    }

    private boolean isEligible(Minecraft arg0) {
        int var13 = 0;
        if (this.counterValue3 != -1) {
            if (arg0.player == null) {
            }
        }
        return false;
        int var11 = InventoryHelper.getSelected(arg0.player.getInventory());
        int var16 = this.counterValue3;
        if (var11 != var16) {
            var13 = 1;
        } else {
            var13 = 0;
        }
        return var13;
    }

    private void setRightClickDelay1(Minecraft arg0, int arg1) {
        Boolean var29 = true;
        if (!((Boolean) this.getSetting("Swap", var29))) {
            return;
        }
        int var13 = InventoryHelper.getSelected(arg0.player.getInventory());
        if (var13 == arg1) {
            this.counterValue3 = arg1;
            return;
        }
        if (this.counterValue2 == -1) {
            this.counterValue2 = var13;
        }
        InventoryHelper.setSelected(arg0.player.getInventory(), arg1);
        this.counterValue3 = arg1;
    }

    private void applyStep(Minecraft arg0) {
        if (this.counterValue2 >= 0) {
            if (arg0.player != null) {
                Inventory var9 = arg0.player.getInventory();
                int var12 = this.counterValue2;
                InventoryHelper.setSelected(var9, var12);
            }
            this.counterValue2 = -1;
            this.counterValue3 = -1;
            return;
        }
    }

    private void applyStep2(Minecraft arg0) {
        AimAssist.clearSilentRotation();
        this.targetBlockPos = null;
        this.enabledState = false;
        this.counterValue1 = 0;
    }

    private void renderWorldEffect(PoseStack arg0, float arg1) {
        if (this.isEnabled()) {
            Boolean var88 = false;
            if (!((Boolean) this.getSetting("Render", var88))) {
            }
        }
        return;
        long var33 = System.currentTimeMillis();
        List var36 = this.entriesList;
        Predicate var74 = (p0) -> AutoTrap.lambda$a(var33, p0);
        boolean var37 = var36.removeIf(var74);
        if (this.entriesList.isEmpty()) {
            return;
        }
        int var44 = computeColorOrIndex(((String) this.getSetting("Render Color", "#3a7fcc")));
        Iterator var47 = this.entriesList.iterator();
        while (true) {
            if (!var47.hasNext()) break;
            AutoTrapPlacedBlock var52 = ((AutoTrapPlacedBlock) var47.next());
            long var91 = var52.placedAtMs();
            float var79 = (((float) (var33 - var91)) / 750.0F);
            float var58 = (1.0F - var79);
            if (Float.compare(var58, 0.0F) <= 0) {
            }
            int var66 = ((((int) (var58 * 90.0F)) & 255) << 24);
            int var85 = (var44 & 16777215);
            int var67 = (var66 | var85);
            BlockPos var69 = var52.pos();
            double var97 = ((double) var69.getX());
            double var101 = ((double) var69.getY());
            double var105 = ((double) var69.getZ());
            double var109 = ((double) (var69.getX() + 1));
            double var113 = ((double) (var69.getY() + 1));
            double var117 = ((double) (var69.getZ() + 1));
            AABB var70 = new AABB(var97, var101, var105, var109, var113, var117);
            List var87 = List.of(var70);
            ClarityRenderUtils.drawBoxes(arg0, var87, var67, 0, true, false);
        }
    }

    private static float[] calculateRotationsToVec(Vec3 arg0, Vec3 arg1) {
        Vec3 var12 = arg1.subtract(arg0);
        double var14 = var12.x;
        double var48 = var12.x;
        double var16 = (var14 * var48);
        double var51 = var12.z;
        double var75 = var12.z;
        double var53 = (var51 * var75);
        double var20 = Math.sqrt((var16 + var53));
        double var25 = (var12.x * -1.0);
        double var58 = var12.z;
        float var33 = ((float) ((Math.atan2(var25, var58) * 180.0) / 3.141592653589793));
        float var45 = ((float) (((Math.atan2(var12.y, var20) * -1.0) * 180.0) / 3.141592653589793));
        float[] var47 = new float[2];
        var47[0] = var33;
        var47[1] = var45;
        return var47;
    }

    private void setRightClickDelay2(Minecraft arg0, int arg1) {
        if (rightClickDelayField == null) {
            return;
        }
        try {
            rightClickDelayField.setInt(arg0, arg1);
        } catch (IllegalAccessException ignored) {
        }
    }

    private static int computeColorOrIndex(int arg0, int arg1) {
        if (arg0 >= arg1) {
            return arg0;
        }
        double var9 = Math.random();
        double var16 = ((double) ((arg1 - arg0) + 1));
        int var13 = ((int) (var9 * var16));
        return (arg0 + var13);
    }

    private static int computeColorOrIndex(int arg0) {
        int var6 = ((arg0 + 49) / 50);
        return Math.max(0, var6);
    }

    private static int computeColorOrIndex(String arg0) {
        return ThemeColors.resolveRgb(arg0, 3833804, false);
    }

    static {
        Field var2 = null;
        var2 = null;
        try {
            var2 = Minecraft.class.getDeclaredField("rightClickDelay");
            var2.setAccessible(true);
        } catch (NoSuchFieldException ignored) {
        }
        rightClickDelayField = var2;
    }

}
