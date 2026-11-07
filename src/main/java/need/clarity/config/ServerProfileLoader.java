package need.clarity.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import need.clarity.modules.ModuleRegistry;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.event.Event;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.network.Connection;

public final class ServerProfileLoader {
    private static final int DEFAULT_PORT = 25565;
    private static volatile boolean registered;

    private ServerProfileLoader() {
    }

    public static void register() {
        if (registered) {
            return;
        }
        registered = true;
        try {
            Event var4 = ClientPlayConnectionEvents.JOIN;
            ClientPlayConnectionEvents.Join var1 = (p0, p1, p2) -> ServerProfileLoader.lambda$register$0(p0, p1, p2);
            var4.register(var1);
        } catch (Throwable ignored) {
        }
    }

    private static void applyForConnection(ClientPacketListener arg0) {
        if (arg0 == null) {
            return;
        }
        List var12 = candidates(resolveRawAddress(arg0));
        if (var12.isEmpty()) {
            return;
        }
        JsonObject var15 = ClarityConfig.getCurrent();
        if (var15 == null) {
            return;
        }
        JsonObject var19 = asObject(var15.get("__serverProfiles"));
        if (var19 != null) {
            if (var19.size() == 0) {
            }
        }
        return;
        String var24 = matchProfile(var19, var12);
        if (var24 != null) {
            if (var24.isEmpty()) {
            }
        }
        return;
        JsonObject var30 = asObject(var15.get("__profiles"));
        if (var30 == null) {
            return;
        }
        JsonObject var34 = asObject(var30.get(var24));
        if (var34 == null) {
            return;
        }
        ClarityConfig.applyProfileSnapshot(var34);
        try {
            ClarityConfig.setActiveProfile(var24);
        } catch (Throwable ignored) {
        }
        ModuleRegistry.syncFromConfig();
    }

    private static String resolveRawAddress(ClientPacketListener arg0) {
        try {
            ServerData var4 = arg0.getServerData();
            if (var4 != null) {
                if (var4.ip != null) {
                    if (!var4.ip.isBlank()) {
                        String var12 = var4.ip;
                    }
                }
            }
        } catch (Throwable ignored) {
        }
        return var12;
        try {
            Connection var15 = arg0.getConnection();
            if (var15 != null) {
                SocketAddress var18 = var15.getRemoteAddress();
                if (var18 != null) {
                    String var22 = var18.toString();
                }
            }
        } catch (Throwable ignored) {
        }
        return var22;
        return null;
    }

    private static List candidates(String arg0) {
        ArrayList var1 = new ArrayList(3);
        String var7 = normalise(arg0);
        if (var7 == null) {
            return var1;
        }
        addUnique(var1, var7);
        String var12 = bareHost(var7);
        if (var12 != null) {
            if (!var12.isEmpty()) {
                addUnique(var1, var12);
                if (!hasPort(var7)) {
                    String var24 = (var12) + ":25565";
                    addUnique(var1, var24);
                }
                return var1;
            }
        }
    }

    private static void addUnique(List arg0, String arg1) {
        if (arg1 != null) {
            if (!arg1.isEmpty()) {
                if (!arg0.contains(arg1)) {
                    boolean var9 = arg0.add(arg1);
                }
                return;
            }
        }
    }

    private static String normalise(String arg0) {
        String var4 = null;
        if (arg0 == null) {
            return null;
        }
        var4 = arg0.trim();
        if (var4.isEmpty()) {
            return null;
        }
        int var12 = var4.lastIndexOf(47);
        if (var12 >= 0) {
            int var22 = (var4.length() - 1);
            if (var12 < var22) {
                String var15 = var4;
                int var24 = (var12 + 1);
                var4 = var15.substring(var24);
            }
            String var17 = var4;
            Locale var25 = Locale.ROOT;
            return var17.toLowerCase(var25);
        }
    }

    private static String bareHost(String arg0) {
        String var18 = null;
        if (arg0 == null) {
            return null;
        }
        if (arg0.startsWith("[")) {
            int var10 = arg0.indexOf(93);
            if (var10 > 0) {
                int var23 = (var10 + 1);
                return arg0.substring(0, var23);
            }
        }
        int var15 = arg0.indexOf(58);
        if (var15 > 0) {
            var18 = arg0.substring(0, var15);
        } else {
            var18 = arg0;
        }
        return var18;
    }

    private static boolean hasPort(String arg0) {
        int var18 = 0;
        int var10 = 0;
        if (arg0 == null) {
            return false;
        }
        if (arg0.startsWith("[")) {
            int var12 = arg0.indexOf(93);
            if (var12 >= 0) {
                int var15 = (var12 + 1);
                int var23 = arg0.length();
                if (var15 < var23) {
                    int var25 = (var12 + 1);
                    if (arg0.charAt(var25) == 58) {
                        var18 = 1;
                    }
                }
            } else {
                var18 = 0;
            }
            return var18;
        }
        if (arg0.indexOf(58) >= 0) {
            var10 = 1;
        } else {
            var10 = 0;
        }
        return var10;
    }

    private static String matchProfile(JsonObject arg0, List arg1) {
        Iterator var10 = arg1.iterator();
        while (true) {
            if (!var10.hasNext()) break;
            String var15 = ((String) var10.next());
            Iterator var18 = arg0.entrySet().iterator();
            while (true) {
                if (!var18.hasNext()) continue;
            }
        }
        Map.Entry var23 = ((Map.Entry) var18.next());
        String var27 = normalise(((String) var23.getKey()));
        if (var27 == null) continue;
        if (!var27.equals(var15)) {
            return null;
        }
        JsonElement var33 = ((JsonElement) var23.getValue());
        if (var33 == null) continue;
        if (!var33.isJsonPrimitive()) continue;
        return var33.getAsString();
    }

    private static JsonObject asObject(JsonElement arg0) {
        JsonObject var5 = null;
        if (arg0 != null) {
            if (arg0.isJsonObject()) {
                var5 = arg0.getAsJsonObject();
            }
        } else {
            var5 = null;
        }
        return var5;
    }

    static {
        registered = false;
    }

}
