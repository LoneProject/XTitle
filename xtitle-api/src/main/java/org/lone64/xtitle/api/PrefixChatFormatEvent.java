package org.lone64.xtitle.api;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.lone64.xtitle.api.cross.prefix.IPrefix;

public class PrefixChatFormatEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private final Player player;
    private final String prefix;
    private final String suffix;
    private String message;
    private String format;
    private boolean cancelled;

    public PrefixChatFormatEvent(Player player, String prefix, String suffix, String message, String format, boolean cancelled) {
        this.player = player;
        this.prefix = prefix;
        this.suffix = suffix;
        this.message = message;
        this.format = format;
        this.cancelled = cancelled;
    }

    public Player getPlayer() {
        return player;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getFormat() {
        return format;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
