package me.ni3cz3k4j.metaEngine.item.builder;

import me.ni3cz3k4j.metaEngine.item.component.*;

import java.util.ArrayList;
import java.util.List;

public final class MetaConsumableBuilder {
    private float consumeSeconds = 1.6f;
    private MetaUseAnimation animation = MetaUseAnimation.EAT;
    private boolean particles = true;
    private String sound;
    private final List<MetaConsumeEffect> effects = new ArrayList<>();

    public MetaConsumableBuilder seconds(float seconds) {
        this.consumeSeconds = seconds;
        return this;
    }

    public MetaConsumableBuilder animation(MetaUseAnimation animation) {
        this.animation = animation;
        return this;
    }

    public MetaConsumableBuilder particles(boolean particles) {
        this.particles = particles;
        return this;
    }

    public MetaConsumableBuilder sound(String sound) {
        this.sound = sound;
        return this;
    }

    public MetaConsumableBuilder effect(MetaConsumeEffect effect) {
        this.effects.add(effect);
        return this;
    }

    public MetaConsumableBuilder potionEffect(String effectKey, int durationTicks, int amplifier, float probability) {
        this.effects.add(new MetaPotionConsumeEffect(effectKey, durationTicks, amplifier, probability));
        return this;
    }

    public MetaConsumableBuilder clearEffects() {
        this.effects.add(new MetaClearEffectsConsumeEffect());
        return this;
    }

    public MetaConsumableBuilder teleportRandomly(float diameter) {
        this.effects.add(new MetaTeleportRandomlyConsumeEffect(diameter));
        return this;
    }

    public MetaConsumableComponent build() {
        return new MetaConsumableComponent(consumeSeconds, animation, particles, sound, List.copyOf(effects));
    }
}