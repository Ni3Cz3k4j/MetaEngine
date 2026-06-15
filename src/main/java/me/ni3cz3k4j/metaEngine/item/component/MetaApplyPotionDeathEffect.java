package me.ni3cz3k4j.metaEngine.item.component;

public record MetaApplyPotionDeathEffect(String effectKey, int durationTicks, int amplifier, boolean ambient, boolean particles, boolean icon) implements MetaDeathProtectionEffect {}