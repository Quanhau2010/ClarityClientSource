package need.clarity.render;

import com.mojang.blaze3d.opengl.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.GpuTextureView;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public final class RawGlStateCapture implements AutoCloseable {
    private static final int TRACKED_TEXTURE_UNITS = 3;
    private final int drawFramebuffer;
    private final int readFramebuffer;
    private final int viewportX;
    private final int viewportY;
    private final int viewportWidth;
    private final int viewportHeight;
    private final int scissorX;
    private final int scissorY;
    private final int scissorWidth;
    private final int scissorHeight;
    private final int program;
    private final int vertexArray;
    private final int arrayBuffer;
    private final int activeTexture;
    private final int[] textureBindings;
    private final boolean blend;
    private final boolean depthTest;
    private final boolean cull;
    private final boolean scissorEnabled;
    private final boolean depthMask;
    private final boolean colorMaskRed;
    private final boolean colorMaskGreen;
    private final boolean colorMaskBlue;
    private final boolean colorMaskAlpha;
    private final int blendSrcRgb;
    private final int blendDstRgb;
    private final int blendSrcAlpha;
    private final int blendDstAlpha;
    private final int blendEquationRgb;
    private final int blendEquationAlpha;
    private final int depthFunction;
    private final int cullFace;
    private final int frontFace;
    private final float clearRed;
    private final float clearGreen;
    private final float clearBlue;
    private final float clearAlpha;
    private final GpuTextureView outputColorOverride;
    private final GpuTextureView outputDepthOverride;
    private boolean closed;

    private RawGlStateCapture() {
        this.textureBindings = new int[3];
        RenderSystem.assertOnRenderThread();
        this.drawFramebuffer = GL11.glGetInteger(36006);
        this.readFramebuffer = GL11.glGetInteger(36010);
        this.program = GL11.glGetInteger(35725);
        this.vertexArray = GL11.glGetInteger(34229);
        this.arrayBuffer = GL11.glGetInteger(34964);
        IntBuffer var1 = BufferUtils.createIntBuffer(4);
        GL11.glGetIntegerv(2978, var1);
        this.viewportX = var1.get(0);
        this.viewportY = var1.get(1);
        this.viewportWidth = var1.get(2);
        this.viewportHeight = var1.get(3);
        var1.clear();
        GL11.glGetIntegerv(3088, var1);
        this.scissorX = var1.get(0);
        this.scissorY = var1.get(1);
        this.scissorWidth = var1.get(2);
        this.scissorHeight = var1.get(3);
        this.activeTexture = GL11.glGetInteger(34016);
        int var2 = 0;
        while (true) {
            if (var2 >= 3) break;
            GlStateManager._activeTexture((33984 + var2));
            this.textureBindings[var2] = GL11.glGetInteger(32873);
            var2++;
        }
        GlStateManager._activeTexture(this.activeTexture);
        this.blend = GL11.glIsEnabled(3042);
        this.depthTest = GL11.glIsEnabled(2929);
        this.cull = GL11.glIsEnabled(2884);
        this.scissorEnabled = GL11.glIsEnabled(3089);
        this.depthMask = GL11.glGetBoolean(2930);
        var2 = BufferUtils.createByteBuffer(4);
        GL11.glGetBooleanv(3107, var2);
        this.colorMaskRed = (var2.get(0) != 0);
        this.colorMaskGreen = (var2.get(1) != 0);
        this.colorMaskBlue = (var2.get(2) != 0);
        this.colorMaskAlpha = (var2.get(3) != 0);
        this.blendSrcRgb = GL11.glGetInteger(32969);
        this.blendDstRgb = GL11.glGetInteger(32968);
        this.blendSrcAlpha = GL11.glGetInteger(32971);
        this.blendDstAlpha = GL11.glGetInteger(32970);
        this.blendEquationRgb = GL11.glGetInteger(32777);
        this.blendEquationAlpha = GL11.glGetInteger(34877);
        this.depthFunction = GL11.glGetInteger(2932);
        this.cullFace = GL11.glGetInteger(2885);
        this.frontFace = GL11.glGetInteger(2886);
        FloatBuffer var3 = BufferUtils.createFloatBuffer(4);
        GL11.glGetFloatv(3106, var3);
        this.clearRed = var3.get(0);
        this.clearGreen = var3.get(1);
        this.clearBlue = var3.get(2);
        this.clearAlpha = var3.get(3);
        this.outputColorOverride = RenderSystem.outputColorTextureOverride;
        this.outputDepthOverride = RenderSystem.outputDepthTextureOverride;
    }

    public static RawGlStateCapture capture() {
        return new RawGlStateCapture();
    }

    public void close() {
        if (this.closed) {
            return;
        }
        this.closed = true;
        if (GL30.glIsFramebuffer(this.drawFramebuffer)) {
        } else {
        }
        GL30.glBindFramebuffer(this.drawFramebuffer, 0);
        if (GL30.glIsFramebuffer(this.readFramebuffer)) {
        } else {
        }
        GL30.glBindFramebuffer(this.readFramebuffer, 0);
        GlStateManager._viewport(this.viewportX, this.viewportY, this.viewportWidth, this.viewportHeight);
        GlStateManager._scissorBox(this.scissorX, this.scissorY, this.scissorWidth, this.scissorHeight);
        int var1 = 0;
        while (true) {
            if (var1 >= 3) break;
            GlStateManager._activeTexture((33984 + var1));
            GlStateManager._bindTexture(this.textureBindings[var1]);
            var1++;
        }
        GlStateManager._activeTexture(this.activeTexture);
        GlStateManager._blendFuncSeparate(this.blendSrcRgb, this.blendDstRgb, this.blendSrcAlpha, this.blendDstAlpha);
        GL20.glBlendEquationSeparate(this.blendEquationRgb, this.blendEquationAlpha);
        GlStateManager._depthFunc(this.depthFunction);
        GlStateManager._depthMask(this.depthMask);
        GL11.glColorMask(this.colorMaskRed, this.colorMaskGreen, this.colorMaskBlue, this.colorMaskAlpha);
        GL11.glCullFace(this.cullFace);
        GL11.glFrontFace(this.frontFace);
        GL11.glClearColor(this.clearRed, this.clearGreen, this.clearBlue, this.clearAlpha);
        setBlend(this.blend);
        setDepthTest(this.depthTest);
        setCull(this.cull);
        setScissor(this.scissorEnabled);
        GL20.glUseProgram(this.program);
        GL30.glBindVertexArray(this.vertexArray);
        GL15.glBindBuffer(34962, this.arrayBuffer);
        RenderSystem.outputColorTextureOverride = this.outputColorOverride;
        RenderSystem.outputDepthTextureOverride = this.outputDepthOverride;
    }

    private static void setBlend(boolean arg0) {
        if (arg0) {
            GlStateManager._enableBlend();
        } else {
            GlStateManager._disableBlend();
        }
    }

    private static void setDepthTest(boolean arg0) {
        if (arg0) {
            GlStateManager._enableDepthTest();
        } else {
            GlStateManager._disableDepthTest();
        }
    }

    private static void setCull(boolean arg0) {
        if (arg0) {
            GlStateManager._enableCull();
        } else {
            GlStateManager._disableCull();
        }
    }

    private static void setScissor(boolean arg0) {
        if (arg0) {
            GlStateManager._enableScissorTest();
        } else {
            GlStateManager._disableScissorTest();
        }
    }

}
