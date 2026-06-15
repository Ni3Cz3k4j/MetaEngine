package me.ni3cz3k4j.metaEngine.item.applier;

import me.ni3cz3k4j.metaEngine.item.MetaItem;
import me.ni3cz3k4j.metaEngine.item.MetaItemApplier;
import me.ni3cz3k4j.metaEngine.item.MetaItemApplyContext;
import me.ni3cz3k4j.metaEngine.item.component.MetaJukeboxComponent;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.JukeboxPlayableComponent;

public final class MetaJukeboxApplier implements MetaItemApplier<MetaJukeboxComponent> {
    @Override
    public Class<MetaJukeboxComponent> componentType() {
        return MetaJukeboxComponent.class;
    }

    @Override
    public void apply(MetaItem item, MetaJukeboxComponent component, ItemStack stack, ItemMeta meta, MetaItemApplyContext context) {
        if (component.songKey() == null || component.songKey().isBlank()) {
            return;
        }

        NamespacedKey songKey = parseKey(component.songKey(), item.key().namespace());

        if (songKey == null) {
            return;
        }

        JukeboxPlayableComponent jukebox = meta.getJukeboxPlayable();
        jukebox.setSongKey(songKey);
        meta.setJukeboxPlayable(jukebox);
    }

    private NamespacedKey parseKey(String raw, String defaultNamespace) {
        if (raw.contains(":")) {
            return NamespacedKey.fromString(raw);
        }

        return new NamespacedKey(defaultNamespace, raw);
    }
}