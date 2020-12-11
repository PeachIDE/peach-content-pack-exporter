package com.github.mouse0w0.pcpe.data;

public class EnchantmentData {
    private String id;
    private String translationKey;

    public EnchantmentData(String id, String translationKey) {
        this.id = id;
        this.translationKey = translationKey;
    }

    public String getId() {
        return id;
    }

    public String getTranslationKey() {
        return translationKey;
    }
}
