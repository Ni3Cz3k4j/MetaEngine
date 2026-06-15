package me.ni3cz3k4j.metaEngine.item.builder;

import me.ni3cz3k4j.metaEngine.item.component.MetaCustomDataComponent;

import java.util.LinkedHashMap;
import java.util.Map;

public final class MetaCustomDataBuilder {
    private final Map<String, String> strings = new LinkedHashMap<>();
    private final Map<String, Integer> integers = new LinkedHashMap<>();
    private final Map<String, Long> longs = new LinkedHashMap<>();
    private final Map<String, Double> doubles = new LinkedHashMap<>();
    private final Map<String, Boolean> booleans = new LinkedHashMap<>();

    public MetaCustomDataBuilder string(String key, String value) {
        strings.put(key, value);
        return this;
    }

    public MetaCustomDataBuilder integer(String key, int value) {
        integers.put(key, value);
        return this;
    }

    public MetaCustomDataBuilder longValue(String key, long value) {
        longs.put(key, value);
        return this;
    }

    public MetaCustomDataBuilder doubleValue(String key, double value) {
        doubles.put(key, value);
        return this;
    }

    public MetaCustomDataBuilder booleanValue(String key, boolean value) {
        booleans.put(key, value);
        return this;
    }

    public MetaCustomDataComponent build() {
        return new MetaCustomDataComponent(
                Map.copyOf(strings),
                Map.copyOf(integers),
                Map.copyOf(longs),
                Map.copyOf(doubles),
                Map.copyOf(booleans)
        );
    }
}