package com.github.mouse0w0.pcpe.generator.vanilla;

import com.github.mouse0w0.pcpe.Exporter;
import com.github.mouse0w0.pcpe.data.IconicData;
import com.github.mouse0w0.pcpe.data.IdMetadata;
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
        List<IconicData> soundTypes = new ArrayList<>();
        soundTypes.add(soundType("WOOD", Blocks.LOG));
        soundTypes.add(soundType("GROUND", Blocks.DIRT));
        soundTypes.add(soundType("PLANT", Blocks.GRASS));
        soundTypes.add(soundType("STONE", Blocks.STONE));
        soundTypes.add(soundType("METAL", Blocks.IRON_BLOCK));
        soundTypes.add(soundType("GLASS", Blocks.GLASS));
        soundTypes.add(soundType("CLOTH", Blocks.WOOL));
        soundTypes.add(soundType("SAND", Blocks.SAND));
        soundTypes.add(soundType("SNOW", Blocks.SNOW));
        soundTypes.add(soundType("LADDER", Blocks.LADDER));
        soundTypes.add(soundType("ANVIL", Blocks.ANVIL));
        soundTypes.add(soundType("SLIME", Blocks.SLIME_BLOCK));
        exporter.writeJson("data/sound_type.json", soundTypes);
    }

    @Override
    public void exportTranslation(Exporter exporter, Map<String, String> translations) {
        // Nothing to do.
    }

    private IconicData soundType(String name, Block block) {
        return soundType(name, block, 0);
    }

    private IconicData soundType(String name, Block block, int metadata) {
        return new IconicData(name, IdMetadata.of(block.getRegistryName().toString(), metadata));
    }
}
