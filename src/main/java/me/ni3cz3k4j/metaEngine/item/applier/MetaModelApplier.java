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

        String modelPath = component.modelPath();

        if (modelPath == null || modelPath.isBlank()) {
            modelPath = "item/" + item.key().path();
        }

        NamespacedKey modelKey;

        if (modelPath.contains(":")) {
            modelKey = NamespacedKey.fromString(modelPath);
        } else if (component.mode() == MetaModelMode.VANILLA_MODEL) {
            modelKey = NamespacedKey.minecraft(modelPath);
        } else {
            modelKey = new NamespacedKey(item.key().namespace(), modelPath);
        }

        if (modelKey != null) {
            meta.setItemModel(modelKey);
        }
    }
}