package kr.msleague.msgui.api.interfaces

import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

interface MSGuiButton {

    val itemStack: ItemStack
    val displayName: String
    val lore: List<String>
    val slot: Int
    val gui: MSGui
    val action: MSGuiButton

    fun action(viewer: MSGuiViewer) { action.action(viewer) }
    fun action(player: Player) { action.action(player) }

}