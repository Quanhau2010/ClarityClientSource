package need.clarity.modules.visual.cosmetics.killeffect;

import java.util.function.Function;
import need.clarity.modules.visual.cosmetics.ICosmeticEffectRenderer;

public enum KillEffectStyle {
    SHOCKWAVE("Shockwave", ShockwaveKillRenderer::new),
    SHATTER("Shatter", ShatterKillRenderer::new),
    LIGHTNING_STRIKE("Lightning Strike", LightningStrikeKillRenderer::new, 7314431, 15135487),
    BLACK_HOLE("Black Hole", BlackHoleKillRenderer::new, 10837247, 16760952),
    PHOENIX("Phoenix", PhoenixKillRenderer::new, 16738842, 16773280),
    ASCEND("Ascend", AscendKillRenderer::new, 16763213, 16774876);

    public final String labelText;
    public final int counterValue;
    public final int counterValue1;
    private final Function factoryFunction;

    KillEffectStyle(String arg0, Function arg1) {
        this(arg0, arg1, 0, 0);
    }

    KillEffectStyle(String arg0, Function arg1, int arg2, int arg3) {
        this.labelText = arg0;
        this.factoryFunction = arg1;
        this.counterValue = arg2;
        this.counterValue1 = arg3;
    }

    public boolean isEligible() {
        int var5 = 0;
        if (this.counterValue == 0) {
            if (this.counterValue1 != 0) {
            }
        }
        var5 = 1;
        var5 = 0;
        return var5;
    }

    public ICosmeticEffectRenderer createRenderer(KillEffectRenderSpec arg0) {
        return ((ICosmeticEffectRenderer) this.factoryFunction.apply(arg0));
    }

    public static KillEffectStyle fromDisplayName(String arg0) {
        int var5 = 0;
        if (arg0 == null) {
            return null;
        }
        KillEffectStyle[] var8 = values();
        int var10 = var8.length;
        var5 = 0;
        while (true) {
            if (var5 >= var10) break;
            int var20 = var5;
            KillEffectStyle var14 = var8[var20];
            if (var14.labelText.equalsIgnoreCase(arg0)) {
                return var14;
            }
            var5 = (var5 + 1);
        }
        return null;
    }

}
