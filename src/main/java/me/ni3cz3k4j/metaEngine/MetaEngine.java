package me.ni3cz3k4j.metaEngine;

import me.ni3cz3k4j.metaEngine.addon.MetaAddon;
import me.ni3cz3k4j.metaEngine.addon.MetaAddonContext;
import me.ni3cz3k4j.metaEngine.api.MetaItems;
import me.ni3cz3k4j.metaEngine.command.MetaCommand;
import me.ni3cz3k4j.metaEngine.command.tab.MetaTabCompleter;
import me.ni3cz3k4j.metaEngine.item.MetaItemManager;
import me.ni3cz3k4j.metaEngine.item.listener.MetaDeathProtectionListener;
import me.ni3cz3k4j.metaEngine.item.listener.MetaItemListener;
import me.ni3cz3k4j.metaEngine.item.runtime.MetaItemTickTask;
import me.ni3cz3k4j.metaEngine.registry.MetaRegistries;
import me.ni3cz3k4j.metaEngine.resourcepack.MetaGeneratedResourcePack;
import me.ni3cz3k4j.metaEngine.resourcepack.MetaResourcePackGenerator;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public final class MetaEngine extends JavaPlugin {
    private MetaRegistries registries;
    private MetaItemManager itemManager;
    private MetaItems itemsApi;
    private MetaGeneratedResourcePack lastGeneratedPack;
    private final Map<String, MetaAddon> addons = new HashMap<>();

    @Override
    public void onLoad() {
        this.registries = new MetaRegistries();
    }

    @Override
    public void onEnable() {
        this.itemManager = new MetaItemManager(this, registries);
        this.itemsApi = new MetaItems(itemManager);

        registries.freezeAll();
        generateResourcePack();

        getServer().getPluginManager().registerEvents(new MetaItemListener(itemManager), this);
        getServer().getPluginManager().registerEvents(new MetaDeathProtectionListener(itemManager), this);

        getServer().getScheduler().runTaskTimer(
                this,
                new MetaItemTickTask(itemManager),
                1L,
                1L
        );

        if (getCommand("meta") != null) {
            getCommand("meta").setExecutor(new MetaCommand(this, itemManager));
            getCommand("meta").setTabCompleter(new MetaTabCompleter(registries));
        }
    }

    public MetaAddonContext initAddon(JavaPlugin addonPlugin, String metaId) {
        if (registries == null) {
            throw new IllegalStateException("MetaEngine registries are not initialized yet.");
        }

        validateMetaId(metaId);

        MetaAddon currentAddon = addons.get(metaId);

        if (currentAddon != null && currentAddon.plugin() != addonPlugin) {
            throw new IllegalStateException(
                    "META_ID '" + metaId + "' is already owned by plugin " + currentAddon.plugin().getName() + "."
            );
        }

        if (registries.items().isFrozen()) {
            throw new IllegalStateException(
                    "META_ID '" + metaId + "' was initialized too late. " +
                            "Call MetaEngineProvider.initMetaEngine(...) in plugin onLoad(), not onEnable()."
            );
        }

        MetaAddon addon = new MetaAddon(addonPlugin, metaId);
        addons.put(metaId, addon);

        return new MetaAddonContext(addonPlugin, metaId, registries);
    }

    public MetaGeneratedResourcePack regenerateResourcePack() {
        return generateResourcePack();
    }

    private MetaGeneratedResourcePack generateResourcePack() {
        try {
            MetaResourcePackGenerator generator = new MetaResourcePackGenerator(
                    this,
                    registries,
                    addons.values()
            );

            this.lastGeneratedPack = generator.generateZip();
            getLogger().info("Generated resource pack: " + lastGeneratedPack.zip() + ".");
            return lastGeneratedPack;
        } catch (IOException exception) {
            getLogger().severe("Failed to generate resource pack: " + exception.getMessage());
            exception.printStackTrace();
            return null;
        }
    }

    private void validateMetaId(String metaId) {
        if (metaId == null || !metaId.matches("[a-z0-9_.-]+")) {
            throw new IllegalArgumentException("Invalid META_ID: " + metaId + ".");
        }

        if (metaId.equals("minecraft") || metaId.equals("metaengine") || metaId.equals("paper") || metaId.equals("spigot") || metaId.equals("bukkit")) {
            throw new IllegalArgumentException("Reserved META_ID: " + metaId + ".");
        }
    }

    public MetaRegistries registries() {
        return registries;
    }

    public MetaItemManager itemManager() {
        return itemManager;
    }

    public MetaItems itemsApi() {
        return itemsApi;
    }

    public Collection<MetaAddon> addons() {
        return addons.values();
    }

    public MetaGeneratedResourcePack lastGeneratedPack() {
        return lastGeneratedPack;
    }
}