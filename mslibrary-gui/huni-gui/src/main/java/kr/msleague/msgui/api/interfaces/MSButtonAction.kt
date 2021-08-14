package kr.msleague.msgui.api.interfaces

import org.bukkit.entity.Player

interface MSButtonAction {

    val isCanceled: Boolean
    fun setCanceled(cancel: Boolean)

    fun action(player: Player)
    fun action(viewer: MSGuiViewer) { action(viewer.player) }

}