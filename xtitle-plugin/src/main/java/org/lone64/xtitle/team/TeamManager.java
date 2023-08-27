package org.lone64.xtitle.team;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.lone64.xtitle.XTitle;
import org.lone64.xtitle.api.cross.prefix.IPrefix;
import org.lone64.xtitle.api.cross.team.ITeam;
import org.lone64.xtitle.util.color.ColorUtil;

import java.util.UUID;

public class TeamManager implements ITeam {

    private final Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();

    @Override
    public ITeam enable() {
        for (IPrefix prefix : XTitle.getTitleManager().getPrefixList()) {
            if (this.scoreboard.getTeam(prefix.getName()) == null) {
                Team team = this.scoreboard.registerNewTeam(prefix.getName());
                team.setDisplayName(prefix.getDisplayName());
                team.setPrefix(ColorUtil.fetchColor(prefix.getPrefix()));
                team.setSuffix(ColorUtil.fetchColor(prefix.getSuffix()));

                for (UUID uuid : XTitle.getPlayerManager().getPrefixPlayers(prefix.getName())) {
                    team.addPlayer(Bukkit.getOfflinePlayer(uuid));
                }
            }
        }

        return this;
    }

    @Override
    public void disable() {
        for (Team team : this.scoreboard.getTeams()) {
            team.unregister();
        }
    }

    @Override
    public boolean enable(String name) {
        if (fetchTeam(name) != null) return false;
        IPrefix prefix = XTitle.getTitleManager().fetchPrefix(name);
        Team team = this.scoreboard.registerNewTeam(prefix.getName());
        team.setDisplayName(prefix.getDisplayName());
        team.setPrefix(ColorUtil.fetchColor(prefix.getPrefix()));
        team.setSuffix(ColorUtil.fetchColor(prefix.getSuffix()));
        return true;
    }

    @Override
    public boolean disable(String name) {
        if (fetchTeam(name) == null) return false;
        Team team = fetchTeam(name);
        team.unregister();
        return true;
    }

    @Override
    public boolean enable(String name, OfflinePlayer player) {
        Team team = fetchTeam(name);
        if (team == null) return false;
        team.addPlayer(player);
        return true;
    }

    @Override
    public boolean enable(OfflinePlayer player, String displayName) {
        Team team = fetchDisplayName(displayName);
        if (team == null) return false;
        team.addPlayer(player);
        return true;
    }

    @Override
    public boolean disable(OfflinePlayer player) {
        Team team = this.scoreboard.getPlayerTeam(player);
        if (team == null) return false;
        team.removePlayer(player);
        return true;
    }

    @Override
    public boolean isTeamPlayer(OfflinePlayer player) {
        return this.scoreboard.getPlayerTeam(player) != null;
    }

    @Override
    public boolean setDisplayName(String name, String displayName) {
        if (fetchTeam(name) == null) return false;
        Team team = fetchTeam(name);
        team.setDisplayName(displayName);
        return true;
    }

    @Override
    public boolean setPrefix(String name, String prefix) {
        if (fetchTeam(name) == null) return false;
        Team team = fetchTeam(name);
        team.setPrefix(ColorUtil.fetchColor(prefix));
        return true;
    }

    @Override
    public boolean setSuffix(String name, String suffix) {
        if (fetchTeam(name) == null) return false;
        Team team = fetchTeam(name);
        team.setSuffix(ColorUtil.fetchColor(suffix));
        return true;
    }

    @Override
    public Team fetchTeam(String name) {
        return this.scoreboard.getTeam(name);
    }

    @Override
    public Team fetchDisplayName(String displayName) {
        for (Team team : this.scoreboard.getTeams()) {
            if (team.getDisplayName().equals(displayName)) {
                return team;
            }
        }
        return null;
    }

}
