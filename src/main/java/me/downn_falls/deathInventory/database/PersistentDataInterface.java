package me.downn_falls.deathInventory.database;

import me.downn_falls.deathInventory.manager.DeathData;
import me.downn_falls.deathInventory.manager.PlayerData;
import me.downn_falls.deathInventory.utils.PushList;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.function.Consumer;

public interface PersistentDataInterface {
    void loadDeathInventories(Player player, Consumer<PlayerData> consumer);
    void saveDeathInventory(Player player, int deathCount, PushList<DeathData> inventory);
    void disconnect();
}
