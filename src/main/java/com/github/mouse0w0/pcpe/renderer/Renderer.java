package com.github.mouse0w0.pcpe.renderer;

import com.github.mouse0w0.pcpe.util.ImageUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
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
    private final RenderItem renderItem = minecraft.getRenderItem();

    private final IntBuffer lastViewport = BufferUtils.createIntBuffer(16);

    public static Renderer getInstance() {
        return INSTANCE;
    }

    private Renderer() {
    }

    public void renderItemToPNG(FrameBuffer frameBuffer, ItemStack itemStack, Path file) throws IOException {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        renderItem.renderItemIntoGUI(itemStack, 0, 0);
        ImageUtils.writePNGImage(frameBuffer.readPixels(), file);
    }

    public BufferedImage renderItem(FrameBuffer frameBuffer, ItemStack itemStack) {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        renderItem.renderItemIntoGUI(itemStack, 0, 0);
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
