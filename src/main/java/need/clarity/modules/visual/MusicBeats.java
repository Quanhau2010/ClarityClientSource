package need.clarity.modules.visual;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Arrays;
import java.util.function.Consumer;
import need.clarity.WorldRenderEvent;
import need.clarity.audio.BeatAnalyzer;
import need.clarity.modules.ModuleBase;
import need.clarity.render.worldfx.WorldFx;
import need.clarity.util.ClarityRenderUtils;
import need.clarity.util.ThemeColors;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.phys.Vec3;

public class MusicBeats extends ModuleBase {
    private final WorldRenderEvent.Listener fieldVal;
    private final float[] floatBuffer;
    private final float[] floatBuffer1;
    private final float[] floatBuffer2;
    private double doubleValue;
    private long timestampMs;
    private boolean enabledState;

    public MusicBeats() {
        super("Music Beats", "Visual");
        WorldRenderEvent.Listener var16 = (p0, p1) -> this.renderWorldEffect(p0, p1);
        this.fieldVal = var16;
        float[] var18 = new float[32];
        this.floatBuffer = var18;
        float[] var20 = new float[32];
        this.floatBuffer1 = var20;
        float[] var22 = new float[4];
        this.floatBuffer2 = var22;
        this.doubleValue = 0.0;
        this.timestampMs = 0L;
        this.enabledState = false;
        if (this.isEnabled()) {
            WorldRenderEvent.register(this.fieldVal);
        }
    }

    public void onEnable() {
        WorldRenderEvent.register(this.fieldVal);
        this.enabledState = false;
    }

    public void onDisable() {
        WorldRenderEvent.unregister(this.fieldVal);
        BeatAnalyzer.stop();
    }

    public void onTick() {
        if (!this.isEnabled()) {
            return;
        }
        Float var13 = 1.0F;
        BeatAnalyzer.setSensitivity(((Float) this.getSetting("Sensitivity", var13)));
        BeatAnalyzer.start(((String) this.getSetting("Audio Device", "")));
    }

    private void renderWorldEffect(PoseStack arg0, float arg1) {
        float var29 = 0.0F;
        int var62 = 0;
        double var74 = 0.0;
        int var338 = 0;
        if (!this.isEnabled()) {
            return;
        }
        Minecraft var53 = Minecraft.getInstance();
        if (var53 != null) {
            if (var53.player == null) {
            }
        }
        return;
        if (var53.options.getCameraType().isFirstPerson()) {
            if (!Freecam.isEligible()) {
                var62 = 1;
            }
        } else {
            var62 = 0;
        }
        if (var62 != 0) {
            Boolean var207 = false;
            if (((Boolean) this.getSetting("Hide In First Person", var207))) {
                return;
            }
        }
        long var68 = System.nanoTime();
        if (this.timestampMs == 0L) {
            var74 = 0.0;
        } else {
            long var325 = this.timestampMs;
            double var277 = (((double) (var68 - var325)) / 1.0E9);
            var74 = Math.min(0.1, var277);
        }
        double var27 = var74;
        this.timestampMs = var68;
        double var175 = ((this.doubleValue + var27) % 3600.0);
        this.doubleValue = var175;
        if (BeatAnalyzer.hasAudio()) {
            float[] var80 = this.floatBuffer;
            float[] var178 = this.floatBuffer1;
            BeatAnalyzer.copyBands(var80, var178);
            BeatAnalyzer.beatAges(this.floatBuffer2, var68);
            var29 = BeatAnalyzer.bass();
        } else {
            var338 = 0;
            while (true) {
                int var155 = var338;
                int var202 = this.floatBuffer.length;
                if (var155 >= var202) break;
                float[] var163 = this.floatBuffer;
                int var205 = var338;
                double var317 = (this.doubleValue * 2.0);
                double var334 = (((double) var338) * 0.5);
                float var323 = ((float) Math.sin((var317 + var334)));
                float var287 = (0.06F * var323);
                float var267 = (0.08F + var287);
                var163[var205] = var267;
                float[] var165 = this.floatBuffer1;
                int var206 = var338;
                float[] var269 = this.floatBuffer;
                int var288 = var338;
                float var270 = var269[var288];
                var165[var206] = var270;
                var338 = (var338 + 1);
            }
            Arrays.fill(this.floatBuffer2, -1.0F);
            var29 = 0.1F;
            if (!this.enabledState) {
                this.enabledState = true;
            }
            LocalPlayer var85 = var53.player;
            Vec3 var86 = ClarityRenderUtils.getCameraPos();
            double var88 = var85.xOld;
            double var211 = var85.getX();
            double var289 = var85.xOld;
            double var213 = (var211 - var289);
            double var292 = ((double) arg1);
            double var215 = (var213 * var292);
            double var90 = (var88 + var215);
            double var93 = var85.yOld;
            double var218 = var85.getY();
            double var295 = var85.yOld;
            double var220 = (var218 - var295);
            double var298 = ((double) arg1);
            double var222 = (var220 * var298);
            double var95 = (var93 + var222);
            double var98 = var85.zOld;
            double var225 = var85.getZ();
            double var301 = var85.zOld;
            double var227 = (var225 - var301);
            double var304 = ((double) arg1);
            double var229 = (var227 * var304);
            double var100 = (var98 + var229);
            double var232 = var86.x;
            float var106 = ((float) (var90 - var232));
            double var235 = var86.y;
            float var111 = ((float) (var95 - var235));
            double var238 = var86.z;
            float var116 = ((float) (var100 - var238));
            Integer var307 = 64;
            int var243 = ((Integer) this.getSetting("Bars", var307));
            int var182 = Math.min(128, var243);
            int var119 = (Math.max(16, var182) & -2);
            Float var245 = 1.1F;
            float var123 = ((Float) this.getSetting("Radius", var245));
            Float var247 = 1.0F;
            float var127 = ((Float) this.getSetting("Bar Height", var247));
            Float var249 = 0.15F;
            float var131 = ((Float) this.getSetting("Spin", var249));
            int var135 = (ThemeColors.resolveRgb(this.getSetting("Color", "#4AD6FF"), 4904703, false) & 16777215);
            int var139 = (ThemeColors.resolveRgb(this.getSetting("Second Color", "#B45CFF"), 11820287, true) & 16777215);
            Object var194 = this.getSetting("Color Mode", "Gradient");
            boolean var141 = "Rainbow".equals(var194);
            Boolean var256 = true;
            boolean var145 = ((Boolean) this.getSetting("Ground Ring", var256));
            Float var309 = 1.0F;
            float var260 = ((Float) this.getSetting("Opacity", var309));
            float var197 = Math.min(1.0F, var260);
            float var147 = Math.max(0.05F, var197);
            float var151 = ((float) this.doubleValue);
            float var152 = var29;
            int var199 = (var119 + 1);
            Boolean var311 = false;
            boolean var265 = ((Boolean) this.getSetting("Through Walls", var311));
            Consumer var313 = (p0) -> this.lambda$a(var106, var111, var116, var123, var127, var151, var131, var119, var152, var135, var139, var141, var145, var147, p0);
            WorldFx.drawInstanced("beats", var199, var265, 0, var313);
            return;
        }
    }

}
