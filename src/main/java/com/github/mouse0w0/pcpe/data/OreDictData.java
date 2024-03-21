package com.github.mouse0w0.pcpe.data;

import java.util.List;

public class OreDictData {

    private String id;
    private List<IdMetadata> entries;

    public OreDictData(String id, List<IdMetadata> entries) {
        this.id = id;
        this.entries = entries;
    }

    public String getId() {
        return id;
    }

    public List<IdMetadata> getEntries() {
        return entries;
    }
}
