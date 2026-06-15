package me.ni3cz3k4j.metaEngine.item.applier;

import me.ni3cz3k4j.metaEngine.item.MetaItem;
import me.ni3cz3k4j.metaEngine.item.MetaItemApplier;
import me.ni3cz3k4j.metaEngine.item.MetaItemApplyContext;
import me.ni3cz3k4j.metaEngine.item.component.MetaUseAnimation;
import me.ni3cz3k4j.metaEngine.item.component.MetaUseComponent;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.consumable.ConsumableComponent;

public final class MetaUseApplier implements MetaItemApplier<MetaUseComponent> {
    @Override
    public Class<MetaUseComponent> componentType() {
        return MetaUseComponent.class;
    }

    @Override
    public void apply(MetaItem item, MetaUseComponent component, ItemStack stack, ItemMeta meta, MetaItemApplyContext context) {
        boolean changedConsumable = false;
        ConsumableComponent consumable = meta.getConsumable();

        if (component.useDurationSeconds() != null) {
            consumable.setConsumeSeconds(component.useDurationSeconds());
            changedConsumable = true;
        }

        if (component.animation() != null) {
            consumable.setAnimation(map(component.animation()));
            changedConsumable = true;
        }

        if (component.useSound() != null && !component.useSound().isBlank()) {
            Sound sound = sound(component.useSound());

            if (sound != null) {
                consumable.setSound(sound);
                changedConsumable = true;
            }
        }

        if (changedConsumable) {
            meta.setConsumable(consumable);
        }
    }

    private ConsumableComponent.Animation map(MetaUseAnimation animation) {
        return switch (animation) {
            case EAT -> ConsumableComponent.Animation.EAT;
            case DRINK -> ConsumableComponent.Animation.DRINK;
            case BLOCK -> ConsumableComponent.Animation.BLOCK;
            case BOW -> ConsumableComponent.Animation.BOW;
            case SPEAR -> ConsumableComponent.Animation.SPEAR;
            case CROSSBOW -> ConsumableComponent.Animation.CROSSBOW;
            case TOOT_HORN -> ConsumableComponent.Animation.TOOT_HORN;
            case BRUSH -> ConsumableComponent.Animation.BRUSH;
            case NONE -> ConsumableComponent.Animation.NONE;
        };
    }

    private Sound sound(String raw) {
        NamespacedKey key = NamespacedKey.fromString(raw);

        if (key != null) {
            Sound sound = Registry.SOUNDS.get(key);

            if (sound != null) {
                return sound;
            }
        }

        try {
            return Sound.valueOf(raw.toUpperCase().replace('.', '_').replace(':', '_'));
        } catch (IllegalArgumentException exception) {
            return null;
        }
    }
}