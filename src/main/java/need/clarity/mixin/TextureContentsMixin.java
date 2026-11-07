package need.clarity.mixin;

import need.clarity.api.Modules;
import net.minecraft.client.renderer.texture.TextureContents;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.ResourceManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TextureContents.class)
public class TextureContentsMixin {
    public TextureContentsMixin() {
    }

    @Inject(method = "load", at = @At("RETURN"))
    private static void cl$themeRecolor(ResourceManager arg0, Identifier arg1, CallbackInfoReturnable arg2) {
        TextureContents var3 = null;
        try {
            var3 = ((TextureContents) arg2.getReturnValue());
            if (var3 != null) {
                if (arg1 != null) {
                    if (Modules.themeTexturesShouldRecolor(arg1.getPath())) {
                        Modules.themeTexturesRecolor(var3.image());
                    }
                    return;
                }
            }
        } catch (Throwable ignored) {
        }
    }

}
