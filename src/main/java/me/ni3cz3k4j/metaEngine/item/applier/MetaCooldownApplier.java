package me.ni3cz3k4j.metaEngine.item.applier;

import me.ni3cz3k4j.metaEngine.item.MetaItem;
import me.ni3cz3k4j.metaEngine.item.MetaItemApplier;
import me.ni3cz3k4j.metaEngine.item.MetaItemApplyContext;
import me.ni3cz3k4j.metaEngine.item.component.MetaCooldownComponent;
import me.ni3cz3k4j.metaEngine.registry.MetaKey;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.UseCooldownComponent;

public final class MetaCooldownApplier implements MetaItemApplier<MetaCooldownComponent> {
    @Override
    public Class<MetaCooldownComponent> componentType() {
        return MetaCooldownComponent.class;
    }

    @Override
    public void apply(MetaItem item, MetaCooldownComponent component, ItemStack stack, ItemMeta meta, MetaItemApplyContext context) {
        if (component.seconds() <= 0.0f) {
            return;
        }

        UseCooldownComponent cooldown = meta.getUseCooldown();
        cooldown.setCooldownSeconds(component.seconds());
        cooldown.setCooldownGroup(groupKey(item, component.group()));
        meta.setUseCooldown(cooldown);
    }

    private NamespacedKey groupKey(MetaItem item, String group) {
        if (group == null || group.isBlank() || group.equalsIgnoreCase("self")) {
            return new NamespacedKey(item.key().namespace(), item.key().path());
        }

        if (group.contains(":")) {
            MetaKey parsed = MetaKey.parse(group);
            return new NamespacedKey(parsed.namespace(), parsed.path());
        }

        return new NamespacedKey(item.key().namespace(), group);
    }
}