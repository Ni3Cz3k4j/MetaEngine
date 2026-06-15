package me.ni3cz3k4j.metaEngine.item.builder;

import me.ni3cz3k4j.metaEngine.item.component.*;

import java.util.ArrayList;
import java.util.List;

public final class MetaAttributeBuilder {
    private final List<MetaAttributeModifier> modifiers = new ArrayList<>();

    public MetaAttributeBuilder modifier(
            String attribute,
            String id,
            double amount,
            MetaAttributeOperation operation,
            MetaEquipmentSlot slot
    ) {
        modifiers.add(new MetaAttributeModifier(attribute, id, amount, operation, slot));
        return this;
    }

    public MetaAttributeBuilder attackDamage(String id, double amount, MetaEquipmentSlot slot) {
        return modifier("minecraft:attack_damage", id, amount, MetaAttributeOperation.ADD_NUMBER, slot);
    }

    public MetaAttributeBuilder attackSpeed(String id, double amount, MetaEquipmentSlot slot) {
        return modifier("minecraft:attack_speed", id, amount, MetaAttributeOperation.ADD_NUMBER, slot);
    }

    public MetaAttributeComponent build() {
        return new MetaAttributeComponent(List.copyOf(modifiers));
    }
}