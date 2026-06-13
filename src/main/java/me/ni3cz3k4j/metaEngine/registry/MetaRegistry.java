package me.ni3cz3k4j.metaEngine.registry;

import java.util.Collection;
import java.util.Optional;

public interface MetaRegistry<T> {
    void register(MetaKey key, T value);

    Optional<T> get(MetaKey key);

    boolean contains(MetaKey key);

    Collection<MetaKey> keys();

    Collection<T> values();

    void freeze();

    boolean isFrozen();
}
