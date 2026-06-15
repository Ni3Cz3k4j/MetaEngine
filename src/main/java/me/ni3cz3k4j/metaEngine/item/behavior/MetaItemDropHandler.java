package me.ni3cz3k4j.metaEngine.item.behavior;

import me.ni3cz3k4j.metaEngine.item.event.MetaItemDropEvent;

@FunctionalInterface
public interface MetaItemDropHandler {
    void handle(MetaItemDropEvent event);
}