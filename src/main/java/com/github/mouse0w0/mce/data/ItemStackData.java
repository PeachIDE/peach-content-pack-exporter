package com.github.mouse0w0.mce.data;

public class ItemStackData {

    private String id;
    private int metadata;

    public ItemStackData(String id, int metadata) {
        this.id = id;
        this.metadata = metadata;
    }

    public String getId() {
        return id;
    }

    public int getMetadata() {
        return metadata;
    }
}
