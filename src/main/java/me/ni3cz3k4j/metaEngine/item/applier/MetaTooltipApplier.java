package me.ni3cz3k4j.metaEngine.item.applier;

import me.ni3cz3k4j.metaEngine.item.MetaItem;
import me.ni3cz3k4j.metaEngine.item.MetaItemApplier;
import me.ni3cz3k4j.metaEngine.item.MetaItemApplyContext;
import me.ni3cz3k4j.metaEngine.item.component.MetaTooltipComponent;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public final class MetaTooltipApplier implements MetaItemApplier<MetaTooltipComponent> {
    @Override
    public Class<MetaTooltipComponent> componentType() {
        return MetaTooltipComponent.class;
    }

    @Override
    public void apply(MetaItem item, MetaTooltipComponent component, ItemStack stack, ItemMeta meta, MetaItemApplyContext context) {
        meta.setHideTooltip(component.hideTooltip());

        if (component.hideAdditionalTooltip()) {
            meta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
        }

        if (component.tooltipStyle() != null && !component.tooltipStyle().isBlank()) {
            NamespacedKey style = parseKey(component.tooltipStyle(), item.key().namespace());

            if (style != null) {
                meta.setTooltipStyle(style);
            }
        }

        for (Object rawPart : component.hiddenParts()) {
            if (!(rawPart instanceof String part)) {
                continue;
            }

            applyHiddenPart(meta, part);
        }
    }

    private NamespacedKey parseKey(String raw, String defaultNamespace) {
        if (raw.contains(":")) {
            return NamespacedKey.fromString(raw);
        }

        return new NamespacedKey(defaultNamespace, raw);
    }

    private void applyHiddenPart(ItemMeta meta, String part) {
        String normalized = part.toUpperCase()
                .replace("MINECRAFT:", "")
                .replace('-', '_')
                .replace(' ', '_');

        try {
            meta.addItemFlags(ItemFlag.valueOf(normalized));
            return;
        } catch (IllegalArgumentException ignored) {
        }

        try {
            meta.addItemFlags(ItemFlag.valueOf("HIDE_" + normalized));
        } catch (IllegalArgumentException ignored) {
        }
    }
}