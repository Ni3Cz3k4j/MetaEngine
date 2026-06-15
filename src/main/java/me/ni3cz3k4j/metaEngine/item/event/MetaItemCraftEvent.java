package me.ni3cz3k4j.metaEngine.item.event;

import me.ni3cz3k4j.metaEngine.item.MetaItem;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

public final class MetaItemCraftEvent extends MetaCancellableItemEvent {
    private static final HandlerList HANDLERS = new HandlerList();
    private final Recipe recipe;

    public MetaItemCraftEvent(Player player, MetaItem item, ItemStack itemStack, Recipe recipe) {
        super(player, item, itemStack);
        this.recipe = recipe;
    }

    public Recipe recipe() {
        return recipe;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}