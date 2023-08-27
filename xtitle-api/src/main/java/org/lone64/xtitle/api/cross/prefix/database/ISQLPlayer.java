package org.lone64.xtitle.api.cross.prefix.database;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public interface ISQLPlayer {

    ISQLPlayer init();

    void addPlayer(UUID uuid, String name);

    void deletePlayer(OfflinePlayer player);

    boolean hasPlayer(OfflinePlayer player, String name);

    List<String> getPrefixList(Player player);

    List<UUID> getPrefixList(String name);

}
