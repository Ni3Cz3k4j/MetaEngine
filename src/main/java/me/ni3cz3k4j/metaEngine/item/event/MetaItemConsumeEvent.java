package me.ni3cz3k4j.metaEngine.item.event;

import me.ni3cz3k4j.metaEngine.item.MetaItem;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

public final class MetaItemConsumeEvent extends MetaCancellableItemEvent {
    private static final HandlerList HANDLERS = new HandlerList();

    public MetaItemConsumeEvent(Player player, MetaItem item, ItemStack itemStack) {
        super(player, item, itemStack);
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}