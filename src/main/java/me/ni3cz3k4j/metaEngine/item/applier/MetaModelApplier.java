package me.ni3cz3k4j.metaEngine.item.applier;

import me.ni3cz3k4j.metaEngine.item.MetaItem;
import me.ni3cz3k4j.metaEngine.item.MetaItemApplier;
import me.ni3cz3k4j.metaEngine.item.MetaItemApplyContext;
import me.ni3cz3k4j.metaEngine.item.component.MetaModelComponent;
import me.ni3cz3k4j.metaEngine.item.component.MetaModelMode;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public final class MetaModelApplier implements MetaItemApplier<MetaModelComponent> {
    @Override
    public Class<MetaModelComponent> componentType() {
        return MetaModelComponent.class;
    }

    @Override
    public void apply(MetaItem item, MetaModelComponent component, ItemStack stack, ItemMeta meta, MetaItemApplyContext context) {
        if (component.mode() == null) {
            return;
        }

        if (component.mode() == MetaModelMode.RAW_ITEM_DEFINITION) {
            return;
        }

        if (component.mode() == MetaModelMode.VANILLA_MODEL) {
            return;
        }

        String itemModelPath = itemModelPath(item, component);

        NamespacedKey itemModelKey = new NamespacedKey(
                item.key().namespace(),
                itemModelPath
        );

        meta.setItemModel(itemModelKey);
    }

    private String itemModelPath(MetaItem item, MetaModelComponent component) {
        String modelId = component.modelId();

        if (modelId == null || modelId.isBlank()) {
            modelId = item.key().path();
        }

        modelId = normalize(modelId);

        if (modelId.startsWith("item/")) {
            modelId = modelId.substring("item/".length());
        }

        return modelId;
    }

    private String normalize(String path) {
        String normalized = path.replace("\\", "/");

        while (normalized.startsWith("/")) {
            normalized = normalized.substring(1);
        }

        if (normalized.endsWith(".json")) {
            normalized = normalized.substring(0, normalized.length() - ".json".length());
        }

        if (normalized.endsWith(".png")) {
            normalized = normalized.substring(0, normalized.length() - ".png".length());
        }

        if (normalized.contains(":")) {
            normalized = normalized.substring(normalized.indexOf(':') + 1);
        }

        return normalized;
    }
}