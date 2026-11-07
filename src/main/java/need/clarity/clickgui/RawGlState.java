package need.clarity.clickgui;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import org.lwjgl.opengl.GL33C;
import org.lwjgl.system.MemoryStack;

public final class RawGlState implements AutoCloseable {
    private static final int UNITS = 4;
    private int program;
    private int vao;
    private int arrayBuffer;
    private int uniformBuffer;
    private int activeTexture;
    private int readFb;
    private int drawFb;
    private int pixelUnpackBuffer;
    private int pixelPackBuffer;
    private final int[] tex2d;
    private final int[] samplers;
    private int unpackAlignment;
    private int unpackRowLength;
    private int unpackSkipPixels;
    private int unpackSkipRows;
    private int vpX;
    private int vpY;
    private int vpW;
    private int vpH;
    private boolean blend;
    private boolean depth;
    private boolean cull;
    private boolean stencil;
    private boolean scissor;
    private int blendSrcRgb;
    private int blendDstRgb;
    private int blendSrcA;
    private int blendDstA;
    private int blendEqRgb;
    private int blendEqA;
    private int cullMode;
    private int frontFace;
    private int depthFunc;
    private boolean depthMask;
    private int stencilWriteMask;
    private int stencilFunc;
    private int stencilRef;
    private int stencilValueMask;
    private int stencilFail;
    private int stencilZFail;
    private int stencilZPass;
    private final byte[] colorMask;
    private int ubo0;
    private long ubo0Start;
    private long ubo0Size;
    private final float[] clearColor;
    private int clearStencil;

    RawGlState() {
        this.tex2d = new int[4];
        this.samplers = new int[4];
        this.colorMask = new byte[4];
        this.clearColor = new float[4];
    }

    static RawGlState capture() {
        int var1 = 0;
        IntBuffer var2 = null;
        ByteBuffer var3 = null;
        RawGlState var0 = new RawGlState();
        var0.program = GL33C.glGetInteger(35725);
        var0.vao = GL33C.glGetInteger(34229);
        var0.arrayBuffer = GL33C.glGetInteger(34964);
        var0.uniformBuffer = GL33C.glGetInteger(35368);
        var0.activeTexture = GL33C.glGetInteger(34016);
        var0.readFb = GL33C.glGetInteger(36010);
        var0.drawFb = GL33C.glGetInteger(36006);
        var0.pixelUnpackBuffer = GL33C.glGetInteger(35055);
        var0.pixelPackBuffer = GL33C.glGetInteger(35053);
        var0.unpackAlignment = GL33C.glGetInteger(3317);
        var0.unpackRowLength = GL33C.glGetInteger(3314);
        var0.unpackSkipPixels = GL33C.glGetInteger(3316);
        var0.unpackSkipRows = GL33C.glGetInteger(3315);
        var1 = 0;
        while (true) {
            if (var1 >= 4) break;
            GL33C.glActiveTexture((33984 + var1));
            var0.tex2d[var1] = GL33C.glGetInteger(32873);
            var0.samplers[var1] = GL33C.glGetInteger(35097);
            var1++;
        }
        GL33C.glActiveTexture(var0.activeTexture);
        var1 = MemoryStack.stackPush();
        try {
            var2 = var1.mallocInt(4);
            GL33C.glGetIntegerv(2978, var2);
            var0.vpX = var2.get(0);
            var0.vpY = var2.get(1);
            var0.vpW = var2.get(2);
            var0.vpH = var2.get(3);
            var3 = var1.malloc(4);
            GL33C.glGetBooleanv(3107, var3);
            var3.get(var0.colorMask);
            ByteBuffer var4 = var1.malloc(1);
            GL33C.glGetBooleanv(2930, var4);
            var0.depthMask = (var4.get(0) != 0);
        } catch (Throwable ignored) {
        }
        if (var1 != null) {
            var1.close();
            if (var1 != null) {
                try {
                    var1.close();
                } catch (Throwable ignored) {
                }
                var2.addSuppressed(var3);
            }
            throw var2;
        }
        var0.ubo0 = GL33C.glGetIntegeri(35368, 0);
        var0.ubo0Start = GL33C.glGetInteger64i(35369, 0);
        var0.ubo0Size = GL33C.glGetInteger64i(35370, 0);
        GL33C.glGetFloatv(3106, var0.clearColor);
        var0.clearStencil = GL33C.glGetInteger(2961);
        var0.blend = GL33C.glIsEnabled(3042);
        var0.depth = GL33C.glIsEnabled(2929);
        var0.cull = GL33C.glIsEnabled(2884);
        var0.stencil = GL33C.glIsEnabled(2960);
        var0.scissor = GL33C.glIsEnabled(3089);
        var0.blendSrcRgb = GL33C.glGetInteger(32969);
        var0.blendDstRgb = GL33C.glGetInteger(32968);
        var0.blendSrcA = GL33C.glGetInteger(32971);
        var0.blendDstA = GL33C.glGetInteger(32970);
        var0.blendEqRgb = GL33C.glGetInteger(32777);
        var0.blendEqA = GL33C.glGetInteger(34877);
        var0.cullMode = GL33C.glGetInteger(2885);
        var0.frontFace = GL33C.glGetInteger(2886);
        var0.depthFunc = GL33C.glGetInteger(2932);
        var0.stencilWriteMask = GL33C.glGetInteger(2968);
        var0.stencilFunc = GL33C.glGetInteger(2962);
        var0.stencilRef = GL33C.glGetInteger(2967);
        var0.stencilValueMask = GL33C.glGetInteger(2963);
        var0.stencilFail = GL33C.glGetInteger(2964);
        var0.stencilZFail = GL33C.glGetInteger(2965);
        var0.stencilZPass = GL33C.glGetInteger(2966);
        return var0;
    }

    public void close() {
        GL33C.glBindFramebuffer(36008, this.readFb);
        GL33C.glBindFramebuffer(36009, this.drawFb);
        GL33C.glViewport(this.vpX, this.vpY, this.vpW, this.vpH);
        GL33C.glUseProgram(this.program);
        GL33C.glBindVertexArray(this.vao);
        GL33C.glBindBuffer(34962, this.arrayBuffer);
        if (this.ubo0 != 0) {
            if (this.ubo0Size > 0L) {
                GL33C.glBindBufferRange(35345, 0, this.ubo0, this.ubo0Start, this.ubo0Size);
            } else {
                GL33C.glBindBufferBase(35345, 0, this.ubo0);
            }
            GL33C.glBindBuffer(35345, this.uniformBuffer);
            int var1 = 0;
            while (true) {
                if (var1 >= 4) break;
                GL33C.glActiveTexture((33984 + var1));
                GL33C.glBindTexture(3553, this.tex2d[var1]);
                GL33C.glBindSampler(var1, this.samplers[var1]);
                var1++;
            }
            GL33C.glActiveTexture(this.activeTexture);
            GL33C.glBindBuffer(35052, this.pixelUnpackBuffer);
            GL33C.glBindBuffer(35051, this.pixelPackBuffer);
            GL33C.glPixelStorei(3317, this.unpackAlignment);
            GL33C.glPixelStorei(3314, this.unpackRowLength);
            GL33C.glPixelStorei(3316, this.unpackSkipPixels);
            GL33C.glPixelStorei(3315, this.unpackSkipRows);
            toggle(3042, this.blend);
            toggle(2929, this.depth);
            toggle(2884, this.cull);
            toggle(2960, this.stencil);
            toggle(3089, this.scissor);
            GL33C.glBlendFuncSeparate(this.blendSrcRgb, this.blendDstRgb, this.blendSrcA, this.blendDstA);
            GL33C.glBlendEquationSeparate(this.blendEqRgb, this.blendEqA);
            GL33C.glCullFace(this.cullMode);
            GL33C.glFrontFace(this.frontFace);
            GL33C.glDepthFunc(this.depthFunc);
            GL33C.glDepthMask(this.depthMask);
            GL33C.glStencilMask(this.stencilWriteMask);
            GL33C.glStencilFunc(this.stencilFunc, this.stencilRef, this.stencilValueMask);
            GL33C.glStencilOp(this.stencilFail, this.stencilZFail, this.stencilZPass);
            GL33C.glColorMask((this.colorMask[0] != 0), (this.colorMask[1] != 0), (this.colorMask[2] != 0), (this.colorMask[3] != 0));
            GL33C.glClearColor(this.clearColor[0], this.clearColor[1], this.clearColor[2], this.clearColor[3]);
            GL33C.glClearStencil(this.clearStencil);
            return;
        }
    }

    private static void toggle(int arg0, boolean arg1) {
        if (arg1) {
            GL33C.glEnable(arg0);
        } else {
            GL33C.glDisable(arg0);
        }
    }

}
