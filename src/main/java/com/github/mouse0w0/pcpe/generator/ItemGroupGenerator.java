package com.github.mouse0w0.pcpe.generator;

import com.github.mouse0w0.pcpe.Exporter;
import com.github.mouse0w0.pcpe.data.IconicData;
import com.github.mouse0w0.pcpe.data.IdMetadata;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

import java.util.*;
import java.util.stream.Collectors;

public class ItemGroupGenerator implements DataGenerator {
    private static final Set<String> IGNORED_ITEM_GROUPS = new HashSet<>(Arrays.asList("search", "inventory", "hotbar"));

    private List<CreativeTabs> data;

    @Override
    public void collectData(Exporter exporter) {
        data = new ArrayList<>();
        for (CreativeTabs itemGroup : CreativeTabs.CREATIVE_TAB_ARRAY) {
            if (IGNORED_ITEM_GROUPS.contains(itemGroup.getTabLabel())) continue;
            ItemStack icon = itemGroup.getIcon();

            if (exporter.checkNamespace(icon.getItem())) {
                data.add(itemGroup);
            }
        }
    }

    @Override
    public void exportData(Exporter exporter) {
        exporter.writeJson("data/item_group.json",
                data.stream().map(creativeTabs -> new IconicData(
                                creativeTabs.getTabLabel(),
                                IdMetadata.of(creativeTabs.getIcon())))
                        .collect(Collectors.toList()));
    }

    @Override
    public void exportTranslation(Exporter exporter, Map<String, String> translations) {
        String namespace = exporter.getNamespace();
        for (CreativeTabs itemGroup : data) {
            translations.put(getTranslationKey(namespace, itemGroup), I18n.format(itemGroup.getTranslationKey()));
        }
    }

    private static String getTranslationKey(String namespace, CreativeTabs creativeTabs) {
        return "itemGroup." + creativeTabs.getTabLabel();
    }
}
