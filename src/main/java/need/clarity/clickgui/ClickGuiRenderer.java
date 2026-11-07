package need.clarity.clickgui;

import com.mojang.blaze3d.opengl.GlTexture;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.FilterMode;
import com.mojang.blaze3d.textures.GpuSampler;
import com.mojang.blaze3d.textures.GpuTexture;
import com.mojang.blaze3d.textures.GpuTextureView;
import com.mojang.blaze3d.textures.TextureFormat;
import java.nio.ByteBuffer;
import need.clarity.nvg.BackdropBlur;
import need.clarity.nvg.ClarityFonts;
import need.clarity.render.RawFramebufferUtil;
import need.clarity.render.Shader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.resources.Identifier;
import org.lwjgl.nanovg.NVGLUFramebuffer;
import org.lwjgl.nanovg.NanoVG;
import org.lwjgl.nanovg.NanoVGGL3;
import org.lwjgl.opengl.GL33C;

public final class ClickGuiRenderer {
    private static final Identifier OVERLAY_ID;
    private static long vg;
    private static NvgCanvas canvas;
    private static NVGLUFramebuffer fb;
    private static NVGLUFramebuffer snapFb;
    private static int fbW;
    private static int fbH;
    private static Shader bgShader;
    private static boolean failed;
    private static boolean bgFailed;
    private static final long START;
    private static int bgVao;
    private static int bgVbo;
    private static int sceneCopyTex;
    private static int sceneCopyFbo;
    private static int sceneReadFbo;
    private static int sceneW;
    private static int sceneH;
    private static boolean sceneValid;

    private ClickGuiRenderer() {
    }

    public static boolean hasFailed() {
        return failed;
    }

    public static void resetScene() {
        sceneValid = false;
    }

    public static void captureScene(ClickGui arg0) {
        RawGlState var3 = null;
        int var4 = 0;
        int var5 = 0;
        int var6 = 0;
        int var7 = 0;
        if (failed) {
            return;
        }
        RenderTarget var2 = Minecraft.getInstance().getMainRenderTarget();
        if (var2 != null) {
            if (var2.width > 0) {
                if (var2.height <= 0) {
                }
            }
            return;
            if (!arg0.wantsScene()) {
                sceneValid = false;
                return;
            }
            try {
                var3 = RawGlState.capture();
                try {
                    var4 = RawFramebufferUtil.getColorTextureId(var2);
                    if (arg0.blur() <= 0) {
                        var5 = var4;
                        var6 = var2.width;
                        var7 = var2.height;
                    } else {
                        var5 = BackdropBlur.capture(var4, var2.width, var2.height, arg0.blur());
                        var6 = BackdropBlur.width();
                        var7 = BackdropBlur.height();
                    }
                    if (var5 == 0) {
                        sceneValid = false;
                    }
                } catch (Throwable ignored) {
                }
                if (var3 != null) {
                    var3.close();
                }
                return;
                try {
                    try {
                        if (!(ensureSceneCopy(var6, var7))) {
                            sceneValid = false;
                        }
                    } catch (Throwable ignored) {
                    }
                } catch (Throwable ignored) {
                }
                if (var3 != null) {
                    var3.close();
                }
                return;
                try {
                    try {
                        GL33C.glBindFramebuffer(36008, sceneReadFbo);
                        GL33C.glFramebufferTexture2D(36008, 36064, 3553, var5, 0);
                        GL33C.glBindFramebuffer(36009, sceneCopyFbo);
                        GL33C.glDisable(3089);
                        GL33C.glBlitFramebuffer(0, 0, var6, var7, 0, 0, var6, var7, 16384, 9728);
                        sceneValid = true;
                    } catch (Throwable ignored) {
                    }
                } catch (Throwable ignored) {
                }
                if (var3 != null) {
                    var3.close();
                    if (var3 != null) {
                        try {
                            var3.close();
                        } catch (Throwable ignored) {
                        }
                        var4.addSuppressed(var5);
                    }
                    throw var4;
                }
                sceneValid = false;
                return;
            } catch (Throwable ignored) {
            }
        }
    }

    private static boolean ensureSceneCopy(int arg0, int arg1) {
        if (sceneCopyFbo != 0) {
            if (sceneW == arg0) {
                if (sceneH == arg1) {
                    return true;
                }
                if (sceneCopyTex == 0) {
                    sceneCopyTex = GL33C.glGenTextures();
                }
                if (sceneCopyFbo == 0) {
                    sceneCopyFbo = GL33C.glGenFramebuffers();
                }
                if (sceneReadFbo == 0) {
                    sceneReadFbo = GL33C.glGenFramebuffers();
                }
                GL33C.glActiveTexture(33984);
                GL33C.glBindBuffer(35052, 0);
                GL33C.glBindTexture(3553, sceneCopyTex);
                GL33C.glTexImage2D(3553, 0, 32856, arg0, arg1, 0, 6408, 5121, ((ByteBuffer) null));
                GL33C.glTexParameteri(3553, 10241, 9729);
                GL33C.glTexParameteri(3553, 10240, 9729);
                GL33C.glTexParameteri(3553, 10242, 33071);
                GL33C.glTexParameteri(3553, 10243, 33071);
                GL33C.glTexParameteri(3553, 33085, 0);
                GL33C.glBindFramebuffer(36160, sceneCopyFbo);
                GL33C.glFramebufferTexture2D(36160, 36064, 3553, sceneCopyTex, 0);
                if (GL33C.glCheckFramebufferStatus(36160) != 36053) {
                    sceneH = 0;
                    sceneW = 0;
                    return false;
                }
                sceneW = arg0;
                sceneH = arg1;
                return true;
            }
        }
    }

    public static float designScale(ClickGui arg0) {
        Minecraft var1 = Minecraft.getInstance();
        float var2 = ((float) Math.max(1, var1.getWindow().getHeight()));
        return (Math.max(0.55F, Math.min(2.4F, (var2 / 900.0F))) * Math.max(0.5F, Math.min(1.6F, arg0.scale())));
    }

    public static void render(GuiGraphicsExtractor arg0, ClickGui arg1) {
        RawGlState var5 = null;
        int var6 = 0;
        float var7 = 0.0F;
        int var8 = 0;
        if (failed) {
            return;
        }
        Minecraft var2 = Minecraft.getInstance();
        int var3 = var2.getWindow().getWidth();
        int var4 = var2.getWindow().getHeight();
        if (var3 > 0) {
            if (var4 <= 0) {
            }
        }
        return;
        try {
            var5 = RawGlState.capture();
            try {
                ensure(var2, var3, var4);
                if (sceneValid) {
                } else {
                }
                var6 = 0;
                canvas.setGlassSource(var6, sceneW, sceneH);
                canvas.setCrisp(arg1.crispText());
                var7 = designScale(arg1);
                if (arg1.snapshotRequest() != 0) {
                    var8 = 0;
                    try {
                        NanoVGGL3.nvgluBindFramebuffer(vg, snapFb);
                        GL33C.glViewport(0, 0, fbW, fbH);
                        GL33C.glDisable(3089);
                        GL33C.glColorMask(true, true, true, true);
                        GL33C.glStencilMask(255);
                        GL33C.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
                        GL33C.glClearStencil(0);
                        GL33C.glClear(17408);
                        NanoVG.nvgBeginFrame(vg, ((float) fbW), ((float) fbH), 1.0F);
                        canvas.begin(fbW, fbH);
                        NanoVG.nvgSave(vg);
                        NanoVG.nvgScale(vg, var7, var7);
                        arg1.renderSnapshot(canvas, (((float) var3) / var7), (((float) var4) / var7));
                        NanoVG.nvgRestore(vg);
                        NanoVG.nvgEndFrame(vg);
                        canvas.setSnapshot(snapFb.texture(), fbW, fbH);
                        var8 = 1;
                    } catch (Throwable ignored) {
                    }
                    Object var9 = sceneCopyTex;
                    arg1.snapshotTaken(var8);
                }
                NanoVGGL3.nvgluBindFramebuffer(vg, fb);
                GL33C.glViewport(0, 0, fbW, fbH);
                GL33C.glDisable(3089);
                GL33C.glColorMask(true, true, true, true);
                GL33C.glStencilMask(255);
                GL33C.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
                GL33C.glClearStencil(0);
                GL33C.glClear(17408);
                GL33C.glDisable(2929);
                GL33C.glDisable(2884);
                GL33C.glBindBuffer(35052, 0);
                GL33C.glPixelStorei(3317, 1);
                GL33C.glPixelStorei(3314, 0);
                GL33C.glPixelStorei(3316, 0);
                GL33C.glPixelStorei(3315, 0);
                var8 = 0;
                while (true) {
                    if (var8 >= 4) break;
                    GL33C.glBindSampler(var8, 0);
                    var8++;
                }
                GL33C.glActiveTexture(33984);
                if (arg1.backgroundOn()) {
                    drawBackground(arg1, var6, var3, var4);
                }
                NanoVG.nvgBeginFrame(vg, ((float) fbW), ((float) fbH), 1.0F);
                canvas.begin(fbW, fbH);
                NanoVG.nvgSave(vg);
                NanoVG.nvgScale(vg, var7, var7);
                arg1.render(canvas, (((float) var3) / var7), (((float) var4) / var7), System.nanoTime());
                NanoVG.nvgRestore(vg);
                NanoVG.nvgEndFrame(vg);
            } catch (Throwable ignored) {
            }
            if (var5 != null) {
                var5.close();
                if (var5 != null) {
                    try {
                        var5.close();
                    } catch (Throwable ignored) {
                    }
                    var6.addSuppressed(var7);
                }
                throw var6;
            }
            var5 = ((float) var2.getWindow().getGuiScale());
            arg0.pose().pushMatrix();
            arg0.pose().scale((1.0F / var5), (1.0F / var5));
            arg0.blit(RenderPipelines.GUI_TEXTURED_PREMULTIPLIED_ALPHA, OVERLAY_ID, 0, 0, 0.0F, ((float) fbH), fbW, fbH, fbW, -(fbH), fbW, fbH);
            arg0.pose().popMatrix();
            return;
        } catch (Throwable ignored) {
        }
    }

    private static void drawBackground(ClickGui arg0, int arg1, int arg2, int arg3) {
        int var4 = 0;
        int var7 = 0;
        if (bgFailed) {
            return;
        }
        try {
            if (bgShader == null) {
                bgShader = new Shader("clickgui", "bg");
            }
            GL33C.glDisable(3042);
            GL33C.glActiveTexture(33984);
            GL33C.glBindTexture(3553, arg1);
            bgShader.bind();
            bgShader.setUniform1i("uScene", 0);
            bgShader.setUniform1f("uTime", ((float) ((((double) (System.nanoTime() - START)) / 1.0E9) % 3600.0)));
            bgShader.setUniform1f("uOpen", arg0.openProgress());
            bgShader.setUniform1f("uAspect", (((float) arg2) / ((float) arg3)));
            bgShader.setUniform1f("uDim", arg0.backgroundDim());
            if (arg1 != 0) {
                bgShader.setUniform1i("uHasScene", (!arg0.blurWindowOnly()));
                var4 = arg0.bgAccent();
                int var5 = arg0.bgAccent2();
                bgShader.setUniform3f("uAccent", (((float) ((var4 >> 16) & 255)) / 255.0F), (((float) ((var4 >> 8) & 255)) / 255.0F), (((float) (var4 & 255)) / 255.0F));
                bgShader.setUniform3f("uAccent2", (((float) ((var5 >> 16) & 255)) / 255.0F), (((float) ((var5 >> 8) & 255)) / 255.0F), (((float) (var5 & 255)) / 255.0F));
                String var6 = arg0.background();
                var7 = -1;
                switch (var6.hashCode()) {
                    case -1965582497 -> {}
                    case 80066187 -> {}
                    case 83352698 -> {}
                    default -> {}
                }
                if (var6.equals("Nebula")) {
                    var7 = 0;
                    if (var6.equals("Waves")) {
                        var7 = 1;
                        if (var6.equals("Solid")) {
                            var7 = 2;
                        }
                        switch (var7) {
                            case 0 -> {}
                            case 1 -> {}
                            case 2 -> {}
                            default -> {}
                        }
                        2.setUniform1i(3, 0);
                        if (bgVao == 0) {
                            bgVao = GL33C.glGenVertexArrays();
                            bgVbo = GL33C.glGenBuffers();
                            GL33C.glBindVertexArray(bgVao);
                            GL33C.glBindBuffer(34962, bgVbo);
                            new float[9][0] = -1.0F;
                            new float[9][1] = -1.0F;
                            new float[9][2] = 0.0F;
                            new float[9][3] = 3.0F;
                            new float[9][4] = -1.0F;
                            new float[9][5] = 0.0F;
                            new float[9][6] = -1.0F;
                            new float[9][7] = 3.0F;
                            new float[9][8] = 0.0F;
                            GL33C.glBufferData(34962, new float[9], 35044);
                            GL33C.glEnableVertexAttribArray(0);
                            GL33C.glVertexAttribPointer(0, 3, 5126, false, 12, 0L);
                        }
                        GL33C.glBindVertexArray(bgVao);
                        GL33C.glDrawArrays(4, 0, 3);
                        GL33C.glBindVertexArray(0);
                        bgShader.unbind();
                        return;
                    }
                }
            }
        } catch (Exception ignored) {
        }
    }

    private static void ensure(Minecraft arg0, int arg1, int arg2) {
        if (vg == 0L) {
            vg = NanoVGGL3.nvgCreate(3);
            if (vg == 0L) {
                throw new IllegalStateException("nvgCreate failed");
            }
            ClarityFonts.loadInto(vg);
            canvas = new NvgCanvas(vg);
        }
        if (fb != null) {
            if (fbW == arg1) {
                if (fbH == arg2) {
                    return;
                }
                if (fb != null) {
                    NanoVGGL3.nvgluDeleteFramebuffer(vg, fb);
                    fb = null;
                }
                if (snapFb != null) {
                    NanoVGGL3.nvgluDeleteFramebuffer(vg, snapFb);
                    snapFb = null;
                }
                canvas.forgetImages();
                fb = NanoVGGL3.nvgluCreateFramebuffer(vg, arg1, arg2, 0);
                if (fb == null) {
                    throw new IllegalStateException("nvgluCreateFramebuffer failed");
                }
                snapFb = NanoVGGL3.nvgluCreateFramebuffer(vg, arg1, arg2, 0);
                if (snapFb == null) {
                    throw new IllegalStateException("nvgluCreateFramebuffer (snapshot) failed");
                }
                fbW = arg1;
                fbH = arg2;
                ClickGuiRenderer.BridgeGlTexture var3 = new ClickGuiRenderer.BridgeGlTexture(fb.texture(), fbW, fbH);
                GpuTextureView var4 = RenderSystem.getDevice().createTextureView(var3);
                GpuSampler var5 = RenderSystem.getSamplerCache().getClampToEdge(FilterMode.NEAREST);
                arg0.getTextureManager().register(OVERLAY_ID, new ClickGuiRenderer.BridgeTexture(var3, var4, var5));
                return;
            }
        }
    }

    static {
        OVERLAY_ID = Identifier.fromNamespaceAndPath("clarity", "clickgui-overlay");
        failed = false;
        bgFailed = false;
        START = System.nanoTime();
        bgVao = 0;
        bgVbo = 0;
        sceneCopyTex = 0;
        sceneCopyFbo = 0;
        sceneReadFbo = 0;
        sceneW = 0;
        sceneH = 0;
        sceneValid = false;
    }

    public final class BridgeGlTexture extends GlTexture {
        BridgeGlTexture(int arg0, int arg1, int arg2) {
            super(12, "clarity-clickgui-overlay", TextureFormat.RGBA8, arg1, arg2, 1, 1, arg0);
        }

        public void close() {
        }

    }

    public final class BridgeTexture extends AbstractTexture {
        BridgeTexture(GpuTexture arg0, GpuTextureView arg1, GpuSampler arg2) {
            super();
            this.texture = arg0;
            this.textureView = arg1;
            this.sampler = arg2;
        }

        public void close() {
        }

    }

}
