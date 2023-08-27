package org.lone64.xtitle.api.cross.player.database;

import org.bukkit.OfflinePlayer;

import java.util.List;
import java.util.UUID;

public interface ISQLPrefixPlayer {

    ISQLPrefixPlayer init();

    void setPrefixPlayer(OfflinePlayer player, String name);

    void delPrefixPlayer(OfflinePlayer player);

    boolean hasPlayer(OfflinePlayer player);

    String fetchPrefix(OfflinePlayer player);

    List<UUID> getPlayerList();

}
