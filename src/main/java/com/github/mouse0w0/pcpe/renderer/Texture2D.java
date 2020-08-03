package com.github.mouse0w0.pcpe.renderer;

import org.lwjgl.opengl.GL11;

import java.nio.ByteBuffer;

public class Texture2D {

    private int id;

    private final int width;
    private final int height;

    public Texture2D(int width, int height, int internalFormat, int format, int type) {
        this.width = width;
        this.height = height;
        id = GL11.glGenTextures();
        bind();
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, internalFormat, width, height,
                0, format, type, (ByteBuffer) null);
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

    public void bind() {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
    }

    public void dispose() {
        if (id == 0) return;
        GL11.glDeleteTextures(id);
        id = 0;
    }
}
