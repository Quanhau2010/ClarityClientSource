package need.clarity.mixin;

import need.clarity.api.Modules;
import net.minecraft.client.ClientBrandRetriever;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientBrandRetriever.class)
public class ClientBrandRetrieverMixin {
    public ClientBrandRetrieverMixin() {
    }

    @Inject(method = "getClientModName", at = @At("RETURN"), cancellable = true, remap = false)
    private static void cl$spoofBrand(CallbackInfoReturnable arg0) {
        if (!Modules.clientSpooferEnabled()) {
            return;
        }
        arg0.setReturnValue(Modules.clientSpooferBrand());
    }

}
