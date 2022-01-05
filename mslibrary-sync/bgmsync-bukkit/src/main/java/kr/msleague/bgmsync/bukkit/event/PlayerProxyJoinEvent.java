package kr.msleague.bgmsync.bukkit.event;

import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;

@Getter
public class PlayerProxyJoinEvent extends Event {
    private static final HandlerList handlerList = new HandlerList();
    private UUID uuid;
    private String userName;

    public PlayerProxyJoinEvent(UUID uuid, String userName) {
        this.uuid = uuid;
        this.userName = userName;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}
