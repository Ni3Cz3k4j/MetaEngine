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
    public void apply(
            MetaItem item,
            MetaCooldownComponent component,
            ItemStack stack,
            ItemMeta meta,
            MetaItemApplyContext context
    ) {
        UseCooldownComponent cooldown = meta.getUseCooldown();

        cooldown.setCooldownSeconds(component.seconds());

        String group = component.group();
        NamespacedKey groupKey;

        if (group == null || group.equals("self")) {
            groupKey = new NamespacedKey(item.key().namespace(), item.key().path());
        } else if (group.contains(":")) {
            MetaKey parsed = MetaKey.parse(group);
            groupKey = new NamespacedKey(parsed.namespace(), parsed.path());
        } else {
            groupKey = new NamespacedKey(item.key().namespace(), group);
        }

        cooldown.setCooldownGroup(groupKey);
        meta.setUseCooldown(cooldown);
    }
}