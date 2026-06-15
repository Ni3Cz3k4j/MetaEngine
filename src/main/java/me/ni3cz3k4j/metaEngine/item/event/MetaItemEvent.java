package me.ni3cz3k4j.metaEngine.item.event;

import me.ni3cz3k4j.metaEngine.item.MetaItem;
import me.ni3cz3k4j.metaEngine.registry.MetaKey;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

public abstract class MetaItemEvent extends Event {
    private final Player player;
    private final MetaItem item;
    private final ItemStack itemStack;

    protected MetaItemEvent(Player player, MetaItem item, ItemStack itemStack) {
        this.player = player;
        this.item = item;
        this.itemStack = itemStack;
    }

    public Player player() {
        return player;
    }

    public MetaItem item() {
        return item;
    }

    public ItemStack itemStack() {
        return itemStack;
    }

    public boolean isItem(MetaKey key) {
        return item.key().equals(key);
    }
}