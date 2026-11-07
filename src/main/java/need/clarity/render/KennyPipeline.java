package need.clarity.render;

import com.mojang.blaze3d.pipeline.DepthStencilState;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.platform.CompareOp;
import java.util.Optional;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.rendertype.LayeringTransform;
import net.minecraft.client.renderer.rendertype.OutputTarget;
import net.minecraft.client.renderer.rendertype.RenderSetup;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.resources.Identifier;

public final class KennyPipeline {
    private static RenderType occluded;
    private static RenderType walls;

    private KennyPipeline() {
    }

    private static RenderType make(String arg0, Optional arg1) {
        new RenderPipeline.Snippet[1][0] = RenderPipelines.DEBUG_FILLED_SNIPPET;
        RenderPipeline var2 = RenderPipelines.register(RenderPipeline.builder(new RenderPipeline.Snippet[1]).withLocation(Identifier.parse("clarity:pipeline/" + (arg0))).withDepthStencilState(arg1).withCull(true).build());
        return RenderType.create("clarity:" + (arg0), RenderSetup.builder(var2).setLayeringTransform(LayeringTransform.VIEW_OFFSET_Z_LAYERING).setOutputTarget(OutputTarget.ITEM_ENTITY_TARGET).createRenderSetup());
    }

    public static RenderType occluded() {
        if (occluded == null) {
            occluded = make("kenny", Optional.of(new DepthStencilState(CompareOp.LESS_THAN_OR_EQUAL, false)));
        }
        return occluded;
    }

    public static RenderType throughWalls() {
        if (walls == null) {
            walls = make("kenny_walls", Optional.empty());
        }
        return walls;
    }

}
