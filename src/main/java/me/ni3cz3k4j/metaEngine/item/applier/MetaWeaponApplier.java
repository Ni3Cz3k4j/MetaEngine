package me.ni3cz3k4j.metaEngine.item.applier;

import me.ni3cz3k4j.metaEngine.item.MetaItem;
import me.ni3cz3k4j.metaEngine.item.MetaItemApplier;
import me.ni3cz3k4j.metaEngine.item.MetaItemApplyContext;
import me.ni3cz3k4j.metaEngine.item.component.MetaWeaponComponent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.AttackRangeComponent;
import org.bukkit.inventory.meta.components.WeaponComponent;

public final class MetaWeaponApplier implements MetaItemApplier<MetaWeaponComponent> {
    @Override
    public Class<MetaWeaponComponent> componentType() {
        return MetaWeaponComponent.class;
    }

    @Override
    public void apply(MetaItem item, MetaWeaponComponent component, ItemStack stack, ItemMeta meta, MetaItemApplyContext context) {
        WeaponComponent weapon = meta.getWeapon();

        if (component.disableBlockingForSeconds() != null) {
            weapon.setDisableBlockingForSeconds(component.disableBlockingForSeconds() ? 5.0f : 0.0f);
        }

        meta.setWeapon(weapon);

        if (component.minimumAttackCharge() != null) {
            meta.setMinimumAttackCharge(component.minimumAttackCharge().floatValue());
        }

        if (component.attackRange() != null) {
            AttackRangeComponent attackRange = meta.getAttackRange();
            attackRange.setMaxReach(component.attackRange().floatValue());
            meta.setAttackRange(attackRange);
        }

        if (Boolean.TRUE.equals(component.piercing())) {
            meta.setPiercingWeapon(meta.getPiercingWeapon());
        }

        if (Boolean.TRUE.equals(component.kinetic())) {
            meta.setKineticWeapon(meta.getKineticWeapon());
        }
    }
}