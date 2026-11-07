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
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class TNTTimer extends ModuleBase {
    public static TNTTimer INSTANCE;
    private final WorldRenderEvent.Listener fieldVal;
    private boolean enabledState;

    public TNTTimer() {
        super("TNT Timer", "Visual");
        WorldRenderEvent.Listener var11 = (p0, p1) -> this.renderWorldEffect(p0, p1);
        this.fieldVal = var11;
        this.enabledState = false;
        INSTANCE = this;
        if (this.isEnabled()) {
            this.applyStep();
        }
    }

    public static boolean isEligible() {
        int var3 = 0;
        if (INSTANCE != null) {
            if (INSTANCE.isEnabled()) {
                var3 = 1;
            } else {
            }
        }
        var3 = 0;
        return var3;
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
        if (!this.isEnabled()) {
            return;
        }
        Minecraft var28 = Minecraft.getInstance();
        if (var28 != null) {
            if (var28.level == null) {
            }
        }
        return;
        double var32 = getMaxDistance();
        boolean var34 = isThroughWalls();
        int var35 = getSafeColor();
        int var36 = getDangerColor();
        Vec3 var37 = ClarityRenderUtils.getCameraPos();
        ArrayList var38 = new ArrayList();
        Iterator var42 = var28.level.entitiesForRendering().iterator();
        while (true) {
            if (!var42.hasNext()) break;
            Entity var47 = ((Entity) var42.next());
            if (!(var47 instanceof PrimedTnt)) continue;
            PrimedTnt var51 = ((PrimedTnt) var47);
            if (Double.compare(var32, 0.5) > 0) {
                Vec3 var78 = var51.position();
                if (Double.compare(var37.distanceTo(var78), var32) > 0) {
                }
            }
            float var64 = computeFloatMetric((((float) var51.getFuse()) / 80.0F));
            int var82 = (computeColorOrIndex(var36, var35, var64) & 16777215);
            int var68 = (-16777216 | var82);
            AABB var71 = var51.getBoundingBox().inflate(0.1);
            ClarityRenderUtils.ESPEntry var85 = new ClarityRenderUtils.ESPEntry(var71, var68);
            boolean var73 = var38.add(var85);
        }
        if (var38.isEmpty()) {
            return;
        }
        float var10 = getOutlineThickness();
        ClarityRenderUtils.drawEspBoxes(arg0, var38, false, 0, true, var34, false, var10);
    }

    public static double getMaxDistance() {
        TNTTimer var0 = INSTANCE;
        Float var8 = 0.0F;
        return ((double) ((Float) var0.getSetting("Max Distance", var8)));
    }

    public static boolean isThroughWalls() {
        TNTTimer var0 = INSTANCE;
        Boolean var6 = true;
        return ((Boolean) var0.getSetting("Through Walls", var6));
    }

    public static float getOutlineThickness() {
        TNTTimer var0 = INSTANCE;
        Float var6 = 2.0F;
        return ((Float) var0.getSetting("Outline Thickness", var6));
    }

    public static int getSafeColor() {
        return ThemeColors.resolveArgb(INSTANCE.getSetting("Safe Color", ""), -12845218, false);
    }

    public static int getDangerColor() {
        return ThemeColors.resolveArgb(INSTANCE.getSetting("Danger Color", ""), -50373, true);
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

    private static int computeColorOrIndex(int arg0, int arg1, float arg2) {
        int var14 = ((arg0 >> 16) & 255);
        int var17 = ((arg0 >> 8) & 255);
        int var19 = (arg0 & 255);
        int var22 = ((arg1 >> 16) & 255);
        int var25 = ((arg1 >> 8) & 255);
        int var27 = (arg1 & 255);
        float var29 = ((float) var14);
        float var47 = (((float) (var22 - var14)) * arg2);
        int var32 = (Math.round((var29 + var47)) << 16);
        float var50 = ((float) var17);
        float var62 = (((float) (var25 - var17)) * arg2);
        int var53 = (Math.round((var50 + var62)) << 8);
        int var33 = (var32 | var53);
        float var55 = ((float) var19);
        float var67 = (((float) (var27 - var19)) * arg2);
        int var57 = Math.round((var55 + var67));
        return (var33 | var57);
    }

}
