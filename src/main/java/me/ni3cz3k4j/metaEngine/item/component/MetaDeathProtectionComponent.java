package me.ni3cz3k4j.metaEngine.item.component;

import java.util.List;

public record MetaDeathProtectionComponent(List<MetaDeathProtectionEffect> effects) implements MetaItemComponent {}