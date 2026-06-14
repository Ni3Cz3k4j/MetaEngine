package me.ni3cz3k4j.metaEngine.resourcepack;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Collections;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public final class MetaPluginAssetExtractor {

    public void copyAssets(JavaPlugin plugin, String namespace, Path packRoot) throws IOException {
        Path pluginPath;

        try {
            pluginPath = Path.of(plugin.getClass()
                    .getProtectionDomain()
                    .getCodeSource()
                    .getLocation()
                    .toURI());
        } catch (URISyntaxException exception) {
            throw new IOException("Cannot locate plugin jar for " + plugin.getName(), exception);
        }

        String prefix = "assets/" + namespace + "/";

        if (Files.isDirectory(pluginPath)) {
            copyFromDirectory(pluginPath, prefix, packRoot);
            return;
        }

        copyFromJar(pluginPath, prefix, packRoot);
    }

    private void copyFromJar(Path jarPath, String prefix, Path packRoot) throws IOException {
        try (JarFile jarFile = new JarFile(jarPath.toFile())) {
            for (JarEntry entry : Collections.list(jarFile.entries())) {
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

    private void copyFromDirectory(Path classesDir, String prefix, Path packRoot) throws IOException {
        Path sourceRoot = classesDir.resolve(prefix);

        if (!Files.exists(sourceRoot)) {
            return;
        }

        try (var stream = Files.walk(sourceRoot)) {
            for (Path source : stream.filter(Files::isRegularFile).toList()) {
                Path relative = classesDir.relativize(source);
                Path target = packRoot.resolve(relative.toString().replace("\\", "/"));

                Files.createDirectories(target.getParent());
                Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
            }
        }
    }
}