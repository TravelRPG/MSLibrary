package kr.msleague.msgui.gui

import kr.msleague.msgui.extensions.getNBTTagCompound
import kr.msleague.msgui.gui.button.MSGuiButton
import kr.msleague.msgui.gui.button.MSGuiButtonData
import kr.msleague.msgui.plugin
import kr.msleague.msgui.pluginManager
import kr.msleague.msgui.server
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryDragEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import java.lang.IllegalArgumentException
import java.util.*

abstract class MSGui(
    private val who: Player,
    val size: Int = 0,
    val title: String? = null
) {

    var cancelGUI: Boolean = false

    constructor(who: Player, size: Int, title: String? = null, cancel: Boolean): this(who, size, title) { this.cancelGUI = cancel }

    companion object {
        fun registerEvent(gui: MSGui) {
            pluginManager.registerEvents(object: Listener {
                @EventHandler fun onClick(e: InventoryClickEvent) { if(gui.viewerUniqueId == e.whoClicked.uniqueId) gui.onClick(e) }
                @EventHandler fun onDrag(e: InventoryDragEvent) { if(gui.viewerUniqueId == e.whoClicked.uniqueId) gui.onDrag(e) }
                @EventHandler fun onClose(e: InventoryCloseEvent) {
                    if(gui.viewerUniqueId == e.player.uniqueId) {
                        e.handlers.unregister(this)
                        gui.onClose(e)
                    }
                }
            }, plugin)
        }
    }
    val inventory: Inventory by lazy { if(title!=null) server.createInventory(null, size) else server.createInventory(null, size, title) }
    private lateinit var viewerUniqueId: UUID
    val player: Player? get() = server.getPlayer(viewerUniqueId)

    init { initializer() }
    private fun initializer() {
        if(size / 9 != 0 || size in 0..54) throw IllegalArgumentException("inventory invalid size error : $size")
        else {
            viewerUniqueId = who.uniqueId
            init()
            registerEvent(this)
            who.openInventory(inventory)
        }
    }

    abstract fun init()

    open fun onClick(e: InventoryClickEvent) {
        if(cancelGUI) e.isCancelled = true
        e.currentItem.guiButtonData?.apply {
            if(isCancelled) e.isCancelled = true
            action?.action()
        }
    }
    open fun onClose(e: InventoryCloseEvent) {}
    open fun onDrag(e: InventoryDragEvent) { if(cancelGUI) e.isCancelled = true }

    private val ItemStack?.guiButtonData: MSGuiButtonData? get() = if(this == null || type == Material.AIR) null else getNBTTagCompound(MSGuiButtonData::class.java)

}