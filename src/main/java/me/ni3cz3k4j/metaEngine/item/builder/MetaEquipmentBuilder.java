package me.ni3cz3k4j.metaEngine.item.builder;

import me.ni3cz3k4j.metaEngine.item.component.MetaEquipmentComponent;
import me.ni3cz3k4j.metaEngine.item.component.MetaEquipmentSlot;

public final class MetaEquipmentBuilder {
    private MetaEquipmentSlot slot;
    private String equipSound;
    private String assetId;
    private String cameraOverlay;
    private Boolean dispensable;
    private Boolean swappable;
    private Boolean damageOnHurt;

    public MetaEquipmentBuilder slot(MetaEquipmentSlot slot) {
        this.slot = slot;
        return this;
    }

    public MetaEquipmentBuilder equipSound(String equipSound) {
        this.equipSound = equipSound;
        return this;
    }

    public MetaEquipmentBuilder assetId(String assetId) {
        this.assetId = assetId;
        return this;
    }

    public MetaEquipmentBuilder cameraOverlay(String cameraOverlay) {
        this.cameraOverlay = cameraOverlay;
        return this;
    }

    public MetaEquipmentBuilder dispensable(boolean dispensable) {
        this.dispensable = dispensable;
        return this;
    }

    public MetaEquipmentBuilder swappable(boolean swappable) {
        this.swappable = swappable;
        return this;
    }

    public MetaEquipmentBuilder damageOnHurt(boolean damageOnHurt) {
        this.damageOnHurt = damageOnHurt;
        return this;
    }

    public MetaEquipmentComponent build() {
        return new MetaEquipmentComponent(slot, equipSound, assetId, cameraOverlay, dispensable, swappable, damageOnHurt);
    }
}