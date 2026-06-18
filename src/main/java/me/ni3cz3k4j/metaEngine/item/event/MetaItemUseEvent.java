package me.ni3cz3k4j.metaEngine.item.event;

import me.ni3cz3k4j.metaEngine.item.MetaItem;
import me.ni3cz3k4j.metaEngine.item.runtime.MetaItemCooldowns;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public final class MetaItemUseEvent extends MetaCancellableItemEvent {
    private static final HandlerList HANDLERS = new HandlerList();
    private final Action action;
    private final EquipmentSlot hand;
    private final MetaItemCooldowns cooldowns;

    public MetaItemUseEvent(Player player, MetaItem item, ItemStack itemStack, Action action, EquipmentSlot hand, MetaItemCooldowns cooldowns) {
        super(player, item, itemStack);
        this.action = action;
        this.hand = hand;
        this.cooldowns = cooldowns;
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

    public boolean hasCooldown() {
        return cooldowns.hasCooldown(player(), item(), itemStack());
    }

    public boolean hasCooldown(String group) {
        return cooldowns.hasCooldown(player(), group);
    }

    public void cooldown() {
        cooldowns.cooldown(player(), item(), itemStack());
    }

    public void cooldown(float seconds) {
        cooldowns.cooldown(player(), item(), itemStack(), seconds);
    }

    public void cooldownGroup(String group, float seconds) {
        cooldowns.cooldownGroup(player(), group, seconds);
    }

    public float remainingCooldownSeconds() {
        return cooldowns.remainingSeconds(player(), item());
    }

    public float remainingCooldownSeconds(String group) {
        return cooldowns.remainingSeconds(player(), group);
    }

    public int remainingCooldownTicks() {
        return cooldowns.remainingTicks(player(), item());
    }

    public int remainingCooldownTicks(String group) {
        return cooldowns.remainingTicks(player(), group);
    }

    public void clearCooldown() {
        cooldowns.clear(player(), item());
    }

    public void clearCooldown(String group) {
        cooldowns.clear(player(), group);
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}