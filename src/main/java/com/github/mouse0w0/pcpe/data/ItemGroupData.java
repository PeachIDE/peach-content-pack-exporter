package com.github.mouse0w0.pcpe.data;

public class ItemGroupData {

    private String id;
    private String translationKey;
    private Item item;

    public ItemGroupData(String id, String translationKey, Item item) {
        this.id = id;
        this.translationKey = translationKey;
        this.item = item;
    }
}
