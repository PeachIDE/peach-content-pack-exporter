package com.github.mouse0w0.pcpe.generator.vanilla;

import com.github.mouse0w0.pcpe.Exporter;
import com.github.mouse0w0.pcpe.data.IconicData;
import com.github.mouse0w0.pcpe.data.IdMetadata;
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
        List<IconicData> mapColors = new ArrayList<>();
        mapColors.add(mapColor("AIR", Blocks.AIR));
        mapColors.add(mapColor("GRASS", Blocks.GRASS));
        mapColors.add(mapColor("SAND", Blocks.SAND));
        mapColors.add(mapColor("CLOTH", Blocks.WOOL));
        mapColors.add(mapColor("TNT", Blocks.TNT));
        mapColors.add(mapColor("ICE", Blocks.ICE));
        mapColors.add(mapColor("IRON", Blocks.IRON_BLOCK));
        mapColors.add(mapColor("FOLIAGE", Blocks.LEAVES));
        mapColors.add(mapColor("SNOW", Blocks.SNOW));
        mapColors.add(mapColor("CLAY", Blocks.CLAY));
        mapColors.add(mapColor("DIRT", Blocks.DIRT));
        mapColors.add(mapColor("STONE", Blocks.STONE));
        mapColors.add(mapColor("WATER", Blocks.WATER));
        mapColors.add(mapColor("WOOD", Blocks.LOG));
        mapColors.add(mapColor("QUARTZ", Blocks.QUARTZ_BLOCK));
        mapColors.add(mapColor("ADOBE", Blocks.WOOL, 1));
        mapColors.add(mapColor("MAGENTA", Blocks.WOOL, 2));
        mapColors.add(mapColor("LIGHT_BLUE", Blocks.WOOL, 3));
        mapColors.add(mapColor("YELLOW", Blocks.WOOL, 4));
        mapColors.add(mapColor("LIME", Blocks.WOOL, 5));
        mapColors.add(mapColor("PINK", Blocks.WOOL, 6));
        mapColors.add(mapColor("GRAY", Blocks.WOOL, 7));
        mapColors.add(mapColor("SILVER", Blocks.WOOL, 8));
        mapColors.add(mapColor("CYAN", Blocks.WOOL, 9));
        mapColors.add(mapColor("PURPLE", Blocks.WOOL, 10));
        mapColors.add(mapColor("BLUE", Blocks.WOOL, 11));
        mapColors.add(mapColor("BROWN", Blocks.WOOL, 12));
        mapColors.add(mapColor("GREEN", Blocks.WOOL, 13));
        mapColors.add(mapColor("RED", Blocks.WOOL, 14));
        mapColors.add(mapColor("BLACK", Blocks.WOOL, 15));
        mapColors.add(mapColor("GOLD", Blocks.GOLD_BLOCK));
        mapColors.add(mapColor("DIAMOND", Blocks.DIAMOND_BLOCK));
        mapColors.add(mapColor("LAPIS", Blocks.LAPIS_BLOCK));
        mapColors.add(mapColor("EMERALD", Blocks.EMERALD_BLOCK));
        mapColors.add(mapColor("OBSIDIAN", Blocks.OBSIDIAN));
        mapColors.add(mapColor("NETHERRACK", Blocks.NETHERRACK));
        mapColors.add(mapColor("WHITE_STAINED_HARDENED_CLAY", Blocks.STAINED_HARDENED_CLAY));
        mapColors.add(mapColor("ORANGE_STAINED_HARDENED_CLAY", Blocks.STAINED_HARDENED_CLAY, 1));
        mapColors.add(mapColor("MAGENTA_STAINED_HARDENED_CLAY", Blocks.STAINED_HARDENED_CLAY, 2));
        mapColors.add(mapColor("LIGHT_BLUE_STAINED_HARDENED_CLAY", Blocks.STAINED_HARDENED_CLAY, 3));
        mapColors.add(mapColor("YELLOW_STAINED_HARDENED_CLAY", Blocks.STAINED_HARDENED_CLAY, 4));
        mapColors.add(mapColor("LIME_STAINED_HARDENED_CLAY", Blocks.STAINED_HARDENED_CLAY, 5));
        mapColors.add(mapColor("PINK_STAINED_HARDENED_CLAY", Blocks.STAINED_HARDENED_CLAY, 6));
        mapColors.add(mapColor("GRAY_STAINED_HARDENED_CLAY", Blocks.STAINED_HARDENED_CLAY, 7));
        mapColors.add(mapColor("SILVER_STAINED_HARDENED_CLAY", Blocks.STAINED_HARDENED_CLAY, 8));
        mapColors.add(mapColor("CYAN_STAINED_HARDENED_CLAY", Blocks.STAINED_HARDENED_CLAY, 9));
        mapColors.add(mapColor("PURPLE_STAINED_HARDENED_CLAY", Blocks.STAINED_HARDENED_CLAY, 10));
        mapColors.add(mapColor("BLUE_STAINED_HARDENED_CLAY", Blocks.STAINED_HARDENED_CLAY, 11));
        mapColors.add(mapColor("BROWN_STAINED_HARDENED_CLAY", Blocks.STAINED_HARDENED_CLAY, 12));
        mapColors.add(mapColor("GREEN_STAINED_HARDENED_CLAY", Blocks.STAINED_HARDENED_CLAY, 13));
        mapColors.add(mapColor("RED_STAINED_HARDENED_CLAY", Blocks.STAINED_HARDENED_CLAY, 14));
        mapColors.add(mapColor("BLACK_STAINED_HARDENED_CLAY", Blocks.STAINED_HARDENED_CLAY, 15));
        exporter.writeJson("data/map_color.json", mapColors);
    }

    @Override
    public void exportTranslation(Exporter exporter, Map<String, String> translations) {
        // Nothing to do.
    }

    private IconicData mapColor(String name, IForgeRegistryEntry<?> entry) {
        return mapColor(name, entry, 0);
    }

    private IconicData mapColor(String name, IForgeRegistryEntry<?> entry, int metadata) {
        return new IconicData(name, IdMetadata.of(entry.getRegistryName().toString(), metadata));
    }
}
