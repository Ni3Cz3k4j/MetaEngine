package me.ni3cz3k4j.metaEngine.resourcepack;

import me.ni3cz3k4j.metaEngine.item.MetaItem;
import me.ni3cz3k4j.metaEngine.item.settings.MetaModelSettings;
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
            if (item.settings().model() == null) {
                continue;
            }

            generateModelIfNeeded(context.root(), item);
        }
    }

    private Map<Material, List<MetaItem>> groupItemsByMaterial(MetaResourcePackBuildContext context) {
        Map<Material, List<MetaItem>> result = new LinkedHashMap<>();

        for (MetaItem item : context.registries().items().values()) {
            if (item.settings().model() == null) {
                continue;
            }

            result.computeIfAbsent(item.material(), ignored -> new ArrayList<>()).add(item);
        }

        return result;
    }

    private void generateItemDefinition(Path root, Material material, List<MetaItem> items) throws IOException {
        String vanillaItemName = material.getKey().getKey();

        StringBuilder cases = new StringBuilder();

        for (int index = 0; index < items.size(); index++) {
            MetaItem item = items.get(index);
            MetaModelSettings model = item.settings().model();
            String customModelDataValue = model.modelId();
            String modelPath = item.key().namespace() + ":" + model.modelPath();

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

    private void generateModelIfNeeded(Path root, MetaItem item) throws IOException {
        MetaModelSettings model = item.settings().model();

        if (!model.generateModel()) {
            return;
        }

        String namespace = item.key().namespace();

        String modelJson = """
                {
                  "parent": "minecraft:item/generated",
                  "textures": {
                    "layer0": "%s:%s"
                  }
                }
                """.formatted(escape(namespace), escape(model.texturePath()));

        Path modelFile = root.resolve("assets/" + namespace + "/models/" + model.modelPath() + ".json");
        write(modelFile, modelJson);
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