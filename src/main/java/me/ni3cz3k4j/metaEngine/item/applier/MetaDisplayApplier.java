package me.ni3cz3k4j.metaEngine.item.applier;

import me.ni3cz3k4j.metaEngine.item.MetaItem;
import me.ni3cz3k4j.metaEngine.item.MetaItemApplier;
import me.ni3cz3k4j.metaEngine.item.MetaItemApplyContext;
import me.ni3cz3k4j.metaEngine.item.component.MetaDisplayComponent;
import me.ni3cz3k4j.metaEngine.text.MetaText;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public final class MetaDisplayApplier implements MetaItemApplier<MetaDisplayComponent> {
    @Override
    public Class<MetaDisplayComponent> componentType() {
        return MetaDisplayComponent.class;
    }

    @Override
    public void apply(
            MetaItem item,
            MetaDisplayComponent component,
            ItemStack stack,
            ItemMeta meta,
            MetaItemApplyContext context
    ) {
        if (component.itemName() != null) {
            meta.setItemName(MetaText.color(component.itemName()));
        }

        if (component.displayName() != null) {
            meta.setDisplayName(MetaText.color(component.displayName()));
        }

        if (component.lore() != null && !component.lore().isEmpty()) {
            meta.setLore(component.lore().stream()
                    .map(MetaText::color)
                    .toList());
        }
    }
}