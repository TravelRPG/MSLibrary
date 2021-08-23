package kr.msleague.msgui.api.event

import kr.msleague.msgui.gui.MSGui
import org.bukkit.entity.Player
import org.bukkit.event.Event

abstract class MSGuiEvent(
    val player: Player,
    val gui: MSGui<*>
) : Event()