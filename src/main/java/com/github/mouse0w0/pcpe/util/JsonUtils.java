package com.github.mouse0w0.pcpe.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

public class JsonUtils {
    private static final Gson GSON = new GsonBuilder().create();

    public static Gson gson() {
        return GSON;
    }

    public static void writeJson(Path path, Object object) throws IOException {
        FileUtils.createFileIfNotExists(path);
        try (Writer writer = Files.newBufferedWriter(path)) {
            JsonUtils.gson().toJson(object, writer);
        }
    }
}
