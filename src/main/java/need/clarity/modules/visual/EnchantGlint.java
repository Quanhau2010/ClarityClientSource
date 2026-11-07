package need.clarity.modules.visual;

import com.mojang.blaze3d.platform.NativeImage;
import java.io.InputStream;
import java.util.function.Supplier;
import need.clarity.PerFrameEvents;
import need.clarity.modules.ModuleBase;
import need.clarity.util.ThemeColors;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.Resource;

public class EnchantGlint extends ModuleBase {
    private static final String labelText = "#8040FF";
    private static final Identifier[] fieldVal;
    public static volatile EnchantGlint INSTANCE;
    private int counterValue;
    private final DynamicTexture[] fieldVal1;
    private boolean enabledState;
    private int counterValue1;

    public EnchantGlint() {
        super("Enchant Glint", "Visual");
        this.counterValue = -2147483648;
        DynamicTexture[] var16 = new DynamicTexture[fieldVal.length];
        this.fieldVal1 = var16;
        this.enabledState = false;
        this.counterValue1 = 0;
        INSTANCE = this;
        try {
            PerFrameEvents.register(() -> this.applyStep());
        } catch (Throwable ignored) {
        }
    }

    private void applyStep() {
        if (!this.isEnabled()) {
            if (this.enabledState) {
                this.applyStep1();
            }
            return;
        }
    }

    public void onEnable() {
        this.enabledState = false;
        this.counterValue = -2147483648;
        this.counterValue1 = 0;
    }

    public void onDisable() {
        if (this.enabledState) {
            this.applyStep1();
        }
    }

    public void onTick() {
        int var28 = 0;
        if (!this.isEnabled()) {
            return;
        }
        Minecraft var8 = Minecraft.getInstance();
        if (var8 != null) {
            if (var8.getTextureManager() == null) {
            }
        }
        return;
        int var13 = this.getGlintColor();
        if (this.enabledState) {
            if (!this.isEligible(var8)) {
                var28 = 1;
            } else {
                var28 = 0;
            }
            int var6 = var28;
            if (this.enabledState) {
                int var30 = this.counterValue;
                if (var13 == var30) {
                    if (var6 != 0) {
                    }
                }
                if (this.counterValue1 > 0) {
                    int var32 = (this.counterValue1 - 1);
                    this.counterValue1 = var32;
                    return;
                }
                if (!this.isEligible(var13)) {
                    this.counterValue1 = 40;
                }
                return;
            }
        }
    }

    private boolean isEligible(Minecraft arg0) {
        int var5 = 0;
        var5 = 0;
        while (true) {
            int var6 = var5;
            int var15 = fieldVal.length;
            if (var6 < var15) {
                DynamicTexture[] var8 = this.fieldVal1;
                int var16 = var5;
                if (var8[var16] != null) {
                    TextureManager var11 = arg0.getTextureManager();
                    Identifier[] var17 = fieldVal;
                    int var4 = var5;
                    Identifier var18 = var17[var4];
                    AbstractTexture var12 = var11.getTexture(var18);
                    DynamicTexture[] var20 = this.fieldVal1;
                    int var22 = var5;
                    DynamicTexture var21 = var20[var22];
                    if (var12 != var21) {
                        return false;
                    }
                }
            }
            return true;
            var5 = (var5 + 1);
        }
    }

    private int getGlintColor() {
        return (ThemeColors.resolveRgb(((String) this.getSetting("Glint Color", "#8040FF")), 8405247, false) & 16777215);
    }

    private boolean isEligible(int arg0) {
        int var5 = 0;
        int var6 = 0;
        int var25 = 0;
        var5 = 0;
        var6 = 0;
        while (true) {
            int var9 = var6;
            int var19 = fieldVal.length;
            if (var9 >= var19) break;
            Identifier[] var22 = fieldVal;
            int var4 = var6;
            Identifier var23 = var22[var4];
            DynamicTexture var14 = this.evaluate(var23, arg0);
            DynamicTexture[] var16 = this.fieldVal1;
            int var24 = var6;
            var16[var24] = var14;
            if (var14 != null) {
                var5 = 1;
            }
            var6 = (var6 + 1);
        }
        int var20 = var5;
        this.enabledState = var20;
        if (var5 != 0) {
            var25 = arg0;
        } else {
            var25 = -2147483648;
        }
        this.counterValue = var25;
        return var5;
    }

    private void applyStep1() {
        int var7 = 0;
        Minecraft var1 = Minecraft.getInstance();
        var7 = 0;
        while (true) {
            int var12 = var7;
            int var28 = fieldVal.length;
            if (var12 < var28) {
                DynamicTexture[] var16 = this.fieldVal1;
                int var31 = var7;
                if (var16[var31] == null) {
                }
            } else {
                this.enabledState = false;
                this.counterValue = -2147483648;
                return;
                Identifier[] var32 = fieldVal;
                int var3 = var7;
                Identifier var33 = var32[var3];
                NativeImage var19 = this.evaluate(var33);
                if (var19 != null) {
                    try {
                        TextureManager var22 = var1.getTextureManager();
                        Identifier[] var34 = fieldVal;
                        int var37 = var7;
                        Identifier var35 = var34[var37];
                        Supplier var4 = () -> EnchantGlint.lambda$e();
                        DynamicTexture var38 = new DynamicTexture(var4, var19);
                        var22.register(var35, var38);
                    } catch (Throwable ignored) {
                    }
                    try {
                        var19.close();
                    } catch (Throwable ignored) {
                    }
                }
                DynamicTexture[] var24 = this.fieldVal1;
                int var36 = var7;
                var24[var36] = null;
            }
            var7 = (var7 + 1);
        }
    }

    private DynamicTexture evaluate(Identifier arg0, int arg1) {
        int var13 = 0;
        int var14 = 0;
        NativeImage var24 = this.evaluate(arg0);
        if (var24 == null) {
            return null;
        }
        try {
            float var31 = (((float) ((arg1 >> 16) & 255)) / 255.0F);
            float var36 = (((float) ((arg1 >> 8) & 255)) / 255.0F);
            float var40 = (((float) (arg1 & 255)) / 255.0F);
            var13 = 0;
            while (true) {
                int var42 = var13;
                int var87 = var24.getHeight();
                if (var42 >= var87) break;
                var14 = 0;
                while (true) {
                    int var44 = var14;
                    int var89 = var24.getWidth();
                    if (var44 >= var89) break;
                    int var90 = var14;
                    int var5 = var13;
                    int var50 = var24.getPixel(var90, var5);
                    int var53 = ((var50 >>> 24) & 255);
                    int var56 = ((var50 >>> 16) & 255);
                    int var59 = ((var50 >>> 8) & 255);
                    int var61 = (var50 & 255);
                    int var99 = Math.max(var59, var61);
                    float var66 = ((((float) Math.max(var56, var99)) / 255.0F) * 1.15F);
                    int var105 = Math.round(((var66 * var31) * 255.0F));
                    int var68 = Math.min(255, var105);
                    int var109 = Math.round(((var66 * var36) * 255.0F));
                    int var70 = Math.min(255, var109);
                    int var113 = Math.round(((var66 * var40) * 255.0F));
                    int var72 = Math.min(255, var113);
                    int var114 = var14;
                    int var123 = var13;
                    int var126 = (var53 << 24);
                    int var132 = (var68 << 16);
                    int var127 = (var126 | var132);
                    int var134 = (var70 << 8);
                    int var129 = ((var127 | var134) | var72);
                    var24.setPixel(var114, var123, var129);
                    var14 = (var14 + 1);
                }
                var13 = (var13 + 1);
            }
            Supplier var124 = () -> EnchantGlint.lambda$d();
            DynamicTexture var74 = new DynamicTexture(var124, var24);
            Minecraft.getInstance().getTextureManager().register(arg0, var74);
        } catch (Throwable ignored) {
        }
        return var74;
        try {
            var24.close();
        } catch (Throwable ignored) {
        }
        return null;
    }

    private NativeImage evaluate(Identifier arg0) {
        try {
            InputStream var11 = ((Resource) Minecraft.getInstance().getResourceManager().getResource(arg0).orElseThrow()).open();
            try {
                NativeImage var13 = NativeImage.read(var11);
            } catch (Throwable ignored) {
            }
            if (var11 != null) {
                var11.close();
            }
            return var13;
            if (var11 != null) {
                try {
                    var11.close();
                } catch (Throwable ignored) {
                }
                var14.addSuppressed(var17);
            }
            throw var14;
        } catch (Throwable ignored) {
        }
    }

    public static boolean isActive() {
        int var3 = 0;
        if (INSTANCE != null) {
            if (INSTANCE.isEnabled()) {
                var3 = 1;
            } else {
                var3 = 0;
            }
            return var3;
        }
    }

    public static float[] getActiveGlintColor() {
        int var8 = 0;
        EnchantGlint var0 = INSTANCE;
        if (var0 != null) {
            var8 = var0.getGlintColor();
        } else {
            var8 = 8405247;
        }
        int var5 = var8;
        float[] var10 = new float[3];
        float var16 = (((float) ((var5 >> 16) & 255)) / 255.0F);
        var10[0] = var16;
        float var21 = (((float) ((var5 >> 8) & 255)) / 255.0F);
        var10[1] = var21;
        float var25 = (((float) (var5 & 255)) / 255.0F);
        var10[2] = var25;
        return var10;
    }

    static {
        Identifier[] var3 = new Identifier[3];
        Identifier var6 = Identifier.withDefaultNamespace("textures/misc/enchanted_glint_item.png");
        var3[0] = var6;
        Identifier var8 = Identifier.withDefaultNamespace("textures/misc/enchanted_glint_armor.png");
        var3[1] = var8;
        Identifier var10 = Identifier.withDefaultNamespace("textures/misc/enchanted_glint_entity.png");
        var3[2] = var10;
        fieldVal = var3;
    }

}
