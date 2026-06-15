package me.ni3cz3k4j.metaEngine.item.component;

import java.util.Map;

public record MetaCustomDataComponent(Map<String, String> strings, Map<String, Integer> integers, Map<String, Long> longs, Map<String, Double> doubles, Map<String, Boolean> booleans) implements MetaItemComponent {}