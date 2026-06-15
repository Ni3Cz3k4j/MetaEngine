package me.ni3cz3k4j.metaEngine.item.applier;

import me.ni3cz3k4j.metaEngine.item.MetaItem;
import me.ni3cz3k4j.metaEngine.item.MetaItemApplier;
import me.ni3cz3k4j.metaEngine.item.MetaItemApplyContext;
import me.ni3cz3k4j.metaEngine.item.component.MetaGlintComponent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public final class MetaGlintApplier implements MetaItemApplier<MetaGlintComponent> {
    @Override
    public Class<MetaGlintComponent> componentType() {
        return MetaGlintComponent.class;
    }

    @Override
    public void apply(MetaItem item, MetaGlintComponent component, ItemStack stack, ItemMeta meta, MetaItemApplyContext context) {
        if (component.glint() != null) {
            meta.setEnchantmentGlintOverride(component.glint());
        }
    }
}