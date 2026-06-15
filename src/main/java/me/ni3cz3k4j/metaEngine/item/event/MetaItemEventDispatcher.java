package me.ni3cz3k4j.metaEngine.item.event;

import me.ni3cz3k4j.metaEngine.item.MetaItem;
import me.ni3cz3k4j.metaEngine.item.behavior.*;
import org.bukkit.Bukkit;

public final class MetaItemEventDispatcher {
    public void dispatchUse(MetaItem item, MetaItemUseEvent event) {
        Bukkit.getPluginManager().callEvent(event);

        if (event.isCancelled()) {
            return;
        }

        for (MetaItemUseHandler handler : item.behaviors().useHandlers()) {
            handler.handle(event);

            if (event.isCancelled()) {
                return;
            }
        }
    }

    public void dispatchClick(MetaItem item, MetaItemClickEvent event) {
        Bukkit.getPluginManager().callEvent(event);

        if (event.isCancelled()) {
            return;
        }

        for (MetaItemClickHandler handler : item.behaviors().clickHandlers()) {
            handler.handle(event);

            if (event.isCancelled()) {
                return;
            }
        }
    }

    public void dispatchAttack(MetaItem item, MetaItemAttackEntityEvent event) {
        Bukkit.getPluginManager().callEvent(event);

        if (event.isCancelled()) {
            return;
        }

        for (MetaItemAttackHandler handler : item.behaviors().attackHandlers()) {
            handler.handle(event);

            if (event.isCancelled()) {
                return;
            }
        }
    }

    public void dispatchConsume(MetaItem item, MetaItemConsumeEvent event) {
        Bukkit.getPluginManager().callEvent(event);

        if (event.isCancelled()) {
            return;
        }

        for (MetaItemConsumeHandler handler : item.behaviors().consumeHandlers()) {
            handler.handle(event);

            if (event.isCancelled()) {
                return;
            }
        }
    }

    public void dispatchDrop(MetaItem item, MetaItemDropEvent event) {
        Bukkit.getPluginManager().callEvent(event);

        if (event.isCancelled()) {
            return;
        }

        for (MetaItemDropHandler handler : item.behaviors().dropHandlers()) {
            handler.handle(event);

            if (event.isCancelled()) {
                return;
            }
        }
    }

    public void dispatchPickup(MetaItem item, MetaItemPickupEvent event) {
        Bukkit.getPluginManager().callEvent(event);

        if (event.isCancelled()) {
            return;
        }

        for (MetaItemPickupHandler handler : item.behaviors().pickupHandlers()) {
            handler.handle(event);

            if (event.isCancelled()) {
                return;
            }
        }
    }

    public void dispatchInventoryClick(MetaItem item, MetaItemInventoryClickEvent event) {
        Bukkit.getPluginManager().callEvent(event);

        if (event.isCancelled()) {
            return;
        }

        for (MetaItemInventoryClickHandler handler : item.behaviors().inventoryClickHandlers()) {
            handler.handle(event);

            if (event.isCancelled()) {
                return;
            }
        }
    }

    public void dispatchCraftPrepare(MetaItem item, MetaItemCraftPrepareEvent event) {
        Bukkit.getPluginManager().callEvent(event);

        if (event.isCancelled()) {
            return;
        }

        for (MetaItemCraftPrepareHandler handler : item.behaviors().craftPrepareHandlers()) {
            handler.handle(event);

            if (event.isCancelled()) {
                return;
            }
        }
    }

    public void dispatchCraft(MetaItem item, MetaItemCraftEvent event) {
        Bukkit.getPluginManager().callEvent(event);

        if (event.isCancelled()) {
            return;
        }

        for (MetaItemCraftHandler handler : item.behaviors().craftHandlers()) {
            handler.handle(event);

            if (event.isCancelled()) {
                return;
            }
        }
    }

    public void dispatchTick(MetaItem item, MetaItemTickEvent event) {
        Bukkit.getPluginManager().callEvent(event);

        for (MetaItemTickHandler handler : item.behaviors().tickHandlers()) {
            handler.handle(event);
        }
    }
}