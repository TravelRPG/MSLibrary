package kr.msleague.msgui.gui

import kr.msleague.msgui.extensions.getNBTTagCompound
import kr.msleague.msgui.gui.button.MSGuiButtonAction
import kr.msleague.msgui.gui.button.MSGuiButtonData
import kr.msleague.msgui.plugin
import kr.msleague.msgui.pluginManager
import kr.msleague.msgui.server
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.*
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import java.lang.IllegalArgumentException
import java.util.*
import kotlin.collections.HashMap

abstract class MSGui<T> (
    private val who: Player,
    val size: Int = 0,
    val title: String? = null
) {

    var cancelGUI: Boolean = false
    private var map: Map<String,T>? = null

    constructor(who: Player, size: Int, title: String? = null, cancel: Boolean): this(who, size, title) { this.cancelGUI = cancel }

    constructor(who: Player, size: Int, title: String? = null, cancel: Boolean, map: Map<String,T>): this(who, size, title) {
        this.cancelGUI = cancel
        this.map = map
    }

    fun getObject(key: String): T? = map?.get(key)

    companion object {
        fun registerEvent(gui: MSGui<*>) {
            pluginManager.registerEvents(object: Listener {
                @EventHandler fun onClick(e: InventoryClickEvent) { if(gui.viewerUniqueId == e.whoClicked.uniqueId) gui.onClick(e) }
                @EventHandler fun onDrag(e: InventoryDragEvent) { if(gui.viewerUniqueId == e.whoClicked.uniqueId) gui.onDrag(e) }
                @EventHandler fun onClose(e: InventoryCloseEvent) {
                    if(gui.viewerUniqueId == e.player.uniqueId) {
                        InventoryClickEvent.getHandlerList().unregister(this)
                        InventoryCloseEvent.getHandlerList().unregister(this)
                        InventoryOpenEvent.getHandlerList().unregister(this)
                        gui.onClose(e)
                    }
                }
            }, plugin)
        }
    }
    val inventory: Inventory by lazy { if(title!=null) server.createInventory(null, size,title) else server.createInventory(null, size) }
    private lateinit var viewerUniqueId: UUID
    val player: Player? get() = server.getPlayer(viewerUniqueId)
    private val buttonMap: MutableMap<Int, MSGuiButtonAction> = HashMap()
    fun addButtonAction(id: Int, action: MSGuiButtonAction) { buttonMap[id] = action }

    init { initializer() }
    private fun initializer() {
        if(size % 9 != 0 || size !in 0..54) throw IllegalArgumentException("inventory invalid size error : $size")
        else {
            val delay = if(who.openInventory.topInventory.type != InventoryType.CRAFTING) 1L else 0
            server.scheduler.runTaskLater(plugin , {
                viewerUniqueId = who.uniqueId
                init()
                registerEvent(this)
                who.openInventory(inventory)
            }, delay)
        }
    }

    abstract fun init()

    open fun onClick(e: InventoryClickEvent) {
        if(cancelGUI) e.isCancelled = true
        e.currentItem.guiButtonData?.apply {
            if(isCancelled) e.isCancelled = true
            buttonMap[e.rawSlot]?.action(e)
        }
    }
    open fun onClose(e: InventoryCloseEvent) {}
    open fun onDrag(e: InventoryDragEvent) { if(cancelGUI) e.isCancelled = true }

    private val ItemStack?.guiButtonData: MSGuiButtonData? get() = if(this == null || type == Material.AIR) null else getNBTTagCompound(MSGuiButtonData::class.java)

}