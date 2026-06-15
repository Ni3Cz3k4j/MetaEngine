package me.ni3cz3k4j.metaEngine.item.applier;

import me.ni3cz3k4j.metaEngine.item.MetaItem;
import me.ni3cz3k4j.metaEngine.item.MetaItemApplier;
import me.ni3cz3k4j.metaEngine.item.MetaItemApplyContext;
import me.ni3cz3k4j.metaEngine.item.component.MetaEnchantmentsComponent;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Map;

public final class MetaEnchantmentsApplier implements MetaItemApplier<MetaEnchantmentsComponent> {
    @Override
    public Class<MetaEnchantmentsComponent> componentType() {
        return MetaEnchantmentsComponent.class;
    }

    @Override
    public void apply(MetaItem item, MetaEnchantmentsComponent component, ItemStack stack, ItemMeta meta, MetaItemApplyContext context) {
        for (Object rawEntry : component.enchantments().entrySet()) {
            if (!(rawEntry instanceof Map.Entry<?, ?> entry)) {
                continue;
            }

            if (!(entry.getKey() instanceof String rawKey)) {
                continue;
            }

            if (!(entry.getValue() instanceof Number rawLevel)) {
                continue;
            }

            NamespacedKey key = NamespacedKey.fromString(rawKey);

            if (key == null) {
                continue;
            }

            Enchantment enchantment = Registry.ENCHANTMENT.get(key);

            if (enchantment == null) {
                continue;
            }

            meta.addEnchant(enchantment, rawLevel.intValue(), component.ignoreRestrictions());
        }

        if (component.hideEnchantments()) {
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
    }
}