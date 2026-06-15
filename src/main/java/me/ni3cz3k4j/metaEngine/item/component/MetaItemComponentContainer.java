package me.ni3cz3k4j.metaEngine.item.component;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public final class MetaItemComponentContainer {
    private final Map<Class<? extends MetaItemComponent>, MetaItemComponent> components = new LinkedHashMap<>();

    public <T extends MetaItemComponent> void set(Class<T> type, T component) {
        components.put(type, component);
    }

    public <T extends MetaItemComponent> Optional<T> get(Class<T> type) {
        MetaItemComponent component = components.get(type);

        if (component == null) {
            return Optional.empty();
        }

        return Optional.of(type.cast(component));
    }

    public boolean has(Class<? extends MetaItemComponent> type) {
        return components.containsKey(type);
    }

    public Collection<MetaItemComponent> all() {
        return components.values();
    }
}