package me.downn_falls.deathInventory.cache;

import me.downn_falls.deathInventory.DeathInventory;
import me.downn_falls.deathInventory.manager.PlayerData;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class LocalDataManager implements TemporaryDataInterface {


    HashMap<Player, PlayerData> deathData = new HashMap<>();


    @Override
    public void loadData(Player player) {
        DeathInventory.getPersistentDataInterface().loadDeathInventories(player, (playerData) -> {
            deathData.put(player, playerData);
        });
    }

    @Override
    public PlayerData getData(Player player) {
        return deathData.get(player);
    }
}
