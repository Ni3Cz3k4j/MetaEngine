package me.ni3cz3k4j.metaEngine.resourcepack;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public final class MetaPackMcmetaContributor implements MetaResourcePackContributor {
    @Override
    public void contribute(MetaResourcePackBuildContext context) throws IOException {
        Path file = context.root().resolve("pack.mcmeta");

        Files.createDirectories(file.getParent());

        Files.writeString(file, """
                {
                  "pack": {
                    "pack_format": 94.1,
                    "description": "MetaEngine generated resource pack"
                  }
                }
                """, StandardCharsets.UTF_8); // https://minecraft.wiki/w/Pack_format: 1.21.11 -> 94.1
    }
}