package me.ni3cz3k4j.metaEngine.item.builder;

import me.ni3cz3k4j.metaEngine.item.component.MetaRepairComponent;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public final class MetaRepairBuilder {
    private Integer repairCost;
    private final List<Material> repairMaterials = new ArrayList<>();
    private final List<String> repairTags = new ArrayList<>();

    public MetaRepairBuilder repairCost(int repairCost) {
        this.repairCost = repairCost;
        return this;
    }

    public MetaRepairBuilder material(Material material) {
        this.repairMaterials.add(material);
        return this;
    }

    public MetaRepairBuilder tag(String tag) {
        this.repairTags.add(tag);
        return this;
    }

    public MetaRepairComponent build() {
        return new MetaRepairComponent(repairCost, List.copyOf(repairMaterials), List.copyOf(repairTags));
    }
}