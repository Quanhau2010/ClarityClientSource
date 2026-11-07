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
import java.nio.IntBuffer;
import java.nio.charset.StandardCharsets;
import need.clarity.util.ThemeColors;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.resources.Identifier;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL33;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

public final class ShaderCapes {
    public static final String[] IDS;
    public static final Identifier TEX_ID;
    private static final int W = 512;
    private static final int H = 256;
    private static volatile int style;
    private static int program;
    private static int vao;
    private static int vbo;
    private static int tex;
    private static int fbo;
    private static int logoTex;
    private static int locRes;
    private static int locTime;
    private static int locStyle;
    private static int locA;
    private static int locB;
    private static int locLogo;
    private static boolean failed;
    private static boolean registered;
    private static long lastDraw;
    private static final long START;

    private ShaderCapes() {
    }

    public static boolean isShaderCape(String arg0) {
        return (styleOf(arg0) >= 0);
    }

    public static int styleOf(String arg0) {
        int var1 = 0;
        while (true) {
            if (var1 >= IDS.length) break;
            if (IDS[var1].equals(arg0)) {
                return var1;
            }
            var1++;
        }
        return -1;
    }

    public static Identifier select(String arg0) {
        style = styleOf(arg0);
        if (style >= 0) {
            if (!failed) {
            } else {
            }
            return null;
        }
    }

    public static void frame() {
        if (style >= 0) {
            if (failed) {
            }
        }
        return;
        long var0 = System.nanoTime();
        if (registered) {
            if ((var0 - lastDraw) < 16000000L) {
                return;
            }
            lastDraw = var0;
            try {
                draw(var0);
            } catch (Throwable ignored) {
            }
            failed = true;
            return;
        }
    }

    private static void draw(long arg0) {
        int var3 = 0;
        int var4 = 0;
        RenderSystem.assertOnRenderThread();
        RawGlStateCapture var2 = RawGlStateCapture.capture();
        try {
            if (!ensure()) {
            }
        } catch (Throwable ignored) {
        }
        if (var2 != null) {
            var2.close();
        }
        return;
        try {
            GL30.glBindFramebuffer(36160, fbo);
            GL11.glDrawBuffer(36064);
            GlStateManager._viewport(0, 0, 512, 256);
            GlStateManager._disableScissorTest();
            GlStateManager._disableDepthTest();
            GlStateManager._disableBlend();
            GlStateManager._disableCull();
            GL11.glColorMask(true, true, true, true);
            GlStateManager._activeTexture(33984);
            var3 = GL11.glGetInteger(35097);
            GL33.glBindSampler(0, 0);
            GlStateManager._bindTexture(logoTex);
            GL20.glUseProgram(program);
            var4 = ThemeColors.getAccentRgb();
            int var5 = ThemeColors.getAccent2Rgb();
            GL20.glUniform2f(locRes, 512.0F, 256.0F);
            GL20.glUniform1f(locTime, ((float) ((((double) (arg0 - START)) / 1.0E9) % 3600.0)));
            GL20.glUniform1i(locStyle, style);
            GL20.glUniform3f(locA, (((float) ((var4 >> 16) & 255)) / 255.0F), (((float) ((var4 >> 8) & 255)) / 255.0F), (((float) (var4 & 255)) / 255.0F));
            GL20.glUniform3f(locB, (((float) ((var5 >> 16) & 255)) / 255.0F), (((float) ((var5 >> 8) & 255)) / 255.0F), (((float) (var5 & 255)) / 255.0F));
            GL20.glUniform1i(locLogo, 0);
            GL30.glBindVertexArray(vao);
            GL11.glDrawArrays(4, 0, 3);
            GL30.glBindVertexArray(0);
            GL33.glBindSampler(0, var3);
        } catch (Throwable ignored) {
        }
        if (var2 != null) {
            var2.close();
            if (var2 != null) {
                try {
                    var2.close();
                } catch (Throwable ignored) {
                }
                var3.addSuppressed(var4);
            }
            throw var3;
        }
    }

    private static boolean ensure() {
        if (program != 0) {
            if (registered) {
                return true;
            }
            if (program == 0) {
                program = link(read("shaders/cape/cape.vsh"), read("shaders/cape/cape.fsh"));
                locRes = GL20.glGetUniformLocation(program, "uRes");
                locTime = GL20.glGetUniformLocation(program, "uTime");
                locStyle = GL20.glGetUniformLocation(program, "uStyle");
                locA = GL20.glGetUniformLocation(program, "uA");
                locB = GL20.glGetUniformLocation(program, "uB");
                locLogo = GL20.glGetUniformLocation(program, "uLogo");
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
                logoTex = loadLogo();
            }
            tex = GL11.glGenTextures();
            GlStateManager._activeTexture(33984);
            GlStateManager._bindTexture(tex);
            GL11.glTexParameteri(3553, 33085, 0);
            int var0 = GL11.glGetInteger(35055);
            GL15.glBindBuffer(35052, 0);
            GL11.glTexImage2D(3553, 0, 32856, 512, 256, 0, 6408, 5121, ((ByteBuffer) null));
            GL15.glBindBuffer(35052, var0);
            fbo = GL30.glGenFramebuffers();
            GL30.glBindFramebuffer(36160, fbo);
            GL30.glFramebufferTexture2D(36160, 36064, 3553, tex, 0);
            if (GL30.glCheckFramebufferStatus(36160) != 36053) {
                failed = true;
                return false;
            }
            ShaderCapes.BridgeGlTexture var1 = new ShaderCapes.BridgeGlTexture(tex);
            GpuTextureView var2 = RenderSystem.getDevice().createTextureView(var1);
            GpuSampler var3 = RenderSystem.getSamplerCache().getClampToEdge(FilterMode.LINEAR);
            Minecraft.getInstance().getTextureManager().register(TEX_ID, new ShaderCapes.BridgeTexture(var1, var2, var3));
            registered = true;
            return true;
        }
    }

    private static int loadLogo() {
        InputStream var1 = null;
        int var2 = 0;
        IntBuffer var3 = null;
        IntBuffer var4 = null;
        int var7 = 0;
        try {
            var1 = ShaderCapes.class.getResourceAsStream("/assets/clarity/textures/fx/clarity_logo.png");
            try {
                if (var1 == null) {
                    var2 = 0;
                }
            } catch (Throwable ignored) {
            }
            if (var1 != null) {
                var1.close();
            }
            return var2;
            try {
                try {
                    byte[] var0 = var1.readAllBytes();
                } catch (Exception ignored) {
                }
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
            var1 = MemoryUtil.memAlloc(var0.length);
            var1.put(var0).flip();
            var2 = MemoryStack.stackPush();
            try {
                var3 = var2.mallocInt(1);
                var4 = var2.mallocInt(1);
                IntBuffer var5 = var2.mallocInt(1);
                ByteBuffer var6 = STBImage.stbi_load_from_memory(var1, var3, var4, var5, 4);
                if (var6 == null) {
                    var7 = 0;
                }
            } catch (Throwable ignored) {
            }
            if (var2 != null) {
                var2.close();
            }
            MemoryUtil.memFree(var1);
            return var7;
            try {
                var7 = GL11.glGenTextures();
                GlStateManager._activeTexture(33984);
                GlStateManager._bindTexture(var7);
                GL11.glTexParameteri(3553, 10241, 9729);
                GL11.glTexParameteri(3553, 10240, 9729);
                GL11.glTexParameteri(3553, 10242, 33071);
                GL11.glTexParameteri(3553, 10243, 33071);
                GL11.glTexParameteri(3553, 33085, 0);
                int var8 = GL11.glGetInteger(35055);
                GL15.glBindBuffer(35052, 0);
                int var9 = GL11.glGetInteger(3317);
                int var10 = GL11.glGetInteger(3314);
                GL11.glPixelStorei(3317, 4);
                GL11.glPixelStorei(3314, 0);
                GL11.glTexImage2D(3553, 0, 32856, var3.get(0), var4.get(0), 0, 6408, 5121, var6);
                GL11.glPixelStorei(3317, var9);
                GL11.glPixelStorei(3314, var10);
                GL15.glBindBuffer(35052, var8);
                int var11 = var7;
                STBImage.stbi_image_free(var6);
            } catch (Throwable ignored) {
            }
            if (var2 != null) {
                var2.close();
            }
            MemoryUtil.memFree(var1);
            return var11;
            try {
                STBImage.stbi_image_free(var6);
                throw var12;
                if (var2 != null) {
                    try {
                        var2.close();
                    } catch (Throwable ignored) {
                    }
                    var3.addSuppressed(var4);
                }
                throw var3;
                MemoryUtil.memFree(var1);
                throw var13;
            }
        } catch (Exception ignored) {
        }
    }

    private static int link(String arg0, String arg1) {
        int var2 = compile(35633, arg0);
        int var3 = compile(35632, arg1);
        int var4 = GL20.glCreateProgram();
        GL20.glAttachShader(var4, var2);
        GL20.glAttachShader(var4, var3);
        GL20.glBindAttribLocation(var4, 0, "Position");
        GL20.glLinkProgram(var4);
        GL20.glDetachShader(var4, var2);
        GL20.glDetachShader(var4, var3);
        GL20.glDeleteShader(var2);
        GL20.glDeleteShader(var3);
        if (GL20.glGetProgrami(var4, 35714) == 0) {
            GL20.glDeleteProgram(var4);
            throw new RuntimeException("cape shader link failed");
        }
        return var4;
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
        InputStream var1 = null;
        String var2 = null;
        try {
            var1 = ShaderCapes.class.getResourceAsStream("/assets/clarity/" + (arg0));
            try {
                if (var1 == null) {
                    throw new RuntimeException("missing " + (arg0));
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
        } catch (IOException ignored) {
        }
    }

    static {
        new String[5][0] = "clarity_aurora";
        new String[5][1] = "clarity_nebula";
        new String[5][2] = "clarity_flow";
        new String[5][3] = "clarity_circuit";
        new String[5][4] = "clarity_prism";
        IDS = new String[5];
        TEX_ID = Identifier.fromNamespaceAndPath("clarity", "capes/shader_cape");
        style = -1;
        program = 0;
        vao = 0;
        vbo = 0;
        tex = 0;
        fbo = 0;
        logoTex = 0;
        failed = false;
        registered = false;
        lastDraw = 0L;
        START = System.nanoTime();
    }

    public final class BridgeGlTexture extends GlTexture {
        BridgeGlTexture(int arg0) {
            super(12, "clarity-shader-cape", TextureFormat.RGBA8, 512, 256, 1, 1, arg0);
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
