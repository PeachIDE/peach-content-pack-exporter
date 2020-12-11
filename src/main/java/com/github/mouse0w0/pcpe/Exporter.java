package com.github.mouse0w0.pcpe;

import com.github.mouse0w0.pcpe.data.*;
import com.github.mouse0w0.pcpe.renderer.FrameBuffer;
import com.github.mouse0w0.pcpe.renderer.Renderer;
import com.github.mouse0w0.pcpe.util.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.Language;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.client.resource.VanillaResourceType;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.oredict.OreDictionary;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Exporter implements Runnable {

    private final String namespace;
    private final ModContainer modContainer;
    private final Path output;
    private final Path outputFile;

    private final List<ItemStack> collectedItems;

    public Exporter(String namespace, Path outputFile) {
        this.namespace = namespace;
        this.modContainer = Loader.instance().getIndexedModList().get(namespace);
        this.outputFile = outputFile;
        this.output = Paths.get(".export", namespace).toAbsolutePath();

        this.collectedItems = collectItems();
    }

    public Path getOutput() {
        return output;
    }

    public void run() {
        try {
            doExport();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doExport() throws Exception {
        System.out.println("Exporting content from namespace: " + namespace);
        FileUtils.createDirectoriesIfNotExists(output);

        exportMetadata();
        exportItems();
        exportItemGroups();
        exportOreDictionary();
        exportLanguage("zh_cn");
        exportLanguage("en_us");

        ZipUtils.zip(outputFile, output);
        System.out.println("Completed export content to " + outputFile);
    }

    private void exportMetadata() throws IOException {
        System.out.println("Exporting content metadata...");

        ContentPackMetadata metadata = new ContentPackMetadata();
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

    private void exportItems() throws IOException {
        System.out.println("Exporting items...");
        FrameBuffer frameBuffer = new FrameBuffer(64, 64);
        Renderer.getInstance().startRenderItem(frameBuffer);
        Set<ItemData> itemDataList = new LinkedHashSet<>();
        for (ItemStack itemStack : collectedItems) {
            Item item = itemStack.getItem();
            if (!namespace.equals(item.getRegistryName().getResourceDomain())) continue;
            if (!itemDataList.add(new ItemData(
                    item.getRegistryName().toString(),
                    itemStack.getMetadata(),
                    getTranslationKey(itemStack),
                    item instanceof ItemBlock))) continue;
            Renderer.getInstance().renderItemToPNG(frameBuffer, itemStack, getOutput().resolve("content/" + namespace + "/image/item/" + item.getRegistryName().getResourcePath() + "_" + itemStack.getMetadata() + ".png"));
        }
        JsonUtils.writeJson(getOutput().resolve("content/" + namespace + "/item.json"), itemDataList);
        Renderer.getInstance().endRenderItem(frameBuffer);
    }

    private void exportItemGroups() throws IOException {
        System.out.println("Exporting item groups...");
        List<ItemGroupData> itemGroupList = new ArrayList<>();
        for (CreativeTabs itemGroup : CreativeTabs.CREATIVE_TAB_ARRAY) {
            if (ignoredItemGroups.contains(itemGroup.getTabLabel())) continue;
            ItemStack icon = itemGroup.getIconItemStack();

            if (!namespace.equals(icon.getItem().getRegistryName().getResourceDomain())) continue;
            itemGroupList.add(new ItemGroupData(itemGroup.getTabLabel(),
                    getTranslationKey(itemGroup),
                    ItemRef.createItem(icon.getItem().getRegistryName().toString(), icon.getMetadata())));
        }
        JsonUtils.writeJson(getOutput().resolve("content/" + namespace + "/itemGroup.json"), itemGroupList);
    }

    private void exportOreDictionary() throws IOException {
        System.out.println("Exporting ore dictionary...");
        List<OreDictData> oreDictDataList = new ArrayList<>();
        for (String oreName : OreDictionary.getOreNames()) {
            List<ItemRef> entries = OreDictionary.getOres(oreName).parallelStream()
                    .filter(itemStack -> namespace.equals(itemStack.getItem().getRegistryName().getResourceDomain()))
                    .map(itemStack -> ItemRef.createItem(itemStack.getItem().getRegistryName().toString(), itemStack.getMetadata()))
                    .collect(Collectors.toList());
            if (entries.isEmpty()) continue;
            oreDictDataList.add(new OreDictData(oreName, entries));
        }
        JsonUtils.writeJson(getOutput().resolve("content/" + namespace + "/oreDictionary.json"), oreDictDataList);
    }

    private void exportLanguage(String language) throws IOException {
        System.out.println("Exporting language: " + language);
        String oldLanguage = MinecraftUtils.getLanguage();
        refreshLanguage(language);
        Properties properties = new Properties();

        for (ItemStack itemStack : collectedItems) {
            Item item = itemStack.getItem();
            if (!namespace.equals(item.getRegistryName().getResourceDomain())) continue;
            properties.setProperty(getTranslationKey(itemStack), item.getItemStackDisplayName(itemStack));
        }

        for (CreativeTabs creativeTabs : CreativeTabs.CREATIVE_TAB_ARRAY) {
            if (ignoredItemGroups.contains(creativeTabs.getTabLabel())) continue;
            ItemStack icon = creativeTabs.getIconItemStack();

            if (!namespace.equals(icon.getItem().getRegistryName().getResourceDomain())) continue;
            properties.setProperty(getTranslationKey(creativeTabs), I18n.format(creativeTabs.getTranslatedTabLabel()));
        }

        Path languageFile = getOutput().resolve("content/" + namespace + "/lang/" + language.toLowerCase() + ".lang");
        FileUtils.createFileIfNotExists(languageFile);
        try (BufferedWriter writer = Files.newBufferedWriter(languageFile)) {
            properties.store(writer, "Generated by MinecraftContentExporter");
        }
        refreshLanguage(oldLanguage);
    }

    private static void refreshLanguage(String locale) {
        Minecraft minecraft = Minecraft.getMinecraft();

        if (minecraft.gameSettings.language.equals(locale)) return;

        minecraft.gameSettings.language = locale;
        minecraft.getLanguageManager().setCurrentLanguage(new Language(locale, "", "", false));
        FMLClientHandler.instance().refreshResources(VanillaResourceType.LANGUAGES);
    }

    private static final Set<String> ignoredItemGroups = new HashSet<>(Arrays.asList("search", "inventory", "hotbar"));

    private String getTranslationKey(ItemStack item) {
        return namespace + ".item." + item.getItem().getRegistryName().getResourcePath() + "_" + item.getMetadata();
    }

    private String getTranslationKey(CreativeTabs creativeTabs) {
        return namespace + ".itemGroup." + creativeTabs.getTabLabel();
    }

    private List<ItemStack> collectItems() {
        final IBakedModel missingModel = MinecraftUtils.getModelManager().getMissingModel();
        final ItemModelMesher itemModelMesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();

        List<ItemStack> itemStacks = new ArrayList<>();

        NonNullList<ItemStack> foundCreativeTabItems = NonNullList.create();
        Set<String> duplicateNames = new HashSet<>();
        for (Item item : Item.REGISTRY) {
            if (item.getHasSubtypes()) {
                foundCreativeTabItems.clear();

                for (CreativeTabs creativeTabs : item.getCreativeTabs()) {
                    item.getSubItems(creativeTabs, foundCreativeTabItems);
                }
                if (!foundCreativeTabItems.isEmpty()) {
                    itemStacks.addAll(foundCreativeTabItems);
                } else {
                    duplicateNames.clear();
                    for (int metadata = 0; metadata < Short.MAX_VALUE; metadata++) {
                        ItemStack itemStack = new ItemStack(item, 1, metadata);
                        IBakedModel model = itemModelMesher.getItemModel(itemStack);
                        if (model == missingModel) break;
                        if (!duplicateNames.add(getDuplicateCheckName(itemStack, model))) continue;
                        itemStacks.add(itemStack);
                    }
                }
            } else {
                itemStacks.add(new ItemStack(item));
            }
        }
        return itemStacks;
    }

    private String getDuplicateCheckName(ItemStack itemStack, IBakedModel model) {
        Item item = itemStack.getItem();
        StringBuilder sb = new StringBuilder(item.getItemStackDisplayName(itemStack));
        return sb.append("@").append(model.hashCode()).toString();
    }
}
