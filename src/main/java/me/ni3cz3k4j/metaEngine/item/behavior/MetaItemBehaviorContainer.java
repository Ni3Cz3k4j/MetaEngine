package me.ni3cz3k4j.metaEngine.item.behavior;

import java.util.ArrayList;
import java.util.List;

public final class MetaItemBehaviorContainer {
    private final List<MetaItemUseHandler> useHandlers = new ArrayList<>();
    private final List<MetaItemClickHandler> clickHandlers = new ArrayList<>();
    private final List<MetaItemAttackHandler> attackHandlers = new ArrayList<>();
    private final List<MetaItemConsumeHandler> consumeHandlers = new ArrayList<>();
    private final List<MetaItemDropHandler> dropHandlers = new ArrayList<>();
    private final List<MetaItemPickupHandler> pickupHandlers = new ArrayList<>();
    private final List<MetaItemInventoryClickHandler> inventoryClickHandlers = new ArrayList<>();
    private final List<MetaItemCraftPrepareHandler> craftPrepareHandlers = new ArrayList<>();
    private final List<MetaItemCraftHandler> craftHandlers = new ArrayList<>();
    private final List<MetaItemTickHandler> tickHandlers = new ArrayList<>();

    public MetaItemBehaviorContainer onUse(MetaItemUseHandler handler) {
        useHandlers.add(handler);
        return this;
    }

    public MetaItemBehaviorContainer onClick(MetaItemClickHandler handler) {
        clickHandlers.add(handler);
        return this;
    }

    public MetaItemBehaviorContainer onAttack(MetaItemAttackHandler handler) {
        attackHandlers.add(handler);
        return this;
    }

    public MetaItemBehaviorContainer onConsume(MetaItemConsumeHandler handler) {
        consumeHandlers.add(handler);
        return this;
    }

    public MetaItemBehaviorContainer onDrop(MetaItemDropHandler handler) {
        dropHandlers.add(handler);
        return this;
    }

    public MetaItemBehaviorContainer onPickup(MetaItemPickupHandler handler) {
        pickupHandlers.add(handler);
        return this;
    }

    public MetaItemBehaviorContainer onInventoryClick(MetaItemInventoryClickHandler handler) {
        inventoryClickHandlers.add(handler);
        return this;
    }

    public MetaItemBehaviorContainer onCraftPrepare(MetaItemCraftPrepareHandler handler) {
        craftPrepareHandlers.add(handler);
        return this;
    }

    public MetaItemBehaviorContainer onCraft(MetaItemCraftHandler handler) {
        craftHandlers.add(handler);
        return this;
    }

    public MetaItemBehaviorContainer onTick(MetaItemTickHandler handler) {
        tickHandlers.add(handler);
        return this;
    }

    public List<MetaItemUseHandler> useHandlers() {
        return List.copyOf(useHandlers);
    }

    public List<MetaItemClickHandler> clickHandlers() {
        return List.copyOf(clickHandlers);
    }

    public List<MetaItemAttackHandler> attackHandlers() {
        return List.copyOf(attackHandlers);
    }

    public List<MetaItemConsumeHandler> consumeHandlers() {
        return List.copyOf(consumeHandlers);
    }

    public List<MetaItemDropHandler> dropHandlers() {
        return List.copyOf(dropHandlers);
    }

    public List<MetaItemPickupHandler> pickupHandlers() {
        return List.copyOf(pickupHandlers);
    }

    public List<MetaItemInventoryClickHandler> inventoryClickHandlers() {
        return List.copyOf(inventoryClickHandlers);
    }

    public List<MetaItemCraftPrepareHandler> craftPrepareHandlers() {
        return List.copyOf(craftPrepareHandlers);
    }

    public List<MetaItemCraftHandler> craftHandlers() {
        return List.copyOf(craftHandlers);
    }

    public List<MetaItemTickHandler> tickHandlers() {
        return List.copyOf(tickHandlers);
    }

    public boolean hasTickHandlers() {
        return !tickHandlers.isEmpty();
    }
}