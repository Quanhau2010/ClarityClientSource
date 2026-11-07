package need.clarity.modules.utility;

import com.mojang.authlib.GameProfile;
import java.util.UUID;
import need.clarity.EventStartAttack;
import need.clarity.PerAttackEvents;
import need.clarity.modules.ModuleBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.player.RemotePlayer;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;

public class FakePlayer extends ModuleBase {
    private static final int CONST_INT = -1337420;
    private static final UUID fieldVal;
    public static FakePlayer INSTANCE;
    private RemotePlayer fieldVal1;
    private ClientLevel fieldVal2;
    private double doubleValue;
    private double doubleValue1;
    private double doubleValue2;
    private int counterValue;
    private int counterValue1;
    private int counterValue2;
    private double doubleValue3;
    private double doubleValue4;
    private double doubleValue5;
    private double doubleValue6;
    private double doubleValue7;
    private double doubleValue8;

    public FakePlayer() {
        super("Fake Player", "Utility");
        this.counterValue1 = -100;
        this.counterValue2 = -1;
        INSTANCE = this;
        PerAttackEvents.register((p0) -> this.onStartAttack(p0));
    }

    public static boolean isEligible(Entity arg0) {
        int var8 = 0;
        FakePlayer var1 = INSTANCE;
        if (var1 != null) {
            if (var1.fieldVal1 != null) {
                RemotePlayer var9 = var1.fieldVal1;
                if (arg0 == var9) {
                    var8 = 1;
                }
            } else {
            }
        }
        var8 = 0;
        return var8;
    }

    public void onEnable() {
        this.applyStep();
    }

    public void onDisable() {
        this.applyStep1();
    }

    private void applyStep() {
        String var12 = null;
        int var16 = 0;
        Minecraft var1 = Minecraft.getInstance();
        LocalPlayer var20 = var1.player;
        ClientLevel var22 = var1.level;
        if (var20 != null) {
            if (var22 == null) {
            }
        }
        return;
        this.applyStep1();
        var12 = ((String) this.getSetting("Name", "FakePlayer"));
        if (var12 != null) {
            if (var12.isBlank()) {
            }
        }
        var12 = "FakePlayer";
        if (var12.length() > 16) {
            var12 = var12.substring(0, 16);
        }
        UUID var5 = fieldVal;
        String var6 = var12;
        GameProfile var4 = new GameProfile(var5, var6);
        RemotePlayer var37 = new RemotePlayer(var22, var4);
        var37.setId(-1337420);
        double var71 = var20.getX();
        this.doubleValue = var71;
        double var74 = var20.getY();
        this.doubleValue1 = var74;
        double var77 = var20.getZ();
        this.doubleValue2 = var77;
        double var80 = this.doubleValue;
        double var110 = this.doubleValue1;
        double var116 = this.doubleValue2;
        float var121 = var20.getYRot();
        float var122 = var20.getXRot();
        var37.snapTo(var80, var110, var116, var121, var122);
        float var100 = var20.getYRot();
        var37.yHeadRotO = var100;
        var37.yHeadRot = var100;
        float var102 = var20.getYRot();
        var37.yBodyRotO = var102;
        var37.yBodyRot = var102;
        var37.setOldPosAndRot();
        Boolean var104 = true;
        if (((Boolean) this.getSetting("Copy Gear", var104))) {
            EquipmentSlot[] var50 = EquipmentSlot.values();
            int var52 = var50.length;
            var16 = 0;
            while (true) {
                if (var16 >= var52) break;
                int var86 = var16;
                EquipmentSlot var56 = var50[var86];
                try {
                    ItemStack var107 = var20.getItemBySlot(var56).copy();
                    var37.setItemSlot(var56, var107);
                } catch (Throwable ignored) {
                }
                var16 = (var16 + 1);
            }
            float var89 = var37.getMaxHealth();
            var37.setHealth(var89);
            var22.addEntity(var37);
            this.fieldVal1 = var37;
            this.fieldVal2 = var22;
            this.counterValue = 0;
            this.counterValue1 = -100;
            this.counterValue2 = -1;
            this.doubleValue8 = 0.0;
            this.doubleValue7 = 0.0;
            this.doubleValue6 = 0.0;
            this.doubleValue5 = 0.0;
            this.doubleValue4 = 0.0;
            this.doubleValue3 = 0.0;
            return;
        }
    }

    private void applyStep1() {
        Minecraft var1 = Minecraft.getInstance();
        if (this.fieldVal1 != null) {
            if (var1.level != null) {
                ClientLevel var11 = var1.level;
                ClientLevel var17 = this.fieldVal2;
                if (var11 == var17) {
                    try {
                        ClientLevel var13 = var1.level;
                        Entity.RemovalReason var3 = Entity.RemovalReason.DISCARDED;
                        var13.removeEntity(-1337420, var3);
                    } catch (Throwable ignored) {
                    }
                }
                this.fieldVal1 = null;
                this.fieldVal2 = null;
                return;
            }
        }
    }

    public void onTick() {
        double var11 = 0.0;
        double var13 = 0.0;
        double var15 = 0.0;
        int var21 = 0;
        Minecraft var1 = Minecraft.getInstance();
        if (var1.player != null) {
            if (var1.level == null) {
            }
        }
        this.fieldVal1 = null;
        this.fieldVal2 = null;
        return;
        if (this.fieldVal1 != null) {
            ClientLevel var40 = var1.level;
            ClientLevel var225 = this.fieldVal2;
            if (var40 == var225) {
                if (this.fieldVal1.isRemoved()) {
                }
            }
            this.applyStep();
            return;
            int var226 = this.counterValue;
            int var227 = (var226 + 1);
            this.counterValue = var227;
            if (this.counterValue2 >= 0) {
                int var228 = this.counterValue2;
                int var229 = (var228 + 1);
                this.counterValue2 = var229;
                if (var229 > 30) {
                    RemotePlayer var49 = this.fieldVal1;
                    RemotePlayer var232 = this.fieldVal1;
                    float var233 = var232.getMaxHealth();
                    var49.setHealth(var233);
                    this.fieldVal1.deathTime = 0;
                    this.counterValue2 = -1;
                }
                return;
            }
            this.fieldVal1.setOldPosAndRot();
            RemotePlayer var56 = this.fieldVal1;
            RemotePlayer var237 = this.fieldVal1;
            float var238 = var237.yBodyRot;
            var56.yBodyRotO = var238;
            RemotePlayer var58 = this.fieldVal1;
            RemotePlayer var240 = this.fieldVal1;
            float var241 = var240.yHeadRot;
            var58.yHeadRotO = var241;
            var11 = this.doubleValue;
            var13 = this.doubleValue2;
            var15 = this.doubleValue1;
            Float var282 = 1.0F;
            float var71 = ((Float) this.getSetting("Speed", var282));
            double var76 = (((double) this.counterValue) * 0.05);
            double var286 = ((double) var71);
            double var78 = (var76 * var286);
            String var82 = String.valueOf(this.getSetting("Movement", "Still"));
            var21 = -1;
            switch (var82.hashCode()) {
                case -1854418717 -> {}
                case -1808126673 -> {}
                case 2018617584 -> {}
                default -> {}
            }
            boolean var217 = var82.equals("Circle");
            if (var217) {
                var21 = 0;
                boolean var220 = var82.equals("Strafe");
                if (var220) {
                    var21 = 1;
                    if (var82.equals("Random")) {
                        var21 = 2;
                    }
                    switch (var21) {
                        case 0 -> {}
                        case 1 -> {}
                        case 2 -> {}
                        default -> {}
                    }
                    double var200 = var11;
                    double var356 = Math.cos((var78 * 2.2));
                    double var358 = (var356 * 2.5);
                    double var202 = (var200 + var358);
                    var11 = var202;
                    double var204 = var13;
                    double var362 = (var78 * 2.2);
                    double var364 = Math.sin(var362);
                    double var366 = (var364 * 2.5);
                    double var206 = (var204 + var366);
                    var13 = var206;
                    double var208 = var11;
                    double var370 = (var78 * 2.1);
                    double var372 = Math.sin(var370);
                    double var374 = (var372 * 1.6);
                    double var427 = (var78 * 5.3);
                    double var429 = (var427 + 1.7);
                    double var431 = Math.sin(var429);
                    double var433 = (var431 * 0.6);
                    double var376 = (var374 + var433);
                    double var210 = (var208 + var376);
                    var11 = var210;
                    double var212 = var13;
                    double var380 = (var78 * 1.7);
                    double var382 = Math.cos(var380);
                    double var384 = (var382 * 1.6);
                    double var441 = (var78 * 4.1);
                    double var443 = (var441 + 0.4);
                    double var445 = Math.sin(var443);
                    double var447 = (var445 * 0.6);
                    double var386 = (var384 + var447);
                    double var214 = (var212 + var386);
                    var13 = var214;
                    double var90 = var11;
                    double var295 = (Math.sin((var78 * 3.0)) * 2.0);
                    var11 = (var90 + var295);
                    Boolean var298 = false;
                    if (((Boolean) this.getSetting("Jump", var298))) {
                        int var100 = (this.counterValue % 12);
                        double var101 = ((double) var100);
                        double var103 = (var101 / 12.0);
                        double var105 = var15;
                        double var403 = (var103 * 3.141592653589793);
                        double var405 = Math.sin(var403);
                        double var305 = (Math.max(0.0, var405) * 1.1);
                        double var107 = (var105 + var305);
                        var15 = var107;
                    }
                    this.applyStep2();
                    RemotePlayer var111 = this.fieldVal1;
                    double var247 = var11;
                    double var389 = this.doubleValue3;
                    double var249 = (var247 + var389);
                    double var391 = var15;
                    double var449 = this.doubleValue4;
                    double var393 = (var391 + var449);
                    double var451 = var13;
                    double var467 = this.doubleValue5;
                    double var453 = (var451 + var467);
                    var111.setPos(var249, var393, var453);
                    RemotePlayer var113 = this.fieldVal1;
                    double var114 = var113.getX();
                    double var309 = this.fieldVal1.xo;
                    double var116 = (var114 - var309);
                    RemotePlayer var119 = this.fieldVal1;
                    double var120 = var119.getZ();
                    double var313 = this.fieldVal1.zo;
                    double var122 = (var120 - var313);
                    Boolean var316 = true;
                    Object var125 = this.getSetting("Face You", var316);
                    Boolean var126 = ((Boolean) var125);
                    boolean var127 = var126;
                    if (var127) {
                        LocalPlayer var159 = var1.player;
                        double var161 = var159.getX();
                        double var334 = this.fieldVal1.getX();
                        double var163 = (var161 - var334);
                        double var166 = var159.getZ();
                        double var338 = this.fieldVal1.getZ();
                        double var168 = (var166 - var338);
                        double var171 = var159.getEyeY();
                        double var342 = this.fieldVal1.getEyeY();
                        double var173 = (var171 - var342);
                        double var177 = Math.atan2(var168, var163);
                        double var179 = Math.toDegrees(var177);
                        float var181 = ((float) var179);
                        float var182 = (var181 - 90.0F);
                        double var348 = Math.hypot(var163, var168);
                        double var185 = Math.atan2(var173, var348);
                        double var187 = Math.toDegrees(var185);
                        double var189 = -(var187);
                        float var191 = ((float) var189);
                        RemotePlayer var193 = this.fieldVal1;
                        var193.setYRot(var182);
                        RemotePlayer var195 = this.fieldVal1;
                        float var270 = Mth.clamp(var191, -90.0F, 90.0F);
                        var195.setXRot(var270);
                        RemotePlayer var197 = this.fieldVal1;
                        var197.yHeadRot = var182;
                        RemotePlayer var199 = this.fieldVal1;
                        float var275 = Mth.approachDegrees(this.fieldVal1.yBodyRot, var182, 20.0F);
                        var199.yBodyRot = var275;
                    } else {
                        double var130 = (var116 * var116);
                        double var321 = (var122 * var122);
                        double var132 = (var130 + var321);
                        int var134 = Double.compare(var132, 1.0E-5);
                        if (var134 > 0) {
                            double var137 = Math.atan2(var122, var116);
                            double var139 = Math.toDegrees(var137);
                            float var141 = ((float) var139);
                            float var142 = (var141 - 90.0F);
                            RemotePlayer var144 = this.fieldVal1;
                            var144.setYRot(var142);
                            RemotePlayer var146 = this.fieldVal1;
                            var146.yHeadRot = var142;
                            RemotePlayer var148 = this.fieldVal1;
                            float var258 = Mth.approachDegrees(this.fieldVal1.yBodyRot, var142, 25.0F);
                            var148.yBodyRot = var258;
                        }
                        int var150 = this.counterValue;
                        int var151 = (var150 % 40);
                        if (var151 == 0) {
                            RemotePlayer var153 = this.fieldVal1;
                            float var154 = var153.getHealth();
                            float var262 = this.fieldVal1.getMaxHealth();
                            int var155 = Float.compare(var154, var262);
                            if (var155 < 0) {
                                RemotePlayer var157 = this.fieldVal1;
                                float var265 = this.fieldVal1.getMaxHealth();
                                float var331 = (this.fieldVal1.getHealth() + 1.0F);
                                float var266 = Math.min(var265, var331);
                                var157.setHealth(var266);
                            }
                            return;
                        }
                    }
                }
            }
        }
    }

    private void applyStep2() {
        int var52 = 0;
        double var53 = 0.0;
        if (this.doubleValue6 == 0.0) {
            if (this.doubleValue7 == 0.0) {
                if (this.doubleValue8 == 0.0) {
                    if (this.doubleValue4 == 0.0) {
                        return;
                    }
                }
                double var55 = this.doubleValue3;
                double var102 = this.doubleValue6;
                double var57 = (var55 + var102);
                this.doubleValue3 = var57;
                double var59 = this.doubleValue4;
                double var105 = this.doubleValue7;
                double var61 = (var59 + var105);
                this.doubleValue4 = var61;
                double var63 = this.doubleValue5;
                double var108 = this.doubleValue8;
                double var65 = (var63 + var108);
                this.doubleValue5 = var65;
                if (Double.compare(this.doubleValue4, 0.0) <= 0) {
                    var52 = 1;
                } else {
                    var52 = 0;
                }
                int var5 = var52;
                if (var5 != 0) {
                    this.doubleValue4 = 0.0;
                    this.doubleValue7 = 0.0;
                } else {
                    double var72 = ((this.doubleValue7 - 0.08) * 0.98);
                    this.doubleValue7 = var72;
                }
                if (var5 != 0) {
                    var53 = 0.546;
                } else {
                    var53 = 0.91;
                }
                double var6 = var53;
                double var76 = (this.doubleValue6 * var6);
                this.doubleValue6 = var76;
                double var80 = (this.doubleValue8 * var6);
                this.doubleValue8 = var80;
                if (var5 != 0) {
                    if (Double.compare(Math.abs(this.doubleValue6), 0.003) < 0) {
                        if (Double.compare(Math.abs(this.doubleValue8), 0.003) < 0) {
                            this.doubleValue6 = 0.0;
                            this.doubleValue8 = 0.0;
                        }
                        return;
                    }
                }
            }
        }
    }

    private void applyStep(LocalPlayer arg0) {
        double var10 = 0.0;
        double var12 = 0.0;
        double var14 = 0.0;
        double var16 = 0.0;
        Float var139 = 1.0F;
        double var90 = ((double) ((Float) this.getSetting("KB Multiplier", var139)));
        var10 = (0.4 * var90);
        if (arg0.isSprinting()) {
            double var22 = var10;
            Float var154 = 1.0F;
            double var144 = ((double) ((Float) this.getSetting("KB Multiplier", var154)));
            double var94 = (0.5 * var144);
            var10 = (var22 + var94);
        }
        double var27 = arg0.getX();
        double var98 = this.fieldVal1.getX();
        var12 = (var27 - var98);
        double var32 = arg0.getZ();
        double var102 = this.fieldVal1.getZ();
        var14 = (var32 - var102);
        double var36 = var12;
        double var104 = var14;
        var16 = Math.hypot(var36, var104);
        if (Double.compare(var16, 1.0E-4) < 0) {
            var12 = (Math.random() - 0.5);
            var14 = (Math.random() - 0.5);
            double var51 = var12;
            double var112 = var14;
            var16 = Math.hypot(var51, var112);
        }
        double var55 = var12;
        double var114 = var16;
        double var57 = (var55 / var114);
        double var59 = var14;
        double var116 = var16;
        double var61 = (var59 / var116);
        double var72 = (this.doubleValue6 / 2.0);
        double var146 = var10;
        double var124 = (var57 * var146);
        double var74 = (var72 - var124);
        this.doubleValue6 = var74;
        double var79 = (this.doubleValue8 / 2.0);
        double var148 = var10;
        double var130 = (var61 * var148);
        double var81 = (var79 - var130);
        this.doubleValue8 = var81;
        if (Double.compare(this.doubleValue4, 0.0) <= 0) {
            double var135 = (this.doubleValue7 / 2.0);
            double var152 = var10;
            double var137 = (var135 + var152);
            double var85 = Math.min(0.4, var137);
            this.doubleValue7 = var85;
        }
    }

    private void onStartAttack(EventStartAttack arg0) {
        float var14 = 0.0F;
        if (this.isEnabled()) {
            if (this.fieldVal1 != null) {
                Entity var22 = arg0.getTarget();
                RemotePlayer var101 = this.fieldVal1;
                if (var22 == var101) {
                    if (this.counterValue2 >= 0) {
                    }
                }
                return;
                int var26 = this.counterValue;
                int var103 = this.counterValue1;
                if ((var26 - var103) < 10) {
                    return;
                }
                int var106 = this.counterValue;
                this.counterValue1 = var106;
                Boolean var139 = true;
                if (!((Boolean) this.getSetting("Take Damage", var139))) {
                    this.fieldVal1.animateHurt(0.0F);
                    Boolean var141 = false;
                    if (((Boolean) this.getSetting("Take KB", var141))) {
                        LocalPlayer var111 = Minecraft.getInstance().player;
                        this.applyStep(var111);
                    }
                    return;
                }
                Minecraft var40 = Minecraft.getInstance();
                var14 = 1.0F;
                try {
                    LocalPlayer var43 = var40.player;
                    Holder var112 = Attributes.ATTACK_DAMAGE;
                    float var46 = ((float) var43.getAttributeValue(var112));
                    float var144 = var40.player.getAttackStrengthScale(0.5F);
                    float var114 = Math.max(0.2F, var144);
                    var14 = (var46 * var114);
                    if (Double.compare(var40.player.fallDistance, 0.0) > 0) {
                        if (!var40.player.onGround()) {
                            var14 = (var14 * 1.5F);
                        }
                    }
                } catch (Throwable ignored) {
                }
                double var60 = var40.player.getZ();
                double var149 = this.fieldVal1.getZ();
                double var62 = (var60 - var149);
                double var153 = var40.player.getX();
                double var171 = this.fieldVal1.getX();
                double var155 = (var153 - var171);
                float var68 = ((float) Math.toDegrees(Math.atan2(var62, var155)));
                float var118 = this.fieldVal1.getYRot();
                float var69 = (var68 - var118);
                this.fieldVal1.animateHurt(var69);
                Boolean var158 = false;
                if (((Boolean) this.getSetting("Take KB", var158))) {
                    LocalPlayer var122 = var40.player;
                    this.applyStep(var122);
                }
                RemotePlayer var78 = this.fieldVal1;
                this.fieldVal1.hurtDuration = 10;
                var78.hurtTime = 10;
                float var81 = this.fieldVal1.getHealth();
                float var125 = var14;
                float var82 = (var81 - var125);
                Boolean var161 = true;
                boolean var86 = ((Boolean) this.getSetting("Sounds", var161));
                if (Float.compare(var82, 0.0F) <= 0) {
                    this.fieldVal1.setHealth(0.0F);
                    this.counterValue2 = 0;
                    if (var86) {
                        ClientLevel var99 = var40.level;
                        double var137 = this.fieldVal1.getX();
                        double var168 = this.fieldVal1.getY();
                        double var178 = this.fieldVal1.getZ();
                        SoundEvent var180 = SoundEvents.PLAYER_DEATH;
                        SoundSource var181 = SoundSource.PLAYERS;
                        var99.playLocalSound(var137, var168, var178, var180, var181, 1.0F, 1.0F, false);
                    }
                } else {
                    this.fieldVal1.setHealth(var82);
                    if (var86) {
                        ClientLevel var93 = var40.level;
                        double var131 = this.fieldVal1.getX();
                        double var164 = this.fieldVal1.getY();
                        double var174 = this.fieldVal1.getZ();
                        SoundEvent var8 = SoundEvents.PLAYER_HURT;
                        SoundSource var9 = SoundSource.PLAYERS;
                        var93.playLocalSound(var131, var164, var174, var8, var9, 1.0F, 1.0F, false);
                    }
                    return;
                }
            }
        }
    }

    static {
        fieldVal = UUID.fromString("c1a7f00d-0000-4000-8000-00000000fa4e");
    }

}
