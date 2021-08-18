package kr.msleague.msgui.api.event;

import kr.msleague.msgui.gui.MSGui;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

@AllArgsConstructor
@Getter
public abstract class MSGuiEvent extends Event {
    protected Player player;
    protected MSGui<?> gui;
}
