package me.downn_falls.deathInventory.database;

import me.downn_falls.deathInventory.manager.ItemData;
import me.downn_falls.deathInventory.manager.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public interface PersistentDataInterface {
    void loadDeathInventories(Player player, Consumer<PlayerData> consumer);
    void addItem(Player player, ItemData item);
    void removeItem(Player player, ItemData item);
    void disconnect();
}
