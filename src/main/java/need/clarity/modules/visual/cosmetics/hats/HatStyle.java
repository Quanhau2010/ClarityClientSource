package need.clarity.modules.visual.cosmetics.hats;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import need.clarity.modules.visual.cosmetics.ICosmeticEffectRenderer;

public enum HatStyle {
    CAT_EARS("Ears", "Cat Ears", CatEarsHatRenderer::new),
    FOX_EARS("Ears", "Fox Ears", FoxEarsHatRenderer::new),
    BUNNY_EARS("Ears", "Bunny Ears", BunnyEarsHatRenderer::new),
    BEAR_EARS("Ears", "Bear Ears", BearEarsHatRenderer::new),
    DEMON_HORNS("Horns", "Demon Horns", DemonHornsHatRenderer::new),
    STAG_ANTLERS("Horns", "Stag Antlers", StagAntlersHatRenderer::new),
    REINDEER_ANTLERS("Horns", "Reindeer Antlers", ReindeerAntlersHatRenderer::new),
    UNICORN_HORN("Horns", "Unicorn Horn", UnicornHornHatRenderer::new),
    VIKING_HORNS("Horns", "Viking Horns", VikingHornsHatRenderer::new),
    CLASSIC_RING("Halos", "Classic Ring", ClassicRingHaloRenderer::new),
    DOUBLE_RING("Halos", "Double Ring", DoubleRingHaloRenderer::new),
    THORN_HALO("Halos", "Thorn Halo", ThornHaloRenderer::new),
    ORBITING_ORBS("Halos", "Orbiting Orbs", OrbitingOrbsHaloRenderer::new),
    RUNIC_HALO("Halos", "Runic Halo", RunicHaloRenderer::new),
    ROYAL_CROWN("Crowns", "Royal Crown", RoyalCrownRenderer::new),
    SPIKED_CROWN("Crowns", "Spiked Crown", SpikedCrownRenderer::new),
    LAUREL_WREATH("Crowns", "Laurel Wreath", LaurelWreathCrownRenderer::new),
    TIARA("Crowns", "Tiara", TiaraCrownRenderer::new),
    FLAME_CROWN("Crowns", "Flame Crown", FlameCrownRenderer::new),
    ICE_CROWN("Crowns", "Ice Crown", IceCrownRenderer::new),
    FLOATING_CROWN("Crowns", "Floating Crown", FloatingCrownRenderer::new),
    CHINA_HAT("Misc", "China Hat", ChinaHatRenderer::new);

    public static final List entriesList;
    public final String labelText;
    public final String labelText1;
    private final Function factoryFunction;

    HatStyle(String arg0, String arg1, Function arg2) {
        this.labelText = arg0;
        this.labelText1 = arg1;
        this.factoryFunction = arg2;
    }

    public ICosmeticEffectRenderer createRenderer(HatRenderSpec arg0) {
        return ((ICosmeticEffectRenderer) this.factoryFunction.apply(arg0));
    }

    public static List evaluate(String arg0) {
        int var5 = 0;
        ArrayList var1 = new ArrayList();
        HatStyle[] var7 = values();
        int var9 = var7.length;
        var5 = 0;
        while (true) {
            if (var5 >= var9) break;
            int var21 = var5;
            HatStyle var13 = var7[var21];
            if (var13.labelText.equals(arg0)) {
                boolean var18 = var1.add(var13);
            }
            var5 = (var5 + 1);
        }
        return var1;
    }

    public static HatStyle fromDisplayName(String arg0) {
        int var5 = 0;
        HatStyle[] var1 = values();
        int var8 = var1.length;
        var5 = 0;
        while (true) {
            if (var5 < var8) {
                int var18 = var5;
                HatStyle var12 = var1[var18];
                if (var12.labelText1.equalsIgnoreCase(arg0)) {
                    return var12;
                }
            }
            return CHINA_HAT;
            var5 = (var5 + 1);
        }
    }

}
