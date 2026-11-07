package need.clarity.modules.visual;

import need.clarity.keybind.KeybindHandler;
import need.clarity.media.MediaBridge;
import need.clarity.modules.ModuleBase;
import need.clarity.util.ThemeColors;
import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;

public class MusicPlayer extends ModuleBase {
    public static MusicPlayer INSTANCE;
    private boolean enabledState;
    private boolean enabledState1;
    private boolean enabledState2;

    public MusicPlayer() {
        super("Music Player", "Visual");
        INSTANCE = this;
    }

    public void onEnable() {
        MediaBridge.ensureStarted();
    }

    public void onDisable() {
        MediaBridge.stop();
        this.enabledState2 = false;
        this.enabledState1 = false;
        this.enabledState = false;
    }

    public void onTick() {
        MediaBridge.ensureStarted();
        Minecraft var1 = Minecraft.getInstance();
        if (var1 != null) {
            if (var1.player != null) {
                if (var1.screen != null) {
                }
            }
            this.enabledState2 = true;
            this.enabledState1 = true;
            this.enabledState = true;
            return;
            String var33 = this.getNextKey();
            boolean var15 = isEligible(var1, var33);
            String var35 = this.getPreviousKey();
            boolean var17 = isEligible(var1, var35);
            String var37 = this.getPlayPauseKey();
            boolean var19 = isEligible(var1, var37);
            if (var15) {
                if (!this.enabledState) {
                    MediaBridge.next();
                }
                if (var17) {
                    if (!this.enabledState1) {
                        MediaBridge.previous();
                    }
                    if (var19) {
                        if (!this.enabledState2) {
                            MediaBridge.playPause();
                        }
                        this.enabledState = var15;
                        this.enabledState1 = var17;
                        this.enabledState2 = var19;
                        return;
                    }
                }
            }
        }
    }

    private static boolean isEligible(Minecraft arg0, String arg1) {
        int var27 = 0;
        int var31 = 0;
        if (arg1 != null) {
            if (!arg1.isEmpty()) {
                if ("NONE".equalsIgnoreCase(arg1)) {
                }
            }
            return false;
            long var15 = arg0.getWindow().handle();
            int var18 = KeybindHandler.mouseButtonIndex(arg1);
            if (var18 >= 0) {
                if (GLFW.glfwGetMouseButton(var15, var18) == 1) {
                    var31 = 1;
                } else {
                    var31 = 0;
                }
                return var31;
            }
            int var21 = KeybindHandler.glfwKeyCode(arg1);
            if (var21 == -1) {
                return false;
            }
            if (GLFW.glfwGetKey(var15, var21) == 1) {
                var27 = 1;
            } else {
                var27 = 0;
            }
            return var27;
        }
    }

    public String getNextKey() {
        return ((String) this.getSetting("Next Key", "NONE"));
    }

    public String getPreviousKey() {
        return ((String) this.getSetting("Previous Key", "NONE"));
    }

    public String getPlayPauseKey() {
        return ((String) this.getSetting("Play/Pause Key", "NONE"));
    }

    public String getAnchor() {
        return ((String) this.getSetting("Anchor", "Top Left"));
    }

    public int getXOffset() {
        Integer var7 = 12;
        return ((Integer) this.getSetting("X Offset", var7));
    }

    public int getYOffset() {
        Integer var7 = 12;
        return ((Integer) this.getSetting("Y Offset", var7));
    }

    public float getScale() {
        Float var11 = 1.0F;
        float var10 = ((Float) this.getSetting("Scale", var11));
        float var7 = Math.min(2.0F, var10);
        return Math.max(0.5F, var7);
    }

    public float getOpacity() {
        Float var11 = 1.0F;
        float var10 = ((Float) this.getSetting("Opacity", var11));
        float var7 = Math.min(1.0F, var10);
        return Math.max(0.1F, var7);
    }

    public String getLayout() {
        return ((String) this.getSetting("Layout", "Card"));
    }

    public float getWidth() {
        Float var11 = 260.0F;
        float var10 = ((Float) this.getSetting("Width", var11));
        float var7 = Math.min(420.0F, var10);
        return Math.max(160.0F, var7);
    }

    public float getCornerRadius() {
        Float var9 = 10.0F;
        float var8 = ((Float) this.getSetting("Corner Radius", var9));
        return Math.max(0.0F, var8);
    }

    public boolean isShowArt() {
        Boolean var7 = true;
        return ((Boolean) this.getSetting("Show Art", var7));
    }

    public boolean isShowArtist() {
        Boolean var7 = true;
        return ((Boolean) this.getSetting("Show Artist", var7));
    }

    public boolean isShowProgressBar() {
        Boolean var7 = true;
        return ((Boolean) this.getSetting("Show Progress Bar", var7));
    }

    public boolean isShowTimes() {
        Boolean var7 = true;
        return ((Boolean) this.getSetting("Show Times", var7));
    }

    public String getTimeMode() {
        return ((String) this.getSetting("Time Mode", "Elapsed / Remaining"));
    }

    public boolean isScrollLongTitles() {
        Boolean var7 = true;
        return ((Boolean) this.getSetting("Scroll Long Titles", var7));
    }

    public boolean isShowSource() {
        Boolean var7 = false;
        return ((Boolean) this.getSetting("Show Source", var7));
    }

    public boolean isHideWhenIdle() {
        Boolean var7 = true;
        return ((Boolean) this.getSetting("Hide When Idle", var7));
    }

    public boolean isHideWhenPaused() {
        Boolean var7 = false;
        return ((Boolean) this.getSetting("Hide When Paused", var7));
    }

    public boolean isDimWhenPaused() {
        Boolean var7 = true;
        return ((Boolean) this.getSetting("Dim When Paused", var7));
    }

    public boolean isBackground() {
        Boolean var7 = true;
        return ((Boolean) this.getSetting("Background", var7));
    }

    public float getBackgroundOpacity() {
        Float var11 = 0.92F;
        float var10 = ((Float) this.getSetting("Background Opacity", var11));
        float var7 = Math.min(1.0F, var10);
        return Math.max(0.0F, var7);
    }

    public boolean isBorder() {
        Boolean var7 = true;
        return ((Boolean) this.getSetting("Border", var7));
    }

    public boolean isHighlight() {
        Boolean var7 = true;
        return ((Boolean) this.getSetting("Highlight", var7));
    }

    public boolean isBlur() {
        Boolean var7 = false;
        return ((Boolean) this.getSetting("Blur", var7));
    }

    public int getBlurStrength() {
        Integer var11 = 2;
        int var10 = ((Integer) this.getSetting("Blur Strength", var11));
        int var7 = Math.min(4, var10);
        return Math.max(1, var7);
    }

    public float getBlurTint() {
        Float var11 = 0.45F;
        float var10 = ((Float) this.getSetting("Blur Tint", var11));
        float var7 = Math.min(1.0F, var10);
        return Math.max(0.0F, var7);
    }

    public boolean isThemeColors() {
        Boolean var7 = true;
        return ((Boolean) this.getSetting("Theme Colors", var7));
    }

    public int getAccentColor() {
        return (ThemeColors.resolveRgb(this.getSetting("Accent Color", ""), -14829228, false) & 16777215);
    }

    public int getTextColor() {
        return (ThemeColors.resolveRgb(this.getSetting("Text Color", ""), -1184272, false) & 16777215);
    }

    public int getBackgroundColor() {
        return (ThemeColors.resolveRgb(this.getSetting("Background Color", ""), -15328474, false) & 16777215);
    }

}
