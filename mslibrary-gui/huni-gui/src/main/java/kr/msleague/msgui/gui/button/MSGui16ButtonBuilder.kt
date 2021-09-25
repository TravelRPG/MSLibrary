package kr.msleague.msgui.gui.button

import kr.msleague.msgui.managers.SkullManager
import kr.msleague.util.extensions.addNBTTagCompound16
import org.bukkit.Material
import org.bukkit.OfflinePlayer
import org.bukkit.enchantments.Enchantment
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.SkullMeta

internal class MSGui16ButtonBuilder: MSGuiButtonBuilderABS {
    var type: MSGuiButtonType = MSGuiButtonType.ITEM_STACK
        private set
    var material: Material = Material.AIR
        private set
    var durability: Int = 0
        private set
    var baseItem: ItemStack? = null
        private set

    constructor(material: Material) {
        type = MSGuiButtonType.ITEM_STACK
        this.material = material
    }

    constructor(baseItem: ItemStack) {
        type = MSGuiButtonType.ITEM_STACK
        this.baseItem = baseItem
    }

    constructor(material: Material, durability: Int) {
        type = MSGuiButtonType.ITEM_STACK
        this.material = material
        this.durability = durability
    }

    private var url: String? = null

    constructor(url: String) {
        type = MSGuiButtonType.CUSTOM_HEAD
        this.url = url
    }

    private var owner: OfflinePlayer? = null

    constructor(offlinePlayer: OfflinePlayer) {
        type = MSGuiButtonType.PLAYER_HEAD
        this.owner = offlinePlayer
    }

    var display: String? = null
        private set
    var lore: MutableList<String>? = null
        private set
    private val baseLore: MutableList<String> get() = lore ?: ArrayList<String>().apply { lore = this }
    override fun setDisplayName(displayName: String?): MSGui16ButtonBuilder {
        this.display = displayName
        return this
    }

    override fun setLore(lore: List<String>?): MSGui16ButtonBuilder {
        if (lore == null || lore.isEmpty()) this.lore = null
        else
            with(baseLore) {
                if (isNotEmpty()) baseLore.clear()
                baseLore.addAll(lore)
            }
        return this
    }

    override fun addLore(line: String): MSGui16ButtonBuilder {
        if (baseItem != null && baseItem!!.hasItemMeta() && baseItem!!.itemMeta.hasLore()) lore =
            baseItem!!.itemMeta.lore
        baseLore.add(line)
        return this
    }

    override fun removeLore(index: Int): MSGui16ButtonBuilder {
        if (baseItem != null && baseItem!!.hasItemMeta() && baseItem!!.itemMeta.hasLore()) lore =
            baseItem!!.itemMeta.lore
        if (index in 0 until if (lore == null) 0 else lore!!.size) baseLore.removeAt(index)
        return this
    }

    var itemFlags: MutableSet<ItemFlag>? = null
        private set
    var glow: Boolean = false
        private set
    var action: MSGuiButtonAction? = null
        private set
    var amount: Int = 1
        private set
    private val baseFlags: MutableSet<ItemFlag> get() = itemFlags ?: HashSet<ItemFlag>().apply { itemFlags = this }
    override fun addItemFlags(vararg itemFlags: ItemFlag): MSGui16ButtonBuilder {
        if (itemFlags.isNotEmpty()) itemFlags.forEach { baseFlags.add(it) }
        return this
    }

    override fun setGlow(glow: Boolean): MSGui16ButtonBuilder {
        this.glow = glow
        return this
    }

    override fun setAction(action: MSGuiButtonAction): MSGui16ButtonBuilder {
        this.action = action
        return this
    }

    override fun setAmount(amount: Int): MSGui16ButtonBuilder {
        this.amount = amount
        return this
    }

    override fun setAction(ktFunc: (InventoryClickEvent)->Unit): MSGui16ButtonBuilder {
        this.action = object: MSGuiButtonAction { override fun action(e: InventoryClickEvent) { ktFunc(e) } }
        return this
    }

    var cancel: Boolean = false
        private set
    var cleanable: Boolean = false
        private set

    override fun setCancellable(cancel: Boolean): MSGui16ButtonBuilder {
        this.cancel = cancel
        return this
    }

    override fun setCleanable(clean: Boolean): MSGui16ButtonBuilder {
        this.cleanable = clean
        return this
    }

    override fun build(): MSGuiButton {
        val makeFunc: () -> ItemStack = {
                (when (type) {
                    MSGuiButtonType.PLAYER_HEAD -> ItemStack(Material.valueOf("PLAYER_HEAD"), amount)
                    MSGuiButtonType.CUSTOM_HEAD -> SkullManager.getSkull(url?: "", amount)
                    else -> if(baseItem != null) baseItem!!.clone() else ItemStack(material, amount)
                }).apply {
                    val meta = itemMeta
                    if(this@MSGui16ButtonBuilder.durability != 0) {
                        val field = meta.javaClass.getDeclaredMethod("setCustomModelData", Int::class.java)
                        field.invoke(meta, this@MSGui16ButtonBuilder.durability)
                    }
                    if (display != null) meta.setDisplayName(display)
                    if (lore != null) meta.lore = lore
                    if (itemFlags != null) itemFlags?.toTypedArray()?.apply { meta.addItemFlags(*this) }
                    if (glow) meta.addItemFlags(ItemFlag.HIDE_ENCHANTS)
                    itemMeta = meta
                    if (glow) addUnsafeEnchantment(Enchantment.LURE, 1)
                }.run { addNBTTagCompound16(MSGuiButtonData(cancel, cleanable)) }
            }
        val lastFunc: ((ItemStack)->Unit)? =  if (type == MSGuiButtonType.PLAYER_HEAD) { item->
            val meta = item.itemMeta as SkullMeta
            meta.owningPlayer = owner
            item.itemMeta = meta
        } else null
        return MSGuiButton(type, makeFunc, lastFunc, action, cancel)
    }
}