package com.github.mouse0w0.mce.data;

public class ItemData {

    private String id;
    private int metadata;
    private String translationKey;
    private boolean block;

    public ItemData(String id, int metadata, String translationKey, boolean block) {
        this.id = id;
        this.metadata = metadata;
        this.translationKey = translationKey;
        this.block = block;
    }

    public String getId() {
        return id;
    }

    public int getMetadata() {
        return metadata;
    }

    public String getTranslationKey() {
        return translationKey;
    }

    public boolean isBlock() {
        return block;
    }
}
