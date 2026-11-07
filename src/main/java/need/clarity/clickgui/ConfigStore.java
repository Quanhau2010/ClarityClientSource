package need.clarity.clickgui;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import need.clarity.config.ClarityConfig;
import need.clarity.config.ConfigFiles;
import need.clarity.modules.ModuleBase;
import need.clarity.modules.ModuleRegistry;
import need.clarity.server.ClarityServer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;

public final class ConfigStore implements GuiStore {
    private final GuiData data;
    private List filesCache;
    private long filesAt;

    public ConfigStore(GuiData arg0) {
        this.filesCache = List.of();
        this.filesAt = 0L;
        this.data = arg0;
    }

    private static JsonObject root() {
        return ClarityConfig.getCurrent();
    }

    private static JsonObject obj(JsonObject arg0, String arg1) {
        JsonElement var2 = arg0.get(arg1);
        if (var2 != null) {
            if (var2.isJsonObject()) {
                return var2.getAsJsonObject();
            }
            JsonObject var3 = new JsonObject();
            arg0.add(arg1, ((JsonElement) var3));
            return var3;
        }
    }

    private static JsonObject peek(JsonObject arg0, String arg1) {
        if (arg0 == null) {
        } else {
        }
        JsonElement var2 = arg0.get(arg1);
        if (var2 != null) {
            if (var2.isJsonObject()) {
            } else {
            }
            return null;
        }
    }

    private static Object fromJson(JsonElement arg0) {
        ArrayList var1 = null;
        if (arg0 != null) {
            if (arg0.isJsonNull()) {
            }
        }
        return null;
        if (arg0.isJsonArray()) {
            var1 = new ArrayList();
            Iterator var2 = arg0.getAsJsonArray().iterator();
            while (true) {
                if (!var2.hasNext()) break;
                JsonElement var3 = ((JsonElement) var2.next());
                if (var3.isJsonPrimitive()) {
                    var1.add(var3.getAsString());
                }
            }
            return var1;
        }
        if (arg0.isJsonPrimitive()) {
            var1 = arg0.getAsJsonPrimitive();
            if (var1.isBoolean()) {
                return var1.getAsBoolean();
            }
            if (var1.isNumber()) {
                return var1.getAsDouble();
            }
            return var1.getAsString();
        }
        return null;
    }

    private static JsonElement toJson(Object arg0) {
        Boolean var1 = null;
        if (arg0 == null) {
            return JsonNull.INSTANCE;
        }
        if ((arg0 instanceof Boolean)) {
            var1 = ((Boolean) arg0);
            return new JsonPrimitive(var1);
        }
        if ((arg0 instanceof Number)) {
            var1 = ((Number) arg0);
            return new JsonPrimitive(var1);
        }
        if ((arg0 instanceof List)) {
            var1 = ((List) arg0);
            JsonArray var2 = new JsonArray();
            Iterator var3 = var1.iterator();
            while (true) {
                if (!var3.hasNext()) break;
                Object var4 = var3.next();
                var2.add(String.valueOf(var4));
            }
            return var2;
        }
        return new JsonPrimitive(String.valueOf(arg0));
    }

    public boolean isEnabled(String arg0) {
        ModuleBase var2 = ModuleRegistry.get(arg0);
        if (var2 != null) {
        } else {
        }
        return ClarityConfig.isEnabled(arg0);
    }

    public void setEnabled(String arg0, boolean arg1) {
        ModuleBase var3 = ModuleRegistry.get(arg0);
        if (var3 != null) {
            var3.setEnabled(arg1);
        } else {
            ClarityConfig.setModuleEnabled(arg0, arg1);
        }
        ClarityConfig.mutate((p0) -> ConfigStore.lambda$setEnabled$0(p0));
    }

    public Object value(String arg0, String arg1) {
        JsonObject var3 = peek(peek(root(), arg0), "values");
        if (var3 == null) {
        } else {
        }
        return fromJson(var3.get(arg1));
    }

    public void setValue(String arg0, String arg1, Object arg2) {
        ClarityConfig.mutate((p0) -> ConfigStore.lambda$setValue$0(arg0, arg1, arg2, p0));
    }

    public void resetModule(String arg0) {
        ClarityConfig.mutate((p0) -> ConfigStore.lambda$resetModule$0(arg0, p0));
    }

    public String bind(String arg0) {
        JsonObject var2 = peek(root(), arg0);
        if (var2 == null) {
        } else {
        }
        JsonElement var3 = var2.get("bind");
        if (var3 != null) {
            if (var3.isJsonNull()) {
            }
        }
        return var3.getAsString();
    }

    public String bindMode(String arg0) {
        JsonObject var2 = peek(root(), arg0);
        if (var2 == null) {
        } else {
        }
        JsonElement var3 = var2.get("bindMode");
        if (var3 != null) {
            if (var3.isJsonNull()) {
            }
        }
        return var3.getAsString();
    }

    public void setBind(String arg0, String arg1, String arg2) {
        ClarityConfig.mutate((p0) -> ConfigStore.lambda$setBind$0(arg0, arg1, arg2, p0));
    }

    public Object gui(String arg0) {
        return this.value("Click GUI", arg0);
    }

    public void setGui(String arg0, Object arg1) {
        ClarityConfig.mutate((p0) -> ConfigStore.lambda$setGui$0(arg0, arg1, p0));
    }

    public Map theme() {
        List var3 = null;
        HashMap var1 = new HashMap();
        JsonObject var2 = peek(root(), "__theme");
        if (var2 != null) {
            if (!var2.has("accent")) {
            }
        }
        if (this.data.themes.isEmpty()) {
        } else {
        }
        var3 = ((List) "TokyoNight".getOrDefault(null, ((List) this.data.themes.values().iterator().next())));
        if (var3 != null) {
            var1.put("accent", ((String) var3.get(0)));
            var1.put("accent2", ((String) var3.get(1)));
            var1.put("background", ((String) var3.get(2)));
            var1.put("surface", ((String) var3.get(3)));
            var1.put("border", ((String) var3.get(4)));
        }
        return var1;
        new String[7][0] = "accent";
        new String[7][1] = "accent2";
        new String[7][2] = "background";
        new String[7][3] = "surface";
        new String[7][4] = "border";
        new String[7][5] = "text";
        new String[7][6] = "muted";
        var3 = new String[7];
        int var4 = var3.length;
        int var5 = 0;
        while (true) {
            if (var5 >= var4) break;
            Object var6 = var3[var5];
            JsonElement var7 = var2.get(var6);
            if (var7 != null) {
                if (var7.isJsonPrimitive()) {
                    var1.put(var6, var7.getAsString());
                }
                var5++;
            }
        }
        if (!var1.containsKey("background")) {
            if (var1.containsKey("surface")) {
                var3 = Theme.hex(((String) var1.get("surface")), 1448742);
                var1.put("background", Theme.toHex(Theme.mix((-16777216 | var3), -16777216, 0.45F)));
            }
            return var1;
        }
    }

    public String themeName() {
        String var2 = null;
        Object var1 = this.gui("Theme");
        if ((var1 instanceof String)) {
            var2 = ((String) var1);
            if (this.data.themes.containsKey(var2)) {
                return var2;
            }
            var2 = ((String) this.theme().get("accent"));
            if (var2 != null) {
                Iterator var3 = this.data.themes.entrySet().iterator();
                while (true) {
                    if (!var3.hasNext()) break;
                    Map.Entry var4 = ((Map.Entry) var3.next());
                    if (((String) ((List) var4.getValue()).get(0)).equalsIgnoreCase(var2)) {
                        return ((String) var4.getKey());
                    }
                }
                return "";
            }
        }
    }

    public void applyTheme(String arg0, List arg1) {
        ClarityConfig.mutate((p0) -> ConfigStore.lambda$applyTheme$0(arg1, arg0, p0));
    }

    public boolean themeColorsEnabled() {
        JsonObject var1 = peek(root(), "__theme");
        if (var1 == null) {
        } else {
        }
        JsonElement var2 = var1.get("enabled");
        if (var2 != null) {
            if (var2.isJsonPrimitive()) {
                if (var2.getAsBoolean()) {
                }
            }
            return false;
        }
    }

    public void setThemeColorsEnabled(boolean arg0) {
        ClarityConfig.mutate((p0) -> ConfigStore.lambda$setThemeColorsEnabled$0(arg0, p0));
    }

    public List profiles() {
        JsonObject var1 = peek(root(), "__profiles");
        if (var1 == null) {
        } else {
        }
        return new ArrayList(var1.keySet());
    }

    public String activeProfile() {
        JsonElement var1 = root().get("__activeProfile");
        if (var1 != null) {
            if (var1.isJsonPrimitive()) {
            } else {
            }
            return "";
        }
    }

    public void loadProfile(String arg0) {
        JsonObject var2 = peek(root(), "__profiles");
        if (var2 == null) {
        } else {
        }
        JsonObject var3 = peek(var2, arg0);
        if (var3 == null) {
            return;
        }
        ClarityConfig.applyProfileSnapshot(var3);
        ClarityConfig.setActiveProfile(arg0);
        ClarityConfig.mutate((p0) -> ConfigStore.lambda$loadProfile$0(p0));
        try {
            ModuleRegistry.syncFromConfig();
        } catch (Throwable ignored) {
        }
        Object var4 = null;
    }

    public void saveProfile(String arg0) {
        ClarityConfig.mutate((p0) -> this.lambda$saveProfile$0(arg0, p0));
    }

    public void deleteProfile(String arg0) {
        ClarityConfig.mutate((p0) -> this.lambda$deleteProfile$0(arg0, p0));
    }

    private static JsonArray bindArr(JsonObject arg0) {
        JsonElement var1 = arg0.get("__binds");
        if (var1 != null) {
            if (var1.isJsonArray()) {
                return var1.getAsJsonArray();
            }
            JsonArray var2 = new JsonArray();
            arg0.add("__binds", ((JsonElement) var2));
            return var2;
        }
    }

    static String nameOf(JsonElement arg0) {
        if (arg0.isJsonObject()) {
        } else {
        }
        Object var1 = null;
        if (var1 != null) {
            if (var1.isJsonPrimitive()) {
            } else {
            }
            return null;
        }
    }

    public List bindSets() {
        ArrayList var1 = new ArrayList();
        JsonElement var2 = root().get("__binds");
        if (var2 != null) {
            if (var2.isJsonArray()) {
                Iterator var3 = var2.getAsJsonArray().iterator();
                while (true) {
                    if (!var3.hasNext()) break;
                    JsonElement var4 = ((JsonElement) var3.next());
                    if (var4.isJsonObject()) {
                        if (var4.getAsJsonObject().has("name")) {
                            var1.add(var4.getAsJsonObject().get("name").getAsString());
                        }
                    }
                }
                return var1;
            }
        }
    }

    public void loadBindSet(String arg0) {
        ClarityConfig.mutate((p0) -> ConfigStore.lambda$loadBindSet$0(arg0, p0));
    }

    public void saveBindSet(String arg0) {
        ClarityConfig.mutate((p0) -> this.lambda$saveBindSet$0(arg0, p0));
    }

    public void deleteBindSet(String arg0) {
        ClarityConfig.mutate((p0) -> ConfigStore.lambda$deleteBindSet$0(arg0, p0));
    }

    public Map serverProfiles() {
        LinkedHashMap var1 = new LinkedHashMap();
        JsonObject var2 = peek(root(), "__serverProfiles");
        if (var2 != null) {
            Iterator var3 = var2.entrySet().iterator();
            while (true) {
                if (!var3.hasNext()) break;
                Map.Entry var4 = ((Map.Entry) var3.next());
                if (((JsonElement) var4.getValue()).isJsonPrimitive()) {
                    var1.put(((String) var4.getKey()), ((JsonElement) var4.getValue()).getAsString());
                }
            }
            return var1;
        }
    }

    public void mapServer(String arg0, String arg1) {
        ClarityConfig.mutate((p0) -> ConfigStore.lambda$mapServer$0(arg0, arg1, p0));
    }

    public void unmapServer(String arg0) {
        ClarityConfig.mutate((p0) -> ConfigStore.lambda$unmapServer$0(arg0, p0));
    }

    public String currentServer() {
        ServerData var1 = null;
        try {
            var1 = Minecraft.getInstance().getCurrentServer();
            if (var1 != null) {
                if (var1.ip != null) {
                }
            }
        } catch (Throwable ignored) {
        }
        return var1.ip;
        return "";
    }

    private static List assetIds(String arg0) {
        ArrayList var1 = new ArrayList();
        JsonElement var2 = root().get(arg0);
        if (var2 != null) {
            if (var2.isJsonArray()) {
                Iterator var3 = var2.getAsJsonArray().iterator();
                while (true) {
                    if (!var3.hasNext()) break;
                    JsonElement var4 = ((JsonElement) var3.next());
                    if (var4.isJsonObject()) {
                        if (var4.getAsJsonObject().has("id")) {
                            var1.add(var4.getAsJsonObject().get("id").getAsString());
                        }
                    }
                }
                return var1;
            }
        }
    }

    public List customSkins() {
        return assetIds("__customSkins");
    }

    public List customCapes() {
        return assetIds("__customCapes");
    }

    public String exportProfile(String arg0) {
        JsonObject var2 = peek(root(), "__profiles");
        if (var2 == null) {
        } else {
        }
        JsonObject var3 = peek(var2, arg0);
        if (var3 == null) {
            throw new IllegalArgumentException("No profile called " + (arg0));
        }
        return ConfigFiles.export(arg0, var3).getFileName().toString();
    }

    public String importProfile(Path arg0, boolean arg1) {
        String var5 = null;
        ConfigFiles.Imported var3 = ConfigFiles.read(arg0);
        JsonObject var4 = peek(root(), "__profiles");
        var5 = var3.name();
        if (var4 != null) {
            if (var4.get(var5) != null) {
                int var6 = (var4.get(var5).equals(var3.snapshot()));
                if (var6 == 0) {
                    if (var4 == null) {
                    } else {
                    }
                    var5 = ConfigFiles.uniqueName(var4.keySet(), var5);
                }
                String var7 = var5;
                if (var6 == 0) {
                    ClarityConfig.mutate((p0) -> ConfigStore.lambda$importProfile$0(var7, var3, p0));
                }
                if (arg1) {
                    this.loadProfile(var7);
                }
                this.filesAt = 0L;
                return var7;
            }
        }
    }

    public List configFiles() {
        long var1 = System.currentTimeMillis();
        if ((var1 - this.filesAt) > 1500L) {
            this.filesCache = ConfigFiles.list();
            this.filesAt = var1;
        }
        return this.filesCache;
    }

    public void openConfigFolder(Consumer arg0) {
        ConfigFiles.openFolder(arg0);
    }

    public String configFolderPath() {
        return ConfigFiles.dir().toAbsolutePath().normalize().toString();
    }

    public void setClipboard(String arg0) {
        try {
            Minecraft.getInstance().keyboardHandler.setClipboard(arg0);
        } catch (Throwable ignored) {
        }
    }

    public boolean pickConfigFile(Consumer arg0) {
        return ConfigFiles.pickFile(arg0);
    }

    public boolean webMode() {
        return ClarityConfig.webGuiActive();
    }

    public void switchToWebGui() {
        this.setGui("GUI", "Web");
        ClarityConfig.flushSync();
        this.openWebGui();
    }

    public void openWebGui() {
        // Web GUI disabled
    }

    public String clipboard() {
        try {
        } catch (Throwable ignored) {
        }
        return Minecraft.getInstance().keyboardHandler.getClipboard();
    }

}
