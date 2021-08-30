package kr.msleague.msgui.api.event;

import kr.msleague.msgui.gui.MSGui;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

@Getter
@Setter
public class MSGuiOpenEvent extends MSGuiEvent implements Cancellable {
    private static HandlerList handlerList = new HandlerList();
    private boolean cancelled;
    private long time;

    public MSGuiOpenEvent(Player player, MSGui<?> gui, long time) {
        super(player, gui);
        this.time = time;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.cancelled = b;
    }
}
