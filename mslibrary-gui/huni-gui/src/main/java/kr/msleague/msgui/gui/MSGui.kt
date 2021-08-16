package kr.msleague.msgui.gui

import kr.msleague.msgui.api.annotations.MSGuiPage
import kr.msleague.msgui.api.event.MSGuiButtonClickEvent
import kr.msleague.msgui.api.event.MSGuiCloseEvent
import kr.msleague.msgui.api.event.MSGuiOpenEvent
import kr.msleague.msgui.api.event.MSGuiOpenedEvent
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
import java.lang.reflect.Method
import java.util.*
import kotlin.collections.HashMap

abstract class MSGui<V> (
    private val who: Player,
    val size: Int = 0,
    val title: String? = null
) {

    private var currentPage: Int = 0
    var cancelGUI: Boolean = false
    private var objs: List<V>? = null
    private var pages: List<Method>? = null
    val page: Int get() = currentPage - 1
    val hasNextPage: Boolean get() = page in 0 until maxPage
    val hasPrevPage: Boolean get() = page > 0
    val maxPage: Int get() = pages?.size?: 0

    constructor(who: Player, size: Int, title: String? = null, cancel: Boolean): this(who, size, title) { this.cancelGUI = cancel }

    constructor(who: Player, size: Int, title: String? = null, cancel: Boolean, vararg args: V): this(who, size, title) {
        this.cancelGUI = cancel
        objs = args.toList()
    }

    fun getObject(index: Int): V? = try { objs?.get(index) } catch (e: IndexOutOfBoundsException) { null }

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
            val preOpenEvent = MSGuiOpenEvent(who, this, System.currentTimeMillis())
            pluginManager.callEvent(preOpenEvent)
            if(preOpenEvent.isCancelled) return
            val delay = if(who.openInventory.topInventory.type != InventoryType.CRAFTING) 1L else 0
            server.scheduler.runTaskLater(plugin , {
                pages = javaClass.declaredMethods.filter { it.getAnnotation(MSGuiPage::class.java) != null }.toList().sortedBy { it.getAnnotation(MSGuiPage::class.java).pagePriority }
                viewerUniqueId = who.uniqueId
                init()
                if(maxPage > 0) openNextPage(clear = false, async = false)
                registerEvent(this)
                who.openInventory(inventory)
                pluginManager.callEvent(MSGuiOpenedEvent(who, this, System.currentTimeMillis()))
            }, delay)
        }
    }

    abstract fun init()

    fun refresh() { if(page in 0 until maxPage) pages?.get(page)?.invoke(this) }
    fun refresh(clear: Boolean) {
        if(clear) {
            buttonMap.forEach {
                val index = it.key
                inventory.getItem(index).guiButtonData?.apply { if(!isCleanable) return@forEach }
                inventory.setItem(index, null)
                buttonMap.remove(index)
            }
        }
        refresh()
    }
    fun asyncRefresh() { server.scheduler.runTaskAsynchronously(plugin) { refresh() } }
    fun asyncRefresh(clear: Boolean) { server.scheduler.runTaskAsynchronously(plugin) { refresh(clear) } }

    fun openNextPage(clear: Boolean, async: Boolean): Boolean {
        if(!hasNextPage) return false
        currentPage++
        if(async) asyncRefresh(clear)
        else refresh(clear)
        return true
    }

    fun openPrevPage(clear: Boolean, async: Boolean): Boolean {
        if(!hasPrevPage) return false
        currentPage--
        if(async) asyncRefresh(clear)
        else refresh(clear)
        return true
    }

    private fun onClick(e: InventoryClickEvent) {
        if(cancelGUI) e.isCancelled = true
        e.currentItem.guiButtonData?.apply {
            val event = MSGuiButtonClickEvent(e.whoClicked as Player, this@MSGui, e.click, e.action, e.slotType, e.slot, e.rawSlot, e.currentItem?: ItemStack(Material.AIR), e.hotbarButton, e.cursor?: ItemStack(Material.AIR))
            pluginManager.callEvent(event)
            if(event.isCancelled) e.isCancelled = true
            else {
                if (isCancellable) e.isCancelled = true
                buttonMap[e.rawSlot]?.action(e)
            }
        }
    }
    open fun onClose(e: InventoryCloseEvent) { pluginManager.callEvent(MSGuiCloseEvent(e.player as Player, this, System.currentTimeMillis())) }
    open fun onDrag(e: InventoryDragEvent) { if(cancelGUI) e.isCancelled = true }

    private val ItemStack?.guiButtonData: MSGuiButtonData? get() = if(this == null || type == Material.AIR) null else getNBTTagCompound(MSGuiButtonData::class.java)

}