package com.github.mouse0w0.pcpe.util;

import java.lang.reflect.Field;

public class ReflectionUtils {

    public static <T> T getDeclaredValue(Object obj, String name) {
        try {
            Field field = obj.getClass().getDeclaredField(name);
            field.setAccessible(true);
            return (T) field.get(obj);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            return null;
        }
    }
}
