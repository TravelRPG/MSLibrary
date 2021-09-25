import kr.msleague.msgui.gui.button.MSGuiButton
import kr.msleague.msgui.gui.button.MSGuiButtonAction
import kr.msleague.msgui.gui.button.MSGuiButtonData
import kr.msleague.msgui.gui.button.MSGuiButtonType
import kr.msleague.msgui.managers.SkullManager
import kr.msleague.util.extensions.addNBTTagCompound
import org.bukkit.Material
import org.bukkit.OfflinePlayer
import org.bukkit.enchantments.Enchantment
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.SkullMeta

class MSGuiButtonBuilderTemp {

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
    fun setDisplayName(displayName: String?): MSGuiButtonBuilderTemp {
        this.display = displayName
        return this
    }

    fun setLore(lore: List<String>?): MSGuiButtonBuilderTemp {
        if (lore == null || lore.isEmpty()) this.lore = null
        else
            with(baseLore) {
                if (isNotEmpty()) baseLore.clear()
                baseLore.addAll(lore)
            }
        return this
    }

    fun addLore(line: String): MSGuiButtonBuilderTemp {
        if (baseItem != null && baseItem!!.hasItemMeta() && baseItem!!.itemMeta.hasLore()) lore =
            baseItem!!.itemMeta.lore
        baseLore.add(line)
        return this
    }

    fun removeLore(index: Int): MSGuiButtonBuilderTemp {
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
    fun addItemFlags(vararg itemFlags: ItemFlag): MSGuiButtonBuilderTemp {
        if (itemFlags.isNotEmpty()) itemFlags.forEach { baseFlags.add(it) }
        return this
    }

    fun setGlow(glow: Boolean): MSGuiButtonBuilderTemp {
        this.glow = glow
        return this
    }

    fun setAction(action: MSGuiButtonAction): MSGuiButtonBuilderTemp {
        this.action = action
        return this
    }

    fun setAmount(amount: Int): MSGuiButtonBuilderTemp {
        this.amount = amount
        return this
    }

    fun setAction(ktFunc: (InventoryClickEvent)->Unit): MSGuiButtonBuilderTemp {
        this.action = object: MSGuiButtonAction { override fun action(e: InventoryClickEvent) { ktFunc(e) } }
        return this
    }

    var cancel: Boolean = false
        private set
    var cleanable: Boolean = false
        private set

    fun setCancellable(cancel: Boolean): MSGuiButtonBuilderTemp {
        this.cancel = cancel
        return this
    }

    fun setCleanable(clean: Boolean): MSGuiButtonBuilderTemp {
        this.cleanable = clean
        return this
    }

    fun build(): MSGuiButton {
        val makeFunc: () -> ItemStack =
            {
                (when (type) {
                    MSGuiButtonType.PLAYER_HEAD -> ItemStack(Material.SKULL_ITEM, amount, 3)
                    MSGuiButtonType.CUSTOM_HEAD -> SkullManager.getSkull(url?: "", amount)
                    else -> if(baseItem != null) baseItem!!.clone() else ItemStack(material, amount, durability.toShort())
                }).apply {
                    val meta = itemMeta
                    if (display != null) meta.displayName = display
                    if (lore != null) meta.lore = lore
                    if (itemFlags != null) itemFlags?.toTypedArray()?.apply { meta.addItemFlags(*this) }
                    if (glow) meta.addItemFlags(ItemFlag.HIDE_ENCHANTS)
                    itemMeta = meta
                    if (glow) addUnsafeEnchantment(Enchantment.LURE, 1)
                }.run { addNBTTagCompound(MSGuiButtonData(cancel, cleanable)) }
            }
        val lastFunc: ((ItemStack)->Unit)? =  if (type == MSGuiButtonType.PLAYER_HEAD) { item->
            val meta = item.itemMeta as SkullMeta
            meta.owningPlayer = owner
            item.itemMeta = meta
        } else null
        return MSGuiButton(type, makeFunc, lastFunc, action, cancel)
    }
}