package me.ni3cz3k4j.metaEngine.item.listener;

import me.ni3cz3k4j.metaEngine.item.MetaItem;
import me.ni3cz3k4j.metaEngine.item.MetaItemManager;
import me.ni3cz3k4j.metaEngine.item.event.*;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import java.util.Optional;

public final class MetaItemListener implements Listener {
    private final MetaItemManager itemManager;
    private final MetaItemEventDispatcher dispatcher = new MetaItemEventDispatcher();

    public MetaItemListener(MetaItemManager itemManager) {
        this.itemManager = itemManager;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        ItemStack stack = event.getItem();
        Optional<MetaItem> optionalItem = itemManager.identify(stack);

        if (optionalItem.isEmpty()) {
            return;
        }

        MetaItem item = optionalItem.get();

        MetaItemUseEvent useEvent = new MetaItemUseEvent(
                event.getPlayer(),
                item,
                stack,
                event.getAction(),
                event.getHand()
        );

        dispatcher.dispatchUse(item, useEvent);

        if (useEvent.isCancelled()) {
            event.setCancelled(true);
            return;
        }

        MetaItemClickEvent clickEvent = new MetaItemClickEvent(
                event.getPlayer(),
                item,
                stack,
                event.getAction(),
                event.getHand(),
                event.getClickedBlock()
        );

        dispatcher.dispatchClick(item, clickEvent);

        if (clickEvent.isCancelled()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onAttack(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player player)) {
            return;
        }

        ItemStack stack = player.getInventory().getItemInMainHand();

        Optional<MetaItem> optionalItem = itemManager.identify(stack);

        if (optionalItem.isEmpty()) {
            return;
        }

        MetaItem item = optionalItem.get();

        MetaItemAttackEntityEvent metaEvent = new MetaItemAttackEntityEvent(
                player,
                item,
                stack,
                event.getEntity(),
                event.getDamage()
        );

        dispatcher.dispatchAttack(item, metaEvent);

        if (metaEvent.isCancelled()) {
            event.setCancelled(true);
            return;
        }

        event.setDamage(metaEvent.damage());
    }

    @EventHandler
    public void onConsume(PlayerItemConsumeEvent event) {
        ItemStack stack = event.getItem();

        Optional<MetaItem> optionalItem = itemManager.identify(stack);

        if (optionalItem.isEmpty()) {
            return;
        }

        MetaItem item = optionalItem.get();

        MetaItemConsumeEvent metaEvent = new MetaItemConsumeEvent(
                event.getPlayer(),
                item,
                stack
        );

        dispatcher.dispatchConsume(item, metaEvent);

        if (metaEvent.isCancelled()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        Item itemEntity = event.getItemDrop();
        ItemStack stack = itemEntity.getItemStack();

        Optional<MetaItem> optionalItem = itemManager.identify(stack);

        if (optionalItem.isEmpty()) {
            return;
        }

        MetaItem item = optionalItem.get();

        MetaItemDropEvent metaEvent = new MetaItemDropEvent(
                event.getPlayer(),
                item,
                stack,
                itemEntity
        );

        dispatcher.dispatchDrop(item, metaEvent);

        if (metaEvent.isCancelled()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPickup(EntityPickupItemEvent event) {
        if (!(event.getEntity() instanceof Player player)) {
            return;
        }

        Item itemEntity = event.getItem();
        ItemStack stack = itemEntity.getItemStack();

        Optional<MetaItem> optionalItem = itemManager.identify(stack);

        if (optionalItem.isEmpty()) {
            return;
        }

        MetaItem item = optionalItem.get();

        MetaItemPickupEvent metaEvent = new MetaItemPickupEvent(
                player,
                item,
                stack,
                itemEntity
        );

        dispatcher.dispatchPickup(item, metaEvent);

        if (metaEvent.isCancelled()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) {
            return;
        }

        ItemStack stack = event.getCurrentItem();

        Optional<MetaItem> optionalItem = itemManager.identify(stack);

        if (optionalItem.isEmpty()) {
            return;
        }

        MetaItem item = optionalItem.get();

        MetaItemInventoryClickEvent metaEvent = new MetaItemInventoryClickEvent(
                player,
                item,
                stack,
                event.getClick(),
                event.getAction(),
                event.getInventory().getType(),
                event.getRawSlot(),
                event.getSlot()
        );

        dispatcher.dispatchInventoryClick(item, metaEvent);

        if (metaEvent.isCancelled()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPrepareCraft(PrepareItemCraftEvent event) {
        if (!(event.getView().getPlayer() instanceof Player player)) {
            return;
        }

        Recipe recipe = event.getRecipe();

        for (ItemStack stack : event.getInventory().getMatrix()) {
            Optional<MetaItem> optionalItem = itemManager.identify(stack);

            if (optionalItem.isEmpty()) {
                continue;
            }

            MetaItem item = optionalItem.get();

            MetaItemCraftPrepareEvent metaEvent = new MetaItemCraftPrepareEvent(
                    player,
                    item,
                    stack,
                    recipe
            );

            dispatcher.dispatchCraftPrepare(item, metaEvent);

            if (metaEvent.isCancelled()) {
                event.getInventory().setResult(null);
                return;
            }
        }
    }

    @EventHandler
    public void onCraft(CraftItemEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) {
            return;
        }

        Recipe recipe = event.getRecipe();

        for (ItemStack stack : event.getInventory().getMatrix()) {
            Optional<MetaItem> optionalItem = itemManager.identify(stack);

            if (optionalItem.isEmpty()) {
                continue;
            }

            MetaItem item = optionalItem.get();

            MetaItemCraftEvent metaEvent = new MetaItemCraftEvent(
                    player,
                    item,
                    stack,
                    recipe
            );

            dispatcher.dispatchCraft(item, metaEvent);

            if (metaEvent.isCancelled()) {
                event.setCancelled(true);
                return;
            }
        }
    }
}