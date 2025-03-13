package me.downn_falls.deathInventory.cache;

import me.downn_falls.deathInventory.manager.PlayerData;
import org.bukkit.entity.Player;

public interface TemporaryDataInterface {

    void loadData(Player player);
    PlayerData getData(Player player);

}
