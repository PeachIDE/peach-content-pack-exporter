package com.github.mouse0w0.pcpe.renderer;

import com.github.mouse0w0.pcpe.util.ImageUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.ItemStack;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.IntBuffer;
import java.nio.file.Path;

public class Renderer {

    private static final Renderer INSTANCE = new Renderer();

    private final Minecraft minecraft = Minecraft.getMinecraft();
    private final TextureMap textureMapBlocks = minecraft.getTextureMapBlocks();
    private final TextureManager textureManager = minecraft.getTextureManager();
    private final RenderItem renderItem = minecraft.getRenderItem();

    private final IntBuffer lastViewport = BufferUtils.createIntBuffer(16);

    public static Renderer getInstance() {
        return INSTANCE;
    }

    private Renderer() {
    }

    public void renderItemToPNG(FrameBuffer frameBuffer, ItemStack stack, Path file) throws IOException {
        ImageUtils.writePNGImage(renderItem(frameBuffer, stack), file);
    }

    public BufferedImage renderItem(FrameBuffer frameBuffer, ItemStack stack) {
        IBakedModel model = renderItem.getItemModelWithOverrides(stack, null, null);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GlStateManager.pushMatrix();
        textureManager.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        textureManager.getTexture(TextureMap.LOCATION_BLOCKS_TEXTURE).setBlurMipmap(false, false);
        GlStateManager.enableRescaleNormal();
        GlStateManager.enableAlpha();
        GlStateManager.alphaFunc(516, 0.1F);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.translate(8.0F, 8.0F, 0.0F);
        GlStateManager.scale(1.0F, -1.0F, 1.0F);
        GlStateManager.scale(16.0F, 16.0F, 16.0F);

        if (model.isGui3d()) {
            GlStateManager.enableLighting();
        } else {
            GlStateManager.disableLighting();
        }
        model = net.minecraftforge.client.ForgeHooksClient.handleCameraTransforms(model, ItemCameraTransforms.TransformType.GUI, false);

        if (!stack.isEmpty()) {
            GlStateManager.pushMatrix();
            GlStateManager.translate(-0.5F, -0.5F, -0.5F);

            if (model.isBuiltInRenderer()) {
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                GlStateManager.enableRescaleNormal();
                stack.getItem().getTileEntityItemStackRenderer().renderByItem(stack);
            } else {
                renderItem.renderModel(model, stack);
            }

            GlStateManager.popMatrix();
        }
        GlStateManager.disableAlpha();
        GlStateManager.disableRescaleNormal();
        GlStateManager.disableLighting();
        GlStateManager.popMatrix();
        textureManager.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        textureManager.getTexture(TextureMap.LOCATION_BLOCKS_TEXTURE).restoreLastBlurMipmap();
        return frameBuffer.readPixels();
    }

    public void startRenderItem(FrameBuffer frameBuffer) {
        bindFrameBuffer(frameBuffer);

        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glPushMatrix();
        GL11.glLoadIdentity();
        GL11.glOrtho(0, 16, 0, 16, -1000f, 1000f);

        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glPushMatrix();
        GL11.glLoadIdentity();

        GlStateManager.cullFace(GlStateManager.CullFace.FRONT);
        GlStateManager.enableDepth();
        RenderHelper.enableGUIStandardItemLighting();
        GlStateManager.enableRescaleNormal();
        GlStateManager.enableLighting();

        resetAnimation();
    }

    public void resetAnimation() {
        GlStateManager.bindTexture(textureMapBlocks.getGlTextureId());
        for (TextureAtlasSprite sprite : textureMapBlocks.listAnimatedSprites) {
            sprite.frameCounter = 0;
            sprite.tickCounter = 0;
            sprite.updateAnimation();
        }
    }

    public void endRenderItem(FrameBuffer frameBuffer) {
        GlStateManager.cullFace(GlStateManager.CullFace.BACK);
        GlStateManager.disableDepth();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.disableLighting();

        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glPopMatrix();

        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glPopMatrix();

        unbindFrameBuffer(frameBuffer);
    }

    private void bindFrameBuffer(FrameBuffer frameBuffer) {
        frameBuffer.bind();

        GL11.glGetInteger(GL11.GL_VIEWPORT, lastViewport);
        GL11.glViewport(0, 0, frameBuffer.getWidth(), frameBuffer.getHeight());

        GlStateManager.clearColor(0, 0, 0, 0);
    }

    private void unbindFrameBuffer(FrameBuffer frameBuffer) {
        frameBuffer.unbind();
        GL11.glViewport(lastViewport.get(0), lastViewport.get(1), lastViewport.get(2), lastViewport.get(3));
    }
}
