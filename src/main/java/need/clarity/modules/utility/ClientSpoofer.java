package need.clarity.modules.utility;

import need.clarity.api.Hooks;
import need.clarity.modules.ModuleBase;

public class ClientSpoofer extends ModuleBase {
    private static ClientSpoofer INSTANCE;

    public ClientSpoofer() {
        super("Client Spoofer", "Utility");
        INSTANCE = this;
        Hooks.Bool0 var7 = () -> ClientSpoofer.lambda$d();
        Hooks.bool0("ClientSpoofer.enabled", var7);
        Hooks.Obj0 var8 = () -> ClientSpoofer.enabled();
        Hooks.obj0("ClientSpoofer.brand", var8);
    }

    public static ClientSpoofer evaluate() {
        return INSTANCE;
    }

    public String resolveLabel() {
        String var17 = null;
        int var6 = 0;
        String var8 = ((String) this.getSetting("Client", "Vanilla"));
        var6 = -1;
        switch (var8.hashCode()) {
            case -1813153191 -> {}
            case -767248267 -> {}
            case 68066119 -> {}
            case 78395117 -> {}
            case 685432963 -> {}
            case 1189713103 -> {}
            case 1318402433 -> {}
            case 1602017974 -> {}
            case 1897755483 -> {}
            case 2096654533 -> {}
            default -> {}
        }
        if (var8.equals("Forge")) {
            var6 = 3;
            if (var8.equals("LabyMod")) {
                var6 = 7;
                if (var8.equals("Lunar Client")) {
                    var6 = 2;
                    if (var8.equals("Vanilla")) {
                        var6 = 0;
                        if (var8.equals("Feather")) {
                            var6 = 6;
                            if (var8.equals("NeoForge")) {
                                var6 = 4;
                                if (var8.equals("Sodium")) {
                                    var6 = 8;
                                    if (var8.equals("Badlion")) {
                                        var6 = 5;
                                        if (var8.equals("Fabric")) {
                                            var6 = 1;
                                            if (var8.equals("Quilt")) {
                                                var6 = 9;
                                            }
                                            switch (var6) {
                                                case 0 -> {}
                                                case 1 -> {}
                                                case 2 -> {}
                                                case 3 -> {}
                                                case 4 -> {}
                                                case 5 -> {}
                                                case 6 -> {}
                                                case 7 -> {}
                                                case 8 -> {}
                                                case 9 -> {}
                                                default -> {}
                                            }
                                            var17 = "badlion";
                                            var17 = "lunarclient:v2.21.5-2540";
                                            var17 = "forge";
                                            var17 = "fabric";
                                            var17 = "sodium";
                                            var17 = "neoforge";
                                            var17 = "vanilla";
                                            var17 = "quilt";
                                            var17 = "labymod";
                                            var17 = "feather";
                                            var17 = "vanilla";
                                            return var17;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}
