package kr.msleague.msgui.gui.api.event;

import kr.msleague.msgui.gui.MSGui;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

@Getter
@Setter
public class MSGuiCloseEvent extends MSGuiEvent {
    private static HandlerList handlerList = new HandlerList();
    private long time;
    public MSGuiCloseEvent(Player player, MSGui<?> gui, long time) {
        super(player, gui);
        this.time = time;
    }

    public static HandlerList getHandlerList(){
        return handlerList;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

}
