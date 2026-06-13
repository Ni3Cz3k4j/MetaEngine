package me.ni3cz3k4j.metaEngine;

import me.ni3cz3k4j.metaEngine.item.MetaItem;
import me.ni3cz3k4j.metaEngine.registry.MetaKey;
import me.ni3cz3k4j.metaEngine.registry.MetaRegistries;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

public final class MetaEngine extends JavaPlugin {
    // Testing 123
    MetaRegistries registries = new MetaRegistries();

    MetaKey fireWandKey = MetaKey.of("magic", "fire_wand");
    MetaItem fireWand = new MetaItem(fireWandKey, Material.STICK, "Fire Wand", ((player, itemStack) -> player.sendMessage("Whoosh!")));

    @Override
    public void onEnable() {
        registries.items().register(fireWandKey, fireWand);
        registries.freezeAll();
    }
}
