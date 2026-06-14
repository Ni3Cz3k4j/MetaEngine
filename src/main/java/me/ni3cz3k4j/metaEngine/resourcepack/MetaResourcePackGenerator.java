package me.ni3cz3k4j.metaEngine.resourcepack;

import me.ni3cz3k4j.metaEngine.item.MetaItem;
import me.ni3cz3k4j.metaEngine.registry.MetaRegistries;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public final class MetaResourcePackGenerator {
    private final JavaPlugin plugin;
    private final MetaRegistries registries;

    public MetaResourcePackGenerator(JavaPlugin plugin, MetaRegistries registries) {
        this.plugin = plugin;
        this.registries = registries;
    }

    public void generate() throws IOException {
        Path root = plugin.getDataFolder().toPath().resolve("generated-resourcepack");

        write(root.resolve("pack.mcmeta"), """
                {
                  "pack": {
                    "pack_format": 75,
                    "description": "MetaEngine generated resource pack"
                  }
                }
                """);

        generateItems(root);
    }

    private void generateItems(Path root) throws IOException {
        for (MetaItem item : registries.items().values()) {
            if (item.settings().model() == null) {
                continue;
            }

            String namespace = item.key().namespace();
            String path = item.key().path();
            String modelId = item.settings().model().modelId();
            String vanillaItemName = item.material().getKey().getKey();
            Path itemDefinition = root.resolve("assets/minecraft/items/" + vanillaItemName + ".json");

            String definitionJson = """
                    {
                      "model": {
                        "type": "minecraft:select",
                        "property": "minecraft:custom_model_data",
                        "cases": [
                          {
                            "when": "%s",
                            "model": {
                              "type": "minecraft:model",
                              "model": "%s:item/%s"
                            }
                          }
                        ],
                        "fallback": {
                          "type": "minecraft:model",
                          "model": "minecraft:item/%s"
                        }
                      }
                    }
                    """.formatted(modelId, namespace, path, vanillaItemName);

            write(itemDefinition, definitionJson);
            Path modelFile = root.resolve("assets/" + namespace + "/models/item/" + path + ".json");

            String modelJson = """
                    {
                      "parent": "minecraft:item/generated",
                      "textures": {
                        "layer0": "%s:item/%s"
                      }
                    }
                    """.formatted(namespace, path);

            write(modelFile, modelJson);
        }
    }

    private void write(Path path, String content) throws IOException {
        Files.createDirectories(path.getParent());
        Files.writeString(path, content, StandardCharsets.UTF_8);
    }
}