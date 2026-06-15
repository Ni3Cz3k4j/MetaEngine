package me.ni3cz3k4j.metaEngine.item.builder;

import me.ni3cz3k4j.metaEngine.item.component.MetaFoodComponent;

public final class MetaFoodBuilder {
    private int nutrition;
    private float saturation;
    private boolean alwaysEat;

    public MetaFoodBuilder nutrition(int nutrition) {
        this.nutrition = nutrition;
        return this;
    }

    public MetaFoodBuilder saturation(float saturation) {
        this.saturation = saturation;
        return this;
    }

    public MetaFoodBuilder alwaysEat(boolean alwaysEat) {
        this.alwaysEat = alwaysEat;
        return this;
    }

    public MetaFoodComponent build() {
        return new MetaFoodComponent(nutrition, saturation, alwaysEat);
    }
}