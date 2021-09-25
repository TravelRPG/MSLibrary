package kr.msleague.msgui.gui

import kr.msleague.msgui.api.annotations.MSGuiPage
import kr.msleague.msgui.api.event.*
import kr.msleague.msgui.gui.button.MSGuiButtonAction
import kr.msleague.msgui.gui.button.MSGuiButtonData
import kr.msleague.msgui.highVersion
import kr.msleague.msgui.plugin
import kr.msleague.msgui.pluginManager
import kr.msleague.msgui.server
import kr.msleague.util.extensions.getNBTTagCompound
import kr.msleague.util.extensions.getNBTTagCompound16
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.*
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import java.lang.reflect.Method
import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.HashSet

abstract class MSGui<V>(
    val who: Player,
    val size: Int = 0,
    val title: String? = null
) {

    private var currentPage: Int = 0
    var cancelGUI: Boolean = false
    private var objs: List<V>? = null
    private var pages: List<Method>? = null
    val page: Int get() = currentPage - 1
    val hasNextPage: Boolean get() = page in 0 until maxPage - 1
    val hasPrevPage: Boolean get() = page > 0
    val maxPage: Int get() = pages?.size ?: 0

    constructor(who: Player, size: Int, title: String? = null, cancel: Boolean) : this(who, size, title) {
        this.cancelGUI = cancel
    }

    constructor(who: Player, size: Int, title: String? = null, vararg args: V) : this(who, size, title) {
        objs = args.toList()
    }

    constructor(who: Player, size: Int, title: String? = null, inventory: Inventory) : this(who, size, title) {
        baseInventory = inventory
    }

    constructor(who: Player, cancel: Boolean, inventory: Inventory) : this(who, inventory.size, inventory.title) {
        this.cancelGUI = cancel
        baseInventory = inventory
    }

    constructor(who: Player, size: Int, title: String? = null, cancel: Boolean, vararg args: V) : this(
        who,
        size,
        title
    ) {
        this.cancelGUI = cancel
        objs = args.toList()
    }

    constructor(who: Player, cancel: Boolean, inventory: Inventory, vararg args: V) : this(
        who,
        inventory.size,
        inventory.title
    ) {
        this.cancelGUI = cancel
        baseInventory = inventory
        objs = args.toList()
    }

    fun getObject(index: Int): V? = try {
        objs?.get(index)
    } catch (e: IndexOutOfBoundsException) {
        null
    }

    private var baseInventory: Inventory? = null
    val inventory: Inventory by lazy {
        if (baseInventory != null) baseInventory!! else if (title != null) server.createInventory(
            null,
            size,
            title
        ) else server.createInventory(null, size)
    }
    private val viewers = HashSet<UUID>()
    fun addViewers(vararg viewer: Player) {
        viewer.forEach {
            if (viewers.add(it.uniqueId)) {
                it.closeInventory()
                it.openInventory(inventory)
            }
        }
    }

    private val buttonMap: MutableMap<Int, MSGuiButtonAction> = HashMap()
    internal fun addButtonAction(id: Int, action: MSGuiButtonAction) {
        buttonMap[id] = action
    }

    init {
        initializer()
    }

    private fun initializer() {
        who.closeInventory()
        if (size % 9 != 0 || size !in 0..54) throw IllegalArgumentException("inventory invalid size error : $size")
        else {
            val preOpenEvent = MSGuiOpenEvent(who, this, System.currentTimeMillis())
            pluginManager.callEvent(preOpenEvent)
            if (preOpenEvent.isCancelled) return
            viewers.add(who.uniqueId)
            val delay = if (who.openInventory.topInventory.type != InventoryType.CRAFTING) 1L else 0
            server.scheduler.runTaskLater(plugin, {
                prevInit()
                init()
                pages = javaClass.declaredMethods.filter { it.getAnnotation(MSGuiPage::class.java) != null }.toList()
                    .sortedBy { it.getAnnotation(MSGuiPage::class.java).pagePriority }
                if (maxPage > 0) {
                    currentPage = 1
                    pages?.get(page)?.invoke(this)
                }
                pluginManager.registerEvents(object : Listener {
                    @EventHandler
                    fun onClick(e: InventoryClickEvent) {
                        if (!viewers.contains(e.whoClicked.uniqueId)) return
                        if (cancelGUI) e.isCancelled = true
                        guiClick(e)
                    }

                    @EventHandler
                    fun onDrag(e: InventoryDragEvent) {
                        if (!viewers.contains(e.whoClicked.uniqueId)) return
                        guiDrag(e)
                    }

                    @EventHandler
                    fun onClose(e: InventoryCloseEvent) {
                        if (!viewers.contains(e.player.uniqueId)) return
                        viewers.remove(e.player.uniqueId)
                        if (viewers.size == 0) {
                            InventoryClickEvent.getHandlerList().unregister(this)
                            InventoryCloseEvent.getHandlerList().unregister(this)
                            InventoryOpenEvent.getHandlerList().unregister(this)
                        }
                        guiClose(e)
                    }
                }, plugin)
                who.openInventory(inventory)
                pluginManager.callEvent(MSGuiOpenedEvent(who, this, System.currentTimeMillis()))
            }, delay)
        }
    }

    open fun prevInit() {}
    abstract fun init()

    fun refresh() {
        if (page in 0 until maxPage) pages?.get(page)?.invoke(this) else init()
    }

    fun refresh(clear: Boolean) {
        if (clear) {
            for (index in 0 until size) {
                if (inventory.getItem(index).guiButtonData?.isCleanable == true) continue
                inventory.setItem(index, null)
                buttonMap.remove(index)
            }
            /*buttonMap.forEach {
                val index = it.key
                inventory.getItem(index).guiButtonData?.apply { if(!isCleanable) return@forEach }
                inventory.setItem(index, null)
                buttonMap.remove(index)
            }*/
        }
        refresh()
    }

    fun asyncRefresh() {
        server.scheduler.runTaskAsynchronously(plugin) { refresh() }
    }

    fun asyncRefresh(clear: Boolean) {
        server.scheduler.runTaskAsynchronously(plugin) { refresh(clear) }
    }

    fun openNextPage(clear: Boolean, async: Boolean): Boolean {
        if (!hasNextPage) return false
        currentPage++
        if (async) asyncRefresh(clear)
        else refresh(clear)
        return true
    }

    fun openPrevPage(clear: Boolean, async: Boolean): Boolean {
        if (!hasPrevPage) return false
        currentPage--
        if (async) asyncRefresh(clear)
        else refresh(clear)
        return true
    }

    open fun guiClick(e: InventoryClickEvent) {
        e.currentItem.guiButtonData?.apply {
            val event = MSGuiButtonClickEvent(
                e.whoClicked as Player,
                this@MSGui,
                e.click,
                e.action,
                e.slotType,
                e.slot,
                e.rawSlot,
                e.currentItem ?: ItemStack(Material.AIR),
                e.hotbarButton,
                e.cursor ?: ItemStack(Material.AIR)
            )
            pluginManager.callEvent(event)
            if (event.isCancelled) e.isCancelled = true
            else {
                if (isCancellable) e.isCancelled = true
                buttonMap[e.rawSlot]?.action(e)
            }
        }
    }

    open fun guiClose(e: InventoryCloseEvent) {
        pluginManager.callEvent(MSGuiCloseEvent(e.player as Player, this, System.currentTimeMillis()))
    }

    open fun guiDrag(e: InventoryDragEvent) {
        if (cancelGUI) e.isCancelled = true
    }

    fun closeGUI() {
        viewers.forEach { server.getPlayer(it)?.closeInventory() }
    }

    private val ItemStack?.guiButtonData: MSGuiButtonData?
        get() = if (this == null || type == Material.AIR) null else {
            if(highVersion) getNBTTagCompound16(MSGuiButtonData::class.java)
            else getNBTTagCompound(MSGuiButtonData::class.java)
        }

}