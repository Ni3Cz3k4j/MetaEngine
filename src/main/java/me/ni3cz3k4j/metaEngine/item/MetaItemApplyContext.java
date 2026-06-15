package me.ni3cz3k4j.metaEngine.item;

import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

public record MetaItemApplyContext(JavaPlugin plugin, NamespacedKey itemIdKey) {}