package need.clarity.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public final class LegacyConfigMigration {
    static final String CHINA_HAT = "China Hat";
    static final String HATS = "Hats";
    static final String[] CHINA_HAT_KEYS;

    private LegacyConfigMigration() {
    }

    public static boolean migrateChinaHat(JsonObject arg0, boolean arg1) {
        int var14 = 0;
        JsonElement var49 = null;
        JsonObject var81 = null;
        int var82 = 0;
        if (arg0 == null) {
            return false;
        }
        JsonElement var20 = arg0.remove("China Hat");
        if (var20 == null) {
            return false;
        }
        if (!var20.isJsonObject()) {
            return true;
        }
        JsonObject var27 = var20.getAsJsonObject();
        boolean var30 = isTrue(var27.get("enabled"));
        JsonObject var32 = childObject(arg0, "Hats");
        JsonObject var34 = childObject(var32, "values");
        JsonElement var36 = var27.get("values");
        if (var36 != null) {
            if (var36.isJsonObject()) {
                var81 = var36.getAsJsonObject();
            } else {
            }
        }
        var81 = null;
        JsonObject var11 = var81;
        String[] var41 = CHINA_HAT_KEYS;
        int var43 = var41.length;
        var14 = 0;
        while (true) {
            if (var14 >= var43) break;
            int var88 = var14;
            String var47 = var41[var88];
            if (var11 == null) {
                var49 = null;
            } else {
                var49 = var11.get(var47);
            }
            JsonElement var16 = var49;
            if (var16 != null) {
                if (!var16.isJsonNull()) {
                    var82 = 1;
                } else {
                    var82 = 0;
                }
                int var17 = var82;
                if (var30) {
                    if (var17 != 0) {
                        JsonElement var101 = var16.deepCopy();
                        var34.add(var47, ((JsonElement) var101));
                    } else {
                        JsonElement var57 = var34.remove(var47);
                    }
                } else {
                    if (var17 != 0) {
                        if (!var34.has(var47)) {
                            JsonElement var103 = var16.deepCopy();
                            var34.add(var47, ((JsonElement) var103));
                        }
                        var14 = (var14 + 1);
                    }
                }
            }
        }
        if (var30) {
            var34.addProperty("Hat", "China Hat");
            Boolean var109 = true;
            var32.addProperty("enabled", var109);
        } else {
            if (arg1) {
                if (!var32.has("enabled")) {
                    Boolean var105 = false;
                    var32.addProperty("enabled", var105);
                }
                String var70 = primitiveString(var27.get("bind"));
                if (!isBlankBind(var70)) {
                    if (isBlankBind(primitiveString(var32.get("bind")))) {
                        var32.addProperty("bind", var70);
                    }
                    return true;
                }
            }
        }
    }

    private static JsonObject childObject(JsonObject arg0, String arg1) {
        JsonElement var7 = arg0.get(arg1);
        if (var7 != null) {
            if (var7.isJsonObject()) {
                return var7.getAsJsonObject();
            }
        }
        JsonObject var13 = new JsonObject();
        arg0.add(arg1, ((JsonElement) var13));
        return var13;
    }

    private static boolean isTrue(JsonElement arg0) {
        if (arg0 != null) {
            if (!arg0.isJsonPrimitive()) {
            }
        }
        return false;
        try {
            boolean var6 = arg0.getAsBoolean();
        } catch (Exception ignored) {
        }
        return var6;
    }

    private static String primitiveString(JsonElement arg0) {
        String var5 = null;
        if (arg0 != null) {
            if (arg0.isJsonPrimitive()) {
                var5 = arg0.getAsString();
            } else {
                var5 = null;
            }
            return var5;
        }
    }

    private static boolean isBlankBind(String arg0) {
        int var7 = 0;
        if (arg0 != null) {
            if (!arg0.isEmpty()) {
                if ("null".equals(arg0)) {
                }
            }
            var7 = 1;
            var7 = 0;
            return var7;
        }
    }

    static {
        String[] var3 = new String[9];
        var3[0] = "Color Mode";
        var3[1] = "Color Count";
        var3[2] = "Color 1";
        var3[3] = "Color 2";
        var3[4] = "Color 3";
        var3[5] = "Color 4";
        var3[6] = "Center Alpha";
        var3[7] = "Edge Alpha";
        var3[8] = "Rotation Speed";
        CHINA_HAT_KEYS = var3;
    }

}
