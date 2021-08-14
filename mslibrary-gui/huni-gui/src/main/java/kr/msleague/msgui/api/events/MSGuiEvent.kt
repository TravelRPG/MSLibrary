package kr.msleague.msgui.api.events

import kr.msleague.msgui.api.interfaces.MSGui
import kr.msleague.msgui.api.interfaces.MSGuiViewer
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

abstract class MSGuiEvent(
    val viewer: MSGuiViewer,
    val gui: MSGui
): Event() {

    override fun getHandlers(): HandlerList = HANDLER_LIST

    companion object {
        var HANDLER_LIST = HandlerList()
        @JvmStatic fun getHandlerList(): HandlerList = HANDLER_LIST
    }

}