package need.clarity.util;

import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.ColorTargetState;
import com.mojang.blaze3d.pipeline.DepthStencilState;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.platform.CompareOp;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import need.clarity.api.Modules;
import need.clarity.api.RenderApi;
import need.clarity.modules.visual.cosmetics.CosmeticSink;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.rendertype.LayeringTransform;
import net.minecraft.client.renderer.rendertype.OutputTarget;
import net.minecraft.client.renderer.rendertype.RenderSetup;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

public final class ClarityRenderUtils {
    private static final RenderPipeline.Snippet FOGLESS_LINES_SNIPPET;
    public static final RenderPipeline ESP_FILLED_PIPE;
    public static final RenderPipeline ESP_LINES_PIPE;
    public static final RenderType ESP_QUADS;
    public static final RenderType ESP_OUTLINE;
    private static final DepthStencilState ESP_OCCLUDED_DEPTH;
    public static final RenderPipeline ESP_FILLED_OCCLUDED_PIPE;
    public static final RenderPipeline ESP_LINES_OCCLUDED_PIPE;
    public static final RenderType ESP_QUADS_OCCLUDED;
    public static final RenderType ESP_OUTLINE_OCCLUDED;
    public static final int WING_OCCLUDED = 0;
    public static final int WING_THROUGH_WALLS = 1;
    public static final int WING_CHAMS_LAYERED = 2;
    public static final int COSMETIC_OCCLUDED = 0;
    public static final int COSMETIC_THROUGH_WALLS = 1;
    public static final int COSMETIC_CHAMS_LAYERED = 2;
    private static final RenderPipeline WINGS_OCCLUDED_PIPE;
    private static final RenderPipeline WINGS_WALLS_PIPE;
    private static final RenderType WINGS_OCCLUDED;
    private static final RenderType WINGS_WALLS;
    private static RenderPipeline wingsChamsPipe;
    private static RenderType wingsChams;
    private static final RenderType ORDERED_OCCLUDED;
    private static final RenderType ORDERED_WALLS;
    private static RenderType orderedChams;
    public static final RenderPipeline CHAMS_UV_PIPE;
    public static final RenderType CHAMS_UV;
    public static final RenderPipeline TRACER_LINES_PIPE;
    public static final RenderType TRACER_LINES;
    private static final double TRACER_MIN_DIST = 2.0;

    private static RenderType makeWingType(String arg0, RenderPipeline arg1) {
        String var5 = "clarity:" + (arg0);
        RenderSetup.RenderSetupBuilder var7 = RenderSetup.builder(arg1);
        LayeringTransform var4 = LayeringTransform.VIEW_OFFSET_Z_LAYERING;
        RenderSetup.RenderSetupBuilder var8 = var7.setLayeringTransform(var4);
        OutputTarget var12 = OutputTarget.ITEM_ENTITY_TARGET;
        RenderSetup var11 = var8.setOutputTarget(var12).sortOnUpload().createRenderSetup();
        return RenderType.create(var5, var11);
    }

    private static RenderPipeline wingChamsPipe() {
        if (wingsChamsPipe == null) {
            DepthStencilState var5 = RenderApi.chamsThroughWallsDepthState();
            RenderPipeline.Snippet[] var7 = new RenderPipeline.Snippet[1];
            RenderPipeline.Snippet var2 = RenderPipelines.DEBUG_FILLED_SNIPPET;
            var7[0] = var2;
            RenderPipeline.Builder var8 = RenderPipeline.builder(var7);
            Identifier var21 = Identifier.parse("clarity:pipeline/wings_chams");
            RenderPipeline.Builder var10 = var8.withLocation(var21).withCull(false);
            if (var5 != null) {
                RenderPipeline.Builder var13 = var10.withDepthStencilState(var5);
            } else {
                Optional var24 = Optional.empty();
                RenderPipeline.Builder var19 = var10.withDepthStencilState(var24);
            }
            wingsChamsPipe = RenderPipelines.register(var10.build());
        }
        return wingsChamsPipe;
    }

    private static RenderType wingChamsType() {
        if (wingsChams == null) {
            RenderPipeline var1 = wingChamsPipe();
            wingsChams = makeWingType("wings_chams", var1);
        }
        return wingsChams;
    }

    private static RenderType makeOrderedType(String arg0, RenderPipeline arg1) {
        String var5 = "clarity:" + (arg0);
        RenderSetup.RenderSetupBuilder var7 = RenderSetup.builder(arg1);
        LayeringTransform var4 = LayeringTransform.VIEW_OFFSET_Z_LAYERING;
        RenderSetup.RenderSetupBuilder var8 = var7.setLayeringTransform(var4);
        OutputTarget var11 = OutputTarget.ITEM_ENTITY_TARGET;
        RenderSetup var10 = var8.setOutputTarget(var11).createRenderSetup();
        return RenderType.create(var5, var10);
    }

    private static RenderType orderedType(int arg0) {
        if (arg0 == 1) {
            return ORDERED_WALLS;
        }
        if (arg0 == 2) {
            if (orderedChams == null) {
                RenderPipeline var11 = wingChamsPipe();
                orderedChams = makeOrderedType("ordered_chams", var11);
            }
            return orderedChams;
        }
        return ORDERED_OCCLUDED;
    }

    public static void drawWingQuad(PoseStack arg0, Vec3 arg1, Vec3 arg2, Vec3 arg3, Vec3 arg4, int arg5, int arg6) {
        RenderType var26 = null;
        Vec3 var18 = getCameraPos().reverse();
        MultiBufferSource.BufferSource var19 = getVCP();
        if (arg6 == 2) {
            var26 = wingChamsType();
        } else {
            if (arg6 == 1) {
                var26 = WINGS_WALLS;
            } else {
                var26 = WINGS_OCCLUDED;
            }
            RenderType var16 = var26;
            VertexConsumer var23 = var19.getBuffer(var16);
            Vec3 var31 = arg1.add(var18);
            Vec3 var33 = arg2.add(var18);
            Vec3 var35 = arg3.add(var18);
            Vec3 var37 = arg4.add(var18);
            int var39 = anim(arg5);
            drawQuad(arg0, var23, var31, var33, var35, var37, var39);
            var19.endBatch(var16);
            return;
        }
    }

    public static ClarityRenderUtils.OrderedBatch beginOrdered(PoseStack arg0, int arg1) {
        ClarityRenderUtils.OrderedBatch var2 = new ClarityRenderUtils.OrderedBatch(arg0, arg1);
        return var2;
    }

    public static ClarityRenderUtils.CosmeticBatch beginCosmetics(PoseStack arg0, int arg1) {
        ClarityRenderUtils.CosmeticBatch var2 = new ClarityRenderUtils.CosmeticBatch(arg0, arg1);
        return var2;
    }

    public static ClarityRenderUtils.CosmeticBatch beginWings(PoseStack arg0, int arg1) {
        return beginCosmetics(arg0, arg1);
    }

    public static void drawEspMaskUV(PoseStack arg0, List arg1) {
        if (arg1.isEmpty()) {
            return;
        }
        Vec3 var15 = getCameraPos().reverse();
        MultiBufferSource.BufferSource var16 = getVCP();
        RenderType var3 = CHAMS_UV;
        VertexConsumer var18 = var16.getBuffer(var3);
        Iterator var20 = arg1.iterator();
        while (true) {
            if (!var20.hasNext()) break;
            AABB var30 = ((ClarityRenderUtils.ESPEntry) var20.next()).box();
            double var32 = var15.x;
            double var34 = var15.y;
            double var36 = var15.z;
            AABB var31 = var30.move(var32, var34, var36);
            drawUVBox(arg0, var18, var31);
        }
        RenderType var28 = CHAMS_UV;
        var16.endBatch(var28);
    }

    private static void drawUVBox(PoseStack arg0, VertexConsumer arg1, AABB arg2) {
        PoseStack.Pose var24 = arg0.last();
        float var28 = ((float) arg2.minX);
        float var32 = ((float) arg2.minY);
        float var36 = ((float) arg2.minZ);
        float var40 = ((float) arg2.maxX);
        float var44 = ((float) arg2.maxY);
        float var48 = ((float) arg2.maxZ);
        uvQuad(arg1, var24, var28, var32, var36, var40, var32, var36, var40, var32, var48, var28, var32, var48);
        uvQuad(arg1, var24, var28, var44, var36, var28, var44, var48, var40, var44, var48, var40, var44, var36);
        uvQuad(arg1, var24, var28, var32, var36, var28, var44, var36, var40, var44, var36, var40, var32, var36);
        uvQuad(arg1, var24, var28, var32, var48, var40, var32, var48, var40, var44, var48, var28, var44, var48);
        uvQuad(arg1, var24, var28, var32, var36, var28, var32, var48, var28, var44, var48, var28, var44, var36);
        uvQuad(arg1, var24, var40, var32, var36, var40, var44, var36, var40, var44, var48, var40, var32, var48);
    }

    private static void uvQuad(VertexConsumer arg0, PoseStack.Pose arg1, float arg2, float arg3, float arg4, float arg5, float arg6, float arg7, float arg8, float arg9, float arg10, float arg11, float arg12, float arg13) {
        VertexConsumer var19 = arg0.addVertex(arg1, arg2, arg3, arg4);
        int var31 = uv(0, 0);
        VertexConsumer var20 = var19.setColor(var31);
        VertexConsumer var22 = arg0.addVertex(arg1, arg5, arg6, arg7);
        int var34 = uv(1, 0);
        VertexConsumer var23 = var22.setColor(var34);
        VertexConsumer var25 = arg0.addVertex(arg1, arg8, arg9, arg10);
        int var37 = uv(1, 1);
        VertexConsumer var26 = var25.setColor(var37);
        VertexConsumer var28 = arg0.addVertex(arg1, arg11, arg12, arg13);
        int var40 = uv(0, 1);
        VertexConsumer var29 = var28.setColor(var40);
    }

    private static int uv(int arg0, int arg1) {
        int var8 = ((arg0 & 255) << 16);
        int var5 = (-16777216 | var8);
        int var11 = ((arg1 & 255) << 8);
        return (var5 | var11);
    }

    public static ClarityRenderUtils.TexBatch beginTextured(PoseStack arg0, Identifier arg1) {
        return beginTextured(arg0, arg1, false);
    }

    public static ClarityRenderUtils.TexBatch beginTextured(PoseStack arg0, Identifier arg1, boolean arg2) {
        RenderType var8 = null;
        var8 = RenderTypes.entityTranslucentCullItemTarget(arg1);
        if (arg2) {
            var8 = RenderApi.throughWallsConvert(var8);
        }
        MultiBufferSource.BufferSource var4 = getVCP();
        RenderType var5 = var8;
        Vec3 var7 = getCameraPos();
        ClarityRenderUtils.TexBatch var13 = new ClarityRenderUtils.TexBatch(var4, var5, arg0, var7);
        return var13;
    }

    private ClarityRenderUtils() {
    }

    private static int anim(int arg0) {
        int var5 = 0;
        float var1 = Modules.renderAnimationsFactor();
        if (Float.compare(var1, 0.999F) >= 0) {
            return arg0;
        }
        var5 = Math.round((((float) ((arg0 >>> 24) & 255)) * var1));
        if (var5 < 0) {
            var5 = 0;
        } else {
            if (var5 > 255) {
                var5 = 255;
            }
            int var19 = (var5 << 24);
            int var28 = (arg0 & 16777215);
            return (var19 | var28);
        }
    }

    public static Vec3 getCameraPos() {
        Vec3 var6 = null;
        Camera var3 = Minecraft.getInstance().gameRenderer.getMainCamera();
        if (var3 == null) {
            var6 = Vec3.ZERO;
        } else {
            var6 = var3.position();
        }
        return var6;
    }

    private static MultiBufferSource.BufferSource getVCP() {
        return Minecraft.getInstance().renderBuffers().bufferSource();
    }

    public static void drawBoxes(PoseStack arg0, List arg1, int arg2, int arg3, boolean arg4, boolean arg5) {
        if (arg1.isEmpty()) {
            return;
        }
        Vec3 var19 = getCameraPos().reverse();
        MultiBufferSource.BufferSource var20 = getVCP();
        if (arg4) {
            RenderType var7 = ESP_QUADS;
            VertexConsumer var23 = var20.getBuffer(var7);
            Iterator var25 = arg1.iterator();
            while (true) {
                if (!var25.hasNext()) break;
                AABB var43 = ((AABB) var25.next());
                double var57 = var19.x;
                double var63 = var19.y;
                double var68 = var19.z;
                AABB var52 = var43.move(var57, var63, var68);
                drawFilledBox(arg0, var23, var52, arg2);
            }
            RenderType var45 = ESP_QUADS;
            var20.endBatch(var45);
        }
        if (arg5) {
            RenderType var46 = ESP_OUTLINE;
            VertexConsumer var31 = var20.getBuffer(var46);
            Iterator var33 = arg1.iterator();
            while (true) {
                if (!var33.hasNext()) break;
                AABB var38 = ((AABB) var33.next());
                double var53 = var19.x;
                double var60 = var19.y;
                double var65 = var19.z;
                AABB var50 = var38.move(var53, var60, var65);
                drawOutlineBox(arg0, var31, var50, arg3);
            }
            RenderType var48 = ESP_OUTLINE;
            var20.endBatch(var48);
        }
    }

    public static void drawBoxes(PoseStack arg0, List arg1, int arg2, int arg3) {
        drawBoxes(arg0, arg1, arg2, arg3, true, true);
    }

    public static void drawEspMask(PoseStack arg0, List arg1, boolean arg2) {
        RenderType var34 = null;
        if (arg1.isEmpty()) {
            return;
        }
        Vec3 var17 = getCameraPos().reverse();
        MultiBufferSource.BufferSource var18 = getVCP();
        if (arg2) {
            var34 = ESP_QUADS;
        } else {
            var34 = ESP_QUADS_OCCLUDED;
        }
        RenderType var10 = var34;
        VertexConsumer var21 = var18.getBuffer(var10);
        Iterator var23 = arg1.iterator();
        while (true) {
            if (!var23.hasNext()) break;
            ClarityRenderUtils.ESPEntry var28 = ((ClarityRenderUtils.ESPEntry) var23.next());
            AABB var30 = var28.box();
            double var36 = var17.x;
            double var40 = var17.y;
            double var44 = var17.z;
            AABB var31 = var30.move(var36, var40, var44);
            int var43 = var28.argb();
            drawFilledBox(arg0, var21, var31, var43);
        }
        var18.endBatch(var10);
    }

    public static void drawEspBoxes(PoseStack arg0, List arg1, boolean arg2, int arg3, boolean arg4, boolean arg5, boolean arg6, float arg7) {
        RenderType var65 = null;
        RenderType var66 = null;
        if (arg1.isEmpty()) {
            return;
        }
        Vec3 var24 = getCameraPos().reverse();
        MultiBufferSource.BufferSource var25 = getVCP();
        if (arg2) {
            if (arg5) {
                var66 = ESP_QUADS;
            } else {
                var66 = ESP_QUADS_OCCLUDED;
            }
            RenderType var16 = var66;
            VertexConsumer var29 = var25.getBuffer(var16);
            int var31 = anim(arg3);
            Iterator var33 = arg1.iterator();
            while (true) {
                if (!var33.hasNext()) break;
                AABB var40 = ((ClarityRenderUtils.ESPEntry) var33.next()).box();
                double var68 = var24.x;
                double var81 = var24.y;
                double var90 = var24.z;
                AABB var41 = var40.move(var68, var81, var90);
                drawFilledBox(arg0, var29, var41, var31);
            }
            var25.endBatch(var16);
        }
        if (arg4) {
            if (arg5) {
                var65 = ESP_OUTLINE;
            } else {
                var65 = ESP_OUTLINE_OCCLUDED;
            }
            RenderType var95 = var65;
            VertexConsumer var47 = var25.getBuffer(var95);
            Iterator var49 = arg1.iterator();
            while (true) {
                if (!var49.hasNext()) break;
                ClarityRenderUtils.ESPEntry var55 = ((ClarityRenderUtils.ESPEntry) var49.next());
                AABB var57 = var55.box();
                double var75 = var24.x;
                double var85 = var24.y;
                double var93 = var24.z;
                AABB var58 = var57.move(var75, var85, var93);
                int var61 = anim(var55.argb());
                if (arg6) {
                    drawCornerBox(arg0, var47, var58, var61, arg7);
                }
                drawOutlineBox(arg0, var47, var58, var61, arg7);
            }
            var25.endBatch(var95);
        }
    }

    public static void drawFilledQuad(PoseStack arg0, Vec3 arg1, Vec3 arg2, Vec3 arg3, Vec3 arg4, int arg5, boolean arg6) {
        RenderType var25 = null;
        Vec3 var18 = getCameraPos().reverse();
        MultiBufferSource.BufferSource var19 = getVCP();
        if (arg6) {
            var25 = ESP_QUADS;
        } else {
            var25 = ESP_QUADS_OCCLUDED;
        }
        RenderType var16 = var25;
        VertexConsumer var22 = var19.getBuffer(var16);
        Vec3 var28 = arg1.add(var18);
        Vec3 var30 = arg2.add(var18);
        Vec3 var32 = arg3.add(var18);
        Vec3 var34 = arg4.add(var18);
        int var36 = anim(arg5);
        drawQuad(arg0, var22, var28, var30, var32, var34, var36);
        var19.endBatch(var16);
    }

    private static void drawQuad(PoseStack arg0, VertexConsumer arg1, Vec3 arg2, Vec3 arg3, Vec3 arg4, Vec3 arg5, int arg6) {
        PoseStack.Pose var13 = arg0.last();
        float var35 = ((float) arg2.x);
        float var50 = ((float) arg2.y);
        float var65 = ((float) arg2.z);
        VertexConsumer var16 = arg1.addVertex(var13, var35, var50, var65).setColor(arg6);
        float var39 = ((float) arg3.x);
        float var54 = ((float) arg3.y);
        float var69 = ((float) arg3.z);
        VertexConsumer var19 = arg1.addVertex(var13, var39, var54, var69).setColor(arg6);
        float var43 = ((float) arg4.x);
        float var58 = ((float) arg4.y);
        float var73 = ((float) arg4.z);
        VertexConsumer var22 = arg1.addVertex(var13, var43, var58, var73).setColor(arg6);
        float var47 = ((float) arg5.x);
        float var62 = ((float) arg5.y);
        float var77 = ((float) arg5.z);
        VertexConsumer var25 = arg1.addVertex(var13, var47, var62, var77).setColor(arg6);
    }

    public static void drawGroundRing(PoseStack arg0, Vec3 arg1, double arg2, int arg3, boolean arg4, int arg5) {
        int var25 = 0;
        Minecraft var7 = Minecraft.getInstance();
        if (var7 != null) {
            if (var7.level == null) {
            }
        }
        return;
        var25 = 0;
        while (true) {
            if (var25 >= arg5) break;
            double var84 = ((double) arg5);
            double var47 = (6.283185307179586 / var84);
            double var87 = ((double) var25);
            double var49 = (var47 * var87);
            double var52 = arg1.x;
            double var93 = (Math.cos(var49) * arg2);
            double var54 = (var52 + var93);
            double var57 = arg1.z;
            double var99 = (Math.sin(var49) * arg2);
            double var59 = (var57 + var99);
            int var65 = ((int) Math.floor(var54));
            int var70 = ((int) Math.floor(var59));
            ClientLevel var72 = var7.level;
            Heightmap.Types var81 = Heightmap.Types.MOTION_BLOCKING;
            double var77 = (((double) var72.getHeight(var81, var65, var70)) + 0.02);
            double var107 = (((double) var65) + 0.5);
            double var138 = (((double) var70) + 0.5);
            Vec3 var79 = new Vec3(var107, var77, var138);
            double var111 = -(0.6);
            double var142 = -(0.6);
            Vec3 var83 = var79.add(var111, 0.0, var142);
            double var147 = -(0.6);
            Vec3 var114 = var79.add(0.6, 0.0, var147);
            Vec3 var118 = var79.add(0.6, 0.0, 0.6);
            double var133 = -(0.6);
            Vec3 var130 = var79.add(var133, 0.0, 0.6);
            drawFilledQuad(arg0, var83, var114, var118, var130, arg3, arg4);
            var25 = (var25 + 1);
        }
    }

    public static void drawFlatRing(PoseStack arg0, Vec3 arg1, double arg2, int arg3, double arg4, int arg5) {
        int var31 = 0;
        var31 = 0;
        while (true) {
            if (var31 >= arg5) break;
            double var57 = ((double) arg5);
            double var41 = (6.283185307179586 / var57);
            double var60 = ((double) var31);
            double var43 = (var41 * var60);
            double var46 = arg1.x;
            double var66 = (Math.cos(var43) * arg2);
            double var48 = (var46 + var66);
            double var51 = arg1.z;
            double var72 = (Math.sin(var43) * arg2);
            double var53 = (var51 + var72);
            double var75 = (var48 - arg4);
            double var86 = arg1.y;
            double var101 = (var53 - arg4);
            Vec3 var56 = new Vec3(var75, var86, var101);
            double var82 = (var48 + arg4);
            double var94 = arg1.y;
            double var108 = (var53 - arg4);
            Vec3 var74 = new Vec3(var82, var94, var108);
            double var90 = (var48 + arg4);
            double var106 = arg1.y;
            double var117 = (var53 + arg4);
            Vec3 var77 = new Vec3(var90, var106, var117);
            double var98 = (var48 - arg4);
            double var113 = arg1.y;
            double var121 = (var53 + arg4);
            Vec3 var84 = new Vec3(var98, var113, var121);
            drawFilledQuad(arg0, var56, var74, var77, var84, arg3, false);
            var31 = (var31 + 1);
        }
    }

    private static Vec3 clampTracerEnd(Vec3 arg0) {
        double var12 = arg0.length();
        if (Double.compare(var12, 2.0) < 0) {
            if (Double.compare(var12, 1.0E-6) < 0) {
                Vec3 var21 = new Vec3(0.0, 0.0, -2.0);
                return var21;
            }
            double var26 = (2.0 / var12);
            return arg0.scale(var26);
        }
        return arg0;
    }

    public static void drawTracer(PoseStack arg0, float arg1, Vec3 arg2, int arg3) {
        drawTracer(arg0, arg1, arg2, arg3, 2.0F);
    }

    public static void drawTracer(PoseStack arg0, float arg1, Vec3 arg2, int arg3, float arg4) {
        Camera var20 = Minecraft.getInstance().gameRenderer.getMainCamera();
        if (var20 == null) {
            return;
        }
        Vec3 var23 = getCameraPos().reverse();
        Vec3 var26 = clampTracerEnd(arg2.add(var23));
        double var46 = Math.sin(-(Math.toRadians(((double) var20.yRot()))));
        double var56 = Math.cos(Math.toRadians(((double) var20.xRot())));
        double var48 = (var46 * var56);
        double var66 = -(Math.sin(Math.toRadians(((double) var20.xRot()))));
        double var76 = Math.cos(-(Math.toRadians(((double) var20.yRot()))));
        double var85 = Math.cos(Math.toRadians(((double) var20.xRot())));
        double var78 = (var76 * var85);
        Vec3 var27 = new Vec3(var48, var66, var78);
        Vec3 var28 = var27.scale(10.0);
        MultiBufferSource.BufferSource var29 = getVCP();
        RenderType var36 = ESP_OUTLINE;
        VertexConsumer var31 = var29.getBuffer(var36);
        drawLine(arg0, var31, var28, var26, arg3, arg4);
        RenderType var38 = ESP_OUTLINE;
        var29.endBatch(var38);
    }

    public static void drawWorldLine(PoseStack arg0, Vec3 arg1, Vec3 arg2, int arg3, float arg4) {
        drawWorldLine(arg0, arg1, arg2, arg3, arg4, true);
    }

    public static void drawWorldLine(PoseStack arg0, Vec3 arg1, Vec3 arg2, int arg3, float arg4, boolean arg5) {
        RenderType var19 = null;
        Vec3 var16 = getCameraPos().reverse();
        MultiBufferSource.BufferSource var17 = getVCP();
        if (arg5) {
            var19 = ESP_OUTLINE;
        } else {
            var19 = ESP_OUTLINE_OCCLUDED;
        }
        RenderType var14 = var19;
        VertexConsumer var21 = var17.getBuffer(var14);
        Vec3 var26 = arg1.add(var16);
        Vec3 var28 = arg2.add(var16);
        int var30 = anim(arg3);
        drawLine(arg0, var21, var26, var28, var30, arg4);
        var17.endBatch(var14);
    }

    public static void drawTracers(PoseStack arg0, float arg1, List arg2, int arg3) {
        drawTracers(arg0, arg1, arg2, arg3, 2.0F);
    }

    public static void drawTracers(PoseStack arg0, float arg1, List arg2, int arg3, float arg4) {
        if (arg2.isEmpty()) {
            return;
        }
        Camera var23 = Minecraft.getInstance().gameRenderer.getMainCamera();
        if (var23 == null) {
            return;
        }
        Vec3 var26 = getCameraPos().reverse();
        double var57 = Math.sin(-(Math.toRadians(((double) var23.yRot()))));
        double var67 = Math.cos(Math.toRadians(((double) var23.xRot())));
        double var59 = (var57 * var67);
        double var77 = -(Math.sin(Math.toRadians(((double) var23.xRot()))));
        double var88 = Math.cos(-(Math.toRadians(((double) var23.yRot()))));
        double var97 = Math.cos(Math.toRadians(((double) var23.xRot())));
        double var90 = (var88 * var97);
        Vec3 var27 = new Vec3(var59, var77, var90);
        Vec3 var28 = var27.scale(10.0);
        MultiBufferSource.BufferSource var29 = getVCP();
        RenderType var46 = ESP_OUTLINE;
        VertexConsumer var31 = var29.getBuffer(var46);
        Iterator var33 = arg2.iterator();
        while (true) {
            if (!var33.hasNext()) break;
            Vec3 var42 = clampTracerEnd(((Vec3) var33.next()).add(var26));
            int var80 = anim(arg3);
            drawLine(arg0, var31, var28, var42, var80, arg4);
        }
        RenderType var47 = ESP_OUTLINE;
        var29.endBatch(var47);
    }

    private static void drawFilledBox(PoseStack arg0, VertexConsumer arg1, AABB arg2, int arg3) {
        PoseStack.Pose var16 = arg0.last();
        float var20 = ((float) arg2.minX);
        float var24 = ((float) arg2.minY);
        float var28 = ((float) arg2.minZ);
        float var32 = ((float) arg2.maxX);
        float var36 = ((float) arg2.maxY);
        float var40 = ((float) arg2.maxZ);
        VertexConsumer var43 = arg1.addVertex(var16, var20, var24, var28).setColor(arg3);
        VertexConsumer var46 = arg1.addVertex(var16, var32, var24, var28).setColor(arg3);
        VertexConsumer var49 = arg1.addVertex(var16, var32, var24, var40).setColor(arg3);
        VertexConsumer var52 = arg1.addVertex(var16, var20, var24, var40).setColor(arg3);
        VertexConsumer var55 = arg1.addVertex(var16, var20, var36, var28).setColor(arg3);
        VertexConsumer var58 = arg1.addVertex(var16, var20, var36, var40).setColor(arg3);
        VertexConsumer var61 = arg1.addVertex(var16, var32, var36, var40).setColor(arg3);
        VertexConsumer var64 = arg1.addVertex(var16, var32, var36, var28).setColor(arg3);
        VertexConsumer var67 = arg1.addVertex(var16, var20, var24, var28).setColor(arg3);
        VertexConsumer var70 = arg1.addVertex(var16, var20, var36, var28).setColor(arg3);
        VertexConsumer var73 = arg1.addVertex(var16, var32, var36, var28).setColor(arg3);
        VertexConsumer var76 = arg1.addVertex(var16, var32, var24, var28).setColor(arg3);
        VertexConsumer var79 = arg1.addVertex(var16, var20, var24, var40).setColor(arg3);
        VertexConsumer var82 = arg1.addVertex(var16, var32, var24, var40).setColor(arg3);
        VertexConsumer var85 = arg1.addVertex(var16, var32, var36, var40).setColor(arg3);
        VertexConsumer var88 = arg1.addVertex(var16, var20, var36, var40).setColor(arg3);
        VertexConsumer var91 = arg1.addVertex(var16, var20, var24, var28).setColor(arg3);
        VertexConsumer var94 = arg1.addVertex(var16, var20, var24, var40).setColor(arg3);
        VertexConsumer var97 = arg1.addVertex(var16, var20, var36, var40).setColor(arg3);
        VertexConsumer var100 = arg1.addVertex(var16, var20, var36, var28).setColor(arg3);
        VertexConsumer var103 = arg1.addVertex(var16, var32, var24, var28).setColor(arg3);
        VertexConsumer var106 = arg1.addVertex(var16, var32, var36, var28).setColor(arg3);
        VertexConsumer var109 = arg1.addVertex(var16, var32, var36, var40).setColor(arg3);
        VertexConsumer var112 = arg1.addVertex(var16, var32, var24, var40).setColor(arg3);
    }

    private static void drawOutlineBox(PoseStack arg0, VertexConsumer arg1, AABB arg2, int arg3) {
        drawOutlineBox(arg0, arg1, arg2, arg3, 2.0F);
    }

    private static void drawOutlineBox(PoseStack arg0, VertexConsumer arg1, AABB arg2, int arg3, float arg4) {
        PoseStack.Pose var25 = arg0.last();
        float var29 = ((float) arg2.minX);
        float var33 = ((float) arg2.minY);
        float var37 = ((float) arg2.minZ);
        float var41 = ((float) arg2.maxX);
        float var45 = ((float) arg2.maxY);
        float var49 = ((float) arg2.maxZ);
        line(arg1, var25, var29, var33, var37, var41, var33, var37, 1.0F, 0.0F, 0.0F, arg3, arg4);
        line(arg1, var25, var41, var33, var37, var41, var33, var49, 0.0F, 0.0F, 1.0F, arg3, arg4);
        line(arg1, var25, var41, var33, var49, var29, var33, var49, -1.0F, 0.0F, 0.0F, arg3, arg4);
        line(arg1, var25, var29, var33, var49, var29, var33, var37, 0.0F, 0.0F, -1.0F, arg3, arg4);
        line(arg1, var25, var29, var45, var37, var41, var45, var37, 1.0F, 0.0F, 0.0F, arg3, arg4);
        line(arg1, var25, var41, var45, var37, var41, var45, var49, 0.0F, 0.0F, 1.0F, arg3, arg4);
        line(arg1, var25, var41, var45, var49, var29, var45, var49, -1.0F, 0.0F, 0.0F, arg3, arg4);
        line(arg1, var25, var29, var45, var49, var29, var45, var37, 0.0F, 0.0F, -1.0F, arg3, arg4);
        line(arg1, var25, var29, var33, var37, var29, var45, var37, 0.0F, 1.0F, 0.0F, arg3, arg4);
        line(arg1, var25, var41, var33, var37, var41, var45, var37, 0.0F, 1.0F, 0.0F, arg3, arg4);
        line(arg1, var25, var41, var33, var49, var41, var45, var49, 0.0F, 1.0F, 0.0F, arg3, arg4);
        line(arg1, var25, var29, var33, var49, var29, var45, var49, 0.0F, 1.0F, 0.0F, arg3, arg4);
    }

    private static void drawCornerBox(PoseStack arg0, VertexConsumer arg1, AABB arg2, int arg3, float arg4) {
        float var78 = 0.0F;
        float var83 = 0.0F;
        float var85 = 0.0F;
        float var89 = 0.0F;
        float var90 = 0.0F;
        float var91 = 0.0F;
        int var29 = 0;
        int var30 = 0;
        int var31 = 0;
        PoseStack.Pose var38 = arg0.last();
        float var42 = ((float) arg2.minX);
        float var46 = ((float) arg2.minY);
        float var50 = ((float) arg2.minZ);
        float var54 = ((float) arg2.maxX);
        float var58 = ((float) arg2.maxY);
        float var62 = ((float) arg2.maxZ);
        float var64 = (var54 - var42);
        float var66 = (var58 - var46);
        float var68 = (var62 - var50);
        float var97 = (Math.min(Math.min(var64, var66), var68) * 0.25F);
        float var70 = Math.max(0.1F, var97);
        var29 = 0;
        while (true) {
            if (var29 < 2) {
                var30 = 0;
                while (true) {
                    if (var30 >= 2) break;
                    var31 = 0;
                    while (true) {
                        if (var31 >= 2) break;
                        if (var29 == 0) {
                            var78 = var42;
                        } else {
                            var78 = var54;
                        }
                        float var32 = var78;
                        if (var30 == 0) {
                            var90 = var46;
                        } else {
                            var90 = var58;
                        }
                        float var33 = var90;
                        if (var31 == 0) {
                            var89 = var50;
                        } else {
                            var89 = var62;
                        }
                        float var34 = var89;
                        if (var29 == 0) {
                            var91 = var70;
                        } else {
                            var91 = var70;
                        }
                        float var35 = var91;
                        if (var30 == 0) {
                            var83 = var70;
                        } else {
                            var83 = var70;
                        }
                        float var36 = var83;
                        if (var31 == 0) {
                            var85 = var70;
                        } else {
                            var85 = var70;
                        }
                        float var37 = var85;
                        float var113 = (var32 + var35);
                        line(arg1, var38, var32, var33, var34, var113, var33, var34, var35, 0.0F, 0.0F, arg3, arg4);
                        float var118 = (var33 + var36);
                        line(arg1, var38, var32, var33, var34, var32, var118, var34, 0.0F, var36, 0.0F, arg3, arg4);
                        float var123 = (var34 + var37);
                        line(arg1, var38, var32, var33, var34, var32, var33, var123, 0.0F, 0.0F, var37, arg3, arg4);
                        var31 = (var31 + 1);
                    }
                    return;
                    var30 = (var30 + 1);
                }
                var29 = (var29 + 1);
            }
        }
    }

    private static void drawLine(PoseStack arg0, VertexConsumer arg1, Vec3 arg2, Vec3 arg3, int arg4) {
        drawLine(arg0, arg1, arg2, arg3, arg4, 2.0F);
    }

    private static void drawLine(PoseStack arg0, VertexConsumer arg1, Vec3 arg2, Vec3 arg3, int arg4, float arg5) {
        PoseStack.Pose var19 = arg0.last();
        float var23 = ((float) arg2.x);
        float var27 = ((float) arg2.y);
        float var31 = ((float) arg2.z);
        float var35 = ((float) arg3.x);
        float var39 = ((float) arg3.y);
        float var43 = ((float) arg3.z);
        float var64 = (var35 - var23);
        float var72 = (var39 - var27);
        float var80 = (var43 - var31);
        Vector3f var44 = new Vector3f(var64, var72, var80);
        Vector3f var45 = var44.normalize();
        VertexConsumer var48 = arg1.addVertex(var19, var23, var27, var31).setColor(arg4);
        float var67 = var45.x;
        float var75 = var45.y;
        float var83 = var45.z;
        VertexConsumer var50 = var48.setNormal(var19, var67, var75, var83).setLineWidth(arg5);
        VertexConsumer var53 = arg1.addVertex(var19, var35, var39, var43).setColor(arg4);
        float var70 = var45.x;
        float var78 = var45.y;
        float var86 = var45.z;
        VertexConsumer var55 = var53.setNormal(var19, var70, var78, var86).setLineWidth(arg5);
    }

    private static void line(VertexConsumer arg0, PoseStack.Pose arg1, float arg2, float arg3, float arg4, float arg5, float arg6, float arg7, float arg8, float arg9, float arg10, int arg11) {
        line(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10, arg11, 2.0F);
    }

    private static void line(VertexConsumer arg0, PoseStack.Pose arg1, float arg2, float arg3, float arg4, float arg5, float arg6, float arg7, float arg8, float arg9, float arg10, int arg11, float arg12) {
        VertexConsumer var21 = arg0.addVertex(arg1, arg2, arg3, arg4).setColor(arg11).setNormal(arg1, arg8, arg9, arg10).setLineWidth(arg12);
        VertexConsumer var26 = arg0.addVertex(arg1, arg5, arg6, arg7).setColor(arg11).setNormal(arg1, arg8, arg9, arg10).setLineWidth(arg12);
    }

    static {
        RenderPipeline.Snippet[] var4 = new RenderPipeline.Snippet[2];
        RenderPipeline.Snippet var2 = RenderPipelines.MATRICES_FOG_SNIPPET;
        var4[0] = var2;
        RenderPipeline.Snippet var190 = RenderPipelines.GLOBALS_SNIPPET;
        var4[1] = var190;
        RenderPipeline.Builder var5 = RenderPipeline.builder(var4);
        Identifier var95 = Identifier.parse("clarity:core/fogless_lines");
        RenderPipeline.Builder var6 = var5.withVertexShader(var95);
        Identifier var97 = Identifier.parse("clarity:core/fogless_lines");
        RenderPipeline.Builder var7 = var6.withFragmentShader(var97);
        BlendFunction var191 = BlendFunction.TRANSLUCENT;
        ColorTargetState var98 = new ColorTargetState(var191);
        RenderPipeline.Builder var9 = var7.withColorTargetState(var98).withCull(false);
        VertexFormat var100 = DefaultVertexFormat.POSITION_COLOR_NORMAL_LINE_WIDTH;
        VertexFormat.Mode var168 = VertexFormat.Mode.LINES;
        FOGLESS_LINES_SNIPPET = var9.withVertexFormat(var100, var168).buildSnippet();
        RenderPipeline.Snippet[] var13 = new RenderPipeline.Snippet[1];
        RenderPipeline.Snippet var192 = RenderPipelines.DEBUG_FILLED_SNIPPET;
        var13[0] = var192;
        RenderPipeline.Builder var14 = RenderPipeline.builder(var13);
        Identifier var102 = Identifier.parse("clarity:pipeline/esp_filled");
        RenderPipeline.Builder var15 = var14.withLocation(var102);
        Optional var103 = Optional.empty();
        ESP_FILLED_PIPE = RenderPipelines.register(var15.withDepthStencilState(var103).withCull(true).build());
        RenderPipeline.Snippet[] var21 = new RenderPipeline.Snippet[1];
        RenderPipeline.Snippet var193 = FOGLESS_LINES_SNIPPET;
        var21[0] = var193;
        RenderPipeline.Builder var22 = RenderPipeline.builder(var21);
        Identifier var106 = Identifier.parse("clarity:pipeline/esp_lines");
        ESP_LINES_PIPE = RenderPipelines.register(var22.withLocation(var106).build());
        RenderSetup.RenderSetupBuilder var108 = RenderSetup.builder(ESP_FILLED_PIPE);
        LayeringTransform var171 = LayeringTransform.VIEW_OFFSET_Z_LAYERING;
        RenderSetup.RenderSetupBuilder var109 = var108.setLayeringTransform(var171);
        OutputTarget var172 = OutputTarget.ITEM_ENTITY_TARGET;
        RenderSetup var112 = var109.setOutputTarget(var172).sortOnUpload().createRenderSetup();
        ESP_QUADS = RenderType.create("clarity:esp_quads", var112);
        RenderSetup.RenderSetupBuilder var114 = RenderSetup.builder(ESP_LINES_PIPE);
        LayeringTransform var173 = LayeringTransform.VIEW_OFFSET_Z_LAYERING;
        RenderSetup.RenderSetupBuilder var115 = var114.setLayeringTransform(var173);
        OutputTarget var174 = OutputTarget.ITEM_ENTITY_TARGET;
        RenderSetup var117 = var115.setOutputTarget(var174).createRenderSetup();
        ESP_OUTLINE = RenderType.create("clarity:esp_outline", var117);
        CompareOp var175 = CompareOp.LESS_THAN_OR_EQUAL;
        DepthStencilState var30 = new DepthStencilState(var175, false);
        ESP_OCCLUDED_DEPTH = var30;
        RenderPipeline.Snippet[] var32 = new RenderPipeline.Snippet[1];
        RenderPipeline.Snippet var195 = RenderPipelines.DEBUG_FILLED_SNIPPET;
        var32[0] = var195;
        RenderPipeline.Builder var33 = RenderPipeline.builder(var32);
        Identifier var119 = Identifier.parse("clarity:pipeline/esp_filled_occluded");
        RenderPipeline.Builder var34 = var33.withLocation(var119);
        DepthStencilState var120 = ESP_OCCLUDED_DEPTH;
        ESP_FILLED_OCCLUDED_PIPE = RenderPipelines.register(var34.withDepthStencilState(var120).withCull(true).build());
        RenderPipeline.Snippet[] var40 = new RenderPipeline.Snippet[1];
        RenderPipeline.Snippet var196 = FOGLESS_LINES_SNIPPET;
        var40[0] = var196;
        RenderPipeline.Builder var41 = RenderPipeline.builder(var40);
        Identifier var123 = Identifier.parse("clarity:pipeline/esp_lines_occluded");
        RenderPipeline.Builder var42 = var41.withLocation(var123);
        DepthStencilState var124 = ESP_OCCLUDED_DEPTH;
        ESP_LINES_OCCLUDED_PIPE = RenderPipelines.register(var42.withDepthStencilState(var124).build());
        RenderSetup.RenderSetupBuilder var126 = RenderSetup.builder(ESP_FILLED_OCCLUDED_PIPE);
        LayeringTransform var178 = LayeringTransform.VIEW_OFFSET_Z_LAYERING;
        RenderSetup.RenderSetupBuilder var127 = var126.setLayeringTransform(var178);
        OutputTarget var179 = OutputTarget.ITEM_ENTITY_TARGET;
        RenderSetup var130 = var127.setOutputTarget(var179).sortOnUpload().createRenderSetup();
        ESP_QUADS_OCCLUDED = RenderType.create("clarity:esp_quads_occluded", var130);
        RenderSetup.RenderSetupBuilder var132 = RenderSetup.builder(ESP_LINES_OCCLUDED_PIPE);
        LayeringTransform var180 = LayeringTransform.VIEW_OFFSET_Z_LAYERING;
        RenderSetup.RenderSetupBuilder var133 = var132.setLayeringTransform(var180);
        OutputTarget var181 = OutputTarget.ITEM_ENTITY_TARGET;
        RenderSetup var135 = var133.setOutputTarget(var181).createRenderSetup();
        ESP_OUTLINE_OCCLUDED = RenderType.create("clarity:esp_outline_occluded", var135);
        RenderPipeline.Snippet[] var51 = new RenderPipeline.Snippet[1];
        RenderPipeline.Snippet var197 = RenderPipelines.DEBUG_FILLED_SNIPPET;
        var51[0] = var197;
        RenderPipeline.Builder var52 = RenderPipeline.builder(var51);
        Identifier var137 = Identifier.parse("clarity:pipeline/wings_occluded");
        RenderPipeline.Builder var53 = var52.withLocation(var137);
        DepthStencilState var138 = ESP_OCCLUDED_DEPTH;
        WINGS_OCCLUDED_PIPE = RenderPipelines.register(var53.withDepthStencilState(var138).withCull(false).build());
        RenderPipeline.Snippet[] var59 = new RenderPipeline.Snippet[1];
        RenderPipeline.Snippet var198 = RenderPipelines.DEBUG_FILLED_SNIPPET;
        var59[0] = var198;
        RenderPipeline.Builder var60 = RenderPipeline.builder(var59);
        Identifier var141 = Identifier.parse("clarity:pipeline/wings_walls");
        RenderPipeline.Builder var61 = var60.withLocation(var141);
        Optional var142 = Optional.empty();
        WINGS_WALLS_PIPE = RenderPipelines.register(var61.withDepthStencilState(var142).withCull(false).build());
        RenderPipeline var144 = WINGS_OCCLUDED_PIPE;
        WINGS_OCCLUDED = makeWingType("wings_occluded", var144);
        RenderPipeline var145 = WINGS_WALLS_PIPE;
        WINGS_WALLS = makeWingType("wings_walls", var145);
        RenderPipeline var146 = WINGS_OCCLUDED_PIPE;
        ORDERED_OCCLUDED = makeOrderedType("ordered_occluded", var146);
        RenderPipeline var147 = WINGS_WALLS_PIPE;
        ORDERED_WALLS = makeOrderedType("ordered_walls", var147);
        RenderPipeline.Snippet[] var75 = new RenderPipeline.Snippet[1];
        RenderPipeline.Snippet var199 = RenderPipelines.DEBUG_FILLED_SNIPPET;
        var75[0] = var199;
        RenderPipeline.Builder var76 = RenderPipeline.builder(var75);
        Identifier var149 = Identifier.parse("clarity:pipeline/chams_uv");
        RenderPipeline.Builder var77 = var76.withLocation(var149);
        Optional var200 = Optional.empty();
        ColorTargetState var150 = new ColorTargetState(var200, 15);
        RenderPipeline.Builder var78 = var77.withColorTargetState(var150);
        CompareOp var201 = CompareOp.ALWAYS_PASS;
        DepthStencilState var151 = new DepthStencilState(var201, false);
        CHAMS_UV_PIPE = RenderPipelines.register(var78.withDepthStencilState(var151).withCull(false).build());
        RenderSetup.RenderSetupBuilder var154 = RenderSetup.builder(CHAMS_UV_PIPE);
        LayeringTransform var185 = LayeringTransform.VIEW_OFFSET_Z_LAYERING;
        RenderSetup.RenderSetupBuilder var155 = var154.setLayeringTransform(var185);
        OutputTarget var186 = OutputTarget.ITEM_ENTITY_TARGET;
        RenderSetup var158 = var155.setOutputTarget(var186).sortOnUpload().createRenderSetup();
        CHAMS_UV = RenderType.create("clarity:chams_uv", var158);
        RenderPipeline.Snippet[] var86 = new RenderPipeline.Snippet[1];
        RenderPipeline.Snippet var202 = FOGLESS_LINES_SNIPPET;
        var86[0] = var202;
        RenderPipeline.Builder var87 = RenderPipeline.builder(var86);
        Identifier var160 = Identifier.parse("clarity:pipeline/tracer_lines");
        RenderPipeline.Builder var88 = var87.withLocation(var160);
        DepthStencilState var161 = DepthStencilState.DEFAULT;
        TRACER_LINES_PIPE = RenderPipelines.register(var88.withDepthStencilState(var161).build());
        RenderSetup.RenderSetupBuilder var163 = RenderSetup.builder(TRACER_LINES_PIPE);
        LayeringTransform var188 = LayeringTransform.VIEW_OFFSET_Z_LAYERING;
        RenderSetup.RenderSetupBuilder var164 = var163.setLayeringTransform(var188);
        OutputTarget var189 = OutputTarget.ITEM_ENTITY_TARGET;
        RenderSetup var166 = var164.setOutputTarget(var189).createRenderSetup();
        TRACER_LINES = RenderType.create("clarity:tracer_lines", var166);
    }

    public final class CosmeticBatch implements CosmeticSink {
        private final MultiBufferSource.BufferSource vcp;
        private final RenderType type;
        private final VertexConsumer vc;
        private final PoseStack.Pose pose;
        private final Vec3 cam;

        private CosmeticBatch(PoseStack arg0, int arg1) {
            Vec3 var4 = ClarityRenderUtils.getCameraPos();
            this.cam = var4;
            MultiBufferSource.BufferSource var11 = ClarityRenderUtils.getVCP();
            this.vcp = var11;
            if (arg1 == 2) {
                RenderType var19 = ClarityRenderUtils.wingChamsType();
            } else {
                if (arg1 == 1) {
                    var19 = ClarityRenderUtils.WINGS_WALLS;
                } else {
                    var19 = ClarityRenderUtils.WINGS_OCCLUDED;
                }
                this.type = var19;
                MultiBufferSource.BufferSource var15 = this.vcp;
                RenderType var22 = this.type;
                VertexConsumer var16 = var15.getBuffer(var22);
                this.vc = var16;
                PoseStack.Pose var18 = arg0.last();
                this.pose = var18;
                return;
            }
        }

        private void put(double[] arg0, int arg1) {
            VertexConsumer var10 = this.vc;
            PoseStack.Pose var13 = this.pose;
            double var16 = arg0[0];
            double var28 = this.cam.x;
            float var20 = ((float) (var16 - var28));
            double var22 = arg0[1];
            double var38 = this.cam.y;
            float var26 = ((float) (var22 - var38));
            double var32 = arg0[2];
            double var42 = this.cam.z;
            float var36 = ((float) (var32 - var42));
            VertexConsumer var11 = var10.addVertex(var13, var20, var26, var36);
            int var15 = ClarityRenderUtils.anim(arg1);
            VertexConsumer var12 = var11.setColor(var15);
        }

        public void quad(double[] arg0, double[] arg1, double[] arg2, double[] arg3, int arg4, int arg5, int arg6, int arg7) {
            this.put(arg0, arg4);
            this.put(arg1, arg5);
            this.put(arg2, arg6);
            this.put(arg3, arg7);
        }

        public void end() {
            MultiBufferSource.BufferSource var3 = this.vcp;
            RenderType var4 = this.type;
            var3.endBatch(var4);
        }

    }

    public record ESPEntry(AABB box, int argb) {

    }

    public final class OrderedBatch implements CosmeticSink {
        private static float[] pos;
        private static int[] col;
        private static long[] keys;
        private final MultiBufferSource.BufferSource vcp;
        private final RenderType type;
        private final VertexConsumer vc;
        private final PoseStack.Pose pose;
        private final Vec3 cam;
        private int n;

        private OrderedBatch(PoseStack arg0, int arg1) {
            Vec3 var4 = ClarityRenderUtils.getCameraPos();
            this.cam = var4;
            MultiBufferSource.BufferSource var11 = ClarityRenderUtils.getVCP();
            this.vcp = var11;
            RenderType var13 = ClarityRenderUtils.orderedType(arg1);
            this.type = var13;
            MultiBufferSource.BufferSource var15 = this.vcp;
            RenderType var19 = this.type;
            VertexConsumer var16 = var15.getBuffer(var19);
            this.vc = var16;
            PoseStack.Pose var18 = arg0.last();
            this.pose = var18;
        }

        public void quad(double[] arg0, double[] arg1, double[] arg2, double[] arg3, int arg4, int arg5, int arg6, int arg7) {
            double var16 = 0.0;
            double var18 = 0.0;
            double var20 = 0.0;
            int var22 = 0;
            double[] var111 = null;
            int var27 = this.n;
            int var112 = keys.length;
            if (var27 == var112) {
                long[] var28 = keys;
                int var115 = (this.n * 2);
                keys = Arrays.copyOf(var28, var115);
                float[] var30 = pos;
                int var118 = (this.n * 24);
                pos = Arrays.copyOf(var30, var118);
                int[] var32 = col;
                int var121 = (this.n * 8);
                col = Arrays.copyOf(var32, var121);
            }
            int var36 = (this.n * 12);
            var16 = 0.0;
            var18 = 0.0;
            var20 = 0.0;
            var22 = 0;
            while (true) {
                if (var22 < 4) {
                    if (var22 == 0) {
                        var111 = arg0;
                    }
                } else {
                    double var47 = (var16 * 0.25);
                    double var51 = (var18 * 0.25);
                    double var55 = (var20 * 0.25);
                    int var59 = (this.n * 4);
                    col[var59] = arg4;
                    int[] var61 = col;
                    int var127 = (var59 + 1);
                    var61[var127] = arg5;
                    int[] var62 = col;
                    int var129 = (var59 + 2);
                    var62[var129] = arg6;
                    int[] var63 = col;
                    int var131 = (var59 + 3);
                    var63[var131] = arg7;
                    double var66 = (var47 * var47);
                    double var168 = (var51 * var51);
                    double var68 = (var66 + var168);
                    double var172 = (var55 * var55);
                    float var72 = ((float) (var68 + var172));
                    long[] var73 = keys;
                    int var133 = this.n;
                    long var178 = (((long) Float.floatToIntBits(var72)) << 32);
                    long var222 = ((long) this.n);
                    long var180 = (var178 | var222);
                    var73[var133] = var180;
                    int var135 = (this.n + 1);
                    this.n = var135;
                    return;
                    if (var22 == 1) {
                        var111 = arg1;
                    } else {
                        if (var22 == 2) {
                            var111 = arg2;
                        } else {
                            var111 = arg3;
                        }
                        double[] var231 = var111;
                        double var78 = var231[0];
                        double var185 = this.cam.x;
                        float var82 = ((float) (var78 - var185));
                        double var84 = var231[1];
                        double var189 = this.cam.y;
                        float var88 = ((float) (var84 - var189));
                        double var90 = var231[2];
                        double var193 = this.cam.z;
                        float var94 = ((float) (var90 - var193));
                        float[] var95 = pos;
                        int var196 = (var22 * 3);
                        int var141 = (var36 + var196);
                        var95[var141] = var82;
                        float[] var96 = pos;
                        int var199 = (var22 * 3);
                        int var144 = ((var36 + var199) + 1);
                        var96[var144] = var88;
                        float[] var97 = pos;
                        int var203 = (var22 * 3);
                        int var147 = ((var36 + var203) + 2);
                        var97[var147] = var94;
                        double var98 = var16;
                        double var207 = ((double) var82);
                        var16 = (var98 + var207);
                        double var102 = var18;
                        double var210 = ((double) var88);
                        var18 = (var102 + var210);
                        double var106 = var20;
                        double var213 = ((double) var94);
                        var20 = (var106 + var213);
                        var22 = (var22 + 1);
                    }
                }
            }
        }

        public void nextLayer() {
            int var9 = 0;
            int var13 = 0;
            if (this.n == 0) {
                return;
            }
            long[] var15 = keys;
            int var48 = this.n;
            Arrays.sort(var15, 0, var48);
            var9 = (this.n - 1);
            while (true) {
                if (var9 < 0) break;
                long[] var20 = keys;
                int var38 = var9;
                int var25 = ((int) (var20[var38] & 4294967295L));
                int var27 = (var25 * 12);
                int var29 = (var25 * 4);
                var13 = 0;
                while (true) {
                    if (var13 >= 4) break;
                    VertexConsumer var33 = this.vc;
                    PoseStack.Pose var43 = this.pose;
                    float[] var51 = pos;
                    int var59 = (var13 * 3);
                    int var55 = (var27 + var59);
                    float var52 = var51[var55];
                    float[] var56 = pos;
                    int var66 = (var13 * 3);
                    int var62 = ((var27 + var66) + 1);
                    float var57 = var56[var62];
                    float[] var63 = pos;
                    int var72 = (var13 * 3);
                    int var70 = ((var27 + var72) + 2);
                    float var64 = var63[var70];
                    VertexConsumer var34 = var33.addVertex(var43, var52, var57, var64);
                    int[] var44 = col;
                    int var58 = var13;
                    int var54 = (var29 + var58);
                    int var46 = ClarityRenderUtils.anim(var44[var54]);
                    VertexConsumer var35 = var34.setColor(var46);
                    var13 = (var13 + 1);
                }
                var9 = (var9 + -1);
            }
            this.n = 0;
        }

        public void end() {
            this.nextLayer();
            MultiBufferSource.BufferSource var4 = this.vcp;
            RenderType var5 = this.type;
            var4.endBatch(var5);
        }

        static {
            pos = new float[12288];
            col = new int[4096];
            keys = new long[1024];
        }

    }

    public final class TexBatch {
        private final MultiBufferSource.BufferSource vcp;
        private final RenderType type;
        private final VertexConsumer vc;
        private final PoseStack.Pose pose;
        private final Vec3 cam;

        private TexBatch(MultiBufferSource.BufferSource arg0, RenderType arg1, PoseStack arg2, Vec3 arg3) {
            this.vcp = arg0;
            this.type = arg1;
            VertexConsumer var15 = arg0.getBuffer(arg1);
            this.vc = var15;
            PoseStack.Pose var17 = arg2.last();
            this.pose = var17;
            this.cam = arg3;
        }

        public void quad(Vec3 arg0, Vec3 arg1, Vec3 arg2, Vec3 arg3, int arg4) {
            int var30 = 0;
            int var13 = ClarityRenderUtils.anim(arg4);
            Vec3 var15 = arg1.subtract(arg0);
            Vec3 var32 = arg2.subtract(arg0);
            Vec3 var16 = var15.cross(var32);
            Vec3 var35 = this.cam.subtract(arg0);
            if (Double.compare(var16.dot(var35), 0.0) >= 0) {
                var30 = 1;
            } else {
                var30 = 0;
            }
            if (var30 != 0) {
                this.vert(arg0, 0.0F, 0.0F, var13);
                this.vert(arg1, 1.0F, 0.0F, var13);
                this.vert(arg2, 1.0F, 1.0F, var13);
                this.vert(arg3, 0.0F, 1.0F, var13);
            } else {
                this.vert(arg3, 0.0F, 1.0F, var13);
                this.vert(arg2, 1.0F, 1.0F, var13);
                this.vert(arg1, 1.0F, 0.0F, var13);
                this.vert(arg0, 0.0F, 0.0F, var13);
            }
        }

        public void billboard(Vec3 arg0, double arg1, double arg2, double arg3, int arg4) {
            Vec3 var141 = null;
            Vec3 var79 = null;
            Vec3 var26 = null;
            Vec3 var35 = this.cam.subtract(arg0);
            double var37 = var35.length();
            if (Double.compare(var37, 1.0E-4) < 0) {
                return;
            }
            double var82 = (1.0 / var37);
            Vec3 var43 = var35.scale(var82);
            Vec3 var44 = new Vec3(0.0, 1.0, 0.0);
            Vec3 var45 = var44.cross(var43);
            double var47 = var45.length();
            if (Double.compare(var47, 1.0E-4) < 0) {
                var79 = new Vec3(1.0, 0.0, 0.0);
            } else {
                double var87 = (1.0 / var47);
                var79 = var45.scale(var87);
            }
            var141 = var79;
            Vec3 var89 = var141;
            var26 = var43.cross(var89);
            if (arg3 != 0.0) {
                double var60 = Math.cos(arg3);
                double var64 = Math.sin(arg3);
                Vec3 var67 = var141.scale(var60);
                Vec3 var93 = var26.scale(var64);
                Vec3 var68 = var67.add(var93);
                Vec3 var70 = var26.scale(var60);
                Vec3 var97 = var141.scale(var64);
                Vec3 var71 = var70.subtract(var97);
                var141 = var68;
                var26 = var71;
            }
            Vec3 var75 = var141.scale(arg1);
            Vec3 var77 = var26.scale(arg2);
            Vec3 var104 = arg0.subtract(var75).add(var77);
            Vec3 var119 = arg0.add(var75).add(var77);
            Vec3 var128 = arg0.add(var75).subtract(var77);
            Vec3 var133 = arg0.subtract(var75).subtract(var77);
            this.quad(var104, var119, var128, var133, arg4);
        }

        public void flat(Vec3 arg0, double arg1, double arg2, int arg3) {
            double var30 = (Math.cos(arg2) * arg1);
            double var36 = (Math.sin(arg2) * arg1);
            double var49 = (-(var30) + var36);
            double var86 = (-(var36) - var30);
            Vec3 var39 = arg0.add(var49, 0.0, var86);
            double var53 = (var30 + var36);
            double var90 = (var36 - var30);
            Vec3 var41 = arg0.add(var53, 0.0, var90);
            double var57 = (var30 - var36);
            double var94 = (var36 + var30);
            Vec3 var43 = arg0.add(var57, 0.0, var94);
            double var63 = (-(var30) - var36);
            double var100 = (-(var36) + var30);
            Vec3 var45 = arg0.add(var63, 0.0, var100);
            this.quad(var39, var41, var43, var45, arg3);
        }

        private void vert(Vec3 arg0, float arg1, float arg2, int arg3) {
            VertexConsumer var12 = this.vc;
            PoseStack.Pose var19 = this.pose;
            double var26 = arg0.x;
            double var40 = this.cam.x;
            float var30 = ((float) (var26 - var40));
            double var33 = arg0.y;
            double var50 = this.cam.y;
            float var37 = ((float) (var33 - var50));
            double var43 = arg0.z;
            double var53 = this.cam.z;
            float var47 = ((float) (var43 - var53));
            VertexConsumer var15 = var12.addVertex(var19, var30, var37, var47).setColor(arg3).setUv(arg1, arg2);
            int var22 = OverlayTexture.NO_OVERLAY;
            VertexConsumer var17 = var15.setOverlay(var22).setLight(15728880);
            PoseStack.Pose var25 = this.pose;
            VertexConsumer var18 = var17.setNormal(var25, 0.0F, 1.0F, 0.0F);
        }

        public void end() {
            MultiBufferSource.BufferSource var3 = this.vcp;
            RenderType var4 = this.type;
            var3.endBatch(var4);
        }

    }

}
