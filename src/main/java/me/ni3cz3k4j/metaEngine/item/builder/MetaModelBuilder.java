package me.ni3cz3k4j.metaEngine.item.builder;

import me.ni3cz3k4j.metaEngine.item.component.MetaCustomModelDataComponent;
import me.ni3cz3k4j.metaEngine.item.component.MetaModelComponent;
import me.ni3cz3k4j.metaEngine.item.component.MetaModelMode;
import me.ni3cz3k4j.metaEngine.registry.MetaKey;

import java.util.List;

public final class MetaModelBuilder {
    private final MetaKey key;
    private MetaModelMode mode = MetaModelMode.GENERATED_TEXTURE;
    private String modelId;
    private String texturePath;
    private String modelPath;
    private String fallbackModel;

    public MetaModelBuilder(MetaKey key) {
        this.key = key;
    }

    public MetaModelBuilder texture(String path) {
        this.mode = MetaModelMode.GENERATED_TEXTURE;
        this.modelId = path;
        this.texturePath = "item/" + path;
        this.modelPath = "item/" + path;
        return this;
    }

    public MetaModelBuilder generatedTexture(String path) {
        return texture(path);
    }

    public MetaModelBuilder customModel(String path) {
        this.mode = MetaModelMode.CUSTOM_MODEL;
        this.modelId = path;
        this.texturePath = null;
        this.modelPath = "item/" + path;
        return this;
    }

    public MetaModelBuilder vanilla(String modelPath) {
        this.mode = MetaModelMode.VANILLA_MODEL;
        this.modelId = modelPath;
        this.texturePath = null;
        this.modelPath = modelPath;
        return this;
    }

    public MetaModelBuilder fallback(String fallbackModel) {
        this.fallbackModel = fallbackModel;
        return this;
    }

    public MetaModelComponent build() {
        String id = modelId != null ? modelId : key.path();
        String model = modelPath != null ? modelPath : "item/" + id;
        String texture = texturePath;

        return new MetaModelComponent(mode, id, texture, model, fallbackModel);
    }

    public MetaCustomModelDataComponent buildCustomModelData() {
        String id = modelId != null ? modelId : key.path();
        return new MetaCustomModelDataComponent(List.of(id), List.of(), List.of(), List.of());
    }
}