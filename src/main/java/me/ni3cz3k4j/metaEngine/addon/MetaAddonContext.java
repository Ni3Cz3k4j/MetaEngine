package me.ni3cz3k4j.metaEngine.addon;

import me.ni3cz3k4j.metaEngine.item.MetaItem;
import me.ni3cz3k4j.metaEngine.item.MetaItemRegistry;
import me.ni3cz3k4j.metaEngine.registry.MetaKey;
import me.ni3cz3k4j.metaEngine.registry.MetaRegistries;
import org.bukkit.plugin.java.JavaPlugin;

public final class MetaAddonContext {
    private final JavaPlugin plugin;
    private final String metaId;
    private final MetaRegistries registries;

    private final MetaItemRegistry items;

    public MetaAddonContext(JavaPlugin plugin, String metaId, MetaRegistries registries) {
        this.plugin = plugin;
        this.metaId = metaId;
        this.registries = registries;
        this.items = new MetaItemRegistry(metaId, registries.items());
    }

    public JavaPlugin plugin() {
        return plugin;
    }

    public String metaId() {
        return metaId;
    }

    public MetaKey key(String path) {
        return MetaKey.of(metaId, path);
    }

    public MetaItemRegistry items() {
        return items;
    }

    public MetaRegistries registries() {
        return registries;
    }
}