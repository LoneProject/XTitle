package org.lone64.xtitle.prefix.database;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.lone64.xtitle.api.cross.prefix.database.ISQLPlayer;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SQLPlayer implements ISQLPlayer {

    private String url;
    private Connection connection;

    public SQLPlayer(JavaPlugin plugin) {
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
    public ISQLPlayer init() {
        String sql = "CREATE TABLE IF NOT EXISTS playerHaves (id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(12), uuid VARCHAR(36));";
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
    public void addPlayer(UUID uuid, String name) {
        String sql = "INSERT INTO playerHaves (name, uuid) VALUES(?, ?);";
        try {
            checkConnection();
            try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
                statement.setString(1, name);
                statement.setString(2, uuid.toString());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deletePlayer(OfflinePlayer player) {
        String sql = "DELETE FROM playerHaves WHERE uuid = ?;";
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
    public boolean hasPlayer(OfflinePlayer player, String name) {
        String sql = "SELECT * FROM playerHaves WHERE uuid = ?;";
        try {
            checkConnection();
            try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
                statement.setString(1, player.getUniqueId().toString());
                try (ResultSet result = statement.executeQuery()) {
                    if (result.getString("name") != null && result.getString("name").equals(name)) {
                        return result.next();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<String> getPrefixList(Player player) {
        List<String> stringList = new ArrayList<>();
        String sql = "SELECT * FROM playerHaves";
        try {
            checkConnection();
            try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
                try (ResultSet result = statement.executeQuery()) {
                    while (result.next()) {
                        if (result.getString("uuid").equals(player.getUniqueId().toString())) {
                            stringList.add(result.getString("name"));
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return stringList;
        }
        return stringList;
    }

    @Override
    public List<UUID> getPrefixList(String name) {
        List<UUID> uuidList = new ArrayList<>();
        String sql = "SELECT * FROM playerHaves";
        try {
            checkConnection();
            try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
                try (ResultSet result = statement.executeQuery()) {
                    while (result.next()) {
                        if (result.getString("name").equals(name)) {
                            uuidList.add(UUID.fromString(result.getString("uuid")));
                        }
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
