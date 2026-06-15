package me.ni3cz3k4j.metaEngine.item.applier;

import me.ni3cz3k4j.metaEngine.item.MetaItem;
import me.ni3cz3k4j.metaEngine.item.MetaItemApplier;
import me.ni3cz3k4j.metaEngine.item.MetaItemApplyContext;
import me.ni3cz3k4j.metaEngine.item.component.MetaCustomModelDataComponent;
import org.bukkit.Color;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.CustomModelDataComponent;

import java.util.ArrayList;
import java.util.List;

public final class MetaCustomModelDataApplier implements MetaItemApplier<MetaCustomModelDataComponent> {
    @Override
    public Class<MetaCustomModelDataComponent> componentType() {
        return MetaCustomModelDataComponent.class;
    }

    @Override
    public void apply(MetaItem item, MetaCustomModelDataComponent component, ItemStack stack, ItemMeta meta, MetaItemApplyContext context) {
        CustomModelDataComponent customModelData = meta.getCustomModelDataComponent();

        List<String> strings = new ArrayList<>();
        for (Object value : component.strings()) {
            if (value instanceof String string) {
                strings.add(string);
            }
        }

        List<Float> floats = new ArrayList<>();
        for (Object value : component.floats()) {
            if (value instanceof Number number) {
                floats.add(number.floatValue());
            }
        }

        List<Boolean> flags = new ArrayList<>();
        for (Object value : component.flags()) {
            if (value instanceof Boolean flag) {
                flags.add(flag);
            }
        }

        List<Color> colors = new ArrayList<>();
        for (Object value : component.colors()) {
            if (value instanceof Color color) {
                colors.add(color);
            }
        }

        customModelData.setStrings(strings);
        customModelData.setFloats(floats);
        customModelData.setFlags(flags);
        customModelData.setColors(colors);

        meta.setCustomModelDataComponent(customModelData);
    }
}