package me.ni3cz3k4j.metaEngine.item.event;

import me.ni3cz3k4j.metaEngine.item.MetaItem;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

public final class MetaItemAttackEntityEvent extends MetaCancellableItemEvent {
    private static final HandlerList HANDLERS = new HandlerList();
    private final Entity target;
    private double damage;

    public MetaItemAttackEntityEvent(Player player, MetaItem item, ItemStack itemStack, Entity target, double damage) {
        super(player, item, itemStack);
        this.target = target;
        this.damage = damage;
    }

    public Entity target() {
        return target;
    }

    public boolean hasLivingTarget() {
        return target instanceof LivingEntity;
    }

    public LivingEntity livingTarget() {
        return target instanceof LivingEntity livingEntity ? livingEntity : null;
    }

    public double damage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = Math.max(0.0D, damage);
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}