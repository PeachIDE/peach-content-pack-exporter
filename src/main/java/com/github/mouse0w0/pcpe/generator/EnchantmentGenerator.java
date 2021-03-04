package com.github.mouse0w0.pcpe.generator;

import com.github.mouse0w0.pcpe.Exporter;
import com.github.mouse0w0.pcpe.data.EnchantmentData;
import net.minecraft.client.resources.I18n;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EnchantmentGenerator implements DataGenerator {

    private List<Enchantment> data;

    @Override
    public void collectData(Exporter exporter) {
        data = new ArrayList<>();
        String namespace = exporter.getNamespace();
        for (Enchantment enchantment : Enchantment.REGISTRY) {
            if (namespace.equals(enchantment.getRegistryName().getNamespace())) {
                data.add(enchantment);
            }
        }
    }

    @Override
    public void exportData(Exporter exporter) {
        String namespace = exporter.getNamespace();
        exporter.writeJson("content/" + namespace + "/enchantment.json",
                data.stream().map(enchantment ->
                        new EnchantmentData(enchantment.getRegistryName().toString(), getTranslationKey(enchantment)))
                        .collect(Collectors.toList()));
    }

    @Override
    public void exportTranslation(Exporter exporter, Map<String, String> translations) {
        data.forEach(enchantment -> translations.put(getTranslationKey(enchantment), I18n.format(enchantment.getName())));
    }


    private static String getTranslationKey(Enchantment enchantment) {
        ResourceLocation location = enchantment.getRegistryName();
        return "enchantment." + location.getNamespace() + "." + location.getPath();
    }
}
