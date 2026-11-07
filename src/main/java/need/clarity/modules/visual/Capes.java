package need.clarity.modules.visual;

import com.mojang.blaze3d.platform.NativeImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Base64;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;
import need.clarity.Clarity;
import need.clarity.PerFrameEvents;
import need.clarity.api.Hooks;
import need.clarity.config.ClarityConfig;
import need.clarity.modules.ModuleBase;
import need.clarity.render.ShaderCapes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.ResourceManager;

public class Capes extends ModuleBase {
    public static volatile Capes INSTANCE;
    private static final Map entriesMap;
    private volatile Identifier fieldVal;
    private volatile String labelText;
    private int counterValue;

    public Capes() {
        super("Capes", "Visual");
        this.labelText = "";
        this.counterValue = 0;
        INSTANCE = this;
        Hooks.Bool0 var12 = () -> Capes.lambda$d();
        Hooks.bool0("Capes.enabled", var12);
        Hooks.Obj0 var13 = () -> Capes.enabled();
        Hooks.obj0("Capes.texture", var13);
        PerFrameEvents.register(ShaderCapes::frame);
    }

    public void onEnable() {
        this.applyStep();
    }

    public void onDisable() {
        this.fieldVal = null;
        this.labelText = "";
        Identifier var5 = ShaderCapes.select("none");
    }

    public void onTick() {
        int var19 = 0;
        String var6 = this.getCape();
        if (this.fieldVal == null) {
            if (!"none".equals(var6)) {
                if (!var6.isEmpty()) {
                    var19 = 1;
                }
            }
        } else {
            var19 = 0;
        }
        int var5 = var19;
        String var21 = this.labelText;
        if (var6.equals(var21)) {
            if (var5 != 0) {
                int var23 = (this.counterValue + 1);
                this.counterValue = var23;
                if (var23 >= 40) {
                }
            }
        }
        this.counterValue = 0;
        this.applyStep();
    }

    public Identifier getActiveTexture() {
        return this.fieldVal;
    }

    private String getCape() {
        String var8 = null;
        Object var5 = this.getSetting("Cape", "none");
        if (var5 != null) {
            var8 = var5.toString();
        } else {
            var8 = "none";
        }
        return var8;
    }

    private void applyStep() {
        String var7 = this.getCape();
        this.labelText = var7;
        this.fieldVal = null;
        if (!"none".equals(var7)) {
            if (var7.isEmpty()) {
            }
        }
        Identifier var31 = ShaderCapes.select("none");
        return;
        if (ShaderCapes.isShaderCape(var7)) {
            Identifier var35 = ShaderCapes.select(var7);
            this.fieldVal = var35;
            return;
        }
        Identifier var18 = ShaderCapes.select("none");
        Identifier var21 = ((Identifier) entriesMap.get(var7));
        if (var21 != null) {
            this.fieldVal = var21;
            return;
        }
        Minecraft var23 = Minecraft.getInstance();
        if (var23 == null) {
            return;
        }
        if (var7.startsWith("custom_")) {
            this.applyStep1(var23, var7);
        } else {
            this.applyStep(var23, var7);
        }
    }

    private void applyStep(Minecraft arg0, String arg1) {
        String var12 = "capes/" + (arg1) + ".png";
        Identifier var15 = Clarity.id("capes/" + (arg1));
        try {
            ResourceManager var17 = arg0.getResourceManager();
            Identifier var29 = Clarity.id(var12);
            InputStream var18 = var17.open(var29);
            NativeImage var20 = NativeImage.read(var18);
            var18.close();
            Supplier var33 = () -> Capes.lambda$c(arg1);
            DynamicTexture var22 = new DynamicTexture(var33, var20);
            arg0.getTextureManager().register(var15, var22);
            Object var26 = entriesMap.put(arg1, var15);
            this.fieldVal = var15;
        } catch (Throwable ignored) {
        }
    }

    private void applyStep1(Minecraft arg0, String arg1) {
        String var20 = null;
        String var13 = ClarityConfig.getCustomCapeUrl(arg1);
        if (var13 != null) {
            if (var13.isEmpty()) {
            }
        }
        return;
        try {
            if (var13.contains(",")) {
                int var37 = (var13.indexOf(44) + 1);
                var20 = var13.substring(var37);
            } else {
                var20 = var13;
            }
            String var8 = var20;
            Base64.Decoder var21 = Base64.getDecoder();
            String var39 = var8.trim();
            byte[] var22 = var21.decode(var39);
            ByteArrayInputStream var23 = new ByteArrayInputStream(var22);
            NativeImage var24 = NativeImage.read(var23);
            Identifier var27 = Clarity.id("capes/" + (arg1));
            Supplier var46 = () -> Capes.lambda$b(arg1);
            DynamicTexture var28 = new DynamicTexture(var46, var24);
            arg0.getTextureManager().register(var27, var28);
            Object var32 = entriesMap.put(arg1, var27);
            this.fieldVal = var27;
        } catch (Exception ignored) {
        }
    }

    public static void applyStep(String arg0) {
        Object var3 = entriesMap.remove(arg0);
    }

    static {
        ConcurrentHashMap var0 = new ConcurrentHashMap();
        entriesMap = var0;
    }

}
