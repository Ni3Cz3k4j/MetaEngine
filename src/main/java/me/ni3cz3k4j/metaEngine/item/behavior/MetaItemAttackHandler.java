package me.ni3cz3k4j.metaEngine.item.behavior;

import me.ni3cz3k4j.metaEngine.item.event.MetaItemAttackEntityEvent;

@FunctionalInterface
public interface MetaItemAttackHandler {
    void handle(MetaItemAttackEntityEvent event);
}