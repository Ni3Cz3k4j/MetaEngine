package me.ni3cz3k4j.metaEngine.item.builder;

import me.ni3cz3k4j.metaEngine.item.component.MetaToolComponent;
import me.ni3cz3k4j.metaEngine.item.component.MetaToolRule;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public final class MetaToolBuilder {
    private float defaultMiningSpeed = 1.0f;
    private int damagePerBlock = 1;
    private final List<MetaToolRule> rules = new ArrayList<>();

    public MetaToolBuilder defaultMiningSpeed(float defaultMiningSpeed) {
        this.defaultMiningSpeed = defaultMiningSpeed;
        return this;
    }

    public MetaToolBuilder damagePerBlock(int damagePerBlock) {
        this.damagePerBlock = damagePerBlock;
        return this;
    }

    public MetaToolBuilder rule(Set<Material> materials, Float speed, Boolean correctForDrops) {
        this.rules.add(new MetaToolRule(materials, speed, correctForDrops));
        return this;
    }

    public MetaToolComponent build() {
        return new MetaToolComponent(defaultMiningSpeed, damagePerBlock, List.copyOf(rules));
    }
}