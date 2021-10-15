package com.github.mouse0w0.pcpe.generator;

import com.github.mouse0w0.pcpe.Exporter;
import com.github.mouse0w0.pcpe.data.CPSoundEvent;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SoundEventGenerator implements DataGenerator {
    private List<SoundEvent> data;

    @Override
    public void collectData(Exporter exporter) {
        data = new ArrayList<>();
        for (SoundEvent soundEvent : SoundEvent.REGISTRY) {
            if (exporter.checkNamespace(getRegistryName(soundEvent))) {
                data.add(soundEvent);
            }
        }
    }

    @Override
    public void exportData(Exporter exporter) {
        String namespace = exporter.getNamespace();
        exporter.writeJson("content/" + namespace + "/sound.json",
                data.stream().map(soundEvent ->
                        new CPSoundEvent(getRegistryName(soundEvent).toString(), getTranslationKey(soundEvent)))
                        .collect(Collectors.toList()));
    }

    @Override
    public void exportTranslation(Exporter exporter, Map<String, String> translations) {
        for (SoundEvent soundEvent : data) {
            String translationKey = "subtitles." + getRegistryName(soundEvent).getPath();
            if (I18n.hasKey(translationKey)) {
                translations.put(getTranslationKey(soundEvent), I18n.format(translationKey));
            }
        }
    }

    private static ResourceLocation getRegistryName(SoundEvent soundEvent) {
        ResourceLocation registryName = soundEvent.getRegistryName();
        return registryName != null ? registryName : soundEvent.getSoundName();
    }


    private static String getTranslationKey(SoundEvent soundEvent) {
        ResourceLocation location = getRegistryName(soundEvent);
        return "soundEvent." + location.getNamespace() + "." + location.getPath();
    }
}
