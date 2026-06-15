package me.ni3cz3k4j.metaEngine.item;

import me.ni3cz3k4j.metaEngine.registry.MetaKey;
import me.ni3cz3k4j.metaEngine.registry.MetaRegistry;

import java.util.function.Consumer;

public final class MetaItemRegistry {
    private final String namespace;
    private final MetaRegistry<MetaItem> registry;

    public MetaItemRegistry(String namespace, MetaRegistry<MetaItem> registry) {
        this.namespace = namespace;
        this.registry = registry;
    }

    public MetaItem register(String path, Consumer<MetaItemBuilder> consumer) {
        MetaKey key = MetaKey.of(namespace, path);

        MetaItemBuilder builder = new MetaItemBuilder(key);
        consumer.accept(builder);

        MetaItem item = builder.build();
        registry.register(key, item);

        return item;
    }

    public MetaKey key(String path) {
        return MetaKey.of(namespace, path);
    }
}