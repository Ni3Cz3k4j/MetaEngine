package me.ni3cz3k4j.metaEngine.resourcepack;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public final class MetaResourcePackZipper {
    public Path zip(Path sourceDirectory, Path outputZip) throws IOException {
        Files.createDirectories(outputZip.getParent());

        try (ZipOutputStream zip = new ZipOutputStream(Files.newOutputStream(outputZip)); var paths = Files.walk(sourceDirectory)) {
            for (Path path : paths.filter(Files::isRegularFile).toList()) {
                String entryName = sourceDirectory.relativize(path)
                        .toString()
                        .replace("\\", "/");

                zip.putNextEntry(new ZipEntry(entryName));
                Files.copy(path, zip);
                zip.closeEntry();
            }
        }

        return outputZip;
    }
}