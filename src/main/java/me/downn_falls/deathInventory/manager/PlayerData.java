package me.downn_falls.deathInventory.manager;

import me.downn_falls.deathInventory.DeathInventory;
import me.downn_falls.deathInventory.utils.PushList;
import org.bukkit.entity.Player;

public class PlayerData {

    private final Player player;
    private int deaths;
    private final PushList<DeathData> deathData;

    public PlayerData(Player player, int deaths, PushList<DeathData> deathData) {
        this.player = player;
        this.deaths = deaths;
        this.deathData = deathData;
    }

    public Player getPlayer() {
        return player;
    }

    public int getDeathCount() {
        return deaths;
    }

    public PushList<DeathData> getDeathData() {
        return deathData;
    }

    public void triggerDeath(DeathData deathData) {
        deaths = deathData.getDeathCount();
        this.deathData.add(deathData);
    }

    public void claim(int index) {
        deathData.remove(index);
    }

    public static PlayerData get(Player player) {
        return DeathInventory.getTemporaryDataInterface().getData(player);
    }
}
