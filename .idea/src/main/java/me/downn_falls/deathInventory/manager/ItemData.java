package me.downn_falls.deathInventory.manager;

import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class ItemData {

    private final ItemStack item;
    private final long timestamp;
    private final UUID item_uuid;

    public ItemData(final UUID item_uuid, final long timestamp, final ItemStack item) {
        this.item_uuid = item_uuid;
        this.item = item;
        this.timestamp = timestamp;
    }

    public ItemStack getItem() {
        return item;
    }
    public long getTimestamp() {
        return timestamp;
    }
    public UUID getItemUuid() {
        return item_uuid;
    }
}
