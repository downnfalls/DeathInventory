package me.downn_falls.deathInventory;

import me.downn_falls.deathInventory.commands.DeathInventoryCommand;
import me.downn_falls.deathInventory.database.PersistentDataInterface;
import me.downn_falls.deathInventory.database.mysql.MySQLDatabase;
import me.downn_falls.deathInventory.cache.LocalDataManager;
import me.downn_falls.deathInventory.cache.TemporaryDataInterface;
import me.downn_falls.deathInventory.listener.PlayerEvent;
import me.downn_falls.guiapi.GuiListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class DeathInventory extends JavaPlugin {

    private static DeathInventory instance;
    private static TemporaryDataInterface temporaryDataInterface;
    private static PersistentDataInterface persistentDataInterface;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        getConfig().options().copyDefaults();
        saveDefaultConfig();

        temporaryDataInterface = new LocalDataManager();
        persistentDataInterface = new MySQLDatabase();

        getServer().getPluginManager().registerEvents(new GuiListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerEvent(), this);
        getCommand("deathinventory").setExecutor(new DeathInventoryCommand());

        for (Player player : Bukkit.getOnlinePlayers()) {
            temporaryDataInterface.loadData(player);
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        for (Player player : Bukkit.getOnlinePlayers()) {
            temporaryDataInterface.saveData(player);
        }

        persistentDataInterface.disconnect();
    }

    public static DeathInventory getInstance() { return instance; }
    public static TemporaryDataInterface getTemporaryDataInterface() { return temporaryDataInterface; }
    public static PersistentDataInterface getPersistentDataInterface() { return persistentDataInterface; }

}
