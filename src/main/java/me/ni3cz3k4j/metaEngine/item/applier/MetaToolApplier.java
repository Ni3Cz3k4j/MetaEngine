package me.ni3cz3k4j.metaEngine.item.applier;

import me.ni3cz3k4j.metaEngine.item.MetaItem;
import me.ni3cz3k4j.metaEngine.item.MetaItemApplier;
import me.ni3cz3k4j.metaEngine.item.MetaItemApplyContext;
import me.ni3cz3k4j.metaEngine.item.component.MetaToolComponent;
import me.ni3cz3k4j.metaEngine.item.component.MetaToolRule;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.ToolComponent;

import java.util.Collection;

public final class MetaToolApplier implements MetaItemApplier<MetaToolComponent> {
    @Override
    public Class<MetaToolComponent> componentType() {
        return MetaToolComponent.class;
    }

    @Override
    public void apply(MetaItem item, MetaToolComponent component, ItemStack stack, ItemMeta meta, MetaItemApplyContext context) {
        ToolComponent tool = meta.getTool();

        tool.setDefaultMiningSpeed(component.defaultMiningSpeed());
        tool.setDamagePerBlock(Math.max(0, component.damagePerBlock()));

        for (Object rawRule : component.rules()) {
            if (!(rawRule instanceof MetaToolRule rule)) {
                continue;
            }

            if (rule.materials() == null || rule.materials().isEmpty()) {
                continue;
            }

            tool.addRule((Collection<Material>) rule.materials(), rule.speed(), rule.correctForDrops());
        }

        meta.setTool(tool);
    }
}