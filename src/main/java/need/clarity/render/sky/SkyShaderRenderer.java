package need.clarity.render.sky;

import com.mojang.blaze3d.opengl.GlStateManager;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.systems.RenderSystem;
import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import need.clarity.api.Hooks;
import need.clarity.modules.visual.SkyShaders;
import need.clarity.render.RawFramebufferUtil;
import need.clarity.render.RawGlStateCapture;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.world.level.material.FogType;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL33;
import org.lwjgl.system.MemoryUtil;

public final class SkyShaderRenderer {
    private static final Matrix4f levelProjection;
    private static boolean haveProjection;
    private static final Matrix4f viewRotation;
    private static CameraRenderState cameraState;
    private static boolean activeThisFrame;
    private static boolean activeLastFrame;
    private static final Map programs;
    private static final Set failed;
    private static SkyProgram upscaleProgram;
    private static boolean upscaleFailed;
    private static int noiseTex;
    private static int samplerRepeat;
    private static int samplerClamp;
    private static int vao;
    private static int vbo;
    private static int lowFbo;
    private static int lowTex;
    private static int lowW;
    private static int lowH;
    private static long lastNanos;
    private static double animTime;
    private static double celestialAngle;
    private static final Matrix4f tmpVP;
    private static final Matrix4f tmpInv;
    private static final Matrix3f tmpCel;
    private static final float[] mat4;
    private static final float[] mat3;
    private static final Vector3f CELESTIAL_AXIS;
    private static float dirSign;
    private static final Matrix4f projForPixel;
    private static final Vector4f tmpA;
    private static final Vector4f tmpB;

    private SkyShaderRenderer() {
    }

    public static void beginFrame(Matrix4fc arg0, CameraRenderState arg1) {
        activeLastFrame = activeThisFrame;
        activeThisFrame = false;
        if (arg0 != null) {
            viewRotation.set(arg0);
        }
        cameraState = arg1;
    }

    public static void captureProjection(Matrix4fc arg0) {
        levelProjection.set(arg0);
        haveProjection = true;
    }

    public static boolean levelViewProjection(Matrix4f arg0) {
        if (haveProjection) {
        } else {
            if (cameraState != null) {
            } else {
            }
            Object var1 = null;
            if (var1 == null) {
                return false;
            }
            var1.mul(viewRotation, arg0);
            return arg0.isFinite();
        }
    }

    public static boolean levelProjection(Matrix4f arg0) {
        if (haveProjection) {
        } else {
            if (cameraState != null) {
            } else {
            }
            Object var1 = null;
            if (var1 == null) {
                return false;
            }
            arg0.set(var1);
            return arg0.isFinite();
        }
    }

    public static void cameraAxes(float[] arg0, float[] arg1, float[] arg2) {
        arg0[0] = viewRotation.m00();
        arg0[1] = viewRotation.m10();
        arg0[2] = viewRotation.m20();
        arg1[0] = viewRotation.m01();
        arg1[1] = viewRotation.m11();
        arg1[2] = viewRotation.m21();
        arg2[0] = -(viewRotation.m02());
        arg2[1] = -(viewRotation.m12());
        arg2[2] = -(viewRotation.m22());
    }

    public static boolean isActiveThisFrame() {
        return activeThisFrame;
    }

    public static boolean wasActiveLastFrame() {
        if (activeLastFrame) {
            return (SkyShaders.active());
        }
    }

    public static boolean tryRender(boolean arg0) {
        boolean var2 = false;
        if (!SkyShaders.active()) {
            return false;
        }
        if (arg0) {
            if (!SkyShaders.isEligible()) {
                return false;
            }
            CameraRenderState var1 = cameraState;
            if (var1 != null) {
                if (var1.fogType != null) {
                    if (var1.fogType != FogType.NONE) {
                        if (var1.fogType != FogType.ATMOSPHERIC) {
                            return false;
                        }
                        try {
                            var2 = draw();
                        } catch (Throwable ignored) {
                        }
                        var2 = false;
                        activeThisFrame = var2;
                        return var2;
                    }
                }
            }
        }
    }

    private static boolean draw() {
        SkyProgram var5 = null;
        int var6 = 0;
        int var7 = 0;
        int var10 = 0;
        int var11 = 0;
        int var12 = 0;
        SkyProgram var13 = null;
        RenderSystem.assertOnRenderThread();
        RenderTarget var1 = Minecraft.getInstance().getMainRenderTarget();
        if (var1 != null) {
            if (var1.width > 0) {
                if (var1.height <= 0) {
                }
            }
            return false;
            String var2 = SkyShaders.preset();
            String var3 = SkyShaders.resolveLabel(var2);
            if (failed.contains(var3)) {
                return false;
            }
            if (!computeMatrices()) {
                return false;
            }
            tickClocks();
            RawGlStateCapture var4 = RawGlStateCapture.capture();
            try {
                var5 = program(var3);
                if (var5 != null) {
                    if (!ensureCommon()) {
                    }
                }
                var6 = 0;
            } catch (Throwable ignored) {
            }
            if (var4 != null) {
                var4.close();
            }
            return var6;
            try {
                var6 = SkyShaders.computeFloatMetric7();
                if (var6 < 0.999F) {
                    var7 = (!upscaleFailed);
                    int var8 = var1.width;
                    int var9 = var1.height;
                    if (var7 != 0) {
                    } else {
                    }
                    var10 = var8;
                    if (var7 != 0) {
                    } else {
                    }
                    var11 = var9;
                    GlStateManager._disableDepthTest();
                    GlStateManager._depthMask(false);
                    GlStateManager._disableBlend();
                    GlStateManager._disableCull();
                    GlStateManager._disableScissorTest();
                    GL11.glColorMask(true, true, true, true);
                    if (var7 != 0) {
                        if (!(ensureLowTarget(var10, var11))) {
                            var7 = 0;
                            var10 = var8;
                            var11 = var9;
                        }
                        if (var7 != 0) {
                            GL30.glBindFramebuffer(36160, lowFbo);
                            GL11.glDrawBuffer(36064);
                            GlStateManager._viewport(0, 0, var10, var11);
                        } else {
                            if (!RawFramebufferUtil.bindDirect(var1)) {
                                var12 = 0;
                            }
                        }
                    }
                }
            } catch (Throwable ignored) {
            }
            if (var4 != null) {
                var4.close();
            }
            return var12;
            try {
                var12 = boundSampler0();
                GlStateManager._activeTexture(33984);
                GlStateManager._bindTexture(noiseTex);
                GL33.glBindSampler(0, samplerRepeat);
                GL20.glUseProgram(var5.id);
                setUniforms(var5, var2, var11);
                drawTriangle();
                if (var7 != 0) {
                    var13 = upscale();
                    if (var13 != null) {
                        if (RawFramebufferUtil.bindDirect(var1)) {
                            GlStateManager._bindTexture(lowTex);
                            GL33.glBindSampler(0, samplerClamp);
                            GL20.glUseProgram(var13.id);
                            var13.set1i("uSource", 0);
                            drawTriangle();
                        }
                        GL33.glBindSampler(0, var12);
                        var13 = 1;
                    }
                }
            } catch (Throwable ignored) {
            }
            if (var4 != null) {
                var4.close();
            }
            return var13;
            var5 = Math.max(1, Math.round((((float) var9) * var6)));
            if (var4 != null) {
                try {
                    var4.close();
                } catch (Throwable ignored) {
                }
                var6 = Math.max(1, Math.round((((float) var8) * var6)));
                var5.addSuppressed(var6);
            }
            throw var5;
        }
    }

    private static void setUniforms(SkyProgram arg0, String arg1, int arg2) {
        float[][] var3 = SkyShaders.colors();
        float[] var4 = var3[0];
        float[] var5 = var3[1];
        float[] var6 = SkyShaders.horizon(arg1, var4, var5);
        tmpInv.get(mat4);
        arg0.setMat4("uInvViewProj", mat4);
        arg0.set1i("uNoise", 0);
        arg0.set1f("uTime", ((float) (animTime % 10000.0)));
        arg0.set3f("uColor1", lin(var4[0]), lin(var4[1]), lin(var4[2]));
        arg0.set3f("uColor2", lin(var5[0]), lin(var5[1]), lin(var5[2]));
        arg0.set1f("uIntensity", SkyShaders.computeFloatMetric2());
        arg0.set1f("uScale", SkyShaders.computeFloatMetric3());
        arg0.set1f("uStars", SkyShaders.computeFloatMetric4());
        arg0.set1f("uBrightness", SkyShaders.computeFloatMetric1());
        arg0.set3f("uHorizon", var6[0], var6[1], var6[2]);
        arg0.set1f("uHorizonFog", SkyShaders.computeFloatMetric6());
        arg0.set1f("uDirSign", dirSign);
        tmpCel.rotation(((float) celestialAngle), CELESTIAL_AXIS).get(mat3);
        arg0.setMat3("uCelestial", mat3);
        float var7 = Math.abs(projForPixel.m11());
        if (var7 > 1.0E-4F) {
        } else {
        }
        "uPixel".set1f((2.0F / (var7 * ((float) arg2))), 0.002F);
    }

    private static boolean computeMatrices() {
        Matrix4f var0 = null;
        if (haveProjection) {
            var0 = levelProjection;
        } else {
            if (cameraState != null) {
                if (cameraState.projectionMatrix != null) {
                    var0 = cameraState.projectionMatrix;
                } else {
                    return false;
                }
                projForPixel.set(var0);
                var0.mul(viewRotation, tmpVP);
                if (tmpVP.isFinite()) {
                    if (Math.abs(tmpVP.determinant()) < 1.0E-12F) {
                    }
                }
                return false;
                tmpVP.invert(tmpInv);
                if (!tmpInv.isFinite()) {
                    return false;
                }
                tmpInv.transform(0.0F, 0.0F, -1.0F, 1.0F, tmpA);
                tmpInv.transform(0.0F, 0.0F, 1.0F, 1.0F, tmpB);
                float var1 = ((tmpB.x * tmpA.w) - (tmpA.x * tmpB.w));
                float var2 = ((tmpB.y * tmpA.w) - (tmpA.y * tmpB.w));
                float var3 = ((tmpB.z * tmpA.w) - (tmpA.z * tmpB.w));
                float var4 = -(viewRotation.m02());
                float var5 = -(viewRotation.m12());
                float var6 = -(viewRotation.m22());
                if ((((var1 * var4) + (var2 * var5)) + (var3 * var6)) < 0.0F) {
                } else {
                }
                dirSign = 1.0F;
                return true;
            }
        }
    }

    private static void tickClocks() {
        double var2 = 0.0;
        long var0 = System.nanoTime();
        if (lastNanos == 0L) {
        } else {
        }
        var2 = (((double) (var0 - lastNanos)) / 1.0E9);
        lastNanos = var0;
        var2 = Math.max(0.0, Math.min(var2, 0.1));
        animTime = (animTime + (var2 * ((double) SkyShaders.computeFloatMetric())));
        celestialAngle = ((celestialAngle + ((var2 * ((double) SkyShaders.computeFloatMetric5())) * 0.010471975511965976)) % 6.283185307179586);
    }

    private static float lin(float arg0) {
        return ((float) Math.pow(((double) Math.max(arg0, 0.0F)), 2.2));
    }

    private static SkyProgram program(String arg0) {
        SkyProgram var1 = null;
        var1 = ((SkyProgram) programs.get(arg0));
        if (var1 != null) {
            return var1;
        }
        try {
            var1 = SkyProgram.preset(arg0);
            programs.put(arg0, var1);
        } catch (Throwable ignored) {
        }
        return var1;
    }

    private static SkyProgram upscale() {
        if (upscaleProgram == null) {
            if (upscaleFailed) {
            }
        }
        return upscaleProgram;
        try {
            upscaleProgram = SkyProgram.upscale();
        } catch (Throwable ignored) {
        }
        upscaleFailed = true;
        return upscaleProgram;
    }

    private static boolean ensureCommon() {
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
        if (samplerRepeat == 0) {
            samplerRepeat = makeSampler(10497);
            samplerClamp = makeSampler(33071);
        }
        if (noiseTex == 0) {
            noiseTex = buildNoise();
        }
        return (noiseTex != 0);
    }

    private static int makeSampler(int arg0) {
        int var1 = GL33.glGenSamplers();
        GL33.glSamplerParameteri(var1, 10241, 9729);
        GL33.glSamplerParameteri(var1, 10240, 9729);
        GL33.glSamplerParameteri(var1, 10242, arg0);
        GL33.glSamplerParameteri(var1, 10243, arg0);
        return var1;
    }

    private static int buildNoise() {
        int var4 = 0;
        int var5 = 0;
        int var6 = 0;
        int var7 = 0;
        int var8 = 0;
        int var9 = 0;
        byte[] var1 = new byte[65536];
        byte[] var2 = new byte[65536];
        byte[] var3 = new byte[65536];
        var4 = -1640531527;
        var5 = 0;
        while (true) {
            if (var5 >= 65536) break;
            var4 = (var4 ^ (var4 << 13));
            var4 = (var4 ^ (var4 >>> 17));
            var4 = (var4 ^ (var4 << 5));
            var1[var5] = ((byte) (var4 >>> 24));
            var4 = (var4 ^ (var4 << 13));
            var4 = (var4 ^ (var4 >>> 17));
            var4 = (var4 ^ (var4 << 5));
            var2[var5] = ((byte) (var4 >>> 24));
            var4 = (var4 ^ (var4 << 13));
            var4 = (var4 ^ (var4 >>> 17));
            var4 = (var4 ^ (var4 << 5));
            var3[var5] = ((byte) (var4 >>> 24));
            var5++;
        }
        var5 = MemoryUtil.memAlloc(262144);
        var6 = 0;
        while (true) {
            if (var6 >= 256) break;
            var7 = 0;
            while (true) {
                if (var7 >= 256) break;
                var8 = ((var6 * 256) + var7);
                var9 = ((((var6 - 17) & 255) * 256) + ((var7 - 37) & 255));
                var5.put(var1[var8]).put(var1[var9]).put(var2[var8]).put(var3[var8]);
                var7++;
            }
            var6++;
        }
        var5.flip();
        var6 = GL11.glGetInteger(35055);
        GL15.glBindBuffer(35052, 0);
        var7 = GL11.glGetInteger(3314);
        var8 = GL11.glGetInteger(3316);
        var9 = GL11.glGetInteger(3315);
        int var10 = GL11.glGetInteger(3317);
        GL11.glPixelStorei(3314, 0);
        GL11.glPixelStorei(3316, 0);
        GL11.glPixelStorei(3315, 0);
        GL11.glPixelStorei(3317, 4);
        int var11 = GL11.glGenTextures();
        GlStateManager._activeTexture(33984);
        GlStateManager._bindTexture(var11);
        GL11.glTexParameteri(3553, 10241, 9729);
        GL11.glTexParameteri(3553, 10240, 9729);
        GL11.glTexParameteri(3553, 10242, 10497);
        GL11.glTexParameteri(3553, 10243, 10497);
        GL11.glTexParameteri(3553, 33085, 0);
        GL11.glTexImage2D(3553, 0, 32856, 256, 256, 0, 6408, 5121, var5);
        GL11.glPixelStorei(3314, var7);
        GL11.glPixelStorei(3316, var8);
        GL11.glPixelStorei(3315, var9);
        GL11.glPixelStorei(3317, var10);
        GL15.glBindBuffer(35052, var6);
        MemoryUtil.memFree(var5);
        return var11;
        MemoryUtil.memFree(var5);
        throw var13;
    }

    private static boolean ensureLowTarget(int arg0, int arg1) {
        if (lowFbo != 0) {
            if (lowW == arg0) {
                if (lowH == arg1) {
                    return true;
                }
                if (lowTex == 0) {
                    lowTex = GL11.glGenTextures();
                }
                GlStateManager._activeTexture(33984);
                GlStateManager._bindTexture(lowTex);
                GL11.glTexParameteri(3553, 10241, 9729);
                GL11.glTexParameteri(3553, 10240, 9729);
                GL11.glTexParameteri(3553, 33085, 0);
                int var2 = GL11.glGetInteger(35055);
                GL15.glBindBuffer(35052, 0);
                GL11.glTexImage2D(3553, 0, 34842, arg0, arg1, 0, 6408, 5126, ((FloatBuffer) null));
                GL15.glBindBuffer(35052, var2);
                if (lowFbo == 0) {
                    lowFbo = GL30.glGenFramebuffers();
                }
                GL30.glBindFramebuffer(36160, lowFbo);
                GL30.glFramebufferTexture2D(36160, 36064, 3553, lowTex, 0);
                int var3 = (GL30.glCheckFramebufferStatus(36160) == 36053);
                if (var3 != 0) {
                } else {
                }
                lowW = 0;
                if (var3 != 0) {
                } else {
                }
                lowH = 0;
                if (var3 == 0) {
                }
                return var3;
            }
        }
    }

    private static int boundSampler0() {
        GlStateManager._activeTexture(33984);
        return GL11.glGetInteger(35097);
    }

    private static void drawTriangle() {
        GL30.glBindVertexArray(vao);
        GL11.glDrawArrays(4, 0, 3);
        GL30.glBindVertexArray(0);
    }

    public static void dispose() {
        Iterator var0 = null;
        try {
            var0 = programs.values().iterator();
            while (true) {
                if (!var0.hasNext()) break;
                ((SkyProgram) var0.next()).delete();
            }
            programs.clear();
            if (upscaleProgram != null) {
                upscaleProgram.delete();
                upscaleProgram = null;
            }
            if (noiseTex != 0) {
                GL11.glDeleteTextures(noiseTex);
                noiseTex = 0;
            }
            if (lowTex != 0) {
                GL11.glDeleteTextures(lowTex);
                lowTex = 0;
            }
            if (lowFbo != 0) {
                GL30.glDeleteFramebuffers(lowFbo);
                lowFbo = 0;
            }
            if (samplerRepeat != 0) {
                GL33.glDeleteSamplers(samplerRepeat);
                samplerRepeat = 0;
            }
            if (samplerClamp != 0) {
                GL33.glDeleteSamplers(samplerClamp);
                samplerClamp = 0;
            }
            if (vbo != 0) {
                GL15.glDeleteBuffers(vbo);
                vbo = 0;
            }
            if (vao != 0) {
                GL30.glDeleteVertexArrays(vao);
                vao = 0;
            }
            return;
        } catch (Throwable ignored) {
        }
    }

    static {
        Hooks.bool0("SkyRenderer.wasActiveLastFrame", () -> SkyShaderRenderer.wasActiveLastFrame());
        Hooks.bool0("SkyRenderer.isActiveThisFrame", () -> SkyShaderRenderer.isActiveThisFrame());
        Hooks.act1("SkyRenderer.beginFrame", (p0) -> SkyShaderRenderer.lambda$static$0(p0));
        Hooks.act1("SkyRenderer.captureProjection", (p0) -> SkyShaderRenderer.lambda$static$1(p0));
        Hooks.bool1("SkyRenderer.tryRender", (p0) -> SkyShaderRenderer.lambda$static$2(p0));
        levelProjection = new Matrix4f();
        haveProjection = false;
        viewRotation = new Matrix4f();
        activeThisFrame = false;
        activeLastFrame = false;
        programs = new HashMap();
        failed = new HashSet();
        upscaleFailed = false;
        noiseTex = 0;
        samplerRepeat = 0;
        samplerClamp = 0;
        vao = 0;
        vbo = 0;
        lowFbo = 0;
        lowTex = 0;
        lowW = 0;
        lowH = 0;
        lastNanos = 0L;
        animTime = 0.0;
        celestialAngle = 0.0;
        tmpVP = new Matrix4f();
        tmpInv = new Matrix4f();
        tmpCel = new Matrix3f();
        mat4 = new float[16];
        mat3 = new float[9];
        CELESTIAL_AXIS = new Vector3f(0.0F, 0.766F, 0.643F);
        dirSign = 1.0F;
        projForPixel = new Matrix4f();
        tmpA = new Vector4f();
        tmpB = new Vector4f();
    }

}
