package me.ni3cz3k4j.metaEngine.item.behavior;

import me.ni3cz3k4j.metaEngine.item.event.MetaItemUseEvent;

@FunctionalInterface
public interface MetaItemUseHandler {
    void handle(MetaItemUseEvent event);
}