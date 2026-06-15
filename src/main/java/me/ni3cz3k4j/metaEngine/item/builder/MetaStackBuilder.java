package me.ni3cz3k4j.metaEngine.item.builder;

import me.ni3cz3k4j.metaEngine.item.component.MetaStackComponent;

public final class MetaStackBuilder {
    private Integer maxStackSize;

    public MetaStackBuilder maxSize(int maxStackSize) {
        this.maxStackSize = maxStackSize;
        return this;
    }

    public MetaStackBuilder maxStackSize(int maxStackSize) {
        return maxSize(maxStackSize);
    }

    public MetaStackComponent build() {
        return new MetaStackComponent(maxStackSize);
    }
}