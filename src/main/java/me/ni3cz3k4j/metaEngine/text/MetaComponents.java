package me.ni3cz3k4j.metaEngine.text;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TranslatableComponent;
import net.kyori.adventure.text.format.TextColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class MetaComponents {
    private static final Pattern HEX_PATTERN = Pattern.compile("&#([A-Fa-f0-9]{6})");

    private MetaComponents() {}

    public static Component legacy(String text) {
        if (text == null || text.isEmpty()) {
            return Component.empty();
        }

        Component result = Component.empty();
        Matcher matcher = HEX_PATTERN.matcher(text);
        int index = 0;
        TextColor currentColor = null;

        while (matcher.find()) {
            if (matcher.start() > index) {
                result = result.append(part(text.substring(index, matcher.start()), currentColor));
            }

            currentColor = TextColor.color(Integer.parseInt(matcher.group(1), 16));
            index = matcher.end();
        }

        if (index < text.length()) {
            result = result.append(part(text.substring(index), currentColor));
        }

        return result;
    }

    public static Component translatable(String key, String fallback) {
        if (key == null || key.isBlank()) {
            return legacy(fallback);
        }

        TranslatableComponent component = Component.translatable(key);

        if (fallback != null && !fallback.isBlank()) {
            component = component.fallback(stripLegacyMarkers(fallback));
        }

        return component;
    }

    private static Component part(String text, TextColor color) {
        String converted = text.replace('&', '§');
        Component component = Component.text(converted);

        if (color != null) {
            component = component.color(color);
        }

        return component;
    }

    private static String stripLegacyMarkers(String text) {
        return HEX_PATTERN.matcher(text.replaceAll("&[0-9a-fk-orA-FK-OR]", "")).replaceAll("");
    }
}