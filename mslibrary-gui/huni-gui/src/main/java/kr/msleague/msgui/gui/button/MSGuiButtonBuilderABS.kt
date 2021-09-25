package kr.msleague.msgui.gui.button

import org.bukkit.Material
import org.bukkit.OfflinePlayer
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.inventory.meta.SkullMeta

abstract class MSGuiButtonBuilderABS {

    var type: MSGuiButtonType = MSGuiButtonType.ITEM_STACK
        internal set
    var material: Material = Material.AIR
        internal set
    var durability: Int = 0
        internal set
    var baseItem: ItemStack? = null
        internal set
    var display: String? = null
        internal set
    var lore: MutableList<String>? = null
        internal set
    var itemFlags: MutableSet<ItemFlag>? = null
        internal set
    var glow: Boolean = false
        internal set
    var action: MSGuiButtonAction? = null
        internal set
    var amount: Int = 1
        internal set
    var cancel: Boolean = false
        internal set
    var cleanable: Boolean = false
        internal set
    private val baseFlags: MutableSet<ItemFlag> get() = itemFlags ?: HashSet<ItemFlag>().apply { itemFlags = this }
    private val baseLore: MutableList<String> get() = lore ?: ArrayList<String>().apply { lore = this }
    internal var owner: OfflinePlayer? = null
    internal var url: String? = null

    fun setDisplayName(displayName: String?) { this.display = displayName }
    fun setLore(lore: List<String>?) {
        if (lore == null || lore.isEmpty()) this.lore = null
        else with(baseLore) {
            if (isNotEmpty()) baseLore.clear()
            baseLore.addAll(lore)
        }
    }
    fun addLore(line: String) {
        if (baseItem != null && baseItem!!.hasItemMeta() && baseItem!!.itemMeta.hasLore()) lore =
            baseItem!!.itemMeta.lore
        baseLore.add(line)
    }
    fun removeLore(index: Int) {
        if (baseItem != null && baseItem!!.hasItemMeta() && baseItem!!.itemMeta.hasLore()) lore =
            baseItem!!.itemMeta.lore
        if (index in 0 until if (lore == null) 0 else lore!!.size) baseLore.removeAt(index)
    }
    fun addItemFlags(vararg itemFlags: ItemFlag) { if (itemFlags.isNotEmpty()) itemFlags.forEach { baseFlags.add(it) } }
    fun setGlow(glow: Boolean) { this.glow = glow }
    fun setAction(action: MSGuiButtonAction) { this.action = action }
    fun setAmount(amount: Int) { this.amount = amount }
    fun setAction(ktFunc: (InventoryClickEvent)->Unit) { this.action = object: MSGuiButtonAction { override fun action(e: InventoryClickEvent) { ktFunc(e) } } }
    fun setCancellable(cancel: Boolean) { this.cancel = cancel }
    fun setCleanable(clean: Boolean) { this.cleanable = clean }
    fun setData(meta: ItemMeta) {
        if (display != null) meta.setDisplayName(display)
        if(lore != null) meta.lore = lore
        if (itemFlags != null) itemFlags?.toTypedArray()?.apply { meta.addItemFlags(*this) }
        if (glow) meta.addItemFlags(ItemFlag.HIDE_ENCHANTS)
    }
    internal abstract fun versionBuild(): ()-> ItemStack
    fun build(): MSGuiButton {
        val lastFunc: ((ItemStack)->Unit)? =  if (type == MSGuiButtonType.PLAYER_HEAD) { item->
            val meta = item.itemMeta as SkullMeta
            meta.owningPlayer = owner
            item.itemMeta = meta
        } else null
        return MSGuiButton(type, versionBuild(), lastFunc, action, cancel)
    }

}