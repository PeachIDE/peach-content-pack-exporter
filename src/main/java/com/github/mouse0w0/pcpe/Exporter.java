package com.github.mouse0w0.pcpe;

import com.github.mouse0w0.pcpe.data.CPMetadata;
import com.github.mouse0w0.pcpe.generator.*;
import com.github.mouse0w0.pcpe.generator.vanilla.VanillaMapColorGenerator;
import com.github.mouse0w0.pcpe.generator.vanilla.VanillaMaterialGenerator;
import com.github.mouse0w0.pcpe.generator.vanilla.VanillaSoundTypeGenerator;
import com.github.mouse0w0.pcpe.util.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.Language;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.resource.VanillaResourceType;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.registries.IForgeRegistryEntry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Exporter implements Runnable {

    private final Logger logger = LogManager.getLogger(PCPE.MOD_ID);

    private final String namespace;
    private final ModContainer modContainer;
    private final Path output;
    private final Path outputFile;

    private final List<DataGenerator> generators = new ArrayList<>();

    public Exporter(String namespace, Path outputFile) {
        this.namespace = namespace;
        this.modContainer = Loader.instance().getIndexedModList().get(namespace);
        this.outputFile = outputFile;
        this.output = Paths.get(".export", namespace).toAbsolutePath();

        generators.add(new ItemGenerator());
        generators.add(new ItemGroupGenerator());
        generators.add(new OreDictGenerator());
        generators.add(new EnchantmentGenerator());
        generators.add(new SoundEventGenerator());

        if (namespace.equals("minecraft")) {
            generators.add(new VanillaMaterialGenerator());
            generators.add(new VanillaSoundTypeGenerator());
            generators.add(new VanillaMapColorGenerator());
        }

        generators.add(new ExtraLanguageGenerator());
    }

    public Logger getLogger() {
        return logger;
    }

    public String getNamespace() {
        return namespace;
    }

    public boolean checkNamespace(IForgeRegistryEntry<?> registryEntry) {
        return namespace.equals(registryEntry.getRegistryName().getNamespace());
    }

    public boolean checkNamespace(ResourceLocation location) {
        return namespace.equals(location.getNamespace());
    }

    public Path getOutput() {
        return output;
    }

    public void writeJson(String path, Object value) {
        Path file = getOutput().resolve(path);
        try {
            JsonUtils.writeJson(file, value);
        } catch (IOException e) {
            logger.error("Caught an exception when write to " + file, e);
        }
    }

    public void run() {
        try {
            doExport();
        } catch (Exception e) {
            logger.error("Failed to export: " + namespace, e);
        }
    }

    private void doExport() throws Exception {
        logger.info("Exporting content pack from namespace: " + namespace);
        if (Files.exists(output)) {
            FileUtils.deleteDirectory(output.toFile());
        }
        Files.createDirectories(output);

        logger.info("Exporting metadata...");
        exportMetadata();

        logger.info("Collecting data...");
        for (DataGenerator generator : generators) {
            generator.collectData(this);
        }

        logger.info("Exporting data...");
        for (DataGenerator generator : generators) {
            generator.exportData(this);
        }

        String oldLanguage = MinecraftUtils.getLanguage();
        exportTranslation("zh_cn");
        exportTranslation("en_us");
        setLanguage(oldLanguage);

        logger.info("Zipping content pack...");
        ZipUtils.zip(outputFile, output);

        logger.info("Exported content pack to " + outputFile);
    }

    private void exportMetadata() throws IOException {
        CPMetadata metadata = new CPMetadata();
        metadata.setId(modContainer.getModId());
        metadata.setName(modContainer.getName());
        metadata.setVersion(modContainer.getVersion());
        metadata.setMcVersion(Loader.MC_VERSION);
        metadata.setDescription(modContainer.getMetadata().description);
        metadata.setUrl(modContainer.getMetadata().url);
        metadata.setAuthors(modContainer.getMetadata().authorList);

        Map<String, Object> descriptor = ReflectionUtils.getDeclaredValue(modContainer, "descriptor");
        if (descriptor != null) {
            metadata.setDependencies((String) descriptor.get("dependencies"));
        }

        JsonUtils.writeJson(getOutput().resolve("content.metadata.json"), metadata);
    }

    private void exportTranslation(String language) throws IOException {
        System.out.println("Exporting translation: " + language);
        setLanguage(language);
        Map<String, String> map = new LinkedHashMap<>();

        for (DataGenerator generator : generators) {
            generator.exportTranslation(this, map);
        }

        Path file = getOutput().resolve("content/" + namespace + "/lang/" + language.toLowerCase() + ".lang");
        FileUtils.createFileIfNotExists(file);
        try (BufferedWriter bw = Files.newBufferedWriter(file)) {
            bw.write("#Generated by Peach Content Pack Exporter");
            bw.newLine();
            bw.write("#" + new Date().toString());
            bw.newLine();
            for (String key : map.keySet()) {
                String val = map.get(key);
                key = saveConvert(key, true, false);
                val = saveConvert(val, false, false);
                bw.write(key + "=" + val);
                bw.newLine();
            }
            bw.flush();
        }
    }

    private static void setLanguage(String language) {
        Minecraft minecraft = Minecraft.getMinecraft();

        if (minecraft.gameSettings.language.equals(language)) return;

        minecraft.gameSettings.language = language;
        minecraft.getLanguageManager().setCurrentLanguage(new Language(language, "", "", false));
        FMLClientHandler.instance().refreshResources(VanillaResourceType.LANGUAGES);
    }

    private static String saveConvert(String theString,
                                      boolean escapeSpace,
                                      boolean escapeUnicode) {
        int len = theString.length();
        int bufLen = len * 2;
        if (bufLen < 0) {
            bufLen = Integer.MAX_VALUE;
        }
        StringBuffer outBuffer = new StringBuffer(bufLen);

        for (int x = 0; x < len; x++) {
            char aChar = theString.charAt(x);
            // Handle common case first, selecting largest block that
            // avoids the specials below
            if ((aChar > 61) && (aChar < 127)) {
                if (aChar == '\\') {
                    outBuffer.append('\\');
                    outBuffer.append('\\');
                    continue;
                }
                outBuffer.append(aChar);
                continue;
            }
            switch (aChar) {
                case ' ':
                    if (x == 0 || escapeSpace)
                        outBuffer.append('\\');
                    outBuffer.append(' ');
                    break;
                case '\t':
                    outBuffer.append('\\');
                    outBuffer.append('t');
                    break;
                case '\n':
                    outBuffer.append('\\');
                    outBuffer.append('n');
                    break;
                case '\r':
                    outBuffer.append('\\');
                    outBuffer.append('r');
                    break;
                case '\f':
                    outBuffer.append('\\');
                    outBuffer.append('f');
                    break;
                case '=': // Fall through
                case ':': // Fall through
                case '#': // Fall through
                case '!':
                    outBuffer.append('\\');
                    outBuffer.append(aChar);
                    break;
                default:
                    if (((aChar < 0x0020) || (aChar > 0x007e)) & escapeUnicode) {
                        outBuffer.append('\\');
                        outBuffer.append('u');
                        outBuffer.append(toHex((aChar >> 12) & 0xF));
                        outBuffer.append(toHex((aChar >> 8) & 0xF));
                        outBuffer.append(toHex((aChar >> 4) & 0xF));
                        outBuffer.append(toHex(aChar & 0xF));
                    } else {
                        outBuffer.append(aChar);
                    }
            }
        }
        return outBuffer.toString();
    }

    private static char toHex(int nibble) {
        return hexDigit[(nibble & 0xF)];
    }

    private static final char[] hexDigit = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
    };
}
