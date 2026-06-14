package me.ni3cz3k4j.metaEngine;

import me.ni3cz3k4j.metaEngine.command.MetaCommand;
import me.ni3cz3k4j.metaEngine.item.MetaItemManager;
import me.ni3cz3k4j.metaEngine.item.MetaItemRegistry;
import me.ni3cz3k4j.metaEngine.listener.MetaItemListener;
import me.ni3cz3k4j.metaEngine.registry.MetaRegistries;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

public final class MetaEngine extends JavaPlugin {
    private MetaRegistries registries;
    private MetaItemManager itemManager;

    @Override
    public void onLoad() {
        this.registries = new MetaRegistries();
    }

    @Override
    public void onEnable() {
        this.itemManager = new MetaItemManager(this, registries);
        registerTestItem();
        registries.freezeAll();

        getServer().getPluginManager().registerEvents(new MetaItemListener(itemManager), this);
        getCommand("meta").setExecutor(new MetaCommand(itemManager));
    }

    private void registerTestItem() {
        MetaItemRegistry items = new MetaItemRegistry("test", registries.items());

        items.register("fire_wand", Material.STICK, "Fire Wand", (player, itemStack) -> player.sendMessage("Whoosh!"));
    }

    public MetaRegistries registries() {
        return registries;
    }

    public MetaItemManager itemManager() {
        return itemManager;
    }
}