package me.ni3cz3k4j.metaEngine;

import me.ni3cz3k4j.metaEngine.addon.MetaAddonContext;
import me.ni3cz3k4j.metaEngine.command.MetaCommand;
import me.ni3cz3k4j.metaEngine.item.MetaItemManager;
import me.ni3cz3k4j.metaEngine.item.runtime.MetaItemTickTask;
import me.ni3cz3k4j.metaEngine.listener.MetaItemListener;
import me.ni3cz3k4j.metaEngine.registry.MetaRegistries;
import me.ni3cz3k4j.metaEngine.resourcepack.MetaGeneratedResourcePack;
import me.ni3cz3k4j.metaEngine.resourcepack.MetaResourcePackGenerator;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class MetaEngine extends JavaPlugin {
    private MetaRegistries registries;
    private MetaItemManager itemManager;

    private final Map<String, JavaPlugin> namespaceOwners = new HashMap<>();

    @Override
    public void onLoad() {
        this.registries = new MetaRegistries();
    }

    @Override
    public void onEnable() {
        this.itemManager = new MetaItemManager(this, registries);
        registries.freezeAll();

        try {
            MetaResourcePackGenerator generator = new MetaResourcePackGenerator(this, registries, List.of());

            MetaGeneratedResourcePack pack = generator.generateZip();
            getLogger().info("Generated rp: " + pack.zip() + ".");
        } catch (IOException exception) {
            getLogger().severe("Failed to generate rp: " + exception.getMessage());
            exception.printStackTrace();
        }

        getServer().getPluginManager().registerEvents(new MetaItemListener(itemManager), this);
        getCommand("meta").setExecutor(new MetaCommand(itemManager));
        getServer().getScheduler().runTaskTimer(this, new MetaItemTickTask(itemManager), 1L, 1L);
    }

    public MetaAddonContext initAddon(JavaPlugin addonPlugin, String metaId) {
        validateMetaId(metaId);

        JavaPlugin currentOwner = namespaceOwners.get(metaId);

        if (currentOwner != null && currentOwner != addonPlugin) {
            throw new IllegalStateException("META_ID '" + metaId + "' is already owned by plugin " + currentOwner.getName() + ".");
        }

        namespaceOwners.put(metaId, addonPlugin);

        return new MetaAddonContext(addonPlugin, metaId, registries);
    }

    private void validateMetaId(String metaId) {
        if (metaId == null || !metaId.matches("[a-z0-9_.-]+")) {
            throw new IllegalArgumentException("Invalid META_ID: " + metaId + ".");
        }

        if (metaId.equals("minecraft") || metaId.equals("metaengine")) {
            throw new IllegalArgumentException("Reserved META_ID: " + metaId + ".");
        }
    }

    public MetaRegistries registries() {
        return registries;
    }

    public MetaItemManager itemManager() {
        return itemManager;
    }
}