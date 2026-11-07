package need.clarity.modules.visual;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import need.clarity.EventStartAttack;
import need.clarity.PerAttackEvents;
import need.clarity.WorldRenderEvent;
import need.clarity.modules.ModuleBase;
import need.clarity.render.ImageParticles;
import need.clarity.render.worldfx.GpuFx;
import need.clarity.render.worldfx.ShapeFx;
import need.clarity.util.ClarityRenderUtils;
import need.clarity.util.ThemeColors;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public class HitParticles extends ModuleBase {
    private final Random fieldVal;
    private final List entriesList;
    private final WorldRenderEvent.Listener fieldVal1;
    private boolean enabledState;

    public HitParticles() {
        super("Hit Particles", "Visual");
        Random var13 = new Random();
        this.fieldVal = var13;
        ArrayList var14 = new ArrayList();
        this.entriesList = var14;
        WorldRenderEvent.Listener var16 = (p0, p1) -> this.renderWorldEffect(p0, p1);
        this.fieldVal1 = var16;
        this.enabledState = false;
        PerAttackEvents.register((p0) -> this.onStartAttack(p0));
        if (this.isEnabled()) {
            this.applyStep();
        }
    }

    public void onEnable() {
        this.applyStep();
    }

    public void onDisable() {
        this.applyStep1();
        this.entriesList.clear();
    }

    private void applyStep() {
        if (this.enabledState) {
            return;
        }
        this.enabledState = true;
        WorldRenderEvent.register(this.fieldVal1);
    }

    private void applyStep1() {
        if (!this.enabledState) {
            return;
        }
        this.enabledState = false;
        WorldRenderEvent.unregister(this.fieldVal1);
    }

    private void onStartAttack(EventStartAttack arg0) {
        int var48 = 0;
        int var200 = 0;
        String var202 = null;
        ParticleOptions var217 = null;
        ParticleOptions var218 = null;
        ParticleOptions var219 = null;
        int var220 = 0;
        if (!this.isEnabled()) {
            return;
        }
        Entity var66 = arg0.getTarget();
        if (var66 == null) {
            return;
        }
        Minecraft var68 = Minecraft.getInstance();
        if (var68.level == null) {
            return;
        }
        String var73 = ((String) this.getSetting("Particle", "Crit"));
        int var75 = GpuFx.hitStyle(var73);
        if (var75 >= 0) {
            Float var250 = 0.3F;
            float var80 = ((Float) this.getSetting("Spread", var250));
            double var223 = var66.getX();
            double var322 = var66.getY();
            double var352 = (((double) var66.getBbHeight()) * 0.5);
            double var324 = (var322 + var352);
            double var355 = var66.getZ();
            float var374 = (0.55F + var80);
            GpuFx.addBurst(var75, var223, var324, var355, var374);
            Boolean var252 = false;
            if (((Boolean) this.getSetting("Ring", var252))) {
                List var87 = this.entriesList;
                double var358 = var66.getX();
                double var376 = var66.getY();
                double var391 = var66.getZ();
                Vec3 var326 = new Vec3(var358, var376, var391);
                long var335 = System.nanoTime();
                HitParticleBurstEntry var226 = new HitParticleBurstEntry(var326, var335);
                boolean var88 = var87.add(var226);
            }
            return;
        }
        Integer var254 = 10;
        int var92 = ((Integer) this.getSetting("Count", var254));
        Float var256 = 0.3F;
        float var96 = ((Float) this.getSetting("Spread", var256));
        Float var338 = 1.0F;
        float var260 = ((Float) this.getSetting("Size", var338));
        float var230 = Math.min(3.0F, var260);
        float var98 = Math.max(0.5F, var230);
        int var101 = ThemeColors.resolveRgb(this.getSetting("Colour", "#FF0000"), 16711680, false);
        String var104 = ((String) this.getSetting("Second Particle", "None"));
        String var106 = ImageParticles.textureFor(var73);
        String var108 = ImageParticles.textureFor(var104);
        if (var106 == null) {
            var219 = evaluate(var73, var101, var98);
        } else {
            var219 = null;
        }
        ParticleOptions var36 = var219;
        if (var106 == null) {
            if (var36 == null) {
                return;
            }
        }
        if (var108 == null) {
            var218 = evaluate(var104, var101, var98);
        } else {
            var218 = null;
        }
        ParticleOptions var37 = var218;
        if (var108 == null) {
            if (var37 != null) {
            }
        }
        var220 = 1;
        var220 = 0;
        int var38 = var220;
        int var118 = (ThemeColors.resolveRgb(this.getSetting("Image Colour", "#FFFFFF"), 16777215, false) & 16777215);
        double var120 = var66.getX();
        double var123 = var66.getY();
        double var270 = (((double) var66.getBbHeight()) * 0.5);
        double var125 = (var123 + var270);
        double var128 = var66.getZ();
        double var133 = (((double) var96) + 0.2);
        double var275 = ((double) var98);
        double var135 = (var133 * var275);
        var48 = 0;
        while (true) {
            if (var48 >= var92) break;
            double var143 = (this.fieldVal.nextDouble() - 0.5);
            double var280 = ((double) var96);
            double var147 = ((var143 * var280) * 2.0);
            double var151 = this.fieldVal.nextDouble();
            double var153 = (var151 - 0.5);
            double var287 = ((double) var96);
            double var155 = (var153 * var287);
            double var157 = (var155 * 2.0);
            Random var160 = this.fieldVal;
            double var161 = var160.nextDouble();
            double var163 = (var161 - 0.5);
            double var294 = ((double) var96);
            double var165 = (var163 * var294);
            double var167 = (var165 * 2.0);
            Random var170 = this.fieldVal;
            double var171 = var170.nextDouble();
            double var173 = (var171 - 0.5);
            double var175 = (var173 * 0.3);
            double var177 = (var175 * var135);
            Random var180 = this.fieldVal;
            double var181 = var180.nextDouble();
            double var183 = (var181 * 0.2);
            double var185 = (var183 * var135);
            Random var188 = this.fieldVal;
            double var189 = var188.nextDouble();
            double var191 = (var189 - 0.5);
            double var193 = (var191 * 0.3);
            double var195 = (var193 * var135);
            if (var38 != 0) {
                int var198 = var48;
                int var199 = (var198 & 1);
                if (var199 == 1) {
                    var200 = 1;
                } else {
                    var200 = 0;
                }
                int var61 = var200;
                if (var61 != 0) {
                    var202 = var108;
                } else {
                    var202 = var106;
                }
                String var62 = var202;
                if (var62 != null) {
                    double var243 = (var120 + var147);
                    double var332 = (var125 + var157);
                    double var364 = (var128 + var167);
                    double var382 = (var177 * 20.0);
                    double var399 = ((var185 * 20.0) + 0.4);
                    double var408 = (var195 * 20.0);
                    Random var412 = this.fieldVal;
                    float var413 = var412.nextFloat();
                    float var414 = (var413 * 0.35F);
                    float var411 = (0.45F + var414);
                    float var416 = (0.22F * var98);
                    Random var422 = this.fieldVal;
                    float var423 = var422.nextFloat();
                    float var424 = (var423 * 0.6F);
                    float var420 = (0.7F + var424);
                    float var417 = (var416 * var420);
                    ImageParticles.spawn(var62, var243, var332, var364, var382, var399, var408, var411, var417, 3.0F, var118, false);
                } else {
                    if (var61 != 0) {
                        var217 = var37;
                    } else {
                        var217 = var36;
                    }
                    ParticleOptions var63 = var217;
                    ClientLevel var206 = var68.level;
                    double var316 = (var120 + var147);
                    double var345 = (var125 + var157);
                    double var371 = (var128 + var167);
                    var206.addParticle(var63, var316, var345, var371, var177, var185, var195);
                }
                var48 = (var48 + 1);
            }
        }
        Boolean var319 = false;
        Object var209 = this.getSetting("Ring", var319);
        Boolean var210 = ((Boolean) var209);
        boolean var211 = var210;
        if (var211) {
            List var213 = this.entriesList;
            double var367 = var66.getX();
            double var385 = var66.getY();
            double var402 = var66.getZ();
            Vec3 var334 = new Vec3(var367, var385, var402);
            long var347 = System.nanoTime();
            HitParticleBurstEntry var246 = new HitParticleBurstEntry(var334, var347);
            boolean var214 = var213.add(var246);
        }
    }

    private void renderWorldEffect(PoseStack arg0, float arg1) {
        if (!this.isEnabled()) {
            return;
        }
        if (GpuFx.hasBursts()) {
            int var35 = GpuFx.hitStyle(((String) this.getSetting("Particle", "Crit")));
            if (var35 >= 0) {
                int var40 = (ThemeColors.resolveRgb(this.getSetting("Colour", "#FF0000"), 16711680, false) & 16777215);
                int var44 = (ThemeColors.resolveRgb(this.getSetting("Second Colour", "#FFD36B"), 16765803, true) & 16777215);
                Integer var180 = 10;
                int var127 = (((Integer) this.getSetting("Count", var180)) * 2);
                Float var200 = 1.0F;
                float var189 = ((Float) this.getSetting("Size", var200));
                float var182 = Math.min(3.0F, var189);
                float var151 = Math.max(0.5F, var182);
                float[] var184 = ShapeFx.rgb(var40);
                float[] var191 = ShapeFx.rgb(var44);
                Boolean var207 = true;
                boolean var198 = ((Boolean) this.getSetting("Natural Colors", var207));
                Boolean var209 = false;
                boolean var205 = ((Boolean) this.getSetting("Through Walls", var209));
                GpuFx.renderBursts(var35, var127, var151, var184, var191, var198, var205);
            }
            if (this.entriesList.isEmpty()) {
                return;
            }
            Float var153 = 0.6F;
            double var53 = ((double) ((Float) this.getSetting("Ring TTL", var153)));
            Float var155 = 7.0F;
            double var59 = ((double) ((Float) this.getSetting("Ring Speed", var155)));
            Float var157 = 2.5F;
            double var65 = ((double) ((Float) this.getSetting("Ring Radius", var157)));
            Integer var159 = 40;
            int var70 = ((Integer) this.getSetting("Ring Points", var159));
            Boolean var161 = true;
            boolean var74 = ((Boolean) this.getSetting("Ring Through Walls", var161));
            int var77 = ThemeColors.resolveRgb(this.getSetting("Ring Color", ""), -50373, false);
            long var78 = System.nanoTime();
            List var81 = this.entriesList;
            Predicate var137 = (p0) -> HitParticles.lambda$a(var78, var53, p0);
            boolean var82 = var81.removeIf(var137);
            Iterator var85 = this.entriesList.iterator();
            while (true) {
                if (!var85.hasNext()) break;
                HitParticleBurstEntry var90 = ((HitParticleBurstEntry) var85.next());
                long var165 = var90.time();
                double var97 = (((double) (var78 - var165)) / 1.0E9);
                double var101 = (var59 * var97);
                if (Double.compare(var101, var65) > 0) {
                }
                double var175 = (var101 / var65);
                int var114 = (Math.round((((float) (1.0 - var175)) * 220.0F)) << 24);
                int var141 = (var77 & 16777215);
                int var115 = (var114 | var141);
                Vec3 var143 = var90.origin();
                ClarityRenderUtils.drawGroundRing(arg0, var143, var101, var115, var74, var70);
            }
            return;
        }
    }

    private static ParticleOptions evaluate(String arg0, int arg1, float arg2) {
        int var8 = 0;
        if (arg0 != null) {
            if ("None".equalsIgnoreCase(arg0)) {
            }
        }
        return null;
        var8 = -1;
        switch (arg0.hashCode()) {
            case -2094112058 -> {}
            case -1342130166 -> {}
            case -985741672 -> {}
            case 2108922 -> {}
            case 2141906 -> {}
            case 2361088 -> {}
            case 2434066 -> {}
            case 47520061 -> {}
            case 57074745 -> {}
            case 65203733 -> {}
            case 67960595 -> {}
            case 69599270 -> {}
            case 80009551 -> {}
            case 80997281 -> {}
            case 1973786418 -> {}
            default -> {}
        }
        if (arg0.equals("Electric")) {
            var8 = 8;
            if (arg0.equals("Enchanted")) {
                var8 = 0;
                if (arg0.equals("Heart")) {
                    var8 = 4;
                    if (arg0.equals("Dust")) {
                        var8 = 13;
                        if (arg0.equals("Soul Flame")) {
                            var8 = 3;
                            if (arg0.equals("Cloud")) {
                                var8 = 7;
                                if (arg0.equals("Smoke")) {
                                    var8 = 5;
                                    if (arg0.equals("Lava")) {
                                        var8 = 9;
                                        if (arg0.equals("Enchant")) {
                                            var8 = 1;
                                            if (arg0.equals("Note")) {
                                                var8 = 12;
                                                if (arg0.equals("Flame")) {
                                                    var8 = 2;
                                                    if (arg0.equals("Snowflake")) {
                                                        var8 = 10;
                                                        if (arg0.equals("Totem")) {
                                                            var8 = 11;
                                                            if (arg0.equals("Large Smoke")) {
                                                                var8 = 6;
                                                                if (arg0.equals("Crit")) {
                                                                    var8 = 14;
                                                                }
                                                                switch (var8) {
                                                                    case 0 -> {}
                                                                    case 1 -> {}
                                                                    case 2 -> {}
                                                                    case 3 -> {}
                                                                    case 4 -> {}
                                                                    case 5 -> {}
                                                                    case 6 -> {}
                                                                    case 7 -> {}
                                                                    case 8 -> {}
                                                                    case 9 -> {}
                                                                    case 10 -> {}
                                                                    case 11 -> {}
                                                                    case 12 -> {}
                                                                    case 13 -> {}
                                                                    default -> {}
                                                                }
                                                                return ParticleTypes.ENCHANTED_HIT;
                                                                return ParticleTypes.ELECTRIC_SPARK;
                                                                return ParticleTypes.SOUL_FIRE_FLAME;
                                                                return ParticleTypes.LAVA;
                                                                return ParticleTypes.CRIT;
                                                                return ParticleTypes.NOTE;
                                                                return ParticleTypes.ENCHANT;
                                                                return ParticleTypes.LARGE_SMOKE;
                                                                return ParticleTypes.CLOUD;
                                                                int var92 = (arg1 & 16777215);
                                                                DustParticleOptions var20 = new DustParticleOptions(var92, arg2);
                                                                return var20;
                                                                return ParticleTypes.SNOWFLAKE;
                                                                return ParticleTypes.TOTEM_OF_UNDYING;
                                                                return ParticleTypes.HEART;
                                                                return ParticleTypes.FLAME;
                                                                return ParticleTypes.SMOKE;
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}
