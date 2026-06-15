package me.ni3cz3k4j.metaEngine.item.component;

import org.bukkit.Material;

import java.util.List;

public record MetaRepairComponent(Integer repairCost, List<Material> repairMaterials, List<String> repairTags) implements MetaItemComponent {}