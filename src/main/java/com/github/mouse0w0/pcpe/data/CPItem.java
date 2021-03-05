package com.github.mouse0w0.pcpe.data;

import java.util.Objects;

public class CPItem {

    private String id;
    private int metadata;
    private String translationKey;
    private boolean block;

    public CPItem(String id, int metadata, String translationKey, boolean block) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CPItem item = (CPItem) o;
        return metadata == item.metadata &&
                id.equals(item.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, metadata);
    }
}
