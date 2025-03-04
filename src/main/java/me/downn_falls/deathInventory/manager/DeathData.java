package me.downn_falls.deathInventory.manager;

import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class DeathData {

    private final long date;
    private final int deathCount;
    private final ItemStack[] inventory;
    private final ItemStack[] armor;
    private final ItemStack offhand;

    public DeathData(long date, int deathCount, ItemStack[] inventory, ItemStack[] armor, ItemStack offhand) {
        this.date = date;
        this.deathCount = deathCount;
        this.inventory = inventory;
        this.armor = armor;
        this.offhand = offhand;
    }

    public long getDate() {
        return date;
    }

    public int getDeathCount() {
        return deathCount;
    }

    public ItemStack[] getInventory() {
        return inventory;
    }

    public ItemStack[] getArmor() {
        return armor;
    }

    public ItemStack getOffhand() {
        return offhand;
    }

    @Override
    public String toString() {
        return "{date: " + date + ", deathCount: " + deathCount + ", inventory: " + Arrays.toString(inventory) + ", armor: " + Arrays.toString(armor) + ", offhand: " + offhand + "}";
    }

}
