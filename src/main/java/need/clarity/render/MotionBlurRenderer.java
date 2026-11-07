package need.clarity.render;

import com.mojang.blaze3d.opengl.GlStateManager;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.systems.RenderSystem;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import need.clarity.api.Hooks;
import need.clarity.modules.visual.MotionBlur;
import need.clarity.render.sky.SkyShaderRenderer;
import net.minecraft.client.Minecraft;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL33;

public final class MotionBlurRenderer {
    private static final float CUT_ANGLE;
    private static final Matrix4f view;
    private static final Matrix3f cur;
    private static final Matrix3f prev;
    private static final Matrix3f delta;
    private static final Matrix4f proj;
    private static final float[] m3;
    private static boolean havePrev;
    private static long lastNanos;
    private static int program;
    private static int locScene;
    private static int locDelta;
    private static int locFocal;
    private static int locScale;
    private static int locMaxLen;
    private static int locSize;
    private static int locSamples;
    private static boolean failed;
    private static int vao;
    private static int vbo;
    private static int sampler;
    private static int copyTex;
    private static int copyFbo;
    private static int readFbo;
    private static int copyW;
    private static int copyH;
    private static int attachedId;

    private MotionBlurRenderer() {
    }

    public static void apply() {
        if (MotionBlur.isEligible()) {
            if (failed) {
            }
        }
        havePrev = false;
        lastNanos = 0L;
        return;
        try {
            run();
        } catch (Throwable ignored) {
        }
        failed = true;
    }

    private static void run() {
        float var3 = 0.0F;
        int var14 = 0;
        RenderSystem.assertOnRenderThread();
        Minecraft var0 = Minecraft.getInstance();
        if (var0.level != null) {
            if (var0.player == null) {
            }
        }
        havePrev = false;
        return;
        long var1 = System.nanoTime();
        if (lastNanos == 0L) {
        } else {
        }
        var3 = (((float) (var1 - lastNanos)) / 1.0E9F);
        lastNanos = var1;
        var3 = Math.max(0.001F, Math.min(0.05F, var3));
        var0.gameRenderer.getMainCamera().getViewRotationMatrix(view);
        cur.set(view);
        if (!havePrev) {
            prev.set(cur);
            havePrev = true;
            return;
        }
        cur.transpose(delta);
        prev.mul(delta, delta);
        prev.set(cur);
        float var5 = ((delta.m00() + delta.m11()) + delta.m22());
        float var6 = ((float) Math.acos(((double) Math.max(-1.0F, Math.min(1.0F, ((var5 - 1.0F) * 0.5F))))));
        if (var6 <= CUT_ANGLE) {
            if (!Float.isFinite(var6)) {
            }
        }
        return;
        RenderTarget var7 = var0.getMainRenderTarget();
        if (var7 != null) {
            if (var7.width > 0) {
                if (var7.height <= 0) {
                }
            }
            return;
            if (!SkyShaderRenderer.levelProjection(proj)) {
                return;
            }
            float var8 = Math.abs(proj.m00());
            float var9 = Math.abs(proj.m11());
            if (var8 >= 1.0E-4F) {
                if (var9 < 1.0E-4F) {
                }
            }
            return;
            float var10 = (MotionBlur.getAmount() / var3);
            if (((((var6 * Math.max(var8, var9)) * 0.5F) * var10) * ((float) Math.max(var7.width, var7.height))) < 0.75F) {
                return;
            }
            int var12 = RawFramebufferUtil.getColorTextureId(var7);
            if (var12 == 0) {
                return;
            }
            RawGlStateCapture var13 = RawGlStateCapture.capture();
            try {
                if (!(ensureResources(var7.width, var7.height))) {
                }
            } catch (Throwable ignored) {
            }
            if (var13 != null) {
                var13.close();
            }
            return;
            try {
                if (attachedId != var12) {
                    GL30.glBindFramebuffer(36160, readFbo);
                    GL30.glFramebufferTexture2D(36160, 36064, 3553, var12, 0);
                    if (GL30.glCheckFramebufferStatus(36160) != 36053) {
                        attachedId = 0;
                    }
                }
            } catch (Throwable ignored) {
            }
            if (var13 != null) {
                var13.close();
            }
            return;
            try {
                attachedId = var12;
                GlStateManager._disableScissorTest();
                GL30.glBindFramebuffer(36008, readFbo);
                GL11.glReadBuffer(36064);
                GL30.glBindFramebuffer(36009, copyFbo);
                GL11.glDrawBuffer(36064);
                GL30.glBlitFramebuffer(0, 0, var7.width, var7.height, 0, 0, var7.width, var7.height, 16384, 9728);
                if (!RawFramebufferUtil.bindDirect(var7)) {
                }
            } catch (Throwable ignored) {
            }
            if (var13 != null) {
                var13.close();
            }
            return;
            try {
                GlStateManager._disableDepthTest();
                GlStateManager._depthMask(false);
                GlStateManager._disableBlend();
                GlStateManager._disableCull();
                GL11.glColorMask(true, true, true, true);
                GlStateManager._activeTexture(33984);
                var14 = GL11.glGetInteger(35097);
                GlStateManager._bindTexture(copyTex);
                GL33.glBindSampler(0, sampler);
                GL20.glUseProgram(program);
                delta.get(m3);
                GL20.glUniform1i(locScene, 0);
                GL20.glUniformMatrix3fv(locDelta, false, m3);
                GL20.glUniform2f(locFocal, var8, var9);
                GL20.glUniform1f(locScale, var10);
                GL20.glUniform1f(locMaxLen, MotionBlur.getMaxBlur());
                GL20.glUniform2f(locSize, ((float) var7.width), ((float) var7.height));
                GL20.glUniform1i(locSamples, MotionBlur.computeColorOrIndex());
                GL30.glBindVertexArray(vao);
                GL11.glDrawArrays(4, 0, 3);
                GL30.glBindVertexArray(0);
                GL33.glBindSampler(0, var14);
            } catch (Throwable ignored) {
            }
            if (var13 != null) {
                var13.close();
                var14 = 0.016666668F;
                if (var13 != null) {
                    try {
                        var13.close();
                    } catch (Throwable ignored) {
                    }
                    var14.addSuppressed(var15);
                }
                throw var14;
            }
            return;
        }
    }

    private static boolean ensureResources(int arg0, int arg1) {
        if (program == 0) {
            if (!buildProgram()) {
                return false;
            }
            if (vao == 0) {
                vao = GL30.glGenVertexArrays();
                vbo = GL15.glGenBuffers();
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
                GL30.glBindVertexArray(0);
            }
            if (sampler == 0) {
                sampler = GL33.glGenSamplers();
                GL33.glSamplerParameteri(sampler, 10241, 9729);
                GL33.glSamplerParameteri(sampler, 10240, 9729);
                GL33.glSamplerParameteri(sampler, 10242, 33071);
                GL33.glSamplerParameteri(sampler, 10243, 33071);
            }
            if (readFbo == 0) {
                readFbo = GL30.glGenFramebuffers();
            }
            if (copyFbo != 0) {
                if (copyW == arg0) {
                    if (copyH == arg1) {
                        return true;
                    }
                    if (copyTex == 0) {
                        copyTex = GL11.glGenTextures();
                    }
                    GlStateManager._activeTexture(33984);
                    GlStateManager._bindTexture(copyTex);
                    GL11.glTexParameteri(3553, 33085, 0);
                    int var2 = GL11.glGetInteger(35055);
                    GL15.glBindBuffer(35052, 0);
                    GL11.glTexImage2D(3553, 0, 32856, arg0, arg1, 0, 6408, 5121, ((ByteBuffer) null));
                    GL15.glBindBuffer(35052, var2);
                    if (copyFbo == 0) {
                        copyFbo = GL30.glGenFramebuffers();
                    }
                    GL30.glBindFramebuffer(36160, copyFbo);
                    GL30.glFramebufferTexture2D(36160, 36064, 3553, copyTex, 0);
                    int var3 = (GL30.glCheckFramebufferStatus(36160) == 36053);
                    if (var3 != 0) {
                    } else {
                    }
                    copyW = 0;
                    if (var3 != 0) {
                    } else {
                    }
                    copyH = 0;
                    return var3;
                }
            }
        }
    }

    private static boolean buildProgram() {
        int var0 = 0;
        try {
            var0 = compile(35633, read("blur.vsh"));
            int var1 = compile(35632, read("blur.fsh"));
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
            locScene = GL20.glGetUniformLocation(var2, "uScene");
            locDelta = GL20.glGetUniformLocation(var2, "uDelta");
            locFocal = GL20.glGetUniformLocation(var2, "uFocal");
            locScale = GL20.glGetUniformLocation(var2, "uScale");
            locMaxLen = GL20.glGetUniformLocation(var2, "uMaxLen");
            locSize = GL20.glGetUniformLocation(var2, "uSize");
            locSamples = GL20.glGetUniformLocation(var2, "uSamples");
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
        InputStream var1 = MotionBlurRenderer.class.getResourceAsStream("/assets/clarity/shaders/motionblur/" + (arg0));
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
        Hooks.act0("MotionBlur.apply", () -> MotionBlurRenderer.apply());
        CUT_ANGLE = ((float) Math.toRadians(45.0));
        view = new Matrix4f();
        cur = new Matrix3f();
        prev = new Matrix3f();
        delta = new Matrix3f();
        proj = new Matrix4f();
        m3 = new float[9];
        havePrev = false;
        lastNanos = 0L;
        program = 0;
        failed = false;
        vao = 0;
        vbo = 0;
        sampler = 0;
        copyTex = 0;
        copyFbo = 0;
        readFbo = 0;
        copyW = 0;
        copyH = 0;
        attachedId = 0;
    }

}
