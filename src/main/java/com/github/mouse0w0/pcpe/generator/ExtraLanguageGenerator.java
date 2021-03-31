package com.github.mouse0w0.pcpe.generator;

import com.github.mouse0w0.pcpe.Exporter;
import net.minecraft.client.Minecraft;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Properties;

public class ExtraLanguageGenerator implements DataGenerator {
    @Override
    public void collectData(Exporter exporter) {
        // Nothing to do.
    }

    @Override
    public void exportData(Exporter exporter) {
        // Nothing to do.
    }

    @Override
    public void exportTranslation(Exporter exporter, Map<String, String> translations) {
        String language = Minecraft.getMinecraft().gameSettings.language;
        String namespace = exporter.getNamespace();

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL url = classLoader.getResource("assets/pcpe/extra/" + namespace + "/lang/" + language + ".lang");
        if (url == null) return;

        exporter.getLogger().info("Found extra language file: {}.", language);

        Properties loadedTranslations = new Properties();
        try (Reader reader = new InputStreamReader(url.openStream(), StandardCharsets.UTF_8)) {
            loadedTranslations.load(reader);
        } catch (IOException e) {
            exporter.getLogger().error("Cannot load extra language file.", e);
            return;
        }

        loadedTranslations.forEach((k, v) -> translations.put((String) k, (String) v));
    }
}
