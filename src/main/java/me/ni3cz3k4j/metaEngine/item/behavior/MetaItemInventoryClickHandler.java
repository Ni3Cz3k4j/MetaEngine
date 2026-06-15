package me.ni3cz3k4j.metaEngine.item.behavior;

import me.ni3cz3k4j.metaEngine.item.event.MetaItemInventoryClickEvent;

@FunctionalInterface
public interface MetaItemInventoryClickHandler {
    void handle(MetaItemInventoryClickEvent event);
}