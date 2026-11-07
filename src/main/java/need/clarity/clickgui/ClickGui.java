package need.clarity.clickgui;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.function.Consumer;

public final class ClickGui {
    public static final String GUI_MODULE = "Click GUI";
    static final String[] SPECIAL;
    static final LinkedHashMap GUI_SECTIONS;
    static final List GUI_SETTINGS;
    static final String[] BG_ON;
    private static String lastPage;
    final GuiData data;
    final GuiStore store;
    final Anim an;
    Theme th;
    float sw;
    float sh;
    float mx;
    float my;
    float wx;
    float wy;
    float ww;
    float wh;
    float winScale;
    float winCx;
    float winCy;
    float wmx;
    float wmy;
    long lastNanos;
    float time;
    float open;
    boolean closing;
    boolean closed;
    String page;
    float pageSwitchAt;
    final TextField search;
    TextField focused;
    Runnable focusCommit;
    String listenModule;
    String listenSetting;
    final Set expanded;
    final Set collapsedSections;
    final Map measured;
    final Map scroll;
    Consumer drag;
    Consumer drag2;
    ClickGui.Pop pop;
    final List hits;
    final List toasts;
    final List ripples;
    String hoverModule;
    float hoverSince;
    String confirmId;
    float confirmAt;
    final TextField nameField;
    final TextField addField;
    String addFieldOwner;
    float clipX;
    float clipY;
    float clipW;
    float clipH;
    int layer;
    static final int SNAP_NONE = 0;
    static final int SNAP_CLOSE = 1;
    static final int SNAP_THEME = 2;
    int snapRequest;
    boolean haveSnapshot;
    boolean snapPass;
    float closeStart;
    String closeStyle;
    float[][] shards;
    String pendingThemeName;
    List pendingThemeCols;
    float themeAt;
    float themeOx;
    float themeOy;
    String themeStyle;
    Theme oldTheme;
    float sideW;
    boolean pageSlide;
    String dragId;
    String focusId;
    private String tipText;
    private String tipKey;

    private static void section(String arg0, GuiData.Setting[] arg1) {
        GUI_SECTIONS.put(arg0, List.of(arg1));
        GUI_SETTINGS.addAll(List.of(arg1));
    }

    private static GuiData.Setting def(String arg0, String arg1, Object arg2, Object arg3) {
        return def(arg0, arg1, arg2, arg3, null);
    }

    private static GuiData.Setting def(String arg0, String arg1, Object arg2, Object arg3, Map arg4) {
        ArrayList var5 = new ArrayList(List.of(arg0, arg1));
        var5.add(arg2);
        if (arg3 != null) {
            var5.add(arg3);
        }
        if (arg4 != null) {
            var5.add(new LinkedHashMap(arg4));
        }
        return GuiData.Setting.of(var5);
    }

    public ClickGui(GuiData arg0, GuiStore arg1) {
        this.an = new Anim();
        this.th = Theme.from(Map.of());
        this.mx = -9999.0F;
        this.my = -9999.0F;
        this.winScale = 1.0F;
        this.lastNanos = 0L;
        this.time = 0.0F;
        this.open = 0.0F;
        this.closing = false;
        this.closed = false;
        this.pageSwitchAt = 0.0F;
        this.search = new TextField(48);
        this.expanded = new HashSet();
        this.collapsedSections = new HashSet();
        this.measured = new HashMap();
        this.scroll = new HashMap();
        this.hits = new ArrayList();
        this.toasts = new ArrayList();
        this.ripples = new ArrayList();
        this.nameField = new TextField(32);
        this.addField = new TextField(64);
        this.clipX = -1000000.0F;
        this.clipY = -1000000.0F;
        this.clipW = 2000000.0F;
        this.clipH = 2000000.0F;
        this.layer = 0;
        this.snapRequest = 0;
        this.haveSnapshot = false;
        this.snapPass = false;
        this.closeStart = -1.0F;
        this.closeStyle = "Fade";
        this.themeAt = -99.0F;
        this.themeStyle = "Ripple";
        this.sideW = 210.0F;
        this.pageSlide = true;
        this.data = arg0;
        this.store = arg1;
        String var3 = lastPage;
        if (var3 == null) {
            Object var5 = arg1.gui("Last Page");
            if ((var5 instanceof String)) {
                var3 = ((String) var5);
            }
            if (var3 != null) {
                if (!arg0.categories.containsKey(var3)) {
                    if (Arrays.asList(SPECIAL).contains(var3)) {
                    }
                }
                this.page = var3;
            } else {
                if (arg0.categories.isEmpty()) {
                } else {
                }
                "GUI".page = ((String) arg0.categories.keySet().iterator().next());
            }
            this.pageSwitchAt = -1.0F;
            return;
        }
    }

    public float openProgress() {
        if (this.closing) {
            if (this.closeStart >= 0.0F) {
                return (1.0F - Anim.ease((this.closeT() * 1.15F)));
            }
            return this.open;
        }
    }

    public boolean isClosed() {
        return this.closed;
    }

    public void close() {
        if (this.closing) {
            return;
        }
        this.closing = true;
        this.pop = null;
        this.focused = null;
        this.listenModule = null;
        this.listenSetting = null;
        this.drag = null;
        this.drag2 = null;
        this.closeStyle = this.gstr("Close Animation", "Fade");
        this.closeStart = -1.0F;
        if (!"Fade".equals(this.closeStyle)) {
            this.snapRequest = 1;
        } else {
            this.closeStart = this.time;
        }
        lastPage = this.page;
        try {
            this.store.setGui("Last Page", this.page);
        } catch (Throwable ignored) {
        }
    }

    public float scale() {
        return ((float) num(this.store.gui("Scale"), 1.0));
    }

    public String background() {
        Object var1 = this.store.gui("Background");
        if (var1 == null) {
        } else {
        }
        return String.valueOf(var1);
    }

    public boolean backgroundOn() {
        return (!"None".equals(this.background()));
    }

    public float backgroundDim() {
        return ((float) num(this.get("Click GUI", "Background Dim"), 0.42));
    }

    public boolean blurWindowOnly() {
        return "Window Only".equals(this.gstr("Blur Area", "Everywhere"));
    }

    public boolean frosted() {
        return bool(this.get("Click GUI", "Frosted Panels"), true);
    }

    public boolean crispText() {
        return bool(this.get("Click GUI", "Smooth Text"), true);
    }

    public boolean wantsScene() {
        if (!this.backgroundOn()) {
            if (this.frosted()) {
            }
        }
        return false;
    }

    public int blur() {
        return ((int) Math.round(num(this.store.gui("Blur"), 3.0)));
    }

    public Theme theme() {
        return this.th;
    }

    public int bgAccent() {
        if (this.oldTheme != null) {
            if ("Fade".equals(this.themeStyle)) {
            }
        }
        return Theme.mix(this.oldTheme.accent, this.th.accent, this.themeP());
    }

    public int bgAccent2() {
        if (this.oldTheme != null) {
            if ("Fade".equals(this.themeStyle)) {
            }
        }
        return Theme.mix(this.oldTheme.accent2, this.th.accent2, this.themeP());
    }

    public int snapshotRequest() {
        return this.snapRequest;
    }

    public void renderSnapshot(Canvas arg0, float arg1, float arg2) {
        this.sw = arg1;
        this.sh = arg2;
        this.an.frame(0.0F, this.an_speed());
        this.layoutWindow(1.0F);
        this.hits.clear();
        this.layer = 0;
        this.noClip();
        this.snapPass = true;
        arg0.save();
        this.drawWindow(arg0);
        arg0.restore();
        this.snapPass = false;
        this.snapPass = false;
        throw var4;
    }

    public void snapshotTaken(boolean arg0) {
        int var2 = this.snapRequest;
        this.snapRequest = 0;
        this.haveSnapshot = arg0;
        if (var2 == 1) {
            if (!arg0) {
                this.closeStyle = "Fade";
            }
            this.closeStart = this.time;
            this.buildShards();
        } else {
            if (var2 == 2) {
                this.oldTheme = this.th;
                if (this.pendingThemeName != null) {
                    this.store.applyTheme(this.pendingThemeName, this.pendingThemeCols);
                }
                if (this.pendingThemeName != null) {
                    this.toast((this.pendingThemeName) + " applied");
                }
                this.pendingThemeName = null;
                this.pendingThemeCols = null;
                if (arg0) {
                } else {
                }
                this.time.themeAt = -99.0F;
                if (!arg0) {
                    this.oldTheme = null;
                }
                return;
            }
        }
    }

    public boolean animating() {
        if (!this.an.busy()) {
            if (!this.closing) {
                if (this.toasts.isEmpty()) {
                    if (this.drag != null) {
                    }
                }
                return false;
            }
        }
    }

    public void mouseMove(float arg0, float arg1) {
        this.mx = arg0;
        this.my = arg1;
        this.toWindow();
        if (this.drag != null) {
            this.drag.accept(this.wmx);
        }
        if (this.drag2 != null) {
            new float[2][0] = this.wmx;
            new float[2][1] = this.wmy;
            this.drag2.accept(new float[2]);
        }
    }

    public void mouseDown(float arg0, float arg1, int arg2) {
        this.mouseMove(arg0, arg1);
        if (this.closing) {
            return;
        }
        if (this.listenModule == null) {
            if (this.listenSetting != null) {
            }
        }
        if (arg2 == 0) {
            this.listenModule = null;
            this.listenSetting = null;
            return;
        }
        switch (arg2) {
            case 1 -> {}
            case 2 -> {}
            default -> {}
        }
        "Middle Click".applyListen("Mouse " + ((arg2 + 1)));
        return;
        ClickGui.Hit var4 = this.hitAt(this.wmx, this.wmy);
        if (this.pop != null) {
            if (var4 != null) {
                if (var4.layer == 0) {
                }
            }
            this.pop = null;
            this.focused = null;
            return;
        }
        if (this.focused != null) {
            Runnable var5 = this.focusCommit;
            this.focused = null;
            this.focusCommit = null;
            if (var5 != null) {
                var5.run();
            }
            if (var4 == null) {
                return;
            }
            if (arg2 == 1) {
                if (var4.right != null) {
                    var4.right.run();
                } else {
                    if (arg2 == 0) {
                        if (var4.click != null) {
                            var4.click.run();
                        }
                        return;
                    }
                }
            }
        }
    }

    public void mouseUp(float arg0, float arg1, int arg2) {
        this.mouseMove(arg0, arg1);
        this.drag = null;
        this.drag2 = null;
    }

    public void scroll(float arg0, float arg1, float arg2) {
        this.mouseMove(arg0, arg1);
        if (this.pop != null) {
            if (!this.pop.color) {
                if (this.inPop(this.wmx, this.wmy)) {
                    this.pop.scrollT = (this.pop.scrollT - (arg2 * 26.0F));
                    return;
                }
                ClickGui.Hit var4 = this.hitAt(this.wmx, this.wmy);
                if (var4 != null) {
                    if (var4.wheel != null) {
                        var4.wheel.accept(arg2);
                        return;
                    }
                    float[] var5 = ((float[]) this.scroll.computeIfAbsent(this.pageKey(), (p0) -> ClickGui.lambda$scroll$0(p0)));
                    var5[0] = Math.max(0.0F, Math.min(var5[2], (var5[0] - (arg2 * 52.0F))));
                    return;
                }
            }
        }
    }

    public boolean key(int arg0, int arg1) {
        if (this.closing) {
            return true;
        }
        int var3 = ((arg1 & 2) != 0);
        if (this.listenModule == null) {
            if (this.listenSetting != null) {
            }
        }
        if (arg0 == 256) {
        } else {
        }
        "NONE".applyListen(KeyNames.name(arg0));
        return true;
        if (this.focused != null) {
            if (arg0 != 256) {
                if (arg0 != 257) {
                    if (arg0 == 335) {
                    }
                }
                if (this.focusCommit != null) {
                    this.focusCommit.run();
                }
                if (this.focused == this.search) {
                    if (arg0 == 256) {
                        this.search.set("");
                    }
                    this.focused = null;
                    return true;
                    if (this.focused.key(arg0, var3, this.store.clipboard())) {
                        if (this.focused == this.search) {
                            this.resetScroll();
                        }
                        if (this.focusCommit != null) {
                            if (this.focused != this.search) {
                                if (this.focused != this.nameField) {
                                    if (this.focused != this.addField) {
                                        if (this.pop != null) {
                                            if (this.focused != this.pop.filter) {
                                            }
                                        }
                                        this.focusCommit.run();
                                    }
                                    return true;
                                }
                            }
                        }
                    }
                    return true;
                }
            }
        }
        if (arg0 == 256) {
            if (this.pop != null) {
                this.pop = null;
                return true;
            }
            if (!this.search.text.isEmpty()) {
                this.search.set("");
                return true;
            }
            this.close();
            return true;
        }
        if (arg0 == 344) {
            this.close();
            return true;
        }
        if (var3 != 0) {
            if (arg0 == 70) {
                this.focus(this.search, null);
                return true;
            }
            return false;
        }
    }

    public void chars(String arg0) {
        if (this.closing) {
            return;
        }
        if (this.focused == null) {
            if (this.pop != null) {
                if (!this.pop.color) {
                    this.focus(this.pop.filter, null);
                }
                if (this.focused != null) {
                    this.focused.type(arg0);
                    if (this.focused == this.search) {
                        this.resetScroll();
                    }
                    if (this.focusCommit != null) {
                        if (this.focused != this.nameField) {
                            if (this.focused != this.addField) {
                                if (this.pop != null) {
                                    if (this.focused != this.pop.filter) {
                                    }
                                }
                                this.focusCommit.run();
                            }
                            return;
                        }
                    }
                }
            }
        }
    }

    public void render(Canvas arg0, float arg1, float arg2, long arg3) {
        float var7 = 0.0F;
        float var9 = 0.0F;
        if (this.lastNanos == 0L) {
        } else {
        }
        float var6 = (((float) (arg3 - this.lastNanos)) / 1.0E9F);
        this.lastNanos = arg3;
        this.time = (this.time + var6);
        this.sw = arg1;
        this.sh = arg2;
        this.an.frame(var6, this.an_speed());
        this.th = Theme.from(this.store.theme());
        if (this.oldTheme != null) {
            if (this.themeP() >= 1.0F) {
                this.oldTheme = null;
                this.themeAt = -99.0F;
            }
            if (this.oldTheme != null) {
                if ("Fade".equals(this.themeStyle)) {
                    this.th = Theme.lerp(this.oldTheme, this.th, Anim.ease(this.themeP()));
                }
                this.ripples.removeIf((p0) -> this.lambda$render$0(p0));
                if (this.closing) {
                    if (this.closeStart < 0.0F) {
                        this.closeStart = this.time;
                        this.closeStyle = "Fade";
                        this.haveSnapshot = false;
                    }
                    var7 = this.closeT();
                    if (var7 >= 1.0F) {
                        this.closed = true;
                        return;
                    }
                    this.hits.clear();
                    this.layer = 0;
                    this.noClip();
                    this.drawClose(arg0, var7);
                    this.drawToasts(arg0);
                    return;
                }
                this.open = this.an.to("open", 1.0F, 7.5F);
                this.layoutWindow(this.open);
                this.toWindow();
                this.hits.clear();
                this.layer = 0;
                this.noClip();
                var7 = this.gstr("Open Animation", "Fade");
                boolean var8 = "CRT".equals(var7);
                var9 = 1.0F;
                arg0.save();
                if (var8) {
                    float var10 = Anim.easeOutCubic(Math.min(1.0F, (this.open / 0.4F)));
                    var9 = Anim.easeOutCubic(Math.max(0.0F, ((this.open - 0.35F) / 0.65F)));
                    arg0.alpha(Math.min(1.0F, (this.open * 4.0F)));
                    arg0.translate(this.winCx, this.winCy);
                    arg0.scale2(Math.max(0.003F, var10), (0.01F + (0.99F * var9)));
                    arg0.translate(-(this.winCx), -(this.winCy));
                } else {
                    if ("Slide".equals(var7)) {
                    } else {
                    }
                    Math.min(1.0F, (this.open * 2.0F)).alpha(Math.min(1.0F, (this.open * 1.15F)));
                    arg0.translate(this.winCx, this.winCy);
                    arg0.scale(this.winScale);
                    arg0.translate(-(this.winCx), -(this.winCy));
                }
                this.drawWindow(arg0);
                if (var8) {
                    if (var9 < 0.999F) {
                        arg0.rect(this.wx, this.wy, this.ww, this.wh, this.winR(), Theme.a(-1, ((1.0F - var9) * 0.85F)));
                    }
                    arg0.restore();
                    this.drawThemeTransition(arg0);
                    this.drawToasts(arg0);
                    this.drawTooltip(arg0);
                    return;
                }
            }
        }
    }

    private void layoutWindow(float arg0) {
        int var5 = 0;
        float var2 = ((float) num(this.get("Click GUI", "Window Width"), 1000.0));
        float var3 = ((float) num(this.get("Click GUI", "Window Height"), 650.0));
        this.ww = Math.min(var2, (this.sw - 48.0F));
        this.wh = Math.min(var3, (this.sh - 48.0F));
        this.sideW = Math.max(150.0F, Math.min(((float) num(this.get("Click GUI", "Sidebar Width"), 210.0)), (this.ww * 0.4F)));
        this.wx = ((this.sw - this.ww) / 2.0F);
        this.wy = ((this.sh - this.wh) / 2.0F);
        this.winScale = 1.0F;
        if (!this.snapPass) {
            String var4 = this.gstr("Open Animation", "Fade");
            var5 = -1;
            switch (var4.hashCode()) {
                case 80433 -> {}
                case 2791411 -> {}
                case 79973777 -> {}
                default -> {}
            }
            if (var4.equals("Pop")) {
                var5 = 0;
                if (var4.equals("Slide")) {
                    var5 = 1;
                    if (var4.equals("Zoom")) {
                        var5 = 2;
                    }
                    switch (var5) {
                        case 0 -> {}
                        case 1 -> {}
                        case 2 -> {}
                        default -> {}
                    }
                    this.wy = (this.wy + ((1.0F - Anim.easeOutCubic(arg0)) * 26.0F));
                    this.winScale = (0.9F + (0.1F * Anim.easeOutBack(Math.min(1.0F, (arg0 * 1.02F)))));
                    this.wy = (this.wy + (((1.0F - Anim.easeOutCubic(arg0)) * this.sh) * 0.4F));
                    this.winScale = (0.55F + (0.45F * Anim.easeOutCubic(arg0)));
                }
            }
        }
        this.winCx = (this.wx + (this.ww / 2.0F));
        this.winCy = (this.wy + (this.wh / 2.0F));
    }

    private float winR() {
        return ((float) num(this.get("Click GUI", "Rounding"), 16.0));
    }

    private void drawWindow(Canvas arg0) {
        float var2 = this.winR();
        float var3 = ((float) num(this.get("Click GUI", "Panel Opacity"), 0.82));
        boolean var4 = bool(this.get("Click GUI", "Accent Glow"), true);
        float var5 = ((float) num(this.get("Click GUI", "Border Width"), 1.2));
        float var6 = ((float) num(this.get("Click GUI", "Border Opacity"), 0.55));
        float var7 = ((float) num(this.get("Click GUI", "Shadow"), 1.0));
        if (var7 > 0.01F) {
            arg0.shadow(this.wx, (this.wy + (18.0F * var7)), this.ww, this.wh, var2, (30.0F + (40.0F * var7)), Theme.a(-16777216, Math.min(0.9F, (0.6F * var7))));
        }
        if (var4) {
            float var8 = (0.18F + (0.06F * ((float) Math.sin(((double) (this.time * 1.6F))))));
            arg0.shadow(this.wx, this.wy, this.ww, this.wh, var2, 46.0F, Theme.a(this.th.accent, var8));
        }
        if (this.frosted()) {
            arg0.glass(this.wx, this.wy, this.ww, this.wh, var2, 1.0F);
        }
        arg0.rect(this.wx, this.wy, this.ww, this.wh, var2, Theme.a(this.th.surface, (var3 * 0.88F)));
        arg0.gradient(this.wx, this.wy, this.ww, (this.wh * 0.5F), var2, Theme.a(this.th.accent, 0.07F), Theme.a(this.th.accent, 0.0F), false);
        if (var5 > 0.01F) {
            if (var6 > 0.01F) {
                arg0.strokeGradient((this.wx + (var5 / 2.0F)), (this.wy + (var5 / 2.0F)), (this.ww - var5), (this.wh - var5), var2, var5, Theme.a(this.th.accent, var6), Theme.a(this.th.border, (var6 * 0.9F)));
            }
            this.drawSidebar(arg0, var2);
            this.drawContent(arg0);
            if (this.pop != null) {
                if (!this.snapPass) {
                    this.drawPop(arg0);
                }
                return;
            }
        }
    }

    private void toWindow() {
        this.wmx = (this.winCx + ((this.mx - this.winCx) / this.winScale));
        this.wmy = (this.winCy + ((this.my - this.winCy) / this.winScale));
    }

    private void drawSidebar(Canvas arg0, float arg1) {
        String var8 = null;
        int var9 = 0;
        float var13 = 0.0F;
        float var21 = 0.0F;
        float var24 = 0.0F;
        Iterator var27 = null;
        String var28 = null;
        int var29 = 0;
        float var30 = 0.0F;
        float var31 = 0.0F;
        float var3 = this.wx;
        float var4 = this.wy;
        float var5 = this.sideW;
        arg0.rect(var3, var4, var5, this.wh, arg1, Theme.a(this.th.bg, 0.35F));
        arg0.line((var3 + var5), (var4 + 18.0F), (var3 + var5), ((var4 + this.wh) - 18.0F), 1.0F, Theme.a(this.th.border, 0.6F));
        String var6 = this.gstr("Logo Tint", "Accent");
        var8 = var6;
        var9 = -1;
        switch (var8.hashCode()) {
            case -2081942916 -> {}
            case 2603341 -> {}
            default -> {}
        }
        if (var8.equals("Accent 2")) {
            var9 = 0;
            if (var8.equals("Text")) {
                var9 = 1;
            }
            switch (var9) {
                case 0 -> {}
                case 1 -> {}
                default -> {}
            }
            int var7 = this.th.accent;
            var8 = "Original".equals(var6);
            var9 = 38.0F;
            float var10 = (var4 + 47.0F);
            float var11 = ((var3 + 20.0F) - (0.25F * var9));
            float var12 = (var10 - (0.762F * var9));
            if (bool(this.get("Click GUI", "Logo Glow"), true)) {
                var13 = (0.5F + (0.5F * ((float) Math.sin(((double) (this.time * 1.8F))))));
                if (var8 != 0) {
                } else {
                }
                (var11 + (0.5F * var9)).glow((var12 + (0.5F * var9)), (22.0F + (4.0F * var13)), -1, Theme.a(var7, (0.2F + (0.1F * var13))));
            }
            if (var8 != 0) {
            } else {
            }
            if (!(var11.logo(var12, var9, -1, var7, 1.0F))) {
                arg0.text("inter-sb", 22.0F, (var3 + 20.0F), var10, 65, var7, "C");
            }
            arg0.text("inter-sb", 22.0F, ((var11 + (0.746F * var9)) + 3.5F), var10, 65, this.th.text, "larity");
            var13 = (var3 + 16.0F);
            float var14 = (var4 + 70.0F);
            float var15 = (var5 - 32.0F);
            float var17 = (this.ctlR() + 1.0F);
            int var18 = (this.focused == this.search);
            if (var18 != 0) {
            } else {
            }
            float var19 = "searchFocus".to(1.0F, 0.0F, 14.0F);
            arg0.rect(var13, var14, var15, 32.0F, var17, Theme.a(Theme.mix(this.th.bg, this.th.text, 0.04F), 0.7F));
            arg0.stroke(var13, var14, var15, 32.0F, var17, 1.0F, Theme.mix(Theme.a(this.th.border, 0.8F), this.th.accent, var19));
            if (var19 > 0.01F) {
                arg0.shadow(var13, var14, var15, 32.0F, var17, 12.0F, Theme.a(this.th.accent, (0.25F * var19)));
            }
            Icons.draw(arg0, "search", (var13 + 16.0F), (var14 + (32.0F / 2.0F)), 14.0F, Theme.mix(this.th.muted, this.th.accent, var19));
            String var20 = this.search.text;
            arg0.save();
            arg0.scissor((var13 + 28.0F), var14, (var15 - 36.0F), 32.0F);
            if (var20.isEmpty()) {
                if (var18 == 0) {
                    arg0.text("inter", 12.5F, (var13 + 30.0F), (var14 + (32.0F / 2.0F)), 17, this.th.muted, "Deobfuscation by Quanhau2010, DragonHerlys");
                } else {
                    arg0.text("inter", 12.5F, (var13 + 30.0F), (var14 + (32.0F / 2.0F)), 17, this.th.text, var20);
                    if (var18 != 0) {
                        this.caret(arg0, "inter", 12.5F, (var13 + 30.0F), (var14 + (32.0F / 2.0F)), this.search);
                    }
                    arg0.restore();
                    this.hit(var13, var14, var15, 32.0F, () -> this.lambda$drawSidebar$0(), null);
                    var21 = ((var14 + 32.0F) + 22.0F);
                    ArrayList var22 = new ArrayList();
                    arg0.text("inter-sb", 9.5F, (var3 + 22.0F), var21, 17, Theme.a(this.th.muted, 0.8F), "MODULES");
                    var21 = (var21 + 12.0F);
                    var24 = -1.0F;
                    int var25 = 0;
                    LinkedHashMap var26 = new LinkedHashMap();
                    var27 = this.data.categories.keySet().iterator();
                    while (true) {
                        if (!var27.hasNext()) break;
                        var28 = ((String) var27.next());
                        var26.put(var28, var21);
                        var21 = (var21 + (34.0F + 2.0F));
                        var22.add(var28);
                    }
                    var21 = (var21 + 14.0F);
                    arg0.text("inter-sb", 9.5F, (var3 + 22.0F), var21, 17, Theme.a(this.th.muted, 0.8F), "CLIENT");
                    var21 = (var21 + 12.0F);
                    var27 = SPECIAL;
                    var28 = var27.length;
                    var29 = 0;
                    while (true) {
                        if (var29 >= var28) break;
                        var30 = var27[var29];
                        var26.put(var30, var21);
                        var21 = (var21 + (34.0F + 2.0F));
                        var22.add(var30);
                        var29++;
                    }
                    if (this.search.text.isEmpty()) {
                    } else {
                    }
                    var27 = null;
                    if (var27 != null) {
                        if (var26.containsKey(var27)) {
                            var24 = ((Float) var26.get(var27));
                        }
                        var28 = Math.min(10.0F, (this.ctlR() + 1.0F));
                        if (var24 >= 0.0F) {
                            var29 = this.an.spring("pillY", var24, 260.0F, 22.0F);
                            var30 = this.an.spring("pillH", 34.0F, 300.0F, 20.0F);
                            var31 = this.an.to("pillA", 1.0F, 10.0F);
                            arg0.shadow((var3 + 12.0F), var29, (var5 - 24.0F), var30, var28, 18.0F, Theme.a(this.th.accent, (0.22F * var31)));
                            arg0.gradient((var3 + 12.0F), var29, (var5 - 24.0F), var30, var28, Theme.a(this.th.accent, (0.26F * var31)), Theme.a(this.th.accent2, (0.08F * var31)), true);
                            arg0.stroke((var3 + 12.0F), var29, (var5 - 24.0F), var30, var28, 1.0F, Theme.a(this.th.accent, (0.35F * var31)));
                        } else {
                            this.an.to("pillA", 0.0F, 10.0F);
                        }
                        var29 = var22.iterator();
                        while (true) {
                            if (!var29.hasNext()) break;
                            var30 = ((String) var29.next());
                            var31 = ((Float) var26.get(var30));
                            boolean var32 = var30.equals(var27);
                            float var33 = Anim.easeOutCubic((((this.time * this.an_speed()) - (0.03F * ((float) var25))) / 0.35F));
                            var25++;
                            if (!var32) {
                                if (this.in((var3 + 12.0F), var31, (var5 - 24.0F), 34.0F)) {
                                } else {
                                }
                                float var34 = "navHover:" + (var30).to(1.0F, 0.0F, 16.0F);
                                if (var34 > 0.01F) {
                                    arg0.rect((var3 + 12.0F), var31, (var5 - 24.0F), 34.0F, var28, Theme.a(this.th.text, (0.05F * var34)));
                                }
                                float var35 = ((1.0F - var33) * -14.0F);
                                arg0.save();
                                arg0.alpha(var33);
                                arg0.translate(var35, 0.0F);
                                if (var32) {
                                } else {
                                }
                                int var36 = Theme.mix(this.th.muted, this.th.text, (var34 * 0.7F));
                                if (var32) {
                                } else {
                                }
                                Icons.draw(var30, (var3 + 34.0F), (var31 + (34.0F / 2.0F)), 16.0F, this.th.accent, var36);
                                arg0.text("inter-med", 13.0F, (var3 + 52.0F), (var31 + (34.0F / 2.0F)), 17, var36, var30);
                                arg0.restore();
                                float var37 = var30;
                                this.hit((var3 + 12.0F), var31, (var5 - 24.0F), 34.0F, () -> this.lambda$drawSidebar$1(var37), null);
                            }
                        }
                        return;
                    }
                }
            }
        }
    }

    private String gstr(String arg0, String arg1) {
        return str(this.get("Click GUI", arg0), arg1);
    }

    private float gnum(String arg0, double arg1) {
        return ((float) num(this.get("Click GUI", arg0), arg1));
    }

    private boolean gbool(String arg0, boolean arg1) {
        return bool(this.get("Click GUI", arg0), arg1);
    }

    private float ctlR() {
        return this.gnum("Control Rounding", 9.0);
    }

    private float an_speed() {
        return ((float) num(this.store.gui("Animation Speed"), 1.0));
    }

    private void switchPage(String arg0) {
        if (!this.search.text.isEmpty()) {
            this.search.set("");
        }
        if (arg0.equals(this.page)) {
            return;
        }
        this.page = arg0;
        lastPage = arg0;
        this.pageSwitchAt = this.time;
        this.pop = null;
        if (this.focused != this.search) {
            this.focused = null;
        }
    }

    private void resetScroll() {
        ((float[]) this.scroll.computeIfAbsent(this.pageKey(), (p0) -> ClickGui.lambda$resetScroll$0(p0)))[0] = 0.0F;
        this.pageSwitchAt = (this.time - 1.0F);
    }

    private String pageKey() {
        if (this.search.text.isEmpty()) {
        } else {
        }
        return "\u00a7search";
    }

    private void drawContent(Canvas arg0) {
        String var10 = null;
        String var11 = null;
        ArrayList var13 = null;
        String var14 = null;
        Iterator var15 = null;
        GuiData.Module var16 = null;
        Iterator var17 = null;
        GuiData.Setting var18 = null;
        float var22 = 0.0F;
        String var24 = null;
        int var25 = 0;
        float var2 = (this.wx + this.sideW);
        float var3 = this.wy;
        float var4 = (this.ww - this.sideW);
        float var5 = this.wh;
        String var6 = this.gstr("Page Transition", "Slide");
        if ("None".equals(var6)) {
        } else {
        }
        float var7 = ((this.time - this.pageSwitchAt) * this.an_speed());
        float var8 = Anim.easeOutCubic((var7 / 0.38F));
        boolean var9 = "Slide".equals(var6);
        this.pageSlide = var9;
        int var12 = (!this.search.text.isEmpty());
        var13 = null;
        if (var12 != 0) {
            var14 = this.search.text.toLowerCase(Locale.ROOT);
            var13 = new ArrayList();
            var15 = this.data.modules.values().iterator();
            while (true) {
                if (!var15.hasNext()) break;
                var16 = ((GuiData.Module) var15.next());
                if (!var16.name.toLowerCase(Locale.ROOT).contains(var14)) {
                    if (var16.description.toLowerCase(Locale.ROOT).contains(var14)) {
                    }
                }
                var13.add(var16);
                var17 = var16.settings.iterator();
                while (true) {
                    if (!var17.hasNext()) break;
                    var18 = ((GuiData.Setting) var17.next());
                    if (var18.name.toLowerCase(Locale.ROOT).contains(var14)) {
                        var13.add(var16);
                    } else {
                    }
                }
            }
            var10 = "Deobfuscation by Quanhau2010, DragonHerlys";
            if (var13.size() == 1) {
            } else {
            }
            var11 = ("") + " result" + ("s") + " for \u201c" + (this.search.text) + "\u201d";
        } else {
            if (this.data.categories.containsKey(this.page)) {
                var13 = ((List) this.data.categories.get(this.page));
                var10 = this.page;
                var11 = null;
            } else {
                var10 = this.page;
                var14 = this.page;
                var15 = -1;
                switch (var14.hashCode()) {
                    case -1790955542 -> {}
                    case -937619446 -> {}
                    case -646164112 -> {}
                    case 64189366 -> {}
                    default -> {}
                }
                if (var14.equals("Themes")) {
                    var15 = 0;
                    if (var14.equals("Profiles")) {
                        var15 = 1;
                        if (var14.equals("Binds")) {
                            var15 = 2;
                            if (var14.equals("Servers")) {
                                var15 = 3;
                            }
                            switch (var15) {
                                case 0 -> {}
                                case 1 -> {}
                                case 2 -> {}
                                case 3 -> {}
                                default -> {}
                            }
                            var11 = "Customise this menu.";
                        }
                    }
                }
            }
            var14 = this.gbool("Page Title", true);
            if (var14 != 0) {
                arg0.save();
                arg0.alpha(var8);
                if (var9) {
                    arg0.translate(((1.0F - var8) * 18.0F), 0.0F);
                }
                if (var11 == null) {
                    arg0.text("inter-sb", 24.0F, (var2 + 28.0F), (var3 + 44.0F), 17, this.th.text, var10);
                    var15 = (var3 + 72.0F);
                } else {
                    arg0.text("inter-sb", 24.0F, (var2 + 28.0F), (var3 + 38.0F), 17, this.th.text, var10);
                    arg0.text("inter", 12.0F, (var2 + 28.0F), (var3 + 62.0F), 17, this.th.muted, var11);
                    var15 = (var3 + 84.0F);
                }
                arg0.restore();
            } else {
                var15 = (var3 + 14.0F);
            }
            var16 = (var2 + 14.0F);
            var17 = var15;
            var18 = (var4 - 28.0F);
            float var19 = ((var5 - (var15 - var3)) - 12.0F);
            float[] var20 = ((float[]) this.scroll.computeIfAbsent(this.pageKey(), (p0) -> ClickGui.lambda$drawContent$0(p0)));
            var20[1] = this.an.to("scroll:" + (this.pageKey()), var20[0], 16.0F);
            float var21 = var20[1];
            arg0.save();
            arg0.scissor(var16, var17, var18, var19);
            this.clip(var16, var17, var18, var19);
            float var23 = ((var17 + 6.0F) - var21);
            if (var13 != null) {
                var22 = (this.drawModuleList(arg0, var13, (var16 + 8.0F), var23, (var18 - 22.0F), var7, var12) - var23);
            } else {
                var24 = this.page;
                var25 = -1;
                switch (var24.hashCode()) {
                    case -1790955542 -> {}
                    case -937619446 -> {}
                    case -646164112 -> {}
                    case 64189366 -> {}
                    default -> {}
                }
                if (var24.equals("Themes")) {
                    var25 = 0;
                    if (var24.equals("Profiles")) {
                        var25 = 1;
                        if (var24.equals("Binds")) {
                            var25 = 2;
                            if (var24.equals("Servers")) {
                                var25 = 3;
                            }
                            switch (var25) {
                                case 0 -> {}
                                case 1 -> {}
                                case 2 -> {}
                                case 3 -> {}
                                default -> {}
                            }
                            var22 = (this.drawGuiPage(arg0, (var16 + 8.0F), var23, (var18 - 22.0F), var7) - var23);
                        }
                    }
                }
            }
            this.noClip();
            arg0.restore();
            var24 = Math.max(0.0F, ((var22 - var19) + 16.0F));
            var20[2] = var24;
            if (var20[0] > var24) {
                var20[0] = var24;
            }
            if (var24 > 1.0F) {
                var25 = Math.max(36.0F, ((var19 * var19) / (var19 + var24)));
                float var26 = (var17 + ((var19 - var25) * (var21 / var24)));
                if (this.in(((var16 + var18) - 10.0F), var17, 10.0F, var19)) {
                } else {
                }
                float var27 = "sbHover".to(1.0F, 0.0F, 12.0F);
                arg0.rect((((var16 + var18) - 6.0F) - (var27 * 1.5F)), var26, (3.0F + (var27 * 3.0F)), var25, 3.0F, Theme.a(this.th.text, (0.18F + (0.2F * var27))));
            }
            return;
        }
    }

    private float drawModuleList(Canvas arg0, List arg1, float arg2, float arg3, float arg4, float arg5, boolean arg6) {
        boolean var8 = this.gbool("Show Descriptions", true);
        float var9 = this.gnum("Card Spacing", 10.0);
        int var10 = 0;
        Iterator var11 = arg1.iterator();
        while (true) {
            if (!var11.hasNext()) break;
            GuiData.Module var12 = ((GuiData.Module) var11.next());
            float var13 = Anim.easeOutCubic(((arg5 - (0.022F * ((float) Math.min(var10, 18)))) / 0.34F));
            arg3 = (this.drawModuleCard(arg0, var12, arg2, arg3, arg4, var13, var8, arg6) + var9);
            var10++;
        }
        if (arg1.isEmpty()) {
            arg0.text("inter-med", 13.0F, (arg2 + (arg4 / 2.0F)), (arg3 + 60.0F), 18, this.th.muted, "Nothing matches.");
            arg3 = (arg3 + 120.0F);
        }
        return (arg3 + 10.0F);
    }

    private float drawModuleCard(Canvas arg0, GuiData.Module arg1, float arg2, float arg3, float arg4, float arg5, boolean arg6, boolean arg7) {
        float var17 = 0.0F;
        String var26 = null;
        int var27 = 0;
        float var28 = 0.0F;
        String var29 = null;
        float var30 = 0.0F;
        float var41 = 0.0F;
        float var42 = 0.0F;
        float var43 = 0.0F;
        String var9 = arg1.name;
        boolean var10 = this.store.isEnabled(var9);
        if (arg6) {
        } else {
        }
        float var11 = 46.0F;
        if (this.expanded.contains(var9)) {
        } else {
        }
        float var12 = "exp:" + (var9).to(1.0F, 0.0F, 11.0F);
        float var13 = ((Float) this.measured.getOrDefault(var9, 0.0F));
        float var14 = (var11 + (var13 * Anim.ease(var12)));
        if (var10) {
        } else {
        }
        float var15 = "en:" + (var9).to(1.0F, 0.0F, 12.0F);
        if ((arg3 + var14) > (this.clipY - 4.0F)) {
            int var16 = (arg3 < ((this.clipY + this.clipH) + 4.0F));
            if (this.pageSlide) {
            } else {
            }
            var17 = (((1.0F - arg5) * 16.0F) + 0.0F);
            if (var16 != 0) {
                if (this.in(arg2, var17, arg4, var11)) {
                    if (this.pop == null) {
                    } else {
                    }
                    float var18 = "cardHover:" + (var9).to(1.0F, 0.0F, 14.0F);
                    float var19 = this.gnum("Card Rounding", 14.0);
                    float var20 = this.gnum("Card Opacity", 0.62);
                    String var21 = this.gstr("Card Style", "Filled");
                    String var22 = this.gstr("Enabled Style", "Glow");
                    boolean var23 = this.gbool("Hover Lift", true);
                    if (var23) {
                    } else {
                    }
                    float var24 = 0.0F;
                    var17 = (var17 + var24);
                    arg0.save();
                    arg0.alpha(arg5);
                    if (var23) {
                        if (var18 > 0.01F) {
                            if (!"Flat".equals(var21)) {
                                arg0.shadow(arg2, (var17 + 3.0F), arg4, var14, var19, 16.0F, Theme.a(-16777216, (0.22F * var18)));
                            }
                            if ("Glow".equals(var22)) {
                                if (var15 > 0.01F) {
                                    arg0.shadow(arg2, var17, arg4, var14, var19, 18.0F, Theme.a(this.th.accent, (0.14F * var15)));
                                }
                                int var25 = Theme.mix(this.th.bg, this.th.text, (0.035F + (0.025F * var18)));
                                var26 = var21;
                                var27 = -1;
                                switch (var26.hashCode()) {
                                    case 2192281 -> {}
                                    case 558407714 -> {}
                                    default -> {}
                                }
                                if (var26.equals("Outline")) {
                                    var27 = 0;
                                    if (var26.equals("Flat")) {
                                        var27 = 1;
                                    }
                                    switch (var27) {
                                        case 0 -> {}
                                        case 1 -> {}
                                        default -> {}
                                    }
                                    if (var18 > 0.01F) {
                                        arg0.rect(arg2, var17, arg4, var14, var19, Theme.a(this.th.text, (0.035F * var18)));
                                        arg0.rect(arg2, var17, arg4, var14, var19, Theme.a(var25, (var20 * 0.75F)));
                                        arg0.rect(arg2, var17, arg4, var14, var19, Theme.a(var25, var20));
                                    }
                                    if (var15 > 0.01F) {
                                        if (!"Glow".equals(var22)) {
                                            if ("Tint".equals(var22)) {
                                            }
                                        }
                                        if ("Tint".equals(var22)) {
                                        } else {
                                        }
                                        arg2.gradient(var17, (arg4 * 0.7F), var14, var19, this.th.accent, Theme.a(0.22F, (0.15F * var15)), Theme.a(this.th.accent, 0.0F), true);
                                    }
                                    if (!"Flat".equals(var21)) {
                                        if (!"None".equals(var22)) {
                                            if ("Text".equals(var22)) {
                                            }
                                        }
                                        var26 = (var15 * 0.8F);
                                        if ("Outline".equals(var21)) {
                                        } else {
                                        }
                                        arg2.stroke(var17, arg4, var14, var19, 1.0F, this.th.border, Theme.mix(Theme.a(0.85F, (0.55F + (0.25F * var18))), Theme.a(this.th.accent, 0.55F), var26));
                                    }
                                    if (this.gbool("Ripple", true)) {
                                        arg0.save();
                                        arg0.scissor(arg2, var17, arg4, var14);
                                        var26 = this.ripples.iterator();
                                        while (true) {
                                            if (!var26.hasNext()) break;
                                            var27 = ((ClickGui.Ripple) var26.next());
                                            if (!var27.key.equals(var9)) {
                                            }
                                            var28 = ((this.time - var27.at) / 0.65F);
                                            if (var28 > 1.0F) {
                                            }
                                            arg0.circle(var27.x, (var27.y + (var17 - arg3)), (20.0F + ((Anim.easeOutCubic(var28) * arg4) * 0.9F)), Theme.a(this.th.accent, (0.22F * (1.0F - var28))));
                                        }
                                        arg0.restore();
                                    }
                                    var26 = (arg2 + 20.0F);
                                    if ("Text".equals(var22)) {
                                    } else {
                                    }
                                    var27 = this.th.text;
                                    if (arg6) {
                                    } else {
                                    }
                                    "inter-sb".text(14.5F, var26, var17, (22.0F + (var11 / 2.0F)), 17, var27, var9);
                                    if (arg7) {
                                        var28 = arg0.textWidth("inter-sb", 14.5F, var9);
                                        var29 = arg1.category;
                                        var30 = (arg0.textWidth("inter-med", 9.5F, var29) + 12.0F);
                                        if (arg6) {
                                        } else {
                                        }
                                        ((var26 + var28) + 8.0F).rect(var17, ((22.0F + (var11 / 2.0F)) - 8.0F), var30, 16.0F, 8.0F, Theme.a(this.th.accent, 0.14F));
                                        if (arg6) {
                                        } else {
                                        }
                                        "inter-med".text(9.5F, (((var26 + var28) + 8.0F) + (var30 / 2.0F)), var17, (22.0F + (var11 / 2.0F)), 18, this.th.accent, var29);
                                    }
                                    var28 = var17;
                                    this.hit(arg2, var17, arg4, var11, () -> this.lambda$drawModuleCard$0(var9, var28, arg3, arg1), () -> this.lambda$drawModuleCard$1(var9));
                                    var29 = (((arg2 + arg4) - 20.0F) - 42.0F);
                                    var30 = ((var17 + (var11 / 2.0F)) - 12.0F);
                                    this.drawSwitch(arg0, "msw:" + (var9), var29, var30, 42.0F, 24.0F, var10);
                                    this.hit((var29 - 4.0F), (var30 - 4.0F), 50.0F, 32.0F, () -> this.lambda$drawModuleCard$2(arg1), null);
                                    String var31 = this.store.bind(var9);
                                    if (var9.equals(this.listenModule)) {
                                        int var32 = (this.listenSetting == null);
                                        if (var32 != 0) {
                                        } else {
                                            if (var31 != null) {
                                                if (!var31.isEmpty()) {
                                                    if ("null".equals(var31)) {
                                                    }
                                                }
                                            }
                                        }
                                        String var33 = var31;
                                        boolean var34 = "hold".equals(this.store.bindMode(var9));
                                        if (var34) {
                                            if (var32 == 0) {
                                            } else {
                                            }
                                            float var35 = (34 + ((float) 0));
                                            float var36 = ((var29 - 12.0F) - var35);
                                            float var37 = ((var17 + (var11 / 2.0F)) - 12.0F);
                                            if (var32 != 0) {
                                            } else {
                                            }
                                            float var38 = 0.0F;
                                            if (this.in(var36, var37, var35, 24.0F)) {
                                            } else {
                                            }
                                            float var39 = "chipHover:" + (var9).to(1.0F, 0.0F, 16.0F);
                                            if (var32 != 0) {
                                            } else {
                                            }
                                            var36.rect(var37, var35, 24.0F, (this.ctlR() - 1.0F), this.th.bg, Theme.a(Theme.mix(this.th.accent, 0.25F, (0.05F + (0.07F * var39))), 0.8F));
                                            if (var32 != 0) {
                                            } else {
                                            }
                                            var36.stroke(var37, var35, 24.0F, (this.ctlR() - 1.0F), 1.0F, Theme.a(this.th.accent, (0.5F + (0.5F * var38))), Theme.a(this.th.border, 0.8F));
                                            if (var31 != null) {
                                                if (!var31.isEmpty()) {
                                                    if ("null".equals(var31)) {
                                                    }
                                                }
                                                int var40 = 0;
                                                if (var32 != 0) {
                                                } else {
                                                    if (var40 != 0) {
                                                    } else {
                                                    }
                                                    11.0F.text((var36 + 9.0F), (var37 + 12.0F), 17, this.th.accent, this.th.muted, this.th.text, var33);
                                                    if (var34) {
                                                        if (var32 == 0) {
                                                            var41 = ((var36 + var35) - 38.0F);
                                                            arg0.rect(var41, (var37 + 5.0F), 30.0F, 14.0F, 7.0F, Theme.a(this.th.accent, 0.2F));
                                                            arg0.text("inter-sb", 8.0F, (var41 + 15.0F), (var37 + 12.0F), 18, this.th.accent, "HOLD");
                                                        }
                                                        this.hit(var36, var37, var35, 24.0F, () -> this.lambda$drawModuleCard$3(var9), () -> this.lambda$drawModuleCard$4(var9, var34));
                                                        var41 = (var36 - 16.0F);
                                                        if (!arg1.settings.isEmpty()) {
                                                            var42 = (var36 - 22.0F);
                                                            var43 = Anim.ease(var12);
                                                            arg0.save();
                                                            arg0.translate(var42, (var17 + (var11 / 2.0F)));
                                                            if (var43 > 0.5F) {
                                                            } else {
                                                            }
                                                            Icons.draw("down", "chevron", 0.0F, 0.0F, 16.0F, Theme.mix(this.th.muted, this.th.accent, var43));
                                                            arg0.restore();
                                                            this.hit((var42 - 12.0F), ((var17 + (var11 / 2.0F)) - 12.0F), 24.0F, 24.0F, () -> this.lambda$drawModuleCard$5(var9), null);
                                                            var41 = (var42 - 18.0F);
                                                        }
                                                        if (arg6) {
                                                            var42 = this.ellipsize(arg0, "inter", 11.5F, arg1.description, (var41 - var26));
                                                            arg0.text("inter", 11.5F, var26, (var17 + 40.0F), 17, this.th.muted, var42);
                                                            if (!var42.equals(arg1.description)) {
                                                                if (this.in(var26, (var17 + 31.0F), (var41 - var26), 18.0F)) {
                                                                    this.hoverTip(var9, arg1.description);
                                                                }
                                                                if (var12 <= 0.002F) {
                                                                    if (var13 == 0.0F) {
                                                                    }
                                                                }
                                                                var42 = (var17 + var11);
                                                                arg0.save();
                                                                var43 = (var13 * Anim.ease(var12));
                                                                arg0.scissor(arg2, var42, arg4, Math.max(0.0F, var43));
                                                                float var44 = this.clipY;
                                                                float var45 = this.clipH;
                                                                float var46 = this.clipX;
                                                                float var47 = this.clipW;
                                                                this.intersectClip(arg2, var42, arg4, var43);
                                                                arg0.alpha(Anim.ease(Math.min(1.0F, (var12 * 1.4F))));
                                                                arg0.line((arg2 + 16.0F), var42, ((arg2 + arg4) - 16.0F), var42, 1.0F, Theme.a(this.th.border, 0.5F));
                                                                float var48 = this.drawSettings(arg0, var9, arg1.settings, (arg2 + 20.0F), (var42 + 8.0F), (arg4 - 40.0F));
                                                                float var49 = (var48 + 2.0F);
                                                                if (this.in((((arg2 + arg4) - 20.0F) - 76.0F), var49, 76.0F, 24.0F)) {
                                                                } else {
                                                                }
                                                                float var51 = "resetHover:" + (var9).to(1.0F, 0.0F, 14.0F);
                                                                arg0.rect((((arg2 + arg4) - 20.0F) - 76.0F), var49, 76.0F, 24.0F, (this.ctlR() - 1.0F), Theme.a(this.th.text, (0.04F + (0.05F * var51))));
                                                                Icons.draw(arg0, "reset", ((((arg2 + arg4) - 20.0F) - 76.0F) + 16.0F), (var49 + 12.0F), 12.0F, Theme.mix(this.th.muted, this.th.text, var51));
                                                                arg0.text("inter-med", 11.0F, ((((arg2 + arg4) - 20.0F) - 76.0F) + 28.0F), (var49 + 12.0F), 17, Theme.mix(this.th.muted, this.th.text, var51), "Reset");
                                                                this.hit((((arg2 + arg4) - 20.0F) - 76.0F), var49, 76.0F, 24.0F, () -> this.lambda$drawModuleCard$6(var9), null);
                                                                if ("reset:".concat(var9).equals(this.confirmId)) {
                                                                    if ((this.time - this.confirmAt) < 2.5F) {
                                                                        arg0.text("inter-med", 11.0F, (((arg2 + arg4) - 28.0F) - 76.0F), (var49 + 12.0F), 20, this.th.accent, "Click again to reset");
                                                                    }
                                                                    this.clipX = var46;
                                                                    this.clipY = var44;
                                                                    this.clipW = var47;
                                                                    this.clipH = var45;
                                                                    this.measured.put(var9, ((var48 - var42) + 36.0F));
                                                                    arg0.restore();
                                                                    arg0.restore();
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
                        }
                    }
                }
            } else {
                this.hoverSinceReset(var9);
            }
            return (arg3 + var14);
        }
    }

    private void toggleModule(GuiData.Module arg0) {
        int var2 = (!this.store.isEnabled(arg0.name));
        this.store.setEnabled(arg0.name, var2);
    }

    private void toggleExpand(String arg0) {
        if (!this.expanded.remove(arg0)) {
            this.expanded.add(arg0);
        }
    }

    float drawSettings(Canvas arg0, String arg1, List arg2, float arg3, float arg4, float arg5) {
        int var8 = 0;
        int var11 = 0;
        String var12 = null;
        String var13 = null;
        Iterator var14 = null;
        Map.Entry var15 = null;
        int var7 = 0;
        var8 = 0;
        Iterator var9 = arg2.iterator();
        while (true) {
            if (!var9.hasNext()) break;
            GuiData.Setting var10 = ((GuiData.Setting) var9.next());
            if ("section".equals(var10.type)) {
                var11 = (arg1) + "/" + (var10.name);
                var8 = this.collapsedSections.contains(var11);
                arg4 = this.sectionHeader(arg0, arg1, var10.name, arg3, arg4, arg5, var8, () -> this.lambda$drawSettings$0(var11));
            }
            if (var8 != 0) {
            }
            if (!(this.visible(arg1, var10))) {
            }
            if ("select".equals(var10.type)) {
            } else {
            }
            var11 = null;
            if (var11 != null) {
                var12 = str(this.get(arg1, var10.name), String.valueOf(var10.defaultValue()));
                var13 = null;
                var14 = var11.entrySet().iterator();
                while (true) {
                    if (!var14.hasNext()) break;
                    var15 = ((Map.Entry) var14.next());
                    if (((List) var15.getValue()).contains(var12)) {
                        var13 = ((String) var15.getKey());
                    } else {
                    }
                    if (var13 != null) break;
                    var13 = ((String) var11.keySet().iterator().next());
                    var14 = var13;
                    var7++;
                    arg4 = this.row(arg0, arg1, (var10.name) + " Type", arg3, arg4, arg5, var7, (p0, p1, p2) -> this.lambda$drawSettings$1(arg0, arg1, var10, var11, var14, p0, p1, p2));
                    var15 = ((List) var11.getOrDefault(var13, List.of()));
                    var7++;
                    arg4 = this.row(arg0, arg1, var10.name, arg3, arg4, arg5, var7, (p0, p1, p2) -> this.lambda$drawSettings$3(arg0, arg1, var10, var15, var12, p0, p1, p2));
                }
            }
            var12 = var10.type;
            var13 = -1;
            switch (var12.hashCode()) {
                case -1475040927 -> {}
                case 642087797 -> {}
                case 1704488795 -> {}
                default -> {}
            }
            if (var12.equals("multiselect")) {
                var13 = 0;
                if (var12.equals("blockpicker")) {
                    var13 = 1;
                    if (var12.equals("itempicker")) {
                        var13 = 2;
                    }
                    switch (var13) {
                        case 0 -> {}
                        case 1 -> {}
                        case 2 -> {}
                        default -> {}
                    }
                    var7++;
                    arg4 = this.drawMulti(arg0, arg1, var10, arg3, arg4, arg5, var7);
                    var7++;
                    arg4 = this.drawIdList(arg0, arg1, var10, arg3, arg4, arg5, var7);
                    var7++;
                    arg4 = this.row(arg0, arg1, var10.name, arg3, arg4, arg5, var7, (p0, p1, p2) -> this.lambda$drawSettings$5(arg0, arg1, var10, p0, p1, p2));
                }
            }
        }
        return arg4;
    }

    private float sectionHeader(Canvas arg0, String arg1, String arg2, float arg3, float arg4, float arg5, boolean arg6, Runnable arg7) {
        if (this.in((arg3 - 8.0F), arg4, (arg5 + 16.0F), 26.0F)) {
            if (this.pop == null) {
            } else {
            }
            float var10 = "secHover:" + (arg1) + "/" + (arg2).to(1.0F, 0.0F, 16.0F);
            if (var10 > 0.01F) {
                arg0.rect((arg3 - 8.0F), (arg4 + 2.0F), (arg5 + 16.0F), (26.0F - 4.0F), 8.0F, Theme.a(this.th.text, (0.035F * var10)));
            }
            arg0.save();
            arg0.translate((arg3 + 8.0F), (arg4 + (26.0F / 2.0F)));
            if (arg6) {
            } else {
            }
            Icons.draw("chevron", "down", 0.0F, 0.0F, 13.0F, Theme.mix(this.th.muted, this.th.accent, (0.6F + (0.4F * var10))));
            arg0.restore();
            arg0.text("inter-med", 11.0F, (arg3 + 22.0F), (arg4 + (26.0F / 2.0F)), 17, Theme.mix(this.th.muted, this.th.text, (0.65F + (0.35F * var10))), arg2.toUpperCase());
            arg0.line((arg3 - 8.0F), ((arg4 + 26.0F) - 1.0F), ((arg3 + arg5) + 8.0F), ((arg4 + 26.0F) - 1.0F), 1.0F, Theme.a(this.th.border, 0.5F));
            this.hit((arg3 - 8.0F), arg4, (arg5 + 16.0F), 26.0F, arg7, null);
            return (arg4 + 26.0F);
        }
    }

    private float row(Canvas arg0, String arg1, String arg2, float arg3, float arg4, float arg5, int arg6, ClickGui.Ctl arg7) {
        float var10 = Math.min(270.0F, (arg5 * 0.5F));
        if (this.in((arg3 - 8.0F), arg4, (arg5 + 16.0F), 38.0F)) {
            if (this.pop == null) {
            } else {
            }
            float var11 = "rowHover:" + (arg1) + "/" + (arg2).to(1.0F, 0.0F, 16.0F);
            if (var11 > 0.01F) {
                arg0.rect((arg3 - 8.0F), (arg4 + 2.0F), (arg5 + 16.0F), (38.0F - 4.0F), 9.0F, Theme.a(this.th.text, (0.035F * var11)));
            }
            String var12 = this.ellipsize(arg0, "inter-med", 12.5F, arg2, ((arg5 - var10) - 16.0F));
            arg0.text("inter-med", 12.5F, arg3, (arg4 + (38.0F / 2.0F)), 17, Theme.a(this.th.text, 0.88F), var12);
            float var13 = arg7.draw(((arg3 + arg5) - var10), (arg4 + (38.0F / 2.0F)), var10);
            return (arg4 + Math.max(38.0F, (var13 + 10.0F)));
        }
    }

    private float control(Canvas arg0, String arg1, GuiData.Setting arg2, float arg3, float arg4, float arg5) {
        int var9 = 0;
        boolean var10 = false;
        float var11 = 0.0F;
        String var7 = (arg1) + "/" + (arg2.name);
        String var8 = arg2.type;
        var9 = -1;
        switch (var8.hashCode()) {
            case -1074035246 -> {}
            case -1045625457 -> {}
            case -906021636 -> {}
            case -815039716 -> {}
            case -706721607 -> {}
            case 3556653 -> {}
            case 94627080 -> {}
            case 94842723 -> {}
            case 108280125 -> {}
            default -> {}
        }
        if (var8.equals("check")) {
            var9 = 0;
            if (var8.equals("range")) {
                var9 = 1;
                if (var8.equals("minmax")) {
                    var9 = 2;
                    if (var8.equals("select")) {
                        var9 = 3;
                        if (var8.equals("skinselect")) {
                            var9 = 4;
                            if (var8.equals("capeselect")) {
                                var9 = 5;
                                if (var8.equals("color")) {
                                    var9 = 6;
                                    if (var8.equals("text")) {
                                        var9 = 7;
                                        if (var8.equals("keybind")) {
                                            var9 = 8;
                                        }
                                        switch (var9) {
                                            case 0 -> {}
                                            case 1 -> {}
                                            case 2 -> {}
                                            case 3 -> {}
                                            case 4 -> {}
                                            case 5 -> {}
                                            case 6 -> {}
                                            case 7 -> {}
                                            case 8 -> {}
                                            default -> {}
                                        }
                                        var10 = bool(this.get(arg1, arg2.name), ((Boolean) arg2.defaultValue()));
                                        var11 = ((arg3 + arg5) - 38.0F);
                                        this.drawSwitch(arg0, "sw:" + (var7), var11, (arg4 - 11.0F), 38.0F, 22.0F, var10);
                                        this.hit((var11 - 4.0F), (arg4 - 15.0F), 46.0F, 30.0F, () -> this.lambda$control$0(arg1, arg2, var10), null);
                                        return 22.0F;
                                        this.slider(arg0, var7, arg1, arg2, arg3, arg4, arg5);
                                        return 22.0F;
                                        this.minmax(arg0, var7, arg1, arg2, arg3, arg4, arg5);
                                        return 22.0F;
                                        var10 = str(this.get(arg1, arg2.name), String.valueOf(arg2.defaultValue()));
                                        this.dropdown(arg0, var7, arg3, arg4, arg5, arg2.options(), var10, (p0) -> this.lambda$control$1(arg1, arg2, p0));
                                        return 28.0F;
                                        var10 = new ArrayList(List.of("none"));
                                        if ("skinselect".equals(arg2.type)) {
                                            var10.add("default");
                                            var10.addAll(this.store.customSkins());
                                        } else {
                                            var10.addAll(this.data.capes);
                                            var10.addAll(this.store.customCapes());
                                        }
                                        var11 = str(this.get(arg1, arg2.name), String.valueOf(arg2.defaultValue()));
                                        this.dropdown(arg0, var7, arg3, arg4, arg5, var10, var11, (p0) -> this.lambda$control$2(arg1, arg2, p0));
                                        return 28.0F;
                                        this.colorSwatch(arg0, var7, arg1, arg2, arg3, arg4, arg5);
                                        return 24.0F;
                                        this.textInput(arg0, var7, arg3, arg4, arg5, str(this.get(arg1, arg2.name), String.valueOf(arg2.defaultValue())), arg2.placeholder(), (p0) -> this.lambda$control$3(arg1, arg2, p0));
                                        return 28.0F;
                                        var10 = str(this.get(arg1, arg2.name), String.valueOf(arg2.defaultValue()));
                                        if (arg1.equals(this.listenModule)) {
                                            var11 = (arg2.name.equals(this.listenSetting));
                                            if (var11 != 0) {
                                            } else {
                                                if (!"NONE".equals(var10)) {
                                                    if (var10.isEmpty()) {
                                                    }
                                                }
                                            }
                                            boolean var12 = var10;
                                            float var13 = Math.max(90.0F, (arg0.textWidth("inter-med", 11.5F, var12) + 24.0F));
                                            float var14 = ((arg3 + arg5) - var13);
                                            if (var11 != 0) {
                                            } else {
                                            }
                                            float var15 = 0.0F;
                                            if (var11 != 0) {
                                            } else {
                                            }
                                            var14.rect((arg4 - 13.0F), var13, 26.0F, (this.ctlR() - 1.0F), this.th.bg, Theme.a(Theme.mix(this.th.accent, 0.25F, 0.06F), 0.85F));
                                            if (var11 != 0) {
                                            } else {
                                            }
                                            var14.stroke((arg4 - 13.0F), var13, 26.0F, (this.ctlR() - 1.0F), 1.0F, Theme.a(this.th.accent, (0.5F + (0.5F * var15))), Theme.a(this.th.border, 0.9F));
                                            if (var11 != 0) {
                                            } else {
                                            }
                                            "inter-med".text(11.5F, (var14 + (var13 / 2.0F)), arg4, 18, this.th.accent, this.th.text, var12);
                                            this.hit(var14, (arg4 - 13.0F), var13, 26.0F, () -> this.lambda$control$4(arg1, arg2), () -> this.lambda$control$5(arg1, arg2));
                                            return 26.0F;
                                            arg0.text("inter", 11.0F, (arg3 + arg5), arg4, 20, this.th.muted, "Edit in the web GUI");
                                            return 20.0F;
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

    private void drawSwitch(Canvas arg0, String arg1, float arg2, float arg3, float arg4, float arg5, boolean arg6) {
        float var16 = 0.0F;
        float var17 = 0.0F;
        int var14 = 0;
        float var15 = 0.0F;
        if (arg6) {
        } else {
        }
        float var8 = arg1.spring(1.0F, 0.0F, 340.0F, 24.0F);
        float var9 = Math.max(0.0F, Math.min(1.0F, var8));
        if (this.in(arg2, arg3, arg4, arg5)) {
        } else {
        }
        float var10 = 0.0F;
        String var11 = this.gstr("Switch Style", "Pill");
        int var12 = Theme.a(Theme.mix(this.th.bg, this.th.text, 0.12F), 0.9F);
        var14 = -1;
        switch (var11.hashCode()) {
            case -1810807491 -> {}
            case -1565776511 -> {}
            default -> {}
        }
        if (var11.equals("Square")) {
            var14 = 0;
            if (var11.equals("Minimal")) {
                var14 = 1;
            }
            switch (var14) {
                case 0 -> {}
                case 1 -> {}
                default -> {}
            }
            var15 = 4.0F;
            if (var9 > 0.01F) {
                arg0.shadow(arg2, arg3, arg4, arg5, var15, 8.0F, Theme.a(this.th.accent, (0.3F * var9)));
            }
            arg0.rect(arg2, arg3, arg4, arg5, var15, Theme.mix(var12, this.th.accent, var9));
            var16 = (arg5 - 6.0F);
            var17 = ((arg2 + 3.0F) + (((arg4 - 6.0F) - var16) * var8));
            arg0.shadow(var17, (arg3 + 3.0F), var16, var16, 3.0F, 5.0F, Theme.a(-16777216, 0.3F));
            arg0.rect(var17, (arg3 + 3.0F), var16, var16, 3.0F, -1);
            var15 = Math.max(4.0F, (arg5 * 0.38F));
            arg0.rect((arg2 + 2.0F), (arg3 + ((arg5 - var15) / 2.0F)), (arg4 - 4.0F), var15, (var15 / 2.0F), Theme.mix(var12, Theme.a(this.th.accent, 0.55F), var9));
            var16 = ((arg5 / 2.0F) - 1.0F);
            var17 = ((arg2 + var16) + ((arg4 - (2.0F * var16)) * var8));
            arg0.shadow((var17 - var16), (arg3 + 1.0F), (2.0F * var16), (2.0F * var16), var16, 6.0F, Theme.a(-16777216, 0.3F));
            arg0.circle(var17, (arg3 + (arg5 / 2.0F)), var16, Theme.mix(-1, this.th.accent, var9));
            arg0.circle(var17, (arg3 + (arg5 / 2.0F)), ((var16 * 0.42F) * (1.0F + (0.15F * var10))), Theme.mix(Theme.a(this.th.bg, 0.6F), -1, var9));
            var15 = Theme.mix(var12, this.th.accent, var9);
            if (var9 > 0.01F) {
                arg0.shadow(arg2, arg3, arg4, arg5, (arg5 / 2.0F), 10.0F, Theme.a(this.th.accent, (0.35F * var9)));
            }
            arg0.rect(arg2, arg3, arg4, arg5, (arg5 / 2.0F), var15);
            if (var9 > 0.01F) {
                arg0.gradient(arg2, arg3, arg4, arg5, (arg5 / 2.0F), Theme.a(this.th.accent2, 0.0F), Theme.a(this.th.accent2, (0.55F * var9)), true);
            }
            var16 = ((arg5 / 2.0F) - 3.0F);
            var17 = (((arg2 + 3.0F) + var16) + (((arg4 - 6.0F) - (2.0F * var16)) * var8));
            float var18 = (1.0F + (0.18F * var10));
            arg0.shadow((var17 - var16), (arg3 + 3.0F), (2.0F * var16), (2.0F * var16), var16, 6.0F, Theme.a(-16777216, 0.35F));
            arg0.circle(var17, (arg3 + (arg5 / 2.0F)), (var16 * (0.96F + (0.04F * var18))), -1);
            if (var9 > 0.5F) {
                Icons.draw(arg0, "check", var17, (arg3 + (arg5 / 2.0F)), (var16 * 1.1F), Theme.a(this.th.accent, ((var9 - 0.5F) * 2.0F)));
            }
            return;
        }
    }

    private void slider(Canvas arg0, String arg1, String arg2, GuiData.Setting arg3, float arg4, float arg5, float arg6) {
        String var28 = null;
        int var29 = 0;
        float var30 = 0.0F;
        float var31 = 0.0F;
        float var32 = 0.0F;
        int var34 = 0;
        float var35 = 0.0F;
        int var36 = 0;
        double var8 = arg3.num(0, 0.0);
        double var10 = arg3.num(1, 1.0);
        double var12 = arg3.num(2, var8);
        double var14 = arg3.num(3, 0.0);
        double var16 = num(this.get(arg2, arg3.name), var12);
        String var18 = this.gstr("Slider Style", "Pill");
        if ("Classic".equals(var18)) {
        } else {
        }
        float var19 = 46.0F;
        float var21 = ((arg6 - var19) - 12.0F);
        float var22 = ((float) ((var16 - var8) / Math.max(1.0E-9, (var10 - var8))));
        float var23 = this.an.to("sl:" + (arg1), var22, 22.0F);
        if (this.drag != null) {
            int var24 = (arg1.equals(this.dragId));
            if (!(this.in((arg4 - 6.0F), (arg5 - 12.0F), (var21 + 12.0F), 24.0F))) {
                if (var24 != 0) {
                }
            }
            float var25 = "slh:" + (arg1).to(1.0F, 0.0F, 16.0F);
            String var26 = fmt(var16, var14);
            if (var14 > 0.0) {
            } else {
            }
            int var27 = 0;
            var28 = var18;
            var29 = -1;
            switch (var28.hashCode()) {
                case -1776693134 -> {}
                case -1565776511 -> {}
                default -> {}
            }
            if (var28.equals("Minimal")) {
                var29 = 0;
                if (var28.equals("Classic")) {
                    var29 = 1;
                }
                switch (var29) {
                    case 0 -> {}
                    case 1 -> {}
                    default -> {}
                }
                var30 = (3.0F + var25);
                arg0.rect(arg4, (arg5 - (var30 / 2.0F)), var21, var30, (var30 / 2.0F), Theme.a(Theme.mix(this.th.bg, this.th.text, 0.16F), 0.9F));
                arg0.rect(arg4, (arg5 - (var30 / 2.0F)), Math.max(var30, (var21 * var23)), var30, (var30 / 2.0F), this.th.accent);
                var31 = (arg4 + (var21 * var23));
                var32 = (5.0F + (1.5F * var25));
                arg0.circle(var31, arg5, var32, (this.th.bg | -16777216));
                arg0.ring(var31, arg5, var32, 2.0F, this.th.accent);
                arg0.rect(arg4, (arg5 - 3.0F), var21, 6.0F, 3.0F, Theme.a(Theme.mix(this.th.bg, this.th.text, 0.14F), 0.9F));
                arg0.gradient(arg4, (arg5 - 3.0F), Math.max(6.0F, (var21 * var23)), 6.0F, 3.0F, this.th.accent2, this.th.accent, true);
                if (var25 <= 0.01F) {
                    if (var24 != 0) {
                    }
                }
                arg0.shadow(arg4, (arg5 - 3.0F), (var21 * var23), 6.0F, 3.0F, 10.0F, Theme.a(this.th.accent, (0.4F * var25)));
                var30 = (arg4 + (var21 * var23));
                var31 = (7.0F + (2.0F * var25));
                arg0.glow(var30, arg5, (var31 * 2.4F), Theme.a(this.th.accent, (0.35F * var25)));
                arg0.circle(var30, arg5, var31, -1);
                arg0.ring(var30, arg5, (var31 - 1.0F), 2.0F, this.th.accent);
                var30 = (10.0F + (2.0F * var25));
                var31 = (var30 / 2.0F);
                var32 = (arg5 - (var30 / 2.0F));
                arg0.rect(arg4, var32, var21, var30, var31, Theme.a(Theme.mix(this.th.bg, this.th.text, 0.1F), 0.92F));
                arg0.stroke(arg4, var32, var21, var30, var31, 1.0F, Theme.a(this.th.border, 0.5F));
                float var33 = Math.max(var30, (var21 * var23));
                if (var25 > 0.01F) {
                    arg0.shadow(arg4, var32, var33, var30, var31, 10.0F, Theme.a(this.th.accent, (0.35F * var25)));
                }
                arg0.gradient(arg4, var32, var33, var30, var31, this.th.accent2, this.th.accent, true);
                arg0.gradient(arg4, var32, var33, (var30 / 2.0F), var31, Theme.a(-1, 0.22F), Theme.a(-1, 0.0F), false);
                if (var27 > 1) {
                    if (var27 <= 12) {
                        var34 = 1;
                        while (true) {
                            if (var34 >= var27) break;
                            var35 = (arg4 + ((var21 * ((float) var34)) / ((float) var27)));
                            var36 = (var35 < ((arg4 + var33) - var31));
                            if (var36 != 0) {
                            } else {
                            }
                            var35.circle(arg5, 1.2F, Theme.a(-1, 0.45F), Theme.a(this.th.text, 0.22F));
                            var34++;
                        }
                        var34 = ((arg4 + var33) - var31);
                        arg0.circle(var34, arg5, (var31 - 2.5F), -1);
                        if (var25 > 0.02F) {
                            var35 = (arg0.textWidth("inter-sb", 10.5F, var26) + 14.0F);
                            var36 = 18.0F;
                            float var37 = (((var32 - 6.0F) - var36) - (3.0F * var25));
                            float var38 = Math.max((arg4 - 4.0F), Math.min((((arg4 + var21) - var35) + 4.0F), (var34 - (var35 / 2.0F))));
                            arg0.save();
                            arg0.alpha(var25);
                            arg0.shadow(var38, (var37 + 2.0F), var35, var36, 9.0F, 8.0F, Theme.a(-16777216, 0.35F));
                            arg0.rect(var38, var37, var35, var36, 9.0F, this.th.accent);
                            arg0.text("inter-sb", 10.5F, (var38 + (var35 / 2.0F)), (var37 + (var36 / 2.0F)), 18, Theme.onColor(this.th.accent), var26);
                            arg0.restore();
                        }
                        var28 = ((arg4 + arg6) - var19);
                        if (this.focused != null) {
                            var29 = (arg1.equals(this.focusId));
                            if (!"Classic".equals(var18)) {
                                if (var29 != 0) {
                                }
                            }
                            arg0.rect(var28, (arg5 - 11.0F), var19, 22.0F, Math.max(0.0F, (this.ctlR() - 2.0F)), Theme.a(Theme.mix(this.th.bg, this.th.text, 0.06F), 0.85F));
                            if (var29 != 0) {
                            } else {
                            }
                            var28.stroke((arg5 - 11.0F), var19, 22.0F, Math.max(0.0F, (this.ctlR() - 2.0F)), 1.0F, this.th.accent, Theme.a(this.th.border, 0.8F));
                            if (var29 != 0) {
                            } else {
                            }
                            "inter-med".text(11.0F, (var28 + (var19 / 2.0F)), arg5, 18, this.th.text, this.focused.text, var26);
                            if (this.in(var28, (arg5 - 11.0F), var19, 22.0F)) {
                            } else {
                            }
                            var30 = "slv:" + (arg1).to(1.0F, 0.0F, 16.0F);
                            arg0.text("inter-sb", 11.5F, (var28 + var19), arg5, 20, Theme.mix(Theme.a(this.th.text, 0.85F), this.th.accent, Math.max(var30, (var25 * 0.6F))), var26);
                            this.hit(var28, (arg5 - 11.0F), var19, 22.0F, () -> this.lambda$slider$0(var26, arg1, arg2, arg3, var8, var10, var14), null);
                            var30 = (p0) -> this.lambda$slider$2(arg4, var21, arg2, arg3, var8, var10, var14, p0);
                            this.hit((arg4 - 8.0F), (arg5 - 12.0F), (var21 + 16.0F), 24.0F, () -> this.lambda$slider$3(arg1, var30), null);
                            return;
                        }
                    }
                }
            }
        }
    }

    private void minmax(Canvas arg0, String arg1, String arg2, GuiData.Setting arg3, float arg4, float arg5, float arg6) {
        boolean var25 = false;
        int var26 = 0;
        int var27 = 0;
        float var28 = 0.0F;
        float var29 = 0.0F;
        double var8 = arg3.num(0, 0.0);
        double var10 = arg3.num(1, 1.0);
        double var12 = arg3.num(4, 0.0);
        double var14 = num(this.get(arg2, (arg3.name) + "_lo"), arg3.num(2, var8));
        double var16 = num(this.get(arg2, (arg3.name) + "_hi"), arg3.num(3, var10));
        float var20 = ((arg6 - 74.0F) - 10.0F);
        float var21 = ((float) ((var14 - var8) / Math.max(1.0E-9, (var10 - var8))));
        float var22 = ((float) ((var16 - var8) / Math.max(1.0E-9, (var10 - var8))));
        float var23 = this.an.to("mmA:" + (arg1), var21, 22.0F);
        float var24 = this.an.to("mmB:" + (arg1), var22, 22.0F);
        if ("Classic".equals(this.gstr("Slider Style", "Pill"))) {
            arg0.rect(arg4, (arg5 - 3.0F), var20, 6.0F, 3.0F, Theme.a(Theme.mix(this.th.bg, this.th.text, 0.14F), 0.9F));
            arg0.gradient((arg4 + (var20 * var23)), (arg5 - 3.0F), Math.max(4.0F, (var20 * (var24 - var23))), 6.0F, 3.0F, this.th.accent2, this.th.accent, true);
            new float[2][0] = var23;
            new float[2][1] = var24;
            var25 = new float[2];
            var26 = var25.length;
            var27 = 0;
            while (true) {
                if (var27 >= var26) break;
                var28 = var25[var27];
                var29 = (arg4 + (var20 * var28));
                arg0.circle(var29, arg5, 7.0F, -1);
                arg0.ring(var29, arg5, 6.0F, 2.0F, this.th.accent);
                var27++;
            }
        } else {
            var25 = "Minimal".equals(this.gstr("Slider Style", "Pill"));
            if (var25) {
            } else {
            }
            var26 = 10.0F;
            var27 = (var26 / 2.0F);
            if (var25) {
            } else {
            }
            arg4.rect((arg5 - var27), var20, var26, var27, this.th.bg, Theme.a(Theme.mix(this.th.text, 0.16F, 0.1F), 0.92F));
            var28 = (arg4 + (var20 * var23));
            var29 = (arg4 + (var20 * var24));
            if (var25) {
                arg0.rect(var28, (arg5 - var27), Math.max(var26, (var29 - var28)), var26, var27, this.th.accent);
            } else {
                arg0.gradient((var28 - var27), (arg5 - var27), Math.max(var26, ((var29 - var28) + var26)), var26, var27, this.th.accent2, this.th.accent, true);
                arg0.gradient((var28 - var27), (arg5 - var27), Math.max(var26, ((var29 - var28) + var26)), var27, var27, Theme.a(-1, 0.22F), Theme.a(-1, 0.0F), false);
            }
            new float[2][0] = var28;
            new float[2][1] = var29;
            float[] var30 = new float[2];
            int var31 = var30.length;
            int var32 = 0;
            while (true) {
                if (var32 >= var31) break;
                float var33 = var30[var32];
                if (var25) {
                    arg0.circle(var33, arg5, 5.0F, (this.th.bg | -16777216));
                    arg0.ring(var33, arg5, 5.0F, 2.0F, this.th.accent);
                } else {
                    arg0.circle(var33, arg5, (var27 - 2.5F), -1);
                }
                var32++;
            }
            arg0.text("inter-med", 11.0F, (arg4 + arg6), arg5, 20, this.th.text, (fmt(var14, var12)) + " \u2013 " + (fmt(var16, var12)));
            this.hit((arg4 - 8.0F), (arg5 - 12.0F), (var20 + 16.0F), 24.0F, () -> this.lambda$minmax$0(arg4, var20, var21, var22, arg1, var8, var10, var12, arg2, arg3), null);
            return;
        }
    }

    private void dropdown(Canvas arg0, String arg1, float arg2, float arg3, float arg4, List arg5, String arg6, Consumer arg7) {
        float var11 = (arg3 - (28.0F / 2.0F));
        if (this.pop != null) {
            if (!this.pop.color) {
                int var12 = (arg1.equals(this.pop.id));
                if (!(this.in(arg2, var11, arg4, 28.0F))) {
                    if (var12 != 0) {
                    }
                }
                float var13 = "ddh:" + (arg1).to(1.0F, 0.0F, 16.0F);
                arg0.rect(arg2, var11, arg4, 28.0F, this.ctlR(), Theme.a(Theme.mix(this.th.bg, this.th.text, (0.05F + (0.04F * var13))), 0.85F));
                if (var12 != 0) {
                } else {
                }
                arg2.stroke(var11, arg4, 28.0F, this.ctlR(), 1.0F, this.th.accent, Theme.mix(Theme.a(this.th.border, 0.9F), Theme.a(this.th.accent, 0.6F), var13));
                arg0.text("inter-med", 12.0F, (arg2 + 12.0F), arg3, 17, this.th.text, this.ellipsize(arg0, "inter-med", 12.0F, display(arg6), (arg4 - 36.0F)));
                if (var12 != 0) {
                } else {
                }
                float var14 = "ddr:" + (arg1).to(1.0F, 0.0F, 14.0F);
                arg0.save();
                arg0.translate(((arg2 + arg4) - 16.0F), arg3);
                if (var14 > 0.5F) {
                } else {
                }
                Icons.draw("down", "down", 0.0F, ((var14 - 0.5F) * -2.0F), 12.0F, Theme.mix(this.th.muted, this.th.accent, var14));
                arg0.restore();
                this.hit(arg2, var11, arg4, 28.0F, () -> this.lambda$dropdown$0(var12, arg1, arg5, arg6, arg7, arg4, arg2, var11, 28.0F), null);
                return;
            }
        }
    }

    private void colorSwatch(Canvas arg0, String arg1, String arg2, GuiData.Setting arg3, float arg4, float arg5, float arg6) {
        String var8 = str(this.get(arg2, arg3.name), String.valueOf(arg3.defaultValue()));
        int var9 = this.resolveColor(var8);
        float var11 = ((arg4 + arg6) - 44.0F);
        if (this.pop != null) {
            if (this.pop.color) {
                int var12 = (arg1.equals(this.pop.id));
                arg0.shadow(var11, (arg5 - 11.0F), 44.0F, 22.0F, 7.0F, 8.0F, Theme.a((var9 | -16777216), 0.45F));
                arg0.rect(var11, (arg5 - 11.0F), 44.0F, 22.0F, 7.0F, (-16777216 | var9));
                if (var12 != 0) {
                } else {
                }
                var11.stroke((arg5 - 11.0F), 44.0F, 22.0F, 7.0F, 1.2F, -1, Theme.a(-1, 0.25F));
                if ("theme".equals(var8)) {
                } else {
                    if ("theme2".equals(var8)) {
                    } else {
                    }
                    String var13 = var8.toUpperCase(Locale.ROOT);
                    arg0.text("inter-med", 11.0F, (var11 - 10.0F), arg5, 20, this.th.muted, var13);
                    this.hit(var11, (arg5 - 11.0F), 44.0F, 22.0F, () -> this.lambda$colorSwatch$0(var12, arg1, arg2, arg3, var11, 44.0F, arg5, var9), null);
                    return;
                }
            }
        }
    }

    private void textInput(Canvas arg0, String arg1, float arg2, float arg3, float arg4, String arg5, String arg6, Consumer arg7) {
        float var11 = (arg3 - (28.0F / 2.0F));
        if (this.focused != null) {
            int var12 = (arg1.equals(this.focusId));
            if (var12 != 0) {
            } else {
            }
            float var13 = "tf:" + (arg1).to(1.0F, 0.0F, 14.0F);
            arg0.rect(arg2, var11, arg4, 28.0F, this.ctlR(), Theme.a(Theme.mix(this.th.bg, this.th.text, 0.05F), 0.85F));
            arg0.stroke(arg2, var11, arg4, 28.0F, this.ctlR(), 1.0F, Theme.mix(Theme.a(this.th.border, 0.9F), this.th.accent, var13));
            if (var13 > 0.01F) {
                arg0.shadow(arg2, var11, arg4, 28.0F, this.ctlR(), 10.0F, Theme.a(this.th.accent, (0.25F * var13)));
            }
            arg0.save();
            arg0.scissor((arg2 + 8.0F), var11, (arg4 - 16.0F), 28.0F);
            if (var12 != 0) {
            } else {
            }
            String var14 = arg5;
            if (var14.isEmpty()) {
                if (var12 == 0) {
                    if (arg6.isEmpty()) {
                    } else {
                    }
                    "inter".text(12.0F, (arg2 + 11.0F), arg3, 17, this.th.muted, "Type\u2026", arg6);
                } else {
                    arg0.text("inter", 12.0F, (arg2 + 11.0F), arg3, 17, this.th.text, var14);
                }
                if (var12 != 0) {
                    this.caret(arg0, "inter", 12.0F, (arg2 + 11.0F), arg3, this.focused);
                }
                arg0.restore();
                this.hit(arg2, var11, arg4, 28.0F, () -> this.lambda$textInput$0(arg5, arg1, arg7), null);
                return;
            }
        }
    }

    private float drawMulti(Canvas arg0, String arg1, GuiData.Setting arg2, float arg3, float arg4, float arg5, int arg6) {
        float var10 = 0.0F;
        float var11 = 0.0F;
        List var8 = arg2.options();
        List var9 = list(this.get(arg1, arg2.name), arg2.defaultValue());
        arg0.text("inter-med", 12.5F, arg3, (arg4 + 18.0F), 17, Theme.a(this.th.text, 0.88F), arg2.name);
        var10 = arg3;
        var11 = (arg4 + 34.0F);
        Iterator var12 = var8.iterator();
        while (true) {
            if (!var12.hasNext()) break;
            String var13 = ((String) var12.next());
            float var14 = (arg0.textWidth("inter-med", 11.5F, var13) + 26.0F);
            if ((var10 + var14) > (arg3 + arg5)) {
                var10 = arg3;
                var11 = (var11 + 32.0F);
            }
            boolean var15 = var9.contains(var13);
            if (var15) {
            } else {
            }
            float var16 = "ms:" + (arg1) + "/" + (arg2.name) + "/" + (var13).to(1.0F, 0.0F, 14.0F);
            if (this.in(var10, var11, var14, 26.0F)) {
            } else {
            }
            float var17 = 0.0F;
            arg0.rect(var10, var11, var14, 26.0F, 13.0F, Theme.mix(Theme.a(Theme.mix(this.th.bg, this.th.text, (0.06F + (0.04F * var17))), 0.85F), Theme.a(this.th.accent, 0.25F), var16));
            arg0.stroke(var10, var11, var14, 26.0F, 13.0F, 1.0F, Theme.mix(Theme.a(this.th.border, 0.9F), this.th.accent, var16));
            arg0.text("inter-med", 11.5F, (var10 + (var14 / 2.0F)), (var11 + 13.0F), 18, Theme.mix(this.th.muted, this.th.text, var16), var13);
            this.hit(var10, var11, var14, 26.0F, () -> this.lambda$drawMulti$0(arg1, arg2, var13), null);
            var10 = (var10 + (var14 + 8.0F));
        }
        return (var11 + 36.0F);
    }

    private float drawIdList(Canvas arg0, String arg1, GuiData.Setting arg2, float arg3, float arg4, float arg5, int arg6) {
        float var9 = 0.0F;
        float var10 = 0.0F;
        Iterator var12 = null;
        String var13 = null;
        String var14 = null;
        List var8 = list(this.get(arg1, arg2.name), List.of());
        arg0.text("inter-med", 12.5F, arg3, (arg4 + 18.0F), 17, Theme.a(this.th.text, 0.88F), arg2.name);
        if (var8.size() == 1) {
        } else {
        }
        "inter".text(10.5F, (arg3 + arg5), (arg4 + 18.0F), 20, this.th.muted, var8.size(), (" entry") + (" entries"));
        var9 = arg3;
        var10 = (arg4 + 34.0F);
        String var11 = (arg1) + "/" + (arg2.name);
        var12 = var8.iterator();
        while (true) {
            if (!var12.hasNext()) break;
            var13 = ((String) var12.next());
            if (var13.startsWith("minecraft:")) {
            } else {
            }
            var14 = var13;
            float var15 = (arg0.textWidth("inter-med", 11.0F, var14) + 34.0F);
            if ((var9 + var15) > (arg3 + arg5)) {
                var9 = arg3;
                var10 = (var10 + 32.0F);
            }
            arg0.rect(var9, var10, var15, 26.0F, 8.0F, Theme.a(Theme.mix(this.th.bg, this.th.accent, 0.12F), 0.9F));
            arg0.text("inter-med", 11.0F, (var9 + 10.0F), (var10 + 13.0F), 17, this.th.text, var14);
            float var16 = ((var9 + var15) - 14.0F);
            boolean var17 = this.in((var16 - 8.0F), var10, 16.0F, 26.0F);
            if (var17) {
            } else {
            }
            Icons.draw("close", var16, (var10 + 13.0F), 10.0F, this.th.accent, this.th.muted);
            this.hit((var16 - 9.0F), var10, 18.0F, 26.0F, () -> this.lambda$drawIdList$0(arg1, arg2, var13), null);
            var9 = (var9 + (var15 + 8.0F));
        }
        if (this.focused == this.addField) {
            var12 = (var11.equals(this.addFieldOwner));
            if (var12 != 0) {
            } else {
            }
            var13 = "+ Add";
            var14 = Math.max(70.0F, (arg0.textWidth("inter-med", 11.0F, var13) + 24.0F));
            if ((var9 + var14) > (arg3 + arg5)) {
                var9 = arg3;
                var10 = (var10 + 32.0F);
            }
            arg0.rect(var9, var10, var14, 26.0F, 8.0F, Theme.a(Theme.mix(this.th.bg, this.th.text, 0.05F), 0.85F));
            if (var12 != 0) {
            } else {
            }
            var9.stroke(var10, var14, 26.0F, 8.0F, 1.0F, this.th.accent, Theme.a(this.th.border, 0.9F));
            if (var12 != 0) {
            } else {
            }
            if (var13.isEmpty()) {
            } else {
            }
            11.0F.text((var9 + 12.0F), (var10 + 13.0F), 17, this.th.text, this.th.accent, "id\u2026", var13);
            if (var12 != 0) {
                this.caret(arg0, "inter-med", 11.0F, (var9 + 12.0F), (var10 + 13.0F), this.addField);
            }
            this.hit(var9, var10, var14, 26.0F, () -> this.lambda$drawIdList$1(var11, arg1, arg2), null);
            return (var10 + 36.0F);
        }
    }

    private void drawPop(Canvas arg0) {
        ClickGui.Pop var2 = this.pop;
        float var3 = Anim.easeOutBack(Math.min(1.0F, (((this.time - var2.openedAt) * this.an_speed()) / 0.22F)));
        this.layer = 1;
        this.noClip();
        arg0.save();
        float var4 = (var2.x + (var2.w / 2.0F));
        float var5 = var2.y;
        arg0.alpha(Math.min(1.0F, (var3 * 1.4F)));
        arg0.translate(var4, var5);
        arg0.scale((0.92F + (0.08F * var3)));
        arg0.translate(-(var4), (-(var5) + ((1.0F - var3) * -6.0F)));
        arg0.shadow(var2.x, (var2.y + 8.0F), var2.w, var2.h, 12.0F, 30.0F, Theme.a(-16777216, 0.55F));
        arg0.glass(var2.x, var2.y, var2.w, var2.h, 12.0F, 1.0F);
        arg0.rect(var2.x, var2.y, var2.w, var2.h, 12.0F, Theme.a(Theme.mix(this.th.surface, this.th.bg, 0.3F), 0.94F));
        arg0.stroke(var2.x, var2.y, var2.w, var2.h, 12.0F, 1.0F, Theme.a(this.th.accent, 0.45F));
        this.hit(var2.x, var2.y, var2.w, var2.h, () -> ClickGui.lambda$drawPop$0(), () -> ClickGui.lambda$drawPop$1());
        if (var2.color) {
            this.drawColorPop(arg0, var2);
        } else {
            this.drawListPop(arg0, var2);
        }
        arg0.restore();
        this.layer = 0;
    }

    private void drawListPop(Canvas arg0, ClickGui.Pop arg1) {
        List var3 = null;
        float var4 = 0.0F;
        float var5 = 0.0F;
        float var6 = 0.0F;
        float var7 = 0.0F;
        String var8 = null;
        String var9 = null;
        ArrayList var10 = null;
        Iterator var11 = null;
        String var12 = null;
        Iterator var13 = null;
        var3 = arg1.options;
        var4 = (arg1.y + 5.0F);
        if (var3.size() > 9) {
            var5 = (arg1.x + 8.0F);
            var6 = (arg1.y + 8.0F);
            var7 = (arg1.w - 16.0F);
            arg0.rect(var5, var6, var7, 26.0F, 8.0F, Theme.a(this.th.bg, 0.6F));
            Icons.draw(arg0, "search", (var5 + 13.0F), (var6 + 13.0F), 11.0F, this.th.muted);
            var8 = arg1.filter.text;
            if (var8.isEmpty()) {
            } else {
            }
            if (var8.isEmpty()) {
            } else {
            }
            11.5F.text((var5 + 24.0F), (var6 + 13.0F), 17, this.th.muted, this.th.text, "Filter\u2026", var8);
            if (this.focused == arg1.filter) {
                this.caret(arg0, "inter", 11.5F, (var5 + 24.0F), (var6 + 13.0F), arg1.filter);
            }
            this.hit(var5, var6, var7, 26.0F, () -> this.lambda$drawListPop$0(arg1), null);
            var4 = (arg1.y + 40.0F);
            var9 = var8.toLowerCase(Locale.ROOT);
            if (!var9.isEmpty()) {
                var10 = new ArrayList();
                var11 = var3.iterator();
                while (true) {
                    if (!var11.hasNext()) break;
                    var12 = ((String) var11.next());
                    if (display(var12).toLowerCase(Locale.ROOT).contains(var9)) {
                        var10.add(var12);
                    }
                }
                var3 = var10;
            }
            var5 = (((arg1.y + arg1.h) - 5.0F) - var4);
            var6 = Math.max(0.0F, (((float) (var3.size() * 28)) - var5));
            arg1.scrollT = Math.max(0.0F, Math.min(var6, arg1.scrollT));
            arg1.scrollY = (arg1.scrollY + ((arg1.scrollT - arg1.scrollY) * 0.35F));
            arg0.save();
            arg0.scissor(arg1.x, var4, arg1.w, var5);
            var7 = this.clipX;
            var8 = this.clipY;
            var9 = this.clipW;
            var10 = this.clipH;
            this.clip(arg1.x, var4, arg1.w, var5);
            var11 = (var4 - arg1.scrollY);
            var12 = 0;
            var13 = var3.iterator();
            while (true) {
                if (!var13.hasNext()) break;
                String var14 = ((String) var13.next());
                if ((var11 + 28.0F) >= var4) {
                    if (var11 <= (var4 + var5)) {
                        boolean var15 = var14.equals(arg1.current);
                        float var16 = Anim.easeOutCubic(((((this.time - arg1.openedAt) * this.an_speed()) - (((float) var12) * 0.012F)) / 0.2F));
                        if (this.in((arg1.x + 5.0F), var11, (arg1.w - 10.0F), 28.0F)) {
                        } else {
                        }
                        float var17 = "po:" + (arg1.id) + "/" + (var14).to(1.0F, 0.0F, 20.0F);
                        arg0.save();
                        arg0.alpha(var16);
                        arg0.translate(0.0F, ((1.0F - var16) * 4.0F));
                        if (var15) {
                            arg0.rect((arg1.x + 5.0F), (var11 + 2.0F), (arg1.w - 10.0F), 24.0F, 7.0F, Theme.a(this.th.accent, 0.2F));
                        }
                        if (var17 > 0.01F) {
                            arg0.rect((arg1.x + 5.0F), (var11 + 2.0F), (arg1.w - 10.0F), 24.0F, 7.0F, Theme.a(this.th.text, (0.07F * var17)));
                        }
                        if (var15) {
                        } else {
                        }
                        if (var15) {
                        } else {
                        }
                        "inter-med".text(12.0F, ((arg1.x + 16.0F) + (var17 * 3.0F)), (var11 + 14.0F), 17, this.th.text, Theme.mix(this.th.muted, this.th.text, (0.4F + (0.6F * var17))), this.ellipsize(arg0, "inter-med", 12.0F, display(var14), (arg1.w - 48.0F)));
                        if (var15) {
                            Icons.draw(arg0, "check", ((arg1.x + arg1.w) - 20.0F), (var11 + 14.0F), 12.0F, this.th.accent);
                        }
                        arg0.restore();
                        this.hit((arg1.x + 5.0F), var11, (arg1.w - 10.0F), 28.0F, () -> this.lambda$drawListPop$1(arg1, var14), null);
                    }
                    var11 = (var11 + 28.0F);
                    var12++;
                }
            }
            this.clipX = var7;
            this.clipY = var8;
            this.clipW = var9;
            this.clipH = var10;
            arg0.restore();
            if (var6 > 0.0F) {
                var13 = Math.max(20.0F, ((var5 * var5) / (var5 + var6)));
                arg0.rect(((arg1.x + arg1.w) - 5.0F), (var4 + ((var5 - var13) * (arg1.scrollY / var6))), 3.0F, var13, 1.5F, Theme.a(this.th.text, 0.25F));
            }
            if (var3.isEmpty()) {
                arg0.text("inter", 11.5F, (arg1.x + (arg1.w / 2.0F)), (var4 + 20.0F), 18, this.th.muted, "No matches");
            }
            return;
        }
    }

    private void drawColorPop(Canvas arg0, ClickGui.Pop arg1) {
        int var13 = 0;
        int var14 = 0;
        int var15 = 0;
        float var16 = 0.0F;
        float var3 = (arg1.x + 14.0F);
        float var4 = (arg1.y + 14.0F);
        float var5 = (arg1.w - 28.0F);
        float[] var6 = arg1.hsv;
        int var7 = (-16777216 | Theme.hsvToRgb(var6[0], 1.0F, 1.0F));
        arg0.gradient(var3, var4, var5, 130.0F, 8.0F, -1, var7, true);
        arg0.gradient(var3, var4, var5, 130.0F, 8.0F, 0, -16777216, false);
        float var9 = (var3 + (var6[1] * var5));
        float var10 = (var4 + ((1.0F - var6[2]) * 130.0F));
        arg0.ring(var9, var10, 7.0F, 2.5F, -1);
        arg0.ring(var9, var10, 8.5F, 1.0F, 1711276032);
        this.hit(var3, var4, var5, 130.0F, () -> this.lambda$drawColorPop$0(var6, var3, var5, var4, 130.0F, arg1), null);
        float var11 = ((var4 + 130.0F) + 12.0F);
        var13 = 0;
        while (true) {
            if (var13 >= 6) break;
            var14 = (-16777216 | Theme.hsvToRgb((((float) var13) / 6.0F), 1.0F, 1.0F));
            var15 = (-16777216 | Theme.hsvToRgb((((float) (var13 + 1)) / 6.0F), 1.0F, 1.0F));
            var16 = (var3 + ((var5 * ((float) var13)) / 6.0F));
            if (var13 > 0) {
            } else {
            }
            if (var13 > 0) {
            } else {
            }
            if (var13 != 0) {
                if (var13 == 5) {
                }
            }
            var11.gradient((var5 / 6.0F), (0.5F + 0.0F), 12.0F, 6.0F, 0.0F, var14, var15, true);
            var13++;
        }
        var13 = (var3 + (var6[0] * var5));
        arg0.rect((var13 - 3.0F), (var11 - 3.0F), 6.0F, (12.0F + 6.0F), 3.0F, -1);
        arg0.stroke((var13 - 3.0F), (var11 - 3.0F), 6.0F, (12.0F + 6.0F), 3.0F, 1.0F, 1711276032);
        this.hit(var3, (var11 - 4.0F), var5, (12.0F + 8.0F), () -> this.lambda$drawColorPop$2(var6, var3, var5, arg1), null);
        var14 = ((var11 + 12.0F) + 14.0F);
        var15 = Theme.hsvToRgb(var6[0], var6[1], var6[2]);
        arg0.rect(var3, var14, 30.0F, 26.0F, 7.0F, (-16777216 | var15));
        var16 = (this.focused == arg1.filter);
        arg0.rect((var3 + 38.0F), var14, 92.0F, 26.0F, 7.0F, Theme.a(this.th.bg, 0.6F));
        if (var16 != 0) {
        } else {
        }
        (var3 + 38.0F).stroke(var14, 92.0F, 26.0F, 7.0F, 1.0F, this.th.accent, Theme.a(this.th.border, 0.9F));
        if (var16 != 0) {
        } else {
        }
        "inter-med".text(11.5F, (var3 + 48.0F), (var14 + 13.0F), 17, this.th.text, arg1.filter.text, Theme.toHex(var15));
        if (var16 != 0) {
            this.caret(arg0, "inter-med", 11.5F, (var3 + 48.0F), (var14 + 13.0F), arg1.filter);
        }
        this.hit((var3 + 38.0F), var14, 92.0F, 26.0F, () -> this.lambda$drawColorPop$4(arg1, var15, var6), null);
        new String[2][0] = "theme";
        new String[2][1] = "Follow theme accent";
        new String[][2][0] = new String[2];
        new String[2][0] = "theme2";
        new String[2][1] = "Follow theme accent 2";
        new String[][2][1] = new String[2];
        String[][] var17 = new String[][2];
        int var18 = 0;
        while (true) {
            if (var18 >= 2) break;
            Object var19 = var17[var18][0];
            float var20 = ((var14 + 34.0F) + ((float) (var18 * 32)));
            boolean var21 = var19.equals(str(this.get(arg1.module, arg1.key), ""));
            if (var18 == 0) {
            } else {
            }
            int var22 = this.th.accent2;
            if (this.in(var3, var20, var5, 26.0F)) {
            } else {
            }
            float var23 = "cpb:" + (var18).to(1.0F, 0.0F, 16.0F);
            if (var21) {
            } else {
            }
            var3.rect(var20, var5, 26.0F, 7.0F, var22, Theme.a(0.32F, (0.12F + (0.08F * var23))));
            if (var21) {
            } else {
            }
            var3.stroke(var20, var5, 26.0F, 7.0F, 1.0F, var22, Theme.a(0.9F, (0.35F + (0.3F * var23))));
            arg0.circle((var3 + 14.0F), (var20 + 13.0F), 5.0F, var22);
            arg0.text("inter-med", 11.0F, (var3 + 26.0F), (var20 + 13.0F), 17, this.th.text, var17[var18][1]);
            if (var21) {
                Icons.draw(arg0, "check", ((var3 + var5) - 14.0F), (var20 + 13.0F), 11.0F, var22);
            }
            this.hit(var3, var20, var5, 26.0F, () -> this.lambda$drawColorPop$6(arg1, var19), null);
            var18++;
        }
    }

    private void commitColor(ClickGui.Pop arg0) {
        this.set(arg0.module, arg0.key, Theme.toHex(Theme.hsvToRgb(arg0.hsv[0], arg0.hsv[1], arg0.hsv[2])));
    }

    private boolean inPop(float arg0, float arg1) {
        if (this.pop != null) {
            if (arg0 >= this.pop.x) {
                if (arg1 >= this.pop.y) {
                    if (arg0 <= (this.pop.x + this.pop.w)) {
                        return (arg1 <= (this.pop.y + this.pop.h));
                    }
                }
            }
        }
    }

    private float drawThemes(Canvas arg0, float arg1, float arg2, float arg3, float arg4) {
        float var6 = 0.0F;
        Iterator var12 = null;
        arg2 = (this.row(arg0, "\u00a7theme", "Theme colours for visuals", (arg1 + 10.0F), arg2, (arg3 - 20.0F), 0, (p0, p1, p2) -> this.lambda$drawThemes$0(arg0, p0, p1, p2)) + 8.0F);
        var6 = 172.0F;
        int var9 = Math.max(1, ((int) ((arg3 + 12.0F) / (var6 + 12.0F))));
        var6 = ((arg3 - (12.0F * ((float) (var9 - 1)))) / ((float) var9));
        String var10 = this.store.themeName();
        int var11 = 0;
        var12 = this.data.themes.entrySet().iterator();
        while (true) {
            if (!var12.hasNext()) break;
            Map.Entry var13 = ((Map.Entry) var12.next());
            String var14 = ((String) var13.getKey());
            List var15 = ((List) var13.getValue());
            int var16 = (var11 % var9);
            int var17 = (var11 / var9);
            float var18 = (arg1 + (((float) var16) * (var6 + 12.0F)));
            float var19 = (arg2 + (((float) var17) * (96.0F + 12.0F)));
            float var20 = Anim.easeOutCubic(((arg4 - (0.012F * ((float) var11))) / 0.32F));
            boolean var21 = var14.equals(var10);
            if (this.in(var18, var19, var6, 96.0F)) {
            } else {
            }
            float var22 = "th:" + (var14).to(1.0F, 0.0F, 14.0F);
            int var23 = (-16777216 | Theme.hex(((String) var15.get(0)), 8947848));
            int var24 = (-16777216 | Theme.hex(((String) var15.get(1)), 6710886));
            int var25 = (-16777216 | Theme.hex(((String) var15.get(2)), 1118481));
            int var26 = (-16777216 | Theme.hex(((String) var15.get(3)), 2236962));
            int var27 = (-16777216 | Theme.hex(((String) var15.get(4)), 3355443));
            float var28 = (-3.0F * var22);
            arg0.save();
            arg0.alpha(var20);
            arg0.translate(0.0F, (((1.0F - var20) * 14.0F) + var28));
            if (var21) {
            } else {
            }
            if (var21) {
            } else {
            }
            (var19 + 4.0F).shadow(var6, 96.0F, 12.0F, (16.0F + (8.0F * var22)), var23, -16777216, Theme.a(0.45F, (0.3F + (0.1F * var22))));
            arg0.gradient(var18, var19, var6, 96.0F, 12.0F, var26, var25, false);
            arg0.glow(((var18 + var6) - 26.0F), (var19 + 24.0F), 42.0F, Theme.a(var23, (0.35F + (0.25F * var22))));
            if (var21) {
            } else {
            }
            if (var21) {
            } else {
            }
            var19.stroke(var6, 96.0F, 12.0F, 2.0F, 1.0F, var23, Theme.a(var27, 0.9F));
            if (Theme.luma(var26) > 0.55F) {
            } else {
            }
            int var29 = -1184272;
            arg0.text("inter-sb", 13.0F, (var18 + 14.0F), (var19 + 22.0F), 17, var29, var14);
            int var30 = 0;
            while (true) {
                if (var30 >= 5) break;
                int var31 = (-16777216 | Theme.hex(((String) var15.get(var30)), 8947848));
                arg0.circle(((var18 + 22.0F) + ((float) (var30 * 22))), ((var19 + 96.0F) - 24.0F), 8.0F, var31);
                arg0.ring(((var18 + 22.0F) + ((float) (var30 * 22))), ((var19 + 96.0F) - 24.0F), 8.0F, 1.0F, Theme.a(-1, 0.18F));
                var30++;
            }
            arg0.gradient((var18 + 14.0F), (var19 + 38.0F), (var6 - 28.0F), 5.0F, 2.5F, var23, var24, true);
            if (var21) {
                arg0.circle(((var18 + var6) - 18.0F), (var19 + 20.0F), 9.0F, var23);
                Icons.draw(arg0, "check", ((var18 + var6) - 18.0F), (var19 + 20.0F), 10.0F, Theme.onColor(var23));
            }
            arg0.restore();
            this.hit(var18, var19, var6, 96.0F, () -> this.lambda$drawThemes$2(var14, var15), null);
            var11++;
        }
        var12 = (((this.data.themes.size() + var9) - 1) / var9);
        return ((arg2 + (((float) var12) * (96.0F + 12.0F))) + 10.0F);
    }

    private float drawProfiles(Canvas arg0, float arg1, float arg2, float arg3, float arg4) {
        float var8 = 0.0F;
        int var10 = 0;
        int var11 = 0;
        int var12 = 0;
        boolean var13 = false;
        float var14 = 0.0F;
        String var15 = null;
        arg2 = this.nameBar(arg0, arg1, arg2, arg3, "New profile name\u2026", "Save current", (p0) -> this.lambda$drawProfiles$0(p0));
        String var6 = this.store.activeProfile();
        List var7 = this.store.profiles();
        var8 = arg1;
        new String[2][0] = "fimport";
        new String[2][1] = "Load from file\u2026";
        new String[][2][0] = new String[2];
        new String[2][0] = "ffolder";
        new String[2][1] = "Open configs folder";
        new String[][2][1] = new String[2];
        var10 = new String[][2];
        var11 = var10.length;
        var12 = 0;
        while (true) {
            if (var12 >= var11) break;
            var13 = var10[var12];
            var14 = (arg0.textWidth("inter-med", 12.0F, var13[1]) + 30.0F);
            if ("fimport".equals(var13[0])) {
            } else {
            }
            var15 = () -> this.lambda$drawProfiles$1();
            this.button(arg0, var13[0], var8, arg2, var14, 30.0F, var13[1], false, var15);
            var8 = (var8 + (var14 + 10.0F));
            var12++;
        }
        arg2 = (arg2 + 46.0F);
        var10 = 0;
        var11 = var7.iterator();
        while (true) {
            if (!var11.hasNext()) break;
            var12 = ((String) var11.next());
            var13 = var12.equals(var6);
            var10++;
            if (var13) {
            } else {
            }
            new String[4][0] = "Load";
            new String[4][1] = "Overwrite";
            new String[4][2] = "Export";
            new String[4][3] = "Delete";
            arg2 = arg0.listRow("prof:" + (var12), arg1, arg2, arg3, var10, arg4, var12, "ACTIVE", null, new String[4], (p0) -> this.lambda$drawProfiles$2(var12, p0), var13);
        }
        if (var7.isEmpty()) {
            arg2 = this.empty(arg0, arg1, arg2, arg3, "No profiles yet. Save your current setup above.");
        }
        arg2 = (arg2 + 8.0F);
        arg0.text("inter-sb", 10.0F, (arg1 + 6.0F), (arg2 + 10.0F), 17, Theme.a(this.th.accent, 0.9F), "FILES IN CLARITY/CONFIGS");
        arg2 = (arg2 + 26.0F);
        var11 = this.store.configFiles();
        var12 = 0;
        var13 = var11.iterator();
        while (true) {
            if (!var13.hasNext()) break;
            var14 = ((Path) var13.next());
            var15 = var14.getFileName().toString();
            var12++;
            new String[2][0] = "Load";
            new String[2][1] = "Import";
            arg2 = this.listRow(arg0, "file:" + (var15), arg1, arg2, arg3, (var10 + var12), arg4, var15, null, new String[2], (p0) -> this.lambda$drawProfiles$5(var14, p0), false);
        }
        if (var11.isEmpty()) {
            arg2 = this.empty(arg0, arg1, arg2, arg3, "Exported profiles and .json configs you put in this folder show up here.");
        }
        return (arg2 + 10.0F);
    }

    private void importFile(Path arg0, boolean arg1) {
        String var3 = null;
        try {
            var3 = this.store.importProfile(arg0, arg1);
            if (arg1) {
            } else {
            }
            "Loaded ".toast(("Imported ") + (var3));
        } catch (Exception ignored) {
        }
        var3 = this;
        this.toast("Can't load " + (String.valueOf(arg0.getFileName())) + ": " + (var3.getMessage()));
    }

    private void pickAndImport() {
        if (!(this.store.pickConfigFile((p0) -> this.lambda$pickAndImport$0(p0)))) {
            this.openFolder("No file picker here: drop the .json in this folder, then press Load below");
        }
    }

    private void openFolder(String arg0) {
        this.store.openConfigFolder((p0) -> this.lambda$openFolder$0(arg0, p0));
    }

    private float drawBindSets(Canvas arg0, float arg1, float arg2, float arg3, float arg4) {
        arg2 = this.nameBar(arg0, arg1, arg2, arg3, "Bind set name\u2026", "Save current binds", (p0) -> this.lambda$drawBindSets$0(p0));
        List var6 = this.store.bindSets();
        int var7 = 0;
        Iterator var8 = var6.iterator();
        while (true) {
            if (!var8.hasNext()) break;
            String var9 = ((String) var8.next());
            var7++;
            new String[3][0] = "Load";
            new String[3][1] = "Overwrite";
            new String[3][2] = "Delete";
            arg2 = this.listRow(arg0, "bind:" + (var9), arg1, arg2, arg3, var7, arg4, var9, null, new String[3], (p0) -> this.lambda$drawBindSets$1(var9, p0), false);
        }
        if (var6.isEmpty()) {
            arg2 = this.empty(arg0, arg1, arg2, arg3, "No bind sets yet. Save your current keybinds above.");
        }
        return (arg2 + 10.0F);
    }

    private float drawServers(Canvas arg0, float arg1, float arg2, float arg3, float arg4) {
        int var9 = 0;
        float var10 = 0.0F;
        String var6 = this.store.currentServer();
        String var7 = this.store.activeProfile();
        arg0.rect(arg1, arg2, arg3, 56.0F, 12.0F, Theme.a(Theme.mix(this.th.bg, this.th.accent, 0.08F), 0.7F));
        arg0.stroke(arg1, arg2, arg3, 56.0F, 12.0F, 1.0F, Theme.a(this.th.accent, 0.35F));
        arg0.text("inter-med", 11.0F, (arg1 + 16.0F), (arg2 + 19.0F), 17, this.th.muted, "Current server");
        if (var6 != null) {
            if (var6.isEmpty()) {
            }
        }
        "inter-sb".text(14.0F, (arg1 + 16.0F), (arg2 + 38.0F), 17, this.th.text, "Not connected", var6);
        if (var6 != null) {
            if (!var6.isEmpty()) {
                if (var7 != null) {
                    if (!var7.isEmpty()) {
                        var9 = "Auto-apply \u201c" + (var7) + "\u201d here";
                        var10 = (arg0.textWidth("inter-sb", 11.5F, var9) + 28.0F);
                        this.button(arg0, "srvmap", (((arg1 + arg3) - 16.0F) - var10), ((arg2 + (56.0F / 2.0F)) - 15.0F), var10, 30.0F, var9, true, () -> this.lambda$drawServers$0(var6, var7));
                    }
                    arg2 = (arg2 + (56.0F + 14.0F));
                    var9 = 0;
                    var10 = this.store.serverProfiles().entrySet().iterator();
                    while (true) {
                        if (!var10.hasNext()) break;
                        Map.Entry var11 = ((Map.Entry) var10.next());
                        String var12 = ((String) var11.getKey());
                        var9++;
                        new String[1][0] = "Remove";
                        arg2 = this.listRow(arg0, "srv:" + (var12), arg1, arg2, arg3, var9, arg4, var12, ((String) var11.getValue()), new String[1], (p0) -> this.lambda$drawServers$1(var12, p0), false);
                    }
                    if (this.store.serverProfiles().isEmpty()) {
                        arg2 = this.empty(arg0, arg1, arg2, arg3, "No server mappings yet.");
                    }
                    return (arg2 + 10.0F);
                }
            }
        }
    }

    private float drawGuiPage(Canvas arg0, float arg1, float arg2, float arg3, float arg4) {
        float var14 = 0.0F;
        float var6 = this.gnum("Card Rounding", 14.0);
        int var7 = 0;
        Iterator var8 = GUI_SECTIONS.entrySet().iterator();
        while (true) {
            if (!var8.hasNext()) break;
            Map.Entry var9 = ((Map.Entry) var8.next());
            String var10 = ((String) var9.getKey());
            float var11 = Anim.easeOutCubic(((arg4 - (0.04F * ((float) var7))) / 0.34F));
            arg0.save();
            arg0.alpha(var11);
            if (this.pageSlide) {
            } else {
            }
            0.0F.translate(((1.0F - var11) * 12.0F), 0.0F);
            arg0.text("inter-sb", 10.0F, (arg1 + 6.0F), (arg2 + 10.0F), 17, Theme.a(this.th.accent, 0.9F), var10.toUpperCase(Locale.ROOT));
            arg2 = (arg2 + 22.0F);
            float var12 = arg2;
            float var13 = ((Float) this.measured.getOrDefault("\u00a7gui:" + (var10), 120.0F));
            arg0.rect(arg1, arg2, arg3, var13, var6, Theme.a(Theme.mix(this.th.bg, this.th.text, 0.035F), this.gnum("Card Opacity", 0.62)));
            arg0.stroke(arg1, arg2, arg3, var13, var6, 1.0F, Theme.a(this.th.border, 0.6F));
            var14 = this.drawSettings(arg0, "Click GUI", ((List) var9.getValue()), (arg1 + 20.0F), (arg2 + 6.0F), (arg3 - 40.0F));
            if ("General".equals(var10)) {
                boolean var15 = this.store.webMode();
                if (var15) {
                } else {
                }
                String var16 = "Switch to the web GUI";
                float var17 = (arg0.textWidth("inter-med", 12.0F, var16) + 34.0F);
                if (var15) {
                    Objects.requireNonNull(this.store);
                } else {
                }
                arg0.button("openweb", (((arg1 + arg3) - 20.0F) - var17), (var14 + 4.0F), var17, 30.0F, var16, 0, () -> this.store.openWebGui(), () -> this.lambda$drawGuiPage$0());
                var14 = (var14 + 42.0F);
            }
            var14 = (var14 + 6.0F);
            this.measured.put("\u00a7gui:" + (var10), (var14 - var12));
            arg0.restore();
            arg2 = (var14 + 18.0F);
            var7++;
        }
        return arg2;
    }

    private void changeTheme(String arg0, List arg1) {
        if (arg0.equals(this.store.themeName())) {
            return;
        }
        this.themeStyle = this.gstr("Theme Transition", "Fade");
        if ("None".equals(this.themeStyle)) {
            this.store.applyTheme(arg0, arg1);
            this.toast((arg0) + " applied");
            return;
        }
        if ("Fade".equals(this.themeStyle)) {
            this.oldTheme = this.th;
            this.store.applyTheme(arg0, arg1);
            this.themeAt = this.time;
            this.toast((arg0) + " applied");
            return;
        }
        this.pendingThemeName = arg0;
        this.pendingThemeCols = arg1;
        this.themeOx = this.mx;
        this.themeOy = this.my;
        this.snapRequest = 2;
    }

    float themeP() {
        if (this.themeAt < -50.0F) {
            return 1.0F;
        }
        return Math.max(0.0F, Math.min(1.0F, (((this.time - this.themeAt) * this.an_speed()) / 0.95F)));
    }

    private void drawThemeTransition(Canvas arg0) {
        int var5 = 0;
        float var6 = 0.0F;
        float var7 = 0.0F;
        float var8 = 0.0F;
        float var9 = 0.0F;
        int var10 = 0;
        int var11 = 0;
        double var12 = 0.0;
        if (this.oldTheme != null) {
            if (this.themeAt >= -50.0F) {
                if (this.haveSnapshot) {
                    if ("Fade".equals(this.themeStyle)) {
                    }
                }
                return;
                float var2 = this.themeP();
                if (var2 >= 1.0F) {
                    return;
                }
                if (var2 < 0.5F) {
                } else {
                }
                float var3 = (1.0F - (((float) Math.pow(((double) ((-2.0F * var2) + 2.0F)), 3.0)) / 2.0F));
                String var4 = this.themeStyle;
                var5 = -1;
                switch (var4.hashCode()) {
                    case 2181788 -> {}
                    case 2696295 -> {}
                    default -> {}
                }
                if (var4.equals("Wipe")) {
                    var5 = 0;
                    if (var4.equals("Fade")) {
                        var5 = 1;
                    }
                    switch (var5) {
                        case 0 -> {}
                        case 1 -> {}
                        default -> {}
                    }
                    var6 = 0.35F;
                    var7 = (((-(this.sh) * var6) - 40.0F) + (var3 * ((this.sw + (this.sh * var6)) + 80.0F)));
                    new float[8][0] = var7;
                    new float[8][1] = 0.0F;
                    new float[8][2] = this.sw;
                    new float[8][3] = 0.0F;
                    new float[8][4] = this.sw;
                    new float[8][5] = this.sh;
                    new float[8][6] = (var7 + (this.sh * var6));
                    new float[8][7] = this.sh;
                    arg0.snapPiece(new float[8], this.sw, this.sh, 1.0F);
                    var8 = ((float) Math.sin((3.141592653589793 * ((double) var2))));
                    arg0.line(var7, 0.0F, (var7 + (this.sh * var6)), this.sh, 22.0F, Theme.a(this.th.accent, (0.18F * var8)));
                    arg0.line(var7, 0.0F, (var7 + (this.sh * var6)), this.sh, 3.0F, Theme.a(Theme.mix(this.th.accent, -1, 0.4F), (0.9F * var8)));
                    new float[8][0] = 0.0F;
                    new float[8][1] = 0.0F;
                    new float[8][2] = this.sw;
                    new float[8][3] = 0.0F;
                    new float[8][4] = this.sw;
                    new float[8][5] = this.sh;
                    new float[8][6] = 0.0F;
                    new float[8][7] = this.sh;
                    arg0.snapPiece(new float[8], this.sw, this.sh, (1.0F - Anim.ease(var2)));
                    var6 = this.themeOx;
                    var7 = this.themeOy;
                    var8 = 0.0F;
                    new float[2][0] = 0.0F;
                    new float[2][1] = 0.0F;
                    new float[][4][0] = new float[2];
                    new float[2][0] = this.sw;
                    new float[2][1] = 0.0F;
                    new float[][4][1] = new float[2];
                    new float[2][0] = 0.0F;
                    new float[2][1] = this.sh;
                    new float[][4][2] = new float[2];
                    new float[2][0] = this.sw;
                    new float[2][1] = this.sh;
                    new float[][4][3] = new float[2];
                    var9 = new float[][4];
                    var10 = var9.length;
                    var11 = 0;
                    while (true) {
                        if (var11 >= var10) break;
                        var12 = var9[var11];
                        var8 = Math.max(var8, ((float) Math.hypot(((double) (var12[0] - var6)), ((double) (var12[1] - var7)))));
                        var11++;
                    }
                    var9 = Math.max(0.5F, (var3 * var8));
                    arg0.snapHole(var6, var7, var9, this.sw, this.sh, 1.0F);
                    var10 = (1.0F - Anim.ease(var2));
                    arg0.ring(var6, var7, var9, 26.0F, Theme.a(this.th.accent, (0.1F * var10)));
                    arg0.ring(var6, var7, var9, 2.5F, Theme.a(Theme.mix(this.th.accent, -1, 0.35F), (0.95F * var10)));
                    arg0.ring(var6, var7, Math.max(0.5F, (var9 * 0.9F)), 1.2F, Theme.a(this.th.accent2, (0.5F * var10)));
                    var11 = 0;
                    while (true) {
                        if (var11 >= 36) break;
                        var12 = ((((double) var11) * 0.17453292519943295) + (((double) hashf(var11)) * 0.3));
                        float var14 = (var9 + ((hashf((var11 + 50)) - 0.5F) * 18.0F));
                        float var15 = (1.2F + (2.0F * hashf((var11 + 99))));
                        if ((var11 % 2) == 0) {
                        } else {
                        }
                        (var6 + (((float) Math.cos(var12)) * var14)).circle((var7 + (((float) Math.sin(var12)) * var14)), var15, this.th.accent, Theme.a(this.th.accent2, (var10 * (0.5F + (0.5F * hashf((var11 + 7)))))));
                        var11++;
                    }
                    if (var2 < 0.25F) {
                        arg0.glow(var6, var7, (30.0F + (120.0F * var2)), Theme.a(this.th.accent, (0.6F * (1.0F - (var2 / 0.25F)))));
                    }
                    return;
                }
            }
        }
    }

    private static float hashf(int arg0) {
        double var1 = (Math.sin(((((double) arg0) * 127.1) + 311.7)) * 43758.5453);
        return ((float) (var1 - Math.floor(var1)));
    }

    private float closeDur() {
        int var2 = 0;
        String var1 = this.closeStyle;
        var2 = -1;
        switch (var1.hashCode()) {
            case -1586642796 -> {}
            case -704368912 -> {}
            case -576022087 -> {}
            case 67013 -> {}
            case 79973746 -> {}
            default -> {}
        }
        if (var1.equals("Shatter")) {
            var2 = 0;
            if (var1.equals("Crumble")) {
                var2 = 1;
                if (var1.equals("Slice")) {
                    var2 = 2;
                    if (var1.equals("Implode")) {
                        var2 = 3;
                        if (var1.equals("CRT")) {
                            var2 = 4;
                        }
                        switch (var2) {
                            case 0 -> {}
                            case 1 -> {}
                            case 2 -> {}
                            case 3 -> {}
                            case 4 -> {}
                            default -> {}
                        }
                        return 0.28F;
                    }
                }
            }
        }
    }

    float closeT() {
        if (this.closeStart < 0.0F) {
            return 0.0F;
        }
        return Math.max(0.0F, (((this.time - this.closeStart) * this.an_speed()) / this.closeDur()));
    }

    private void buildShards() {
        int var4 = 0;
        int var5 = 0;
        int var6 = 0;
        float var7 = 0.0F;
        float var8 = 0.0F;
        int var9 = 0;
        int var10 = 0;
        float var11 = 0.0F;
        float var12 = 0.0F;
        this.layoutWindow(1.0F);
        Random var1 = new Random((((long) (this.time * 1000.0F)) ^ 24301L));
        ArrayList var2 = new ArrayList();
        String var3 = this.closeStyle;
        var4 = -1;
        switch (var3.hashCode()) {
            case -1586642796 -> {}
            case -704368912 -> {}
            case -576022087 -> {}
            case 79973746 -> {}
            default -> {}
        }
        if (var3.equals("Shatter")) {
            var4 = 0;
            if (var3.equals("Crumble")) {
                var4 = 1;
                if (var3.equals("Implode")) {
                    var4 = 2;
                    if (var3.equals("Slice")) {
                        var4 = 3;
                    }
                    switch (var4) {
                        case 0 -> {}
                        case 1 -> {}
                        case 2 -> {}
                        case 3 -> {}
                        default -> {}
                    }
                    if ("Shatter".equals(this.closeStyle)) {
                    } else {
                        if ("Crumble".equals(this.closeStyle)) {
                        } else {
                        }
                        var5 = 8;
                        if ("Shatter".equals(this.closeStyle)) {
                        } else {
                            if ("Crumble".equals(this.closeStyle)) {
                            } else {
                            }
                            var6 = 5;
                            if ("Crumble".equals(this.closeStyle)) {
                            } else {
                            }
                            var7 = 0.38F;
                            var8 = new float[(var5 + 1)][(var6 + 1)][];
                            var9 = 0;
                            while (true) {
                                if (var9 > var5) break;
                                var10 = 0;
                                while (true) {
                                    if (var10 > var6) break;
                                    if (var9 > 0) {
                                        if (var9 < var5) {
                                        } else {
                                        }
                                        var11 = 0.0F;
                                        if (var10 > 0) {
                                            if (var10 < var6) {
                                            } else {
                                            }
                                            var12 = 0.0F;
                                            new float[2][0] = (this.wx + ((this.ww * (((float) var9) + var11)) / ((float) var5)));
                                            new float[2][1] = (this.wy + ((this.wh * (((float) var10) + var12)) / ((float) var6)));
                                            var8[var9][var10] = new float[2];
                                            var10++;
                                        }
                                    }
                                }
                                var9++;
                            }
                            var9 = 0;
                            while (true) {
                                if (var9 >= var5) break;
                                var10 = 0;
                                while (true) {
                                    if (var10 >= var6) break;
                                    var11 = var8[var9][var10];
                                    var12 = var8[(var9 + 1)][var10];
                                    Object var13 = var8[(var9 + 1)][(var10 + 1)];
                                    Object var14 = var8[var9][(var10 + 1)];
                                    if ("Shatter".equals(this.closeStyle)) {
                                        if (var1.nextBoolean()) {
                                            new float[][3][0] = var11;
                                            new float[][3][1] = var12;
                                            new float[][3][2] = var13;
                                            var2.add(piece(var1, var10, var6, new float[][3]));
                                            new float[][3][0] = var11;
                                            new float[][3][1] = var13;
                                            new float[][3][2] = var14;
                                            var2.add(piece(var1, var10, var6, new float[][3]));
                                        } else {
                                            new float[][3][0] = var11;
                                            new float[][3][1] = var12;
                                            new float[][3][2] = var14;
                                            var2.add(piece(var1, var10, var6, new float[][3]));
                                            new float[][3][0] = var12;
                                            new float[][3][1] = var13;
                                            new float[][3][2] = var14;
                                            var2.add(piece(var1, var10, var6, new float[][3]));
                                        }
                                    } else {
                                        new float[][4][0] = var11;
                                        new float[][4][1] = var12;
                                        new float[][4][2] = var13;
                                        new float[][4][3] = var14;
                                        var2.add(piece(var1, var10, var6, new float[][4]));
                                    }
                                    var10++;
                                }
                                var9++;
                            }
                            var5 = 14;
                            var6 = 0;
                            while (true) {
                                if (var6 >= var5) break;
                                var7 = (this.wy + ((this.wh * ((float) var6)) / ((float) var5)));
                                var8 = (this.wy + ((this.wh * ((float) (var6 + 1))) / ((float) var5)));
                                new float[2][0] = (this.wx - 60.0F);
                                new float[2][1] = var7;
                                new float[][4][0] = new float[2];
                                new float[2][0] = ((this.wx + this.ww) + 60.0F);
                                new float[2][1] = var7;
                                new float[][4][1] = new float[2];
                                new float[2][0] = ((this.wx + this.ww) + 60.0F);
                                new float[2][1] = var8;
                                new float[][4][2] = new float[2];
                                new float[2][0] = (this.wx - 60.0F);
                                new float[2][1] = var8;
                                new float[][4][3] = new float[2];
                                var2.add(piece(var1, var6, var5, new float[][4]));
                                var6++;
                            }
                            var5 = 70.0F;
                            new float[2][0] = (this.wx - var5);
                            new float[2][1] = (this.wy - var5);
                            new float[][4][0] = new float[2];
                            new float[2][0] = ((this.wx + this.ww) + var5);
                            new float[2][1] = (this.wy - var5);
                            new float[][4][1] = new float[2];
                            new float[2][0] = ((this.wx + this.ww) + var5);
                            new float[2][1] = ((this.wy + this.wh) + var5);
                            new float[][4][2] = new float[2];
                            new float[2][0] = (this.wx - var5);
                            new float[2][1] = ((this.wy + this.wh) + var5);
                            new float[][4][3] = new float[2];
                            var2.add(piece(var1, 0, 1, new float[][4]));
                            this.shards = ((float[][]) var2.toArray(new float[][0]));
                            return;
                        }
                    }
                }
            }
        }
    }

    private static float[] piece(Random arg0, int arg1, int arg2, float[][] arg3) {
        float var6 = 0.0F;
        float var7 = 0.0F;
        int var8 = 0;
        int var4 = arg3.length;
        float[] var5 = new float[((1 + (2 * var4)) + 6)];
        var5[0] = ((float) var4);
        var6 = 0.0F;
        var7 = 0.0F;
        var8 = 0;
        while (true) {
            if (var8 >= var4) break;
            var5[(1 + (2 * var8))] = arg3[var8][0];
            var5[(2 + (2 * var8))] = arg3[var8][1];
            var6 = (var6 + arg3[var8][0]);
            var7 = (var7 + arg3[var8][1]);
            var8++;
        }
        var8 = (1 + (2 * var4));
        var5[var8] = (var6 / ((float) var4));
        var5[(var8 + 1)] = (var7 / ((float) var4));
        var5[(var8 + 2)] = arg0.nextFloat();
        var5[(var8 + 3)] = arg0.nextFloat();
        var5[(var8 + 4)] = arg0.nextFloat();
        if (arg2 <= 1) {
        } else {
        }
        (var8 + 5)[0.0F] = (((float) arg1) / ((float) (arg2 - 1)));
        return var5;
    }

    private static float[] poly(float[] arg0) {
        int var1 = ((int) arg0[0]);
        float[] var2 = new float[(2 * var1)];
        System.arraycopy(arg0, 1, var2, 0, (2 * var1));
        return var2;
    }

    private void drawClose(Canvas arg0, float arg1) {
        int var7 = 0;
        float var8 = 0.0F;
        float var9 = 0.0F;
        float var10 = 0.0F;
        float[][] var11 = null;
        int var12 = 0;
        int var13 = 0;
        float var14 = 0.0F;
        int var15 = 0;
        float var16 = 0.0F;
        float var17 = 0.0F;
        float var18 = 0.0F;
        float var19 = 0.0F;
        float var20 = 0.0F;
        float var21 = 0.0F;
        float var22 = 0.0F;
        float var23 = 0.0F;
        float var24 = 0.0F;
        float var3 = this.closeDur();
        float var4 = (arg1 * var3);
        this.layoutWindow(1.0F);
        if (this.shards != null) {
            if (this.haveSnapshot) {
                if (!arg0.hasSnapshot()) {
                }
            }
            arg0.save();
            arg0.alpha((1.0F - Anim.ease(arg1)));
            arg0.translate(this.winCx, this.winCy);
            arg0.scale((1.0F - (0.06F * Anim.ease(arg1))));
            arg0.translate(-(this.winCx), -(this.winCy));
            this.drawWindow(arg0);
            arg0.restore();
            return;
            float var5 = (1.0F - Anim.ease(((arg1 - 0.6F) / 0.4F)));
            String var6 = this.closeStyle;
            var7 = -1;
            switch (var6.hashCode()) {
                case -1586642796 -> {}
                case -704368912 -> {}
                case -576022087 -> {}
                case 67013 -> {}
                case 79973746 -> {}
                default -> {}
            }
            if (var6.equals("Shatter")) {
                var7 = 0;
                if (var6.equals("Crumble")) {
                    var7 = 1;
                    if (var6.equals("Slice")) {
                        var7 = 2;
                        if (var6.equals("Implode")) {
                            var7 = 3;
                            if (var6.equals("CRT")) {
                                var7 = 4;
                            }
                            switch (var7) {
                                case 0 -> {}
                                case 1 -> {}
                                case 2 -> {}
                                case 3 -> {}
                                case 4 -> {}
                                default -> {}
                            }
                            if (this.mx > this.wx) {
                                if (this.mx < (this.wx + this.ww)) {
                                    if (this.my > this.wy) {
                                        if (this.my < (this.wy + this.wh)) {
                                        } else {
                                        }
                                        var8 = this.winCx;
                                        if (this.mx > this.wx) {
                                            if (this.mx < (this.wx + this.ww)) {
                                                if (this.my > this.wy) {
                                                    if (this.my < (this.wy + this.wh)) {
                                                    } else {
                                                    }
                                                    var9 = this.winCy;
                                                    var10 = ((float) Math.hypot(((double) this.ww), ((double) this.wh)));
                                                    arg0.shadow(this.wx, (this.wy + 18.0F), this.ww, this.wh, this.winR(), 60.0F, Theme.a(-16777216, (0.5F * (1.0F - Anim.ease((arg1 * 3.0F))))));
                                                    var11 = this.shards;
                                                    var12 = var11.length;
                                                    var13 = 0;
                                                    while (true) {
                                                        if (var13 >= var12) break;
                                                        var14 = var11[var13];
                                                        var15 = (1 + (2 * ((int) var14[0])));
                                                        var16 = var14[var15];
                                                        var17 = var14[(var15 + 1)];
                                                        var18 = var14[(var15 + 2)];
                                                        var19 = var14[(var15 + 3)];
                                                        var20 = var14[(var15 + 4)];
                                                        var21 = (var16 - var8);
                                                        var22 = (var17 - var9);
                                                        var23 = ((float) Math.hypot(((double) var21), ((double) var22)));
                                                        if (var23 < 0.001F) {
                                                            var21 = (var18 - 0.5F);
                                                            var22 = (var19 - 0.5F);
                                                            var23 = (((float) Math.hypot(((double) var21), ((double) var22))) + 0.001F);
                                                        }
                                                        var24 = (var23 / var10);
                                                        float var25 = Math.max(0.0F, ((var4 - 0.07F) - (0.16F * var24)));
                                                        float var26 = ((220.0F + (380.0F * var18)) * (1.25F - var24));
                                                        float var27 = (((var21 / var23) * var26) * var25);
                                                        float var28 = ((((var22 / var23) * var26) * var25) + ((850.0F * var25) * var25));
                                                        float var29 = (((var20 - 0.5F) * 8.0F) * var25);
                                                        float var30 = (1.0F + (((var19 - 0.4F) * 0.35F) * Math.min(1.0F, (var25 * 2.0F))));
                                                        arg0.save();
                                                        arg0.alpha(var5);
                                                        arg0.translate((var16 + var27), (var17 + var28));
                                                        arg0.rotate(var29);
                                                        arg0.scale(var30);
                                                        arg0.translate(-(var16), -(var17));
                                                        arg0.snapPiece(poly(var14), this.sw, this.sh, 1.0F);
                                                        if (var4 < 0.35F) {
                                                            arg0.strokePoly(poly(var14), 1.2F, Theme.a(Theme.mix(this.th.accent, -1, 0.6F), ((0.9F * (1.0F - (var4 / 0.35F))) * Math.min(1.0F, (var4 * 30.0F)))));
                                                        }
                                                        arg0.restore();
                                                        var13++;
                                                    }
                                                    if (var4 < 0.4F) {
                                                        arg0.glow(var8, var9, (40.0F + (500.0F * var4)), Theme.a(Theme.mix(this.th.accent, -1, 0.5F), (0.55F * (1.0F - (var4 / 0.4F)))));
                                                    }
                                                    var8 = this.shards;
                                                    var9 = var8.length;
                                                    var10 = 0;
                                                    while (true) {
                                                        if (var10 >= var9) break;
                                                        var11 = var8[var10];
                                                        var12 = (1 + (2 * ((int) var11[0])));
                                                        var13 = var11[var12];
                                                        var14 = var11[(var12 + 1)];
                                                        var15 = var11[(var12 + 2)];
                                                        var16 = var11[(var12 + 3)];
                                                        var17 = var11[(var12 + 4)];
                                                        var18 = var11[(var12 + 5)];
                                                        var19 = (((1.0F - var18) * 0.42F) + (var15 * 0.14F));
                                                        var20 = Math.max(0.0F, (var4 - var19));
                                                        if (var20 == 0.0F) {
                                                            if (var4 > (var19 - 0.12F)) {
                                                            } else {
                                                            }
                                                            var21 = 0.0F;
                                                            var22 = ((((var16 - 0.5F) * 70.0F) * var20) + var21);
                                                            var23 = ((1200.0F * var20) * var20);
                                                            var24 = (((var17 - 0.5F) * 3.2F) * var20);
                                                            arg0.save();
                                                            arg0.alpha(Math.min(var5, (1.0F - Anim.ease(((var20 - 0.35F) / 0.35F)))));
                                                            arg0.translate((var13 + var22), (var14 + var23));
                                                            arg0.rotate(var24);
                                                            arg0.translate(-(var13), -(var14));
                                                            arg0.snapPiece(poly(var11), this.sw, this.sh, 1.0F);
                                                            arg0.restore();
                                                            var10++;
                                                        }
                                                    }
                                                    var8 = 0;
                                                    var9 = this.shards;
                                                    var10 = var9.length;
                                                    var11 = 0;
                                                    while (true) {
                                                        if (var11 >= var10) break;
                                                        var12 = var9[var11];
                                                        var13 = (1 + (2 * ((int) var12[0])));
                                                        var8++;
                                                        if ((var8 % 2) == 0) {
                                                        } else {
                                                        }
                                                        var14 = 1.0F;
                                                        var15 = Math.max(0.0F, (var4 - (var12[(var13 + 5)] * 0.16F)));
                                                        var16 = (var14 * (((2600.0F * var15) * var15) + (40.0F * var15)));
                                                        arg0.save();
                                                        arg0.alpha((1.0F - Anim.ease(((arg1 * 1.1F) - 0.1F))));
                                                        arg0.translate(var16, 0.0F);
                                                        arg0.snapPiece(poly(var12), this.sw, this.sh, 1.0F);
                                                        arg0.restore();
                                                        var11++;
                                                    }
                                                    if (arg1 < 0.3F) {
                                                        var9 = 1;
                                                        while (true) {
                                                            if (var9 >= 14) break;
                                                            var10 = (this.wy + ((this.wh * ((float) var9)) / 14.0F));
                                                            arg0.line(this.wx, var10, (this.wx + this.ww), var10, 1.2F, Theme.a(this.th.accent, (0.7F * (1.0F - (arg1 / 0.3F)))));
                                                            var9++;
                                                        }
                                                        var8 = 0.0F;
                                                        var9 = this.shards;
                                                        var10 = var9.length;
                                                        var11 = 0;
                                                        while (true) {
                                                            if (var11 >= var10) break;
                                                            var12 = var9[var11];
                                                            var13 = (1 + (2 * ((int) var12[0])));
                                                            var14 = var12[var13];
                                                            var15 = var12[(var13 + 1)];
                                                            var16 = var12[(var13 + 2)];
                                                            var17 = var12[(var13 + 4)];
                                                            var18 = Anim.ease(Math.min(1.0F, Math.max(0.0F, ((arg1 - (var16 * 0.15F)) / 0.7F))));
                                                            var19 = (var18 * var18);
                                                            var20 = (var14 + ((this.winCx - var14) * var19));
                                                            var21 = (var15 + ((this.winCy - var15) * var19));
                                                            if (var16 < 0.5F) {
                                                            } else {
                                                            }
                                                            var22 = (-1 * ((float) 1));
                                                            arg0.save();
                                                            arg0.alpha((1.0F - Anim.ease(((var18 - 0.75F) / 0.25F))));
                                                            arg0.translate(var20, var21);
                                                            arg0.rotate(var22);
                                                            arg0.scale(Math.max(0.01F, (1.0F - var18)));
                                                            arg0.translate(-(var14), -(var15));
                                                            arg0.snapPiece(poly(var12), this.sw, this.sh, 1.0F);
                                                            arg0.restore();
                                                            var8 = (var8 + var18);
                                                            var11++;
                                                        }
                                                        var8 = (var8 / ((float) Math.max(1, this.shards.length)));
                                                        var9 = Math.max(0.0F, ((arg1 - 0.82F) / 0.18F));
                                                        arg0.glow(this.winCx, this.winCy, ((20.0F + (70.0F * var8)) + (260.0F * var9)), Theme.a(Theme.mix(this.th.accent, -1, 0.3F), ((0.7F * var8) * (1.0F - var9))));
                                                        if (var9 > 0.0F) {
                                                            arg0.ring(this.winCx, this.winCy, (10.0F + (300.0F * var9)), 2.0F, Theme.a(this.th.accent, (0.8F * (1.0F - var9))));
                                                        }
                                                        var8 = this.shards[0];
                                                        var9 = Anim.ease(Math.min(1.0F, (arg1 / 0.5F)));
                                                        var10 = Anim.ease(Math.max(0.0F, ((arg1 - 0.5F) / 0.32F)));
                                                        var11 = Math.max(0.006F, (1.0F - var9));
                                                        var12 = Math.max(0.004F, ((1.0F + (0.06F * var9)) * (1.0F - var10)));
                                                        arg0.save();
                                                        arg0.translate(this.winCx, this.winCy);
                                                        arg0.scale2(var12, var11);
                                                        arg0.translate(-(this.winCx), -(this.winCy));
                                                        if (arg1 < 0.82F) {
                                                            arg0.snapPiece(poly(var8), this.sw, this.sh, 1.0F);
                                                            arg0.rect(this.wx, this.wy, this.ww, this.wh, this.winR(), Theme.a(-1, (0.9F * var9)));
                                                        }
                                                        arg0.restore();
                                                        if (arg1 > 0.4F) {
                                                            if (arg1 < 0.85F) {
                                                                arg0.line((this.winCx - ((this.ww / 2.0F) * var12)), this.winCy, (this.winCx + ((this.ww / 2.0F) * var12)), this.winCy, 3.0F, Theme.a(-1, 0.9F));
                                                            }
                                                            var13 = Math.max(0.0F, ((arg1 - 0.7F) / 0.3F));
                                                            if (arg1 > 0.6F) {
                                                                arg0.glow(this.winCx, this.winCy, ((26.0F * (1.0F - var13)) + 6.0F), Theme.a(-1, (0.9F * (1.0F - var13))));
                                                            }
                                                            arg0.save();
                                                            arg0.alpha((1.0F - Anim.ease(arg1)));
                                                            arg0.translate(this.winCx, this.winCy);
                                                            arg0.scale((1.0F - (0.06F * Anim.ease(arg1))));
                                                            arg0.translate(-(this.winCx), -(this.winCy));
                                                            arg0.snapPiece(poly(this.shards[0]), this.sw, this.sh, 1.0F);
                                                            arg0.restore();
                                                            return;
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
                }
            }
        }
    }

    private float nameBar(Canvas arg0, float arg1, float arg2, float arg3, String arg4, String arg5, Consumer arg6) {
        float var8 = Math.min(320.0F, (arg3 - 200.0F));
        int var9 = (this.focused == this.nameField);
        if (var9 != 0) {
        } else {
        }
        float var10 = "nameField".to(1.0F, 0.0F, 14.0F);
        arg0.rect(arg1, arg2, var8, 34.0F, (this.ctlR() + 1.0F), Theme.a(Theme.mix(this.th.bg, this.th.text, 0.05F), 0.85F));
        arg0.stroke(arg1, arg2, var8, 34.0F, (this.ctlR() + 1.0F), 1.0F, Theme.mix(Theme.a(this.th.border, 0.9F), this.th.accent, var10));
        String var11 = this.nameField.text;
        if (var11.isEmpty()) {
            if (var9 == 0) {
            } else {
            }
            if (var11.isEmpty()) {
                if (var9 == 0) {
                } else {
                }
                12.5F.text((arg1 + 13.0F), (arg2 + 17.0F), 17, this.th.muted, this.th.text, arg4, var11);
                if (var9 != 0) {
                    this.caret(arg0, "inter", 12.5F, (arg1 + 13.0F), (arg2 + 17.0F), this.nameField);
                }
                Runnable var12 = () -> this.lambda$nameBar$0(arg6);
                this.hit(arg1, arg2, var8, 34.0F, () -> this.lambda$nameBar$1(), null);
                float var13 = (arg0.textWidth("inter-sb", 12.0F, arg5) + 32.0F);
                this.button(arg0, "namebar:" + (arg5), ((arg1 + var8) + 10.0F), (arg2 + 2.0F), var13, 30.0F, arg5, true, var12);
                if (var9 != 0) {
                    this.focusCommit = null;
                }
                return (arg2 + 50.0F);
            }
        }
    }

    private void button(Canvas arg0, String arg1, float arg2, float arg3, float arg4, float arg5, String arg6, boolean arg7, Runnable arg8) {
        if (this.in(arg2, arg3, arg4, arg5)) {
        } else {
        }
        float var10 = "btn:" + (arg1).to(1.0F, 0.0F, 16.0F);
        if (arg7) {
            arg0.shadow(arg2, (arg3 + 2.0F), arg4, arg5, (this.ctlR() + 1.0F), (12.0F + (6.0F * var10)), Theme.a(this.th.accent, (0.35F + (0.2F * var10))));
            arg0.gradient(arg2, arg3, arg4, arg5, (this.ctlR() + 1.0F), Theme.mix(this.th.accent, -1, (0.08F * var10)), this.th.accent2, true);
            arg0.text("inter-sb", 12.0F, (arg2 + (arg4 / 2.0F)), (arg3 + (arg5 / 2.0F)), 18, Theme.onColor(this.th.accent), arg6);
        } else {
            arg0.rect(arg2, arg3, arg4, arg5, (this.ctlR() + 1.0F), Theme.a(Theme.mix(this.th.bg, this.th.text, (0.06F + (0.05F * var10))), 0.85F));
            arg0.stroke(arg2, arg3, arg4, arg5, (this.ctlR() + 1.0F), 1.0F, Theme.mix(Theme.a(this.th.border, 0.9F), this.th.accent, var10));
            arg0.text("inter-med", 12.0F, (arg2 + (arg4 / 2.0F)), (arg3 + (arg5 / 2.0F)), 18, this.th.text, arg6);
        }
        this.hit(arg2, arg3, arg4, arg5, arg8, null);
    }

    private float listRow(Canvas arg0, String arg1, float arg2, float arg3, float arg4, int arg5, float arg6, String arg7, String arg8, String[] arg9, ClickGui.IntAction arg10, boolean arg11) {
        float var16 = 0.0F;
        float var17 = 0.0F;
        float var14 = Anim.easeOutCubic(((arg6 - (0.025F * ((float) Math.min(arg5, 14)))) / 0.32F));
        if (this.in(arg2, arg3, arg4, 52.0F)) {
        } else {
        }
        float var15 = "lr:" + (arg1).to(1.0F, 0.0F, 14.0F);
        arg0.save();
        arg0.alpha(var14);
        arg0.translate(((1.0F - var14) * 16.0F), 0.0F);
        if (arg11) {
            arg0.shadow(arg2, arg3, arg4, 52.0F, this.gnum("Card Rounding", 14.0), 16.0F, Theme.a(this.th.accent, 0.2F));
        }
        arg0.rect(arg2, arg3, arg4, 52.0F, this.gnum("Card Rounding", 14.0), Theme.a(Theme.mix(this.th.bg, this.th.text, (0.035F + (0.025F * var15))), 0.62F));
        if (arg11) {
            arg0.gradient(arg2, arg3, (arg4 * 0.5F), 52.0F, this.gnum("Card Rounding", 14.0), Theme.a(this.th.accent, 0.16F), Theme.a(this.th.accent, 0.0F), true);
        }
        if (arg11) {
        } else {
        }
        arg2.stroke(arg3, arg4, 52.0F, this.gnum("Card Rounding", 14.0), 1.0F, Theme.a(this.th.accent, 0.55F), Theme.a(this.th.border, (0.55F + (0.2F * var15))));
        arg0.text("inter-sb", 13.5F, (arg2 + 18.0F), (arg3 + (52.0F / 2.0F)), 17, this.th.text, arg7);
        if (arg8 != null) {
            var16 = (arg0.textWidth("inter-sb", 9.5F, arg8) + 14.0F);
            var17 = ((arg2 + 26.0F) + arg0.textWidth("inter-sb", 13.5F, arg7));
            arg0.rect(var17, ((arg3 + (52.0F / 2.0F)) - 9.0F), var16, 18.0F, 9.0F, Theme.a(this.th.accent, 0.2F));
            arg0.text("inter-sb", 9.5F, (var17 + (var16 / 2.0F)), (arg3 + (52.0F / 2.0F)), 18, this.th.accent, arg8);
        }
        var16 = ((arg2 + arg4) - 14.0F);
        var17 = (arg9.length - 1);
        while (true) {
            if (var17 < 0) break;
            String var18 = arg9[var17];
            if (!var18.equals("Delete")) {
                if (var18.equals("Remove")) {
                }
            }
            int var19 = 0;
            if (var19 != 0) {
                if (arg1.startsWith("srv:")) {
                } else {
                    if (arg1.startsWith("bind:")) {
                    } else {
                    }
                }
            } else {
                if (var18.equals("Overwrite")) {
                    if (arg1.startsWith("bind:")) {
                    } else {
                    }
                } else {
                }
                if ("".equals(this.confirmId)) {
                    int var21 = ((this.time - this.confirmAt) < 2.5F);
                    if (var21 != 0) {
                    } else {
                    }
                    String var22 = var18;
                    float var23 = (arg0.textWidth("inter-med", 11.5F, var22) + 24.0F);
                    var16 = (var16 - var23);
                    if (this.in(var16, ((arg3 + (52.0F / 2.0F)) - 14.0F), var23, 28.0F)) {
                    } else {
                    }
                    float var24 = "lrb:" + (arg1) + (var18).to(1.0F, 0.0F, 16.0F);
                    if (var19 != 0) {
                    } else {
                        if (var17 == 0) {
                        } else {
                        }
                        int var25 = this.th.text;
                        if (var21 != 0) {
                        } else {
                        }
                        var16.rect(((arg3 + (52.0F / 2.0F)) - 14.0F), var23, 28.0F, this.ctlR(), var25, Theme.a(0.3F, (0.08F + (0.12F * var24))));
                        arg0.stroke(var16, ((arg3 + (52.0F / 2.0F)) - 14.0F), var23, 28.0F, this.ctlR(), 1.0F, Theme.a(var25, (0.25F + (0.4F * var24))));
                        if (var19 == 0) {
                            if (var17 == 0) {
                            }
                        }
                        "inter-med".text(11.5F, (var16 + (var23 / 2.0F)), (arg3 + (52.0F / 2.0F)), 18, Theme.mix(var25, this.th.text, 0.25F), this.th.text, var22);
                        int var26 = var17;
                        this.hit(var16, ((arg3 + (52.0F / 2.0F)) - 14.0F), var23, 28.0F, () -> ClickGui.lambda$listRow$0(arg10, var26), null);
                        var16 = (var16 - 8.0F);
                        var17--;
                    }
                }
            }
        }
        arg0.restore();
        return ((arg3 + 52.0F) + 10.0F);
    }

    private float empty(Canvas arg0, float arg1, float arg2, float arg3, String arg4) {
        arg0.text("inter-med", 12.5F, (arg1 + (arg3 / 2.0F)), (arg2 + 40.0F), 18, this.th.muted, arg4);
        return (arg2 + 90.0F);
    }

    private void confirm(String arg0, Runnable arg1) {
        if (arg0.equals(this.confirmId)) {
            if ((this.time - this.confirmAt) < 2.5F) {
                this.confirmId = null;
                arg1.run();
            } else {
                this.confirmId = arg0;
                this.confirmAt = this.time;
            }
            return;
        }
    }

    void toast(String arg0) {
        this.toasts.add(new ClickGui.Toast(arg0, this.time));
        if (this.toasts.size() > 4) {
            this.toasts.remove(0);
        }
    }

    private void drawToasts(Canvas arg0) {
        float var2 = 0.0F;
        this.toasts.removeIf((p0) -> this.lambda$drawToasts$0(p0));
        var2 = (this.sh - 40.0F);
        int var3 = (this.toasts.size() - 1);
        while (true) {
            if (var3 < 0) break;
            ClickGui.Toast var4 = ((ClickGui.Toast) this.toasts.get(var3));
            float var5 = (this.time - var4.at);
            float var6 = Anim.easeOutBack(Math.min(1.0F, (var5 / 0.3F)));
            float var7 = (1.0F - Anim.ease(((var5 - 2.2F) / 0.4F)));
            float var8 = Math.min(var6, var7);
            float var9 = (arg0.textWidth("inter-med", 12.5F, var4.text) + 46.0F);
            float var10 = ((this.sw / 2.0F) - (var9 / 2.0F));
            float var11 = (var2 + ((1.0F - var6) * 20.0F));
            arg0.save();
            arg0.alpha(Math.max(0.0F, var8));
            arg0.shadow(var10, var11, var9, 32.0F, 16.0F, 18.0F, Theme.a(-16777216, 0.5F));
            arg0.glass(var10, var11, var9, 32.0F, 16.0F, 1.0F);
            arg0.rect(var10, var11, var9, 32.0F, 16.0F, Theme.a(this.th.surface, 0.9F));
            arg0.stroke(var10, var11, var9, 32.0F, 16.0F, 1.0F, Theme.a(this.th.accent, 0.5F));
            arg0.glow((var10 + 18.0F), (var11 + 16.0F), 12.0F, Theme.a(this.th.accent, 0.6F));
            arg0.circle((var10 + 18.0F), (var11 + 16.0F), 4.0F, this.th.accent);
            arg0.text("inter-med", 12.5F, (var10 + 30.0F), (var11 + 16.0F), 17, this.th.text, var4.text);
            arg0.restore();
            var2 = (var2 - (40.0F * var8));
            var3--;
        }
    }

    private void hoverTip(String arg0, String arg1) {
        if (!arg0.equals(this.tipKey)) {
            this.tipKey = arg0;
            this.hoverSince = this.time;
        }
        this.tipText = arg1;
    }

    private void hoverSinceReset(String arg0) {
        if (arg0.equals(this.tipKey)) {
            this.tipKey = null;
        }
    }

    private void drawTooltip(Canvas arg0) {
        float var7 = 0.0F;
        Iterator var8 = null;
        String var9 = null;
        if (this.tipKey != null) {
            if (this.tipText != null) {
                if (this.pop != null) {
                }
            }
            this.tipKey = null;
            return;
            float var2 = (this.time - this.hoverSince);
            String var3 = this.tipText;
            this.tipText = null;
            if (var2 < 0.55F) {
                return;
            }
            float var4 = Anim.ease(((var2 - 0.55F) / 0.2F));
            List var6 = this.wrap(arg0, "inter", 11.5F, var3, 300.0F);
            var7 = 0.0F;
            var8 = var6.iterator();
            while (true) {
                if (!var8.hasNext()) break;
                var9 = ((String) var8.next());
                var7 = Math.max(var7, arg0.textWidth("inter", 11.5F, var9));
            }
            var7 = (var7 + 22.0F);
            var8 = ((float) ((var6.size() * 16) + 14));
            var9 = Math.min((this.mx + 14.0F), ((this.sw - var7) - 8.0F));
            float var10 = Math.min((this.my + 18.0F), ((this.sh - var8) - 8.0F));
            arg0.save();
            arg0.alpha(var4);
            arg0.shadow(var9, var10, var7, var8, 9.0F, 14.0F, Theme.a(-16777216, 0.5F));
            arg0.rect(var9, var10, var7, var8, 9.0F, Theme.a(Theme.mix(this.th.surface, this.th.bg, 0.4F), 0.97F));
            arg0.stroke(var9, var10, var7, var8, 9.0F, 1.0F, Theme.a(this.th.border, 0.9F));
            int var11 = 0;
            while (true) {
                if (var11 >= var6.size()) break;
                arg0.text("inter", 11.5F, (var9 + 11.0F), ((var10 + 15.0F) + ((float) (var11 * 16))), 17, this.th.text, ((String) var6.get(var11)));
                var11++;
            }
            arg0.restore();
            return;
        }
    }

    private void focus(TextField arg0, Runnable arg1) {
        this.focused = arg0;
        this.focusCommit = arg1;
        if (arg0 != this.search) {
            if (arg0 != this.nameField) {
                if (arg0 != this.addField) {
                    if (this.pop != null) {
                        if (arg0 == this.pop.filter) {
                            if (!this.pop.color) {
                            }
                        }
                    }
                }
                this.focusId = null;
                return;
            }
        }
    }

    private void applyListen(String arg0) {
        if (this.listenSetting != null) {
            this.set(this.listenModule, this.listenSetting, arg0);
        } else {
            if (this.listenModule != null) {
                String var2 = this.store.bindMode(this.listenModule);
                if ("NONE".equals(arg0)) {
                } else {
                }
                if (var2 == null) {
                } else {
                }
                null.setBind(arg0, "toggle", var2);
                if ("NONE".equals(arg0)) {
                } else {
                }
                (this.listenModule) + " unbound".toast((this.listenModule) + " \u2192 " + (arg0));
            }
            this.listenModule = null;
            this.listenSetting = null;
            return;
        }
    }

    private void caret(Canvas arg0, String arg1, float arg2, float arg3, float arg4, TextField arg5) {
        if ((((int) (this.time * 2.2F)) % 2) == 1) {
            return;
        }
        float var7 = (arg3 + arg0.textWidth(arg1, arg2, arg5.text.substring(0, Math.min(arg5.caret, arg5.text.length()))));
        if (arg5.allSelected) {
            if (!arg5.text.isEmpty()) {
                float var8 = arg0.textWidth(arg1, arg2, arg5.text);
                arg0.rect((arg3 - 1.0F), (arg4 - (arg2 * 0.7F)), (var8 + 2.0F), (arg2 * 1.4F), 3.0F, Theme.a(this.th.accent, 0.3F));
                return;
            }
            arg0.rect(var7, (arg4 - (arg2 * 0.62F)), 1.4F, (arg2 * 1.24F), 0.7F, this.th.accent);
            return;
        }
    }

    private void hit(float arg0, float arg1, float arg2, float arg3, Runnable arg4, Runnable arg5) {
        this.hit(arg0, arg1, arg2, arg3, arg4, arg5, null);
    }

    private void hit(float arg0, float arg1, float arg2, float arg3, Runnable arg4, Runnable arg5, Consumer arg6) {
        this.hits.add(new ClickGui.Hit(arg0, arg1, arg2, arg3, this.clipX, this.clipY, this.clipW, this.clipH, this.layer, arg4, arg5, arg6));
    }

    private ClickGui.Hit hitAt(float arg0, float arg1) {
        ClickGui.Hit var3 = null;
        var3 = null;
        Iterator var4 = this.hits.iterator();
        while (true) {
            if (!var4.hasNext()) break;
            ClickGui.Hit var5 = ((ClickGui.Hit) var4.next());
            if (var5.contains(arg0, arg1)) {
                if (var3 != null) {
                    if (var5.layer >= var3.layer) {
                    }
                }
                var3 = var5;
            }
        }
        return var3;
    }

    private void clip(float arg0, float arg1, float arg2, float arg3) {
        this.clipX = arg0;
        this.clipY = arg1;
        this.clipW = arg2;
        this.clipH = arg3;
    }

    private void intersectClip(float arg0, float arg1, float arg2, float arg3) {
        float var5 = Math.max(this.clipX, arg0);
        float var6 = Math.max(this.clipY, arg1);
        float var7 = Math.min((this.clipX + this.clipW), (arg0 + arg2));
        float var8 = Math.min((this.clipY + this.clipH), (arg1 + arg3));
        this.clip(var5, var6, Math.max(0.0F, (var7 - var5)), Math.max(0.0F, (var8 - var6)));
    }

    private void noClip() {
        this.clip(-1000000.0F, -1000000.0F, 2000000.0F, 2000000.0F);
    }

    private boolean in(float arg0, float arg1, float arg2, float arg3) {
        if (this.layer == 0) {
            if (this.inPop(this.wmx, this.wmy)) {
                return false;
            }
            if (this.wmx >= arg0) {
                if (this.wmy >= arg1) {
                    if (this.wmx <= (arg0 + arg2)) {
                        if (this.wmy <= (arg1 + arg3)) {
                            if (this.wmx >= this.clipX) {
                                if (this.wmy >= this.clipY) {
                                    if (this.wmx <= (this.clipX + this.clipW)) {
                                        return (this.wmy <= (this.clipY + this.clipH));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private Object get(String arg0, String arg1) {
        if ("Click GUI".equals(arg0)) {
        } else {
        }
        Object var3 = this.store.value(arg0, arg1);
        if (var3 == null) {
            if ("Click GUI".equals(arg0)) {
                Iterator var4 = GUI_SETTINGS.iterator();
                while (true) {
                    if (!var4.hasNext()) break;
                    GuiData.Setting var5 = ((GuiData.Setting) var4.next());
                    if (var5.name.equals(arg1)) {
                        return var5.defaultValue();
                    }
                }
                return var3;
            }
        }
    }

    private void set(String arg0, String arg1, Object arg2) {
        if ("Click GUI".equals(arg0)) {
            this.store.setGui(arg1, arg2);
            if ("GUI".equals(arg1)) {
                if ("Web".equals(arg2)) {
                    this.toast("Right Shift now opens the web GUI");
                }
                if ("GUI".equals(arg1)) {
                    if ("ClickGUI".equals(arg2)) {
                        this.toast("Web GUI locked while the ClickGUI is in use");
                    }
                }
            }
        } else {
            this.store.setValue(arg0, arg1, arg2);
        }
    }

    private boolean visible(String arg0, GuiData.Setting arg1) {
        List var4 = null;
        List var5 = null;
        Object var6 = null;
        Map var3 = arg1.meta;
        if (var3 == null) {
            return true;
        }
        var6 = var3.get("showifOr");
        if ((var6 instanceof List)) {
            var4 = ((List) var6);
            if (var4.size() >= 2) {
                var6 = var4.get(1);
                if ((var6 instanceof List)) {
                    var5 = ((List) var6);
                    var6 = String.valueOf(this.curOrDefault(arg0, String.valueOf(var4.get(0))));
                    Iterator var7 = var5.iterator();
                    while (true) {
                        if (!var7.hasNext()) break;
                        Object var8 = var7.next();
                        if (var6.equals(jsStr(var8))) {
                            return true;
                        }
                    }
                    return false;
                }
                var5 = var3.get("showif2");
                if ((var5 instanceof List)) {
                    var4 = ((List) var5);
                    if (var4.size() >= 4) {
                        if (match(this.curOrDefault(arg0, String.valueOf(var4.get(0))), var4.get(1))) {
                            return (match(this.curOrDefault(arg0, String.valueOf(var4.get(2))), var4.get(3)));
                        }
                    }
                    var5 = var3.get("showif");
                    if ((var5 instanceof List)) {
                        var4 = ((List) var5);
                        if (var4.size() >= 2) {
                            return match(this.curOrDefault(arg0, String.valueOf(var4.get(0))), var4.get(1));
                        }
                        return true;
                    }
                }
            }
        }
    }

    private Object curOrDefault(String arg0, String arg1) {
        Object var3 = this.get(arg0, arg1);
        if (var3 != null) {
            return var3;
        }
        GuiData.Module var4 = ((GuiData.Module) this.data.modules.get(arg0));
        if (var4 != null) {
        } else {
            if ("Click GUI".equals(arg0)) {
            } else {
            }
            Iterator var6 = List.of().iterator();
            while (true) {
                if (!var6.hasNext()) break;
                GuiData.Setting var7 = ((GuiData.Setting) var6.next());
                if (var7.name.equals(arg1)) {
                    return var7.defaultValue();
                }
            }
            return null;
        }
    }

    private static boolean match(Object arg0, Object arg1) {
        if ((arg1 instanceof Boolean)) {
            Boolean var2 = ((Boolean) arg1);
            if ((arg0 instanceof Boolean)) {
                if (((Boolean) arg0) == var2) {
                } else {
                }
            } else {
            }
            return String.valueOf(var2).equals(String.valueOf(arg0));
        }
        return jsStr(arg1).equals(jsStr(arg0));
    }

    private static String jsStr(Object arg0) {
        if ((arg0 instanceof Double)) {
            Double var1 = ((Double) arg0);
            if (var1 == Math.rint(var1)) {
                if (!Double.isInfinite(var1)) {
                    return String.valueOf(var1);
                }
                return String.valueOf(arg0);
            }
        }
    }

    private int resolveColor(String arg0) {
        if ("theme".equals(arg0)) {
            return (this.th.accent & 16777215);
        }
        if ("theme2".equals(arg0)) {
            return (this.th.accent2 & 16777215);
        }
        return Theme.hex(arg0, 16777215);
    }

    static String display(String arg0) {
        if (arg0 == null) {
            return "";
        }
        if ("none".equals(arg0)) {
            return "None";
        }
        if ("default".equals(arg0)) {
            return "Default";
        }
        if (arg0.startsWith("custom_")) {
            return "Custom " + (arg0.substring(7, Math.min(arg0.length(), 13)));
        }
        return arg0;
    }

    static double snap(double arg0, double arg1, double arg2) {
        if (arg1 <= 0.0) {
            return arg0;
        }
        return (((double) Math.round((((((double) Math.round(((arg0 - arg2) / arg1))) * arg1) + arg2) * 1000000.0))) / 1000000.0);
    }

    static String fmt(double arg0, double arg1) {
        if (arg1 < 1.0) {
            if (arg1 <= 0.0) {
                if (arg0 == Math.rint(arg0)) {
                }
            }
        }
        return String.valueOf(Math.round(arg0));
        if (arg1 <= 0.0) {
        } else {
        }
        int var4 = Math.max(0, Math.min(4, ((int) Math.ceil((-(Math.log10(arg1)) - 1.0E-9)))));
        new Object[1][0] = arg0;
        return String.format(Locale.ROOT, "%." + (var4) + "f", new Object[1]);
    }

    static double num(Object arg0, double arg1) {
        Number var3 = null;
        if ((arg0 instanceof Number)) {
            var3 = ((Number) arg0);
            return var3;
        }
        if ((arg0 instanceof String)) {
            var3 = ((String) arg0);
            try {
            } catch (NumberFormatException ignored) {
            }
            return Double.parseDouble(var3);
        }
        return arg1;
    }

    static boolean bool(Object arg0, boolean arg1) {
        Boolean var2 = null;
        if ((arg0 instanceof Boolean)) {
            var2 = ((Boolean) arg0);
            return var2;
        }
        if ((arg0 instanceof String)) {
            var2 = ((String) arg0);
            return "true".equalsIgnoreCase(var2);
        }
        return arg1;
    }

    static String str(Object arg0, String arg1) {
        if (arg0 == null) {
        } else {
        }
        return jsStr(arg0);
    }

    static List list(Object arg0, Object arg1) {
        if (arg0 != null) {
        } else {
        }
        Object var2 = arg1;
        ArrayList var3 = new ArrayList();
        if ((var2 instanceof List)) {
            Iterator var5 = ((List) var2).iterator();
            while (true) {
                if (!var5.hasNext()) break;
                Object var6 = var5.next();
                if (var6 != null) {
                    var3.add(String.valueOf(var6));
                }
            }
            return var3;
        }
    }

    private String ellipsize(Canvas arg0, String arg1, float arg2, String arg3, float arg4) {
        int var6 = 0;
        int var7 = 0;
        if (arg3 == null) {
            return "";
        }
        if (arg4 <= 10.0F) {
            return "";
        }
        if (arg0.textWidth(arg1, arg2, arg3) <= arg4) {
            return arg3;
        }
        var6 = 0;
        var7 = arg3.length();
        while (true) {
            if (var6 >= var7) break;
            int var8 = (((var6 + var7) + 1) / 2);
            if (arg0.textWidth(arg1, arg2, (arg3.substring(0, var8)) + "\u2026") <= arg4) {
                var6 = var8;
            } else {
                var7 = (var8 - 1);
            }
        }
        return (arg3.substring(0, var6).trim()) + "\u2026";
    }

    private List wrap(Canvas arg0, String arg1, float arg2, String arg3, float arg4) {
        StringBuilder var7 = null;
        ArrayList var6 = new ArrayList();
        var7 = new StringBuilder();
        String[] var8 = arg3.split(" ");
        int var9 = var8.length;
        int var10 = 0;
        while (true) {
            if (var10 >= var9) break;
            String var11 = var8[var10];
            if (var7.length() == 0) {
            } else {
            }
            String var12 = (String.valueOf(var7)) + " " + (var11);
            if (arg0.textWidth(arg1, arg2, var12) > arg4) {
                if (var7.length() > 0) {
                    var6.add(var7.toString());
                    var7 = new StringBuilder(var11);
                } else {
                    var7 = new StringBuilder(var12);
                }
                if (var6.size() >= 8) {
                } else {
                    var10++;
                }
                if (var7.length() <= 0) break;
                if (var6.size() >= 9) break;
                var6.add(var7.toString());
                return var6;
            }
        }
    }

    static {
        new String[5][0] = "Themes";
        new String[5][1] = "Profiles";
        new String[5][2] = "Binds";
        new String[5][3] = "Servers";
        new String[5][4] = "GUI";
        SPECIAL = new String[5];
        GUI_SECTIONS = new LinkedHashMap();
        GUI_SETTINGS = new ArrayList();
        new String[4][0] = "Aurora";
        new String[4][1] = "Nebula";
        new String[4][2] = "Waves";
        new String[4][3] = "Solid";
        BG_ON = new String[4];
        new GuiData.Setting[6][0] = def("GUI", "select", List.of("Web", "ClickGUI"), "ClickGUI");
        new GuiData.Setting[6][1] = def("Scale", "range", List.of(0.7, 1.4, 1.0, 0.05), null);
        new GuiData.Setting[6][2] = def("Animation Speed", "range", List.of(0.5, 2.0, 1.0, 0.05), null);
        new GuiData.Setting[6][3] = def("Show Descriptions", "check", true, null);
        new GuiData.Setting[6][4] = def("Page Title", "check", true, null);
        new GuiData.Setting[6][5] = def("Smooth Text", "check", true, null);
        section("General", new GuiData.Setting[6]);
        new GuiData.Setting[10][0] = def("Window Width", "range", List.of(700.0, 1400.0, 1000.0, 10.0), null);
        new GuiData.Setting[10][1] = def("Window Height", "range", List.of(440.0, 900.0, 650.0, 10.0), null);
        new GuiData.Setting[10][2] = def("Rounding", "range", List.of(0.0, 28.0, 16.0, 1.0), null);
        new GuiData.Setting[10][3] = def("Sidebar Width", "range", List.of(170.0, 280.0, 210.0, 2.0), null);
        new GuiData.Setting[10][4] = def("Panel Opacity", "range", List.of(0.3, 1.0, 0.82, 0.01), null);
        new GuiData.Setting[10][5] = def("Border Width", "range", List.of(0.0, 3.0, 1.2, 0.1), null);
        new GuiData.Setting[10][6] = def("Border Opacity", "range", List.of(0.0, 1.0, 0.55, 0.05), null);
        new GuiData.Setting[10][7] = def("Shadow", "range", List.of(0.0, 1.5, 1.0, 0.05), null);
        new GuiData.Setting[10][8] = def("Accent Glow", "check", true, null);
        new GuiData.Setting[10][9] = def("Frosted Panels", "check", true, null);
        section("Window", new GuiData.Setting[10]);
        new GuiData.Setting[2][0] = def("Logo Tint", "select", List.of("Accent", "Accent 2", "Text", "Original"), "Accent");
        new GuiData.Setting[2][1] = def("Logo Glow", "check", true, null);
        section("Logo", new GuiData.Setting[2]);
        new GuiData.Setting[7][0] = def("Card Style", "select", List.of("Filled", "Outline", "Flat"), "Filled");
        new GuiData.Setting[7][1] = def("Card Rounding", "range", List.of(0.0, 24.0, 14.0, 1.0), null);
        new GuiData.Setting[7][2] = def("Card Spacing", "range", List.of(0.0, 24.0, 10.0, 1.0), null);
        new GuiData.Setting[7][3] = def("Card Opacity", "range", List.of(0.0, 1.0, 0.62, 0.01), null);
        new GuiData.Setting[7][4] = def("Enabled Style", "select", List.of("Glow", "Tint", "Text", "None"), "Glow");
        new GuiData.Setting[7][5] = def("Ripple", "check", true, null);
        new GuiData.Setting[7][6] = def("Hover Lift", "check", true, null);
        section("Modules", new GuiData.Setting[7]);
        new GuiData.Setting[3][0] = def("Control Rounding", "range", List.of(0.0, 14.0, 9.0, 1.0), null);
        new GuiData.Setting[3][1] = def("Switch Style", "select", List.of("Pill", "Square", "Minimal"), "Pill");
        new GuiData.Setting[3][2] = def("Slider Style", "select", List.of("Pill", "Minimal", "Classic"), "Pill");
        section("Controls", new GuiData.Setting[3]);
        new GuiData.Setting[4][0] = def("Background", "select", List.of("Aurora", "Nebula", "Waves", "Solid", "None"), "Aurora");
        new GuiData.Setting[4][1] = def("Blur", "range", List.of(0.0, 4.0, 3.0, 1.0), null);
        new GuiData.Setting[4][2] = def("Blur Area", "select", List.of("Everywhere", "Window Only"), "Everywhere");
        new GuiData.Setting[4][3] = def("Background Dim", "range", List.of(0.0, 0.9, 0.42, 0.01), null, Map.of("showifOr", List.of("Background", List.of(BG_ON))));
        section("Background", new GuiData.Setting[4]);
        new GuiData.Setting[4][0] = def("Open Animation", "select", List.of("Fade", "Pop", "Slide", "Zoom", "CRT"), "Fade");
        new GuiData.Setting[4][1] = def("Close Animation", "select", List.of("Fade", "Shatter", "Crumble", "Slice", "Implode", "CRT"), "Fade");
        new GuiData.Setting[4][2] = def("Theme Transition", "select", List.of("Fade", "Ripple", "Wipe", "None"), "Fade");
        new GuiData.Setting[4][3] = def("Page Transition", "select", List.of("Slide", "Fade", "None"), "Slide");
        section("Animations", new GuiData.Setting[4]);
    }

    public interface Ctl {
        float draw(float arg0, float arg1, float arg2);

    }

    public record Hit(float x, float y, float w, float h, float cx, float cy, float cw, float ch, int layer, Runnable click, Runnable right, Consumer wheel) {

        boolean contains(float arg0, float arg1) {
            if (arg0 >= this.x) {
                if (arg1 >= this.y) {
                    if (arg0 <= (this.x + this.w)) {
                        if (arg1 <= (this.y + this.h)) {
                            if (arg0 >= this.cx) {
                                if (arg1 >= this.cy) {
                                    if (arg0 <= (this.cx + this.cw)) {
                                        return (arg1 <= (this.cy + this.ch));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

    }

    public interface IntAction {
        void run(int arg0);

    }

    public final class Pop {
        final String id;
        final boolean color;
        float x;
        float y;
        float w;
        float h;
        List options;
        String current;
        Consumer pick;
        String module;
        String key;
        float scrollY;
        float scrollT;
        final float openedAt;
        final TextField filter;
        float[] hsv;

        Pop(ClickGui arg0, String arg1, boolean arg2) {
            this.this$0 = arg0;
            this.scrollY = 0.0F;
            this.scrollT = 0.0F;
            this.openedAt = this.this$0.time;
            this.filter = new TextField(24);
            this.id = arg1;
            this.color = arg2;
        }

    }

    public record Ripple(String key, float x, float y, float at) {

    }

    public record Toast(String text, float at) {

    }

}
