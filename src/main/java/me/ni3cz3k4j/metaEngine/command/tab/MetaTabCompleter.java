package me.ni3cz3k4j.metaEngine.command.tab;

import me.ni3cz3k4j.metaEngine.registry.MetaKey;
import me.ni3cz3k4j.metaEngine.registry.MetaRegistries;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public final class MetaTabCompleter implements TabCompleter {
    private final MetaRegistries registries;

    public MetaTabCompleter(MetaRegistries registries) {
        this.registries = registries;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return filter(List.of("give"), args[0]);
        }

        if (args.length >= 2 && args[0].equalsIgnoreCase("give")) {
            return completeGive(args);
        }

        return List.of();
    }

    private List<String> completeGive(String[] args) {
        if (args.length == 2) {
            return completeItemKeys(args[1]);
        }

        if (args.length == 3) {
            return completePlayers(args[2]);
        }

        if (args.length == 4) {
            return filter(List.of("1", "2", "4", "8", "16", "32", "64"), args[3]);
        }

        return List.of();
    }

    private List<String> completeItemKeys(String input) {
        List<String> keys = new ArrayList<>();

        for (MetaKey key : registries.items().keys()) {
            keys.add(key.asString());
        }

        return filter(keys, input);
    }

    private List<String> completePlayers(String input) {
        List<String> players = new ArrayList<>();

        for (Player player : Bukkit.getOnlinePlayers()) {
            players.add(player.getName());
        }

        return filter(players, input);
    }

    private List<String> filter(List<String> values, String input) {
        String normalizedInput = input.toLowerCase(Locale.ROOT);
        List<String> result = new ArrayList<>();

        for (String value : values) {
            if (value.toLowerCase(Locale.ROOT).startsWith(normalizedInput)) {
                result.add(value);
            }
        }

        return result;
    }
}