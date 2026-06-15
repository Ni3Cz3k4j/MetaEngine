package me.ni3cz3k4j.metaEngine.item.component;

import java.util.List;

public record MetaDisplayComponent(String itemName, String displayName, List<String> lore) implements MetaItemComponent {}