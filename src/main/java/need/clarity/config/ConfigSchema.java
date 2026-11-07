package need.clarity.config;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import need.clarity.clickgui.GuiData;

public final class ConfigSchema {
    public static final String GUI_MODULE = "Click GUI";
    static final String[] SNAPSHOT_EXTRAS;
    static final String[] NEVER_IN_SNAPSHOT;

    private ConfigSchema() {
    }

    private static GuiData data() {
        try {
            GuiData var0 = GuiData.get();
        } catch (Throwable ignored) {
        }
        return var0;
    }

    public static boolean defaultEnabled(String arg0) {
        int var10 = 0;
        if ("Click GUI".equals(arg0)) {
            return true;
        }
        GuiData var5 = data();
        if (var5 != null) {
            if (var5.defaultOn.contains(arg0)) {
                var10 = 1;
            } else {
                var10 = 0;
            }
            return var10;
        }
    }

    public static JsonObject defaultModule(String arg0) {
        JsonObject var1 = new JsonObject();
        Boolean var14 = defaultEnabled(arg0);
        var1.addProperty("enabled", var14);
        JsonObject var15 = new JsonObject();
        var1.add("values", ((JsonElement) var15));
        JsonNull var16 = JsonNull.INSTANCE;
        var1.add("bind", ((JsonElement) var16));
        var1.addProperty("bindMode", "toggle");
        return var1;
    }

    public static void normalizeRoot(JsonObject arg0) {
        if (arg0 == null) {
            return;
        }
        normalizeModules(arg0);
        JsonElement var13 = arg0.get("__profiles");
        if (var13 != null) {
            if (!var13.isJsonObject()) {
                JsonElement var91 = arg0.remove("__profiles");
            } else {
                JsonObject var18 = var13.getAsJsonObject();
                Set var106 = var18.keySet();
                ArrayList var19 = new ArrayList(var106);
                Iterator var20 = var19.iterator();
                while (true) {
                    if (!var20.hasNext()) break;
                    String var25 = ((String) var20.next());
                    JsonElement var27 = var18.get(var25);
                    if (!var25.isBlank()) {
                        if (var27 != null) {
                            if (!var27.isJsonObject()) {
                            }
                        }
                        JsonElement var36 = var18.remove(var25);
                        normalizeSnapshot(var27.getAsJsonObject());
                    }
                }
            }
            JsonElement var38 = arg0.get("__binds");
            if (var38 != null) {
                if (!var38.isJsonArray()) {
                    JsonElement var43 = arg0.remove("__binds");
                } else {
                    JsonArray var65 = new JsonArray();
                    Iterator var68 = var38.getAsJsonArray().iterator();
                    while (true) {
                        if (!var68.hasNext()) break;
                        JsonElement var73 = ((JsonElement) var68.next());
                        if (!var73.isJsonObject()) {
                        }
                        JsonObject var77 = var73.getAsJsonObject();
                        String var80 = str(var77.get("name"));
                        if (var80 == null) continue;
                        if (var80.isBlank()) {
                        }
                        if (!(var77.get("binds") instanceof JsonObject)) {
                            JsonObject var109 = new JsonObject();
                            var77.add("binds", ((JsonElement) var109));
                        }
                        var65.add(var77);
                    }
                    arg0.add("__binds", ((JsonElement) var65));
                }
                JsonElement var45 = arg0.get("__serverProfiles");
                if (var45 != null) {
                    if (!var45.isJsonObject()) {
                        JsonElement var50 = arg0.remove("__serverProfiles");
                    } else {
                        JsonObject var52 = var45.getAsJsonObject();
                        Set var108 = var52.keySet();
                        ArrayList var53 = new ArrayList(var108);
                        Iterator var54 = var53.iterator();
                        while (true) {
                            if (!var54.hasNext()) break;
                            String var59 = ((String) var54.next());
                            if (str(var52.get(var59)) != null) continue;
                            JsonElement var64 = var52.remove(var59);
                        }
                    }
                    return;
                }
            }
        }
    }

    public static void normalizeSnapshot(JsonObject arg0) {
        int var5 = 0;
        if (arg0 == null) {
            return;
        }
        String[] var7 = NEVER_IN_SNAPSHOT;
        int var9 = var7.length;
        var5 = 0;
        while (true) {
            if (var5 >= var9) break;
            int var17 = var5;
            String var14 = var7[var17];
            JsonElement var16 = arg0.remove(var14);
            var5 = (var5 + 1);
        }
        normalizeModules(arg0);
    }

    public static void normalizeModules(JsonObject arg0) {
        Set var23 = arg0.keySet();
        ArrayList var1 = new ArrayList(var23);
        Iterator var7 = var1.iterator();
        while (true) {
            if (var7.hasNext()) {
                String var12 = ((String) var7.next());
                if (var12.startsWith("__")) {
                }
            }
            return;
            if (var12.isBlank()) {
                JsonElement var19 = arg0.remove(var12);
            }
            JsonElement var26 = arg0.get(var12);
            JsonObject var25 = normalizeModule(var12, var26);
            arg0.add(var12, ((JsonElement) var25));
        }
    }

    public static JsonObject normalizeModule(String arg0, JsonElement arg1) {
        String var66 = null;
        JsonObject var70 = null;
        JsonObject var42 = null;
        boolean var60 = false;
        if (arg1 != null) {
            if (arg1.isJsonObject()) {
                var70 = arg1.getAsJsonObject();
            }
        } else {
            JsonObject var43 = new JsonObject();
            var70 = var43;
            Boolean var45 = bool(arg1);
            if (var45 != null) {
                var70.addProperty("enabled", var45);
            }
            Boolean var17 = bool(var70.get("enabled"));
            JsonObject var18 = var70;
            if (var17 != null) {
                var60 = var17;
            } else {
                var60 = defaultEnabled(arg0);
            }
            Boolean var61 = var60;
            var18.addProperty("enabled", var61);
            JsonElement var20 = var70.get("values");
            if (var20 != null) {
                if (var20.isJsonObject()) {
                    var42 = var20.getAsJsonObject();
                } else {
                    var42 = new JsonObject();
                }
                JsonObject var8 = var42;
                coerceValues(arg0, var8);
                var70.add("values", ((JsonElement) var8));
                String var29 = str(var70.get("bind"));
                if (var29 != null) {
                    if (!var29.isBlank()) {
                        if ("null".equals(var29)) {
                        }
                    }
                    JsonObject var41 = var70;
                    JsonNull var67 = JsonNull.INSTANCE;
                    var41.add("bind", ((JsonElement) var67));
                    var70.addProperty("bind", var29);
                    String var38 = str(var70.get("bindMode"));
                    JsonObject var39 = var70;
                    if ("hold".equals(var38)) {
                        var66 = "hold";
                    } else {
                        var66 = "toggle";
                    }
                    var39.addProperty("bindMode", var66);
                    return var70;
                }
            }
        }
    }

    private static void coerceValues(String arg0, JsonObject arg1) {
        int var13 = 0;
        GuiData.Module var90 = null;
        GuiData var2 = data();
        if (var2 == null) {
            var90 = null;
        } else {
            var90 = ((GuiData.Module) var2.modules.get(arg0));
        }
        GuiData.Module var6 = var90;
        HashMap var20 = new HashMap();
        if (var6 != null) {
            Iterator var24 = var6.settings.iterator();
            while (true) {
                if (!var24.hasNext()) break;
                GuiData.Setting var87 = ((GuiData.Setting) var24.next());
                String var105 = var87.name;
                String var117 = var87.type;
                Object var89 = var20.put(var105, var117);
            }
            Set var106 = arg1.keySet();
            ArrayList var27 = new ArrayList(var106);
            Iterator var28 = var27.iterator();
            while (true) {
                if (!var28.hasNext()) break;
                String var33 = ((String) var28.next());
                JsonElement var35 = arg1.get(var33);
                if (var35 != null) {
                    if (var35.isJsonNull()) {
                    }
                }
                JsonElement var84 = arg1.remove(var33);
                String var41 = ((String) var20.get(var33));
                if (var41 == null) {
                }
                var13 = -1;
                switch (var41.hashCode()) {
                    case -906021636 -> {}
                    case 94627080 -> {}
                    case 108280125 -> {}
                    default -> {}
                }
                if (var41.equals("range")) {
                    var13 = 0;
                    if (var41.equals("check")) {
                        var13 = 1;
                        if (var41.equals("select")) {
                            var13 = 2;
                        }
                        switch (var13) {
                            case 0 -> {}
                            case 1 -> {}
                            case 2 -> {}
                            default -> {}
                        }
                        Boolean var64 = bool(var35);
                        if (var64 == null) {
                            JsonElement var68 = arg1.remove(var33);
                        }
                        arg1.addProperty(var33, var64);
                        if (!var35.isJsonPrimitive()) {
                            JsonElement var76 = arg1.remove(var33);
                        }
                        if (var35.getAsJsonPrimitive().isString()) continue;
                        String var115 = var35.getAsString();
                        arg1.addProperty(var33, var115);
                        if (!var35.isJsonPrimitive()) {
                            JsonElement var62 = arg1.remove(var33);
                        }
                        JsonPrimitive var54 = var35.getAsJsonPrimitive();
                        if (var54.isNumber()) {
                        }
                        try {
                            Double var112 = Double.parseDouble(var54.getAsString().trim());
                            arg1.addProperty(var33, var112);
                        } catch (Exception ignored) {
                        }
                    }
                }
            }
            return;
        }
    }

    public static JsonObject snapshot(JsonObject arg0, Collection arg1) {
        int var7 = 0;
        JsonObject var2 = new JsonObject();
        Iterator var12 = arg0.entrySet().iterator();
        while (true) {
            if (!var12.hasNext()) break;
            Map.Entry var17 = ((Map.Entry) var12.next());
            if (((String) var17.getKey()).startsWith("__")) continue;
            String var52 = ((String) var17.getKey());
            JsonElement var61 = ((JsonElement) var17.getValue()).deepCopy();
            var2.add(var52, ((JsonElement) var61));
        }
        if (arg1 != null) {
            Iterator var25 = arg1.iterator();
            while (true) {
                if (!var25.hasNext()) break;
                String var30 = ((String) var25.next());
                if (var2.has(var30)) continue;
                JsonObject var63 = defaultModule(var30);
                var2.add(var30, ((JsonElement) var63));
            }
        }
        String[] var34 = SNAPSHOT_EXTRAS;
        int var36 = var34.length;
        var7 = 0;
        while (true) {
            if (var7 >= var36) break;
            int var56 = var7;
            String var42 = var34[var56];
            JsonElement var44 = arg0.get(var42);
            if (var44 != null) {
                if (!var44.isJsonNull()) {
                    JsonElement var65 = var44.deepCopy();
                    var2.add(var42, ((JsonElement) var65));
                }
                var7 = (var7 + 1);
            }
        }
        normalizeSnapshot(var2);
        return var2;
    }

    static Boolean bool(JsonElement arg0) {
        int var36 = 0;
        if (arg0 != null) {
            if (!arg0.isJsonPrimitive()) {
            }
        }
        return null;
        JsonPrimitive var10 = arg0.getAsJsonPrimitive();
        if (var10.isBoolean()) {
            return var10.getAsBoolean();
        }
        if (var10.isNumber()) {
            if (var10.getAsDouble() != 0.0) {
                var36 = 1;
            }
        } else {
            String var17 = var10.getAsString().trim();
            if (var17.equalsIgnoreCase("true")) {
                return true;
            }
            if (var17.equalsIgnoreCase("false")) {
                return false;
            }
            return null;
            var36 = 0;
        }
        return var36;
    }

    static String str(JsonElement arg0) {
        String var5 = null;
        if (arg0 != null) {
            if (arg0.isJsonPrimitive()) {
                var5 = arg0.getAsString();
            }
        } else {
            var5 = null;
        }
        return var5;
    }

    static {
        String[] var3 = new String[3];
        var3[0] = "__theme";
        var3[1] = "__customCapes";
        var3[2] = "__customSkins";
        SNAPSHOT_EXTRAS = var3;
        String[] var5 = new String[6];
        var5[0] = "__profiles";
        var5[1] = "__binds";
        var5[2] = "__serverProfiles";
        var5[3] = "__activeProfile";
        var5[4] = "__rev";
        var5[5] = "__webPrefs";
        NEVER_IN_SNAPSHOT = var5;
    }

}
