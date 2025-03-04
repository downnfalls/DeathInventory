package me.downn_falls.deathInventory.commands;

import com.google.gson.Gson;
import me.downn_falls.deathInventory.DeathInventory;
import me.downn_falls.deathInventory.inventory.DeathInventoryUI;
import me.downn_falls.deathInventory.manager.DeathData;
import me.downn_falls.deathInventory.utils.ItemStackSerializer;
import me.downn_falls.deathInventory.utils.PushList;
import me.downn_falls.guiapi.ItemStackBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class DeathInventoryCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player player) {
            if (args.length == 1) {

                Gson gson = DeathInventory.getGson();

                ItemStack itemStack = player.getInventory().getItemInMainHand();
                //ItemStack itemStack = new ItemStackBuilder(Material.STONE, 1).build();

                String json = gson.toJson(itemStack);
                player.sendMessage(json);


            } else {
                new DeathInventoryUI(player).open(player);
            }
        }

        return false;
    }
}
