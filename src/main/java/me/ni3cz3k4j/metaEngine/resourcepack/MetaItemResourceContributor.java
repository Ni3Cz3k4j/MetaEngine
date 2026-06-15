package me.ni3cz3k4j.metaEngine.resourcepack;

import me.ni3cz3k4j.metaEngine.item.MetaItem;
import me.ni3cz3k4j.metaEngine.item.component.MetaModelComponent;
import me.ni3cz3k4j.metaEngine.item.component.MetaModelMode;
import org.bukkit.Material;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class MetaItemResourceContributor implements MetaResourcePackContributor {
    @Override
    public void contribute(MetaResourcePackBuildContext context) throws IOException {
        Map<Material, List<MetaItem>> itemsByMaterial = groupItemsByMaterial(context);

        for (Map.Entry<Material, List<MetaItem>> entry : itemsByMaterial.entrySet()) {
            generateItemDefinition(context.root(), entry.getKey(), entry.getValue());
        }

        for (MetaItem item : context.registries().items().values()) {
            MetaModelComponent model = model(item);

            if (model == null) {
                continue;
            }

            generateModelIfNeeded(context.root(), item, model);
        }
    }

    private Map<Material, List<MetaItem>> groupItemsByMaterial(MetaResourcePackBuildContext context) {
        Map<Material, List<MetaItem>> result = new LinkedHashMap<>();

        for (MetaItem item : context.registries().items().values()) {
            MetaModelComponent model = model(item);

            if (model == null) {
                continue;
            }

            if (model.mode() == MetaModelMode.RAW_ITEM_DEFINITION) {
                continue;
            }

            Material material = item.material();

            if (material == null || material.isAir()) {
                continue;
            }

            result.computeIfAbsent(material, ignored -> new ArrayList<>()).add(item);
        }

        return result;
    }

    private void generateItemDefinition(Path root, Material material, List<MetaItem> items) throws IOException {
        String vanillaItemName = material.getKey().getKey();

        StringBuilder cases = new StringBuilder();

        for (int index = 0; index < items.size(); index++) {
            MetaItem item = items.get(index);
            MetaModelComponent model = model(item);

            if (model == null) {
                continue;
            }

            String customModelDataValue = modelId(item, model);
            String modelPath = itemModelPath(item, model);

            cases.append("""
                    {
                      "when": "%s",
                      "model": {
                        "type": "minecraft:model",
                        "model": "%s"
                      }
                    }
                    """.formatted(escape(customModelDataValue), escape(modelPath)));

            if (index < items.size() - 1) {
                cases.append(",");
            }

            cases.append("\n");
        }

        String json = """
                {
                  "model": {
                    "type": "minecraft:select",
                    "property": "minecraft:custom_model_data",
                    "cases": [
                %s
                    ],
                    "fallback": {
                      "type": "minecraft:model",
                      "model": "minecraft:item/%s"
                    }
                  }
                }
                """.formatted(indent(cases.toString(), 6), escape(vanillaItemName));

        Path file = root.resolve("assets/minecraft/items/" + vanillaItemName + ".json");
        write(file, json);
    }

    private void generateModelIfNeeded(Path root, MetaItem item, MetaModelComponent model) throws IOException {
        if (model.mode() != MetaModelMode.GENERATED_TEXTURE) {
            return;
        }

        String namespace = item.key().namespace();
        String texturePath = texturePath(item, model);
        String modelPath = modelPath(item, model);

        String modelJson = """
                {
                  "parent": "minecraft:item/generated",
                  "textures": {
                    "layer0": "%s:%s"
                  }
                }
                """.formatted(escape(namespace), escape(texturePath));

        Path modelFile = root.resolve("assets/" + namespace + "/models/" + modelPath + ".json");
        write(modelFile, modelJson);
    }

    private MetaModelComponent model(MetaItem item) {
        return item.components()
                .get(MetaModelComponent.class)
                .orElse(null);
    }

    private String modelId(MetaItem item, MetaModelComponent model) {
        if (model.modelId() != null && !model.modelId().isBlank()) {
            return model.modelId();
        }

        return item.key().path();
    }

    private String texturePath(MetaItem item, MetaModelComponent model) {
        if (model.texturePath() != null && !model.texturePath().isBlank()) {
            return normalizeResourcePath(model.texturePath());
        }

        return "item/" + item.key().path();
    }

    private String modelPath(MetaItem item, MetaModelComponent model) {
        if (model.modelPath() != null && !model.modelPath().isBlank()) {
            return normalizeResourcePath(model.modelPath());
        }

        return "item/" + item.key().path();
    }

    private String itemModelPath(MetaItem item, MetaModelComponent model) {
        if (model.mode() == MetaModelMode.VANILLA_MODEL) {
            String raw = model.modelPath();

            if (raw == null || raw.isBlank()) {
                return "minecraft:item/" + item.material().getKey().getKey();
            }

            if (raw.contains(":")) {
                return raw;
            }

            if (raw.startsWith("item/") || raw.startsWith("block/")) {
                return "minecraft:" + raw;
            }

            return "minecraft:item/" + raw;
        }

        String raw = modelPath(item, model);

        if (raw.contains(":")) {
            return raw;
        }

        return item.key().namespace() + ":" + raw;
    }

    private String normalizeResourcePath(String path) {
        String normalized = path.replace("\\", "/");

        while (normalized.startsWith("/")) {
            normalized = normalized.substring(1);
        }

        if (normalized.endsWith(".json")) {
            normalized = normalized.substring(0, normalized.length() - ".json".length());
        }

        if (normalized.endsWith(".png")) {
            normalized = normalized.substring(0, normalized.length() - ".png".length());
        }

        return normalized;
    }

    private void write(Path path, String content) throws IOException {
        Files.createDirectories(path.getParent());
        Files.writeString(path, content, StandardCharsets.UTF_8);
    }

    private String escape(String value) {
        return value.replace("\\", "\\\\").replace("\"", "\\\"");
    }

    private String indent(String text, int spaces) {
        String prefix = " ".repeat(spaces);
        return prefix + text.replace("\n", "\n" + prefix);
    }
}