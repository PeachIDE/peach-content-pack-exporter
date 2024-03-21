package com.github.mouse0w0.pcpe.data;

public class IconicData {
    private final String id;
    private final IdMetadata icon;

    public IconicData(String id, IdMetadata icon) {
        this.id = id;
        this.icon = icon;
    }

    public String getId() {
        return id;
    }

    public IdMetadata getIcon() {
        return icon;
    }
}
