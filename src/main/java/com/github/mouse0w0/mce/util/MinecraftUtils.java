package com.github.mouse0w0.mce.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelManager;

import java.lang.reflect.Field;

public class MinecraftUtils {

    public static final ModelManager MODEL_MANAGER = getFieldValueByType(Minecraft.getMinecraft(), ModelManager.class);

    public static ModelManager getModelManager() {
        return MODEL_MANAGER;
    }

    public static String getLanguage() {
        return Minecraft.getMinecraft().gameSettings.language;
    }

    private static <T> T getFieldValueByType(Object obj, Class<T> type) {
        for (Field field : obj.getClass().getDeclaredFields()) {
            if (field.getType().equals(type)) {
                try {
                    field.setAccessible(true);
                    return (T) field.get(obj);
                } catch (IllegalAccessException e) {
                    return null;
                }
            }
        }
        return null;
    }
}
