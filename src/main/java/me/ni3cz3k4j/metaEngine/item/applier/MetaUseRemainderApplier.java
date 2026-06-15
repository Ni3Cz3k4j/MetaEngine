package me.ni3cz3k4j.metaEngine.item.applier;

import me.ni3cz3k4j.metaEngine.item.MetaItem;
import me.ni3cz3k4j.metaEngine.item.MetaItemApplier;
import me.ni3cz3k4j.metaEngine.item.MetaItemApplyContext;
import me.ni3cz3k4j.metaEngine.item.component.MetaUseRemainderComponent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public final class MetaUseRemainderApplier implements MetaItemApplier<MetaUseRemainderComponent> {
    @Override
    public Class<MetaUseRemainderComponent> componentType() {
        return MetaUseRemainderComponent.class;
    }

    @Override
    public void apply(MetaItem item, MetaUseRemainderComponent component, ItemStack stack, ItemMeta meta, MetaItemApplyContext context) {
        if (component.material() == null) {
            return;
        }

        meta.setUseRemainder(new ItemStack(component.material()));
    }
}