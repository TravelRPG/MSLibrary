package kr.msleague.msgui.api.events

import kr.msleague.msgui.api.interfaces.MSGui
import kr.msleague.msgui.api.interfaces.MSGuiButton
import kr.msleague.msgui.api.interfaces.MSGuiViewer
import org.bukkit.event.Cancellable
import org.bukkit.event.inventory.ClickType

class MSGuiClickEvent(
    clicked: MSGuiViewer,
    gui: MSGui,
    val button: MSGuiButton,
    val clickType: ClickType
): MSGuiEvent(clicked, gui), Cancellable {

    private var cancelled: Boolean = false
    override fun isCancelled(): Boolean = cancelled
    override fun setCancelled(cancel: Boolean) { cancelled = cancel }

}