package com.github.mouse0w0.pcpe.generator;

import com.github.mouse0w0.pcpe.Exporter;
import com.github.mouse0w0.pcpe.data.ItemGroupData;
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
        String namespace = exporter.getNamespace();
        for (CreativeTabs itemGroup : CreativeTabs.CREATIVE_TAB_ARRAY) {
            if (IGNORED_ITEM_GROUPS.contains(itemGroup.getTabLabel())) continue;
            ItemStack icon = itemGroup.getIconItemStack();

            if (namespace.equals(icon.getItem().getRegistryName().getResourceDomain())) {
                data.add(itemGroup);
            }
        }
    }

    @Override
    public void exportData(Exporter exporter) {
        String namespace = exporter.getNamespace();
        List<ItemGroupData> itemGroupList = new ArrayList<>();
        for (CreativeTabs itemGroup : data) {
            ItemStack icon = itemGroup.getIconItemStack();
            itemGroupList.add(new ItemGroupData(itemGroup.getTabLabel(),
                    getTranslationKey(namespace, itemGroup),
                    ItemRef.createItem(icon.getItem().getRegistryName().toString(), icon.getMetadata())));
        }
        exporter.writeJson("content/" + namespace + "/itemGroup.json", itemGroupList);
    }

    @Override
    public void exportL10n(Exporter exporter, Map<String, String> map) {
        String namespace = exporter.getNamespace();
        for (CreativeTabs itemGroup : data) {
            map.put(getTranslationKey(namespace, itemGroup), I18n.format(itemGroup.getTranslatedTabLabel()));
        }
    }

    private static String getTranslationKey(String namespace, CreativeTabs creativeTabs) {
        return namespace + ".itemGroup." + creativeTabs.getTabLabel();
    }
}
