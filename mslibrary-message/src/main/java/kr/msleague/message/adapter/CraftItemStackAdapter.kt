package kr.msleague.message.adapter

import kr.msleague.message.api.MSMessageAdapter
import kr.msleague.util.locale.friendlyName
import kr.msleague.util.locale.l10nDisplayName
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack

class CraftItemStackAdapter : MSMessageAdapter<CraftItemStack> {

    override fun request(obj: CraftItemStack, placeHolder: String): String? {
        return when (placeHolder) {
            "item_name" -> obj.l10nDisplayName
            "item_amount" -> obj.amount.toString()
            "item_type" -> obj.friendlyName
            else -> null
        }
    }
}