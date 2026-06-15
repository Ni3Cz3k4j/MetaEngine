package me.ni3cz3k4j.metaEngine.item.event;

import me.ni3cz3k4j.metaEngine.item.MetaItem;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

public final class MetaItemInventoryClickEvent extends MetaCancellableItemEvent {
    private static final HandlerList HANDLERS = new HandlerList();
    private final ClickType clickType;
    private final InventoryAction inventoryAction;
    private final InventoryType inventoryType;
    private final int rawSlot;
    private final int slot;

    public MetaItemInventoryClickEvent(
            Player player,
            MetaItem item,
            ItemStack itemStack,
            ClickType clickType,
            InventoryAction inventoryAction,
            InventoryType inventoryType,
            int rawSlot,
            int slot
    ) {
        super(player, item, itemStack);
        this.clickType = clickType;
        this.inventoryAction = inventoryAction;
        this.inventoryType = inventoryType;
        this.rawSlot = rawSlot;
        this.slot = slot;
    }

    public ClickType clickType() {
        return clickType;
    }

    public InventoryAction inventoryAction() {
        return inventoryAction;
    }

    public InventoryType inventoryType() {
        return inventoryType;
    }

    public int rawSlot() {
        return rawSlot;
    }

    public int slot() {
        return slot;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}