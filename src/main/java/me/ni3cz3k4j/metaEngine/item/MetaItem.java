package me.ni3cz3k4j.metaEngine.item;

import me.ni3cz3k4j.metaEngine.item.settings.MetaItemSettings;
import me.ni3cz3k4j.metaEngine.registry.MetaKey;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public final class MetaItem {
    private final MetaKey key;
    private final Material material;
    private final MetaItemSettings settings;
    private final MetaItemUseHandler useHandler;

    public MetaItem(MetaKey key, Material material, MetaItemSettings settings, MetaItemUseHandler useHandler) {
        this.key = key;
        this.material = material;
        this.settings = settings;
        this.useHandler = useHandler;
    }

    public MetaKey key() {
        return key;
    }

    public Material material() {
        return material;
    }

    public MetaItemSettings settings() {
        return settings;
    }

    public void onUse(Player player, ItemStack itemStack) {
        if (useHandler != null) {
            useHandler.use(player, itemStack);
        }
    }
}