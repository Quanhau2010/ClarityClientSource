package need.clarity.modules.visual;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import need.clarity.config.ClarityConfig;

public final class ModuleSuffixFormatter {
    private static final Map entriesMap;

    private ModuleSuffixFormatter() {
    }

    public static String resolveLabel(String arg0) {
        String var17 = null;
        Supplier var6 = ((Supplier) entriesMap.get(arg0));
        if (var6 == null) {
            return null;
        }
        try {
            String var10 = ((String) var6.get());
            if (var10 != null) {
                if (var10.isEmpty()) {
                }
            }
            var17 = null;
            var17 = var10;
        } catch (Throwable ignored) {
        }
        return var17;
    }

    private static void applyStep(String arg0, String arg1, String arg2) {
        Map var3 = entriesMap;
        Supplier var9 = () -> ModuleSuffixFormatter.lambda$c(arg0, arg1, arg2);
        Object var8 = var3.put(arg0, var9);
    }

    private static void applyStep(String arg0, String arg1, boolean arg2, String arg3, String arg4) {
        Map var5 = entriesMap;
        Supplier var13 = () -> ModuleSuffixFormatter.lambda$b(arg0, arg1, arg2, arg3, arg4);
        Object var12 = var5.put(arg0, var13);
    }

    private static String resolveLabel(String arg0, String arg1, String arg2) {
        String var14 = null;
        Object var7 = ClarityConfig.getSetting(arg0, arg1);
        if ((var7 instanceof String)) {
            String var11 = ((String) var7);
            if (!var11.isEmpty()) {
                var14 = var11;
            } else {
            }
        }
        var14 = arg2;
        return var14;
    }

    private static boolean isEligible(String arg0, String arg1, boolean arg2) {
        boolean var13 = false;
        Object var7 = ClarityConfig.getSetting(arg0, arg1);
        if ((var7 instanceof Boolean)) {
            var13 = ((Boolean) var7);
        } else {
            var13 = arg2;
        }
        return var13;
    }

    static {
        HashMap var0 = new HashMap();
        entriesMap = var0;
        applyStep("Kill Aura", "Aim At", "Dynamic");
        applyStep("Auto Anchor", "Anchor Mode", "Normal");
        applyStep("Chest Stealer", "Collection Mode", "Normal");
        applyStep("Overtick", "Release Mode", "Flow");
        applyStep("Disabler", "Mode", "Spartan Trident");
        applyStep("Client Spoofer", "Client", "Vanilla");
        applyStep("Anti Cob", "Mode", "Water");
        applyStep("WindCharge", "Mode", "Immediate");
        applyStep("MoveFix", "Mode", "Silent");
        applyStep("Fly", "Mode", "Creative");
        applyStep("NoFall", "Mode", "Normal");
        applyStep("Animations", "Mode", "Simple");
        applyStep("Tracers", "Style", "Lines");
        applyStep("Hats", "Hat", "China Hat");
        applyStep("Hand Shaders", "Shader", "Chroma");
        applyStep("Player ESP", "Mode", "Outline");
        applyStep("Hit Particles", "Particle", "Crit");
        applyStep("Hit Sounds", "Sound", "Attack Crit");
        applyStep("Kill Sounds", "Sound", "Headshot");
        applyStep("Chams", "Mode", "Color");
        applyStep("Jump Effect", "Particle", "Cloud");
        applyStep("Wings", "Style", "Angel");
        applyStep("Target ESP", "Mode", "Box");
        applyStep("Particles", "Particle", "Snowflake");
        applyStep("Kill Effect", "Effect", "Shockwave");
        applyStep("Player Model", "Model", "Kenny");
        applyStep("Aim Assist", "Silent Rotations", false, "Silent", "Normal");
        applyStep("Auto Armour", "Silent", false, "Silent", "Normal");
        applyStep("Inventory Cleaner", "Silent", false, "Silent", "Normal");
        applyStep("Silent Totem", "Bypass", true, "Bypass", "Normal");
        applyStep("Inventory Walk", "Grim", false, "Grim", "Normal");
        Map var35 = entriesMap;
        Supplier var99 = () -> ModuleSuffixFormatter.lambda$a();
        Object var36 = var35.put("Aura", var99);
    }

}
