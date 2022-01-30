package kr.msleague.msgui.gui.button

import kr.msleague.msgui.*
import kr.msleague.msgui.gui.MSGui
import org.bukkit.inventory.ItemStack

class MSGuiButton internal constructor(
    val type: MSGuiButtonType,
    private val itemStack: ItemStack,
    private val lateFunc: ((ItemStack)->Unit)?,
    private val action: MSGuiButtonAction?,
    private val cancellable: Boolean
) {

    fun setSlot(gui: MSGui<*>, vararg slots: Int) {
        val itemStack = itemStack.clone()
        slots.forEach { a(itemStack, it, gui) }
    }

    fun setSlot(gui: MSGui<*>, vararg slots: IntProgression) {
        val itemStack = itemStack.clone()
        slots.forEach { slotsIn -> slotsIn.forEach { a(itemStack, it, gui) } }
    }

    private fun a(itemStack: ItemStack, it: Int, gui: MSGui<*>) {
        if (gui.size > it) {
            if (action != null) gui.addButtonAction(it, action)
            val current = gui.inventory.getItem(it)
            if (current != null && current.type == itemStack.type && (!highVersion16 && !highVersion17 && current.durability == itemStack.durability)) current.itemMeta = itemStack.itemMeta
            else gui.inventory.setItem(it, itemStack.clone())
            server.scheduler.runTaskAsynchronously(plugin){ lateFunc?.let { it1 -> it1(gui.inventory.getItem(it)) } }
        }
    }

}