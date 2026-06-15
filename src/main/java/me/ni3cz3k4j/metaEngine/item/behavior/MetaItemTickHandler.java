package me.ni3cz3k4j.metaEngine.item.behavior;

import me.ni3cz3k4j.metaEngine.item.event.MetaItemTickEvent;

@FunctionalInterface
public interface MetaItemTickHandler {
    void handle(MetaItemTickEvent event);
}