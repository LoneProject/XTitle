package org.lone64.xtitle.api;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.lone64.xtitle.api.cross.prefix.IPrefix;

public class ClearedPrefixEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private final Player player;
    private final IPrefix oldPrefix;

    public ClearedPrefixEvent(Player player, IPrefix oldPrefix) {
        this.player = player;
        this.oldPrefix = oldPrefix;
    }

    public Player getPlayer() {
        return player;
    }

    public IPrefix getOldPrefix() {
        return oldPrefix;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
