package me.downn_falls.deathInventory.database.mysql;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import me.downn_falls.deathInventory.DeathInventory;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseManager {

    private HikariDataSource dataSource;
    private final String HOST = DeathInventory.getInstance().getConfig().getString("mysql.host");
    private final String USER = DeathInventory.getInstance().getConfig().getString("mysql.user");
    private final String PASSWORD = DeathInventory.getInstance().getConfig().getString("mysql.password");
    private final String PORT = DeathInventory.getInstance().getConfig().getString("mysql.port");
    private final String DATABASE = DeathInventory.getInstance().getConfig().getString("mysql.database");

    public void init() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://"+HOST+":"+PORT+"/"+DATABASE);
        config.setUsername(USER);
        config.setPassword(PASSWORD);
        config.setMaximumPoolSize(10); // Adjust based on your server load
        config.setIdleTimeout(60000); // Close idle connections after 60 seconds
        config.setMaxLifetime(1800000); // Reconnect every 30 minutes to avoid stale connections
        config.setLeakDetectionThreshold(5000); // Debugging: Detect leaked connections
        dataSource = new HikariDataSource(config);
    }

    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void close() {
        if (dataSource != null) {
            dataSource.close();
        }
    }
}
