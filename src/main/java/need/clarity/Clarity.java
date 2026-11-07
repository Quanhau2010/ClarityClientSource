package need.clarity;

import need.clarity.config.ClarityConfig;
import need.clarity.config.ServerProfileLoader;
import need.clarity.modules.ModuleRegistry;
import need.clarity.sec.ModuleBootstrap;
import need.clarity.server.ClarityServer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Clarity implements ModInitializer {
    public static final Logger LOGGER;
    private static boolean enabledState;

    static {
        LOGGER = LoggerFactory.getLogger("clarity");
        enabledState = false;
    }

    public Clarity() {
    }

    public void onInitialize() {
        ClarityConfig.load();
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            ClarityServer.init();
        }
        ModuleBootstrap.loadRemoteModules();
        new String[7][0] = "need.clarity.render.ModelHands";
        new String[7][1] = "need.clarity.render.AnimationsRenderer";
        new String[7][2] = "need.clarity.render.ThroughWalls";
        new String[7][3] = "need.clarity.render.ShaderHelper";
        new String[7][4] = "need.clarity.render.GLCompat";
        new String[7][5] = "need.clarity.render.ChamsRenderer";
        new String[7][6] = "need.clarity.render.ChamsCollector";
        String[] var4 = new String[7];
        int var3 = new String[7].length;
        int var2 = 0;
        while (true) {
            String var1 = var4[var2];
            try {
                Class.forName(var1, true, Clarity.class.getClassLoader());
            } catch (Throwable ignored) {
            }
            var2++;
            if (var2 < var3) continue;
        }
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            ModuleRegistry.syncFromConfig();
            ModuleRegistry.registerTickEvent();
            ServerProfileLoader.register();
        }
        if (!enabledState) {
            enabledState = true;
            if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
                ClientLifecycleEvents.CLIENT_STOPPING.register((p0) -> Clarity.lambda$0(p0));
            }
            Runtime.getRuntime().addShutdownHook(new Thread(() -> Clarity.lambda$1(), "clarity-shutdown"));
        }
    }

    public static Identifier id(String arg0) {
        return Identifier.fromNamespaceAndPath("clarity", arg0);
    }

}
