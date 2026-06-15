package me.ni3cz3k4j.metaEngine.item.component;

public record MetaEquipmentComponent(MetaEquipmentSlot slot, String equipSound, String assetsId, String cameraOverlay, Boolean dispensable, Boolean swappable, Boolean damageOnHurt) implements MetaItemComponent {}