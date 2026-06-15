package me.ni3cz3k4j.metaEngine.item.builder;

import me.ni3cz3k4j.metaEngine.item.component.MetaTooltipComponent;

import java.util.LinkedHashSet;
import java.util.Set;

public final class MetaTooltipBuilder {
    private boolean hideTooltip;
    private boolean hideAdditionalTooltip;
    private String tooltipStyle;
    private final Set<String> hiddenParts = new LinkedHashSet<>();

    public MetaTooltipBuilder hideTooltip(boolean hideTooltip) {
        this.hideTooltip = hideTooltip;
        return this;
    }

    public MetaTooltipBuilder hideAdditionalTooltip(boolean hideAdditionalTooltip) {
        this.hideAdditionalTooltip = hideAdditionalTooltip;
        return this;
    }

    public MetaTooltipBuilder style(String tooltipStyle) {
        this.tooltipStyle = tooltipStyle;
        return this;
    }

    public MetaTooltipBuilder hidePart(String part) {
        this.hiddenParts.add(part);
        return this;
    }

    public MetaTooltipComponent build() {
        return new MetaTooltipComponent(hideTooltip, hideAdditionalTooltip, tooltipStyle, Set.copyOf(hiddenParts));
    }
}