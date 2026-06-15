package me.ni3cz3k4j.metaEngine.item.component;

import java.util.Set;

public record MetaDamageResistanceComponent(Set<String> damageTypes) implements MetaItemComponent {}