package org.lone64.xtitle.api;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.lone64.xtitle.api.cross.prefix.IPrefix;

public class CreatedPrefixEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private final Player player;
    private final IPrefix prefix;

    public CreatedPrefixEvent(Player player, IPrefix prefix) {
        this.player = player;
        this.prefix = prefix;
    }

    public Player getPlayer() {
        return player;
    }

    public IPrefix getPrefix() {
        return prefix;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
