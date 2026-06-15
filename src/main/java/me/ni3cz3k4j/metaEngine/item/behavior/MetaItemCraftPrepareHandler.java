package me.ni3cz3k4j.metaEngine.item.behavior;

import me.ni3cz3k4j.metaEngine.item.event.MetaItemCraftPrepareEvent;

@FunctionalInterface
public interface MetaItemCraftPrepareHandler {
    void handle(MetaItemCraftPrepareEvent event);
}