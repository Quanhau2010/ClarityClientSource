package need.clarity.mixin;

import need.clarity.api.FreecamApi;
import net.minecraft.client.renderer.chunk.CompiledSectionMesh;
import net.minecraft.core.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CompiledSectionMesh.class)
public class SectionOcclusionGraphMixin {
    public SectionOcclusionGraphMixin() {
    }

    @Inject(method = "facesCanSeeEachother", at = @At("HEAD"), cancellable = true)
    private void clarity$bypassOcclusionCulling(Direction arg0, Direction arg1, CallbackInfoReturnable arg2) {
        if (FreecamApi.isActive()) {
            arg2.setReturnValue(true);
        }
    }

}
