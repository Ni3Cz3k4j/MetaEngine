package me.ni3cz3k4j.metaEngine.addon;

import me.ni3cz3k4j.metaEngine.item.MetaItem;
import me.ni3cz3k4j.metaEngine.registry.MetaKey;
import me.ni3cz3k4j.metaEngine.registry.MetaRegistries;

public final class MetaAddonContext {
    private final String metaId;
    private final MetaRegistries registries;

    public MetaAddonContext(String metaId, MetaRegistries registries) {
        this.metaId = metaId;
        this.registries = registries;
    }

    public String metaId() {
        return metaId;
    }

    public MetaKey key(String path) {
        return MetaKey.of(metaId, path);
    }

    public void registerItem(String path, MetaItem item) {
        MetaKey key = key(path);

        if (!item.key().equals(key)) {
            throw new IllegalArgumentException("MetaItem key mismatch. Expected " + key + ", got " + item.key() + ".");
        }

        registries.items().register(key, item);
    }

    public MetaRegistries registries() {
        return registries;
    }
}