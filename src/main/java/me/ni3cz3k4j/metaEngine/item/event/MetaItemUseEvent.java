package me.ni3cz3k4j.metaEngine.item.event;

import me.ni3cz3k4j.metaEngine.item.MetaItem;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public final class MetaItemUseEvent extends MetaCancellableItemEvent {
    private static final HandlerList HANDLERS = new HandlerList();
    private final Action action;
    private final EquipmentSlot hand;

    public MetaItemUseEvent(Player player, MetaItem item, ItemStack itemStack, Action action, EquipmentSlot hand) {
        super(player, item, itemStack);
        this.action = action;
        this.hand = hand;
    }

    public Action action() {
        return action;
    }

    public EquipmentSlot hand() {
        return hand;
    }

    public boolean isRightClick() {
        return action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK;
    }

    public boolean isLeftClick() {
        return action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}