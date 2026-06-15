package me.ni3cz3k4j.metaEngine.item.component;

public record MetaFoodComponent(int nutrition, float saturation, boolean alwaysEat) implements MetaItemComponent {}