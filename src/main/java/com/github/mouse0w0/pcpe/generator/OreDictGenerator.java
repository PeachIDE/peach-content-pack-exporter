package com.github.mouse0w0.pcpe.generator;

import com.github.mouse0w0.pcpe.Exporter;
import com.github.mouse0w0.pcpe.data.CPOreDict;
import com.github.mouse0w0.pcpe.data.ItemRef;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OreDictGenerator implements DataGenerator {
    @Override
    public void collectData(Exporter exporter) {
        // Nothing to do
    }

    @Override
    public void exportData(Exporter exporter) {
        String namespace = exporter.getNamespace();
        List<CPOreDict> data = new ArrayList<>();
        for (String oreName : OreDictionary.getOreNames()) {
            List<ItemRef> entries = OreDictionary.getOres(oreName).parallelStream()
                    .filter(itemStack -> exporter.checkNamespace(itemStack.getItem()))
                    .map(itemStack -> ItemRef.createItem(itemStack.getItem().getRegistryName().toString(), itemStack.getMetadata()))
                    .collect(Collectors.toList());
            if (entries.isEmpty()) continue;
            data.add(new CPOreDict(oreName, entries));
        }
        exporter.writeJson("content/" + namespace + "/oreDictionary.json", data);
    }

    @Override
    public void exportTranslation(Exporter exporter, Map<String, String> translations) {
        // Nothing to do
    }
}
