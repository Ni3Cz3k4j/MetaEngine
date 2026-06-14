package me.ni3cz3k4j.metaEngine.item;

import me.ni3cz3k4j.metaEngine.registry.MetaKey;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public final class MetaItem {
    private final MetaKey key;
    private final Material material;
    private final String displayName;
    private final MetaItemUseHandler useHandler;

    public MetaItem(MetaKey key, Material material, String displayName, MetaItemUseHandler useHandler) {
        this.key = key;
        this.material = material;
        this.displayName = displayName;
        this.useHandler = useHandler;
    }

    public static MetaItem of(MetaKey key, Material material, String displayName, MetaItemUseHandler useHandler) {
        return new MetaItem(key, material, displayName, useHandler);
    }

    public MetaKey key() {
        return key;
    }

    public Material material() {
        return material;
    }

    public String displayName() {
        return displayName;
    }

    public void onUse(Player player, ItemStack itemStack) {
        if (useHandler != null) {
            useHandler.use(player, itemStack);
        }
    }
}