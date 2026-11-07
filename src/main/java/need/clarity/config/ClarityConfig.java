package need.clarity.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import java.io.BufferedReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;
import need.clarity.clickgui.GuiData;
import need.clarity.modules.ModuleBase;
import need.clarity.modules.ModuleRegistry;
import need.clarity.server.ClarityServer;
import net.fabricmc.loader.api.FabricLoader;

public class ClarityConfig {
    private static final Path CONFIG_PATH;
    private static final Gson GSON;
    private static volatile JsonObject current;
    private static volatile String cachedJson;
    private static final ConcurrentHashMap moduleEnabled;
    private static final ScheduledExecutorService SCHEDULER;
    private static volatile ScheduledFuture pendingWrite;
    private static final String[] GAME_OWNED;
    private static final Map recentLayout;
    private static volatile long recentLayoutAt;
    private static final long LAYOUT_PROTECT_MS = 2000L;

    public ClarityConfig() {
    }

    public static void load() {
        Path var0 = CONFIG_PATH;
        LinkOption[] var34 = new LinkOption[0];
        if (!(Files.exists(var0, var34))) {
            return;
        }
        try {
            Path var7 = CONFIG_PATH;
            Charset var35 = StandardCharsets.UTF_8;
            BufferedReader var8 = Files.newBufferedReader(var7, var35);
            try {
                JsonElement var10 = JsonParser.parseReader(var8);
                if (var10.isJsonObject()) {
                    JsonObject var14 = var10.getAsJsonObject();
                    String var16 = GSON.toJson(var14);
                    boolean var18 = LegacyConfigMigration.migrateChinaHat(var14, true);
                    ConfigSchema.normalizeRoot(var14);
                    current = var14;
                    rebuildLiveState();
                    Gson var21 = GSON;
                    JsonObject var38 = current;
                    cachedJson = var21.toJson(var38);
                    if (!cachedJson.equals(var16)) {
                        scheduleWrite();
                    }
                    if (var8 != null) {
                        var8.close();
                        if (var8 != null) {
                            try {
                                var8.close();
                            } catch (Throwable ignored) {
                            }
                            var25.addSuppressed(var30);
                        }
                        throw var25;
                    }
                }
            } catch (Throwable ignored) {
            }
        } catch (Exception ignored) {
        }
    }

    private static void rebuildLiveState() {
        boolean var39 = false;
        moduleEnabled.clear();
        Iterator var11 = current.entrySet().iterator();
        while (true) {
            if (var11.hasNext()) {
                Map.Entry var16 = ((Map.Entry) var11.next());
                String var19 = ((String) var16.getKey());
                if (var19.startsWith("__")) {
                }
            }
            return;
            JsonElement var24 = ((JsonElement) var16.getValue());
            if (!var24.isJsonObject()) {
            }
            Boolean var31 = ConfigSchema.bool(var24.getAsJsonObject().get("enabled"));
            ConcurrentHashMap var32 = moduleEnabled;
            if (var31 != null) {
                var39 = var31;
            } else {
                var39 = ConfigSchema.defaultEnabled(var19);
            }
            Boolean var37 = var39;
            Object var33 = var32.put(var19, var37);
        }
    }

    public static JsonObject getCurrent() {
        return current;
    }

    public static String getCurrentJson() {
        return cachedJson;
    }

    public static synchronized void update(String arg0) {
        int var74 = 0;
        try {
            JsonElement var12 = JsonParser.parseString(arg0);
            if (!var12.isJsonObject()) {
            }
        } catch (Exception ignored) {
        }
        return;
        try {
            JsonObject var16 = var12.getAsJsonObject();
            JsonObject var17 = current;
            Iterator var20 = var17.entrySet().iterator();
            while (true) {
                if (!var20.hasNext()) break;
                Map.Entry var25 = ((Map.Entry) var20.next());
                String var28 = ((String) var25.getKey());
                if (var28.startsWith("__")) continue;
                if (var16.has(var28)) continue;
                JsonElement var66 = ((JsonElement) var25.getValue()).deepCopy();
                var16.add(var28, ((JsonElement) var66));
            }
            String[] var35 = GAME_OWNED;
            int var37 = var35.length;
            var74 = 0;
            while (true) {
                if (var74 >= var37) break;
                int var60 = var74;
                String var49 = var35[var60];
                if (!var16.has(var49)) {
                    if (var17.has(var49)) {
                        JsonElement var69 = var17.get(var49).deepCopy();
                        var16.add(var49, ((JsonElement) var69));
                    }
                    var74 = (var74 + 1);
                }
            }
            boolean var41 = LegacyConfigMigration.migrateChinaHat(var16, true);
            ConfigSchema.normalizeRoot(var16);
            current = var16;
            reapplyRecentLayout(current);
            rebuildLiveState();
            Gson var45 = GSON;
            JsonObject var59 = current;
            cachedJson = var45.toJson(var59);
            scheduleWrite();
            try {
                ModuleRegistry.syncFromConfig();
            } catch (Throwable ignored) {
            }
            guiModeMayHaveChanged();
        } catch (Exception ignored) {
        }
    }

    public static synchronized void mutate(Consumer arg0) {
        try {
            JsonObject var2 = current;
            arg0.accept(var2);
            JsonObject var6 = current;
            Long var12 = System.currentTimeMillis();
            var6.addProperty("__rev", var12);
            rebuildLiveState();
            Gson var7 = GSON;
            JsonObject var11 = current;
            cachedJson = var7.toJson(var11);
            scheduleWrite();
        } catch (Exception ignored) {
        }
        guiModeMayHaveChanged();
    }

    private static void guiModeMayHaveChanged() {
        try {
            ClarityServer.syncServerState();
        } catch (Throwable ignored) {
        }
    }

    public static boolean isEnabled(String arg0) {
        boolean var9 = false;
        Boolean var5 = ((Boolean) moduleEnabled.get(arg0));
        if (var5 != null) {
            var9 = var5;
        } else {
            var9 = ConfigSchema.defaultEnabled(arg0);
        }
        return var9;
    }

    public static boolean webGuiActive() {
        return false;
    }

    public static void setModuleEnabled(String arg0, boolean arg1) {
        JsonObject var6 = null;
        ConcurrentHashMap var2 = moduleEnabled;
        Boolean var25 = arg1;
        Object var7 = var2.put(arg0, var25);
        JsonElement var9 = current.get(arg0);
        if (var9 != null) {
            if (var9.isJsonObject()) {
                var6 = var9.getAsJsonObject();
            } else {
                var6 = ConfigSchema.defaultModule(arg0);
                JsonObject var20 = current;
                JsonObject var28 = var6;
                var20.add(arg0, ((JsonElement) var28));
            }
            JsonObject var15 = var6;
            Boolean var27 = arg1;
            var15.addProperty("enabled", var27);
            Gson var16 = GSON;
            JsonObject var23 = current;
            cachedJson = var16.toJson(var23);
            scheduleWrite();
            return;
        }
    }

    public static int valuesHash(String arg0) {
        int var14 = 0;
        try {
            JsonElement var5 = current.get(arg0);
            if (var5 != null) {
                if (!var5.isJsonObject()) {
                }
            }
        } catch (Exception ignored) {
        }
        return 0;
        try {
            JsonElement var11 = var5.getAsJsonObject().get("values");
            if (var11 == null) {
                var14 = 0;
            } else {
                var14 = var11.hashCode();
            }
            return var14;
        } catch (Exception ignored) {
        }
    }

    public static Object getSetting(String arg0, String arg1) {
        if ("Click GUI".equals(arg0) && "GUI".equals(arg1)) {
            return "In-game";
        }
        try {
            JsonElement var8 = current.get(arg0);
            if (var8 != null) {
                if (!var8.isJsonObject()) {
                }
            }
        } catch (Exception ignored) {
        }
        return null;
        try {
            JsonElement var17 = var8.getAsJsonObject().get("values");
            if (var17 != null) {
                if (!var17.isJsonObject()) {
                }
            }
        } catch (Exception ignored) {
        }
        return null;
        try {
            JsonElement var24 = var17.getAsJsonObject().get(arg1);
            if (var24 != null) {
                if (var24.isJsonNull()) {
                }
            }
        } catch (Exception ignored) {
        }
        return null;
        try {
            JsonPrimitive var30 = var24.getAsJsonPrimitive();
            if (var30.isBoolean()) {
                Boolean var43 = var30.getAsBoolean();
            }
        } catch (Exception ignored) {
        }
        return var43;
        try {
            if (var30.isNumber()) {
                Double var40 = var30.getAsDouble();
            }
        } catch (Exception ignored) {
        }
        return var40;
        try {
            String var36 = var30.getAsString();
        } catch (Exception ignored) {
        }
        return var36;
    }

    public static String getString(String arg0, String arg1, String arg2) {
        String var19 = null;
        try {
            JsonElement var12 = current.getAsJsonObject(arg0).getAsJsonObject("values").get(arg1);
            if (var12 != null) {
                if (var12.isJsonPrimitive()) {
                    var19 = var12.getAsString();
                } else {
                    var19 = arg2;
                }
                return var19;
            }
        } catch (Exception ignored) {
        }
    }

    public static void putSetting(String arg0, String arg1, Object arg2) {
        JsonObject var55 = null;
        JsonObject var56 = null;
        try {
            JsonElement var10 = current.get(arg0);
            if (var10 != null) {
                if (var10.isJsonObject()) {
                    var55 = var10.getAsJsonObject();
                } else {
                    var55 = ConfigSchema.defaultModule(arg0);
                    JsonObject var41 = current;
                    JsonObject var53 = var55;
                    var41.add(arg0, ((JsonElement) var53));
                }
                JsonElement var17 = var55.get("values");
                if (var17 != null) {
                    if (var17.isJsonObject()) {
                        var56 = var17.getAsJsonObject();
                    } else {
                        JsonObject var37 = new JsonObject();
                        var56 = var37;
                        JsonObject var38 = var55;
                        JsonObject var52 = var56;
                        var38.add("values", ((JsonElement) var52));
                    }
                    if ((arg2 instanceof Boolean)) {
                        Boolean var26 = ((Boolean) arg2);
                        var56.addProperty(arg1, var26);
                    } else {
                        if ((arg2 instanceof Number)) {
                            Number var35 = ((Number) arg2);
                            var56.addProperty(arg1, var35);
                        } else {
                            JsonObject var33 = var56;
                            String var50 = arg2.toString();
                            var33.addProperty(arg1, var50);
                        }
                        Gson var28 = GSON;
                        JsonObject var44 = current;
                        cachedJson = var28.toJson(var44);
                        scheduleWrite();
                    }
                }
            }
        } catch (Exception ignored) {
        }
    }

    public static void putSettings(String arg0, Map arg1, boolean arg2) {
        if (arg1 != null) {
            if (arg1.isEmpty()) {
            }
        }
        return;
        try {
            writeValues(current, arg0, arg1);
            if (arg2) {
                Map var12 = recentLayout;
                try {
                    Map var13 = recentLayout;
                    Function var28 = (p0) -> ClarityConfig.lambda$putSettings$0(p0);
                    ((Map) var13.computeIfAbsent(arg0, var28)).putAll(arg1);
                    recentLayoutAt = System.currentTimeMillis();
                } catch (Throwable ignored) {
                }
                Map var19 = var12;
                throw var19;
            }
        } catch (Exception ignored) {
        }
        try {
            Gson var23 = GSON;
            JsonObject var27 = current;
            cachedJson = var23.toJson(var27);
            scheduleWrite();
        } catch (Exception ignored) {
        }
        Map var22 = var12;
    }

    private static void writeValues(JsonObject arg0, String arg1, Map arg2) {
        JsonObject var70 = null;
        JsonObject var71 = null;
        JsonElement var13 = arg0.get(arg1);
        if (var13 != null) {
            if (var13.isJsonObject()) {
                var70 = var13.getAsJsonObject();
            }
        } else {
            var70 = ConfigSchema.defaultModule(arg1);
            JsonObject var69 = var70;
            arg0.add(arg1, ((JsonElement) var69));
        }
        JsonElement var20 = var70.get("values");
        if (var20 != null) {
            if (var20.isJsonObject()) {
                var71 = var20.getAsJsonObject();
            } else {
                JsonObject var48 = new JsonObject();
                var71 = var48;
                JsonObject var49 = var70;
                JsonObject var68 = var71;
                var49.add("values", ((JsonElement) var68));
            }
            Iterator var28 = arg2.entrySet().iterator();
            while (true) {
                if (!var28.hasNext()) break;
                Map.Entry var33 = ((Map.Entry) var28.next());
                Object var35 = var33.getValue();
                if (var35 == null) {
                }
                if ((var35 instanceof Boolean)) {
                    Boolean var40 = ((Boolean) var35);
                    JsonObject var41 = var71;
                    String var56 = ((String) var33.getKey());
                    var41.addProperty(var56, var40);
                }
                if ((var35 instanceof Number)) {
                    Number var46 = ((Number) var35);
                    JsonObject var47 = var71;
                    String var62 = ((String) var33.getKey());
                    var47.addProperty(var62, var46);
                }
                JsonObject var44 = var71;
                String var59 = ((String) var33.getKey());
                String var66 = var35.toString();
                var44.addProperty(var59, var66);
            }
            return;
        }
    }

    private static void reapplyRecentLayout(JsonObject arg0) {
        Map var1 = recentLayout;
        try {
            if (recentLayout.isEmpty()) {
            }
        } catch (Throwable ignored) {
        }
        return;
        try {
            long var14 = System.currentTimeMillis();
            long var2 = recentLayoutAt;
            if (Long.compare((var14 - var2), 2000L) > 0) {
                recentLayout.clear();
            }
        } catch (Throwable ignored) {
        }
        return;
        try {
            Iterator var21 = recentLayout.entrySet().iterator();
            while (true) {
                if (!var21.hasNext()) break;
                Map.Entry var26 = ((Map.Entry) var21.next());
                String var33 = ((String) var26.getKey());
                Map var38 = ((Map) var26.getValue());
                writeValues(arg0, var33, var38);
            }
        } catch (Throwable ignored) {
        }
        return;
        Map var11 = var1;
        throw var11;
    }

    public static void setActiveProfile(String arg0) {
        String var9 = null;
        try {
            JsonObject var1 = current;
            if (arg0 == null) {
                var9 = "";
            } else {
                var9 = arg0;
            }
            var1.addProperty("__activeProfile", var9);
            Gson var5 = GSON;
            JsonObject var8 = current;
            cachedJson = var5.toJson(var8);
            scheduleWrite();
        } catch (Exception ignored) {
        }
    }

    public static void applyProfileSnapshot(JsonObject arg0) {
        int var7 = 0;
        JsonObject var149 = null;
        JsonNull var204 = null;
        JsonNull var225 = null;
        boolean var249 = false;
        if (arg0 == null) {
            return;
        }
        try {
            JsonObject var16 = arg0.deepCopy();
            boolean var18 = LegacyConfigMigration.migrateChinaHat(var16, false);
            ConfigSchema.normalizeSnapshot(var16);
            String[] var21 = new String[2];
            var21[0] = "__customCapes";
            var21[1] = "__customSkins";
            int var23 = var21.length;
            var7 = 0;
            while (true) {
                if (var7 >= var23) break;
                int var198 = var7;
                String var157 = var21[var198];
                JsonElement var159 = var16.get(var157);
                if (var159 != null) {
                    if (var159.isJsonArray()) {
                        JsonObject var163 = current;
                        JsonElement var224 = var159.deepCopy();
                        var163.add(var157, ((JsonElement) var224));
                    }
                    var7 = (var7 + 1);
                }
            }
            Iterator var28 = var16.entrySet().iterator();
            while (true) {
                if (!var28.hasNext()) break;
                Map.Entry var33 = ((Map.Entry) var28.next());
                String var36 = ((String) var33.getKey());
                if (var36 == null) continue;
                if (var36.isEmpty()) continue;
                if (var36.startsWith("__")) {
                }
                if ("Click GUI".equals(var36)) {
                }
                JsonElement var47 = ((JsonElement) var33.getValue());
                if (var47 == null) continue;
                if (!var47.isJsonObject()) {
                }
                JsonObject var52 = var47.getAsJsonObject();
                JsonObject var53 = new JsonObject();
                Iterator var56 = var52.entrySet().iterator();
                while (true) {
                    if (!var56.hasNext()) break;
                    Map.Entry var98 = ((Map.Entry) var56.next());
                    JsonElement var101 = ((JsonElement) var98.getValue());
                    String var182 = ((String) var98.getKey());
                    if (var101 == null) {
                        var225 = JsonNull.INSTANCE;
                    } else {
                        var225 = var101.deepCopy();
                    }
                    var53.add(var182, ((JsonElement) var225));
                }
                JsonElement var60 = current.get(var36);
                if (var60 != null) {
                    if (var60.isJsonObject()) {
                        Iterator var67 = var60.getAsJsonObject().entrySet().iterator();
                        while (true) {
                            if (!var67.hasNext()) break;
                            Map.Entry var72 = ((Map.Entry) var67.next());
                            String var170 = ((String) var72.getKey());
                            if (var53.has(var170)) continue;
                            JsonElement var77 = ((JsonElement) var72.getValue());
                            String var173 = ((String) var72.getKey());
                            if (var77 == null) {
                                var204 = JsonNull.INSTANCE;
                            } else {
                                var204 = var77.deepCopy();
                            }
                            var53.add(var173, ((JsonElement) var204));
                        }
                        JsonElement var80 = var52.get("enabled");
                        if (var80 != null) {
                            if (var80.isJsonPrimitive()) {
                                try {
                                    var249 = var80.getAsBoolean();
                                } catch (Exception ignored) {
                                }
                                var249 = isEnabled(var36);
                                ConcurrentHashMap var89 = moduleEnabled;
                                Boolean var206 = var249;
                                Object var90 = var89.put(var36, var206);
                                Boolean var208 = var249;
                                var53.addProperty("enabled", var208);
                            } else {
                                if (!var53.has("enabled")) {
                                    Boolean var212 = isEnabled(var36);
                                    var53.addProperty("enabled", var212);
                                }
                                current.add(var36, ((JsonElement) var53));
                            }
                        }
                    }
                }
            }
            LinkedHashSet var104 = new LinkedHashSet();
            Iterator var107 = current.keySet().iterator();
            while (true) {
                if (!var107.hasNext()) break;
                String var112 = ((String) var107.next());
                if (var112.startsWith("__")) continue;
                boolean var116 = var104.add(var112);
            }
            try {
                Set var187 = GuiData.get().modules.keySet();
                boolean var118 = var104.addAll(var187);
            } catch (Throwable ignored) {
            }
            try {
                Iterator var121 = ModuleRegistry.all().iterator();
                while (true) {
                    if (!var121.hasNext()) break;
                    String var197 = ((ModuleBase) var121.next()).getName();
                    boolean var154 = var104.add(var197);
                }
                Iterator var125 = var104.iterator();
                while (true) {
                    if (!var125.hasNext()) break;
                    String var133 = ((String) var125.next());
                    if ("Click GUI".equals(var133)) continue;
                    if (var16.has(var133)) {
                    }
                    JsonElement var139 = current.get(var133);
                    if (var139 != null) {
                        if (var139.isJsonObject()) {
                            var149 = var139.getAsJsonObject();
                        } else {
                            var149 = ConfigSchema.defaultModule(var133);
                        }
                        JsonObject var245 = var149;
                        Boolean var219 = false;
                        var245.addProperty("enabled", var219);
                        current.add(var133, ((JsonElement) var245));
                        ConcurrentHashMap var146 = moduleEnabled;
                        Boolean var222 = false;
                        Object var147 = var146.put(var133, var222);
                    }
                }
                JsonObject var128 = current;
                Long var217 = System.currentTimeMillis();
                var128.addProperty("__rev", var217);
                rebuildLiveState();
                Gson var129 = GSON;
                JsonObject var189 = current;
                cachedJson = var129.toJson(var189);
                scheduleWrite();
            } catch (Throwable ignored) {
            }
        } catch (Exception ignored) {
        }
    }

    public static JsonObject getBlockColors(String arg0) {
        try {
            JsonElement var5 = current.get(arg0);
            if (var5 != null) {
                if (!var5.isJsonObject()) {
                }
            }
            JsonObject var9 = new JsonObject();
        } catch (Exception ignored) {
        }
        return var9;
        try {
            JsonElement var14 = var5.getAsJsonObject().get("blockColors");
            if (var14 != null) {
                if (!var14.isJsonObject()) {
                }
            }
            JsonObject var20 = new JsonObject();
        } catch (Exception ignored) {
        }
        return var20;
        try {
            JsonObject var19 = var14.getAsJsonObject();
        } catch (Exception ignored) {
        }
        return var19;
    }

    public static Map getKeybinds() {
        HashMap var0 = new HashMap();
        Iterator var11 = current.entrySet().iterator();
        while (true) {
            if (var11.hasNext()) {
                Map.Entry var17 = ((Map.Entry) var11.next());
                String var20 = ((String) var17.getKey());
                if (var20.startsWith("__")) {
                }
            }
            return var0;
            JsonElement var25 = ((JsonElement) var17.getValue());
            if (!var25.isJsonObject()) {
            }
            JsonElement var30 = var25.getAsJsonObject().get("bind");
            if (var30 == null) continue;
            if (!var30.isJsonPrimitive()) continue;
            String var35 = var30.getAsString();
            if (var35 == null) continue;
            if (var35.isEmpty()) continue;
            if (var35.equals("null")) continue;
            Object var42 = var0.put(var20, var35);
        }
    }

    private static synchronized void scheduleWrite() {
        if (pendingWrite != null) {
            boolean var6 = pendingWrite.cancel(false);
        }
        ScheduledExecutorService var7 = SCHEDULER;
        Runnable var9 = () -> ClarityConfig.writeToDisk();
        TimeUnit var4 = TimeUnit.MILLISECONDS;
        pendingWrite = var7.schedule(var9, 300L, var4);
    }

    private static void writeToDisk() {
        try {
            Gson var0 = GSON;
            JsonObject var1 = current;
            String var5 = var0.toJson(var1);
            Path var6 = CONFIG_PATH;
            Charset var2 = StandardCharsets.UTF_8;
            OpenOption[] var10 = new OpenOption[0];
            Path var7 = Files.writeString(var6, var5, var2, var10);
        } catch (Exception ignored) {
        }
    }

    public static void flushSync() {
        if (pendingWrite != null) {
            boolean var3 = pendingWrite.cancel(false);
        }
        writeToDisk();
    }

    public static String getCustomCapeUrl(String arg0) {
        return findCustomAsset("__customCapes", arg0);
    }

    public static String getCustomSkinUrl(String arg0) {
        return findCustomAsset("__customSkins", arg0);
    }

    private static String findCustomAsset(String arg0, String arg1) {
        String var38 = null;
        try {
            JsonElement var10 = current.get(arg0);
            if (var10 != null) {
                if (!var10.isJsonArray()) {
                }
            }
        } catch (Exception ignored) {
        }
        return null;
        try {
            Iterator var16 = var10.getAsJsonArray().iterator();
            while (true) {
                if (!var16.hasNext()) break;
                JsonElement var22 = ((JsonElement) var16.next());
                if (!var22.isJsonObject()) {
                }
                JsonObject var27 = var22.getAsJsonObject();
                JsonElement var29 = var27.get("id");
                if (var29 == null) continue;
                String var41 = var29.getAsString();
                if (!arg1.equals(var41)) continue;
            }
            JsonElement var34 = var27.get("url");
            if (var34 != null) {
                var38 = var34.getAsString();
            } else {
                var38 = null;
            }
            return var38;
            return null;
        } catch (Exception ignored) {
        }
    }

    static {
        CONFIG_PATH = FabricLoader.getInstance().getGameDir().resolve("clarity_config.json");
        GsonBuilder var6 = new GsonBuilder();
        GSON = var6.setPrettyPrinting().create();
        JsonObject var9 = new JsonObject();
        current = var9;
        cachedJson = "{}";
        ConcurrentHashMap var11 = new ConcurrentHashMap();
        moduleEnabled = var11;
        SCHEDULER = Executors.newSingleThreadScheduledExecutor((p0) -> ClarityConfig.lambda$static$0(p0));
        pendingWrite = null;
        String[] var16 = new String[2];
        var16[0] = "__activeProfile";
        var16[1] = "__rev";
        GAME_OWNED = var16;
        HashMap var17 = new HashMap();
        recentLayout = var17;
        recentLayoutAt = 0L;
    }

}
