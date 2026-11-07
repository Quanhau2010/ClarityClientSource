package need.clarity.render.sky;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import org.lwjgl.opengl.GL20;

public final class SkyProgram {
    private static final String DIR = "/assets/clarity/shaders/sky/";
    final int id;
    private final Map locations;

    private SkyProgram(int arg0) {
        this.locations = new HashMap();
        this.id = arg0;
    }

    static SkyProgram preset(String arg0) {
        String var1 = "#version 330 core\n" + (read("common.glsl")) + "\n" + (read((arg0) + ".fsh"));
        return link(read("sky.vsh"), var1, arg0);
    }

    static SkyProgram upscale() {
        return link(read("upscale.vsh"), read("upscale.fsh"), "upscale");
    }

    private static SkyProgram link(String arg0, String arg1, String arg2) {
        int var5 = 0;
        int var3 = compile(35633, arg0, (arg2) + ".vsh");
        try {
            int var4 = compile(35632, arg1, (arg2) + ".fsh");
        } catch (RuntimeException ignored) {
        }
        var5 = GL20.glCreateProgram();
        GL20.glAttachShader(var5, var3);
        GL20.glAttachShader(var5, var4);
        GL20.glBindAttribLocation(var5, 0, "Position");
        GL20.glLinkProgram(var5);
        GL20.glDetachShader(var5, var3);
        GL20.glDetachShader(var5, var4);
        GL20.glDeleteShader(var3);
        GL20.glDeleteShader(var4);
        if (GL20.glGetProgrami(var5, 35714) == 0) {
            String var6 = GL20.glGetProgramInfoLog(var5);
            GL20.glDeleteProgram(var5);
            throw new RuntimeException("link failed (" + (arg2) + "): " + (var6));
        }
        return new SkyProgram(var5);
    }

    private static int compile(int arg0, String arg1, String arg2) {
        int var3 = GL20.glCreateShader(arg0);
        GL20.glShaderSource(var3, arg1);
        GL20.glCompileShader(var3);
        if (GL20.glGetShaderi(var3, 35713) == 0) {
            String var4 = GL20.glGetShaderInfoLog(var3);
            GL20.glDeleteShader(var3);
            throw new RuntimeException("compile failed (" + (arg2) + "): " + (var4));
        }
        return var3;
    }

    private static String read(String arg0) {
        String var2 = null;
        InputStream var1 = SkyProgram.class.getResourceAsStream("/assets/clarity/shaders/sky/" + (arg0));
        try {
            if (var1 == null) {
                throw new IOException("missing shader /assets/clarity/shaders/sky/" + (arg0));
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

    int loc(String arg0) {
        Integer var2 = null;
        var2 = ((Integer) this.locations.get(arg0));
        if (var2 == null) {
            var2 = GL20.glGetUniformLocation(this.id, arg0);
            this.locations.put(arg0, var2);
        }
        return var2;
    }

    void set1i(String arg0, int arg1) {
        int var3 = this.loc(arg0);
        if (var3 >= 0) {
            GL20.glUniform1i(var3, arg1);
        }
    }

    void set1f(String arg0, float arg1) {
        int var3 = this.loc(arg0);
        if (var3 >= 0) {
            GL20.glUniform1f(var3, arg1);
        }
    }

    void set3f(String arg0, float arg1, float arg2, float arg3) {
        int var5 = this.loc(arg0);
        if (var5 >= 0) {
            GL20.glUniform3f(var5, arg1, arg2, arg3);
        }
    }

    void setMat3(String arg0, float[] arg1) {
        int var3 = this.loc(arg0);
        if (var3 >= 0) {
            GL20.glUniformMatrix3fv(var3, false, arg1);
        }
    }

    void setMat4(String arg0, float[] arg1) {
        int var3 = this.loc(arg0);
        if (var3 >= 0) {
            GL20.glUniformMatrix4fv(var3, false, arg1);
        }
    }

    void delete() {
        GL20.glDeleteProgram(this.id);
    }

}
