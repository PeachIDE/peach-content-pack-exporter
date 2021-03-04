package com.github.mouse0w0.pcpe.generator;

import com.github.mouse0w0.pcpe.Exporter;

import java.util.Map;

public interface DataGenerator {
    void collectData(Exporter exporter);

    void exportData(Exporter exporter);

    void exportTranslation(Exporter exporter, Map<String, String> translations);
}
