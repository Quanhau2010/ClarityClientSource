package need.clarity.modules.visual;

import com.mojang.blaze3d.platform.NativeImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;
import need.clarity.Clarity;
import need.clarity.config.ClarityConfig;
import need.clarity.modules.ModuleBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.core.ClientAsset;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.PlayerModelType;
import net.minecraft.world.entity.player.PlayerSkin;

public class SkinChanger extends ModuleBase {
    public static volatile SkinChanger INSTANCE;
    private static final Map entriesMap;
    private static final Map entriesMap1;
    private static final Set entriesSet;
    private static final Map entriesMap2;
    private volatile Identifier fieldVal;
    private volatile String labelText;
    private int counterValue;
    private int counterValue1;

    public SkinChanger() {
        super("Skin Changer", "Visual");
        this.labelText = "";
        this.counterValue = 0;
        this.counterValue1 = 0;
        INSTANCE = this;
    }

    public void onEnable() {
        this.labelText = "";
        this.counterValue1 = 0;
    }

    public void onDisable() {
        this.fieldVal = null;
        this.labelText = "";
    }

    public void onTick() {
        int var15 = 0;
        int var34 = (this.counterValue + 1);
        this.counterValue = var34;
        String var7 = getNone();
        boolean var9 = var7.startsWith("custom_");
        if (var9) {
            if (this.fieldVal == null) {
                String var37 = this.labelText;
                if (var7.equals(var37)) {
                    var15 = 1;
                }
            } else {
            }
        }
        var15 = 0;
        int var5 = var15;
        String var39 = this.labelText;
        if (var7.equals(var39)) {
            if (var5 != 0) {
                int var20 = this.counterValue;
                int var41 = this.counterValue1;
                if (var20 >= var41) {
                }
            }
        }
        this.labelText = var7;
        this.fieldVal = null;
        if (var9) {
            try {
                this.applyStep1(var7);
            } catch (Throwable ignored) {
            }
            if (this.fieldVal == null) {
                int var47 = (this.counterValue + 40);
                this.counterValue1 = var47;
            }
            String var29 = getSetting();
            if (!var29.isEmpty()) {
                applyStep(var29);
            }
            return;
        }
    }

    public static PlayerSkin evaluate(PlayerSkin arg0) {
        SkinChanger var1 = INSTANCE;
        if (var1 != null) {
            if (var1.isEnabled()) {
                if (arg0 == null) {
                }
            }
            return null;
            String var17 = getSetting();
            if (!var17.isEmpty()) {
                PlayerSkin var21 = var1.evaluate(var17);
                if (var21 != null) {
                    return var21;
                }
            }
            String var24 = getNone();
            if ("default".equals(var24)) {
                return DefaultPlayerSkin.getDefaultSkin();
            }
            Identifier var29 = var1.fieldVal;
            if (var29 == null) {
                return null;
            }
            ClientAsset.ResourceTexture var32 = new ClientAsset.ResourceTexture(var29, var29);
            ClientAsset.Texture var38 = arg0.cape();
            ClientAsset.Texture var39 = arg0.elytra();
            PlayerModelType var41 = arg0.model();
            PlayerModelType var40 = var1.evaluate(var41);
            boolean var43 = arg0.secure();
            PlayerSkin var33 = new PlayerSkin(var32, var38, var39, var40, var43);
            return var33;
        }
    }

    private PlayerSkin evaluate(String arg0) {
        Minecraft var2 = Minecraft.getInstance();
        if (var2 != null) {
            if (var2.getConnection() != null) {
                PlayerInfo var13 = var2.getConnection().getPlayerInfo(arg0);
                if (var13 != null) {
                    if (var2.player != null) {
                        UUID var19 = var13.getProfile().id();
                        UUID var30 = var2.player.getUUID();
                        if (!var19.equals(var30)) {
                            PlayerSkin var22 = var13.getSkin();
                            if (var22 != null) {
                                return var22;
                            }
                        }
                    }
                }
            }
        }
        Map var25 = entriesMap1;
        Locale var4 = Locale.ROOT;
        String var32 = arg0.toLowerCase(var4);
        return ((PlayerSkin) var25.get(var32));
    }

    private static void applyStep(String arg0) {
        Locale var2 = Locale.ROOT;
        String var8 = arg0.toLowerCase(var2);
        if (!entriesMap1.containsKey(var8)) {
            if (!entriesSet.add(var8)) {
            }
        }
        return;
        Long var15 = ((Long) entriesMap2.get(var8));
        if (var15 != null) {
            long var17 = System.currentTimeMillis();
            long var32 = var15;
            if (Long.compare((var17 - var32), 30000L) < 0) {
                boolean var23 = entriesSet.remove(var8);
                return;
            }
        }
        Runnable var37 = () -> SkinChanger.lambda$a(arg0, var8);
        Thread var24 = new Thread(var37, "clarity-skin-fetch");
        var24.setDaemon(true);
        var24.start();
    }

    private void applyStep1(String arg0) {
        NativeImage var11 = null;
        String var25 = null;
        Identifier var15 = ((Identifier) entriesMap.get(arg0));
        if (var15 != null) {
            this.fieldVal = var15;
            return;
        }
        String var19 = ClarityConfig.getCustomSkinUrl(arg0);
        if (var19 != null) {
            if (var19.isEmpty()) {
            }
        }
        return;
        if (var19.contains(",")) {
            int var68 = (var19.indexOf(44) + 1);
            var25 = var19.substring(var68);
        } else {
            var25 = var19;
        }
        String var8 = var25;
        Base64.Decoder var26 = Base64.getDecoder();
        String var58 = var8.trim();
        byte[] var27 = var26.decode(var58);
        try {
            ByteArrayInputStream var28 = new ByteArrayInputStream(var27);
            try {
                var11 = NativeImage.read(var28);
                if (var11.getHeight() == 32) {
                    if (var11.getWidth() == 64) {
                        var11 = evaluate(var11);
                    }
                    Locale var61 = Locale.ROOT;
                    Identifier var40 = Clarity.id("skins/" + (arg0.toLowerCase(var61)));
                    Supplier var70 = () -> SkinChanger.lambda$e(arg0);
                    NativeImage var5 = var11;
                    DynamicTexture var41 = new DynamicTexture(var70, var5);
                    Minecraft.getInstance().getTextureManager().register(var40, var41);
                    Object var45 = entriesMap.put(arg0, var40);
                    this.fieldVal = var40;
                }
            } catch (Throwable ignored) {
            }
            var28.close();
            try {
                var28.close();
            } catch (Throwable ignored) {
            }
            var49.addSuppressed(var52);
            throw var49;
        } catch (IOException ignored) {
        }
    }

    private static NativeImage evaluate(NativeImage arg0) {
        int var8 = 0;
        int var9 = 0;
        NativeImage var1 = new NativeImage(64, 64, true);
        var8 = 0;
        while (true) {
            if (var8 < 32) {
                var9 = 0;
                while (true) {
                    if (var9 >= 64) break;
                    int var43 = var9;
                    int var56 = var8;
                    int var83 = var9;
                    int var95 = var8;
                    int var70 = arg0.getPixel(var83, var95);
                    var1.setPixel(var43, var56, var70);
                    var9 = (var9 + 1);
                }
                applyStep(var1, 4, 16, 20, 48, 4, 4);
                applyStep(var1, 8, 16, 24, 48, 4, 4);
                applyStep(var1, 0, 20, 24, 52, 4, 12);
                applyStep(var1, 4, 20, 20, 52, 4, 12);
                applyStep(var1, 8, 20, 16, 52, 4, 12);
                applyStep(var1, 12, 20, 28, 52, 4, 12);
                applyStep(var1, 44, 16, 36, 48, 4, 4);
                applyStep(var1, 48, 16, 40, 48, 4, 4);
                applyStep(var1, 40, 20, 40, 52, 4, 12);
                applyStep(var1, 44, 20, 36, 52, 4, 12);
                applyStep(var1, 48, 20, 32, 52, 4, 12);
                applyStep(var1, 52, 20, 44, 52, 4, 12);
                arg0.close();
                return var1;
                var8 = (var8 + 1);
            }
        }
    }

    private static void applyStep(NativeImage arg0, int arg1, int arg2, int arg3, int arg4, int arg5, int arg6) {
        int var14 = 0;
        int var15 = 0;
        var14 = 0;
        while (true) {
            if (var14 < arg6) {
                var15 = 0;
                while (true) {
                    if (var15 >= arg5) break;
                    int var23 = ((arg3 + arg5) - 1);
                    int var26 = var15;
                    int var24 = (var23 - var26);
                    int var10 = var14;
                    int var28 = (arg4 + var10);
                    int var12 = var15;
                    int var31 = (arg1 + var12);
                    int var13 = var14;
                    int var33 = (arg2 + var13);
                    int var30 = arg0.getPixel(var31, var33);
                    arg0.setPixel(var24, var28, var30);
                    var15 = (var15 + 1);
                }
                return;
                var14 = (var14 + 1);
            }
        }
    }

    private PlayerModelType evaluate(PlayerModelType arg0) {
        String var7 = ((String) this.getSetting("Model", "Auto"));
        if ("Slim".equals(var7)) {
            return PlayerModelType.SLIM;
        }
        if ("Wide".equals(var7)) {
            return PlayerModelType.WIDE;
        }
        return arg0;
    }

    public static boolean isEligible() {
        int var3 = 0;
        if (INSTANCE != null) {
            if (INSTANCE.isEnabled()) {
                var3 = 1;
            }
        } else {
            var3 = 0;
        }
        return var3;
    }

    public Identifier evaluate() {
        return this.fieldVal;
    }

    public static String getNone() {
        String var8 = null;
        if (INSTANCE == null) {
            return "none";
        }
        Object var5 = INSTANCE.getSetting("Skin", "none");
        if (var5 != null) {
            var8 = var5.toString();
        } else {
            var8 = "none";
        }
        return var8;
    }

    public static String getSetting() {
        String var9 = null;
        if (INSTANCE == null) {
            return "";
        }
        String var7 = ((String) INSTANCE.getSetting("Copy Player", ""));
        if (var7 == null) {
            var9 = "";
        } else {
            var9 = var7.trim();
        }
        return var9;
    }

    public static void applyStep2(String arg0) {
        Object var3 = entriesMap.remove(arg0);
    }

    static {
        ConcurrentHashMap var0 = new ConcurrentHashMap();
        entriesMap = var0;
        ConcurrentHashMap var1 = new ConcurrentHashMap();
        entriesMap1 = var1;
        entriesSet = ConcurrentHashMap.newKeySet();
        ConcurrentHashMap var3 = new ConcurrentHashMap();
        entriesMap2 = var3;
    }

}
