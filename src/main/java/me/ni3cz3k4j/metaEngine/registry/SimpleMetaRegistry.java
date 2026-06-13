package me.ni3cz3k4j.metaEngine.registry;

import java.util.*;

public final class SimpleMetaRegistry<T> implements MetaRegistry<T> {
    private final String name;
    private final Map<MetaKey, T> entries = new LinkedHashMap<>();
    private boolean frozen = false;

    public SimpleMetaRegistry(String name) {
        this.name = name;
    }

    @Override
    public void register(MetaKey key, T value) {
        if (frozen) {
            throw new IllegalStateException("Registry '" + name + "' is frozen. Cannot register: " + key + ".");
        }

        if (key == null) {
            throw new IllegalArgumentException("Registry key cannot be null.");
        }

        if (value == null) {
            throw new IllegalArgumentException("Registry value cannot be null.");
        }

        if (entries.containsKey(key)) {
            throw new IllegalStateException("Duplicate key in registry '" + name + "': " + key + ".");
        }

        entries.put(key, value);
    }

    @Override
    public Optional<T> get(MetaKey key) {
        return Optional.ofNullable(entries.get(key));
    }

    @Override
    public boolean contains(MetaKey key) {
        return entries.containsKey(key);
    }

    @Override
    public Collection<MetaKey> keys() {
        return Collections.unmodifiableSet(entries.keySet());
    }

    @Override
    public Collection<T> values() {
        return Collections.unmodifiableCollection(entries.values());
    }

    @Override
    public void freeze() {
        this.frozen = true;
    }

    @Override
    public boolean isFrozen() {
        return frozen;
    }
}