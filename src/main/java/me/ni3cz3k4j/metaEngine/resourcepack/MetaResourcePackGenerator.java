package me.ni3cz3k4j.metaEngine.resourcepack;

import me.ni3cz3k4j.metaEngine.addon.MetaAddon;
import me.ni3cz3k4j.metaEngine.registry.MetaRegistries;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;

public final class MetaResourcePackGenerator {
    private final JavaPlugin plugin;
    private final MetaRegistries registries;
    private final Collection<MetaAddon> addons;
    private final List<MetaResourcePackContributor> contributors;

    public MetaResourcePackGenerator(JavaPlugin plugin, MetaRegistries registries, Collection<MetaAddon> addons) {
        this.plugin = plugin;
        this.registries = registries;
        this.addons = addons;
        this.contributors = List.of(
                new MetaPackMcmetaContributor(),
                new MetaAddonAssetContributor(),
                new MetaItemResourceContributor()
        );
    }

    public Path generate() throws IOException {
        Path root = plugin.getDataFolder().toPath().resolve("generated-resourcepack");

        deleteDirectory(root);
        Files.createDirectories(root);

        MetaResourcePackBuildContext context = new MetaResourcePackBuildContext(plugin, registries, addons, root);

        for (MetaResourcePackContributor contributor : contributors) {
            contributor.contribute(context);
        }

        return root;
    }

    private void deleteDirectory(Path path) throws IOException {
        if (!Files.exists(path)) {
            return;
        }

        try (var stream = Files.walk(path)) {
            for (Path file : stream.sorted((a, b) -> b.compareTo(a)).toList()) {
                Files.deleteIfExists(file);
            }
        }
    }

    public MetaGeneratedResourcePack generateZip() throws IOException {
        Path directory = generate();

        Path zip = plugin.getDataFolder()
                .toPath()
                .resolve("generated-resourcepack.zip");

        new MetaResourcePackZipper().zip(directory, zip);
        byte[] sha1 = new MetaResourcePackHash().sha1(zip);

        return new MetaGeneratedResourcePack(directory, zip, sha1);
    }
}