package me.ni3cz3k4j.metaEngine.resourcepack;

import me.ni3cz3k4j.metaEngine.item.MetaItem;
import me.ni3cz3k4j.metaEngine.item.component.MetaModelComponent;
import me.ni3cz3k4j.metaEngine.item.component.MetaModelMode;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public final class MetaItemResourceContributor implements MetaResourcePackContributor {
    @Override
    public void contribute(MetaResourcePackBuildContext context) throws IOException {
        for (MetaItem item : context.registries().items().values()) {
            MetaModelComponent model = item.components()
                    .get(MetaModelComponent.class)
                    .orElse(null);

            if (model == null) {
                continue;
            }

            if (model.mode() == MetaModelMode.RAW_ITEM_DEFINITION) {
                continue;
            }

            generateItemDeclaration(context.root(), item, model);
            generateModelIfNeeded(context.root(), item, model);
        }
    }

    private void generateItemDeclaration(Path root, MetaItem item, MetaModelComponent model) throws IOException {
        String namespace = item.key().namespace();
        String itemDeclarationPath = itemDeclarationPath(item, model);
        String modelReference = modelReference(item, model);

        String json = """
                {
                  "model": {
                    "type": "minecraft:model",
                    "model": "%s"
                  }
                }
                """.formatted(escape(modelReference));

        Path file = root.resolve("assets/" + namespace + "/items/" + itemDeclarationPath + ".json");
        write(file, json);
    }

    private void generateModelIfNeeded(Path root, MetaItem item, MetaModelComponent model) throws IOException {
        if (model.mode() != MetaModelMode.GENERATED_TEXTURE) {
            return;
        }

        String namespace = item.key().namespace();
        String modelPath = modelPath(item, model);
        String texturePath = texturePath(item, model);

        String json = """
                {
                  "parent": "minecraft:item/generated",
                  "textures": {
                    "layer0": "%s:%s"
                  }
                }
                """.formatted(escape(namespace), escape(texturePath));

        Path file = root.resolve("assets/" + namespace + "/models/" + modelPath + ".json");
        write(file, json);
    }

    private String itemDeclarationPath(MetaItem item, MetaModelComponent model) {
        String id = model.modelId();

        if (id == null || id.isBlank()) {
            id = item.key().path();
        }

        id = normalize(id);

        if (id.startsWith("item/")) {
            id = id.substring("item/".length());
        }

        return id;
    }

    private String modelReference(MetaItem item, MetaModelComponent model) {
        if (model.mode() == MetaModelMode.VANILLA_MODEL) {
            String raw = model.modelPath();

            if (raw == null || raw.isBlank()) {
                return "minecraft:item/" + item.material().getKey().getKey();
            }

            raw = normalize(raw);

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

    private String modelPath(MetaItem item, MetaModelComponent model) {
        String path = model.modelPath();

        if (path == null || path.isBlank()) {
            path = "item/" + item.key().path();
        }

        path = normalize(path);

        if (!path.startsWith("item/") && !path.startsWith("block/")) {
            path = "item/" + path;
        }

        return path;
    }

    private String texturePath(MetaItem item, MetaModelComponent model) {
        String path = model.texturePath();

        if (path == null || path.isBlank()) {
            path = "item/" + item.key().path();
        }

        path = normalize(path);

        if (!path.startsWith("item/") && !path.startsWith("block/")) {
            path = "item/" + path;
        }

        return path;
    }

    private String normalize(String path) {
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

        if (normalized.contains(":")) {
            normalized = normalized.substring(normalized.indexOf(':') + 1);
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
}