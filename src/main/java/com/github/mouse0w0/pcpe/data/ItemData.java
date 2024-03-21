package com.github.mouse0w0.pcpe.data;

public class ItemData {
    public String id;
    public int metadata;
    public int maxStackSize;
    public int maxDamage;
    public boolean isBlock;

    public ItemData(String id, int metadata, int maxStackSize, int maxDamage, boolean isBlock) {
        this.id = id;
        this.metadata = metadata;
        this.maxStackSize = maxStackSize;
        this.maxDamage = maxDamage;
        this.isBlock = isBlock;
    }
}
