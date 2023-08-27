package org.lone64.xtitle.api.cross.player;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public interface IPlayerManager {

    IPlayerManager enable();

    void disable();

    boolean addPrefixPlayer(OfflinePlayer player, String name);

    boolean delPrefixPlayer(OfflinePlayer player);

    IPlayer getPrefixPlayer(OfflinePlayer player);

    boolean isPrefixPlayer(OfflinePlayer player);

    List<UUID> getPrefixPlayers(String name);

    List<UUID> getPrefixPlayers();

}
