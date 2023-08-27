package org.lone64.xtitle.title.database;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.lone64.xtitle.XTitle;
import org.lone64.xtitle.api.cross.prefix.IPrefix;
import org.lone64.xtitle.api.cross.title.database.ISQLPrefix;
import org.lone64.xtitle.prefix.Prefix;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SQLPrefix implements ISQLPrefix {

    private String url;
    private Connection connection;

    public SQLPrefix(JavaPlugin plugin) {
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
    public ISQLPrefix init() {
        String sql = "CREATE TABLE IF NOT EXISTS prefixes (id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(12), displayName VARCHAR(12)," +
                "prefix VARCHAR(40), suffix VARCHAR(40));";
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
    public void savePrefix(IPrefix prefix) {
        String sql;
        try {
            checkConnection();
            if (isPrefix(prefix.getName())) {
                sql = "UPDATE prefixes SET name = ?, displayName = ?, prefix = ?, suffix = ? WHERE name = ?;";
                try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
                    statement.setString(1, prefix.getName());
                    statement.setString(2, prefix.getDisplayName());
                    statement.setString(3, prefix.getPrefix());
                    statement.setString(4, prefix.getSuffix());
                    statement.setString(5, prefix.getName());
                    statement.executeUpdate();
                }
            } else {
                sql = "INSERT INTO prefixes (name, displayName, prefix, suffix) VALUES(?, ?, ?, ?);";
                try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
                    statement.setString(1, prefix.getName());
                    statement.setString(2, prefix.getDisplayName());
                    statement.setString(3, prefix.getPrefix());
                    statement.setString(4, prefix.getSuffix());
                    statement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deletePrefix(String name) {
        for (UUID uuid : XTitle.getSQLPlayer().getPrefixList(name)) {
            XTitle.getSQLPlayer().deletePlayer(Bukkit.getOfflinePlayer(uuid));
        }
        String sql = "DELETE FROM prefixes WHERE name = ?;";
        try {
            checkConnection();
            try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
                statement.setString(1, name);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isPrefix(String name) {
        String sql = "SELECT name FROM prefixes WHERE name = ?;";
        try {
            checkConnection();
            try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
                statement.setString(1, name);
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
    public IPrefix fetchPrefix(String name) {
        String sql = "SELECT * FROM prefixes WHERE name = ?;";
        try {
            checkConnection();
            try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
                statement.setString(1, name);
                try (ResultSet result = statement.executeQuery()) {
                    if (result.next()) {
                        IPrefix prefix = new Prefix(result.getString("name"),
                                result.getString("displayName"), result.getString("prefix"),
                                result.getString("suffix"));
                        prefix.setPlayers(XTitle.getSQLPlayer().getPrefixList(prefix.getName()));
                        return prefix;
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
    public List<IPrefix> getPrefixList() {
        List<IPrefix> prefixList = new ArrayList<>();
        String sql = "SELECT * FROM prefixes";
        try {
            checkConnection();
            try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
                try (ResultSet result = statement.executeQuery()) {
                    while (result.next()) {
                        IPrefix prefix = new Prefix(result.getString("name"),
                                result.getString("displayName"), result.getString("prefix"),
                                result.getString("suffix"));
                        prefix.setPlayers(XTitle.getSQLPlayer().getPrefixList(prefix.getName()));
                        prefixList.add(prefix);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return prefixList;
        }
        return prefixList;
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
