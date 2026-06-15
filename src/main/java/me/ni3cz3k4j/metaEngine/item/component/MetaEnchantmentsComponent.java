package me.ni3cz3k4j.metaEngine.item.component;

import java.util.Map;

public record MetaEnchantmentsComponent(Map<String, Integer> enchantments, boolean ignoreRestrictions, boolean hideEnchantments) implements MetaItemComponent {}