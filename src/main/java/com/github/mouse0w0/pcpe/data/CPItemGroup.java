package com.github.mouse0w0.pcpe.data;

public class CPItemGroup {

    private String id;
    private String translationKey;
    private ItemRef item;

    public CPItemGroup(String id, String translationKey, ItemRef item) {
        this.id = id;
        this.translationKey = translationKey;
        this.item = item;
    }
}
