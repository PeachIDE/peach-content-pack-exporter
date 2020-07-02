package com.github.mouse0w0.mce.renderer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

import java.awt.image.BufferedImage;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_BGRA;
import static org.lwjgl.opengl.GL12.GL_UNSIGNED_INT_8_8_8_8_REV;
import static org.lwjgl.opengl.GL30.*;

public class FrameBuffer {

    private int id;

    private final int width;
    private final int height;

    private final Texture2D colorBuffer;
    private final Texture2D depthBuffer;

    private int lastFrameBuffer;

    public FrameBuffer(int width, int height) {
        this.width = width;
        this.height = height;
        this.colorBuffer = new Texture2D(width, height, GL_RGBA, GL_BGRA, GL_UNSIGNED_BYTE);
        this.depthBuffer = new Texture2D(width, height, GL_DEPTH_COMPONENT, GL_DEPTH_COMPONENT, GL_FLOAT);
        id = GL30.glGenFramebuffers();
        bind();
        GL30.glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, this.colorBuffer.getId(), 0);
        GL30.glFramebufferTexture2D(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_TEXTURE_2D, this.depthBuffer.getId(), 0);
    }

    public int getId() {
        return id;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Texture2D getColorBuffer() {
        return colorBuffer;
    }

    public Texture2D getDepthBuffer() {
        return depthBuffer;
    }

    public void bind() {
        lastFrameBuffer = GL11.glGetInteger(GL_FRAMEBUFFER_BINDING);

        GL30.glBindFramebuffer(GL_FRAMEBUFFER, id);
    }

    public void unbind() {
        GL30.glBindFramebuffer(GL_FRAMEBUFFER, lastFrameBuffer);
    }

    public BufferedImage readPixels() {
        IntBuffer pixels = BufferUtils.createIntBuffer(width * height);
        int[] pixelsArray = new int[width * height];
        GL11.glReadPixels(0, 0, width, height, GL_BGRA, GL_UNSIGNED_INT_8_8_8_8_REV, pixels);
        pixels.get(pixelsArray);
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        bufferedImage.setRGB(0, 0, width, height, pixelsArray, 0, width);
        return bufferedImage;
    }

    public void dispose() {
        if (id == 0) return;
        GL30.glDeleteFramebuffers(id);
        id = 0;

        colorBuffer.dispose();
        depthBuffer.dispose();
    }
}
