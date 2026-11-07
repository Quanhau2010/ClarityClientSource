package need.clarity.render;

import com.mojang.blaze3d.platform.NativeImage;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.Identifier;

public final class FxTextures {
    private static final Map CACHE;

    private FxTextures() {
    }

    public static Identifier get(String arg0) {
        if (CACHE.containsKey(arg0)) {
            return ((Identifier) CACHE.get(arg0));
        }
        String var46 = "fxdyn/" + (arg0);
        Identifier var12 = Identifier.fromNamespaceAndPath("clarity", var46);
        try {
            String var48 = "/assets/clarity/textures/fx/" + (arg0) + ".png";
            InputStream var14 = FxTextures.class.getResourceAsStream(var48);
            try {
                if (var14 == null) {
                    Object var37 = CACHE.put(arg0, null);
                }
            } catch (Throwable ignored) {
            }
            if (var14 != null) {
                var14.close();
            }
            return null;
            try {
                try {
                    NativeImage var17 = NativeImage.read(var14);
                    TextureManager var19 = Minecraft.getInstance().getTextureManager();
                    Supplier var58 = () -> FxTextures.lambda$get$0(arg0);
                    DynamicTexture var3 = new DynamicTexture(var58, var17);
                    var19.register(var12, var3);
                    Object var21 = CACHE.put(arg0, var12);
                } catch (Throwable ignored) {
                }
            } catch (Throwable ignored) {
            }
            if (var14 != null) {
                var14.close();
            }
            return var12;
            if (var14 != null) {
                try {
                    var14.close();
                } catch (Throwable ignored) {
                }
                var30.addSuppressed(var33);
            }
            throw var30;
        } catch (Throwable ignored) {
        }
    }

    static {
        HashMap var0 = new HashMap();
        CACHE = var0;
    }

}
