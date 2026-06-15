package me.ni3cz3k4j.metaEngine.item.applier;

import me.ni3cz3k4j.metaEngine.item.MetaItem;
import me.ni3cz3k4j.metaEngine.item.MetaItemApplier;
import me.ni3cz3k4j.metaEngine.item.MetaItemApplyContext;
import me.ni3cz3k4j.metaEngine.item.component.MetaRepairComponent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.Repairable;

public final class MetaRepairApplier implements MetaItemApplier<MetaRepairComponent> {
    @Override
    public Class<MetaRepairComponent> componentType() {
        return MetaRepairComponent.class;
    }

    @Override
    public void apply(MetaItem item, MetaRepairComponent component, ItemStack stack, ItemMeta meta, MetaItemApplyContext context) {
        if (component.repairCost() != null && meta instanceof Repairable repairable) {
            repairable.setRepairCost(component.repairCost());
        }
    }
}