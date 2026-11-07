package need.clarity.modules.visual;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.ArrayList;
import java.util.Iterator;
import need.clarity.WorldRenderEvent;
import need.clarity.modules.ModuleBase;
import need.clarity.util.ClarityRenderUtils;
import need.clarity.util.ThemeColors;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class ItemESP extends ModuleBase {
    public static ItemESP INSTANCE;
    private static final double CONST_DBL = 4096.0;
    private final WorldRenderEvent.Listener fieldVal;
    private boolean enabledState;

    public ItemESP() {
        super("Item ESP", "Visual");
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

    public static boolean isStyle() {
        int var10 = 0;
        ItemESP var0 = INSTANCE;
        if (var0 != null) {
            if (var0.isEnabled()) {
                Object var11 = var0.getSetting("Style", "Box");
                if ("Shader Glow".equals(var11)) {
                    var10 = 1;
                } else {
                    var10 = 0;
                }
                return var10;
            }
        }
    }

    public static int getGlowColor() {
        int var7 = 0;
        ItemESP var0 = INSTANCE;
        if (var0 == null) {
            var7 = 16765503;
        } else {
            var7 = ThemeColors.resolveRgb(var0.getSetting("Glow Color", "#FFD23F"), 16765503, false);
        }
        return var7;
    }

    public static float getGlowRadius() {
        float var7 = 0.0F;
        ItemESP var0 = INSTANCE;
        if (var0 == null) {
            var7 = 10.0F;
        } else {
            Float var13 = 10.0F;
            float var12 = ((Float) var0.getSetting("Glow Radius", var13));
            float var9 = Math.min(40.0F, var12);
            var7 = Math.max(2.0F, var9);
        }
        return var7;
    }

    public static boolean isEligible(Entity arg0) {
        int var20 = 0;
        if (INSTANCE == null) {
            return false;
        }
        double var10 = ((double) getMaxDistance());
        if (Double.compare(var10, 0.5) > 0) {
            Vec3 var15 = ClarityRenderUtils.getCameraPos();
            Vec3 var21 = arg0.position();
            if (Double.compare(var15.distanceTo(var21), var10) <= 0) {
            }
        }
        var20 = 1;
        var20 = 0;
        return var20;
    }

    private void renderWorldEffect(PoseStack arg0, float arg1) {
        int var25 = 0;
        float var31 = 0.0F;
        if (!this.isEnabled()) {
            return;
        }
        if (isStyle()) {
            return;
        }
        Minecraft var35 = Minecraft.getInstance();
        if (var35 != null) {
            if (var35.level != null) {
                if (var35.player == null) {
                }
            }
            return;
            boolean var41 = isThroughWalls();
            boolean var42 = isFill();
            boolean var43 = isOutline();
            double var45 = ((double) getMaxDistance());
            float var47 = getOutlineThickness();
            int var48 = computeColorOrIndex();
            float var4 = getFillOpacity();
            int var49 = computeColorOrIndex2(var48, var4);
            int var50 = computeColorOrIndex1();
            float var105 = getOutlineOpacity();
            int var51 = computeColorOrIndex2(var50, var105);
            Vec3 var52 = ClarityRenderUtils.getCameraPos();
            ArrayList var53 = new ArrayList();
            var25 = 0;
            Iterator var58 = var35.level.entitiesForRendering().iterator();
            while (true) {
                if (!var58.hasNext()) break;
                Entity var63 = ((Entity) var58.next());
                if (!(var63 instanceof ItemEntity)) {
                }
                if (Double.compare(((double) var25), 4096.0) >= 0) {
                } else {
                    double var120 = var63.xOld;
                    double var144 = var63.getX();
                    double var169 = var63.xOld;
                    double var146 = (var144 - var169);
                    double var172 = ((double) arg1);
                    double var148 = (var146 * var172);
                    double var122 = (var120 + var148);
                    double var151 = var63.yOld;
                    double var175 = var63.getY();
                    double var189 = var63.yOld;
                    double var177 = (var175 - var189);
                    double var192 = ((double) arg1);
                    double var179 = (var177 * var192);
                    double var153 = (var151 + var179);
                    double var182 = var63.zOld;
                    double var195 = var63.getZ();
                    double var201 = var63.zOld;
                    double var197 = (var195 - var201);
                    double var204 = ((double) arg1);
                    double var199 = (var197 * var204);
                    double var184 = (var182 + var199);
                    Vec3 var73 = new Vec3(var122, var153, var184);
                    double var75 = var52.distanceTo(var73);
                    if (Double.compare(var45, 0.5) > 0) {
                        if (Double.compare(var75, var45) > 0) {
                        }
                    }
                    var31 = 1.0F;
                    if (isDistanceFade()) {
                        if (Double.compare(var45, 0.5) > 0) {
                            double var132 = (var75 / var45);
                            var31 = ((float) (1.0 - var132));
                            if (Float.compare(var31, 0.0F) < 0) {
                                var31 = 0.0F;
                            }
                            if (Float.compare(var31, 1.0F) > 0) {
                                var31 = 1.0F;
                            }
                            AABB var100 = var63.getBoundingBox();
                            double var111 = var73.x;
                            double var135 = var63.getX();
                            double var113 = (var111 - var135);
                            double var138 = var73.y;
                            double var160 = var63.getY();
                            double var140 = (var138 - var160);
                            double var163 = var73.z;
                            double var187 = var63.getZ();
                            double var165 = (var163 - var187);
                            AABB var102 = var100.move(var113, var140, var165).inflate(0.05);
                            float var167 = var31;
                            int var158 = computeColorOrIndex(var51, var167);
                            ClarityRenderUtils.ESPEntry var117 = new ClarityRenderUtils.ESPEntry(var102, var158);
                            boolean var104 = var53.add(var117);
                            var25 = (var25 + 1);
                        }
                    }
                }
                if (!var53.isEmpty()) break;
                return;
                ClarityRenderUtils.drawEspBoxes(arg0, var53, var42, var49, var43, var41, false, var47);
                return;
            }
        }
    }

    public static boolean isThroughWalls() {
        ItemESP var0 = INSTANCE;
        Boolean var6 = true;
        return ((Boolean) var0.getSetting("Through Walls", var6));
    }

    public static boolean isFill() {
        ItemESP var0 = INSTANCE;
        Boolean var6 = false;
        return ((Boolean) var0.getSetting("Fill", var6));
    }

    public static int computeColorOrIndex() {
        return computeColorOrIndex("Fill Color", -16733441, true);
    }

    public static float getFillOpacity() {
        ItemESP var0 = INSTANCE;
        Float var6 = 0.25F;
        return ((Float) var0.getSetting("Fill Opacity", var6));
    }

    public static boolean isOutline() {
        ItemESP var0 = INSTANCE;
        Boolean var6 = true;
        return ((Boolean) var0.getSetting("Outline", var6));
    }

    public static int computeColorOrIndex1() {
        return computeColorOrIndex("Outline Color", -10934, false);
    }

    public static float getOutlineOpacity() {
        ItemESP var0 = INSTANCE;
        Float var6 = 0.85F;
        return ((Float) var0.getSetting("Outline Opacity", var6));
    }

    public static float getOutlineThickness() {
        ItemESP var0 = INSTANCE;
        Float var6 = 2.0F;
        return ((Float) var0.getSetting("Outline Thickness", var6));
    }

    public static float getMaxDistance() {
        ItemESP var0 = INSTANCE;
        Float var6 = 0.0F;
        return ((Float) var0.getSetting("Max Distance", var6));
    }

    public static boolean isDistanceFade() {
        ItemESP var0 = INSTANCE;
        Boolean var6 = false;
        return ((Boolean) var0.getSetting("Distance Fade", var6));
    }

    private static int computeColorOrIndex(int arg0, float arg1) {
        int var5 = 0;
        var5 = Math.round((((float) ((arg0 >>> 24) & 255)) * arg1));
        if (var5 < 0) {
            var5 = 0;
        } else {
            if (var5 > 255) {
                var5 = 255;
            }
            int var15 = (var5 << 24);
            int var23 = (arg0 & 16777215);
            return (var15 | var23);
        }
    }

    private static int computeColorOrIndex(String arg0, int arg1, boolean arg2) {
        return ThemeColors.resolveArgb(INSTANCE.getSetting(arg0, ""), arg1, arg2);
    }

    private static int computeColorOrIndex2(int arg0, float arg1) {
        int var10 = (Math.round((computeFloatMetric(arg1) * 255.0F)) << 24);
        int var14 = (arg0 & 16777215);
        return (var10 | var14);
    }

    private static float computeFloatMetric(float arg0) {
        float var4 = 0.0F;
        if (Float.compare(arg0, 0.0F) < 0) {
            var4 = 0.0F;
        } else {
            if (Float.compare(arg0, 1.0F) > 0) {
                var4 = 1.0F;
            } else {
                var4 = arg0;
            }
            return var4;
        }
    }

}
