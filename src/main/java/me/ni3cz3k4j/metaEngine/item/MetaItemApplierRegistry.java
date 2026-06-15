package me.ni3cz3k4j.metaEngine.item;

import me.ni3cz3k4j.metaEngine.item.applier.*;
import me.ni3cz3k4j.metaEngine.item.component.MetaItemComponent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public final class MetaItemApplierRegistry {
    private final List<MetaItemApplier<?>> appliers = new ArrayList<>();

    public void registerDefaults() {
        register(new MetaDisplayApplier());
        register(new MetaModelApplier());
        register(new MetaCustomModelDataApplier());
        register(new MetaStackApplier());
        register(new MetaDurabilityApplier());
        register(new MetaRarityApplier());
        register(new MetaGlintApplier());
        register(new MetaTooltipApplier());
        register(new MetaCooldownApplier());
        register(new MetaUseApplier());
        register(new MetaFoodApplier());
        register(new MetaConsumableApplier());
        register(new MetaUseRemainderApplier());
        register(new MetaToolApplier());
        register(new MetaWeaponApplier());
        register(new MetaEquipmentApplier());
        register(new MetaAttributeApplier());
        register(new MetaEnchantmentsApplier());
        register(new MetaRepairApplier());
        register(new MetaJukeboxApplier());
        register(new MetaCustomDataApplier());
    }

    public void register(MetaItemApplier<?> applier) {
        appliers.add(applier);
    }

    public void applyAll(MetaItem item, ItemStack stack, ItemMeta meta, MetaItemApplyContext context) {
        for (MetaItemApplier<?> applier : appliers) {
            applyUnchecked(applier, item, stack, meta, context);
        }
    }

    private <T extends MetaItemComponent> void applyUnchecked(
            MetaItemApplier<T> applier,
            MetaItem item,
            ItemStack stack,
            ItemMeta meta,
            MetaItemApplyContext context
    ) {
        item.components().get(applier.componentType())
                .ifPresent(component -> applier.apply(item, component, stack, meta, context));
    }
}