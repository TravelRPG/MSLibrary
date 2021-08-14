package kr.msleague.msgui.api.events

import kr.msleague.msgui.api.interfaces.MSGui
import kr.msleague.msgui.api.interfaces.MSGuiViewer
import org.bukkit.event.Cancellable

class MSGuiCloseEvent(
    viewer: MSGuiViewer,
    gui: MSGui,
): MSGuiEvent(viewer, gui), Cancellable {

    private var cancelled: Boolean = false
    override fun isCancelled(): Boolean = cancelled
    override fun setCancelled(cancel: Boolean) { cancelled = cancel }

}