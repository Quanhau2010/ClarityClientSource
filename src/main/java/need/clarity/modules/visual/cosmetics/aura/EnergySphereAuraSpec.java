package need.clarity.modules.visual.cosmetics.aura;

import java.util.List;

public record EnergySphereAuraSpec(double radius, int frequency, List ripples) {

    public static EnergySphereAuraSpec evaluate() {
        List var4 = List.of();
        EnergySphereAuraSpec var0 = new EnergySphereAuraSpec(1.2, 3, var4);
        return var0;
    }

    public EnergySphereAuraSpec evaluate(List arg0) {
        double var6 = this.radius;
        int var8 = this.frequency;
        EnergySphereAuraSpec var2 = new EnergySphereAuraSpec(var6, var8, arg0);
        return var2;
    }

    public EnergySphereAuraSpec evaluate(int arg0) {
        double var6 = this.radius;
        List var8 = this.ripples;
        EnergySphereAuraSpec var2 = new EnergySphereAuraSpec(var6, arg0, var8);
        return var2;
    }

}
