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
import java.util.*
import kotlin.collections.HashMap

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

    constructor(uuid: UUID) {
        type = MSGuiButtonType.PLAYER_HEAD
        this.uuid = uuid
    }

    constructor(offlinePlayer: OfflinePlayer) {
        type = MSGuiButtonType.PLAYER_HEAD
        this.owner = offlinePlayer
    }
    override fun versionBuild(it: ItemStack?): Pair<ItemStack, Boolean> =
        (when (type) {
            MSGuiButtonType.PLAYER_HEAD -> {
                if(it == null) ItemStack(Material.SKULL_ITEM, amount, 3) to false
                else it.apply { amount = this@MSGui12ButtonBuilder.amount } to true
            }
            MSGuiButtonType.CUSTOM_HEAD -> SkullManager.getSkull(url?: "", amount) to true
            else -> if(baseItem != null) baseItem!!.clone() to true else ItemStack(material, amount, durability.toShort()) to true
        }).apply {
            val meta = first.itemMeta
            meta.run(this@MSGui12ButtonBuilder::setData)
            first.itemMeta = meta
            if (glow) first.addUnsafeEnchantment(Enchantment.LURE, 1)
        }.run { first.addNBTTagCompound(MSGuiButtonData(cancel, cleanable)) to second }


}