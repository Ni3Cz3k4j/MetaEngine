package me.ni3cz3k4j.metaEngine.item.settings;

public final class MetaModelSettings {
    private final String modelId;
    private final String texturePath;
    private final String modelPath;
    private final boolean generateModel;

    private MetaModelSettings(String modelId, String texturePath, String modelPath, boolean generateModel) {
        this.modelId = modelId;
        this.texturePath = texturePath;
        this.modelPath = modelPath;
        this.generateModel = generateModel;
    }

    public static MetaModelSettings texture(String path) {
        return new MetaModelSettings(path, "item/" + path, "item/" + path, true);
    }

    public static MetaModelSettings customModel(String path) {
        return new MetaModelSettings(path, null, "item/" + path, false);
    }

    public String modelId() {
        return modelId;
    }

    public String texturePath() {
        return texturePath;
    }

    public String modelPath() {
        return modelPath;
    }

    public boolean generateModel() {
        return generateModel;
    }
}