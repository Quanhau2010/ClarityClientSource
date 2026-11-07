package need.clarity.nvg;

import com.mojang.blaze3d.platform.Window;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import need.clarity.api.Modules;
import need.clarity.media.MediaBridge;
import need.clarity.modules.visual.ArrayListModule;
import need.clarity.modules.visual.HUD;
import need.clarity.modules.visual.ItemTags;
import need.clarity.modules.visual.KeybindList;
import need.clarity.modules.visual.KeybindListEntry;
import need.clarity.modules.visual.MusicPlayer;
import need.clarity.modules.visual.Nametags;
import need.clarity.modules.visual.NotificationToastEntry;
import need.clarity.modules.visual.Notifications;
import need.clarity.modules.visual.TNTTimer;
import need.clarity.modules.visual.TargetHud;
import need.clarity.modules.visual.TracerArrowEntry;
import need.clarity.modules.visual.Tracers;
import need.clarity.render.RawFramebufferUtil;
import need.clarity.util.ClarityRenderUtils;
import need.clarity.util.ThemeColors;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.core.ClientAsset;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityReference;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.lwjgl.BufferUtils;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NVGPaint;
import org.lwjgl.nanovg.NanoVG;
import org.lwjgl.nanovg.NanoVGGL3;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryStack;

public final class HudElements {
    private static final NVGColor COL;
    private static final Map HUD_ANIM;
    private static final Map HEAD_IMAGE_CACHE;
    private static float CANVAS_W;
    private static float CANVAS_H;
    private static final Map KB_ANIM;
    private static float MUSIC_ANIM;
    private static int musicImg;
    private static int musicImgVer;
    private static int blurImg;
    private static int blurImgTex;
    private static int blurImgW;
    private static int blurImgH;
    private static float TGT_ANIM;
    private static String C_NAME;
    private static float C_RATIO;
    private static int C_DIST;
    private static String C_GEAR;
    private static boolean C_SHOWGEAR;
    private static boolean C_SHOWDIST;
    private static boolean C_SHOWHEALTH;

    private HudElements() {
    }

    public static boolean enabled() {
        int var38 = 0;
        HUD var0 = HUD.INSTANCE;
        TargetHud var6 = TargetHud.INSTANCE;
        Nametags var7 = Nametags.INSTANCE;
        ItemTags var8 = ItemTags.INSTANCE;
        TNTTimer var9 = TNTTimer.INSTANCE;
        if (HudLayout.isEditing()) {
            return true;
        }
        if (Notifications.isEligible1()) {
            return true;
        }
        if (KeybindList.INSTANCE != null) {
            if (KeybindList.INSTANCE.isEnabled()) {
                return true;
            }
        }
        if (MusicPlayer.INSTANCE != null) {
            if (MusicPlayer.INSTANCE.isEnabled()) {
                return true;
            }
        }
        if (var0 != null) {
            if (!var0.isEnabled()) {
            }
        }
        if (var6 != null) {
            if (!var6.isEnabled()) {
            }
        }
        if (var7 != null) {
            if (!var7.isEnabled()) {
            }
        }
        if (var8 != null) {
            if (!var8.isEnabled()) {
            }
        }
        if (var9 != null) {
            if (!var9.isEnabled()) {
            }
        }
        if (Tracers.isStyle()) {
            var38 = 1;
        } else {
            var38 = 0;
        }
        return var38;
    }

    public static void draw(long arg0, float arg1, float arg2) {
        Minecraft var4 = Minecraft.getInstance();
        if (var4 != null) {
            if (var4.player == null) {
            }
        }
        return;
        CANVAS_W = arg1;
        CANVAS_H = arg2;
        drawInfo(arg0, var4);
        drawKeybindList(arg0, var4, arg1, arg2);
        drawMusicPlayer(arg0, var4, arg1, arg2);
        drawNametags(arg0, var4, arg1, arg2);
        drawMobOwners(arg0, var4, arg1, arg2);
        drawItemTags(arg0, var4, arg1, arg2);
        drawTntTimers(arg0, var4, arg1, arg2);
        drawTarget(arg0, var4, arg1, arg2);
        drawNotifications(arg0, var4, arg1, arg2);
        drawTracerArrows(arg0, var4, arg1, arg2);
        HudLayout.drawOverlay(arg0, arg1, arg2);
    }

    private static void drawTracerArrows(long arg0, Minecraft arg1, float arg2, float arg3) {
        double var388 = 0.0;
        double var390 = 0.0;
        Vec3 var49 = null;
        if (Tracers.isStyle()) {
            if (arg1.level != null) {
                if (arg1.player == null) {
                }
            }
            return;
            Tracers var70 = Tracers.evaluate();
            String var274 = var70.resolveLabel();
            int var73 = ClarityImages.get(arg0, var274);
            if (var73 <= 0) {
                return;
            }
            float var77 = arg1.getDeltaTracker().getGameTimeDeltaPartialTick(false);
            Camera var80 = arg1.gameRenderer.getMainCamera();
            if (var80 == null) {
                return;
            }
            Vec3 var82 = ClarityRenderUtils.getCameraPos();
            double var87 = Math.toRadians(((double) var80.yRot()));
            double var93 = Math.toRadians(((double) var80.xRot()));
            double var97 = Math.cos(var93);
            double var101 = Math.sin(var93);
            double var105 = Math.cos(var87);
            double var109 = Math.sin(var87);
            double var279 = (-(var109) * var97);
            double var346 = -(var101);
            double var367 = (var105 * var97);
            Vec3 var111 = new Vec3(var279, var346, var367);
            double var283 = -(var105);
            double var371 = -(var109);
            Vec3 var112 = new Vec3(var283, 0.0, var371);
            double var289 = (-(var109) * var101);
            double var375 = (var105 * var101);
            Vec3 var113 = new Vec3(var289, var97, var375);
            Vec3 var114 = var113.scale(1.0);
            Window var116 = arg1.getWindow();
            int var250 = var116.getGuiScaledWidth();
            int var118 = Math.max(1, var250);
            int var252 = var116.getGuiScaledHeight();
            int var120 = Math.max(1, var252);
            float var125 = (((float) ((var118 - 15) / 2)) + 7.5F);
            float var257 = ((float) var118);
            float var127 = ((var125 / var257) * arg2);
            float var132 = (((float) ((var120 - 15) / 2)) + 7.5F);
            float var263 = ((float) var120);
            float var134 = ((var132 / var263) * arg3);
            float var136 = var70.getArrowDistance();
            float var138 = var70.computeFloatMetric();
            float var266 = var70.getArrowScale();
            float var139 = (var138 * var266);
            float var141 = var70.getArrowScale();
            Iterator var144 = var70.evaluate(var77).iterator();
            while (true) {
                if (!var144.hasNext()) break;
                TracerArrowEntry var149 = ((TracerArrowEntry) var144.next());
                var49 = null;
                try {
                    GameRenderer var152 = arg1.gameRenderer;
                    Vec3 var269 = var149.pos();
                    var49 = var152.projectPointToScreen(var269);
                } catch (Throwable ignored) {
                }
                if (var49 != null) {
                    if (Double.compare(var49.z, 0.0) > 0) {
                        if (Double.compare(var49.z, 1.0) < 0) {
                            if (Double.isFinite(var49.x)) {
                                if (Double.isFinite(var49.y)) {
                                    double var176 = ((var49.x * 0.5) + 0.5);
                                    double var300 = ((double) arg2);
                                    double var178 = (var176 * var300);
                                    double var307 = ((var49.y * 0.5) + 0.5);
                                    double var182 = (1.0 - var307);
                                    double var310 = ((double) arg3);
                                    double var184 = (var182 * var310);
                                    double var313 = ((double) var127);
                                    double var188 = (var178 - var313);
                                    double var316 = ((double) var134);
                                    double var192 = (var184 - var316);
                                    double var196 = Math.hypot(var188, var192);
                                    if (Double.compare(var196, 0.25) < 0) {
                                    }
                                    var388 = (var188 / var196);
                                    var390 = (var192 / var196);
                                }
                            } else {
                            }
                        }
                        Vec3 var222 = var149.pos().subtract(var82);
                        double var224 = var222.dot(var112);
                        double var227 = var222.dot(var114);
                        double var231 = Math.hypot(var224, var227);
                        if (Double.compare(var231, 1.0E-6) < 0) {
                        }
                        var388 = (var224 / var231);
                        var390 = (-(var227) / var231);
                        double var209 = var388;
                        double var328 = -(var390);
                        float var213 = ((float) Math.atan2(var209, var328));
                        double var216 = ((double) (var136 + var139));
                        double var339 = ((double) var127);
                        double var364 = (var388 * var216);
                        float var343 = ((float) (var339 + var364));
                        double var359 = ((double) var134);
                        double var379 = (var390 * var216);
                        float var363 = ((float) (var359 + var379));
                        int var383 = var149.argb();
                        ClarityImages.draw(arg0, var73, var343, var363, var141, var213, var383);
                    }
                }
            }
            return;
        }
    }

    private static void drawTntTimers(long arg0, Minecraft arg1, float arg2, float arg3) {
        if (TNTTimer.isEligible()) {
            if (arg1.level == null) {
            }
        }
        return;
        Iterator var29 = arg1.level.entitiesForRendering().iterator();
        while (true) {
            if (!var29.hasNext()) break;
            Entity var34 = ((Entity) var29.next());
            if (!(var34 instanceof PrimedTnt)) continue;
            PrimedTnt var38 = ((PrimedTnt) var34);
            try {
                GameRenderer var40 = arg1.gameRenderer;
                double var112 = var38.getX();
                double var146 = (var38.getY() + 1.1);
                double var160 = var38.getZ();
                Vec3 var6 = new Vec3(var112, var146, var160);
                Vec3 var41 = var40.projectPointToScreen(var6);
            } catch (Throwable ignored) {
            }
            if (var41 == null) continue;
            if (Double.compare(var41.z, 0.0) <= 0) continue;
            if (Double.compare(var41.z, 1.0) >= 0) {
            }
            double var57 = ((var41.x * 0.5) + 0.5);
            double var92 = ((double) arg2);
            float var61 = ((float) (var57 * var92));
            double var99 = ((var41.y * 0.5) + 0.5);
            double var64 = (1.0 - var99);
            double var102 = ((double) arg3);
            float var68 = ((float) (var64 * var102));
            Object[] var82 = new Object[1];
            Float var133 = (((float) var38.getFuse()) / 20.0F);
            var82[0] = var133;
            String var70 = String.format("%.1fs", var82);
            float var73 = ClarityDraw.textWidth(arg0, "inter-sb", 8.0F, 0.0F, var70);
            float var117 = (var73 / 2.0F);
            float var107 = ((var61 - var117) - 4.0F);
            float var120 = (var68 - 6.0F);
            float var138 = (var73 + 8.0F);
            int var166 = (ThemeColors.getSurfaceRgb() & 16777215);
            int var163 = (-687865856 | var166);
            ClarityDraw.fillRRect(arg0, var107, var120, var138, 12.0F, 3.0F, var163);
            float var122 = (var73 / 2.0F);
            float var110 = ((var61 - var122) - 4.0F);
            float var125 = (var68 - 6.0F);
            float var142 = (var73 + 8.0F);
            int var170 = ThemeColors.getBorderRgb();
            int var168 = (-16777216 | var170);
            ClarityDraw.strokeRRect(arg0, var110, var125, var142, 12.0F, 3.0F, 1.0F, var168);
            int var157 = ThemeColors.getTextRgb();
            int var155 = (-16777216 | var157);
            float var80 = ClarityDraw.text(arg0, "inter-sb", 8.0F, 0.0F, var155, 18, var61, var68, var70);
        }
    }

    private static void drawItemTags(long arg0, Minecraft arg1, float arg2, float arg3) {
        String var26 = null;
        ItemTags var5 = ItemTags.INSTANCE;
        if (var5 != null) {
            if (var5.isEnabled()) {
                if (arg1.level == null) {
                }
            }
            return;
            float var34 = var5.getScale();
            NanoVG.nvgSave(arg0);
            NanoVG.nvgScale(arg0, var34, var34);
            Iterator var42 = arg1.level.entitiesForRendering().iterator();
            while (true) {
                if (!var42.hasNext()) break;
                Entity var47 = ((Entity) var42.next());
                if (!(var47 instanceof ItemEntity)) continue;
                ItemEntity var51 = ((ItemEntity) var47);
                float var54 = arg1.player.distanceTo(var51);
                float var120 = var5.getMaxDistance();
                if (Float.compare(var54, var120) > 0) {
                }
                try {
                    GameRenderer var57 = arg1.gameRenderer;
                    double var164 = var51.getX();
                    double var192 = (var51.getY() + 0.4);
                    double var203 = var51.getZ();
                    Vec3 var121 = new Vec3(var164, var192, var203);
                    Vec3 var58 = var57.projectPointToScreen(var121);
                } catch (Throwable ignored) {
                }
                if (var58 == null) continue;
                if (Double.compare(var58.z, 0.0) <= 0) continue;
                if (Double.compare(var58.z, 1.0) >= 0) {
                }
                double var73 = ((var58.x * 0.5) + 0.5);
                double var140 = ((double) arg2);
                float var78 = (((float) (var73 * var140)) / var34);
                double var147 = ((var58.y * 0.5) + 0.5);
                double var81 = (1.0 - var147);
                double var150 = ((double) arg3);
                float var86 = (((float) (var81 * var150)) / var34);
                ItemStack var88 = var51.getItem();
                var26 = var88.getHoverName().getString();
                if (var26.length() > 16) {
                    var26 = var26.substring(0, 16);
                }
                if (var5.isShowCount()) {
                    if (var88.getCount() > 1) {
                        String var100 = var26;
                        int var128 = var88.getCount();
                        var26 = (var100) + " x" + (var128);
                    }
                    String var194 = var26;
                    float var104 = ClarityDraw.textWidth(arg0, "inter-med", 6.5F, 0.0F, var194);
                    if (var5.isBackground()) {
                        NVGColor var107 = COL;
                        int var129 = ThemeColors.getSurfaceRgb();
                        setARGB(var107, var129, 0.65F);
                        float var168 = (var104 / 2.0F);
                        float var157 = ((var78 - var168) - 3.0F);
                        float var171 = (var86 - 5.0F);
                        float var184 = (var104 + 6.0F);
                        NVGColor var205 = COL;
                        ClarityDraw.fillRRect(arg0, var157, var171, var184, 10.0F, 2.0F, var205);
                        float var173 = (var104 / 2.0F);
                        float var160 = ((var78 - var173) - 3.0F);
                        float var176 = (var86 - 5.0F);
                        float var188 = (var104 + 6.0F);
                        int var16 = ThemeColors.getBorderRgb();
                        int var208 = (-16777216 | var16);
                        ClarityDraw.strokeRRect(arg0, var160, var176, var188, 10.0F, 2.0F, 1.0F, var208);
                    }
                    NVGColor var112 = COL;
                    int var130 = ThemeColors.getTextRgb();
                    setARGB(var112, var130, 0.95F);
                    NVGColor var199 = COL;
                    String var210 = var26;
                    float var115 = ClarityDraw.text(arg0, "inter-med", 6.5F, 0.0F, var199, 18, var78, var86, var210);
                }
            }
            NanoVG.nvgRestore(arg0);
            return;
        }
    }

    private static void drawNametags(long arg0, Minecraft arg1, float arg2, float arg3) {
        float var135 = 0.0F;
        float var166 = 0.0F;
        String var193 = null;
        Nametags var5 = Nametags.INSTANCE;
        if (var5 != null) {
            if (var5.isEnabled()) {
                if (arg1.level == null) {
                }
            }
            return;
            float var38 = var5.getScale();
            NanoVG.nvgSave(arg0);
            NanoVG.nvgScale(arg0, var38, var38);
            int var43 = ThemeColors.getAccentRgb();
            Iterator var47 = arg1.level.players().iterator();
            while (true) {
                if (!var47.hasNext()) break;
                Player var54 = ((Player) var47.next());
                LocalPlayer var167 = arg1.player;
                if (var54 == var167) {
                    if (var5.isHideSelf()) {
                    }
                }
                float var60 = arg1.player.distanceTo(var54);
                float var170 = var5.getMaxDistance();
                if (Float.compare(var60, var170) > 0) {
                }
                double var201 = var54.getX();
                double var243 = var54.getY();
                double var273 = ((double) var54.getBbHeight());
                double var245 = (var243 + var273);
                double var247 = (var245 + 0.4);
                double var278 = var54.getZ();
                Vec3 var62 = new Vec3(var201, var247, var278);
                try {
                    Vec3 var65 = arg1.gameRenderer.projectPointToScreen(var62);
                } catch (Throwable ignored) {
                }
                if (var65 == null) continue;
                if (Double.compare(var65.z, 0.0) <= 0) continue;
                if (Double.compare(var65.z, 1.0) >= 0) {
                }
                double var80 = ((var65.x * 0.5) + 0.5);
                double var212 = ((double) arg2);
                float var85 = (((float) (var80 * var212)) / var38);
                double var219 = ((var65.y * 0.5) + 0.5);
                double var88 = (1.0 - var219);
                double var222 = ((double) arg3);
                float var93 = (((float) (var88 * var222)) / var38);
                ArrayList var94 = new ArrayList();
                if (var5.isHealth()) {
                    Object[] var225 = new Object[1];
                    Float var261 = var54.getHealth();
                    var225[0] = var261;
                    String var175 = String.format("%.0f HP", var225);
                    boolean var98 = var94.add(var175);
                }
                boolean var100 = var5.isDistance();
                if (var100) {
                    String var180 = (((int) arg1.player.distanceTo(var54))) + "m";
                    boolean var102 = var94.add(var180);
                }
                boolean var104 = var5.isPing();
                if (var104) {
                    ClientPacketListener var106 = arg1.getConnection();
                    if (var106 != null) {
                        ClientPacketListener var108 = arg1.getConnection();
                        UUID var182 = var54.getUUID();
                        PlayerInfo var109 = var108.getPlayerInfo(var182);
                        if (var109 != null) {
                            String var185 = (var109.getLatency()) + "ms";
                            boolean var112 = var94.add(var185);
                        }
                        boolean var114 = var5.isArmour();
                        if (var114) {
                            String var188 = "A" + (var54.getArmorValue());
                            boolean var116 = var94.add(var188);
                        }
                        boolean var118 = var5.isHeldItem();
                        if (var118) {
                            InteractionHand var189 = InteractionHand.MAIN_HAND;
                            ItemStack var120 = var54.getItemInHand(var189);
                            boolean var122 = var120.isEmpty();
                            if (!var122) {
                                Component var124 = var120.getHoverName();
                                String var125 = var124.getString();
                                if (var125.length() > 14) {
                                    var193 = var125.substring(0, 14);
                                } else {
                                    var193 = var125;
                                }
                                boolean var127 = var94.add(var193);
                            }
                            String var129 = String.join("  ", var94);
                            String var264 = var54.getName().getString();
                            float var132 = ClarityDraw.textWidth(arg0, "inter-sb", 7.0F, 0.0F, var264);
                            if (var129.isEmpty()) {
                                var135 = 0.0F;
                            } else {
                                var135 = ClarityDraw.textWidth(arg0, "inter-med", 6.5F, 0.0F, var129);
                            }
                            float var27 = var135;
                            float var138 = (Math.max(var132, var27) + 8.0F);
                            if (var129.isEmpty()) {
                                var166 = 12.0F;
                            } else {
                                var166 = 21.0F;
                            }
                            float var29 = var166;
                            float var198 = (var138 / 2.0F);
                            float var144 = ((float) Math.round((var85 - var198)));
                            float var148 = ((float) Math.round((var93 - 17.0F)));
                            if (var5.isBackground()) {
                                int var294 = (ThemeColors.getSurfaceRgb() & 16777215);
                                int var286 = (-1023410176 | var294);
                                ClarityDraw.fillRRect(arg0, var144, var148, var138, var29, 3.0F, var286);
                                int var305 = ThemeColors.getBorderRgb();
                                int var296 = (-16777216 | var305);
                                ClarityDraw.strokeRRect(arg0, var144, var148, var138, var29, 3.0F, 1.0F, var296);
                            }
                            int var268 = (-16777216 | var43);
                            float var290 = ((float) Math.round(var85));
                            float var300 = (((float) Math.round(var93)) - 14.0F);
                            String var309 = var54.getName().getString();
                            float var157 = ClarityDraw.text(arg0, "inter-sb", 7.0F, 0.0F, var268, 10, var290, var300, var309);
                            if (var129.isEmpty()) continue;
                            int var284 = ThemeColors.getTextRgb();
                            int var270 = (-16777216 | var284);
                            float var293 = ((float) Math.round(var85));
                            float var304 = (((float) Math.round(var93)) - 6.0F);
                            float var162 = ClarityDraw.text(arg0, "inter-med", 6.5F, 0.0F, var270, 10, var293, var304, var129);
                        }
                    }
                }
            }
            NanoVG.nvgRestore(arg0);
            return;
        }
    }

    private static void drawMobOwners(long arg0, Minecraft arg1, float arg2, float arg3) {
        String var22 = null;
        Nametags var5 = Nametags.INSTANCE;
        if (var5 != null) {
            if (var5.isEnabled()) {
                if (var5.isMobOwners()) {
                    if (arg1.level == null) {
                    }
                }
                return;
                float var36 = var5.getScale();
                NanoVG.nvgSave(arg0);
                NanoVG.nvgScale(arg0, var36, var36);
                int var41 = ThemeColors.getAccentRgb();
                Iterator var45 = arg1.level.entitiesForRendering().iterator();
                while (true) {
                    if (!var45.hasNext()) break;
                    Entity var52 = ((Entity) var45.next());
                    if (!(var52 instanceof TamableAnimal)) continue;
                    TamableAnimal var56 = ((TamableAnimal) var52);
                    if (!var56.isTame()) {
                    }
                    float var61 = arg1.player.distanceTo(var56);
                    float var122 = var5.getMaxDistance();
                    if (Float.compare(var61, var122) > 0) {
                    }
                    var22 = "?";
                    try {
                        EntityReference var65 = var56.getOwnerReference();
                        ClientLevel var124 = arg1.level;
                        LivingEntity var67 = EntityReference.getLivingEntity(var65, var124);
                        if (var67 != null) {
                            var22 = var67.getName().getString();
                        }
                    } catch (Throwable ignored) {
                    }
                    try {
                        GameRenderer var73 = arg1.gameRenderer;
                        double var163 = var56.getX();
                        double var189 = var56.getY();
                        double var204 = ((double) var56.getBbHeight());
                        double var193 = ((var189 + var204) + 0.4);
                        double var209 = var56.getZ();
                        Vec3 var125 = new Vec3(var163, var193, var209);
                        Vec3 var74 = var73.projectPointToScreen(var125);
                    } catch (Throwable ignored) {
                    }
                    if (var74 == null) continue;
                    if (Double.compare(var74.z, 0.0) <= 0) continue;
                    if (Double.compare(var74.z, 1.0) >= 0) {
                    }
                    double var90 = ((var74.x * 0.5) + 0.5);
                    double var140 = ((double) arg2);
                    float var95 = (((float) (var90 * var140)) / var36);
                    double var147 = ((var74.y * 0.5) + 0.5);
                    double var98 = (1.0 - var147);
                    double var150 = ((double) arg3);
                    float var103 = (((float) (var98 * var150)) / var36);
                    String var106 = var56.getName().getString();
                    String var128 = var22;
                    String var107 = (var106) + "  " + (var128);
                    float var110 = ClarityDraw.textWidth(arg0, "inter-med", 6.5F, 0.0F, var107);
                    NVGColor var111 = COL;
                    int var129 = ThemeColors.getSurfaceRgb();
                    setARGB(var111, var129, 0.65F);
                    float var167 = (var110 / 2.0F);
                    float var156 = ((var95 - var167) - 3.0F);
                    float var170 = (var103 - 5.0F);
                    float var183 = (var110 + 6.0F);
                    NVGColor var211 = COL;
                    ClarityDraw.fillRRect(arg0, var156, var170, var183, 10.0F, 2.0F, var211);
                    float var172 = (var110 / 2.0F);
                    float var159 = ((var95 - var172) - 3.0F);
                    float var175 = (var103 - 5.0F);
                    float var187 = (var110 + 6.0F);
                    int var15 = ThemeColors.getBorderRgb();
                    int var214 = (-16777216 | var15);
                    ClarityDraw.strokeRRect(arg0, var159, var175, var187, 10.0F, 2.0F, 1.0F, var214);
                    setARGB(COL, var41, 1.0F);
                    NVGColor var200 = COL;
                    float var119 = ClarityDraw.text(arg0, "inter-med", 6.5F, 0.0F, var200, 18, var95, var103, var107);
                }
                NanoVG.nvgRestore(arg0);
                return;
            }
        }
    }

    private static void drawInfo(long arg0, Minecraft arg1) {
        int var33 = 0;
        float var549 = 0.0F;
        int var550 = 0;
        int var551 = 0;
        float var556 = 0.0F;
        int var45 = 0;
        int var46 = 0;
        int var242 = 0;
        float var248 = 0.0F;
        float var249 = 0.0F;
        int var320 = 0;
        float var324 = 0.0F;
        int var494 = 0;
        HUD var3 = HUD.INSTANCE;
        if (var3 != null) {
            boolean var50 = var3.isEnabled();
            if (!var50) {
            }
        }
        return;
        float var52 = var3.getScale();
        NanoVG.nvgSave(arg0);
        NanoVG.nvgScale(arg0, var52, var52);
        ArrayList var57 = new ArrayList();
        boolean var59 = var3.isFPS();
        if (var59) {
            String[] var251 = new String[2];
            int var389 = 0;
            var251[var389] = "FPS";
            int var390 = 1;
            Minecraft var417 = arg1;
            int var418 = var417.getFps();
            String var419 = String.valueOf(var418);
            var251[var390] = var419;
            boolean var61 = var57.add(var251);
        }
        boolean var63 = var3.isPing();
        if (var63) {
            String[] var253 = new String[2];
            int var391 = 0;
            String var420 = "Ping";
            var253[var391] = var420;
            int var392 = 1;
            Minecraft var421 = arg1;
            int var422 = ping(var421);
            String var423 = (var422) + "ms";
            var253[var392] = var423;
            boolean var65 = var57.add(var253);
        }
        boolean var67 = var3.isCoords();
        if (var67) {
            String[] var255 = new String[2];
            int var393 = 0;
            String var424 = "XYZ";
            var255[var393] = var424;
            int var394 = 1;
            String var425 = "%.0f %.0f %.0f";
            Object[] var443 = new Object[3];
            LocalPlayer var495 = arg1.player;
            double var496 = var495.getX();
            Double var498 = var496;
            var443[0] = var498;
            int var473 = 1;
            Minecraft var499 = arg1;
            LocalPlayer var500 = var499.player;
            double var501 = var500.getY();
            Double var503 = var501;
            var443[var473] = var503;
            int var474 = 2;
            Minecraft var504 = arg1;
            LocalPlayer var505 = var504.player;
            double var506 = var505.getZ();
            Double var508 = var506;
            var443[var474] = var508;
            String var426 = String.format(var425, var443);
            var255[var394] = var426;
            boolean var69 = var57.add(var255);
        }
        boolean var71 = var3.isWatermark();
        if (!var71) {
            boolean var74 = var57.isEmpty();
            if (var74) {
                NanoVG.nvgRestore(arg0);
                return;
            }
        }
        int var77 = ThemeColors.getAccentRgb();
        int var78 = ThemeColors.getTextRgb();
        int var79 = ThemeColors.getMutedRgb();
        float var84 = var3.getItemGap();
        float var86 = var3.getPaddingX();
        float var88 = var3.getPaddingY();
        float var90 = var3.getCornerRadius();
        if (var71) {
            float var395 = 13.0F;
            float var427 = 0.0F;
            HUD var444 = var3;
            String var445 = var444.getWatermarkText();
            var248 = ClarityDraw.textWidth(arg0, "inter-sb", var395, var427, var445);
        } else {
            var248 = 0.0F;
        }
        float var30 = var248;
        int var95 = var57.size();
        float[] var96 = new float[var95];
        int var98 = var57.size();
        float[] var99 = new float[var98];
        var33 = 0;
        while (true) {
            int var101 = var33;
            int var257 = var57.size();
            if (var101 >= var257) break;
            int var322 = var33;
            long var383 = arg0;
            String var441 = "inter-med";
            float var458 = 12.0F;
            float var471 = 0.0F;
            ArrayList var486 = var57;
            int var519 = var33;
            Object var487 = var486.get(var519);
            String[] var488 = ((String[]) var487);
            String var489 = var488[0];
            float var385 = ClarityDraw.textWidth(var383, var441, var458, var471, var489);
            var96[var322] = var385;
            int var323 = var33;
            long var386 = arg0;
            String var442 = "inter-med";
            float var459 = 12.0F;
            float var472 = 0.0F;
            ArrayList var490 = var57;
            int var521 = var33;
            Object var491 = var490.get(var521);
            String[] var492 = ((String[]) var491);
            String var493 = var492[1];
            float var388 = ClarityDraw.textWidth(var386, var442, var459, var472, var493);
            var99[var323] = var388;
            var33 = (var33 + 1);
        }
        LinkedHashSet var102 = new LinkedHashSet();
        if (var71) {
            boolean var105 = var102.add("wm");
        }
        Iterator var107 = var57.iterator();
        while (true) {
            boolean var109 = var107.hasNext();
            if (!var109) break;
            Object var111 = var107.next();
            String[] var112 = ((String[]) var111);
            String var260 = var112[0];
            boolean var114 = var102.add(var260);
        }
        Map var327 = HUD_ANIM;
        Set var328 = var327.keySet();
        LinkedHashSet var115 = new LinkedHashSet(var328);
        boolean var117 = var115.addAll(var102);
        Iterator var119 = var115.iterator();
        while (true) {
            boolean var121 = var119.hasNext();
            if (!var121) break;
            Object var123 = var119.next();
            String var124 = ((String) var123);
            boolean var126 = var102.contains(var124);
            Map var127 = HUD_ANIM;
            Float var330 = 0.0F;
            Object var128 = var127.getOrDefault(var124, var330);
            Float var129 = ((Float) var128);
            float var130 = var129;
            if (var126) {
                var324 = 1.0F;
            } else {
                var324 = 0.0F;
            }
            float var265 = (var324 - var130);
            float var266 = (var265 * 0.12F);
            float var132 = (var130 + var266);
            if (!var126) {
                int var135 = Float.compare(var132, 0.01F);
                if (var135 < 0) {
                    Map var136 = HUD_ANIM;
                    Object var137 = var136.remove(var124);
                }
            }
            Map var138 = HUD_ANIM;
            Float var334 = var132;
            Object var139 = var138.put(var124, var334);
        }
        if (var71) {
            Map var243 = HUD_ANIM;
            float var381 = 0.0F;
            Float var382 = var381;
            Object var244 = var243.getOrDefault("wm", var382);
            Float var245 = ((Float) var244);
            var249 = var245;
        } else {
            var249 = 0.0F;
        }
        float var548 = var249;
        var549 = 0.0F;
        var550 = 0;
        if (var71) {
            int var145 = Float.compare(var548, 0.005F);
            if (var145 > 0) {
                float var146 = var549;
                float var272 = (var30 * var548);
                float var147 = (var146 + var272);
                var549 = var147;
                var550 = (var550 + 1);
            }
            var551 = 0;
            while (true) {
                int var149 = var551;
                int var274 = var57.size();
                if (var149 >= var274) break;
                Map var150 = HUD_ANIM;
                int var336 = var551;
                Object var276 = var57.get(var336);
                String[] var277 = ((String[]) var276);
                String var278 = var277[0];
                Float var339 = 0.0F;
                Object var151 = var150.getOrDefault(var278, var339);
                Float var152 = ((Float) var151);
                float var153 = var152;
                int var155 = Float.compare(var153, 0.005F);
                if (var155 > 0) {
                    float var156 = var549;
                    int var340 = var551;
                    float var281 = var96[var340];
                    float var282 = (var281 + 4.0F);
                    int var396 = var551;
                    float var343 = var99[var396];
                    float var283 = (var282 + var343);
                    float var284 = (var283 * var153);
                    float var157 = (var156 + var284);
                    var549 = var157;
                    var550 = (var550 + 1);
                }
                var551 = (var551 + 1);
            }
            float var159 = (var86 * 2.0F);
            float var286 = var549;
            float var160 = (var159 + var286);
            int var345 = var550;
            int var397 = 1;
            int var346 = (var345 - var397);
            int var288 = Math.max(0, var346);
            float var289 = ((float) var288);
            float var290 = (var289 * var84);
            float var161 = (var160 + var290);
            int var164 = var3.getX();
            float var165 = ((float) var164);
            int var166 = Math.round(var165);
            int var168 = var3.getY();
            float var169 = ((float) var168);
            int var170 = Math.round(var169);
            float var292 = ((float) var166);
            float var293 = (var292 * var52);
            float var350 = ((float) var170);
            float var398 = var52;
            float var351 = (var350 * var398);
            float var399 = var161;
            float var428 = var52;
            float var400 = (var399 * var428);
            float var429 = 30.0F;
            float var446 = var52;
            float var430 = (var429 * var446);
            HudLayout.report("HUD", var293, var351, var400, var430);
            boolean var173 = var3.isBackground();
            if (var173) {
                int var174 = ThemeColors.getSurfaceRgb();
                int var175 = mix(var174, var77, 0.02F);
                boolean var177 = var3.isBlur();
                if (var177) {
                    float var354 = ((float) var166);
                    int var401 = var170;
                    float var402 = ((float) var401);
                    float var431 = var161;
                    float var447 = 30.0F;
                    float var460 = var90;
                    float var475 = var90;
                    float var509 = var90;
                    float var13 = CANVAS_W;
                    float var541 = (var13 / var52);
                    float var542 = CANVAS_H;
                    float var543 = (var542 / var52);
                    boolean var180 = fillBlurRRect(arg0, var354, var402, var431, var447, var460, var475, var509, var90, 0.0F, 0.0F, var541, var543, 1.0F);
                    if (var180) {
                        var242 = 1;
                    } else {
                        var242 = 0;
                    }
                    int var43 = var242;
                    float var356 = ((float) var166);
                    int var403 = var170;
                    float var404 = ((float) var403);
                    float var432 = var161;
                    float var448 = 30.0F;
                    float var461 = var90;
                    int var476 = var43;
                    if (var476 != 0) {
                        int var477 = var175;
                        HUD var510 = var3;
                        float var511 = var510.getBlurTint();
                        var494 = tintA(var477, var511);
                    } else {
                        int var485 = -16777216;
                        var494 = (var485 | var175);
                    }
                    ClarityDraw.fillRRect(arg0, var356, var404, var432, var448, var461, var494);
                    boolean var184 = var3.isBorder();
                    if (var184) {
                        float var358 = ((float) var166);
                        int var405 = var170;
                        float var406 = ((float) var405);
                        float var433 = var161;
                        float var449 = 30.0F;
                        float var462 = var90;
                        float var478 = 1.0F;
                        int var523 = ThemeColors.getBorderRgb();
                        int var524 = mix(var523, var77, 0.1F);
                        int var513 = (-16777216 | var524);
                        ClarityDraw.strokeRRect(arg0, var358, var406, var433, var449, var462, var478, var513);
                    }
                    boolean var188 = var3.isHighlight();
                    if (var188) {
                        int var189 = ThemeColors.getSurfaceRgb();
                        boolean var295 = ThemeColors.isLight();
                        if (var295) {
                            var320 = 0;
                        } else {
                            var320 = 16777215;
                        }
                        int var190 = mix(var189, var320, 0.12F);
                        float var361 = ((float) var166);
                        int var407 = var170;
                        float var408 = ((float) var407);
                        float var434 = var161;
                        float var450 = 30.0F;
                        float var463 = var90;
                        int var479 = 1711276032;
                        int var480 = (var479 | var190);
                        ClarityDraw.topHighlight(arg0, var361, var408, var434, var450, var463, var480);
                    }
                    float var194 = ((float) var170);
                    float var297 = (30.0F * 0.5F);
                    float var195 = (var194 + var297);
                    float var196 = (var195 + 1.0F);
                    int var197 = Math.round(var196);
                    float var198 = ((float) var197);
                    float var200 = ((float) var166);
                    float var201 = (var200 + var86);
                    var556 = var201;
                    int var300 = ThemeColors.getBorderRgb();
                    int var363 = var77;
                    float var409 = 0.1F;
                    int var301 = mix(var300, var363, var409);
                    int var203 = (-939524096 | var301);
                    var45 = 1;
                    if (var71) {
                        int var207 = Float.compare(var548, 0.005F);
                        if (var207 > 0) {
                            String var364 = "inter-sb";
                            float var410 = 13.0F;
                            float var435 = 0.0F;
                            int var451 = var77;
                            float var464 = var548;
                            int var452 = tintA(var451, var464);
                            int var465 = 17;
                            float var481 = var556;
                            String var526 = var3.getWatermarkText();
                            float var210 = ClarityDraw.text(arg0, var364, var410, var435, var452, var465, var481, var198, var526);
                            float var211 = var556;
                            float var365 = var548;
                            float var304 = (var30 * var365);
                            float var212 = (var211 + var304);
                            var556 = var212;
                            var45 = 0;
                        }
                        var46 = 0;
                        while (true) {
                            int var215 = var46;
                            int var306 = var57.size();
                            if (var215 >= var306) break;
                            Map var218 = HUD_ANIM;
                            int var366 = var46;
                            Object var308 = var57.get(var366);
                            String[] var309 = ((String[]) var308);
                            int var367 = 0;
                            String var310 = var309[var367];
                            float var368 = 0.0F;
                            Float var369 = var368;
                            Object var219 = var218.getOrDefault(var310, var369);
                            Float var220 = ((Float) var219);
                            float var221 = var220;
                            int var223 = Float.compare(var221, 0.005F);
                            if (var223 <= 0) {
                            } else {
                                int var224 = var45;
                                if (var224 == 0) {
                                    float var225 = var556;
                                    float var226 = (var225 + var84);
                                    var556 = var226;
                                    boolean var228 = var3.isSeparators();
                                    if (var228) {
                                        float var370 = var556;
                                        float var411 = var84;
                                        float var436 = 0.5F;
                                        float var412 = (var411 * var436);
                                        float var371 = (var370 - var412);
                                        int var372 = Math.round(var371);
                                        float var373 = ((float) var372);
                                        float var413 = var198;
                                        float var437 = 7.0F;
                                        float var414 = (var413 - var437);
                                        float var438 = 1.0F;
                                        float var453 = 14.0F;
                                        float var466 = 0.5F;
                                        int var482 = var203;
                                        ClarityDraw.fillRRect(arg0, var373, var414, var438, var453, var466, var482);
                                    }
                                    var45 = 0;
                                    String var374 = "inter-med";
                                    float var415 = 12.0F;
                                    float var439 = 0.0F;
                                    int var454 = var78;
                                    float var467 = var221;
                                    int var455 = tintA(var454, var467);
                                    int var468 = 17;
                                    float var483 = var556;
                                    int var536 = var46;
                                    Object var528 = var57.get(var536);
                                    String[] var529 = ((String[]) var528);
                                    String var530 = var529[0];
                                    float var234 = ClarityDraw.text(arg0, var374, var415, var439, var455, var468, var483, var198, var530);
                                    float var235 = var556;
                                    int var375 = var46;
                                    float var314 = var96[var375];
                                    float var376 = 4.0F;
                                    float var315 = (var314 + var376);
                                    float var377 = var221;
                                    float var316 = (var315 * var377);
                                    float var236 = (var235 + var316);
                                    String var378 = "inter-med";
                                    float var416 = 12.0F;
                                    float var440 = 0.0F;
                                    int var456 = var79;
                                    float var469 = var221;
                                    int var457 = tintA(var456, var469);
                                    int var470 = 17;
                                    float var484 = var236;
                                    int var538 = var46;
                                    Object var532 = var57.get(var538);
                                    String[] var533 = ((String[]) var532);
                                    String var534 = var533[1];
                                    float var239 = ClarityDraw.text(arg0, var378, var416, var440, var457, var470, var484, var198, var534);
                                    int var379 = var46;
                                    float var318 = var99[var379];
                                    float var380 = var221;
                                    float var319 = (var318 * var380);
                                    float var241 = (var236 + var319);
                                    var556 = var241;
                                }
                            }
                            var46 = (var46 + 1);
                        }
                        NanoVG.nvgRestore(arg0);
                        return;
                    }
                }
            }
        }
    }

    private static int tintA(int arg0, float arg1) {
        float var12 = Math.min(1.0F, arg1);
        int var10 = (Math.round((Math.max(0.0F, var12) * 255.0F)) << 24);
        int var16 = (arg0 & 16777215);
        return (var10 | var16);
    }

    private static int mix(int arg0, int arg1, float arg2) {
        int var14 = ((arg0 >> 16) & 255);
        int var17 = ((arg0 >> 8) & 255);
        int var19 = (arg0 & 255);
        int var22 = ((arg1 >> 16) & 255);
        int var25 = ((arg1 >> 8) & 255);
        int var27 = (arg1 & 255);
        float var29 = ((float) var14);
        float var47 = (((float) (var22 - var14)) * arg2);
        int var32 = (Math.round((var29 + var47)) << 16);
        float var50 = ((float) var17);
        float var62 = (((float) (var25 - var17)) * arg2);
        int var53 = (Math.round((var50 + var62)) << 8);
        int var33 = (var32 | var53);
        float var55 = ((float) var19);
        float var67 = (((float) (var27 - var19)) * arg2);
        int var57 = Math.round((var55 + var67));
        return (var33 | var57);
    }

    private static void drawPlayerHead(long arg0, Minecraft arg1, LivingEntity arg2, float arg3, float arg4, float arg5) {
        int var18 = 0;
        if ((arg2 instanceof AbstractClientPlayer)) {
            AbstractClientPlayer var25 = ((AbstractClientPlayer) arg2);
        } else {
            return;
        }
        var18 = 0;
        try {
            ClientAsset.Texture var29 = var25.getSkin().body();
            if (var29 != null) {
                TextureManager var32 = arg1.getTextureManager();
                Identifier var94 = var29.texturePath();
                AbstractTexture var33 = var32.getTexture(var94);
                if (var33 != null) {
                    if (var33.getTexture() != null) {
                        int var39 = RawFramebufferUtil.getGlId(var33.getTexture());
                        if (var39 > 0) {
                            try {
                                GL11.glBindTexture(3553, var39);
                                GL11.glTexParameteri(3553, 10241, 9728);
                                GL11.glTexParameteri(3553, 10240, 9728);
                            } catch (Throwable ignored) {
                            }
                            Map var44 = HEAD_IMAGE_CACHE;
                            Integer var99 = var39;
                            Integer var46 = ((Integer) var44.get(var99));
                            if (var46 != null) {
                                var18 = var46;
                            } else {
                                var18 = NanoVGGL3.nvglCreateImageFromHandle(arg0, var39, 64, 64, 32);
                                Map var91 = HEAD_IMAGE_CACHE;
                                Integer var103 = var39;
                                Integer var117 = var18;
                                Object var92 = var91.put(var103, var117);
                            }
                        }
                    }
                }
            }
        } catch (Throwable ignored) {
        }
        if (var18 <= 0) {
            NVGColor var85 = COL;
            int var101 = ThemeColors.getBorderRgb();
            setARGB(var85, var101, 1.0F);
            float var155 = (arg5 * 0.22F);
            NVGColor var160 = COL;
            ClarityDraw.fillRRect(arg0, arg3, arg4, arg5, arg5, var155, var160);
            return;
        }
        MemoryStack var52 = MemoryStack.stackPush();
        try {
            NVGPaint var54 = NVGPaint.malloc(var52);
            float var106 = (arg3 - arg5);
            float var119 = (arg4 - arg5);
            float var129 = (arg5 * 8.0F);
            float var139 = (arg5 * 8.0F);
            int var14 = var18;
            NVGPaint var57 = NanoVG.nvgImagePattern(arg0, var106, var119, var129, var139, 0.0F, var14, 1.0F, var54);
            NanoVG.nvgBeginPath(arg0);
            float var149 = (arg5 * 0.22F);
            NanoVG.nvgRoundedRect(arg0, arg3, arg4, arg5, arg5, var149);
            NanoVG.nvgFillPaint(arg0, var54);
            NanoVG.nvgFill(arg0);
            float var122 = (arg5 * 5.0F);
            float var110 = (arg3 - var122);
            float var124 = (arg4 - arg5);
            float var134 = (arg5 * 8.0F);
            float var143 = (arg5 * 8.0F);
            int var157 = var18;
            NVGPaint var68 = NanoVG.nvgImagePattern(arg0, var110, var124, var134, var143, 0.0F, var157, 1.0F, var54);
            NanoVG.nvgBeginPath(arg0);
            float var153 = (arg5 * 0.22F);
            NanoVG.nvgRoundedRect(arg0, arg3, arg4, arg5, arg5, var153);
            NanoVG.nvgFillPaint(arg0, var54);
            NanoVG.nvgFill(arg0);
        } catch (Throwable ignored) {
        }
        if (var52 != null) {
            var52.close();
        }
        return;
        if (var52 != null) {
            try {
                var52.close();
            } catch (Throwable ignored) {
            }
            var79.addSuppressed(var83);
        }
        throw var79;
    }

    public static boolean backdropReady() {
        int var1 = 0;
        if (BackdropBlur.textureId() != 0) {
            var1 = 1;
        } else {
            var1 = 0;
        }
        return var1;
    }

    public static int backdropStrength() {
        int var2 = 0;
        var2 = 0;
        HUD var7 = HUD.INSTANCE;
        if (var7 != null) {
            if (var7.isEnabled()) {
                if (var7.isBackground()) {
                    if (var7.isBlur()) {
                        int var15 = var2;
                        int var57 = var7.getBlurStrength();
                        var2 = Math.max(var15, var57);
                    }
                    TargetHud var17 = TargetHud.INSTANCE;
                    if (var17 != null) {
                        if (var17.isEnabled()) {
                            if (var17.isBlur()) {
                                if (Float.compare(TGT_ANIM, 0.01F) <= 0) {
                                    if (Modules.aimAssistCurrentTarget() != null) {
                                    }
                                }
                                int var26 = var2;
                                int var60 = var17.getBlurStrength();
                                var2 = Math.max(var26, var60);
                            }
                            KeybindList var28 = KeybindList.INSTANCE;
                            if (var28 != null) {
                                if (var28.isEnabled()) {
                                    if (var28.isBackground()) {
                                        if (var28.isBlur()) {
                                            int var36 = var2;
                                            int var62 = var28.getBlurStrength();
                                            var2 = Math.max(var36, var62);
                                        }
                                        MusicPlayer var38 = MusicPlayer.INSTANCE;
                                        if (var38 != null) {
                                            if (var38.isEnabled()) {
                                                if (var38.isBackground()) {
                                                    if (var38.isBlur()) {
                                                        if (Float.compare(MUSIC_ANIM, 0.01F) > 0) {
                                                            int var48 = var2;
                                                            int var65 = var38.getBlurStrength();
                                                            var2 = Math.max(var48, var65);
                                                        }
                                                        if (Notifications.isEligible()) {
                                                            int var51 = var2;
                                                            int var67 = Notifications.INSTANCE.getBlurStrength();
                                                            var2 = Math.max(var51, var67);
                                                        }
                                                        if (ArrayListModule.isBlur()) {
                                                            int var54 = var2;
                                                            int var68 = ArrayListModule.getBlurStrength();
                                                            var2 = Math.max(var54, var68);
                                                        }
                                                        return var2;
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

    public static boolean fillBlurRRect(long arg0, float arg1, float arg2, float arg3, float arg4, float arg5, float arg6, float arg7, float arg8, float arg9, float arg10, float arg11, float arg12, float arg13) {
        int var30 = backdropImage(arg0);
        if (var30 > 0) {
            if (Float.compare(arg13, 0.002F) <= 0) {
            }
        }
        return false;
        MemoryStack var34 = MemoryStack.stackPush();
        try {
            NVGPaint var36 = NVGPaint.malloc(var34);
            float var66 = Math.min(1.0F, arg13);
            NVGPaint var39 = NanoVG.nvgImagePattern(arg0, arg9, arg10, arg11, arg12, 0.0F, var30, var66, var36);
            NanoVG.nvgBeginPath(arg0);
            NanoVG.nvgRoundedRectVarying(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8);
            NanoVG.nvgFillPaint(arg0, var36);
            NanoVG.nvgFill(arg0);
        } catch (Throwable ignored) {
        }
        if (var34 != null) {
            var34.close();
            if (var34 != null) {
                try {
                    var34.close();
                } catch (Throwable ignored) {
                }
                var48.addSuppressed(var51);
            }
            throw var48;
        }
        return true;
    }

    public static boolean fillBlurRRect(long arg0, float arg1, float arg2, float arg3, float arg4, float arg5, float arg6) {
        float var20 = CANVAS_W;
        float var21 = CANVAS_H;
        return fillBlurRRect(arg0, arg1, arg2, arg3, arg4, arg5, arg5, arg5, arg5, 0.0F, 0.0F, var20, var21, arg6);
    }

    private static String fmtTime(double arg0) {
        String var26 = null;
        double var34 = Math.floor(arg0);
        int var15 = ((int) Math.max(0.0, var34));
        int var17 = (var15 / 3600);
        int var20 = ((var15 % 3600) / 60);
        int var22 = (var15 % 60);
        if (var17 > 0) {
            Object[] var33 = new Object[3];
            Integer var44 = var17;
            var33[0] = var44;
            Integer var46 = var20;
            var33[1] = var46;
            Integer var48 = var22;
            var33[2] = var48;
            var26 = String.format("%d:%02d:%02d", var33);
        } else {
            Object[] var31 = new Object[2];
            Integer var40 = var20;
            var31[0] = var40;
            Integer var42 = var22;
            var31[1] = var42;
            var26 = String.format("%d:%02d", var31);
        }
        return var26;
    }

    private static String ellipsize(long arg0, String arg1, float arg2, String arg3, float arg4) {
        int var13 = 0;
        if (Float.compare(ClarityDraw.textWidth(arg0, arg1, arg2, 0.0F, arg3), arg4) <= 0) {
            return arg3;
        }
        var13 = arg3.length();
        while (true) {
            if (var13 <= 1) break;
            var13 = (var13 + -1);
            int var34 = var13;
            String var24 = (arg3.substring(0, var34).stripTrailing()) + "\u2026";
            if (Float.compare(ClarityDraw.textWidth(arg0, arg1, arg2, 0.0F, var24), arg4) > 0) continue;
        }
        return var24;
        return "\u2026";
    }

    private static void drawScrollText(long arg0, String arg1, float arg2, int arg3, float arg4, float arg5, float arg6, float arg7, String arg8, boolean arg9) {
        float var176 = 0.0F;
        float var38 = ClarityDraw.textWidth(arg0, arg1, arg2, 0.0F, arg8);
        if (Float.compare(var38, arg6) <= 0) {
            float var97 = ClarityDraw.text(arg0, arg1, arg2, 0.0F, arg3, 17, arg4, arg5, arg8);
            return;
        }
        if (!arg9) {
            String var173 = ellipsize(arg0, arg1, arg2, arg8, arg6);
            float var44 = ClarityDraw.text(arg0, arg1, arg2, 0.0F, arg3, 17, arg4, arg5, var173);
            return;
        }
        double var49 = (((double) System.nanoTime()) / 1.0E9);
        float var53 = ((var38 - arg6) + 14.0F);
        float var57 = (var53 / 28.0F);
        float var59 = (1.6F * 2.0F);
        float var103 = (var57 * 2.0F);
        double var61 = ((double) (var59 + var103));
        double var65 = (var49 % var61);
        double var120 = ((double) 1.6F);
        if (Double.compare(var65, var120) < 0) {
            var176 = 0.0F;
        } else {
            double var126 = ((double) (1.6F + var57));
            if (Double.compare(var65, var126) < 0) {
                double var135 = ((double) 1.6F);
                var176 = (((float) (var65 - var135)) * 28.0F);
            } else {
                double var131 = ((double) ((1.6F * 2.0F) + var57));
                if (Double.compare(var65, var131) < 0) {
                    var176 = var53;
                } else {
                    double var147 = ((double) (1.6F * 2.0F));
                    double var106 = (var65 - var147);
                    double var150 = ((double) var57);
                    float var111 = (((float) (var106 - var150)) * 28.0F);
                    var176 = (var53 - var111);
                }
                NanoVG.nvgSave(arg0);
                float var155 = (arg7 * 0.5F);
                float var140 = (arg5 - var155);
                NanoVG.nvgIntersectScissor(arg0, arg4, var140, arg6, arg7);
                float var170 = var176;
                float var168 = (arg4 - var170);
                float var77 = ClarityDraw.text(arg0, arg1, arg2, 0.0F, arg3, 17, var168, arg5, arg8);
                NanoVG.nvgRestore(arg0);
                return;
            }
        }
    }

    private static void fillCol(long arg0, int arg1) {
        NVGColor var6 = COL;
        NVGColor var9 = ClarityNvg.argb(arg1, var6);
        NanoVG.nvgFillColor(arg0, var9);
        NanoVG.nvgFill(arg0);
    }

    private static void drawNote(long arg0, float arg1, float arg2, float arg3, int arg4) {
        NanoVG.nvgBeginPath(arg0);
        float var45 = (arg3 * 0.16F);
        float var34 = (arg1 - var45);
        float var65 = (arg3 * 0.28F);
        float var47 = (arg2 + var65);
        float var67 = (arg3 * 0.2F);
        float var88 = (arg3 * 0.15F);
        NanoVG.nvgEllipse(arg0, var34, var47, var67, var88);
        fillCol(arg0, arg4);
        float var49 = (arg3 * 0.02F);
        float var37 = (arg1 + var49);
        float var70 = (arg3 * 0.36F);
        float var51 = (arg2 - var70);
        float var72 = (arg3 * 0.07F);
        float var92 = (arg3 * 0.66F);
        float var106 = (arg3 * 0.03F);
        ClarityDraw.fillRRect(arg0, var37, var51, var72, var92, var106, arg4);
        NanoVG.nvgBeginPath(arg0);
        float var53 = (arg3 * 0.09F);
        float var39 = (arg1 + var53);
        float var75 = (arg3 * 0.36F);
        float var55 = (arg2 - var75);
        NanoVG.nvgMoveTo(arg0, var39, var55);
        float var57 = (arg3 * 0.36F);
        float var41 = (arg1 + var57);
        float var78 = (arg3 * 0.28F);
        float var59 = (arg2 - var78);
        float var96 = (arg3 * 0.4F);
        float var80 = (arg1 + var96);
        float var109 = (arg3 * 0.02F);
        float var98 = (arg2 - var109);
        float var120 = (arg3 * 0.26F);
        float var111 = (arg1 + var120);
        float var129 = (arg3 * 0.12F);
        float var122 = (arg2 + var129);
        NanoVG.nvgBezierTo(arg0, var41, var59, var80, var98, var111, var122);
        float var61 = (arg3 * 0.31F);
        float var43 = (arg1 + var61);
        float var83 = (arg3 * 0.06F);
        float var63 = (arg2 - var83);
        float var101 = (arg3 * 0.22F);
        float var85 = (arg1 + var101);
        float var114 = (arg3 * 0.16F);
        float var103 = (arg2 - var114);
        float var125 = (arg3 * 0.09F);
        float var116 = (arg1 + var125);
        float var132 = (arg3 * 0.13F);
        float var127 = (arg2 - var132);
        NanoVG.nvgBezierTo(arg0, var43, var63, var85, var103, var116, var127);
        NanoVG.nvgClosePath(arg0);
        fillCol(arg0, arg4);
    }

    private static void drawMusicArt(long arg0, float arg1, float arg2, float arg3, float arg4, MediaBridge.Snapshot arg5, int arg6, int arg7, float arg8, boolean arg9) {
        if (arg5.active) {
            if (arg5.art != null) {
                if (musicImg > 0) {
                    MemoryStack var35 = MemoryStack.stackPush();
                    try {
                        IntBuffer var37 = var35.mallocInt(1);
                        IntBuffer var39 = var35.mallocInt(1);
                        int var13 = musicImg;
                        NanoVG.nvgImageSize(arg0, var13, var37, var39);
                        int var110 = var37.get(0);
                        float var44 = ((float) Math.max(1, var110));
                        int var112 = var39.get(0);
                        float var47 = ((float) Math.max(1, var112));
                    } catch (Throwable ignored) {
                    }
                    if (var35 != null) {
                        var35.close();
                        if (var35 != null) {
                            try {
                                var35.close();
                            } catch (Throwable ignored) {
                            }
                            var48.addSuppressed(var52);
                        }
                        throw var48;
                    }
                    float var57 = (arg3 / var44);
                    float var116 = (arg3 / var47);
                    float var58 = Math.max(var57, var116);
                    float var60 = (var44 * var58);
                    float var62 = (var47 * var58);
                    MemoryStack var63 = MemoryStack.stackPush();
                    try {
                        NVGPaint var65 = NVGPaint.malloc(var63);
                        float var147 = ((var60 - arg3) * 0.5F);
                        float var131 = (arg1 - var147);
                        float var171 = ((var62 - arg3) * 0.5F);
                        float var149 = (arg2 - var171);
                        int var18 = musicImg;
                        float var228 = Math.min(1.0F, arg8);
                        NVGPaint var68 = NanoVG.nvgImagePattern(arg0, var131, var149, var60, var62, 0.0F, var18, var228, var65);
                        NanoVG.nvgBeginPath(arg0);
                        NanoVG.nvgRoundedRect(arg0, arg1, arg2, arg3, arg3, arg4);
                        NanoVG.nvgFillPaint(arg0, var65);
                        NanoVG.nvgFill(arg0);
                    } catch (Throwable ignored) {
                    }
                    if (var63 != null) {
                        var63.close();
                    }
                } else {
                    int var226 = tintA(mix(arg6, arg7, 0.22F), arg8);
                    ClarityDraw.fillRRect(arg0, arg1, arg2, arg3, arg3, arg4, var226);
                    float var164 = (arg3 * 0.5F);
                    float var144 = (arg1 + var164);
                    float var188 = (arg3 * 0.5F);
                    float var166 = (arg2 + var188);
                    float var190 = (arg3 * 0.5F);
                    float var215 = (0.9F * arg8);
                    int var205 = tintA(arg7, var215);
                    drawNote(arg0, var144, var166, var190, var205);
                }
                if (arg9) {
                    if (arg5.active) {
                        float var120 = (arg3 * 0.5F);
                        float var83 = (arg1 + var120);
                        float var122 = (arg3 * 0.5F);
                        float var85 = (arg2 + var122);
                        float var87 = (arg3 * 0.24F);
                        float var137 = (var83 - var87);
                        float var153 = (var85 - var87);
                        float var176 = (var87 * 2.0F);
                        float var196 = (var87 * 2.0F);
                        float var230 = (0.55F * arg8);
                        int var217 = tintA(0, var230);
                        ClarityDraw.fillRRect(arg0, var137, var153, var176, var196, var87, var217);
                        float var91 = (var87 * 0.28F);
                        float var93 = (var87 * 0.9F);
                        float var155 = (var91 * 1.45F);
                        float var139 = (var83 - var155);
                        float var179 = (var93 * 0.5F);
                        float var157 = (var85 - var179);
                        float var210 = (var91 * 0.3F);
                        float var232 = (0.95F * arg8);
                        int var220 = tintA(16777215, var232);
                        ClarityDraw.fillRRect(arg0, var139, var157, var91, var93, var210, var220);
                        float var159 = (var91 * 0.45F);
                        float var141 = (var83 + var159);
                        float var183 = (var93 * 0.5F);
                        float var161 = (var85 - var183);
                        float var212 = (var91 * 0.3F);
                        float var234 = (0.95F * arg8);
                        int var223 = tintA(16777215, var234);
                        ClarityDraw.fillRRect(arg0, var141, var161, var91, var93, var212, var223);
                    }
                    return;
                    if (var63 != null) {
                        try {
                            var63.close();
                        } catch (Throwable ignored) {
                        }
                        var98.addSuppressed(var101);
                    }
                    throw var98;
                }
            }
        }
    }

    private static void drawMusicPlayer(long arg0, Minecraft arg1, float arg2, float arg3) {
        int var23 = 0;
        String var35 = null;
        String var36 = null;
        int var54 = 0;
        float var58 = 0.0F;
        String var140 = null;
        String var151 = null;
        float var167 = 0.0F;
        int var229 = 0;
        float var281 = 0.0F;
        float var379 = 0.0F;
        String var383 = null;
        float var419 = 0.0F;
        float var420 = 0.0F;
        float var421 = 0.0F;
        int var422 = 0;
        String var425 = null;
        String var430 = null;
        int var433 = 0;
        int var436 = 0;
        int var437 = 0;
        float var439 = 0.0F;
        float var440 = 0.0F;
        String var441 = null;
        int var442 = 0;
        float var443 = 0.0F;
        int var444 = 0;
        int var445 = 0;
        int var446 = 0;
        float var447 = 0.0F;
        int var448 = 0;
        int var449 = 0;
        int var529 = 0;
        float var541 = 0.0F;
        float var549 = 0.0F;
        float var550 = 0.0F;
        float var551 = 0.0F;
        float var566 = 0.0F;
        float var622 = 0.0F;
        float var625 = 0.0F;
        float var626 = 0.0F;
        float var627 = 0.0F;
        float var822 = 0.0F;
        float var863 = 0.0F;
        float var865 = 0.0F;
        float var867 = 0.0F;
        float var870 = 0.0F;
        float var873 = 0.0F;
        int var876 = 0;
        MusicPlayer var5 = MusicPlayer.INSTANCE;
        if (var5 == null) {
        } else {
            boolean var67 = var5.isEnabled();
            if (!var67) {
            }
        }
        MUSIC_ANIM = 0.0F;
        return;
        MediaBridge.Snapshot var68 = MediaBridge.get();
        boolean var70 = var68.active;
        if (!var70) {
            var445 = 1;
        } else {
            var445 = 0;
        }
        int var22 = var445;
        if (var22 != 0) {
            boolean var73 = var5.isHideWhenIdle();
            if (!var73) {
            }
        }
        if (var22 == 0) {
            boolean var76 = var68.playing;
            if (!var76) {
                boolean var78 = var5.isHideWhenPaused();
                if (!var78) {
                }
            }
            var437 = 1;
            var437 = 0;
            var23 = var437;
            boolean var79 = HudLayout.isEditing();
            if (var79) {
                var23 = 1;
            }
            float var81 = MUSIC_ANIM;
            int var6 = var23;
            if (var6 != 0) {
                var549 = 1.0F;
            } else {
                var549 = 0.0F;
            }
            float var7 = MUSIC_ANIM;
            float var450 = (var549 - var7);
            float var552 = 0.18F;
            float var451 = (var450 * var552);
            float var82 = (var81 + var451);
            MUSIC_ANIM = var82;
            float var83 = MUSIC_ANIM;
            int var84 = Float.compare(var83, 0.01F);
            if (var84 < 0) {
                return;
            }
            int var86 = var68.artVersion;
            int var453 = musicImgVer;
            if (var86 != var453) {
                int var87 = musicImg;
                if (var87 > 0) {
                    int var553 = musicImg;
                    NanoVG.nvgDeleteImage(arg0, var553);
                }
                musicImg = 0;
                int var92 = var68.artVersion;
                musicImgVer = var92;
                byte[] var94 = var68.art;
                if (var94 != null) {
                    byte[] var96 = var68.art;
                    int var97 = var96.length;
                    if (var97 > 0) {
                        try {
                            byte[] var99 = var68.art;
                            int var100 = var99.length;
                            ByteBuffer var101 = BufferUtils.createByteBuffer(var100);
                            byte[] var455 = var68.art;
                            ByteBuffer var103 = var101.put(var455);
                            ByteBuffer var104 = var103.flip();
                            int var554 = 1;
                            int var107 = NanoVG.nvgCreateImageMem(arg0, var554, var101);
                            if (var107 > 0) {
                                var436 = var107;
                            } else {
                                var436 = 0;
                            }
                            musicImg = var436;
                        } catch (Throwable ignored) {
                        }
                        Object var861 = var434;
                        musicImg = 0;
                    }
                    float var110 = var5.getScale();
                    float var860 = var110;
                    float var112 = var5.getOpacity();
                    float var456 = MUSIC_ANIM;
                    float var113 = (var112 * var456);
                    float var862 = var113;
                    String var458 = var5.getLayout();
                    boolean var115 = "Compact".equals(var458);
                    boolean var117 = var5.isThemeColors();
                    if (var22 == 0) {
                        boolean var120 = var68.playing;
                        if (!var120) {
                            var449 = 1;
                        } else {
                            var449 = 0;
                        }
                        int var28 = var449;
                        if (var28 != 0) {
                            boolean var123 = var5.isDimWhenPaused();
                            if (var123) {
                                var443 = 0.62F;
                            } else {
                            }
                        }
                        var443 = 1.0F;
                        float var29 = var443;
                        if (var117) {
                            var448 = ThemeColors.getAccentRgb();
                        } else {
                            var448 = var5.getAccentColor();
                        }
                        int var30 = var448;
                        if (var117) {
                            var433 = ThemeColors.getTextRgb();
                        } else {
                            var433 = var5.getTextColor();
                        }
                        int var31 = var433;
                        int var127 = ThemeColors.getMutedRgb();
                        if (var117) {
                            int var129 = ThemeColors.getSurfaceRgb();
                            float var555 = 0.02F;
                            var446 = mix(var129, var30, var555);
                        } else {
                            var446 = var5.getBackgroundColor();
                        }
                        int var33 = var446;
                        int var130 = ThemeColors.getBorderRgb();
                        if (var22 != 0) {
                            var35 = "Nothing playing";
                            String var428 = var68.error;
                            if (var428 != null) {
                                var430 = var68.error;
                            }
                        } else {
                            String var133 = var68.title;
                            if (var133 != null) {
                                String var135 = var68.title;
                                boolean var136 = var135.isBlank();
                                if (var136) {
                                }
                            }
                            var425 = "Unknown title";
                            var425 = var68.title;
                            var35 = var425;
                            String var139 = var68.artist;
                            if (var139 == null) {
                                var140 = "";
                            } else {
                                var140 = var68.artist;
                            }
                            var36 = var140;
                            boolean var142 = var5.isShowSource();
                            if (var142) {
                                String var144 = var68.app;
                                if (var144 != null) {
                                    String var146 = var68.app;
                                    boolean var147 = var146.isEmpty();
                                    if (!var147) {
                                        String var148 = var36;
                                        boolean var149 = var148.isEmpty();
                                        if (var149) {
                                            var151 = var68.app;
                                            var430 = "Play something in Spotify or SoundCloud";
                                        }
                                    }
                                }
                            }
                        }
                        var36 = var430;
                        String var423 = var36;
                        MediaBridge.Snapshot var547 = var68;
                        String var548 = var547.app;
                        var151 = (var423) + " \u00b7 " + (var548);
                        var36 = var151;
                        boolean var153 = var5.isShowArtist();
                        if (var153) {
                            String var154 = var36;
                            boolean var155 = var154.isEmpty();
                            if (!var155) {
                                var442 = 1;
                            } else {
                                var442 = 0;
                            }
                            int var37 = var442;
                            boolean var157 = var5.isShowProgressBar();
                            if (var157) {
                                if (var22 == 0) {
                                    var422 = 1;
                                }
                            } else {
                                var422 = 0;
                            }
                            int var38 = var422;
                            boolean var160 = var5.isShowTimes();
                            if (var160) {
                                if (var22 == 0) {
                                    var444 = 1;
                                } else {
                                }
                            }
                            var444 = 0;
                            int var39 = var444;
                            if (var115) {
                                var421 = 8.0F;
                            } else {
                                var421 = 10.0F;
                            }
                            float var163 = (var421 * var860);
                            if (var115) {
                                var420 = 12.0F;
                            } else {
                                var420 = 13.0F;
                            }
                            float var165 = (var420 * var860);
                            if (var115) {
                                var167 = 10.0F;
                            } else {
                                var167 = 11.0F;
                            }
                            float var462 = var860;
                            float var168 = (var167 * var462);
                            float var463 = var860;
                            float var170 = (10.0F * var463);
                            if (var115) {
                                var447 = 15.0F;
                            } else {
                                var447 = 17.0F;
                            }
                            float var464 = var860;
                            float var172 = (var447 * var464);
                            if (var115) {
                                var419 = 13.0F;
                            } else {
                                var419 = 14.0F;
                            }
                            float var465 = var860;
                            float var174 = (var419 * var465);
                            float var466 = var860;
                            float var176 = (3.0F * var466);
                            if (var115) {
                                int var467 = var37;
                                if (var467 == 0) {
                                    int var468 = var39;
                                    if (var468 != 0) {
                                    }
                                }
                                var541 = var174;
                                var541 = 0.0F;
                                float var179 = (var172 + var541);
                                var863 = var179;
                            } else {
                                int var542 = var37;
                                if (var542 != 0) {
                                    var551 = var174;
                                } else {
                                    var551 = 0.0F;
                                }
                                float var414 = (var172 + var551);
                                var863 = var414;
                                if (var38 == 0) {
                                    if (var39 != 0) {
                                    }
                                }
                                float var417 = var863;
                                float var543 = 6.0F;
                                float var619 = var860;
                                float var544 = (var543 * var619);
                                int var620 = var38;
                                if (var620 != 0) {
                                    var627 = var176;
                                } else {
                                    var627 = 0.0F;
                                }
                                float var545 = (var544 + var627);
                                int var621 = var39;
                                if (var621 != 0) {
                                    int var623 = var38;
                                    if (var623 != 0) {
                                        float var624 = 4.0F;
                                        float var659 = var860;
                                        var625 = (var624 * var659);
                                    } else {
                                        var625 = 0.0F;
                                    }
                                    float var657 = 11.0F;
                                    float var693 = var860;
                                    float var658 = (var657 * var693);
                                    var622 = (var625 + var658);
                                } else {
                                    var622 = 0.0F;
                                }
                                float var546 = (var545 + var622);
                                float var418 = (var417 + var546);
                                var863 = var418;
                            }
                            boolean var181 = var5.isShowArt();
                            if (var181) {
                                if (var115) {
                                    float var539 = var860;
                                    float var412 = (30.0F * var539);
                                    float var540 = var863;
                                    var440 = Math.max(var412, var540);
                                } else {
                                    float var537 = var860;
                                    float var410 = (44.0F * var537);
                                    float var538 = var863;
                                    var440 = Math.max(var410, var538);
                                }
                            } else {
                                var440 = 0.0F;
                            }
                            float var47 = var440;
                            float var469 = var863;
                            float var183 = Math.max(var47, var469);
                            float var185 = var5.getWidth();
                            float var470 = var860;
                            float var186 = (var185 * var470);
                            float var471 = 2.0F;
                            float var188 = (var163 * var471);
                            float var472 = var183;
                            float var189 = (var188 + var472);
                            int var473 = var115;
                            if (var473 != 0) {
                                int var474 = var38;
                                if (var474 != 0) {
                                    float var536 = 4.0F;
                                    float var618 = var860;
                                    var550 = (var536 * var618);
                                }
                            } else {
                                var550 = 0.0F;
                            }
                            float var190 = (var189 + var550);
                            int var192 = var5.getXOffset();
                            float var193 = ((float) var192);
                            int var195 = var5.getYOffset();
                            float var196 = ((float) var195);
                            String var198 = var5.getAnchor();
                            var54 = -1;
                            int var201 = var198.hashCode();
                            switch (var201) {
                                case 287355729 -> {}
                                case 926500839 -> {}
                                case 999535836 -> {}
                                default -> {}
                            }
                            String var535 = "Top Right";
                            boolean var406 = var198.equals(var535);
                            if (var406) {
                                var54 = 0;
                                String var475 = "Bottom Left";
                                boolean var203 = var198.equals(var475);
                                if (var203) {
                                    var54 = 1;
                                    String var534 = "Bottom Right";
                                    boolean var403 = var198.equals(var534);
                                    if (var403) {
                                        var54 = 2;
                                    }
                                    int var205 = var54;
                                    switch (var205) {
                                        case 0 -> {}
                                        case 1 -> {}
                                        case 2 -> {}
                                        default -> {}
                                    }
                                    float var476 = var186;
                                    float var207 = (arg2 - var476);
                                    float var477 = var193;
                                    float var208 = (var207 - var477);
                                    var865 = var208;
                                    float var478 = var190;
                                    float var210 = (arg3 - var478);
                                    float var479 = var196;
                                    float var211 = (var210 - var479);
                                    var867 = var211;
                                    var865 = var193;
                                    float var530 = var190;
                                    float var394 = (arg3 - var530);
                                    float var531 = var196;
                                    float var395 = (var394 - var531);
                                    var867 = var395;
                                    float var532 = var186;
                                    float var397 = (arg2 - var532);
                                    float var533 = var193;
                                    float var398 = (var397 - var533);
                                    var865 = var398;
                                    var867 = var196;
                                    var865 = var193;
                                    var867 = var196;
                                    float var212 = var865;
                                    int var213 = Math.round(var212);
                                    float var214 = ((float) var213);
                                    float var864 = var214;
                                    float var215 = var867;
                                    int var216 = Math.round(var215);
                                    float var217 = ((float) var216);
                                    float var866 = var217;
                                    float var480 = var864;
                                    float var556 = var866;
                                    float var628 = var186;
                                    HudLayout.report("Music Player", var480, var556, var628, var190);
                                    float var220 = var5.getCornerRadius();
                                    float var481 = var860;
                                    float var221 = (var220 * var481);
                                    float var868 = var221;
                                    boolean var223 = var5.isBackground();
                                    if (var223) {
                                        boolean var225 = var5.isBlur();
                                        if (var225) {
                                            float var557 = var864;
                                            float var629 = var866;
                                            float var660 = var186;
                                            boolean var228 = fillBlurRRect(arg0, var557, var629, var660, var190, var868, var862);
                                            if (var228) {
                                                var229 = 1;
                                            } else {
                                                var229 = 0;
                                            }
                                            int var869 = var229;
                                            float var558 = var864;
                                            float var630 = var866;
                                            float var661 = var186;
                                            float var694 = var190;
                                            float var726 = var868;
                                            int var756 = var33;
                                            if (var869 != 0) {
                                                MusicPlayer var789 = var5;
                                                var822 = var789.getBlurTint();
                                            } else {
                                                MusicPlayer var821 = var5;
                                                var822 = var821.getBackgroundOpacity();
                                            }
                                            float var790 = (var822 * var862);
                                            int var757 = tintA(var756, var790);
                                            ClarityDraw.fillRRect(arg0, var558, var630, var661, var694, var726, var757);
                                            boolean var233 = var5.isBorder();
                                            if (var233) {
                                                float var559 = var864;
                                                float var631 = var866;
                                                float var662 = var186;
                                                float var695 = var190;
                                                float var727 = var868;
                                                float var758 = 1.0F;
                                                int var791 = var130;
                                                int var823 = var30;
                                                int var792 = mix(var791, var823, 0.1F);
                                                float var824 = var862;
                                                int var793 = tintA(var792, var824);
                                                ClarityDraw.strokeRRect(arg0, var559, var631, var662, var695, var727, var758, var793);
                                            }
                                            boolean var237 = var5.isHighlight();
                                            if (var237) {
                                                int var238 = ThemeColors.getSurfaceRgb();
                                                boolean var482 = ThemeColors.isLight();
                                                if (var482) {
                                                    var529 = 0;
                                                } else {
                                                    var529 = 16777215;
                                                }
                                                float var560 = 0.12F;
                                                int var239 = mix(var238, var529, var560);
                                                float var561 = var864;
                                                float var632 = var866;
                                                float var663 = var186;
                                                float var696 = var190;
                                                float var728 = var868;
                                                int var759 = var239;
                                                float var794 = 0.66F;
                                                float var825 = var862;
                                                float var795 = (var794 * var825);
                                                int var760 = tintA(var759, var795);
                                                ClarityDraw.topHighlight(arg0, var561, var632, var663, var696, var728, var760);
                                            }
                                            float var483 = var163;
                                            float var243 = (var864 + var483);
                                            var870 = var243;
                                            float var484 = var163;
                                            float var245 = (var866 + var484);
                                            float var871 = var245;
                                            float var485 = 0.0F;
                                            int var247 = Float.compare(var47, var485);
                                            if (var247 > 0) {
                                                float var486 = 0.7F;
                                                float var249 = (var868 * var486);
                                                float var487 = var47;
                                                float var562 = 0.22F;
                                                float var488 = (var487 * var562);
                                                float var250 = Math.min(var249, var488);
                                                float var563 = var864;
                                                float var633 = var163;
                                                float var564 = (var563 + var633);
                                                float var634 = var871;
                                                float var664 = var183;
                                                float var697 = var47;
                                                float var665 = (var664 - var697);
                                                float var698 = 0.5F;
                                                float var666 = (var665 * var698);
                                                float var635 = (var634 + var666);
                                                float var667 = var47;
                                                float var699 = var250;
                                                MediaBridge.Snapshot var729 = var68;
                                                int var761 = var33;
                                                int var796 = var30;
                                                float var826 = var862;
                                                float var851 = var29;
                                                float var827 = (var826 * var851);
                                                int var852 = var28;
                                                drawMusicArt(arg0, var564, var635, var667, var699, var729, var761, var796, var827, var852);
                                                float var253 = var870;
                                                float var489 = var47;
                                                int var565 = var115;
                                                if (var565 != 0) {
                                                    var566 = 8.0F;
                                                } else {
                                                    var566 = 10.0F;
                                                }
                                                float var636 = var860;
                                                float var567 = (var566 * var636);
                                                float var490 = (var489 + var567);
                                                float var254 = (var253 + var490);
                                                var870 = var254;
                                            }
                                            float var491 = var186;
                                            float var256 = (var864 + var491);
                                            float var492 = var163;
                                            float var257 = (var256 - var492);
                                            float var493 = var870;
                                            float var258 = (var257 - var493);
                                            float var872 = var258;
                                            float var494 = var183;
                                            float var568 = var863;
                                            float var495 = (var494 - var568);
                                            float var569 = 0.5F;
                                            float var496 = (var495 * var569);
                                            float var260 = (var871 + var496);
                                            String var570 = "inter-sb";
                                            float var637 = var165;
                                            int var668 = var31;
                                            float var700 = var862;
                                            float var730 = var29;
                                            float var701 = (var700 * var730);
                                            int var669 = tintA(var668, var701);
                                            float var702 = var870;
                                            float var731 = var260;
                                            float var762 = var172;
                                            float var797 = 0.5F;
                                            float var763 = (var762 * var797);
                                            float var732 = (var731 + var763);
                                            float var764 = var872;
                                            float var798 = var172;
                                            String var828 = var35;
                                            MusicPlayer var853 = var5;
                                            boolean var854 = var853.isScrollLongTitles();
                                            drawScrollText(arg0, var570, var637, var669, var702, var732, var764, var798, var828, var854);
                                            if (var115) {
                                                float var511 = var172;
                                                float var327 = (var260 + var511);
                                                float var512 = var174;
                                                float var595 = 0.5F;
                                                float var513 = (var512 * var595);
                                                float var328 = (var327 + var513);
                                                float var874 = var328;
                                                var876 = null;
                                                if (var39 != 0) {
                                                    MusicPlayer var514 = var5;
                                                    String var515 = var514.getTimeMode();
                                                    boolean var332 = "Elapsed / Total".equals(var515);
                                                    if (var332) {
                                                        double var334 = var68.durSec;
                                                        double var596 = 0.0;
                                                        int var336 = Double.compare(var334, var596);
                                                        if (var336 > 0) {
                                                            double var381 = var68.durSec;
                                                            var383 = fmtTime(var381);
                                                        } else {
                                                            var383 = "--:--";
                                                        }
                                                    } else {
                                                        double var385 = var68.durSec;
                                                        double var616 = 0.0;
                                                        int var387 = Double.compare(var385, var616);
                                                        if (var387 > 0) {
                                                            double var389 = var68.remaining();
                                                            String var391 = fmtTime(var389);
                                                            var383 = "-" + (var391);
                                                        } else {
                                                            var383 = "--:--";
                                                        }
                                                        String var878 = var383;
                                                        double var338 = var68.position();
                                                        String var340 = fmtTime(var338);
                                                        String var516 = var878;
                                                        String var341 = (var340) + " / " + (var516);
                                                        var876 = var341;
                                                    }
                                                }
                                                Object var342 = var876;
                                                if (var342 != null) {
                                                    String var615 = "inter-med";
                                                    float var656 = var170;
                                                    float var692 = 0.0F;
                                                    Object var725 = var876;
                                                    var379 = ClarityDraw.textWidth(arg0, var615, var656, var692, var725);
                                                } else {
                                                    var379 = 0.0F;
                                                }
                                                float var879 = var379;
                                                if (var37 != 0) {
                                                    float var517 = var872;
                                                    float var598 = var879;
                                                    float var649 = 0.0F;
                                                    int var599 = Float.compare(var598, var649);
                                                    if (var599 > 0) {
                                                        float var614 = var879;
                                                        float var654 = 8.0F;
                                                        float var691 = var860;
                                                        float var655 = (var654 * var691);
                                                        var626 = (var614 + var655);
                                                    } else {
                                                        var626 = 0.0F;
                                                    }
                                                    float var518 = (var517 - var626);
                                                    float var345 = Math.max(10.0F, var518);
                                                    float var880 = var345;
                                                    String var600 = "inter-med";
                                                    float var650 = var168;
                                                    float var683 = 0.0F;
                                                    int var715 = var127;
                                                    float var745 = var862;
                                                    int var716 = tintA(var715, var745);
                                                    int var746 = 17;
                                                    float var778 = var870;
                                                    float var812 = var874;
                                                    long var844 = arg0;
                                                    String var856 = "inter-med";
                                                    float var857 = var168;
                                                    String var858 = var36;
                                                    float var859 = var880;
                                                    String var846 = ellipsize(var844, var856, var857, var858, var859);
                                                    float var348 = ClarityDraw.text(arg0, var600, var650, var683, var716, var746, var778, var812, var846);
                                                }
                                                Object var349 = var876;
                                                if (var349 != null) {
                                                    String var601 = "inter-med";
                                                    float var651 = var170;
                                                    float var684 = 0.0F;
                                                    int var717 = var127;
                                                    float var747 = var862;
                                                    int var718 = tintA(var717, var747);
                                                    int var748 = 20;
                                                    float var779 = var864;
                                                    float var813 = var186;
                                                    float var780 = (var779 + var813);
                                                    float var814 = var163;
                                                    float var781 = (var780 - var814);
                                                    float var815 = var874;
                                                    Object var847 = var876;
                                                    float var352 = ClarityDraw.text(arg0, var601, var651, var684, var718, var748, var781, var815, var847);
                                                }
                                                if (var38 != 0) {
                                                    float var519 = var163;
                                                    float var355 = (var864 + var519);
                                                    float var881 = var355;
                                                    float var520 = var163;
                                                    float var602 = 2.0F;
                                                    float var521 = (var520 * var602);
                                                    float var357 = (var186 - var521);
                                                    float var522 = var190;
                                                    float var359 = (var866 + var522);
                                                    float var523 = var163;
                                                    float var603 = 0.5F;
                                                    float var524 = (var523 * var603);
                                                    float var360 = (var359 - var524);
                                                    float var525 = var176;
                                                    float var604 = 0.5F;
                                                    float var526 = (var525 * var604);
                                                    float var361 = (var360 - var526);
                                                    float var527 = 1.0F;
                                                    float var362 = (var361 - var527);
                                                    double var364 = var68.durSec;
                                                    double var605 = 0.0;
                                                    int var366 = Double.compare(var364, var605);
                                                    if (var366 > 0) {
                                                        MediaBridge.Snapshot var609 = var68;
                                                        double var610 = var609.position();
                                                        MediaBridge.Snapshot var688 = var68;
                                                        double var689 = var688.durSec;
                                                        double var612 = (var610 / var689);
                                                        double var375 = Math.min(1.0, var612);
                                                        var439 = ((float) var375);
                                                    } else {
                                                        var439 = 0.0F;
                                                    }
                                                    float var64 = var439;
                                                    float var607 = var881;
                                                    float var652 = var362;
                                                    float var685 = var357;
                                                    float var719 = var176;
                                                    float var749 = 0.8F;
                                                    float var720 = (var719 * var749);
                                                    float var750 = var176;
                                                    float var782 = 0.4F;
                                                    float var751 = (var750 * var782);
                                                    int var783 = var130;
                                                    int var816 = var31;
                                                    float var848 = 0.15F;
                                                    int var784 = mix(var783, var816, var848);
                                                    float var817 = 0.9F;
                                                    float var849 = var862;
                                                    float var818 = (var817 * var849);
                                                    int var785 = tintA(var784, var818);
                                                    ClarityDraw.fillRRect(arg0, var607, var652, var685, var720, var751, var785);
                                                    float var528 = 0.0F;
                                                    int var370 = Float.compare(var64, var528);
                                                    if (var370 > 0) {
                                                        float var608 = var881;
                                                        float var653 = var362;
                                                        float var686 = var176;
                                                        float var721 = var357;
                                                        float var752 = var64;
                                                        float var722 = (var721 * var752);
                                                        float var687 = Math.max(var686, var722);
                                                        float var723 = var176;
                                                        float var753 = 0.8F;
                                                        float var724 = (var723 * var753);
                                                        float var754 = var176;
                                                        float var786 = 0.4F;
                                                        float var755 = (var754 * var786);
                                                        int var787 = var30;
                                                        float var819 = var862;
                                                        float var850 = var29;
                                                        float var820 = (var819 * var850);
                                                        int var788 = tintA(var787, var820);
                                                        ClarityDraw.fillRRect(arg0, var608, var653, var687, var724, var755, var788);
                                                    }
                                                    return;
                                                }
                                            }
                                            float var497 = var172;
                                            float var265 = (var260 + var497);
                                            var58 = var265;
                                            if (var37 != 0) {
                                                String var571 = "inter-med";
                                                float var638 = var168;
                                                float var670 = 0.0F;
                                                int var703 = var127;
                                                float var733 = var862;
                                                int var704 = tintA(var703, var733);
                                                int var734 = 17;
                                                float var765 = var870;
                                                float var799 = var58;
                                                float var829 = var174;
                                                float var855 = 0.5F;
                                                float var830 = (var829 * var855);
                                                float var800 = (var799 + var830);
                                                long var831 = arg0;
                                                String var18 = var36;
                                                String var833 = ellipsize(var831, "inter-med", var168, var18, var872);
                                                float var269 = ClarityDraw.text(arg0, var571, var638, var670, var704, var734, var765, var800, var833);
                                                float var270 = var58;
                                                float var498 = var174;
                                                float var271 = (var270 + var498);
                                                var58 = var271;
                                            }
                                            if (var38 == 0) {
                                                if (var39 != 0) {
                                                }
                                            }
                                            float var274 = var58;
                                            float var499 = 6.0F;
                                            float var572 = var860;
                                            float var500 = (var499 * var572);
                                            float var275 = (var274 + var500);
                                            var873 = var275;
                                            if (var38 != 0) {
                                                double var278 = var68.durSec;
                                                double var573 = 0.0;
                                                int var280 = Double.compare(var278, var573);
                                                if (var280 > 0) {
                                                    MediaBridge.Snapshot var590 = var68;
                                                    double var591 = var590.position();
                                                    MediaBridge.Snapshot var680 = var68;
                                                    double var681 = var680.durSec;
                                                    double var593 = (var591 / var681);
                                                    double var324 = Math.min(1.0, var593);
                                                    var281 = ((float) var324);
                                                } else {
                                                    var281 = 0.0F;
                                                }
                                                float var59 = var281;
                                                float var575 = var870;
                                                float var639 = var873;
                                                float var671 = var872;
                                                float var705 = var176;
                                                float var735 = var176;
                                                float var766 = 0.5F;
                                                float var736 = (var735 * var766);
                                                int var767 = var130;
                                                int var801 = var31;
                                                float var834 = 0.15F;
                                                int var768 = mix(var767, var801, var834);
                                                float var802 = 0.9F;
                                                float var835 = var862;
                                                float var803 = (var802 * var835);
                                                int var769 = tintA(var768, var803);
                                                ClarityDraw.fillRRect(arg0, var575, var639, var671, var705, var736, var769);
                                                float var501 = 0.0F;
                                                int var285 = Float.compare(var59, var501);
                                                if (var285 > 0) {
                                                    float var502 = var872;
                                                    float var576 = var59;
                                                    float var503 = (var502 * var576);
                                                    float var287 = Math.max(var176, var503);
                                                    float var577 = var870;
                                                    float var640 = var873;
                                                    float var672 = var287;
                                                    float var706 = var176;
                                                    float var737 = var176;
                                                    float var770 = 0.5F;
                                                    float var738 = (var737 * var770);
                                                    int var771 = var30;
                                                    float var804 = var862;
                                                    float var836 = var29;
                                                    float var805 = (var804 * var836);
                                                    int var772 = tintA(var771, var805);
                                                    ClarityDraw.fillRRect(arg0, var577, var640, var672, var706, var738, var772);
                                                    float var504 = 0.95F;
                                                    float var291 = (var176 * var504);
                                                    float var578 = var870;
                                                    float var641 = var287;
                                                    float var579 = (var578 + var641);
                                                    float var642 = var291;
                                                    float var580 = (var579 - var642);
                                                    float var643 = var873;
                                                    float var673 = var176;
                                                    float var707 = 0.5F;
                                                    float var674 = (var673 * var707);
                                                    float var644 = (var643 + var674);
                                                    float var675 = var291;
                                                    float var645 = (var644 - var675);
                                                    float var676 = var291;
                                                    float var708 = 2.0F;
                                                    float var677 = (var676 * var708);
                                                    float var709 = var291;
                                                    float var739 = 2.0F;
                                                    float var710 = (var709 * var739);
                                                    float var740 = var291;
                                                    int var773 = var31;
                                                    float var806 = 0.95F;
                                                    float var837 = var862;
                                                    float var807 = (var806 * var837);
                                                    float var838 = var29;
                                                    float var808 = (var807 * var838);
                                                    int var774 = tintA(var773, var808);
                                                    ClarityDraw.fillRRect(arg0, var580, var645, var677, var710, var740, var774);
                                                }
                                                float var294 = var873;
                                                float var505 = var176;
                                                float var581 = 4.0F;
                                                float var646 = var860;
                                                float var582 = (var581 * var646);
                                                float var506 = (var505 + var582);
                                                float var295 = (var294 + var506);
                                                var873 = var295;
                                            }
                                            if (var39 != 0) {
                                                MusicPlayer var507 = var5;
                                                String var508 = var507.getTimeMode();
                                                boolean var298 = "Elapsed / Total".equals(var508);
                                                if (var298) {
                                                    double var316 = var68.durSec;
                                                    double var588 = 0.0;
                                                    int var318 = Double.compare(var316, var588);
                                                    if (var318 > 0) {
                                                        double var320 = var68.durSec;
                                                        var441 = fmtTime(var320);
                                                    }
                                                } else {
                                                    double var300 = var68.durSec;
                                                    double var583 = 0.0;
                                                    int var302 = Double.compare(var300, var583);
                                                    if (var302 > 0) {
                                                        double var304 = var68.remaining();
                                                        String var306 = fmtTime(var304);
                                                        var441 = "-" + (var306);
                                                        var441 = "--:--";
                                                    } else {
                                                        var441 = "--:--";
                                                    }
                                                    String var875 = var441;
                                                    float var307 = var873;
                                                    float var509 = 5.5F;
                                                    float var585 = var860;
                                                    float var510 = (var509 * var585);
                                                    float var308 = (var307 + var510);
                                                    float var877 = var308;
                                                    String var586 = "inter-med";
                                                    float var647 = var170;
                                                    float var678 = 0.0F;
                                                    int var711 = var127;
                                                    float var741 = var862;
                                                    int var712 = tintA(var711, var741);
                                                    int var742 = 17;
                                                    float var775 = var870;
                                                    float var809 = var877;
                                                    MediaBridge.Snapshot var839 = var68;
                                                    double var840 = var839.position();
                                                    String var842 = fmtTime(var840);
                                                    float var311 = ClarityDraw.text(arg0, var586, var647, var678, var712, var742, var775, var809, var842);
                                                    String var587 = "inter-med";
                                                    float var648 = var170;
                                                    float var679 = 0.0F;
                                                    int var713 = var127;
                                                    float var743 = var862;
                                                    int var714 = tintA(var713, var743);
                                                    int var744 = 20;
                                                    float var776 = var870;
                                                    float var810 = var872;
                                                    float var777 = (var776 + var810);
                                                    float var811 = var877;
                                                    String var843 = var875;
                                                    float var314 = ClarityDraw.text(arg0, var587, var648, var679, var714, var744, var777, var811, var843);
                                                }
                                            }
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

    private static void drawKeybindList(long arg0, Minecraft arg1, float arg2, float arg3) {
        List var17 = null;
        float var49 = 0.0F;
        float var50 = 0.0F;
        int var52 = 0;
        int var60 = 0;
        int var127 = 0;
        int var132 = 0;
        int var142 = 0;
        String var164 = null;
        float var180 = 0.0F;
        int var226 = 0;
        int var350 = 0;
        float var351 = 0.0F;
        int var352 = 0;
        float var353 = 0.0F;
        float var354 = 0.0F;
        int var355 = 0;
        int var408 = 0;
        float var453 = 0.0F;
        float var474 = 0.0F;
        String var483 = null;
        float var530 = 0.0F;
        float var536 = 0.0F;
        int var668 = 0;
        float var707 = 0.0F;
        float var710 = 0.0F;
        float var781 = 0.0F;
        float var783 = 0.0F;
        int var788 = 0;
        KeybindList var5 = KeybindList.INSTANCE;
        if (var5 != null) {
            boolean var75 = var5.isEnabled();
            if (!var75) {
            }
        }
        return;
        List var77 = var5.evaluate1();
        var17 = var77;
        List var78 = var17;
        boolean var79 = var78.isEmpty();
        if (var79) {
            boolean var80 = HudLayout.isEditing();
            if (var80) {
                List var82 = var5.evaluate();
                var17 = var82;
            }
            List var83 = var17;
            int var84 = var83.size();
            if (var84 == 0) {
                return;
            }
            float var87 = var5.getScale();
            float var89 = var5.getOpacity();
            float var91 = var5.getTextSize();
            float var92 = (var91 * var87);
            float var357 = var5.getTextSize();
            float var358 = (var357 - 1.0F);
            float var94 = Math.max(8.0F, var358);
            float var95 = (var94 * var87);
            float var97 = var5.getRowHeight();
            float var98 = (var97 * var87);
            float var100 = var5.getRowGap();
            float var101 = (var100 * var87);
            float var103 = var5.getPadding();
            float var104 = (var103 * var87);
            boolean var106 = var5.isShowTitle();
            float var108 = var5.getTextSize();
            float var109 = (var108 + 1.0F);
            float var110 = (var109 * var87);
            if (var106) {
                float var462 = var87;
                float var366 = (12.0F * var462);
                var353 = (var110 + var366);
            } else {
                var353 = 0.0F;
            }
            float var28 = var353;
            String var114 = var5.getKeyStyle();
            boolean var116 = "Pill".equals(var114);
            boolean var118 = "Brackets".equals(var114);
            boolean var120 = var5.isStateDot();
            boolean var122 = var5.isDimDisabled();
            boolean var124 = var5.isShowMode();
            String var370 = var5.getKeyAlign();
            boolean var126 = "Left".equals(var370);
            if (!var126) {
                var127 = 1;
            } else {
                var127 = 0;
            }
            int var35 = var127;
            boolean var129 = var5.isThemeColors();
            int var130 = ThemeColors.getAccentRgb();
            if (var129) {
                var132 = ThemeColors.getTextRgb();
            } else {
                var132 = var5.getNameColor();
            }
            int var38 = var132;
            if (var129) {
                var355 = var130;
            } else {
                var355 = var5.getKeyColor();
            }
            int var39 = var355;
            int var135 = ThemeColors.getMutedRgb();
            int var137 = var5.getEnabledColor();
            int var139 = var5.getDisabledColor();
            if (var129) {
                int var141 = ThemeColors.getSurfaceRgb();
                float var463 = 0.02F;
                var142 = mix(var141, var130, var463);
            } else {
                var142 = var5.getBackgroundColor();
            }
            int var43 = var142;
            int var143 = ThemeColors.getBorderRgb();
            String[] var145 = new String[var84];
            float[] var147 = new float[var84];
            float[] var149 = new float[var84];
            float[] var151 = new float[var84];
            var49 = 0.0F;
            var50 = 0.0F;
            if (var120) {
                var354 = (12.0F * var87);
            } else {
                var354 = 0.0F;
            }
            float var51 = var354;
            var52 = 0;
            while (true) {
                int var157 = var52;
                if (var157 >= var84) break;
                List var158 = var17;
                int var374 = var52;
                Object var159 = var158.get(var374);
                KeybindListEntry var160 = ((KeybindListEntry) var159);
                boolean var162 = var5.isUppercaseKeys();
                if (var162) {
                    String var174 = var160.labelText1;
                    Locale var384 = Locale.ROOT;
                    var164 = var174.toUpperCase(var384);
                } else {
                    var164 = var160.labelText1;
                }
                String var54 = var164;
                int var375 = var52;
                int var464 = var118;
                if (var464 != 0) {
                    String var465 = var54;
                    var483 = "[" + (var465) + "]";
                } else {
                    var483 = var54;
                }
                var145[var375] = var483;
                int var376 = var52;
                long var466 = arg0;
                String var669 = var160.labelText;
                float var468 = ClarityDraw.textWidth(var466, "inter-med", var92, 0.0F, var669);
                var147[var376] = var468;
                int var377 = var52;
                int var469 = var124;
                if (var469 != 0) {
                    String var470 = "hold";
                    String var532 = var160.labelText2;
                    boolean var471 = var470.equals(var532);
                    if (var471) {
                        long var472 = arg0;
                        String var569 = "inter-med";
                        float var605 = var92;
                        float var638 = 0.8F;
                        float var606 = (var605 * var638);
                        float var639 = 0.0F;
                        String var670 = " hold";
                        var474 = ClarityDraw.textWidth(var472, var569, var606, var639, var670);
                    }
                } else {
                    var474 = 0.0F;
                }
                var149[var377] = var474;
                int var378 = var52;
                long var475 = arg0;
                String var570 = "inter-med";
                float var607 = var95;
                float var640 = 0.0F;
                String[] var671 = var145;
                int var13 = var52;
                String var672 = var671[var13];
                float var477 = ClarityDraw.textWidth(var475, var570, var607, var640, var672);
                int var533 = var116;
                if (var533 != 0) {
                    float var535 = 12.0F;
                    float var571 = var87;
                    var536 = (var535 * var571);
                } else {
                    var536 = 0.0F;
                }
                float var478 = (var477 + var536);
                var151[var378] = var478;
                float var169 = var49;
                int var479 = var52;
                float var380 = var147[var479];
                float[] var480 = var149;
                int var534 = var52;
                float var481 = var480[var534];
                float var381 = (var380 + var481);
                float var170 = Math.max(var169, var381);
                var49 = var170;
                float var171 = var50;
                int var482 = var52;
                float var383 = var151[var482];
                float var172 = Math.max(var171, var383);
                var50 = var172;
                var52 = (var52 + 1);
            }
            float var176 = (16.0F * var87);
            float var777 = var176;
            String var178 = var5.getTitleText();
            String var778 = var178;
            if (var106) {
                String var531 = "inter-sb";
                float var568 = var110;
                float var604 = 0.0F;
                String var637 = var778;
                var180 = ClarityDraw.textWidth(arg0, var531, var568, var604, var637);
            } else {
                var180 = 0.0F;
            }
            float var779 = var180;
            float var182 = var5.getMinWidth();
            float var183 = (var182 * var87);
            float var484 = var49;
            float var388 = (var51 + var484);
            float var485 = var777;
            float var389 = (var388 + var485);
            float var486 = var50;
            float var390 = (var389 + var486);
            float var487 = var779;
            float var391 = Math.max(var390, var487);
            float var488 = var104;
            float var537 = 2.0F;
            float var489 = (var488 * var537);
            float var392 = (var391 + var489);
            float var184 = Math.max(var183, var392);
            float var186 = (var104 * 2.0F);
            float var187 = (var186 + var28);
            float var396 = ((float) var84);
            float var490 = var98;
            float var397 = (var396 * var490);
            float var188 = (var187 + var397);
            int var491 = 1;
            int var399 = (var84 - var491);
            float var400 = ((float) var399);
            float var492 = var101;
            float var401 = (var400 * var492);
            float var189 = (var188 + var401);
            int var191 = var5.getXOffset();
            float var192 = ((float) var191);
            int var194 = var5.getYOffset();
            float var195 = ((float) var194);
            String var197 = var5.getAnchor();
            var60 = -1;
            int var200 = var197.hashCode();
            switch (var200) {
                case 287355729 -> {}
                case 926500839 -> {}
                case 999535836 -> {}
                default -> {}
            }
            String var460 = "Bottom Left";
            boolean var341 = var197.equals(var460);
            if (var341) {
                var60 = 1;
                String var461 = "Top Right";
                boolean var344 = var197.equals(var461);
                if (var344) {
                    var60 = 0;
                    boolean var202 = var197.equals("Bottom Right");
                    if (var202) {
                        var60 = 2;
                    }
                    int var204 = var60;
                    switch (var204) {
                        case 0 -> {}
                        case 1 -> {}
                        case 2 -> {}
                        default -> {}
                    }
                    float var454 = var184;
                    float var329 = (arg2 - var454);
                    float var455 = var192;
                    float var330 = (var329 - var455);
                    var781 = var330;
                    var783 = var195;
                    float var456 = var184;
                    float var333 = (arg2 - var456);
                    float var457 = var192;
                    float var334 = (var333 - var457);
                    var781 = var334;
                    float var458 = var189;
                    float var336 = (arg3 - var458);
                    float var459 = var195;
                    float var337 = (var336 - var459);
                    var783 = var337;
                    var781 = var192;
                    float var207 = (arg3 - var189);
                    float var208 = (var207 - var195);
                    var783 = var208;
                    var781 = var192;
                    var783 = var195;
                    float var209 = var781;
                    int var210 = Math.round(var209);
                    float var211 = ((float) var210);
                    float var780 = var211;
                    float var212 = var783;
                    int var213 = Math.round(var212);
                    float var214 = ((float) var213);
                    float var782 = var214;
                    float var493 = var782;
                    float var538 = var184;
                    float var572 = var189;
                    HudLayout.report("Keybind List", var780, var493, var538, var572);
                    float var217 = var5.getCornerRadius();
                    float var218 = (var217 * var87);
                    float var784 = var218;
                    boolean var220 = var5.isBackground();
                    if (var220) {
                        boolean var222 = var5.isBlur();
                        if (var222) {
                            float var494 = var780;
                            float var539 = var782;
                            float var573 = var184;
                            float var608 = var189;
                            float var641 = var784;
                            float var673 = var89;
                            boolean var225 = fillBlurRRect(arg0, var494, var539, var573, var608, var641, var673);
                            if (var225) {
                                var226 = 1;
                            } else {
                                var226 = 0;
                            }
                            int var785 = var226;
                            float var495 = var780;
                            float var540 = var782;
                            float var574 = var184;
                            float var609 = var189;
                            float var642 = var784;
                            int var674 = var43;
                            int var708 = var785;
                            if (var708 != 0) {
                                KeybindList var746 = var5;
                                var710 = var746.getBlurTint();
                            } else {
                                KeybindList var709 = var5;
                                var710 = var709.getBackgroundOpacity();
                            }
                            float var711 = (var710 * var89);
                            int var675 = tintA(var674, var711);
                            ClarityDraw.fillRRect(arg0, var495, var540, var574, var609, var642, var675);
                            boolean var230 = var5.isBorder();
                            if (var230) {
                                float var496 = var780;
                                float var541 = var782;
                                float var575 = var184;
                                float var610 = var189;
                                float var643 = var784;
                                float var676 = 1.0F;
                                int var712 = var143;
                                int var747 = var130;
                                int var713 = mix(var712, var747, 0.1F);
                                float var748 = var89;
                                int var714 = tintA(var713, var748);
                                ClarityDraw.strokeRRect(arg0, var496, var541, var575, var610, var643, var676, var714);
                            }
                            boolean var234 = var5.isHighlight();
                            if (var234) {
                                int var235 = ThemeColors.getSurfaceRgb();
                                boolean var407 = ThemeColors.isLight();
                                if (var407) {
                                    var408 = 0;
                                } else {
                                    var408 = 16777215;
                                }
                                float var497 = 0.12F;
                                int var236 = mix(var235, var408, var497);
                                float var498 = var780;
                                float var542 = var782;
                                float var576 = var184;
                                float var611 = var189;
                                float var644 = var784;
                                int var677 = var236;
                                float var715 = 0.66F;
                                float var749 = var89;
                                float var716 = (var715 * var749);
                                int var678 = tintA(var677, var716);
                                ClarityDraw.topHighlight(arg0, var498, var542, var576, var611, var644, var678);
                            }
                            if (var106) {
                                float var241 = (var782 + var104);
                                float var499 = 0.5F;
                                float var411 = (var110 * var499);
                                float var242 = (var241 + var411);
                                float var786 = var242;
                                String var500 = "inter-sb";
                                float var543 = var110;
                                float var577 = 0.0F;
                                int var612 = var39;
                                float var645 = var89;
                                int var613 = tintA(var612, var645);
                                int var646 = 17;
                                float var679 = var780;
                                float var717 = var104;
                                float var680 = (var679 + var717);
                                float var718 = var786;
                                String var750 = var778;
                                float var245 = ClarityDraw.text(arg0, var500, var543, var577, var613, var646, var680, var718, var750);
                                boolean var247 = var5.isTitleDivider();
                                if (var247) {
                                    float var501 = var780;
                                    float var544 = var104;
                                    float var502 = (var501 + var544);
                                    float var545 = var782;
                                    float var578 = var104;
                                    float var546 = (var545 + var578);
                                    float var579 = var28;
                                    float var547 = (var546 + var579);
                                    float var580 = 5.0F;
                                    float var614 = var87;
                                    float var581 = (var580 * var614);
                                    float var548 = (var547 - var581);
                                    float var582 = var184;
                                    float var615 = var104;
                                    float var647 = 2.0F;
                                    float var616 = (var615 * var647);
                                    float var583 = (var582 - var616);
                                    float var617 = 1.0F;
                                    float var648 = 0.5F;
                                    int var681 = var143;
                                    int var719 = var130;
                                    float var751 = 0.1F;
                                    int var682 = mix(var681, var719, var751);
                                    float var720 = 0.9F;
                                    float var752 = var89;
                                    float var721 = (var720 * var752);
                                    int var683 = tintA(var682, var721);
                                    ClarityDraw.fillRRect(arg0, var502, var548, var583, var617, var648, var683);
                                }
                                HashSet var250 = new HashSet();
                                HashSet var787 = var250;
                                var788 = 0;
                                while (true) {
                                    int var252 = var788;
                                    int var412 = var84;
                                    if (var252 >= var412) break;
                                    List var256 = var17;
                                    int var414 = var788;
                                    Object var257 = var256.get(var414);
                                    KeybindListEntry var258 = ((KeybindListEntry) var257);
                                    KeybindListEntry var415 = var258;
                                    String var416 = var415.labelText;
                                    boolean var260 = var787.add(var416);
                                    Map var261 = KB_ANIM;
                                    KeybindListEntry var417 = var258;
                                    String var418 = var417.labelText;
                                    KeybindListEntry var503 = var258;
                                    boolean var504 = var503.enabledState;
                                    if (var504) {
                                        var530 = 1.0F;
                                    } else {
                                        var530 = 0.0F;
                                    }
                                    Float var505 = var530;
                                    Object var262 = var261.getOrDefault(var418, var505);
                                    Float var263 = ((Float) var262);
                                    float var264 = var263;
                                    KeybindListEntry var419 = var258;
                                    boolean var420 = var419.enabledState;
                                    if (var420) {
                                        var453 = 1.0F;
                                    } else {
                                        var453 = 0.0F;
                                    }
                                    float var506 = var264;
                                    float var421 = (var453 - var506);
                                    float var507 = 0.18F;
                                    float var422 = (var421 * var507);
                                    float var266 = (var264 + var422);
                                    float var789 = var266;
                                    Map var267 = KB_ANIM;
                                    KeybindListEntry var423 = var258;
                                    String var424 = var423.labelText;
                                    float var508 = var789;
                                    Float var509 = var508;
                                    Object var268 = var267.put(var424, var509);
                                    float var425 = var104;
                                    float var270 = (var782 + var425);
                                    float var426 = var28;
                                    float var271 = (var270 + var426);
                                    int var427 = var788;
                                    float var428 = ((float) var427);
                                    float var510 = var98;
                                    float var549 = var101;
                                    float var511 = (var510 + var549);
                                    float var429 = (var428 * var511);
                                    float var272 = (var271 + var429);
                                    float var430 = var98;
                                    float var512 = 0.5F;
                                    float var431 = (var430 * var512);
                                    float var274 = (var272 + var431);
                                    float var432 = 0.5F;
                                    float var275 = (var274 + var432);
                                    boolean var277 = var5.isRowHighlight();
                                    if (var277) {
                                        float var433 = 0.02F;
                                        int var279 = Float.compare(var789, var433);
                                        if (var279 > 0) {
                                            float var513 = var780;
                                            float var550 = var104;
                                            float var584 = 0.5F;
                                            float var551 = (var550 * var584);
                                            float var514 = (var513 + var551);
                                            float var552 = var272;
                                            float var585 = var184;
                                            float var618 = var104;
                                            float var586 = (var585 - var618);
                                            float var619 = var98;
                                            float var649 = var784;
                                            float var684 = var98;
                                            float var722 = 0.4F;
                                            float var685 = (var684 * var722);
                                            float var650 = Math.min(var649, var685);
                                            int var686 = var137;
                                            float var723 = 0.1F;
                                            float var753 = var789;
                                            float var724 = (var723 * var753);
                                            float var754 = var89;
                                            float var725 = (var724 * var754);
                                            int var687 = tintA(var686, var725);
                                            ClarityDraw.fillRRect(arg0, var514, var552, var586, var619, var650, var687);
                                        }
                                        float var434 = var104;
                                        float var283 = (var780 + var434);
                                        if (var120) {
                                            float var435 = var87;
                                            float var286 = (3.5F * var435);
                                            float var515 = var283;
                                            float var553 = var286;
                                            float var516 = (var515 + var553);
                                            float var554 = var275;
                                            float var587 = var286;
                                            float var555 = (var554 - var587);
                                            float var588 = var286;
                                            float var620 = 2.0F;
                                            float var589 = (var588 * var620);
                                            float var621 = var286;
                                            float var651 = 2.0F;
                                            float var622 = (var621 * var651);
                                            float var652 = var286;
                                            int var688 = var139;
                                            int var726 = var137;
                                            float var755 = var789;
                                            int var689 = mix(var688, var726, var755);
                                            float var727 = 0.55F;
                                            float var756 = 0.45F;
                                            float var773 = var789;
                                            float var757 = (var756 * var773);
                                            float var728 = (var727 + var757);
                                            float var758 = var89;
                                            float var729 = (var728 * var758);
                                            int var690 = tintA(var689, var729);
                                            ClarityDraw.fillRRect(arg0, var516, var555, var589, var622, var652, var690);
                                        }
                                        float var436 = var51;
                                        float var290 = (var283 + var436);
                                        float var790 = var290;
                                        if (var122) {
                                            int var452 = var38;
                                            float var529 = var789;
                                            var352 = mix(var135, var452, var529);
                                        } else {
                                            var352 = var38;
                                        }
                                        int var791 = var352;
                                        String var517 = "inter-med";
                                        float var556 = var92;
                                        float var590 = 0.0F;
                                        int var623 = var791;
                                        float var653 = var89;
                                        int var624 = tintA(var623, var653);
                                        int var654 = 17;
                                        float var691 = var790;
                                        float var730 = var275;
                                        KeybindListEntry var759 = var258;
                                        String var760 = var759.labelText;
                                        float var294 = ClarityDraw.text(arg0, var517, var556, var590, var624, var654, var691, var730, var760);
                                        int var437 = var788;
                                        float var296 = var149[var437];
                                        float var438 = 0.0F;
                                        int var297 = Float.compare(var296, var438);
                                        if (var297 > 0) {
                                            String var518 = "inter-med";
                                            float var557 = var92;
                                            float var591 = 0.8F;
                                            float var558 = (var557 * var591);
                                            float var592 = 0.0F;
                                            int var625 = var135;
                                            float var655 = 0.8F;
                                            float var692 = var89;
                                            float var656 = (var655 * var692);
                                            int var626 = tintA(var625, var656);
                                            int var657 = 17;
                                            float var693 = var790;
                                            float[] var731 = var147;
                                            int var761 = var788;
                                            float var732 = var731[var761];
                                            float var694 = (var693 + var732);
                                            float var733 = var275;
                                            float var762 = 0.5F;
                                            float var734 = (var733 + var762);
                                            String var763 = " hold";
                                            float var300 = ClarityDraw.text(arg0, var518, var558, var592, var626, var657, var694, var734, var763);
                                        }
                                        if (var122) {
                                            int var439 = var39;
                                            float var519 = 0.45F;
                                            float var559 = 0.55F;
                                            float var593 = var789;
                                            float var560 = (var559 * var593);
                                            float var520 = (var519 + var560);
                                            var350 = mix(var135, var439, var520);
                                        } else {
                                            var350 = var39;
                                        }
                                        int var68 = var350;
                                        float var440 = var184;
                                        float var304 = (var780 + var440);
                                        float var441 = var104;
                                        float var305 = (var304 - var441);
                                        float var442 = var104;
                                        float var307 = (var780 + var442);
                                        float var443 = var51;
                                        float var308 = (var307 + var443);
                                        float var444 = var49;
                                        float var309 = (var308 + var444);
                                        float var445 = var777;
                                        float var310 = (var309 + var445);
                                        if (var35 != 0) {
                                            float[] var446 = var151;
                                            int var521 = var788;
                                            float var447 = var446[var521];
                                            var351 = (var305 - var447);
                                        } else {
                                            var351 = var310;
                                        }
                                        float var71 = var351;
                                        if (var116) {
                                            float var448 = 4.0F;
                                            float var522 = var87;
                                            float var449 = (var448 * var522);
                                            float var315 = (var98 - var449);
                                            float var450 = var95;
                                            float var523 = 8.0F;
                                            float var561 = var87;
                                            float var524 = (var523 * var561);
                                            float var451 = (var450 + var524);
                                            float var316 = Math.min(var315, var451);
                                            float var525 = var71;
                                            float var562 = var275;
                                            float var594 = var316;
                                            float var627 = 0.5F;
                                            float var595 = (var594 * var627);
                                            float var563 = (var562 - var595);
                                            float[] var596 = var151;
                                            int var628 = var788;
                                            float var597 = var596[var628];
                                            float var629 = var316;
                                            float var658 = 6.0F;
                                            float var695 = var87;
                                            float var659 = (var658 * var695);
                                            float var696 = var316;
                                            float var735 = 0.5F;
                                            float var697 = (var696 * var735);
                                            float var660 = Math.min(var659, var697);
                                            int var698 = var68;
                                            float var736 = 0.14F;
                                            float var764 = var89;
                                            float var737 = (var736 * var764);
                                            int var699 = tintA(var698, var737);
                                            ClarityDraw.fillRRect(arg0, var525, var563, var597, var629, var660, var699);
                                            float var526 = var71;
                                            float var564 = var275;
                                            float var598 = var316;
                                            float var630 = 0.5F;
                                            float var599 = (var598 * var630);
                                            float var565 = (var564 - var599);
                                            float[] var600 = var151;
                                            int var631 = var788;
                                            float var601 = var600[var631];
                                            float var632 = var316;
                                            float var661 = 6.0F;
                                            float var700 = var87;
                                            float var662 = (var661 * var700);
                                            float var701 = var316;
                                            float var738 = 0.5F;
                                            float var702 = (var701 * var738);
                                            float var663 = Math.min(var662, var702);
                                            float var703 = 1.0F;
                                            int var739 = var68;
                                            float var765 = 0.35F;
                                            float var774 = var89;
                                            float var766 = (var765 * var774);
                                            int var740 = tintA(var739, var766);
                                            ClarityDraw.strokeRRect(arg0, var526, var565, var601, var632, var663, var703, var740);
                                            String var527 = "inter-med";
                                            float var566 = var95;
                                            float var602 = 0.0F;
                                            int var633 = var68;
                                            float var664 = var89;
                                            int var634 = tintA(var633, var664);
                                            int var665 = 18;
                                            float var704 = var71;
                                            float[] var741 = var151;
                                            int var767 = var788;
                                            float var742 = var741[var767];
                                            float var768 = 0.5F;
                                            float var743 = (var742 * var768);
                                            float var705 = (var704 + var743);
                                            float var744 = var275;
                                            String[] var769 = var145;
                                            int var775 = var788;
                                            String var770 = var769[var775];
                                            float var323 = ClarityDraw.text(arg0, var527, var566, var602, var634, var665, var705, var744, var770);
                                        } else {
                                            String var528 = "inter-med";
                                            float var567 = var95;
                                            float var603 = 0.0F;
                                            int var635 = var68;
                                            float var666 = var89;
                                            int var636 = tintA(var635, var666);
                                            int var667 = var35;
                                            if (var667 != 0) {
                                                var668 = 20;
                                            } else {
                                                var668 = 17;
                                            }
                                            int var706 = var35;
                                            if (var706 != 0) {
                                                var707 = var305;
                                            } else {
                                                var707 = var310;
                                            }
                                            float var745 = var275;
                                            String[] var771 = var145;
                                            int var776 = var788;
                                            String var772 = var771[var776];
                                            float var326 = ClarityDraw.text(arg0, var528, var567, var603, var636, var668, var707, var745, var772);
                                        }
                                        var788 = (var788 + 1);
                                    }
                                }
                                Map var253 = KB_ANIM;
                                Set var254 = var253.keySet();
                                HashSet var413 = var787;
                                boolean var255 = var254.retainAll(var413);
                                return;
                            }
                        }
                    }
                }
            }
        }
    }

    public static void onContextDestroyed() {
        blurImg = 0;
        blurImgH = 0;
        blurImgW = 0;
        blurImgTex = 0;
        musicImg = 0;
        musicImgVer = -1;
    }

    private static int backdropImage(long arg0) {
        int var2 = BackdropBlur.textureId();
        if (var2 == 0) {
            return 0;
        }
        int var12 = BackdropBlur.width();
        int var13 = BackdropBlur.height();
        if (blurImg != 0) {
            if (blurImgTex == var2) {
                if (blurImgW == var12) {
                    if (blurImgH == var13) {
                        return blurImg;
                    }
                }
            }
            if (blurImg != 0) {
                int var4 = blurImg;
                NanoVG.nvgDeleteImage(arg0, var4);
            }
            blurImg = NanoVGGL3.nvglCreateImageFromHandle(arg0, var2, var12, var13, 65544);
            blurImgTex = var2;
            blurImgW = var12;
            blurImgH = var13;
            return blurImg;
        }
    }

    private static void statusIcon(long arg0, float arg1, float arg2, float arg3, boolean arg4, int arg5, float arg6, float arg7) {
        float var69 = (arg3 / 2.0F);
        float var48 = (arg1 - var69);
        float var103 = (arg3 / 2.0F);
        float var71 = (arg2 - var103);
        float var135 = (arg3 / 2.0F);
        float var138 = (0.18F * arg6);
        int var137 = tintA(arg5, var138);
        ClarityDraw.fillRRect(arg0, var48, var71, arg3, arg3, var135, var137);
        NanoVG.nvgLineCap(arg0, 1);
        NanoVG.nvgLineJoin(arg0, 1);
        NanoVG.nvgBeginPath(arg0);
        if (arg4) {
            float var73 = (arg3 * 0.24F);
            float var52 = (arg1 - var73);
            float var107 = (arg3 * 0.02F);
            float var75 = (arg2 + var107);
            NanoVG.nvgMoveTo(arg0, var52, var75);
            float var77 = (arg3 * 0.06F);
            float var54 = (arg1 - var77);
            float var110 = (arg3 * 0.2F);
            float var79 = (arg2 + var110);
            NanoVG.nvgLineTo(arg0, var54, var79);
            float var81 = (arg3 * 0.26F);
            float var56 = (arg1 + var81);
            float var113 = (arg3 * 0.16F);
            float var83 = (arg2 - var113);
            NanoVG.nvgLineTo(arg0, var56, var83);
        } else {
            float var87 = (arg3 * 0.18F);
            float var62 = (arg1 - var87);
            float var117 = (arg3 * 0.18F);
            float var89 = (arg2 - var117);
            NanoVG.nvgMoveTo(arg0, var62, var89);
            float var91 = (arg3 * 0.18F);
            float var64 = (arg1 + var91);
            float var120 = (arg3 * 0.18F);
            float var93 = (arg2 + var120);
            NanoVG.nvgLineTo(arg0, var64, var93);
            float var95 = (arg3 * 0.18F);
            float var66 = (arg1 + var95);
            float var123 = (arg3 * 0.18F);
            float var97 = (arg2 - var123);
            NanoVG.nvgMoveTo(arg0, var66, var97);
            float var99 = (arg3 * 0.18F);
            float var68 = (arg1 - var99);
            float var126 = (arg3 * 0.18F);
            float var101 = (arg2 + var126);
            NanoVG.nvgLineTo(arg0, var68, var101);
        }
        setARGB(COL, arg5, arg6);
        NVGColor var58 = COL;
        NanoVG.nvgStrokeColor(arg0, var58);
        float var85 = (1.9F * arg7);
        float var60 = Math.max(1.4F, var85);
        NanoVG.nvgStrokeWidth(arg0, var60);
        NanoVG.nvgStroke(arg0);
    }

    private static void drawNotifications(long arg0, Minecraft arg1, float arg2, float arg3) {
        float var22 = 0.0F;
        float var23 = 0.0F;
        float var24 = 0.0F;
        float var25 = 0.0F;
        int var60 = 0;
        int var61 = 0;
        float var64 = 0.0F;
        int var160 = 0;
        float var226 = 0.0F;
        float var229 = 0.0F;
        float var239 = 0.0F;
        float var372 = 0.0F;
        float var455 = 0.0F;
        float var462 = 0.0F;
        int var468 = 0;
        float var473 = 0.0F;
        float var479 = 0.0F;
        int var483 = 0;
        int var484 = 0;
        int var485 = 0;
        float var486 = 0.0F;
        List var487 = null;
        float var488 = 0.0F;
        float var489 = 0.0F;
        float var490 = 0.0F;
        float var491 = 0.0F;
        int var590 = 0;
        float var598 = 0.0F;
        float var602 = 0.0F;
        float var615 = 0.0F;
        float var622 = 0.0F;
        int var637 = 0;
        float var737 = 0.0F;
        float var1105 = 0.0F;
        float var1157 = 0.0F;
        float var1187 = 0.0F;
        float var1188 = 0.0F;
        Notifications var5 = Notifications.INSTANCE;
        if (var5 != null) {
            boolean var95 = var5.isEnabled();
            if (!var95) {
            }
        }
        return;
        boolean var96 = HudLayout.isEditing();
        if (var96) {
            var487 = Notifications.evaluate1();
        } else {
            var487 = Notifications.evaluate();
        }
        List var21 = var487;
        boolean var99 = var21.isEmpty();
        if (var99) {
            return;
        }
        var22 = 3.4028235E38F;
        var23 = 3.4028235E38F;
        var24 = -3.4028235E38F;
        var25 = -3.4028235E38F;
        String var105 = var5.getPosition();
        boolean var107 = var105.endsWith("Center");
        String var492 = "Right";
        boolean var109 = var105.endsWith(var492);
        String var493 = "Bottom";
        boolean var111 = var105.startsWith(var493);
        String var113 = var5.getStyle();
        String var115 = var5.getAnimation();
        boolean var117 = var5.isCompact();
        boolean var119 = var5.isStatusBar();
        boolean var121 = var5.isProgressBar();
        float var123 = var5.getScale();
        float var125 = var5.getOpacity();
        int var127 = var5.getXOffset();
        float var128 = ((float) var127);
        int var130 = var5.getYOffset();
        float var131 = ((float) var130);
        int var134 = ThemeColors.getAccentRgb();
        boolean var136 = var5.isThemeColors();
        if (var136) {
            var485 = var134;
        } else {
            var485 = var5.getEnabledColor();
        }
        int var43 = var485;
        if (var136) {
            int var494 = 0;
            var483 = mix(var134, var494, 0.45F);
        } else {
            Notifications var482 = var5;
            var483 = var482.getDisabledColor();
        }
        int var44 = var483;
        long var141 = System.currentTimeMillis();
        int var143 = ThemeColors.getSurfaceRgb();
        int var144 = ThemeColors.getBorderRgb();
        int var145 = ThemeColors.getTextRgb();
        boolean var146 = ThemeColors.isLight();
        String var495 = var113;
        boolean var148 = "Classic".equals(var495);
        String var496 = var113;
        boolean var150 = "Minimal".equals(var496);
        String var497 = var113;
        boolean var152 = "Modern".equals(var497);
        String var498 = var113;
        boolean var154 = "Switch".equals(var498);
        String var499 = var113;
        boolean var156 = "Blur".equals(var499);
        if (var156) {
            var160 = backdropImage(arg0);
        } else {
            var160 = 0;
        }
        int var56 = ((byte) var160);
        if (var150) {
            var486 = 12.0F;
        } else {
            var486 = 13.0F;
        }
        float var500 = var123;
        float var162 = (var486 * var500);
        float var501 = var123;
        float var164 = (11.0F * var501);
        if (var150) {
            float var480 = 26.0F;
            float var636 = var123;
            float var481 = (var480 * var636);
            var1187 = var481;
        } else {
            if (var154) {
                float var502 = var123;
                float var168 = (34.0F * var502);
                var1187 = var168;
            } else {
                int var474 = var148;
                if (var474 != 0) {
                    int var475 = var117;
                    if (var475 != 0) {
                        var490 = 28.0F;
                    } else {
                        var490 = 40.0F;
                    }
                    float var634 = var123;
                    float var476 = (var490 * var634);
                    var1187 = var476;
                } else {
                    int var477 = var117;
                    if (var477 != 0) {
                        var479 = 30.0F;
                    } else {
                        var479 = 42.0F;
                    }
                    float var635 = var123;
                    float var478 = (var479 * var635);
                    var1187 = var478;
                }
                if (var150) {
                    var473 = 5.0F;
                } else {
                    var473 = 6.0F;
                }
                float var503 = var123;
                float var170 = (var473 * var503);
                int var172 = var21.size();
                int var504 = 1;
                int var173 = (var172 - var504);
                var60 = var173;
                var61 = 0;
                while (true) {
                    int var175 = var60;
                    if (var175 < 0) break;
                    int var505 = var60;
                    Object var177 = var21.get(var505);
                    NotificationToastEntry var178 = ((NotificationToastEntry) var177);
                    NotificationToastEntry var638 = var178;
                    long var639 = var638.timestampMs;
                    long var181 = (var141 - var639);
                    float var183 = ((float) var181);
                    float var506 = var183;
                    float var641 = 220.0F;
                    float var507 = (var506 / var641);
                    float var185 = Math.min(1.0F, var507);
                    var64 = var185;
                    long var187 = var178.timestampMs1;
                    float var189 = ((float) var187);
                    float var508 = var183;
                    float var190 = (var189 - var508);
                    float var509 = 260.0F;
                    int var192 = Float.compare(var190, var509);
                    if (var192 < 0) {
                        float var193 = var64;
                        float var510 = 0.0F;
                        float var642 = var190;
                        float var643 = (var642 / 260.0F);
                        float var511 = Math.max(var510, var643);
                        float var194 = Math.min(var193, var511);
                        var64 = var194;
                    }
                    float var512 = 1.0F;
                    float var644 = var64;
                    float var513 = (var512 - var644);
                    float var645 = 1.0F;
                    float var743 = var64;
                    float var646 = (var645 - var743);
                    float var514 = (var513 * var646);
                    float var196 = (1.0F - var514);
                    float var515 = var125;
                    float var198 = (var196 * var515);
                    float var516 = 0.005F;
                    int var200 = Float.compare(var198, var516);
                    if (var200 <= 0) {
                    } else {
                        boolean var202 = var178.enabledState;
                        if (var202) {
                            var468 = var43;
                        } else {
                            var468 = var44;
                        }
                        int var69 = var468;
                        String var205 = var178.labelText1;
                        String var647 = "inter-sb";
                        float var744 = var162;
                        String var861 = var178.labelText;
                        float var208 = ClarityDraw.textWidth(arg0, var647, var744, 0.0F, var861);
                        String var648 = "inter-sb";
                        float var745 = var162;
                        float var808 = 0.0F;
                        NotificationToastEntry var862 = var178;
                        String var863 = var862.labelText;
                        String var864 = (var863) + "  " + (var205);
                        float var211 = ClarityDraw.textWidth(arg0, var648, var745, var808, var864);
                        String var649 = "inter-med";
                        float var746 = var164;
                        float var809 = 0.0F;
                        String var865 = var205;
                        float var214 = ClarityDraw.textWidth(arg0, var649, var746, var809, var865);
                        if (!var117) {
                            if (!var150) {
                                if (!var154) {
                                    var484 = 1;
                                } else {
                                    var484 = 0;
                                }
                                int var74 = var484;
                                float var218 = var1187;
                                float var517 = 16.0F;
                                float var650 = var123;
                                float var518 = (var517 * var650);
                                float var219 = (var218 - var518);
                                if (var150) {
                                    float var519 = 38.0F;
                                    float var651 = var123;
                                    float var520 = (var519 * var651);
                                    float var222 = (var211 + var520);
                                    var1188 = var222;
                                } else {
                                    int var451 = var154;
                                    if (var451 != 0) {
                                        float var463 = var208;
                                        float var624 = 14.0F;
                                        float var738 = var123;
                                        float var625 = (var624 * var738);
                                        float var464 = (var463 + var625);
                                        float var626 = 16.0F;
                                        float var739 = var123;
                                        float var627 = (var626 * var739);
                                        float var465 = (var464 + var627);
                                        float var628 = 32.0F;
                                        float var740 = var123;
                                        float var629 = (var628 * var740);
                                        float var466 = (var465 + var629);
                                        float var630 = 12.0F;
                                        float var741 = var123;
                                        float var631 = (var630 * var741);
                                        float var467 = (var466 + var631);
                                        var1188 = var467;
                                    } else {
                                        int var452 = var148;
                                        if (var452 != 0) {
                                            int var458 = var117;
                                            if (var458 != 0) {
                                                float var461 = 110.0F;
                                                float var623 = var123;
                                                var462 = (var461 * var623);
                                            }
                                        } else {
                                            int var453 = var117;
                                            if (var453 != 0) {
                                                float var454 = 130.0F;
                                                float var608 = var123;
                                                var455 = (var454 * var608);
                                            } else {
                                                float var457 = 160.0F;
                                                float var616 = var123;
                                                var455 = (var457 * var616);
                                            }
                                            int var609 = var74;
                                            if (var609 != 0) {
                                                float var610 = var208;
                                                float var724 = var214;
                                                var615 = Math.max(var610, var724);
                                                float var459 = 150.0F;
                                                float var617 = var123;
                                                var462 = (var459 * var617);
                                            }
                                        }
                                        int var618 = var74;
                                        if (var618 != 0) {
                                            float var619 = var208;
                                            float var732 = var214;
                                            var622 = Math.max(var619, var732);
                                        } else {
                                            var622 = var211;
                                        }
                                        int var733 = var119;
                                        if (var733 != 0) {
                                            var737 = 24.0F;
                                        } else {
                                            var737 = 12.0F;
                                        }
                                        float var804 = var123;
                                        float var734 = (var737 * var804);
                                        float var620 = (var622 + var734);
                                        float var735 = 14.0F;
                                        float var805 = var123;
                                        float var736 = (var735 * var805);
                                        float var621 = (var620 + var736);
                                        float var460 = Math.max(var462, var621);
                                        var1188 = var460;
                                        var615 = var211;
                                        float var725 = 8.0F;
                                        float var801 = var123;
                                        float var726 = (var725 * var801);
                                        float var611 = (var615 + var726);
                                        float var727 = var219;
                                        float var612 = (var611 + var727);
                                        float var728 = 9.0F;
                                        float var802 = var123;
                                        float var729 = (var728 * var802);
                                        float var613 = (var612 + var729);
                                        float var730 = 14.0F;
                                        float var803 = var123;
                                        float var731 = (var730 * var803);
                                        float var614 = (var613 + var731);
                                        float var456 = Math.max(var455, var614);
                                        var1188 = var456;
                                    }
                                    if (var107) {
                                        float var521 = var1188;
                                        float var225 = (arg2 - var521);
                                        float var522 = 0.5F;
                                        var226 = (var225 * var522);
                                    } else {
                                        int var448 = var109;
                                        if (var448 != 0) {
                                            float var449 = arg2;
                                            float var606 = var1188;
                                            float var450 = (var449 - var606);
                                            float var607 = var128;
                                            var226 = (var450 - var607);
                                        } else {
                                            var226 = var128;
                                        }
                                        float var76 = var226;
                                        String var523 = var115;
                                        boolean var228 = "Slide".equals(var523);
                                        if (var228) {
                                            float var445 = 1.0F;
                                            float var603 = var196;
                                            float var446 = (var445 - var603);
                                            float var604 = 40.0F;
                                            float var447 = (var446 * var604);
                                            float var605 = var123;
                                            var229 = (var447 * var605);
                                        } else {
                                            var229 = 0.0F;
                                        }
                                        float var77 = var229;
                                        int var524 = var107;
                                        if (var524 != 0) {
                                            var602 = 0.0F;
                                        } else {
                                            int var525 = var109;
                                            if (var525 != 0) {
                                                var602 = var77;
                                            } else {
                                                var602 = var77;
                                            }
                                            float var231 = (var76 + var602);
                                            int var232 = Math.round(var231);
                                            float var233 = ((float) var232);
                                            int var234 = var61;
                                            float var235 = ((float) var234);
                                            float var526 = var1187;
                                            float var652 = var170;
                                            float var527 = (var526 + var652);
                                            float var236 = (var235 * var527);
                                            if (var111) {
                                                float var442 = arg3;
                                                float var599 = var1187;
                                                float var443 = (var442 - var599);
                                                float var600 = var131;
                                                float var444 = (var443 - var600);
                                                float var601 = var236;
                                                var239 = (var444 - var601);
                                            } else {
                                                float var528 = var236;
                                                var239 = (var131 + var528);
                                            }
                                            float var80 = var239;
                                            int var529 = var107;
                                            if (var529 != 0) {
                                                int var530 = var111;
                                                if (var530 != 0) {
                                                    var598 = var77;
                                                } else {
                                                    var598 = var77;
                                                }
                                            } else {
                                                var598 = 0.0F;
                                            }
                                            float var241 = (var80 + var598);
                                            int var242 = Math.round(var241);
                                            float var243 = ((float) var242);
                                            float var531 = var1187;
                                            float var653 = 0.5F;
                                            float var532 = (var531 * var653);
                                            float var245 = (var243 + var532);
                                            if (var96) {
                                                int var248 = Math.round(var76);
                                                float var249 = ((float) var248);
                                                int var251 = Math.round(var80);
                                                float var252 = ((float) var251);
                                                float var253 = var22;
                                                float var533 = var249;
                                                float var254 = Math.min(var253, var533);
                                                var22 = var254;
                                                float var255 = var23;
                                                float var534 = var252;
                                                float var256 = Math.min(var255, var534);
                                                var23 = var256;
                                                float var257 = var24;
                                                float var535 = var249;
                                                float var654 = var1188;
                                                float var536 = (var535 + var654);
                                                float var258 = Math.max(var257, var536);
                                                var24 = var258;
                                                float var259 = var25;
                                                float var537 = var252;
                                                float var655 = var1187;
                                                float var538 = (var537 + var655);
                                                float var260 = Math.max(var259, var538);
                                                var25 = var260;
                                            }
                                            if (var150) {
                                                float var419 = var1187;
                                                float var592 = 0.5F;
                                                float var420 = (var419 * var592);
                                                float var1190 = var420;
                                                long var421 = arg0;
                                                float var714 = var233;
                                                float var791 = var243;
                                                float var849 = var1188;
                                                float var913 = var1187;
                                                float var969 = var1190;
                                                int var1030 = var143;
                                                int var1094 = 0;
                                                int var1150 = var146;
                                                if (var1150 != 0) {
                                                    var1157 = 0.0F;
                                                } else {
                                                    var1157 = 0.25F;
                                                }
                                                int var1031 = mix(var1030, var1094, var1157);
                                                float var1095 = 0.92F;
                                                float var1151 = var198;
                                                float var1096 = (var1095 * var1151);
                                                int var1032 = tintA(var1031, var1096);
                                                ClarityDraw.fillRRect(var421, var714, var791, var849, var913, var969, var1032);
                                                long var423 = arg0;
                                                float var715 = var233;
                                                float var792 = var243;
                                                float var850 = var1188;
                                                float var914 = var1187;
                                                float var970 = var1190;
                                                float var1033 = 1.0F;
                                                int var1097 = var144;
                                                int var1152 = var69;
                                                float var1171 = 0.25F;
                                                int var1098 = mix(var1097, var1152, var1171);
                                                float var1153 = var198;
                                                int var1099 = tintA(var1098, var1153);
                                                ClarityDraw.strokeRRect(var423, var715, var792, var850, var914, var970, var1033, var1099);
                                                float var425 = 4.0F;
                                                float var593 = var123;
                                                float var426 = (var425 * var593);
                                                float var1198 = var426;
                                                long var427 = arg0;
                                                float var716 = var233;
                                                float var793 = 11.0F;
                                                float var851 = var123;
                                                float var794 = (var793 * var851);
                                                float var717 = (var716 + var794);
                                                float var795 = var1198;
                                                float var718 = (var717 - var795);
                                                float var796 = var245;
                                                float var852 = var1198;
                                                float var797 = (var796 - var852);
                                                float var853 = var1198;
                                                float var915 = 2.0F;
                                                float var854 = (var853 * var915);
                                                float var916 = var1198;
                                                float var971 = 2.0F;
                                                float var917 = (var916 * var971);
                                                float var972 = var1198;
                                                int var1034 = var69;
                                                float var1100 = var198;
                                                int var1035 = tintA(var1034, var1100);
                                                ClarityDraw.fillRRect(var427, var718, var797, var854, var917, var972, var1035);
                                                float var429 = var245;
                                                float var594 = var162;
                                                float var719 = 0.5F;
                                                float var595 = (var594 * var719);
                                                float var430 = (var429 - var595);
                                                float var1205 = var430;
                                                float var431 = var233;
                                                float var596 = 22.0F;
                                                float var720 = var123;
                                                float var597 = (var596 * var720);
                                                float var432 = (var431 + var597);
                                                float var1210 = var432;
                                                long var433 = arg0;
                                                String var721 = "inter-sb";
                                                float var798 = var162;
                                                float var855 = 0.0F;
                                                int var918 = var145;
                                                float var973 = var198;
                                                int var919 = tintA(var918, var973);
                                                int var974 = 9;
                                                float var1036 = var1210;
                                                float var1101 = var1205;
                                                NotificationToastEntry var1154 = var178;
                                                String var1155 = var1154.labelText;
                                                float var435 = ClarityDraw.text(var433, var721, var798, var855, var919, var974, var1036, var1101, var1155);
                                                long var436 = arg0;
                                                String var722 = "inter-sb";
                                                float var799 = var162;
                                                float var856 = 0.0F;
                                                NotificationToastEntry var920 = var178;
                                                String var921 = var920.labelText;
                                                String var922 = (var921) + "  ";
                                                float var438 = ClarityDraw.textWidth(var436, var722, var799, var856, var922);
                                                float var1213 = var438;
                                                long var439 = arg0;
                                                String var723 = "inter-med";
                                                float var800 = var162;
                                                float var857 = 0.0F;
                                                int var923 = var69;
                                                float var975 = var198;
                                                int var924 = tintA(var923, var975);
                                                int var976 = 9;
                                                float var1037 = var1210;
                                                float var1102 = var1213;
                                                float var1038 = (var1037 + var1102);
                                                float var1103 = var1205;
                                                String var1156 = var205;
                                                float var441 = ClarityDraw.text(var439, var723, var800, var857, var924, var976, var1038, var1103, var1156);
                                            } else {
                                                if (var154) {
                                                    var491 = 10.0F;
                                                } else {
                                                    if (var156) {
                                                        var491 = 12.0F;
                                                    } else {
                                                        var491 = 10.0F;
                                                    }
                                                    float var539 = var123;
                                                    float var264 = (var491 * var539);
                                                    float var1189 = var264;
                                                    if (var156) {
                                                        if (var56 > 0) {
                                                            MemoryStack var267 = MemoryStack.stackPush();
                                                            MemoryStack var1191 = var267;
                                                            try {
                                                                NVGPaint var269 = NVGPaint.malloc(var1191);
                                                                float var656 = 0.0F;
                                                                float var747 = 0.0F;
                                                                float var810 = arg2;
                                                                float var866 = arg3;
                                                                float var926 = 0.0F;
                                                                NVGPaint var272 = NanoVG.nvgImagePattern(arg0, var656, var747, var810, var866, var926, var56, var198, var269);
                                                                NanoVG.nvgBeginPath(arg0);
                                                                float var657 = var233;
                                                                float var748 = var243;
                                                                float var811 = var1188;
                                                                float var867 = var1187;
                                                                float var927 = var1189;
                                                                NanoVG.nvgRoundedRect(arg0, var657, var748, var811, var867, var927);
                                                                NVGPaint var658 = var269;
                                                                NanoVG.nvgFillPaint(arg0, var658);
                                                                NanoVG.nvgFill(arg0);
                                                            } catch (Throwable ignored) {
                                                            }
                                                            if (var1191 != null) {
                                                                var1191.close();
                                                            }
                                                        }
                                                    } else {
                                                        int var400 = var152;
                                                        if (var400 != 0) {
                                                            long var401 = arg0;
                                                            float var705 = var233;
                                                            float var784 = var243;
                                                            float var842 = var1188;
                                                            float var906 = var1187;
                                                            float var962 = var1189;
                                                            int var1017 = var143;
                                                            int var1079 = var69;
                                                            float var1140 = 0.07F;
                                                            int var1018 = mix(var1017, var1079, var1140);
                                                            float var1080 = var198;
                                                            int var1019 = tintA(var1018, var1080);
                                                            ClarityDraw.fillRRect(var401, var705, var784, var842, var906, var962, var1019);
                                                            long var403 = arg0;
                                                            float var706 = var233;
                                                            float var785 = var243;
                                                            float var843 = var1188;
                                                            float var907 = var1187;
                                                            float var963 = var1189;
                                                            float var1020 = 1.0F;
                                                            int var1081 = var144;
                                                            int var1141 = var69;
                                                            float var1166 = 0.35F;
                                                            int var1082 = mix(var1081, var1141, var1166);
                                                            float var1142 = var198;
                                                            int var1083 = tintA(var1082, var1142);
                                                            ClarityDraw.strokeRRect(var403, var706, var785, var843, var907, var963, var1020, var1083);
                                                            long var405 = arg0;
                                                            float var707 = var233;
                                                            float var786 = var243;
                                                            float var844 = var1188;
                                                            float var908 = var1187;
                                                            float var964 = var1189;
                                                            float var1021 = var233;
                                                            float var1084 = var243;
                                                            float var1143 = var233;
                                                            float var1167 = var1188;
                                                            float var1174 = 0.55F;
                                                            float var1168 = (var1167 * var1174);
                                                            float var1144 = (var1143 + var1168);
                                                            float var1169 = var243;
                                                            int var1175 = var69;
                                                            float var1180 = 0.2F;
                                                            float var1185 = var198;
                                                            float var1181 = (var1180 * var1185);
                                                            int var1176 = tintA(var1175, var1181);
                                                            int var1182 = var69;
                                                            float var1186 = 0.0F;
                                                            int var1183 = tintA(var1182, var1186);
                                                            ClarityDraw.fillRRectLinear(var405, var707, var786, var844, var908, var964, var1021, var1084, var1144, var1169, var1176, var1183);
                                                            int var407 = var143;
                                                            int var589 = var146;
                                                            if (var589 != 0) {
                                                                var590 = 0;
                                                            }
                                                        } else {
                                                            long var411 = arg0;
                                                            float var710 = var233;
                                                            float var788 = var243;
                                                            float var846 = var1188;
                                                            float var910 = var1187;
                                                            float var966 = var1189;
                                                            int var1024 = var143;
                                                            int var1087 = var134;
                                                            float var1146 = 0.02F;
                                                            int var1025 = mix(var1024, var1087, var1146);
                                                            float var1088 = var198;
                                                            int var1026 = tintA(var1025, var1088);
                                                            ClarityDraw.fillRRect(var411, var710, var788, var846, var910, var966, var1026);
                                                            long var413 = arg0;
                                                            float var711 = var233;
                                                            float var789 = var243;
                                                            float var847 = var1188;
                                                            float var911 = var1187;
                                                            float var967 = var1189;
                                                            float var1027 = 1.0F;
                                                            int var1089 = var144;
                                                            int var1147 = var134;
                                                            float var1170 = 0.1F;
                                                            int var1090 = mix(var1089, var1147, var1170);
                                                            float var1148 = var198;
                                                            int var1091 = tintA(var1090, var1148);
                                                            ClarityDraw.strokeRRect(var413, var711, var789, var847, var911, var967, var1027, var1091);
                                                            int var415 = var143;
                                                            int var591 = var146;
                                                            if (var591 != 0) {
                                                                var637 = 0;
                                                                var590 = 16777215;
                                                            }
                                                        }
                                                        float var708 = 0.12F;
                                                        int var408 = mix(var407, var590, var708);
                                                        int var1196 = var408;
                                                        long var409 = arg0;
                                                        float var709 = var233;
                                                        float var787 = var243;
                                                        float var845 = var1188;
                                                        float var909 = var1187;
                                                        float var965 = var1189;
                                                        int var1022 = var1196;
                                                        float var1085 = 0.5F;
                                                        float var1145 = var198;
                                                        float var1086 = (var1085 * var1145);
                                                        int var1023 = tintA(var1022, var1086);
                                                        ClarityDraw.topHighlight(var409, var709, var787, var845, var909, var965, var1023);
                                                    }
                                                    float var659 = var233;
                                                    float var749 = var243;
                                                    float var812 = var1188;
                                                    float var868 = var1187;
                                                    float var928 = var1189;
                                                    int var977 = var143;
                                                    int var1039 = 0;
                                                    int var1104 = var146;
                                                    if (var1104 != 0) {
                                                        var1105 = 0.0F;
                                                    } else {
                                                        var1105 = 0.35F;
                                                    }
                                                    int var978 = mix(var977, var1039, var1105);
                                                    Notifications var1040 = var5;
                                                    float var1041 = var1040.getBlurTint();
                                                    float var1106 = var198;
                                                    float var1042 = (var1041 * var1106);
                                                    int var979 = tintA(var978, var1042);
                                                    ClarityDraw.fillRRect(arg0, var659, var749, var812, var868, var928, var979);
                                                    long var398 = arg0;
                                                    float var704 = var233;
                                                    float var783 = var243;
                                                    float var841 = var1188;
                                                    float var905 = var1187;
                                                    float var961 = var1189;
                                                    int var1014 = var143;
                                                    int var1076 = 0;
                                                    float var1138 = 0.2F;
                                                    int var1015 = mix(var1014, var1076, var1138);
                                                    float var1077 = 0.72F;
                                                    float var1139 = var198;
                                                    float var1078 = (var1077 * var1139);
                                                    int var1016 = tintA(var1015, var1078);
                                                    ClarityDraw.fillRRect(var398, var704, var783, var841, var905, var961, var1016);
                                                    float var660 = var233;
                                                    float var750 = var243;
                                                    float var813 = var1188;
                                                    float var869 = var1187;
                                                    float var929 = var1189;
                                                    float var980 = 1.0F;
                                                    int var1043 = 16777215;
                                                    float var1107 = 0.16F;
                                                    float var1108 = (var1107 * var198);
                                                    int var1044 = tintA(var1043, var1108);
                                                    ClarityDraw.strokeRRect(arg0, var660, var750, var813, var869, var929, var980, var1044);
                                                    float var661 = var233;
                                                    float var751 = var243;
                                                    float var814 = var1188;
                                                    float var870 = var1187;
                                                    float var930 = var1189;
                                                    int var981 = 16777215;
                                                    float var1045 = 0.22F;
                                                    float var1109 = var198;
                                                    float var1046 = (var1045 * var1109);
                                                    int var982 = tintA(var981, var1046);
                                                    ClarityDraw.topHighlight(arg0, var661, var751, var814, var870, var930, var982);
                                                    float var662 = var233;
                                                    float var752 = var243;
                                                    float var815 = var1188;
                                                    float var871 = var1187;
                                                    float var931 = var1189;
                                                    float var983 = var233;
                                                    float var1047 = var243;
                                                    float var1110 = var233;
                                                    float var1158 = var1188;
                                                    float var1159 = (var1158 * 0.5F);
                                                    float var1111 = (var1110 + var1159);
                                                    float var1160 = var243;
                                                    int var1172 = var69;
                                                    float var1177 = (0.16F * var198);
                                                    int var1173 = tintA(var1172, var1177);
                                                    int var1178 = var69;
                                                    float var1184 = 0.0F;
                                                    int var1179 = tintA(var1178, var1184);
                                                    ClarityDraw.fillRRectLinear(arg0, var662, var752, var815, var871, var931, var983, var1047, var1111, var1160, var1173, var1179);
                                                    var637 = 16777215;
                                                    float var712 = 0.12F;
                                                    int var416 = mix(var415, var637, var712);
                                                    int var1197 = var416;
                                                    long var417 = arg0;
                                                    float var713 = var233;
                                                    float var790 = var243;
                                                    float var848 = var1188;
                                                    float var912 = var1187;
                                                    float var968 = var1189;
                                                    int var1028 = var1197;
                                                    float var1092 = 0.66F;
                                                    float var1149 = var198;
                                                    float var1093 = (var1092 * var1149);
                                                    int var1029 = tintA(var1028, var1093);
                                                    ClarityDraw.topHighlight(var417, var713, var790, var848, var912, var968, var1029);
                                                    if (var148) {
                                                        if (var119) {
                                                            var372 = 24.0F;
                                                        }
                                                    } else {
                                                        if (var154) {
                                                            String var663 = "inter-sb";
                                                            float var753 = var162;
                                                            float var816 = 0.0F;
                                                            int var872 = var145;
                                                            float var932 = var198;
                                                            int var873 = tintA(var872, var932);
                                                            int var933 = 9;
                                                            float var984 = var233;
                                                            float var1048 = 14.0F;
                                                            float var1112 = var123;
                                                            float var1049 = (var1048 * var1112);
                                                            float var985 = (var984 + var1049);
                                                            float var1050 = var245;
                                                            float var1113 = var162;
                                                            float var1161 = 0.5F;
                                                            float var1114 = (var1113 * var1161);
                                                            float var1051 = (var1050 - var1114);
                                                            NotificationToastEntry var1115 = var178;
                                                            String var1116 = var1115.labelText;
                                                            float var301 = ClarityDraw.text(arg0, var663, var753, var816, var873, var933, var985, var1051, var1116);
                                                            float var541 = var123;
                                                            float var303 = (32.0F * var541);
                                                            float var1192 = var303;
                                                            float var542 = var123;
                                                            float var305 = (18.0F * var542);
                                                            float var1200 = var305;
                                                            float var543 = var1188;
                                                            float var307 = (var233 + var543);
                                                            float var544 = var1192;
                                                            float var308 = (var307 - var544);
                                                            float var545 = 12.0F;
                                                            float var664 = var123;
                                                            float var546 = (var545 * var664);
                                                            float var309 = (var308 - var546);
                                                            float var1206 = var309;
                                                            float var547 = var1200;
                                                            float var665 = 0.5F;
                                                            float var548 = (var547 * var665);
                                                            float var311 = (var245 - var548);
                                                            float var549 = var183;
                                                            float var666 = 300.0F;
                                                            float var550 = (var549 / var666);
                                                            float var313 = Math.min(1.0F, var550);
                                                            float var551 = 1.0F;
                                                            float var667 = var313;
                                                            float var552 = (var551 - var667);
                                                            float var668 = 1.0F;
                                                            float var754 = var313;
                                                            float var669 = (var668 - var754);
                                                            float var553 = (var552 * var669);
                                                            float var670 = 1.0F;
                                                            float var755 = var313;
                                                            float var671 = (var670 - var755);
                                                            float var554 = (var553 * var671);
                                                            float var315 = (1.0F - var554);
                                                            float var1214 = var315;
                                                            if (var202) {
                                                                var489 = var1214;
                                                            }
                                                        } else {
                                                            float var573 = 8.0F;
                                                            float var685 = var123;
                                                            float var574 = (var573 * var685);
                                                            float var345 = (var233 + var574);
                                                            float var575 = var219;
                                                            float var686 = 0.5F;
                                                            float var576 = (var575 * var686);
                                                            float var346 = (var345 + var576);
                                                            float var1194 = var346;
                                                            float var687 = var1194;
                                                            float var768 = var245;
                                                            float var826 = var219;
                                                            int var879 = var202;
                                                            int var940 = var69;
                                                            float var995 = var198;
                                                            float var1056 = var123;
                                                            statusIcon(arg0, var687, var768, var826, var879, var940, var995, var1056);
                                                            float var577 = 8.0F;
                                                            float var688 = var123;
                                                            float var578 = (var577 * var688);
                                                            float var350 = (var233 + var578);
                                                            float var579 = var219;
                                                            float var351 = (var350 + var579);
                                                            float var580 = 9.0F;
                                                            float var689 = var123;
                                                            float var581 = (var580 * var689);
                                                            float var352 = (var351 + var581);
                                                            float var1202 = var352;
                                                            if (var117) {
                                                                float var582 = var162;
                                                                float var690 = 0.5F;
                                                                float var583 = (var582 * var690);
                                                                float var355 = (var245 - var583);
                                                                float var1208 = var355;
                                                                String var691 = "inter-sb";
                                                                float var769 = var162;
                                                                float var827 = 0.0F;
                                                                int var880 = var145;
                                                                float var941 = var198;
                                                                int var881 = tintA(var880, var941);
                                                                int var942 = 9;
                                                                float var996 = var1202;
                                                                float var1057 = var1208;
                                                                NotificationToastEntry var1118 = var178;
                                                                String var1119 = var1118.labelText;
                                                                float var358 = ClarityDraw.text(arg0, var691, var769, var827, var881, var942, var996, var1057, var1119);
                                                                String var692 = "inter-sb";
                                                                float var770 = var162;
                                                                float var828 = 0.0F;
                                                                NotificationToastEntry var882 = var178;
                                                                String var883 = var882.labelText;
                                                                String var884 = (var883) + "  ";
                                                                float var361 = ClarityDraw.textWidth(arg0, var692, var770, var828, var884);
                                                                float var1212 = var361;
                                                                String var693 = "inter-med";
                                                                float var771 = var162;
                                                                float var829 = 0.0F;
                                                                int var885 = var69;
                                                                float var943 = var198;
                                                                int var886 = tintA(var885, var943);
                                                                int var944 = 9;
                                                                float var997 = var1202;
                                                                float var1058 = var1212;
                                                                float var998 = (var997 + var1058);
                                                                float var1059 = var1208;
                                                                String var1120 = var205;
                                                                float var364 = ClarityDraw.text(arg0, var693, var771, var829, var886, var944, var998, var1059, var1120);
                                                                float var572 = var1214;
                                                                var489 = (1.0F - var572);
                                                            }
                                                        }
                                                        float var89 = var489;
                                                        int var555 = 0;
                                                        float var672 = 0.1F;
                                                        int var318 = mix(var144, var555, var672);
                                                        int var556 = var69;
                                                        float var673 = var89;
                                                        int var319 = mix(var318, var556, var673);
                                                        float var674 = var1206;
                                                        float var756 = var311;
                                                        float var817 = var1192;
                                                        float var874 = var1200;
                                                        float var934 = var1200;
                                                        float var986 = 0.5F;
                                                        float var935 = (var934 * var986);
                                                        int var987 = var319;
                                                        float var1052 = var198;
                                                        int var988 = tintA(var987, var1052);
                                                        ClarityDraw.fillRRect(arg0, var674, var756, var817, var874, var935, var988);
                                                        float var557 = 4.0F;
                                                        float var675 = var123;
                                                        float var558 = (var557 * var675);
                                                        float var323 = (var1200 - var558);
                                                        float var559 = 2.0F;
                                                        float var676 = var123;
                                                        float var560 = (var559 * var676);
                                                        float var325 = (var1206 + var560);
                                                        float var561 = var89;
                                                        float var677 = var1192;
                                                        float var757 = var1200;
                                                        float var678 = (var677 - var757);
                                                        float var562 = (var561 * var678);
                                                        float var326 = (var325 + var562);
                                                        float var679 = var326;
                                                        float var758 = var311;
                                                        float var818 = 2.0F;
                                                        float var875 = var123;
                                                        float var819 = (var818 * var875);
                                                        float var759 = (var758 + var819);
                                                        float var820 = var323;
                                                        float var876 = var323;
                                                        float var936 = var323;
                                                        float var989 = 0.5F;
                                                        float var937 = (var936 * var989);
                                                        int var990 = 16777215;
                                                        float var1053 = var198;
                                                        int var991 = tintA(var990, var1053);
                                                        ClarityDraw.fillRRect(arg0, var679, var759, var820, var876, var937, var991);
                                                        var372 = 12.0F;
                                                    }
                                                    float var584 = var123;
                                                    float var373 = (var372 * var584);
                                                    float var1195 = var373;
                                                    if (var119) {
                                                        if (var117) {
                                                            float var397 = 7.0F;
                                                            float var588 = var123;
                                                            var488 = (var397 * var588);
                                                        } else {
                                                            float var585 = var123;
                                                            var488 = (10.0F * var585);
                                                        }
                                                        float var1203 = var488;
                                                        float var696 = var233;
                                                        float var774 = 10.0F;
                                                        float var832 = var123;
                                                        float var775 = (var774 * var832);
                                                        float var697 = (var696 + var775);
                                                        float var776 = var243;
                                                        float var833 = var1203;
                                                        float var777 = (var776 + var833);
                                                        float var834 = 4.0F;
                                                        float var891 = var123;
                                                        float var835 = (var834 * var891);
                                                        float var892 = var1187;
                                                        float var949 = var1203;
                                                        float var1001 = 2.0F;
                                                        float var950 = (var949 * var1001);
                                                        float var893 = (var892 - var950);
                                                        float var951 = 2.0F;
                                                        float var1002 = var123;
                                                        float var952 = (var951 * var1002);
                                                        int var1003 = var69;
                                                        float var1064 = var198;
                                                        int var1004 = tintA(var1003, var1064);
                                                        ClarityDraw.fillRRect(arg0, var697, var777, var835, var893, var952, var1004);
                                                    }
                                                    int var379 = var117;
                                                    if (var379 != 0) {
                                                        float var386 = var245;
                                                        float var586 = var162;
                                                        float var700 = 0.5F;
                                                        float var587 = (var586 * var700);
                                                        float var387 = (var386 - var587);
                                                        float var1204 = var387;
                                                        long var388 = arg0;
                                                        String var701 = "inter-sb";
                                                        float var780 = var162;
                                                        float var838 = 0.0F;
                                                        int var898 = var134;
                                                        float var957 = var198;
                                                        int var899 = tintA(var898, var957);
                                                        int var958 = 9;
                                                        float var1009 = var233;
                                                        float var1071 = var1195;
                                                        float var1010 = (var1009 + var1071);
                                                        float var1072 = var1204;
                                                        NotificationToastEntry var1135 = var178;
                                                        String var1136 = var1135.labelText;
                                                        float var390 = ClarityDraw.text(var388, var701, var780, var838, var899, var958, var1010, var1072, var1136);
                                                        long var391 = arg0;
                                                        String var702 = "inter-sb";
                                                        float var781 = var162;
                                                        float var839 = 0.0F;
                                                        NotificationToastEntry var900 = var178;
                                                        String var901 = var900.labelText;
                                                        String var902 = (var901) + "  ";
                                                        float var393 = ClarityDraw.textWidth(var391, var702, var781, var839, var902);
                                                        float var1209 = var393;
                                                        long var394 = arg0;
                                                        String var703 = "inter-med";
                                                        float var782 = var162;
                                                        float var840 = 0.0F;
                                                        int var903 = var69;
                                                        float var959 = var198;
                                                        int var904 = tintA(var903, var959);
                                                        int var960 = 9;
                                                        float var1011 = var233;
                                                        float var1073 = var1195;
                                                        float var1012 = (var1011 + var1073);
                                                        float var1074 = var1209;
                                                        float var1013 = (var1012 + var1074);
                                                        float var1075 = var1204;
                                                        String var1137 = var205;
                                                        float var396 = ClarityDraw.text(var394, var703, var782, var840, var904, var960, var1013, var1075, var1137);
                                                    } else {
                                                        long var380 = arg0;
                                                        String var698 = "inter-sb";
                                                        float var778 = var162;
                                                        float var836 = 0.0F;
                                                        int var894 = var134;
                                                        float var953 = var198;
                                                        int var895 = tintA(var894, var953);
                                                        int var954 = 9;
                                                        float var1005 = var233;
                                                        float var1065 = var1195;
                                                        float var1006 = (var1005 + var1065);
                                                        float var1066 = var243;
                                                        float var1128 = 8.0F;
                                                        float var1164 = var123;
                                                        float var1129 = (var1128 * var1164);
                                                        float var1067 = (var1066 + var1129);
                                                        NotificationToastEntry var1130 = var178;
                                                        String var1131 = var1130.labelText;
                                                        float var382 = ClarityDraw.text(var380, var698, var778, var836, var895, var954, var1006, var1067, var1131);
                                                        long var383 = arg0;
                                                        String var699 = "inter-med";
                                                        float var779 = var164;
                                                        float var837 = 0.0F;
                                                        int var896 = var69;
                                                        float var955 = var198;
                                                        int var897 = tintA(var896, var955);
                                                        int var956 = 9;
                                                        float var1007 = var233;
                                                        float var1068 = var1195;
                                                        float var1008 = (var1007 + var1068);
                                                        float var1069 = var243;
                                                        float var1132 = 23.0F;
                                                        float var1165 = var123;
                                                        float var1133 = (var1132 * var1165);
                                                        float var1070 = (var1069 + var1133);
                                                        String var1134 = var205;
                                                        float var385 = ClarityDraw.text(var383, var699, var779, var837, var897, var956, var1008, var1070, var1134);
                                                        String var694 = "inter-sb";
                                                        float var772 = var162;
                                                        float var830 = 0.0F;
                                                        int var887 = var145;
                                                        float var945 = var198;
                                                        int var888 = tintA(var887, var945);
                                                        int var946 = 9;
                                                        float var999 = var1202;
                                                        float var1060 = var243;
                                                        float var1121 = 8.0F;
                                                        float var1162 = var123;
                                                        float var1122 = (var1121 * var1162);
                                                        float var1061 = (var1060 + var1122);
                                                        NotificationToastEntry var1123 = var178;
                                                        String var1124 = var1123.labelText;
                                                        float var367 = ClarityDraw.text(arg0, var694, var772, var830, var888, var946, var999, var1061, var1124);
                                                        String var695 = "inter-med";
                                                        float var773 = var164;
                                                        float var831 = 0.0F;
                                                        int var889 = var69;
                                                        float var947 = var198;
                                                        int var890 = tintA(var889, var947);
                                                        int var948 = 9;
                                                        float var1000 = var1202;
                                                        float var1062 = var243;
                                                        float var1125 = 24.0F;
                                                        float var1163 = var123;
                                                        float var1126 = (var1125 * var1163);
                                                        float var1063 = (var1062 + var1126);
                                                        String var1127 = var205;
                                                        float var370 = ClarityDraw.text(arg0, var695, var773, var831, var890, var948, var1000, var1063, var1127);
                                                    }
                                                    if (var121) {
                                                        float var563 = 1.0F;
                                                        float var680 = var190;
                                                        NotificationToastEntry var760 = var178;
                                                        long var761 = var760.timestampMs1;
                                                        float var763 = ((float) var761);
                                                        float var681 = (var680 / var763);
                                                        float var564 = Math.min(var563, var681);
                                                        float var331 = Math.max(0.0F, var564);
                                                        float var1193 = var331;
                                                        float var565 = var123;
                                                        float var333 = (2.0F * var565);
                                                        float var1201 = var333;
                                                        float var566 = var1189;
                                                        float var682 = 0.6F;
                                                        float var567 = (var566 * var682);
                                                        float var335 = (var233 + var567);
                                                        float var1207 = var335;
                                                        float var336 = var1188;
                                                        float var568 = var1189;
                                                        float var683 = 1.2F;
                                                        float var569 = (var568 * var683);
                                                        float var337 = (var336 - var569);
                                                        float var570 = var1193;
                                                        float var338 = (var337 * var570);
                                                        float var1211 = var338;
                                                        float var571 = 1.0F;
                                                        int var340 = Float.compare(var1211, var571);
                                                        if (var340 > 0) {
                                                            float var684 = var1207;
                                                            float var764 = var243;
                                                            float var821 = var1187;
                                                            float var765 = (var764 + var821);
                                                            float var822 = var1201;
                                                            float var766 = (var765 - var822);
                                                            float var823 = 2.0F;
                                                            float var877 = var123;
                                                            float var824 = (var823 * var877);
                                                            float var767 = (var766 - var824);
                                                            float var825 = var1211;
                                                            float var878 = var1201;
                                                            float var938 = var1201;
                                                            float var992 = 0.5F;
                                                            float var939 = (var938 * var992);
                                                            int var993 = var69;
                                                            float var1054 = var198;
                                                            float var1117 = 0.85F;
                                                            float var1055 = (var1054 * var1117);
                                                            int var994 = tintA(var993, var1055);
                                                            ClarityDraw.fillRRect(arg0, var684, var767, var825, var878, var939, var994);
                                                        }
                                                        var60 = (var60 + -1);
                                                        var61 = (var61 + 1);
                                                        Object var1199 = var281;
                                                        if (var1191 == null) break;
                                                        try {
                                                            var1191.close();
                                                        } catch (Throwable ignored) {
                                                        }
                                                        Object var540 = var284;
                                                        var1199.addSuppressed(var540);
                                                        throw var1199;
                                                        int var469 = var96;
                                                        if (var469 == 0) break;
                                                        float var470 = var22;
                                                        float var632 = var24;
                                                        int var471 = Float.compare(var470, var632);
                                                        if (var471 >= 0) break;
                                                        String var472 = "Notifications";
                                                        float var633 = var22;
                                                        float var742 = var23;
                                                        float var806 = var24;
                                                        float var858 = var22;
                                                        float var807 = (var806 - var858);
                                                        float var859 = var25;
                                                        float var925 = var23;
                                                        float var860 = (var859 - var925);
                                                        HudLayout.report(var472, var633, var742, var807, var860);
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

    static float bottomCenterClear(Minecraft arg0, float arg1) {
        float var33 = 0.0F;
        int var7 = 0;
        int var28 = 0;
        float var11 = ((float) arg0.getWindow().getWidth());
        float var29 = Math.max(1.0F, arg1);
        float var12 = (var11 / var29);
        float var16 = ((float) arg0.getWindow().getGuiScale());
        float var31 = Math.max(0.01F, var12);
        float var17 = (var16 / var31);
        var7 = 0;
        try {
            if (arg0.gameMode != null) {
                if (arg0.gameMode.canHurtPlayer()) {
                    var28 = 1;
                } else {
                    var28 = 0;
                }
                var7 = var28;
            }
        } catch (Throwable ignored) {
        }
        if (var7 != 0) {
            var33 = 38.0F;
        } else {
            var33 = 4.0F;
        }
        return ((22.0F + var33) * var17);
    }

    private static void drawTarget(long arg0, Minecraft arg1, float arg2, float arg3) {
        int var521 = 0;
        String var523 = null;
        float var524 = 0.0F;
        float var525 = 0.0F;
        LivingEntity var17 = null;
        String var21 = null;
        float var22 = 0.0F;
        int var23 = 0;
        String var24 = null;
        int var25 = 0;
        int var26 = 0;
        int var27 = 0;
        int var35 = 0;
        int var62 = 0;
        float var72 = 0.0F;
        int var191 = 0;
        int var271 = 0;
        float var288 = 0.0F;
        float var343 = 0.0F;
        int var344 = 0;
        float var387 = 0.0F;
        float var388 = 0.0F;
        float var476 = 0.0F;
        int var501 = 0;
        TargetHud var5 = TargetHud.INSTANCE;
        if (var5 != null) {
            boolean var55 = var5.isEnabled();
            if (var55) {
                LocalPlayer var57 = arg1.player;
                if (var57 == null) {
                }
            }
            return;
            var17 = null;
            try {
                Object var59 = Modules.aimAssistCurrentTarget();
                LivingEntity var60 = ((LivingEntity) var59);
                var17 = var60;
            } catch (Throwable ignored) {
            }
            LivingEntity var61 = var17;
            if (var61 != null) {
                var62 = 1;
            } else {
                var62 = 0;
            }
            int var18 = var62;
            boolean var63 = HudLayout.isEditing();
            float var64 = TGT_ANIM;
            if (var18 == 0) {
                if (var63) {
                }
            }
            var343 = 1.0F;
            var343 = 0.0F;
            float var7 = TGT_ANIM;
            float var273 = (var343 - var7);
            float var274 = (var273 * 0.12F);
            float var65 = (var64 + var274);
            TGT_ANIM = var65;
            if (var18 == 0) {
                if (!var63) {
                    float var68 = TGT_ANIM;
                    int var69 = Float.compare(var68, 0.01F);
                    if (var69 < 0) {
                        return;
                    }
                }
            }
            if (var63) {
                var72 = 1.0F;
            } else {
                float var346 = TGT_ANIM;
                float var277 = Math.min(1.0F, var346);
                var72 = Math.max(0.0F, var277);
            }
            float var20 = var72;
            if (var18 == 0) {
                if (var63) {
                    var21 = "Target";
                    var22 = 0.7F;
                    var23 = 4;
                    var24 = "A10  Netherite Sword";
                    boolean var80 = var5.isShowGear();
                    var25 = var80;
                    boolean var82 = var5.isShowDistance();
                    var26 = var82;
                    boolean var84 = var5.isShowHealth();
                    var27 = var84;
                }
            } else {
                if (var18 != 0) {
                    LivingEntity var230 = var17;
                    Component var231 = var230.getName();
                    String var232 = var231.getString();
                    var21 = var232;
                    LivingEntity var332 = var17;
                    float var333 = var332.getHealth();
                    float var234 = Math.max(0.0F, var333);
                    LivingEntity var334 = var17;
                    float var335 = var334.getMaxHealth();
                    float var236 = Math.max(1.0F, var335);
                    float var384 = var234;
                    float var404 = var236;
                    float var385 = (var384 / var404);
                    float var337 = Math.min(1.0F, var385);
                    float var238 = Math.max(0.0F, var337);
                    var22 = var238;
                    LocalPlayer var240 = arg1.player;
                    LivingEntity var338 = var17;
                    float var241 = var240.distanceTo(var338);
                    int var242 = ((int) var241);
                    var23 = var242;
                    var521 = 0;
                    try {
                        LivingEntity var244 = var17;
                        int var245 = var244.getArmorValue();
                        var521 = var245;
                    } catch (Throwable ignored) {
                    }
                    LivingEntity var247 = var17;
                    InteractionHand var339 = InteractionHand.MAIN_HAND;
                    ItemStack var248 = var247.getItemInHand(var339);
                    Component var249 = var248.getHoverName();
                    String var250 = var249.getString();
                    var523 = var250;
                    String var251 = var523;
                    int var252 = var251.length();
                    if (var252 > 14) {
                        String var253 = var523;
                        int var386 = 14;
                        String var254 = var253.substring(0, var386);
                        var523 = var254;
                    }
                    int var255 = var521;
                    String var342 = var523;
                    String var256 = "A" + (var255) + "  " + (var342);
                    var24 = var256;
                    boolean var258 = var5.isShowGear();
                    var25 = var258;
                    boolean var260 = var5.isShowDistance();
                    var26 = var260;
                    boolean var262 = var5.isShowHealth();
                    var27 = var262;
                    String var263 = var21;
                    C_NAME = var263;
                    float var264 = var22;
                    C_RATIO = var264;
                    int var265 = var23;
                    C_DIST = var265;
                    String var266 = var24;
                    C_GEAR = var266;
                    int var267 = var25;
                    C_SHOWGEAR = var267;
                    int var268 = var26;
                    C_SHOWDIST = var268;
                    int var269 = var27;
                    C_SHOWHEALTH = var269;
                } else {
                    String var223 = C_NAME;
                    var21 = var223;
                    float var224 = C_RATIO;
                    var22 = var224;
                    int var225 = C_DIST;
                    var23 = var225;
                    String var226 = C_GEAR;
                    var24 = var226;
                    boolean var227 = C_SHOWGEAR;
                    var25 = var227;
                    boolean var228 = C_SHOWDIST;
                    var26 = var228;
                    boolean var229 = C_SHOWHEALTH;
                    var27 = var229;
                }
                int var86 = var5.getWidth();
                float var87 = ((float) var86);
                int var90 = var5.getX();
                float var91 = ((float) var90);
                int var93 = var5.getY();
                float var94 = ((float) var93);
                String var96 = var5.getAnchor();
                boolean var98 = "Bottom Center".equals(var96);
                var35 = -1;
                int var102 = var96.hashCode();
                switch (var102) {
                    case -1776156694 -> {}
                    case -960744398 -> {}
                    case 287355729 -> {}
                    case 999535836 -> {}
                    default -> {}
                }
                boolean var217 = var96.equals("Top Left");
                if (var217) {
                    var35 = 0;
                    boolean var104 = var96.equals("Bottom Left");
                    if (var104) {
                        var35 = 2;
                        boolean var220 = var96.equals("Top Right");
                        if (var220) {
                            var35 = 1;
                            boolean var214 = var96.equals("Bottom Center");
                            if (var214) {
                                var35 = 3;
                            }
                            int var106 = var35;
                            switch (var106) {
                                case 0 -> {}
                                case 1 -> {}
                                case 2 -> {}
                                case 3 -> {}
                                default -> {}
                            }
                            float var193 = (arg2 - var87);
                            float var194 = (var193 * 0.5F);
                            var524 = var194;
                            float var196 = (arg3 - 65.0F);
                            float var319 = bottomCenterClear(arg1, arg2);
                            float var197 = (var196 - var319);
                            float var198 = (var197 - var94);
                            var525 = var198;
                            var524 = var91;
                            var525 = var94;
                            float var200 = (arg2 - var87);
                            float var201 = (var200 - var91);
                            var524 = var201;
                            var525 = var94;
                            var524 = var91;
                            float var211 = (arg3 - 65.0F);
                            float var212 = (var211 - var94);
                            var525 = var212;
                            float var204 = (arg2 - var87);
                            float var205 = (var204 - var91);
                            var524 = var205;
                            float var207 = (arg3 - 65.0F);
                            float var208 = (var207 - var94);
                            var525 = var208;
                            boolean var110 = var96.endsWith("Left");
                            if (!var110) {
                                var191 = 1;
                            } else {
                                var191 = 0;
                            }
                            int var526 = var191;
                            boolean var112 = var96.startsWith("Bottom");
                            float var282 = var524;
                            int var283 = Math.round(var282);
                            float var284 = ((float) var283);
                            float var347 = var525;
                            int var348 = Math.round(var347);
                            float var349 = ((float) var348);
                            HudLayout.report("Target Hud", var284, var349, var87, 65.0F);
                            float var114 = var524;
                            if (var98) {
                                var288 = 0.0F;
                            } else {
                                float var287 = (1.0F - var20);
                                if (var526 != 0) {
                                    var388 = 28.0F;
                                } else {
                                    var388 = -28.0F;
                                }
                                var288 = (var287 * var388);
                            }
                            float var115 = (var114 + var288);
                            int var116 = Math.round(var115);
                            float var117 = ((float) var116);
                            float var118 = var525;
                            float var290 = (1.0F - var20);
                            if (var112) {
                                var387 = 16.0F;
                            } else {
                                var387 = -16.0F;
                            }
                            float var291 = (var290 * var387);
                            float var119 = (var118 + var291);
                            int var120 = Math.round(var119);
                            float var121 = ((float) var120);
                            int var122 = ThemeColors.getAccentRgb();
                            int var123 = ThemeColors.getSurfaceRgb();
                            int var124 = mix(var123, var122, 0.02F);
                            boolean var126 = var5.isBlur();
                            if (var126) {
                                float var389 = var121;
                                float var405 = var87;
                                boolean var129 = fillBlurRRect(arg0, var117, var389, var405, 65.0F, 12.0F, var20);
                                if (var129) {
                                    var271 = 1;
                                } else {
                                    var271 = 0;
                                }
                                int var40 = var271;
                                float var390 = var121;
                                float var406 = var87;
                                float var420 = 65.0F;
                                float var437 = 12.0F;
                                int var455 = var124;
                                if (var40 != 0) {
                                    TargetHud var499 = var5;
                                    float var500 = var499.getBlurTint();
                                    var476 = (var500 * var20);
                                } else {
                                    var476 = var20;
                                }
                                int var456 = tintA(var455, var476);
                                ClarityDraw.fillRRect(arg0, var117, var390, var406, var420, var437, var456);
                                float var391 = var121;
                                float var407 = var87;
                                float var421 = 65.0F;
                                float var438 = 12.0F;
                                float var457 = 1.0F;
                                int var477 = ThemeColors.getBorderRgb();
                                int var478 = mix(var477, var122, 0.1F);
                                float var502 = var20;
                                int var479 = tintA(var478, var502);
                                ClarityDraw.strokeRRect(arg0, var117, var391, var407, var421, var438, var457, var479);
                                int var134 = ThemeColors.getSurfaceRgb();
                                boolean var293 = ThemeColors.isLight();
                                if (var293) {
                                    var344 = 0;
                                } else {
                                    var344 = 16777215;
                                }
                                int var135 = mix(var134, var344, 0.12F);
                                float var392 = var121;
                                float var408 = var87;
                                float var422 = 65.0F;
                                float var439 = 12.0F;
                                int var458 = var135;
                                float var480 = 0.66F;
                                float var503 = var20;
                                float var481 = (var480 * var503);
                                int var459 = tintA(var458, var481);
                                ClarityDraw.topHighlight(arg0, var117, var392, var408, var422, var439, var459);
                                float var140 = (var117 + 10.0F);
                                float var296 = (65.0F - 34.0F);
                                float var297 = (var296 * 0.5F);
                                float var142 = (var121 + var297);
                                int var143 = Math.round(var142);
                                float var144 = ((float) var143);
                                if (var18 != 0) {
                                    LivingEntity var393 = var17;
                                    float var409 = var140;
                                    float var423 = var144;
                                    float var440 = 34.0F;
                                    drawPlayerHead(arg0, arg1, var393, var409, var423, var440);
                                } else {
                                    float var403 = var144;
                                    float var419 = 34.0F;
                                    float var436 = 34.0F;
                                    float var453 = 34.0F;
                                    float var473 = 0.22F;
                                    float var454 = (var453 * var473);
                                    int var474 = 1909293;
                                    float var498 = var20;
                                    int var475 = tintA(var474, var498);
                                    ClarityDraw.fillRRect(arg0, var140, var403, var419, var436, var454, var475);
                                }
                                float var394 = var144;
                                float var410 = 34.0F;
                                float var424 = 34.0F;
                                float var441 = 34.0F;
                                float var460 = 0.22F;
                                float var442 = (var441 * var460);
                                float var461 = 1.0F;
                                int var482 = ThemeColors.getBorderRgb();
                                float var504 = var20;
                                int var483 = tintA(var482, var504);
                                ClarityDraw.strokeRRect(arg0, var140, var394, var410, var424, var442, var461, var483);
                                float var151 = (var140 + 34.0F);
                                float var152 = (var151 + 9.0F);
                                float var395 = 13.0F;
                                float var411 = 0.0F;
                                int var425 = var122;
                                float var443 = var20;
                                int var426 = tintA(var425, var443);
                                int var444 = 9;
                                float var462 = var152;
                                float var484 = var121;
                                float var505 = 10.0F;
                                float var485 = (var484 + var505);
                                String var506 = var21;
                                float var156 = ClarityDraw.text(arg0, "inter-sb", var395, var411, var426, var444, var462, var485, var506);
                                int var157 = var26;
                                if (var157 != 0) {
                                    float var396 = 12.0F;
                                    float var412 = 0.0F;
                                    int var427 = ThemeColors.getTextRgb();
                                    float var445 = var20;
                                    int var428 = tintA(var427, var445);
                                    int var446 = 12;
                                    float var463 = var117;
                                    float var486 = var87;
                                    float var464 = (var463 + var486);
                                    float var487 = 10.0F;
                                    float var465 = (var464 - var487);
                                    float var488 = var121;
                                    float var507 = 10.5F;
                                    float var489 = (var488 + var507);
                                    int var508 = var23;
                                    String var509 = (var508) + "m";
                                    float var160 = ClarityDraw.text(arg0, "inter-med", var396, var412, var428, var446, var465, var489, var509);
                                }
                                int var161 = var25;
                                if (var161 != 0) {
                                    float var397 = 11.0F;
                                    float var413 = 0.0F;
                                    int var429 = ThemeColors.getTextRgb();
                                    float var447 = var20;
                                    int var430 = tintA(var429, var447);
                                    int var448 = 9;
                                    float var466 = var152;
                                    float var490 = var121;
                                    float var510 = 29.0F;
                                    float var491 = (var490 + var510);
                                    String var511 = var24;
                                    float var164 = ClarityDraw.text(arg0, "inter-med", var397, var413, var430, var448, var466, var491, var511);
                                }
                                int var165 = var27;
                                if (var165 != 0) {
                                    float var168 = (var121 + 65.0F);
                                    float var169 = (var168 - 15.0F);
                                    float var303 = (var117 + var87);
                                    float var304 = (var303 - 10.0F);
                                    float var305 = (var304 - var152);
                                    float var171 = Math.max(4.0F, var305);
                                    NVGColor var173 = COL;
                                    int var306 = ThemeColors.getBorderRgb();
                                    setARGB(var173, var306, var20);
                                    float var398 = var169;
                                    float var414 = var171;
                                    float var431 = 5.0F;
                                    float var449 = 2.5F;
                                    NVGColor var467 = COL;
                                    ClarityDraw.fillRRect(arg0, var152, var398, var414, var431, var449, var467);
                                    float var372 = var22;
                                    float var308 = (-160.0F * var372);
                                    float var177 = (220.0F + var308);
                                    int var178 = Math.round(var177);
                                    float var373 = var22;
                                    float var310 = (160.0F * var373);
                                    float var180 = (60.0F + var310);
                                    int var181 = Math.round(var180);
                                    NVGColor var182 = COL;
                                    int var312 = (var178 << 16);
                                    int var399 = 8;
                                    int var376 = (var181 << var399);
                                    int var313 = (var312 | var376);
                                    int var314 = (var313 | 60);
                                    setARGB(var182, var314, var20);
                                    float var400 = var169;
                                    float var415 = var171;
                                    float var432 = var22;
                                    float var416 = (var415 * var432);
                                    float var433 = 5.0F;
                                    float var450 = 2.5F;
                                    NVGColor var468 = COL;
                                    ClarityDraw.fillRRect(arg0, var152, var400, var416, var433, var450, var468);
                                    float var401 = var169;
                                    float var417 = var171;
                                    float var434 = 5.0F;
                                    float var451 = 2.5F;
                                    float var469 = 1.0F;
                                    int var492 = ThemeColors.getBorderRgb();
                                    int var493 = mix(var492, var122, 0.1F);
                                    int var494 = tintA(var493, var20);
                                    ClarityDraw.strokeRRect(arg0, var152, var401, var417, var434, var451, var469, var494);
                                    float var402 = var169;
                                    float var418 = var171;
                                    float var435 = 5.0F;
                                    float var452 = 2.5F;
                                    int var470 = ThemeColors.getSurfaceRgb();
                                    boolean var495 = ThemeColors.isLight();
                                    if (var495) {
                                        var501 = 0;
                                    } else {
                                        var501 = 16777215;
                                    }
                                    int var471 = mix(var470, var501, 0.2F);
                                    float var496 = 0.66F;
                                    float var497 = (var496 * var20);
                                    int var472 = tintA(var471, var497);
                                    ClarityDraw.topHighlight(arg0, var152, var402, var418, var435, var452, var472);
                                }
                                return;
                            }
                        }
                    }
                }
            }
        }
    }

    private static int ping(Minecraft arg0) {
        int var10 = 0;
        try {
            ClientPacketListener var5 = arg0.getConnection();
            if (var5 == null) {
            }
        } catch (Throwable ignored) {
        }
        return 0;
        try {
            UUID var16 = arg0.player.getUUID();
            PlayerInfo var8 = var5.getPlayerInfo(var16);
            if (var8 == null) {
                var10 = 0;
            } else {
                var10 = var8.getLatency();
            }
            return var10;
        } catch (Throwable ignored) {
        }
    }

    private static void setARGB(NVGColor arg0, int arg1, float arg2) {
        float var11 = (((float) ((arg1 >> 16) & 255)) / 255.0F);
        float var19 = (((float) ((arg1 >> 8) & 255)) / 255.0F);
        float var25 = (((float) (arg1 & 255)) / 255.0F);
        NVGColor var12 = NanoVG.nvgRGBAf(var11, var19, var25, arg2, arg0);
    }

    static {
        COL = NVGColor.create();
        HashMap var1 = new HashMap();
        HUD_ANIM = var1;
        HashMap var2 = new HashMap();
        HEAD_IMAGE_CACHE = var2;
        CANVAS_W = 1.0F;
        CANVAS_H = 1.0F;
        HashMap var5 = new HashMap();
        KB_ANIM = var5;
        MUSIC_ANIM = 0.0F;
        musicImg = 0;
        musicImgVer = -1;
        blurImg = 0;
        blurImgTex = 0;
        blurImgW = 0;
        blurImgH = 0;
        TGT_ANIM = 0.0F;
        C_NAME = "";
        C_RATIO = 1.0F;
        C_GEAR = "";
    }

}
