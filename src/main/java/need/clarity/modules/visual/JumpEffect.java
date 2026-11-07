package need.clarity.modules.visual;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import need.clarity.WorldRenderEvent;
import need.clarity.modules.ModuleBase;
import need.clarity.render.worldfx.WorldFx;
import need.clarity.util.ClarityRenderUtils;
import need.clarity.util.ThemeColors;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class JumpEffect extends ModuleBase {
    public static JumpEffect INSTANCE;
    public static final String[] fieldVal;
    private final List entriesList;
    private final WorldRenderEvent.Listener fieldVal1;
    private boolean enabledState;
    private boolean enabledState1;
    private int counterValue;
    private int counterValue1;

    public JumpEffect() {
        super("Jump Effect", "Visual");
        ArrayList var14 = new ArrayList();
        this.entriesList = var14;
        WorldRenderEvent.Listener var16 = (p0, p1) -> this.renderWorldEffect(p0, p1);
        this.fieldVal1 = var16;
        this.enabledState = false;
        this.enabledState1 = true;
        this.counterValue = 0;
        this.counterValue1 = 0;
        INSTANCE = this;
        if (this.isEnabled()) {
            this.applyStep();
        }
    }

    public void onEnable() {
        this.applyStep();
    }

    public void onDisable() {
        this.enabledState1 = true;
        this.entriesList.clear();
        this.applyStep1();
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

    public void onTick() {
        int var74 = 0;
        int var75 = 0;
        int var49 = 0;
        int var91 = 0;
        Minecraft var1 = Minecraft.getInstance();
        if (var1 != null) {
            if (var1.player != null) {
                if (var1.level == null) {
                }
            }
            return;
            LocalPlayer var22 = var1.player;
            boolean var24 = var22.onGround();
            String var27 = ((String) this.getSetting("Trigger", "Jump"));
            if (!var24) {
                if (this.enabledState1) {
                    if (Double.compare(var22.getDeltaMovement().y, 0.0) > 0) {
                        var75 = 1;
                    } else {
                        var75 = 0;
                    }
                    int var12 = var75;
                    if (var24) {
                        if (!this.enabledState1) {
                            if (this.counterValue > 3) {
                                var74 = 1;
                            } else {
                            }
                        }
                        var74 = 0;
                        int var13 = var74;
                        if (var24) {
                            var91 = 0;
                        } else {
                            var91 = (this.counterValue + 1);
                        }
                        this.counterValue = var91;
                        this.enabledState1 = var24;
                        if (var12 != 0) {
                            if ("Land".equals(var27)) {
                            }
                        }
                        if (var13 != 0) {
                            if (!"Jump".equals(var27)) {
                                var49 = 1;
                            } else {
                                var49 = 0;
                            }
                            if (var49 == 0) {
                                return;
                            }
                            Boolean var95 = false;
                            if (((Boolean) this.getSetting("Only Sprinting", var95))) {
                                if (!var22.isSprinting()) {
                                    if (var13 == 0) {
                                        return;
                                    }
                                }
                                if (this.computeColorOrIndex() >= 0) {
                                    int var83 = this.counterValue1;
                                    int var97 = (var83 + 1);
                                    this.counterValue1 = var97;
                                    float var64 = ((((float) var83) * 0.6180339F) % 1.0F);
                                    List var66 = this.entriesList;
                                    double var103 = var22.getX();
                                    double var105 = var22.getY();
                                    double var107 = var22.getZ();
                                    Vec3 var100 = new Vec3(var103, var105, var107);
                                    long var101 = System.nanoTime();
                                    JumpCircleEntry var86 = new JumpCircleEntry(var100, var101, var64);
                                    boolean var67 = var66.add(var86);
                                    if (this.entriesList.size() > 32) {
                                        Object var73 = this.entriesList.remove(0);
                                    }
                                } else {
                                    this.applyStep(var1, var22);
                                }
                                return;
                            }
                        }
                    }
                }
            }
        }
    }

    private int computeColorOrIndex() {
        int var5 = 0;
        String var7 = ((String) this.getSetting("Particle", "Ripple"));
        if ("Jump Circle".equals(var7)) {
            return 0;
        }
        var5 = 0;
        while (true) {
            int var12 = var5;
            int var20 = fieldVal.length;
            if (var12 >= var20) break;
            String[] var13 = fieldVal;
            int var21 = var5;
            if (var13[var21].equals(var7)) {
                return var5;
            }
            var5 = (var5 + 1);
        }
        return -1;
    }

    private void renderWorldEffect(PoseStack arg0, float arg1) {
        if (this.isEnabled()) {
            if (this.entriesList.isEmpty()) {
            }
        }
        return;
        Float var270 = 0.9F;
        Object var202 = this.getSetting("Circle Duration", var270);
        Float var203 = ((Float) var202);
        float var204 = var203;
        double var205 = ((double) var204);
        double var41 = Math.max(0.05, var205);
        Float var254 = 1.6F;
        float var159 = ((Float) this.getSetting("Circle Size", var254));
        float var44 = Math.max(0.1F, var159);
        int var48 = (ThemeColors.resolveRgb(this.getSetting("Circle Colour", "#6FC3FF"), 7324671, false) & 16777215);
        int var52 = (ThemeColors.resolveRgb(this.getSetting("Circle Colour 2", "#C77DFF"), 13073919, true) & 16777215);
        Boolean var213 = false;
        boolean var56 = ((Boolean) this.getSetting("Circle Through Walls", var213));
        int var168 = this.computeColorOrIndex();
        int var58 = Math.max(0, var168);
        long var59 = System.nanoTime();
        List var62 = this.entriesList;
        Predicate var171 = (p0) -> JumpEffect.lambda$a(var59, var41, p0);
        boolean var63 = var62.removeIf(var171);
        if (this.entriesList.isEmpty()) {
            return;
        }
        Vec3 var67 = ClarityRenderUtils.getCameraPos();
        int var70 = ((var48 >> 16) & 255);
        float var71 = ((float) var70);
        float var72 = (var71 / 255.0F);
        int var74 = (var48 >> 8);
        int var75 = (var74 & 255);
        float var76 = ((float) var75);
        float var77 = (var76 / 255.0F);
        int var79 = (var48 & 255);
        float var80 = ((float) var79);
        float var81 = (var80 / 255.0F);
        WorldFx.begin();
        List var83 = this.entriesList;
        Iterator var84 = var83.iterator();
        while (true) {
            boolean var86 = var84.hasNext();
            if (!var86) break;
            Object var112 = var84.next();
            JumpCircleEntry var113 = ((JumpCircleEntry) var112);
            long var273 = var113.time();
            long var222 = (var59 - var273);
            double var224 = ((double) var222);
            double var226 = (var224 / 1.0E9);
            double var228 = (var226 / var41);
            double var116 = Math.min(1.0, var228);
            float var118 = ((float) var116);
            Vec3 var120 = var113.origin();
            double var121 = var120.x;
            double var231 = var67.x;
            double var123 = (var121 - var231);
            float var125 = ((float) var123);
            Vec3 var127 = var113.origin();
            double var130 = (var127.y + 0.02);
            double var236 = var67.y;
            float var134 = ((float) (var130 - var236));
            double var137 = var113.origin().z;
            double var239 = var67.z;
            float var141 = ((float) (var137 - var239));
            float var144 = (var44 * 1.25F);
            float var146 = (var125 - var144);
            float var242 = (var141 - var144);
            float var312 = var113.seed();
            WorldFx.vertex(var146, var134, var242, -1.25F, -1.25F, var72, var77, var81, 1.0F, var118, var312);
            float var148 = (var125 + var144);
            float var244 = (var141 - var144);
            float var314 = var113.seed();
            WorldFx.vertex(var148, var134, var244, 1.25F, -1.25F, var72, var77, var81, 1.0F, var118, var314);
            float var150 = (var125 + var144);
            float var246 = (var141 + var144);
            float var316 = var113.seed();
            WorldFx.vertex(var150, var134, var246, 1.25F, 1.25F, var72, var77, var81, 1.0F, var118, var316);
            float var152 = (var125 - var144);
            float var248 = (var141 - var144);
            float var318 = var113.seed();
            WorldFx.vertex(var152, var134, var248, -1.25F, -1.25F, var72, var77, var81, 1.0F, var118, var318);
            float var154 = (var125 + var144);
            float var250 = (var141 + var144);
            float var320 = var113.seed();
            WorldFx.vertex(var154, var134, var250, 1.25F, 1.25F, var72, var77, var81, 1.0F, var118, var320);
            float var156 = (var125 - var144);
            float var252 = (var141 + var144);
            float var322 = var113.seed();
            WorldFx.vertex(var156, var134, var252, -1.25F, 1.25F, var72, var77, var81, 1.0F, var118, var322);
        }
        double var89 = ((double) var59);
        double var91 = (var89 / 1.0E9);
        double var93 = (var91 % 3600.0);
        float var95 = ((float) var93);
        int var97 = (var52 >> 16);
        int var98 = (var97 & 255);
        float var99 = ((float) var98);
        float var100 = (var99 / 255.0F);
        int var102 = (var52 >> 8);
        int var103 = (var102 & 255);
        float var104 = ((float) var103);
        float var105 = (var104 / 255.0F);
        int var107 = (var52 & 255);
        float var108 = ((float) var107);
        float var109 = (var108 / 255.0F);
        Consumer var219 = (p0) -> JumpEffect.lambda$a(var95, var58, var100, var105, var109, p0);
        WorldFx.flush("jumpring", var56, var219);
    }

    private void applyStep(Minecraft arg0, Player arg1) {
        int var25 = 0;
        ParticleOptions var32 = this.evaluate();
        if (var32 == null) {
            return;
        }
        Float var78 = 0.6F;
        float var37 = ((Float) this.getSetting("Radius", var78));
        Integer var80 = 16;
        int var41 = ((Integer) this.getSetting("Points", var80));
        double var43 = arg1.getX();
        double var48 = (arg1.getY() + 0.05);
        double var51 = arg1.getZ();
        var25 = 0;
        while (true) {
            if (var25 >= var41) break;
            double var84 = ((double) var41);
            double var57 = (6.283185307179586 / var84);
            double var87 = ((double) var25);
            double var59 = (var57 * var87);
            double var63 = Math.cos(var59);
            double var90 = ((double) var37);
            double var65 = (var63 * var90);
            double var69 = Math.sin(var59);
            double var93 = ((double) var37);
            double var71 = (var69 * var93);
            ClientLevel var74 = arg0.level;
            double var97 = (var43 + var65);
            double var101 = (var51 + var71);
            var74.addParticle(var32, var97, var48, var101, 0.0, 0.02, 0.0);
            var25 = (var25 + 1);
        }
    }

    private ParticleOptions evaluate() {
        SimpleParticleType var17 = null;
        int var6 = 0;
        String var8 = ((String) this.getSetting("Particle", "Cloud"));
        var6 = -1;
        switch (var8.hashCode()) {
            case -2094112058 -> {}
            case -1114675514 -> {}
            case 2108922 -> {}
            case 55838914 -> {}
            case 67960595 -> {}
            default -> {}
        }
        if (var8.equals("Flame")) {
            var6 = 2;
            if (var8.equals("Soul Flame")) {
                var6 = 3;
                if (var8.equals("End Rod")) {
                    var6 = 0;
                    if (var8.equals("Crit")) {
                        var6 = 1;
                        if (var8.equals("Happy Villager")) {
                            var6 = 4;
                        }
                        switch (var6) {
                            case 0 -> {}
                            case 1 -> {}
                            case 2 -> {}
                            case 3 -> {}
                            case 4 -> {}
                            default -> {}
                        }
                        var17 = ParticleTypes.SOUL_FIRE_FLAME;
                        var17 = ParticleTypes.FLAME;
                        var17 = ParticleTypes.CRIT;
                        var17 = ParticleTypes.HAPPY_VILLAGER;
                        var17 = ParticleTypes.END_ROD;
                        var17 = ParticleTypes.CLOUD;
                        return var17;
                    }
                }
            }
        }
    }

    static {
        String[] var3 = new String[7];
        var3[0] = "Ripple";
        var3[1] = "Shockwave";
        var3[2] = "Rune";
        var3[3] = "Bloom";
        var3[4] = "Corona";
        var3[5] = "Vortex";
        var3[6] = "Spectrum";
        fieldVal = var3;
    }

}
