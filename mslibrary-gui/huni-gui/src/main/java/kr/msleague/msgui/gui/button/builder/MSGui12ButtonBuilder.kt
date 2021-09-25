package kr.msleague.msgui.gui.button.builder

import kr.msleague.msgui.gui.button.MSGuiButtonBuilderABS
import kr.msleague.msgui.gui.button.MSGuiButtonData
import kr.msleague.msgui.gui.button.MSGuiButtonType
import kr.msleague.msgui.managers.SkullManager
import kr.msleague.util.extensions.addNBTTagCompound
import org.bukkit.Material
import org.bukkit.OfflinePlayer
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack

internal class MSGui12ButtonBuilder: MSGuiButtonBuilderABS {

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

    constructor(url: String) {
        type = MSGuiButtonType.CUSTOM_HEAD
        this.url = url
    }

    constructor(offlinePlayer: OfflinePlayer) {
        type = MSGuiButtonType.PLAYER_HEAD
        this.owner = offlinePlayer
    }

    override fun versionBuild(): () -> ItemStack = {
        (when (type) {
            MSGuiButtonType.PLAYER_HEAD -> ItemStack(Material.SKULL_ITEM, amount, 3)
            MSGuiButtonType.CUSTOM_HEAD -> SkullManager.getSkull(url?: "", amount)
            else -> if(baseItem != null) baseItem!!.clone() else ItemStack(material, amount, durability.toShort())
        }).apply {
            val meta = itemMeta
            meta.run(this@MSGui12ButtonBuilder::setData)
            itemMeta = meta
            if (glow) addUnsafeEnchantment(Enchantment.LURE, 1)
        }.run { addNBTTagCompound(MSGuiButtonData(cancel, cleanable)) }
    }

}