package need.clarity.render;

import com.mojang.blaze3d.opengl.GlStateManager;
import com.mojang.blaze3d.opengl.GlTexture;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.FilterMode;
import com.mojang.blaze3d.textures.GpuSampler;
import com.mojang.blaze3d.textures.GpuTexture;
import com.mojang.blaze3d.textures.GpuTextureView;
import com.mojang.blaze3d.textures.TextureFormat;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import need.clarity.api.Hooks;
import need.clarity.modules.visual.CustomCrosshair;
import need.clarity.util.ThemeColors;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.world.phys.HitResult;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public final class CrosshairRenderer {
    private static final Identifier TEX_ID;
    private static int program;
    private static int vao;
    private static int vbo;
    private static int tex;
    private static int fbo;
    private static int size;
    private static boolean failed;
    private static final Map loc;
    private static float spread;
    private static float target;
    private static float spinAngle;
    private static long lastNanos;
    private static final long START;

    private CrosshairRenderer() {
    }

    public static boolean render(GuiGraphicsExtractor arg0) {
        CustomCrosshair var1 = CustomCrosshair.INSTANCE;
        if (var1 != null) {
            if (var1.isEnabled()) {
                if (failed) {
                }
            }
            return false;
            try {
            } catch (Throwable ignored) {
            }
            return draw(arg0, var1);
        }
    }

    private static boolean draw(GuiGraphicsExtractor arg0, CustomCrosshair arg1) {
        float var12 = 0.0F;
        double var13 = 0.0;
        int var21 = 0;
        int var22 = 0;
        int var28 = 0;
        Minecraft var2 = Minecraft.getInstance();
        LocalPlayer var3 = var2.player;
        if (var3 == null) {
            return true;
        }
        if (var2.options.getCameraType().isFirstPerson()) {
            if (var3.isSpectator()) {
            }
        }
        return true;
        long var4 = System.nanoTime();
        if (lastNanos == 0L) {
        } else {
        }
        float var6 = Math.min(0.1F, (((float) (var4 - lastNanos)) / 1.0E9F));
        lastNanos = var4;
        int var7 = var2.getWindow().getWidth();
        int var8 = var2.getWindow().getHeight();
        double var9 = ((double) var2.getWindow().getGuiScale());
        float var11 = Math.max(0.75F, (((float) var8) / 1080.0F));
        var12 = 0.0F;
        if (arg1.isDynamicSpread()) {
            var13 = (Math.hypot(var3.getDeltaMovement().x, var3.getDeltaMovement().z) * 20.0);
            if (var3.onGround()) {
            } else {
            }
            var12 = (0.0F + 2.5F);
        }
        spread = (spread + ((var12 - spread) * Math.min(1.0F, (var6 * 12.0F))));
        if (var2.hitResult != null) {
            var13 = (var2.hitResult.getType() == HitResult.Type.ENTITY);
            if (arg1.isTargetColor()) {
                if (var13 != 0) {
                } else {
                }
                target = (1.0F + ((0.0F - target) * Math.min(1.0F, (var6 * 14.0F))));
                spinAngle = ((spinAngle + ((arg1.getSpin() * 90.0F) * var6)) % 360.0F);
                if (arg1.isCooldownRing()) {
                } else {
                }
                float var14 = 1.0F;
                float var15 = arg1.computeFloatMetric(var4);
                float var16 = arg1.computeFloatMetric1(var4);
                float var17 = (((((arg1.getGap() + arg1.getSize()) + 22.0F) + (arg1.getGlow() * 8.0F)) + 4.0F) * var11);
                int var18 = ((int) Math.ceil((((double) (2.0F * var17)) / var9)));
                int var19 = ((int) Math.round((((double) var18) * var9)));
                if (!ensure(var19)) {
                    return false;
                }
                RawGlStateCapture var20 = RawGlStateCapture.capture();
                try {
                    GL30.glBindFramebuffer(36160, fbo);
                    GL11.glDrawBuffer(36064);
                    GlStateManager._viewport(0, 0, var19, var19);
                    GlStateManager._disableScissorTest();
                    GlStateManager._disableDepthTest();
                    GlStateManager._disableBlend();
                    GlStateManager._disableCull();
                    GL11.glColorMask(true, true, true, true);
                    GL11.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
                    GL11.glClear(16384);
                    GL20.glUseProgram(program);
                    var21 = var2.getWindow().getGuiScaledWidth();
                    var22 = var2.getWindow().getGuiScaledHeight();
                    int var23 = ((var21 - var18) / 2);
                    int var24 = ((var22 - var18) / 2);
                    float var25 = ((float) ((((double) var7) / 2.0) - ((((double) var23) * var9) + (((double) var19) / 2.0))));
                    float var26 = ((float) -(((((double) var8) / 2.0) - ((((double) var24) * var9) + (((double) var19) / 2.0)))));
                    u2("uRes", ((float) var19), ((float) var19));
                    u2("uOffset", var25, var26);
                    u1("uK", var11);
                    u1i("uStyle", styleIndex(arg1.getStyle()));
                    u1("uLen", arg1.getSize());
                    u1("uGap", arg1.getGap());
                    u1("uThick", arg1.getThickness());
                    u1("uDot", arg1.getDotSize());
                    u1("uRot", (arg1.getRotation() + spinAngle));
                    if (arg1.isOutline()) {
                    } else {
                    }
                    u1(arg1.getOutlineThickness(), 0.0F);
                    u1("uGlow", arg1.getGlow());
                    u1("uTime", ((float) ((((double) (var4 - START)) / 1.0E9) % 3600.0)));
                    u1("uSpread", spread);
                    String var27 = arg1.getColorMode();
                    var28 = -1;
                    switch (var27.hashCode()) {
                        case 154295120 -> {}
                        case 2017705622 -> {}
                        default -> {}
                    }
                    if (var27.equals("Gradient")) {
                        var28 = 0;
                        if (var27.equals("Chroma")) {
                            var28 = 1;
                        }
                        switch (var28) {
                            case 0 -> {}
                            case 1 -> {}
                            default -> {}
                        }
                        u1i(2, 0);
                        argb("uCol1", ThemeColors.resolveArgb(arg1.getColor(), -1, false));
                        argb("uCol2", ThemeColors.resolveArgb(arg1.getSecondColour(), -8745729, true));
                        GL20.glUniform3f(loc("uOutCol"), 0.02F, 0.02F, 0.03F);
                        rgb("uHitCol", arg1.getHitColor());
                        rgb("uTargetCol", arg1.getTargetColour());
                        if (ThemeColors.isThemeValue(arg1.getHitColor())) {
                            argb("uHitCol", ThemeColors.resolveArgb(arg1.getHitColor(), -50373, false));
                        }
                        if (ThemeColors.isThemeValue(arg1.getTargetColour())) {
                            argb("uTargetCol", ThemeColors.resolveArgb(arg1.getTargetColour(), -44976, true));
                        }
                        u1("uTarget", target);
                        u1("uHitAge", var15);
                        u1("uKillAge", var16);
                        u1i("uHitmarker", (arg1.isHitmarker()));
                        u1i("uExpand", (arg1.isHitExpand()));
                        u1i("uFlash", (arg1.isHitFlash()));
                        u1i("uShock", (arg1.isShockwave()));
                        u1("uCooldown", var14);
                        GL30.glBindVertexArray(vao);
                        GL11.glDrawArrays(4, 0, 3);
                        GL30.glBindVertexArray(0);
                        arg0.blit(RenderPipelines.GUI_TEXTURED_PREMULTIPLIED_ALPHA, TEX_ID, var23, var24, 0.0F, ((float) var19), var18, var18, var19, -(var19), var19, var19);
                    }
                } catch (Throwable ignored) {
                }
                if (var20 != null) {
                    var20.close();
                    var21 = 1;
                    if (var20 != null) {
                        try {
                            var20.close();
                        } catch (Throwable ignored) {
                        }
                        var22 = "uColorMode";
                        var21.addSuppressed(var22);
                    }
                    throw var21;
                }
                return true;
            }
        }
    }

    private static int styleIndex(String arg0) {
        int var2 = 0;
        var2 = -1;
        switch (arg0.hashCode()) {
            case -1887427045 -> {}
            case -1328042060 -> {}
            case -975259340 -> {}
            case 68905 -> {}
            case 29314283 -> {}
            case 1120512960 -> {}
            case 2018617584 -> {}
            default -> {}
        }
        if (arg0.equals("Dot")) {
            var2 = 0;
            if (arg0.equals("Circle")) {
                var2 = 1;
                if (arg0.equals("Cross + Dot")) {
                    var2 = 2;
                    if (arg0.equals("Segments")) {
                        var2 = 3;
                        if (arg0.equals("Chevron")) {
                            var2 = 4;
                            if (arg0.equals("Diamond")) {
                                var2 = 5;
                                if (arg0.equals("Brackets")) {
                                    var2 = 6;
                                }
                                switch (var2) {
                                    case 0 -> {}
                                    case 1 -> {}
                                    case 2 -> {}
                                    case 3 -> {}
                                    case 4 -> {}
                                    case 5 -> {}
                                    case 6 -> {}
                                    default -> {}
                                }
                                return 0;
                            }
                        }
                    }
                }
            }
        }
    }

    private static int loc(String arg0) {
        return ((Integer) loc.computeIfAbsent(arg0, (p0) -> CrosshairRenderer.lambda$loc$0(p0)));
    }

    private static void u1(String arg0, float arg1) {
        GL20.glUniform1f(loc(arg0), arg1);
    }

    private static void u1i(String arg0, int arg1) {
        GL20.glUniform1i(loc(arg0), arg1);
    }

    private static void u2(String arg0, float arg1, float arg2) {
        GL20.glUniform2f(loc(arg0), arg1, arg2);
    }

    private static void argb(String arg0, int arg1) {
        GL20.glUniform3f(loc(arg0), (((float) ((arg1 >> 16) & 255)) / 255.0F), (((float) ((arg1 >> 8) & 255)) / 255.0F), (((float) (arg1 & 255)) / 255.0F));
    }

    private static void rgb(String arg0, String arg1) {
        int var2 = 0;
        String var3 = null;
        var2 = 16777215;
        try {
            if (arg1.startsWith("#")) {
            } else {
            }
            var3 = arg1;
            if (var3.length() > 6) {
            } else {
            }
            var2 = ((int) Long.parseLong(var3, 16));
        } catch (Throwable ignored) {
        }
        var3 = var3.substring((var3.length() - 6));
        GL20.glUniform3f(loc(arg0), (((float) ((var2 >> 16) & 255)) / 255.0F), (((float) ((var2 >> 8) & 255)) / 255.0F), (((float) (var2 & 255)) / 255.0F));
    }

    private static boolean ensure(int arg0) {
        if (program == 0) {
            if (!build()) {
                return false;
            }
            if (tex != 0) {
                if (size == arg0) {
                    return true;
                }
                if (tex == 0) {
                    tex = GL11.glGenTextures();
                }
                GlStateManager._activeTexture(33984);
                int var1 = GL11.glGetInteger(32873);
                GlStateManager._bindTexture(tex);
                GL11.glTexParameteri(3553, 10241, 9729);
                GL11.glTexParameteri(3553, 10240, 9729);
                GL11.glTexParameteri(3553, 33085, 0);
                int var2 = GL11.glGetInteger(35055);
                GL15.glBindBuffer(35052, 0);
                GL11.glTexImage2D(3553, 0, 32856, arg0, arg0, 0, 6408, 5121, ((ByteBuffer) null));
                GL15.glBindBuffer(35052, var2);
                GlStateManager._bindTexture(var1);
                if (fbo == 0) {
                    fbo = GL30.glGenFramebuffers();
                }
                int var3 = GL11.glGetInteger(36006);
                GL30.glBindFramebuffer(36160, fbo);
                GL30.glFramebufferTexture2D(36160, 36064, 3553, tex, 0);
                int var4 = (GL30.glCheckFramebufferStatus(36160) == 36053);
                GL30.glBindFramebuffer(36160, var3);
                if (var4 == 0) {
                    return false;
                }
                size = arg0;
                CrosshairRenderer.BridgeGlTexture var5 = new CrosshairRenderer.BridgeGlTexture(tex, arg0);
                GpuTextureView var6 = RenderSystem.getDevice().createTextureView(var5);
                GpuSampler var7 = RenderSystem.getSamplerCache().getClampToEdge(FilterMode.LINEAR);
                Minecraft.getInstance().getTextureManager().register(TEX_ID, new CrosshairRenderer.BridgeTexture(var5, var6, var7));
                return true;
            }
        }
    }

    private static boolean build() {
        int var0 = 0;
        try {
            var0 = compile(35633, read("crosshair.vsh"));
            int var1 = compile(35632, read("crosshair.fsh"));
            int var2 = GL20.glCreateProgram();
            GL20.glAttachShader(var2, var0);
            GL20.glAttachShader(var2, var1);
            GL20.glBindAttribLocation(var2, 0, "Position");
            GL20.glLinkProgram(var2);
            GL20.glDetachShader(var2, var0);
            GL20.glDetachShader(var2, var1);
            GL20.glDeleteShader(var0);
            GL20.glDeleteShader(var1);
            if (GL20.glGetProgrami(var2, 35714) == 0) {
                GL20.glDeleteProgram(var2);
                failed = true;
            }
        } catch (Throwable ignored) {
        }
        return false;
        try {
            program = var2;
            vao = GL30.glGenVertexArrays();
            vbo = GL15.glGenBuffers();
            int var3 = GL11.glGetInteger(34229);
            int var4 = GL11.glGetInteger(34964);
            GL30.glBindVertexArray(vao);
            GL15.glBindBuffer(34962, vbo);
            new float[9][0] = -1.0F;
            new float[9][1] = -1.0F;
            new float[9][2] = 0.0F;
            new float[9][3] = 3.0F;
            new float[9][4] = -1.0F;
            new float[9][5] = 0.0F;
            new float[9][6] = -1.0F;
            new float[9][7] = 3.0F;
            new float[9][8] = 0.0F;
            GL15.glBufferData(34962, new float[9], 35044);
            GL20.glEnableVertexAttribArray(0);
            GL20.glVertexAttribPointer(0, 3, 5126, false, 12, 0L);
            GL30.glBindVertexArray(var3);
            GL15.glBindBuffer(34962, var4);
        } catch (Throwable ignored) {
        }
        return true;
    }

    private static int compile(int arg0, String arg1) {
        int var2 = GL20.glCreateShader(arg0);
        GL20.glShaderSource(var2, arg1);
        GL20.glCompileShader(var2);
        if (GL20.glGetShaderi(var2, 35713) == 0) {
            String var3 = GL20.glGetShaderInfoLog(var2);
            GL20.glDeleteShader(var2);
            throw new RuntimeException(var3);
        }
        return var2;
    }

    private static String read(String arg0) {
        String var2 = null;
        InputStream var1 = CrosshairRenderer.class.getResourceAsStream("/assets/clarity/shaders/crosshair/" + (arg0));
        try {
            if (var1 == null) {
                throw new IOException("missing " + (arg0));
            }
            var2 = new String(var1.readAllBytes(), StandardCharsets.UTF_8);
        } catch (Throwable ignored) {
        }
        if (var1 != null) {
            var1.close();
        }
        return var2;
        if (var1 != null) {
            try {
                var1.close();
            } catch (Throwable ignored) {
            }
            var2.addSuppressed(var3);
        }
        throw var2;
    }

    static {
        Hooks.bool1("Crosshair.render", (p0) -> CrosshairRenderer.lambda$static$0(p0));
        TEX_ID = Identifier.fromNamespaceAndPath("clarity", "crosshair_overlay");
        program = 0;
        vao = 0;
        vbo = 0;
        tex = 0;
        fbo = 0;
        size = 0;
        failed = false;
        loc = new HashMap();
        spread = 0.0F;
        target = 0.0F;
        spinAngle = 0.0F;
        lastNanos = 0L;
        START = System.nanoTime();
    }

    public final class BridgeGlTexture extends GlTexture {
        BridgeGlTexture(int arg0, int arg1) {
            super(12, "clarity-crosshair", TextureFormat.RGBA8, arg1, arg1, 1, 1, arg0);
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
