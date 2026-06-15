package me.ni3cz3k4j.metaEngine.item.applier;

import me.ni3cz3k4j.metaEngine.item.MetaItem;
import me.ni3cz3k4j.metaEngine.item.MetaItemApplier;
import me.ni3cz3k4j.metaEngine.item.MetaItemApplyContext;
import me.ni3cz3k4j.metaEngine.item.component.MetaAttributeComponent;
import me.ni3cz3k4j.metaEngine.item.component.MetaAttributeModifier;
import me.ni3cz3k4j.metaEngine.item.component.MetaAttributeOperation;
import me.ni3cz3k4j.metaEngine.item.component.MetaEquipmentSlot;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public final class MetaAttributeApplier implements MetaItemApplier<MetaAttributeComponent> {
    @Override
    public Class<MetaAttributeComponent> componentType() {
        return MetaAttributeComponent.class;
    }

    @Override
    public void apply(MetaItem item, MetaAttributeComponent component, ItemStack stack, ItemMeta meta, MetaItemApplyContext context) {
        for (Object rawModifier : component.modifiers()) {
            if (!(rawModifier instanceof MetaAttributeModifier modifier)) {
                continue;
            }

            Attribute attribute = attribute(modifier.attribute());

            if (attribute == null) {
                continue;
            }

            NamespacedKey modifierKey = new NamespacedKey(
                    item.key().namespace(),
                    sanitizeModifierKey(modifier.id())
            );

            AttributeModifier attributeModifier = new AttributeModifier(
                    modifierKey,
                    modifier.amount(),
                    operation(modifier.operation()),
                    slotGroup(modifier.slot())
            );

            meta.addAttributeModifier(attribute, attributeModifier);
        }
    }

    private Attribute attribute(String raw) {
        if (raw == null || raw.isBlank()) {
            return null;
        }

        NamespacedKey key;

        if (raw.contains(":")) {
            key = NamespacedKey.fromString(raw);
        } else {
            key = NamespacedKey.minecraft(raw.toLowerCase().replace('.', '_'));
        }

        if (key != null) {
            Attribute attribute = Registry.ATTRIBUTE.get(key);

            if (attribute != null) {
                return attribute;
            }
        }

        String enumName = raw.toUpperCase()
                .replace("MINECRAFT:", "")
                .replace('.', '_')
                .replace('-', '_');

        try {
            return Attribute.valueOf(enumName);
        } catch (IllegalArgumentException exception) {
            return null;
        }
    }

    private AttributeModifier.Operation operation(MetaAttributeOperation operation) {
        if (operation == null) {
            return AttributeModifier.Operation.ADD_NUMBER;
        }

        return switch (operation) {
            case ADD_NUMBER -> AttributeModifier.Operation.ADD_NUMBER;
            case ADD_SCALAR -> AttributeModifier.Operation.ADD_SCALAR;
            case MULTIPLY_SCALAR_1 -> AttributeModifier.Operation.MULTIPLY_SCALAR_1;
        };
    }

    private EquipmentSlotGroup slotGroup(MetaEquipmentSlot slot) {
        if (slot == null) {
            return EquipmentSlotGroup.ANY;
        }

        return switch (slot) {
            case HEAD -> EquipmentSlotGroup.HEAD;
            case CHEST -> EquipmentSlotGroup.CHEST;
            case LEGS -> EquipmentSlotGroup.LEGS;
            case FEET -> EquipmentSlotGroup.FEET;
            case MAIN_HAND -> EquipmentSlotGroup.MAINHAND;
            case OFF_HAND -> EquipmentSlotGroup.OFFHAND;
            case BODY -> EquipmentSlotGroup.ARMOR;
        };
    }

    private String sanitizeModifierKey(String raw) {
        if (raw == null || raw.isBlank()) {
            return "modifier";
        }

        return raw.toLowerCase()
                .replace("minecraft:", "")
                .replaceAll("[^a-z0-9_./-]", "_");
    }
}