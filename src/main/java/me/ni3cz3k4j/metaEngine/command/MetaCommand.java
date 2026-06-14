package me.ni3cz3k4j.metaEngine.command;

import me.ni3cz3k4j.metaEngine.item.MetaItemManager;
import me.ni3cz3k4j.metaEngine.registry.MetaKey;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public final class MetaCommand implements CommandExecutor {
    private final MetaItemManager itemManager;

    public MetaCommand(MetaItemManager itemManager) {
        this.itemManager = itemManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (args.length < 1) {
            sender.sendMessage("Usage: /meta give <item_key> <player> <amount>");
            return true;
        }

        if (args[0].equalsIgnoreCase("give")) {
            return handleGive(sender, args);
        }

        sender.sendMessage("Unknown subcommand. Usage: /meta give <item_key> <player> <amount>");
        return true;
    }

    private boolean handleGive(CommandSender sender, String[] args) {
        if (args.length < 3) {
            sender.sendMessage("Usage: /meta give <item_key> <player> <amount>");
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
            ItemStack itemStack = itemManager.createItem(key);
            itemStack.setAmount(Math.min(amount, itemStack.getMaxStackSize()));

            target.getInventory().addItem(itemStack);
            sender.sendMessage("Given " + amount + "x " + key.asString() + " to "+ target.getName());
        } catch (IllegalArgumentException exception) {
            sender.sendMessage("Unknown MetaItem: " + key.asString());
        }

        return true;
    }
}