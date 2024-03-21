package com.github.mouse0w0.pcpe.generator.vanilla;

import com.github.mouse0w0.pcpe.Exporter;
import com.github.mouse0w0.pcpe.data.IconicData;
import com.github.mouse0w0.pcpe.data.IdMetadata;
import com.github.mouse0w0.pcpe.generator.DataGenerator;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class VanillaMaterialGenerator implements DataGenerator {
    @Override
    public void collectData(Exporter exporter) {
        // Nothing to do.
    }

    /**
     * @see net.minecraft.block.material.Material
     */
    @Override
    public void exportData(Exporter exporter) {
        List<IconicData> materials = new ArrayList<>();
        materials.add(material("AIR", Blocks.AIR));
        materials.add(material("GRASS", Blocks.GRASS));
        materials.add(material("GROUND", Blocks.DIRT));
        materials.add(material("WOOD", Blocks.LOG));
        materials.add(material("ROCK", Blocks.STONE));
        materials.add(material("IRON", Blocks.IRON_BLOCK));
        materials.add(material("ANVIL", Blocks.ANVIL));
        materials.add(material("WATER", Blocks.WATER));
        materials.add(material("LAVA", Blocks.LAVA));
        materials.add(material("LEAVES", Blocks.LEAVES));
        materials.add(material("PLANKS", Blocks.PLANKS));
        materials.add(material("VINE", Blocks.VINE));
        materials.add(material("SPONGE", Blocks.SPONGE));
        materials.add(material("CLOTH", Blocks.WOOL));
        materials.add(material("FIRE", Blocks.FIRE));
        materials.add(material("SAND", Blocks.SAND));
        materials.add(material("CIRCUITS", Items.REDSTONE));
        materials.add(material("CARPET", Blocks.CARPET));
        materials.add(material("GLASS", Blocks.GLASS));
        materials.add(material("REDSTONE_LIGHT", Blocks.REDSTONE_LAMP));
        materials.add(material("TNT", Blocks.TNT));
        materials.add(material("CORAL", Blocks.AIR));
        materials.add(material("ICE", Blocks.ICE));
        materials.add(material("PACKED_ICE", Blocks.PACKED_ICE));
        materials.add(material("SNOW", Blocks.SNOW_LAYER));
        materials.add(material("CRAFTED_SNOW", Blocks.SNOW));
        materials.add(material("CACTUS", Blocks.CACTUS));
        materials.add(material("CLAY", Blocks.CLAY));
        materials.add(material("GOURD", Blocks.MELON_BLOCK));
        materials.add(material("DRAGON_EGG", Blocks.DRAGON_EGG));
        materials.add(material("PORTAL", Blocks.PORTAL));
        materials.add(material("CAKE", Blocks.CAKE));
        materials.add(material("WEB", Blocks.WEB));
        materials.add(material("PISTON", Blocks.PISTON));
        materials.add(material("BARRIER", Blocks.BARRIER));
        materials.add(material("STRUCTURE_VOID", Blocks.STRUCTURE_VOID));
        exporter.writeJson("data/material.json", materials);
    }

    @Override
    public void exportTranslation(Exporter exporter, Map<String, String> translations) {
        // Nothing to do.
    }

    private IconicData material(String name, IForgeRegistryEntry<?> entry) {
        return material(name, entry, 0);
    }

    private IconicData material(String name, IForgeRegistryEntry<?> entry, int metadata) {
        return new IconicData(name, IdMetadata.of(entry.getRegistryName().toString(), metadata));
    }
}
