package me.ni3cz3k4j.metaEngine.item.builder;

import me.ni3cz3k4j.metaEngine.item.component.MetaCooldownComponent;
import me.ni3cz3k4j.metaEngine.registry.MetaKey;

public final class MetaCooldownBuilder {
    private final MetaKey key;
    private float seconds;
    private String group;

    public MetaCooldownBuilder(MetaKey key) {
        this.key = key;
        this.group = key.asString();
    }

    public MetaCooldownBuilder seconds(float seconds) {
        this.seconds = seconds;
        return this;
    }

    public MetaCooldownBuilder group(String group) {
        this.group = group;
        return this;
    }

    public MetaCooldownBuilder groupSelf() {
        this.group = key.asString();
        return this;
    }

    public MetaCooldownComponent build() {
        return new MetaCooldownComponent(seconds, group);
    }
}