package kr.msleague.msgui.api.events

import kr.msleague.msgui.api.interfaces.MSGui
import kr.msleague.msgui.api.interfaces.MSGuiViewer
import org.bukkit.event.Cancellable

class MSGuiPageChangeEvent(
    viewer: MSGuiViewer,
    gui: MSGui,
    val prevPage: Int,
    val currentPage: Int
): MSGuiEvent(viewer, gui), Cancellable {

    private var cancelled: Boolean = false
    override fun isCancelled(): Boolean = cancelled
    override fun setCancelled(cancel: Boolean) { cancelled = cancel }

}