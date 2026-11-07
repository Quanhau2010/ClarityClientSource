package need.clarity.mixin;

import com.mojang.blaze3d.buffers.GpuBufferSlice;
import com.mojang.blaze3d.resource.GraphicsResourceAllocator;
import com.mojang.blaze3d.vertex.PoseStack;
import need.clarity.WorldRenderEvent;
import need.clarity.api.OvertickApi;
import need.clarity.api.RenderApi;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.chunk.ChunkSectionsToRender;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.state.level.LevelRenderState;
import org.joml.Matrix4fc;
import org.joml.Vector4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public class LevelRendererMixin {
    public LevelRendererMixin() {
    }

    @Inject(method = "renderLevel(Lcom/mojang/blaze3d/resource/GraphicsResourceAllocator;Lnet/minecraft/client/DeltaTracker;ZLnet/minecraft/client/renderer/state/level/CameraRenderState;Lorg/joml/Matrix4fc;Lcom/mojang/blaze3d/buffers/GpuBufferSlice;Lorg/joml/Vector4f;ZLnet/minecraft/client/renderer/chunk/ChunkSectionsToRender;)V", at = @At("RETURN"))
    private void clarity$onRenderReturn(GraphicsResourceAllocator arg0, DeltaTracker arg1, boolean arg2, CameraRenderState arg3, Matrix4fc arg4, GpuBufferSlice arg5, Vector4f arg6, boolean arg7, ChunkSectionsToRender arg8, CallbackInfo arg9) {
        PoseStack var11 = new PoseStack();
        var11.mulPose(arg4);
        float var12 = arg1.getGameTimeDeltaPartialTick(false);
        WorldRenderEvent.fire(var11, var12);
        RenderApi.motionBlurApply();
    }

    @Inject(method = "extractVisibleEntities(Lnet/minecraft/client/Camera;Lnet/minecraft/client/renderer/culling/Frustum;Lnet/minecraft/client/DeltaTracker;Lnet/minecraft/client/renderer/state/level/LevelRenderState;)V", at = @At("TAIL"))
    private void clarity$overtickGhostEntities(Camera arg0, Frustum arg1, DeltaTracker arg2, LevelRenderState arg3, CallbackInfo arg4) {
        try {
            OvertickApi.addGhostRenderStates(arg3, arg2.getGameTimeDeltaPartialTick(false));
        } catch (Throwable ignored) {
        }
    }

}
