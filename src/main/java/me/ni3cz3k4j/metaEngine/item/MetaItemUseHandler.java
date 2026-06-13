package me.ni3cz3k4j.metaEngine.item;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@FunctionalInterface
public interface MetaItemUseHandler {
    void use(Player player, ItemStack itemStack);
}