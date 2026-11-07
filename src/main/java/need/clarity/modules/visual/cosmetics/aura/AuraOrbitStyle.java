package need.clarity.modules.visual.cosmetics.aura;

import java.util.function.Function;
import need.clarity.modules.visual.cosmetics.ICosmeticEffectRenderer;

public enum AuraOrbitStyle {
    SHARDS("Shards", ShardsOrbitRenderer::new),
    SWORDS("Swords", SwordsOrbitRenderer::new),
    ORBS("Orbs", OrbsOrbitRenderer::new),
    RUNES("Runes", RunesOrbitRenderer::new),
    RINGS("Rings", RingsOrbitRenderer::new);

    public final String labelText;
    private final Function factoryFunction;

    AuraOrbitStyle(String arg0, Function arg1) {
        this.labelText = arg0;
        this.factoryFunction = arg1;
    }

    public ICosmeticEffectRenderer createRenderer(OrbitAuraSpec arg0) {
        return ((ICosmeticEffectRenderer) this.factoryFunction.apply(arg0));
    }

    public static AuraOrbitStyle fromDisplayName(String arg0) {
        int var5 = 0;
        AuraOrbitStyle[] var1 = values();
        int var8 = var1.length;
        var5 = 0;
        while (true) {
            if (var5 >= var8) break;
            int var18 = var5;
            AuraOrbitStyle var13 = var1[var18];
            if (var13.labelText.equalsIgnoreCase(arg0)) {
                return var13;
            }
            var5 = (var5 + 1);
        }
        return SHARDS;
    }

}
