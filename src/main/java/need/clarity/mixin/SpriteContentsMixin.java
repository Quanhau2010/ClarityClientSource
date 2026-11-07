package need.clarity.mixin;

import com.mojang.blaze3d.platform.NativeImage;
import java.util.List;
import java.util.Optional;
import need.clarity.api.Modules;
import net.minecraft.client.renderer.texture.SpriteContents;
import net.minecraft.client.resources.metadata.animation.FrameSize;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SpriteContents.class)
public class SpriteContentsMixin {
    public SpriteContentsMixin() {
    }

    @Inject(method = "<init>(Lnet/minecraft/resources/Identifier;Lnet/minecraft/client/resources/metadata/animation/FrameSize;Lcom/mojang/blaze3d/platform/NativeImage;Ljava/util/Optional;Ljava/util/List;Ljava/util/Optional;)V", at = @At("TAIL"))
    private void cl$themeRecolor(Identifier arg0, FrameSize arg1, NativeImage arg2, Optional arg3, List arg4, Optional arg5, CallbackInfo arg6) {
        try {
            if (arg0 != null) {
                if (Modules.themeTexturesShouldRecolor(arg0.getPath())) {
                    Modules.themeTexturesRecolor(arg2);
                }
                return;
            }
        } catch (Throwable ignored) {
        }
    }

}
