package need.clarity.nvg;

import com.mojang.blaze3d.opengl.GlTexture;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.systems.SamplerCache;
import com.mojang.blaze3d.textures.FilterMode;
import com.mojang.blaze3d.textures.GpuSampler;
import com.mojang.blaze3d.textures.GpuTexture;
import com.mojang.blaze3d.textures.GpuTextureView;
import com.mojang.blaze3d.textures.TextureFormat;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.List;
import need.clarity.api.Hooks;
import need.clarity.config.ClarityConfig;
import need.clarity.modules.ModuleBase;
import need.clarity.modules.ModuleRegistry;
import need.clarity.modules.visual.ArrayListModule;
import need.clarity.modules.visual.HudBlurHelper;
import need.clarity.modules.visual.ModuleSuffixFormatter;
import need.clarity.render.RawFramebufferUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.Identifier;
import org.lwjgl.nanovg.NVGLUFramebuffer;
import org.lwjgl.nanovg.NanoVG;
import org.lwjgl.nanovg.NanoVGGL3;
import org.lwjgl.opengl.GL33C;
import org.lwjgl.system.MemoryStack;

public final class ArrayListRenderer {
    private static final ArrayListRenderer INSTANCE;
    private static final Identifier OVERLAY_ID;
    private long vg;
    private boolean disabled;
    private NVGLUFramebuffer fb;
    private int fbW;
    private int fbH;
    private GpuTextureView bridgeView;
    private boolean fbValid;
    private int lastEnabledHash;
    private boolean animationsSettled;

    private ArrayListRenderer() {
    }

    public static void render(GuiGraphicsExtractor arg0, float arg1, float arg2, float arg3, float arg4) {
        int var133 = 0;
        boolean var162 = false;
        int var44 = 0;
        if (INSTANCE.disabled) {
            return;
        }
        Minecraft var34 = Minecraft.getInstance();
        if (var34 != null) {
            HudLayout.validate();
        }
        if (var34 != null) {
            if (var34.options.hideGui) {
            }
        }
        return;
        ArrayListModule var40 = ArrayListModule.INSTANCE;
        if (var40 != null) {
            if (var40.isEnabled()) {
                var44 = 1;
            }
        } else {
            var44 = 0;
        }
        int var20 = var44;
        boolean var45 = HudElements.enabled();
        if (var20 == 0) {
            if (!var45) {
                INSTANCE.fbValid = false;
                return;
            }
        }
        try {
            INSTANCE.ensureContext();
            int var50 = HudBlurHelper.computeColorOrIndex();
            float var167 = ((float) var50);
            int var136 = ((int) (arg3 * var167));
            int var52 = Math.max(1, var136);
            float var169 = ((float) var50);
            int var139 = ((int) (arg4 * var169));
            int var54 = Math.max(1, var139);
            INSTANCE.ensureFramebuffer(var52, var54, var34);
            int var56 = HudElements.backdropStrength();
            if (INSTANCE.fbValid) {
                if (INSTANCE.animationsSettled) {
                    if (!var45) {
                        if (var56 <= 0) {
                            ArrayListRenderer var63 = INSTANCE;
                            float var172 = ((float) var52);
                            float var190 = ((float) var54);
                            if (var63.hasStateChanged(var40, var172, var190)) {
                            }
                        }
                        var133 = 1;
                        var133 = 0;
                        if (var133 != 0) {
                            ArrayListRenderer.GlState var66 = ArrayListRenderer.GlState.capture();
                            try {
                                if (var56 > 0) {
                                    RenderTarget var69 = var34.getMainRenderTarget();
                                    int var71 = RawFramebufferUtil.getColorTextureId(var69);
                                    int var143 = var69.width;
                                    int var174 = var69.height;
                                    int var72 = BackdropBlur.capture(var71, var143, var174, var56);
                                } else {
                                    BackdropBlur.invalidate();
                                }
                                long var74 = INSTANCE.vg;
                                NVGLUFramebuffer var176 = INSTANCE.fb;
                                NanoVGGL3.nvgluBindFramebuffer(var74, var176);
                                int var178 = INSTANCE.fbW;
                                int var193 = INSTANCE.fbH;
                                GL33C.glViewport(0, 0, var178, var193);
                                GL33C.glDisable(3089);
                                GL33C.glColorMask(true, true, true, true);
                                GL33C.glStencilMask(255);
                                GL33C.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
                                GL33C.glClearStencil(0);
                                GL33C.glClear(17408);
                                GL33C.glDisable(2929);
                                GL33C.glBindBuffer(35052, 0);
                                GL33C.glBindBuffer(35051, 0);
                                GL33C.glPixelStorei(3317, 1);
                                GL33C.glPixelStorei(3314, 0);
                                GL33C.glPixelStorei(3316, 0);
                                GL33C.glPixelStorei(3315, 0);
                                GL33C.glBindSampler(0, 0);
                                float var92 = HudBlurHelper.computeFloatMetric(arg3, arg4);
                                float var156 = ((float) var50);
                                float var94 = (var92 * var156);
                                float var96 = (arg3 / var92);
                                float var98 = (arg4 / var92);
                                long var100 = INSTANCE.vg;
                                float var183 = ((float) INSTANCE.fbW);
                                float var198 = ((float) INSTANCE.fbH);
                                NanoVG.nvgBeginFrame(var100, var183, var198, 1.0F);
                                NanoVG.nvgSave(INSTANCE.vg);
                                NanoVG.nvgScale(INSTANCE.vg, var94, var94);
                                HudLayout.beginFrame(var96, var98);
                                if (var20 != 0) {
                                    ArrayListModule.applyStep(INSTANCE.vg, var96, var98);
                                }
                                if (var45) {
                                    HudElements.draw(INSTANCE.vg, var96, var98);
                                }
                                NanoVG.nvgRestore(INSTANCE.vg);
                                NanoVG.nvgEndFrame(INSTANCE.vg);
                            } catch (Throwable ignored) {
                            }
                            var66.restore();
                            var66.restore();
                            throw var130;
                        }
                    }
                }
            }
        } catch (Throwable ignored) {
        }
        try {
            INSTANCE.fbValid = true;
            ArrayListRenderer var125 = INSTANCE;
            if (var20 != 0) {
                var162 = INSTANCE.checkAnimationsSettled(var40);
            } else {
                var162 = true;
            }
            var125.animationsSettled = var162;
            ArrayListRenderer var126 = INSTANCE;
            int var163 = buildEnabledHash();
            var126.lastEnabledHash = var163;
            RenderPipeline var164 = RenderPipelines.GUI_TEXTURED_PREMULTIPLIED_ALPHA;
            Identifier var187 = OVERLAY_ID;
            float var205 = ((float) INSTANCE.fbH);
            int var206 = ((int) arg1);
            int var207 = ((int) arg2);
            int var208 = INSTANCE.fbW;
            int var210 = -(INSTANCE.fbH);
            int var211 = INSTANCE.fbW;
            int var212 = INSTANCE.fbH;
            arg0.blit(var164, var187, 0, 0, 0.0F, var205, var206, var207, var208, var210, var211, var212);
        } catch (Throwable ignored) {
        }
        INSTANCE.disabled = true;
    }

    public static void dispose() {
        BackdropBlur.dispose();
        HudElements.onContextDestroyed();
        if (INSTANCE.vg != 0L) {
            if (INSTANCE.fb != null) {
                long var10 = INSTANCE.vg;
                NVGLUFramebuffer var22 = INSTANCE.fb;
                NanoVGGL3.nvgluDeleteFramebuffer(var10, var22);
                INSTANCE.fb = null;
            }
            ClarityFonts.free();
            ClarityImages.clear();
            NanoVGGL3.nvgDelete(INSTANCE.vg);
            INSTANCE.vg = 0L;
        }
        INSTANCE.fbValid = false;
    }

    private boolean hasStateChanged(ArrayListModule arg0, float arg1, float arg2) {
        int var7 = ((int) arg1);
        int var15 = this.fbW;
        if (var7 == var15) {
            int var9 = ((int) arg2);
            int var17 = this.fbH;
            if (var9 != var17) {
            }
        }
        return true;
        int var11 = buildEnabledHash();
        int var19 = this.lastEnabledHash;
        if (var11 != var19) {
            return true;
        }
        return false;
    }

    private static int buildEnabledHash() {
        int var5 = 0;
        int var6 = 0;
        int var7 = 0;
        List var0 = ModuleRegistry.all();
        var5 = 0;
        var6 = 0;
        var7 = 0;
        while (true) {
            int var13 = var7;
            int var32 = var0.size();
            if (var13 >= var32) break;
            int var36 = var7;
            ModuleBase var19 = ((ModuleBase) var0.get(var36));
            if (var19.isEnabled()) {
                if (!(var19 instanceof ArrayListModule)) {
                    int var24 = var5;
                    int var40 = (var19.getName().hashCode() * 31);
                    int var47 = var6;
                    int var41 = (var40 + var47);
                    var5 = (var24 ^ var41);
                    String var28 = ModuleSuffixFormatter.resolveLabel(var19.getName());
                    if (var28 != null) {
                        int var30 = var5;
                        int var44 = (var28.hashCode() * 17);
                        int var50 = (var6 * 7);
                        int var45 = (var44 + var50);
                        var5 = (var30 ^ var45);
                    }
                    var6 = (var6 + 1);
                }
                var7 = (var7 + 1);
            }
        }
        int var14 = var5;
        int var35 = (ClarityConfig.valuesHash("ArrayList") * 131);
        return (var14 ^ var35);
    }

    private boolean checkAnimationsSettled(ArrayListModule arg0) {
        return ArrayListModule.isEligible();
    }

    private void ensureContext() {
        if (this.vg != 0L) {
            return;
        }
        long var17 = NanoVGGL3.nvgCreate(3);
        this.vg = var17;
        if (this.vg == 0L) {
            IllegalStateException var16 = new IllegalStateException("[clarity/ArrayList] nvgCreate failed");
            throw var16;
        }
        ClarityFonts.loadAll(this.vg);
    }

    private void ensureFramebuffer(int arg0, int arg1, Minecraft arg2) {
        if (this.fb != null) {
            if (this.fbW == arg0) {
                if (this.fbH == arg1) {
                    return;
                }
            }
        }
        if (this.fb != null) {
            long var19 = this.vg;
            NVGLUFramebuffer var51 = this.fb;
            NanoVGGL3.nvgluDeleteFramebuffer(var19, var51);
            this.fb = null;
        }
        NVGLUFramebuffer var44 = NanoVGGL3.nvgluCreateFramebuffer(this.vg, arg0, arg1, 0);
        this.fb = var44;
        if (this.fb == null) {
            String var56 = "[clarity/ArrayList] nvgluCreateFramebuffer failed at " + (arg0) + "\u00d7" + (arg1);
            IllegalStateException var38 = new IllegalStateException(var56);
            throw var38;
        }
        this.fbW = arg0;
        this.fbH = arg1;
        this.fbValid = false;
        int var29 = GL33C.glGetInteger(36006);
        long var31 = this.vg;
        NVGLUFramebuffer var53 = this.fb;
        NanoVGGL3.nvgluBindFramebuffer(var31, var53);
        GL33C.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
        GL33C.glClearStencil(0);
        GL33C.glClear(17408);
        GL33C.glBindFramebuffer(36160, var29);
        this.registerBridge(arg2);
    }

    private void registerBridge(Minecraft arg0) {
        int var11 = this.fb.texture();
        int var26 = this.fbW;
        int var29 = this.fbH;
        ArrayListRenderer.BridgeGlTexture var12 = new ArrayListRenderer.BridgeGlTexture(var11, var26, var29);
        GpuTextureView var20 = RenderSystem.getDevice().createTextureView(var12);
        this.bridgeView = var20;
        SamplerCache var14 = RenderSystem.getSamplerCache();
        FilterMode var21 = FilterMode.LINEAR;
        GpuSampler var15 = var14.getClampToEdge(var21);
        GpuTextureView var28 = this.bridgeView;
        ArrayListRenderer.BridgeTexture var16 = new ArrayListRenderer.BridgeTexture(var12, var28, var15);
        TextureManager var18 = arg0.getTextureManager();
        Identifier var22 = OVERLAY_ID;
        var18.register(var22, var16);
    }

    static {
        Hooks.Act1 var1 = (p0) -> ArrayListRenderer.lambda$static$0(p0);
        Hooks.act1("ArrayListRenderer.render", var1);
        ArrayListRenderer var2 = new ArrayListRenderer();
        INSTANCE = var2;
        OVERLAY_ID = Identifier.fromNamespaceAndPath("clarity", "arraylist-overlay");
    }

    public final class BridgeGlTexture extends GlTexture {
        BridgeGlTexture(int arg0, int arg1, int arg2) {
            TextureFormat var7 = TextureFormat.RGBA8;
            super(12, "clarity-arraylist-overlay", var7, arg1, arg2, 1, 1, arg0);
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

    public final class GlState {
        int program;
        int vao;
        int arrayBuffer;
        int uniformBuffer;
        int activeTexture;
        int texture2d;
        int framebuffer;
        int pixelUnpackBuffer;
        int pixelPackBuffer;
        int sampler0;
        int unpackAlignment;
        int unpackRowLength;
        int unpackSkipPixels;
        int unpackSkipRows;
        int vpX;
        int vpY;
        int vpW;
        int vpH;
        boolean blend;
        boolean depth;
        boolean cull;
        boolean stencil;
        boolean scissor;
        int blendSrcRgb;
        int blendDstRgb;
        int blendSrcA;
        int blendDstA;
        int blendEqRgb;
        int blendEqA;
        int cullMode;
        int frontFace;
        int stencilWriteMask;
        int stencilFunc;
        int stencilRef;
        int stencilValueMask;
        int stencilFail;
        int stencilZFail;
        int stencilZPass;
        final byte[] colorMask;

        private GlState() {
            byte[] var4 = new byte[4];
            this.colorMask = var4;
        }

        static ArrayListRenderer.GlState capture() {
            ArrayListRenderer.GlState var0 = new ArrayListRenderer.GlState();
            int var75 = GL33C.glGetInteger(35725);
            var0.program = var75;
            int var77 = GL33C.glGetInteger(34229);
            var0.vao = var77;
            int var79 = GL33C.glGetInteger(34964);
            var0.arrayBuffer = var79;
            int var81 = GL33C.glGetInteger(35368);
            var0.uniformBuffer = var81;
            int var83 = GL33C.glGetInteger(34016);
            var0.activeTexture = var83;
            int var85 = GL33C.glGetInteger(32873);
            var0.texture2d = var85;
            int var87 = GL33C.glGetInteger(36006);
            var0.framebuffer = var87;
            int var89 = GL33C.glGetInteger(35055);
            var0.pixelUnpackBuffer = var89;
            int var91 = GL33C.glGetInteger(35053);
            var0.pixelPackBuffer = var91;
            int var93 = GL33C.glGetInteger(3317);
            var0.unpackAlignment = var93;
            int var95 = GL33C.glGetInteger(3314);
            var0.unpackRowLength = var95;
            int var97 = GL33C.glGetInteger(3316);
            var0.unpackSkipPixels = var97;
            int var99 = GL33C.glGetInteger(3315);
            var0.unpackSkipRows = var99;
            GL33C.glActiveTexture(33984);
            int var101 = GL33C.glGetInteger(35097);
            var0.sampler0 = var101;
            GL33C.glActiveTexture(var0.activeTexture);
            MemoryStack var23 = MemoryStack.stackPush();
            try {
                IntBuffer var25 = var23.mallocInt(4);
                GL33C.glGetIntegerv(2978, var25);
                int var105 = var25.get(0);
                var0.vpX = var105;
                int var107 = var25.get(1);
                var0.vpY = var107;
                int var109 = var25.get(2);
                var0.vpW = var109;
                int var111 = var25.get(3);
                var0.vpH = var111;
            } catch (Throwable ignored) {
            }
            if (var23 != null) {
                var23.close();
                if (var23 != null) {
                    try {
                        var23.close();
                    } catch (Throwable ignored) {
                    }
                    var31.addSuppressed(var35);
                }
                throw var31;
            }
            boolean var114 = GL33C.glIsEnabled(3042);
            var0.blend = var114;
            boolean var116 = GL33C.glIsEnabled(2929);
            var0.depth = var116;
            boolean var118 = GL33C.glIsEnabled(2884);
            var0.cull = var118;
            boolean var120 = GL33C.glIsEnabled(2960);
            var0.stencil = var120;
            boolean var122 = GL33C.glIsEnabled(3089);
            var0.scissor = var122;
            int var124 = GL33C.glGetInteger(32969);
            var0.blendSrcRgb = var124;
            int var126 = GL33C.glGetInteger(32968);
            var0.blendDstRgb = var126;
            int var128 = GL33C.glGetInteger(32971);
            var0.blendSrcA = var128;
            int var130 = GL33C.glGetInteger(32970);
            var0.blendDstA = var130;
            int var132 = GL33C.glGetInteger(32777);
            var0.blendEqRgb = var132;
            int var134 = GL33C.glGetInteger(34877);
            var0.blendEqA = var134;
            int var136 = GL33C.glGetInteger(2885);
            var0.cullMode = var136;
            int var138 = GL33C.glGetInteger(2886);
            var0.frontFace = var138;
            int var140 = GL33C.glGetInteger(2968);
            var0.stencilWriteMask = var140;
            int var142 = GL33C.glGetInteger(2962);
            var0.stencilFunc = var142;
            int var144 = GL33C.glGetInteger(2967);
            var0.stencilRef = var144;
            int var146 = GL33C.glGetInteger(2963);
            var0.stencilValueMask = var146;
            int var148 = GL33C.glGetInteger(2964);
            var0.stencilFail = var148;
            int var150 = GL33C.glGetInteger(2965);
            var0.stencilZFail = var150;
            int var152 = GL33C.glGetInteger(2966);
            var0.stencilZPass = var152;
            MemoryStack var59 = MemoryStack.stackPush();
            try {
                ByteBuffer var61 = var59.malloc(4);
                GL33C.glGetBooleanv(3107, var61);
                byte[] var156 = var0.colorMask;
                ByteBuffer var64 = var61.get(var156);
            } catch (Throwable ignored) {
            }
            if (var59 != null) {
                var59.close();
                if (var59 != null) {
                    try {
                        var59.close();
                    } catch (Throwable ignored) {
                    }
                    var65.addSuppressed(var69);
                }
                throw var65;
            }
            return var0;
        }

        void restore() {
            int var92 = 0;
            int var104 = 0;
            int var46 = 0;
            int var112 = 0;
            int var47 = this.framebuffer;
            GL33C.glBindFramebuffer(36160, var47);
            int var7 = this.vpX;
            int var49 = this.vpY;
            int var93 = this.vpW;
            int var105 = this.vpH;
            GL33C.glViewport(var7, var49, var93, var105);
            GL33C.glUseProgram(this.program);
            GL33C.glBindVertexArray(this.vao);
            int var51 = this.arrayBuffer;
            GL33C.glBindBuffer(34962, var51);
            int var53 = this.uniformBuffer;
            GL33C.glBindBuffer(35345, var53);
            GL33C.glActiveTexture(this.activeTexture);
            int var55 = this.texture2d;
            GL33C.glBindTexture(3553, var55);
            int var57 = this.pixelUnpackBuffer;
            GL33C.glBindBuffer(35052, var57);
            int var59 = this.pixelPackBuffer;
            GL33C.glBindBuffer(35051, var59);
            int var61 = this.unpackAlignment;
            GL33C.glPixelStorei(3317, var61);
            int var63 = this.unpackRowLength;
            GL33C.glPixelStorei(3314, var63);
            int var65 = this.unpackSkipPixels;
            GL33C.glPixelStorei(3316, var65);
            int var67 = this.unpackSkipRows;
            GL33C.glPixelStorei(3315, var67);
            int var69 = this.sampler0;
            GL33C.glBindSampler(0, var69);
            boolean var71 = this.blend;
            toggle(3042, var71);
            boolean var73 = this.depth;
            toggle(2929, var73);
            boolean var75 = this.cull;
            toggle(2884, var75);
            boolean var77 = this.stencil;
            toggle(2960, var77);
            boolean var79 = this.scissor;
            toggle(3089, var79);
            int var30 = this.blendSrcRgb;
            int var81 = this.blendDstRgb;
            int var95 = this.blendSrcA;
            int var107 = this.blendDstA;
            GL33C.glBlendFuncSeparate(var30, var81, var95, var107);
            int var32 = this.blendEqRgb;
            int var83 = this.blendEqA;
            GL33C.glBlendEquationSeparate(var32, var83);
            GL33C.glCullFace(this.cullMode);
            GL33C.glFrontFace(this.frontFace);
            GL33C.glStencilMask(this.stencilWriteMask);
            int var40 = this.stencilFunc;
            int var85 = this.stencilRef;
            int var97 = this.stencilValueMask;
            GL33C.glStencilFunc(var40, var85, var97);
            int var42 = this.stencilFail;
            int var87 = this.stencilZFail;
            int var99 = this.stencilZPass;
            GL33C.glStencilOp(var42, var87, var99);
            if (this.colorMask[0] != 0) {
                var46 = 1;
            } else {
                var46 = 0;
            }
            if (this.colorMask[1] != 0) {
                var92 = 1;
            } else {
                var92 = 0;
            }
            if (this.colorMask[2] != 0) {
                var104 = 1;
            } else {
                var104 = 0;
            }
            if (this.colorMask[3] != 0) {
                var112 = 1;
            } else {
                var112 = 0;
            }
            GL33C.glColorMask(var46, var92, var104, var112);
        }

        private static void toggle(int arg0, boolean arg1) {
            if (arg1) {
                GL33C.glEnable(arg0);
            } else {
                GL33C.glDisable(arg0);
            }
        }

    }

}
