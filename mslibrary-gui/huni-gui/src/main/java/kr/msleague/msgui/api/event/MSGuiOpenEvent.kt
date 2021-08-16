package kr.msleague.msgui.api.event

import kr.msleague.msgui.gui.MSGui
import org.bukkit.entity.Player
import org.bukkit.event.Cancellable

class MSGuiOpenEvent(
    player: Player,
    gui: MSGui<*>,
    val time: Long
) : MSGuiEvent(player, gui), Cancellable {

    private var isCancelled: Boolean = false

    override fun isCancelled(): Boolean = isCancelled
    override fun setCancelled(cancel: Boolean) { isCancelled = cancel }

}