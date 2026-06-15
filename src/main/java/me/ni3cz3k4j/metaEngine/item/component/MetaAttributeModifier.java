package me.ni3cz3k4j.metaEngine.item.component;

public record MetaAttributeModifier(String attribute, String id, double amount, MetaAttributeOperation operation, MetaEquipmentSlot slot) {}