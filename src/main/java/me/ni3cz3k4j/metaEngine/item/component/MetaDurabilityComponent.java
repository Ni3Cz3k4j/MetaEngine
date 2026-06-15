package me.ni3cz3k4j.metaEngine.item.component;

public record MetaDurabilityComponent(Integer maxDamage, Integer damage, Boolean unbreakable, Boolean hideDamageBar) implements MetaItemComponent {}