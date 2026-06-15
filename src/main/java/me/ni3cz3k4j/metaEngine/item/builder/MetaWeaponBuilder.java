package me.ni3cz3k4j.metaEngine.item.builder;

import me.ni3cz3k4j.metaEngine.item.component.MetaWeaponComponent;

public final class MetaWeaponBuilder {
    private Boolean disableBlockingForSeconds;
    private Boolean piercing;
    private Boolean kinetic;
    private Double minimumAttackCharge;
    private Double attackRange;

    public MetaWeaponBuilder disableBlockingForSeconds(boolean value) {
        this.disableBlockingForSeconds = value;
        return this;
    }

    public MetaWeaponBuilder piercing(boolean piercing) {
        this.piercing = piercing;
        return this;
    }

    public MetaWeaponBuilder kinetic(boolean kinetic) {
        this.kinetic = kinetic;
        return this;
    }

    public MetaWeaponBuilder minimumAttackCharge(double minimumAttackCharge) {
        this.minimumAttackCharge = minimumAttackCharge;
        return this;
    }

    public MetaWeaponBuilder attackRange(double attackRange) {
        this.attackRange = attackRange;
        return this;
    }

    public MetaWeaponComponent build() {
        return new MetaWeaponComponent(disableBlockingForSeconds, piercing, kinetic, minimumAttackCharge, attackRange);
    }
}