package org.lone64.xtitle.player.database;

import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.java.JavaPlugin;
import org.lone64.xtitle.api.cross.player.database.ISQLPrefixPlayer;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SQLPrefixPlayer implements ISQLPrefixPlayer {

    private String url;
    private Connection connection;

    public SQLPrefixPlayer(JavaPlugin plugin) {
        File folder = plugin.getDataFolder();
        if (!folder.exists()) {
            if (!folder.mkdir()) {
                plugin.getLogger().severe("Could not create folder!");
            }
        }

        folder = new File(plugin.getDataFolder() + "/cache");
        if (!folder.exists()) {
            if (!folder.mkdir()) {
                plugin.getLogger().severe("Could not create folder!");
            }
        }
        File dataFolder = new File(folder.getPath() + "/cache.db");
        if (!dataFolder.exists()) {
            try {
                if (!dataFolder.createNewFile()) {
                    plugin.getLogger().severe("Could not create cache/cache.db file!");
                }
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
        this.url = "jdbc:sqlite:" + dataFolder;
        try {
            Class.forName("org.sqlite.JDBC");
            DriverManager.getConnection(this.url);
        } catch (SQLException | ClassNotFoundException e) {
            if (e instanceof ClassNotFoundException) {
                plugin.getLogger().severe("Could Not Found SQLite Driver on your system!");
            } else e.printStackTrace();
        }
    }

    @Override
    public ISQLPrefixPlayer init() {
        String sql = "CREATE TABLE IF NOT EXISTS playerPrefixes (id INTEGER PRIMARY KEY AUTOINCREMENT, uuid VARCHAR(36), name VARCHAR(12));";
        try {
            checkConnection();
            try (Statement statement = this.connection.createStatement()) {
                statement.executeUpdate(sql);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return this;
    }

    @Override
    public void setPrefixPlayer(OfflinePlayer player, String name) {
        String sql = "INSERT INTO playerPrefixes (uuid, name) VALUES(?, ?);";
        try {
            checkConnection();
            try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
                statement.setString(1, player.getUniqueId().toString());
                statement.setString(2, name);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delPrefixPlayer(OfflinePlayer player) {
        String sql = "DELETE FROM playerPrefixes WHERE uuid = ?;";
        try {
            checkConnection();
            try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
                statement.setString(1, player.getUniqueId().toString());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean hasPlayer(OfflinePlayer player) {
        String sql = "SELECT * FROM playerPrefixes WHERE uuid = ?;";
        try {
            checkConnection();
            try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
                statement.setString(1, player.getUniqueId().toString());
                try (ResultSet result = statement.executeQuery()) {
                    return result.next();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public String fetchPrefix(OfflinePlayer player) {
        String sql = "SELECT * FROM playerPrefixes WHERE uuid = ?;";
        try {
            checkConnection();
            try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
                statement.setString(1, player.getUniqueId().toString());
                try (ResultSet result = statement.executeQuery()) {
                    if (result.next()) {
                        return result.getString("name");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    @Override
    public List<UUID> getPlayerList() {
        List<UUID> uuidList = new ArrayList<>();
        String sql = "SELECT * FROM playerPrefixes";
        try {
            checkConnection();
            try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
                try (ResultSet result = statement.executeQuery()) {
                    while (result.next()) {
                        uuidList.add(UUID.fromString(result.getString("uuid")));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return uuidList;
        }
        return uuidList;
    }

    private void checkConnection() throws SQLException {
        boolean renew = false;

        if (this.connection == null)
            renew = true;
        else
        if (this.connection.isClosed())
            renew = true;

        if (renew)
            this.connection = DriverManager.getConnection(this.url);
    }

}
