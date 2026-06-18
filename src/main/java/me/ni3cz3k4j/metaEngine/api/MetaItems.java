package me.ni3cz3k4j.metaEngine.api;

import me.ni3cz3k4j.metaEngine.item.MetaItem;
import me.ni3cz3k4j.metaEngine.item.MetaItemManager;
import me.ni3cz3k4j.metaEngine.registry.MetaKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public final class MetaItems {
    private final MetaItemManager manager;

    public MetaItems(MetaItemManager manager) {
        this.manager = manager;
    }

    public ItemStack create(String key) {
        return manager.createItem(MetaKey.parse(key));
    }

    public ItemStack create(MetaKey key) {
        return manager.createItem(key);
    }

    public void give(Player player, String key, int amount) {
        manager.give(player, MetaKey.parse(key), amount);
    }

    public void give(Player player, MetaKey key, int amount) {
        manager.give(player, key, amount);
    }

    public Optional<MetaItem> identify(ItemStack stack) {
        return manager.identify(stack);
    }

    public Optional<MetaKey> identifyKey(ItemStack stack) {
        return manager.identifyKey(stack);
    }

    public boolean isMetaItem(ItemStack stack) {
        return manager.isMetaItem(stack);
    }

    public ItemStack rebuild(ItemStack stack) {
        return manager.rebuild(stack);
    }
}