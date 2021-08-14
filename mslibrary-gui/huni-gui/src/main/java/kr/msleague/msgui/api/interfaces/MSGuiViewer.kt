package kr.msleague.msgui.api.interfaces

import org.bukkit.entity.Player

interface MSGuiViewer {

    val player: Player
    val openTime: Long
    val isClosed: Boolean
    fun sendMessage(message: String)
    fun closeInventory()

}