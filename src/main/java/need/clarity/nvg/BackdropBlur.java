package need.clarity.nvg;

import java.nio.ByteBuffer;
import org.lwjgl.opengl.GL33C;

public final class BackdropBlur {
    private static final String VERT = "#version 330 core\nout vec2 uv;\nvoid main() {\n    vec2 p = vec2((gl_VertexID << 1) & 2, gl_VertexID & 2);\n    uv = p;\n    gl_Position = vec4(p * 2.0 - 1.0, 0.0, 1.0);\n}\n";
    private static final String FRAG_DOWN = "#version 330 core\nuniform sampler2D uTex;\nuniform vec2 uTexel;\nin vec2 uv;\nout vec4 fragColor;\nvoid main() {\n    vec3 c = texture(uTex, uv + uTexel * vec2(-1.0, -1.0)).rgb\n           + texture(uTex, uv + uTexel * vec2( 1.0, -1.0)).rgb\n           + texture(uTex, uv + uTexel * vec2(-1.0,  1.0)).rgb\n           + texture(uTex, uv + uTexel * vec2( 1.0,  1.0)).rgb;\n    fragColor = vec4(c * 0.25, 1.0);\n}\n";
    private static final String FRAG_BLUR = "#version 330 core\nuniform sampler2D uTex;\nuniform vec2 uDir;\nin vec2 uv;\nout vec4 fragColor;\nvoid main() {\n    const float w0 = 0.2270270270;\n    const float w1 = 0.1945945946;\n    const float w2 = 0.1216216216;\n    const float w3 = 0.0540540541;\n    const float w4 = 0.0162162162;\n    vec3 c = texture(uTex, uv).rgb * w0;\n    c += (texture(uTex, uv + uDir)       .rgb + texture(uTex, uv - uDir)       .rgb) * w1;\n    c += (texture(uTex, uv + uDir * 2.0) .rgb + texture(uTex, uv - uDir * 2.0) .rgb) * w2;\n    c += (texture(uTex, uv + uDir * 3.0) .rgb + texture(uTex, uv - uDir * 3.0) .rgb) * w3;\n    c += (texture(uTex, uv + uDir * 4.0) .rgb + texture(uTex, uv - uDir * 4.0) .rgb) * w4;\n    fragColor = vec4(c, 1.0);\n}\n";
    private static final int DOWNSCALE = 4;
    private static boolean failed;
    private static int progDown;
    private static int progBlur;
    private static int vao;
    private static int sampler;
    private static int uDownTex;
    private static int uDownTexel;
    private static int uBlurTex;
    private static int uBlurDir;
    private static final int[] fbo;
    private static final int[] tex;
    private static int bw;
    private static int bh;
    private static int resultTex;

    private BackdropBlur() {
    }

    public static int textureId() {
        return resultTex;
    }

    public static int width() {
        return bw;
    }

    public static int height() {
        return bh;
    }

    public static void invalidate() {
        resultTex = 0;
    }

    public static int capture(int arg0, int arg1, int arg2, int arg3) {
        int var10 = 0;
        resultTex = 0;
        if (!failed) {
            if (arg0 != 0) {
                if (arg1 > 0) {
                    if (arg2 <= 0) {
                    }
                }
                return 0;
                try {
                    if (!init()) {
                    }
                } catch (Throwable ignored) {
                }
                return 0;
                try {
                    int var66 = (arg1 / 4);
                    int var23 = Math.max(1, var66);
                    int var103 = (arg2 / 4);
                    int var68 = Math.max(1, var103);
                    if (!(ensureTargets(var23, var68))) {
                    }
                } catch (Throwable ignored) {
                }
                return 0;
                try {
                    GL33C.glActiveTexture(33984);
                    int var28 = GL33C.glGetInteger(32873);
                    GL33C.glDisable(3042);
                    GL33C.glDisable(2929);
                    GL33C.glDisable(2884);
                    GL33C.glDisable(3089);
                    GL33C.glDisable(2960);
                    GL33C.glColorMask(true, true, true, true);
                    GL33C.glBindVertexArray(vao);
                    int var70 = sampler;
                    GL33C.glBindSampler(0, var70);
                    int var72 = fbo[0];
                    GL33C.glBindFramebuffer(36160, var72);
                    int var106 = bw;
                    int var126 = bh;
                    GL33C.glViewport(0, 0, var106, var126);
                    GL33C.glUseProgram(progDown);
                    GL33C.glUniform1i(uDownTex, 0);
                    int var41 = uDownTexel;
                    float var108 = ((float) arg1);
                    float var76 = (1.0F / var108);
                    float var128 = ((float) arg2);
                    float var110 = (1.0F / var128);
                    GL33C.glUniform2f(var41, var76, var110);
                    GL33C.glBindTexture(3553, arg0);
                    GL33C.glDrawArrays(4, 0, 3);
                    int var80 = Math.min(4, arg3);
                    int var45 = Math.max(1, var80);
                    GL33C.glUseProgram(progBlur);
                    GL33C.glUniform1i(uBlurTex, 0);
                    var10 = 0;
                    while (true) {
                        if (var10 >= var45) break;
                        float var85 = (((float) var10) * 0.9F);
                        float var51 = (1.0F + var85);
                        int var87 = fbo[1];
                        GL33C.glBindFramebuffer(36160, var87);
                        int var89 = tex[0];
                        GL33C.glBindTexture(3553, var89);
                        int var54 = uBlurDir;
                        float var117 = ((float) bw);
                        float var91 = (var51 / var117);
                        GL33C.glUniform2f(var54, var91, 0.0F);
                        GL33C.glDrawArrays(4, 0, 3);
                        int var94 = fbo[0];
                        GL33C.glBindFramebuffer(36160, var94);
                        int var96 = tex[1];
                        GL33C.glBindTexture(3553, var96);
                        int var58 = uBlurDir;
                        float var130 = ((float) bh);
                        float var123 = (var51 / var130);
                        GL33C.glUniform2f(var58, 0.0F, var123);
                        GL33C.glDrawArrays(4, 0, 3);
                        var10 = (var10 + 1);
                    }
                    GL33C.glBindTexture(3553, var28);
                    GL33C.glBindSampler(0, 0);
                    resultTex = tex[0];
                    int var64 = resultTex;
                } catch (Throwable ignored) {
                }
                return var64;
            }
        }
    }

    public static void dispose() {
        int var3 = 0;
        resultTex = 0;
        try {
            var3 = 0;
            while (true) {
                if (var3 >= 2) break;
                int[] var6 = fbo;
                int var27 = var3;
                if (var6[var27] != 0) {
                    int[] var8 = fbo;
                    int var28 = var3;
                    GL33C.glDeleteFramebuffers(var8[var28]);
                }
                int[] var10 = tex;
                int var29 = var3;
                if (var10[var29] != 0) {
                    int[] var12 = tex;
                    int var30 = var3;
                    GL33C.glDeleteTextures(var12[var30]);
                }
                int[] var14 = fbo;
                int var31 = var3;
                var14[var31] = 0;
                int[] var15 = tex;
                int var32 = var3;
                var15[var32] = 0;
                var3 = (var3 + 1);
            }
            if (progDown != 0) {
                GL33C.glDeleteProgram(progDown);
            }
            if (progBlur != 0) {
                GL33C.glDeleteProgram(progBlur);
            }
            if (vao != 0) {
                GL33C.glDeleteVertexArrays(vao);
            }
            if (sampler != 0) {
                GL33C.glDeleteSamplers(sampler);
            }
        } catch (Throwable ignored) {
        }
        sampler = 0;
        vao = 0;
        progBlur = 0;
        progDown = 0;
        bh = 0;
        bw = 0;
    }

    private static boolean init() {
        if (progDown != 0) {
            return true;
        }
        int var7 = compile(35633, "#version 330 core\nout vec2 uv;\nvoid main() {\n    vec2 p = vec2((gl_VertexID << 1) & 2, gl_VertexID & 2);\n    uv = p;\n    gl_Position = vec4(p * 2.0 - 1.0, 0.0, 1.0);\n}\n");
        int var9 = compile(35632, "#version 330 core\nuniform sampler2D uTex;\nuniform vec2 uTexel;\nin vec2 uv;\nout vec4 fragColor;\nvoid main() {\n    vec3 c = texture(uTex, uv + uTexel * vec2(-1.0, -1.0)).rgb\n           + texture(uTex, uv + uTexel * vec2( 1.0, -1.0)).rgb\n           + texture(uTex, uv + uTexel * vec2(-1.0,  1.0)).rgb\n           + texture(uTex, uv + uTexel * vec2( 1.0,  1.0)).rgb;\n    fragColor = vec4(c * 0.25, 1.0);\n}\n");
        int var11 = compile(35632, "#version 330 core\nuniform sampler2D uTex;\nuniform vec2 uDir;\nin vec2 uv;\nout vec4 fragColor;\nvoid main() {\n    const float w0 = 0.2270270270;\n    const float w1 = 0.1945945946;\n    const float w2 = 0.1216216216;\n    const float w3 = 0.0540540541;\n    const float w4 = 0.0162162162;\n    vec3 c = texture(uTex, uv).rgb * w0;\n    c += (texture(uTex, uv + uDir)       .rgb + texture(uTex, uv - uDir)       .rgb) * w1;\n    c += (texture(uTex, uv + uDir * 2.0) .rgb + texture(uTex, uv - uDir * 2.0) .rgb) * w2;\n    c += (texture(uTex, uv + uDir * 3.0) .rgb + texture(uTex, uv - uDir * 3.0) .rgb) * w3;\n    c += (texture(uTex, uv + uDir * 4.0) .rgb + texture(uTex, uv - uDir * 4.0) .rgb) * w4;\n    fragColor = vec4(c, 1.0);\n}\n");
        if (var7 != 0) {
            if (var9 != 0) {
                if (var11 == 0) {
                }
            }
            failed = true;
            return false;
            progDown = link(var7, var9);
            progBlur = link(var7, var11);
            GL33C.glDeleteShader(var7);
            GL33C.glDeleteShader(var9);
            GL33C.glDeleteShader(var11);
            if (progDown != 0) {
                if (progBlur == 0) {
                }
            }
            failed = true;
            return false;
            uDownTex = GL33C.glGetUniformLocation(progDown, "uTex");
            uDownTexel = GL33C.glGetUniformLocation(progDown, "uTexel");
            uBlurTex = GL33C.glGetUniformLocation(progBlur, "uTex");
            uBlurDir = GL33C.glGetUniformLocation(progBlur, "uDir");
            vao = GL33C.glGenVertexArrays();
            sampler = GL33C.glGenSamplers();
            GL33C.glSamplerParameteri(sampler, 10241, 9729);
            GL33C.glSamplerParameteri(sampler, 10240, 9729);
            GL33C.glSamplerParameteri(sampler, 10242, 33071);
            GL33C.glSamplerParameteri(sampler, 10243, 33071);
            return true;
        }
    }

    private static boolean ensureTargets(int arg0, int arg1) {
        int var11 = 0;
        if (bw == arg0) {
            if (bh == arg1) {
                if (fbo[0] != 0) {
                    return true;
                }
            }
        }
        var11 = 0;
        while (true) {
            if (var11 >= 2) break;
            int[] var24 = fbo;
            int var57 = var11;
            if (var24[var57] != 0) {
                int[] var26 = fbo;
                int var58 = var11;
                GL33C.glDeleteFramebuffers(var26[var58]);
            }
            int[] var28 = tex;
            int var59 = var11;
            if (var28[var59] != 0) {
                int[] var30 = tex;
                int var60 = var11;
                GL33C.glDeleteTextures(var30[var60]);
            }
            int[] var32 = tex;
            int var61 = var11;
            int var4 = GL33C.glGenTextures();
            var32[var61] = var4;
            GL33C.glActiveTexture(33984);
            int var35 = GL33C.glGetInteger(32873);
            int[] var62 = tex;
            int var76 = var11;
            int var63 = var62[var76];
            GL33C.glBindTexture(3553, var63);
            ByteBuffer var89 = ((ByteBuffer) null);
            GL33C.glTexImage2D(3553, 0, 32856, arg0, arg1, 0, 6408, 5121, var89);
            GL33C.glTexParameteri(3553, 10241, 9729);
            GL33C.glTexParameteri(3553, 10240, 9729);
            GL33C.glTexParameteri(3553, 10242, 33071);
            GL33C.glTexParameteri(3553, 10243, 33071);
            GL33C.glBindTexture(3553, var35);
            int[] var43 = fbo;
            int var70 = var11;
            int var82 = GL33C.glGenFramebuffers();
            var43[var70] = var82;
            int var45 = GL33C.glGetInteger(36006);
            int[] var71 = fbo;
            int var83 = var11;
            int var72 = var71[var83];
            GL33C.glBindFramebuffer(36160, var72);
            int[] var85 = tex;
            int var87 = var11;
            int var86 = var85[var87];
            GL33C.glFramebufferTexture2D(36160, 36064, 3553, var86, 0);
            int var49 = GL33C.glCheckFramebufferStatus(36160);
            GL33C.glBindFramebuffer(36160, var45);
            if (var49 != 36053) {
                failed = true;
                return false;
            }
            var11 = (var11 + 1);
        }
        bw = arg0;
        bh = arg1;
        return true;
    }

    private static int compile(int arg0, String arg1) {
        int var5 = GL33C.glCreateShader(arg0);
        GL33C.glShaderSource(var5, arg1);
        GL33C.glCompileShader(var5);
        if (GL33C.glGetShaderi(var5, 35713) == 0) {
            GL33C.glDeleteShader(var5);
            return 0;
        }
        return var5;
    }

    private static int link(int arg0, int arg1) {
        int var2 = GL33C.glCreateProgram();
        GL33C.glAttachShader(var2, arg0);
        GL33C.glAttachShader(var2, arg1);
        GL33C.glLinkProgram(var2);
        if (GL33C.glGetProgrami(var2, 35714) == 0) {
            GL33C.glDeleteProgram(var2);
            return 0;
        }
        return var2;
    }

    static {
        fbo = new int[2];
        tex = new int[2];
    }

}
