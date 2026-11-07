package need.clarity.modules.visual;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import need.clarity.WorldRenderEvent;
import need.clarity.config.ClarityConfig;
import need.clarity.modules.ModuleBase;
import need.clarity.util.ClarityRenderUtils;
import need.clarity.util.ThemeColors;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class Tracers extends ModuleBase {
    private static Tracers INSTANCE;
    private final WorldRenderEvent.Listener fieldVal;
    public static final String labelText = "Arrows";
    private static final String labelText1 = "Crosshair Arrows";

    public Tracers() {
        super("Tracers", "Visual");
        WorldRenderEvent.Listener var7 = (p0, p1) -> this.renderWorldEffect(p0, p1);
        this.fieldVal = var7;
        INSTANCE = this;
    }

    public void onEnable() {
        WorldRenderEvent.register(this.fieldVal);
    }

    public void onDisable() {
        WorldRenderEvent.unregister(this.fieldVal);
    }

    public static boolean isStyle() {
        int var16 = 0;
        Tracers var0 = INSTANCE;
        if (var0 != null) {
            if (!var0.isEnabled()) {
            }
        }
        return false;
        String var10 = ((String) var0.getSetting("Style", "Lines"));
        if (!"Arrows".equals(var10)) {
            if ("Crosshair Arrows".equals(var10)) {
            }
        }
        var16 = 1;
        var16 = 0;
        return var16;
    }

    public String resolveLabel() {
        int var5 = 0;
        String var15 = null;
        String var7 = ((String) this.getSetting("Arrow Image", "Smooth"));
        var5 = -1;
        switch (var7.hashCode()) {
            case -1765963719 -> {}
            case -1765963718 -> {}
            default -> {}
        }
        if (var7.equals("Triangle 1")) {
            var5 = 0;
            if (var7.equals("Triangle 2")) {
                var5 = 1;
            }
            switch (var5) {
                case 0 -> {}
                case 1 -> {}
                default -> {}
            }
            var15 = "arrow_triangle2";
            var15 = "arrow_triangle1";
            var15 = "arrow_smooth";
            return var15;
        }
    }

    public float computeFloatMetric() {
        int var5 = 0;
        float var15 = 0.0F;
        String var7 = ((String) this.getSetting("Arrow Image", "Smooth"));
        var5 = -1;
        switch (var7.hashCode()) {
            case -1765963719 -> {}
            case -1765963718 -> {}
            default -> {}
        }
        if (var7.equals("Triangle 1")) {
            var5 = 0;
            if (var7.equals("Triangle 2")) {
                var5 = 1;
            }
            switch (var5) {
                case 0 -> {}
                case 1 -> {}
                default -> {}
            }
            var15 = 0.052F;
            var15 = 0.071F;
            var15 = 0.098F;
            return var15;
        }
    }

    public float getArrowDistance() {
        Float var7 = 60.0F;
        return ((Float) this.getSetting("Arrow Distance", var7));
    }

    public float getArrowScale() {
        Float var7 = 22.0F;
        return ((Float) this.getSetting("Arrow Scale", var7));
    }

    private TracerTargetGroup evaluate(float arg0) {
        Minecraft var2 = Minecraft.getInstance();
        Integer var95 = 200;
        int var30 = ((Integer) this.getSetting("Alpha", var95));
        int var34 = computeColorOrIndex(((String) this.getSetting("Player Colour", "#ff4444")), var30, false);
        int var38 = computeColorOrIndex(((String) this.getSetting("Mob Colour", "#44ff44")), var30, true);
        int var42 = computeColorOrIndex(((String) this.getSetting("Tracer Colour", "#ffb52e")), var30, false);
        boolean var44 = this.isEligible("Players");
        boolean var46 = this.isEligible("Mobs");
        boolean var48 = this.isEligible("Entities");
        ArrayList var49 = new ArrayList();
        ArrayList var50 = new ArrayList();
        ArrayList var51 = new ArrayList();
        Iterator var55 = var2.level.entitiesForRendering().iterator();
        while (true) {
            if (!var55.hasNext()) break;
            Entity var60 = ((Entity) var55.next());
            LocalPlayer var91 = var2.player;
            if (var60 == var91) continue;
            if (!var60.isAlive()) {
            }
            double var103 = var60.xOld;
            double var108 = var60.getX();
            double var122 = var60.xOld;
            double var110 = (var108 - var122);
            double var125 = ((double) arg0);
            double var112 = (var110 * var125);
            double var105 = (var103 + var112);
            double var115 = var60.yOld;
            double var128 = var60.getY();
            double var147 = var60.yOld;
            double var130 = (var128 - var147);
            double var150 = ((double) arg0);
            double var132 = (var130 * var150);
            double var117 = (var115 + var132);
            double var138 = (((double) var60.getBbHeight()) * 0.5);
            double var119 = (var117 + var138);
            double var141 = var60.zOld;
            double var155 = var60.getZ();
            double var163 = var60.zOld;
            double var157 = (var155 - var163);
            double var166 = ((double) arg0);
            double var159 = (var157 * var166);
            double var143 = (var141 + var159);
            Vec3 var64 = new Vec3(var105, var119, var143);
            if (var44) {
                if ((var60 instanceof Player)) {
                    boolean var69 = var49.add(var64);
                }
            }
            if (var46) {
                if ((var60 instanceof Mob)) {
                    boolean var74 = var50.add(var64);
                }
            }
            if (!var48) continue;
            if ((var60 instanceof LivingEntity)) continue;
            boolean var79 = var51.add(var64);
        }
        int var162 = (var30 / 2);
        int var146 = computeColorOrIndex(var42, var162);
        TracerTargetGroup var80 = new TracerTargetGroup(var49, var50, var51, var34, var38, var146);
        return var80;
    }

    public List evaluate(float arg0) {
        Minecraft var2 = Minecraft.getInstance();
        ArrayList var10 = new ArrayList();
        if (var2.level != null) {
            if (var2.player == null) {
            }
        }
        return var10;
        TracerTargetGroup var16 = this.evaluate(arg0);
        Iterator var19 = var16.players().iterator();
        while (true) {
            if (!var19.hasNext()) break;
            Vec3 var24 = ((Vec3) var19.next());
            int var61 = var16.playerArgb();
            TracerArrowEntry var52 = new TracerArrowEntry(var24, var61);
            boolean var26 = var10.add(var52);
        }
        Iterator var29 = var16.mobs().iterator();
        while (true) {
            if (!var29.hasNext()) break;
            Vec3 var47 = ((Vec3) var29.next());
            int var65 = var16.mobArgb();
            TracerArrowEntry var58 = new TracerArrowEntry(var47, var65);
            boolean var49 = var10.add(var58);
        }
        Iterator var34 = var16.entities().iterator();
        while (true) {
            if (!var34.hasNext()) break;
            Vec3 var39 = ((Vec3) var34.next());
            int var63 = var16.entityArgb();
            TracerArrowEntry var53 = new TracerArrowEntry(var39, var63);
            boolean var41 = var10.add(var53);
        }
        Comparator var57 = Comparator.comparingDouble((p0) -> Tracers.lambda$a(ClarityRenderUtils.getCameraPos(), p0)).reversed();
        var10.sort(var57);
        return var10;
    }

    private void renderWorldEffect(PoseStack arg0, float arg1) {
        Minecraft var3 = Minecraft.getInstance();
        if (var3.level != null) {
            if (var3.player == null) {
            }
        }
        return;
        if (isStyle()) {
            return;
        }
        Integer var62 = 200;
        int var27 = ((Integer) this.getSetting("Alpha", var62));
        Float var64 = 2.0F;
        float var31 = ((Float) this.getSetting("Thickness", var64));
        TracerTargetGroup var33 = this.evaluate(arg1);
        int var35 = var33.playerArgb();
        int var37 = var33.mobArgb();
        int var40 = computeColorOrIndex(var33.entityArgb(), var27);
        List var42 = var33.players();
        List var44 = var33.mobs();
        List var46 = var33.entities();
        if (!var42.isEmpty()) {
            ClarityRenderUtils.drawTracers(arg0, arg1, var42, var35, var31);
        }
        if (!var44.isEmpty()) {
            ClarityRenderUtils.drawTracers(arg0, arg1, var44, var37, var31);
        }
        if (!var46.isEmpty()) {
            int var73 = (var27 / 2);
            int var70 = computeColorOrIndex(var40, var73);
            ClarityRenderUtils.drawTracers(arg0, arg1, var46, var70, var31);
        }
    }

    private boolean isEligible(String arg0) {
        try {
            JsonObject var9 = ClarityConfig.getCurrent().getAsJsonObject("Tracers");
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
            JsonElement var19 = var15.get("Tracer Mode");
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

    private static int computeColorOrIndex(String arg0, int arg1, boolean arg2) {
        int var7 = ThemeColors.resolveRgb(arg0, 16758062, arg2);
        int var10 = ((arg1 & 255) << 24);
        int var15 = (var7 & 16777215);
        return (var10 | var15);
    }

    private static int computeColorOrIndex(int arg0, int arg1) {
        int var5 = (arg0 & 16777215);
        int var9 = ((arg1 & 255) << 24);
        return (var5 | var9);
    }

    public static Tracers evaluate() {
        return INSTANCE;
    }

}
