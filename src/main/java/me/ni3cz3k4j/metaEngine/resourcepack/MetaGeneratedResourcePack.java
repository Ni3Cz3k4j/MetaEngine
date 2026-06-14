package me.ni3cz3k4j.metaEngine.resourcepack;

import java.nio.file.Path;

public record MetaGeneratedResourcePack(Path directory, Path zip, byte[] sha1) {}