package need.clarity.modules.visual.cosmetics.aura;


public record ElementalAuraSpec(double radius, double bodyHeight) {

    public static ElementalAuraSpec evaluate() {
        ElementalAuraSpec var0 = new ElementalAuraSpec(1.0, 1.8);
        return var0;
    }

}
