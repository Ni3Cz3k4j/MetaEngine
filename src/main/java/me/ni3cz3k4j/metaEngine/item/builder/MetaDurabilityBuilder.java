package me.ni3cz3k4j.metaEngine.item.builder;

import me.ni3cz3k4j.metaEngine.item.component.MetaDurabilityComponent;

public final class MetaDurabilityBuilder {
    private Integer maxDamage;
    private Integer damage;
    private Boolean unbreakable;
    private Boolean hideDamageBar;

    public MetaDurabilityBuilder maxDamage(int maxDamage) {
        this.maxDamage = maxDamage;
        return this;
    }

    public MetaDurabilityBuilder damage(int damage) {
        this.damage = damage;
        return this;
    }

    public MetaDurabilityBuilder unbreakable(boolean unbreakable) {
        this.unbreakable = unbreakable;
        return this;
    }

    public MetaDurabilityBuilder hideDamageBar(boolean hideDamageBar) {
        this.hideDamageBar = hideDamageBar;
        return this;
    }

    public MetaDurabilityComponent build() {
        return new MetaDurabilityComponent(maxDamage, damage, unbreakable, hideDamageBar);
    }
}