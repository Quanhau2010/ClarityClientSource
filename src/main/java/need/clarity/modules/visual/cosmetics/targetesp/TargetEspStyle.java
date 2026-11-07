package need.clarity.modules.visual.cosmetics.targetesp;

import java.util.function.Function;
import need.clarity.modules.visual.cosmetics.ICosmeticEffectRenderer;

public enum TargetEspStyle {
    CORNER_BRACKETS("Corner Brackets", CornerBracketsTargetRenderer::new),
    HEX_SHIELD("Hex Shield", HexShieldTargetRenderer::new),
    ROTATING_DIAMOND("Rotating Diamond", RotatingDiamondTargetRenderer::new),
    CHEVRON_LOCK_ON("Chevron Lock-on", ChevronLockOnTargetRenderer::new),
    ORBITING_TRIANGLES("Orbiting Triangles", OrbitingTrianglesTargetRenderer::new),
    SPINNING_RING("Spinning Ring", SpinningRingTargetRenderer::new),
    LIGHT_PILLAR("Light Pillar", LightPillarTargetRenderer::new),
    HOLOGRAM_SCAN("Hologram Scan", HologramScanTargetRenderer::new),
    SOUL_FLAME("Soul Flame", SoulFlameTargetRenderer::new, 11860223, 2788607);

    public final String labelText;
    public final int counterValue;
    public final int counterValue1;
    private final Function factoryFunction;

    TargetEspStyle(String arg0, Function arg1) {
        this(arg0, arg1, 0, 0);
    }

    TargetEspStyle(String arg0, Function arg1, int arg2, int arg3) {
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

    public ICosmeticEffectRenderer createRenderer(TargetEspRenderSpec arg0) {
        return ((ICosmeticEffectRenderer) this.factoryFunction.apply(arg0));
    }

    public static TargetEspStyle fromDisplayName(String arg0) {
        int var5 = 0;
        if (arg0 == null) {
            return null;
        }
        TargetEspStyle[] var8 = values();
        int var10 = var8.length;
        var5 = 0;
        while (true) {
            if (var5 >= var10) break;
            int var20 = var5;
            TargetEspStyle var14 = var8[var20];
            if (var14.labelText.equalsIgnoreCase(arg0)) {
                return var14;
            }
            var5 = (var5 + 1);
        }
        return null;
    }

}
