package me.ni3cz3k4j.metaEngine.item.event;

import me.ni3cz3k4j.metaEngine.item.MetaItem;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.inventory.ItemStack;

public abstract class MetaCancellableItemEvent extends MetaItemEvent implements Cancellable {
    private boolean cancelled;

    protected MetaCancellableItemEvent(Player player, MetaItem item, ItemStack itemStack) {
        super(player, item, itemStack);
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}