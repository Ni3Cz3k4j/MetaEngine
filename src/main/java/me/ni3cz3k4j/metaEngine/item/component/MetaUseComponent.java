package me.ni3cz3k4j.metaEngine.item.component;

public record MetaUseComponent(Float useDurationSeconds, MetaUseAnimation animation, Boolean consumeOnUse, Boolean releaseToUse, String useSound, String breakSound) implements MetaItemComponent {}