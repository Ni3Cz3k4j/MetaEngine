package me.ni3cz3k4j.metaEngine.item;

import me.ni3cz3k4j.metaEngine.registry.MetaKey;
import me.ni3cz3k4j.metaEngine.registry.MetaRegistries;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Optional;

public final class MetaItemManager {
    private final MetaRegistries registries;
    private final NamespacedKey itemIdKey;

    public MetaItemManager(JavaPlugin plugin, MetaRegistries registries) {
        this.registries = registries;
        this.itemIdKey = new NamespacedKey(plugin, "item_id");
    }

    public ItemStack createItem(MetaKey key) {
        MetaItem item = registries.items()
                .get(key)
                .orElseThrow(() -> new IllegalArgumentException("Unknown MetaItem :" + key + "."));

        ItemStack stack = new ItemStack(item.material());
        ItemMeta meta = stack.getItemMeta();

        if (meta == null) {
            return stack;
        }

        meta.setDisplayName(ChatColor.RESET + item.displayName());
        meta.getPersistentDataContainer().set(itemIdKey, PersistentDataType.STRING, key.asString());
        stack.setItemMeta(meta);
        return stack;
    }

    public Optional<MetaItem> identify(ItemStack stack) {
        if (stack == null || !stack.hasItemMeta()) {
            return Optional.empty();
        }

        ItemMeta meta = stack.getItemMeta();

        if (meta == null) {
            return Optional.empty();
        }

        String rawId = meta.getPersistentDataContainer().get(itemIdKey, PersistentDataType.STRING);

        if (rawId == null) {
            return Optional.empty();
        }

        MetaKey key;

        try {
            key = MetaKey.parse(rawId);
        } catch (IllegalArgumentException exception) {
            return Optional.empty();
        }

        return registries.items().get(key);
    }

    public boolean isMetaItem(ItemStack stack) {
        return identify(stack).isPresent();
    }

    public boolean isMetaItem(ItemStack stack, MetaKey key) {
        return identify(stack)
                .map(item -> item.key().equals(key))
                .orElse(false);
    }
}