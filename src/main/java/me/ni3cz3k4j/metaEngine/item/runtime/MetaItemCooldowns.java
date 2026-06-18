package me.ni3cz3k4j.metaEngine.item.runtime;

import me.ni3cz3k4j.metaEngine.item.MetaItem;
import me.ni3cz3k4j.metaEngine.item.component.MetaCooldownComponent;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class MetaItemCooldowns {
    private final Map<UUID, Map<String, Long>> cooldowns = new HashMap<>();

    public boolean hasCooldown(Player player, MetaItem item, ItemStack stack) {
        MetaCooldownComponent component = cooldown(item);

        if (component == null || component.seconds() <= 0.0f) {
            return false;
        }

        return hasCooldown(player, group(item, component));
    }

    public boolean hasCooldown(Player player, String group) {
        if (player == null || group == null || group.isBlank()) {
            return false;
        }

        cleanup(player);

        Map<String, Long> playerCooldowns = cooldowns.get(player.getUniqueId());

        if (playerCooldowns == null) {
            return false;
        }

        Long expiresAt = playerCooldowns.get(group);
        return expiresAt != null && expiresAt > System.currentTimeMillis();
    }

    public void cooldown(Player player, MetaItem item, ItemStack stack) {
        MetaCooldownComponent component = cooldown(item);

        if (component == null || component.seconds() <= 0.0f) {
            return;
        }

        cooldown(player, item, stack, component.seconds());
    }

    public void cooldown(Player player, MetaItem item, ItemStack stack, float seconds) {
        MetaCooldownComponent component = cooldown(item);

        if (component == null || seconds <= 0.0f) {
            return;
        }

        cooldownGroup(player, group(item, component), seconds);

        if (stack != null && !stack.getType().isAir()) {
            player.setCooldown(stack, secondsToTicks(seconds));
        }
    }

    public void cooldownGroup(Player player, String group, float seconds) {
        if (player == null || group == null || group.isBlank() || seconds <= 0.0f) {
            return;
        }

        cooldowns
                .computeIfAbsent(player.getUniqueId(), ignored -> new HashMap<>())
                .put(group, System.currentTimeMillis() + Math.round(seconds * 1000.0f));
    }

    public float remainingSeconds(Player player, MetaItem item) {
        MetaCooldownComponent component = cooldown(item);

        if (component == null) {
            return 0.0f;
        }

        return remainingSeconds(player, group(item, component));
    }

    public float remainingSeconds(Player player, String group) {
        return remainingMillis(player, group) / 1000.0f;
    }

    public int remainingTicks(Player player, MetaItem item) {
        MetaCooldownComponent component = cooldown(item);

        if (component == null) {
            return 0;
        }

        return remainingTicks(player, group(item, component));
    }

    public int remainingTicks(Player player, String group) {
        long millis = remainingMillis(player, group);

        if (millis <= 0L) {
            return 0;
        }

        return Math.max(1, (int) Math.ceil(millis / 50.0D));
    }

    public void clear(Player player, MetaItem item) {
        MetaCooldownComponent component = cooldown(item);

        if (component == null) {
            return;
        }

        clear(player, group(item, component));
    }

    public void clear(Player player, String group) {
        if (player == null || group == null || group.isBlank()) {
            return;
        }

        Map<String, Long> playerCooldowns = cooldowns.get(player.getUniqueId());

        if (playerCooldowns == null) {
            return;
        }

        playerCooldowns.remove(group);

        if (playerCooldowns.isEmpty()) {
            cooldowns.remove(player.getUniqueId());
        }
    }

    public void clearAll(Player player) {
        if (player != null) {
            cooldowns.remove(player.getUniqueId());
        }
    }

    private long remainingMillis(Player player, String group) {
        if (player == null || group == null || group.isBlank()) {
            return 0L;
        }

        cleanup(player);

        Map<String, Long> playerCooldowns = cooldowns.get(player.getUniqueId());

        if (playerCooldowns == null) {
            return 0L;
        }

        Long expiresAt = playerCooldowns.get(group);

        if (expiresAt == null) {
            return 0L;
        }

        return Math.max(0L, expiresAt - System.currentTimeMillis());
    }

    private void cleanup(Player player) {
        Map<String, Long> playerCooldowns = cooldowns.get(player.getUniqueId());

        if (playerCooldowns == null) {
            return;
        }

        long now = System.currentTimeMillis();
        playerCooldowns.entrySet().removeIf(entry -> entry.getValue() <= now);

        if (playerCooldowns.isEmpty()) {
            cooldowns.remove(player.getUniqueId());
        }
    }

    private MetaCooldownComponent cooldown(MetaItem item) {
        if (item == null) {
            return null;
        }

        return item.components().get(MetaCooldownComponent.class).orElse(null);
    }

    private String group(MetaItem item, MetaCooldownComponent component) {
        if (component.group() != null && !component.group().isBlank()) {
            return component.group();
        }

        return item.key().asString();
    }

    private int secondsToTicks(float seconds) {
        return Math.max(1, Math.round(seconds * 20.0f));
    }
}