package me.ni3cz3k4j.metaEngine.registry;

import java.util.Objects;

public final class MetaKey {
    private final String namespace;
    private final String path;

    private MetaKey(String namespace, String path) {
        this.namespace = validateNamespace(namespace);
        this.path = validatePath(path);
    }

    public static MetaKey of(String namespace, String path) {
        return new MetaKey(namespace, path);
    }

    public static MetaKey parse(String raw) {
        String[] split = raw.split(":", 2);

        if (split.length != 2) {
            throw new IllegalArgumentException("Invalid MetaKey: " + raw + ". Expected namespace:path.");
        }

        return of(split[0], split[1]);
    }

    public String namespace() {
        return namespace;
    }

    public String path() {
        return path;
    }

    public String asString() {
        return namespace + ":" + path;
    }

    private static String validateNamespace(String value) {
        if (value == null || !value.matches("[a-z0-9_.-]+")) {
            throw new IllegalArgumentException("Invalid namespace: " + value);
        }

        return value;
    }

    private static String validatePath(String value) {
        if (value == null || !value.matches("[a-z0-9_./-]+")) {
            throw new IllegalArgumentException("Invalid path: " + value);
        }

        return value;
    }

    @Override
    public String toString() {
        return asString();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof MetaKey metaKey)) return false;
        return namespace.equals(metaKey.namespace) && path.equals(metaKey.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(namespace, path);
    }
}