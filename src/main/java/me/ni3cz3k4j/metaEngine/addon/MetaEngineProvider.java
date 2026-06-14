package me.ni3cz3k4j.metaEngine.addon;

import me.ni3cz3k4j.metaEngine.MetaEngine;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class MetaEngineProvider {
    private MetaEngineProvider() {}

    public static MetaAddonContext initMetaEngine(JavaPlugin addonPlugin, String metaId) {
        Plugin plugin = addonPlugin.getServer().getPluginManager().getPlugin("MetaEngine");

        if (!(plugin instanceof MetaEngine metaEngine)) {
            throw new IllegalStateException("MetaEngine is not loaded.");
        }

        return metaEngine.initAddon(addonPlugin, metaId);
    }
}