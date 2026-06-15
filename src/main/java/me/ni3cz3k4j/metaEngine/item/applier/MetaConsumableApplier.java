package me.ni3cz3k4j.metaEngine.item.applier;

import me.ni3cz3k4j.metaEngine.item.MetaItem;
import me.ni3cz3k4j.metaEngine.item.MetaItemApplier;
import me.ni3cz3k4j.metaEngine.item.MetaItemApplyContext;
import me.ni3cz3k4j.metaEngine.item.component.MetaConsumableComponent;
import me.ni3cz3k4j.metaEngine.item.component.MetaUseAnimation;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.consumable.ConsumableComponent;

public final class MetaConsumableApplier implements MetaItemApplier<MetaConsumableComponent> {
    @Override
    public Class<MetaConsumableComponent> componentType() {
        return MetaConsumableComponent.class;
    }

    @Override
    public void apply(MetaItem item, MetaConsumableComponent component, ItemStack stack, ItemMeta meta, MetaItemApplyContext context) {
        ConsumableComponent consumable = meta.getConsumable();

        consumable.setConsumeSeconds(component.consumeSeconds());
        consumable.setConsumeParticles(component.particles());

        if (component.animation() != null) {
            consumable.setAnimation(map(component.animation()));
        }

        if (component.sounds() != null && !component.sounds().isBlank()) {
            NamespacedKey soundKey = NamespacedKey.fromString(component.sounds());

            if (soundKey != null) {
                Sound sound = Registry.SOUNDS.get(soundKey);

                if (sound != null) {
                    consumable.setSound(sound);
                }
            }
        }

        meta.setConsumable(consumable);
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
}