package com.github.mouse0w0.pcpe.generator;

import com.github.mouse0w0.pcpe.Exporter;
import com.github.mouse0w0.pcpe.data.SoundEventData;
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
        String namespace = exporter.getNamespace();
        data = new ArrayList<>();
        for (SoundEvent soundEvent : SoundEvent.REGISTRY) {
            if (namespace.equals(getRegistryName(soundEvent).getResourceDomain())) {
                data.add(soundEvent);
            }
        }
    }

    @Override
    public void exportData(Exporter exporter) {
        String namespace = exporter.getNamespace();
        exporter.writeJson("content/" + namespace + "/sound.json",
                data.stream().map(soundEvent ->
                        new SoundEventData(getRegistryName(soundEvent).toString(), getTranslationKey(soundEvent)))
                        .collect(Collectors.toList()));
    }

    @Override
    public void exportL10n(Exporter exporter, Map<String, String> map) {
        data.forEach(soundEvent -> map.put(getTranslationKey(soundEvent), I18n.format("subtitles." + getRegistryName(soundEvent).getResourcePath())));
    }

    private static ResourceLocation getRegistryName(SoundEvent soundEvent) {
        ResourceLocation registryName = soundEvent.getRegistryName();
        return registryName != null ? registryName : soundEvent.getSoundName();
    }


    private static String getTranslationKey(SoundEvent soundEvent) {
        ResourceLocation location = getRegistryName(soundEvent);
        return location.getResourceDomain() + ".soundEvent." + location.getResourcePath();
    }
}
