package org.modogthedev.transparentglowing.mixin;

import org.modogthedev.transparentglowing.Constants;
import org.modogthedev.transparentglowing.TransparentGlowing;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.WorldRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(WorldRenderer.class)
public abstract class WorldRendererMixin {

    @Redirect(
            method = {"drawEntityOutlinesFramebuffer"},
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/mojang/blaze3d/systems/RenderSystem;blendFuncSeparate(Lcom/mojang/blaze3d/platform/GlStateManager$SrcFactor;Lcom/mojang/blaze3d/platform/GlStateManager$DstFactor;Lcom/mojang/blaze3d/platform/GlStateManager$SrcFactor;Lcom/mojang/blaze3d/platform/GlStateManager$DstFactor;)V"
            )
    )
    private void doEntityOutline(GlStateManager.SrcFactor srcFactor, GlStateManager.DstFactor dstFactor, GlStateManager.SrcFactor srcAlpha, GlStateManager.DstFactor dstAlpha) {
        if (Constants.enabled) {
            RenderSystem.blendFuncSeparate(
                GlStateManager.SrcFactor.SRC_ALPHA,GlStateManager.DstFactor.ONE,
                GlStateManager.SrcFactor.ONE,GlStateManager.DstFactor.ONE);
        } else {
            RenderSystem.blendFuncSeparate(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA, SourceFactor.ZERO, DestFactor.ONE);
        }
    }
}
