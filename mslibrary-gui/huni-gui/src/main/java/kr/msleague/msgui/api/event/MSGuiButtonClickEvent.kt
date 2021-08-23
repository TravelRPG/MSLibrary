package kr.msleague.msgui.api.event

import kr.msleague.msgui.gui.MSGui
import org.bukkit.entity.Player
import org.bukkit.event.Cancellable
import org.bukkit.event.HandlerList
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryAction
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.ItemStack

class MSGuiButtonClickEvent(
    player: Player,
    gui: MSGui<*>,
    val clickType: ClickType,
    val action: InventoryAction,
    val slotType: InventoryType.SlotType,
    val slot: Int,
    val rawSlot: Int,
    val currentItem: ItemStack,
    val hotBarKey: Int,
    val cursor: ItemStack
) : MSGuiEvent(player, gui), Cancellable {

    private var isCancelled: Boolean = false

    override fun isCancelled(): Boolean = isCancelled
    override fun setCancelled(cancel: Boolean) {
        isCancelled = cancel
    }

    override fun getHandlers(): HandlerList = HANDLER_LIST

    companion object {
        var HANDLER_LIST: HandlerList = HandlerList()
        @JvmStatic
        fun getHandlerList(): HandlerList = HANDLER_LIST
    }

}