package com.github.mouse0w0.pcpe.util;

import com.google.common.base.Strings;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.*;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.StringJoiner;

import static org.lwjgl.opengl.GL11.*;

public class RenderUtils {
    private static final Int2ObjectMap<String> GL_PARAMS = new Int2ObjectArrayMap<>();

    static {
        Class<?>[] classes = new Class[]{
                GL11.class, GL12.class, GL13.class, GL14.class, GL15.class,
                GL20.class, GL21.class,
                //                GL30.class, GL31.class, GL32.class, GL33.class,
                //                GL40.class, GL41.class, GL42.class, GL43.class, GL44.class, GL45.class
        };
        for (Class<?> clazz : classes) {
            for (Field field : clazz.getFields()) {
                try {
                    int params = (int) field.get(null);
                    GL_PARAMS.putIfAbsent(params, field.getName() + " (0x" + Integer.toHexString(params) + ")");
                } catch (ReflectiveOperationException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void saveGLState(String fileName) throws IOException {
        File file = new File(fileName);
        if (!file.exists()) {
            file.createNewFile();
        }
        try (PrintStream writer = new PrintStream(file)) {
            printGLState(writer);
        }
    }

    public static void printGLState() {
        printGLState(System.out);
    }


    private static final IntBuffer ib16 = BufferUtils.createIntBuffer(16);
    private static final FloatBuffer fb16 = BufferUtils.createFloatBuffer(16);

    public static void printGLState(PrintStream writer) {
        writer.println("Date: " + ZonedDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        writer.println(Strings.repeat("=", 32));

        writer.println("GL Version: " + glGetString(GL_VERSION));
        writer.println("GL Vendor: " + glGetString(GL_VENDOR));
        writer.println("GL Renderer: " + glGetString(GL_RENDERER));
        //        writer.println("GL Extensions: " + GL11.glGetString(GL_EXTENSIONS));
        writer.println(Strings.repeat("=", 32));

        writer.println("Alpha Test: " + glGetBoolean(GL_ALPHA_TEST));
        writer.println("Alpha Func: " + glGetParams(GL_ALPHA_TEST_FUNC));
        writer.println("Alpha Ref: " + glGetFloat(GL_ALPHA_TEST_REF));

        writer.println("Blend: " + glGetBoolean(GL_BLEND));
        writer.println("Blend Src: " + glGetParams(GL_BLEND_SRC));
        writer.println("Blend Dst: " + glGetParams(GL_BLEND_DST));

        writer.println("Depth Test: " + glGetBoolean(GL_DEPTH_TEST));
        writer.println("Depth Write: " + glGetBoolean(GL_DEPTH_WRITEMASK));
        writer.println("Depth Func: " + glGetParams(GL_DEPTH_FUNC));

        writer.println("Cull Face: " + glGetBoolean(GL_CULL_FACE));
        writer.println("Cull Mode: " + glGetParams(GL_CULL_FACE_MODE));
        writer.println("Front Face: " + glGetParams(GL_FRONT_FACE));

        glGetInteger(GL_VIEWPORT, ib16);
        writer.println("Viewport: " + toString(ib16, 4));

        glGetFloat(GL_PROJECTION_MATRIX, fb16);
        writer.println("ProjectionMatrix: " + toString(fb16));

        glGetFloat(GL_MODELVIEW_MATRIX, fb16);
        writer.println("ModelViewMatrix: " + toString(fb16));
    }

    public static String glGetParams(int pname) {
        int params = glGetInteger(pname);
        return GL_PARAMS.getOrDefault(params, "0x" + Integer.toHexString(params));
    }

    public static String toString(IntBuffer buffer) {
        return toString(buffer, buffer.limit());
    }

    public static String toString(IntBuffer buffer, int limit) {
        StringJoiner joiner = new StringJoiner(", ", "[", "]");
        for (int i = 0; i < limit; i++) {
            joiner.add(Integer.toString(buffer.get(i)));
        }
        return joiner.toString();
    }

    public static String toString(FloatBuffer buffer) {
        return toString(buffer, buffer.limit());
    }

    public static String toString(FloatBuffer buffer, int limit) {
        StringJoiner joiner = new StringJoiner(", ", "[", "]");
        for (int i = 0; i < limit; i++) {
            joiner.add(Float.toString(buffer.get(i)));
        }
        return joiner.toString();
    }
}
