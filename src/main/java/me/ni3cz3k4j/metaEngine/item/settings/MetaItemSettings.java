package me.ni3cz3k4j.metaEngine.item.settings;

import org.bukkit.Material;
import org.bukkit.inventory.ItemRarity;

import java.util.ArrayList;
import java.util.List;

public final class MetaItemSettings {
    private String displayName;
    private final List<String> lore = new ArrayList<>();
    private Integer maxStackSize;
    private Boolean glint;
    private Boolean unbreakable;
    private ItemRarity rarity;
    private MetaModelSettings model;
    private MetaCooldownSettings cooldown;
    private MetaFoodSettings food;
    private MetaConsumableSettings consumable;
    private Material useRemainder;

    public String displayName() {
        return displayName;
    }

    public List<String> lore() {
        return lore;
    }

    public Integer maxStackSize() {
        return maxStackSize;
    }

    public Boolean glint() {
        return glint;
    }

    public Boolean unbreakable() {
        return unbreakable;
    }

    public ItemRarity rarity() {
        return rarity;
    }

    public MetaModelSettings model() {
        return model;
    }

    public MetaCooldownSettings cooldown() {
        return cooldown;
    }

    public MetaFoodSettings food() {
        return food;
    }

    public MetaConsumableSettings consumable() {
        return consumable;
    }

    public Material useRemainder() {
        return useRemainder;
    }

    public MetaItemSettings name(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public MetaItemSettings lore(String... lines) {
        this.lore.clear();

        for (String line : lines) {
            this.lore.add(line);
        }

        return this;
    }

    public MetaItemSettings maxStackSize(int maxStackSize) {
        this.maxStackSize = maxStackSize;
        return this;
    }

    public MetaItemSettings glint(boolean glint) {
        this.glint = glint;
        return this;
    }

    public MetaItemSettings unbreakable(boolean unbreakable) {
        this.unbreakable = unbreakable;
        return this;
    }

    public MetaItemSettings rarity(ItemRarity rarity) {
        this.rarity = rarity;
        return this;
    }

    public MetaItemSettings modelTexture(String path) {
        this.model = MetaModelSettings.texture(path);
        return this;
    }

    public MetaItemSettings customModel(String path) {
        this.model = MetaModelSettings.customModel(path);
        return this;
    }

    public MetaItemSettings useCooldown(float seconds) {
        this.cooldown = new MetaCooldownSettings(seconds, null);
        return this;
    }

    public MetaItemSettings useCooldown(float seconds, String groupPath) {
        this.cooldown = new MetaCooldownSettings(seconds, groupPath);
        return this;
    }

    public MetaItemSettings food(int nutrition, float saturation, boolean alwaysEat) {
        this.food = new MetaFoodSettings(nutrition, saturation, alwaysEat);
        return this;
    }

    public MetaItemSettings consumable(float consumeSeconds, MetaConsumeAnimation animation, boolean particles) {
        this.consumable = new MetaConsumableSettings(consumeSeconds, animation, particles);
        return this;
    }

    public MetaItemSettings useRemainder(Material material) {
        this.useRemainder = material;
        return this;
    }
}