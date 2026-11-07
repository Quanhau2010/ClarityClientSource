package need.clarity.server;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import need.clarity.audio.BeatAnalyzer;
import need.clarity.config.ClarityConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.ServerList;
import net.minecraft.network.Connection;

public class ClarityServer {
    private static final Object LOCK;
    private static HttpServer httpServer;
    private static ExecutorService executorService;
    private static volatile boolean enabledState;

    public ClarityServer() {
    }

    public static void init() {
        enabledState = true;
        syncServerState();
    }

    public static void syncServerState() {
        if (!enabledState) {
            return;
        }
        if (ClarityConfig.webGuiActive()) {
            start();
        } else {
            scheduleAsyncStop();
        }
    }

    public static boolean isRunning() {
        int var7 = 0;
        Object var0 = LOCK;
        try {
            if (httpServer != null) {
                var7 = 1;
            } else {
                var7 = 0;
            }
        } catch (Throwable ignored) {
        }
        return var7;
        Object var4 = var0;
        throw var4;
    }

    public static void start() {
        Object var0 = LOCK;
        try {
            if (httpServer != null) {
            }
        } catch (Throwable ignored) {
        }
        return;
        try {
            try {
                InetSocketAddress var11 = new InetSocketAddress("127.0.0.1", 7420);
                HttpServer var12 = HttpServer.create(var11, 0);
                HttpHandler var25 = (p0) -> ClarityServer.handleHttpRequest(p0);
                HttpContext var14 = var12.createContext("/", var25);
                executorService = Executors.newCachedThreadPool((p0) -> ClarityServer.createWebServerThread(p0));
                ExecutorService var24 = executorService;
                var12.setExecutor(var24);
                var12.start();
                httpServer = var12;
            } catch (Throwable ignored) {
            }
        } catch (IOException ignored) {
        }
        Object var20 = var0;
        scheduleStartRetry();
        return;
        Object var8 = var0;
        throw var8;
    }

    private static void scheduleStartRetry() {
        Runnable var1 = () -> ClarityServer.lambda$scheduleStartRetry$0();
        Thread var0 = new Thread(var1, "clarity-web-retry");
        var0.setDaemon(true);
        var0.start();
    }

    private static void scheduleAsyncStop() {
        if (!isRunning()) {
            return;
        }
        Runnable var1 = () -> ClarityServer.lambda$scheduleAsyncStop$0();
        Thread var4 = new Thread(var1, "clarity-web-stop");
        var4.setDaemon(true);
        var4.start();
    }

    public static void stop() {
        Object var0 = LOCK;
        try {
            HttpServer var5 = httpServer;
            ExecutorService var6 = executorService;
            httpServer = null;
            executorService = null;
        } catch (Throwable ignored) {
        }
        Object var10 = var0;
        throw var10;
        if (var5 != null) {
            try {
                var5.stop(0);
            } catch (Throwable ignored) {
            }
            Object var15 = var0;
        }
        if (var6 != null) {
            List var18 = var6.shutdownNow();
        }
    }

    private static void handleHttpRequest(HttpExchange arg0) {
        String var83 = null;
        String var266 = null;
        String var461 = null;
        int var483 = 0;
        Headers var12 = arg0.getResponseHeaders();
        var12.add("Access-Control-Allow-Origin", "*");
        Headers var14 = arg0.getResponseHeaders();
        String var401 = "GET, POST, OPTIONS";
        var14.add("Access-Control-Allow-Methods", var401);
        Headers var16 = arg0.getResponseHeaders();
        String var402 = "Content-Type, *";
        var16.add("Access-Control-Allow-Headers", var402);
        String var289 = arg0.getRequestMethod();
        boolean var18 = "OPTIONS".equalsIgnoreCase(var289);
        if (var18) {
            long var403 = -1L;
            arg0.sendResponseHeaders(204, var403);
            arg0.close();
            return;
        }
        URI var22 = arg0.getRequestURI();
        String var23 = var22.getPath();
        String var25 = arg0.getRequestMethod();
        String var26 = var25.toUpperCase();
        boolean var28 = var23.equals("/status");
        if (var28) {
            boolean var30 = var26.equals("GET");
            if (var30) {
                boolean var265 = ClarityConfig.webGuiActive();
                if (var265) {
                    var266 = "Web";
                } else {
                    var266 = "ClickGUI";
                }
                String var467 = var266;
                String var268 = "{\"gui\":\"" + (var467) + "\"}";
                Charset var395 = StandardCharsets.UTF_8;
                byte[] var269 = var268.getBytes(var395);
                byte[] var478 = var269;
                Headers var271 = arg0.getResponseHeaders();
                String var396 = "Content-Type";
                String var448 = "application/json; charset=utf-8";
                var271.add(var396, var448);
                Headers var273 = arg0.getResponseHeaders();
                String var397 = "Cache-Control";
                String var449 = "no-store";
                var273.add(var397, var449);
                int var398 = 200;
                byte[] var450 = var478;
                int var451 = var450.length;
                long var452 = ((long) var451);
                arg0.sendResponseHeaders(var398, var452);
                OutputStream var276 = arg0.getResponseBody();
                OutputStream var485 = var276;
                try {
                    byte[] var399 = var478;
                    var485.write(var399);
                } catch (Throwable ignored) {
                }
                if (var485 != null) {
                    var485.close();
                    Object var491 = var280;
                    if (var485 != null) {
                        try {
                            var485.close();
                        } catch (Throwable ignored) {
                        }
                        Object var495 = var284;
                        Object var400 = var495;
                        var491.addSuppressed(var400);
                    }
                    throw var491;
                }
                return;
            }
        }
        boolean var32 = var23.equals("/");
        if (!var32) {
            boolean var34 = var23.equals("/index.html");
            if (var34) {
            }
        }
        boolean var36 = var26.equals("GET");
        if (var36) {
            Headers var38 = arg0.getResponseHeaders();
            String var405 = "no-store, no-cache, must-revalidate";
            var38.add("Cache-Control", var405);
            Headers var40 = arg0.getResponseHeaders();
            String var406 = "no-cache";
            var40.add("Pragma", var406);
            boolean var41 = ClarityConfig.webGuiActive();
            if (!var41) {
                String var407 = "text/html; charset=utf-8";
                sendTextResponse(arg0, 200, var407, "<!doctype html><html><head><meta charset=\"utf-8\"><title>Clarity</title>\n<link rel=\"icon\" href=\"/icon.png\">\n<style>\n  html,body{height:100%;margin:0;background:#0b0e17;color:#ededf0;font:15px/1.6 system-ui,-apple-system,Segoe UI,sans-serif}\n  body{display:flex;align-items:center;justify-content:center}\n  .box{max-width:440px;padding:32px 36px;border:1px solid #2a3142;border-radius:16px;background:#161b26;text-align:center}\n  h1{font-size:19px;margin:0 0 10px}\n  p{color:#8b8b93;margin:0}\n  b{color:#ededf0}\n</style></head><body><div class=\"box\">\n  <h1>The web GUI is turned off</h1>\n  <p>Clarity is set to use the in-game <b>ClickGUI</b>, so only one menu edits your config at a time.<br><br>\n  To use this page, open the ClickGUI (Right Shift) &rarr; <b>GUI</b> &rarr; <b>Switch to the web GUI</b>.\n  This tab reloads by itself when you do.</p>\n</div><script>\n  setInterval(function(){fetch(\"/status\",{cache:\"no-store\"}).then(function(r){return r.json()})\n    .then(function(s){if(s&&s.gui===\"Web\")location.reload()}).catch(function(){})},1000);\n</script></body></html>\n");
                return;
            }
            String var408 = "text/html; charset=utf-8";
            serveClasspathAsset(arg0, "assets/clarity/Clarity.html", var408);
            return;
        }
        boolean var45 = var23.equals("/favicon.ico");
        if (!var45) {
            boolean var47 = var23.equals("/icon.png");
            if (var47) {
            }
        }
        boolean var49 = var26.equals("GET");
        if (var49) {
            String var409 = "image/png";
            serveClasspathAsset(arg0, "assets/clarity/icon.png", var409);
            return;
        }
        boolean var52 = var23.startsWith("/capes/");
        if (var52) {
            boolean var54 = var26.equals("GET");
            if (var54) {
                int var307 = "/capes/".length();
                String var56 = var23.substring(var307);
                boolean var58 = var56.contains("..");
                if (!var58) {
                    boolean var60 = var56.contains("/");
                    if (!var60) {
                        boolean var62 = var56.contains("\\");
                        if (var62) {
                        }
                    }
                    String var411 = "text/plain";
                    String var454 = "Bad request";
                    sendTextResponse(arg0, 400, var411, var454);
                    return;
                }
            }
        }
        boolean var66 = var23.startsWith("/skins/");
        if (var66) {
            boolean var68 = var26.equals("GET");
            if (var68) {
                String var387 = "/skins/";
                int var388 = var387.length();
                String var256 = var23.substring(var388);
                String var466 = var256;
                String var389 = "..";
                boolean var258 = var466.contains(var389);
                if (!var258) {
                    String var390 = "/";
                    boolean var260 = var466.contains(var390);
                    if (!var260) {
                        String var391 = "\\";
                        boolean var262 = var466.contains(var391);
                        if (var262) {
                        }
                    }
                    int var394 = 400;
                    String var447 = "text/plain";
                    String var459 = "Bad request";
                    sendTextResponse(arg0, var394, var447, var459);
                    return;
                }
            }
        }
        boolean var70 = var23.startsWith("/backgrounds/");
        if (var70) {
            boolean var72 = var26.equals("GET");
            if (var72) {
                int var319 = "/backgrounds/".length();
                String var74 = var23.substring(var319);
                String var460 = var74;
                boolean var76 = var460.contains("..");
                if (!var76) {
                    boolean var78 = var460.contains("/");
                    if (!var78) {
                        boolean var80 = var460.contains("\\");
                        if (var80) {
                        }
                    }
                    String var413 = "text/plain";
                    String var455 = "Bad request";
                    sendTextResponse(arg0, 400, var413, var455);
                    return;
                    boolean var82 = var460.endsWith(".png");
                    if (var82) {
                        var83 = "image/png";
                    } else {
                        var83 = "image/jpeg";
                    }
                    String var8 = var83;
                    String var325 = "assets/clarity/backgrounds/" + (var460);
                    String var412 = var8;
                    serveClasspathAsset(arg0, var325, var412);
                    return;
                }
            }
        }
        boolean var87 = var23.equals("/currentserver");
        if (var87) {
            boolean var89 = var26.equals("GET");
            if (var89) {
                var461 = "";
                try {
                    Minecraft var91 = Minecraft.getInstance();
                    ServerData var92 = var91.getCurrentServer();
                    ServerData var468 = var92;
                    if (var468 != null) {
                        String var95 = var468.ip;
                        if (var95 != null) {
                            String var97 = var468.ip;
                            boolean var98 = var97.isEmpty();
                            if (!var98) {
                                String var100 = var468.ip;
                                var461 = var100;
                            } else {
                                Minecraft var122 = Minecraft.getInstance();
                                ClientPacketListener var123 = var122.getConnection();
                                ClientPacketListener var479 = var123;
                                if (var479 != null) {
                                    Connection var126 = var479.getConnection();
                                    if (var126 != null) {
                                        Connection var128 = var479.getConnection();
                                        SocketAddress var129 = var128.getRemoteAddress();
                                        SocketAddress var492 = var129;
                                        int var131 = (var492 instanceof InetSocketAddress);
                                        if (var131 != 0) {
                                            InetSocketAddress var133 = ((InetSocketAddress) var492);
                                            InetSocketAddress var486 = var133;
                                            String var135 = var486.getHostString();
                                            int var336 = var486.getPort();
                                            String var136 = (var135) + ":" + (var336);
                                            var461 = var136;
                                        }
                                    }
                                }
                            }
                        }
                    }
                } catch (Throwable ignored) {
                }
                Object var470 = var121;
                String var101 = var461;
                String var102 = escapeJsonString(var101);
                String var103 = "{\"ip\":\"" + (var102) + "\"}";
                Charset var329 = StandardCharsets.UTF_8;
                byte[] var104 = var103.getBytes(var329);
                byte[] var469 = var104;
                Headers var106 = arg0.getResponseHeaders();
                String var414 = "application/json; charset=utf-8";
                var106.add("Content-Type", var414);
                Headers var108 = arg0.getResponseHeaders();
                String var415 = "no-store";
                var108.add("Cache-Control", var415);
                byte[] var416 = var469;
                int var417 = var416.length;
                long var418 = ((long) var417);
                arg0.sendResponseHeaders(200, var418);
                OutputStream var111 = arg0.getResponseBody();
                try {
                    var111.write(var469);
                } catch (Throwable ignored) {
                }
                if (var111 != null) {
                    var111.close();
                    if (var111 != null) {
                        try {
                            var111.close();
                        } catch (Throwable ignored) {
                        }
                        var113.addSuppressed(var117);
                    }
                    throw var113;
                }
                return;
            }
        }
        boolean var138 = var23.equals("/audiodevices");
        if (var138) {
            boolean var140 = var26.equals("GET");
            if (var140) {
                String var420 = "[";
                StringBuilder var141 = new StringBuilder(var420);
                StringBuilder var462 = var141;
                try {
                    List var142 = BeatAnalyzer.deviceNames();
                    Iterator var143 = var142.iterator();
                    Iterator var471 = var143;
                    while (true) {
                        boolean var145 = var471.hasNext();
                        if (!var145) break;
                        Object var168 = var471.next();
                        String var169 = ((String) var168);
                        String var481 = var169;
                        int var171 = var462.length();
                        int var346 = 1;
                        if (var171 > var346) {
                            int var347 = 44;
                            StringBuilder var173 = var462.append(((char) var347));
                        }
                        int var348 = 34;
                        StringBuilder var175 = var462.append(((char) var348));
                        String var349 = var481;
                        String var350 = escapeJsonString(var349);
                        StringBuilder var176 = var175.append(var350);
                        int var351 = 34;
                        StringBuilder var177 = var176.append(((char) var351));
                    }
                } catch (Throwable ignored) {
                }
            }
        }
        String var352 = "/servers";
        boolean var180 = var23.equals(var352);
        if (var180) {
            String var353 = "GET";
            boolean var182 = var26.equals(var353);
            if (var182) {
                String var438 = "[";
                StringBuilder var215 = new StringBuilder(var438);
                StringBuilder var465 = var215;
                try {
                    Minecraft var439 = Minecraft.getInstance();
                    ServerList var216 = new ServerList(var439);
                    ServerList var475 = var216;
                    var475.load();
                    var483 = 0;
                    while (true) {
                        int var219 = var483;
                        ServerList var366 = var475;
                        int var367 = var366.size();
                        if (var219 >= var367) break;
                        int var368 = var483;
                        ServerData var221 = var475.get(var368);
                        ServerData var489 = var221;
                        if (var489 == null) {
                        } else {
                            int var224 = var465.length();
                            int var369 = 1;
                            if (var224 > var369) {
                                int var370 = 44;
                                StringBuilder var226 = var465.append(((char) var370));
                            }
                            String var371 = "{\"name\":\"";
                            StringBuilder var228 = var465.append(var371);
                            ServerData var372 = var489;
                            String var373 = var372.name;
                            String var374 = escapeJsonString(var373);
                            StringBuilder var229 = var228.append(var374);
                            String var375 = "\",\"ip\":\"";
                            StringBuilder var230 = var229.append(var375);
                            ServerData var376 = var489;
                            String var377 = var376.ip;
                            String var378 = escapeJsonString(var377);
                            StringBuilder var231 = var230.append(var378);
                            String var379 = "\"}";
                            StringBuilder var232 = var231.append(var379);
                        }
                        var483 = (var483 + 1);
                    }
                } catch (Throwable ignored) {
                }
                Object var476 = var233;
                int var380 = 93;
                StringBuilder var235 = var465.append(((char) var380));
                String var237 = var465.toString();
                Charset var381 = StandardCharsets.UTF_8;
                byte[] var238 = var237.getBytes(var381);
                byte[] var477 = var238;
                Headers var240 = arg0.getResponseHeaders();
                String var382 = "Content-Type";
                String var440 = "application/json; charset=utf-8";
                var240.add(var382, var440);
                Headers var242 = arg0.getResponseHeaders();
                String var383 = "Cache-Control";
                String var441 = "no-store";
                var242.add(var383, var441);
                int var384 = 200;
                byte[] var442 = var477;
                int var443 = var442.length;
                long var444 = ((long) var443);
                arg0.sendResponseHeaders(var384, var444);
                OutputStream var245 = arg0.getResponseBody();
                OutputStream var484 = var245;
                try {
                    byte[] var385 = var477;
                    var484.write(var385);
                } catch (Throwable ignored) {
                }
                if (var484 != null) {
                    var484.close();
                    Object var490 = var247;
                    if (var484 != null) {
                        try {
                            var484.close();
                        } catch (Throwable ignored) {
                        }
                        Object var494 = var251;
                        Object var386 = var494;
                        var490.addSuppressed(var386);
                    }
                    throw var490;
                }
                return;
            }
        }
        String var354 = "/config";
        boolean var184 = var23.equals(var354);
        if (var184) {
            String var355 = "GET";
            boolean var186 = var26.equals(var355);
            if (var186) {
                String var187 = ClarityConfig.getCurrentJson();
                Charset var356 = StandardCharsets.UTF_8;
                byte[] var188 = var187.getBytes(var356);
                byte[] var463 = var188;
                Headers var190 = arg0.getResponseHeaders();
                String var357 = "Content-Type";
                String var427 = "application/json; charset=utf-8";
                var190.add(var357, var427);
                Headers var192 = arg0.getResponseHeaders();
                String var358 = "Cache-Control";
                String var428 = "no-store";
                var192.add(var358, var428);
                int var359 = 200;
                byte[] var429 = var463;
                int var430 = var429.length;
                long var431 = ((long) var430);
                arg0.sendResponseHeaders(var359, var431);
                OutputStream var195 = arg0.getResponseBody();
                OutputStream var474 = var195;
                try {
                    byte[] var360 = var463;
                    var474.write(var360);
                } catch (Throwable ignored) {
                }
                if (var474 != null) {
                    var474.close();
                }
            } else {
            }
        }
        String var362 = "/config";
        boolean var206 = var23.equals(var362);
        if (var206) {
            String var363 = "POST";
            boolean var208 = var26.equals(var363);
            if (var208) {
                HttpExchange var433 = arg0;
                InputStream var434 = var433.getRequestBody();
                byte[] var435 = var434.readAllBytes();
                Charset var456 = StandardCharsets.UTF_8;
                String var209 = new String(var435, var456);
                String var464 = var209;
                boolean var210 = ClarityConfig.webGuiActive();
                if (!var210) {
                    int var364 = 423;
                    String var436 = "text/plain";
                    String var457 = "Web GUI is locked: GUI is set to ClickGUI";
                    sendTextResponse(arg0, var364, var436, var457);
                    return;
                }
                ClarityConfig.update(var464);
                int var365 = 204;
                String var437 = "text/plain";
                String var458 = "";
                sendTextResponse(arg0, var365, var437, var458);
                return;
            }
        }
        sendNotFound(arg0);
        return;
        Object var482 = var197;
        if (var474 != null) {
            try {
                var474.close();
            } catch (Throwable ignored) {
            }
            Object var488 = var201;
            Object var361 = var488;
            var482.addSuppressed(var361);
        }
        throw var482;
        return;
        Object var473 = var178;
        StringBuilder var147 = var462.append(((char) 93));
        String var149 = var462.toString();
        Charset var340 = StandardCharsets.UTF_8;
        byte[] var150 = var149.getBytes(var340);
        byte[] var472 = var150;
        Headers var152 = arg0.getResponseHeaders();
        String var421 = "application/json; charset=utf-8";
        var152.add("Content-Type", var421);
        Headers var154 = arg0.getResponseHeaders();
        String var342 = "Cache-Control";
        String var422 = "no-store";
        var154.add(var342, var422);
        int var343 = 200;
        byte[] var423 = var472;
        int var424 = var423.length;
        long var425 = ((long) var424);
        arg0.sendResponseHeaders(var343, var425);
        OutputStream var157 = arg0.getResponseBody();
        OutputStream var480 = var157;
        try {
            byte[] var344 = var472;
            var480.write(var344);
        } catch (Throwable ignored) {
        }
        if (var480 != null) {
            var480.close();
        }
        return;
        Object var487 = var159;
        if (var480 != null) {
            try {
                var480.close();
            } catch (Throwable ignored) {
            }
            Object var493 = var162;
            Object var345 = var493;
            var487.addSuppressed(var345);
        }
        throw var487;
        String var392 = var466;
        String var393 = "assets/clarity/skins/" + (var392);
        String var446 = "image/png";
        serveClasspathAsset(arg0, var393, var446);
        return;
        String var312 = "assets/clarity/capes/" + (var56);
        String var410 = "image/png";
        serveClasspathAsset(arg0, var312, var410);
    }

    private static String escapeJsonString(String arg0) {
        if (arg0 == null) {
            return "";
        }
        return arg0.replace("\\", "\\\\").replace("\"", "\\\"");
    }

    private static void serveClasspathAsset(HttpExchange arg0, String arg1, String arg2) {
        InputStream var12 = ClarityServer.class.getClassLoader().getResourceAsStream(arg1);
        try {
            if (var12 == null) {
                sendNotFound(arg0);
            }
        } catch (Throwable ignored) {
        }
        if (var12 != null) {
            var12.close();
        }
        return;
        try {
            byte[] var15 = var12.readAllBytes();
            arg0.getResponseHeaders().add("Content-Type", arg2);
            arg0.getResponseHeaders().add("Cache-Control", "no-cache");
            long var52 = ((long) var15.length);
            arg0.sendResponseHeaders(200, var52);
            OutputStream var22 = arg0.getResponseBody();
            try {
                var22.write(var15);
            } catch (Throwable ignored) {
            }
            if (var22 != null) {
                var22.close();
                if (var22 != null) {
                    try {
                        var22.close();
                    } catch (Throwable ignored) {
                    }
                    var34.addSuppressed(var37);
                }
                throw var34;
            }
        } catch (Throwable ignored) {
        }
        if (var12 != null) {
            try {
                var12.close();
            } catch (Throwable ignored) {
            }
            var28.addSuppressed(var32);
        }
        throw var28;
        if (var12 != null) {
            var12.close();
        }
    }

    private static void sendTextResponse(HttpExchange arg0, int arg1, String arg2, String arg3) {
        long var37 = 0L;
        Charset var5 = StandardCharsets.UTF_8;
        byte[] var11 = arg3.getBytes(var5);
        arg0.getResponseHeaders().add("Content-Type", arg2);
        if (var11.length > 0) {
            var37 = ((long) var11.length);
        } else {
            var37 = -1L;
        }
        arg0.sendResponseHeaders(arg1, var37);
        if (var11.length > 0) {
            OutputStream var19 = arg0.getResponseBody();
            try {
                var19.write(var11);
            } catch (Throwable ignored) {
            }
            if (var19 != null) {
                var19.close();
                if (var19 != null) {
                    try {
                        var19.close();
                    } catch (Throwable ignored) {
                    }
                    var21.addSuppressed(var25);
                }
                throw var21;
            }
        }
        arg0.close();
    }

    private static void sendNotFound(HttpExchange arg0) {
        sendTextResponse(arg0, 404, "text/plain", "Not found");
    }

    static {
        Object var0 = new Object();
        LOCK = var0;
        enabledState = false;
    }

}
