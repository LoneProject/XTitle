package org.lone64.xtitle.api.cross.team;

import org.bukkit.OfflinePlayer;
import org.bukkit.scoreboard.Team;

public interface ITeam {

    ITeam enable();

    void disable();

    boolean enable(String name);

    boolean disable(String name);

    boolean enable(String name, OfflinePlayer player);

    boolean enable(OfflinePlayer player, String displayName);

    boolean disable(OfflinePlayer player);

    boolean isTeamPlayer(OfflinePlayer player);

    boolean setDisplayName(String name, String displayName);

    boolean setPrefix(String name, String prefix);

    boolean setSuffix(String name, String suffix);

    Team fetchTeam(String name);

    Team fetchDisplayName(String displayName);

}
