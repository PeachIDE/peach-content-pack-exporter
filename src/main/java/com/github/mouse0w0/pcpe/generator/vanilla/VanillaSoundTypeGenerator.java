package com.github.mouse0w0.pcpe.generator.vanilla;

import com.github.mouse0w0.pcpe.Exporter;
import com.github.mouse0w0.pcpe.data.CPSoundType;
import com.github.mouse0w0.pcpe.data.ItemRef;
import com.github.mouse0w0.pcpe.generator.DataGenerator;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class VanillaSoundTypeGenerator implements DataGenerator {
    @Override
    public void collectData(Exporter exporter) {
        // Nothing to do.
    }

    /**
     * @see net.minecraft.block.SoundType
     */
    @Override
    public void exportData(Exporter exporter) {
        List<CPSoundType> soundTypes = new ArrayList<>();
        soundTypes.add(soundType("wood", Blocks.LOG));
        soundTypes.add(soundType("ground", Blocks.DIRT));
        soundTypes.add(soundType("plant", Blocks.GRASS));
        soundTypes.add(soundType("stone", Blocks.STONE));
        soundTypes.add(soundType("metal", Blocks.IRON_BLOCK));
        soundTypes.add(soundType("glass", Blocks.GLASS));
        soundTypes.add(soundType("cloth", Blocks.WOOL));
        soundTypes.add(soundType("sand", Blocks.SAND));
        soundTypes.add(soundType("snow", Blocks.SNOW));
        soundTypes.add(soundType("ladder", Blocks.LADDER));
        soundTypes.add(soundType("anvil", Blocks.ANVIL));
        soundTypes.add(soundType("slime", Blocks.SLIME_BLOCK));
        exporter.writeJson("content/" + exporter.getNamespace() + "/soundType.json", soundTypes);
    }

    @Override
    public void exportTranslation(Exporter exporter, Map<String, String> translations) {
        // Nothing to do.
    }

    private CPSoundType soundType(String name, Block block) {
        return soundType(name, block, 0);
    }

    private CPSoundType soundType(String name, Block block, int metadata) {
        return new CPSoundType("minecraft:" + name, "soundType.minecraft." + name, ItemRef.createItem(block.getRegistryName().toString(), metadata));
    }
}
