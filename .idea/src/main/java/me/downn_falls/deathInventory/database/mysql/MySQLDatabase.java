package me.downn_falls.deathInventory.database.mysql;

import me.downn_falls.deathInventory.DeathInventory;
import me.downn_falls.deathInventory.database.PersistentDataInterface;
import me.downn_falls.deathInventory.manager.ItemData;
import me.downn_falls.deathInventory.manager.PlayerData;
import me.downn_falls.deathInventory.utils.ItemStackSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.sql.*;
import java.util.*;
import java.util.function.Consumer;

public class MySQLDatabase implements PersistentDataInterface {

    private final DatabaseManager databaseManager;

    public MySQLDatabase() {
        databaseManager = new DatabaseManager();
        databaseManager.init();

        init();
    }

    private void init() {
        try (Connection conn = databaseManager.getConnection();
             Statement stmt = conn.createStatement()) {

            // Create inventory table
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS `items` (" +
                    "item_uuid VARCHAR(50) NOT NULL PRIMARY KEY, " +
                    "player_uuid VARCHAR(50) NOT NULL, " +
                    "`time` TIMESTAMP NOT NULL, " +
                    "item TEXT NOT NULL " +
                    ");");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadDeathInventories(Player player, Consumer<PlayerData> consumer) {

        Bukkit.getScheduler().runTaskAsynchronously(DeathInventory.getInstance(), () -> {

            List<ItemData> items = new ArrayList<>();

            try {
                Connection conn = databaseManager.getConnection();

                PreparedStatement queryInventories = conn.prepareStatement("select * from items where player_uuid = ?");
                queryInventories.setString(1, player.getUniqueId().toString());

                ResultSet rs = queryInventories.executeQuery();

                while (rs.next()) {

                    UUID itemUUID = UUID.fromString(rs.getString("item_uuid"));
                    long time = rs.getTimestamp("time").getTime();
                    ItemStack item = ItemStackSerializer.deserialize(rs.getString("item"));

                    items.add(new ItemData(itemUUID, time, item));
                }

                queryInventories.close();
                conn.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }

            consumer.accept(new PlayerData(player, items));

        });
    }

    @Override
    public void addItem(Player player, ItemData item) {
        try {

            Connection conn = databaseManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement("insert into items (item_uuid, player_uuid, `time`, item) values (?, ?, ?, ?)");
            stmt.setString(1, item.getItemUuid().toString());
            stmt.setString(2, player.getUniqueId().toString());
            stmt.setTimestamp(3, new Timestamp(item.getTimestamp()));
            stmt.setString(4, ItemStackSerializer.serialize(item.getItem()));

            stmt.executeUpdate();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeItem(Player player, ItemData item) {
        try {

            Connection conn = databaseManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement("delete from items WHERE player_uuid = ? AND item_uuid = ?");
            stmt.setString(1, player.getUniqueId().toString());
            stmt.setString(2, item.getItemUuid().toString());

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
