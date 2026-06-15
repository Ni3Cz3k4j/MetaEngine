package me.ni3cz3k4j.metaEngine.item.event;

import me.ni3cz3k4j.metaEngine.item.MetaItem;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public final class MetaItemTickEvent extends MetaItemEvent {
    private static final HandlerList HANDLERS = new HandlerList();
    private final EquipmentSlot slot;
    private final int inventorySlot;

    public MetaItemTickEvent(Player player, MetaItem item, ItemStack itemStack, EquipmentSlot slot, int inventorySlot) {
        super(player, item, itemStack);
        this.slot = slot;
        this.inventorySlot = inventorySlot;
    }

    public EquipmentSlot equipmentSlot() {
        return slot;
    }

    public int inventorySlot() {
        return inventorySlot;
    }

    public boolean isMainHand() {
        return slot == EquipmentSlot.HAND;
    }

    public boolean isOffHand() {
        return slot == EquipmentSlot.OFF_HAND;
    }

    public boolean isInventory() {
        return slot == null;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}