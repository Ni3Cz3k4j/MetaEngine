package me.ni3cz3k4j.metaEngine.item.component;

public record MetaModelComponent(MetaModelMode mode, String modelId, String texturePath, String modelPath, String fallbackModel) implements MetaItemComponent {}