package need.clarity.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.fabricmc.loader.api.FabricLoader;

public final class ConfigFiles {
    private static final Gson GSON;
    public static final Path NO_DIALOG;

    private ConfigFiles() {
    }

    public static Path dir() {
        Path var6 = FabricLoader.getInstance().getGameDir().resolve("clarity").resolve("configs");
        try {
            FileAttribute[] var13 = new FileAttribute[0];
            Path var8 = Files.createDirectories(var6, var13);
        } catch (Exception ignored) {
        }
        return var6;
    }

    public static String fileName(String arg0) {
        String var10 = null;
        String var11 = null;
        if (arg0 == null) {
            var11 = "";
        } else {
            var11 = arg0.replaceAll("[^A-Za-z0-9_\\- ]", "_").trim();
        }
        String var4 = var11;
        if (var4.isEmpty()) {
            var10 = "profile";
        } else {
            var10 = var4;
        }
        return (var10) + ".json";
    }

    public static Path export(String arg0, JsonObject arg1) {
        JsonObject var2 = new JsonObject();
        var2.addProperty("name", arg0);
        JsonObject var21 = arg1.deepCopy();
        var2.add("data", ((JsonElement) var21));
        Path var9 = dir();
        String var17 = fileName(arg0);
        Path var10 = var9.resolve(var17);
        String var19 = GSON.toJson(var2);
        Charset var23 = StandardCharsets.UTF_8;
        OpenOption[] var24 = new OpenOption[0];
        Path var12 = Files.writeString(var10, var19, var23, var24);
        return var10;
    }

    public static List list() {
        ArrayList var0 = new ArrayList();
        try {
            Stream var6 = Files.list(dir());
            try {
                Predicate var20 = (p0) -> ConfigFiles.lambda$list$0(p0);
                Stream var8 = var6.filter(var20);
                Object var25 = Objects.requireNonNull(var0);
                Consumer var22 = (p0) -> var0.add(p0);
                var8.forEach(var22);
            } catch (Throwable ignored) {
            }
            if (var6 != null) {
                var6.close();
                if (var6 != null) {
                    try {
                        var6.close();
                    } catch (Throwable ignored) {
                    }
                    var9.addSuppressed(var12);
                }
                throw var9;
            }
        } catch (Exception ignored) {
        }
        Comparator var24 = (p0, p1) -> ConfigFiles.lambda$list$1(p0, p1);
        var0.sort(var24);
        return var0;
    }

    public static ConfigFiles.Imported read(Path arg0) {
        String var8 = null;
        String var72 = null;
        int var10 = 0;
        JsonObject var44 = null;
        try {
            Charset var2 = StandardCharsets.UTF_8;
            JsonElement var14 = JsonParser.parseString(Files.readString(arg0, var2));
        } catch (Exception ignored) {
        }
        if (!var14.isJsonObject()) {
            IllegalArgumentException var17 = new IllegalArgumentException("Not a Clarity config");
            throw var17;
        }
        JsonObject var19 = var14.getAsJsonObject();
        String var23 = arg0.getFileName().toString().replaceAll("(?i)\\.json$", "");
        if (var19.has("name")) {
            if (var19.get("name").isJsonPrimitive()) {
                var72 = var19.get("name").getAsString().trim();
            } else {
            }
        }
        var72 = var23;
        var8 = var72;
        if (var8.isEmpty()) {
            var8 = var23;
        }
        if (var19.has("binds")) {
            if (!var19.has("data")) {
                IllegalArgumentException var36 = new IllegalArgumentException("That's a bind set file, not a profile");
                throw var36;
            }
        }
        if (var19.has("data")) {
            if (var19.get("data").isJsonObject()) {
                var44 = var19.getAsJsonObject("data").deepCopy();
            } else {
                var44 = var19.deepCopy();
            }
            JsonObject var9 = var44;
            var10 = 0;
            Iterator var48 = var9.keySet().iterator();
            while (true) {
                if (!var48.hasNext()) break;
                String var53 = ((String) var48.next());
                if (var53.startsWith("__")) continue;
                if (!var9.get(var53).isJsonObject()) continue;
            }
            var10 = 1;
            if (var10 == 0) {
                IllegalArgumentException var61 = new IllegalArgumentException("No module settings in that file");
                throw var61;
            }
            boolean var63 = LegacyConfigMigration.migrateChinaHat(var9, false);
            ConfigSchema.normalizeSnapshot(var9);
            String var88 = var8;
            ConfigFiles.Imported var65 = new ConfigFiles.Imported(var88, var9);
            return var65;
        }
    }

    public static String uniqueName(Collection arg0, String arg1) {
        int var4 = 0;
        if (!arg0.contains(arg1)) {
            return arg1;
        }
        var4 = 2;
        while (true) {
            int var14 = var4;
            String var10 = (arg1) + " (" + (var14) + ")";
            if (!arg0.contains(var10)) {
                return var10;
            }
            var4 = (var4 + 1);
        }
    }

    public static void openFolder(Consumer arg0) {
        String var10 = dir().toAbsolutePath().normalize().toString();
        Runnable var15 = () -> ConfigFiles.lambda$openFolder$0(var10, arg0);
        Thread var11 = new Thread(var15, "clarity-open-folder");
        var11.setDaemon(true);
        var11.start();
    }

    public static void openUrl(String arg0) {
        Runnable var8 = () -> ConfigFiles.lambda$openUrl$0(arg0);
        Thread var1 = new Thread(var8, "clarity-open-url");
        var1.setDaemon(true);
        var1.start();
    }

    private static List openers(String arg0) {
        String var13 = System.getProperty("os.name", "");
        Locale var58 = Locale.ROOT;
        String var14 = var13.toLowerCase(var58);
        ArrayList var15 = new ArrayList();
        if (var14.contains("win")) {
            List var94 = List.of("explorer.exe", arg0);
            boolean var55 = var15.add(var94);
            List var96 = List.of("cmd", "/c", "start", "", arg0);
            boolean var57 = var15.add(var96);
        } else {
            if (var14.contains("mac")) {
                List var62 = List.of("open", arg0);
                boolean var21 = var15.add(var62);
            } else {
                String[] var64 = new String[0];
                String var26 = Paths.get(arg0, var64).toUri().toString();
                String[] var66 = new String[0];
                Path var28 = Paths.get("/.flatpak-info", var66);
                LinkOption[] var68 = new LinkOption[0];
                if (Files.exists(var28, var68)) {
                    List var70 = List.of("flatpak-spawn", "--host", "xdg-open", arg0);
                    boolean var31 = var15.add(var70);
                }
                List var72 = List.of("xdg-open", arg0);
                boolean var33 = var15.add(var72);
                List var74 = List.of("gio", "open", arg0);
                boolean var35 = var15.add(var74);
                List var76 = List.of("kde-open", arg0);
                boolean var37 = var15.add(var76);
                List var78 = List.of("kde-open5", arg0);
                boolean var39 = var15.add(var78);
                List var80 = List.of("exo-open", "--launch", "FileManager", arg0);
                boolean var41 = var15.add(var80);
                List var82 = List.of("nautilus", arg0);
                boolean var43 = var15.add(var82);
                List var84 = List.of("dolphin", arg0);
                boolean var45 = var15.add(var84);
                List var86 = List.of("thunar", arg0);
                boolean var47 = var15.add(var86);
                List var88 = List.of("nemo", arg0);
                boolean var49 = var15.add(var88);
                List var90 = List.of("pcmanfm", arg0);
                boolean var51 = var15.add(var90);
                String var119 = "array:string:" + (var26);
                List var92 = List.of("dbus-send", "--session", "--print-reply", "--dest=org.freedesktop.FileManager1", "/org/freedesktop/FileManager1", "org.freedesktop.FileManager1.ShowFolders", var119, "string:");
                boolean var53 = var15.add(var92);
            }
            return var15;
        }
    }

    private static boolean tryRun(List arg0) {
        int var24 = 0;
        try {
            ProcessBuilder var1 = new ProcessBuilder(arg0);
            ProcessBuilder.Redirect var25 = ProcessBuilder.Redirect.DISCARD;
            ProcessBuilder var6 = var1.redirectOutput(var25);
            ProcessBuilder.Redirect var26 = ProcessBuilder.Redirect.DISCARD;
            ProcessBuilder var7 = var6.redirectError(var26);
            ProcessBuilder.Redirect var27 = ProcessBuilder.Redirect.PIPE;
            Process var9 = var7.redirectInput(var27).start();
            try {
                var9.getOutputStream().close();
            } catch (Exception ignored) {
            }
            TimeUnit var3 = TimeUnit.SECONDS;
            if (!(var9.waitFor(4L, var3))) {
            }
        } catch (Exception ignored) {
        }
        return true;
        try {
            if (var9.exitValue() != 0) {
                if (((String) arg0.get(0)).equals("explorer.exe")) {
                }
            }
            var24 = 1;
            var24 = 0;
        } catch (Exception ignored) {
        }
        return var24;
    }

    public static boolean pickFile(Consumer arg0) {
        try {
            Class var5 = Class.forName("org.lwjgl.util.tinyfd.TinyFileDialogs");
        } catch (Throwable ignored) {
        }
        Runnable var13 = () -> ConfigFiles.lambda$pickFile$0(arg0);
        Thread var6 = new Thread(var13, "clarity-file-picker");
        var6.setDaemon(true);
        var6.start();
        return true;
    }

    static {
        GsonBuilder var0 = new GsonBuilder();
        GSON = var0.setPrettyPrinting().create();
        String[] var6 = new String[0];
        NO_DIALOG = Paths.get("__clarity_no_dialog__", var6);
    }

    public record Imported(String name, JsonObject snapshot) {

    }

}
