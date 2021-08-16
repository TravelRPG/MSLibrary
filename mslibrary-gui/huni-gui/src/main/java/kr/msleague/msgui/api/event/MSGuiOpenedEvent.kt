package kr.msleague.msgui.api.event

import kr.msleague.msgui.gui.MSGui
import org.bukkit.entity.Player
import org.bukkit.event.HandlerList

class MSGuiOpenedEvent(
    player: Player,
    gui: MSGui<*>,
    val time: Long
) : MSGuiEvent(player, gui) {
    override fun getHandlers(): HandlerList = HANDLER_LIST
    companion object {
        var HANDLER_LIST: HandlerList = HandlerList()
        @JvmStatic fun getHandlerList(): HandlerList = HANDLER_LIST
    }
}