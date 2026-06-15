package me.ni3cz3k4j.metaEngine.item.component;

import java.util.Set;

public record MetaTooltipComponent(boolean hideTooltip, boolean hideAdditionalTooltip, String tooltipStyle, Set<String> hiddenParts) implements MetaItemComponent {}