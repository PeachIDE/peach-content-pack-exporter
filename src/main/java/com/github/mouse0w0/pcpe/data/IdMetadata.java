package com.github.mouse0w0.pcpe.data;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import net.minecraft.item.ItemStack;

import java.io.IOException;

@JsonAdapter(IdMetadata.TypeAdapter.class)
public class IdMetadata {
    public static final int METADATA_WILDCARD = Short.MAX_VALUE;
    public static final int ORE_DICTIONARY = Short.MAX_VALUE + 1;

    private String id;
    private int metadata;

    public static IdMetadata of(ItemStack itemStack) {
        return new IdMetadata(itemStack.getItem().getRegistryName().toString(), itemStack.getMetadata());
    }

    public static IdMetadata of(String id, int metadata) {
        return new IdMetadata(id, metadata);
    }

    public static IdMetadata ignoreMetadata(String id) {
        return new IdMetadata(id, METADATA_WILDCARD);
    }

    public static IdMetadata oreDictionary(String id) {
        return new IdMetadata(id, ORE_DICTIONARY);
    }

    private IdMetadata(String id, int metadata) {
        this.id = id;
        this.metadata = metadata;
    }

    public String getId() {
        return id;
    }

    public int getMetadata() {
        return metadata;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IdMetadata that = (IdMetadata) o;
        return metadata == that.metadata && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode() * 31 + metadata;
    }

    @Override
    public String toString() {
        return metadata == 0 ? id : id + "#" + metadata;
    }

    public static final class TypeAdapter extends com.google.gson.TypeAdapter<IdMetadata> {

        @Override
        public void write(JsonWriter out, IdMetadata value) throws IOException {
            if (value == null) {
                out.nullValue();
            } else {
                out.value(value.toString());
            }
        }

        @Override
        public IdMetadata read(JsonReader in) throws IOException {
            throw new UnsupportedOperationException();
        }
    }
}
