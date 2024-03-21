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
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

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

        FloatBuffer fb16 = BufferUtils.createFloatBuffer(16);
        glGetFloat(GL_PROJECTION_MATRIX, fb16);
        writer.println("ProjMatrix: " + toString(fb16));
        glGetFloat(GL_MODELVIEW_MATRIX, fb16);
        writer.println("ModelViewMatrix: " + toString(fb16));
    }

    public static String glGetParams(int pname) {
        int params = glGetInteger(pname);
        return GL_PARAMS.getOrDefault(params, "0x" + Integer.toHexString(params));
    }

    public static void printBuffer(FloatBuffer buffer) {
        System.out.println(toString(buffer));
    }

    public static String toString(FloatBuffer buffer) {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        int limit = buffer.position() != 0 ? buffer.position() : buffer.limit();
        for (int i = 0; i < limit; i++) {
            if (i != 0) builder.append(", ");
            builder.append(buffer.get(i)).append("f");
        }
        builder.append("]");
        return builder.toString();
    }

    public static float[] toFloatArray(int[] array) {
        float[] result = new float[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = Float.intBitsToFloat(array[i]);
        }
        return result;
    }
}
