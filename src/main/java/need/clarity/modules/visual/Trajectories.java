package need.clarity.modules.visual;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import need.clarity.WorldRenderEvent;
import need.clarity.modules.ModuleBase;
import need.clarity.util.ClarityRenderUtils;
import need.clarity.util.ThemeColors;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class Trajectories extends ModuleBase {
    public static Trajectories INSTANCE;
    private static final int CONST_INT = 300;
    private static final double CONST_DBL = 0.08;
    private final WorldRenderEvent.Listener fieldVal;
    private boolean enabledState;

    public Trajectories() {
        super("Trajectories", "Visual");
        WorldRenderEvent.Listener var11 = (p0, p1) -> this.renderWorldEffect(p0, p1);
        this.fieldVal = var11;
        this.enabledState = false;
        INSTANCE = this;
        if (this.isEnabled()) {
            this.applyStep();
        }
    }

    public void onEnable() {
        this.applyStep();
    }

    public void onDisable() {
        this.applyStep1();
    }

    private void applyStep() {
        if (this.enabledState) {
            return;
        }
        this.enabledState = true;
        WorldRenderEvent.register(this.fieldVal);
    }

    private void applyStep1() {
        if (!this.enabledState) {
            return;
        }
        this.enabledState = false;
        WorldRenderEvent.unregister(this.fieldVal);
    }

    private void renderWorldEffect(PoseStack arg0, float arg1) {
        int var81 = 0;
        int var55 = 0;
        if (!this.isEnabled()) {
            return;
        }
        Minecraft var35 = Minecraft.getInstance();
        if (var35 != null) {
            if (var35.level != null) {
                if (var35.player == null) {
                }
            }
            return;
            LocalPlayer var42 = var35.player;
            ProjectilePhysicsSpec var44 = this.evaluate(var42, arg1);
            if (var44 == null) {
                return;
            }
            List var47 = this.evaluate(var35, var44);
            if (var47.size() < 2) {
                return;
            }
            String var50 = getRender();
            if (!"Boxes".equals(var50)) {
                if ("Both".equals(var50)) {
                }
            }
            var55 = 1;
            var55 = 0;
            int var26 = var55;
            if (!"Line".equals(var50)) {
                if ("Both".equals(var50)) {
                }
            }
            var81 = 1;
            var81 = 0;
            int var27 = var81;
            float var60 = getBoxOpacity();
            float var61 = getLineOpacity();
            if (var26 != 0) {
                int var64 = computeColorOrIndex(getBoxColor(), var60);
                ArrayList var65 = new ArrayList();
                Iterator var67 = var47.iterator();
                while (true) {
                    if (!var67.hasNext()) break;
                    Vec3 var78 = ((Vec3) var67.next());
                    double var102 = (var78.x - 0.08);
                    double var109 = (var78.y - 0.08);
                    double var114 = (var78.z - 0.08);
                    double var119 = (var78.x + 0.08);
                    double var124 = (var78.y + 0.08);
                    double var129 = (var78.z + 0.08);
                    AABB var97 = new AABB(var102, var109, var114, var119, var124, var129);
                    ClarityRenderUtils.ESPEntry var92 = new ClarityRenderUtils.ESPEntry(var97, var64);
                    boolean var80 = var65.add(var92);
                }
                ClarityRenderUtils.drawEspBoxes(arg0, var65, false, var64, true, true, false, 1.0F);
            }
            if (var27 != 0) {
                int var73 = computeColorOrIndex(getLineColor(), var61);
                float var74 = getLineWidth();
                TrajectoryLineRenderer.renderWorldEffect(arg0, var47, var73, var74);
            }
            return;
        }
    }

    private List evaluate(Minecraft arg0, ProjectilePhysicsSpec arg1) {
        Vec3 var8 = null;
        Vec3 var9 = null;
        int var10 = 0;
        ArrayList var3 = new ArrayList();
        var8 = arg1.start;
        Vec3 var16 = arg1.dir;
        double var43 = arg1.speed;
        var9 = var16.scale(var43);
        Vec3 var45 = var8;
        boolean var19 = var3.add(var45);
        var10 = 0;
        while (true) {
            if (var10 >= 300) break;
            Vec3 var22 = var8;
            Vec3 var47 = var9;
            var8 = var22.add(var47);
            Vec3 var24 = var9;
            double var49 = arg1.drag;
            Vec3 var25 = var24.scale(var49);
            double var59 = -(arg1.gravity);
            var9 = var25.add(0.0, var59, 0.0);
            BlockPos var29 = BlockPos.containing(var8);
            if (!arg0.level.getBlockState(var29).isAir()) {
                Vec3 var54 = var8;
                boolean var36 = var3.add(var54);
            } else {
                if ((var10 % 2) == 0) {
                    Vec3 var56 = var8;
                    boolean var41 = var3.add(var56);
                }
                var10 = (var10 + 1);
            }
            return var3;
        }
    }

    private ProjectilePhysicsSpec evaluate(Player arg0, float arg1) {
        ItemStack var13 = null;
        InteractionHand var14 = null;
        ItemStack var22 = arg0.getMainHandItem();
        ItemStack var24 = arg0.getOffhandItem();
        var13 = null;
        var14 = InteractionHand.MAIN_HAND;
        if (this.isEligible(arg0, var22)) {
            var13 = var22;
        } else {
            if (this.isEligible(arg0, var24)) {
                var13 = var24;
                var14 = InteractionHand.OFF_HAND;
            } else {
                if (isEligible(var22)) {
                    var13 = var22;
                } else {
                    if (isEligible(var24)) {
                        var13 = var24;
                        var14 = InteractionHand.OFF_HAND;
                    }
                    if (var13 == null) {
                        return null;
                    }
                    Vec3 var34 = arg0.getViewVector(arg1).normalize();
                    double var37 = ((double) arg0.getEyeHeight());
                    double var77 = arg0.xOld;
                    double var93 = arg0.getX();
                    double var116 = arg0.xOld;
                    double var95 = (var93 - var116);
                    double var119 = ((double) arg1);
                    double var97 = (var95 * var119);
                    double var79 = (var77 + var97);
                    double var100 = arg0.yOld;
                    double var122 = arg0.getY();
                    double var145 = arg0.yOld;
                    double var124 = (var122 - var145);
                    double var148 = ((double) arg1);
                    double var126 = (var124 * var148);
                    double var106 = (((var100 + var126) + var37) - 0.1);
                    double var133 = arg0.zOld;
                    double var151 = arg0.getZ();
                    double var165 = arg0.zOld;
                    double var153 = (var151 - var165);
                    double var168 = ((double) arg1);
                    double var155 = (var153 * var168);
                    double var135 = (var133 + var155);
                    Vec3 var39 = new Vec3(var79, var106, var135);
                    ItemStack var40 = var13;
                    Item var71 = Items.BOW;
                    if (var40.is(var71)) {
                        InteractionHand var74 = var14;
                        double var56 = (Math.min(((double) computeFloatMetric(arg0, var74)), 1.0) * 3.0);
                        ProjectilePhysicsSpec var58 = new ProjectilePhysicsSpec(var39, var34, var56, 0.05, 0.99);
                        return var58;
                    }
                    ItemStack var42 = var13;
                    Item var72 = Items.CROSSBOW;
                    if (var42.is(var72)) {
                        ProjectilePhysicsSpec var44 = new ProjectilePhysicsSpec(var39, var34, 3.15, 0.05, 0.99);
                        return var44;
                    }
                    ItemStack var45 = var13;
                    Item var73 = Items.TRIDENT;
                    if (var45.is(var73)) {
                        ProjectilePhysicsSpec var47 = new ProjectilePhysicsSpec(var39, var34, 2.5, 0.05, 0.99);
                        return var47;
                    }
                    ProjectilePhysicsSpec var48 = new ProjectilePhysicsSpec(var39, var34, 1.5, 0.03, 0.99);
                    return var48;
                }
            }
        }
    }

    private boolean isEligible(Player arg0, ItemStack arg1) {
        if (arg1.isEmpty()) {
            return false;
        }
        Item var4 = Items.BOW;
        if (arg1.is(var4)) {
            return arg0.isUsingItem();
        }
        Item var17 = Items.CROSSBOW;
        if (!arg1.is(var17)) {
            Item var18 = Items.TRIDENT;
            if (arg1.is(var18)) {
            }
        }
        return true;
        return false;
    }

    private static boolean isEligible(ItemStack arg0) {
        int var19 = 0;
        if (arg0.isEmpty()) {
            return false;
        }
        Item var2 = Items.SNOWBALL;
        if (!arg0.is(var2)) {
            Item var20 = Items.EGG;
            if (!arg0.is(var20)) {
                Item var21 = Items.ENDER_PEARL;
                if (!arg0.is(var21)) {
                    Item var22 = Items.SPLASH_POTION;
                    if (!arg0.is(var22)) {
                        Item var23 = Items.LINGERING_POTION;
                        if (!arg0.is(var23)) {
                            Item var24 = Items.EXPERIENCE_BOTTLE;
                            if (!arg0.is(var24)) {
                                Item var25 = Items.WIND_CHARGE;
                                if (arg0.is(var25)) {
                                }
                            }
                            var19 = 1;
                            var19 = 0;
                            return var19;
                        }
                    }
                }
            }
        }
    }

    private static float computeFloatMetric(Player arg0, InteractionHand arg1) {
        if (arg0.isUsingItem()) {
            if (arg0.getUsedItemHand() != arg1) {
            }
        }
        return 0.15F;
        float var14 = (((float) arg0.getTicksUsingItem()) / 20.0F);
        float var16 = (var14 * var14);
        float var25 = (var14 * 2.0F);
        return Math.min(((var16 + var25) / 3.0F), 1.0F);
    }

    public static String getRender() {
        return ((String) INSTANCE.getSetting("Render", "Both"));
    }

    public static int getBoxColor() {
        return ThemeColors.resolveArgb(INSTANCE.getSetting("Box Color", ""), -16733441, true);
    }

    public static float getBoxOpacity() {
        Trajectories var0 = INSTANCE;
        Float var6 = 1.0F;
        return ((Float) var0.getSetting("Box Opacity", var6));
    }

    public static int getLineColor() {
        return ThemeColors.resolveArgb(INSTANCE.getSetting("Line Color", ""), -16733441, false);
    }

    public static float getLineOpacity() {
        Trajectories var0 = INSTANCE;
        Float var6 = 1.0F;
        return ((Float) var0.getSetting("Line Opacity", var6));
    }

    public static float getLineWidth() {
        Trajectories var0 = INSTANCE;
        Float var6 = 2.0F;
        return ((Float) var0.getSetting("Line Width", var6));
    }

    private static int computeColorOrIndex(int arg0, float arg1) {
        int var10 = (Math.round((computeFloatMetric(arg1) * 255.0F)) << 24);
        int var14 = (arg0 & 16777215);
        return (var10 | var14);
    }

    private static float computeFloatMetric(float arg0) {
        float var6 = 0.0F;
        if (Float.compare(arg0, 0.0F) < 0) {
            var6 = 0.0F;
        } else {
            if (Float.compare(arg0, 1.0F) > 0) {
                var6 = 1.0F;
            } else {
                var6 = arg0;
            }
            return var6;
        }
    }

}
