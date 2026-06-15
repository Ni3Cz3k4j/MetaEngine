package me.ni3cz3k4j.metaEngine.item.behavior;

import me.ni3cz3k4j.metaEngine.item.event.MetaItemConsumeEvent;

@FunctionalInterface
public interface MetaItemConsumeHandler {
    void handle(MetaItemConsumeEvent event);
}