package need.clarity.modules.visual;

import com.mojang.blaze3d.vertex.PoseStack;
import need.clarity.WorldRenderEvent;
import need.clarity.modules.ModuleBase;
import need.clarity.render.ImageParticles;
import need.clarity.render.worldfx.GpuFx;
import need.clarity.util.ThemeColors;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;

public class Particles extends ModuleBase {
    public static Particles INSTANCE;
    private final WorldRenderEvent.Listener fieldVal;
    private final long timestampMs;
    private double doubleValue;
    private long timestampMs1;

    public Particles() {
        super("Particles", "Visual");
        WorldRenderEvent.Listener var14 = (p0, p1) -> this.renderWorldEffect(p0, p1);
        this.fieldVal = var14;
        long var15 = System.nanoTime();
        this.timestampMs = var15;
        this.doubleValue = 0.0;
        this.timestampMs1 = 0L;
        INSTANCE = this;
        if (this.isEnabled()) {
            WorldRenderEvent.register(this.fieldVal);
        }
    }

    public void onEnable() {
        WorldRenderEvent.register(this.fieldVal);
    }

    public void onDisable() {
        WorldRenderEvent.unregister(this.fieldVal);
    }

    private void renderWorldEffect(PoseStack arg0, float arg1) {
        double var55 = 0.0;
        if (!this.isEnabled()) {
            return;
        }
        int var30 = GpuFx.skyStyle(((String) this.getSetting("Particle", "Snowflake")));
        if (var30 < 0) {
            return;
        }
        long var32 = System.nanoTime();
        if (this.timestampMs1 == 0L) {
            var55 = 0.0;
        } else {
            long var108 = this.timestampMs1;
            double var83 = (((double) (var32 - var108)) / 1.0E9);
            var55 = Math.min(0.1, var83);
        }
        double var21 = var55;
        this.timestampMs1 = var32;
        double var64 = ((this.doubleValue + var21) % 3600.0);
        this.doubleValue = var64;
        Float var86 = 0.05F;
        float var45 = ((Float) this.getSetting("Fall Speed", var86));
        int var49 = (ThemeColors.resolveRgb(this.getSetting("Colour", "#8FD3FF"), 9425919, false) & 16777215);
        int var53 = (ThemeColors.resolveRgb(this.getSetting("Second Colour", "#FF9AE6"), 16751334, true) & 16777215);
        Integer var118 = 2500;
        int var102 = ((Integer) this.getSetting("Density", var118));
        int var92 = Math.min(12000, var102);
        int var74 = Math.max(100, var92);
        Float var114 = 16.0F;
        float var96 = ((Float) this.getSetting("Radius", var114));
        Float var120 = 8.0F;
        float var107 = ((Float) this.getSetting("Height", var120));
        float var117 = (var45 / 0.05F);
        Float var128 = 1.0F;
        float var125 = ((Float) this.getSetting("Particle Size", var128));
        float[] var127 = evaluate(var49);
        float[] var130 = evaluate(var53);
        Boolean var136 = true;
        boolean var133 = ((Boolean) this.getSetting("Natural Colors", var136));
        Float var153 = 1.0F;
        float var145 = ((Float) this.getSetting("Opacity", var153));
        float var138 = Math.min(1.0F, var145);
        float var135 = Math.max(0.05F, var138);
        Boolean var152 = true;
        boolean var142 = ((Boolean) this.getSetting("Land On Blocks", var152));
        float var150 = ((float) this.doubleValue);
        GpuFx.sky(var30, var74, var96, var107, var117, var125, var127, var130, var133, var135, var142, var150);
    }

    private static float[] evaluate(int arg0) {
        float[] var5 = new float[3];
        float var11 = (((float) ((arg0 >> 16) & 255)) / 255.0F);
        var5[0] = var11;
        float var16 = (((float) ((arg0 >> 8) & 255)) / 255.0F);
        var5[1] = var16;
        float var20 = (((float) (arg0 & 255)) / 255.0F);
        var5[2] = var20;
        return var5;
    }

    public void onTick() {
        ParticleOptions var140 = null;
        int var46 = 0;
        Minecraft var1 = Minecraft.getInstance();
        if (var1 != null) {
            if (var1.player != null) {
                if (var1.level == null) {
                }
            }
            return;
            String var62 = ((String) this.getSetting("Particle", "Snowflake"));
            if (GpuFx.skyStyle(var62) >= 0) {
                Boolean var199 = true;
                if (((Boolean) this.getSetting("Land On Blocks", var199))) {
                    GpuFx.tickHeightmap();
                }
                return;
            }
            String var66 = ImageParticles.textureFor(var62);
            if (var66 == null) {
                var140 = this.evaluate();
            } else {
                var140 = null;
            }
            ParticleOptions var32 = var140;
            if (var66 == null) {
                if (var32 == null) {
                    return;
                }
            }
            Integer var161 = 4;
            int var74 = ((Integer) this.getSetting("Rate", var161));
            Float var163 = 16.0F;
            float var78 = ((Float) this.getSetting("Radius", var163));
            Float var165 = 8.0F;
            float var82 = ((Float) this.getSetting("Height", var165));
            Float var167 = 0.05F;
            float var86 = ((Float) this.getSetting("Fall Speed", var167));
            double var89 = var1.player.getX();
            double var93 = var1.player.getY();
            double var97 = var1.player.getZ();
            Float var169 = 0.18F;
            float var102 = ((Float) this.getSetting("Image Size", var169));
            int var106 = (ThemeColors.resolveRgb(this.getSetting("Image Colour", "#FFFFFF"), 16777215, false) & 16777215);
            double var204 = (((double) var82) + 1.0);
            double var233 = (((double) var86) * 20.0);
            double var219 = Math.max(0.01, var233);
            double var206 = (var204 / var219);
            double var174 = Math.min(10.0, var206);
            float var111 = ((float) Math.max(0.5, var174));
            var46 = 0;
            while (true) {
                if (var46 >= var74) break;
                double var118 = ((Math.random() - 0.5) * 2.0);
                double var181 = ((double) var78);
                double var120 = (var118 * var181);
                double var126 = ((Math.random() - 0.5) * 2.0);
                double var188 = ((double) var78);
                double var128 = (var126 * var188);
                double var190 = Math.random();
                double var209 = ((double) var82);
                double var192 = (var190 * var209);
                double var132 = (var93 + var192);
                if (var66 != null) {
                    double var157 = (var89 + var120);
                    double var215 = (var97 + var128);
                    double var229 = ((Math.random() - 0.5) * 0.25);
                    double var244 = (((double) var86) * 20.0);
                    double var253 = ((Math.random() - 0.5) * 0.25);
                    float var262 = (((float) Math.random()) * 0.6F);
                    float var259 = (0.7F + var262);
                    float var258 = (var102 * var259);
                    ImageParticles.spawn(var66, var157, var132, var215, var229, var244, var253, var111, var258, 0.0F, var106, true);
                } else {
                    ClientLevel var136 = var1.level;
                    double var196 = (var89 + var120);
                    double var223 = (var97 + var128);
                    double var247 = ((double) var86);
                    var136.addParticle(var32, var196, var132, var223, 0.0, var247, 0.0);
                }
                var46 = (var46 + 1);
            }
            return;
        }
    }

    private ParticleOptions evaluate() {
        int var5 = 0;
        SimpleParticleType var15 = null;
        String var7 = ((String) this.getSetting("Particle", "Snowflake"));
        var5 = -1;
        switch (var7.hashCode()) {
            case -2094112058 -> {}
            case 2141906 -> {}
            case 2361088 -> {}
            case 2433880 -> {}
            case 2434066 -> {}
            case 47520061 -> {}
            case 55838914 -> {}
            case 65203733 -> {}
            case 67960595 -> {}
            case 69599270 -> {}
            case 80997281 -> {}
            default -> {}
        }
        if (var7.equals("Dust")) {
            var5 = 0;
            if (var7.equals("Lava")) {
                var5 = 6;
                if (var7.equals("Electric")) {
                    var5 = 5;
                    if (var7.equals("Totem")) {
                        var5 = 8;
                        if (var7.equals("End Rod")) {
                            var5 = 9;
                            if (var7.equals("Soul Flame")) {
                                var5 = 2;
                                if (var7.equals("Cloud")) {
                                    var5 = 4;
                                    if (var7.equals("Flame")) {
                                        var5 = 1;
                                        if (var7.equals("Note")) {
                                            var5 = 7;
                                            if (var7.equals("Heart")) {
                                                var5 = 3;
                                                if (var7.equals("None")) {
                                                    var5 = 10;
                                                }
                                                switch (var5) {
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
                                                    default -> {}
                                                }
                                                var15 = ParticleTypes.NOTE;
                                                var15 = ParticleTypes.TOTEM_OF_UNDYING;
                                                var15 = ParticleTypes.ELECTRIC_SPARK;
                                                var15 = ParticleTypes.END_ROD;
                                                var15 = null;
                                                var15 = ParticleTypes.CRIT;
                                                var15 = ParticleTypes.HEART;
                                                var15 = ParticleTypes.FLAME;
                                                var15 = ParticleTypes.CLOUD;
                                                var15 = ParticleTypes.SOUL_FIRE_FLAME;
                                                var15 = ParticleTypes.LAVA;
                                                var15 = ParticleTypes.SNOWFLAKE;
                                                return var15;
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
