package me.ni3cz3k4j.metaEngine.item.behavior;

import me.ni3cz3k4j.metaEngine.item.event.MetaItemCraftEvent;

@FunctionalInterface
public interface MetaItemCraftHandler {
    void handle(MetaItemCraftEvent event);
}