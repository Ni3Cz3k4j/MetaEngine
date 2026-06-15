package me.ni3cz3k4j.metaEngine.item.builder;

import me.ni3cz3k4j.metaEngine.item.component.MetaTagsComponent;

import java.util.LinkedHashSet;
import java.util.Set;

public final class MetaTagsBuilder {
    private final Set<String> tags = new LinkedHashSet<>();

    public MetaTagsBuilder add(String tag) {
        tags.add(tag);
        return this;
    }

    public MetaTagsComponent build() {
        return new MetaTagsComponent(Set.copyOf(tags));
    }
}