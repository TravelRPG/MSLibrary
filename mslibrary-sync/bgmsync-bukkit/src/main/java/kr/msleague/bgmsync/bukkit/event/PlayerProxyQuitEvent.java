package kr.msleague.bgmsync.bukkit.event;

import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;

@Getter
public class PlayerProxyQuitEvent extends Event {
    private static final HandlerList handlerList = new HandlerList();
    private UUID uuid;
    public PlayerProxyQuitEvent(UUID uuid){
        this.uuid = uuid;
    }
    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
    public static HandlerList getHandlerList() {
        return handlerList;
    }
}
