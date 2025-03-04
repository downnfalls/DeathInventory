package me.downn_falls.deathInventory.database.mysql;

import me.downn_falls.deathInventory.manager.DeathData;
import me.downn_falls.deathInventory.DeathInventory;
import me.downn_falls.deathInventory.database.PersistentDataInterface;
import me.downn_falls.deathInventory.manager.PlayerData;
import me.downn_falls.deathInventory.utils.PushList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Consumer;

public class MySQLDatabase implements PersistentDataInterface {

    private final DatabaseManager databaseManager;

    public MySQLDatabase() {
        databaseManager = new DatabaseManager();
        databaseManager.init();
    }

    @Override
    public void loadDeathInventories(Player player, Consumer<PlayerData> consumer) {

        Bukkit.getScheduler().runTaskAsynchronously(DeathInventory.getInstance(), () -> {

            try (Connection conn = databaseManager.getConnection();
                 PreparedStatement stmt = conn.prepareStatement("SELECT * FROM death_inventory WHERE player_uuid = ?")) {
                stmt.setString(1, player.getUniqueId().toString());
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {

                    int deathCount = rs.getInt("death_count");
                    String json = rs.getString("inventory");
                    PushList<DeathData> deathInventories = DeathInventory.getGson().fromJson(json, PushList.class);

                    PlayerData playerData = new PlayerData(player, deathCount, deathInventories);

                    consumer.accept(playerData);
                } else {
                    PlayerData playerData = new PlayerData(player, 0, new PushList<>(3));
                    consumer.accept(playerData);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        });
    }

    @Override
    public void saveDeathInventory(Player player, int deathCount, PushList<DeathData> inventory) {

        System.out.println(inventory.getList().toString());
        String json = DeathInventory.getGson().toJson(inventory);

        try {
            Connection conn = databaseManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement("insert into death_inventory (player_uuid, death_count, inventory) values (?, ?, ?) on duplicate key update death_count = values(death_count), inventory = values(inventory)");
            stmt.setString(1, player.getUniqueId().toString());
            stmt.setInt(2, deathCount);
            stmt.setString(3, json);

            stmt.executeUpdate();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void disconnect() {
        databaseManager.close();
    }
}
