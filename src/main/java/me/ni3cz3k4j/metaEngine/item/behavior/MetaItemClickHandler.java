package me.ni3cz3k4j.metaEngine.item.behavior;

import me.ni3cz3k4j.metaEngine.item.event.MetaItemClickEvent;

@FunctionalInterface
public interface MetaItemClickHandler {
    void handle(MetaItemClickEvent event);
}