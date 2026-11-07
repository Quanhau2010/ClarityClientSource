package need.clarity.render.worldfx;

import com.mojang.blaze3d.opengl.GlStateManager;
import com.mojang.blaze3d.pipeline.RenderTarget;
import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import need.clarity.render.RawFramebufferUtil;
import need.clarity.render.RawGlStateCapture;
import need.clarity.render.Shader;
import need.clarity.render.sky.SkyShaderRenderer;
import net.minecraft.client.Minecraft;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL31;
import org.lwjgl.opengl.GL33;
import org.lwjgl.system.MemoryUtil;

public final class WorldFx {
    public static final String TRAIL = "trail";
    public static final String RING = "jumpring";
    public static final String WINGS = "wings";
    public static final String HATS = "hats";
    public static final String WEATHER = "weather";
    public static final String BURST = "burst";
    public static final String AURA_PARTICLES = "aurap";
    public static final String TARGET = "target";
    public static final String AURA = "aura";
    public static final String KILL = "killfx";
    public static final String BEATS = "beats";
    public static final String CORPSE = "corpse";
    private static final int FLOATS = 11;
    private static FloatBuffer buf;
    private static int verts;
    private static final Map shaders;
    private static final Set failed;
    private static int vao;
    private static int vbo;
    private static final Matrix4f viewProj;
    private static final float[] mat;
    private static final float[] axR;
    private static final float[] axU;
    private static final float[] axF;
    private static int emptyVao;
    private static int linearSampler;

    private WorldFx() {
    }

    public static void begin() {
        buf.clear();
        verts = 0;
    }

    public static boolean isEmpty() {
        return (verts == 0);
    }

    public static void vertex(float arg0, float arg1, float arg2, float arg3, float arg4, float arg5, float arg6, float arg7, float arg8, float arg9, float arg10) {
        if (buf.remaining() < 11) {
            buf = MemoryUtil.memRealloc(buf, (buf.capacity() * 2));
        }
        buf.put(arg0).put(arg1).put(arg2).put(arg3).put(arg4).put(arg5).put(arg6).put(arg7).put(arg8).put(arg9).put(arg10);
        verts = (verts + 1);
    }

    public static void flushTextured(String arg0, boolean arg1, int arg2, Consumer arg3) {
        int var4 = 0;
        if (arg2 == 0) {
            begin();
            return;
        }
        var4 = 0;
        GlStateManager._activeTexture(33984);
        var4 = GL11.glGetInteger(35097);
        int var5 = GL11.glGetInteger(32873);
        if (linearSampler == 0) {
            linearSampler = GL33.glGenSamplers();
            GL33.glSamplerParameteri(linearSampler, 10241, 9729);
            GL33.glSamplerParameteri(linearSampler, 10240, 9729);
            GL33.glSamplerParameteri(linearSampler, 10242, 33071);
            GL33.glSamplerParameteri(linearSampler, 10243, 33071);
        }
        GlStateManager._bindTexture(arg2);
        GL33.glBindSampler(0, linearSampler);
        flush(arg0, arg1, arg3);
        GlStateManager._activeTexture(33984);
        GlStateManager._bindTexture(var5);
        GL33.glBindSampler(0, var4);
        GL33.glBindSampler(0, var4);
        throw var6;
    }

    public static void flush(String arg0, boolean arg1, Consumer arg2) {
        Shader var3 = null;
        try {
            if (verts == 0) {
            }
        } catch (Throwable ignored) {
        }
        buf.clear();
        verts = 0;
        return;
        try {
            var3 = shader(arg0);
            if (var3 == null) {
            }
        } catch (Throwable ignored) {
        }
        buf.clear();
        verts = 0;
        return;
        try {
            if (!SkyShaderRenderer.levelViewProjection(viewProj)) {
            }
        } catch (Throwable ignored) {
        }
        buf.clear();
        verts = 0;
        return;
        try {
            RenderTarget var4 = Minecraft.getInstance().getMainRenderTarget();
            if (var4 == null) {
            }
        } catch (Throwable ignored) {
        }
        buf.clear();
        verts = 0;
        return;
        try {
            RawGlStateCapture var5 = RawGlStateCapture.capture();
            try {
                if (!RawFramebufferUtil.bindDirect(var4)) {
                }
            } catch (Throwable ignored) {
            }
            if (var5 != null) {
                var5.close();
            }
            buf.clear();
            verts = 0;
            return;
            try {
                try {
                    GlStateManager._disableScissorTest();
                    GlStateManager._disableCull();
                    GlStateManager._depthMask(false);
                    if (arg1) {
                        GlStateManager._disableDepthTest();
                    } else {
                        GlStateManager._enableDepthTest();
                        GlStateManager._depthFunc(515);
                    }
                    GlStateManager._enableBlend();
                    GlStateManager._blendFuncSeparate(1, 771, 1, 771);
                    GL14.glBlendEquation(32774);
                    if (vao == 0) {
                        createVao();
                    }
                    buf.flip();
                    GL30.glBindVertexArray(vao);
                    GL15.glBindBuffer(34962, vbo);
                    GL15.glBufferData(34962, buf, 35040);
                    var3.bind();
                    var3.setUniformMatrix4f("uViewProj", viewProj.get(mat));
                    setAxes(var3);
                    if (arg2 != null) {
                        arg2.accept(var3);
                    }
                    GL11.glDrawArrays(4, 0, verts);
                    var3.unbind();
                    GL30.glBindVertexArray(0);
                } catch (Throwable ignored) {
                }
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
            buf.clear();
            verts = 0;
            buf.clear();
            verts = 0;
            throw var8;
            return;
        } catch (Throwable ignored) {
        }
    }

    public static void drawInstanced(String arg0, int arg1, boolean arg2, int arg3, Consumer arg4) {
        Shader var5 = null;
        int var8 = 0;
        try {
            if (arg1 <= 0) {
            }
        } catch (Throwable ignored) {
        }
        return;
        try {
            var5 = shader(arg0);
            if (var5 == null) {
            }
        } catch (Throwable ignored) {
        }
        return;
        try {
            if (!SkyShaderRenderer.levelViewProjection(viewProj)) {
            }
        } catch (Throwable ignored) {
        }
        return;
        try {
            RenderTarget var6 = Minecraft.getInstance().getMainRenderTarget();
            if (var6 == null) {
            }
        } catch (Throwable ignored) {
        }
        return;
        try {
            RawGlStateCapture var7 = RawGlStateCapture.capture();
            try {
                if (!RawFramebufferUtil.bindDirect(var6)) {
                }
            } catch (Throwable ignored) {
            }
            if (var7 != null) {
                var7.close();
            }
            return;
            try {
                try {
                    GlStateManager._disableScissorTest();
                    GlStateManager._disableCull();
                    GlStateManager._depthMask(false);
                    if (arg2) {
                        GlStateManager._disableDepthTest();
                    } else {
                        GlStateManager._enableDepthTest();
                        GlStateManager._depthFunc(515);
                    }
                    GlStateManager._enableBlend();
                    GlStateManager._blendFuncSeparate(1, 771, 1, 771);
                    GL14.glBlendEquation(32774);
                    if (emptyVao == 0) {
                        emptyVao = GL30.glGenVertexArrays();
                    }
                    GL30.glBindVertexArray(emptyVao);
                    var8 = 0;
                    if (arg3 != 0) {
                        GlStateManager._activeTexture(33984);
                        var8 = GL11.glGetInteger(35097);
                        GlStateManager._bindTexture(arg3);
                        GL33.glBindSampler(0, 0);
                    }
                    var5.bind();
                    var5.setUniformMatrix4f("uViewProj", viewProj.get(mat));
                    setAxes(var5);
                    if (arg4 != null) {
                        arg4.accept(var5);
                    }
                    GL31.glDrawArraysInstanced(5, 0, 4, arg1);
                    var5.unbind();
                    GL30.glBindVertexArray(0);
                    if (arg3 != 0) {
                        GL33.glBindSampler(0, var8);
                    }
                    if (var7 != null) {
                        var7.close();
                        if (var7 != null) {
                            try {
                                var7.close();
                            } catch (Throwable ignored) {
                            }
                            var8.addSuppressed(var9);
                        }
                        throw var8;
                    }
                    return;
                } catch (Throwable ignored) {
                }
            } catch (Throwable ignored) {
            }
        } catch (Throwable ignored) {
        }
    }

    private static void setAxes(Shader arg0) {
        SkyShaderRenderer.cameraAxes(axR, axU, axF);
        arg0.setUniform3f("uRight", axR[0], axR[1], axR[2]);
        arg0.setUniform3f("uUp", axU[0], axU[1], axU[2]);
        arg0.setUniform3f("uFwd", axF[0], axF[1], axF[2]);
    }

    public static void axes(float[] arg0, float[] arg1, float[] arg2) {
        SkyShaderRenderer.cameraAxes(arg0, arg1, arg2);
    }

    private static void createVao() {
        vao = GL30.glGenVertexArrays();
        vbo = GL15.glGenBuffers();
        GL30.glBindVertexArray(vao);
        GL15.glBindBuffer(34962, vbo);
        GL20.glEnableVertexAttribArray(0);
        GL20.glVertexAttribPointer(0, 3, 5126, false, 44, 0L);
        GL20.glEnableVertexAttribArray(1);
        GL20.glVertexAttribPointer(1, 2, 5126, false, 44, 12L);
        GL20.glEnableVertexAttribArray(2);
        GL20.glVertexAttribPointer(2, 4, 5126, false, 44, 20L);
        GL20.glEnableVertexAttribArray(3);
        GL20.glVertexAttribPointer(3, 2, 5126, false, 44, 36L);
        GL30.glBindVertexArray(0);
    }

    private static Shader shader(String arg0) {
        Shader var1 = null;
        var1 = ((Shader) shaders.get(arg0));
        if (var1 == null) {
            if (failed.contains(arg0)) {
            }
        }
        return var1;
        try {
            var1 = new Shader("worldfx", arg0);
            shaders.put(arg0, var1);
        } catch (Exception ignored) {
        }
        failed.add(arg0);
        return var1;
    }

    static {
        buf = MemoryUtil.memAllocFloat(67584);
        verts = 0;
        shaders = new HashMap();
        failed = new HashSet();
        vao = 0;
        vbo = 0;
        viewProj = new Matrix4f();
        mat = new float[16];
        axR = new float[3];
        axU = new float[3];
        axF = new float[3];
        emptyVao = 0;
        linearSampler = 0;
    }

}
