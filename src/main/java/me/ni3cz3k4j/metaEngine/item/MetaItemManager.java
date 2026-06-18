package me.ni3cz3k4j.metaEngine.item;

import me.ni3cz3k4j.metaEngine.item.runtime.MetaItemCooldowns;
import me.ni3cz3k4j.metaEngine.registry.MetaKey;
import me.ni3cz3k4j.metaEngine.registry.MetaRegistries;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class MetaItemManager {
    private final JavaPlugin plugin;
    private final MetaRegistries registries;
    private final NamespacedKey itemIdKey;
    private final MetaItemApplierRegistry appliers;
    private final MetaItemCooldowns cooldowns;

    public MetaItemManager(JavaPlugin plugin, MetaRegistries registries) {
        this.plugin = plugin;
        this.registries = registries;
        this.itemIdKey = new NamespacedKey(plugin, "item_id");
        this.appliers = new MetaItemApplierRegistry();
        this.cooldowns = new MetaItemCooldowns();
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
        if (stack == null || stack.getType().isAir() || !stack.hasItemMeta()) {
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
        } catch (IllegalArgumentException ignored) {
            return Optional.empty();
        }

        return registries.items().get(key);
    }

    public Optional<MetaKey> identifyKey(ItemStack stack) {
        if (stack == null || stack.getType().isAir() || !stack.hasItemMeta()) {
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

        try {
            return Optional.of(MetaKey.parse(rawId));
        } catch (IllegalArgumentException ignored) {
            return Optional.empty();
        }
    }

    public boolean isMetaItem(ItemStack stack) {
        return identify(stack).isPresent();
    }

    public void give(Player player, MetaKey key, int amount) {
        if (player == null) {
            throw new IllegalArgumentException("Player cannot be null.");
        }

        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0.");
        }

        int remaining = amount;

        while (remaining > 0) {
            ItemStack stack = createItem(key);
            int stackAmount = Math.min(remaining, stack.getMaxStackSize());
            stack.setAmount(stackAmount);

            Map<Integer, ItemStack> leftovers = player.getInventory().addItem(stack);

            for (ItemStack leftover : leftovers.values()) {
                player.getWorld().dropItemNaturally(player.getLocation(), leftover);
            }

            remaining -= stackAmount;
        }
    }

    public ItemStack rebuild(ItemStack stack) {
        MetaKey key = identifyKey(stack).orElseThrow(() -> new IllegalArgumentException("ItemStack is not a MetaItem."));

        ItemStack rebuilt = createItem(key);
        rebuilt.setAmount(stack.getAmount());
        return rebuilt;
    }

    public Map<Integer, ItemStack> giveOrReturnLeftovers(Player player, MetaKey key, int amount) {
        if (player == null) {
            throw new IllegalArgumentException("Player cannot be null.");
        }

        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0.");
        }

        Map<Integer, ItemStack> allLeftovers = new HashMap<>();
        int leftoverSlot = 0;
        int remaining = amount;

        while (remaining > 0) {
            ItemStack stack = createItem(key);
            int stackAmount = Math.min(remaining, stack.getMaxStackSize());
            stack.setAmount(stackAmount);

            Map<Integer, ItemStack> leftovers = player.getInventory().addItem(stack);

            for (ItemStack leftover : leftovers.values()) {
                allLeftovers.put(leftoverSlot++, leftover);
            }

            remaining -= stackAmount;
        }

        return allLeftovers;
    }

    public MetaItemCooldowns cooldowns() {
        return cooldowns;
    }

    public NamespacedKey itemIdKey() {
        return itemIdKey;
    }

    public MetaRegistries registries() {
        return registries;
    }
}