package kr.msleague.msgui.api.event

import kr.msleague.msgui.gui.MSGui
import org.bukkit.entity.Player

class MSGuiOpenedEvent(
    player: Player,
    gui: MSGui<*>,
    val time: Long
) : MSGuiEvent(player, gui)