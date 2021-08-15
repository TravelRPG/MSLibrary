package kr.msleague.msgui.gui.button

import kr.msleague.msgui.gui.MSGui
import kr.msleague.msgui.plugin
import kr.msleague.msgui.server
import org.bukkit.inventory.ItemStack

class MSGuiButton(
    val type: MSGuiButtonType,
    private val makeFunc: ()->ItemStack,
    val cancellable: Boolean
) {

    fun setSlot(gui: MSGui, vararg slots: Int) {
        server.scheduler.runTaskAsynchronously(plugin) {
            val itemStack = makeFunc()
            slots.forEach {
                if(gui.size <= it) return@forEach
                gui.inventory.setItem(it, itemStack.clone())
            }
        }
    }

}