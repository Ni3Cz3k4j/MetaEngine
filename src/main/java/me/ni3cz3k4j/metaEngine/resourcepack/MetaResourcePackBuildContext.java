package me.ni3cz3k4j.metaEngine.resourcepack;

import me.ni3cz3k4j.metaEngine.addon.MetaAddon;
import me.ni3cz3k4j.metaEngine.registry.MetaRegistries;
import org.bukkit.plugin.java.JavaPlugin;

import java.nio.file.Path;
import java.util.Collection;

public final class MetaResourcePackBuildContext {
    private final JavaPlugin metaEnginePlugin;
    private final MetaRegistries registries;
    private final Collection<MetaAddon> addons;
    private final Path root;

    public MetaResourcePackBuildContext(JavaPlugin metaEnginePlugin, MetaRegistries registries, Collection<MetaAddon> addons, Path root) {
        this.metaEnginePlugin = metaEnginePlugin;
        this.registries = registries;
        this.addons = addons;
        this.root = root;
    }

    public JavaPlugin metaEnginePlugin() {
        return metaEnginePlugin;
    }

    public MetaRegistries registries() {
        return registries;
    }

    public Collection<MetaAddon> addons() {
        return addons;
    }

    public Path root() {
        return root;
    }
}