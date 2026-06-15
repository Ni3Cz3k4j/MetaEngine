package me.ni3cz3k4j.metaEngine.item;

import me.ni3cz3k4j.metaEngine.item.behavior.MetaItemBehaviorContainer;
import me.ni3cz3k4j.metaEngine.item.builder.*;
import me.ni3cz3k4j.metaEngine.item.component.*;
import me.ni3cz3k4j.metaEngine.registry.MetaKey;
import org.bukkit.Material;

import java.util.function.Consumer;

public final class MetaItemBuilder {
    private final MetaKey key;
    private Material baseMaterial = Material.STICK;
    private final MetaItemComponentContainer components = new MetaItemComponentContainer();
    private final MetaItemBehaviorContainer behaviors = new MetaItemBehaviorContainer();

    public MetaItemBuilder(MetaKey key) {
        this.key = key;
    }

    public MetaItemBuilder base(Material material) {
        this.baseMaterial = material;
        return this;
    }

    public MetaItemBuilder display(Consumer<MetaDisplayBuilder> consumer) {
        MetaDisplayBuilder builder = new MetaDisplayBuilder();
        consumer.accept(builder);
        components.set(MetaDisplayComponent.class, builder.build());
        return this;
    }

    public MetaItemBuilder model(Consumer<MetaModelBuilder> consumer) {
        MetaModelBuilder builder = new MetaModelBuilder(key);
        consumer.accept(builder);
        components.set(MetaModelComponent.class, builder.build());

        MetaCustomModelDataComponent customModelData = builder.buildCustomModelData();
        if (customModelData != null) {
            components.set(MetaCustomModelDataComponent.class, customModelData);
        }

        return this;
    }

    public MetaItemBuilder stack(Consumer<MetaStackBuilder> consumer) {
        MetaStackBuilder builder = new MetaStackBuilder();
        consumer.accept(builder);
        components.set(MetaStackComponent.class, builder.build());
        return this;
    }

    public MetaItemBuilder durability(Consumer<MetaDurabilityBuilder> consumer) {
        MetaDurabilityBuilder builder = new MetaDurabilityBuilder();
        consumer.accept(builder);
        components.set(MetaDurabilityComponent.class, builder.build());
        return this;
    }

    public MetaItemBuilder rarity(MetaItemRarity rarity) {
        components.set(MetaRarityComponent.class, new MetaRarityComponent(rarity));
        return this;
    }

    public MetaItemBuilder glint(boolean glint) {
        components.set(MetaGlintComponent.class, new MetaGlintComponent(glint));
        return this;
    }

    public MetaItemBuilder tooltip(Consumer<MetaTooltipBuilder> consumer) {
        MetaTooltipBuilder builder = new MetaTooltipBuilder();
        consumer.accept(builder);
        components.set(MetaTooltipComponent.class, builder.build());
        return this;
    }

    public MetaItemBuilder cooldown(Consumer<MetaCooldownBuilder> consumer) {
        MetaCooldownBuilder builder = new MetaCooldownBuilder(key);
        consumer.accept(builder);
        components.set(MetaCooldownComponent.class, builder.build());
        return this;
    }

    public MetaItemBuilder use(Consumer<MetaUseBuilder> consumer) {
        MetaUseBuilder builder = new MetaUseBuilder();
        consumer.accept(builder);
        components.set(MetaUseComponent.class, builder.build());
        return this;
    }

    public MetaItemBuilder food(Consumer<MetaFoodBuilder> consumer) {
        MetaFoodBuilder builder = new MetaFoodBuilder();
        consumer.accept(builder);
        components.set(MetaFoodComponent.class, builder.build());
        return this;
    }

    public MetaItemBuilder consumable(Consumer<MetaConsumableBuilder> consumer) {
        MetaConsumableBuilder builder = new MetaConsumableBuilder();
        consumer.accept(builder);
        components.set(MetaConsumableComponent.class, builder.build());
        return this;
    }

    public MetaItemBuilder tool(Consumer<MetaToolBuilder> consumer) {
        MetaToolBuilder builder = new MetaToolBuilder();
        consumer.accept(builder);
        components.set(MetaToolComponent.class, builder.build());
        return this;
    }

    public MetaItemBuilder weapon(Consumer<MetaWeaponBuilder> consumer) {
        MetaWeaponBuilder builder = new MetaWeaponBuilder();
        consumer.accept(builder);
        components.set(MetaWeaponComponent.class, builder.build());
        return this;
    }

    public MetaItemBuilder equipment(Consumer<MetaEquipmentBuilder> consumer) {
        MetaEquipmentBuilder builder = new MetaEquipmentBuilder();
        consumer.accept(builder);
        components.set(MetaEquipmentComponent.class, builder.build());
        return this;
    }

    public MetaItemBuilder attributes(Consumer<MetaAttributeBuilder> consumer) {
        MetaAttributeBuilder builder = new MetaAttributeBuilder();
        consumer.accept(builder);
        components.set(MetaAttributeComponent.class, builder.build());
        return this;
    }

    public MetaItemBuilder enchantments(Consumer<MetaEnchantmentsBuilder> consumer) {
        MetaEnchantmentsBuilder builder = new MetaEnchantmentsBuilder();
        consumer.accept(builder);
        components.set(MetaEnchantmentsComponent.class, builder.build());
        return this;
    }

    public MetaItemBuilder repair(Consumer<MetaRepairBuilder> consumer) {
        MetaRepairBuilder builder = new MetaRepairBuilder();
        consumer.accept(builder);
        components.set(MetaRepairComponent.class, builder.build());
        return this;
    }

    public MetaItemBuilder useRemainder(Material material) {
        components.set(MetaUseRemainderComponent.class, new MetaUseRemainderComponent(material));
        return this;
    }

    public MetaItemBuilder customData(Consumer<MetaCustomDataBuilder> consumer) {
        MetaCustomDataBuilder builder = new MetaCustomDataBuilder();
        consumer.accept(builder);
        components.set(MetaCustomDataComponent.class, builder.build());
        return this;
    }

    public MetaItemBuilder tags(Consumer<MetaTagsBuilder> consumer) {
        MetaTagsBuilder builder = new MetaTagsBuilder();
        consumer.accept(builder);
        components.set(MetaTagsComponent.class, builder.build());
        return this;
    }

    public MetaItemBuilder behavior(Consumer<MetaItemBehaviorContainer> consumer) {
        consumer.accept(behaviors);
        return this;
    }

    public MetaItem build() {
        return new MetaItem(key, baseMaterial, components, behaviors);
    }
}