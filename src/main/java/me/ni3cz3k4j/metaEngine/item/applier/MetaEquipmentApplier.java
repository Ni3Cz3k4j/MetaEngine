package me.ni3cz3k4j.metaEngine.item.applier;

import me.ni3cz3k4j.metaEngine.item.MetaItem;
import me.ni3cz3k4j.metaEngine.item.MetaItemApplier;
import me.ni3cz3k4j.metaEngine.item.MetaItemApplyContext;
import me.ni3cz3k4j.metaEngine.item.component.MetaEquipmentComponent;
import me.ni3cz3k4j.metaEngine.item.component.MetaEquipmentSlot;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.Sound;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.EquippableComponent;

public final class MetaEquipmentApplier implements MetaItemApplier<MetaEquipmentComponent> {
    @Override
    public Class<MetaEquipmentComponent> componentType() {
        return MetaEquipmentComponent.class;
    }

    @Override
    public void apply(MetaItem item, MetaEquipmentComponent component, ItemStack stack, ItemMeta meta, MetaItemApplyContext context) {
        EquippableComponent equippable = meta.getEquippable();

        if (component.slot() != null) {
            equippable.setSlot(mapSlot(component.slot()));
        }

        if (component.equipSound() != null && !component.equipSound().isBlank()) {
            Sound sound = sound(component.equipSound());

            if (sound != null) {
                equippable.setEquipSound(sound);
            }
        }

        if (component.assetsId() != null && !component.assetsId().isBlank()) {
            NamespacedKey model = parseKey(component.assetsId(), item.key().namespace());

            if (model != null) {
                equippable.setModel(model);
            }
        }

        if (component.cameraOverlay() != null && !component.cameraOverlay().isBlank()) {
            NamespacedKey overlay = parseKey(component.cameraOverlay(), item.key().namespace());

            if (overlay != null) {
                equippable.setCameraOverlay(overlay);
            }
        }

        if (component.dispensable() != null) {
            equippable.setDispensable(component.dispensable());
        }

        if (component.damageOnHurt() != null) {
            equippable.setDamageOnHurt(component.damageOnHurt());
        }

        if (component.swappable() != null) {
            equippable.setEquipOnInteract(component.swappable());
        }

        meta.setEquippable(equippable);
    }

    private EquipmentSlot mapSlot(MetaEquipmentSlot slot) {
        return switch (slot) {
            case HEAD -> EquipmentSlot.HEAD;
            case CHEST -> EquipmentSlot.CHEST;
            case LEGS -> EquipmentSlot.LEGS;
            case FEET -> EquipmentSlot.FEET;
            case MAIN_HAND -> EquipmentSlot.HAND;
            case OFF_HAND -> EquipmentSlot.OFF_HAND;
            case BODY -> EquipmentSlot.BODY;
        };
    }

    private NamespacedKey parseKey(String raw, String defaultNamespace) {
        if (raw.contains(":")) {
            return NamespacedKey.fromString(raw);
        }

        return new NamespacedKey(defaultNamespace, raw);
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