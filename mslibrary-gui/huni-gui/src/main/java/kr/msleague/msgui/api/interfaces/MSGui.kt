package kr.msleague.msgui.api.interfaces

import org.bukkit.inventory.Inventory

interface MSGui {

    val viewer: MSGuiViewer
    val inventory: Inventory
    val buttons: List<MSGuiButton>
    val hasPage: Boolean
    val page: Int

    fun openNextPage(): Boolean = openPage(page + 1)
    fun openPrevPage(): Boolean = openPage(page - 1)
    fun openPage(page: Int): Boolean
    fun update()
    fun updateAsync()

}