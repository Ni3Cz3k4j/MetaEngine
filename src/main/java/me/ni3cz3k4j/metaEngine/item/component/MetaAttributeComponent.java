package me.ni3cz3k4j.metaEngine.item.component;

import java.util.List;

public record MetaAttributeComponent(List<MetaAttributeModifier> modifiers) implements MetaItemComponent {}