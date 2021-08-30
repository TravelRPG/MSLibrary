package kr.msleague.bgmsync.bukkit.event;

import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;

@Getter
public class PlayerProxyJoinEvent extends Event {
    private static final HandlerList handlerList = new HandlerList();
    private UUID uuid;

    public PlayerProxyJoinEvent(UUID uuid) {
        this.uuid = uuid;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}
