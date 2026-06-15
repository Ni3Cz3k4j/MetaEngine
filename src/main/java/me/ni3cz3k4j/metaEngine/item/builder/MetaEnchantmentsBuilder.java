package me.ni3cz3k4j.metaEngine.item.builder;

import me.ni3cz3k4j.metaEngine.item.component.MetaEnchantmentsComponent;

import java.util.LinkedHashMap;
import java.util.Map;

public final class MetaEnchantmentsBuilder {
    private final Map<String, Integer> enchantments = new LinkedHashMap<>();
    private boolean ignoreRestrictions;
    private boolean hideEnchantments;

    public MetaEnchantmentsBuilder add(String enchantmentKey, int level) {
        enchantments.put(enchantmentKey, level);
        return this;
    }

    public MetaEnchantmentsBuilder ignoreRestrictions(boolean ignoreRestrictions) {
        this.ignoreRestrictions = ignoreRestrictions;
        return this;
    }

    public MetaEnchantmentsBuilder hide(boolean hideEnchantments) {
        this.hideEnchantments = hideEnchantments;
        return this;
    }

    public MetaEnchantmentsComponent build() {
        return new MetaEnchantmentsComponent(Map.copyOf(enchantments), ignoreRestrictions, hideEnchantments);
    }
}