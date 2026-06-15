package me.ni3cz3k4j.metaEngine.item.applier;

import me.ni3cz3k4j.metaEngine.item.MetaItem;
import me.ni3cz3k4j.metaEngine.item.MetaItemApplier;
import me.ni3cz3k4j.metaEngine.item.MetaItemApplyContext;
import me.ni3cz3k4j.metaEngine.item.component.MetaFoodComponent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.FoodComponent;

public final class MetaFoodApplier implements MetaItemApplier<MetaFoodComponent> {
    @Override
    public Class<MetaFoodComponent> componentType() {
        return MetaFoodComponent.class;
    }

    @Override
    public void apply(
            MetaItem item,
            MetaFoodComponent component,
            ItemStack stack,
            ItemMeta meta,
            MetaItemApplyContext context
    ) {
        FoodComponent food = meta.getFood();

        food.setNutrition(component.nutrition());
        food.setSaturation(component.saturation());
        food.setCanAlwaysEat(component.alwaysEat());

        meta.setFood(food);
    }
}