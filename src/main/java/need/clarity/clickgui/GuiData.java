package need.clarity.clickgui;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

public final class GuiData {
    public final LinkedHashMap categories;
    public final Map modules;
    public final LinkedHashMap themes;
    public final List capes;
    public final Set defaultOn;
    private static GuiData cached;

    public GuiData() {
        this.categories = new LinkedHashMap();
        this.modules = new LinkedHashMap();
        this.themes = new LinkedHashMap();
        this.capes = new ArrayList();
        this.defaultOn = new HashSet();
    }

    public static synchronized GuiData get() {
        if (cached == null) {
            cached = load(readHtml());
        }
        return cached;
    }

    public static String readHtml() {
        InputStream var0 = null;
        String var1 = null;
        try {
            var0 = GuiData.class.getResourceAsStream("/assets/clarity/Clarity.html");
            try {
                if (var0 == null) {
                } else {
                }
                var1 = new String(var0.readAllBytes(), StandardCharsets.UTF_8);
            } catch (Throwable ignored) {
            }
            if (var0 != null) {
                var0.close();
            }
            return var1;
            var1 = "";
            if (var0 != null) {
                try {
                    var0.close();
                } catch (Throwable ignored) {
                }
                var1.addSuppressed(var2);
            }
            throw var1;
        } catch (Exception ignored) {
        }
    }

    public static GuiData load(String arg0) {
        Map var7 = null;
        Iterator var8 = null;
        Map.Entry var9 = null;
        String var10 = null;
        ArrayList var11 = null;
        List var12 = null;
        Object var13 = null;
        Object var21 = null;
        GuiData var1 = new GuiData();
        Object var2 = JsLiteral.after(arg0, "const categories=");
        Object var3 = JsLiteral.after(arg0, "const moduleSettings=");
        Object var4 = JsLiteral.after(arg0, "const themes=");
        Object var5 = JsLiteral.after(arg0, "const CAPES=");
        if ((var3 instanceof Map)) {
            var7 = ((Map) var3);
        } else {
        }
        Map var6 = Map.of();
        if ((var2 instanceof Map)) {
            var7 = ((Map) var2);
            var8 = var7.entrySet().iterator();
            while (true) {
                if (!var8.hasNext()) break;
                var9 = ((Map.Entry) var8.next());
                var10 = String.valueOf(var9.getKey());
                var11 = new ArrayList();
                var13 = var9.getValue();
                if ((var13 instanceof List)) {
                    var12 = ((List) var13);
                    var13 = var12.iterator();
                    while (true) {
                        if (!var13.hasNext()) break;
                        Object var14 = var13.next();
                        if (!(var14 instanceof List)) continue;
                        List var15 = ((List) var14);
                        if (var15.isEmpty()) {
                        }
                        String var16 = String.valueOf(var15.get(0));
                        if (var15.size() > 1) {
                        } else {
                        }
                        String var17 = "";
                        GuiData.Module var18 = new GuiData.Module(var16, var17, var10);
                        var21 = var6.get(var16);
                        if ((var21 instanceof List)) {
                            var21 = ((List) var21).iterator();
                            while (true) {
                                if (!var21.hasNext()) break;
                                Object var22 = var21.next();
                                if ((var22 instanceof List)) {
                                    List var20 = ((List) var22);
                                    if (var20.size() >= 2) {
                                        var18.settings.add(new GuiData.Setting(var20));
                                    }
                                }
                            }
                            var11.add(var18);
                            var1.modules.put(var16, var18);
                        }
                    }
                    var1.categories.put(var10, var11);
                }
            }
            if ((var4 instanceof Map)) {
                var7 = ((Map) var4);
                var8 = var7.entrySet().iterator();
                while (true) {
                    if (!var8.hasNext()) break;
                    var9 = ((Map.Entry) var8.next());
                    var10 = new ArrayList();
                    var12 = var9.getValue();
                    if ((var12 instanceof List)) {
                        var11 = ((List) var12);
                        var12 = var11.iterator();
                        while (true) {
                            if (!var12.hasNext()) break;
                            var13 = var12.next();
                            var10.add(String.valueOf(var13));
                        }
                        if (var10.size() >= 5) {
                            var1.themes.put(String.valueOf(var9.getKey()), var10);
                        }
                    }
                }
                var7 = Pattern.compile("state\\[\"([^\"]+)\"\\]\\.enabled\\s*=\\s*true").matcher(arg0);
                while (true) {
                    if (!var7.find()) break;
                    var1.defaultOn.add(var7.group(1));
                }
                if ((var5 instanceof List)) {
                    var8 = ((List) var5);
                    var9 = var8.iterator();
                    while (true) {
                        if (!var9.hasNext()) break;
                        var10 = var9.next();
                        if ((var10 instanceof Map)) {
                            var11 = ((Map) var10);
                            if (var11.get("id") != null) {
                                var1.capes.add(String.valueOf(var11.get("id")));
                            }
                        }
                    }
                    return var1;
                }
            }
        }
    }

    static String str(List arg0, int arg1) {
        if (arg0.size() > arg1) {
            if (arg0.get(arg1) != null) {
            } else {
            }
            return "";
        }
    }

    static Map cast(Map arg0) {
        return arg0;
    }

    public final class Module {
        public final String name;
        public final String description;
        public final String category;
        public final List settings;

        Module(String arg0, String arg1, String arg2) {
            this.settings = new ArrayList();
            this.name = arg0;
            this.description = arg1;
            this.category = arg2;
        }

    }

    public final class Setting {
        public final String name;
        public final String type;
        public final List raw;
        public final Map meta;

        public static GuiData.Setting of(List arg0) {
            return new GuiData.Setting(arg0);
        }

        Setting(List arg0) {
            this.raw = arg0;
            this.name = GuiData.str(arg0, 0);
            this.type = GuiData.str(arg0, 1);
            if (arg0.isEmpty()) {
            } else {
            }
            Object var2 = arg0.get((arg0.size() - 1));
            if ((var2 instanceof Map)) {
                Map var3 = ((Map) var2);
                if (arg0.size() > 2) {
                } else {
                }
                GuiData.cast(var3).meta = null;
                return;
            }
        }

        public Object arg(int arg0) {
            if (this.raw.size() > arg0) {
            } else {
            }
            return null;
        }

        public double num(int arg0, double arg1) {
            Object var4 = this.arg(2);
            if ((var4 instanceof List)) {
                List var5 = ((List) var4);
                if (var5.size() > arg0) {
                    Object var7 = var5.get(arg0);
                    if ((var7 instanceof Number)) {
                        return ((Number) var7);
                    }
                    return arg1;
                }
            }
        }

        public List options() {
            Object var1 = this.arg(2);
            ArrayList var2 = new ArrayList();
            if ((var1 instanceof List)) {
                Iterator var4 = ((List) var1).iterator();
                while (true) {
                    if (!var4.hasNext()) break;
                    Object var5 = var4.next();
                    if (var5 != null) {
                        var2.add(String.valueOf(var5));
                    }
                }
                return var2;
            }
        }

        public Map groups() {
            Object var2 = null;
            Object var7 = null;
            if (this.meta != null) {
                var2 = this.meta.get("groups");
                if ((var2 instanceof Map)) {
                    Map var1 = ((Map) var2);
                } else {
                    return null;
                }
                var2 = new LinkedHashMap();
                Iterator var3 = var1.entrySet().iterator();
                while (true) {
                    if (!var3.hasNext()) break;
                    Map.Entry var4 = ((Map.Entry) var3.next());
                    ArrayList var5 = new ArrayList();
                    var7 = var4.getValue();
                    if ((var7 instanceof List)) {
                        var7 = ((List) var7).iterator();
                        while (true) {
                            if (!var7.hasNext()) break;
                            Object var8 = var7.next();
                            var5.add(String.valueOf(var8));
                        }
                        var2.put(String.valueOf(var4.getKey()), var5);
                    }
                }
                return var2;
            }
        }

        public Object defaultValue() {
            int var2 = 0;
            Boolean var3 = null;
            Object var4 = null;
            String var1 = this.type;
            var2 = -1;
            switch (var1.hashCode()) {
                case -1475040927 -> {}
                case -906021636 -> {}
                case 94627080 -> {}
                case 108280125 -> {}
                case 642087797 -> {}
                case 1704488795 -> {}
                default -> {}
            }
            if (var1.equals("range")) {
                var2 = 0;
                if (var1.equals("check")) {
                    var2 = 1;
                    if (var1.equals("select")) {
                        var2 = 2;
                        if (var1.equals("multiselect")) {
                            var2 = 3;
                            if (var1.equals("blockpicker")) {
                                var2 = 4;
                                if (var1.equals("itempicker")) {
                                    var2 = 5;
                                }
                                switch (var2) {
                                    case 0 -> {}
                                    case 1 -> {}
                                    case 2 -> {}
                                    case 3 -> {}
                                    case 4 -> {}
                                    case 5 -> {}
                                    default -> {}
                                }
                                var4 = this.arg(2);
                                if ((var4 instanceof Boolean)) {
                                    var3 = ((Boolean) var4);
                                } else {
                                    if (this.arg(3) != null) {
                                    } else {
                                        if (this.options().isEmpty()) {
                                        } else {
                                            var4 = this.arg(3);
                                            if ((var4 instanceof List)) {
                                                var3 = ((List) var4);
                                            } else {
                                                if (this.arg(2) != null) {
                                                    if ((this.arg(2) instanceof Map)) {
                                                    }
                                                }
                                                if ((this.arg(2) instanceof List)) {
                                                } else {
                                                }
                                                return String.valueOf(this.arg(2));
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

        public String placeholder() {
            Object var2 = this.arg(3);
            if ((var2 instanceof String)) {
            } else {
            }
            return "";
        }

    }

}
