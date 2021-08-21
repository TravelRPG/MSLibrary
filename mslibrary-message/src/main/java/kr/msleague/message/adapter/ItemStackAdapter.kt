package kr.msleague.message.adapter

import kr.msleague.message.api.MSMessageAdapter
import kr.msleague.util.extensions.roundString
import kr.msleague.util.locale.*
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class ItemStackAdapter: MSMessageAdapter<ItemStack> {

    override fun request(obj: ItemStack, placeHolder: String): String? {
        return when(placeHolder) {
            "item_name" -> obj.l10nDisplayName
            "item_amount" -> obj.amount.toString()
            "item_type" -> obj.friendlyName
            else -> null
        }
    }
}