package kr.msleague.msgui.gui.button.builder

import kr.msleague.msgui.gui.button.MSGuiButtonBuilderABS
import kr.msleague.msgui.gui.button.MSGuiButtonData
import kr.msleague.msgui.gui.button.MSGuiButtonType
import kr.msleague.msgui.managers.SkullManager
import kr.msleague.msgui.server
import kr.msleague.util.extensions.ItemStackExtension19.addNBTTagCompound19
import org.bukkit.Material
import org.bukkit.OfflinePlayer
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack
import java.util.*

internal class MSGui19ButtonBuilder: MSGuiButtonBuilderABS {

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
        this.uuid = offlinePlayer.uniqueId
    }

    //: (UUID, ItemStack?) -> Pair<ItemStack, Boolean> = { uuid, it ->
    //        (when (type) {
    //            MSGuiButtonType.PLAYER_HEAD -> {
    //                if(it == null) ItemStack(Material.SKULL_ITEM, amount, 3).apply { SkullManager.setHead(uuid, this) } to false
    //                else it to true
    //            }
    //            MSGuiButtonType.CUSTOM_HEAD -> SkullManager.getSkull(url?: "", amount) to true
    //            else -> if(baseItem != null) baseItem!!.clone() to true else ItemStack(material, amount, durability.toShort()) to true
    //        }).apply {
    //            val meta = first.itemMeta
    //            meta.run(this@MSGui12ButtonBuilder::setData)
    //            first.itemMeta = meta
    //            if (glow) first.addUnsafeEnchantment(Enchantment.LURE, 1)
    //        }.run { first.addNBTTagCompound(MSGuiButtonData(cancel, cleanable)) to second }
    //    }

    override fun versionBuild(item: String?): Pair<ItemStack, Boolean> =
        (when (type) {
            MSGuiButtonType.PLAYER_HEAD -> {
                if(item == null) ItemStack(Material.valueOf("PLAYER_HEAD"), amount) to false
                else item.run{
                    server.unsafe.modifyItemStack(ItemStack(Material.valueOf("PLAYER_HEAD"), amount), this).apply {
                        amount = this@MSGui19ButtonBuilder.amount
                    }
                } to true
            }
            MSGuiButtonType.CUSTOM_HEAD -> SkullManager.getSkull(url?: "", amount) to true
            else -> baseItem?.clone()?.run { this to true }?: ItemStack(material, amount) to true
        //if(baseItem != null) baseItem!!.clone() else ItemStack(material, amount)
        }).apply {
            val meta = first.itemMeta
            if(this@MSGui19ButtonBuilder.durability != 0) {
                val field = meta.javaClass.getDeclaredMethod("setCustomModelData", Integer::class.java)
                field.isAccessible = true
                field.invoke(meta, this@MSGui19ButtonBuilder.durability)
                field.isAccessible = false
            }
            meta.run(this@MSGui19ButtonBuilder::setData)
            first.itemMeta = meta
            if (glow) first.addUnsafeEnchantment(Enchantment.LURE, 1)
        }.run { first.addNBTTagCompound19(MSGuiButtonData(cancel, cleanable)) to second }


}