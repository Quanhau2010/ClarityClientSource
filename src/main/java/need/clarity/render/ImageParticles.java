package need.clarity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import need.clarity.WorldRenderEvent;
import need.clarity.util.ClarityRenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.world.phys.Vec3;

public final class ImageParticles {
    public static final String GLOW = "particle_glow";
    public static final String SNOWFLAKE = "particle_snowflake";
    private static final int MAX = 3000;
    private static final List LIST;
    private static final WorldRenderEvent.Listener LISTENER;
    private static boolean registered;
    private static long lastNanos;

    private ImageParticles() {
    }

    public static String textureFor(String arg0) {
        String var17 = null;
        int var4 = 0;
        if (arg0 == null) {
            return null;
        }
        var4 = -1;
        switch (arg0.hashCode()) {
            case 2975373 -> {}
            case 491171186 -> {}
            default -> {}
        }
        if (arg0.equals("Image Glow")) {
            var4 = 0;
            if (arg0.equals("Image Snowflake")) {
                var4 = 1;
            }
            switch (var4) {
                case 0 -> {}
                case 1 -> {}
                default -> {}
            }
            var17 = "particle_snowflake";
            var17 = "particle_glow";
            var17 = null;
            return var17;
        }
    }

    public static void spawn(String arg0, double arg1, double arg2, double arg3, double arg4, double arg5, double arg6, float arg7, float arg8, float arg9, int arg10, boolean arg11) {
        if (LIST.size() >= 3000) {
            return;
        }
        if (!registered) {
            registered = true;
            WorldRenderEvent.register(LISTENER);
        }
        ImageParticles.Particle var26 = new ImageParticles.Particle();
        var26.x = arg1;
        var26.y = arg2;
        var26.z = arg3;
        var26.vx = arg4;
        var26.vy = arg5;
        var26.vz = arg6;
        float var54 = Math.max(0.05F, arg7);
        var26.life = var54;
        var26.size = arg8;
        var26.gravity = arg9;
        int var58 = (arg10 & 16777215);
        var26.rgb = var58;
        var26.tex = arg0;
        var26.collide = arg11;
        boolean var40 = LIST.add(var26);
    }

    public static void clear() {
        LIST.clear();
    }

    private static void onRender(PoseStack arg0, float arg1) {
        float var143 = 0.0F;
        float var17 = 0.0F;
        int var18 = 0;
        if (LIST.isEmpty()) {
            lastNanos = 0L;
            return;
        }
        Minecraft var34 = Minecraft.getInstance();
        if (var34 != null) {
            if (var34.level != null) {
                if (var34.player == null) {
                }
            }
            LIST.clear();
            return;
            long var40 = System.nanoTime();
            if (lastNanos == 0L) {
                var143 = 0.0F;
            } else {
                long var207 = lastNanos;
                var143 = (((float) (var40 - var207)) / 1.0E9F);
            }
            var17 = var143;
            lastNanos = var40;
            if (Float.compare(var17, 0.1F) > 0) {
                var17 = 0.1F;
            }
            var18 = (LIST.size() - 1);
            while (true) {
                if (var18 < 0) break;
                List var114 = LIST;
                int var166 = var18;
                ImageParticles.Particle var116 = ((ImageParticles.Particle) var114.get(var166));
                float var167 = var116.age;
                float var206 = var17;
                float var168 = (var167 + var206);
                var116.age = var168;
                float var119 = var116.age;
                float var170 = var116.life;
                if (Float.compare(var119, var170) >= 0) {
                    List var133 = LIST;
                    int var192 = var18;
                    Object var134 = var133.remove(var192);
                } else {
                    double var171 = var116.vy;
                    float var213 = var116.gravity;
                    float var235 = var17;
                    double var215 = ((double) (var213 * var235));
                    double var173 = (var171 - var215);
                    var116.vy = var173;
                    double var175 = var116.x;
                    double var218 = var116.vx;
                    double var239 = ((double) var17);
                    double var220 = (var218 * var239);
                    double var177 = (var175 + var220);
                    var116.x = var177;
                    double var179 = var116.y;
                    double var223 = var116.vy;
                    double var242 = ((double) var17);
                    double var225 = (var223 * var242);
                    double var181 = (var179 + var225);
                    var116.y = var181;
                    double var183 = var116.z;
                    double var228 = var116.vz;
                    double var245 = ((double) var17);
                    double var230 = (var228 * var245);
                    double var185 = (var183 + var230);
                    var116.z = var185;
                    if (var116.collide) {
                        ClientLevel var128 = var34.level;
                        double var188 = var116.x;
                        double var233 = var116.y;
                        double var248 = var116.z;
                        BlockPos var190 = BlockPos.containing(var188, var233, var248);
                        if (!var128.getBlockState(var190).isAir()) {
                            List var131 = LIST;
                            int var191 = var18;
                            Object var132 = var131.remove(var191);
                        }
                        var18 = (var18 + -1);
                    }
                }
            }
            if (LIST.isEmpty()) {
                return;
            }
            Vec3 var56 = ClarityRenderUtils.getCameraPos();
            HashMap var57 = new HashMap();
            Iterator var59 = LIST.iterator();
            while (true) {
                if (!var59.hasNext()) break;
                ImageParticles.Particle var64 = ((ImageParticles.Particle) var59.next());
                String var146 = var64.tex;
                Function var194 = (p0) -> ImageParticles.lambda$onRender$0(p0);
                boolean var68 = ((List) var57.computeIfAbsent(var146, var194)).add(var64);
            }
            Iterator var71 = var57.entrySet().iterator();
            while (true) {
                if (!var71.hasNext()) break;
                Map.Entry var76 = ((Map.Entry) var71.next());
                Identifier var80 = FxTextures.get(((String) var76.getKey()));
                if (var80 == null) {
                }
                List var84 = ((List) var76.getValue());
                Comparator var150 = Comparator.comparingDouble((p0) -> ImageParticles.lambda$onRender$1(var56, p0));
                var84.sort(var150);
                ClarityRenderUtils.TexBatch var87 = ClarityRenderUtils.beginTextured(arg0, var80);
                Iterator var89 = var84.iterator();
                while (true) {
                    if (!var89.hasNext()) break;
                    ImageParticles.Particle var94 = ((ImageParticles.Particle) var89.next());
                    float var154 = (var94.age / 0.15F);
                    float var96 = Math.min(1.0F, var154);
                    float var156 = var94.life;
                    float var197 = var94.age;
                    float var157 = (var156 - var197);
                    float var200 = (var94.life * 0.35F);
                    float var158 = (var157 / var200);
                    float var98 = Math.min(1.0F, var158);
                    float var160 = Math.min(var96, var98);
                    int var104 = (Math.round((Math.max(0.0F, var160) * 255.0F)) << 24);
                    int var164 = var94.rgb;
                    int var105 = (var104 | var164);
                    double var110 = (((double) var94.size) * 0.5);
                    double var210 = var94.x;
                    double var236 = var94.y;
                    double var250 = var94.z;
                    Vec3 var165 = new Vec3(var210, var236, var250);
                    var87.billboard(var165, var110, var110, 0.0, var105);
                }
                var87.end();
            }
            return;
        }
    }

    private static double distSq(ImageParticles.Particle arg0, Vec3 arg1) {
        double var12 = arg0.x;
        double var34 = arg1.x;
        double var14 = (var12 - var34);
        double var17 = arg0.y;
        double var37 = arg1.y;
        double var19 = (var17 - var37);
        double var22 = arg0.z;
        double var40 = arg1.z;
        double var24 = (var22 - var40);
        double var28 = (var14 * var14);
        double var46 = (var19 * var19);
        double var30 = (var28 + var46);
        double var50 = (var24 * var24);
        return (var30 + var50);
    }

    static {
        ArrayList var0 = new ArrayList();
        LIST = var0;
        LISTENER = (p0, p1) -> ImageParticles.onRender(p0, p1);
        registered = false;
        lastNanos = 0L;
    }

    public final class Particle {
        double x;
        double y;
        double z;
        double vx;
        double vy;
        double vz;
        float age;
        float life;
        float size;
        float gravity;
        int rgb;
        String tex;
        boolean collide;

        private Particle() {
        }

    }

}
