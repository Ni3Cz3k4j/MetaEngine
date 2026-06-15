package me.ni3cz3k4j.metaEngine.item.component;

public record MetaPotionConsumeEffect(String effectKey, int durationTicks, int amplifier, float probability) implements MetaConsumeEffect {}