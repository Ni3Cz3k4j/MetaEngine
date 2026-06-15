package me.ni3cz3k4j.metaEngine.item.runtime;

import me.ni3cz3k4j.metaEngine.item.MetaItem;
import me.ni3cz3k4j.metaEngine.item.MetaItemManager;
import me.ni3cz3k4j.metaEngine.item.event.MetaItemEventDispatcher;
import me.ni3cz3k4j.metaEngine.item.event.MetaItemTickEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public final class MetaItemTickTask implements Runnable {
    private final MetaItemManager itemManager;
    private final MetaItemEventDispatcher dispatcher = new MetaItemEventDispatcher();

    public MetaItemTickTask(MetaItemManager itemManager) {
        this.itemManager = itemManager;
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            tickHand(player, player.getInventory().getItemInMainHand(), EquipmentSlot.HAND);
            tickHand(player, player.getInventory().getItemInOffHand(), EquipmentSlot.OFF_HAND);
            tickInventory(player);
        }
    }

    private void tickHand(Player player, ItemStack stack, EquipmentSlot slot) {
        Optional<MetaItem> optionalItem = itemManager.identify(stack);

        if (optionalItem.isEmpty()) {
            return;
        }

        MetaItem item = optionalItem.get();

        if (!item.behaviors().hasTickHandlers()) {
            return;
        }

        MetaItemTickEvent event = new MetaItemTickEvent(player, item, stack, slot, -1);
        dispatcher.dispatchTick(item, event);
    }

    private void tickInventory(Player player) {
        ItemStack[] contents = player.getInventory().getContents();

        for (int slot = 0; slot < contents.length; slot++) {
            ItemStack stack = contents[slot];

            Optional<MetaItem> optionalItem = itemManager.identify(stack);

            if (optionalItem.isEmpty()) {
                continue;
            }

            MetaItem item = optionalItem.get();

            if (!item.behaviors().hasTickHandlers()) {
                continue;
            }

            MetaItemTickEvent event = new MetaItemTickEvent(player, item, stack, null, slot);
            dispatcher.dispatchTick(item, event);
        }
    }
}