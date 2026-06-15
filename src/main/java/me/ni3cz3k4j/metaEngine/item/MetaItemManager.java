package me.ni3cz3k4j.metaEngine.item;

import me.ni3cz3k4j.metaEngine.item.settings.MetaConsumeAnimation;
import me.ni3cz3k4j.metaEngine.item.settings.MetaItemSettings;
import me.ni3cz3k4j.metaEngine.registry.MetaKey;
import me.ni3cz3k4j.metaEngine.registry.MetaRegistries;
import me.ni3cz3k4j.metaEngine.text.MetaText;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.CustomModelDataComponent;
import org.bukkit.inventory.meta.components.FoodComponent;
import org.bukkit.inventory.meta.components.UseCooldownComponent;
import org.bukkit.inventory.meta.components.consumable.ConsumableComponent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class MetaItemManager {
    private final JavaPlugin plugin;
    private final MetaRegistries registries;
    private final NamespacedKey itemIdKey;
    private final MetaItemApplierRegistry appliers;

    public MetaItemManager(JavaPlugin plugin, MetaRegistries registries) {
        this.plugin = plugin;
        this.registries = registries;
        this.itemIdKey = new NamespacedKey(plugin, "item_id");
        this.appliers = new MetaItemApplierRegistry();
        this.appliers.registerDefaults();
    }

    public ItemStack createItem(MetaKey key) {
        MetaItem item = registries.items()
                .get(key)
                .orElseThrow(() -> new IllegalArgumentException("Unknown MetaItem: " + key.asString() + "."));

        ItemStack stack = new ItemStack(item.material());
        ItemMeta meta = stack.getItemMeta();

        if (meta == null) {
            throw new IllegalStateException("Item has no ItemMeta: " + item.material() + ".");
        }

        meta.getPersistentDataContainer().set(itemIdKey, PersistentDataType.STRING, item.key().asString());

        MetaItemApplyContext context = new MetaItemApplyContext(plugin, itemIdKey);
        appliers.applyAll(item, stack, meta, context);

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

        MetaKey key = MetaKey.parse(rawId);
        return registries.items().get(key);
    }
}