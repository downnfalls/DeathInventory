package me.downn_falls.deathInventory.listener;

import me.downn_falls.deathInventory.DeathInventory;
import me.downn_falls.deathInventory.manager.PlayerData;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class PlayerEvent implements Listener {

    ConfigurationSection configSetting = DeathInventory.getInstance().getConfig().getConfigurationSection("setting");

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        DeathInventory.getTemporaryDataInterface().loadData(event.getPlayer());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        DeathInventory.getTemporaryDataInterface().saveData(event.getPlayer());
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {

        Player player = event.getEntity();
        PlayerData playerData = PlayerData.get(player);

        ItemStack[] inventory = player.getInventory().getContents();
        ItemStack[] armor = player.getInventory().getArmorContents();
        ItemStack offhand = player.getInventory().getItemInOffHand();

        for (int slot : configSetting.getIntegerList("artifact_slot")) {
            inventory[slot] = null;
        }

        com.francobm.magicosmetics.cache.PlayerData cosmeticData = com.francobm.magicosmetics.cache.PlayerData.getPlayer(event.getEntity());
        if (cosmeticData != null) {
            cosmeticData.clearCosmeticsInUse(false);
            if (cosmeticData.getHat() != null && cosmeticData.getHat().isCosmetic(armor[3])) {
                if (cosmeticData.getHat().getCurrentItemSaved() != null && cosmeticData.getHat().isOverlaps()) {
                    armor[3] = cosmeticData.getHat().leftItemAndGet();
                }
            }
            if (cosmeticData.getWStick() != null && cosmeticData.getWStick().isCosmetic(offhand)) {
                if (cosmeticData.getWStick().getCurrentItemSaved() != null && cosmeticData.getWStick().isOverlaps()) {
                    offhand = cosmeticData.getWStick().leftItemAndGet();
                }
            }
        }

        DeathData deathData = new DeathData(UUID.randomUUID(), System.currentTimeMillis(), playerData.getDeathCount() + 1, inventory, armor, offhand);
        playerData.triggerDeath(deathData);

    }
}
