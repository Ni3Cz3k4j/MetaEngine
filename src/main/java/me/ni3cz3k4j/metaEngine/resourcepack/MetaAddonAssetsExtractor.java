package me.ni3cz3k4j.metaEngine.resourcepack;

import me.ni3cz3k4j.metaEngine.addon.MetaAddon;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public final class MetaAddonAssetsExtractor {
    public void copyAssets(MetaAddon addon, Path packRoot) throws IOException {
        JavaPlugin plugin = addon.plugin();
        String metaId = addon.metaId();

        Path jarPath;

        try {
            jarPath = Path.of(plugin.getClass()
                    .getProtectionDomain()
                    .getCodeSource()
                    .getLocation()
                    .toURI());
        } catch (URISyntaxException exception) {
            throw new IOException("Cannot locate plugin jar for " + plugin.getName() + ".", exception);
        }

        String prefix = "assets/" + metaId + "/";

        try (JarFile jarFile = new JarFile(jarPath.toFile())) {
            for (JarEntry entry : java.util.Collections.list(jarFile.entries())) {
                if (entry.isDirectory()) {
                    continue;
                }

                String name = entry.getName();

                if (!name.startsWith(prefix)) {
                    continue;
                }

                Path target = packRoot.resolve(name);
                Files.createDirectories(target.getParent());

                try (InputStream inputStream = jarFile.getInputStream(entry)) {
                    Files.copy(inputStream, target, StandardCopyOption.REPLACE_EXISTING);
                }
            }
        }
    }
}