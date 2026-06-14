package me.ni3cz3k4j.metaEngine.item;

import me.ni3cz3k4j.metaEngine.registry.MetaKey;
import me.ni3cz3k4j.metaEngine.registry.MetaRegistry;
import org.bukkit.Material;

public final class MetaItemRegistry {
    private final String namespace;
    private final MetaRegistry<MetaItem> registry;

    public MetaItemRegistry(String namespace, MetaRegistry<MetaItem> registry) {
        this.namespace = namespace;
        this.registry = registry;
    }

    public MetaItem register(String path, Material material, String displayName, MetaItemUseHandler useHandler) {
        MetaKey key = MetaKey.of(namespace, path);
        MetaItem item = new MetaItem(key, material, displayName, useHandler);
        registry.register(key, item);
        return item;
    }

    public MetaKey key(String path) {
        return MetaKey.of(namespace, path);
    }
}