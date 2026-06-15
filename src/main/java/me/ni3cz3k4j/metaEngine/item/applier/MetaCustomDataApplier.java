package me.ni3cz3k4j.metaEngine.item.applier;

import me.ni3cz3k4j.metaEngine.item.MetaItem;
import me.ni3cz3k4j.metaEngine.item.MetaItemApplier;
import me.ni3cz3k4j.metaEngine.item.MetaItemApplyContext;
import me.ni3cz3k4j.metaEngine.item.component.MetaCustomDataComponent;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public final class MetaCustomDataApplier implements MetaItemApplier<MetaCustomDataComponent> {
    @Override
    public Class<MetaCustomDataComponent> componentType() {
        return MetaCustomDataComponent.class;
    }

    @Override
    public void apply(MetaItem item, MetaCustomDataComponent component, ItemStack stack, ItemMeta meta, MetaItemApplyContext context) {
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        component.strings().forEach((key, value) ->
                pdc.set(new NamespacedKey(context.plugin(), "data_" + key), PersistentDataType.STRING, value));

        component.integers().forEach((key, value) ->
                pdc.set(new NamespacedKey(context.plugin(), "data_" + key), PersistentDataType.INTEGER, value));

        component.longs().forEach((key, value) ->
                pdc.set(new NamespacedKey(context.plugin(), "data_" + key), PersistentDataType.LONG, value));

        component.doubles().forEach((key, value) ->
                pdc.set(new NamespacedKey(context.plugin(), "data_" + key), PersistentDataType.DOUBLE, value));

        component.booleans().forEach((key, value) ->
                pdc.set(new NamespacedKey(context.plugin(), "data_" + key), PersistentDataType.BOOLEAN, value));
    }
}