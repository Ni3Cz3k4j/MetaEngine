package me.ni3cz3k4j.metaEngine.item.runtime;

import me.ni3cz3k4j.metaEngine.item.MetaItem;
import me.ni3cz3k4j.metaEngine.item.component.MetaCooldownComponent;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public final class MetaItemCooldowns {
    private MetaItemCooldowns() {}

    public static boolean hasCooldown(Player player, MetaItem item, ItemStack stack) {
        MetaCooldownComponent cooldown = cooldown(item);

        if (cooldown == null || cooldown.seconds() <= 0.0f) {
            return false;
        }

        return player.hasCooldown(stack);
    }

    public static void applyCooldown(Player player, MetaItem item, ItemStack stack) {
        MetaCooldownComponent cooldown = cooldown(item);

        if (cooldown == null || cooldown.seconds() <= 0.0f) {
            return;
        }

        applyCooldown(player, item, stack, cooldown.seconds());
    }

    public static void applyCooldown(Player player, MetaItem item, ItemStack stack, float seconds) {
        if (seconds <= 0.0f) {
            return;
        }

        int ticks = Math.max(1, Math.round(seconds * 20.0f));
        player.setCooldown(stack, ticks);
    }

    private static MetaCooldownComponent cooldown(MetaItem item) {
        return item.components()
                .get(MetaCooldownComponent.class)
                .orElse(null);
    }
}