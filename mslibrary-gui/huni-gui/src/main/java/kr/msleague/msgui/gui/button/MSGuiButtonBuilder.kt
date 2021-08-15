package kr.msleague.msgui.gui.button

import kr.msleague.msgui.extensions.addNBTTagCompound
import kr.msleague.msgui.managers.SkullManager
import org.bukkit.Material
import org.bukkit.OfflinePlayer
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.SkullMeta
import kotlin.collections.ArrayList
import kotlin.collections.HashSet

class MSGuiButtonBuilder() {
    var type: MSGuiButtonType = MSGuiButtonType.ITEM_STACK
        private set
    var material: Material = Material.AIR
        private set
    var durability: Int = 0
        private set

    constructor(material: Material) : this() {
        type = MSGuiButtonType.ITEM_STACK
        this.material = material
    }

    constructor(material: Material, durability: Int) : this() {
        type = MSGuiButtonType.ITEM_STACK
        this.material = material
        this.durability = durability
    }

    private var url: String? = null

    constructor(url: String) : this() {
        type = MSGuiButtonType.CUSTOM_HEAD
        this.url = url
    }

    private var owner: OfflinePlayer? = null

    constructor(offlinePlayer: OfflinePlayer) : this() {
        type = MSGuiButtonType.PLAYER_HEAD
        this.owner = offlinePlayer
    }

    var display: String? = null
        private set
    var lore: MutableList<String>? = null
        private set
    private val baseLore: MutableList<String> get() = lore ?: ArrayList<String>().apply { lore = this }
    fun setDisplayName(displayName: String?): MSGuiButtonBuilder {
        this.display = display
        return this
    }

    fun setLore(lore: List<String>?): MSGuiButtonBuilder {
        if (lore == null || lore.isEmpty()) this.lore = null
        else
            with(baseLore) {
                if (isNotEmpty()) baseLore.clear()
                baseLore.addAll(lore)
            }
        return this
    }

    fun addLore(line: String): MSGuiButtonBuilder {
        baseLore.add(line)
        return this
    }

    fun removeLore(index: Int): MSGuiButtonBuilder {
        if (index in 0 until if (lore == null) 0 else lore!!.size) baseLore.removeAt(index)
        return this
    }

    var itemFlags: MutableSet<ItemFlag>? = null
        private set
    var glow: Boolean = false
        private set
    var action: MSGuiButtonAction? = null
        private set
    private val baseFlags: MutableSet<ItemFlag> get() = itemFlags ?: HashSet<ItemFlag>().apply { itemFlags = this }
    fun addItemFlags(vararg itemFlags: ItemFlag): MSGuiButtonBuilder {
        if (itemFlags.isNotEmpty()) itemFlags.forEach { baseFlags.add(it) }
        return this
    }

    fun setGlow(glow: Boolean): MSGuiButtonBuilder {
        this.glow = glow
        return this
    }

    fun setAction(action: MSGuiButtonAction): MSGuiButtonBuilder {
        this.action = action
        return this
    }

    var cancel: Boolean = false
        private set

    fun setCancellable(cancel: Boolean): MSGuiButtonBuilder {
        this.cancel = cancel
        return this
    }

    fun build(): MSGuiButton {
        val makeFunc: () -> ItemStack =
            {
                (when (type) {
                    MSGuiButtonType.PLAYER_HEAD -> ItemStack(Material.SKULL_ITEM, 1, 3).apply {
                        val meta = itemMeta as SkullMeta
                        meta.owningPlayer = owner
                        itemMeta = meta
                    }
                    MSGuiButtonType.CUSTOM_HEAD -> SkullManager.getSkull(url!!)
                    else -> ItemStack(material, 1, durability.toShort())
                }).apply {
                    val meta = itemMeta
                    if (display != null) meta.displayName = display
                    if (lore != null) meta.lore = lore
                    if (itemFlags != null) itemFlags?.toTypedArray()?.apply { meta.addItemFlags(*this) }
                    if (glow) meta.addItemFlags(ItemFlag.HIDE_ENCHANTS)
                    itemMeta = meta
                    addUnsafeEnchantment(Enchantment.LURE, 1)
                }.run { addNBTTagCompound(MSGuiButtonData(cancel)) }
            }
        return MSGuiButton(type, makeFunc, action, cancel)
    }
}