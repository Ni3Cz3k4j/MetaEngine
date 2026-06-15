package me.ni3cz3k4j.metaEngine.item.builder;

import me.ni3cz3k4j.metaEngine.item.component.MetaUseAnimation;
import me.ni3cz3k4j.metaEngine.item.component.MetaUseComponent;

public final class MetaUseBuilder {
    private Float useDurationSeconds;
    private MetaUseAnimation animation;
    private Boolean consumeOnUse;
    private Boolean releaseToUse;
    private String useSound;
    private String breakSound;

    public MetaUseBuilder durationSeconds(float seconds) {
        this.useDurationSeconds = seconds;
        return this;
    }

    public MetaUseBuilder animation(MetaUseAnimation animation) {
        this.animation = animation;
        return this;
    }

    public MetaUseBuilder consumeOnUse(boolean consumeOnUse) {
        this.consumeOnUse = consumeOnUse;
        return this;
    }

    public MetaUseBuilder releaseToUse(boolean releaseToUse) {
        this.releaseToUse = releaseToUse;
        return this;
    }

    public MetaUseBuilder useSound(String useSound) {
        this.useSound = useSound;
        return this;
    }

    public MetaUseBuilder breakSound(String breakSound) {
        this.breakSound = breakSound;
        return this;
    }

    public MetaUseComponent build() {
        return new MetaUseComponent(useDurationSeconds, animation, consumeOnUse, releaseToUse, useSound, breakSound);
    }
}