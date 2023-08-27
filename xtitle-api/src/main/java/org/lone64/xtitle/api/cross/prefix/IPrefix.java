package org.lone64.xtitle.api.cross.prefix;

import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public interface IPrefix {

    void setName(String name);

    String getName();

    void setDisplayName(String displayName);

    String getDisplayName();

    void setPrefix(String prefix);

    String getPrefix();

    void setSuffix(String suffix);

    String getSuffix();

    void setPlayers(List<UUID> playerList);

    void addPlayer(UUID uuid);

    void deletePlayer(UUID uuid);

    List<UUID> getPlayerList();

}
