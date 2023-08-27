package org.lone64.xtitle;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.lone64.xtitle.api.cross.player.IPlayerManager;
import org.lone64.xtitle.api.cross.player.database.ISQLPrefixPlayer;
import org.lone64.xtitle.api.cross.prefix.database.ISQLPlayer;
import org.lone64.xtitle.api.cross.team.ITeam;
import org.lone64.xtitle.api.cross.title.ITitleManager;
import org.lone64.xtitle.api.cross.title.database.ISQLPrefix;
import org.lone64.xtitle.command.MainCommand;
import org.lone64.xtitle.command.tab.MainTab;
import org.lone64.xtitle.listener.InventoryClickListener;
import org.lone64.xtitle.listener.PlayerListener;
import org.lone64.xtitle.player.PlayerManager;
import org.lone64.xtitle.player.database.SQLPrefixPlayer;
import org.lone64.xtitle.prefix.database.SQLPlayer;
import org.lone64.xtitle.team.TeamManager;
import org.lone64.xtitle.title.TitleManager;
import org.lone64.xtitle.title.database.SQLPrefix;

import java.util.HashMap;
import java.util.Map;

public final class XTitle extends JavaPlugin {

    private static XTitle instance;
    private static String prefix;
    private static ISQLPrefix SQLPrefix;
    private static ISQLPlayer SQLPlayer;
    private static ISQLPrefixPlayer SQLPrefixPlayer;
    private static ITitleManager titleManager;
    private static ITeam teamManager;
    private static IPlayerManager playerManager;
    private static final Map<Player, Integer> intMap = new HashMap<>();
    private static final Map<Player, String> strMap = new HashMap<>();


    @Override
    public void onLoad() {
        instance = this;
        prefix = "[XTitle]";
        SQLPrefix = new SQLPrefix(this).init();
        SQLPlayer = new SQLPlayer(this).init();
        SQLPrefixPlayer = new SQLPrefixPlayer(this).init();
    }

    @Override
    public void onEnable() {
        titleManager = new TitleManager().enable();
        playerManager = new PlayerManager().enable();
        teamManager = new TeamManager().enable();
        this.getCommand("칭호").setExecutor(new MainCommand());
        this.getCommand("칭호").setTabCompleter(new MainTab());
        this.getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        this.getServer().getPluginManager().registerEvents(new InventoryClickListener(), this);
    }

    @Override
    public void onDisable() {
        if (titleManager != null) titleManager.disable();
        if (playerManager != null) playerManager.disable();
        if (teamManager != null) teamManager.disable();
    }

    public static XTitle getInstance() {
        return instance;
    }

    public static String getPrefix() {
        return prefix;
    }

    public static ISQLPrefix getSQLPrefix() {
        return SQLPrefix;
    }

    public static ISQLPlayer getSQLPlayer() {
        return SQLPlayer;
    }

    public static ISQLPrefixPlayer getSQLPrefixPlayer() {
        return SQLPrefixPlayer;
    }

    public static ITitleManager getTitleManager() {
        return titleManager;
    }

    public static ITeam getTeamManager() {
        return teamManager;
    }

    public static IPlayerManager getPlayerManager() {
        return playerManager;
    }

    public static Map<Player, Integer> getIntMap() {
        return intMap;
    }

    public static Map<Player, String> getStrMap() {
        return strMap;
    }

}
