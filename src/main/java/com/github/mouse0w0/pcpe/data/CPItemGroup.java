package com.github.mouse0w0.pcpe.data;

public class CPItemGroup {

    private String id;
    private String translationKey;
    private ItemRef icon;

    public CPItemGroup(String id, String translationKey, ItemRef icon) {
        this.id = id;
        this.translationKey = translationKey;
        this.icon = icon;
    }
}
