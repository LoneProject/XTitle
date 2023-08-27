package org.lone64.xtitle.player;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.lone64.xtitle.api.cross.player.IPlayer;

import java.util.UUID;

public class PrefixPlayer implements IPlayer {

    private final UUID uuid;
    private final String name;

    public PrefixPlayer(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }

    @Override
    public UUID getUUID() {
        return uuid;
    }

    @Override
    public OfflinePlayer getPlayer() {
        return Bukkit.getOfflinePlayer(this.uuid);
    }

    @Override
    public String getName() {
        return name;
    }

}
