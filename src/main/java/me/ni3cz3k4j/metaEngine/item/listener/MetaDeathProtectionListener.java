package me.ni3cz3k4j.metaEngine.item.listener;

import me.ni3cz3k4j.metaEngine.item.MetaItem;
import me.ni3cz3k4j.metaEngine.item.MetaItemManager;
import me.ni3cz3k4j.metaEngine.item.component.*;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityResurrectEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Optional;
import java.util.Random;

public final class MetaDeathProtectionListener implements Listener {
    private final MetaItemManager itemManager;
    private final Random random = new Random();

    public MetaDeathProtectionListener(MetaItemManager itemManager) {
        this.itemManager = itemManager;
    }

    @EventHandler
    public void onResurrect(EntityResurrectEvent event) {
        if (!(event.getEntity() instanceof Player player)) {
            return;
        }

        Source source = find(player).orElse(null);

        if (source == null) {
            return;
        }

        MetaDeathProtectionComponent component = source.item()
                .components()
                .get(MetaDeathProtectionComponent.class)
                .orElse(null);

        if (component == null) {
            return;
        }

        event.setCancelled(false);

        source.stack().setAmount(Math.max(0, source.stack().getAmount() - 1));

        for (MetaDeathProtectionEffect effect : component.effects()) {
            apply(player, effect);
        }
    }

    private Optional<Source> find(Player player) {
        Optional<Source> main = identify(player.getInventory().getItemInMainHand());
        if (main.isPresent()) return main;

        Optional<Source> off = identify(player.getInventory().getItemInOffHand());
        if (off.isPresent()) return off;

        for (ItemStack stack : player.getInventory().getContents()) {
            Optional<Source> source = identify(stack);
            if (source.isPresent()) return source;
        }

        return Optional.empty();
    }

    private Optional<Source> identify(ItemStack stack) {
        if (stack == null || stack.getType().isAir()) {
            return Optional.empty();
        }

        Optional<MetaItem> item = itemManager.identify(stack);

        if (item.isEmpty()) {
            return Optional.empty();
        }

        if (!item.get().components().has(MetaDeathProtectionComponent.class)) {
            return Optional.empty();
        }

        return Optional.of(new Source(item.get(), stack));
    }

    private void apply(Player player, MetaDeathProtectionEffect effect) {
        if (effect instanceof MetaApplyPotionDeathEffect potion) {
            applyPotion(player, potion);
            return;
        }

        if (effect instanceof MetaClearEffectsDeathEffect) {
            player.getActivePotionEffects().forEach(active -> player.removePotionEffect(active.getType()));
            return;
        }

        if (effect instanceof MetaHealDeathEffect heal) {
            double maxHealth = player.getAttribute(Attribute.MAX_HEALTH).getValue();
            player.setHealth(Math.min(maxHealth, Math.max(1.0D, heal.amount())));
            return;
        }

        if (effect instanceof MetaExtinguishDeathEffect) {
            player.setFireTicks(0);
            return;
        }

        if (effect instanceof MetaTeleportDeathEffect teleport) {
            teleport(player, teleport.radius());
            return;
        }

        if (effect instanceof MetaPlaySoundDeathEffect sound) {
            player.playSound(player.getLocation(), sound.soundKey(), sound.volume(), sound.pitch());
        }
    }

    private void applyPotion(Player player, MetaApplyPotionDeathEffect effect) {
        NamespacedKey key = NamespacedKey.fromString(effect.effectKey());

        if (key == null) {
            return;
        }

        PotionEffectType type = Registry.EFFECT.get(key);

        if (type == null) {
            return;
        }

        player.addPotionEffect(new PotionEffect(
                type,
                effect.durationTicks(),
                effect.amplifier(),
                effect.ambient(),
                effect.particles(),
                effect.icon()
        ));
    }

    private void teleport(Player player, double radius) {
        if (radius <= 0) {
            return;
        }

        Location origin = player.getLocation();

        for (int attempt = 0; attempt < 16; attempt++) {
            int x = origin.getBlockX() + (int) Math.round(((random.nextDouble() * 2.0D) - 1.0D) * radius);
            int z = origin.getBlockZ() + (int) Math.round(((random.nextDouble() * 2.0D) - 1.0D) * radius);
            int y = origin.getWorld().getHighestBlockYAt(x, z) + 1;

            Location target = new Location(origin.getWorld(), x + 0.5D, y, z + 0.5D, origin.getYaw(), origin.getPitch());

            if (target.getBlock().isPassable() && target.clone().add(0, 1, 0).getBlock().isPassable()) {
                player.teleport(target);
                return;
            }
        }
    }

    private record Source(MetaItem item, ItemStack stack) {
    }
}