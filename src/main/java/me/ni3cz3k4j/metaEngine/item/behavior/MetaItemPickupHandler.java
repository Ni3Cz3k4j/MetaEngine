package me.ni3cz3k4j.metaEngine.item.behavior;

import me.ni3cz3k4j.metaEngine.item.event.MetaItemPickupEvent;

@FunctionalInterface
public interface MetaItemPickupHandler {
    void handle(MetaItemPickupEvent event);
}