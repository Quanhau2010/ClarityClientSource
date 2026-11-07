package need.clarity.render;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.MeshData;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormatElement;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.List;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;

public final class RawShaderDrawUtil {
    private static int vertexArray;
    private static int vertexBuffer;
    private static int indexBuffer;
    private static int indexedQuadCapacity;

    private RawShaderDrawUtil() {
    }

    public static void draw(MeshData arg0) {
        RenderSystem.assertOnRenderThread();
        ensureBuffers();
        ByteBuffer var1 = arg0.vertexBuffer();
        MeshData.DrawState var2 = arg0.drawState();
        VertexFormat var3 = var2.format();
        VertexFormat.Mode var4 = var2.mode();
        int var5 = var2.vertexCount();
        GL30.glBindVertexArray(vertexArray);
        GL15.glBindBuffer(34962, vertexBuffer);
        GL15.glBufferData(34962, var1, 35040);
        configureAttributes(var3);
        drawArrays(var4, var5);
        List var6 = var3.getElements();
        int var7 = 0;
        while (true) {
            if (var7 >= var6.size()) break;
            GL20.glDisableVertexAttribArray(var7);
            var7++;
        }
        GL30.glBindVertexArray(0);
        GL15.glBindBuffer(34962, 0);
        arg0.close();
    }

    private static void ensureBuffers() {
        if (vertexArray == -1) {
            vertexArray = GL30.glGenVertexArrays();
            vertexBuffer = GL15.glGenBuffers();
            indexBuffer = GL15.glGenBuffers();
        }
    }

    private static void configureAttributes(VertexFormat arg0) {
        List var1 = arg0.getElements();
        List var2 = arg0.getElementAttributeNames();
        int var3 = arg0.getVertexSize();
        int[] var4 = arg0.getOffsetsByElement();
        int var5 = 0;
        while (true) {
            if (var5 >= var1.size()) break;
            VertexFormatElement var6 = ((VertexFormatElement) var1.get(var5));
            String var7 = ((String) var2.get(var5));
            int var8 = resolveAttributeLocation(var6, var7);
            if (var8 < 0) {
            } else {
                GL20.glEnableVertexAttribArray(var8);
                GL20.glVertexAttribPointer(var8, var6.count(), toGlType(var6.type()), shouldNormalize(var6), var3, ((long) var4[var5]));
            }
            var5++;
        }
    }

    private static int resolveAttributeLocation(VertexFormatElement arg0, String arg1) {
        if (arg0 == VertexFormatElement.POSITION) {
            return 0;
        }
        if (arg0 == VertexFormatElement.UV0) {
            return 1;
        }
        if (arg0 == VertexFormatElement.COLOR) {
            return 2;
        }
        if (arg0 == VertexFormatElement.NORMAL) {
            return 3;
        }
        return GL20.glGetAttribLocation(GL11.glGetInteger(35725), arg1);
    }

    private static void drawArrays(VertexFormat.Mode arg0, int arg1) {
        int var2 = 0;
        if (arg0 == VertexFormat.Mode.QUADS) {
            var2 = (arg1 / 4);
            ensureQuadIndices(var2);
            GL15.glBindBuffer(34963, indexBuffer);
            GL11.glDrawElements(4, (var2 * 6), 5125, 0L);
            GL15.glBindBuffer(34963, 0);
        } else {
            switch (RawShaderDrawUtil.1.$SwitchMap$com$mojang$blaze3d$vertex$VertexFormat$Mode[arg0.ordinal()]) {
                case 1 -> {}
                case 2 -> {}
                case 3 -> {}
                case 4 -> {}
                default -> {}
            }
            var2 = 4;
            GL11.glDrawArrays(var2, 0, arg1);
        }
    }

    private static void ensureQuadIndices(int arg0) {
        if (arg0 <= indexedQuadCapacity) {
            return;
        }
        int var1 = Math.max(arg0, (indexedQuadCapacity * 2));
        IntBuffer var2 = MemoryUtil.memAllocInt((var1 * 6));
        int var3 = 0;
        while (true) {
            if (var3 >= var1) break;
            int var4 = (var3 * 4);
            var2.put(var4);
            var2.put((var4 + 1));
            var2.put((var4 + 2));
            var2.put((var4 + 2));
            var2.put((var4 + 3));
            var2.put(var4);
            var3++;
        }
        var2.flip();
        GL15.glBindBuffer(34963, indexBuffer);
        GL15.glBufferData(34963, var2, 35044);
        GL15.glBindBuffer(34963, 0);
        MemoryUtil.memFree(var2);
        indexedQuadCapacity = var1;
    }

    private static int toGlType(VertexFormatElement.Type arg0) {
        switch (RawShaderDrawUtil.1.$SwitchMap$com$mojang$blaze3d$vertex$VertexFormatElement$Type[arg0.ordinal()]) {
            case 1 -> {}
            case 2 -> {}
            case 3 -> {}
            case 4 -> {}
            case 5 -> {}
            case 6 -> {}
            case 7 -> {}
            default -> {}
        }
        throw new MatchException(null, null);
        return 5125;
    }

    private static boolean shouldNormalize(VertexFormatElement arg0) {
        if (arg0 != VertexFormatElement.COLOR) {
            if (arg0 == VertexFormatElement.NORMAL) {
            }
        }
        return false;
    }

    static {
        vertexArray = -1;
        vertexBuffer = -1;
        indexBuffer = -1;
        indexedQuadCapacity = 0;
    }

    public class 1 {

        static {
            int var0 = 0;
            $SwitchMap$com$mojang$blaze3d$vertex$VertexFormatElement$Type = new int[VertexFormatElement.Type.values().length];
            try {
                $SwitchMap$com$mojang$blaze3d$vertex$VertexFormatElement$Type[VertexFormatElement.Type.FLOAT.ordinal()] = 1;
            } catch (NoSuchFieldError ignored) {
            }
            try {
                $SwitchMap$com$mojang$blaze3d$vertex$VertexFormatElement$Type[VertexFormatElement.Type.UBYTE.ordinal()] = 2;
            } catch (NoSuchFieldError ignored) {
            }
            try {
                $SwitchMap$com$mojang$blaze3d$vertex$VertexFormatElement$Type[VertexFormatElement.Type.BYTE.ordinal()] = 3;
            } catch (NoSuchFieldError ignored) {
            }
            try {
                $SwitchMap$com$mojang$blaze3d$vertex$VertexFormatElement$Type[VertexFormatElement.Type.USHORT.ordinal()] = 4;
            } catch (NoSuchFieldError ignored) {
            }
            try {
                $SwitchMap$com$mojang$blaze3d$vertex$VertexFormatElement$Type[VertexFormatElement.Type.SHORT.ordinal()] = 5;
            } catch (NoSuchFieldError ignored) {
            }
            try {
                $SwitchMap$com$mojang$blaze3d$vertex$VertexFormatElement$Type[VertexFormatElement.Type.UINT.ordinal()] = 6;
            } catch (NoSuchFieldError ignored) {
            }
            try {
                $SwitchMap$com$mojang$blaze3d$vertex$VertexFormatElement$Type[VertexFormatElement.Type.INT.ordinal()] = 7;
            } catch (NoSuchFieldError ignored) {
            }
            $SwitchMap$com$mojang$blaze3d$vertex$VertexFormat$Mode = new int[VertexFormat.Mode.values().length];
            try {
                $SwitchMap$com$mojang$blaze3d$vertex$VertexFormat$Mode[VertexFormat.Mode.TRIANGLES.ordinal()] = 1;
            } catch (NoSuchFieldError ignored) {
            }
            try {
                $SwitchMap$com$mojang$blaze3d$vertex$VertexFormat$Mode[VertexFormat.Mode.TRIANGLE_STRIP.ordinal()] = 2;
            } catch (NoSuchFieldError ignored) {
            }
            try {
                $SwitchMap$com$mojang$blaze3d$vertex$VertexFormat$Mode[VertexFormat.Mode.TRIANGLE_FAN.ordinal()] = 3;
            } catch (NoSuchFieldError ignored) {
            }
            try {
                $SwitchMap$com$mojang$blaze3d$vertex$VertexFormat$Mode[VertexFormat.Mode.LINES.ordinal()] = 4;
            } catch (NoSuchFieldError ignored) {
            }
        }

    }

}
