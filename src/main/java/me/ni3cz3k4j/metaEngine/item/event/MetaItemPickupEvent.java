package me.ni3cz3k4j.metaEngine.item.event;

import me.ni3cz3k4j.metaEngine.item.MetaItem;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

public final class MetaItemPickupEvent extends MetaCancellableItemEvent {
    private static final HandlerList HANDLERS = new HandlerList();

    private final Item pickedUpEntity;

    public MetaItemPickupEvent(Player player, MetaItem item, ItemStack itemStack, Item pickedUpEntity) {
        super(player, item, itemStack);
        this.pickedUpEntity = pickedUpEntity;
    }

    public Item pickedUpEntity() {
        return pickedUpEntity;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}