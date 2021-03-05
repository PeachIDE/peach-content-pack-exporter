package com.github.mouse0w0.pcpe.generator.vanilla;

import com.github.mouse0w0.pcpe.Exporter;
import com.github.mouse0w0.pcpe.data.CPMaterial;
import com.github.mouse0w0.pcpe.data.ItemRef;
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
        List<CPMaterial> materials = new ArrayList<>();
        materials.add(material("air", Blocks.AIR));
        materials.add(material("grass", Blocks.GRASS));
        materials.add(material("ground", Blocks.DIRT));
        materials.add(material("wood", Blocks.LOG));
        materials.add(material("rock", Blocks.STONE));
        materials.add(material("iron", Blocks.IRON_BLOCK));
        materials.add(material("anvil", Blocks.ANVIL));
        materials.add(material("water", Blocks.WATER));
        materials.add(material("lava", Blocks.LAVA));
        materials.add(material("leaves", Blocks.LEAVES));
        materials.add(material("plants", Blocks.PLANKS));
        materials.add(material("vine", Blocks.VINE));
        materials.add(material("sponge", Blocks.SPONGE));
        materials.add(material("cloth", Blocks.WOOL));
        materials.add(material("fire", Blocks.FIRE));
        materials.add(material("sand", Blocks.SAND));
        materials.add(material("circuits", Items.REDSTONE));
        materials.add(material("carpet", Blocks.CARPET));
        materials.add(material("glass", Blocks.GLASS));
        materials.add(material("redstone_light", Blocks.REDSTONE_LAMP));
        materials.add(material("tnt", Blocks.TNT));
        materials.add(material("coral", Blocks.AIR));
        materials.add(material("ice", Blocks.ICE));
        materials.add(material("packed_ice", Blocks.PACKED_ICE));
        materials.add(material("snow", Blocks.SNOW_LAYER));
        materials.add(material("crafted_snow", Blocks.SNOW));
        materials.add(material("cactus", Blocks.CACTUS));
        materials.add(material("clay", Blocks.CLAY));
        materials.add(material("gourd", Blocks.MELON_BLOCK));
        materials.add(material("dragon_egg", Blocks.DRAGON_EGG));
        materials.add(material("portal", Blocks.PORTAL));
        materials.add(material("cake", Blocks.CAKE));
        materials.add(material("web", Blocks.WEB));
        materials.add(material("piston", Blocks.PISTON));
        materials.add(material("barrier", Blocks.BARRIER));
        materials.add(material("structure_void", Blocks.STRUCTURE_VOID));
        exporter.writeJson("content/" + exporter.getNamespace() + "/material.json", materials);
    }

    @Override
    public void exportTranslation(Exporter exporter, Map<String, String> translations) {
        // Nothing to do.
    }

    private CPMaterial material(String name, IForgeRegistryEntry<?> entry) {
        return material(name, entry, 0);
    }

    private CPMaterial material(String name, IForgeRegistryEntry<?> entry, int metadata) {
        return new CPMaterial("minecraft:" + name, "material.minecraft." + name, ItemRef.createItem(entry.getRegistryName().toString(), metadata));
    }
}
