package me.ni3cz3k4j.metaEngine.item;

import me.ni3cz3k4j.metaEngine.item.component.MetaItemComponent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public interface MetaItemApplier<T extends MetaItemComponent> {
    Class<T> componentType();

    void apply(MetaItem item, T component, ItemStack stack, ItemMeta meta, MetaItemApplyContext context);
}