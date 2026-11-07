package need.clarity.mixin;

import need.clarity.api.SkyApi;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Projection;
import net.minecraft.client.renderer.ProjectionMatrixBuffer;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ProjectionMatrixBuffer.class)
public abstract class SkyShadersProjectionMixin {
    @Unique
    private static final Matrix4f clarity$tmp;

    public SkyShadersProjectionMixin() {
    }

    @Unique
    private boolean clarity$isLevelBuffer() {
        Minecraft var1 = Minecraft.getInstance();
        if (var1 != null) {
            if (var1.gameRenderer == null) {
            }
        }
        return false;
        return (((GameRendererSkyAccessor) var1.gameRenderer).clarity$getLevelProjectionMatrixBuffer() == this);
    }

    @Inject(method = "getBuffer(Lorg/joml/Matrix4f;)Lcom/mojang/blaze3d/buffers/GpuBufferSlice;", at = @At("HEAD"))
    private void clarity$captureMatrix(Matrix4f arg0, CallbackInfoReturnable arg1) {
        if (arg0 != null) {
            if (this.clarity$isLevelBuffer()) {
                SkyApi.captureProjection(arg0);
            }
            return;
        }
    }

    @Inject(method = "getBuffer(Lnet/minecraft/client/renderer/Projection;)Lcom/mojang/blaze3d/buffers/GpuBufferSlice;", at = @At("HEAD"))
    private void clarity$captureProjection(Projection arg0, CallbackInfoReturnable arg1) {
        if (arg0 != null) {
            if (this.clarity$isLevelBuffer()) {
                SkyApi.captureProjection(arg0.getMatrix(clarity$tmp));
            }
            return;
        }
    }

    static {
        clarity$tmp = new Matrix4f();
    }

}
