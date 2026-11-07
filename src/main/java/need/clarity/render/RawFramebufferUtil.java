package need.clarity.render;

import com.mojang.blaze3d.opengl.GlTexture;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.systems.CommandEncoder;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.GpuTexture;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

public final class RawFramebufferUtil {
    private static int fbo;
    private static boolean loggedBadTexture;
    private static boolean loggedIncomplete;

    private RawFramebufferUtil() {
    }

    public static void copy(RenderTarget arg0, RenderTarget arg1, boolean arg2) {
        RenderSystem.assertOnRenderThread();
        if (isUsable(arg0)) {
            if (!isUsable(arg1)) {
            }
        }
        return;
        CommandEncoder var4 = RenderSystem.getDevice().createCommandEncoder();
        int var5 = Math.min(arg0.width, arg1.width);
        int var6 = Math.min(arg0.height, arg1.height);
        GpuTexture var7 = arg0.getColorTexture();
        GpuTexture var8 = arg1.getColorTexture();
        if (var7 != null) {
            if (var8 != null) {
                var4.copyTextureToTexture(var7, var8, 0, 0, 0, 0, 0, var5, var6);
            }
            if (arg2) {
                GpuTexture var9 = arg0.getDepthTexture();
                GpuTexture var10 = arg1.getDepthTexture();
                if (var9 != null) {
                    if (var10 != null) {
                        var4.copyTextureToTexture(var9, var10, 0, 0, 0, 0, 0, var5, var6);
                    }
                    return;
                }
            }
        }
    }

    public static void clear(RenderTarget arg0) {
        RenderSystem.assertOnRenderThread();
        if (!isUsable(arg0)) {
            return;
        }
        CommandEncoder var2 = RenderSystem.getDevice().createCommandEncoder();
        GpuTexture var3 = arg0.getColorTexture();
        if (var3 != null) {
            if (!var3.isClosed()) {
                var2.clearColorTexture(var3, 0);
            }
            return;
        }
    }

    public static void clearColorAndDepth(RenderTarget arg0) {
        RenderSystem.assertOnRenderThread();
        if (!isUsable(arg0)) {
            return;
        }
        CommandEncoder var2 = RenderSystem.getDevice().createCommandEncoder();
        GpuTexture var3 = arg0.getColorTexture();
        GpuTexture var4 = arg0.getDepthTexture();
        if (var3 != null) {
            if (!var3.isClosed()) {
                if (var4 != null) {
                    if (!var4.isClosed()) {
                        var2.clearColorAndDepthTextures(var3, 0, var4, 1.0);
                    } else {
                        var2.clearColorTexture(var3, 0);
                    }
                    return;
                }
            }
        }
    }

    public static boolean bindDirect(RenderTarget arg0) {
        RenderSystem.assertOnRenderThread();
        if (!isUsable(arg0)) {
            return false;
        }
        int var1 = GL11.glGetInteger(36006);
        int var3 = getGlId(arg0.getColorTexture());
        GpuTexture var4 = arg0.getDepthTexture();
        if (var4 != null) {
            if (!var4.isClosed()) {
            } else {
            }
            int var5 = 0;
            if (var3 != 0) {
                if (GL11.glIsTexture(var3)) {
                    if (var5 != 0) {
                        if (!GL11.glIsTexture(var5)) {
                        }
                    }
                }
                if (!loggedBadTexture) {
                    loggedBadTexture = true;
                }
                return false;
                int var6 = 0;
                while (true) {
                    if (var6 >= 2) break;
                    ensureFbo();
                    attach(var3, var5);
                    if (GL30.glCheckFramebufferStatus(36160) == 36053) {
                        GL11.glDrawBuffer(36064);
                        GL11.glViewport(0, 0, arg0.width, arg0.height);
                        return true;
                    }
                    if (!loggedIncomplete) {
                        loggedIncomplete = true;
                    }
                    deleteFbo();
                    var6++;
                }
                if (GL30.glIsFramebuffer(var1)) {
                } else {
                }
                GL30.glBindFramebuffer(var1, 0);
                return false;
            }
        }
    }

    private static void attach(int arg0, int arg1) {
        GL30.glBindFramebuffer(36160, fbo);
        GL30.glFramebufferTexture2D(36160, 36064, 3553, arg0, 0);
        GL30.glFramebufferTexture2D(36160, 36096, 3553, arg1, 0);
    }

    public static void invalidate() {
        if (fbo == 0) {
            return;
        }
        int var0 = GL11.glGetInteger(36006);
        int var1 = GL11.glGetInteger(36010);
        GL30.glBindFramebuffer(36160, fbo);
        GL30.glFramebufferTexture2D(36160, 36064, 3553, 0, 0);
        GL30.glFramebufferTexture2D(36160, 36096, 3553, 0, 0);
        if (var0 == fbo) {
        } else {
        }
        GL30.glBindFramebuffer(0, var0);
        if (var1 == fbo) {
        } else {
        }
        GL30.glBindFramebuffer(0, var1);
    }

    public static void dispose() {
        deleteFbo();
    }

    private static void deleteFbo() {
        if (fbo == 0) {
            return;
        }
        if (GL11.glGetInteger(36006) == fbo) {
            GL30.glBindFramebuffer(36009, 0);
        }
        if (GL11.glGetInteger(36010) == fbo) {
            GL30.glBindFramebuffer(36008, 0);
        }
        GL30.glDeleteFramebuffers(fbo);
        fbo = 0;
    }

    public static void unbind() {
        GL30.glBindFramebuffer(36160, 0);
    }

    public static int getColorTextureId(RenderTarget arg0) {
        if (arg0 == null) {
            return 0;
        }
        return getGlId(arg0.getColorTexture());
    }

    public static int getDepthTextureId(RenderTarget arg0) {
        if (arg0 == null) {
            return 0;
        }
        return getGlId(arg0.getDepthTexture());
    }

    private static boolean isUsable(RenderTarget arg0) {
        if (arg0 != null) {
            if (arg0.width > 0) {
                if (arg0.height > 0) {
                    return (arg0.getColorTexture() != null);
                }
            }
        }
    }

    private static void ensureFbo() {
        if (fbo == 0) {
            fbo = GL30.glGenFramebuffers();
        }
    }

    public static int getGlId(GpuTexture arg0) {
        GlTexture var1 = null;
        if (arg0 != null) {
            if (arg0.isClosed()) {
            }
        }
        return 0;
        if ((arg0 instanceof GlTexture)) {
            var1 = ((GlTexture) arg0);
            return var1.glId();
        }
        try {
            var1 = arg0.getClass().getMethod("glId", new Class[0]);
        } catch (Exception ignored) {
        }
        return ((Integer) var1.invoke(arg0, new Object[0]));
    }

    static {
        fbo = 0;
        loggedBadTexture = false;
        loggedIncomplete = false;
    }

}
