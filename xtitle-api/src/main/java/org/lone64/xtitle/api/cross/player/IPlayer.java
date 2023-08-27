package org.lone64.xtitle.api.cross.player;

import org.bukkit.OfflinePlayer;

import java.util.UUID;

public interface IPlayer {

    UUID getUUID();

    OfflinePlayer getPlayer();

    String getName();

}
