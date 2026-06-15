package me.ni3cz3k4j.metaEngine.item.applier;

import me.ni3cz3k4j.metaEngine.item.MetaItem;
import me.ni3cz3k4j.metaEngine.item.MetaItemApplier;
import me.ni3cz3k4j.metaEngine.item.MetaItemApplyContext;
import me.ni3cz3k4j.metaEngine.item.component.MetaStackComponent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public final class MetaStackApplier implements MetaItemApplier<MetaStackComponent> {
    @Override
    public Class<MetaStackComponent> componentType() {
        return MetaStackComponent.class;
    }

    @Override
    public void apply(
            MetaItem item,
            MetaStackComponent component,
            ItemStack stack,
            ItemMeta meta,
            MetaItemApplyContext context
    ) {
        if (component.maxStackSize() != null) {
            meta.setMaxStackSize(component.maxStackSize());
        }
    }
}