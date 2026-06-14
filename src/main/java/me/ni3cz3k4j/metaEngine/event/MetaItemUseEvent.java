package me.ni3cz3k4j.metaEngine.event;

import me.ni3cz3k4j.metaEngine.item.MetaItem;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

public final class MetaItemUseEvent extends Event implements Cancellable {
    private static final HandlerList HANDLERS = new HandlerList();

    private final Player player;
    private final MetaItem item;
    private final ItemStack itemStack;
    private boolean cancelled;

    public MetaItemUseEvent(Player player, MetaItem item, ItemStack itemStack) {
        this.player = player;
        this.item = item;
        this.itemStack = itemStack;
    }

    public Player getPlayer() {
        return player;
    }

    public MetaItem getItem() {
        return item;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
