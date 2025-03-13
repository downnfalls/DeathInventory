package me.downn_falls.deathInventory.commands;

import me.downn_falls.deathInventory.inventory.DeathInventoryUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DeathInventoryCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player player) {
            if (args.length == 1) {
            } else {
                new DeathInventoryUI(player).open(player);
            }
        }

        return false;
    }
}
