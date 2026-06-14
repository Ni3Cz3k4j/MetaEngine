package me.ni3cz3k4j.metaEngine.listener;

import me.ni3cz3k4j.metaEngine.event.MetaItemUseEvent;
import me.ni3cz3k4j.metaEngine.item.MetaItemManager;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public final class MetaItemListener implements Listener {
    private final MetaItemManager itemManager;

    public MetaItemListener(MetaItemManager itemManager) {
        this.itemManager = itemManager;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Action action = event.getAction();

        if (action != Action.RIGHT_CLICK_AIR && action != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        itemManager.identify(event.getItem()).ifPresent(metaItem -> {
            MetaItemUseEvent metaEvent = new MetaItemUseEvent(event.getPlayer(), metaItem, event.getItem());
            Bukkit.getPluginManager().callEvent(metaEvent);

            if (metaEvent.isCancelled()) {
                event.setCancelled(true);
                return;
            }

            event.setCancelled(true);
            metaItem.onUse(event.getPlayer(), event.getItem());
        });
    }
}