package me.ni3cz3k4j.metaEngine.item.component;

import org.bukkit.Color;

import java.util.List;

public record MetaCustomModelDataComponent(List<String> strings, List<Float> floats, List<Boolean> flags, List<Color> colors) implements MetaItemComponent {}