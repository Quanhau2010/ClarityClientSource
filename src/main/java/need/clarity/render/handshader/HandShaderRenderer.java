package need.clarity.render.handshader;

import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.model.geometry.BakedQuad;
import net.minecraft.resources.Identifier;
import org.joml.Vector3fc;

public class HandShaderRenderer {
    public static boolean itemGlowContext;
    private static Identifier whiteTextureId;
    private static DynamicTexture whiteTexture;
    private static Identifier glowRampTextureId;
    private static DynamicTexture glowRampTexture;

    public HandShaderRenderer() {
    }

    public static Identifier getWhiteTexture() {
        if (whiteTextureId == null) {
            whiteTextureId = Identifier.fromNamespaceAndPath("clarity", "textures/misc/white.png");
            try {
                NativeImage var8 = new NativeImage(1, 1, false);
                var8.setPixel(0, 0, -1);
                Supplier var18 = () -> HandShaderRenderer.lambda$getWhiteTexture$0();
                DynamicTexture var10 = new DynamicTexture(var18, var8);
                whiteTexture = var10;
                TextureManager var12 = Minecraft.getInstance().getTextureManager();
                Identifier var16 = whiteTextureId;
                DynamicTexture var19 = whiteTexture;
                var12.register(var16, var19);
            } catch (Throwable ignored) {
            }
        }
        return whiteTextureId;
    }

    public static Identifier getGlowRampTexture() {
        int var9 = 0;
        int var10 = 0;
        if (glowRampTextureId == null) {
            glowRampTextureId = Identifier.fromNamespaceAndPath("clarity", "textures/misc/glow_ramp.png");
            try {
                NativeImage var18 = new NativeImage(64, 4, false);
                var9 = 0;
                while (true) {
                    if (var9 >= 4) break;
                    var10 = 0;
                    while (true) {
                        if (var10 >= 64) break;
                        float var29 = ((float) var10);
                        float var50 = ((float) (64 - 1));
                        float var30 = (var29 / var50);
                        double var37 = Math.exp((((double) (var30 * var30)) * 3.5));
                        double var75 = ((double) (var30 * var30));
                        double var62 = (1.0 - var75);
                        int var66 = Math.round((((float) (var37 * var62)) * 255.0F));
                        int var53 = Math.min(255, var66);
                        int var43 = Math.max(0, var53);
                        int var54 = var10;
                        int var67 = var9;
                        int var72 = ((var43 << 24) | 16777215);
                        var18.setPixel(var54, var67, var72);
                        var10 = (var10 + 1);
                    }
                    var9 = (var9 + 1);
                }
                Supplier var55 = () -> HandShaderRenderer.lambda$getGlowRampTexture$0();
                DynamicTexture var21 = new DynamicTexture(var55, var18);
                glowRampTexture = var21;
                TextureManager var23 = Minecraft.getInstance().getTextureManager();
                Identifier var46 = glowRampTextureId;
                DynamicTexture var56 = glowRampTexture;
                var23.register(var46, var56);
            } catch (Throwable ignored) {
            }
        }
        return glowRampTextureId;
    }

    public static void renderCustomArm(PoseStack arg0, SubmitNodeCollector arg1, int arg2, Identifier arg3, ModelPart arg4) {
        RenderType var37 = null;
        Identifier var25 = null;
        int var23 = HandShaderConfig.getEffectiveLight(arg2);
        if (HandShaderConfig.textured) {
            var25 = arg3;
        } else {
            var25 = getWhiteTexture();
        }
        Identifier var18 = var25;
        if (HandShaderConfig.fullbright) {
            var37 = RenderTypes.entityTranslucentEmissive(var18);
        } else {
            var37 = RenderTypes.entityTranslucent(var18);
        }
        RenderType var19 = var37;
        int var28 = HandShaderConfig.getArmColor();
        int var10 = OverlayTexture.NO_OVERLAY;
        arg1.submitModelPart(arg4, arg0, var19, var23, var10, null, false, false, var28, null, 0);
        if (HandShaderConfig.outline) {
            int var31 = HandShaderConfig.getEffectiveOutlineColor();
            float var34 = ((float) HandShaderConfig.outlineWidth);
            ModelPart.Visitor var40 = (p0, p1, p2, p3) -> HandShaderRenderer.lambda$renderCustomArm$0(var31, arg1, arg0, var34, p0, p1, p2, p3);
            arg4.visit(arg0, var40);
        }
    }

    private static void drawBoxEdges(VertexConsumer arg0, PoseStack.Pose arg1, float arg2, float arg3, float arg4, float arg5, float arg6, float arg7, int arg8, float arg9, float arg10) {
        float var27 = (arg2 - arg10);
        float var29 = (arg5 + arg10);
        float var31 = (arg3 - arg10);
        float var33 = (arg6 + arg10);
        float var35 = (arg4 - arg10);
        float var37 = (arg7 + arg10);
        drawEdge(arg0, arg1, var27, arg3, arg4, var29, arg3, arg4, arg8, arg9);
        drawEdge(arg0, arg1, arg5, arg3, var35, arg5, arg3, var37, arg8, arg9);
        drawEdge(arg0, arg1, var29, arg3, arg7, var27, arg3, arg7, arg8, arg9);
        drawEdge(arg0, arg1, arg2, arg3, var37, arg2, arg3, var35, arg8, arg9);
        drawEdge(arg0, arg1, var27, arg6, arg4, var29, arg6, arg4, arg8, arg9);
        drawEdge(arg0, arg1, arg5, arg6, var35, arg5, arg6, var37, arg8, arg9);
        drawEdge(arg0, arg1, var29, arg6, arg7, var27, arg6, arg7, arg8, arg9);
        drawEdge(arg0, arg1, arg2, arg6, var37, arg2, arg6, var35, arg8, arg9);
        drawEdge(arg0, arg1, arg2, var31, arg4, arg2, var33, arg4, arg8, arg9);
        drawEdge(arg0, arg1, arg5, var31, arg4, arg5, var33, arg4, arg8, arg9);
        drawEdge(arg0, arg1, arg5, var31, arg7, arg5, var33, arg7, arg8, arg9);
        drawEdge(arg0, arg1, arg2, var31, arg7, arg2, var33, arg7, arg8, arg9);
    }

    private static void drawEdge(VertexConsumer arg0, PoseStack.Pose arg1, float arg2, float arg3, float arg4, float arg5, float arg6, float arg7, int arg8, float arg9) {
        float var15 = 0.0F;
        float var16 = 0.0F;
        float var17 = 0.0F;
        var15 = (arg5 - arg2);
        var16 = (arg6 - arg3);
        var17 = (arg7 - arg4);
        float var24 = var15;
        float var53 = var15;
        float var25 = (var24 * var53);
        float var54 = var16;
        float var12 = var16;
        float var55 = (var54 * var12);
        float var26 = (var25 + var55);
        float var56 = var17;
        float var70 = var17;
        float var57 = (var56 * var70);
        float var32 = ((float) Math.sqrt(((double) (var26 + var57))));
        if (Float.compare(var32, 1.0E-5F) > 0) {
            var15 = (var15 / var32);
            var16 = (var16 / var32);
            var17 = (var17 / var32);
        }
        VertexConsumer var43 = arg0.addVertex(arg1, arg2, arg3, arg4).setColor(arg8);
        float var72 = var15;
        float var75 = var16;
        float var78 = var17;
        VertexConsumer var45 = var43.setNormal(arg1, var72, var75, var78).setLineWidth(arg9);
        VertexConsumer var48 = arg0.addVertex(arg1, arg5, arg6, arg7).setColor(arg8);
        float var74 = var15;
        float var77 = var16;
        float var80 = var17;
        VertexConsumer var50 = var48.setNormal(arg1, var74, var77, var80).setLineWidth(arg9);
    }

    private static void renderSilhouetteGlow(SubmitNodeCollector arg0, PoseStack arg1, float arg2, float arg3, float arg4, float arg5, float arg6, float arg7, int arg8, float arg9) {
        RenderType var21 = RenderTypes.entityTranslucentEmissive(getWhiteTexture());
        SubmitNodeCollector.CustomGeometryRenderer var22 = (p0, p1) -> HandShaderRenderer.lambda$renderSilhouetteGlow$0(arg2, arg3, arg4, arg5, arg6, arg7, arg9, arg8, p0, p1);
        arg0.submitCustomGeometry(arg1, var21, var22);
    }

    private static void emitGlowQuad(VertexConsumer arg0, PoseStack.Pose arg1, float arg2, float arg3, float arg4, float arg5, float arg6, float arg7, float arg8, float arg9, float arg10, float arg11, float arg12, float arg13, float arg14, float arg15, float arg16, int arg17) {
        int var24 = (arg17 & 16777215);
        VertexConsumer var28 = arg0.addVertex(arg1, arg2, arg3, arg4).setColor(arg17).setUv(0.0F, 0.5F);
        int var84 = OverlayTexture.NO_OVERLAY;
        VertexConsumer var31 = var28.setOverlay(var84).setLight(15728880).setNormal(arg1, arg14, arg15, arg16);
        VertexConsumer var35 = arg0.addVertex(arg1, arg5, arg6, arg7).setColor(arg17).setUv(0.0F, 0.5F);
        int var90 = OverlayTexture.NO_OVERLAY;
        VertexConsumer var38 = var35.setOverlay(var90).setLight(15728880).setNormal(arg1, arg14, arg15, arg16);
        VertexConsumer var42 = arg0.addVertex(arg1, arg8, arg9, arg10).setColor(var24).setUv(1.0F, 0.5F);
        int var96 = OverlayTexture.NO_OVERLAY;
        VertexConsumer var45 = var42.setOverlay(var96).setLight(15728880).setNormal(arg1, arg14, arg15, arg16);
        VertexConsumer var49 = arg0.addVertex(arg1, arg11, arg12, arg13).setColor(var24).setUv(1.0F, 0.5F);
        int var102 = OverlayTexture.NO_OVERLAY;
        VertexConsumer var52 = var49.setOverlay(var102).setLight(15728880).setNormal(arg1, arg14, arg15, arg16);
        VertexConsumer var56 = arg0.addVertex(arg1, arg11, arg12, arg13).setColor(var24).setUv(1.0F, 0.5F);
        int var108 = OverlayTexture.NO_OVERLAY;
        VertexConsumer var59 = var56.setOverlay(var108).setLight(15728880).setNormal(arg1, arg14, arg15, arg16);
        VertexConsumer var63 = arg0.addVertex(arg1, arg8, arg9, arg10).setColor(var24).setUv(1.0F, 0.5F);
        int var114 = OverlayTexture.NO_OVERLAY;
        VertexConsumer var66 = var63.setOverlay(var114).setLight(15728880).setNormal(arg1, arg14, arg15, arg16);
        VertexConsumer var70 = arg0.addVertex(arg1, arg5, arg6, arg7).setColor(arg17).setUv(0.0F, 0.5F);
        int var120 = OverlayTexture.NO_OVERLAY;
        VertexConsumer var73 = var70.setOverlay(var120).setLight(15728880).setNormal(arg1, arg14, arg15, arg16);
        VertexConsumer var77 = arg0.addVertex(arg1, arg2, arg3, arg4).setColor(arg17).setUv(0.0F, 0.5F);
        int var126 = OverlayTexture.NO_OVERLAY;
        VertexConsumer var80 = var77.setOverlay(var126).setLight(15728880).setNormal(arg1, arg14, arg15, arg16);
    }

    public static void renderItemGlowBox(PoseStack arg0, SubmitNodeCollector arg1, double arg2, double arg3, double arg4, double arg5, double arg6, double arg7) {
        if (!HandShaderConfig.outline) {
            return;
        }
        int var41 = HandShaderConfig.getEffectiveOutlineColor();
        float var44 = ((float) HandShaderConfig.outlineWidth);
        float var47 = ((float) arg2);
        float var50 = ((float) arg3);
        float var53 = ((float) arg4);
        float var56 = ((float) arg5);
        float var59 = ((float) arg6);
        float var62 = ((float) arg7);
        if (HandShaderConfig.outlineGlow) {
            float var66 = ((float) HandShaderConfig.glowSpread);
            int var69 = ((var41 >> 16) & 255);
            int var72 = ((var41 >> 8) & 255);
            int var74 = (var41 & 255);
            int var109 = Math.round((((float) ((var41 >> 24) & 255)) * 0.55F));
            int var96 = Math.min(255, var109);
            int var81 = (Math.max(1, var96) << 24);
            int var99 = (var69 << 16);
            int var82 = (var81 | var99);
            int var101 = (var72 << 8);
            int var84 = ((var82 | var101) | var74);
            float var104 = (0.035F * var66);
            float var86 = (0.04F + var104);
            renderSilhouetteGlow(arg1, arg0, var47, var50, var53, var56, var59, var62, var84, var86);
        }
        RenderType var114 = RenderTypes.linesTranslucent();
        SubmitNodeCollector.CustomGeometryRenderer var117 = (p0, p1) -> HandShaderRenderer.lambda$renderItemGlowBox$0(var47, var50, var53, var56, var59, var62, var41, var44, p0, p1);
        arg1.submitCustomGeometry(arg0, var114, var117);
    }

    public static void renderItemGlowQuads(PoseStack arg0, SubmitNodeCollector arg1, List arg2) {
        double var15 = 0.0;
        double var17 = 0.0;
        double var19 = 0.0;
        double var21 = 0.0;
        double var23 = 0.0;
        double var25 = 0.0;
        float var219 = 0.0F;
        int var221 = 0;
        if (!HandShaderConfig.outline) {
            return;
        }
        if (arg2 != null) {
            if (arg2.isEmpty()) {
            }
        }
        return;
        int var39 = HandShaderConfig.getEffectiveOutlineColor();
        float var42 = ((float) HandShaderConfig.outlineWidth);
        var15 = 1.7976931348623157E308;
        var17 = 1.7976931348623157E308;
        var19 = 1.7976931348623157E308;
        var21 = -1.7976931348623157E308;
        var23 = -1.7976931348623157E308;
        var25 = -1.7976931348623157E308;
        Iterator var56 = arg2.iterator();
        while (true) {
            if (!var56.hasNext()) break;
            BakedQuad var97 = ((BakedQuad) var56.next());
            var221 = 0;
            while (true) {
                if (var221 >= 4) continue;
            }
        }
        int var177 = var221;
        Vector3fc var101 = var97.position(var177);
        if (var101 == null) {
        } else {
            double var105 = ((double) var101.x());
            double var195 = var15;
            if (Double.compare(var105, var195) < 0) {
                var15 = ((double) var101.x());
            }
            double var114 = ((double) var101.y());
            double var197 = var17;
            if (Double.compare(var114, var197) < 0) {
                var17 = ((double) var101.y());
            }
            double var123 = ((double) var101.z());
            double var199 = var19;
            if (Double.compare(var123, var199) < 0) {
                var19 = ((double) var101.z());
            }
            double var132 = ((double) var101.x());
            double var201 = var21;
            if (Double.compare(var132, var201) > 0) {
                var21 = ((double) var101.x());
            }
            double var141 = ((double) var101.y());
            double var203 = var23;
            if (Double.compare(var141, var203) > 0) {
                var23 = ((double) var101.y());
            }
            double var150 = ((double) var101.z());
            double var205 = var25;
            if (Double.compare(var150, var205) > 0) {
                var25 = ((double) var101.z());
            }
            var221 = (var221 + 1);
            double var59 = var21;
            double var5 = var15;
            double var61 = (var59 - var5);
            double var178 = var23;
            double var8 = var17;
            double var180 = (var178 - var8);
            double var212 = var25;
            double var11 = var19;
            double var214 = (var212 - var11);
            double var182 = Math.max(var180, var214);
            var219 = ((float) Math.max(var61, var182));
            if (Float.compare(var219, 0.0F) <= 0) {
                var219 = 1.0F;
            }
            RenderType var184 = RenderTypes.linesTranslucent();
            SubmitNodeCollector.CustomGeometryRenderer var207 = (p0, p1) -> HandShaderRenderer.lambda$renderItemGlowQuads$0(arg2, var39, var42, p0, p1);
            arg1.submitCustomGeometry(arg0, var184, var207);
            if (HandShaderConfig.outlineGlow) {
                float var73 = ((float) HandShaderConfig.glowSpread);
                int var76 = ((var39 >> 16) & 255);
                int var79 = ((var39 >> 8) & 255);
                int var81 = (var39 & 255);
                int var188 = Math.round((((float) ((var39 >> 24) & 255)) * 0.55F));
                int var166 = Math.min(255, var188);
                int var88 = (Math.max(1, var166) << 24);
                int var169 = (var76 << 16);
                int var89 = (var88 | var169);
                int var171 = (var79 << 8);
                int var91 = ((var89 | var171) | var81);
                float var92 = var219;
                float var192 = (0.04F * var73);
                float var174 = (0.045F + var192);
                float var93 = (var92 * var174);
                RenderType var194 = RenderTypes.entityTranslucentEmissive(getWhiteTexture());
                SubmitNodeCollector.CustomGeometryRenderer var211 = (p0, p1) -> HandShaderRenderer.lambda$renderItemGlowQuads$1(arg2, var91, var93, p0, p1);
                arg1.submitCustomGeometry(arg0, var194, var211);
            }
            return;
        }
    }

    private static void drawEdgeGlow(VertexConsumer arg0, PoseStack.Pose arg1, float arg2, float arg3, float arg4, float arg5, float arg6, float arg7, float arg8, float arg9, float arg10, int arg11, float arg12) {
        float var35 = 0.0F;
        float var36 = 0.0F;
        float var37 = 0.0F;
        float var45 = (arg5 - arg2);
        float var47 = (arg6 - arg3);
        float var49 = (arg7 - arg4);
        float var51 = (var45 * var45);
        float var89 = (var47 * var47);
        float var52 = (var51 + var89);
        float var91 = (var49 * var49);
        float var58 = ((float) Math.sqrt(((double) (var52 + var91))));
        var35 = 0.0F;
        var36 = 0.0F;
        var37 = 0.0F;
        if (Float.compare(var58, 1.0E-5F) > 0) {
            var35 = (var45 / var58);
            var36 = (var47 / var58);
            var37 = (var49 / var58);
        }
        float var71 = (arg12 * 0.70710677F);
        float var73 = (arg8 * arg12);
        float var75 = (arg9 * arg12);
        float var77 = (arg10 * arg12);
        float var79 = (var35 * var71);
        float var81 = (var36 * var71);
        float var83 = (var37 * var71);
        float var107 = ((arg5 + var73) + var79);
        float var111 = ((arg6 + var75) + var81);
        float var115 = ((arg7 + var77) + var83);
        float var119 = ((arg2 + var73) - var79);
        float var123 = ((arg3 + var75) - var81);
        float var127 = ((arg4 + var77) - var83);
        emitGlowQuad(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, var107, var111, var115, var119, var123, var127, arg8, arg9, arg10, arg11);
    }

    static {
        itemGlowContext = false;
        whiteTextureId = null;
        whiteTexture = null;
        glowRampTextureId = null;
        glowRampTexture = null;
    }

}
