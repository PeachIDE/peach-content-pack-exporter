package com.github.mouse0w0.mce.data;

import java.util.List;

public class OreDictionaryData {

    private String name;
    private List<ItemStackData> itemStacks;

    public OreDictionaryData(String name, List<ItemStackData> itemStacks) {
        this.name = name;
        this.itemStacks = itemStacks;
    }

    public String getName() {
        return name;
    }

    public List<ItemStackData> getItemStacks() {
        return itemStacks;
    }
}
