package me.ni3cz3k4j.metaEngine.item.applier;

import me.ni3cz3k4j.metaEngine.item.MetaItem;
import me.ni3cz3k4j.metaEngine.item.MetaItemApplier;
import me.ni3cz3k4j.metaEngine.item.MetaItemApplyContext;
import me.ni3cz3k4j.metaEngine.item.component.MetaCustomModelDataComponent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.CustomModelDataComponent;

public final class MetaCustomModelDataApplier implements MetaItemApplier<MetaCustomModelDataComponent> {
    @Override
    public Class<MetaCustomModelDataComponent> componentType() {
        return MetaCustomModelDataComponent.class;
    }

    @Override
    public void apply(
            MetaItem item,
            MetaCustomModelDataComponent component,
            ItemStack stack,
            ItemMeta meta,
            MetaItemApplyContext context
    ) {
        CustomModelDataComponent modelData = meta.getCustomModelDataComponent();

        if (component.strings() != null) {
            modelData.setStrings(component.strings());
        }

        if (component.floats() != null) {
            modelData.setFloats(component.floats());
        }

        if (component.flags() != null) {
            modelData.setFlags(component.flags());
        }

        if (component.colors() != null) {
            modelData.setColors(component.colors());
        }

        meta.setCustomModelDataComponent(modelData);
    }
}