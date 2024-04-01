package com.github.mouse0w0.pcpe.generator;

import com.github.mouse0w0.pcpe.Exporter;
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
        exporter.writeJson("data/sound_event.json",
                data.stream().map(soundEvent -> getRegistryName(soundEvent).toString())
                        .collect(Collectors.toList()));
    }

    @Override
    public void exportTranslation(Exporter exporter, Map<String, String> translations) {
        for (SoundEvent soundEvent : data) {
            String registryPath = getRegistryName(soundEvent).getPath();
            String translationKey = "subtitles." + registryPath;
            if (I18n.hasKey(translationKey)) {
                translations.put(getTranslationKey(soundEvent), I18n.format(translationKey));
            } else {
                int lastIndex = registryPath.lastIndexOf('.');
                int secondLastIndex = registryPath.lastIndexOf('.', lastIndex - 1);
                String objectName = capitalize(registryPath.substring(secondLastIndex + 1, lastIndex).replace('_', ' '));
                String behaviorName = registryPath.substring(lastIndex + 1).replace('_', ' ');
                translations.put(getTranslationKey(soundEvent), objectName + " " + behaviorName);
            }
        }
    }

    private static String capitalize(String s) {
        StringBuilder sb = new StringBuilder(s);
        sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
        return sb.toString();
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
