package me.ni3cz3k4j.metaEngine.item.component;

import org.bukkit.Material;

import java.util.Set;

public record MetaToolRule(Set<Material> materials, Float speed, Boolean correctForDrops) {}