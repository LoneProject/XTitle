package org.lone64.xtitle.player;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.lone64.xtitle.XTitle;
import org.lone64.xtitle.api.cross.player.IPlayer;
import org.lone64.xtitle.api.cross.player.IPlayerManager;

import java.util.*;

public class PlayerManager implements IPlayerManager {

    private final Map<UUID, PrefixPlayer> playerMap = new HashMap<>();

    @Override
    public IPlayerManager enable() {
        for (UUID uuid : XTitle.getSQLPrefixPlayer().getPlayerList()) {
            String name = XTitle.getSQLPrefixPlayer().fetchPrefix(Bukkit.getOfflinePlayer(uuid));
            this.playerMap.put(uuid, new PrefixPlayer(uuid, name));
        }
        return this;
    }

    @Override
    public void disable() {
        for (Map.Entry<UUID, PrefixPlayer> entry : this.playerMap.entrySet()) {
            if (XTitle.getSQLPrefixPlayer().hasPlayer(Bukkit.getOfflinePlayer(entry.getKey()))) continue;
            XTitle.getSQLPrefixPlayer().setPrefixPlayer(Bukkit.getOfflinePlayer(entry.getKey()), entry.getValue().getName());
        }

        for (UUID uuid : XTitle.getSQLPrefixPlayer().getPlayerList()) {
            if (this.playerMap.get(uuid) == null) {
                XTitle.getSQLPrefixPlayer().delPrefixPlayer(Bukkit.getOfflinePlayer(uuid));
            }
        }
    }

    @Override
    public boolean addPrefixPlayer(OfflinePlayer player, String name) {
        if (this.playerMap.get(player.getUniqueId()) != null) return false;
        this.playerMap.put(player.getUniqueId(), new PrefixPlayer(player.getUniqueId(), name));
        return true;
    }

    @Override
    public boolean delPrefixPlayer(OfflinePlayer player) {
        if (this.playerMap.get(player.getUniqueId()) == null) return false;
        this.playerMap.remove(player.getUniqueId());
        return true;
    }

    @Override
    public IPlayer getPrefixPlayer(OfflinePlayer player) {
        return this.playerMap.get(player.getUniqueId());
    }

    @Override
    public boolean isPrefixPlayer(OfflinePlayer player) {
        return this.playerMap.get(player.getUniqueId()) != null;
    }

    @Override
    public List<UUID> getPrefixPlayers(String name) {
        List<UUID> uuidList = new ArrayList<>();
        for (UUID uuid : getPrefixPlayers()) {
            if (this.playerMap.get(uuid).getName().equals(name)) {
                uuidList.add(uuid);
            }
        }
        return uuidList;
    }

    @Override
    public List<UUID> getPrefixPlayers() {
        return new ArrayList<>(this.playerMap.keySet());
    }

}
