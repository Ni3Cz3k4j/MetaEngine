package me.ni3cz3k4j.metaEngine.item.builder;

import me.ni3cz3k4j.metaEngine.item.component.MetaDisplayComponent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class MetaDisplayBuilder {
    private String itemName;
    private String displayName;
    private String translationKey;
    private String translationFallback;
    private final List<String> lore = new ArrayList<>();

    public MetaDisplayBuilder itemName(String itemName) {
        this.itemName = itemName;
        return this;
    }

    public MetaDisplayBuilder name(String itemName) {
        this.itemName = itemName;
        return this;
    }

    public MetaDisplayBuilder displayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public MetaDisplayBuilder translatable(String key) {
        this.translationKey = key;
        return this;
    }

    public MetaDisplayBuilder translatable(String key, String fallback) {
        this.translationKey = key;
        this.translationFallback = fallback;
        return this;
    }

    public MetaDisplayBuilder translationKey(String key) {
        this.translationKey = key;
        return this;
    }

    public MetaDisplayBuilder fallback(String fallback) {
        this.translationFallback = fallback;
        return this;
    }

    public MetaDisplayBuilder lore(String... lines) {
        this.lore.addAll(Arrays.asList(lines));
        return this;
    }

    public MetaDisplayBuilder lore(List<String> lines) {
        this.lore.addAll(lines);
        return this;
    }

    public MetaDisplayComponent build() {
        return new MetaDisplayComponent(itemName, displayName, translationKey, translationFallback, List.copyOf(lore));
    }
}