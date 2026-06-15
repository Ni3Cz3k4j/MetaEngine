package me.ni3cz3k4j.metaEngine.item;

import me.ni3cz3k4j.metaEngine.item.behavior.MetaItemBehaviorContainer;
import me.ni3cz3k4j.metaEngine.item.component.MetaItemComponentContainer;
import me.ni3cz3k4j.metaEngine.registry.MetaKey;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public final class MetaItem {
    private final MetaKey key;
    private final Material material;
    private final MetaItemComponentContainer components;
    private final MetaItemBehaviorContainer behaviors;

    public MetaItem(MetaKey key, Material material, MetaItemComponentContainer components, MetaItemBehaviorContainer behaviors) {
        this.key = key;
        this.material = material;
        this.components = components;
        this.behaviors = behaviors;
    }

    public MetaKey key() {
        return key;
    }

    public Material material() {
        return material;
    }

    public MetaItemComponentContainer components() {
        return components;
    }

    public MetaItemBehaviorContainer behaviors() {
        return behaviors;
    }
}