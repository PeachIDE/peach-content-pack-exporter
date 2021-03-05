package com.github.mouse0w0.pcpe.generator.vanilla;

import com.github.mouse0w0.pcpe.Exporter;
import com.github.mouse0w0.pcpe.data.CPMapColor;
import com.github.mouse0w0.pcpe.data.ItemRef;
import com.github.mouse0w0.pcpe.generator.DataGenerator;
import net.minecraft.init.Blocks;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class VanillaMapColorGenerator implements DataGenerator {
    @Override
    public void collectData(Exporter exporter) {
        // Nothing to do.
    }

    /**
     * @see net.minecraft.block.material.MapColor
     */
    @Override
    public void exportData(Exporter exporter) {
        List<CPMapColor> mapColors = new ArrayList<>();
        mapColors.add(mapColor("air", Blocks.AIR));
        mapColors.add(mapColor("grass", Blocks.GRASS));
        mapColors.add(mapColor("sand", Blocks.SAND));
        mapColors.add(mapColor("cloth", Blocks.WOOL));
        mapColors.add(mapColor("tnt", Blocks.TNT));
        mapColors.add(mapColor("ice", Blocks.ICE));
        mapColors.add(mapColor("iron", Blocks.IRON_BLOCK));
        mapColors.add(mapColor("foliage", Blocks.LEAVES));
        mapColors.add(mapColor("snow", Blocks.SNOW));
        mapColors.add(mapColor("clay", Blocks.CLAY));
        mapColors.add(mapColor("dirt", Blocks.DIRT));
        mapColors.add(mapColor("stone", Blocks.STONE));
        mapColors.add(mapColor("water", Blocks.WATER));
        mapColors.add(mapColor("wood", Blocks.LOG));
        mapColors.add(mapColor("quartz", Blocks.QUARTZ_BLOCK));
        mapColors.add(mapColor("adobe", Blocks.WOOL, 1));
        mapColors.add(mapColor("magenta", Blocks.WOOL, 2));
        mapColors.add(mapColor("light_blue", Blocks.WOOL, 3));
        mapColors.add(mapColor("yellow", Blocks.WOOL, 4));
        mapColors.add(mapColor("lime", Blocks.WOOL, 5));
        mapColors.add(mapColor("pink", Blocks.WOOL, 6));
        mapColors.add(mapColor("gray", Blocks.WOOL, 7));
        mapColors.add(mapColor("silver", Blocks.WOOL, 8));
        mapColors.add(mapColor("cyan", Blocks.WOOL, 9));
        mapColors.add(mapColor("purple", Blocks.WOOL, 10));
        mapColors.add(mapColor("blue", Blocks.WOOL, 11));
        mapColors.add(mapColor("brown", Blocks.WOOL, 12));
        mapColors.add(mapColor("green", Blocks.WOOL, 13));
        mapColors.add(mapColor("red", Blocks.WOOL, 14));
        mapColors.add(mapColor("black", Blocks.WOOL, 15));
        mapColors.add(mapColor("gold", Blocks.GOLD_BLOCK));
        mapColors.add(mapColor("diamond", Blocks.DIAMOND_BLOCK));
        mapColors.add(mapColor("lapis", Blocks.LAPIS_BLOCK));
        mapColors.add(mapColor("emerald", Blocks.EMERALD_BLOCK));
        mapColors.add(mapColor("obsidian", Blocks.OBSIDIAN));
        mapColors.add(mapColor("netherrack", Blocks.NETHERRACK));
        mapColors.add(mapColor("white_stained_hardened_clay", Blocks.HARDENED_CLAY));
        mapColors.add(mapColor("orange_stained_hardened_clay", Blocks.HARDENED_CLAY, 1));
        mapColors.add(mapColor("magenta_stained_hardened_clay", Blocks.HARDENED_CLAY, 2));
        mapColors.add(mapColor("light_blue_stained_hardened_clay", Blocks.HARDENED_CLAY, 3));
        mapColors.add(mapColor("yellow_stained_hardened_clay", Blocks.HARDENED_CLAY, 4));
        mapColors.add(mapColor("lime_stained_hardened_clay", Blocks.HARDENED_CLAY, 5));
        mapColors.add(mapColor("pink_stained_hardened_clay", Blocks.HARDENED_CLAY, 6));
        mapColors.add(mapColor("gray_stained_hardened_clay", Blocks.HARDENED_CLAY, 7));
        mapColors.add(mapColor("silver_stained_hardened_clay", Blocks.HARDENED_CLAY, 8));
        mapColors.add(mapColor("cyan_stained_hardened_clay", Blocks.HARDENED_CLAY, 9));
        mapColors.add(mapColor("purple_stained_hardened_clay", Blocks.HARDENED_CLAY, 10));
        mapColors.add(mapColor("blue_stained_hardened_clay", Blocks.HARDENED_CLAY, 11));
        mapColors.add(mapColor("brown_stained_hardened_clay", Blocks.HARDENED_CLAY, 12));
        mapColors.add(mapColor("green_stained_hardened_clay", Blocks.HARDENED_CLAY, 13));
        mapColors.add(mapColor("red_stained_hardened_clay", Blocks.HARDENED_CLAY, 14));
        mapColors.add(mapColor("black_stained_hardened_clay", Blocks.HARDENED_CLAY, 15));
        exporter.writeJson("content/" + exporter.getNamespace() + "/mapColor.json", mapColors);
    }

    @Override
    public void exportTranslation(Exporter exporter, Map<String, String> translations) {
        // Nothing to do.
    }

    private CPMapColor mapColor(String name, IForgeRegistryEntry<?> entry) {
        return mapColor(name, entry, 0);
    }

    private CPMapColor mapColor(String name, IForgeRegistryEntry<?> entry, int metadata) {
        return new CPMapColor("minecraft:" + name, "mapColor.minecraft." + name, ItemRef.createItem(entry.getRegistryName().toString(), metadata));
    }
}
