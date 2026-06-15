package me.ni3cz3k4j.metaEngine.item.applier;

import me.ni3cz3k4j.metaEngine.item.MetaItem;
import me.ni3cz3k4j.metaEngine.item.MetaItemApplier;
import me.ni3cz3k4j.metaEngine.item.MetaItemApplyContext;
import me.ni3cz3k4j.metaEngine.item.component.MetaItemRarity;
import me.ni3cz3k4j.metaEngine.item.component.MetaRarityComponent;
import org.bukkit.inventory.ItemRarity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public final class MetaRarityApplier implements MetaItemApplier<MetaRarityComponent> {
    @Override
    public Class<MetaRarityComponent> componentType() {
        return MetaRarityComponent.class;
    }

    @Override
    public void apply(MetaItem item, MetaRarityComponent component, ItemStack stack, ItemMeta meta, MetaItemApplyContext context) {
        if (component.rarity() == null) {
            return;
        }

        meta.setRarity(map(component.rarity()));
    }

    private ItemRarity map(MetaItemRarity rarity) {
        return switch (rarity) {
            case COMMON -> ItemRarity.COMMON;
            case UNCOMMON -> ItemRarity.UNCOMMON;
            case RARE -> ItemRarity.RARE;
            case EPIC -> ItemRarity.EPIC;
        };
    }
}