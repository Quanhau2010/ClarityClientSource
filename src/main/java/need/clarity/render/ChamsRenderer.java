package need.clarity.render;

import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.ColorTargetState;
import com.mojang.blaze3d.pipeline.DepthStencilState;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.platform.CompareOp;
import com.mojang.blaze3d.shaders.UniformType;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import java.lang.reflect.Constructor;
import java.util.Optional;
import need.clarity.api.Hooks;
import net.minecraft.client.renderer.rendertype.RenderSetup;
import net.minecraft.client.renderer.rendertype.RenderType;

public final class ChamsRenderer {
    private static final float BIAS_SCALE = 0.0F;
    private static final float BIAS_CONSTANT = -1100000.0F;
    private static final DepthStencilState THROUGH_WALLS_STATE;
    private static final RenderType CHAMS_WALLS;
    private static final RenderType CHAMS_DEPTH;

    private static DepthStencilState createThroughWallsState() {
        Constructor var0 = null;
        try {
            new Class[4][0] = CompareOp.class;
            new Class[4][1] = Boolean.TYPE;
            new Class[4][2] = Float.TYPE;
            new Class[4][3] = Float.TYPE;
            var0 = DepthStencilState.class.getDeclaredConstructor(new Class[4]);
            var0.setAccessible(true);
            new Object[4][0] = CompareOp.LESS_THAN_OR_EQUAL;
            new Object[4][1] = true;
            new Object[4][2] = 0.0F;
            new Object[4][3] = -1100000.0F;
        } catch (Throwable ignored) {
        }
        return ((DepthStencilState) var0.newInstance(new Object[4]));
    }

    public static DepthStencilState throughWallsDepthState() {
        return THROUGH_WALLS_STATE;
    }

    private static RenderPipeline buildFlat(String arg0, boolean arg1) {
        RenderPipeline.Builder var2 = RenderPipeline.builder(new RenderPipeline.Snippet[0]).withLocation(arg0).withVertexShader("core/position_color").withFragmentShader("core/position_color").withUniform("DynamicTransforms", UniformType.UNIFORM_BUFFER).withColorTargetState(new ColorTargetState(BlendFunction.TRANSLUCENT)).withCull(false).withVertexFormat(DefaultVertexFormat.ENTITY, VertexFormat.Mode.QUADS);
        if (!arg1) {
            var2.withDepthStencilState(DepthStencilState.DEFAULT);
        } else {
            if (THROUGH_WALLS_STATE != null) {
                var2.withDepthStencilState(THROUGH_WALLS_STATE);
            } else {
                var2.withDepthStencilState(Optional.empty());
            }
            return var2.build();
        }
    }

    private ChamsRenderer() {
    }

    public static RenderType modelRenderType(boolean arg0) {
        if (arg0) {
        } else {
        }
        return CHAMS_DEPTH;
    }

    public static boolean isOwnType(RenderType arg0) {
        if (arg0 != CHAMS_WALLS) {
            if (arg0 == CHAMS_DEPTH) {
            }
        }
        return false;
    }

    public static void dispose() {
    }

    static {
        Hooks.obj1("ChamsRenderer.modelRenderType", (p0) -> ChamsRenderer.lambda$static$0(p0));
        Hooks.obj0("ChamsRenderer.throughWallsDepthState", () -> ChamsRenderer.throughWallsDepthState());
        THROUGH_WALLS_STATE = createThroughWallsState();
        CHAMS_WALLS = RenderType.create("clarity:chams_walls", RenderSetup.builder(buildFlat("clarity/chams_walls", true)).sortOnUpload().createRenderSetup());
        CHAMS_DEPTH = RenderType.create("clarity:chams_depth", RenderSetup.builder(buildFlat("clarity/chams_depth", false)).sortOnUpload().createRenderSetup());
    }

}
