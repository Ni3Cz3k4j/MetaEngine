package me.ni3cz3k4j.metaEngine.item.component;

import java.util.List;

public record MetaToolComponent(float defaultMiningSpeed, int damagePerBlock, List<MetaToolRule> rules) implements MetaItemComponent {}