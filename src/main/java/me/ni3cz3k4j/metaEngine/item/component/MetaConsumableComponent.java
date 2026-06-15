package me.ni3cz3k4j.metaEngine.item.component;

import java.util.List;

public record MetaConsumableComponent(float consumeSeconds, MetaUseAnimation animation, boolean particles, String sounds, List<MetaConsumeEffect> effects) implements MetaItemComponent {}