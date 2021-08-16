package kr.msleague.msgui.api.event

import kr.msleague.msgui.gui.MSGui
import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

abstract class MSGuiEvent(
    val player: Player,
    val gui: MSGui<*>
): Event() {

    override fun getHandlers(): HandlerList = HANDLER_LIST
    companion object {
        var HANDLER_LIST: HandlerList = HandlerList()
        @JvmStatic fun getHandlerList(): HandlerList = HANDLER_LIST
    }

}