package need.clarity.modules.visual;

import com.mojang.blaze3d.vertex.PoseStack;
import need.clarity.WorldRenderEvent;
import need.clarity.modules.ModuleBase;
import need.clarity.modules.combat.AimAssist;
import need.clarity.modules.visual.kenny.KennyCharacterRenderer;
import need.clarity.modules.visual.kenny.KennyModelParts;
import need.clarity.render.KennyPipeline;
import need.clarity.util.ClarityRenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Pose;
import org.joml.Matrix4f;

public class PlayerModel extends ModuleBase {
    public static PlayerModel INSTANCE;
    private final WorldRenderEvent.Listener fieldVal;
    private boolean enabledState;
    private long timestampMs;
    private double doubleValue;
    private double doubleValue1;
    private final double[][] vertexMatrix;
    private final double[] coordsVector;
    private final double[] coordsVector1;
    private static volatile LivingEntityRenderState fieldVal1;
    private static final double[] coordsVector2;
    private static final double[] coordsVector3;
    private static volatile boolean enabledState1;
    private long timestampMs1;
    private long timestampMs2;
    private long timestampMs3;
    private long timestampMs4;
    private long timestampMs5;

    public PlayerModel() {
        super("Player Model", "Visual");
        this.fieldVal = (p0, p1) -> this.renderWorldEffect(p0, p1);
        this.enabledState = false;
        this.timestampMs = 0L;
        this.doubleValue = 0.0;
        this.doubleValue1 = 0.0;
        this.vertexMatrix = new double[6][12];
        this.coordsVector = new double[12];
        this.coordsVector1 = new double[12];
        INSTANCE = this;
        if (this.isEnabled()) {
            this.applyStep();
        }
    }

    public static void markModelState(LivingEntityRenderState arg0) {
        fieldVal1 = arg0;
    }

    public static void forget(LivingEntityRenderState arg0) {
        if (fieldVal1 == arg0) {
            fieldVal1 = null;
        }
    }

    public static boolean isModelState(LivingEntityRenderState arg0) {
        if (arg0 != null) {
            if (arg0 == fieldVal1) {
                return (isActive());
            }
        }
    }

    public static double[] handPose(boolean arg0) {
        if (enabledState1) {
            if (!isActive()) {
            }
        }
        return null;
        if (arg0) {
        } else {
        }
        return coordsVector3;
    }

    public static boolean isActive() {
        if (INSTANCE != null) {
            return (INSTANCE.isEnabled());
        }
    }

    public void onEnable() {
        this.applyStep();
    }

    public void onDisable() {
        this.applyStep1();
        enabledState1 = false;
    }

    private void applyStep() {
        if (this.enabledState) {
            return;
        }
        this.enabledState = true;
        WorldRenderEvent.register(this.fieldVal);
    }

    private void applyStep1() {
        if (!this.enabledState) {
            return;
        }
        this.enabledState = false;
        WorldRenderEvent.unregister(this.fieldVal);
    }

    private void renderWorldEffect(PoseStack arg0, float arg1) {
        if (!this.isEnabled()) {
            return;
        }
        if (!((Boolean) this.getSetting("Show Timing", false))) {
            this.renderWorldEffect1(arg0, arg1);
            return;
        }
        long var3 = System.nanoTime();
        this.renderWorldEffect1(arg0, arg1);
        long var5 = System.nanoTime();
        this.timestampMs1 = (this.timestampMs1 + (var5 - var3));
        if (this.timestampMs3 != 0L) {
            this.timestampMs4 = (this.timestampMs4 + (var5 - this.timestampMs3));
        }
        this.timestampMs3 = var5;
        this.timestampMs2 = (this.timestampMs2 + 1L);
        if ((var5 - this.timestampMs5) > 1000000000L) {
            if (this.timestampMs2 > 1L) {
                Minecraft var7 = Minecraft.getInstance();
                if (var7.player != null) {
                    new Object[3][0] = ((((double) this.timestampMs1) / 1000000.0) / ((double) this.timestampMs2));
                    new Object[3][1] = ((((double) this.timestampMs4) / 1000000.0) / ((double) (this.timestampMs2 - 1L)));
                    new Object[3][2] = ((1.0E9 * ((double) (this.timestampMs2 - 1L))) / ((double) Math.max(1L, this.timestampMs4)));
                    var7.player.sendOverlayMessage(Component.literal(String.format("Kenny: %.3f ms/frame  |  whole frame: %.1f ms (%.0f fps)", new Object[3])));
                }
                this.timestampMs2 = 0L;
                this.timestampMs4 = this;
                this.timestampMs1 = null;
                this.timestampMs5 = var5;
            }
            return;
        }
    }

    private void renderWorldEffect1(PoseStack arg0, float arg1) {
        int var29 = 0;
        Minecraft var3 = Minecraft.getInstance();
        if (var3 != null) {
            if (var3.level != null) {
                if (var3.player == null) {
                }
            }
            return;
            if (var3.options.getCameraType().isFirstPerson()) {
                if (!Freecam.isEligible()) {
                    return;
                }
                LocalPlayer var4 = var3.player;
                double var5 = ((double) ((Float) this.getSetting("Scale", 1.0F)));
                boolean var7 = ((Boolean) this.getSetting("Through Walls", false));
                long var8 = System.nanoTime();
                if (this.timestampMs == 0L) {
                } else {
                }
                double var10 = Math.min(0.1, (((double) (var8 - this.timestampMs)) / 1.0E9));
                this.timestampMs = var8;
                double var12 = Math.hypot((var4.getX() - var4.xOld), (var4.getZ() - var4.zOld));
                if (var4.onGround()) {
                } else {
                }
                double var14 = 0.25;
                this.doubleValue1 = (this.doubleValue1 + ((var14 - this.doubleValue1) * Math.min(1.0, (var10 * 12.0))));
                this.doubleValue = (this.doubleValue + (((var10 * var12) * 20.0) * 3.6));
                if (this.doubleValue > 1000000.0) {
                    this.doubleValue = (this.doubleValue - 1000000.0);
                }
                double var16 = Mth.lerp(((double) arg1), var4.xOld, var4.getX());
                double var18 = Mth.lerp(((double) arg1), var4.yOld, var4.getY());
                double var20 = Mth.lerp(((double) arg1), var4.zOld, var4.getZ());
                float var22 = Mth.rotLerp(arg1, var4.yBodyRotO, var4.yBodyRot);
                float var23 = Mth.rotLerp(arg1, var4.yHeadRotO, var4.yHeadRot);
                if (ServerRotations.isEligible()) {
                    if (AimAssist.isActivelyTracking()) {
                    } else {
                    }
                    float var24 = Mth.lerp(arg1, var4.xRotO, var4.getXRot());
                    double var25 = Math.toRadians(((double) Mth.clamp(Mth.wrapDegrees((var23 - var22)), -85.0F, 85.0F)));
                    double var27 = Math.toRadians(((double) Mth.clamp(var24, -89.0F, 89.0F)));
                    KennyModelParts.applyStep(this.doubleValue, this.doubleValue1, ((double) var4.getAttackAnim(arg1)), (var4.getPose() == Pose.CROUCHING), var25, var27, this.vertexMatrix);
                    KennyModelParts.applyStep(this.vertexMatrix[2], coordsVector2);
                    KennyModelParts.applyStep(this.vertexMatrix[3], coordsVector3);
                    var29 = 0;
                    while (true) {
                        if (var29 >= 3) break;
                        Math.min(1.0, (var12 / 0.2))[ServerRotations.computeFloatMetric(arg1)] = (coordsVector2[var29] * var5);
                        arr[0.016] = (coordsVector3[var29] * var5);
                        var29++;
                    }
                    enabledState1 = true;
                    var29 = ClarityRenderUtils.getCameraPos();
                    double var30 = Math.toRadians(((double) var22));
                    double var32 = -(Math.sin(var30));
                    double var34 = Math.cos(var30);
                    double[] var36 = this.coordsVector;
                    var36[0] = (-(var34) * var5);
                    var36[1] = 0.0;
                    var36[2] = (var32 * var5);
                    var36[3] = (var16 - var29.x);
                    var36[4] = 0.0;
                    var36[5] = var5;
                    var36[6] = 0.0;
                    var36[7] = (var18 - var29.y);
                    var36[8] = (var32 * var5);
                    var36[9] = 0.0;
                    var36[10] = (var34 * var5);
                    var36[11] = (var20 - var29.z);
                    Matrix4f var37 = arg0.last().pose();
                    double[] var38 = this.coordsVector1;
                    var38[0] = ((double) var37.m00());
                    var38[1] = ((double) var37.m10());
                    var38[2] = ((double) var37.m20());
                    var38[3] = ((double) var37.m30());
                    var38[4] = ((double) var37.m01());
                    var38[5] = ((double) var37.m11());
                    var38[6] = ((double) var37.m21());
                    var38[7] = ((double) var37.m31());
                    var38[8] = ((double) var37.m02());
                    var38[9] = ((double) var37.m12());
                    var38[10] = ((double) var37.m22());
                    var38[11] = ((double) var37.m32());
                    if (!var7) {
                        if (Chams.INSTANCE != null) {
                            if (Chams.INSTANCE.isEnabled()) {
                                if (Chams.isThroughWalls()) {
                                    if (!Chams.isHideSelf()) {
                                    }
                                }
                            }
                        }
                    }
                    if (0 != 0) {
                    } else {
                    }
                    RenderType var40 = KennyPipeline.occluded();
                    float var41 = RenderAnimations.computeFloatMetric();
                    MultiBufferSource.BufferSource var42 = var3.renderBuffers().bufferSource();
                    KennyCharacterRenderer.applyStep(var42.getBuffer(var40), var36, var38, this.vertexMatrix, var41);
                    var42.endBatch(var40);
                    return;
                }
            }
        }
    }

    static {
        coordsVector2 = new double[12];
        coordsVector3 = new double[12];
        enabledState1 = false;
    }

}
