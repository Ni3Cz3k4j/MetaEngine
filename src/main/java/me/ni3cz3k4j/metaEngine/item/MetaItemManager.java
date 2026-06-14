package me.ni3cz3k4j.metaEngine.item;

import me.ni3cz3k4j.metaEngine.item.settings.MetaConsumeAnimation;
import me.ni3cz3k4j.metaEngine.item.settings.MetaItemSettings;
import me.ni3cz3k4j.metaEngine.registry.MetaKey;
import me.ni3cz3k4j.metaEngine.registry.MetaRegistries;
import me.ni3cz3k4j.metaEngine.text.MetaText;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.CustomModelDataComponent;
import org.bukkit.inventory.meta.components.FoodComponent;
import org.bukkit.inventory.meta.components.UseCooldownComponent;
import org.bukkit.inventory.meta.components.consumable.ConsumableComponent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class MetaItemManager {
    private final JavaPlugin plugin;
    private final MetaRegistries registries;
    private final NamespacedKey itemIdKey;

    public MetaItemManager(JavaPlugin plugin, MetaRegistries registries) {
        this.plugin = plugin;
        this.registries = registries;
        this.itemIdKey = new NamespacedKey(plugin, "item_id");
    }

    public ItemStack createItem(MetaKey key) {
        MetaItem item = registries.items()
                .get(key)
                .orElseThrow(() -> new IllegalArgumentException("Unknown MetaItem :" + key + "."));

        ItemStack stack = new ItemStack(item.material());
        ItemMeta meta = stack.getItemMeta();

        if (meta == null) {
            return stack;
        }

        meta.getPersistentDataContainer().set(itemIdKey, PersistentDataType.STRING, key.asString());
        applySettings(key, item, meta);
        stack.setItemMeta(meta);
        return stack;
    }

    private void applySettings(MetaKey key, MetaItem item, ItemMeta meta) {
        MetaItemSettings settings = item.settings();

        if (settings.displayName() != null) {
            meta.setItemName(MetaText.color(settings.displayName()));
        }

        if (!settings.lore().isEmpty()) {
            List<String> coloredLore = new ArrayList<>();

            for (String line : settings.lore()) {
                coloredLore.add(MetaText.color(line));
            }

            meta.setLore(coloredLore);
        }

        if (settings.maxStackSize() != null) {
            meta.setMaxStackSize(settings.maxStackSize());
        }

        if (settings.glint() != null) {
            meta.setEnchantmentGlintOverride(settings.glint());
        }

        if (settings.unbreakable() != null) {
            meta.setUnbreakable(settings.unbreakable());
        }

        if (settings.rarity() != null) {
            meta.setRarity(settings.rarity());
        }

        if (settings.useRemainder() != null) {
            meta.setUseRemainder(new ItemStack(settings.useRemainder()));
        }

        if (settings.model() != null) {
            CustomModelDataComponent modelData = meta.getCustomModelDataComponent();
            modelData.setStrings(List.of(settings.model().modelId()));
            meta.setCustomModelDataComponent(modelData);
        }

        if (settings.cooldown() != null) {
            UseCooldownComponent cooldown = meta.getUseCooldown();
            cooldown.setCooldownSeconds(settings.cooldown().seconds());

            String groupPath = settings.cooldown().groupPath();
            String finalGroupPath = groupPath != null ? groupPath : key.path();

            cooldown.setCooldownGroup(new NamespacedKey(key.namespace(), finalGroupPath));
            meta.setUseCooldown(cooldown);
        }

        if (settings.food() != null) {
            FoodComponent food = meta.getFood();
            food.setNutrition(settings.food().nutrition());
            food.setSaturation(settings.food().saturation());
            food.setCanAlwaysEat(settings.food().alwaysEat());
            meta.setFood(food);
        }

        if (settings.consumable() != null) {
            ConsumableComponent consumable = meta.getConsumable();
            consumable.setConsumeSeconds(settings.consumable().consumeSeconds());
            consumable.setConsumeParticles(settings.consumable().particles());
            ConsumableComponent.Animation animation = mapAnimation(settings.consumable().animation());
            consumable.setAnimation(animation);
            meta.setConsumable(consumable);
        }
    }

    private ConsumableComponent.Animation mapAnimation(MetaConsumeAnimation animation) {
        return switch (animation) {
            case EAT -> ConsumableComponent.Animation.EAT;
            case DRINK -> ConsumableComponent.Animation.DRINK;
            case BLOCK -> ConsumableComponent.Animation.BLOCK;
            case BOW -> ConsumableComponent.Animation.BOW;
            case SPEAR -> ConsumableComponent.Animation.SPEAR;
            case CROSSBOW -> ConsumableComponent.Animation.CROSSBOW;
            case TOOT_HORN -> ConsumableComponent.Animation.TOOT_HORN;
            case BRUSH -> ConsumableComponent.Animation.BRUSH;
        };
    }

    public Optional<MetaItem> identify(ItemStack stack) {
        if (stack == null || !stack.hasItemMeta()) {
            return Optional.empty();
        }

        ItemMeta meta = stack.getItemMeta();

        if (meta == null) {
            return Optional.empty();
        }

        String rawId = meta.getPersistentDataContainer().get(itemIdKey, PersistentDataType.STRING);

        if (rawId == null) {
            return Optional.empty();
        }

        MetaKey key;

        try {
            key = MetaKey.parse(rawId);
        } catch (IllegalArgumentException exception) {
            return Optional.empty();
        }

        return registries.items().get(key);
    }

    public boolean isMetaItem(ItemStack stack) {
        return identify(stack).isPresent();
    }

    public boolean isMetaItem(ItemStack stack, MetaKey key) {
        return identify(stack)
                .map(item -> item.key().equals(key))
                .orElse(false);
    }
}