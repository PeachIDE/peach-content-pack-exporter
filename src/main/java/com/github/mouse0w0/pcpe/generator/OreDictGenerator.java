package com.github.mouse0w0.pcpe.generator;

import com.github.mouse0w0.pcpe.Exporter;
import com.github.mouse0w0.pcpe.data.IdMetadata;
import com.github.mouse0w0.pcpe.data.OreDictData;
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
        List<OreDictData> data = new ArrayList<>();
        for (String oreName : OreDictionary.getOreNames()) {
            List<IdMetadata> entries = OreDictionary.getOres(oreName)
                    .stream()
                    .filter(exporter::checkNamespace)
                    .map(IdMetadata::of)
                    .collect(Collectors.toList());
            if (entries.isEmpty()) continue;
            data.add(new OreDictData(oreName, entries));
        }
        exporter.writeJson("data/ore_dictionary.json", data);
    }

    @Override
    public void exportTranslation(Exporter exporter, Map<String, String> translations) {
        // Nothing to do
    }
}
