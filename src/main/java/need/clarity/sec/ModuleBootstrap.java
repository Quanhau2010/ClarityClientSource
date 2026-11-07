package need.clarity.sec;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import need.clarity.modules.ModuleBase;

public final class ModuleBootstrap {
    private ModuleBootstrap() {
    }

    private static void prioritize(List arg0) {
        Comparator var3 = Comparator.comparingInt((p0) -> ModuleBootstrap.lambda$prioritize$0(p0));
        arg0.sort(var3);
    }

    private static int loadLocalModules() {
        int var169 = 0;
        int var171 = 0;
        try {
            Path var17 = Path.of(ModuleBootstrap.class.getProtectionDomain().getCodeSource().getLocation().toURI());
            ArrayList var18 = new ArrayList();
            LinkOption[] var107 = new LinkOption[0];
            if (Files.isDirectory(var17, var107)) {
                FileVisitOption[] var132 = new FileVisitOption[0];
                Stream var82 = Files.walk(var17, var132);
                try {
                    Predicate var133 = (p0) -> ModuleBootstrap.lambda$loadLocalModules$0(p0);
                    Stream var84 = var82.filter(var133);
                    Object var134 = Objects.requireNonNull(var84);
                    Iterator var86 = () -> var84.iterator().iterator();
                    while (true) {
                        if (!var86.hasNext()) break;
                        Path var91 = ((Path) var86.next());
                        String var94 = var17.relativize(var91).toString();
                        int var136 = File.separatorChar;
                        String var95 = var94.replace(var136, ((char) 47));
                        int var161 = (var95.length() - 6);
                        String var139 = var95.substring(0, var161).replace(((char) 47), ((char) 46));
                        boolean var97 = var18.add(var139);
                    }
                    if (var82 != null) {
                        var82.close();
                        if (var82 != null) {
                            try {
                                var82.close();
                            } catch (Throwable ignored) {
                            }
                            var98.addSuppressed(var101);
                        }
                        throw var98;
                    }
                } catch (Throwable ignored) {
                }
            }
        } catch (Throwable ignored) {
        }
        try {
            if (var17.getFileName().toString().endsWith(".jar")) {
                File var141 = var17.toFile();
                ZipFile var25 = new ZipFile(var141);
                try {
                    Enumeration var27 = var25.entries();
                    while (true) {
                        if (!var27.hasMoreElements()) break;
                        String var71 = ((ZipEntry) var27.nextElement()).getName();
                        if (!var71.endsWith(".class")) continue;
                        int var157 = (var71.length() - 6);
                        String var129 = var71.substring(0, var157).replace(((char) 47), ((char) 46));
                        boolean var75 = var18.add(var129);
                    }
                    var25.close();
                    try {
                        var25.close();
                    } catch (Throwable ignored) {
                    }
                    var76.addSuppressed(var78);
                    throw var76;
                } catch (Throwable ignored) {
                }
            }
        } catch (Throwable ignored) {
        }
        try {
            var169 = 0;
            var171 = 0;
            prioritize(var18);
            Iterator var35 = var18.iterator();
            while (true) {
                if (!var35.hasNext()) break;
                String var40 = ((String) var35.next());
                if (!var40.startsWith("need.clarity.modules.")) {
                }
                if (var40.equals("need.clarity.modules.ModuleBase")) continue;
                if (var40.equals("need.clarity.modules.ModuleRegistry")) {
                }
                if (var40.contains("$")) {
                }
                if (var40.contains("cosmetics.")) {
                }
                try {
                    ClassLoader var145 = ModuleBootstrap.class.getClassLoader();
                    Class var55 = Class.forName(var40, true, var145);
                    if (!ModuleBase.class.isAssignableFrom(var55)) {
                    }
                } catch (Throwable ignored) {
                }
                try {
                    if (Modifier.isAbstract(var55.getModifiers())) {
                    }
                } catch (Throwable ignored) {
                }
                try {
                    Class[] var119 = new Class[0];
                    Constructor var62 = var55.getDeclaredConstructor(var119);
                    Object[] var121 = new Object[0];
                    Object var63 = var62.newInstance(var121);
                    var169 = (var169 + 1);
                } catch (Throwable ignored) {
                }
            }
            Integer var148 = var169;
            Integer var165 = var171;
            Map var125 = Map.of("loaded", var148, "failed", var165);
            Telemetry.info("modules.local.done", var125);
            int var67 = var169;
        } catch (Throwable ignored) {
        }
        return var67;
    }

    public static void loadRemoteModules() {
        loadLocalModules();
    }

    private static void unused5() {
    }

}
