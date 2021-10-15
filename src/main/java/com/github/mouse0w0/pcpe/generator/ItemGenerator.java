package com.github.mouse0w0.pcpe.generator;

import com.github.mouse0w0.pcpe.Exporter;
import com.github.mouse0w0.pcpe.data.CPItem;
import com.github.mouse0w0.pcpe.renderer.FrameBuffer;
import com.github.mouse0w0.pcpe.renderer.Renderer;
import com.github.mouse0w0.pcpe.util.MinecraftUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

import java.util.*;

public class ItemGenerator implements DataGenerator {

    private List<ItemStack> data;

    @Override
    public void collectData(Exporter exporter) {
        data = new ArrayList<>();

        final IBakedModel missingModel = MinecraftUtils.getModelManager().getMissingModel();
        final ItemModelMesher itemModelMesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();

        final NonNullList<ItemStack> foundCreativeTabItems = NonNullList.create();
        final Set<String> duplicateNames = new HashSet<>();

        for (Item item : Item.REGISTRY) {
            if (!exporter.checkNamespace(item)) continue;

            if (item.getHasSubtypes()) {
                foundCreativeTabItems.clear();
                for (CreativeTabs creativeTabs : item.getCreativeTabs()) {
                    item.getSubItems(creativeTabs, foundCreativeTabItems);
                }
                if (!foundCreativeTabItems.isEmpty()) {
                    data.addAll(foundCreativeTabItems);
                } else {
                    // Not found item. Try scan metadata.
                    exporter.getLogger().info("Not found item. Try scan metadata. RegistryName: {}", item.getRegistryName());
                    duplicateNames.clear();
                    for (int metadata = 0; metadata <= Short.MAX_VALUE; metadata++) {
                        ItemStack itemStack = new ItemStack(item, 1, metadata);
                        IBakedModel model = itemModelMesher.getItemModel(itemStack);
                        if (model == missingModel) break;
                        if (duplicateNames.add(getDuplicateCheckName(itemStack, model))) {
                            data.add(itemStack);
                        }
                    }
                }
            } else {
                data.add(new ItemStack(item));
            }
        }
    }

    private static String getDuplicateCheckName(ItemStack itemStack, IBakedModel model) {
        Item item = itemStack.getItem();
        StringBuilder sb = new StringBuilder(item.getItemStackDisplayName(itemStack));
        return sb.append("@").append(model.hashCode()).toString();
    }

    @Override
    public void exportData(Exporter exporter) {
        String namespace = exporter.getNamespace();
        FrameBuffer frameBuffer = new FrameBuffer(64, 64);
        Renderer.getInstance().startRenderItem(frameBuffer);
        Set<CPItem> dataList = new LinkedHashSet<>();
        for (ItemStack itemStack : data) {
            Item item = itemStack.getItem();
            dataList.add(new CPItem(item.getRegistryName().toString(), itemStack.getMetadata(),
                    getTranslationKey(itemStack), item instanceof ItemBlock));
            try {
                Renderer.getInstance().renderItemToPNG(frameBuffer, itemStack,
                        exporter.getOutput().resolve("content/" + namespace + "/image/item/" + item.getRegistryName().getPath() + "_" + itemStack.getMetadata() + ".png"));
            } catch (Exception e) {
                exporter.getLogger().error("Caught an exception when rendering item \"" + itemStack.toString() + "\", skip it.", e);
            }
        }
        exporter.writeJson("content/" + namespace + "/item.json", dataList);
        Renderer.getInstance().endRenderItem(frameBuffer);
    }

    @Override
    public void exportTranslation(Exporter exporter, Map<String, String> translations) {
        data.forEach(itemStack -> translations.put(getTranslationKey(itemStack), itemStack.getItem().getItemStackDisplayName(itemStack)));
    }

    private static String getTranslationKey(ItemStack item) {
        ResourceLocation location = item.getItem().getRegistryName();
        return "item." + location.getNamespace() + "." + location.getPath() + "#" + item.getMetadata();
    }
}
