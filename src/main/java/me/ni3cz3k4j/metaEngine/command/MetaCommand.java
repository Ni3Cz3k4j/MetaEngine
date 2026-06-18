package me.ni3cz3k4j.metaEngine.command;

import me.ni3cz3k4j.metaEngine.MetaEngine;
import me.ni3cz3k4j.metaEngine.item.MetaItem;
import me.ni3cz3k4j.metaEngine.item.MetaItemManager;
import me.ni3cz3k4j.metaEngine.registry.MetaKey;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public final class MetaCommand implements CommandExecutor {
    private final MetaEngine plugin;
    private final MetaItemManager itemManager;

    public MetaCommand(MetaEngine plugin, MetaItemManager itemManager) {
        this.plugin = plugin;
        this.itemManager = itemManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (args.length < 1) {
            sendHelp(sender);
            return true;
        }

        return switch (args[0].toLowerCase()) {
            case "give" -> handleGive(sender, args);
            case "id", "identify" -> handleIdentify(sender);
            case "list" -> handleList(sender);
            case "reloadpack", "pack" -> handleReloadPack(sender);
            default -> {
                sendHelp(sender);
                yield true;
            }
        };
    }

    private boolean handleGive(CommandSender sender, String[] args) {
        if (args.length < 3) {
            sender.sendMessage("Usage: /meta give <namespace:id> <player> [amount]");
            return true;
        }

        MetaKey key;

        try {
            key = MetaKey.parse(args[1]);
        } catch (IllegalArgumentException exception) {
            sender.sendMessage("Invalid item key. Example: namespace:id");
            return true;
        }

        Player target = Bukkit.getPlayerExact(args[2]);

        if (target == null) {
            sender.sendMessage("Player not found: " + args[2]);
            return true;
        }

        int amount = 1;

        if (args.length >= 4) {
            try {
                amount = Integer.parseInt(args[3]);
            } catch (NumberFormatException exception) {
                sender.sendMessage("Amount must be a number.");
                return true;
            }
        }

        if (amount <= 0) {
            sender.sendMessage("Amount must be greater than 0.");
            return true;
        }

        try {
            itemManager.give(target, key, amount);
            sender.sendMessage("Given " + amount + "x " + key.asString() + " to " + target.getName() + ".");
        } catch (IllegalArgumentException exception) {
            sender.sendMessage(exception.getMessage());
        }

        return true;
    }

    private boolean handleIdentify(CommandSender sender) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        ItemStack stack = player.getInventory().getItemInMainHand();

        Optional<MetaItem> item = itemManager.identify(stack);

        if (item.isEmpty()) {
            sender.sendMessage("Main hand item is not a MetaItem.");
            return true;
        }

        sender.sendMessage("MetaItem: " + item.get().key().asString());
        return true;
    }

    private boolean handleList(CommandSender sender) {
        sender.sendMessage("Registered MetaItems:");

        int count = 0;

        for (MetaKey key : itemManager.registries().items().keys()) {
            sender.sendMessage("- " + key.asString());
            count++;
        }

        if (count == 0) {
            sender.sendMessage("- none");
        }

        return true;
    }

    private boolean handleReloadPack(CommandSender sender) {
        plugin.regenerateResourcePack();
        sender.sendMessage("Resource pack regenerated.");
        return true;
    }

    private void sendHelp(CommandSender sender) {
        sender.sendMessage("MetaEngine commands:");
        sender.sendMessage("/meta give <namespace:id> <player> [amount]");
        sender.sendMessage("/meta id");
        sender.sendMessage("/meta list");
        sender.sendMessage("/meta reloadpack");
    }
}