package me.downn_falls.deathInventory.manager;

import me.downn_falls.deathInventory.DeathInventory;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.UUID;

public class PlayerData {

    private final Player player;
    private final List<ItemData> items;

    public PlayerData(Player player, List<ItemData> items) {
        this.player = player;
        this.items = items;
    }

    public Player getPlayer() {
        return player;
    }

    public List<ItemData> getItems() { return items; }

    public void addItem(ItemData item) {
        items.add(item);

        DeathInventory.getPersistentDataInterface().addItem(player, item);
    }

    public void addItem(ItemStack item) {

        ItemData itemData = new ItemData(UUID.randomUUID(), System.currentTimeMillis(), item);
        addItem(itemData);

    }

    public void removeItem(ItemData item) {
        items.remove(item);
        DeathInventory.getPersistentDataInterface().removeItem(player, item);
    }
}
