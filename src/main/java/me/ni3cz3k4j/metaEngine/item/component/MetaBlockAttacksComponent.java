package me.ni3cz3k4j.metaEngine.item.component;

import java.util.List;

public record MetaBlockAttacksComponent(float blockDelaySeconds, float disableCooldownScale, List<String> bypassedByDamageTypes, String blockSound, String disableSound) implements MetaItemComponent {}