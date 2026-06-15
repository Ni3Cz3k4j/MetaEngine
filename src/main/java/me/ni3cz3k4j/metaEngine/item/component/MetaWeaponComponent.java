package me.ni3cz3k4j.metaEngine.item.component;

public record MetaWeaponComponent(Boolean disableBlockingForSeconds, Boolean piercing, Boolean kinetic, Double minimumAttackCharge, Double attackRange) implements MetaItemComponent {}