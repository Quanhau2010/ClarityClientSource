package need.clarity.modules.visual.cosmetics.aura;

import java.util.function.Function;
import need.clarity.modules.visual.cosmetics.ICosmeticEffectRenderer;

public enum AuraElementStyle {
    FLAME("Flame", 16761395, 16726802, FlameAuraRenderer::new),
    FROST("Frost", 12907007, 4033791, FrostAuraRenderer::new),
    VOID("Void", 13073919, 3805050, VoidAuraRenderer::new),
    SAKURA("Sakura", 16763103, 16735902, SakuraAuraRenderer::new),
    STORM("Storm", 13624319, 5661583, StormAuraRenderer::new);

    public final String labelText;
    public final int counterValue;
    public final int counterValue1;
    private final Function factoryFunction;

    AuraElementStyle(String arg0, int arg1, int arg2, Function arg3) {
        this.labelText = arg0;
        this.counterValue = arg1;
        this.counterValue1 = arg2;
        this.factoryFunction = arg3;
    }

    public ICosmeticEffectRenderer createRenderer(ElementalAuraSpec arg0) {
        return ((ICosmeticEffectRenderer) this.factoryFunction.apply(arg0));
    }

    public static AuraElementStyle fromDisplayName(String arg0) {
        int var5 = 0;
        AuraElementStyle[] var1 = values();
        int var8 = var1.length;
        var5 = 0;
        while (true) {
            if (var5 < var8) {
                int var18 = var5;
                AuraElementStyle var12 = var1[var18];
                if (var12.labelText.equalsIgnoreCase(arg0)) {
                    return var12;
                }
            }
            return FLAME;
            var5 = (var5 + 1);
        }
    }

}
