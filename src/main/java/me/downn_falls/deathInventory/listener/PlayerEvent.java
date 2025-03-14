package me.downn_falls.deathInventory.listener;

import me.downn_falls.deathInventory.DeathInventory;
import me.downn_falls.deathInventory.manager.PlayerData;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerEvent implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        DeathInventory.getTemporaryDataInterface().loadData(event.getPlayer());
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {

        if (!event.getKeepInventory()) {

            Player player = event.getEntity();
            PlayerData playerData = PlayerData.get(player);

            ItemStack[] inventory = player.getInventory().getStorageContents();
            ItemStack[] armor = player.getInventory().getArmorContents();
            ItemStack offhand = player.getInventory().getItemInOffHand();

            for (ItemStack item : inventory) {
                if (item != null && item.getType() != Material.AIR) {
                    playerData.addItem(item);
                }
            }

            for (ItemStack item : armor) {
                if (item != null && item.getType() != Material.AIR) {
                    playerData.addItem(item);
                }
            }

            if (offhand.getType() != Material.AIR) {
                playerData.addItem(offhand);
            }
        }

    }
}
