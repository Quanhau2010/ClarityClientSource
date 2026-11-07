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
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BarrelBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BrewingStandBlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.DispenserBlockEntity;
import net.minecraft.world.level.block.entity.DropperBlockEntity;
import net.minecraft.world.level.block.entity.EnderChestBlockEntity;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
import net.minecraft.world.level.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class StorageESP extends ModuleBase {
    public static StorageESP INSTANCE;
    private static final int CONST_INT = 20;
    private final WorldRenderEvent.Listener fieldVal;
    private boolean enabledState;
    private int counterValue;
    private List entriesList;
    private final List entriesList1;
    private int counterValue1;

    public StorageESP() {
        super("Storage ESP", "Visual");
        WorldRenderEvent.Listener var15 = (p0, p1) -> this.renderWorldEffect(p0, p1);
        this.fieldVal = var15;
        this.enabledState = false;
        this.counterValue = 0;
        List var18 = List.of();
        this.entriesList = var18;
        ArrayList var19 = new ArrayList();
        this.entriesList1 = var19;
        this.counterValue1 = -1;
        INSTANCE = this;
        if (this.isEnabled()) {
            this.applyStep();
        }
    }

    public void onEnable() {
        this.applyStep();
        this.counterValue = 0;
        List var7 = List.of();
        this.entriesList = var7;
        this.entriesList1.clear();
    }

    public void onDisable() {
        this.applyStep1();
        List var2 = List.of();
        this.entriesList = var2;
        this.entriesList1.clear();
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

    public void onTick() {
        Minecraft var1 = Minecraft.getInstance();
        if (var1 != null) {
            if (var1.level != null) {
                if (var1.player == null) {
                }
            }
            return;
            int var13 = (this.counterValue + 1);
            this.counterValue = var13;
            if (var13 < 20) {
                return;
            }
            this.counterValue = 0;
            this.applyStep(var1);
            return;
        }
    }

    private void applyStep(Minecraft arg0) {
        int var270 = 0;
        int var36 = 0;
        int var37 = 0;
        int var38 = 0;
        int var39 = 0;
        double var2 = getMaxDistance();
        float var49 = getFillOpacity();
        float var50 = getOutlineOpacity();
        int var52 = computeColorOrIndex("Chest Color", -19712, false);
        int var54 = computeColorOrIndex("Shulker Color", -5157889, true);
        int var56 = computeColorOrIndex("Furnace Color", -36797, true);
        int var58 = computeColorOrIndex("Other Color", -11549705, true);
        Boolean var196 = true;
        boolean var62 = ((Boolean) this.getSetting("Chests", var196));
        Boolean var198 = true;
        boolean var66 = ((Boolean) this.getSetting("Shulkers", var198));
        Boolean var200 = true;
        boolean var70 = ((Boolean) this.getSetting("Furnaces", var200));
        Boolean var202 = true;
        boolean var74 = ((Boolean) this.getSetting("Other", var202));
        Vec3 var75 = ClarityRenderUtils.getCameraPos();
        int var82 = Math.min(((Integer) arg0.options.renderDistance().get()), 24);
        ChunkPos var85 = arg0.player.chunkPosition();
        ArrayList var86 = new ArrayList();
        ArrayList var87 = new ArrayList();
        var36 = 0;
        var37 = 0;
        var38 = -(var82);
        while (true) {
            if (var38 > var82) break;
            var39 = -(var82);
            while (true) {
                if (var39 <= var82) {
                    int var97 = var85.x();
                    int var180 = var38;
                    int var98 = (var97 + var180);
                    int var100 = var85.z();
                    int var181 = var39;
                    int var101 = (var100 + var181);
                    if (!(arg0.level.hasChunk(var98, var101))) {
                    }
                } else {
                    var38 = (var38 + 1);
                }
            }
        }
        Iterator var111 = arg0.level.getChunk(var98, var101).getBlockEntities().values().iterator();
        while (true) {
            if (!var111.hasNext()) break;
            BlockEntity var116 = ((BlockEntity) var111.next());
            if (!(var116 instanceof ChestBlockEntity)) {
                if (!(var116 instanceof BarrelBlockEntity)) {
                    if ((var116 instanceof EnderChestBlockEntity)) {
                    }
                }
                if (!var62) {
                }
                var270 = var52;
                if ((var116 instanceof ShulkerBoxBlockEntity)) {
                    if (!var66) {
                    }
                    var270 = var54;
                } else {
                    if ((var116 instanceof AbstractFurnaceBlockEntity)) {
                        if (!var70) {
                        }
                        var270 = var56;
                    } else {
                        if (!(var116 instanceof HopperBlockEntity)) {
                            if (!(var116 instanceof DispenserBlockEntity)) {
                                if (!(var116 instanceof DropperBlockEntity)) {
                                    if (!(var116 instanceof BrewingStandBlockEntity)) continue;
                                }
                                if (!var74) {
                                }
                                var270 = var58;
                            }
                        }
                    }
                    BlockPos var138 = var116.getBlockPos();
                    double var209 = (((double) var138.getX()) + 0.5);
                    double var225 = (((double) var138.getY()) + 0.5);
                    double var240 = (((double) var138.getZ()) + 0.5);
                    Vec3 var139 = new Vec3(var209, var225, var240);
                    if (Double.compare(var2, 0.5) > 0) {
                        if (Double.compare(var75.distanceTo(var139), var2) > 0) {
                        }
                    }
                    double var219 = (((double) var138.getX()) + 0.05);
                    double var233 = (((double) var138.getY()) + 0.05);
                    double var248 = (((double) var138.getZ()) + 0.05);
                    double var256 = (((double) var138.getX()) + 0.95);
                    double var262 = (((double) var138.getY()) + 0.95);
                    double var268 = (((double) var138.getZ()) + 0.95);
                    AABB var147 = new AABB(var219, var233, var248, var256, var262, var268);
                    int var149 = computeColorOrIndex(var270, var50);
                    ClarityRenderUtils.ESPEntry var186 = new ClarityRenderUtils.ESPEntry(var147, var149);
                    boolean var151 = var86.add(var186);
                    if (!isTracers()) continue;
                    var36 = computeColorOrIndex(var270, 1.0F);
                    boolean var156 = var87.add(var139);
                    var37 = (var37 + 1);
                }
            }
        }
        var39 = (var39 + 1);
        this.entriesList = var86;
        this.entriesList1.clear();
        boolean var168 = this.entriesList1.addAll(var87);
        int var191 = var36;
        this.counterValue1 = var191;
    }

    private void renderWorldEffect(PoseStack arg0, float arg1) {
        if (!this.isEnabled()) {
            return;
        }
        if (this.entriesList.isEmpty()) {
            if (this.entriesList1.isEmpty()) {
                return;
            }
        }
        boolean var23 = isThroughWalls();
        boolean var24 = isFill();
        boolean var25 = isOutline();
        float var26 = getOutlineThickness();
        int var28 = computeColorOrIndex("Fill Color", -1, false);
        float var39 = getFillOpacity();
        int var29 = computeColorOrIndex(var28, var39);
        if (!this.entriesList.isEmpty()) {
            List var41 = this.entriesList;
            ClarityRenderUtils.drawEspBoxes(arg0, var41, var24, var29, var25, var23, false, var26);
        }
        if (isTracers()) {
            if (!this.entriesList1.isEmpty()) {
                List var45 = this.entriesList1;
                int var47 = this.counterValue1;
                ClarityRenderUtils.drawTracers(arg0, arg1, var45, var47, 2.0F);
            }
            return;
        }
    }

    public static double getMaxDistance() {
        StorageESP var0 = INSTANCE;
        Float var8 = 0.0F;
        return ((double) ((Float) var0.getSetting("Max Distance", var8)));
    }

    public static boolean isThroughWalls() {
        StorageESP var0 = INSTANCE;
        Boolean var6 = true;
        return ((Boolean) var0.getSetting("Through Walls", var6));
    }

    public static boolean isFill() {
        StorageESP var0 = INSTANCE;
        Boolean var6 = false;
        return ((Boolean) var0.getSetting("Fill", var6));
    }

    public static float getFillOpacity() {
        StorageESP var0 = INSTANCE;
        Float var6 = 0.15F;
        return ((Float) var0.getSetting("Fill Opacity", var6));
    }

    public static boolean isOutline() {
        StorageESP var0 = INSTANCE;
        Boolean var6 = true;
        return ((Boolean) var0.getSetting("Outline", var6));
    }

    public static float getOutlineOpacity() {
        StorageESP var0 = INSTANCE;
        Float var6 = 0.85F;
        return ((Float) var0.getSetting("Outline Opacity", var6));
    }

    public static float getOutlineThickness() {
        StorageESP var0 = INSTANCE;
        Float var6 = 2.0F;
        return ((Float) var0.getSetting("Outline Thickness", var6));
    }

    public static boolean isTracers() {
        StorageESP var0 = INSTANCE;
        Boolean var6 = false;
        return ((Boolean) var0.getSetting("Tracers", var6));
    }

    private static int computeColorOrIndex(String arg0, int arg1, boolean arg2) {
        return ThemeColors.resolveArgb(INSTANCE.getSetting(arg0, ""), arg1, arg2);
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
