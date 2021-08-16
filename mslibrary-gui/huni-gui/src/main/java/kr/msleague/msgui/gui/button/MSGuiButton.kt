package kr.msleague.msgui.gui.button

import kr.msleague.msgui.gui.MSGui
import kr.msleague.msgui.plugin
import kr.msleague.msgui.server
import org.bukkit.inventory.ItemStack

class MSGuiButton(
    val type: MSGuiButtonType,
    private val makeFunc: () -> ItemStack,
    private val action: MSGuiButtonAction?,
    private val cancellable: Boolean,
) {

    fun setSlot(gui: MSGui<*>, vararg slots: Int) {
        server.scheduler.runTaskAsynchronously(plugin) {
            if (slots.isNotEmpty()) {
                val itemStack = makeFunc()
                slots.forEach {
                    if (gui.size <= it) return@forEach
                    if (action != null) gui.addButtonAction(it, action)
                    gui.inventory.setItem(it, itemStack.clone())
                }
            }
        }
    }

}