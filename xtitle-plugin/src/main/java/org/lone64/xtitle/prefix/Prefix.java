package org.lone64.xtitle.prefix;

import org.lone64.xtitle.api.cross.prefix.IPrefix;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Prefix implements IPrefix {

    private String name, displayName;
    private String prefix, suffix;
    private List<UUID> playerList;

    public Prefix(String name, String displayName, String prefix, String suffix) {
        this.name = name;
        this.displayName = displayName;
        this.prefix = prefix;
        this.suffix = suffix;
        this.playerList = new ArrayList<>();
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public String getPrefix() {
        return prefix;
    }

    @Override
    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    @Override
    public String getSuffix() {
        return suffix;
    }

    @Override
    public void setPlayers(List<UUID> playerList) {
        this.playerList = playerList;
    }

    @Override
    public void addPlayer(UUID uuid) {
        this.playerList.add(uuid);
    }

    @Override
    public void deletePlayer(UUID uuid) {
        this.playerList.remove(uuid);
    }

    @Override
    public List<UUID> getPlayerList() {
        return playerList;
    }

}
