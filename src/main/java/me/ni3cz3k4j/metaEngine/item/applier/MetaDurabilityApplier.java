package me.ni3cz3k4j.metaEngine.item.applier;

import me.ni3cz3k4j.metaEngine.item.MetaItem;
import me.ni3cz3k4j.metaEngine.item.MetaItemApplier;
import me.ni3cz3k4j.metaEngine.item.MetaItemApplyContext;
import me.ni3cz3k4j.metaEngine.item.component.MetaDurabilityComponent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

public final class MetaDurabilityApplier implements MetaItemApplier<MetaDurabilityComponent> {
    @Override
    public Class<MetaDurabilityComponent> componentType() {
        return MetaDurabilityComponent.class;
    }

    @Override
    public void apply(MetaItem item, MetaDurabilityComponent component, ItemStack stack, ItemMeta meta, MetaItemApplyContext context) {
        if (component.unbreakable() != null) {
            meta.setUnbreakable(component.unbreakable());
        }

        if (meta instanceof Damageable damageable) {
            if (component.maxDamage() != null) {
                damageable.setMaxDamage(component.maxDamage());
            }

            if (component.damage() != null) {
                damageable.setDamage(component.damage());
            }
        }

        if (component.hideDamageBar() != null && component.hideDamageBar()) {
            meta.addItemFlags(ItemFlag.HIDE_DAMAGE);
        }
    }
}