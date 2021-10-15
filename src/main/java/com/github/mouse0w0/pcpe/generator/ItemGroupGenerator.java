package com.github.mouse0w0.pcpe.generator;

import com.github.mouse0w0.pcpe.Exporter;
import com.github.mouse0w0.pcpe.data.CPItemGroup;
import com.github.mouse0w0.pcpe.data.ItemRef;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

import java.util.*;

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
        String namespace = exporter.getNamespace();
        List<CPItemGroup> itemGroupList = new ArrayList<>();
        for (CreativeTabs itemGroup : data) {
            ItemStack icon = itemGroup.getIcon();
            itemGroupList.add(new CPItemGroup(itemGroup.getTabLabel(),
                    getTranslationKey(namespace, itemGroup),
                    ItemRef.createItem(icon.getItem().getRegistryName().toString(), icon.getMetadata())));
        }
        exporter.writeJson("content/" + namespace + "/itemGroup.json", itemGroupList);
    }

    @Override
    public void exportTranslation(Exporter exporter, Map<String, String> translations) {
        String namespace = exporter.getNamespace();
        for (CreativeTabs itemGroup : data) {
            translations.put(getTranslationKey(namespace, itemGroup), I18n.format(itemGroup.getTranslationKey()));
        }
    }

    private static String getTranslationKey(String namespace, CreativeTabs creativeTabs) {
        return "itemGroup." + namespace + "." + creativeTabs.getTabLabel();
    }
}
