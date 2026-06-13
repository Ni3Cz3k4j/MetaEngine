package me.ni3cz3k4j.metaEngine.registry;

import me.ni3cz3k4j.metaEngine.item.MetaItem;

public final class MetaRegistries {
    private final MetaRegistry<MetaItem> items = new SimpleMetaRegistry<>("items");

    public MetaRegistry<MetaItem> items() {
        return items;
    }

    public void freezeAll() {
        items.freeze();
    }
}