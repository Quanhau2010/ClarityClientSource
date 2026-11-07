package need.clarity.render;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL20;

public class Shader {
    private final int programId;
    private final String vertexPath;
    private final String fragmentPath;
    private final Map uniformLocations;

    public Shader(String arg0, String arg1) {
        this.uniformLocations = new HashMap();
        this.vertexPath = "/assets/clarity/shaders/" + (arg0) + "/" + (arg1) + ".vsh";
        this.fragmentPath = "/assets/clarity/shaders/" + (arg0) + "/" + (arg1) + ".fsh";
        int var3 = this.createShader(this.vertexPath, 35633);
        int var4 = this.createShader(this.fragmentPath, 35632);
        this.programId = GL20.glCreateProgram();
        GL20.glAttachShader(this.programId, var3);
        GL20.glAttachShader(this.programId, var4);
        GL20.glLinkProgram(this.programId);
        if (GL20.glGetProgrami(this.programId, 35714) == 0) {
            throw new RuntimeException("Failed to link shader program: " + (GL20.glGetProgramInfoLog(this.programId)));
        }
        GL20.glDeleteShader(var3);
        GL20.glDeleteShader(var4);
    }

    private int createShader(String arg0, int arg1) {
        String var3 = null;
        try {
            var3 = this.readShaderFile(arg0);
            int var4 = GL20.glCreateShader(arg1);
            GL20.glShaderSource(var4, var3);
            GL20.glCompileShader(var4);
            if (GL20.glGetShaderi(var4, 35713) == 0) {
                throw new RuntimeException("Failed to compile shader: " + (arg0) + "\n" + (GL20.glGetShaderInfoLog(var4)));
            }
        } catch (IOException ignored) {
        }
        return var4;
    }

    private String readShaderFile(String arg0) {
        int var5 = 0;
        InputStream var2 = Shader.class.getResourceAsStream(arg0);
        if (var2 == null) {
            throw new IOException("Shader file not found: " + (arg0));
        }
        StringBuilder var3 = new StringBuilder();
        BufferedReader var4 = new BufferedReader(new InputStreamReader(var2));
        while (true) {
            try {
                var5 = var4.readLine();
                if (var4.readLine() == null) break;
                var3.append(var5).append("\n");
            } catch (Throwable ignored) {
            }
            var4.close();
            try {
                var4.close();
            } catch (Throwable ignored) {
            }
            var5.addSuppressed(var6);
            throw var5;
            return var3.toString();
        }
    }

    public void bind() {
        GL20.glUseProgram(this.programId);
    }

    public void unbind() {
        GL20.glUseProgram(0);
    }

    private int location(String arg0) {
        Integer var2 = ((Integer) this.uniformLocations.get(arg0));
        if (var2 != null) {
            return var2;
        }
        int var3 = GL20.glGetUniformLocation(this.programId, arg0);
        this.uniformLocations.put(arg0, var3);
        return var3;
    }

    public void setUniform1i(String arg0, int arg1) {
        int var3 = this.location(arg0);
        if (var3 != -1) {
            GL20.glUniform1i(var3, arg1);
        }
    }

    public void setUniform1f(String arg0, float arg1) {
        int var3 = this.location(arg0);
        if (var3 != -1) {
            GL20.glUniform1f(var3, arg1);
        }
    }

    public void setUniform2f(String arg0, float arg1, float arg2) {
        int var4 = this.location(arg0);
        if (var4 != -1) {
            GL20.glUniform2f(var4, arg1, arg2);
        }
    }

    public void setUniform2f(String arg0, Vector2f arg1) {
        this.setUniform2f(arg0, arg1.x, arg1.y);
    }

    public void setUniform3f(String arg0, float arg1, float arg2, float arg3) {
        int var5 = this.location(arg0);
        if (var5 != -1) {
            GL20.glUniform3f(var5, arg1, arg2, arg3);
        }
    }

    public void setUniform3f(String arg0, Vector3f arg1) {
        this.setUniform3f(arg0, arg1.x, arg1.y, arg1.z);
    }

    public void setUniform4f(String arg0, float arg1, float arg2, float arg3, float arg4) {
        int var6 = this.location(arg0);
        if (var6 != -1) {
            GL20.glUniform4f(var6, arg1, arg2, arg3, arg4);
        }
    }

    public void setUniform1fv(String arg0, float[] arg1) {
        int var3 = this.location(arg0);
        if (var3 != -1) {
            GL20.glUniform1fv(var3, arg1);
        }
    }

    public void setUniform4fv(String arg0, float[] arg1) {
        int var3 = this.location(arg0);
        if (var3 != -1) {
            GL20.glUniform4fv(var3, arg1);
        }
    }

    public void setUniformMatrix4f(String arg0, float[] arg1) {
        int var3 = this.location(arg0);
        if (var3 != -1) {
            GL20.glUniformMatrix4fv(var3, false, arg1);
        }
    }

    public void setUniformBool(String arg0, boolean arg1) {
        this.setUniform1i(arg0, (arg1));
    }

    public void delete() {
        GL20.glDeleteProgram(this.programId);
    }

}
